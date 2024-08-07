package customCrafts.itemStack;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChronosItem {
	public static ItemStack getItem() {
		ItemStack item = new ItemStack(Material.CLOCK);
		ItemMeta metaChronos = item.getItemMeta();
		metaChronos.setDisplayName("§6§lЧасы Хроноса");
		List<String> lore = new ArrayList<String>();
		lore.add("Смена дня и ночи");
		metaChronos.setLore(lore);
		metaChronos.addEnchant(Enchantment.ARROW_INFINITE, 1 , true);
		item.setItemMeta(metaChronos);
		return item;
	}
}
