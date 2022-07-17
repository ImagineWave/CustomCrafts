package CustomCrafts.ItemStack;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ReaperItem {
	public static ItemStack getItem() {
		ItemStack item = new ItemStack(Material.DIAMOND_HOE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§5§lЖнец");
		List<String> lore = new ArrayList<>();
		meta.addEnchant(Enchantment.DAMAGE_ALL, 4 , true);
		lore.add("§6Позволяет выбить визер череп с шансом 30%");
		lore.add("Можно улучшить до незеритовой версии(+10%)");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
}
