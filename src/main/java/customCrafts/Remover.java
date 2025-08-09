package customCrafts;

import java.util.ArrayList;
import java.util.Arrays;
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

	private final int CHARGES_PER_USE = 5;
	private final int MAX_CHARGES = 20;
	private static long COOLDOWN = 0;


	private Main plugin;
	public Remover(Main plugin) {
		this.plugin = plugin;
	}
	private Material[] blackListArr = {
			Material.CHEST, Material.TRAPPED_CHEST, Material.CRAFTING_TABLE, Material.DISPENSER, Material.DROPPER,
			Material.FURNACE, Material.BLAST_FURNACE, Material.BREWING_STAND, Material.ENCHANTING_TABLE, Material.SMOKER
	};


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
		if(Arrays.asList(blackListArr).contains(e.getClickedBlock().getType())){
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
		if(charge>MAX_CHARGES) return false;
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
		return charge>=CHARGES_PER_USE;
	}
	private void removeBlock(Block b, Player p) {
		if(!readyToUse()){
			return;
		}
		if(b.getType() == Material.REINFORCED_DEEPSLATE){
			if(Math.random()>0.8) {
				deepslateDrop(p);
			}
		}
		b.setType(Material.AIR);
		ItemStack item = p.getInventory().getItemInMainHand();
		ItemMeta meta = item.getItemMeta();
		List<String> lore = item.getItemMeta().getLore();
		Integer charge = Integer.parseInt(lore.get(2));
		charge = charge - CHARGES_PER_USE;
		lore.set(2, charge.toString());
		meta.setLore(lore);
		item.setItemMeta(meta);
		setCooldown();
	}
	private void deepslateDrop(Player p){
		ItemStack deepslate = new ItemStack(Material.REINFORCED_DEEPSLATE);
		p.getInventory().addItem(deepslate);
		p.sendMessage("§aВы получили кусочек сланца");
		return;
	}

	private boolean readyToUse(){
		return plugin.DESTROYER_GLOBAL_COOLDOWN+200<=System.currentTimeMillis();
	}

	private void setCooldown(){
		plugin.DESTROYER_GLOBAL_COOLDOWN = System.currentTimeMillis();
	}
}
