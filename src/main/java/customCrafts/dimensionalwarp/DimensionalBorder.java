package customCrafts.dimensionalwarp;

import org.bukkit.World;

public enum DimensionalBorder {

    WORLD("world", "world_the_end", "world_nether",
            true, 450, -80,
            true, -120, 340),
    NETHER("world_nether", "world", null,
            true, 350, -110,
            false, 0, 0),
    END("world_the_end", null, "world",
            false, 0, 0,
            true, -85, 440);

    private final String worldName;
    private final String topSendToWorldName;
    private final String bottomSendToWorldName;

    private final boolean hasTopBorder;
    private final double topTeleportBorder;
    private final double topTeleportBorderExit;

    private final boolean hasBottomBorder;
    private final double bottomTeleportBorder;
    private final double bottomTeleportBorderExit;

    DimensionalBorder(String worldName, String topSendToWorldName, String bottomSendToWorldName,
                       boolean hasTopBorder, double topTeleportBorder, double topTeleportBorderExit,
                       boolean hasBottomBorder, double bottomTeleportBorder, double bottomTeleportBorderExit) {
        this.worldName = worldName;
        this.topSendToWorldName = topSendToWorldName;
        this.bottomSendToWorldName = bottomSendToWorldName;
        this.hasTopBorder = hasTopBorder;
        this.topTeleportBorder = topTeleportBorder;
        this.topTeleportBorderExit = topTeleportBorderExit;
        this.hasBottomBorder = hasBottomBorder;
        this.bottomTeleportBorder = bottomTeleportBorder;
        this.bottomTeleportBorderExit = bottomTeleportBorderExit;
    }

    public String getWorldName() {
        return worldName;
    }

    public String getTopSendToWorldName() {
        return topSendToWorldName;
    }

    public String getBottomSendToWorldName() {
        return bottomSendToWorldName;
    }

    public boolean hasTopBorder() {
        return hasTopBorder;
    }

    public double getTopTeleportBorder() {
        return topTeleportBorder;
    }

    public double getTopTeleportBorderExit() {
        return topTeleportBorderExit;
    }

    public boolean hasBottomBorder() {
        return hasBottomBorder;
    }

    public double getBottomTeleportBorder() {
        return bottomTeleportBorder;
    }

    public double getBottomTeleportBorderExit() {
        return bottomTeleportBorderExit;
    }

    public static DimensionalBorder byWorld(World world) {
        return byWorldName(world.getName());
    }

    public static DimensionalBorder byWorldName(String worldName) {
        for (DimensionalBorder border : values()) {
            if (border.worldName.equalsIgnoreCase(worldName)) {
                return border;
            }
        }
        return null;
    }
}
