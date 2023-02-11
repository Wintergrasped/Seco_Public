package cc.koffeecreations.main.unknow;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import cc.koffeecreations.main.events.SyncPrices;

public class ISA {
	FileConfiguration config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();

	public int FV = config.getInt("ServerData.FarmingVolume"); // Value for tracking volume in Farming Industry
	public int LV = config.getInt("ServerData.LoggingVolume"); // Value for tracking volume in Logging Industry
	public int MV = config.getInt("ServerData.MiningVolume"); // Value for tracking volume in Mining Industry
	double miningPRC = config.getDouble("ServerData.MiningStockPrice");
	double loggingPRC = config.getDouble("ServerData.LoggingStockPrice");
	double farmingPRC = config.getDouble("ServerData.FarmingStockPrice");
	int count = 0;
	int updateCount = 40;

	Player p = null;
	Block b = null;
	
	Plugin pl = Bukkit.getPluginManager().getPlugin("STSEcon");

	public ISA(Player P, Block B) {

		// BlockBreak(P,B);

		Player player = p;
		b = B;
		p = P;
		double move = 0.001;
		double dec = move * 0.8;
		Material M = b.getType();
		Material Stone = Material.STONE;
		boolean debug = true;
		pl = Bukkit.getPluginManager().getPlugin("STSEcon");
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (M.equals(Material.STONE)) {

			MV = MV + 1;
			LV = LV - 1;
			count = count + 1;

			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;

			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();

			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);

			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		} else

		if (M.equals(Material.IRON_ORE)) {
			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC + 0.001;
			loggingPRC = loggingPRC - 0.001;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		} else

		if (M.equals(Material.COAL_ORE)) {
			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.GRANITE)) {
			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.DIORITE)) {
			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		} else

		if (M.equals(Material.DEEPSLATE)) {

			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		} else

		if (M.equals(Material.DEEPSLATE_DIAMOND_ORE)) {

			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			loggingPRC = loggingPRC - (dec * 2);
			miningPRC = miningPRC + (move * 2);
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		} else

		if (M.equals(Material.DEEPSLATE_EMERALD_ORE)) {

			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		} else

		if (M.equals(Material.DEEPSLATE_COPPER_ORE)) {

			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		} else

		if (M.equals(Material.DEEPSLATE_COAL_ORE)) {

			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		} else

		if (M.equals(Material.DEEPSLATE_GOLD_ORE)) {

			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		} else

		if (M.equals(Material.DEEPSLATE_IRON_ORE)) {

			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		} else

		if (M.equals(Material.DEEPSLATE_REDSTONE_ORE)) {

			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		} else

		if (M.equals(Material.DEEPSLATE_LAPIS_ORE)) {

			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		} else

		if (M.equals(Material.OAK_LOG)) {
			// refreshPrices();
			count = count + 1;
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}

			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		} else

		if (M.equals(Material.SPRUCE_LOG)) {
			// refreshPrices();
			count = count + 1;
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.DARK_OAK_LOG)) {
			count = count + 1;
			// refreshPrices();
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.BIRCH_LOG)) {
			count = count + 1;
			// refreshPrices();
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.JUNGLE_LOG)) {
			count = count + 1;
			// refreshPrices();
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);

			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.OAK_PLANKS)) {
			count = count + 1;
			// refreshPrices();
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.BIRCH_PLANKS)) {
			count = count + 1;
			// refreshPrices();
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		} else

		if (M.equals(Material.JUNGLE_PLANKS)) {
			count = count + 1;
			// refreshPrices();
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.WHEAT)) {
			count = count + 1;
			// refreshPrices();
			FV = FV + 1;
			MV = MV - 1;
			LV = LV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC - dec;
			farmingPRC = farmingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.MELON)) {
			count = count + 1;
			// refreshPrices();
			FV = FV + 1;
			MV = MV - 1;
			LV = LV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC - dec;
			farmingPRC = farmingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.SUGAR_CANE)) {
			// refreshPrices();
			count = count + 1;
			FV = FV + 1;
			MV = MV - 1;
			LV = LV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC - dec;
			farmingPRC = farmingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.SWEET_BERRY_BUSH)) {
			count = count + 1;
			// refreshPrices();
			FV = FV + 1;
			MV = MV - 1;
			LV = LV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC - dec;
			farmingPRC = farmingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.CARROT)) {
			count = count + 1;
			// refreshPrices();
			FV = FV + 1;
			MV = MV - 1;
			LV = LV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC - dec;
			farmingPRC = farmingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.POTATO)) {
			// refreshPrices();
			count = count + 1;
			FV = FV + 1;
			MV = MV - 1;
			LV = LV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC - dec;
			farmingPRC = farmingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			checkPrices();
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		}

	}

	public synchronized void BlockBreak(Player P, Block B) {
		Player player = p;
		double move = 0.001;
		double dec = move * 0.8;
		Material M = b.getType();
		Material Stone = Material.STONE;
		boolean debug = true;
		if (debug) {
			Player w = Bukkit.getPlayer("Wintergrasped");

			w.sendMessage("Material: " + M.name().toString());
		}
		if (M.equals(Material.DARK_PRISMARINE)) {

			MV = MV + 1;
			LV = LV - 1;
			count = count + 1;

			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;

			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);

			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);

			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		}else
		if (M.equals(Material.PRISMARINE_BRICKS)) {

			MV = MV + 1;
			LV = LV - 1;
			count = count + 1;

			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;

			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);

			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);

			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		}else
		if (M.equals(Material.PRISMARINE)) {

			MV = MV + 1;
			LV = LV - 1;
			count = count + 1;

			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;

			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);

			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);

			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		}else
		if (M.equals(Material.STONE)) {

			MV = MV + 1;
			LV = LV - 1;
			count = count + 1;

			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;

			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);

			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);

			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		} else

		if (M.equals(Material.IRON_ORE)) {
			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC + 0.001;
			loggingPRC = loggingPRC - 0.001;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		} else

		if (M.equals(Material.COAL_ORE)) {
			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.GRANITE)) {
			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.DIORITE)) {
			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		} else

		if (M.equals(Material.OAK_LOG)) {
			// refreshPrices();
			count = count + 1;
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}

			if (debug) {
				Player w = Bukkit.getPlayer("Wintergrasped");
				w.sendMessage("Oak_LOG");
				w.sendMessage("Old PRC:" + loggingPRC);
			}

			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);

			if (debug) {
				Player w = Bukkit.getPlayer("Wintergrasped");

				w.sendMessage("PRC:" + loggingPRC);
			}
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		} else

		if (M.equals(Material.SPRUCE_LOG)) {
			// refreshPrices();
			count = count + 1;
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.DARK_OAK_LOG)) {
			count = count + 1;
			// refreshPrices();
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.BIRCH_LOG)) {
			count = count + 1;
			// refreshPrices();
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.JUNGLE_LOG)) {
			count = count + 1;
			// refreshPrices();
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);

			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.OAK_PLANKS)) {
			count = count + 1;
			// refreshPrices();
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.BIRCH_PLANKS)) {
			count = count + 1;
			// refreshPrices();
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

		} else

		if (M.equals(Material.JUNGLE_PLANKS)) {
			count = count + 1;
			// refreshPrices();
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.WHEAT)) {
			count = count + 1;
			// refreshPrices();
			FV = FV + 1;
			MV = MV - 1;
			LV = LV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC - dec;
			farmingPRC = farmingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);

			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.MELON)) {
			count = count + 1;
			// refreshPrices();
			FV = FV + 1;
			MV = MV - 1;
			LV = LV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC - dec;
			farmingPRC = farmingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.SUGAR_CANE)) {
			// refreshPrices();
			count = count + 1;
			FV = FV + 1;
			MV = MV - 1;
			LV = LV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC - dec;
			farmingPRC = farmingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.SWEET_BERRY_BUSH)) {
			count = count + 1;
			// refreshPrices();
			FV = FV + 1;
			MV = MV - 1;
			LV = LV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC - dec;
			farmingPRC = farmingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.CARROT)) {
			count = count + 1;
			// refreshPrices();
			FV = FV + 1;
			MV = MV - 1;
			LV = LV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC - dec;
			farmingPRC = farmingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		} else

		if (M.equals(Material.POTATO)) {
			// refreshPrices();
			count = count + 1;
			FV = FV + 1;
			MV = MV - 1;
			LV = LV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC - dec;
			farmingPRC = farmingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
		}

	}

	public synchronized void BlockPlace(Player P, Block B) {
		Player player = p;
		double move = 0.001;
		double dec = move * 0.8;
		String M = b.getType().name().toString().toUpperCase();
		Material Stone = Material.STONE;

		switch (M) {

		case "STONE":

			MV = MV + 1;
			LV = LV - 1;
			count = count + 1;

			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;

			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);

			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

			break;

		case "IRON_ORE":
			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC + 0.001;
			loggingPRC = loggingPRC - 0.001;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

			break;

		case "COAL_ORE":
			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
			break;

		case "GRANITE":
			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
			break;

		case "DIORITE":
			// refreshPrices();
			count = count + 1;
			MV = MV + 1;
			LV = LV - 1;
			if (LV <= 0) {
				LV = 1;
			}
			loggingPRC = loggingPRC - dec;
			miningPRC = miningPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

			break;

		case "OAK_LOG":
			// refreshPrices();
			count = count + 1;
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

			break;

		case "SPRUCE_LOG":
			// refreshPrices();
			count = count + 1;
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
			break;

		case "DARK_OAK_LOG":
			count = count + 1;
			// refreshPrices();
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
			break;

		case "BIRTCH_LOG":
			count = count + 1;
			// refreshPrices();
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
			break;

		case "JUNGLE_LOG":
			count = count + 1;
			// refreshPrices();
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);

			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
			break;

		case "OAK_PLANKS":
			count = count + 1;
			// refreshPrices();
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
			break;

		case "BIRTCH_PLANKS":
			count = count + 1;
			// refreshPrices();
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();

			break;

		case "JUNLGE_PLANKS":
			count = count + 1;
			// refreshPrices();
			LV = LV + 1;
			MV = MV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
			break;

		case "WHEAT":
			count = count + 1;
			// refreshPrices();
			FV = FV + 1;
			MV = MV - 1;
			LV = LV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC - dec;
			farmingPRC = farmingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
			break;

		case "MELON":
			count = count + 1;
			// refreshPrices();
			FV = FV + 1;
			MV = MV - 1;
			LV = LV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC - dec;
			farmingPRC = farmingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
			break;

		case "SUGAR_CANE":
			// refreshPrices();
			count = count + 1;
			FV = FV + 1;
			MV = MV - 1;
			LV = LV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC - dec;
			farmingPRC = farmingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
			break;

		case "SWEET_BERRY_BUSH":
			count = count + 1;
			// refreshPrices();
			FV = FV + 1;
			MV = MV - 1;
			LV = LV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC - dec;
			farmingPRC = farmingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
			break;

		case "CARROT":
			count = count + 1;
			// refreshPrices();
			FV = FV + 1;
			MV = MV - 1;
			LV = LV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC - dec;
			farmingPRC = farmingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			// UpdatePrices();
			break;

		case "POTATO":
			// refreshPrices();
			count = count + 1;
			FV = FV + 1;
			MV = MV - 1;
			LV = LV - 1;
			if (MV <= 0) {
				MV = 1;
			}
			if (LV <= 0) {
				LV = 1;
			}
			miningPRC = miningPRC - dec;
			loggingPRC = loggingPRC - dec;
			farmingPRC = farmingPRC + move;
			config.set("ServerData.MiningStockPrice", miningPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.FarmingStockPrice", farmingPRC);
			// config.set("ServerData.LoggingVolume",LV);
			// config.set("ServerData.MiningVolume",MV);
			// config.set("ServerData.FarmingVolume",FV);
			// updateConfig(count);
			saveConfig();
			//// UpdatePrices();

			break;
		}

	}

	public synchronized void refreshPrices() {
		miningPRC = config.getDouble("ServerData.MiningStockPrice");
		loggingPRC = config.getDouble("ServerData.LoggingStockPrice");
		farmingPRC = config.getDouble("ServerData.FarmingStockPrice");
	}

	public synchronized void updateConfig(int i) {

		if (i >= 50) {

			config.set("ServerData.FarmingStockPrice", farmingPRC);
			config.set("ServerData.LoggingStockPrice", loggingPRC);
			config.set("ServerData.MiningStockPrice", miningPRC);
			count = 0;
		}

	}

	public void saveConfig() {

		if (updateCount > 0) {
			updateCount = updateCount - 1;
		} else {

			SyncPrices event = new SyncPrices(miningPRC, loggingPRC, farmingPRC);

			Bukkit.getServer().getPluginManager().callEvent(event);
			updateCount = 40;
			Bukkit.getLogger().log(Level.INFO, "SyncPrice Event");
		}

		try {
			Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
	    } catch (Exception ex) {
	    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + ex.getMessage());
	    }

	}

	public synchronized void checkPrices() {

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
		saveConfig();
	}
}
