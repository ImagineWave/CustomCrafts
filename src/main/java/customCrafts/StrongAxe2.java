package customCrafts;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StrongAxe2 implements Listener{
	private Main plugin;
	public StrongAxe2(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.getAction() != Action.LEFT_CLICK_AIR && e.getAction() !=Action.LEFT_CLICK_BLOCK) return;
		Player p = e.getPlayer();
		if(checkAxe(p)) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 0));
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 1));
		}
	}
	private boolean checkAxe(Player p) {
		ItemStack item = p.getInventory().getItemInMainHand();
		if(item.getItemMeta() == null) return false;
		if(!item.getItemMeta().hasDisplayName()) return false;
		if(!item.getItemMeta().hasLore()) return false;
		if(!item.getItemMeta().getDisplayName().equals("§c§lСлепая Ярость")) return false;
		if(!(item.getItemMeta().getLore().get(0).equals("Убить их всех!"))) return false;
		return true;
	}
}
