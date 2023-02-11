package cc.koffeecreations.main.commands;

import java.text.DecimalFormat;
import java.util.logging.Level;

import cc.koffeecreations.main.econ.Econ;
import cc.koffeecreations.main.unknow.UpdatePrices;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

//import net.md_5.bungee.api.ChatColor;

public class CommandHandler {

	FileConfiguration config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
	
	public Player P;
	public String cmd;
	public String[] args;
	double miningPRC = config.getDouble("ServerData.MiningStockPrice");
	double loggingPRC = config.getDouble("ServerData.LoggingStockPrice");
	double farmingPRC = config.getDouble("ServerData.FarmingStockPrice");
	double redstonePRC = config.getDouble("ServerData.RedStoneStockPrice");
	double lumberPRC = config.getDouble("ServerData.LumberStockPrice");
	Econ econ;
	public DecimalFormat price = new DecimalFormat("0.00");
	public DecimalFormat stocks = new DecimalFormat("0");
	public String prf = ChatColor.AQUA+"["+ChatColor.GREEN+"STS Stocks"+ChatColor.AQUA+"] ";
	String lp = price.format(loggingPRC);
	String mp = price.format(miningPRC);
	String lmp = price.format(lumberPRC);
	String fp = price.format(farmingPRC);
	
	public CommandHandler(Player p, String cmds, String[] ag) {
	
		P = p;
		cmd = cmds;
		args = ag;
		econ = new Econ();
		price.setGroupingUsed(true);
		price.setGroupingSize(3);
		stocks.setGroupingUsed(true);
		stocks.setGroupingSize(3);
		new UpdatePrices(miningPRC, loggingPRC, farmingPRC);
		
	}
	
	public synchronized boolean stocks() {
		
		
    	Player p = P;
    	if (args.length < 1) {
    		
    		
    		//TODO Add Customizable company Stock Tags
    		P.sendMessage(prf+ChatColor.YELLOW+"The Stock Prices are:");
    		P.sendMessage(prf+ChatColor.YELLOW+"Logging: "+ChatColor.GREEN+"$"+lp);
    		P.sendMessage(prf+ChatColor.YELLOW+"Mining: "+ChatColor.GREEN+"$"+mp);
    		P.sendMessage(prf+ChatColor.YELLOW+"Farming: "+ChatColor.GREEN+"$"+fp);
    		return true;
    	}else{
    		
    		if (args[0].equalsIgnoreCase("buy")) {
    			
    			
    				if (args[1].equalsIgnoreCase("logging")) {
    					int q = Integer.parseInt(args[2]);
    					double pc = loggingPRC*q;
    					
    					
    					if (econ.has(p.getName(), pc)) {
    						
    						econ.withdrawPlayer(p.getName(), pc);
    						int sa = 0;
    						sa = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Logging");
    						
    						sa = sa+q;
    						
    						config.set("PlayerData."+P.getName()+".Stocks.Shares.Logging", sa);
    						saveConfig();
    						
    						P.sendMessage(prf+ChatColor.YELLOW+"You bought "+stocks.format(q)+" shares of logging for "+ChatColor.GREEN+"$"+price.format(pc));
    						return true;
    						
    					}else {
    						P.sendMessage(prf+ChatColor.RED+"You don't have enough money for this");
    						return true;
    					}
    				}else if (args[1].equalsIgnoreCase("mining")) {
    					int q = Integer.parseInt(args[2]);
    					double pc = miningPRC*q;
    					
    					
    					if (econ.has(p.getName(), pc)) {
    						
    						econ.withdrawPlayer(p.getName(), pc);
    						int sa = 0;
    						sa = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Mining");
    						
    						sa = sa+q;
    						
    						config.set("PlayerData."+P.getName()+".Stocks.Shares.Mining", sa);
    						saveConfig();
    						
    						P.sendMessage(prf+ChatColor.YELLOW+"You bought "+stocks.format(q)+" shares of mining for "+ChatColor.GREEN+"$"+price.format(pc));
    						return true;
    						
    					}else {
    						P.sendMessage(prf+ChatColor.RED+"You don't have enough money for this");
    					}
    				}else if (args[1].equalsIgnoreCase("lumber")) {
    					int q = Integer.parseInt(args[2]);
    					double pc = lumberPRC*q;
    					
    					
    					if (econ.has(p.getName(), pc)) {
    						
    						econ.withdrawPlayer(p.getName(), pc);
    						int sa = 0;
    						sa = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Lumber");
    						
    						sa = sa+q;
    						
    						config.set("PlayerData."+P.getName()+".Stocks.Shares.Lumber", sa);
    						saveConfig();
    						
    						P.sendMessage(prf+ChatColor.YELLOW+"You bought "+stocks.format(q)+" shares of Lumber for "+ChatColor.GREEN+"$"+price.format(pc));
    						return true;
    						
    					}else {
    						P.sendMessage(prf+ChatColor.RED+"You don't have enough money for this");
    					}
    				}else if (args[1].equalsIgnoreCase("farming")) {
    					int q = Integer.parseInt(args[2]);
    					double pc = farmingPRC*q;
    					
    					
    					if (econ.has(p.getName(), pc)) {
    						
    						econ.withdrawPlayer(p.getName(), pc);
    						int sa = 0;
    						sa = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Farming");
    						
    						sa = sa+q;
    						
    						config.set("PlayerData."+P.getName()+".Stocks.Shares.Farming", sa);
    						saveConfig();
    						
    						P.sendMessage(prf+ChatColor.YELLOW+"You bought "+stocks.format(q)+" shares of Farming for "+ChatColor.GREEN+"$"+price.format(pc));
    						return true;
    						
    					}else {
    						P.sendMessage(prf+ChatColor.RED+"You don't have enough money for this");
    					}
    				}
    			
    		}else if (args[0].equalsIgnoreCase("sell")) {
				
    			
    			
    				if (args[1].equalsIgnoreCase("logging")) {
    					int q = Integer.parseInt(args[2]);
    					double pc = loggingPRC*q;
    					
    					int sta = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Logging");
    					
    					
    					if (sta >= q) {
    						
    						econ.depositPlayer(p.getName(), pc);
    						int sa = 0;
    						sa = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Logging");
    						
    						sa = sa-q;
    						
    						config.set("PlayerData."+P.getName()+".Stocks.Shares.Logging", sa);
    						saveConfig();
    						
    						P.sendMessage(prf+ChatColor.YELLOW+"You sold "+stocks.format(q)+" shares of logging for "+ChatColor.GREEN+"$"+price.format(pc));
    						return true;
    						
    					}else {
    						P.sendMessage(prf+ChatColor.RED+"You don't have that many stocks to sell!");
    					}
    				}else if (args[1].equalsIgnoreCase("mining")) {
    					int q = Integer.parseInt(args[2]);
    					double pc = miningPRC*q;
    					
    					int sta = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Mining");
    					
    					if (sta >= q) {
    						
    						econ.depositPlayer(p.getName(), pc);
    						int sa = 0;
    						sa = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Mining");
    						
    						sa = sa-q;
    						
    						config.set("PlayerData."+P.getName()+".Stocks.Shares.Mining", sa);
    						
    						saveConfig();
    						
    						
    						P.sendMessage(prf+ChatColor.YELLOW+"You sold "+stocks.format(q)+" shares of mining for "+ChatColor.GREEN+"$"+price.format(pc));
    						return true;
    						
    					}else {
    						P.sendMessage(prf+ChatColor.RED+"You don't have that many stocks to sell!");
    					}
    				}else if (args[1].equalsIgnoreCase("lumber")) {
    					int q = Integer.parseInt(args[2]);
    					double pc = lumberPRC*q;
    					
    					int sta = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Lumber");
    					
    					if (sta >= q) {
    						
    						econ.depositPlayer(p.getName(), pc);
    						int sa = 0;
    						sa = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Lumber");
    						
    						sa = sa-q;
    						
    						config.set("PlayerData."+P.getName()+".Stocks.Shares.Lumber", sa);
    						saveConfig();
    						
    						P.sendMessage(prf+ChatColor.YELLOW+"You sold "+stocks.format(q)+" shares of Lumber for"+ChatColor.GREEN+"$"+price.format(pc));
    						return true;
    						
    					}else {
    						P.sendMessage(prf+ChatColor.RED+"You don't have that many stocks to sell!");
    					}
    			
			}else if (args[1].equalsIgnoreCase("farming")) {
				int q = Integer.parseInt(args[2]);
				double pc = farmingPRC*q;
				
				int sta = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Farming");
				
				if (sta >= q) {
					
					econ.depositPlayer(p.getName(), pc);
					int sa = 0;
					sa = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Farming");
					
					sa = sa-q;
					
					config.set("PlayerData."+P.getName()+".Stocks.Shares.Farming", sa);
					saveConfig();
					
					P.sendMessage(prf+ChatColor.YELLOW+"You sold "+stocks.format(q)+" shares of Farming for"+ChatColor.GREEN+"$"+price.format(pc));
					return true;
					
				}else {
					P.sendMessage(prf+ChatColor.RED+"You don't have enough stocks to sell!");
				}
		
	}
    		
    	
    	
    }else if (args[0].equalsIgnoreCase("bal")) {
		
		
		int lm = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Lumber");
		int lms = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Logging");
		int mm = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Mining");
		int fm = config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Farming");
		
		P.sendMessage(prf+ChatColor.GREEN+"Logging: "+stocks.format(lms));
		P.sendMessage(prf+ChatColor.GREEN+"Lumber: "+stocks.format(lm));
		P.sendMessage(prf+ChatColor.GREEN+"Mining: "+stocks.format(mm));
		P.sendMessage(prf+ChatColor.GREEN+"Farming: "+stocks.format(fm));
		

}	//config.getInt("PlayerData."+P.getName()+".Stocks.Shares.Logging");
    		
    		
    	}
    	return true;
    	}
   
	
public void saveConfig() {

	Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
	try {
		Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
    } catch (Exception e) {
    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
    }

}

}