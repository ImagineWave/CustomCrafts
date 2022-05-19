package CustomCrafts.relocator;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
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

import Plugin.Main;
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
	//chronos(p);
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
		
		ItemMeta redMeta = redItem.getItemMeta();
		ItemMeta blueMeta = blueItem.getItemMeta();
		ItemMeta yellowMeta = yellowItem.getItemMeta();
		ItemMeta greenMeta = greenItem.getItemMeta();
		
		redMeta.setDisplayName(locToDisplayName(redLoc));
		blueMeta.setDisplayName(locToDisplayName(blueLoc));
		yellowMeta.setDisplayName(locToDisplayName(yellowLoc));
		greenMeta.setDisplayName(locToDisplayName(greenLoc));
		
		redItem.setItemMeta(redMeta);
		blueItem.setItemMeta(blueMeta);
		yellowItem.setItemMeta(yellowMeta);
		greenItem.setItemMeta(greenMeta);
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
	private Block createPortal(Location start, Location exit) {
		Block portal = start.getBlock();
		portal.setType(Material.END_GATEWAY);
		EndGateway portalData = (EndGateway) portal.getState(); //TODO СУПЕР ПРОБЛЕМНАЯ ХЕРНЯ, КОТОРАЯ НИХЕРА НЕ БУДЕТ РАБОТАТЬ
		portalData.setExitLocation(exit);
		return portal;
	}
}
