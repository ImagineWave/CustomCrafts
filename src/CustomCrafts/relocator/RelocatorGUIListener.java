package CustomCrafts.relocator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import CustomCrafts.Main;
import net.md_5.bungee.api.ChatColor;


public class RelocatorGUIListener implements Listener {
	private Main plugin;
	private RelocatorOpenGUIandCharge openGUIClass;
	private String guiName = "§3Открытие и настройка портала";
	public RelocatorGUIListener(Main plugin) {
		this.plugin = plugin;
		this.openGUIClass = new RelocatorOpenGUIandCharge(plugin);
	}
	@EventHandler
	public void guiClickEvent(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getView().getTitle().equalsIgnoreCase(guiName)) {
			if (e.getCurrentItem() == null) { //NULL CHECK
				e.setCancelled(true);
				return;
			}
			if (e.getCurrentItem().getType().equals(Material.RED_BANNER)) {
				locToConfig(p.getName(),"red",p.getLocation());
				p.closeInventory();
				openGUIClass.openGUI(p);
			}
			
			if (e.getCurrentItem().getType().equals(Material.BLUE_BANNER)) {
				locToConfig(p.getName(),"blue",p.getLocation());
				p.closeInventory();
				openGUIClass.openGUI(p);
			}
			
			if (e.getCurrentItem().getType().equals(Material.YELLOW_BANNER)) {
				locToConfig(p.getName(),"yellow",p.getLocation());
				p.closeInventory();
				openGUIClass.openGUI(p);
			}
			
			if (e.getCurrentItem().getType().equals(Material.LIME_BANNER)) {
				locToConfig(p.getName(),"green",p.getLocation());
				p.closeInventory();
				openGUIClass.openGUI(p);
			}
	
			if (e.getCurrentItem().getType().equals(Material.RED_CONCRETE)) {
				changeColor(p, ChatColor.RED, "red");
			}
			if (e.getCurrentItem().getType().equals(Material.BLUE_CONCRETE)) {
				changeColor(p, ChatColor.BLUE, "blue");
			}
			if (e.getCurrentItem().getType().equals(Material.YELLOW_CONCRETE)) {
				changeColor(p, ChatColor.YELLOW, "yellow");
			}
			if (e.getCurrentItem().getType().equals(Material.LIME_CONCRETE)) {
				changeColor(p, ChatColor.GREEN, "green");
			}
			e.setCancelled(true);
		}
	}
	private void changeColor (Player p, ChatColor color, String loreColor) {
		ItemStack item = p.getInventory().getItemInMainHand();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(color+"§l"+"Э.П.У.П.Т.");
		List<String> lore = item.getItemMeta().getLore();
		lore.set(4, loreColor);
		meta.setLore(lore);
		item.setItemMeta(meta);
		p.getInventory().setItemInMainHand(item);
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
