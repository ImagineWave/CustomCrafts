package CustomCrafts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import CustomCrafts.ItemStack.CheapPickaxeItem;
import CustomCrafts.ItemStack.ChronosItem;
import CustomCrafts.ItemStack.CompassItem;
import CustomCrafts.ItemStack.DestroyerItem;
import CustomCrafts.ItemStack.EthernalFeatherItem;
import CustomCrafts.ItemStack.NotchedPickaxeItem;
import CustomCrafts.ItemStack.ReaperItem;
import CustomCrafts.ItemStack.RelocatorItem;
import CustomCrafts.ItemStack.SoulFeatherItem;
import CustomCrafts.ItemStack.StrongAxeItem;
import CustomCrafts.ItemStack.TotemItem;

public class Main extends JavaPlugin {

private static Main instance;
	
	public static Main instance() {
		return instance;
	}
	
	
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(new Chronos(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Reaper(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Totem(this), this);
		//Bukkit.getServer().getPluginManager().registerEvents(new Contract(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Compass(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Handler(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new SoulAngel(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new NotchedPickaxe(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Remover(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new StrongAxe2(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new CustomCrafts.relocator.RelocatorGUIListener(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new CustomCrafts.relocator.RelocatorOpenGUIandCharge(this), this);
		
		//BukkitTask globalCheck = new StrongAxe(this).runTaskTimer(this, 0, 30L);
		
		getCommand("givecustomitem").setExecutor(new giveCustomItem(this));
		//craftClock();
		craftAngel();
		craftEnchantedGoldenApple();
		craftPhantomMembrane();
		craftReaper();
		craftTotem();
		//craftContract();
		craftCompass();
		craftTrident();
		//craftSoulFeather();
		craftNotchedPickaxe();
		craftCheapPickaxe();
		craftStrongAxe();
		craftRemover();
		craftLight();
		craftRelocator();
		}
	
	public void onDisable(){
		
	}
	
	
	public void craftClock() {
		ItemStack item = ChronosItem.getItem();
		NamespacedKey Chronoskey = new NamespacedKey(this, "Chronos_key");
		ShapedRecipe Chronosrecipe = new ShapedRecipe(Chronoskey, item);
		Chronosrecipe.shape("ABA", "BCB", "ABA");
		Chronosrecipe.setIngredient('C', Material.CLOCK);
		Chronosrecipe.setIngredient('A', Material.GOLD_BLOCK);
		Chronosrecipe.setIngredient('B', Material.DIAMOND_BLOCK);
		Bukkit.getServer().addRecipe(Chronosrecipe);
	}
	
	public void craftAngel() {
		ItemStack item = EthernalFeatherItem.getItem();
		NamespacedKey angelkey = new NamespacedKey(this, "angel_key");
		ShapedRecipe angelrecipe = new ShapedRecipe(angelkey, item);
		angelrecipe.shape("ABA", "BCB", " B ");
		angelrecipe.setIngredient('C', Material.NETHER_STAR);
		angelrecipe.setIngredient('A', Material.ELYTRA);
		angelrecipe.setIngredient('B', Material.NETHERITE_BLOCK);
		Bukkit.getServer().addRecipe(angelrecipe);
	}
	public void craftEnchantedGoldenApple() {
		ItemStack egapple = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
		NamespacedKey egappleKey = new NamespacedKey(this, "egapple_key");
		ShapedRecipe egappleRecipe = new ShapedRecipe(egappleKey, egapple);
		egappleRecipe.shape("AAA","ABA","AAA");
		egappleRecipe.setIngredient('A', Material.GOLD_BLOCK);
		egappleRecipe.setIngredient('B', Material.APPLE);
		Bukkit.getServer().addRecipe(egappleRecipe);
	}
	public void craftPhantomMembrane() {
		ItemStack membrane = new ItemStack(Material.PHANTOM_MEMBRANE);
		NamespacedKey membraneKey = new NamespacedKey(this, "membrane_key");
		ShapedRecipe membraneRecipe = new ShapedRecipe(membraneKey, membrane);
		membraneRecipe.shape(" A ","ABA"," A ");
		membraneRecipe.setIngredient('A', Material.STRING);
		membraneRecipe.setIngredient('B', Material.LEATHER);
		Bukkit.getServer().addRecipe(membraneRecipe);
	}	
	
	public void craftReaper() {
		ItemStack item = ReaperItem.getItem();
		NamespacedKey reaperKey = new NamespacedKey(this, "reaper_key");
		ShapedRecipe reaperRecipe = new ShapedRecipe(reaperKey, item);
		reaperRecipe.shape("AA "," CA","C  ");
		reaperRecipe.setIngredient('A', Material.NETHERITE_INGOT);
		reaperRecipe.setIngredient('C', Material.BLAZE_ROD);
		Bukkit.getServer().addRecipe(reaperRecipe);
	}	
	public void craftTotem() {
		ItemStack item = TotemItem.getItem();
		NamespacedKey totemKey = new NamespacedKey(this, "totem_key");
		ShapedRecipe totemRecipe = new ShapedRecipe(totemKey, item);
		totemRecipe.shape(" A ","ABA"," A ");
		totemRecipe.setIngredient('A', Material.TOTEM_OF_UNDYING);
		totemRecipe.setIngredient('B', Material.DIAMOND_BLOCK);
		Bukkit.getServer().addRecipe(totemRecipe);
	}
	public void craftContract() {
		ItemStack contract = new ItemStack(Material.PAPER);
		ItemMeta meta = contract.getItemMeta();
		meta.setDisplayName("§a§lКонтракт");
		List<String> lore = new ArrayList<String>();
		meta.addEnchant(Enchantment.DURABILITY, 1 , true);
		lore.add("Позволяет создавать");
		lore.add("самые могущественные артефакты");
		lore.add("§4Но какой ценой?");
		lore.add("§f／人§c◕§f ‿‿§c ◕§f人＼");
		meta.setLore(lore);
		contract.setItemMeta(meta);
		NamespacedKey contractKey = new NamespacedKey(this, "contract_key");
		ShapedRecipe contractRecipe = new ShapedRecipe(contractKey, contract);
		contractRecipe.shape("AAA","ABA","AAA");
		contractRecipe.setIngredient('A', Material.PAPER);
		contractRecipe.setIngredient('B', Material.REDSTONE);
		Bukkit.getServer().addRecipe(contractRecipe);
	}
	public void craftCompass() {
		ItemStack item = CompassItem.getItem();
		NamespacedKey itemKey = new NamespacedKey(this, "compass_key");
		ShapedRecipe itemRecipe = new ShapedRecipe(itemKey, item);
		itemRecipe.shape(" D ","CBC"," D ");
		itemRecipe.setIngredient('B', Material.COMPASS);
		itemRecipe.setIngredient('C', Material.DRAGON_BREATH);
		itemRecipe.setIngredient('D', Material.IRON_BLOCK);
		Bukkit.getServer().addRecipe(itemRecipe);
	}
	public void craftTrident() {
		ItemStack item = new ItemStack(Material.TRIDENT);
		NamespacedKey itemKey = new NamespacedKey(this, "trident_key");
		ShapedRecipe itemRecipe = new ShapedRecipe(itemKey, item);
		itemRecipe.shape("AA ","AB ","  C");
		itemRecipe.setIngredient('A', Material.PRISMARINE_SHARD);
		itemRecipe.setIngredient('B', Material.HEART_OF_THE_SEA);
		itemRecipe.setIngredient('C', Material.NETHER_STAR);
		Bukkit.getServer().addRecipe(itemRecipe);
	}
	public void craftSoulFeather() {
		ItemStack item = SoulFeatherItem.getItem();
		NamespacedKey itemKey = new NamespacedKey(this, "soulFeather_key");
		ShapedRecipe itemRecipe = new ShapedRecipe(itemKey, item);
		itemRecipe.shape(" A ","ABA"," A ");
		itemRecipe.setIngredient('A', Material.PLAYER_HEAD);
		itemRecipe.setIngredient('B', Material.FEATHER);
		Bukkit.getServer().addRecipe(itemRecipe);
	}
	public void craftNotchedPickaxe() {
		ItemStack item = NotchedPickaxeItem.getItem();
		NamespacedKey itemKey = new NamespacedKey(this, "nPickaxe_key");
		ShapedRecipe itemRecipe = new ShapedRecipe(itemKey, item);
		itemRecipe.shape("BBH"," NB","N B");
		itemRecipe.setIngredient('B', Material.BEDROCK);
		itemRecipe.setIngredient('H', Material.CONDUIT);
		itemRecipe.setIngredient('N', Material.NETHERITE_BLOCK);
		Bukkit.getServer().addRecipe(itemRecipe);
	}
	public void craftCheapPickaxe() {
		ItemStack item = CheapPickaxeItem.getItem();
		NamespacedKey itemKey = new NamespacedKey(this, "cheap_pickaxe");
		ShapedRecipe itemRecipe = new ShapedRecipe(itemKey, item);
		itemRecipe.shape("BSB","SAS","BSB");
		itemRecipe.setIngredient('A', Material.NETHERITE_PICKAXE);
		itemRecipe.setIngredient('S', Material.NETHERITE_INGOT);
		itemRecipe.setIngredient('B', Material.ENCHANTED_GOLDEN_APPLE);
		Bukkit.getServer().addRecipe(itemRecipe);
	}
	public void craftStrongAxe() {
        ItemStack item = StrongAxeItem.getItem();
        NamespacedKey itemKey = new NamespacedKey(this, "strong_axe");
        ShapedRecipe itemRecipe = new ShapedRecipe(itemKey, item);
        itemRecipe.shape("SNN"," BN"," B ");
        itemRecipe.setIngredient('N', Material.NETHERITE_INGOT);
        itemRecipe.setIngredient('B', Material.BLAZE_ROD);
        itemRecipe.setIngredient('S', Material.NETHER_STAR);
        Bukkit.getServer().addRecipe(itemRecipe);
    }
	public void craftRemover() {
        ItemStack item = DestroyerItem.getItem();
        NamespacedKey itemKey = new NamespacedKey(this, "destroyer");
        ShapedRecipe itemRecipe = new ShapedRecipe(itemKey, item);
        itemRecipe.shape("DON","BCO","BBD");
        itemRecipe.setIngredient('D', Material.DRAGON_BREATH);
        itemRecipe.setIngredient('O', Material.CRYING_OBSIDIAN);
        itemRecipe.setIngredient('N', Material.NETHERITE_BLOCK);
        itemRecipe.setIngredient('C', Material.HEART_OF_THE_SEA);
        itemRecipe.setIngredient('B', Material.IRON_BLOCK);
        Bukkit.getServer().addRecipe(itemRecipe);
    }
	public void craftLight() {
		ItemStack item = new ItemStack(Material.LIGHT);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add("Дорого? А все потому что");
		lore.add("невидимый свет это роскошь");
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		NamespacedKey itemKey = new NamespacedKey(this, "light_key");
		ShapedRecipe itemRecipe = new ShapedRecipe(itemKey, item);
		itemRecipe.shape(" A ","BCB"," A ");
		itemRecipe.setIngredient('A', Material.CANDLE);
		itemRecipe.setIngredient('C', Material.SEA_LANTERN);
		itemRecipe.setIngredient('B', Material.GLOW_BERRIES);
		
		Bukkit.getServer().addRecipe(itemRecipe);
	}
	public void craftRelocator() {
		ItemStack item = RelocatorItem.getItem();
		NamespacedKey itemKey = new NamespacedKey(this, "relocator_key");
		ShapedRecipe itemRecipe = new ShapedRecipe(itemKey, item);
		itemRecipe.shape("RIB","DSD","YIG");
		itemRecipe.setIngredient('I', Material.NETHERITE_INGOT);
		itemRecipe.setIngredient('D', Material.DRAGON_BREATH);
		itemRecipe.setIngredient('S', Material.NETHER_STAR);
		itemRecipe.setIngredient('R', Material.MUSIC_DISC_CHIRP);
		itemRecipe.setIngredient('B', Material.MUSIC_DISC_MALL);
		itemRecipe.setIngredient('Y', Material.MUSIC_DISC_13);
		itemRecipe.setIngredient('G', Material.MUSIC_DISC_CAT);
		
		Bukkit.getServer().addRecipe(itemRecipe);
	}
	public void craftBedrock() {
		ItemStack item = new ItemStack(Material.BEDROCK);
		NamespacedKey itemKey = new NamespacedKey(this, "trident_key");
		ShapedRecipe itemRecipe = new ShapedRecipe(itemKey, item);
		itemRecipe.shape("AA ","AB ","  C");
		itemRecipe.setIngredient('A', Material.PRISMARINE_SHARD);
		itemRecipe.setIngredient('B', Material.HEART_OF_THE_SEA);
		itemRecipe.setIngredient('C', Material.NETHER_STAR);
		Bukkit.getServer().addRecipe(itemRecipe);
	}
}
