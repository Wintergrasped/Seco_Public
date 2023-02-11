package cc.koffeecreations.main.Items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class InfusedArmor {

	
	public static ItemStack StrNHelm() {
		
		ItemStack IS = new ItemStack(Material.NETHERITE_HELMET);
		ItemMeta IM = IS.getItemMeta();
		
		List<String> lore = new ArrayList();
		IM.setDisplayName("Strength Infused Netherite Helmet");
		lore.add("Infused with Pure Strength");
		lore.add("Str Infusion 1");
		IM.setLore(lore);
		IM.setUnbreakable(true);
		IS.setItemMeta(IM);
		
		
		return IS;
	}
	
	public static ItemStack StrNChest() {
		
		ItemStack IS = new ItemStack(Material.NETHERITE_CHESTPLATE);
		ItemMeta IM = IS.getItemMeta();
		
		List<String> lore = new ArrayList();
		IM.setDisplayName("Strength Infused Netherite Chestplate");
		lore.add("Infused with Pure Strength");
		lore.add("Str Infusion 1");
		IM.setLore(lore);
		IM.setUnbreakable(true);
		IS.setItemMeta(IM);
		
		
		return IS;
	}
	
	public static ItemStack StrNPants() {
		
		ItemStack IS = new ItemStack(Material.NETHERITE_LEGGINGS);
		ItemMeta IM = IS.getItemMeta();
		
		List<String> lore = new ArrayList();
		IM.setDisplayName("Strength Infused Netherite Pants");
		lore.add("Infused with Pure Strength");
		lore.add("Str Infusion 1");
		IM.setLore(lore);
		IM.setUnbreakable(true);
		IS.setItemMeta(IM);
		
		
		return IS;
	}
	
	public static ItemStack StrNBoots() {
		
		ItemStack IS = new ItemStack(Material.NETHERITE_BOOTS);
		ItemMeta IM = IS.getItemMeta();
		
		List<String> lore = new ArrayList();
		IM.setDisplayName("Strength Infused Netherite Boots");
		lore.add("Infused with Pure Strength");
		lore.add("Str Infusion 1");
		IM.setLore(lore);
		IM.setUnbreakable(true);
		IS.setItemMeta(IM);
		
		
		return IS;
	}
	
	//REGEN TANK SET
	
	public static ItemStack RegenNHelm() {
		
		ItemStack IS = new ItemStack(Material.NETHERITE_HELMET);
		ItemMeta IM = IS.getItemMeta();
		
		List<String> lore = new ArrayList();
		IM.setDisplayName("Regen Infused Netherite Helmet");
		lore.add("Infused with Pure Regen");
		lore.add("Regen Infusion 1");
		IM.setLore(lore);
		IM.setUnbreakable(true);
		IS.setItemMeta(IM);
		
		
		return IS;
	}
	
	public static ItemStack RegenNChest() {
		
		ItemStack IS = new ItemStack(Material.NETHERITE_CHESTPLATE);
		ItemMeta IM = IS.getItemMeta();
		
		List<String> lore = new ArrayList();
		IM.setDisplayName("Regen Infused Netherite Chestplate");
		lore.add("Infused with Pure Regen");
		lore.add("Regen Infusion 1");
		IM.setLore(lore);
		IM.setUnbreakable(true);
		IS.setItemMeta(IM);
		
		
		return IS;
	}
	
	public static ItemStack RegenNPants() {
		
		ItemStack IS = new ItemStack(Material.NETHERITE_LEGGINGS);
		ItemMeta IM = IS.getItemMeta();
		
		List<String> lore = new ArrayList();
		IM.setDisplayName("Regen Infused Netherite Pants");
		lore.add("Infused with Pure Regen");
		lore.add("Regen Infusion 1");
		IM.setLore(lore);
		IM.setUnbreakable(true);
		IS.setItemMeta(IM);
		
		
		return IS;
	}
	
	public static ItemStack RegenNBoots() {
		
		ItemStack IS = new ItemStack(Material.NETHERITE_BOOTS);
		ItemMeta IM = IS.getItemMeta();
		
		List<String> lore = new ArrayList();
		IM.setDisplayName("Regen Infused Netherite Boots");
		lore.add("Infused with Pure Regen");
		lore.add("Regen Infusion 1");
		IM.setLore(lore);
		IM.setUnbreakable(true);
		IS.setItemMeta(IM);
		
		
		return IS;
	}
	
	
}
