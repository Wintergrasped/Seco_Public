package cc.koffeecreations.main.Items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InfusedWeapons {

	
public static ItemStack StrNSword() {
		
		ItemStack IS = new ItemStack(Material.NETHERITE_SWORD);
		ItemMeta IM = IS.getItemMeta();
		
		List<String> lore = new ArrayList();
		IM.setDisplayName("Strength Infused Netherite Sword");
		lore.add("Infused with Pure Strength");
		lore.add("Str Infusion 1");
		IM.setLore(lore);
		IM.setUnbreakable(true);
		IS.setItemMeta(IM);
		
		
		return IS;
	}

public static ItemStack RegenNSword() {
	
	ItemStack IS = new ItemStack(Material.NETHERITE_SWORD);
	ItemMeta IM = IS.getItemMeta();
	
	List<String> lore = new ArrayList();
	IM.setDisplayName("Strength Infused Netherite Sword");
	lore.add("Infused with Pure Regen");
	lore.add("Regen Infusion 1");
	IM.setLore(lore);
	IM.setUnbreakable(true);
	IS.setItemMeta(IM);
	
	
	return IS;
}

public static ItemStack MightNSword() {
	
	ItemStack IS = new ItemStack(Material.NETHERITE_SWORD);
	ItemMeta IM = IS.getItemMeta();
	
	List<String> lore = new ArrayList();
	IM.setDisplayName("Strength Infused Netherite Sword");
	lore.add("Infused with Pure Might");
	lore.add("Might Infusion 1");
	IM.setLore(lore);
	IM.setUnbreakable(true);
	IS.setItemMeta(IM);
	
	
	return IS;
}

}
