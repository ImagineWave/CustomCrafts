package customCrafts.itemStack;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TotemItem {
	public static ItemStack getItem() {
		ItemStack item = new ItemStack(Material.TOTEM_OF_UNDYING);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§a§lБессмертие");
		List<String> lore = new ArrayList<String>();
		meta.addEnchant(Enchantment.DURABILITY, 1 , true);
		lore.add("§6Сохраняет инвентарь и опыт при смерти");
		lore.add("§4Испольуются срузу ВСЕ тотемы");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
}
