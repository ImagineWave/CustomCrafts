package customCrafts;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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

public class NotchedPickaxe implements Listener{
	private Main plugin;

	public NotchedPickaxe(Main plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void use(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() !=Action.RIGHT_CLICK_BLOCK) return;
		if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.NETHERITE_PICKAXE) return;
		Player p = e.getPlayer();
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		if(!item.getItemMeta().hasDisplayName()) return;
		if(!item.getItemMeta().hasLore()) return;
		if(!item.getItemMeta().getLore().get(0).equals("§4ПКМ для смены режима")) return;
		toggleMode(item);
		return;
	}
	public void toggleMode(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = item.getItemMeta().getLore();
		if(meta.getLore().get(2).equals("Щёлк")) {
			meta.removeEnchant(Enchantment.SILK_TOUCH);
			meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 5, true);
			meta.setDisplayName("§4§lЗ§e§lа§a§lз§b§lу§9§lб§d§lр§9§le§b§lн§a§lн§e§lа§4§lя §6§lКирка");
			lore.set(2, "Удача");
			meta.setLore(lore);
			item.setItemMeta(meta);
			return;
		}
		if(meta.getLore().get(2).equals("Удача")) {
			meta.removeEnchant(Enchantment.LOOT_BONUS_BLOCKS);
			meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
			meta.setDisplayName("§4§lЗ§e§lа§a§lз§b§lу§9§lб§d§lр§9§le§b§lн§a§lн§e§lа§4§lя §5§lКирка");
			lore.set(2, "Щёлк");
			meta.setLore(lore);
			item.setItemMeta(meta);
			return;
		}
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
		if(item.getType()!=Material.NETHERITE_PICKAXE) return false;
		if(!item.getItemMeta().hasDisplayName()) return false;
		if(!item.getItemMeta().hasLore()) return false;
		if(!item.getItemMeta().getLore().get(0).equals("§4ПКМ для смены режима")) return false;
		if(item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLore()) {
				return true;
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
