package customCrafts;

import customCrafts.itemStack.UnlimitedRulebookItem;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class UnlimitedRulebook implements Listener {

    private Main plugin;

    public UnlimitedRulebook(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerShoot(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = UnlimitedRulebookItem.getItem();
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(player.getInventory().getItemInMainHand().getItemMeta() == null){
            return;
        }
        if(!player.getInventory().getItemInMainHand().getItemMeta().hasLore()){
            return;
        }
        if (!player.getInventory().getItemInMainHand().getItemMeta().getLore().equals(item.getItemMeta().getLore())){
            return;
        }

        double maxDistance = 50.0; // Дальность выстрела
        Vector direction = player.getEyeLocation().getDirection().normalize(); // Направление взгляда
        Location start = player.getEyeLocation().clone(); // Начальная точка (глаза игрока)
        Location end = start.clone().add(direction.clone().multiply(maxDistance)); // Конечная точка взгляда

        drawLaserAndKill(start, end, Color.RED, player);
    }

    /**
     * Рисует лазерный луч от start до end с цветом color и убивает любого LivingEntity, попавшего в зону.
     */
    private void drawLaserAndKill(Location start, Location end, Color color, Player shooter) {
        World world = start.getWorld();
        Vector direction = end.toVector().subtract(start.toVector()).normalize(); // Вектор направления
        double distance = start.distance(end);

        for (double i = 0; i < distance; i += 0.3) { // Шаг в 0.3 блока
            Location point = start.clone().add(direction.clone().multiply(i));

            // Создаем частицы
            Particle.DustOptions dust = new Particle.DustOptions(color, 1);
            world.spawnParticle(Particle.REDSTONE, point.getX(), point.getY(), point.getZ(), 1, 0, 0, 0, 0, dust);

            // Проверяем попадание по мобам и игрокам
            for (Entity entity : world.getNearbyEntities(point, 0.5, 0.5, 0.5)) {
                if (entity instanceof LivingEntity && entity != shooter) {
                    LivingEntity target = (LivingEntity) entity;

                    double minHealth = 0.5; // Минимальный запас ХП перед добиванием
                    target.setHealth(Math.min(target.getHealth(), minHealth));

                    // Добиваем уроном от игрока
                    target.damage(9999, shooter); // Огромный урон, чтобы точно убить

                    return; // Останавливаем луч при попадании
                }
            }
        }
    }

}
