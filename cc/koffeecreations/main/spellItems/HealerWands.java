package cc.koffeecreations.main.spellItems;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HealerWands {




	public static ItemStack getRejuv() {
	
		ItemStack wand = new ItemStack(Material.BONE, 1);
		ItemMeta wm = wand.getItemMeta();
		List<String> lore = new ArrayList();
		
		wm.setDisplayName("Rejuvinate");
		lore.add("Used by a healer to heal near by players");
		lore.add("Rejuve 1");
		wm.setLore(lore);
		wand.setItemMeta(wm);
		
		return wand;
	}
	
	public static ItemStack getMassHeal() {
		
		ItemStack wand = new ItemStack(Material.BONE, 1);
		ItemMeta wm = wand.getItemMeta();
		List<String> lore = new ArrayList();
		
		wm.setDisplayName("Mass Heal");
		lore.add("Used by a healer to heal near by players");
		lore.add("Mass Heal 1");
		wm.setLore(lore);
		wand.setItemMeta(wm);
		
		return wand;
	}
	
	public static ItemStack getProtWand() {
		
		ItemStack wand = new ItemStack(Material.BONE, 1);
		ItemMeta wm = wand.getItemMeta();
		List<String> lore = new ArrayList();
		
		wm.setDisplayName("Mass Protection");
		lore.add("Used by a healer to increase Armor of Near by Players");
		lore.add("Prot Wand 1");
		wm.setLore(lore);
		wand.setItemMeta(wm);
		
		return wand;
	}
}