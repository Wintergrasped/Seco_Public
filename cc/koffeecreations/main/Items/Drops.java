package cc.koffeecreations.main.Items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Drops {

	
	
	
	
	
	public static ItemStack StrInfusion() {
		
		ItemStack IS = new ItemStack(Material.PAPER);
		ItemMeta IM = IS.getItemMeta();
		
		List<String> lore = new ArrayList();
		IM.setDisplayName("Infusion Script");
		lore.add("Strength Infusion Script");
		IM.setLore(lore);
		
		
		return IS;
	}
	
	
}
