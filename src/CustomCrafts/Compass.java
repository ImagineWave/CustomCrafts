package CustomCrafts;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Compass implements Listener{
	private Main plugin;
	public Compass(Main plugin) {
		this.plugin = plugin;
	}
@EventHandler
public void use(PlayerInteractEvent e) {
	if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() !=Action.RIGHT_CLICK_BLOCK) return;
	if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.COMPASS) return;
	Player p = e.getPlayer();
	ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
	if(!item.getItemMeta().hasDisplayName()) return;
	if(!item.getItemMeta().hasLore()) return;
	if(!item.getItemMeta().getDisplayName().equals("§6Компас возвращения")) return;
	if(!item.getItemMeta().getLore().get(0).equals("§aВозвращает владельца домой")) return;
	if(p.getInventory().getItemInOffHand().getType() == Material.DRAGON_BREATH) {
		if(recharged(p,item)) {
			consumeDragonBreath(p);
			p.sendMessage("§aЗаряд восстановлен");
			return;
		}
		p.sendMessage("§cВ предмете уже максимально количество зарядов");
		return;
	}
	if(onCooldown(p)) {
		return;
	}
	if(compassCheck(p,item)) {
		relocate(p);
		setCooldown(p);
		return;
	}
}
public boolean compassCheck(Player p, ItemStack item) {
	ItemMeta meta = item.getItemMeta();
	List<String> lore = item.getItemMeta().getLore();
	if(item.getItemMeta().getLore().get(2).equals("")) {
		Integer charge = Integer.parseInt(lore.get(4));
		charge = charge - 1;
		lore.set(2, p.getName());
		lore.set(4, charge.toString());
		meta.setLore(lore);
		item.setItemMeta(meta);
		return true;
	}
	if(!item.getItemMeta().getLore().get(2).equals(p.getName())) {
		p.sendMessage("§cПредмет отвергает вас");
		return false;
	}
	if(item.getItemMeta().getLore().get(4).equals("0")) {
		p.sendMessage("§cЗакончились заряды");
		return false;
	}
	if(!item.getItemMeta().getLore().get(4).equals("0")) {
		Integer charge = Integer.parseInt(lore.get(4));
		charge = charge - 1;
		lore.set(4, charge.toString());
		meta.setLore(lore);
		item.setItemMeta(meta);
		return true;
	}
	return false;
}
public void consumeDragonBreath(Player p) {
	ItemStack bottle = new ItemStack (Material.GLASS_BOTTLE);
	int amount = p.getInventory().getItemInOffHand().getAmount();
	amount = amount - 1;
	if (amount!=0) {
		p.getInventory().getItemInOffHand().setAmount(amount);
		p.getInventory().addItem(bottle);
		return;
	}
	p.getInventory().setItemInOffHand(bottle);
	return;
}
public boolean recharged(Player p, ItemStack item) {
	ItemMeta meta = item.getItemMeta();
	List<String> lore = item.getItemMeta().getLore();
	Integer charge = Integer.parseInt(lore.get(4));
	charge = charge + 1;
	if(charge>5) return false;
	lore.set(4, charge.toString());
	meta.setLore(lore);
	item.setItemMeta(meta);
	return true;
}
public void relocate(Player p) {
	Location home = p.getBedSpawnLocation();
	p.teleport(home);
	p.sendMessage("§a Добро пожаловать домой");
	return;
}
public boolean onCooldown(Player p) {
	File cooldowns = new File(plugin.getDataFolder() + File.separator + "Cooldowns.yml");
	FileConfiguration h = YamlConfiguration.loadConfiguration(cooldowns);
	Long lastUsage = h.getLong("cooldonws."+p.getName()+".compass");
	if(lastUsage+600*1000> System.currentTimeMillis()) {
		Long cd = (lastUsage+600*1000 - System.currentTimeMillis());
		String msg = formatDuration(cd/1000);
		p.sendMessage("§cПредмет на перезарядке еще " + msg);
		return true;
	}
	return false;
}
public void setCooldown(Player p) {
	File cooldowns = new File(plugin.getDataFolder() + File.separator + "Cooldowns.yml");
	FileConfiguration h = YamlConfiguration.loadConfiguration(cooldowns);
	Long time = System.currentTimeMillis();
	h.set("cooldonws."+p.getName()+".compass", time);
	try {
		h.save(cooldowns);
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	return;
}
public HashMap<Player , ItemStack> items = new HashMap<Player , ItemStack>();
@EventHandler (priority = EventPriority.HIGHEST)
public void onPlayerDeath(PlayerDeathEvent e) {
	Player p = (Player) e.getEntity();
	if(playerHasTotem(p)) {
		return;
	}
	if(playerHasItem(p)) {
		ItemStack compass = items.get(p);
		e.getDrops().remove(compass);
		return;
	}
}
@EventHandler
public void onPlayerRespawn(PlayerRespawnEvent e) {
	Player p = e.getPlayer();
	 if(items.containsKey(p)) {
		 ItemStack item = items.get(p);
		 item.setAmount(1);
		 p.getInventory().addItem(item);
	 }
	 items.remove(e.getPlayer());
}
public boolean playerHasItem(Player p) {
	for (ItemStack item : p.getInventory().getContents()) {
        if(checkItem(p,item)) {
        	items.put(p,item);
        	return true;
        }
    }
	return false;
}
public boolean checkItem(Player p,ItemStack item){
	if(item == null) return false;
	if(item.getType()!=Material.COMPASS) return false;
	if(!item.getItemMeta().hasDisplayName()) return false;
	if(!item.getItemMeta().hasLore()) return false;
	if(!item.getItemMeta().getDisplayName().equals("§6Компас возвращения")) return false;
	if(!item.getItemMeta().getLore().get(0).equals("§aВозвращает владельца домой")) return false;
	if(item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLore()) {
		
		if(item.getItemMeta().getDisplayName().equals("§6Компас возвращения") && item.getItemMeta().getLore().get(2).equals(p.getName())){
			return true;
		}
	}
	return false;
}
public boolean playerHasTotem(Player p) {
	for (ItemStack item : p.getInventory().getContents()) {
        if(checkTotem(item)) {
        	return true;
        }
    }
	return false;
}
public boolean checkTotem(ItemStack item){
	if(item == null) return false;
	if(!item.getItemMeta().hasDisplayName()) return false;
	if(!item.getItemMeta().hasLore()) return false;
	if(!item.getItemMeta().getDisplayName().equals("§a§lБессмертие")) return false;
	if(item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLore()) {
		
		if(item.getItemMeta().getDisplayName().equals("§a§lБессмертие")){
			return true;
		}
	}
	return false;
}
public String formatDuration(Long time) {
	long days = time/86400;
	long hours = time%86400/3600;
	long minutes = time%86400%3600/60;
	long seconds = time%86400%3600%60;
	String stdays = Long.toString(days);
	String sthours = Long.toString(hours);
	String stminutes = Long.toString(minutes);
	String stseconds = Long.toString(seconds);
	String msgduration = "§6";
	if(days != 0) msgduration += stdays + " §4Day(s)§6 ";
	if(hours != 0) msgduration += sthours + " §4Hour(s)§6 ";
	if(minutes != 0) msgduration += stminutes + " §4Minute(s)§6 ";
	if(seconds != 0) msgduration += stseconds + " §4Second(s) ";
	return msgduration;
}
}
