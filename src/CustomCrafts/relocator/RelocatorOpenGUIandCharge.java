package CustomCrafts.relocator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.block.EndGateway;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import CustomCrafts.Main;
import net.md_5.bungee.api.ChatColor;

public class RelocatorOpenGUIandCharge implements Listener {
	private Main plugin;
	public RelocatorOpenGUIandCharge (Main plugin) {
		this.plugin = plugin;
	}
	private static Map<Player,Long> map = new HashMap<>();
	@EventHandler
	public void use(PlayerInteractEvent e) {
	if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() !=Action.RIGHT_CLICK_BLOCK) return;
	if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.NETHER_STAR) return;
	Player p = e.getPlayer();
	ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
	if(!item.getItemMeta().hasDisplayName()) return;
	if(!item.getItemMeta().hasLore()) return;
	if(!item.getItemMeta().getLore().get(0).equals("Экспериментальное портативное устройство")) return;
	if(!item.getItemMeta().getLore().get(1).equals("пространственного туннелирования")) return;
	if(p.getInventory().getItemInOffHand().getType() == Material.DRAGON_BREATH) {
		if(recharged(p,item)) {
			consumeDragonBreath(p);
			return;
		}
		p.sendMessage("§cВ предмете уже максимально количество зарядов");
		return;
	}
	openGUI(p);
	}
	@EventHandler
	public void useLEFT(PlayerInteractEvent e) {
		if (e.getAction() != Action.LEFT_CLICK_BLOCK) return;
		if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.NETHER_STAR) return;
		Player p = e.getPlayer();
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		if(!item.getItemMeta().hasDisplayName()) return;
		if(!item.getItemMeta().hasLore()) return;
		if(!item.getItemMeta().getLore().get(0).equals("Экспериментальное портативное устройство")) return;
		if(!item.getItemMeta().getLore().get(1).equals("пространственного туннелирования")) return;
		String color = item.getItemMeta().getLore().get(4);
		Location exit = getPortalExit(p.getName(),color);
		if(!isReady(p)) {
			return;
		}
		if(!e.getClickedBlock().getWorld().equals(exit.getWorld())) {
			p.sendMessage("§cТочка входа и точка выхода должны быть в одном мире");
			return;
		}
		if(checkCharges(p, item)) {
			if(e.getClickedBlock().getType().equals(Material.END_GATEWAY)) {
				return;
			}
			createPortal(e.getClickedBlock().getLocation(),exit);
			consumeCharges(p, item);
			return;
		}
		p.sendMessage("§cВ предмете недостаточно зарядов");
		}
	private boolean recharged(Player p, ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = item.getItemMeta().getLore();
		Integer charge = Integer.parseInt(lore.get(3));
		charge = charge + 1;
		if(charge>60) return false; //МАКС зарядов
		lore.set(3, charge.toString());
		meta.setLore(lore);
		item.setItemMeta(meta);
		return true;
	}
	private void consumeDragonBreath(Player p) {
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
	private void consumeCharges(Player p, ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = item.getItemMeta().getLore();
		Integer charge = Integer.parseInt(lore.get(3));
		int cost = amountPerPortal(p);
		charge = charge - cost; //Потребление зарядов
		lore.set(3, charge.toString());
		meta.setLore(lore);
		item.setItemMeta(meta);
	}
	private int amountPerPortal(Player p) {
		int baseConsume = 6;
		int discount = 0;
		if(getPortalExit(p.getName(), "cyan")== null) {
			discount++;
		}
		if(getPortalExit(p.getName(), "orange")== null) {
			discount++;
		}
		return baseConsume - discount;
	}
	private boolean checkCharges(Player p, ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = item.getItemMeta().getLore();
		Integer charge = Integer.parseInt(lore.get(3));
		return charge>=2;
	}
	public void openGUI(Player p) {
		String guiName = "§3Открытие и настройка портала";
		
		Location redLoc = getPortalExit(p.getName(), "red");
		Location blueLoc = getPortalExit(p.getName(), "blue");
		Location yellowLoc = getPortalExit(p.getName(), "yellow");
		Location greenLoc = getPortalExit(p.getName(), "green");
		//-------
		Location cyanLoc = getPortalExit(p.getName(), "cyan");
		Location orangeLoc = getPortalExit(p.getName(), "orange");
		//-------
		Inventory gui = Bukkit.createInventory(p, 9*4, guiName);
		
		ItemStack redItem = new ItemStack(Material.RED_CONCRETE);
		if(redLoc == null) {
			redItem.setType(Material.WHITE_CONCRETE);
		}
		ItemStack blueItem = new ItemStack(Material.BLUE_CONCRETE);
		if(blueLoc == null) {
			blueItem.setType(Material.WHITE_CONCRETE);
		}
		ItemStack yellowItem = new ItemStack(Material.YELLOW_CONCRETE);
		if(yellowLoc == null) {
			yellowItem.setType(Material.WHITE_CONCRETE);
		}
		ItemStack greenItem = new ItemStack(Material.LIME_CONCRETE);
		if(greenLoc == null) {
			greenItem.setType(Material.WHITE_CONCRETE);
		}
		//-----
		ItemStack cyanItem = new ItemStack(Material.CYAN_CONCRETE);
		if(cyanLoc == null) {
			cyanItem.setType(Material.WHITE_CONCRETE);
		}
		ItemStack orangeItem = new ItemStack(Material.ORANGE_CONCRETE);
		if(orangeLoc == null) {
			orangeItem.setType(Material.WHITE_CONCRETE);
		}
		//-------
		
		
		ItemStack redFlagItem = new ItemStack(Material.RED_BANNER);
		ItemStack blueFlagItem = new ItemStack(Material.BLUE_BANNER);
		ItemStack yellowFlagItem = new ItemStack(Material.YELLOW_BANNER);
		ItemStack greenFlagItem = new ItemStack(Material.LIME_BANNER);
		//-------
		ItemStack cyanFlagItem = new ItemStack(Material.CYAN_BANNER);
		ItemStack orangeFlagItem = new ItemStack(Material.ORANGE_BANNER);
		//-------
		

		ItemMeta redMeta = redItem.getItemMeta();
		ItemMeta blueMeta = blueItem.getItemMeta();
		ItemMeta yellowMeta = yellowItem.getItemMeta();
		ItemMeta greenMeta = greenItem.getItemMeta();
		//-------
		ItemMeta cyanMeta = cyanItem.getItemMeta();
		ItemMeta orangeMeta = orangeItem.getItemMeta();
		//-------
		
		
		ItemMeta redFlagMeta = redFlagItem.getItemMeta();
		ItemMeta blueFlagMeta = blueFlagItem.getItemMeta();
		ItemMeta yellowFlagMeta = yellowFlagItem.getItemMeta();
		ItemMeta greenFlagMeta = greenFlagItem.getItemMeta();
		//-------
		ItemMeta cyanFlagMeta = cyanFlagItem.getItemMeta();
		ItemMeta orangeFlagMeta = orangeFlagItem.getItemMeta();
		//-------
		
		redMeta.setDisplayName(locToDisplayName(redLoc));
		blueMeta.setDisplayName(locToDisplayName(blueLoc));
		yellowMeta.setDisplayName(locToDisplayName(yellowLoc));
		greenMeta.setDisplayName(locToDisplayName(greenLoc));
		//-------
		cyanMeta.setDisplayName(locToDisplayName(cyanLoc));
		orangeMeta.setDisplayName(locToDisplayName(orangeLoc));
		//-------
		
		redFlagMeta.setDisplayName("Установить "+ ChatColor.RED +"КРАСНУЮ "+ChatColor.RESET+"точку");
		blueFlagMeta.setDisplayName("Установить "+ ChatColor.BLUE +"СИНЮЮ "+ChatColor.RESET+"точку");
		yellowFlagMeta.setDisplayName("Установить "+ ChatColor.YELLOW +"ЖЁЛТУЮ "+ChatColor.RESET+"точку");
		greenFlagMeta.setDisplayName("Установить "+ ChatColor.GREEN +"ЗЕЛЁНУЮ "+ChatColor.RESET+"точку");
		//-------
		cyanFlagMeta.setDisplayName("Установить "+ ChatColor.DARK_AQUA +"БИРЮЗОВУЮ "+ChatColor.RESET+"точку");
		orangeFlagMeta.setDisplayName("Установить "+ ChatColor.GOLD +"ОРАНЖЕНУЮ "+ChatColor.RESET+"точку");
		//-------
		
		redItem.setItemMeta(redMeta);
		blueItem.setItemMeta(blueMeta);
		yellowItem.setItemMeta(yellowMeta);
		greenItem.setItemMeta(greenMeta);
		//-------
		cyanItem.setItemMeta(cyanMeta);
		orangeItem.setItemMeta(orangeMeta);
		//-------
		
		redFlagItem.setItemMeta(redFlagMeta);
		blueFlagItem.setItemMeta(blueFlagMeta);
		yellowFlagItem.setItemMeta(yellowFlagMeta);
		greenFlagItem.setItemMeta(greenFlagMeta);
		//-------
		cyanFlagItem.setItemMeta(cyanFlagMeta);
		orangeFlagItem.setItemMeta(orangeFlagMeta);
		//-------
		
		gui.setItem(10, redItem);
		gui.setItem(12, blueItem);
		gui.setItem(14, yellowItem);
		gui.setItem(16, greenItem);
		gui.setItem(29, cyanItem);
		gui.setItem(33, orangeItem);
		//-------
		
		gui.setItem(1, redFlagItem);
		gui.setItem(3, blueFlagItem);
		gui.setItem(5, yellowFlagItem);
		gui.setItem(7, greenFlagItem);
		gui.setItem(20, cyanFlagItem);
		gui.setItem(24, orangeFlagItem);
		//-------
		ItemStack cyanUpgrade = new ItemStack(Material.MUSIC_DISC_5);
		ItemStack orangeUpgrade = new ItemStack(Material.MUSIC_DISC_PIGSTEP);
		ItemMeta cyanUpgradeMeta = cyanUpgrade.getItemMeta();
		ItemMeta orangeUpgradeMeta = orangeUpgrade.getItemMeta();
		cyanUpgradeMeta.setDisplayName("Добавить бирюзовую точку");
		orangeUpgradeMeta.setDisplayName("Добавить оранжевую точку");
		
		List<String> cyanLore = new ArrayList<>();
		cyanLore.add("Для улучшения требуется");
		cyanLore.add("пластинка §3#5");
		cyanUpgradeMeta.setLore(cyanLore);
		cyanUpgrade.setItemMeta(cyanUpgradeMeta);
		
		List<String> orangeLore = new ArrayList<>();
		orangeLore.add("Для улучшения требуется");
		orangeLore.add("пластинка §6Pigstep");
		orangeUpgradeMeta.setLore(orangeLore);
		orangeUpgrade.setItemMeta(orangeUpgradeMeta);
		
		if(cyanItem.getType().equals(Material.WHITE_CONCRETE)) {
			gui.setItem(20, cyanUpgrade);
		}
		if(orangeItem.getType().equals(Material.WHITE_CONCRETE)) {
			gui.setItem(24, orangeUpgrade);
		}
		p.openInventory(gui);
	}
	private String locToDisplayName(Location loc) {
		if(loc == null) {
			return "Точка не задана";
		}
		String result = "";
		switch (loc.getWorld().getName()) {
		case("world"):{
			result = result+ChatColor.GREEN;
			break;
		}
		case("world_nether"):{
			result = result+ChatColor.RED;
			break;
		}
		case("world_the_end"):{
			result = result+ChatColor.DARK_PURPLE;
			break;
		}
		default:{
			result = result+"Локация не обнаружена";
			break;
		}
		}
		result = result + loc.getBlockX() + " ";
		result = result + loc.getBlockY() + " ";
		result = result + loc.getBlockZ() + " ";
		return result;
	}
	private Location getPortalExit(String name, String color) { //TODO ПРОБЛЕМА ТУТ
		File file = new File(plugin.getDataFolder() + File.separator + "Relocator.yml");
		FileConfiguration f = YamlConfiguration.loadConfiguration(file);
		
		try {
			Location loc = new Location(Bukkit.getServer().getWorld(
					f.getString("relocator." + name +"."+ color +".world")),
					f.getDouble("relocator." + name +"."+ color +".x"),
					f.getDouble("relocator." + name +"."+ color +".y"),
					f.getDouble("relocator." + name +"."+ color +".z"));
			return loc;
		} catch(IllegalArgumentException e1) {
			
		}
		return null;
	}
	private void createPortal(Location start, Location exit) {
		Block portal = start.getBlock();
		Material old = portal.getType();
		BlockState oldState = portal.getState();
		portal.setType(Material.END_GATEWAY);
		BlockState state = portal.getState();
		if(state instanceof EndGateway) {
			EndGateway gate = (EndGateway) state;
			gate.setExitLocation(exit);
			gate.setExactTeleport(true);
			state.update();
		}
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
            	portal.setType(old);
            	oldState.update();
            }
        }, 5*20L);
	}
	private Boolean isReady(Player p) {
		Long cooldown = 1000*5l;
		if(map.containsKey(p)) {
			if(System.currentTimeMillis()>map.get(p)) {
				map.put(p, System.currentTimeMillis()+cooldown);
				return true;
			}
			return false;
		}
		map.put(p, System.currentTimeMillis()+cooldown);
		return true;
	}
}
