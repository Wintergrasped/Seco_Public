package cc.koffeecreations.main.unknow;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class UpdatePrices {

	FileConfiguration config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
	double miningPRC = 0;
	double loggingPRC = 0;
	double farmingPRC = 0;
	double redstonePRC = 0;
	double lumberPRC = 0;
	
	public UpdatePrices(double miningp, double loggingp, double farmingp) {
		/*miningPRC = miningp;
		loggingPRC = loggingp;
		farmingPRC = farmingp;
		if (loggingPRC <= 0.05) {
			loggingPRC = 0.06;
		}
		
		if (miningPRC <= 0.05) {
			miningPRC = 0.06;
		}
		
		
		if (farmingPRC <= 0.05) {
			farmingPRC = 0.06;
		}
		
		
		config.set("ServerData.FarmingStockPrice", farmingPRC);
		config.set("ServerData.LoggingStockPrice", loggingPRC);
		config.set("ServerData.MiningStockPrice", miningPRC);*/
	}
	
}
