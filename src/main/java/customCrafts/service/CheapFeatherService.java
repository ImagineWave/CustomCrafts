package customCrafts.service;

import customCrafts.Main;
import customCrafts.entities.TemporaryFlyingPlayer;
import customCrafts.itemStack.CheapFeatherItem;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheapFeatherService extends BukkitRunnable implements Listener {
    private final Main plugin;
    private static final CheapFeatherService INSTANCE = new CheapFeatherService();
    private static final int BLOCK_RANGE_LIMIT = 150;
    private static final long FLIGHT_DURATION = 10*60*1000;

    private Map<String, TemporaryFlyingPlayer> flyingPlayers = new HashMap<>();

    private CheapFeatherService() {
        plugin = Main.getInstance();
    }
    public static CheapFeatherService getInstance() {
        return INSTANCE;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(!canPlayerFlyAfterJoin(player)) {
            disableFlightSequence(player);
        } else {
            player.setAllowFlight(true);
        }
    }

    @EventHandler
    public void onFeatherActivation(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = CheapFeatherItem.getItem();
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(player.getInventory().getItemInMainHand().getItemMeta() == null){
            return;
        }
        if(!player.getInventory().getItemInMainHand().getItemMeta().hasLore()){
            return;
        }
        if (!player.getInventory().getItemInMainHand().getItemMeta().getLore().equals(item.getItemMeta().getLore())){
            return;
        }
        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
        enableFlightSequence(player);
    }

    @Override
    public void run() {
        checkAllFlyingPlayers();
    }

    public boolean isTemporaryFlying(Player player) {
        return flyingPlayers.containsKey(player.getName());
    }

    private void checkAllFlyingPlayers() {
        for(Map.Entry<String, TemporaryFlyingPlayer> entry : flyingPlayers.entrySet()) {
            if(Bukkit.getPlayer(entry.getKey()) == null){
                continue;
            }
            Player player = Bukkit.getPlayer(entry.getKey());
            if(!canPlayerFly(player)){
                disableFlightSequence(player);
            }
        }
    }

    private void addPlayerToFlyingList(Player p){
        File players = new File(plugin.getDataFolder() + File.separator + "flying_players.yml");
        FileConfiguration users = YamlConfiguration.loadConfiguration(players);
        List<String> list = users.getStringList("players");
        if(list.contains(p.getName())){
            return;
        }
        list.add(p.getName());
        users.set("players", list);
        try {
            users.save(players);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void removePlayerFromFlyingList(Player p){
        File players = new File(plugin.getDataFolder() + File.separator + "flying_players.yml");
        FileConfiguration users = YamlConfiguration.loadConfiguration(players);
        List<String> list = users.getStringList("players");
        list.remove(p.getName());
        users.set("players", list);
        try {
            users.save(players);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean playerWasFlying(String playerName){
        File players = new File(plugin.getDataFolder() + File.separator + "flying_players.yml");
        FileConfiguration users = YamlConfiguration.loadConfiguration(players);
        List<String> list = users.getStringList("players");
        return list.contains(playerName);
    }
    private boolean canPlayerFly(Player player){
        if ((player.getGameMode().equals(GameMode.CREATIVE))||player.getGameMode().equals(GameMode.SPECTATOR)){
            return true;
        }
        if(!flyingPlayers.containsKey(player.getName())){
            return true;
        }
        TemporaryFlyingPlayer flyingPlayer = flyingPlayers.get(player.getName());
        long flyEndTime = flyingPlayer.getEndTime();
        if(System.currentTimeMillis() > flyEndTime){
            return false;
        }
        showCountDownIfLessThan10Seconds(player, flyEndTime);
        return checkPosition(player, flyingPlayer);
    }

    private boolean canPlayerFlyAfterJoin(Player player){
        if(playerWasFlying(player.getName())){
           if(flyingPlayers.containsKey(player.getName())){
               return canPlayerFly(player);
           }
           else return false;
        } else {
            return canPlayerFly(player);
        }
    }

    private void showCountDownIfLessThan10Seconds(Player player, long flyEndTime){
        if(flyEndTime-System.currentTimeMillis() < 5*1000){
            long secondsLeft = (flyEndTime-System.currentTimeMillis())/1000;
            player.sendMessage("§cПолет закончится через: §4"+secondsLeft);
        }
    }

    private void disableFlightSequence(Player player){
        flyingPlayers.remove(player.getName());
        removePlayerFromFlyingList(player);
        player.setFlying(false);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 10*20, 1));
        Vector downwardImpulse = new Vector(0, -5, 0); // по Y вниз
        player.setVelocity(player.getVelocity().add(downwardImpulse));
    }

    private void enableFlightSequence(Player player){
        TemporaryFlyingPlayer flyingPlayer = new TemporaryFlyingPlayer();
        flyingPlayer.setPlayerName(player.getName());
        flyingPlayer.setEndTime(System.currentTimeMillis()+FLIGHT_DURATION);
        flyingPlayer.setWorld(player.getLocation().getWorld().getName()); //getName в worldInfo @NotNull
        flyingPlayer.setX(player.getLocation().getBlockX());
        flyingPlayer.setY(player.getLocation().getBlockY());
        flyingPlayer.setZ(player.getLocation().getBlockZ());
        if(flyingPlayers.containsKey(player.getName())){
            TemporaryFlyingPlayer alreadyFlyingPlayer = flyingPlayers.get(player.getName());
            alreadyFlyingPlayer.setEndTime(alreadyFlyingPlayer.getEndTime()+FLIGHT_DURATION);
            flyingPlayers.put(player.getName(), alreadyFlyingPlayer);
        } else {
            flyingPlayers.put(player.getName(), flyingPlayer);
            addPlayerToFlyingList(player);
        }
        player.setAllowFlight(true);
    }

    private boolean checkPosition(Player p, TemporaryFlyingPlayer flyingPlayer){
        Location loc = p.getLocation();
        int dx = Math.abs(loc.getBlockX() - flyingPlayer.getX());
        int dy = Math.abs(loc.getBlockY() - flyingPlayer.getY());
        int dz = Math.abs(loc.getBlockZ() - flyingPlayer.getZ());
        return dx <= BLOCK_RANGE_LIMIT && dy <= BLOCK_RANGE_LIMIT*5 && dz <= BLOCK_RANGE_LIMIT;
    }


}
