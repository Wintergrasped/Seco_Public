package cc.koffeecreations.main.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

	
	public void onDetah (PlayerDeathEvent e) {
	
		
		FileConfiguration config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		
		Location LD = e.getEntity().getLocation();
		
		
		
		config.set("PlayerData."+e.getEntity().getName()+".LastDeath.World", LD.getWorld().getName().toString());
		config.set("PlayerData."+e.getEntity().getName()+".LastDeath.X", LD.getBlockX());
		config.set("PlayerData."+e.getEntity().getName()+".LastDeath.Y", LD.getBlockY());
		config.set("PlayerData."+e.getEntity().getName()+".LastDeath.Z", LD.getBlockZ());
		Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
		
		
	}
	
	
}
