package cc.koffeecreations.main.crafting;

import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class CraftListener implements Listener {

	public boolean debug = true;
	
	@EventHandler
	public void onInventory(InventoryEvent e) {

		if (e.getInventory().getType().equals(InventoryType.ANVIL)) {
			if (debug) {
				Bukkit.getLogger().log(Level.INFO, "Destination IS Anvil");
			}
			ItemStack[] sl = e.getInventory().getStorageContents();
			ItemStack sl1 = sl[1];
			
			if (sl[0].getType().equals(Material.DIAMOND_SWORD)) {
				if (debug) {
					Bukkit.getLogger().log(Level.INFO, "SL0 IS Diamond Sword");
				}
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "SL1 IS Book");
					}
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}else if (sl[0].getType().equals(Material.NETHERITE_SWORD)) {
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}else if (sl[0].getType().equals(Material.IRON_SWORD)) {
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}else if (sl[0].getType().equals(Material.GOLDEN_SWORD)) {
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}else if (sl[0].getType().equals(Material.STONE_SWORD)) {
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			if (sl[0].getType().equals(Material.DIAMOND_HELMET)) {
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}else if (sl[0].getType().equals(Material.NETHERITE_HELMET)) {
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}else if (sl[0].getType().equals(Material.IRON_HELMET)) {
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}else if (sl[0].getType().equals(Material.GOLDEN_HELMET)) {
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}
			
			
			
			if (sl[0].getType().equals(Material.DIAMOND_CHESTPLATE)) {
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}else if (sl[0].getType().equals(Material.NETHERITE_CHESTPLATE)) {
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}else if (sl[0].getType().equals(Material.IRON_CHESTPLATE)) {
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}else if (sl[0].getType().equals(Material.GOLDEN_CHESTPLATE)) {
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}
			
			
			
			
			
			
			if (sl[0].getType().equals(Material.DIAMOND_LEGGINGS)) {
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}else if (sl[0].getType().equals(Material.NETHERITE_LEGGINGS)) {
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}else if (sl[0].getType().equals(Material.IRON_LEGGINGS)) {
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}else if (sl[0].getType().equals(Material.GOLDEN_LEGGINGS)) {
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}
			
			
			
			
			
			
			
			if (sl[0].getType().equals(Material.DIAMOND_BOOTS)) {
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}else if (sl[0].getType().equals(Material.NETHERITE_BOOTS)) {
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}else if (sl[0].getType().equals(Material.IRON_BOOTS)) {
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}else if (sl[0].getType().equals(Material.GOLDEN_BOOTS)) {
				if (sl[1].getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(sl1.hasItemMeta() && sl1.getItemMeta().hasEnchants()){
						int el = 1;
					    Map<Enchantment, Integer> enchantments = sl1.getEnchantments();
					    for (Enchantment enchantment : enchantments.keySet()) {
					        int level = enchantments.get(enchantment);
					        //setHeadEnchant(Name, el, enchantment.getName(), level);
					        sl[0].addUnsafeEnchantment(enchantment, level);
					        sl1.setAmount(0);
					    }
					    
					}
					
				}
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		}else {
			return;
			}
		
	}
	
}
