package CustomCrafts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Totem implements Listener{

	private Main plugin;
	public Totem(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void playerDeath2(PlayerDeathEvent e) {
		Player p = (Player) e.getEntity();
		if(playerHasTotem(p)) { 
		e.setKeepInventory(true);
		e.setKeepLevel(true);
		e.getDrops().clear();
		}
		return;
	}
	
	public HashMap<Player , ItemStack[]> items = new HashMap<Player , ItemStack[]>();
	
	public boolean playerHasTotem(Player p) {
		for (ItemStack item : p.getInventory().getContents()) {
            if(checkTotem(item)) {
            	return true;
            }
        }
		return false;
	}
	public void playerSafe(Player p) {
		Location loc = p.getBedSpawnLocation();
		p.teleport(loc);
		return;
	}
	public boolean checkTotem(ItemStack item){
		if(item == null) return false;
		if(item.getType()!=Material.TOTEM_OF_UNDYING) return false;
		if(!item.getItemMeta().hasDisplayName()) return false;
		if(!item.getItemMeta().hasLore()) return false;
		if(!item.getItemMeta().getDisplayName().equals("§a§lБессмертие")) return false;
		if(!item.getItemMeta().getLore().get(0).equals("§6Сохраняет инвентарь и опыт при смерти")) return false;
		if(item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLore()) {
			
			if(item.getItemMeta().getDisplayName().equals("§a§lБессмертие") && item.getItemMeta().getLore().get(0).equals("§6Сохраняет инвентарь и опыт при смерти")){
				removeItem(item);
				return true;
			}
		}
		return false;
	}
	
	public void removeItem(ItemStack item) {
		int amt = item.getAmount();
		if (amt>1) item.setAmount(amt - 1);
		else if (amt >= 0) {
			item.setType(Material.GOLD_NUGGET);
			item.setAmount(1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§a§lБессмертие");
			List<String> lore = new ArrayList<String>();
			lore.add("Не костыль, а кешбек");
			meta.setLore(lore);
			item.setItemMeta(meta);
		}
	}
	
}
