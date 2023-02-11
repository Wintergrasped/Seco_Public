package cc.koffeecreations.main.crafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import cc.koffeecreations.main.Items.Drops;
import cc.koffeecreations.main.spellItems.TankShields;

public class Infusion {

	
	public Infusion() {
		tankNetherChest();
	}
	
	
	public void tankNetherChest() {
		// obtain a reference to your plugin
			Plugin plugin = Bukkit.getPluginManager().getPlugin("STSEcon");

			// create the item stack for the result of the recipe
			ItemStack result = new ItemStack(TankShields.getStr());

			// create the recipe
			ShapedRecipe recipe = new ShapedRecipe(result);

			// set the shape of the recipe
			recipe.shape("CAC", "CBC", "CCC");

			
			
			// set the ingredients
			recipe.setIngredient('C', Material.DIAMOND);
			recipe.setIngredient('A', Material.ENCHANTED_GOLDEN_APPLE);
			recipe.setIngredient('B', Material.NETHERITE_CHESTPLATE);

			// add the recipe to the server
			plugin.getServer().addRecipe(recipe);
	}
	
	
}
