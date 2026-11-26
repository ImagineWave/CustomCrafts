package customCrafts;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.List;

public class UnlimitedRulebook implements Listener {

    private static final String LORE_LINE_AUTHOR = "§6Written by BoB4uk76";
    private static final String LORE_LINE_DESC = "§4Выпускает луч";
    private static final int MODE_LORE_INDEX = 3;
    private static final String MODE_PVP = "§cРежим: PvP";
    private static final String MODE_PVE = "§aРежим: PvE";

    private Main plugin;

    public UnlimitedRulebook(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerShoot(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!isRulebook(item)) return;

        boolean pve = isPve(item);

        double maxDistance = 50.0; // Дальность выстрела
        Vector direction = player.getEyeLocation().getDirection().normalize(); // Направление взгляда
        Location start = player.getEyeLocation().clone(); // Начальная точка (глаза игрока)
        Location end = start.clone().add(direction.clone().multiply(maxDistance)); // Конечная точка взгляда

        Color color = pve ? Color.LIME : Color.RED;
        drawLaserAndKill(start, end, color, player, pve);
    }

    @EventHandler
    public void onToggleMode(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!isRulebook(item)) return;

        toggleMode(item, player);
    }

    private boolean isRulebook(ItemStack item) {
        if (item == null || item.getItemMeta() == null) return false;
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) return false;
        List<String> lore = meta.getLore();
        return lore.size() > 1
                && LORE_LINE_AUTHOR.equals(lore.get(0))
                && LORE_LINE_DESC.equals(lore.get(1));
    }

    private boolean isPve(ItemStack item) {
        List<String> lore = item.getItemMeta().getLore();
        return lore.size() > MODE_LORE_INDEX && MODE_PVE.equals(lore.get(MODE_LORE_INDEX));
    }

    private void toggleMode(ItemStack item, Player player) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();

        boolean pve = lore.size() > MODE_LORE_INDEX && MODE_PVE.equals(lore.get(MODE_LORE_INDEX));
        if (pve) {
            lore.set(MODE_LORE_INDEX, MODE_PVP);
            player.sendMessage("§4PvP");
        } else {
            lore.set(MODE_LORE_INDEX, MODE_PVE);
            player.sendMessage("§aPvE");
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    /**
     * Рисует лазерный луч от start до end с цветом color и убивает любого LivingEntity, попавшего в зону.
     * В режиме PvE игроков луч не убивает.
     */
    private void drawLaserAndKill(Location start, Location end, Color color, Player shooter, boolean pve) {
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
                if (entity == shooter) continue;
                if (!(entity instanceof LivingEntity)) continue;
                if (pve && entity instanceof Player) continue; // PvE: не трогаем игроков

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
