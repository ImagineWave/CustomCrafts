package customCrafts.dimensionalwarp;

import customCrafts.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DimensionalWarpToggleCommand implements CommandExecutor {
    private final Main plugin;

    public DimensionalWarpToggleCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§cУ вас нет прав");
            return true;
        }

        plugin.dimensionalWarpEnabled = !plugin.dimensionalWarpEnabled;
        plugin.getConfig().set("dimensional-warp-enabled", plugin.dimensionalWarpEnabled);
        plugin.saveConfig();

        if (plugin.dimensionalWarpEnabled) {
            sender.sendMessage("§aМежпространственные телепорты включены");
        } else {
            sender.sendMessage("§cМежпространственные телепорты выключены");
        }
        return true;
    }
}
