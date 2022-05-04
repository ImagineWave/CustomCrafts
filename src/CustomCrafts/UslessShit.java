package CustomCrafts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UslessShit implements Listener{
	private Main plugin;
	public UslessShit(Main plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void use(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() !=Action.RIGHT_CLICK_BLOCK) return;
		ArrayList<Material> block = new ArrayList<Material>();
		block.add(Material.RED_CONCRETE);
		block.add(Material.BLUE_CONCRETE);
		block.add(Material.YELLOW_CONCRETE);
		block.add(Material.LIME_CONCRETE);
		if(!block.contains(e.getPlayer().getInventory().getItemInMainHand().getType())) return;
		Player p = e.getPlayer();
		Material type = p.getInventory().getItemInMainHand().getType();
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		if(!item.getItemMeta().hasDisplayName()) return;
		ArrayList<String> name = new ArrayList<String>();
		name.add("§4§l???");
		name.add("§9§l???");
		name.add("§e§l???");
		name.add("§a§l???");
		if(!name.contains(item.getItemMeta().getDisplayName())) return;
		activate(p,type);
		e.setCancelled(true);
	}
	private void activate(Player p, Material type) {
		ItemStack item = p.getInventory().getItemInMainHand();
		ItemMeta meta = item.getItemMeta();
		switch(type) {
			case RED_CONCRETE:{
				if (checkRelicOwner(p, "red")) {
					if(!isItemEnchanted(item)) {
						meta.addEnchant(Enchantment.DURABILITY, 1 , true);
						item.setItemMeta(meta);
					}
					//ДОБАВИТЬ МЕТОД АКТИВАЦИИ АЛТАРЯ
					p.sendMessage("§cОшибка построения алтаря (ЮГ)");
				}
				else {
					returnResourses(p);
				}
				break;
			}
			case BLUE_CONCRETE:{
				if (checkRelicOwner(p, "blue")) {
					if(!isItemEnchanted(item)) {
						meta.addEnchant(Enchantment.DURABILITY, 1 , true);
						item.setItemMeta(meta);
					}
					//ДОБАВИТЬ МЕТОД АКТИВАЦИИ АЛТАРЯ
					p.sendMessage("§cОшибка построения алтаря (СЕВЕР)");
				}
				else {
					returnResourses(p);
				}
				break;
			}
			case YELLOW_CONCRETE:{
				if (checkRelicOwner(p, "yellow")) {
					if(!isItemEnchanted(item)) {
						meta.addEnchant(Enchantment.DURABILITY, 1 , true);
						item.setItemMeta(meta);
					}
					//ДОБАВИТЬ МЕТОД АКТИВАЦИИ АЛТАРЯ
					p.sendMessage("§cОшибка построения алтаря (ЗАПАД)");
				}
				else {
					returnResourses(p);
				}
				break;
			}
			case LIME_CONCRETE:{
				if (checkRelicOwner(p, "lime")) {
					if(!isItemEnchanted(item)) {
						meta.addEnchant(Enchantment.DURABILITY, 1 , true);
						item.setItemMeta(meta);
					}
					//ДОБАВИТЬ МЕТОД АКТИВАЦИИ АЛТАРЯ
					p.sendMessage("§cОшибка построения алтаря (ВОСТОК)");
				}
				else {
					returnResourses(p);
				}
				break;
			}

		}
	}
	private boolean checkRelicOwner(Player p, String type) {
		File relic = new File(plugin.getDataFolder() + File.separator + "Relics.yml");
		FileConfiguration r = YamlConfiguration.loadConfiguration(relic);
		Long time = System.currentTimeMillis();
		String relicOwner = r.getString("ColorBlock."+type+".Player");
		Long relicTime = r.getLong("ColorBlock."+type+".Time");
		Long duration = 259200000l;
		if (relicTime+duration<time) {
			r.set("ColorBlock."+type+".Player", p.getName());
			r.set("ColorBlock."+type+".Time", time);
			try {
				r.save(relic);
			} 
			catch (IOException e1) {
				e1.printStackTrace();
			}
			return true;
		}
		if(relicOwner.equalsIgnoreCase(p.getName())){
			return true;
		}
		return false;
	}
	private boolean isItemEnchanted(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		if( meta.hasEnchants()) {
			return true;
		}
		return false;
	}
	private void returnResourses(Player p) {
		ItemStack resBedrock =new ItemStack(Material.BEDROCK);
		resBedrock.setAmount(4);
		ItemStack resStar =new ItemStack(Material.NETHER_STAR);
		resStar.setAmount(4);
		ItemStack item = p.getInventory().getItemInMainHand();
		item.setType(Material.WHITE_CONCRETE);
		p.getInventory().addItem(resBedrock,resStar);
		p.sendMessage("§aРесурсы возвращены");
	}
	
}
