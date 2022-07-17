package CustomCrafts.ItemStack;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StrongAxeItem {
	public static ItemStack getItem() {
		ItemStack item = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§c§lСлепая Ярость");
        List<String> lore = new ArrayList<String>();
        lore.add("Убить их всех!");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DAMAGE_ALL, 10 , true);
        meta.addEnchant(Enchantment.DIG_SPEED, 5 , true);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        return item;
	}
}
