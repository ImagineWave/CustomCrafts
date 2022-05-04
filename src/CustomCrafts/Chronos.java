package CustomCrafts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;



public class Chronos implements Listener{

	private Main plugin;
	public Chronos(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void use(PlayerInteractEvent e) {
	if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() !=Action.RIGHT_CLICK_BLOCK) return;
	if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.CLOCK) return;
	Player p = e.getPlayer();
	ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
	if(!item.getItemMeta().hasDisplayName()) return;
	if(!item.getItemMeta().hasLore()) return;
	if(!item.getItemMeta().getDisplayName().equals("§6§lЧасы Хроноса")) return;
	if(!item.getItemMeta().getLore().get(0).equals("Смена дня и ночи")) return;
	if(p.hasPotionEffect(PotionEffectType.WEAKNESS)) {
		p.sendMessage("§cВам не хватет сил!");
		return;
	}
	chronos(p);
	}
	public void removeItem(Player p) {
		ItemStack hand = p.getInventory().getItemInMainHand();
		int amt = hand.getAmount();
		if (amt>1) hand.setAmount(amt - 1);
		else if (amt >= 0) {
			ItemStack cash = new ItemStack(Material.GOLD_NUGGET);
			ItemMeta meta = cash.getItemMeta();
			List<String> lore = new ArrayList<String>();
			lore.add("Не костыль, а кешбек");
			meta.setLore(lore);
			cash.setItemMeta(meta);
			p.getInventory().setItemInMainHand(cash);
			ItemStack resDiamond = new ItemStack(Material.DIAMOND_BLOCK);
			resDiamond.setAmount(4);
			ItemStack resGold = new ItemStack(Material.GOLD_BLOCK);
			resGold.setAmount(4);
			p.getInventory().addItem(resDiamond,resGold);
			Bukkit.broadcastMessage("§a" + p.getName()+" §6Попытался использовать силу Хроноса, но получил §aкешбек");
		}
	}
	public void chronos(Player p) {
		Bukkit.broadcastMessage("§a" + p.getName()+" §6Использовал силу Хроноса");
		p.sendMessage("§c§lВы чувствуете себя уставшим...");
		p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 180, 0), true);
		World world = p.getLocation().getWorld();
		Long time = world.getTime()+12000;
		world.setTime(time);
	}
}
