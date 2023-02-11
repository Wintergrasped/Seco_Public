package cc.koffeecreations.main.config;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ConfigManager {

	public void saveConf() {
		
		Plugin pl = Bukkit.getPluginManager().getPlugin("STSEcon");
		

			pl.saveConfig();
		
		
	}
	
	
}
