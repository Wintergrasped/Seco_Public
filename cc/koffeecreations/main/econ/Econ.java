package cc.koffeecreations.main.econ;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import cc.koffeecreations.main.Main;
import cc.koffeecreations.main.database.Mysql;
import cc.koffeecreations.main.events.EconDepositEvent;
import cc.koffeecreations.main.events.EconWithdrawEvent;
import cc.koffeecreations.main.events.payCycleEvent;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class Econ {

	private Main plugin;
	public File EconFile;
	public FileConfiguration config = null;

	double pm = 1.002;
	double lpm = 1.10;
	double am = 1.005;

	double base = 100;
	
	double tax = 9.2;
	double tx = tax/100;
	double volume = 0;
	Main M;
	Mysql SQL;
	boolean logging = false;

	int LN = 0;

	List<EconLog> LS = new ArrayList();
	
	public Econ() {

		plugin = Main.getInstance;
		// plugin.saveDefaultConfig();
		// EconFile = new File(plugin.getDataFolder(), "economy.yml");
		// config = YamlConfiguration.loadConfiguration(EconFile);
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		volume = config.getInt("EconVolume");
		tax = config.getDouble("SalesTax");
		
		LN = config.getInt("LogNumber");
		
		startEconLogging();
		//M = Main.getInstance;
		//startEconLogging(Main.getInstance);

	}

	public void startEconLogging() {
		
		
		//SQL = new Mysql(M, config.getString("MySQL.IP"), config.getInt("MySQL.Port"), config.getString("MySQL.DataBase"), config.getString("MySQL.User"), config.getString("MySQL.Password"));
	    //SQL.openConnection();
	    logging = true;
		
	}
	
	public void StartEcon() {
		if (plugin == null) {

		} else {
			config = plugin.getConfig();
		}
	}

	public void LoadConfig() {
		if (plugin == null) {

		} else {
			config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		}
	}

	
	public boolean has(String Name, double a) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		double bal = config.getDouble("PlayerData." + Name + ".Economy.Balance");

		if (bal >= a) {
			return true;
		} else {
			return false;
		}
	}

	public boolean Savingshas(String Name, double a) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		Double bal = config.getDouble("PlayerData." + Name + ".Economy.Savings");

		if (bal >= a) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean Checkinghas(String Name, double a) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		Double bal = config.getDouble("PlayerData." + Name + ".Economy.Checking");

		if (bal >= a) {
			return true;
		} else {
			return false;
		}
	}

	public boolean exists(String Name) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		Double e = config.getDouble("PlayerData." + Name + ".Economy.Exists");

		if (e == 0) {
			return false;
		} else {
			return true;
		}

	}

	public boolean existsSavings(String Name) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		Double e = config.getDouble("PlayerData." + Name + ".Economy.SavingsExists");

		if (e == 0) {
			return false;
		} else {
			return true;
		}

	}
	
	public boolean existsChecking(String Name) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		Double e = config.getDouble("PlayerData." + Name + ".Economy.CheckingExists");

		if (e == 0) {
			return false;
		} else {
			return true;
		}

	}

	public void create(String Name) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		config.set("PlayerData." + Name + ".Economy.Balance", 100);
		config.set("PlayerData." + Name + ".Economy.Exists", 1);
		save();

	}

	public void createSavings(String Name) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		config.set("PlayerData." + Name + ".Economy.Savings", 0);
		config.set("PlayerData." + Name + ".Economy.SavingExists", 1);
		save();
	}
	
	public void createChecking(String Name) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		config.set("PlayerData." + Name + ".Economy.Checking", 0);
		config.set("PlayerData." + Name + ".Economy.CheckingExists", 1);
		save();
	}

	public double getSavingsBalance(String Name) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		return config.getDouble("PlayerData." + Name + ".Economy.Savings");

	}
	
	public double getCheckingBalance(String Name) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		return config.getDouble("PlayerData." + Name + ".Economy.Checking");

	}

	public double getBalance(String Name) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		return config.getDouble("PlayerData." + Name + ".Economy.Balance");

	}


	public void withdraw(String Name, double a) {
		EconWithdrawEvent ev = new EconWithdrawEvent(Name, a);
        Bukkit.getServer().getPluginManager().callEvent(ev);
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		double spent = config.getDouble("PlayerData."+Name+".EconProfile.Spent");
		spent = spent+a;
		config.set("PlayerData."+Name+".EconProfile.Spent", spent);
		if (config == null) {
			LoadConfig();
		}
		double bal = config.getDouble("PlayerData." + Name + ".Economy.Balance");
		bal = bal - a;
		config.set("PlayerData." + Name + ".Economy.Balance", bal);
		
		if (logging) {
			LS.add(new EconLog(Name, a, "Withdraw", bal));
			saveLogs();
		}else {
			startEconLogging();
		}
		
		save();

	}
	
	public void withdrawPlayerTaxExempt(String Name, double a) {
		EconWithdrawEvent ev = new EconWithdrawEvent(Name, a);
        Bukkit.getServer().getPluginManager().callEvent(ev);
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		double spent = config.getDouble("PlayerData."+Name+".EconProfile.Spent");
		spent = spent+a;
		config.set("PlayerData."+Name+".EconProfile.Spent", spent);
		if (config == null) {
			LoadConfig();
		}
		double bal = config.getDouble("PlayerData." + Name + ".Economy.Balance");
		bal = bal - a;
		config.set("PlayerData." + Name + ".Economy.Balance", bal);
		
		if (logging) {
			LS.add(new EconLog(Name, a, "Withdraw", bal));
			saveLogs();
		}else {
			startEconLogging();
		}
		
		save();

	}

	public void withdrawSavings(String Name, double a) {
		EconWithdrawEvent ev = new EconWithdrawEvent(Name, a);
        Bukkit.getServer().getPluginManager().callEvent(ev);
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		double bal = config.getDouble("PlayerData." + Name + ".Economy.Savings");
		bal = bal - a;
		config.set("PlayerData." + Name + ".Economy.Savings", bal);
		if (logging) {
			LS.add(new EconLog(Name, a, "Withdraw", bal));
			saveLogs();
		}else {
			startEconLogging();
		}
		save();

	}
	
	public void withdrawChecking(String Name, double a) {
		EconWithdrawEvent ev = new EconWithdrawEvent(Name, a);
        Bukkit.getServer().getPluginManager().callEvent(ev);
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		double bal = config.getDouble("PlayerData." + Name + ".Economy.Checking");
		bal = bal - a;
		config.set("PlayerData." + Name + ".Economy.Checking", bal);
		if (logging) {
			LS.add(new EconLog(Name, a, "Withdraw", bal));
			saveLogs();
		}else {
			startEconLogging();
		}
		save();

	}

	public void withdrawPlayer(String Name, double a) {
		EconWithdrawEvent ev = new EconWithdrawEvent(Name, a);
        Bukkit.getServer().getPluginManager().callEvent(ev);
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		double spent = config.getDouble("PlayerData."+Name+".EconProfile.Spent");
		spent = spent+a;
		config.set("PlayerData."+Name+".EconProfile.Spent", spent);
		if (config == null) {
			LoadConfig();
		}
		double bal = config.getDouble("PlayerData." + Name + ".Economy.Balance");
		bal = bal - a;
		config.set("PlayerData." + Name + ".Economy.Balance", bal);
		if (logging) {
			LS.add(new EconLog(Name, a, "Withdraw", bal));
			saveLogs();
		}else {
			startEconLogging();
		}
		save();

	}

	public void depositPlayer(String Name, double a) {
		EconDepositEvent ev = new EconDepositEvent(Name, a);
        Bukkit.getServer().getPluginManager().callEvent(ev);
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		double earned = config.getDouble("PlayerData."+Name+".EconProfile.Earned");
		earned = earned+a;
		addCashFlow(Name, a);
		
		if (config == null) {
			LoadConfig();
		}
		double bal = config.getDouble("PlayerData." + Name + ".Economy.Balance");
		double tax = getTaxBracket(Name);
		double tx = tax/100;
		bal = bal + (a-(a*tx));
		
		double txm = a*tx;
		double tt = config.getDouble("PlayerData."+Name+".EconProfile.TotalTax");
		tt = tt+txm;
		depositTax(tt);
		config.set("PlayerData."+Name+".EconProfile.TotalTax", tt);
		
		volume = volume+a;
		config.set("PlayerData." + Name + ".Economy.Balance", bal);
		config.set("PlayerData."+Name+".EconProfile.Earned", earned);
		if (logging) {
			LS.add(new EconLog(Name, a, "Deposit", bal));
			saveLogs();
		}else {
			startEconLogging();
		}
		save();

	}
	
	public void depositPlayerTaxExmpt(String Name, double a) {
		EconDepositEvent ev = new EconDepositEvent(Name, a);
        Bukkit.getServer().getPluginManager().callEvent(ev);
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		double earned = config.getDouble("PlayerData."+Name+".EconProfile.Earned");
		double bal = config.getDouble("PlayerData." + Name + ".Economy.Balance");
		earned = earned+a;
		addCashFlow(Name, a);
		
		if (config == null) {
			LoadConfig();
		}
		
		
		volume = volume+a;
		config.set("PlayerData." + Name + ".Economy.Balance", bal+a);
		config.set("PlayerData."+Name+".EconProfile.Earned", earned);
		if (logging) {
			LS.add(new EconLog(Name, a, "Deposit", bal));
			saveLogs();
		}else {
			startEconLogging();
		}
		save();

	}
	
	public void set(String Name, double a) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		double bal = config.getDouble("PlayerData." + Name + ".Economy.Balance");
		bal = a;
		config.set("PlayerData." + Name + ".Economy.Balance", bal);
		if (logging) {
			LS.add(new EconLog(Name, a, "Set", bal));
			saveLogs();
		}else {
			startEconLogging();
		}
		save();

	}

	public void deposit(String Name, double a) {
		EconDepositEvent ev = new EconDepositEvent(Name, a);
        Bukkit.getServer().getPluginManager().callEvent(ev);
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		double earned = config.getDouble("PlayerData."+Name+".EconProfile.Earned");
		earned = earned+a;
		addCashFlow(Name, a);
		
		if (config == null) {
			LoadConfig();
		}
		double tax = getTaxBracket(Name);
		double tx = tax/100;
		Double bal = config.getDouble("PlayerData." + Name + ".Economy.Balance");
		bal = bal + (a-(a*tx));
		
		double txm = a*tx;
		double tt = config.getDouble("PlayerData."+Name+".EconProfile.TotalTax");
		tt = tt+txm;
		depositTax(tt);
		config.set("PlayerData."+Name+".EconProfile.TotalTax", tt);
		
		volume = volume+a;
		config.set("PlayerData." + Name + ".Economy.Balance", bal);
		config.set("PlayerData."+Name+".EconProfile.Earned", earned);
		if (logging) {
			LS.add(new EconLog(Name, a, "Deposit", bal));
			saveLogs();
		}else {
			startEconLogging();
		}
		save();

	}

	public void depositSaving(String Name, double a) {
		EconDepositEvent ev = new EconDepositEvent(Name, a);
        Bukkit.getServer().getPluginManager().callEvent(ev);
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		Double bal = config.getDouble("PlayerData." + Name + ".Economy.Savings");
		bal = bal + a;
		config.set("PlayerData." + Name + ".Economy.Savings", bal);
		if (logging) {
			LS.add(new EconLog(Name, a, "DepositSaving", bal));
			saveLogs();
		}else {
			startEconLogging();
		}
		save();

	}
	
	public void depositChecking(String Name, double a) {
		EconDepositEvent ev = new EconDepositEvent(Name, a);
        Bukkit.getServer().getPluginManager().callEvent(ev);
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		Double bal = config.getDouble("PlayerData." + Name + ".Economy.Checking");
		bal = bal + a;
		config.set("PlayerData." + Name + ".Economy.Checking", bal);
		if (logging) {
			LS.add(new EconLog(Name, a, "DepositChecking", bal));
			saveLogs();
		}else {
			startEconLogging();
		}
		save();

	}
	
	public void depositTax(double a) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		Double bal = config.getDouble("PlayerData.Server.Economy.Checking");
		bal = bal + a;
		config.set("PlayerData.Server.Economy.Checking", bal);
		if (logging) {
			LS.add(new EconLog("Server", a, "Deposit", bal));
			saveLogs();
		}else {
			startEconLogging();
		}
		save();

	}
	
	public void withdrawTax(double a) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		Double bal = config.getDouble("PlayerData.Server.Economy.Checking");
		bal = bal - a;
		config.set("PlayerData.Server.Economy.Checking", bal);
		if (logging) {
			LS.add(new EconLog("Server", a, "Withdraw", bal));
			saveLogs();
		}else {
			startEconLogging();
		}
		save();

	}
	
	public void setTax(double a) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		Double bal = config.getDouble("PlayerData.Server.Economy.Checking");
		bal = a;
		config.set("PlayerData.Server.Economy.Checking", bal);
		if (logging) {
			LS.add(new EconLog("Server", a, "Deposit", bal));
			saveLogs();
		}else {
			startEconLogging();
		}
		save();

	}
	
	public double getTaxBal(double a) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		if (config == null) {
			LoadConfig();
		}
		Double bal = config.getDouble("PlayerData.Server.Economy.Checking");
		return bal;
	}

	public void save() {
		try {
			Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
	    } catch (Exception e) {
	    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    }
	}

	public void addPayment(String N) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		config.set("PlayerData.Econ." + N + ".Credit.Payments",
				config.getInt("PlayerData.Econ." + N + ".Credit.Payments") + 1);
		save();

	}
	
	public int getPayments(String N) {
		return config.getInt("PlayerData.Econ." + N + ".Credit.Payments");
	}
	
	public int getLatePayments(String N) {
		return config.getInt("PlayerData.Econ." + N + ".Credit.LatePayments");
	}

	public void addLatePayment(String N) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		config.set("PlayerData.Econ." + N + ".Credit.LatePayments",
				config.getInt("PlayerData.Econ." + N + ".Credit.LatePayments") + 1);
		save();

	}
	
	public void addLatePayments(String N, int A) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		config.set("PlayerData.Econ." + N + ".Credit.LatePayments",
				config.getInt("PlayerData.Econ." + N + ".Credit.LatePayments") + A);
		save();

	}
	
	public void setLatePayments(String N, int A) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		config.set("PlayerData.Econ." + N + ".Credit.LatePayments", A);
		save();

	}
	
	public void addPayments(String N, int A) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		config.set("PlayerData.Econ." + N + ".Credit.LatePayments",
				config.getInt("PlayerData.Econ." + N + ".Credit.Payments") + A);
		save();

	}
	
	public void setPayments(String N, int A) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		config.set("PlayerData.Econ." + N + ".Credit.Payments",A);
		save();

	}

	public void addAcc(String N) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		config.set("PlayerData.Econ." + N + ".Credit.Accounts",
				config.getInt("PlayerData.Econ." + N + ".Credit.Accounts") + 1);
		save();

	}
	
	public void setAcc(String N, int A) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		config.set("PlayerData.Econ." + N + ".Credit.Accounts",A);
		save();

	}
	
	public int getAcc(String N) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
				return config.getInt("PlayerData.Econ." + N + ".Credit.Accounts");
	}

	public void removeAcc(String N) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		config.set("PlayerData.Econ." + N + ".Credit.Accounts",
				config.getInt("PlayerData.Econ." + N + ".Credit.Accounts") - 1);
		save();

	}

	public double getScore(String N) {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		double score;
		int pmts = config.getInt("PlayerData.Econ." + N + ".Credit.Payments");
		int acc = config.getInt("PlayerData.Econ." + N + ".Credit.Accounts");
		int lpmts = config.getInt("PlayerData.Econ." + N + ".Credit.LatePayments");

		double sm = pmts * pm;
		sm = sm + (acc * am);
		// sm = sm/(lpmts*lpm);
		score = base * sm;
		
		config.set("PlayerData.Econ."+N+".Credit.Score", score);
		save();

		return score;
	}
	
	public void addScore(String N, double sc) {
		
		double score = config.getDouble("PlayerData.Econ."+N+".Credit.Score");
		score = score +sc;
		config.set("PlayerData.Econ."+N+".Credit.Score", score);
		save();
		
	}
	
	public void removeScore(String N, double sc) {
		
		double score = config.getDouble("PlayerData.Econ."+N+".Credit.Score");
		score = score +sc;
		config.set("PlayerData.Econ."+N+".Credit.Score", score);
		save();
	}
	
	public void setScore(String N, double sc) {
		
		
		config.set("PlayerData.Econ."+N+".Credit.Score", sc);
		save();
	}
	
	public void createCreditCard(String N, String ID) {
		
		config.set("PlayerData.Econ."+N+".Credit.Card.ID", ID);
		config.set("PlayerData.Econ."+N+".Credit.Card.Exists", 1);
		config.set("PlayerData.Econ."+N+".Credit.Card.Limit", 0);
		save();

	}
	
	public void setCreditLimit(String N, int limit) {
		
		config.set("PlayerData.Econ."+N+".Credit.Card.Limit", limit);
		
		save();

	}
	
	public int getCreditLimit(String N) {
		
		return config.getInt("PlayerData.Econ."+N+".Credit.Card.Limit");

	}
	
	public double getCreditBalance(String N) {
		
		return config.getDouble("PlayerData.Econ."+N+".Credit.Card.Balance");

	}
	
	public void setCreditBalance(String N, int am) {
		
		config.set("PlayerData.Econ."+N+".Credit.Card.Balance", am);
		
		save();

	}
	
	public void addCreditBalance(String N, int am) {
		
		double bl = config.getDouble("PlayerData.Econ."+N+".Credit.Card.Balance");
		
		bl = bl+am;
		
		config.set("PlayerData.Econ."+N+".Credit.Card.Balance", bl);
		
		save();

	}
	
	public void removeCreditBalance(String N, int am) {
		
		double bl = config.getDouble("PlayerData.Econ."+N+".Credit.Card.Balance");
		
		bl = bl-am;
		
		config.set("PlayerData.Econ."+N+".Credit.Card.Balance", bl);
		
		save();

	}
	
	public boolean isBusiness(String n) {
    	
    	
    	List<String> bso = config.getStringList("PlayerData.BusinessOwners");
    	if (bso.contains(n)) {
    		return true;
    	}else {
    		return false;
    	}
    	
    }
    
    public boolean isCorp(String n) {
    	
    	
    	List<String> cso = config.getStringList("PlayerData.CorpOwners");
    	if (cso.contains(n)) {
    		return true;
    	}else {
    		return false;
    	}
    	
    }
    
    
    public void addCashFlow(String P,double m) {
    	
    	double cf = config.getDouble("PlayerData."+P+".CashFlow");
    	double cft = config.getDouble("PlayerData."+P+".TotalCashFlow");
    	
    	cf = cf + m;
    	cft = cft+m;
    	
    	config.set("PlayerData."+P+".CashFlow", cf);
    	config.set("PlayerData."+P+".TotalCashFlow", cft);
    	save();
    	
    }
    
    public double getCashFlow(String N) {
    	return config.getDouble("PlayerData."+N+".CashFlow");
    }
    
    public void setCashFlow(String P,double m) {
    	
    	config.set("PlayerData."+P+".CashFlow", m);
    	save();
    	
    }
    
    public double getTaxBracket(String N) {
    	
    	double cf = getCashFlow(N);
    	double txb = 0;
    	
    	if (cf >= 1000000) {
    		txb = 14.7;
    	}else if (cf >= 100000 && cf < 999999) {
    		txb = 12.6;
    	}else if (cf >= 99999 && cf < 9999) {
    		txb = 10.6;
    	}else {
    		txb = 9.2;
    	}
    		
    	
    	
    	
    	return txb;
    }


	
	public void saveLogs() {
		
		//config.set("EconLogs", LS);
		List<Integer> LI = new ArrayList();
		for (EconLog EL : LS) {
			config.set("EconLogs."+LS.indexOf(EL)+".Name", EL.getName());
			config.set("EconLogs."+LS.indexOf(EL)+".Ammount", EL.getAmmount());
			config.set("EconLogs."+LS.indexOf(EL)+".Type", EL.getType());
			config.set("EconLogs."+LS.indexOf(EL)+".Bal", EL.getBal());
			LI.add(LS.indexOf(EL));
		}
		 config.set("EconLogList", LI);
		save();
		clearLogs();
		
	}
	
	public void clearLogs() {
		LS = new ArrayList();
	}
	

}
