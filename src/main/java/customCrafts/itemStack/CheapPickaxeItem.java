package customCrafts.itemStack;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CheapPickaxeItem {
	public static ItemStack getItem() {
		ItemStack item = new ItemStack(Material.GOLDEN_PICKAXE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§5§lВоля §5ш§5§lахтера");
		List<String> lore = new ArrayList<String>();
		lore.add("Время наше все");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DIG_SPEED, 8 , true);
		meta.setUnbreakable(true);
		item.setItemMeta(meta);
		return item;
	}
}
