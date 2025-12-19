package customCrafts.itemStack;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CheapFeatherItem {
    public static ItemStack getItem() {
        ItemStack item = new ItemStack(Material.FEATHER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Временное Перо");
        List<String> lore = new ArrayList<>();
        meta.addEnchant(Enchantment.DURABILITY, 1 , true);
        lore.add("§3Дает полет на §e10 §3минут");
        lore.add("§3при повтороной активации добавляет");
        lore.add("§2еще 10 минут §3к длительности полета");
        lore.add("§3Радиус действия §4150 блоков");
        lore.add("§3от точки активации");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
