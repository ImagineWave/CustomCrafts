package CustomCrafts.relocator;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.EndGateway;
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
	
	}
	private boolean isCharged(Player p) {
		ItemStack item = p.getInventory().getItemInMainHand();
		ItemMeta meta = item.getItemMeta();
		Integer count = Integer.parseInt(meta.getLore().get(3));
		return count>=5;
	}
	private void openGUI(Player p) {
		String guiName = "§3Открытие и настройка портала";
		
		Location redLoc = getPortalExit(p.getName(), "red");
		Location blueLoc = getPortalExit(p.getName(), "blue");
		Location yellowLoc = getPortalExit(p.getName(), "yellow");
		Location greenLoc = getPortalExit(p.getName(), "green");
		
		Inventory gui = Bukkit.createInventory(p, 9*3, guiName);
		
		ItemStack redItem = new ItemStack(Material.RED_CONCRETE);
		ItemStack blueItem = new ItemStack(Material.BLUE_CONCRETE);
		ItemStack yellowItem = new ItemStack(Material.YELLOW_CONCRETE);
		ItemStack greenItem = new ItemStack(Material.LIME_CONCRETE);
		
		ItemStack redFlagItem = new ItemStack(Material.RED_BANNER);
		ItemStack blueFlagItem = new ItemStack(Material.BLUE_BANNER);
		ItemStack yellowFlagItem = new ItemStack(Material.YELLOW_BANNER);
		ItemStack greenFlagItem = new ItemStack(Material.LIME_BANNER);
		
		ItemMeta redMeta = redItem.getItemMeta();
		ItemMeta blueMeta = blueItem.getItemMeta();
		ItemMeta yellowMeta = yellowItem.getItemMeta();
		ItemMeta greenMeta = greenItem.getItemMeta();
		
		ItemMeta redFlagMeta = redFlagItem.getItemMeta();
		ItemMeta blueFlagMeta = blueFlagItem.getItemMeta();
		ItemMeta yellowFlagMeta = yellowFlagItem.getItemMeta();
		ItemMeta greenFlagMeta = greenFlagItem.getItemMeta();
		
		redMeta.setDisplayName(locToDisplayName(redLoc));
		blueMeta.setDisplayName(locToDisplayName(blueLoc));
		yellowMeta.setDisplayName(locToDisplayName(yellowLoc));
		greenMeta.setDisplayName(locToDisplayName(greenLoc));
		
		redFlagMeta.setDisplayName("Установить "+ ChatColor.RED +"КРУСНУЮ "+ChatColor.RESET+"точку");
		blueFlagMeta.setDisplayName("Установить "+ ChatColor.BLUE +"СИНЮЮ "+ChatColor.RESET+"точку");
		yellowFlagMeta.setDisplayName("Установить "+ ChatColor.YELLOW +"ЖЁЛТУЮ "+ChatColor.RESET+"точку");
		greenFlagMeta.setDisplayName("Установить "+ ChatColor.GREEN +"ЗЕЛЁНУЮ "+ChatColor.RESET+"точку");
		
		redItem.setItemMeta(redMeta);
		blueItem.setItemMeta(blueMeta);
		yellowItem.setItemMeta(yellowMeta);
		greenItem.setItemMeta(greenMeta);
		
		redFlagItem.setItemMeta(redFlagMeta);
		blueFlagItem.setItemMeta(blueFlagMeta);
		yellowFlagItem.setItemMeta(yellowFlagMeta);
		greenFlagItem.setItemMeta(greenFlagMeta);
		
		gui.setItem(1, redItem);
		gui.setItem(2, blueItem);
		gui.setItem(3, yellowItem);
		gui.setItem(4, greenItem);
		
		gui.setItem(1, redFlagItem);
		gui.setItem(2, blueFlagItem);
		gui.setItem(3, yellowFlagItem);
		gui.setItem(4, greenFlagItem);
	}
	private String locToDisplayName(Location loc) {
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
	private Location getPortalExit(String name, String color) {
		File file = new File(plugin.getDataFolder() + File.separator + "Relocator.yml");
		FileConfiguration f = YamlConfiguration.loadConfiguration(file);
		Location loc = new Location(Bukkit.getServer().getWorld(
				f.getString("relocator." + name +"."+ color +".world")),
				f.getDouble("relocator." + name +"."+ color +".x"),
				f.getDouble("relocator." + name +"."+ color +".y"),
				f.getDouble("relocator." + name +"."+ color +".z"));
		return loc;
	}
	private void locToConfig (String name, String color, Location loc) {
		File file = new File(plugin.getDataFolder() + File.separator + "Relocator.yml");
		FileConfiguration f = YamlConfiguration.loadConfiguration(file);
		f.set("relocator." + name + "."+ color +".world", loc.getWorld().getName());
		f.set("relocator." + name + "."+ color +".x", loc.getBlockX());
		f.set("relocator." + name + "."+ color +".y", loc.getBlockY());
		f.set("relocator." + name + "."+ color +".z", loc.getBlockZ());
				try {
			f.save(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	 }
	private void createPortal(Location start, Location exit) {
		Block portal = start.getBlock();
		EndGateway gate = (EndGateway) portal.getState();
		gate.setExitLocation(exit);
		gate.setExactTeleport(false);
	}
}
