package customCrafts.itemStack;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NotchedPickaxeItem {
	public static ItemStack getItem() {
		ItemStack item = new ItemStack(Material.NETHERITE_PICKAXE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§4§lЗ§e§lа§a§lз§b§lу§9§lб§d§lр§9§le§b§lн§a§lн§e§lа§4§lя §5§lКирка");
		List<String> lore = new ArrayList<String>();
		lore.add("§4ПКМ для смены режима"); //0
		lore.add("§eРежим");				//1
		lore.add("Щёлк");					//2
		lore.add("§aПосле смерти");			//3
		lore.add("§aвернется к владельцу");	//4
		meta.setLore(lore);
		meta.setUnbreakable(true);
		meta.addEnchant(Enchantment.SILK_TOUCH, 1 , true);
		meta.addEnchant(Enchantment.DIG_SPEED, 10 , true);
		
		item.setItemMeta(meta);
		return item;
	}
}
