package CustomCrafts;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class Reaper implements Listener{
	private Main plugin;
	public Reaper(Main main) {
		this.plugin = plugin;
	}

	@EventHandler
	public void mobKill(EntityDeathEvent e) {
		LivingEntity dead = e.getEntity();
		if((dead.getType() != EntityType.WITHER_SKELETON)&&(dead.getType() != EntityType.ENDER_DRAGON)) return;
 		 
			Player p = e.getEntity().getKiller();
			ItemStack item = p.getInventory().getItemInMainHand();
			if(!item.getItemMeta().hasDisplayName()) return;
			if(!item.getItemMeta().hasLore()) return;
			if(!item.getItemMeta().getDisplayName().equals("§5§lЖнец")) return;
			if(!(item.getItemMeta().getLore().get(0).equals("§6Позволяет выбить визер череп с шансом 30%"))) return;
			if(dead.getType() == EntityType.WITHER_SKELETON) {
				if(randomizer(p)) {
					headDrop(p);
					return;
				}
				return;
			}
			if((dead.getType() == EntityType.ENDER_DRAGON)&&(item.getType().equals(Material.NETHERITE_HOE))) {
				if(randomizer(p)) {
					eggDrop(p);
					return;
				}
				return;
			}
			return;
		
	}
	
	private void headDrop(Player p){
		ItemStack skull = new ItemStack(Material.WITHER_SKELETON_SKULL);
		p.getInventory().addItem(skull);
		p.sendMessage("§aВы выбили череп");
		return;
	}
	
	private boolean randomizer(Player p) {
		ItemStack item = p.getInventory().getItemInMainHand();
		if(item.getType().equals(Material.DIAMOND_HOE)) {
			if(Math.random()>0.7) {
				return true;
			}
		}
		if(item.getType().equals(Material.NETHERITE_HOE)) {
			if(Math.random()>0.5) {
				return true;
			}
		}
		return false;
	}
	
	private void eggDrop(Player p) {
		ItemStack egg = new ItemStack(Material.DRAGON_EGG);
		p.getInventory().addItem(egg);
		p.sendMessage("§aВы выбили яйцо дракона");
		return;
	}
}
