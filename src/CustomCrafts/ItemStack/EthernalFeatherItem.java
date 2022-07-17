package CustomCrafts.ItemStack;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EthernalFeatherItem {
	public static ItemStack getItem() {
		ItemStack item = new ItemStack(Material.FEATHER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§5§lВечное перо");
		List<String> lore = new ArrayList<String>();
		lore.add("§4Постоянный полет");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DURABILITY, 1 , true);
		item.setItemMeta(meta);
		return item;
	}
}
