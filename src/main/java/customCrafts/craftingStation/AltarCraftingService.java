package customCrafts.craftingStation;

import customCrafts.Main;
import customCrafts.craftingStation.recipes.HalloweenRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AltarCraftingService implements Listener {

	public static final int GRID_SIZE = 5;

	// Слот GUI алтаря, в который кладётся результат совпавшего рецепта.
	public static final int RESULT_SLOT = 25;

	// Символ "пустой ячейки" в схеме рецепта. В отличие от многоблочной структуры, здесь означает
	// "слот должен быть пустым" (а не "любой блок/предмет") - рецепт проверяется на exact совпадение.
	public static final char EMPTY_SLOT = '#';

	private final Main plugin;
	private final List<AltarRecipe> recipes = new ArrayList<>();

	public AltarCraftingService(Main plugin) {
		this.plugin = plugin;
		registerRecipe(HalloweenRecipe.create());
	}

	public void registerRecipe(AltarRecipe recipe) {
		recipes.add(recipe);
	}

	/**
	 * Ищет среди зарегистрированных рецептов тот, что точно совпадает с переданной сеткой 5x5.
	 *
	 * @param grid сетка предметов [ряд][столбец] размером 5x5 (как в крафтовой области алтаря)
	 * @return совпавший рецепт либо пусто, если ни один рецепт не подошёл
	 */
	public Optional<AltarRecipe> findMatch(ItemStack[][] grid) {
		validateGridSize(grid);

		for (AltarRecipe recipe : recipes) {
			if (matches(grid, recipe)) {
				return Optional.of(recipe);
			}
		}

		return Optional.empty();
	}

	private boolean matches(ItemStack[][] grid, AltarRecipe recipe) {
		char[][] pattern = recipe.getPattern();

		for (int row = 0; row < GRID_SIZE; row++) {
			for (int col = 0; col < GRID_SIZE; col++) {
				char symbol = pattern[row][col];
				ItemStack actual = grid[row][col];

				if (symbol == EMPTY_SLOT) {
					if (!isEmpty(actual)) {
						return false;
					}
					continue;
				}

				ItemStack expected = recipe.getIngredients().get(symbol);
				if (expected == null) {
					throw new IllegalArgumentException("Нет предмета для символа рецепта: " + symbol);
				}

				if (!isExactMatch(actual, expected)) {
					return false;
				}
			}
		}

		return true;
	}

	private boolean isEmpty(ItemStack item) {
		return item == null || item.getType() == Material.AIR;
	}

	/**
	 * Точное совпадение ингредиента: материал и ItemMeta (имя, лор, зачарования и т.д.) через
	 * {@link ItemStack#isSimilar(ItemStack)}, плюс достаточное количество в слоте.
	 */
	private boolean isExactMatch(ItemStack actual, ItemStack expected) {
		if (isEmpty(actual)) {
			return false;
		}

		if (actual.getAmount() < expected.getAmount()) {
			return false;
		}

		return actual.isSimilar(expected);
	}

	private void validateGridSize(ItemStack[][] grid) {
		if (grid.length != GRID_SIZE) {
			throw new IllegalArgumentException("Сетка должна быть размером 5x5");
		}
		for (ItemStack[] row : grid) {
			if (row.length != GRID_SIZE) {
				throw new IllegalArgumentException("Сетка должна быть размером 5x5");
			}
		}
	}

	private int[][] guiToCraftingGrid(Inventory gui) {
		int[][] grid = new int[5][5];
		for (int i = 0; i < gui.getSize(); i++) {
			if((i%9>0)&&(i%9<6)){
				int row = i / 9;
				int col = i % 9 - 1;
				grid[row][col] = i;
			}
		}
		return grid;
	}

	/**
	 * Считывает текущее содержимое крафтовой сетки GUI алтаря (через {@link #guiToCraftingGrid}),
	 * ищет совпадающий рецепт и на следующий тик кладёт его результат в {@link #RESULT_SLOT},
	 * либо очищает слот, если ни один рецепт не подошёл.
	 */
	public void checkRecipe(Inventory gui) {
		int[][] slotGrid = guiToCraftingGrid(gui);
		ItemStack[][] itemGrid = readItemGrid(gui, slotGrid);

		Optional<AltarRecipe> match = findMatch(itemGrid);

		Bukkit.getScheduler().runTaskLater(plugin, () ->
				gui.setItem(RESULT_SLOT, match.map(recipe -> recipe.getResult().clone()).orElse(emptyResultPane())),
				1L);
	}

	/**
	 * Вызывается после забора предмета из {@link #RESULT_SLOT}: списывает из крафтовой сетки
	 * ингредиенты рецепта, совпавшего с её текущим содержимым, и пересчитывает результат
	 * (при наличии достаточного количества ингредиентов результат появится снова).
	 */
	public void consumeIngredientsForResult(Inventory gui) {
		int[][] slotGrid = guiToCraftingGrid(gui);
		ItemStack[][] itemGrid = readItemGrid(gui, slotGrid);

		findMatch(itemGrid).ifPresent(recipe -> {
			consumeIngredients(gui, recipe, slotGrid);
			checkRecipe(gui);
		});
	}

	private ItemStack[][] readItemGrid(Inventory gui, int[][] slotGrid) {
		ItemStack[][] itemGrid = new ItemStack[GRID_SIZE][GRID_SIZE];
		for (int row = 0; row < GRID_SIZE; row++) {
			for (int col = 0; col < GRID_SIZE; col++) {
				itemGrid[row][col] = gui.getItem(slotGrid[row][col]);
			}
		}
		return itemGrid;
	}

	private void consumeIngredients(Inventory gui, AltarRecipe recipe, int[][] slotGrid) {
		char[][] pattern = recipe.getPattern();

		for (int row = 0; row < GRID_SIZE; row++) {
			for (int col = 0; col < GRID_SIZE; col++) {
				char symbol = pattern[row][col];
				if (symbol == EMPTY_SLOT) {
					continue;
				}

				int slot = slotGrid[row][col];
				ItemStack actual = gui.getItem(slot);
				if (actual == null) {
					continue;
				}

				int remaining = actual.getAmount() - recipe.getIngredients().get(symbol).getAmount();
				if (remaining <= 0) {
					gui.setItem(slot, null);
				} else {
					actual.setAmount(remaining);
				}
			}
		}
	}

	/**
	 * Заглушка для 25го слота, когда ни один рецепт не совпал - декоративная панель
	 * вместо воздуха, чтобы слот визуально отличался от "пустого" результата крафта.
	 */
	private ItemStack emptyResultPane() {
		ItemStack pane = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
		ItemMeta meta = pane.getItemMeta();
		meta.setDisplayName(" ");
		pane.setItemMeta(meta);
		return pane;
	}
}
