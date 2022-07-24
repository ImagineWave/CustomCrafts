package CustomCrafts.ItemStack;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SoulFeatherItem {
	public static ItemStack getItem() {
		ItemStack item = new ItemStack(Material.FEATHER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§6Перо Души");
		List<String> lore = new ArrayList<String>();
		meta.addEnchant(Enchantment.DURABILITY, 1 , true);
		lore.add("§4Постоянный полет");
		lore.add("§eВладелец");
		lore.add("-");
		lore.add("§aПосле смерти");
		lore.add("§aвернется к владельцу");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
}
