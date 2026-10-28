package customCrafts.craftingStation.recipes;

import customCrafts.craftingStation.AltarRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReinforcedDeepslateBedrockRecipe {

    // 'P' - тыквенный пирог (углы), 'J' - тыквенный фонарь (всё остальное).
    private static final char[][] PATTERN = {
            "##I##".toCharArray(),
            "##D##".toCharArray(),
            "IDHDI".toCharArray(),
            "##D##".toCharArray(),
            "##I##".toCharArray()
    };

    public static AltarRecipe create() {
        Map<Character, ItemStack> ingredients = Map.of(
                'D', new ItemStack(Material.REINFORCED_DEEPSLATE, 1),
                'H', new ItemStack(Material.HEART_OF_THE_SEA, 1),
                'I', new ItemStack(Material.IRON_BLOCK, 16)
        );
        ItemStack result = new ItemStack(Material.BEDROCK, 1);
        ItemMeta meta = result.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add("§9Источник: §3Deepslate Craft");
        meta.setLore(lore);
        result.setItemMeta(meta);
        return new AltarRecipe(PATTERN, ingredients, result);
    }
}
