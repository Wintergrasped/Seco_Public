package cc.koffeecreations.main.spellItems;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TankShields {

	
	
	
	public static ItemStack getProt() {
		
		ItemStack wand = new ItemStack(Material.SHIELD, 1);
		ItemMeta wm = wand.getItemMeta();
		
		List<String> lore = new ArrayList();
		
		
		wm.setDisplayName("Protection Sheild 1");
		lore.add("Used by a Tank to gain Protection");
		lore.add("ProtShield 1");
		wm.setLore(lore);
		wand.setItemMeta(wm);
		
		return wand;
	}
	
	public static ItemStack getRegen() {
		
		ItemStack wand = new ItemStack(Material.SHIELD, 1);
		ItemMeta wm = wand.getItemMeta();
		List<String> lore = new ArrayList();
		
		wm.setDisplayName("Regen Sheild 1");
		lore.add("Used by a Tank to gain Protection");
		lore.add("RegenShield 1");
		wm.setLore(lore);
		wand.setItemMeta(wm);
		
		return wand;
	}
	
	public static ItemStack getStr() {
		
		ItemStack wand = new ItemStack(Material.SHIELD, 1);
		ItemMeta wm = wand.getItemMeta();
		List<String> lore = new ArrayList();
		
		wm.setDisplayName("Strength Sheild 1");
		lore.add("Used by a Tank to gain Protection");
		lore.add("StrengthShield 1");
		wm.setLore(lore);
		wand.setItemMeta(wm);
		
		return wand;
	}
	
}
