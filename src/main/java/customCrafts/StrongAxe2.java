package customCrafts;

import customCrafts.itemStack.CheapFeatherItem;
import customCrafts.itemStack.StrongAxeItem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
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
		}
	}

	@EventHandler
	public void onKill(EntityDeathEvent e) {
		LivingEntity entity = e.getEntity();
		Player killer = entity.getKiller();
		if(killer == null) return;
		if(checkAxe(killer)){
			stackStrength(killer);
		}
	}

	private void stackStrength(Player p){
		int amplifier = 0;
		if(p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)){
			PotionEffect current = p.getActivePotionEffects().stream()
					.filter(eff -> eff.getType().equals(PotionEffectType.INCREASE_DAMAGE))
					.findFirst().get();
			amplifier = current.getAmplifier() + 1;
		}
		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, amplifier));
	}

	private boolean checkAxe2(Player p) {
		ItemStack item = p.getInventory().getItemInMainHand();
		if(item.getItemMeta() == null) return false;
		if(!item.getItemMeta().hasDisplayName()) return false;
		if(!item.getItemMeta().hasLore()) return false;
		if(!item.getItemMeta().getDisplayName().equals("§c§lСлепая Ярость")) return false;
		if(!(item.getItemMeta().getLore().get(0).equals("Убить их всех!"))) return false;
		return true;
	}

	private boolean checkAxe(Player p){
		ItemStack playerItem = p.getInventory().getItemInMainHand();
		ItemStack item = StrongAxeItem.getItem();
		if(playerItem.getItemMeta() == null){
			return false;
		}
		if(!playerItem.getItemMeta().hasLore()){
			return false;
		}
		return (playerItem.getItemMeta().getLore().equals(item.getItemMeta().getLore()));
	}
}
