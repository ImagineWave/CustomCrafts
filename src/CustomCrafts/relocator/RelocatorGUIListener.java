package CustomCrafts.relocator;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import CustomCrafts.Main;
import net.md_5.bungee.api.ChatColor;


public class RelocatorGUIListener implements Listener {
	private Main plugin;
	private String guiName = "§3Открытие и настройка портала";
	public RelocatorGUIListener(Main plugin) {
		this.plugin = plugin;
	}
	
	public void guiClickEvent(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getView().getTitle().equalsIgnoreCase(guiName)) {
			if (e.getCurrentItem() == null) { //NULL CHECK
				e.setCancelled(true);
				return;
			}
			if (e.getCurrentItem().getType().equals(Material.RED_BANNER)) {
				locToConfig(p.getName(),"red",p.getLocation());
			}
			
			if (e.getCurrentItem().getType().equals(Material.BLUE_BANNER)) {
				locToConfig(p.getName(),"blue",p.getLocation());
			}
			
			if (e.getCurrentItem().getType().equals(Material.YELLOW_BANNER)) {
				locToConfig(p.getName(),"yellow",p.getLocation());
			}
			
			if (e.getCurrentItem().getType().equals(Material.LIME_BANNER)) {
				locToConfig(p.getName(),"green",p.getLocation());
			}
			//---------
			if (e.getCurrentItem().getType().equals(Material.RED_CONCRETE)) {
				changeColor(p, ChatColor.RED, "red");
			}
			if (e.getCurrentItem().getType().equals(Material.BLUE_CONCRETE)) {
				changeColor(p, ChatColor.BLUE, "blue");
			}
			if (e.getCurrentItem().getType().equals(Material.YELLOW_CONCRETE)) {
				changeColor(p, ChatColor.YELLOW, "yellow");
			}
			if (e.getCurrentItem().getType().equals(Material.GREEN_CONCRETE)) {
				changeColor(p, ChatColor.GREEN, "green");
			}
			e.setCancelled(true);
		}
	}
	private void changeColor (Player p, ChatColor color, String loreColor) {
		if(!p.getInventory().getItemInMainHand().equals(Material.NETHER_STAR)) {
			return;
		}
		ItemStack item = p.getInventory().getItemInMainHand();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BOLD+""+color+"Э.П.У.П.Т.");
		meta.getLore().set(4, loreColor);
		item.setItemMeta(meta);
		return;
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
}
