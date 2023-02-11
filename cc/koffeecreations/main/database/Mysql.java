package cc.koffeecreations.main.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import cc.koffeecreations.main.econ.Business;
import cc.koffeecreations.main.econ.Econ;
import cc.koffeecreations.main.econ.EconLog;
import cc.koffeecreations.main.playerdata.DataHandler;
import cc.koffeecreations.main.playerdata.PlayerInfo;
import cc.koffeecreations.main.property.Property;
import cc.koffeecreations.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;

public class Mysql {

	
	public Econ econ = null;
	public Main mc;
	Main main = null;
	String host = "";
	String database = "";
	String user = "";
	String pass = "";
	int port = 0;
	MySQLBase MySQL = null;
	Connection c = null;
	FileConfiguration config = null;
	public List<String> bso = new ArrayList();
	public List<Business> bsl = new ArrayList();
	public List<Property> prys = new ArrayList();
	
	Statement statement;

	
	public Mysql(Main m, String Host, int porta, String databasea, String usera, String passa){
		main = m;
		host = Host;
		port = porta;
		database = databasea;
		user = usera;
		pass = passa;
		MySQL = new MySQLBase(main, host, String.valueOf(port), database, user, pass);
		econ = new Econ();
		mc = m;
		config = m.getConfig();
		bso = config.getStringList("PlayerData.BusinessOwners");
	}
	
	public void openConnection(){
            c = MySQL.openConnection();

		
	}
	
	public void openBackupConnection() {
		
		MySQL = new MySQLBase(main, "127.0.0.1", "3306", "seco", "root", "");
		c = MySQL.openConnection();
		main.getLogger().log(Level.WARNING, "MySQL Conenction Failed Attempting Backup Connection");
	}
	
	public void closeConnection(){
		MySQL.closeConnection();
	}
	
	public double getTokens(String Player){
		
		try {
			Statement statement = c.createStatement();
		
		double money = 0;
		ResultSet res;
		
			res = statement.executeQuery("SELECT * FROM `NSHC_Points` WHERE  `name`='" + Player + "';");
		
		res.next();
		money = res.getDouble("points");
		return money;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
		
	}
	
	public boolean isRecruited(String Player){
		
		try {
			Statement statement = c.createStatement();
		
		boolean R = false;;
		ResultSet res;
		
			res = statement.executeQuery("SELECT * FROM `NSHC_Points` WHERE  `name`='" + Player + "';");
		
		res.next();
		if (res.getInt("recruited") == 1) {
			return true;
		}else {
			return false;
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
		
	}
	
	public void setTokens(String Player, double a){
		
		try {
			Statement statement = c.createStatement();
		
		
	
		
			statement.executeUpdate("UPDATE `" + this.database + "`.`NSHC_Points` SET `points`=" + a + " WHERE  `name`='" + Player + "';");
			
		
		
		
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void setRecruited(String Player, int a){
		
		try {
			Statement statement = c.createStatement();
		
		
	
		
			statement.executeUpdate("UPDATE `" + this.database + "`.`NSHC_Points` SET `recruited`=" + a + " WHERE  `name`='" + Player + "';");
			
		
		
		
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addGameTotal(){
		try {
			Statement statement = c.createStatement();
		
		int money = 0;
		ResultSet res;
		
			res = statement.executeQuery("SELECT * FROM `Game` WHERE  `Game`='Breacher';");
		
		res.next();
		money = res.getInt("TotalGames");
		money++;
		statement.executeUpdate("UPDATE `" + this.database + "`.`Game` SET `TotalGames`=" + money + " WHERE  `Game`='Breacher';");
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public String getGameTag(){
		try {
			Statement statement = c.createStatement();
		
		String money = "";
		ResultSet res;
		
			res = statement.executeQuery("SELECT * FROM `Game` WHERE  `Game`='Breacher';");
		
		res.next();
		money = res.getString("GameTag");
		return money;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
		
	}
	
	
   public void setupGameTable(String table){
		
		
		try {
			Statement statement = c.createStatement();
		
		
		
			
			statement.executeUpdate("CREATE TABLE " + table + " (`Game` TEXT,`GameTag` TEXT,`TotalGames` BIGINT,`MinPlayers` INT,`StartTimeSeconds` INT ,`GameTimeSeconds` INT, `SecureSeconds` INT)");

			statement.executeUpdate("INSERT INTO " + table + " (`Game`, `GameTag`, `TotalGames`, `MinPlayers`, `StartTimeSeconds`, `GameTimeSeconds`, `SecureSeconds`) VALUES ('Breacher', '&7[&6Breach&7]', '0', '5', '120', '600', '120');");
		
		
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
   
   public int getSecureSeconds(){
		try {
			Statement statement = c.createStatement();
		
		int money = 0;
		ResultSet res;
		
			res = statement.executeQuery("SELECT * FROM `Game` WHERE  `Game`='Breacher';");
		
		res.next();
		money = res.getInt("SecureSeconds");
		return money;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
   }
   
   public int getStartSeconds(){
	   try {
			Statement statement = c.createStatement();
		
		int money = 0;
		ResultSet res;
		
			res = statement.executeQuery("SELECT * FROM `Game` WHERE  `Game`='Breacher';");
		
		res.next();
		money = res.getInt("StartTimeSeconds");
		return money;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
   }
   
   public int getGameSeconds(){
	   try {
			Statement statement = c.createStatement();
		
		int money = 0;
		ResultSet res;
		
			res = statement.executeQuery("SELECT * FROM `Game` WHERE  `Game`='Breacher';");
		
		res.next();
		money = res.getInt("GameTimeSeconds");
		return money;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
   }
   
   public int getMinPlayers(){
	   try {
			Statement statement = c.createStatement();
		
		int money = 0;
		ResultSet res;
		
			res = statement.executeQuery("SELECT * FROM `Game` WHERE  `Game`='Breacher';");
		
		res.next();
		money = res.getInt("MinPlayers");
		return money;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
   }
   
   public int getTotalGames(){
		try {
			Statement statement = c.createStatement();
		
		int money = 0;
		ResultSet res;
		
			res = statement.executeQuery("SELECT * FROM `Game` WHERE  `Game`='Breacher';");
		
		res.next();
		money = res.getInt("TotalGames");
		return money;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
   }
	public boolean isSetup(String player){
	
			
			try {
				Statement statement = c.createStatement();
			
			
			ResultSet res;
		    	statement.executeUpdate("USE " + this.database); 
				res = statement.executeQuery("SELECT * FROM econ  WHERE Name='" + player + "';");
				
			
			
			
			return res.next();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		
		
	}
	
	public boolean isProfileSetup(String player){
		
		
		try {
			Statement statement = c.createStatement();
		
		
		ResultSet res;
	    	statement.executeUpdate("USE " + this.database); 
			res = statement.executeQuery("SELECT * FROM EconProfile  WHERE Name='" + player + "';");
			
		
		
		
		return res.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	
	
}
	
	public void setupPlayer(String player){
		try {
			if (c == null) {
				openBackupConnection();
			}
			Statement statement = c.createStatement();
		
		
		
			statement.executeUpdate("INSERT INTO NSHC_Points (`name`, `points`) VALUES ('" + player + "', '1');");
			
		
		
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setupTable(String table){
		
		
		try {
			if (c == null) {
				openBackupConnection();
			}
			Statement statement = c.createStatement();
		
		
		
			
			statement.executeUpdate("CREATE TABLE " + table + " (`PlayerName` TEXT,`Money` BIGINT )");
			
		
		
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public boolean tableExist(String tablename){

		try {
			if (c == null) {
				openBackupConnection();
			}
			Statement statement = c.createStatement();
		
		
		 
		
			ResultSet res = statement.executeQuery("SELECT count(*) FROM information_schema.TABLES WHERE (TABLE_SCHEMA = '" + this.database + "') AND (TABLE_NAME = '" + tablename + "');");
		
			DatabaseMetaData dbm = c.getMetaData();
			ResultSet tables = dbm.getTables(null, null, tablename, null);
		
		
			if(tables.next()){
				return true;
			}else{
				return false;
			}
			
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	
public void syncDB(){
	////LogTransaction();
	//loadProperty();
	openConnection();
	String ST = "USE `"+this.database+"`; ";
	

		FileConfiguration config = mc.getConfig();
		try {
			if (c == null) {
				openBackupConnection();
			}
			statement = c.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double PSP = mc.psp;
		double MP = config.getDouble("ServerData.MiningStockPrice");
		double LP = config.getDouble("ServerData.LoggingStockPrice");
		double FP = config.getDouble("ServerData.FarmingStockPrice");
		
		
			//statement.executeUpdate("UPDATE `" + this.database + "`.`markets` SET `FarmingStocks`=" + FP + ";");
			//statement.executeUpdate("UPDATE `" + this.database + "`.`markets` SET `LoggingStocks`=" + LP + ";");
			//statement.executeUpdate("UPDATE `" + this.database + "`.`markets` SET `MiningStocks`=" + MP + ";");
			//statement.executeUpdate("UPDATE `" + this.database + "`.`markets` SET `PlayerStocks`=" + PSP + ";");
			

	
	closeConnection();
	updateMarkets();
	
	updateEconLog();

	econ.clearLogs();	
	}

public int getStockShares(String P){
	openConnection();
	try {
		if (c == null) {
			openBackupConnection();
		}
		Statement statement = c.createStatement();
	
	int s = 0;
	ResultSet res;
	
		res = statement.executeQuery("SELECT * FROM `econ` WHERE  `Name`='"+P+"';");
	
	res.next();
	s = res.getInt("Stocks");
	return s;
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeConnection();
	return 0;
}

public double getStockPrice(){
	openConnection();
	try {
		if (c == null) {
			openBackupConnection();
		}
		Statement statement = c.createStatement();
	
	int s = 0;
	ResultSet res;
	
		res = statement.executeQuery("SELECT * FROM `econ`;");
	
	res.next();
	s = res.getInt("StocksPrice");
	return s;
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeConnection();
	return 0;
}

public void loadProperty() {
	FileConfiguration config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
	prys  = new ArrayList();
	
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
		
		if (WW == null) {
			WW = "RPWorld";
		}
		
		Location L1 = new Location(Bukkit.getWorld(WW), MNX, MNY, MNZ);
		Location L2 = new Location(Bukkit.getWorld(WW), MXX, MXY, MXZ);
		
		Property PRR = new Property(NM, ON, L1, L2);
		
		if (config.getBoolean("PropertyData."+PR+".ForSale")) {
			PRR.setPrice(config.getInt("PropertyData."+PR+".Price"));
			PRR.SetSaleType(config.getString("PropertyData."+PR+".SaleType"));
			PRR.setForSale(true);
		}
		
		if (prys.contains(PRR)) {
			
		}else {
			prys.add(PRR);
		}
		
	}
}

public double getPropertyValue(Property PR) {
	
	double value = 0;
	BlockVector3 MN = BlockVector3.at(PR.getMinLoc().getBlockX(), PR.getMinLoc().getBlockY(), PR.getMinLoc().getBlockZ());
	BlockVector3 MX = BlockVector3.at(PR.getMaxLoc().getBlockX(), PR.getMaxLoc().getBlockY(), PR.getMaxLoc().getBlockZ());
	Region R = new CuboidRegion(MN, MX);
	World W = PR.getMinLoc().getWorld();
	FileConfiguration config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		for (BlockVector3 point : R) {
			
			Location L = new Location(W, point.getBlockX(), point.getBlockY(), point.getBlockZ());
			Material M = L.getBlock().getType();
			
			if (config.getStringList("ServerData.PricedMaterials").contains(M.name())) {
				value = value+config.getDouble("ServerData.Materials.Value."+M.name());
			}else{
				
				if (M.equals(Material.AIR)) {
					
				}else{
					value = value+0.0001;
				}
				
			}
		}
	
		
		return value;
	}


public void asyncDB(){
	
	loadProperty();
	openConnection();
	String ST = "USE `"+this.database+"`; ";
	try {
		if (c == null) {
			openBackupConnection();
		}
		FileConfiguration config = mc.getConfig();
		Statement statement = c.createStatement();
		double PSP = mc.psp;
		double MP = config.getDouble("ServerData.MiningStockPrice");
		double LP = config.getDouble("ServerData.LoggingStockPrice");
		double FP = config.getDouble("ServerData.FarmingStockPrice");
		
		
			//statement.executeUpdate("UPDATE `" + this.database + "`.`markets` SET `FarmingStocks`=" + FP + ";");
			//statement.executeUpdate("UPDATE `" + this.database + "`.`markets` SET `LoggingStocks`=" + LP + ";");
			//statement.executeUpdate("UPDATE `" + this.database + "`.`markets` SET `MiningStocks`=" + MP + ";");
			//statement.executeUpdate("UPDATE `" + this.database + "`.`markets` SET `PlayerStocks`=" + PSP + ";");
		statement.addBatch("INSERT INTO `" + this.database + "`.`markets` (`PlayerStocks`, `FarmingStocks`, `MiningStocks`, `LoggingStocks`) VALUES ("+PSP+", "+FP+", "+MP+", "+LP+");");
		
		//statement.executeUpdate("UPDATE `" + this.database + "`.`NSHC_Points` SET `points`=" + a + " WHERE  `name`='" + Player + "';");

	
	
	for (Player P : Bukkit.getOnlinePlayers()) {
			
			
			String Name = P.getName();
			
			UUID ID = P.getUniqueId();
			double bal = econ.getBalance(P.getName());

			int Stocks = mc.getConfig().getInt("PlayerStocks.PlayerData."+P.getName()+".Shares");
			double MiningPrice = config.getDouble("ServerData.MiningStockPrice");
			double LoggingPrice = config.getDouble("ServerData.LoggingStockPrice");
			double FarmingPrice = config.getDouble("ServerData.FarmingStockPrice");
			List<Property> op = new ArrayList();
			double PV = 0.00;
			
			if (main.isBusiness(P.getName())) {
				//ALTER TABLE businessstocks ADD COLUMN IF NOT EXISTS bus3 VARCHAR(255);
				//statement.addBatch("ALTER TABLE `Business_Stocks` ADD COLUMN IF NOT EXISTS `"+P.getName()+"` INT(11);");
			}
			
			for (Property PR : prys) {
				
				if (PR.getOwner().equals(P.getName())) {
					op.add(PR);
					//PV = PV+getPropertyValue(PR);
				}
				
			}
			
			int SS = getStockShares(P.getName());
			double SP = getStockPrice();
			
			int MiningShares = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Mining");
			int LoggingShares = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Logging");
			int FarmingShares = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Farming");
			String UID = P.getUniqueId().toString();
			String Checking = UID.substring(0, 7);
			String Savings = UID.substring(24, 36);
			double CB = econ.getCheckingBalance(Checking);
			double SB = econ.getSavingsBalance(Savings);
			
			double SW = (Stocks*PSP)+(MiningShares*MiningPrice)+(FarmingShares*FarmingPrice)+(LoggingShares*LoggingPrice)+(SS*SP)+SB+CB;
			double nw = bal+SW;
			nw = nw+PV;
			
			if (isSetup(P.getName())) {
				
				statement.addBatch("UPDATE `" + this.database + "`.`econ` SET `Balance`=" + bal + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`econ` SET `PlayerStocks`=" + Stocks + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`econ` SET `FarmingStocks`=" + FarmingShares + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`econ` SET `MiningStocks`=" + MiningShares + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`econ` SET `LoggingStocks`=" + LoggingShares + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`econ` SET `PropertyValue`= 0 WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`econ` SET `NetWorth`=" + nw + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`econ` SET `PropertyValue`=" + PV + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`econ` SET `Checking`=" + CB + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`econ` SET `Saving`=" + SB + " WHERE  `Name`='" + P.getName() + "';");
				

			}else{
				statement.addBatch("INSERT INTO econ (`Name`, `UUID`) VALUES ('" + P.getName() + "', '"+P.getUniqueId().toString()+"');");
				
				statement.addBatch("UPDATE `" + this.database + "`.`econ` SET `Balance`=" + bal + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`econ` SET `PlayerStocks`=" + Stocks + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`econ` SET `FarmingStocks`=" + FarmingShares + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`econ` SET `MiningStocks`=" + MiningShares + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`econ` SET `LoggingStocks`=" + LoggingShares + " WHERE  `Name`='" + P.getName() + "';");

			}
			
			
			
			
			double TotalTaxes = config.getDouble("PlayerData."+P.getName()+".EconProfile.TotalTax");
			double CashFlow = config.getDouble("PlayerData."+P.getName()+".CashFlow");
			double CashFlowTotal = config.getDouble("PlayerData."+P.getName()+".TotalCashFlow");
			double TaxBracket = econ.getTaxBracket(P.getName());
			double Earned = config.getDouble("PlayerData."+P.getName()+".EconProfile.Earned");
			double Spent = config.getDouble("PlayerData."+P.getName()+".EconProfile.Spent");
			
			if (isProfileSetup(P.getName())) {
				
				statement.addBatch("UPDATE `" + this.database + "`.`EconProfile` SET `TaxesPaid`=" + TotalTaxes + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`EconProfile` SET `CashFlow`=" + CashFlow + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`EconProfile` SET `CashFlowTotal`=" + CashFlowTotal + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`EconProfile` SET `TaxBracket`=" + TaxBracket + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`EconProfile` SET `Earned`=" + Earned + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`EconProfile` SET `Spent`= "+ Spent +" WHERE  `Name`='" + P.getName() + "';");
				

			}else{
				statement.addBatch("INSERT INTO EconProfile (`Name`, `UUID`) VALUES ('" + P.getName() + "', '"+P.getUniqueId().toString()+"');");
				
				statement.addBatch("UPDATE `" + this.database + "`.`EconProfile` SET `TaxesPaid`=" + TotalTaxes + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`EconProfile` SET `CashFlow`=" + CashFlow + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`EconProfile` SET `CashFlowTotal`=" + CashFlowTotal + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`EconProfile` SET `TaxBracket`=" + TaxBracket + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`EconProfile` SET `Earned`=" + Earned + " WHERE  `Name`='" + P.getName() + "';");
				statement.addBatch("UPDATE `" + this.database + "`.`EconProfile` SET `Spent`= "+ Spent +" WHERE  `Name`='" + P.getName() + "';");

			}
			
			//statement.executeUpdate("UPDATE `" + this.database + "`.`NSHC_Points` SET `points`=" + a + " WHERE  `name`='" + Player + "';");
			
		
		
		
			//PreparedStatement PS = c.prepareStatement(ST);
	}
			statement.executeBatch();
			updateEconLog();
			//PS.executeLargeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	closeConnection();
	}

public boolean isBusiness(String n) {
	
	
	List<String> bso = config.getStringList("PlayerData.BusinessOwners");
	if (bso.contains(n)) {
		return true;
	}else {
		return false;
	}
	
}

public void listBusiness() {
	bsl = new ArrayList();
	for (String B : bso) {
		
		bsl.add(getBusiness(B));
		
	}
}

public synchronized void updateMarkets() {
	openConnection();
	double PSP = mc.psp;
	double MP = config.getDouble("ServerData.MiningStockPrice");
	double LP = config.getDouble("ServerData.LoggingStockPrice");
	double FP = config.getDouble("ServerData.FarmingStockPrice");
	
	try {
		if (c == null) {
			openBackupConnection();
		}
		statement = c.createStatement();
		statement.executeUpdate("INSERT INTO `"+this.database+"`.`markets` (`PlayerStocks`, `FarmingStocks`, `MiningStocks`, `LoggingStocks`) VALUES ("+PSP+", "+FP+", "+MP+", "+LP+"); ");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	closeConnection();
	
}

public synchronized void updateEcon() {
	FileConfiguration config = mc.getConfig();

	double PSP = mc.psp;
	double MiningPrice = config.getDouble("ServerData.MiningStockPrice");
	double LoggingPrice = config.getDouble("ServerData.LoggingStockPrice");
	double FarmingPrice = config.getDouble("ServerData.FarmingStockPrice");
	double PV = 0.00;
	
	
	for (Player P : Bukkit.getOnlinePlayers()) {
		try {

			int SS = getStockShares(P.getName());
			double SP = getStockPrice();
			
			int MiningShares = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Mining");
			int LoggingShares = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Logging");
			int FarmingShares = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Farming");
			int Stocks = mc.getConfig().getInt("PlayerStocks.PlayerData."+P.getName()+".Shares");
			UUID ID = P.getUniqueId();
			String Name = P.getName();
			double bal = econ.getBalance(P.getName());
			
			double SW = (Stocks*PSP)+(MiningShares*MiningPrice)+(FarmingShares*FarmingPrice)+(LoggingShares*LoggingPrice)+(SS*SP)+econ.getCheckingBalance(P.getName())+econ.getSavingsBalance(P.getName());
			double nw = bal+SW;
			nw = nw+PV;
			
			if (isSetup(P.getName())) {
				statement.addBatch("UPDATE `econ` SET `LoggingStocks`=" + LoggingShares + " WHERE  `Name`='" + P.getName() + "'; ");
				statement.addBatch("UPDATE `econ` SET `MiningStocks`=" + MiningShares + " WHERE  `Name`='" + P.getName() + "'; ");
				statement.addBatch("UPDATE `econ` SET `FarmingStocks`=" + FarmingShares + " WHERE  `Name`='" + P.getName() + "'; ");
				statement.addBatch("UPDATE `econ` SET `PlayerStocks`=" + Stocks + " WHERE  `Name`='" + P.getName() + "'; ");
				statement.addBatch("UPDATE `econ` SET `Balance`=" + bal + " WHERE  `Name`='" + P.getName() + "'; ");
				

			}else{
				statement.addBatch("INSERT INTO econ (`Name`, `UUID`) VALUES ('" + P.getName() + "', '"+P.getUniqueId().toString()+"');");
				statement.addBatch("UPDATE `econ` SET `LoggingStocks`=" + LoggingShares + " WHERE  `Name`='" + P.getName() + "'; ");
				statement.addBatch("UPDATE `econ` SET `MiningStocks`=" + MiningShares + " WHERE  `Name`='" + P.getName() + "'; ");
				statement.addBatch("UPDATE `econ` SET `FarmingStocks`=" + FarmingShares + " WHERE  `Name`='" + P.getName() + "'; ");
				statement.addBatch("UPDATE `econ` SET `PlayerStocks`=" + Stocks + " WHERE  `Name`='" + P.getName() + "'; ");
				statement.addBatch("UPDATE `econ` SET `Balance`=" + bal + " WHERE  `Name`='" + P.getName() + "'; ");
			}

				statement.executeBatch();

	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
}

public void updatePlayerStats() {
	PlayerInfo pi;
	boolean setup = false;
	
	openConnection();
	
	for (Player p : Bukkit.getOnlinePlayers()) {
		Bukkit.getLogger().log(Level.INFO, "SQL Player Data "+p.getName());
		DataHandler dh = new DataHandler(p.getName());
		pi = dh.getPlayerInfo(p);
		setup = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig().getBoolean("SQL.PlayerData.Setup."+p.getName());
	
	try {
		
		if (c == null) {
			openBackupConnection();
		}
		
		if (setup) {
			statement = c.createStatement();
			Bukkit.getLogger().log(Level.INFO, "SQL Player Data Updating "+p.getName());
			//UPDATE `seco`.`playerdata` SET `RAIDS`=1, `BOB`=1, `FRED`=1 WHERE  `UUID`='c8274e91-f23d-4c9f-aca7-d350f36f66b5';
			statement.addBatch("UPDATE `seco`.`playerdata` SET `NAME`='"+p.getName()+"', "
									+ "`HEAD_SLOT`='"+pi.getHead()+"', "
									+ "`HEAD_E1`='"+pi.getHeadEnchants().get(0)+"', "
									+ "`HEAD_E2`='"+pi.getHeadEnchants().get(1)+"',"
									+ "`HEAD_E3`='"+pi.getHeadEnchants().get(2)+"', "
									+ "`HEAD_E4`='"+pi.getHeadEnchants().get(3)+"', "
									+ "`CHEST_SLOT`='"+pi.getChest()+"',"
									+ "`CHEST_E1`='"+pi.getChestEnchants().get(0)+"',"
									+ "`CHEST_E2`='"+pi.getChestEnchants().get(1)+"',"
									+ "`CHEST_E3`='"+pi.getChestEnchants().get(2)+"',"
									+ "`CHEST_E4`='"+pi.getChestEnchants().get(3)+"',"
									+ "`LEGS_SLOT`='"+pi.getLegs()+"',"
									+ "`LEGS_E1`='"+pi.getLegEnchants().get(0)+"',"
									+ "`LEGS_E2`='"+pi.getLegEnchants().get(1)+"',"
									+ "`LEGS_E3`='"+pi.getLegEnchants().get(2)+"',"
									+ "`LEGS_E4`='"+pi.getLegEnchants().get(3)+"',"
									+ "`BOOTS_SLOT`='"+pi.getBoots()+"',"
									+ "`BOOTS_E1`='"+pi.getBootEnchants().get(0)+"',"
									+ "`BOOTS_E2`='"+pi.getBootEnchants().get(1)+"',"
									+ "`BOOTS_E3`='"+pi.getBootEnchants().get(2)+"',"
									+ "`BOOTS_E4`='"+pi.getBootEnchants().get(3)+"' WHERE `UUID`='"+p.getUniqueId().toString()+"';");
			
			statement.addBatch("UPDATE `seco`.`playerdata` SET `QUEST_TIER`="+config.getInt("PlayerData."+p.getName()+".QuestData.QuestStage")+" WHERE `UUID`='"+p.getUniqueId().toString()+"';");
			statement.addBatch("UPDATE `seco`.`playerdata` SET `BOSSES`="+config.getInt("PlayerData."+p.getName()+".RaidData.Boss.Kills")+" WHERE `UUID`='"+p.getUniqueId().toString()+"';");
			statement.addBatch("UPDATE `seco`.`playerdata` SET `RAID_DEATHS`="+config.getInt("PlayerData."+p.getName()+".RaidData.Boss.Deaths")+" WHERE `UUID`='"+p.getUniqueId().toString()+"';");
			statement.addBatch("UPDATE `seco`.`playerdata` SET `BOB`="+config.getInt("PlayerData."+p.getName()+".RaidData.Boss.Bob.Kills")+" WHERE `UUID`='"+p.getUniqueId().toString()+"';");
			statement.addBatch("UPDATE `seco`.`playerdata` SET `FRED`="+config.getInt("PlayerData."+p.getName()+".RaidData.Boss.Fred.Kills")+" WHERE `UUID`='"+p.getUniqueId().toString()+"';");
			statement.addBatch("UPDATE `seco`.`playerdata` SET `JOE`="+config.getInt("PlayerData."+p.getName()+".RaidData.Boss.Joe.Kills")+" WHERE `UUID`='"+p.getUniqueId().toString()+"';");
			statement.addBatch("UPDATE `seco`.`playerdata` SET `JJ`="+config.getInt("PlayerData."+p.getName()+".RaidData.Boss.JJ.Kills")+" WHERE `UUID`='"+p.getUniqueId().toString()+"';");
			
			statement.executeBatch();
			
		}else {
	
			statement = c.createStatement();
		
		
		Bukkit.getLogger().log(Level.INFO, "SQL Player Data Adding "+p.getName());
			statement.addBatch("INSERT INTO `seco`.`playerdata` (`NAME`, `UUID`, `QUEST_TIER`, `RAIDS`, `BOB`, `FRED`, `JOE`, `JJ`, "
					+ "`RAID_DEATHS`, `MAIN_WEP`, `MAIN_MELEE`, `MAIN_RANGED`, `MAIN_RANGED_E1`, `MAIN_RANGED_E2`, `MAIN_RANGED_E3`, "
					+ "`MAIN_RANGED_E4`, `MAIN_MELEE_E1`, `MAIN_MELEE_E2`, `MAIN_MELEE_E3`, `MAIN_MELEE_E4`, `HEAD_SLOT`, `HEAD_E1`, "
					+ "`HEAD_E2`, `HEAD_E3`, `HEAD_E4`, `CHEST_SLOT`, `CHEST_E1`, `CHEST_E2`, `CHEST_E3`, `CHEST_E4`, `LEGS_SLOT`, "
					+ "`LEGS_E1`, `LEGS_E2`, `LEGS_E3`, `LEGS_E4`, `BOOTS_SLOT`, `BOOTS_E1`, `BOOTS_E2`, `BOOTS_E3`, `BOOTS_E4`) "
					+ "VALUES ('"+p.getName()+"', '"+p.getUniqueId().toString()+"', 0, 0, 0, 0, 0, 0, 0, 'MAIN_WEP', "
					+ "'MAIN_MELEE', 'MAIN_RANGED', 'MRE1', 'MRE2', 'MRE3', 'MRE4', 'MME1', 'MME2', 'MME3', 'MME4', '"+pi.getHead()+"',"
					+ " '"+pi.getHeadEnchants().get(0)+"', '"+pi.getHeadEnchants().get(1)+"', '"+pi.getHeadEnchants().get(2)+"',"
					+ " '"+pi.getHeadEnchants().get(3)+"', '"+pi.getChest()+"', '"+pi.getChestEnchants().get(0)+"', '"+pi.getChestEnchants().get(1)+"', "
							+ "'"+pi.getChestEnchants().get(2)+"', '"+pi.getChestEnchants().get(3)+"', '"+pi.getLegs()+"', '"+pi.getLegEnchants().get(0)+"', '"+pi.getLegEnchants().get(1)+"', '"+pi.getLegEnchants().get(2)+"',"
							+ " '"+pi.getLegEnchants().get(3)+"', '"+pi.getBoots()+"', '"+pi.getBootEnchants().get(0)+"', '"+pi.getBootEnchants().get(1)+"', "
							+ "'"+pi.getBootEnchants().get(2)+"', '"+pi.getBootEnchants().get(3)+"');");
		
		statement.executeBatch();
		Bukkit.getPluginManager().getPlugin("STSEcon").getConfig().set("SQL.PlayerData.Setup."+p.getName(), true);
		try {
			Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
	    } catch (Exception e) {
	    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    }
		}
		//statement.executeUpdate("INSERT INTO `"+this.database+"`.`markets` (`PlayerStocks`, `FarmingStocks`, `MiningStocks`, `LoggingStocks`) VALUES ("+PSP+", "+FP+", "+MP+", "+LP+"); ");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	closeConnection();
	Bukkit.getLogger().log(Level.INFO, "SQL Player Data Updated");
}

public void updateEconLog() {
	
	List<Integer> IL = new ArrayList();
	IL = config.getIntegerList("EconLogList");
	List<EconLog> EL = new ArrayList();
	openConnection();

	try {
		if (c == null) {
			openBackupConnection();
		}
	statement = c.createStatement();
	for (Integer I : IL) {
		
		String N = config.getString("EconLogs."+I+".Name");
		double A = config.getDouble("EconLogs."+I+".Ammount");
		String T = config.getString("EconLogs."+I+".Type");
		double B = config.getDouble("EconLogs."+I+".Bal");
		EconLog LE = new EconLog(N, A, T, B);
		EL.add(LE);
		
	}
	
	
	
	for (EconLog L : EL) {
			
			
			statement.addBatch("INSERT INTO `EconLog` (`PlayerName`,`Ammount`, `Type`, `RunningBalance`) VALUES ('"+L.getName()+"', "+L.getAmmount()+", '"+L.getType()+"', "+L.getBal()+"); ");

	}
		statement.executeBatch();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	closeConnection();
	
}

public synchronized void updateProperty() {
	
	double PSP = mc.psp;
	double MP = config.getDouble("ServerData.MiningStockPrice");
	double LP = config.getDouble("ServerData.LoggingStockPrice");
	double FP = config.getDouble("ServerData.FarmingStockPrice");
	
	for (Property PR : prys) {
		
		
		boolean inDB = PR.inDB();
		
		
			if (inDB) {
				//statement.executeUpdate("UPDATE `" + this.database + "`.`PropertyData` SET `Owner`='" + PR.getOwner() + "' WHERE `Name`='"+PR.getName()+"';");
				//statement.executeUpdate("UPDATE `" + this.database + "`.`PropertyData` SET `Renter`='" + PR.getRenter() + "' WHERE `Name`='"+PR.getName()+"';");
				//statement.executeUpdate("UPDATE `" + this.database + "`.`PropertyData` SET `Zone`='" + PR.getZone() + "' WHERE `Name`='"+PR.getName()+"';");
				//statement.executeUpdate("UPDATE `" + this.database + "`.`PropertyData` SET `Value`='" + getPropertyValue(PR) + "' WHERE `Name`='"+PR.getName()+"';");
			}else {
				//statement.executeUpdate("INSERT INTO `"+this.database+"`.`PropertyData` (`Name`, `Owner`, `Renter`, `MinX`, `MinY`, `MinZ`, `MaxX`, `MaxY`, `MaxZ`, `Value`, `Zone`) VALUES ('"+PR.getName()+"', '"+PR.getOwner()+"', '"+PR.getRenter()+"', '"+PR.getMinLoc().getX()+"', '"+PR.getMinLoc().getY()+"', '"+PR.getMinLoc().getZ()+"', '"+PR.getMaxLoc().getX()+"', '"+PR.getMaxLoc().getY()+"', '"+PR.getMaxLoc().getZ()+"', '"+getPropertyValue(PR)+"', '"+PR.getZone()+"');");
				PR.setinDB(true);
			}
		}

	
}

public Business getBusiness(String Name) {
	
	if (isBusiness(Name)) {
		bso = config.getStringList("PlayerData.BusinessOwners");
		for (String b : bso) {
			
			if (b == Name) {

				int e = config.getInt("PlayerData."+b+".Business.Employee.Count");
				int arev = config.getInt("PlayerData."+b+".Business.Revenue");
				int asal = config.getInt("PlayerData."+b+".Business.OperationCost");
				int RV = config.getInt("PlayerData."+b+".Business.Revenue");
		
		
				int rv = config.getInt("PlayerData."+b+".Business.Revenue");
				int size = config.getInt("PlayerData."+b+".Business.Employee.Count")+1;
				int oc = config.getInt("PlayerData."+b+".Business.OperationCost");
				int sh = config.getInt("PlayerData."+b+".Business.Shares");
				int PRF = RV-oc;
				int s = config.getInt("PlayerData."+b+".Business.Supplies");
				Business bl = new Business(b, rv, oc, PRF, sh, size);
				return bl;
				
			}
		}
	}
	
	
	return null;
}
}
