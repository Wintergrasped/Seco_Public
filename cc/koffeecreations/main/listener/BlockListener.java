package cc.koffeecreations.main.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import cc.koffeecreations.main.unknow.ISA;
import cc.koffeecreations.main.property.Property;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public final class BlockListener implements Listener {

	FileConfiguration config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
	
	//config.set("ServerData.LoggingVolume",LV);

	private List<Property> prys = new ArrayList();
	
	public int FV = config.getInt("ServerData.FarmingVolume"); //Value for tracking volume in Farming Industry
	public int LV = config.getInt("ServerData.LoggingVolume"); //Value for tracking volume in Logging Industry
	public int MV = config.getInt("ServerData.MiningVolume"); //Value for tracking volume in Mining Industry
	double miningPRC = config.getDouble("ServerData.MiningStockPrice");
	double loggingPRC = config.getDouble("ServerData.LoggingStockPrice");
	double farmingPRC = config.getDouble("ServerData.FarmingStockPrice");
	int count = 0;
	
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {

		Player player = event.getPlayer();
		double move = 0.001;
		double dec = move*0.8;
		String M = event.getBlock().getType().name();
		Material Stone = Material.STONE;

			BlockBreak(event.getPlayer(), event.getBlock());
			


	}


	public void BlockBreak(Player P, Block B) {
		new ISA(P, B);
	}
	
	public boolean isBusiness(String n) {

		List<String> bso = config.getStringList("PlayerData.BusinessOwners");
		if (bso.contains(n)) {
			return true;
		} else {
			return false;
		}

	}

	public void saveConfig() {

		try {
			Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
	    } catch (Exception e) {
	    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    }

	}
	
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		ISA I = new ISA(event.getPlayer(), event.getBlock());
		
	}

	
public void updateConfig(int i) {
	
	if (i >= 50) {
		
		config.set("ServerData.FarmingStockPrice", farmingPRC);
		config.set("ServerData.LoggingStockPrice", loggingPRC);
		config.set("ServerData.MiningStockPrice", miningPRC);
		count = 0;
	}
	
}

public void UpdatePrices() {
	
	if (FV > MV) {
		
		double mp = config.getDouble("ServerData.MiningStockPrice");
		double fp = config.getDouble("ServerData.FarmingStockPrice");
		
		//mp = mp+((FV-MV)/2);
		
		
		//config.set("ServerData.MiningStockPrice", mp);
	}
	if (MV > FV) {
		double mp = config.getDouble("ServerData.MiningStockPrice");
		double fp = config.getDouble("ServerData.FarmingStockPrice");
		
		//fp = fp+((MV-FV)/2);
		
		
		//config.set("ServerData.FarmingStockPrice", fp);
	}
	if (LV > MV) {
		double mp = config.getDouble("ServerData.MiningStockPrice");
		double lp = config.getDouble("ServerData.LoggingStockPrice");
		
		//mp = mp+((LV-MV)/2);
		
		
		//config.set("ServerData.MiningStockPrice", mp);
	}
	if (MV > LV) {
		double mp = config.getDouble("ServerData.MiningStockPrice");
		double lp = config.getDouble("ServerData.LoggingStockPrice");
		
		//lp = lp+((MV-LV)/2);
		
		
		//config.set("ServerData.MiningStockPrice", lp);
	}
	saveConfig();
	
}


public void loadPrys() {
	config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
	prys = new ArrayList();
	for (String PR : config.getStringList("ServerData.Propertys")) {
		
		String NM = config.getString("PropertyData."+PR+".Name");
		String ON = config.getString("PropertyData."+PR+".Owner");
		int MNX = config.getInt("PropertyData."+PR+".MinX");
		int MNY = config.getInt("PropertyData."+PR+".MinY");
		int MNZ = config.getInt("PropertyData."+PR+".MinZ");
		
		int MXX = config.getInt("PropertyData."+PR+".MaxX");
		int MXY = config.getInt("PropertyData."+PR+".MaxY");
		int MXZ = config.getInt("PropertyData."+PR+".MaxZ");
		
		String WW = config.getString("PropertyData."+PR+".World");
		
		Location L1 = new Location(Bukkit.getWorld(WW), MNX, MNY, MNZ);
		Location L2 = new Location(Bukkit.getWorld(WW), MXX, MXY, MXZ);
		
		Property PRR = new Property(NM, ON, L1, L2);
		
		if (config.getBoolean("PropertyData."+PR+".ForSale")) {
			PRR.setPrice(config.getInt("PropertyData."+PR+".Price"));
			PRR.SetSaleType(config.getString("PropertyData."+PR+".SaleType"));
			PRR.setForSale(true);
		}
		
		prys.add(PRR);
}

}

public void updateData() {
	
	config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
	for (Property PR : prys) {
		
		config.set("PropertyData."+PR.getName()+".Name", PR.getName());
		config.set("PropertyData."+PR.getName()+".Owner", PR.getOwner());
		config.set("PropertyData."+PR.getName()+".MinX", PR.getMinLoc().getX());
		config.set("PropertyData."+PR.getName()+".MinY", PR.getMinLoc().getY());
		config.set("PropertyData."+PR.getName()+".MinZ", PR.getMinLoc().getZ());
		
		config.set("PropertyData."+PR.getName()+".MaxX", PR.getMaxLoc().getX());
		config.set("PropertyData."+PR.getName()+".MaxY", PR.getMaxLoc().getY());
		config.set("PropertyData."+PR.getName()+".MaxZ", PR.getMaxLoc().getZ());
		
		config.set("PropertyData."+PR.getName()+".World", PR.getMinLoc().getWorld().getName());
		
		config.set("PropertyData."+PR.getName()+".Trusted", PR.getTrusted());
		config.set("PropertyData."+PR.getName()+".Renter", PR.getRenter());


		
		if (config.getBoolean("PropertyData."+PR.getName()+".ForSale")) {
			config.getInt("PropertyData."+PR.getName()+".Price");
			config.getString("PropertyData."+PR.getName()+".SaleType");
		}
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		

}

}

public void refreshPrices() {
	miningPRC = config.getDouble("ServerData.MiningStockPrice");
	loggingPRC = config.getDouble("ServerData.LoggingStockPrice");
	farmingPRC = config.getDouble("ServerData.FarmingStockPrice");
}
}
