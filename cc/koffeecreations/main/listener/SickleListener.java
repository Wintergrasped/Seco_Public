package cc.koffeecreations.main.listener;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import cc.koffeecreations.main.Sickle.SickleEvent;

public class SickleListener implements Listener {

	
	
FileConfiguration config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
	
	public int FV = config.getInt("ServerData.FarmingVolume"); //Value for tracking volume in Farming Industry
	public int LV = config.getInt("ServerData.LoggingVolume"); //Value for tracking volume in Logging Industry
	public int MV = config.getInt("ServerData.MiningVolume"); //Value for tracking volume in Mining Industry
	double miningPRC = config.getDouble("ServerData.MiningStockPrice");
	double loggingPRC = config.getDouble("ServerData.LoggingStockPrice");
	double farmingPRC = config.getDouble("ServerData.FarmingStockPrice");
	
	
	@EventHandler
	public void onHarvest(SickleEvent E) {
		
		refreshPrices();
		farmingPRC = farmingPRC+0.001;
		loggingPRC = loggingPRC-0.008;
		miningPRC = miningPRC-0.008;
		
		config.set("ServerData.MiningStockPrice", miningPRC);
		config.set("ServerData.LoggingStockPrice", loggingPRC);
		config.set("ServerData.FarmingStockPrice", farmingPRC);
		Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
		checkPrices();
		
	}
	
	public void refreshPrices() {
		miningPRC = config.getDouble("ServerData.MiningStockPrice");
		loggingPRC = config.getDouble("ServerData.LoggingStockPrice");
		farmingPRC = config.getDouble("ServerData.FarmingStockPrice");
	}
	
	public void checkPrices() {
		
		miningPRC = config.getDouble("ServerData.MiningStockPrice");
		loggingPRC = config.getDouble("ServerData.LoggingStockPrice");
		farmingPRC = config.getDouble("ServerData.FarmingStockPrice");
		
		if (miningPRC <= 0.05) {
			miningPRC = 0.50;
		}
		
		if (loggingPRC <= 0.05) {
			loggingPRC = 0.50;
		}
		
		if (farmingPRC <= 0.05) {
			farmingPRC = 0.50;
		}
		
		config.set("ServerData.FarmingStockPrice", farmingPRC);
		config.set("ServerData.LoggingStockPrice", loggingPRC);
		config.set("ServerData.MiningStockPrice", miningPRC);
		Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
	}
	
}
