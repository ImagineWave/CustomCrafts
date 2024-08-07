package customCrafts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Contract implements Listener{
	private Main plugin;
	public Contract(Main plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void use(PlayerInteractEvent e) {
	if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() !=Action.RIGHT_CLICK_BLOCK) return;
	if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.PAPER) return;
	Player p = e.getPlayer();
	ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
	if(!item.getItemMeta().hasDisplayName()) return;
	if(!item.getItemMeta().hasLore()) return;
	if(!item.getItemMeta().getDisplayName().equals("�a�l��������")) return;
	if(!item.getItemMeta().getLore().get(0).equals("��������� ���������")) return;
	if(item.getAmount()>1) {
		p.sendMessage("�c� ���� ������ ���� ������ ���� ��������");
		return;
	}
	//���� ������ ������� �������� ���� �������� �� �����
	consumeSoul(p);
	return;
	}
	public void consumeSoul1(Player p) {
		try {
		hpToConfig(p, p.getMaxHealth()-2); 
		p.setMaxHealth(p.getMaxHealth()-2);
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
			p.sendMessage("�c���� ���� ������� ��������");
			return;
		}
		addSoulShard(p);
		return;
	}
	public void consumeSoul(Player p) {
		if(p.getHealth()>4) {
			addToSoulless(p);
			hpToConfig(p, p.getMaxHealth()-2); 
			p.setMaxHealth(p.getMaxHealth()-2);
			addSoulShard(p);
			return;
		}
		p.sendMessage("�c���� ���� ������� ��������");
		return;
	}
	public void addSoulShard(Player p) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("�4������� ����");
		List<String> lore = new ArrayList<String>();
		lore.add("�6���� ������");
		lore.add(p.getName());
		meta.setLore(lore);
		item.setItemMeta(meta);
		p.getInventory().setItemInMainHand(item);
	}
	public boolean hasSoldSoul(Player p) {
		File players = new File(plugin.getDataFolder() + File.separator + "playersWithoutSoul.yml");
		FileConfiguration users = YamlConfiguration.loadConfiguration(players);
		List<String> list = users.getStringList("users");//govno ebanoe
		if(list.contains(p.getName())) return true;
		return false;
	}
	public void hpToConfig(Player p, double hp){
		File souls = new File(plugin.getDataFolder() + File.separator + "Souls.yml");
		FileConfiguration h = YamlConfiguration.loadConfiguration(souls);
		h.set("souls." + p.getName() + ".MaxHp", hp);
		try {
			h.save(souls);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return;
	}
	public double configToHp(Player p) {
		File souls = new File(plugin.getDataFolder() + File.separator + "Souls.yml");
		FileConfiguration h = YamlConfiguration.loadConfiguration(souls);
		double hp = h.getDouble("souls." + p.getName() + ".MaxHp");
		return hp;
	}
	public void addToSoulless(Player p) {
		File players = new File(plugin.getDataFolder() + File.separator + "playersWithoutSoul.yml");
		FileConfiguration users = YamlConfiguration.loadConfiguration(players);
		List<String> list = users.getStringList("users");//govno ebanoe
		if(list.contains(p.getName())) return;
		Bukkit.broadcastMessage("�4"+p.getName()+"�c �������� ���� ����");
		list.add(p.getName());
		users.set("users", list);
		try {
			users.save(players);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
