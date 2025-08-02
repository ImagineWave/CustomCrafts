package customCrafts.itemStack;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class UnlimitedRulebookItem {
    public static ItemStack getItem() {
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6§lUNLIMITED RULEBOOK");
        List<String> lore = new ArrayList<String>();
        meta.addEnchant(Enchantment.DAMAGE_ALL, 255 , true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1000 , true);
        lore.add("§6Written by BoB4uk76");
        lore.add("§4Выпускает луч");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
