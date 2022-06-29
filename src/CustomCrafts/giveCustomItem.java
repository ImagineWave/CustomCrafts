package CustomCrafts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class giveCustomItem implements CommandExecutor{
	private Main plugin;
	public giveCustomItem(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand( CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.isOp()) {
			sender.sendMessage("§cУ вас нет прав");
			return true;
		}
		Player p = (Player) sender;
		if(args.length==0){
			
			ItemStack ethernalFeather = new ItemStack(Material.FEATHER);
			ItemMeta ethernalFeatherMeta = ethernalFeather.getItemMeta();
			ethernalFeatherMeta.setDisplayName("§5§lВечное перо");
			List<String> ethernalFeatherLore = new ArrayList<String>();
			ethernalFeatherLore.add("§4Постоянный полет");
			ethernalFeatherMeta.setLore(ethernalFeatherLore);
			ethernalFeatherMeta.addEnchant(Enchantment.DURABILITY, 1 , true);
			ethernalFeather.setItemMeta(ethernalFeatherMeta);
			
			ItemStack soulFeather = new ItemStack(Material.FEATHER);
			ItemMeta soulFeatherMeta = soulFeather.getItemMeta();
			soulFeatherMeta.setDisplayName("§6Перо Души");
			List<String> soulFeatherLore = new ArrayList<String>();
			soulFeatherMeta.addEnchant(Enchantment.DURABILITY, 1 , true);
			soulFeatherLore.add("§4Постоянный полет");
			soulFeatherLore.add("§eВладелец");
			soulFeatherLore.add("-");
			soulFeatherLore.add("§aПосле смерти");
			soulFeatherLore.add("§aвернется к владельцу");
			soulFeatherMeta.setLore(soulFeatherLore);
			soulFeather.setItemMeta(soulFeatherMeta);
			
			ItemStack compass = new ItemStack(Material.COMPASS);
			ItemMeta compassMeta = compass.getItemMeta();
			compassMeta.setDisplayName("§6Компас возвращения");
			List<String> compassLore = new ArrayList<String>();
			compassMeta.addEnchant(Enchantment.DURABILITY, 1 , true);
			compassLore.add("§aВозвращает владельца домой");
			compassLore.add("§eВладелец");
			compassLore.add("");
			compassLore.add("§eОсталось использований");
			compassLore.add("5");
			compassMeta.setLore(compassLore);
			compass.setItemMeta(compassMeta);
			
			ItemStack notchedPickaxe = new ItemStack(Material.NETHERITE_PICKAXE);
			ItemMeta notchedPickaxeMeta = notchedPickaxe.getItemMeta();
			notchedPickaxeMeta.setDisplayName("§4§lЗ§e§lа§a§lз§b§lу§9§lб§d§lр§9§le§b§lн§a§lн§e§lа§4§lя §5§lКирка");
			List<String> notchedPickaxeLore = new ArrayList<String>();
			notchedPickaxeLore.add("§4ПКМ для смены режима");
			notchedPickaxeLore.add("§eВладелец");
			notchedPickaxeLore.add("");
			notchedPickaxeLore.add("§eРежим");
			notchedPickaxeLore.add("Щёлк");
			notchedPickaxeLore.add("§aПосле смерти");
			notchedPickaxeLore.add("§aвернется к владельцу");
			notchedPickaxeMeta.setLore(notchedPickaxeLore);
			notchedPickaxeMeta.setUnbreakable(true);
			notchedPickaxeMeta.addEnchant(Enchantment.SILK_TOUCH, 1 , true);
			notchedPickaxeMeta.addEnchant(Enchantment.DIG_SPEED, 10 , true);
			notchedPickaxe.setItemMeta(notchedPickaxeMeta);
			
			ItemStack reaper = new ItemStack(Material.DIAMOND_HOE);
			ItemMeta reaperMeta = reaper.getItemMeta();
			reaperMeta.setDisplayName("§5§lЖнец");
			List<String> reaperLore = new ArrayList<>();
			reaperMeta.addEnchant(Enchantment.DAMAGE_ALL, 4 , true);
			reaperLore.add("§6Позволяет выбить визер череп с шансом 30%");
			reaperMeta.setLore(reaperLore);
			reaper.setItemMeta(reaperMeta);
			
			ItemStack Chronos = new ItemStack(Material.CLOCK);
			ItemMeta metaChronos = Chronos.getItemMeta();
			metaChronos.setDisplayName("§6§lЧасы Хроноса");
			List<String> chronosLore = new ArrayList<String>();
			chronosLore.add("Смена дня и ночи");
			metaChronos.setLore(chronosLore);
			metaChronos.addEnchant(Enchantment.ARROW_INFINITE, 1 , true);
			Chronos.setItemMeta(metaChronos);
			
			ItemStack cheapPickaxe = new ItemStack(Material.GOLDEN_PICKAXE);
			ItemMeta cheapPickaxeMeta = cheapPickaxe.getItemMeta();
			cheapPickaxeMeta.setDisplayName("§5§lВоля §5ш§5§lахтера");
			List<String> cheapPickaxeLore = new ArrayList<String>();
			cheapPickaxeLore.add("Время наше все");
			cheapPickaxeMeta.setLore(cheapPickaxeLore);
			cheapPickaxeMeta.addEnchant(Enchantment.DIG_SPEED, 8 , true);
			cheapPickaxeMeta.setUnbreakable(true);
			cheapPickaxe.setItemMeta(cheapPickaxeMeta);
			
			ItemStack strongAxe = new ItemStack(Material.NETHERITE_AXE);
	        ItemMeta strongAxeMeta = strongAxe.getItemMeta();
	        strongAxeMeta.setDisplayName("§c§lСлепая Ярость");
	        List<String> strongAxeLore = new ArrayList<String>();
	        strongAxeLore.add("Убить их всех!");
	        strongAxeMeta.setLore(strongAxeLore);
	        strongAxeMeta.addEnchant(Enchantment.DAMAGE_ALL, 10 , true);
	        strongAxeMeta.addEnchant(Enchantment.DIG_SPEED, 5 , true);
	        strongAxeMeta.setUnbreakable(true);
	        strongAxe.setItemMeta(strongAxeMeta);
	        
	        ItemStack destroyer = new ItemStack(Material.GOLDEN_SHOVEL);
	        ItemMeta destroyerMeta = destroyer.getItemMeta();
	        destroyerMeta.setDisplayName("§5Разрушитель");
	        List<String> destroyerLore = new ArrayList<String>();
	        destroyerLore.add("Уничтожет любой блок");
	        destroyerLore.add("§eОсталось зарядов");
	        destroyerLore.add("0");
	        destroyerMeta.setLore(destroyerLore);
	        destroyerMeta.addEnchant(Enchantment.DURABILITY, 1 , true);
	        destroyer.setItemMeta(destroyerMeta);
	        
	        ItemStack relocator = new ItemStack(Material.NETHER_STAR);
			ItemMeta relocatormeta = relocator.getItemMeta();
			relocatormeta.setDisplayName("§5§lЭ.П.У.П.Т.");
			List<String> relocatorlore = new ArrayList<String>();
			relocatorlore.add("Экспериментальное портативное устройство");
			relocatorlore.add("пространственного туннелирования");
			relocatorlore.add("§eОсталось зарядов");
			relocatorlore.add("0");
			relocatorlore.add("Точка не выбрана");
			relocatormeta.setLore(relocatorlore);
			relocator.setItemMeta(relocatormeta);
			
			Inventory gui = Bukkit.createInventory(p, 18, "MegaOpIMBAHolyShitItems");
			ItemStack[] imbaItems = {ethernalFeather,soulFeather,compass,notchedPickaxe,reaper,Chronos,cheapPickaxe,strongAxe,destroyer,relocator};
			gui.setContents(imbaItems);
			p.openInventory(gui);
			return true;
		}
		String item = args[0];
		addSelectedItemToPlayer(item, p);
		return true;
	}
	private void addSelectedItemToPlayer(String itemString, Player p) {
		switch (itemString) {
			case("ethernalFeather"):{
				ItemStack angel = new ItemStack(Material.FEATHER);
				ItemMeta meta = angel.getItemMeta();
				meta.setDisplayName("§5§lВечное перо");
				List<String> lore = new ArrayList<String>();
				lore.add("§4Постоянный полет");
				meta.setLore(lore);
				meta.addEnchant(Enchantment.DURABILITY, 1 , true);
				angel.setItemMeta(meta);
				p.getInventory().addItem(angel);
			}
			break;
			case("soulFeather"):{
				ItemStack item = new ItemStack(Material.FEATHER);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§6Перо Души");
				List<String> lore = new ArrayList<String>();
				meta.addEnchant(Enchantment.DURABILITY, 1 , true);
				lore.add("§4Постоянный полет");
				lore.add("§eВладелец");
				lore.add("-");
				lore.add("§aПосле смерти");
				lore.add("§aвернется к владельцу");
				meta.setLore(lore);
				item.setItemMeta(meta);
				p.getInventory().addItem(item);
			}
			break;
			
			case("compass"):{
				ItemStack item = new ItemStack(Material.COMPASS);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§6Компас возвращения");
				List<String> lore = new ArrayList<String>();
				meta.addEnchant(Enchantment.DURABILITY, 1 , true);
				lore.add("§aВозвращает владельца домой");
				lore.add("§eВладелец");
				lore.add("");
				lore.add("§eОсталось использований");
				lore.add("5");
				meta.setLore(lore);
				item.setItemMeta(meta);
				p.getInventory().addItem(item);
			}
			break;
			case("compass*"):{
				ItemStack item = new ItemStack(Material.COMPASS);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§6Компас возвращения");
				List<String> lore = new ArrayList<String>();
				meta.addEnchant(Enchantment.DURABILITY, 1 , true);
				lore.add("§aВозвращает владельца домой");
				lore.add("§eВладелец");
				lore.add("");
				lore.add("§eОсталось использований");
				lore.add("99999");
				meta.setLore(lore);
				item.setItemMeta(meta);
				p.getInventory().addItem(item);
			}
			break;
			case("pickaxe"):{
				ItemStack item = new ItemStack(Material.NETHERITE_PICKAXE);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§4§lЗ§e§lа§a§lз§b§lу§9§lб§d§lр§9§le§b§lн§a§lн§e§lа§4§lя §5§lКирка");
				List<String> lore = new ArrayList<String>();
				lore.add("§4ПКМ для смены режима");
				lore.add("§eВладелец");
				lore.add("");
				lore.add("§eРежим");
				lore.add("Щёлк");
				lore.add("§aПосле смерти");
				lore.add("§aвернется к владельцу");
				meta.setLore(lore);
				meta.setUnbreakable(true);
				meta.addEnchant(Enchantment.SILK_TOUCH, 1 , true);
				meta.addEnchant(Enchantment.DIG_SPEED, 10 , true);
				
				item.setItemMeta(meta);
				p.getInventory().addItem(item);
			}
			break;
			case("reaper"):{
				ItemStack reaper = new ItemStack(Material.DIAMOND_HOE);
				ItemMeta meta = reaper.getItemMeta();
				meta.setDisplayName("§5§lЖнец");
				List<String> lore = new ArrayList<>();
				meta.addEnchant(Enchantment.DAMAGE_ALL, 4 , true);
				lore.add("§6Позволяет выбить визер череп с шансом 30%");
				meta.setLore(lore);
				reaper.setItemMeta(meta);
				p.getInventory().addItem(reaper);
				
			}
			break;
			case("reaper*"):{
				ItemStack reaper = new ItemStack(Material.NETHERITE_HOE);
				ItemMeta meta = reaper.getItemMeta();
				meta.setDisplayName("§5§lЖнец");
				List<String> lore = new ArrayList<>();
				meta.addEnchant(Enchantment.DAMAGE_ALL, 127 , true);
				lore.add("§6Позволяет выбить визер череп с шансом 30%");
				meta.setLore(lore);
				reaper.setItemMeta(meta);
				p.getInventory().addItem(reaper);
				
			}
			break;
			case("chronos"):{
				ItemStack Chronos = new ItemStack(Material.CLOCK);
				ItemMeta metaChronos = Chronos.getItemMeta();
				metaChronos.setDisplayName("§6§lЧасы Хроноса");
				List<String> lore = new ArrayList<String>();
				lore.add("Смена дня и ночи");
				metaChronos.setLore(lore);
				metaChronos.addEnchant(Enchantment.ARROW_INFINITE, 1 , true);
				Chronos.setItemMeta(metaChronos);
				p.getInventory().addItem(Chronos);
			}
			break;
			case("cheapPickaxe"):{
				ItemStack item = new ItemStack(Material.GOLDEN_PICKAXE);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§5§lВоля §5ш§5§lахтера");
				List<String> lore = new ArrayList<String>();
				lore.add("Время наше все");
				meta.setLore(lore);
				meta.addEnchant(Enchantment.DIG_SPEED, 8 , true);
				meta.setUnbreakable(true);
				item.setItemMeta(meta);
				p.getInventory().addItem(item);
			}
			break;
			case("cheapPickaxe*"):{
				ItemStack item = new ItemStack(Material.GOLDEN_PICKAXE);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§5§lВоля §5ш§5§lахтера");
				List<String> lore = new ArrayList<String>();
				lore.add("Время наше все");
				meta.setLore(lore);
				meta.addEnchant(Enchantment.DIG_SPEED, 127 , true);
				meta.setUnbreakable(true);
				item.setItemMeta(meta);
				p.getInventory().addItem(item);
			}
			break;
			case("strongAxe"):{
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
				p.getInventory().addItem(item);
			}
			break;
			case("destroyer"):{
				ItemStack item = new ItemStack(Material.GOLDEN_SHOVEL);
		        ItemMeta meta = item.getItemMeta();
		        meta.setDisplayName("§5Разрушитель");
		        List<String> lore = new ArrayList<String>();
		        lore.add("Уничтожет любой блок");
		        lore.add("§eОсталось зарядов");
		        lore.add("0");
		        meta.setLore(lore);
		        meta.addEnchant(Enchantment.DURABILITY, 1 , true);
		        item.setItemMeta(meta);
				p.getInventory().addItem(item);
			}
			break;
			case("destroyer*"):{
				ItemStack item = new ItemStack(Material.GOLDEN_SHOVEL);
		        ItemMeta meta = item.getItemMeta();
		        meta.setDisplayName("§5Разрушитель");
		        List<String> lore = new ArrayList<String>();
		        lore.add("Уничтожет любой блок");
		        lore.add("§eОсталось зарядов");
		        lore.add("999999");
		        meta.setLore(lore);
		        meta.addEnchant(Enchantment.DURABILITY, 1 , true);
		        item.setItemMeta(meta);
				p.getInventory().addItem(item);
			}
			break;
			case("relocator"):{
				ItemStack relocator = new ItemStack(Material.NETHER_STAR);
				ItemMeta relocatormeta = relocator.getItemMeta();
				relocatormeta.setDisplayName("§5§lЭ.П.У.П.Т.");
				List<String> relocatorlore = new ArrayList<String>();
				relocatorlore.add("Экспериментальное портативное устройство");
				relocatorlore.add("пространственного туннелирования");
				relocatorlore.add("§eОсталось зарядов");
				relocatorlore.add("0");
				relocatorlore.add("Точка не выбрана");
				relocatormeta.setLore(relocatorlore);
				relocator.setItemMeta(relocatormeta);
				p.getInventory().addItem(relocator);
			}
			break;
			case("relocator*"):{
				ItemStack relocator = new ItemStack(Material.NETHER_STAR);
				ItemMeta relocatormeta = relocator.getItemMeta();
				relocatormeta.setDisplayName("§5§lЭ.П.У.П.Т.");
				List<String> relocatorlore = new ArrayList<String>();
				relocatorlore.add("Экспериментальное портативное устройство");
				relocatorlore.add("пространственного туннелирования");
				relocatorlore.add("§eОсталось зарядов");
				relocatorlore.add("9999999");
				relocatorlore.add("Точка не выбрана");
				relocatormeta.setLore(relocatorlore);
				relocator.setItemMeta(relocatormeta);
				p.getInventory().addItem(relocator);
			}
			break;
			default:{
				p.sendMessage("§c ethernalFeather soulFeather compass pickaxe reaper chronos cheapPickaxe strongAxe destroyer");
			}
		}
		return;
	}
}
