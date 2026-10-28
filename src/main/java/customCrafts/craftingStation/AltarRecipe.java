package customCrafts.craftingStation;

import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Objects;

/**
 * Рецепт алтаря: схема 5x5 из символов (аналогично паттерну многоблочной структуры в OpenAltarGui),
 * сопоставление символов с требуемыми предметами (материал + ItemMeta) и результат крафта.
 */
public class AltarRecipe {
	private final char[][] pattern;
	private final Map<Character, ItemStack> ingredients;
	private final ItemStack result;

	public AltarRecipe(char[][] pattern, Map<Character, ItemStack> ingredients, ItemStack result) {
		if (pattern.length != AltarCraftingService.GRID_SIZE) {
			throw new IllegalArgumentException("Паттерн рецепта должен быть размером 5x5");
		}
		for (char[] row : pattern) {
			if (row.length != AltarCraftingService.GRID_SIZE) {
				throw new IllegalArgumentException("Паттерн рецепта должен быть размером 5x5");
			}
		}

		this.pattern = pattern;
		this.ingredients = Map.copyOf(ingredients);
		this.result = Objects.requireNonNull(result);
	}

	public char[][] getPattern() {
		return pattern;
	}

	public Map<Character, ItemStack> getIngredients() {
		return ingredients;
	}

	public ItemStack getResult() {
		return result;
	}
}
