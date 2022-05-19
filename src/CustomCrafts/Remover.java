package CustomCrafts;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Remover implements Listener{
	private Main plugin;
	public Remover(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		
		if(e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() !=Action.RIGHT_CLICK_AIR) return;
		if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.GOLDEN_SHOVEL) return;
		Player p = e.getPlayer();
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		if(!item.getItemMeta().hasDisplayName()) return;
		if(!item.getItemMeta().hasLore()) return;
		if(!item.getItemMeta().getDisplayName().equals("§5Разрушитель")) return;
		if(!item.getItemMeta().getLore().get(0).equals("Уничтожет любой блок")) return;
		if(p.getInventory().getItemInOffHand().getType() == Material.DRAGON_BREATH) {
			if(recharged(p,item)) {
				consumeDragonBreath(p);
				return;
			}
			p.sendMessage("§cВ предмете уже максимально количество зарядов");
			return;
		}
		if(e.getClickedBlock() == null) {
			return;
		}
		if(checkCharges(p,item)) {
			removeBlock(e.getClickedBlock(), p);
			return;
		}
		p.sendMessage("§cНедостаточно зарядов");
		return;
	}
	
	
	private boolean recharged(Player p, ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = item.getItemMeta().getLore();
		Integer charge = Integer.parseInt(lore.get(2));
		charge = charge + 1;
		if(charge>20) return false;
		lore.set(2, charge.toString());
		meta.setLore(lore);
		item.setItemMeta(meta);
		return true;
	}
	private void consumeDragonBreath(Player p) {
		ItemStack bottle = new ItemStack (Material.GLASS_BOTTLE);
		int amount = p.getInventory().getItemInOffHand().getAmount();
		amount = amount - 1;
		if (amount!=0) {
			p.getInventory().getItemInOffHand().setAmount(amount);
			p.getInventory().addItem(bottle);
			return;
		}
		p.getInventory().setItemInOffHand(bottle);
		return;
	}
	private boolean checkCharges(Player p, ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = item.getItemMeta().getLore();
		Integer charge = Integer.parseInt(lore.get(2));
		return charge>=20;
	}
	private void removeBlock(Block b, Player p) {
		b.setType(Material.AIR);
		ItemStack item = p.getInventory().getItemInMainHand();
		ItemMeta meta = item.getItemMeta();
		List<String> lore = item.getItemMeta().getLore();
		Integer charge = Integer.parseInt(lore.get(2));
		charge = charge - 20;
		lore.set(2, charge.toString());
		meta.setLore(lore);
		item.setItemMeta(meta);
	}
}
