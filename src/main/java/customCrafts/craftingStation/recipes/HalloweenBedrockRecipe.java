package customCrafts.craftingStation.recipes;

import customCrafts.craftingStation.AltarRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HalloweenBedrockRecipe {

	// 'P' - тыквенный пирог (углы), 'J' - тыквенный фонарь (всё остальное).
	private static final char[][] PATTERN = {
			"PJJJP".toCharArray(),
			"JJJJJ".toCharArray(),
			"JJJJJ".toCharArray(),
			"JJJJJ".toCharArray(),
			"PJJJP".toCharArray()
	};

	public static AltarRecipe create() {
		Map<Character, ItemStack> ingredients = Map.of(
				'P', new ItemStack(Material.PUMPKIN_PIE, 10),
				'J', new ItemStack(Material.JACK_O_LANTERN, 64)
		);
		ItemStack result = new ItemStack(Material.BEDROCK, 1);
		ItemMeta meta = result.getItemMeta();
		List<String> lore = new ArrayList<>();
		lore.add("§9Источник: §6Halloween Craft");
		meta.setLore(lore);
		result.setItemMeta(meta);
		return new AltarRecipe(PATTERN, ingredients, result);
	}
}
