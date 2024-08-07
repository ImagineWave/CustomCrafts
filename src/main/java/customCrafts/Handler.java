package customCrafts;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class Handler implements Listener{
	private Main plugin;
	public Handler(Main plugin) {
		this.plugin = plugin;
	}
@EventHandler
public void onPlayerSpawn(PlayerRespawnEvent e) {
	Player p = e.getPlayer();
	if(hasSoldSoul(p)){
		Double hp = configToHp(p);
		p.setMaxHealth(hp);
		return;
	}
	return;
	}
public boolean hasSoldSoul(Player p) {
	File players = new File(plugin.getDataFolder() + File.separator + "playersWithoutSoul.yml");
	FileConfiguration users = YamlConfiguration.loadConfiguration(players);
	List<String> list = users.getStringList("users");//govno ebanoe
	if(list.contains(p.getName())) return true;
	return false;
}
public double configToHp(Player p) {
	File souls = new File(plugin.getDataFolder() + File.separator + "Souls.yml");
	FileConfiguration h = YamlConfiguration.loadConfiguration(souls);
	double hp = h.getDouble("souls." + p.getName() + ".MaxHp");
	return hp;
}
}
