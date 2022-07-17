package CustomCrafts.ItemStack;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DestroyerItem {
	public static ItemStack getItem() {
		ItemStack item = new ItemStack(Material.GOLDEN_SHOVEL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§5Разрушитель");
        List<String> lore = new ArrayList<String>();
        lore.add("Уничтожет любой блок");
        lore.add("§eОсталось зарядов");
        lore.add("0");
        meta.setLore(lore);
        meta.setUnbreakable(true);
		meta.addEnchant(Enchantment.SILK_TOUCH, 1 , true);
		meta.addEnchant(Enchantment.DIG_SPEED, 10 , true);
        item.setItemMeta(meta);
        return item;
	}
}
