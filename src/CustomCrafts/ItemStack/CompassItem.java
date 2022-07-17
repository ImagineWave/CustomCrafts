package CustomCrafts.ItemStack;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CompassItem {
	public static ItemStack getItem() {
		ItemStack item = new ItemStack(Material.COMPASS);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§6Компас возвращения");
		List<String> lore = new ArrayList<String>();
		meta.addEnchant(Enchantment.DURABILITY, 1 , true);
		lore.add("§aВозвращает владельца домой");
		lore.add("§eВладелец");
		lore.add("");
		lore.add("§eОсталось использований");
		lore.add("5");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
}
