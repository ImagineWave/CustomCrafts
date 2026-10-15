package customCrafts.dimensionalwarp.listener;

import customCrafts.Main;
import customCrafts.dimensionalwarp.DimensionalBorder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class DimensionalWarpListener implements Listener {

    private final Main plugin;

    public DimensionalWarpListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        DimensionalBorder border = DimensionalBorder.byWorld(player.getWorld());
        if (border == null){
            return;
        }

        double y = event.getTo().getY();

        if (border.hasTopBorder() && y >= border.getTopTeleportBorder()) {
            World targetWorld = Bukkit.getWorld(border.getTopSendToWorldName());
            teleportToDimension(player, targetWorld, border.getTopTeleportBorderExit());
            addVerticalImpulse(player, 5);
            return;
        }

        if (border.hasBottomBorder() && y <= border.getBottomTeleportBorder()) {
            World targetWorld = Bukkit.getWorld(border.getBottomSendToWorldName());
            teleportToDimension(player, targetWorld, border.getBottomTeleportBorderExit());
            addVerticalImpulse(player, -2);
        }
    }


    private void teleportToDimension(Player player, World targetWorld, double y) {
        Location from = player.getLocation();
        Vector velocity = player.getVelocity();

        double x = from.getX();
        double z = from.getZ();

        Location destination = new Location(targetWorld, x, y, z, from.getYaw(), from.getPitch());

        player.teleport(destination);
        player.setVelocity(velocity);
    }

    private void addVerticalImpulse(Player player, double verticalImpulse) {
        player.setVelocity(player.getVelocity().add(new Vector(0, verticalImpulse, 0)));
    }

}
