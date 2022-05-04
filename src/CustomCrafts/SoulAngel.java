package CustomCrafts;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SoulAngel implements Listener{
	private Main plugin;

	public SoulAngel(Main plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(!p.getGameMode().equals(org.bukkit.GameMode.SURVIVAL)) return;
		if(allowFly(p)) {
			p.setAllowFlight(true);
			return;
		}
		p.setAllowFlight(false);
		return;
	}
	public boolean allowFly(Player p) {
		if(p.getInventory().getItemInOffHand().getType() != Material.FEATHER) return false;
		ItemStack item = p.getInventory().getItemInOffHand();
		if(!item.getItemMeta().hasDisplayName()) return false;
		if(!item.getItemMeta().hasLore()) return false;
		if((item.getItemMeta().getDisplayName().equals("§6Перо Души")) || (item.getItemMeta().getDisplayName().equals("§5§lВечное перо")))
		{
			if(!item.getItemMeta().getLore().get(0).equals("§4Постоянный полет")) return false;
			if(item.getItemMeta().getDisplayName().equals("§5§lВечное перо")) {
				return true;
			}
			if(checkOwner(p)) {
				
				return true;
			}
		}
		return false;
	}
	public boolean checkOwner(Player p) {
		ItemStack item = p.getInventory().getItemInOffHand();
		ItemMeta meta = item.getItemMeta();
		List<String> lore = item.getItemMeta().getLore();
		if(item.getItemMeta().getLore().get(2).equals("-")) {
			lore.set(2, p.getName());
			meta.setLore(lore);
			item.setItemMeta(meta);
			return true;
		}
		if(!item.getItemMeta().getLore().get(2).equals(p.getName())) {
			p.sendMessage("§cПеро отвергает вас");
			return false;
		}
		if(item.getItemMeta().getLore().get(2).equals(p.getName())) {
			return true;
		}
		return false;
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
		if(item.getType()!=Material.FEATHER) return false;
		if(!item.getItemMeta().hasDisplayName()) return false;
		if(!item.getItemMeta().hasLore()) return false;
		if(!item.getItemMeta().getDisplayName().equals("§6Перо Души")) return false;
		if(!item.getItemMeta().getLore().get(0).equals("§4Постоянный полет")) return false;
		if(item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLore()) {
			
			if(item.getItemMeta().getDisplayName().equals("§6Перо Души") && item.getItemMeta().getLore().get(2).equals(p.getName())){
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
}
