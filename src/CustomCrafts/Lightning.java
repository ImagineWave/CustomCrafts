package CustomCrafts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class Lightning implements Listener{

	
	@EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        final Action action = event.getAction();
        final Player player = event.getPlayer();
        final ItemStack hand = player.getInventory().getItemInMainHand();
        
        if (action != Action.LEFT_CLICK_AIR && action != Action.LEFT_CLICK_BLOCK) {
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                
            	//this.shot(player);
                event.setCancelled(true);
            }
    }
	}
	
	public boolean checkLightning(ItemStack item){
		if(item == null) return false;
		if(item.getType()!=Material.GOLDEN_SWORD) return false;
		if(!item.getItemMeta().hasDisplayName()) return false;
		if(!item.getItemMeta().hasLore()) return false;
		if(!item.getItemMeta().getDisplayName().equals("§a§eКопьё Молний")) return false;
		if(!item.getItemMeta().getLore().get(0).equals("Осталось использований")) return false;
		if(item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLore()) {
			if(item.getItemMeta().getDisplayName().equals("§a§lБессмертие") && item.getItemMeta().getLore().get(0).equals("§6Сохраняет инвентарь и опыт при смерти")){
				return true;
			}
		}
		return false;
	}
	
	/*public List<LivingEntity> getTargetV3(final Player player, int maxRange, final double aiming, final boolean wallHack) {
            final List<LivingEntity> target = new ArrayList<LivingEntity>();
            final Location playerEyes = player.getEyeLocation();
            final Vector direction = playerEyes.getDirection().normalize();
            final List<Player> targets = new ArrayList<Player>();
            final Location loc = playerEyes.clone();
            final Vector progress = direction.clone().multiply(0.7);
            maxRange = 100 * maxRange / 70;
            int loop = 0;
            while (loop < maxRange) {
                ++loop;
                loc.add(progress);
                final Block block = loc.getBlock();
                if (!wallHack && block.getType().isSolid()) {
                    break;
                }
                final double lx = loc.getX();
                final double ly = loc.getY();
                final double lz = loc.getZ();
                for (final Player apa : Bukkit.getOnlinePlayers()) {
                    try {
                        apa.spawnParticle(Particle.FIREWORKS_SPARK, loc, 2, 0.1f, 0.1f, 0.1f, 0.05f, 2);
                        //Particle.FIREWORKS_SPARK.(apa.getPlayer(), loc, 0.1f, 0.1f, 0.1f, 0.05f, 2);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (final Player possibleTarget : targets) {
                    if (possibleTarget.getUniqueId() == player.getUniqueId()) {
                        continue;
                    }
                    final Location testLoc = possibleTarget.getLocation().add(0.0, 0.85, 0.0);
                    final double px = testLoc.getX();
                    final double py = testLoc.getY();
                    final double pz = testLoc.getZ();
                    final boolean dX = Math.abs(lx - px) < 0.7 * aiming;
                    final boolean dY = Math.abs(ly - py) < 1.7 * aiming;
                    final boolean dZ = Math.abs(lz - pz) < 0.7 * aiming;
                    if (!dX || !dY || !dZ || target.contains(possibleTarget)) {
                        continue;
                    }
                    target.add(possibleTarget);
                }
            }
            return target;
        }
	*/

}
