package cc.koffeecreations.main.crafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import cc.koffeecreations.main.spellItems.HealerWands;
import cc.koffeecreations.main.spellItems.TankShields;

public class Wands {

public Wands() {
	
	
	rejuve();
	mass();
	prot();
	prots();
	Regens();
	str();

}

public void eBook() {
	// obtain a reference to your plugin
		Plugin plugin = Bukkit.getPluginManager().getPlugin("STSEcon");

		// create the item stack for the result of the recipe
		ItemStack result = new ItemStack(TankShields.getStr());

		// create the recipe
		ShapedRecipe recipe = new ShapedRecipe(result);

		// set the shape of the recipe
		recipe.shape("   ", " AB", "   ");

		// set the ingredients
		recipe.setIngredient('A', Material.DIAMOND_SWORD);
		recipe.setIngredient('B', Material.ENCHANTED_BOOK);
		
		
		// add the recipe to the server
		plugin.getServer().addRecipe(recipe);
}

public void str() {
	// obtain a reference to your plugin
		Plugin plugin = Bukkit.getPluginManager().getPlugin("STSEcon");

		// create the item stack for the result of the recipe
		ItemStack result = new ItemStack(TankShields.getStr());

		// create the recipe
		ShapedRecipe recipe = new ShapedRecipe(result);

		// set the shape of the recipe
		recipe.shape("CAC", "CBC", "CCC");

		// set the ingredients
		recipe.setIngredient('C', Material.GOLDEN_APPLE);
		recipe.setIngredient('A', Material.COOKED_BEEF);
		recipe.setIngredient('B', Material.SHIELD);

		// add the recipe to the server
		plugin.getServer().addRecipe(recipe);
}

public void Regens() {
	// obtain a reference to your plugin
		Plugin plugin = Bukkit.getPluginManager().getPlugin("STSEcon");

		// create the item stack for the result of the recipe
		ItemStack result = new ItemStack((TankShields.getRegen()));

		// create the recipe
		ShapedRecipe recipe = new ShapedRecipe(result);

		// set the shape of the recipe
		recipe.shape("CAC", "CBC", "CCC");

		// set the ingredients
		recipe.setIngredient('C', Material.IRON_INGOT);
		recipe.setIngredient('A', Material.CAKE);
		recipe.setIngredient('B', Material.SHIELD);

		// add the recipe to the server
		plugin.getServer().addRecipe(recipe);
}

public void prots() {
	// obtain a reference to your plugin
		Plugin plugin = Bukkit.getPluginManager().getPlugin("STSEcon");

		// create the item stack for the result of the recipe
		ItemStack result = new ItemStack((TankShields.getProt()));

		// create the recipe
		ShapedRecipe recipe = new ShapedRecipe(result);

		// set the shape of the recipe
		recipe.shape("CAC", "CBC", "CCC");

		// set the ingredients
		recipe.setIngredient('C', Material.IRON_INGOT);
		recipe.setIngredient('A', Material.CARROT);
		recipe.setIngredient('B', Material.SHIELD);

		// add the recipe to the server
		plugin.getServer().addRecipe(recipe);
}


public void prot() {
	// obtain a reference to your plugin
		Plugin plugin = Bukkit.getPluginManager().getPlugin("STSEcon");

		// create the item stack for the result of the recipe
		ItemStack result = new ItemStack(HealerWands.getProtWand());

		// create the recipe
		ShapedRecipe recipe = new ShapedRecipe(result);

		// set the shape of the recipe
		recipe.shape(" C ", " A ", " B ");

		// set the ingredients
		recipe.setIngredient('C', Material.IRON_INGOT);
		recipe.setIngredient('A', Material.SHIELD);
		recipe.setIngredient('B', Material.BONE);

		// add the recipe to the server
		plugin.getServer().addRecipe(recipe);
}

public void mass() {
	// obtain a reference to your plugin
		Plugin plugin = Bukkit.getPluginManager().getPlugin("STSEcon");

		// create the item stack for the result of the recipe
		ItemStack result = new ItemStack(HealerWands.getMassHeal());

		// create the recipe
		ShapedRecipe recipe = new ShapedRecipe(result);

		// set the shape of the recipe
		recipe.shape(" C ", " A ", " B ");

		// set the ingredients
		recipe.setIngredient('C', Material.CAKE);
		recipe.setIngredient('A', Material.AMETHYST_SHARD);
		recipe.setIngredient('B', Material.BONE);

		// add the recipe to the server
		plugin.getServer().addRecipe(recipe);
}

public void rejuve() {
	// obtain a reference to your plugin
		Plugin plugin = Bukkit.getPluginManager().getPlugin("STSEcon");

		// create the item stack for the result of the recipe
		ItemStack result = new ItemStack(HealerWands.getRejuv());

		// create the recipe
		ShapedRecipe recipe = new ShapedRecipe(result);

		// set the shape of the recipe
		recipe.shape(" C ", " A ", " B ");

		// set the ingredients
		recipe.setIngredient('C', Material.GOLDEN_CARROT);
		recipe.setIngredient('A', Material.AMETHYST_SHARD);
		recipe.setIngredient('B', Material.BONE);

		// add the recipe to the server
		plugin.getServer().addRecipe(recipe);
}
	
}
