package CustomCrafts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import Plugin.StrPlayer;

public class Angel implements Listener {
	
	private Main plugin;
	public static String str = "";
	

	public Angel(Main plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void holdOFF (PlayerMoveEvent e) {
		Player p = e.getPlayer();
	    if (e.getPlayer().getInventory().getItemInOffHand().getType() != Material.FEATHER) {
	    	angelOFF(p);
			return;
		}
		ItemStack item = e.getPlayer().getInventory().getItemInOffHand();
		if(!item.getItemMeta().hasDisplayName()) {
			angelOFF(p);
			return;
		}
		if(!item.getItemMeta().hasLore()) {
			angelOFF(p);
			return;
		}
		if(!item.getItemMeta().getDisplayName().equals("�5�l������ ����")) {
			angelOFF(p);
			return;
		}
		if(!item.getItemMeta().getLore().get(0).equals("�4���������� �����")) {
			angelOFF(p);
			return;
		}
		return;
}

	@EventHandler
	public void holdON (PlayerMoveEvent e) {
	Player p = e.getPlayer();
	if(p.getAllowFlight()) return;
	if (e.getPlayer().getInventory().getItemInOffHand().getType() != Material.FEATHER) return;
	ItemStack item = e.getPlayer().getInventory().getItemInOffHand();
	if(!item.getItemMeta().hasDisplayName()) return;
	if(!item.getItemMeta().hasLore()) return;
	if(!item.getItemMeta().getDisplayName().equals("�5�l������ ����")) return;
	if(!item.getItemMeta().getLore().get(0).equals("�4���������� �����")) return;
	angelON(p);
	}
	public boolean checkOwner(Player p) {
		ItemStack hand = p.getInventory().getItemInOffHand();
		ItemMeta meta = hand.getItemMeta();
		List<String> lore = meta.getLore();
		if (lore.contains(p.getName())) {
			return true;
		}
		if ((lore.contains("�������� ���� ")) && (!lore.contains(p.getName()))) {
			p.sendMessage("�c���� ��������� ���");
			return false;
		}
		if (!lore.contains("�������� ���� ")) {
			lore.add("�������� ���� ");
			lore.add(p.getName());
			meta.setLore(lore);
			return true;
		}
		return false;
	}
	public void removeItem(Player p) {
		ItemStack hand = p.getInventory().getItemInMainHand();
		int amt = hand.getAmount();
		if (amt>1) hand.setAmount(amt - 1);
		else if (amt >= 0) {
			ItemStack ChronosDemo = new ItemStack(Material.GOLD_NUGGET);
			ItemMeta metaChronos = ChronosDemo.getItemMeta();
			List<String> lore = new ArrayList<String>();
			lore.add("�� �������, � ������");
			metaChronos.setLore(lore);
			ChronosDemo.setItemMeta(metaChronos);
			p.getInventory().setItemInMainHand(ChronosDemo);
		}
	}
	
	public void angelOFF(Player p){
		if(!p.getGameMode().equals(org.bukkit.GameMode.SURVIVAL)) return;
		if(p.hasPotionEffect(PotionEffectType.LUCK)) return;
	p.setAllowFlight(false);
	}
	public void angelON(Player p){
		if(!p.isOp()) {
			if(p.hasPermission("str.xray")) {
				p.sendMessage("�4���� �� ����� ���� ������������ ������ �������� x-ray����");
				return;
			}
		}
		if(!p.getGameMode().equals(org.bukkit.GameMode.SURVIVAL)) return;
			p.setAllowFlight(true);
		return;	
	}
}
