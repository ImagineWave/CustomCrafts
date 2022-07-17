package CustomCrafts.ItemStack;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RelocatorItem {
	public static ItemStack getItem() {
		ItemStack item = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§5§lЭ.П.У.П.Т.");
		List<String> lore = new ArrayList<String>();
		lore.add("Экспериментальное портативное устройство");
		lore.add("пространственного туннелирования");
		lore.add("§eОсталось зарядов");
		lore.add("0");
		lore.add("Точка не выбрана");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
}
