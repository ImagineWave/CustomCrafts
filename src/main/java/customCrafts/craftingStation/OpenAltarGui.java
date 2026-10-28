package customCrafts.craftingStation;

import customCrafts.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class OpenAltarGui implements Listener {

	private static final String GUI_NAME = "§5Алтарь";
	private static final char ANY_BLOCK = '#';
	private final AltarCraftingService craftingService;

	public OpenAltarGui(AltarCraftingService craftingService) {
		this.craftingService = craftingService;
	}
	// Сопоставление символов схемы с материалами. '#' зарезервирован как "любой блок" и не задаётся здесь.
	private static final Map<Character, Material> ALTAR_MATERIALS = Map.of(
			'B', Material.IRON_BLOCK,
			'E', Material.END_ROD,
			'A', Material.AMETHYST_BLOCK,
			'H', Material.HONEY_BLOCK,
			'D', Material.DISPENSER
	);

	// Паттерн структуры по слоям Y: [нижний, средний, верхний], каждый слой 5x5 (строки по Z, столбцы по X).
	// Центр схемы - центральная ячейка (ряд 2, колонка 2) ВЕРХНЕГО (последнего) слоя, это блок алтаря.
	private static final char[][][] ALTAR_PATTERN = {
			{ // слой y - 2
					"#BBB#".toCharArray(),
					"BBBBB".toCharArray(),
					"BBBBB".toCharArray(),
					"BBBBB".toCharArray(),
					"#BBB#".toCharArray()
			},
			{ // слой y - 1
					"#####".toCharArray(),
					"#EAE#".toCharArray(),
					"#AHA#".toCharArray(),
					"#EAE#".toCharArray(),
					"#####".toCharArray()
			},
			{ // слой y - уровень самого блока алтаря
					"#####".toCharArray(),
					"#####".toCharArray(),
					"##D##".toCharArray(),
					"#####".toCharArray(),
					"#####".toCharArray()
			}
	};


	/**
	 * При открытии диспенсера, являющегося центром корректно собранной структуры алтаря,
	 * отменяет стандартный интерфейс диспенсера и открывает GUI алтаря.
	 */
	@EventHandler
	public void onOpenDispenser(InventoryOpenEvent event) {
		InventoryHolder holder = event.getInventory().getHolder();
		if (!(holder instanceof Dispenser dispenser)) {
			return;
		}

		Block block = dispenser.getBlock();
		if (!isAltarStructureValid(block)) {
			return; // структура не собрана - это обычный диспенсер, открываем его штатно
		}

		event.setCancelled(true);
		if (event.getPlayer() instanceof Player player) {
			open(player);
		}
	}

	/**
	 * Запрещает забирать декоративные панели BLACK_STAINED_GLASS_PANE и LIGHT_GRAY_STAINED_GLASS_PANE
	 * из GUI алтаря.
	 */
	@EventHandler
	public void onTakeGlassPane(InventoryClickEvent event) {
		if (!GUI_NAME.equals(event.getView().getTitle())) {
			return;
		}

		ItemStack clicked = event.getCurrentItem();
		if (clicked != null && (clicked.getType() == Material.BLACK_STAINED_GLASS_PANE
				|| clicked.getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {
			event.setCancelled(true);
		}
	}

	/**
	 * Запрещает двойной клик (сбор одинаковых предметов со всего инвентаря в курсор) в GUI алтаря,
	 * пока в 25м слоте лежит реальный результат рецепта: такой клик может забрать его, даже если
	 * физически кликнули по предмету того же типа в инвентаре игрока, минуя списание ингредиентов
	 * (дюп). Если в 25м слоте декоративная заглушка (рецепт не собран), защита не нужна.
	 */
	@EventHandler
	public void onCollectToCursor(InventoryClickEvent event) {
		if (!GUI_NAME.equals(event.getView().getTitle())) {
			return;
		}

		if (event.getAction() != InventoryAction.COLLECT_TO_CURSOR) {
			return;
		}

		ItemStack resultSlotItem = event.getView().getTopInventory().getItem(25);
		if (resultSlotItem == null || resultSlotItem.getType() == Material.AIR
				|| resultSlotItem.getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
			return; // рецепт не собран - собирать нечего, можно безопасно разрешить двойной клик
		}

		event.setCancelled(true);
	}

	/**
	 * Запрещает класть предметы в 25й слот GUI алтаря (разрешено только забирать из него).
	 */
	@EventHandler
	public void onPlaceItemInCraftSlot(InventoryClickEvent event) {
		if (!GUI_NAME.equals(event.getView().getTitle())) {
			return;
		}

		if (event.getClickedInventory() != event.getView().getTopInventory() || event.getSlot() != 25) {
			return;
		}

		switch (event.getAction()) {
			case PLACE_ALL, PLACE_SOME, PLACE_ONE, SWAP_WITH_CURSOR, HOTBAR_SWAP, HOTBAR_MOVE_AND_READD -> event.setCancelled(true);
			default -> {}
		}
	}

	/**
	 * При заборе предмета из 25го слота GUI алтаря списывает ингредиенты совпавшего рецепта
	 * из крафтовой сетки. Приоритет MONITOR, чтобы читать итоговое состояние отмены события
	 * после {@link #onPlaceItemInCraftSlot} - иначе, например, HOTBAR_SWAP по 25му слоту
	 * отменяется им (предмет не забирается), но здесь всё равно списал бы ингредиенты.
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onTakeCraftResult(InventoryClickEvent event) {
		if (!GUI_NAME.equals(event.getView().getTitle())) {
			return;
		}

		if (event.getClickedInventory() != event.getView().getTopInventory() || event.getSlot() != 25) {
			return;
		}

		if (event.isCancelled()) {
			return; // забор не состоялся (например, отменён onPlaceItemInCraftSlot)
		}

		ItemStack result = event.getCurrentItem();
		if (result == null || result.getType() == Material.AIR
				|| result.getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE
				|| result.getType() == Material.BLACK_STAINED_GLASS_PANE) {
			return; // слот пуст либо в нём декоративная заглушка "рецепт не собран" - забор уже запрещён onTakeGlassPane
		}

		Inventory topInventory = event.getView().getTopInventory();
		// Задержка в 1 тик по той же причине, что и в onCraftingGridChange: забор предмета
		// применяется к инвентарю после этого события, а не до него.
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> craftingService.consumeIngredientsForResult(topInventory), 1L);
	}

	/**
	 * При любом клике в GUI алтаря (включая shift-click из инвентаря игрока в крафтовую сетку
	 * или из сетки обратно в инвентарь) запускает проверку рецептов, чтобы обновить результат
	 * в 25м слоте. Не фильтруется по кликнутому инвентарю/слоту, т.к. при shift-click слот
	 * события относится к инвентарю игрока, а не к сетке алтаря, хотя предмет попадает именно в неё.
	 */
	@EventHandler
	public void onCraftingGridChange(InventoryClickEvent event) {
		if (!GUI_NAME.equals(event.getView().getTitle())) {
			return;
		}

		Inventory topInventory = event.getView().getTopInventory();
		// Задержка в 1 тик: клик ещё не применён к инвентарю в момент события,
		// проверка должна идти уже по обновлённому содержимому.
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> craftingService.checkRecipe(topInventory), 1L);
	}

	/**
	 * Перетаскивание предмета мышью по нескольким слотам сразу (ЛКМ/ПКМ с движением) не порождает
	 * InventoryClickEvent, поэтому для такого изменения сетки алтаря проверка рецепта запускается отдельно.
	 */
	@EventHandler
	public void onCraftingGridDrag(InventoryDragEvent event) {
		if (!GUI_NAME.equals(event.getView().getTitle())) {
			return;
		}

		Inventory topInventory = event.getView().getTopInventory();
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> craftingService.checkRecipe(topInventory), 1L);
	}

	/**
	 * При закрытии GUI алтаря возвращает все предметы из инвентаря (кроме декоративных стеклянных
	 * панелей и содержимого слота 25 - крафтового слота) в инвентарь игрока, а то, что не влезло,
	 * выбрасывает на землю.
	 */
	@EventHandler
	public void onCloseAltarGui(InventoryCloseEvent event) {
		if (!GUI_NAME.equals(event.getView().getTitle())) {
			return;
		}

		if (!(event.getPlayer() instanceof Player player)) {
			return;
		}

		Inventory inventory = event.getInventory();
		for (int i = 0; i < inventory.getSize(); i++) {
			if (i == 25) {
				continue;
			}

			ItemStack item = inventory.getItem(i);
			if (item == null || item.getType() == Material.AIR || item.getType() == Material.BLACK_STAINED_GLASS_PANE) {
				continue;
			}

			for (ItemStack leftover : player.getInventory().addItem(item).values()) {
				player.getWorld().dropItemNaturally(player.getLocation(), leftover);
			}
			inventory.setItem(i, null);
		}
	}


	/**
	 * Проверяет наличие многоблочной структуры 5x5x3 вокруг переданного блока по заданному паттерну.
	 * Блок center считается центром схемы: строка 2, столбец 2 ВЕРХНЕГО (последнего) слоя паттерна,
	 * остальные слои располагаются ниже center по оси Y.
	 *
	 * @param center    центральный блок структуры (например, блок алтаря), находится в верхнем слое паттерна
	 * @param pattern   схема по слоям [y][z][x] от нижнего к верхнему, размер каждого слоя 5x5
	 * @param materials сопоставление символов схемы с требуемыми материалами
	 * @return true, если все блоки структуры соответствуют паттерну
	 */
	private boolean detectMultiblockStructure(Block center, char[][][] pattern, Map<Character, Material> materials) {
		int layers = pattern.length;
		int centerLayer = layers - 1;

		for (int layerIndex = 0; layerIndex < layers; layerIndex++) {
			char[][] layer = pattern[layerIndex];
			int dy = layerIndex - centerLayer;
			int rows = layer.length;
			int centerRow = rows / 2;

			for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
				char[] row = layer[rowIndex];
				int dz = rowIndex - centerRow;
				int centerCol = row.length / 2;

				for (int colIndex = 0; colIndex < row.length; colIndex++) {
					char symbol = row[colIndex];
					if (symbol == ANY_BLOCK) {
						continue;
					}

					int dx = colIndex - centerCol;
					if (dx == 0 && dy == 0 && dz == 0) {
						continue; // сам центральный блок не проверяем
					}

					Material required = materials.get(symbol);
					if (required == null) {
						throw new IllegalArgumentException("Нет материала для символа паттерна: " + symbol);
					}

					Block block = center.getRelative(dx, dy, dz);
					if (block.getType() != required) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Проверяет структуру алтаря 5x5x3 вокруг указанного блока по {@link #ALTAR_PATTERN}.
	 */
	public boolean isAltarStructureValid(Block altarBlock) {
		return detectMultiblockStructure(altarBlock, ALTAR_PATTERN, ALTAR_MATERIALS);
	}

	private void open(Player player) {
		Inventory gui = Bukkit.createInventory(player, 9 * 5, GUI_NAME);
		fillInventory(gui);
		player.openInventory(gui);
	}

    private void fillInventory(Inventory gui) {
		ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemStack fakePane = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
		ItemMeta meta = pane.getItemMeta();
		meta.setDisplayName(" ");
		pane.setItemMeta(meta);
		ItemMeta fakePaneMeta = fakePane.getItemMeta();
		fakePaneMeta.setDisplayName(" ");
		fakePane.setItemMeta(fakePaneMeta);
		for (int i = 0; i < gui.getSize(); i++) {
			if(i%9==0){
				gui.setItem(i, pane);
			}

			if(i%9>=6){
				if(i == 25){
					gui.setItem(i, fakePane);
				} else {
					gui.setItem(i, pane);
				}
			}
		}
	}

}
