package CustomCrafts;

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
			
			ItemStack ethernalFeather = EthernalFeatherItem.getItem();
			
			ItemStack soulFeather = SoulFeatherItem.getItem();
			
			ItemStack compass = CompassItem.getItem();
			
			ItemStack notchedPickaxe = NotchedPickaxeItem.getItem();
			
			ItemStack reaper = ReaperItem.getItem();
			
			ItemStack chronos = ChronosItem.getItem();
			
			ItemStack cheapPickaxe = CheapPickaxeItem.getItem();
			
			ItemStack strongAxe = StrongAxeItem.getItem();
	        
	        ItemStack destroyer = DestroyerItem.getItem();
	        
	        ItemStack relocator = RelocatorItem.getItem();
	        
	        ItemStack totem = TotemItem.getItem();
			
			Inventory gui = Bukkit.createInventory(p, 18, "MegaOpIMBAHolyShitItems");
			ItemStack[] imbaItems = {ethernalFeather,soulFeather,compass,notchedPickaxe,reaper,chronos,totem,cheapPickaxe,strongAxe,destroyer,relocator};
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
				ItemStack ethernalFeather = EthernalFeatherItem.getItem();
				p.getInventory().addItem(ethernalFeather);
			}
			break;
			case("soulFeather"):{
				ItemStack soulFeather = SoulFeatherItem.getItem();
				p.getInventory().addItem(soulFeather);
			}
			break;
			
			case("compass"):{
				ItemStack compass = CompassItem.getItem();
				p.getInventory().addItem(compass);
			}
			break;
			case("compass*"):{
				ItemStack compass = CompassItem.getItem();
				ItemMeta meta = compass.getItemMeta();
				List<String> lore = meta.getLore();
				lore.set(4, "99999");
				meta.setLore(lore);
				compass.setItemMeta(meta);
				p.getInventory().addItem(compass);
			}
			break;
			case("pickaxe"):{
				ItemStack notchedPickaxe = NotchedPickaxeItem.getItem();
				p.getInventory().addItem(notchedPickaxe);
			}
			break;
			case("reaper"):{
				ItemStack reaper = ReaperItem.getItem();
				p.getInventory().addItem(reaper);
				
			}
			break;
			case("reaper*"):{
				ItemStack reaper = ReaperItem.getItem();
				reaper.setType(Material.NETHERITE_HOE);
				ItemMeta meta = reaper.getItemMeta();
				meta.addEnchant(Enchantment.DAMAGE_ALL, 32767 , true);
				reaper.setItemMeta(meta);
				p.getInventory().addItem(reaper);
				
			}
			break;
			case("chronos"):{
				ItemStack chronos = ChronosItem.getItem();
				p.getInventory().addItem(chronos);
			}
			break;
			case("chronos*"):{
				ItemStack chronos = ChronosItem.getItem();
				ItemMeta meta = chronos.getItemMeta();
				List<String> lore = meta.getLore();
				lore.add("§2§lБез побочек!");
				meta.setLore(lore);
				chronos.setItemMeta(meta);
				p.getInventory().addItem(chronos);
			}
			break;
			case("cheapPickaxe"):{
				ItemStack cheapPickaxe = CheapPickaxeItem.getItem();
				p.getInventory().addItem(cheapPickaxe);
			}
			break;
			case("cheapPickaxe*"):{
				ItemStack cheapPickaxe = CheapPickaxeItem.getItem();
				ItemMeta meta = cheapPickaxe.getItemMeta();
				meta.addEnchant(Enchantment.DIG_SPEED, 127 , true);
				cheapPickaxe.setItemMeta(meta);
				p.getInventory().addItem(cheapPickaxe);
			}
			break;
			case("strongAxe"):{
				ItemStack strongAxe = StrongAxeItem.getItem();
				p.getInventory().addItem(strongAxe);
			}
			break;
			case("destroyer"):{
				ItemStack destroyer = DestroyerItem.getItem();
				p.getInventory().addItem(destroyer);
			}
			break;
			case("destroyer*"):{
				ItemStack item = DestroyerItem.getItem();
		        ItemMeta meta = item.getItemMeta();
		        meta.setDisplayName("§5Разрушитель");
		        List<String> lore = meta.getLore();
		        lore.set(2, "999999");
		        meta.setLore(lore);
		        item.setItemMeta(meta);
				p.getInventory().addItem(item);
			}
			break;
			case("relocator"):{
				ItemStack relocator = RelocatorItem.getItem();
				p.getInventory().addItem(relocator);
			}
			break;
			case("relocator*"):{
				ItemStack relocator = RelocatorItem.getItem();
				ItemMeta relocatormeta = relocator.getItemMeta();
				relocatormeta.setDisplayName("§5§lЭ.П.У.П.Т.");
				List<String> relocatorlore = relocatormeta.getLore();
				relocatorlore.set(3,"9999999"); //3
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
