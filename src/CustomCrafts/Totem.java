package CustomCrafts;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class Totem implements Listener{

	private Main plugin;
	public Totem(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void playerDeath(PlayerDeathEvent e) {
		Player p = (Player) e.getEntity();
		if(playerHasTotem(p)) { 
		e.setKeepInventory(true);
		e.setKeepLevel(true);
		e.getDrops().clear();
		}
		return;
	}
	
	public boolean playerHasTotem(Player p) {
		for (ItemStack item : p.getInventory().getContents()) {
            if(checkTotem(item)) {
            	Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                    	p.getInventory().remove(item);
                    }
                }, 1*20L);
            	return true;
            }
        }
		return false;
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
				
				return true;
			}
		}
		return false;
	}
}
