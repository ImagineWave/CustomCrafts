package customCrafts.recipes;

import customCrafts.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class QualityOfLifeRecipes {
    private Main plugin;

    public QualityOfLifeRecipes(Main plugin){
        this.plugin = plugin;

        init();
    }

    private void init(){
        craftStar();
        craftTrident();
        craftPhantomMembrane();
        craftLight();
        craftEnchantedGoldenApple();
        craftConcrete();
        craftBlackDye();
        craftStickyPiston();

        //Медное бытие
        craftExposedCopperBlock();
        craftWeatheredCopperBlock();
        craftOxidizedCopperBlock();
    }

    private void craftStar() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        NamespacedKey itemKey = new NamespacedKey(plugin, "nether_star_key");
        ShapedRecipe itemRecipe = new ShapedRecipe(itemKey, item);
        itemRecipe.shape("AAA","BBB"," B ");
        itemRecipe.setIngredient('A', Material.WITHER_SKELETON_SKULL);
        itemRecipe.setIngredient('B', Material.SOUL_SAND);
        Bukkit.getServer().addRecipe(itemRecipe);
    }

    private void craftTrident() {
        ItemStack item = new ItemStack(Material.TRIDENT);
        NamespacedKey itemKey = new NamespacedKey(plugin, "trident_key");
        ShapedRecipe itemRecipe = new ShapedRecipe(itemKey, item);
        itemRecipe.shape("AA ","AB ","  C");
        itemRecipe.setIngredient('A', Material.PRISMARINE_SHARD);
        itemRecipe.setIngredient('B', Material.HEART_OF_THE_SEA);
        itemRecipe.setIngredient('C', Material.NETHER_STAR);
        Bukkit.getServer().addRecipe(itemRecipe);
    }

    private void craftPhantomMembrane() {
        ItemStack membrane = new ItemStack(Material.PHANTOM_MEMBRANE);
        NamespacedKey membraneKey = new NamespacedKey(plugin, "membrane_key");
        ShapedRecipe membraneRecipe = new ShapedRecipe(membraneKey, membrane);
        membraneRecipe.shape(" A ","ABA"," A ");
        membraneRecipe.setIngredient('A', Material.STRING);
        membraneRecipe.setIngredient('B', Material.LEATHER);
        Bukkit.getServer().addRecipe(membraneRecipe);
    }

    private void craftLight() {
        ItemStack item = new ItemStack(Material.LIGHT, 4);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add("Дорого? А все потому что");
        lore.add("невидимый свет это роскошь");
        meta.setLore(lore);
        item.setItemMeta(meta);

        NamespacedKey itemKey = new NamespacedKey(plugin, "light_key");
        ShapedRecipe itemRecipe = new ShapedRecipe(itemKey, item);
        itemRecipe.shape(" A ","BCB"," A ");
        itemRecipe.setIngredient('A', Material.CANDLE);
        itemRecipe.setIngredient('C', Material.SEA_LANTERN);
        itemRecipe.setIngredient('B', Material.GLOW_BERRIES);

        Bukkit.getServer().addRecipe(itemRecipe);
    }


    private void craftConcrete() {
        // Массив всех возможных цветов бетона
        Material[] concretePowderTypes = {
                Material.WHITE_CONCRETE_POWDER,
                Material.ORANGE_CONCRETE_POWDER,
                Material.MAGENTA_CONCRETE_POWDER,
                Material.LIGHT_BLUE_CONCRETE_POWDER,
                Material.YELLOW_CONCRETE_POWDER,
                Material.LIME_CONCRETE_POWDER,
                Material.PINK_CONCRETE_POWDER,
                Material.GRAY_CONCRETE_POWDER,
                Material.LIGHT_GRAY_CONCRETE_POWDER,
                Material.CYAN_CONCRETE_POWDER,
                Material.PURPLE_CONCRETE_POWDER,
                Material.BLUE_CONCRETE_POWDER,
                Material.BROWN_CONCRETE_POWDER,
                Material.GREEN_CONCRETE_POWDER,
                Material.RED_CONCRETE_POWDER,
                Material.BLACK_CONCRETE_POWDER
        };

        Material[] concreteTypes = {
                Material.WHITE_CONCRETE,
                Material.ORANGE_CONCRETE,
                Material.MAGENTA_CONCRETE,
                Material.LIGHT_BLUE_CONCRETE,
                Material.YELLOW_CONCRETE,
                Material.LIME_CONCRETE,
                Material.PINK_CONCRETE,
                Material.GRAY_CONCRETE,
                Material.LIGHT_GRAY_CONCRETE,
                Material.CYAN_CONCRETE,
                Material.PURPLE_CONCRETE,
                Material.BLUE_CONCRETE,
                Material.BROWN_CONCRETE,
                Material.GREEN_CONCRETE,
                Material.RED_CONCRETE,
                Material.BLACK_CONCRETE
        };

        for (int i = 0; i < concretePowderTypes.length; i++) {
            ItemStack resultItem = new ItemStack(concreteTypes[i], 9);

            NamespacedKey key = new NamespacedKey(plugin, concreteTypes[i].name().toLowerCase() + "_concrete_recipe");

            ShapedRecipe recipe = new ShapedRecipe(key, resultItem);
            recipe.shape("BBB", "BBB", "BBB");
            recipe.setIngredient('B', concretePowderTypes[i]);

            Bukkit.getServer().addRecipe(recipe);
        }
    }


    private void craftEnchantedGoldenApple() {
        ItemStack egapple = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
        NamespacedKey egappleKey = new NamespacedKey(plugin, "egapple_key");
        ShapedRecipe egappleRecipe = new ShapedRecipe(egappleKey, egapple);
        egappleRecipe.shape("AAA","ABA","AAA");
        egappleRecipe.setIngredient('A', Material.GOLD_BLOCK);
        egappleRecipe.setIngredient('B', Material.APPLE);
        Bukkit.getServer().addRecipe(egappleRecipe);
    }

    private void craftExposedCopperBlock() {
        ItemStack exposedCopper = new ItemStack(Material.EXPOSED_COPPER);
        exposedCopper.setAmount(9);
        NamespacedKey exposedCopperKey = new NamespacedKey(plugin, "exposed_copper_block_key");
        ShapedRecipe exposedCopperRecipe = new ShapedRecipe(exposedCopperKey, exposedCopper);
        exposedCopperRecipe.shape("AAA","AAA","AAA");
        exposedCopperRecipe.setIngredient('A', Material.COPPER_BLOCK);
        Bukkit.getServer().addRecipe(exposedCopperRecipe);
    }

    private void craftWeatheredCopperBlock() {
        ItemStack weatheredCopper = new ItemStack(Material.WEATHERED_COPPER);
        weatheredCopper.setAmount(9);
        NamespacedKey weatheredCopperBlockKey = new NamespacedKey(plugin, "weathered_copper_block_key");
        ShapedRecipe weatheredCopperRecipe = new ShapedRecipe(weatheredCopperBlockKey, weatheredCopper);
        weatheredCopperRecipe.shape("AAA","AAA","AAA");
        weatheredCopperRecipe.setIngredient('A', Material.EXPOSED_COPPER);
        Bukkit.getServer().addRecipe(weatheredCopperRecipe);
    }

    private void craftOxidizedCopperBlock() {
        ItemStack oxidizedCopper = new ItemStack(Material.OXIDIZED_COPPER);
        oxidizedCopper.setAmount(9);
        NamespacedKey oxidizedCopperKey = new NamespacedKey(plugin, "oxidized_copper_block_key");
        ShapedRecipe oxidizedCopperRecipe = new ShapedRecipe(oxidizedCopperKey, oxidizedCopper);
        oxidizedCopperRecipe.shape("AAA","AAA","AAA");
        oxidizedCopperRecipe.setIngredient('A', Material.WEATHERED_COPPER);
        Bukkit.getServer().addRecipe(oxidizedCopperRecipe);
    }

    private void craftBlackDye(){
        ItemStack blackDye = new ItemStack(Material.BLACK_DYE);
        blackDye.setAmount(1);
        NamespacedKey blackDyeKey = new NamespacedKey(plugin, "black_dye_key");
        ShapelessRecipe blackDyeRecipe = new ShapelessRecipe(blackDyeKey, blackDye);
        blackDyeRecipe.addIngredient(1, Material.COAL);
        Bukkit.getServer().addRecipe(blackDyeRecipe);
    }

    private void craftStickyPiston(){
        ItemStack stickyPiston = new ItemStack(Material.STICKY_PISTON);
        stickyPiston.setAmount(1);
        NamespacedKey stickyPistonKey = new NamespacedKey(plugin, "sticky_piston_recipe");
        ShapelessRecipe stickyPistonRecipe = new ShapelessRecipe(stickyPistonKey, stickyPiston);
        stickyPistonRecipe.addIngredient(1, Material.HONEY_BOTTLE);
        stickyPistonRecipe.addIngredient(1, Material.PISTON);
        Bukkit.getServer().addRecipe(stickyPistonRecipe);
    }
}
