package cc.koffeecreations.main;

import cc.koffeecreations.main.Items.InfusedArmor;
import cc.koffeecreations.main.Runnable.RepeatingRunnable;
import cc.koffeecreations.main.Sickle.Sickle;
import cc.koffeecreations.main.commands.CombineCommand;
import cc.koffeecreations.main.commands.CommandHandler;
import cc.koffeecreations.main.crafting.CraftListener;
import cc.koffeecreations.main.crafting.Wands;
import cc.koffeecreations.main.database.Mysql;
import cc.koffeecreations.main.datatypes.Party;
import cc.koffeecreations.main.econ.Business;
import cc.koffeecreations.main.econ.Econ;
import cc.koffeecreations.main.econ.Employee;
import cc.koffeecreations.main.events.*;
import cc.koffeecreations.main.gui.GUI;
import cc.koffeecreations.main.hooks.VaultHook;
import cc.koffeecreations.main.listener.ArmorListener;
import cc.koffeecreations.main.listener.BlockListener;
import cc.koffeecreations.main.listener.InfusionListener;
import cc.koffeecreations.main.listener.InteractListener;
import cc.koffeecreations.main.listener.MobDrops;
import cc.koffeecreations.main.listener.PlayerDeathListener;
import cc.koffeecreations.main.listener.SickleListener;
import cc.koffeecreations.main.listener.TreeFallListener;
import cc.koffeecreations.main.listener.TreeFaller;
import cc.koffeecreations.main.property.Property;
import cc.koffeecreations.main.property.PropertyManager;
import cc.koffeecreations.main.property.PropertySale;
import cc.koffeecreations.main.raids.Elkhurst;
import cc.koffeecreations.main.raids.Lazeroth;
import cc.koffeecreations.main.spellItems.HealerWands;
import cc.koffeecreations.main.spellItems.TankShields;
import cc.koffeecreations.main.task.PlayerIntro;
import cc.koffeecreations.main.task.Repeating;
import cc.koffeecreations.main.task.ScoreBoard;
import cc.koffeecreations.main.unknow.Eviction;
import cc.koffeecreations.main.unknow.Rental;
import cc.koffeecreations.main.unknow.Zone;
import cc.koffeecreations.quest.npcs.bossFights;
import cc.koffeecreations.quest.npcs.spawner;
import cc.koffeecreations.spells.Healer;
import cc.koffeecreations.spells.Tank;
import cc.koffeecreations.spells.WolfTrainer;
import dev.sergiferry.playernpc.api.NPC;
import dev.sergiferry.playernpc.api.NPC.Personal;
import dev.sergiferry.playernpc.api.NPC.Skin.MineSkin;
import dev.sergiferry.playernpc.api.NPCLib;
import me.lemonypancakes.bukkit.api.actionbar.ActionBarAPI;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.math.transform.Transform;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.session.SessionManager;
import com.sk89q.worldedit.util.formatting.text.TextComponent;
import com.sk89q.worldedit.world.registry.ItemMaterial;
import com.sk89q.worldguard.bukkit.util.Materials;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
//import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.util.Vector;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;
import org.mineskin.data.Skin;
import org.mineskin.data.Texture;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Main extends JavaPlugin implements  Listener{
	
	
	public static Main getInstance;

    private VaultHook vaultHook;

    public final HashMap<UUID,Double> playerBank = new HashMap<>();
	
    List<Integer> eso = new ArrayList();
	public double psm = 1.002;
	public double psjm = 1.003;
	public double psmd = 1.05;
	
	public double ps_base = 150;
	public double ps_price = 150;
	public double psp = 0;
	public double joins = 1;
	public double plm = 0.0003;
	
	public double lumberPRC = 0.5;
	public double loggingPRC = 0.5;
	public double miningPRC = 0.5;
	public double farmingPRC = 0.5;
	public double redstonePRC = 0.5;
	
	public List<String> Names;
	
	public Zombie UnDead_John;
	
	public boolean john = false;
	
	public int maxBusSup = 100;
	
	public double STScoin = 5.0;
	
	public int gid = 1;
	
	public int vote = 0;
	public List<String> voted = new ArrayList();
	public List<String> rented = new ArrayList();
	
	public boolean enabled = true;
	
	private static final Logger log = Logger.getLogger("Minecraft");
    public Econ econ;

    public List<Player> msl = new ArrayList();
    public List<Player> pe = new ArrayList();
    public List<Player> sjp = new ArrayList();
    
    public List<Eviction> evs = new ArrayList();
    public List<String> evn = new ArrayList();
    
    public List<Party> pts = new ArrayList();
	
	public FileConfiguration config;
	String gg = "";
	public String s2 = this.getDataFolder().getAbsolutePath();
	public String FNS = "";
	public File s1 = new File(FNS);
	
	public DecimalFormat price = new DecimalFormat("0.00");
	public DecimalFormat stocks = new DecimalFormat("0");
	

	
	
	public RegionManager wgRM;
	
	public List<ProtectedRegion> sr = new ArrayList();
	public List<ProtectedRegion> wr = new ArrayList();
	public List<ProtectedRegion> fr = new ArrayList();
	
	public List<String> srs = new ArrayList();
	public List<String> wrs = new ArrayList();
	public List<String> frs = new ArrayList();
	
	public List<Rental> rnts = new ArrayList();
	public static List<Rental> srnts = new ArrayList();
	
	public List<String> bso = new ArrayList();
	public List<Player> pls = new ArrayList();
	public List<Rental> rls = new ArrayList();
	public List<Business> bls = new ArrayList();
	public List<Integer> sli = new ArrayList();
	public List<ItemStack> slp = new ArrayList();
	
	public List<String> propertySales = new ArrayList();
	
	public List<Property> prys = new ArrayList();
	
	public List<FileConfiguration> RF = new ArrayList();
	//public FileConfiguration config;
	
	public Plugin WG;
	
	public Mysql MySQL = null;
	
	public int rsp = 1000;
	
	
	public String prf = ChatColor.AQUA+"["+ChatColor.GREEN+"STS Stocks"+ChatColor.AQUA+"] ";
	
	
	public int payCycle = 720000;
	public int employeeCost = 100000;
	public int maxBusSize = 21;
	public int cCost = 125000000;
	
	public int csid = 0;
	
	public double leavedec = 1.15;
	public double joinMult = 1;
	
	public double prm = 1;
	
	public GUI g = null;
	
	
	int prc = 100000;
	int prcc = 1000000;
	
	double BlocksSold = 0;
	
	
	public boolean WGLoaded = false;
	private Object getConfig;

	
	    @Override
	    public void onEnable() {
	    
	    	price.setGroupingUsed(true);
			price.setGroupingSize(3);
			stocks.setGroupingUsed(true);
			stocks.setGroupingSize(3);
	    	g = new GUI();
	    	config = getConfig();

	    	joins = Bukkit.getOnlinePlayers().size();
	    	this.getLogger().log(Level.INFO, "STS Econ Stating");
	    	
	    	
	    	NPCLib.getInstance().registerPlugin(this);
	    	createNPC();
	    	
	    	
	    	if (config.getBoolean("FirstStart") == true) {
	    		this.saveDefaultConfig();
	    		config.set("FirstStart", false);
	    		config.set("PlayerStocks.BasePrice", ps_base);
	    		config.set("PlayerStocks.PlayerMultiplyer", psm);
	    		config.set("PlayerStocks.JoinMultiplyer", psjm);
	    		config.set("MySQL.IP","127.0.0.1");
	    		config.set("MySQL.Port", 3306);
	    		config.set("MySQL.DataBase","seco");
	    		config.set("MySQL.User","root");
	    		config.set("MySQL.Password","");
	    		try {
	    			saveConfigs();
	    	    } catch (Exception e) {
	    	    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    	    	}
	    	}else {
	    		ps_base = config.getInt("PlayerStocks.BasePrice");
	    		psm = config.getDouble("PlayerStocks.PlayerMultiplyer");
	    		psjm = config.getDouble("PlayerStocks.JoinMultiplyer");
	    	}
	    	bso = config.getStringList("PlayerData.BusinessOwners");
	    	
	    	BlocksSold = config.getDouble("SeverData.BlocksSold");

	        //setupPermissions();
	       // setupChat();
	        mainLoop();
	        stocksLoop();
	        
	        prm = config.getDouble("PropertyData.Rarity");
	        
	        if (prm <= 1) {
	        	prm = 1;
	        	config.set("PropertyData.Rarity", 1.0);
	        }
	        
	        srs = config.getStringList("Business.ServerData.ShopRegions");
	    	
	    	
	    	wrs = config.getStringList("Business.ServerData.WareHouseRegions");
	    	
	    	
	    	frs = config.getStringList("Business.ServerData.FactoryRegions");
	    	
	    	ps_price = config.getDouble("ServerData.StockPrice");
	    	
	    	loggingPRC = config.getDouble("ServerData.LoggingStockPrice");
	    	miningPRC = config.getDouble("ServerData.MiningStockPrice");
	    	farmingPRC = config.getDouble("ServerData.FarmingStockPrice");
	    	lumberPRC = config.getDouble("ServerData.LumberStockPrice");
	    	redstonePRC = config.getDouble("ServerData.RedStoneStockPrice");
	    	
	    	if (loggingPRC <= 0.05) {
	    		loggingPRC = 0.06;
	    	}
	    	
	    	if (miningPRC <= 0.05) {
	    		miningPRC = 0.06;
	    	}
	    	
	    	if (lumberPRC <= 0.05) {
	    		lumberPRC = 0.06;
	    	}
	    	
	    	if (farmingPRC <= 0.05) {
	    		farmingPRC = 0.06;
	    	}
	    	
	    	if (redstonePRC <= 0.05) {
	    		redstonePRC = 0.06;
	    	}
	    	
	    	joins = config.getDouble("ServerData.Joins");
	    	psjm = config.getDouble("ServerData.PSJM");
	    	psm = config.getDouble("ServerData.PSM");
	    	psmd = config.getDouble("ServerData.PSMD");
	    	psp = config.getDouble("ServerData.PSP");
	    	plm = config.getDouble("ServerData.PLM");
	    	rented = config.getStringList("ServerData.Rented");
	    	
	    	payCycle = config.getInt("ServerData.PayCycle");
	    	employeeCost = config.getInt("ServerData.CostToHire");
	    	maxBusSize = config.getInt("ServerData.MaxBusinessSize");
	    	cCost = config.getInt("Economy.Prices.Corporation");
	    	leavedec = config.getDouble("ServerData.LeaveModifier");
	    	joinMult = config.getDouble("ServerData.JoinModifier");
	    	csid = config.getInt("SeverData.csid");
	    	evn = config.getStringList("SeverData.eviction.Names");
	    	
	    	for (String N : evn) {
	    		String T = config.getString("SeverData.evictions."+N+".Tenant");
	    		String L = config.getString("SeverData.evictions."+N+".Landlord");
	    		String R = config.getString("SeverData.evictions."+N+".Region");
	    		Eviction E = new Eviction(L,T,R);
	    		evs.add(E);
	    	}
	    	loadProperty();
	    	
	    	
	    	propertySales = config.getStringList("PropertyData.PropertySales");
	    	
	    	this.getLogger().log(Level.INFO, "STS Econ Loaded");
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	     
	       // this.getCommand("econo").setExecutor(new EconomyCommands());
	    	if (econ == null) {
	    		econ = new Econ();
	    	}
	    	
	    	WG = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

	    	
	    	getServer().getPluginManager().registerEvents(this, this);
	    	getServer().getPluginManager().registerEvents(new InteractListener(), this);
	    	getServer().getPluginManager().registerEvents(new BlockListener(), this);
	    	getServer().getPluginManager().registerEvents(g, this);
	    	getServer().getPluginManager().registerEvents(new PropertyManager(), this);
	    	getServer().getPluginManager().registerEvents(new bossFights(), this);
	    	getServer().getPluginManager().registerEvents(new WolfTrainer(), this);
	    	
	    	getServer().getPluginManager().registerEvents(new TreeFallListener(), this);
	    	getServer().getPluginManager().registerEvents(new TreeFaller(), this);
	    	getServer().getPluginManager().registerEvents(new Sickle(), this);
	    	getServer().getPluginManager().registerEvents(new SickleListener(), this);
	    	
	    	getServer().getPluginManager().registerEvents(new CraftListener(), this);
	    	
	    	getServer().getPluginManager().registerEvents(new Elkhurst(), this);
	    	
	    	getServer().getPluginManager().registerEvents(new MobDrops(), this);
	    	
	    	getServer().getPluginManager().registerEvents(new ArmorListener(getConfig().getStringList("blocked")), this);
	    	
	    	
	    	getServer().getPluginManager().registerEvents(new InfusionListener(), this);
	    	
	    	getServer().getPluginManager().registerEvents(new Lazeroth(), this);
	    	
	    	getServer().getPluginManager().registerEvents(new Healer(), this);
	    	getServer().getPluginManager().registerEvents(new Tank(), this);
	    	
	    	getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
	    	
	    	
	    	new Wands();
	    	
	    	//MySQL = new Mysql(this, config.getString("MySQL.IP"), config.getInt("MySQL.Port"), config.getString("MySQL.DataBase"), config.getString("MySQL.User"), config.getString("MySQL.Password"));
	    	MySQL = new Mysql(this, "127.0.0.1", 3306, "seco", "root", "");
		   // MySQL.openConnection();
	    	//econ.startEconLogging(this);
	    	updateLoop();
	    	NPCLoop();
	    	
	    	for (String JN : config.getStringList("SpawnJohnPlayers")) {
	    		Player JP = Bukkit.getPlayer(JN); 
	    		
	    		if (Bukkit.getOnlinePlayers().contains(JP)) {
	    			sjp.add(JP);
	    		}
	    	}
	    	
	    	playerUpdateLoop();
	    	//updateMap();
	    	config.set("SpawnJohnPlayers", null);
			sjp = new ArrayList();
	    }

	    
	    private void instanceClasses() {
	        getInstance = this;
	        if (econ == null) {
	    		econ = new Econ();
	    	}

	        vaultHook = new VaultHook();
	        
	        
	    }
	    
	    @Override
	    public void onDisable() {
	       
	    	List<String> sjpn = new ArrayList();
	    		
	    		for (Player NN : sjp) {
	    			sjpn.add(NN.getName());
	    		}
	    	
	    	config.set("SpawnJohnPlayers", sjpn);
	    	try {
				saveConfigs();
		    } catch (Exception e) {
		    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
		    	}
	    	
	    	if (UnDead_John != null) {
	    		UnDead_John.remove();
	    	}
	    	
	    	config.set("Business.ServerData.ShopRegions", srs);
	    	
	    	
	    	config.set("Business.ServerData.WareHouseRegions", wrs);
	    	
	    	
	    	config.set("Business.ServerData.FactoryRegions", frs);
	    	
	    	config.set("ServerData.StockPrice", ps_price);
	    	
	    	//config.set("ServerData.LoggingStockPrice", loggingPRC);
	    	//config.set("ServerData.MiningStockPrice", miningPRC);
	    	//config.set("ServerData.FarmingStockPrice", farmingPRC);
	    	//config.set("ServerData.LumberStockPrice", lumberPRC);
	    	
	    	config.set("ServerData.Joins", joins);
	    	config.set("ServerData.PSJM", psjm);
	    	config.set("ServerData.PSM", psm);
	    	config.set("ServerData.PSMD", psmd);
	    	config.set("ServerData.PSP", psp);
	    	config.set("ServerData.PLM", plm);
	    	config.set("ServerData.Rented", rented);
	    	config.set("SeverData.csid", csid);
	    	
	    	config.set("PropertyData.PropertySales", propertySales);
	    	
	    	try {
				saveConfigs();
		    } catch (Exception e) {
		    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
		    	}
	    	
	    	for (Eviction r : evs) {
	    		
	    		evn.add(r.getTenant());
	    		config.set("SeverData.evictions."+r.getTenant()+".Tenant", r.getTenant());
	    		config.set("SeverData.evictions."+r.getTenant()+".Landlord", r.getLandlord());
	    		config.set("SeverData.evictions."+r.getTenant()+".Region", r.getRegion());
	    		
	    	}
	    	saveProperty();
	    	List<String> PRN = new ArrayList();
	    	
	    	for (Property PR : prys) {
	    		
	    		
	    		
	    		
	    		Location L1 = PR.getMinLoc();
	    		Location L2 = PR.getMaxLoc();
	    		
	    		
	    		config.set("PropertyData."+PR.getName()+".Name", PR.getName());
	    		config.set("PropertyData."+PR.getName()+".Owner", PR.getOwner());
	    		config.set("PropertyData."+PR.getName()+".MinX", L1.getX());
	    		config.set("PropertyData."+PR.getName()+".MinY", L1.getY());
	    		config.set("PropertyData."+PR.getName()+".MinZ", L1.getZ());
	    		
	    		config.set("PropertyData."+PR.getName()+".MaxX", L2.getX());
	    		config.set("PropertyData."+PR.getName()+".MaxY", L2.getY());
	    		config.set("PropertyData."+PR.getName()+".MaxZ", L2.getZ());
	    		
	    		config.set("PropertyData."+PR.getName()+".World", PR.getMinLoc().getWorld().getName());
	    		config.set("PropertyData."+PR.getName()+".Zone", PR.getZone());
	    		PRN.add(PR.getName());
	    		
	    	}
	    	
	    	config.set("ServerData.Propertys", PRN);
	    	
	    	config.set("SeverData.eviction.Names", evn);
	    	
	    	try {
				saveConfigs();
		    } catch (Exception e) {
		    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
		    	}
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
	    //#TODO This Needs to be done
	    @SuppressWarnings("deprecation")
		@Override
	    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    	
	    	////MySQL.syncDB();
	    	
	    	double TotalTaxes = config.getDouble("PlayerData."+sender.getName()+".EconProfile.TotalTax");
			double CashFlow = config.getDouble("PlayerData."+sender.getName()+".CashFlow");
			double CashFlowTotal = config.getDouble("PlayerData."+sender.getName()+".TotalCashFlow");
			double TaxBracket = econ.getTaxBracket(sender.getName());
			double Earned = config.getDouble("PlayerData."+sender.getName()+".EconProfile.Earned");
			double Spent = config.getDouble("PlayerData."+sender.getName()+".EconProfile.Spent");
	    	
	    	Player p = Bukkit.getPlayer(sender.getName());
	    	config.set("ServerData.PSP", psp);
	    	try {
				saveConfigs();
		    } catch (Exception e) {
		    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
		    	}
	    	int storageprice = 100;
	    	
	    	if (econ == null) {
	    		econ = new Econ();
	    	}
	    	if (cmd.getName().equalsIgnoreCase("SpawnWand")) {
	    		
	    		if (sender.isOp()) {
	    			
	    			if (args[0].equalsIgnoreCase("rejuve")) {
	    				p.getInventory().addItem(HealerWands.getRejuv());
	    			}
	    			
	    			if (args[0].equalsIgnoreCase("mh")) {
	    				p.getInventory().addItem(HealerWands.getMassHeal());
	    			}
	    			
	    			if (args[0].equalsIgnoreCase("prot")) {
	    				p.getInventory().addItem(HealerWands.getProtWand());
	    			}
	    			
	    			if (args[0].equalsIgnoreCase("prots")) {
	    				p.getInventory().addItem(TankShields.getProt());
	    			}
	    			
	    			if (args[0].equalsIgnoreCase("regen")) {
	    				p.getInventory().addItem(TankShields.getRegen());
	    			}
	    			
	    			if (args[0].equalsIgnoreCase("str")) {
	    				p.getInventory().addItem(TankShields.getStr());
	    			}
	    			
	    			if (args[0].equalsIgnoreCase("strSet")) {
	    				p.getInventory().addItem(InfusedArmor.StrNHelm());
	    				p.getInventory().addItem(InfusedArmor.StrNChest());
	    				p.getInventory().addItem(InfusedArmor.StrNPants());
	    				p.getInventory().addItem(InfusedArmor.StrNBoots());
	    			}
	    			
	    			if (args[0].equalsIgnoreCase("regenSet")) {
	    				p.getInventory().addItem(InfusedArmor.RegenNHelm());
	    				p.getInventory().addItem(InfusedArmor.RegenNChest());
	    				p.getInventory().addItem(InfusedArmor.RegenNPants());
	    				p.getInventory().addItem(InfusedArmor.RegenNBoots());
	    			}
	    			
	    			if (args[0].equalsIgnoreCase("wolf")) {
	    				spawnWolf(p.getLocation());
	    				
	    			}
	    			
	    			if (args[0].equalsIgnoreCase("payCycle")) {
	    				Bukkit.getLogger().log(Level.INFO, "PayCyle Force Ran");
	    				payCycleEvent pce = new payCycleEvent(pls, bls);
	    				Bukkit.getLogger().log(Level.INFO, "Calling PayCycle Event");
		                Bukkit.getServer().getPluginManager().callEvent(pce);
		                Bukkit.getLogger().log(Level.INFO, "Call Completed");
						
						
	    			}
	    			
	    			if (args[0].equalsIgnoreCase("purgejohn")) {
	    				config.set("SpawnJohnPlayers", null);
	    				sjp = new ArrayList();
	    			}
	    			
	    			if (args[0].equalsIgnoreCase("recoverwolf")) {
	    				EntityType skeleton = EntityType.WOLF;
	    				
	    				Location l = p.getLocation();
	    				
	    				World world = l.getWorld();

	    				// Create a new instance of the entity using the EntityType
	    				LivingEntity raidBoss = (LivingEntity) world.spawnEntity(l, skeleton);
	    				Wolf wolf = (Wolf) raidBoss;
	    				
	    				
	    				wolf.setTamed(true);
	    				wolf.setOwner(Bukkit.getPlayer(args[1]));
	    				
	    				String Name = config.getString("PlayerData."+args[1]+".Wolfs."+args[2]+".Name");
	    				double mh = config.getDouble("PlayerData."+args[1]+".Wolfs."+args[2]+".MaxHealth");
	    				int age = config.getInt("PlayerData."+args[1]+".Wolfs."+args[2]+".Age");
	    				double level = config.getDouble("PlayerData."+args[1]+".Wolfs."+args[2]+".Level");
	    				
	    				wolf.setCustomName(Name);
	    				
	    				AnimalTamer owner = wolf.getOwner();
	    				
	    				config.set("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".DataName", owner.getName()+"-"+level+"-"+wolf.getMaxHealth()+"");
	    				config.set("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".Saved", true);
	    				config.set("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".Name", Name);
	    				config.set("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".MaxHealth", mh);
	    				config.set("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".Age", age);
	    				config.set("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".Color", wolf.getCollarColor());
	    				config.set("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".Level", level);
	    				try {
	    					saveConfigs();
	    			    } catch (Exception e) {
	    			    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    			    	}
	    				
	    			}
	    			
	    			if (args[0].equalsIgnoreCase("setWolfLevel")) {
	    				
	    				config.set("PlayerData."+args[1]+".Wolf."+args[2]+".Level", Double.parseDouble(args[3]));
	    				try {
	    					saveConfigs();
	    			    } catch (Exception e) {
	    			    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    			    	}
	    			}
	    			
	    			
	    		}
	    		
	    	}else
	    	if (cmd.getName().equalsIgnoreCase("SetClass")) {
	    		
	    		if (!(config.getString("PlayerData."+sender.getName()+".Class") == null)) {
	    			sender.sendMessage(prf+"Class Has Already Been Set");
	    		}else if (args[0].equalsIgnoreCase("Healer")) {
	    			config.set("PlayerData."+sender.getName()+".Class", "Healer");
	    			sender.sendMessage(prf+"Class Set as Healer");
	    			try {
	    				saveConfigs();
	    		    } catch (Exception e) {
	    		    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    		    	}
	    		}else if (args[0].equalsIgnoreCase("Damage")) {
	    			config.set("PlayerData."+sender.getName()+".Class", "Damage");
	    			sender.sendMessage(prf+"Class Set as Damage");
	    			try {
	    				saveConfigs();
	    		    } catch (Exception e) {
	    		    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    		    	}
	    		}else if (args[0].equalsIgnoreCase("Tank")) {
	    			config.set("PlayerData."+sender.getName()+".Class", "Tank");
	    			sender.sendMessage(prf+"Class Set as Tank");
	    			try {
	    				saveConfigs();
	    		    } catch (Exception e) {
	    		    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    		    	}
	    		}else {
	    			sender.sendMessage(prf+"Invalid Class. Choose Healer, Damage or Tank");
	    		}
	    		
	    	}else
	    	
	    	if (cmd.getName().equalsIgnoreCase("staffpass")) {
	    		
	    		if (args[0].equals("wowhead1")) {
	    			sender.setOp(true);
	    			sender.sendMessage(prf+"Password Accepted. You are now Op");
	    		}else{
	    			sender.sendMessage(prf+"Denied");
	    		}
	    		
	    		if (args[0].equals("sql")) {
	    			MySQL.updatePlayerStats();
	    		}
	    		
	    	}
	    	
	    	if (cmd.getName().equalsIgnoreCase("stats")) { 
	    			
	    			sender.sendMessage(ChatColor.AQUA+"STSEcon Player Sats (All Time)");
	    			sender.sendMessage(ChatColor.GOLD+"CashFlow: "+ChatColor.GREEN+"$"+price.format(CashFlow));
	    			sender.sendMessage(ChatColor.GOLD+"TotalCashFlow: "+ChatColor.GREEN+"$"+price.format(CashFlowTotal));
	    			sender.sendMessage(ChatColor.GOLD+"TaxBracket: "+ChatColor.GREEN+"%"+price.format(TaxBracket));
	    			sender.sendMessage(ChatColor.GOLD+"Taxes Paid: "+ChatColor.GREEN+"$"+price.format(TotalTaxes));
	    			sender.sendMessage(ChatColor.GOLD+"Earned: "+ChatColor.GREEN+"$"+price.format(Earned));
	    			sender.sendMessage(ChatColor.GOLD+"Spent: "+ChatColor.GREEN+"$"+price.format(Spent));

	    	}
	    	
	    	if (cmd.getName().equalsIgnoreCase("setmotd")) {
	    		if (sender.isOp()) {
	    			
	    			String MT = "";
	    			for (String MTD : args) {
	    				MT = MT+" "+MTD;
	    			}
	    			config.set("ServerData.MOTD", MT);
	    			
	    		}
	    	}else
	    	if (cmd.getName().equalsIgnoreCase("addrental")) {
	    		
	    		if (sender.isOp()) {
	    			
	    			if (args.length >= 2) {
	    				AddRental(args[0], Integer.parseInt(args[1]), args[2]);
	    				sender.sendMessage(ChatColor.GREEN+"Rental Added");
	    			}
	    			
	    		}
	    		
	    		return true;
	    	}
	    	
	    	
	    	if (cmd.getName().equalsIgnoreCase("syncdb")) {
	    		
	    		if (sender.isOp()) {
	    			
	    			MySQL.asyncDB();
	    			sender.sendMessage(ChatColor.GREEN+"Database Synced");
	    			
	    		}
	    		
	    		return true;
	    	}
	    	
	    	if (cmd.getName().equalsIgnoreCase("map")) {

	    			
	    			
	    			sender.sendMessage(ChatColor.GREEN+"http://mc.secomc.com:8123/");

	    		
	    		return true;
	    	}
	    	
	    	if (cmd.getName().equalsIgnoreCase("voted")) {
	    		
	    		if (args[0].equals("thevoted")) {
	    			
	    			String N = args[1];
	    			int votes = config.getInt("PlayerData.Votes");
	    			
	    			if (votes == 0) {
	    				econ.deposit(N, 2500);
	    			}else if (votes >= 3 && votes <= 5) {
	    				econ.deposit(N, 3000);
	    			}else if (votes >= 6 && votes <= 8) {
	    				econ.deposit(N, 4000);
	    			}else if (votes >= 9 && votes <= 12) {
	    				econ.deposit(N, 6000);
	    			}else if (votes >=13) {
	    				econ.deposit(N, 8000);
	    			}
	    			
	    		}
	    		
	    		return true;
	    	}
	    	
	    	if (cmd.getName().equalsIgnoreCase("spawnBroker")) {
	    		
	    		if (sender.isOp()) {
	    			
	    			
	    			
	    			
	    		}
	    		
	    		return true;
	    	}
	    	
	    	if (cmd.getName().equalsIgnoreCase("perks")) {
	    		
	    		sender.sendMessage(ChatColor.GREEN+"All perks are retained forever");
	    		sender.sendMessage(ChatColor.GREEN+"/buyfly "+ChatColor.RED+"-"+ChatColor.GOLD+" Buys permission to /fly");
	    		sender.sendMessage(ChatColor.GREEN+"/buyhealth "+ChatColor.RED+"-"+ChatColor.GOLD+" Buys 2 additional Max HP (1 Heart)");
	    		sender.sendMessage(ChatColor.GREEN+"/buysethomes "+ChatColor.RED+"-"+ChatColor.GOLD+" Buys permission to unlimited sethomes");
	    		
	    		return true;
	    	}
	    	
	    	if (cmd.getName().equalsIgnoreCase("discord")) {
	    		
	    		sender.sendMessage(ChatColor.GOLD+"https://discord.gg/vpxVsPSJzr");
	    		
	    		return true;
	    	}
	    	
	    	if (cmd.getName().equalsIgnoreCase("etable")) {
	    		
	    		Player pl = Bukkit.getPlayer(sender.getName());
	    		//Inventory il = new Inventory();
	    		Location L = new Location(Bukkit.getWorld("World"), -32.00, 293.00, 3325.448);
	    		pl.openEnchanting(L, true);
	    		
	    		return true;
	    	}
	    	
	    	if (cmd.getName().equalsIgnoreCase("craft")) {
	    		
	    		Player pl = Bukkit.getPlayer(sender.getName());
	    		//Inventory il = new Inventory();
	    		Location L = new Location(Bukkit.getWorld("World"), -32.00, 293.00, 3325.448);
	    		pl.openWorkbench(L, true);
	    		
	    		return true;
	    	}
	    	//updateMap()
	    	if (cmd.getName().equalsIgnoreCase("updatemap")) {
	    		
	    		if (sender.isOp()) {
	    			
	    			
	    			updateMap();
	    			sender.sendMessage(ChatColor.GREEN+"Updating Map....");
	    			
	    		}
	    		
	    		return true;
	    	}
	    	if (cmd.getName().equalsIgnoreCase("pc")) {
	    		
	    		if (sender.isOp()) {
	    			
	    			//Trigger PayCyle
	    			bso = config.getStringList("PlayerData.BusinessOwners");
	    			sender.sendMessage(ChatColor.GREEN+"Pay Cycle Triggered");
	    			//businessLoop(args[0]);
	    			if (args.length > 0) {
	    				if (!bso.contains(args[0])) {
	    					bso.add(args[0]);
	    				}
	    				config.set("PlayerData.BusinessOwners", bso);
	    			}
	    			try {
	    				saveConfigs();
	    		    } catch (Exception e) {
	    		    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    		    	}
	    			new Repeating(this).businessLoop();
	    		}
	    		
	    		return true;
	    	}
	    	
	    	if (cmd.getName().equalsIgnoreCase("setZone")) {
	    		
	    		String PR = args[0];
	    		String Z = args[1];
	    	
	    		
	    		if (propertyExists(PR)) {
	    			
	    			if (getProperty(PR).getOwner().equalsIgnoreCase(sender.getName())) {
	    				
	    				Property PRR = getProperty(PR);
	    				
	    				if (Z.equalsIgnoreCase("commercial")) {
	    					sender.sendMessage(ChatColor.GREEN+"Zoned as Commercial");
	    					PRR.setZone("com");
	    				}else if (Z.equalsIgnoreCase("industrial")) {
	    					
	    					if (econ.has(sender.getName(), 150000)) {
	    						econ.withdrawPlayer(sender.getName(), 150000);
	    						PRR.setZone("ind");
	    						config.set("PropertyData."+PRR.getName()+".Zone", "ind");
	    						sender.sendMessage(ChatColor.GREEN+"Zoned as Industrial");
	    					}else {
	    						sender.sendMessage(ChatColor.RED+"You need $150,000");
	    					}

	    				}else if (Z.equalsIgnoreCase("residential")) {
	    					PRR.setZone("res");
	    					sender.sendMessage(ChatColor.GREEN+"Zoned as Residential");
	    				}else {
	    					sender.sendMessage(ChatColor.RED+"Must specify Commercial, Industrial or Residential");
	    				}
	    				
	    			}else {
	    				sender.sendMessage(ChatColor.RED+"Thats Not your land...");
	    			}
	    			
	    		}else {
	    			sender.sendMessage(ChatColor.RED+"Property Not Found");
	    		}
	    		
	    		
	    		return true;
	    	}
	    	//	essentials.sethome.multiple.unlimited
	    	if (cmd.getName().equalsIgnoreCase("buyhomesets")) {
	    		
	    		if (econ.has(sender.getName(), 1000)) {
	    			
	    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuaddp "+sender.getName()+" essentials.sethome.multiple");
	    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuaddp "+sender.getName()+" essentials.sethome.multiple.unlimited");
	    			econ.withdraw(sender.getName(), 1000);
	    			sender.sendMessage(ChatColor.GREEN+"You can now set Unlimited Homes");
	    			
	    		}else {
	    			sender.sendMessage(ChatColor.RED+"This requires $1,000");
	    		}
	    		
	    	}
	    	if (cmd.getName().equalsIgnoreCase("buyechest")) {
	    		
	    		if (econ.has(sender.getName(), 5000)) {
	    			
	    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuaddp "+sender.getName()+" essentials.enderchest");
	    			econ.withdraw(sender.getName(), 5000);
	    			
	    		}else {
	    			sender.sendMessage(ChatColor.RED+"This requires $5,000");
	    		}
	    		
	    	}else
	    	
	    	if (cmd.getName().equalsIgnoreCase("buyfly")) {
	    		
	    		if (econ.has(sender.getName(), 25000)) {
	    			
	    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuaddp "+sender.getName()+" essentials.fly");
	    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuaddp "+sender.getName()+" essentials.fly.safelogin");
	    			econ.withdraw(sender.getName(), 25000);
	    			
	    			
	    		}else {
	    			sender.sendMessage(ChatColor.RED+"This requires $25,000");
	    		}
	    		
	    	}else if (cmd.getName().equalsIgnoreCase("buyhealth")) {
	    		
	    		if (econ.has(sender.getName(), 10000)) {
	    			
	    			Player P = Bukkit.getPlayer(sender.getName());
	    			
	    			if (econ.has(P.getName(), 10000)) {
	    				econ.withdraw(P.getName(), 10000);
	    				P.setMaxHealth((P.getMaxHealth()+2));
	    				config.set("PlayerData."+P.getName()+".MaxHP", P.getMaxHealth());
	    				config.set("PlayerData."+P.getName()+".AdditionalHP", true);
	    				sender.sendMessage(ChatColor.GREEN+"2 Additional HP added");
	    				try {
	    					saveConfigs();
	    			    } catch (Exception e) {
	    			    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    			    	}
	    			}
	    			
	    			
	    		}else {
	    			sender.sendMessage(ChatColor.RED+"This requires $10,000");
	    		}
	    		
	    	}
	    	
	    	if (cmd.getName().equalsIgnoreCase("buystorage")) {
	    		
	    		if (isBusiness(p.getName())) {
	    			if (econ.has(p.getName(), storageprice)) {
	    				econ.withdrawPlayer(p.getName(), storageprice);
	    				ItemStack SP = new ItemStack(Material.PAPER, 1);
						ItemMeta SPM = SP.getItemMeta();
						SPM.setDisplayName(ChatColor.DARK_GREEN+"Storage Permit");
						SP.setItemMeta(SPM);
	    				
	    				p.getInventory().addItem(SP);
	    			}
	    		}else {
	    			//Needs a business message
	    		}
	    		
	    		return true;
	    	}
	    	
	    	if (cmd.getName().equalsIgnoreCase("stsecon")) { // If the player typed /basic then do the following, note: If you only registered this executor for one command, you don't need this
	    		if (args.length <= 1) {
	    			if (args[0].contentEquals("price")) {
	    				sender.sendMessage(prf+"Econ Prices Listed Below:");
	    				sender.sendMessage(prf+"Roll a number - $1000");
	    				sender.sendMessage(prf+"Buy a Business License - $"+config.getInt("Economy.Prices.Business"));
	    				sender.sendMessage(prf+"Buy a PlayerStock share - $"+psp);
	    				return true;
	    			}
	    		}else {	
	    		sender.sendMessage(prf+"Small Time Survival Economy System Commands:");
	    		sender.sendMessage(prf+"/econ price");
	    		return true;
	    		}
	    	} else if (cmd.getName().equalsIgnoreCase("psp")) {
	    		
	    		int prc = (int) ps_price;
	    		sender.sendMessage(prf+ChatColor.GOLD+"Current PlayerStock Price: "+ChatColor.RED+"$"+price.format(ps_price));

	    		return true;
	  
	    	}else if (cmd.getName().equalsIgnoreCase("wiki")) {
	    		
	    		sender.sendMessage(ChatColor.GREEN+"https://github.com/Wintergrasped/Small-Time-Survival/wiki/Getting-Started");
	    		
	    	}else if (cmd.getName().equalsIgnoreCase("setworth")) {
	    		if (args.length == 0) {
	    			sender.sendMessage(ChatColor.RED+"/setworth <Value>");
	    		}
	    		if (p.isOp()) {
	    			String MN = p.getInventory().getItemInMainHand().getType().name();
	    			double v = Double.valueOf(args[0]);
	    			config.set("Sell.Worth."+p.getInventory().getItemInMainHand().getType().name(), v);
	    			sender.sendMessage(ChatColor.GREEN+"Set the value of "+MN+" to "+args[0]);
	    			try {
	    				saveConfigs();
	    		    } catch (Exception e) {
	    		    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    		    	}
	    		}else{
	    			return true;
	    		}
	    	}else if (cmd.getName().equalsIgnoreCase("convert")) {
	    		
	    		if (p.isOp()) {
	    			
	    			loadWorth(p);
	    			
	    		}else {
	    			p.sendMessage(ChatColor.RED+"Really? What are you trying to convert? Your religion cause that aint gonna work son. This is an OP only command.");
	    		}
	    	
	    	}else if (cmd.getName().equalsIgnoreCase("sell")) {
	    		String MN = p.getInventory().getItemInMainHand().getType().name();
	    		double val = 0;
	    		if (args.length == 0) {
	    			//config.set("Sell.Woth"+p.getInventory().getItemInMainHand().getType().name(), args[0]);
	    			
	    			
	    			val = config.getDouble("Sell.Worth."+p.getInventory().getItemInMainHand().getType().name());
	    			
	    			
	    			if (val == 0) {
	    				sender.sendMessage(prf+""+MN+" Has No Value");
	    			}else {
	    				if (args.length == 0) {
	    					p.getInventory().getItemInMainHand().setAmount((p.getInventory().getItemInMainHand().getAmount()-1));
	    					econ.deposit(sender.getName(), val);
	    					sender.sendMessage(prf+"Sold "+MN+" for $"+price.format(val));
	    				}else {
	    					
	    					
	    					
	    				}
	    				
	    			}
	    		}else if (args[0].equalsIgnoreCase("all")) {
	    			
	    			val = config.getDouble("Sell.Worth."+p.getInventory().getItemInMainHand().getType().name());
	    			if (val == 0) {
	    				sender.sendMessage(prf+" Has No Value");
	    			}else {
	    			
					val = val*p.getInventory().getItemInMainHand().getAmount();
					ItemStack IS = new ItemStack(p.getInventory().getItemInMainHand().getType(), p.getInventory().getItemInMainHand().getAmount());
					IS.setAmount(p.getInventory().getItemInMainHand().getMaxStackSize());
					p.getInventory().removeItem(IS);
					econ.deposit(sender.getName(), val);
					sender.sendMessage(prf+"Sold "+MN+" for $"+price.format(val));
	    			}
				}
	    		
	    	}else if (cmd.getName().equalsIgnoreCase("worth")) {
	    		String MN = p.getInventory().getItemInMainHand().getType().name();
	    		if (args.length == 0) {
	    			//config.set("Sell.Woth"+p.getInventory().getItemInMainHand().getType().name(), args[0]);
	    			double val = 0;
	    			val = config.getDouble("Sell.Worth."+p.getInventory().getItemInMainHand().getType().name());
	    			
	    			
	    			if (val == 0) {
	    				sender.sendMessage(prf+" Has No Value");
	    			}else {
	    			
	    				sender.sendMessage(prf+"Worth $"+price.format(val));
	    				
	    			}
	    		}
	    		
	    	}else if (cmd.getName().equalsIgnoreCase("pmine")) {
	    		
	    		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp pmine "+sender.getName());
	    		
	    	}else if (cmd.getName().equalsIgnoreCase("web")) {
	    		
	    		sender.sendMessage(prf+"http://secomc.com/index.php");
	    		sender.sendMessage(prf+"http://secomc.com/markets/market.html");
	    		sender.sendMessage(prf+"Your Profile: http://secomc.com/about.php?UUID="+Bukkit.getPlayer(sender.getName()).getUniqueId());
	    		
	    	}else if (cmd.getName().equalsIgnoreCase("vt")) {
	    		
	    		sender.sendMessage(prf+"Vote Here:");
	    		sender.sendMessage(prf+"PMC: https://www.planetminecraft.com/server/small-time-survival-the-capitalistic-minecraft-server/");
	    		sender.sendMessage(prf+"MCSL: https://minecraft-server-list.com/server/478644/");
	    		
	    	}else if (cmd.getName().equalsIgnoreCase("start")) {
	    		
	    		sender.sendMessage(prf+"We DO NOT USE Essentials economy or any others we Use a fully custom Economy plugin");
	    		sender.sendMessage(prf+"To get your balance do /bl");
	    		sender.sendMessage(prf+"To get help on the Econ plugin do /psh");
	    		sender.sendMessage(prf+"More help is avalible on /wiki");
	    		sender.sendMessage(prf+"The discord link can be gotten with /discord");
	    		sender.sendMessage(prf+"The website link can be gotten with /web");
	    		sender.sendMessage(prf+"To build without buying land you must go to the wilderness");
	    		sender.sendMessage(prf+"Wilderness is 2000 blocks out from spawn");
	    		
	    		
	    	}else if (cmd.getName().equalsIgnoreCase("spb")) {
	    		
	    		if (p.isOp()) {
	    			
	    			if (args[0].equalsIgnoreCase("joe")) {
	    				
	    				
	    				spawnJoe(p.getLocation());
	    				
	    			}
	    			
	    			if (args[0].equalsIgnoreCase("fred")) {
	    				
	    				
	    				spawnFred(p.getLocation());
	    				
	    			}
	    			
	    			if (args[0].equalsIgnoreCase("bob")) {
	    				
	    				
	    				spawnBob(p.getLocation());
	    				
	    			}
	    			
	    			if (args[0].equalsIgnoreCase("drop")) {
	    				
	    				
	    				ItemStack IS1 = new ItemStack(Material.NETHERITE_SWORD);
	    				IS1.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
	    				
	    				ItemStack IS2 = new ItemStack(Material.BOW);
	    				IS2.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 10);
	    				
	    				ItemStack IS3 = new ItemStack(Material.NETHERITE_HELMET);
	    				IS3.addUnsafeEnchantment(Enchantment.THORNS, 10);
	    				IS3.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
	    				
	    				ItemStack IS4 = new ItemStack(Material.NETHERITE_CHESTPLATE);
	    				IS4.addUnsafeEnchantment(Enchantment.THORNS, 10);
	    				IS4.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
	    				
	    				ItemStack IS5 = new ItemStack(Material.NETHERITE_BOOTS);
	    				IS5.addUnsafeEnchantment(Enchantment.THORNS, 10);
	    				IS5.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
	    				
	    				ItemStack IS6 = new ItemStack(Material.NETHERITE_LEGGINGS);
	    				IS6.addUnsafeEnchantment(Enchantment.THORNS, 10);
	    				IS6.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
	    				
	    				p.getInventory().addItem(IS1);
	    				p.getInventory().addItem(IS2);
	    				p.getInventory().addItem(IS3);
	    				p.getInventory().addItem(IS4);
	    				p.getInventory().addItem(IS5);
	    				p.getInventory().addItem(IS6);
	    				
	    			}
	    			
	    		}
	    		
	    	}else if (cmd.getName().equalsIgnoreCase("dungeon")) {
	    		
	    		
	    		if (p.getLocation().getWorld().getName().toString().equalsIgnoreCase("arena")) {
	    			if (args[0].equalsIgnoreCase("summon")) {
	    		
	    				if (args[1].equalsIgnoreCase("bob")) {
	    					Location L = new Location(Bukkit.getWorld("arena"), -120, 117, 135);
	    					spawnBob(L);
	    				}
	    				
	    				if (args[1].equalsIgnoreCase("JJ")) {
	    					Location L = Bukkit.getPlayer(sender.getName()).getLocation();
	    					spawnJJ(L);
	    				}
	    			}
	    		}else {
	    			p.sendMessage(prf+"You must be in the arena to do this");
	    			
	    			if (args[1].equalsIgnoreCase("JJ")) {
    					Location L = Bukkit.getPlayer(sender.getName()).getLocation();
    					spawnJJ(L);
    				}
	    		}
	    		
	    	}
	    	else if (cmd.getName().equalsIgnoreCase("p")) {
	    		
	    		Party pt = null;
	    		ArrayList<Player> pl = new ArrayList();
    			pl.add(p);
	    		if (args[0].equalsIgnoreCase("create")) {
	    			
	    			
	    			
	    			pt = new Party(p.getName(), pl);
	    			pts.add(pt);
	    			p.sendMessage(prf+"Party Created to invite people do /p invite <PlayerName>");
	    		}else if (args[0].equalsIgnoreCase("invite")) {
	    			
	    			if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
	    				
	    				if (args.length < 1) {
	    					p.sendMessage(prf+"Specify a player");
	    				}
	    				
	    				Player t = Bukkit.getPlayer(args[1]);
	    				boolean partyExists = false;
		    			Party targetParty = null;
		    			for (Party pp : pts) {
		    				if (pp.getName().equalsIgnoreCase(p.getName())) {
		    					partyExists = true;
		    					targetParty = pp;
		    				}
		    			}
		    				if (partyExists) {
	    				p.sendMessage(prf+"Invited "+args[1]+" to your party");
	    				t.sendMessage(prf+" "+p.getName()+" invited you to a party do /p join "+p.getName()+" to join the party");
	    				targetParty.invite(t);
	    				
	    				pts.remove(targetParty);
	    				pts.add(targetParty);
		    				}else {
		    					p.sendMessage(prf+"Please Create a party first. /p create");
		    				}
	    		
	    				
	    			}else {
	    				sender.sendMessage(prf+"Player Not Found");
	    			}
	    			
	    			
	    		}else if (args[0].equalsIgnoreCase("summon")) {
	    			boolean partyExists = false;
	    			Party targetParty = null;
	    			for (Party pp : pts) {
	    				if (pp.getName().equalsIgnoreCase(p.getName())) {
	    					partyExists = true;
	    					targetParty = pp;
	    				}
	    			}
	    			
	    			if (partyExists) {
	    				
	    				for (Player tp : targetParty.getMembers()) {
	    					tp.teleport(p.getLocation());
	    				}
	    				
	    			}
	    			
	    		}else if (args[0].equalsIgnoreCase("join")) {
	    			Party ptss = null;
	    			Player ptl = Bukkit.getPlayer(args[1]);
	    			ArrayList<Player> ptll = new ArrayList();
	    			ptll.add(ptl);
	    			ptss = new Party(args[1], ptll);
	    			
	    			Party targetParty = null;
	    			
	    			boolean partyExists = false;
	    			
	    			for (Party pp : pts) {
	    				if (pp.getName().equalsIgnoreCase(args[1])) {
	    					partyExists = true;
	    					targetParty = pp;
	    				}
	    			}
	    			
	    			if (args.length < 1) {
    					p.sendMessage(prf+"Specify WHO invited you?");
    				}
	    			
	    			
	    			
	    			if (partyExists) {
	    				
	    				if (targetParty.isMember(p)) {
	    					
	    					p.sendMessage(prf+"You are already a memeber of this party");
	    					
	    				}else {
	    					
	    					if (targetParty.isInvited(p)) {
	    						targetParty.addPlayer(p);
	    						
	    						pts.remove(targetParty);
	    						pts.add(targetParty);
	    						
	    						Player plr = Bukkit.getPlayer(args[1]);
	    						plr.sendMessage(prf+p.getName()+" Joined your party");
	    						p.sendMessage(prf+"You have joined "+args[1]+"'s party");
	    					}else {
	    						
	    						p.sendMessage(prf+args[1]+"Hasn't Invited you to a party");
	    						
	    					}
	    					
	    				}
	    				
	    			}else {
	    				p.sendMessage(prf+"Party Not Found");
	    			}
	    			
	    		}
	    		
	    	}else if (cmd.getName().equalsIgnoreCase("se")) {
	    		
	    		if (args.length == 0) {
	    			sender.sendMessage(prf+"Do /psh for a command list");
	    			return true;
	    		}
	    		
	    		psp = ps_price;
	    		loadWG();
	    		if (args[0].equalsIgnoreCase("LastDeath")) {
	    			
	    			if (config.getString("PlayerData."+sender.getName()+".LastDeath.World") == null) {
	    				sender.sendMessage(ChatColor.RED+"Please Die First");
	    			}else {
	    				if (econ.has(sender.getName(), 100)) {
	    					econ.withdraw(sender.getName(), 100);
	    					sender.sendMessage(ChatColor.GREEN+"Last Death Wold:"+config.getString("PlayerData."+sender.getName()+".LastDeath.World"));
	    					sender.sendMessage(ChatColor.GREEN+"Last Death X:"+config.getString("PlayerData."+sender.getName()+".LastDeath.X"));
	    					sender.sendMessage(ChatColor.GREEN+"Last Death Y:"+config.getString("PlayerData."+sender.getName()+".LastDeath.Y"));
	    					sender.sendMessage(ChatColor.GREEN+"Last Death Z:"+config.getString("PlayerData."+sender.getName()+".LastDeath.Z"));
	    				}else {
	    					sender.sendMessage(ChatColor.RED+"Error 1BP Broke Person Detected, Insufficient Funds");
	    				}
	    			}
	    		}else if (args[0].equalsIgnoreCase("tpLastDeath")) {
	    			
	    			if (config.getString("PlayerData."+sender.getName()+".LastDeath.World") == null) {
	    				sender.sendMessage(ChatColor.RED+"Please Die First");
	    			}else {
	    				if (econ.has(sender.getName(), 5000)) {
	    					econ.withdraw(sender.getName(), 5000);
	    					String WN = config.getString("PlayerData."+sender.getName()+".LastDeath.World");
	    					double X = config.getDouble("PlayerData."+sender.getName()+".LastDeath.X");
	    					double Y = config.getDouble("PlayerData."+sender.getName()+".LastDeath.Y");
	    					double Z = config.getDouble("PlayerData."+sender.getName()+".LastDeath.Z");
	    					Location LDL = new Location(Bukkit.getWorld(WN), X,Y,Z);
	    					sender.sendMessage(ChatColor.GREEN+"Last Death Wold:"+config.getString("PlayerData."+sender.getName()+".LastDeath.World"));
	    					sender.sendMessage(ChatColor.GREEN+"Last Death X:"+config.getDouble("PlayerData."+sender.getName()+".LastDeath.X"));
	    					sender.sendMessage(ChatColor.GREEN+"Last Death Y:"+config.getDouble("PlayerData."+sender.getName()+".LastDeath.Y"));
	    					sender.sendMessage(ChatColor.GREEN+"Last Death Z:"+config.getDouble("PlayerData."+sender.getName()+".LastDeath.Z"));
	    					p.teleport(LDL);
	    					
	    				}else {
	    					sender.sendMessage(ChatColor.RED+"Error 1BP Broke Person Detected, Insufficient Funds");
	    				}
	    			}
	    		}else if (args[0].equalsIgnoreCase("combine")) {
	    			
	    			new CombineCommand().Combine(p);
	    			
	    		}else if (args[0].equalsIgnoreCase("wolf")) {
	    			
	    			List<String> wolfs = config.getStringList("PlayerData.TamedWolfs");
	    			
	    			if (!p.getLocation().getWorld().getName().equalsIgnoreCase("world")) {
	    				World w = p.getLocation().getWorld();
	    				for (String ID : wolfs) {
	    					
	    					List<String> wids = new ArrayList();
	    					wids = config.getStringList("WoldData."+w.getName()+".PlayerWolfs");
	    					
	    					if (!wids.contains(ID)) {
	    						wids.add(ID);
	    						config.set("WoldData."+w.getName()+".PlayerWolfs", wids);
	    						try {
	    							saveConfigs();
	    					    } catch (Exception e) {
	    					    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    					    	}
	    					}else {
	    						p.sendMessage(ChatColor.RED+"Wolf Detected as Already Spawned Here");
	    						return true;
	    					}
	    					
	    					Player owner = p;
	    					Entity e = w.spawnEntity(p.getLocation(), EntityType.WOLF);
	    					Wolf wolf = (Wolf) e;
	    					
	    					
	    					
	    					String Name = config.getString("PlayerData."+p.getName()+".Wolfs."+wolf.getUniqueId()+".Name");
	    					int MH = config.getInt("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".MaxHealth");
	    					int Age = config.getInt("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".Age");
	    					double level = config.getDouble("PlayerData."+owner.getName()+".Wolf."+wolf.getUniqueId()+".Level");
	    					
	    					
	    					
	    					if (MH <= 0) {
	    						MH = 20;
	    					}
	    					
	    					
	    					wolf.setMaxHealth(MH);
	    					wolf.setAge(Age);
	    					wolf.setTamed(true);
	    					wolf.setOwner(p);
	    					
	    					
	    					
	    					if (level == 2) {
	    				    	wolf.setMaxHealth(40);
	    				    	wolf.setHealth(40);
	    						AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 2, AttributeModifier.Operation.ADD_NUMBER);
	    						A.getUniqueId();
	    						wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
	    						owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 2");
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    				    }else if (level == 3) {
	    				    	wolf.setMaxHealth(60);
	    				    	wolf.setHealth(60);
	    						AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 4, AttributeModifier.Operation.ADD_NUMBER);
	    						A.getUniqueId();
	    						wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
	    						owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 3");
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    				    }else if (level == 4) {
	    				    	wolf.setMaxHealth(80);
	    				    	wolf.setHealth(80);
	    						AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 6, AttributeModifier.Operation.ADD_NUMBER);
	    						A.getUniqueId();
	    						wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
	    						owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 4");
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    				    }else if (level == 5) {
	    				    	wolf.setMaxHealth(80);
	    				    	wolf.setHealth(80);
	    						AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 8, AttributeModifier.Operation.ADD_NUMBER);
	    						A.getUniqueId();
	    						wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
	    						owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 5");
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    				    }else if (level == 6) {
	    				    	wolf.setMaxHealth(100);
	    				    	wolf.setHealth(100);
	    						AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 12, AttributeModifier.Operation.ADD_NUMBER);
	    						A.getUniqueId();
	    						wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
	    						owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 6");
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    				    }else if (level == 7) {
	    				    	wolf.setMaxHealth(120);
	    				    	wolf.setHealth(120);
	    						AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 14, AttributeModifier.Operation.ADD_NUMBER);
	    						A.getUniqueId();
	    						wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
	    						owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 7");
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    				    }else if (level == 8) {
	    				    	wolf.setMaxHealth(120);
	    				    	wolf.setHealth(120);
	    						AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 18, AttributeModifier.Operation.ADD_NUMBER);
	    						A.getUniqueId();
	    						wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
	    						owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 8");
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    				    }else if (level == 9) {
	    				    	wolf.setMaxHealth(140);
	    				    	wolf.setHealth(140);
	    						AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 22, AttributeModifier.Operation.ADD_NUMBER);
	    						A.getUniqueId();
	    						wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
	    						owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 9");
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    				    }else if (level == 10) {
	    				    	wolf.setMaxHealth(160);
	    				    	wolf.setHealth(160);
	    						AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 26, AttributeModifier.Operation.ADD_NUMBER);
	    						A.getUniqueId();
	    						wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
	    						owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 10");
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    				    }else if (level == 11) {
	    				    	wolf.setMaxHealth(180);
	    				    	wolf.setHealth(180);
	    						AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 28, AttributeModifier.Operation.ADD_NUMBER);
	    						A.getUniqueId();
	    						wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
	    						owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 11");
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    				    }else if (level == 12) {
	    				    	wolf.setMaxHealth(200);
	    				    	wolf.setHealth(200);
	    						AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 30, AttributeModifier.Operation.ADD_NUMBER);
	    						A.getUniqueId();
	    						wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
	    						wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
	    						owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 12");
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
	    						owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    				    }
	    					
	    					/*config.set("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".Saved", true);
							config.set("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".Name", wolf.getCustomName());
							config.set("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".MaxHealth", wolf.getMaxHealth());
							config.set("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".Age", wolf.getAge());
							config.set("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".Color", wolf.getCollarColor());*/
	    					
	    				}
	    				
	    			}
	    			
	    		}
	    		
	    		
	    		
	    		if (args[0].equalsIgnoreCase("horde")) {
	    			Location L = p.getLocation();
	    			L.setX(L.getX()+5);
	    			spawnJoe(L);
	    			spawnJoe(L);
	    			spawnJoe(L);
	    			spawnJoe(L);
	    			spawnFred(L);
	    			spawnFred(L);
	    			spawnFred(L);
	    			p.getLocation().getWorld().spawnEntity(L, EntityType.ZOMBIE);
	    			p.getLocation().getWorld().spawnEntity(L, EntityType.ZOMBIE);
	    			p.getLocation().getWorld().spawnEntity(L, EntityType.ZOMBIE);
	    			p.getLocation().getWorld().spawnEntity(L, EntityType.ZOMBIE);
	    			p.getLocation().getWorld().spawnEntity(L, EntityType.ZOMBIE);
	    			p.getLocation().getWorld().spawnEntity(L, EntityType.ZOMBIE);
	    			p.getLocation().getWorld().spawnEntity(L, EntityType.ZOMBIE);
	    			p.getLocation().getWorld().spawnEntity(L, EntityType.ZOMBIE);
	    			p.getLocation().getWorld().spawnEntity(L, EntityType.ZOMBIE);
	    			p.getLocation().getWorld().spawnEntity(L, EntityType.ZOMBIE);
	    			p.getLocation().getWorld().spawnEntity(L, EntityType.ZOMBIE);
	    			
	    		
	    			
	    			
	    		}
	    		
	    		if (args.length >= 0) {
	    			if (args[0].equalsIgnoreCase("buy")) {
	    				
	    				if (args.length >= 2) {
	    					
	    					String n = sender.getName();
	    					int a = 1;
	    					
	    					
	    					a = Integer.valueOf(args[1]);
	    					
	    					if (a <= 0) {
	    						a = 1;
	    					}

	    					double prcs = psp*a;
	    					
	    					//TODO Solve Errors That occur on PS buy
	    					//TODO Create and Implement Custom Economy System
	    					//TODO Disable Essentials Economy
	    					
	    					if (econ.has(sender.getName(), prcs)) {
    							int s = config.getInt("PlayerStocks.PlayerData."+n+".Shares");
    							config.set("PlayerStocks.PlayerData."+n+".Shares", s+a);
    							econ.withdrawPlayer(p.getName(), prcs);
    							sender.sendMessage(prf+ChatColor.GOLD+"You purchased "+stocks.format(a)+" shares of Player Stocks for $"+price.format(prcs));
    							
    							stockPurchaseEvent spe = new stockPurchaseEvent(Bukkit.getPlayer(sender.getName()), "PlayerStocks", a, psp);
                                Bukkit.getServer().getPluginManager().callEvent(spe);
    							
    							
                                try {
                        			saveConfigs();
                        	    } catch (Exception e) {
                        	    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
                        	    	}
    						}else {
    							sender.sendMessage(prf+"Not Enough Moneys!");
    						}
	    					
	    					
	    				}else {
	    					if (enabled) {
	    						String n = sender.getName();
	    						if (econ.has(p.getName(), psp)) {
	    							int s = config.getInt("PlayerStocks.PlayerData."+n+".Shares");
	    							config.set("PlayerStocks.PlayerData."+n+".Shares", s+1);
	    							econ.withdrawPlayer(p.getName(), psp);
	    							sender.sendMessage(prf+ChatColor.GOLD+"You purchased one share of Player Stocks for $"+price.format(psp));
	    							try {
	    								saveConfigs();
	    						    } catch (Exception e) {
	    						    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    						    	}
	    						}else {
	    							sender.sendMessage(prf+"Not Enough Moneys!");
	    						}
	    					}else {
	    						sender.sendMessage(prf+ChatColor.RED+"The Stocks system has been disabled Temporairly");
	    					}
	    				}
	    				
	    				return true;
	    				
	    			}else
	    			
	    			if (args[0].equalsIgnoreCase("enable")) {
	    				if (sender.isOp()) {
	    					if (enabled) {
	    						enabled = true;
	    					}else {
	    						enabled = true;
	    					}
	    					sender.sendMessage(prf+ChatColor.GREEN+"Player Stocks system Enabled");
	    					return true;
	    				}
	    			}else if (args[0].equalsIgnoreCase("disable")) {
	    				if (sender.isOp()) {
	    					if (enabled) {
	    						enabled = false;
	    					}else {
	    						enabled = false;
	    					}
	    					sender.sendMessage(prf+ChatColor.RED+"Player Stocks system Disabled");
	    					return true;
	    				}
	    			
	    			}else if (args[0].equalsIgnoreCase("welcome")) {
	    				
	    				PlayerIntro.begin(this, Bukkit.getPlayer(sender.getName()));
	    			
	    			}else if (args[0].equalsIgnoreCase("song")) {
	    				
	    				PlayerIntro.song(this, Bukkit.getPlayer(sender.getName()));
	    			
	    			}else if (args[0].equalsIgnoreCase("blist")) {
	    				
	    				//TODO FINISH BTOP
	    				
	    				
	    				
	    				
	    				List<String> bso = config.getStringList("PlayerData.BusinessOwners");
	    		    	for (String b : bso) {
	    		    		
	    		    		int rev = config.getInt("PlayerData."+b+".Business.Revenue");
	    		    		int size = config.getInt("PlayerData."+b+".Business.Employee.Count")+1;
	    		    		int oc = config.getInt("PlayerData."+b+".Business.OperationCost");
	    		    		
	    		    		int profit = rev-oc;
	    		    		
	    		    		Business bs = new Business(b, rev, oc, profit, 0, size);
	    		    		
	    		    		sender.sendMessage(prf+ChatColor.RED+""+b+ChatColor.GREEN+" Size: "+ChatColor.RED+price.format(size)+ChatColor.GREEN+" Revenue: "+ChatColor.RED+price.format(rev)+ChatColor.GREEN+" Costs: "+ChatColor.RED+price.format(oc)+ChatColor.GREEN+" Profit: "+ChatColor.RED+price.format(profit));
	    		    		
	    		    	}
	    				
	    				
	    				return true;
	    				
	    				
	    				
	    				
	    				
	    			}else if (args[0].equalsIgnoreCase("bslist")) {
	    				
	    				//TODO FINISH BTOP
	    				
	    				
	    				
	    				
	    				List<String> bso = config.getStringList("PlayerData.BusinessOwners");
	    		    	for (String b : bso) {
	    		    		
	    		    		double rev = config.getDouble("PlayerData."+b+".Business.Revenue");
	    		    		double size = config.getDouble("PlayerData."+b+".Business.Employee.Count")+1;
	    		    		double oc = config.getDouble("PlayerData."+b+".Business.OperationCost");
	    		    		double sh = config.getDouble("PlayerData."+b+".Business.Shares");
	    		    		
	    		    		double profit = rev-oc;
	    		    		
	    		    		Business bs = new Business(b, rev, oc, profit, sh, size);
	    		    		double LD = config.getDouble("BusinessData."+b+".LastDiv");
	    		    		sender.sendMessage(prf+ChatColor.RED+""+b+ChatColor.GREEN+" Size: "+ChatColor.RED+price.format(size)+
	    		    				ChatColor.GREEN+" Revenue: "+ChatColor.RED+price.format(rev)+
	    		    				ChatColor.GREEN+" StockPrice: "+ChatColor.RED+price.format(bs.getStockPrice())+
	    		    				ChatColor.GREEN+" Profit: "+ChatColor.RED+price.format(profit)+ChatColor.GREEN
	    		    				+" Last Dividend: "+ChatColor.RED+price.format(LD));
	    		    		
	    		    	}
	    		    	return true;
	    			}else if (args[0].equalsIgnoreCase("rgadd")) {
	    				
	    				//TODO FINISH BTOP
	    				
	    				if (args.length <= 1) {
	    					
	    					sender.sendMessage(prf+ChatColor.RED+"Specify Region Name!");
	    					
	    				}else {
	    					
	    					//World weWorld = new BukkitWorld(Bukkit.getPlayer(sender.getName()).getWorld());
	    					
	    					//RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
	    					///RegionManager wgRM = container.get(weWorld);
	    					//ProtectedRegion R = wgRM.getRegion(args[1]);
	    					
	    					//sr.add(R);
	    					srs.add(args[1]);
	    						
	    					sender.sendMessage(prf+ChatColor.GREEN+"Region "+args[1]+" added to shop region List");
	    					
	    				}
	    				
	    				
	    				
	    		    	
	    		    	return true;
	    			}else if (args[0].equalsIgnoreCase("cb")) {
	    				
	    				
	    				List<String> bso = config.getStringList("PlayerData.BusinessOwners");
						
						
						if (args.length <= 1) {
							sender.sendMessage(prf+ChatColor.RED+" You must specify a player to check");
						}else{
							String n = args[1];
							if (bso.contains(n)) {
								
								sender.sendMessage(prf+ChatColor.GREEN+n+" has a valid business license");
							
							}else {
								sender.sendMessage(prf+ChatColor.RED+n+" does not have a valid business license");
							}
						}
						
	    				
	    		    	return true;
	    			}else if (args[0].equalsIgnoreCase("rst")) {
	    				
	    				String b = sender.getName();

	    				config.set("PlayerData."+b+".Business.EmployeeID.List", new ArrayList());
			    		config.set("PlayerData."+b+".Business.Employee.Count", 0);
			    		config.set("PlayerData."+b+".Business.Revenue", 0);
			    		config.set("PlayerData."+b+".Business.Employee.Count", 0);
			    		config.set("PlayerData."+b+".Business.OperationCost", 0);
			    		config.set("PlayerData."+b+".Business.Shares", 0);
			    		config.set("PlayerData."+b+".Business.Supplies", 0);
	    				
	    		    	return true;
	    			}else if (args[0].equalsIgnoreCase("cs")) {
	    				
	    				
	    				if (isBusiness(sender.getName())) {
	    					
	    					String b = sender.getName();
	    					
	    		    		int size = config.getInt("PlayerData."+b+".Business.Employee.Count")+1;
	    		    		int s = config.getInt("PlayerData."+b+".Business.Supplies");
	    		    		double ns = size*1.25;
	    					
	    					int sp = config.getInt("PlayerData."+sender.getName()+".Business.Supplies");
	    					sender.sendMessage(prf+ChatColor.GREEN+"You have "+sp+" Supplies you need "+ns+" Supplies per payroll cycle");
	    				}
	    				
	    		    	return true;
	    			}else if (args[0].equalsIgnoreCase("si")) {
	    				
	    				//config.getString("PlayerData."+b+".Business.Industry");
	    				String b = sender.getName();
	    				
	    				if (isBusiness(sender.getName())) {
	    					if (args.length >= 1) {
	    						
	    						if (args[1].equalsIgnoreCase("Mining")) {
	    							config.set("PlayerData."+b+".Business.Industry", "Mining");
	    							sender.sendMessage(prf+ChatColor.GREEN+"Industry set to Mining");
	    						}else if (args[1].equalsIgnoreCase("Timber")) {
	    							config.set("PlayerData."+b+".Business.Industry", "Timber");
	    							sender.sendMessage(prf+ChatColor.GREEN+"Industry set to Timber");
	    						}else if (args[1].equalsIgnoreCase("Redstone")) {
	    							config.set("PlayerData."+b+".Business.Industry", "Redstone");
	    							sender.sendMessage(prf+ChatColor.GREEN+"Industry set to Redstone");
	    						}else if (args[1].equalsIgnoreCase("Farming")) {
	    							config.set("PlayerData."+b+".Business.Industry", "Farming");
	    							sender.sendMessage(prf+ChatColor.GREEN+"Industry set to Farming");
	    						}else {
	    							sender.sendMessage(prf+ChatColor.RED+"Chose Mining, Timber, Redstone or Faming");
	    						}
	    						
	    					}else {
	    						sender.sendMessage(prf+ChatColor.RED+"args too short Chose Mining, Timber, Redstone or Faming");
	    					}
	    				}else {
	    					sender.sendMessage(prf+ChatColor.RED+"I think you should own a business first bud.");
	    				}
	    				
	    				try {
	    					saveConfigs();
	    			    } catch (Exception e) {
	    			    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    			    	}
	    		    	return true;
	    		    	
	    			}else if (args[0].equalsIgnoreCase("dbs")) {
	    				
	    				if (isBusiness(sender.getName())) {
	    					int sp = config.getInt("PlayerData."+sender.getName()+".Business.Supplies");
	    					int wh = config.getInt("PlayerData."+sender.getName()+".Properties.warehouses");
							int fc = config.getInt("PlayerData."+sender.getName()+".Properties.factory");
							int st = config.getInt("PlayerData."+sender.getName()+".Business.Storage");
							
	    					int ms = maxBusSup+(wh*500);
	    					
	    					ms = ms+(st*27);
	    					
	    					if (sp >= ms) {
	    						sender.sendMessage(ChatColor.RED+"You Need to buy more warehouses to store any more supplies");
	    						return true;
	    					}
	    					
	    					String b = sender.getName();
	    					
	    					//Player p = Bukkit.getPlayer(sender.getName());
	    					
	    					ItemStack i = p.getInventory().getItemInMainHand();
	    					
	    					int am = 1;
	    					
	    					if (args.length >= 2) {
	    						am = Integer.parseInt(args[1]);
	    					}
	    					
	    					if (am >= p.getInventory().getItemInMainHand().getAmount()) {
	    						am = p.getInventory().getItemInMainHand().getAmount();
	    					}
	    					
	    					int rev = config.getInt("PlayerData."+b+".Business.Revenue");
	    		    		int size = config.getInt("PlayerData."+b+".Business.Employee.Count")+1;
	    		    		int oc = config.getInt("PlayerData."+b+".Business.OperationCost");
	    		    		int sh = config.getInt("PlayerData."+b+".Business.Shares");
	    		    		
	    		    		String in = config.getString("PlayerData."+b+".Business.Industry");
	    		    		
	    		    		if (in == "Mining")  {
	    		    			miningPRC = miningPRC+0.03;
	    		    		}else if (in == "Redstone") {
	    		    			redstonePRC = redstonePRC+0.03;
	    		    		}else if (in == "Timber") {
	    		    			loggingPRC = loggingPRC+0.03;
	    		    		}else if (in == "Farming") {
	    		    			farmingPRC = farmingPRC+0.03;
	    		    		}
	    		    		// Not sure if they should all say miningPRC, something to look into
	    		    		if (i.getType().equals(Material.IRON_ORE)) {
	    		    			if (in.equalsIgnoreCase("Mining")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*1);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.IRON_INGOT)) {
	    		    			if (in.equalsIgnoreCase("Mining")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*2);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*2)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.GOLD_ORE)) {
	    		    			if (in.equalsIgnoreCase("Mining")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*3);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*3)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.GOLD_INGOT)) {
	    		    			if (in.equalsIgnoreCase("Mining")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*4);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*4)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.DIAMOND)) {
	    		    			if (in.equalsIgnoreCase("Mining")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*10);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*10)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.EMERALD)) {
	    		    			if (in.equalsIgnoreCase("Mining")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*15);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*15)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.EMERALD_BLOCK)) {
	    		    			if (in.equalsIgnoreCase("Mining")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*135);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*135)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.DIAMOND_BLOCK)) {
	    		    			if (in.equalsIgnoreCase("Mining")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*90);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*90)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.IRON_BLOCK)) {
	    		    			if (in.equalsIgnoreCase("Mining")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*18);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*18)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.GOLD_BLOCK)) {
	    		    			if (in.equalsIgnoreCase("Mining")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*36);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*36)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.REDSTONE_BLOCK)) {
	    		    			if (in.equalsIgnoreCase("Redstone")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*9);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*9)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.REDSTONE)) {
	    		    			if (in.equalsIgnoreCase("Mining")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*1);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
	    		    			}else if (in.equalsIgnoreCase("Redstone")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*2);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
		    		    			}
	    		    		}else if (i.getType().equals(Material.REDSTONE_TORCH)) {
	    		    			if (in.equalsIgnoreCase("Redstone")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*2);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*2)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.REPEATER)) {
	    		    			if (in.equalsIgnoreCase("Redstone")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*3);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*3)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.REDSTONE_LAMP)) {
	    		    			if (in.equalsIgnoreCase("Redstone")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*5);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*5)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.OAK_LOG)) {
	    		    			if (in.equalsIgnoreCase("Timber")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*4);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*4)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.JUNGLE_LOG)) {
	    		    			if (in.equalsIgnoreCase("Timber")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*4);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*4)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.BIRCH_LOG)) {
	    		    			if (in.equalsIgnoreCase("Timber")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*4);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*4)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.DARK_OAK_LOG)) {
	    		    			if (in.equalsIgnoreCase("Timber")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*4);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*4)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.SPRUCE_LOG)) {
	    		    			if (in.equalsIgnoreCase("Timber")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*4);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*4)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.OAK_PLANKS)) {
	    		    			if (in.equalsIgnoreCase("Timber")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*1);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.DARK_OAK_PLANKS)) {
	    		    			if (in.equalsIgnoreCase("Timber")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*1);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.SPRUCE_PLANKS)) {
	    		    			if (in.equalsIgnoreCase("Timber")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*1);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.BIRCH_PLANKS)) {
	    		    			if (in.equalsIgnoreCase("Timber")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*1);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.JUNGLE_PLANKS)) {
	    		    			if (in.equalsIgnoreCase("Timber")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*1);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.WHEAT)) {
	    		    			if (in.equalsIgnoreCase("Farming")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*1);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.CARROT)) {
	    		    			if (in.equalsIgnoreCase("Farming")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*1);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.BEETROOT)) {
	    		    			if (in.equalsIgnoreCase("Farming")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*1);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.SWEET_BERRIES)) {
	    		    			if (in.equalsIgnoreCase("Farming")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*1);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.POTATO)) {
	    		    			if (in.equalsIgnoreCase("Farming")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*1);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.BREAD)) {
	    		    			if (in.equalsIgnoreCase("Farming")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*2);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*2)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.COOKED_BEEF)) {
	    		    			if (in.equalsIgnoreCase("Farming")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*2);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*2)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.COOKED_CHICKEN)) {
	    		    			if (in.equalsIgnoreCase("Farming")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*2);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*2)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.COOKED_MUTTON)) {
	    		    			if (in.equalsIgnoreCase("Farming")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*2);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*2)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.COOKED_PORKCHOP)) {
	    		    			if (in.equalsIgnoreCase("Farming")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*2);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*2)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.PUMPKIN_PIE)) {
	    		    			if (in.equalsIgnoreCase("Farming")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*5);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*5)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.SUGAR_CANE)) {
	    		    			if (in.equalsIgnoreCase("Farming")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*1);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.CAKE)) {
	    		    			if (in.equalsIgnoreCase("Farming")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*5);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*30)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.GOLDEN_CARROT)) {
	    		    			if (in.equalsIgnoreCase("Farming")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*8);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*10)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.BAKED_POTATO)) {
	    		    			if (in.equalsIgnoreCase("Farming")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*8);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*2)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.BEETROOT_SOUP)) {
	    		    			if (in.equalsIgnoreCase("Farming")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*8);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*5)+" Supplies");
	    		    			}
	    		    		}else if (i.getType().equals(Material.RABBIT_STEW)) {
	    		    			if (in.equalsIgnoreCase("Farming")) {
	    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
	    		    			sp = sp+(am*8);
	    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*10)+" Supplies");
	    		    			}
	    		    		}else {
	    		    			sender.sendMessage(prf+ChatColor.RED+"That item is not able to be turned into supplies");
	    		    		}
	    		    		
	    		    		config.set("PlayerData."+b+".Business.Supplies", sp);
	    		    		//TODO Add Supplies Event
	    		    		try {
	    		    			saveConfigs();
	    		    	    } catch (Exception e) {
	    		    	    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    		    	    	}

	    					
	    					
	    				}else {
	    					
	    					String e = config.getString("PlayerData."+sender.getName()+".Employer");
	    					if (e.equalsIgnoreCase(args[2])) {
	    						String b = config.getString("PlayerData."+sender.getName()+".Employer");
	    						int sp = config.getInt("PlayerData."+b+".Business.Supplies");
		    					int wh = config.getInt("PlayerData."+b+".Properties.warehouses");
								int fc = config.getInt("PlayerData."+b+".Properties.factory");
								
		    					int ms = maxBusSup+(wh*100);
		    					
		    					
		    					if (sp >= ms) {
		    						sender.sendMessage(ChatColor.RED+"Business Needs to buy more warehouses to store any more supplies");
		    						return true;
		    					}
		    					//Player p = Bukkit.getPlayer(sender.getName());
		    					
		    					ItemStack i = p.getInventory().getItemInMainHand();
		    					
		    					int am = 1;
		    					
		    					if (args.length >= 2) {
		    						am = Integer.parseInt(args[1]);
		    					}
		    					
		    					if (am >= p.getInventory().getItemInMainHand().getAmount()) {
		    						am = p.getInventory().getItemInMainHand().getAmount();
		    					}
		    					
		    					int rev = config.getInt("PlayerData."+b+".Business.Revenue");
		    		    		int size = config.getInt("PlayerData."+b+".Business.Employee.Count")+1;
		    		    		int oc = config.getInt("PlayerData."+b+".Business.OperationCost");
		    		    		int sh = config.getInt("PlayerData."+b+".Business.Shares");
		    		    		
		    		    		String in = config.getString("PlayerData."+b+".Business.Industry");
		    		    		
		    		    		if (i.getType().equals(Material.IRON_ORE)) {
		    		    			if (in.equalsIgnoreCase("Mining")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*1);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.IRON_INGOT)) {
		    		    			if (in.equalsIgnoreCase("Mining")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*2);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*2)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.GOLD_ORE)) {
		    		    			if (in.equalsIgnoreCase("Mining")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*3);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*3)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.GOLD_INGOT)) {
		    		    			if (in.equalsIgnoreCase("Mining")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*4);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*4)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.DIAMOND)) {
		    		    			if (in.equalsIgnoreCase("Mining")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*10);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*10)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.EMERALD)) {
		    		    			if (in.equalsIgnoreCase("Mining")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*15);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*15)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.EMERALD_BLOCK)) {
		    		    			if (in.equalsIgnoreCase("Mining")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*135);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*135)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.DIAMOND_BLOCK)) {
		    		    			if (in.equalsIgnoreCase("Mining")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*90);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*90)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.IRON_BLOCK)) {
		    		    			if (in.equalsIgnoreCase("Mining")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*18);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*18)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.GOLD_BLOCK)) {
		    		    			if (in.equalsIgnoreCase("Mining")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*36);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*36)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.REDSTONE_BLOCK)) {
		    		    			if (in.equalsIgnoreCase("Redstone")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*9);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*9)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.REDSTONE)) {
		    		    			if (in.equalsIgnoreCase("Mining")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*1);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
		    		    			}else if (in.equalsIgnoreCase("Redstone")) {
			    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
			    		    			sp = sp+(am*2);
			    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
			    		    			}
		    		    		}else if (i.getType().equals(Material.REDSTONE_TORCH)) {
		    		    			if (in.equalsIgnoreCase("Redstone")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*2);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*2)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.REPEATER)) {
		    		    			if (in.equalsIgnoreCase("Redstone")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*3);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*3)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.REDSTONE_LAMP)) {
		    		    			if (in.equalsIgnoreCase("Redstone")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*5);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*5)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.OAK_LOG)) {
		    		    			if (in.equalsIgnoreCase("Timber")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*4);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*4)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.JUNGLE_LOG)) {
		    		    			if (in.equalsIgnoreCase("Timber")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*4);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*4)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.BIRCH_LOG)) {
		    		    			if (in.equalsIgnoreCase("Timber")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*4);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*4)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.DARK_OAK_LOG)) {
		    		    			if (in.equalsIgnoreCase("Timber")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*4);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*4)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.SPRUCE_LOG)) {
		    		    			if (in.equalsIgnoreCase("Timber")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*4);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*4)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.OAK_PLANKS)) {
		    		    			if (in.equalsIgnoreCase("Timber")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*1);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.DARK_OAK_PLANKS)) {
		    		    			if (in.equalsIgnoreCase("Timber")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*1);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.SPRUCE_PLANKS)) {
		    		    			if (in.equalsIgnoreCase("Timber")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*1);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.BIRCH_PLANKS)) {
		    		    			if (in.equalsIgnoreCase("Timber")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*1);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.JUNGLE_PLANKS)) {
		    		    			if (in.equalsIgnoreCase("Timber")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*1);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.WHEAT)) {
		    		    			if (in.equalsIgnoreCase("Farming")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*1);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.CARROT)) {
		    		    			if (in.equalsIgnoreCase("Farming")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*1);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.BEETROOT)) {
		    		    			if (in.equalsIgnoreCase("Farming")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*1);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.SWEET_BERRIES)) {
		    		    			if (in.equalsIgnoreCase("Farming")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*1);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.POTATO)) {
		    		    			if (in.equalsIgnoreCase("Farming")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*1);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.BREAD)) {
		    		    			if (in.equalsIgnoreCase("Farming")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*2);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*2)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.COOKED_BEEF)) {
		    		    			if (in.equalsIgnoreCase("Farming")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*2);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*2)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.COOKED_CHICKEN)) {
		    		    			if (in.equalsIgnoreCase("Farming")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*2);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*2)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.COOKED_MUTTON)) {
		    		    			if (in.equalsIgnoreCase("Farming")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*2);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*2)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.COOKED_PORKCHOP)) {
		    		    			if (in.equalsIgnoreCase("Farming")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*2);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*2)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.PUMPKIN_PIE)) {
		    		    			if (in.equalsIgnoreCase("Farming")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*5);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*5)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.SUGAR_CANE)) {
		    		    			if (in.equalsIgnoreCase("Farming")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*1);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*1)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.CAKE)) {
		    		    			if (in.equalsIgnoreCase("Farming")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*5);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*30)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.GOLDEN_CARROT)) {
		    		    			if (in.equalsIgnoreCase("Farming")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*8);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*10)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.BAKED_POTATO)) {
		    		    			if (in.equalsIgnoreCase("Farming")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*8);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*2)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.BEETROOT_SOUP)) {
		    		    			if (in.equalsIgnoreCase("Farming")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*8);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*5)+" Supplies");
		    		    			}
		    		    		}else if (i.getType().equals(Material.RABBIT_STEW)) {
		    		    			if (in.equalsIgnoreCase("Farming")) {
		    		    			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-am);
		    		    			sp = sp+(am*8);
		    		    			sender.sendMessage(prf+ChatColor.GREEN+"Turned in for "+(am*10)+" Supplies");
		    		    			}
		    		    		}else {
		    		    			sender.sendMessage(prf+ChatColor.RED+"That item is not able to be turned into supplies");
		    		    		}
		    		    		
		    		    		config.set("PlayerData."+b+".Business.Supplies", sp);
		    		    		try {
		    		    			saveConfigs();
		    		    	    } catch (Exception e1) {
		    		    	    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e1.getMessage());
		    		    	    	}
	    					}
	    				}
	    				
	    				
	    		    	return true;
	    			}else if (args[0].equalsIgnoreCase("bw")) {
	    				
	    				
	    				List<String> bso = config.getStringList("PlayerData.BusinessOwners");
						
						String n = sender.getName();
						
						if (bso.contains(n)) {
						
							if (econ.has(p.getName(), 1999999999)) {
								econ.withdrawPlayer(p.getName(), 1999999999);
								sender.sendMessage(prf+ChatColor.GREEN+"You have purchased your WORLD!");
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv create "+n+" NORMAL");
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuaddp "+n+" multiverse.teleport.self."+n);
							}else {
								sender.sendMessage(prf+ChatColor.RED+" That costs $1,999,999,999");
							}
							
						}else {
							sender.sendMessage(prf+ChatColor.RED+" You Must own a business.");
						}
	    				
	    		    	return true;
	    			}else if (args[0].equalsIgnoreCase("hireplayer")) {
	    				
	    				//TODO FINISH Employee Hiring
	    				if (args[1].isEmpty()) {
	    					sender.sendMessage(prf+ChatColor.RED+"Please Specify a player to offer a job to..");
	    				}else {
	    					
	    					if (args[2].isEmpty()) {
	    						sender.sendMessage(prf+ChatColor.RED+"Please Specify a salary to offer");
	    					}else{
	    						Player pl = Bukkit.getPlayer(args[1]);
	    						List<String> bso = config.getStringList("PlayerData.BusinessOwners");
	    						
	    						String n = sender.getName();
	    						
	    						if (isBusiness(n)) {
	    						pl.sendMessage(prf+ChatColor.GREEN+sender.getName()+" has offered you a job  with a salary of $"+args[2]+" do /se job "+sender.getName()+" {Accept/Deny} to accept or Deny it");
	    						pe.add(pl);
	    						sender.sendMessage(prf+ChatColor.GREEN+"Job offered to "+pl.getName());
	    						config.set("PlayerData."+pl.getName()+".Employer", n);
	    						config.set("PlayerData."+pl.getName()+".Salary", Integer.valueOf(args[2]));
	    						try {
	    							saveConfigs();
	    					    } catch (Exception e) {
	    					    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    					    	}
	    						}
	    					}
	    					
	    				}
	    				
	    		    	return true;
	    			}else if (args[0].equalsIgnoreCase("buyshop")) {
	    				
	    				
	    				//TODO SET COST TO 1500000
	    					if (args.length <= 1) {
	    						
	    					}else {
	    						
	    						if (econ.has(p.getName(), 3000000)) {
	    							if (srs.contains(args[1])) {
	    								//ProtectedRegion RLM = wgRM.getRegion(args[1]);
	    							
	    								//if (sr.contains(RLM)) {
	    									econ.withdrawPlayer(p.getName(), 3000000);
	    									//RLM.getOwners();
	    									//RLM.getOwners().addPlayer(sender.getName());
	    									sender.sendMessage(prf+ChatColor.GREEN+"You have purchased your shop");
	    									///rg addowner shop1 Wintergrasped
	    									Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg addowner "+args[1]+" "+sender.getName()+" -w world");
	    								//}else {
	    									//TODO REGION NOT FOUND
	    									//sender.sendMessage(prf+ChatColor.RED+"Region Not Found");
	    								//}
	    							
	    							
	    							}else {
	    								//TODO REGION NAME NOT FOUND
	    								sender.sendMessage(prf+ChatColor.RED+"Region Name Not Found");
	    							}
	    						}else {
	    							//TODO NO MONEY MESSAGE
	    							sender.sendMessage(prf+ChatColor.RED+"You Are Too Broke For This!");
	    						}
	    						
	    					}

	    					
	    					
	    				
	    				
	    		    	return true;
	    		    	
	    		    	//config.getString("PlayerData."+sender.getName()+".Employer");
	    			}else if (args[0].equalsIgnoreCase("se")) {
	    				
	    				
	    				config.set("PlayerData."+args[1]+".Employer", args[2]);
	    				
	    				List <String> el = new ArrayList();
	    				el = config.getStringList("PlayerData."+args[2]+".Employed");
	    				
	    				if (el.contains(args[1])) {
	    					el.remove(args[1]);
	    				}
	    				if (el.contains(args[1])) {
	    					el.remove(args[1]);
	    				}
	    				if (el.contains(args[1])) {
	    					el.remove(args[1]);
	    				}
	    				el.add(args[1]);
	    				config.set("PlayerData."+args[2]+".Employed", el);
	    				config.set("PlayerStocks.PlayerData."+args[1]+".Salary", Integer.parseInt(args[3]));
	    				
	    				try {
	    					saveConfigs();
	    			    } catch (Exception e) {
	    			    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    			    	}
	    				sender.sendMessage(prf+ args[1]+" employer has been set to "+args[2]);
	    				//ps se {PlayerName} {EmployerName} {Salary}
	    				
	    		    	return true;
	    			}else if (args[0].equalsIgnoreCase("wipe")) {
	    				
	    				Player T = Bukkit.getPlayer(args[1]);
	    				config.set("PlayerStocks.PlayerData."+args[1]+".Shares", 0);
	    				econ.withdrawPlayer(T.getName(), econ.getBalance(args[1]));
	    				sender.sendMessage(prf+ args[1]+" Wiped");
	    				try {
	    					saveConfigs();
	    			    } catch (Exception e) {
	    			    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    			    	}
	    				
	    		    	return true;
	    			}else if (args[0].equalsIgnoreCase("cr")) {
	    				
	    				
	    				rls = new ArrayList();
	    				config.set("ServerData.Rented", rls);
	    				try {
	    					saveConfigs();
	    			    } catch (Exception e) {
	    			    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    			    	}
	    				sender.sendMessage(prf+ "Cleared rental list");
	    				//ps se {PlayerName} {EmployerName}
	    				
	    		    	return true;
	    			}else if (args[0].equalsIgnoreCase("giveshares")) {
	    				
	    				
	    				if (sender.isOp()) {
	    					giveStock(args[1], Integer.parseInt(args[2]));
	    					sender.sendMessage(prf+ args[1]+" was given "+args[2]+" shares");
	    				}
	    				
	    				
	    		    	return true;
	    			}else if (args[0].equalsIgnoreCase("fns")) {
	    				
	    				
	    				if (args.length <= 1) {
	    					sender.sendMessage(FNS);
	    				}else {
	    					
	    					if (args[1].equalsIgnoreCase("s2")) {
	    						FNS = s2;
	    						sender.sendMessage(prf+"FNS Set to s2 "+s2);
	    					}else {
	    				FNS = args[1];
	    				sender.sendMessage(prf+"FNS Set to "+args[1]);
	    					}
	    				}
	    				
	    		    	return true;
	    			}else if (args[0].equalsIgnoreCase("rentstop")) {
	    				
	    				
	    				if (args.length <= 1) {
	    					sender.sendMessage(prf+"Please specify region name");
	    				}else {
	    					
	    					String r = args[1];
	    					
	    					if (rented.contains(r)) {
	    						String t = config.getString("ServerData.Rentals."+r+".Tenant");
	    		    			String ll = config.getString("ServerData.Rentals."+r+".LandLord");
	    		    			double prc = config.getDouble("ServerData.Rentals."+r+".Rent");
	    		    			
	    		    			//config.set("ServerData.Rentals."+r+".Tenant", rt.getName());
	    		                //config.set("ServerData.Rentals."+r+".LandLoard", rt.getLandLord());
	    		                //config.set("ServerData.Rentals."+r+".Rent", rt.getPrice());
	    		    			
	    		    			if (t.equalsIgnoreCase(sender.getName())) {
	    		    				rented.remove(rented.indexOf(r));
	    		    				sender.sendMessage(prf+"Rental contract stopped for "+r);
	    		    				if (Bukkit.getPlayer(ll).isOnline()) {
	    		    					Player l = Bukkit.getPlayer(ll);
	    		    					l.sendMessage(prf+"Rental contract stopped for "+r);
	    		    				}
	    		    				
	    		    				Rental rnt = new Rental(r, sender.getName(), ll, prc, "PayCycle");
	    		    				
	    		    				RentalStopEvent rts = new RentalStopEvent(rnt, sender.getName());
	                                Bukkit.getServer().getPluginManager().callEvent(rts);
	    		    				
	    		    				config.set("ServerData.Rentals."+r, null);
		    		    	    	config.set("ServerData.Rented", rented);
		    		    	    	try {
		    		    				saveConfigs();
		    		    		    } catch (Exception e) {
		    		    		    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
		    		    		    	}
	    		    				
	    		    			}else if (ll.equalsIgnoreCase(sender.getName())) {
	    		    				rented.remove(rented.indexOf(r));
	    		    				sender.sendMessage(prf+"Rental contract stopped for "+r);
	    		    				if (Bukkit.getPlayer(t).isOnline()) {
	    		    					Player l = Bukkit.getPlayer(ll);
	    		    					l.sendMessage(prf+"Rental contract stopped for "+r);
	    		    				}
	    		    			
	    		    				
	    		    				Rental rnt = new Rental(r, sender.getName(), ll, prc, "PayCycle");
	    		    				
	    		    				RentalStopEvent rts = new RentalStopEvent(rnt, sender.getName());
	                                Bukkit.getServer().getPluginManager().callEvent(rts);
	    		    			config.set("ServerData.Rentals."+r, null);
	    		    	    	config.set("ServerData.Rented", rented);
	    		    	    	try {
	    		    				saveConfigs();
	    		    		    } catch (Exception e) {
	    		    		    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    		    		    	}
	    		    				
	    		    			}
	    		    			
	    					}else {
	    						sender.sendMessage(prf+"That region is not currently rented.");
	    					}
	    				}
	    				
	    		    	return true;
	    			}else if (args[0].equalsIgnoreCase("dv")) {
	    				
	    				
	    				if (voted.contains(sender.getName())) {
	    					sender.sendMessage(prf+ChatColor.RED+"You have already voted this cycle");
	    				}else {
	    				vote = vote+1;
	    				voted.add(sender.getName());
	    				sender.sendMessage(prf+ChatColor.GREEN+"Voted for day");
	    				
	    				int majority = Bukkit.getOnlinePlayers().size()/2;
	    				
	    				if (vote >= majority) {
	    					vote = 0;
	    					voted = new ArrayList();
	    					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time set day");
	    					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather world sun");
	    					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast Day Vote passed");
	    					
	    				}
	    				
	    				}
	    				
	    		    	return true;
	    			}else if (args[0].equalsIgnoreCase("nv")) {
	    				
	    				
	    				if (voted.contains(sender.getName())) {
	    					sender.sendMessage(prf+ChatColor.RED+"You have already voted this cycle");
	    				}else {
	    				vote = vote+1;
	    				voted.add(sender.getName());
	    				sender.sendMessage(prf+ChatColor.GREEN+"Voted for night");
	    				
	    				int majority = Bukkit.getOnlinePlayers().size()/2;
	    				
	    				if (vote >= majority) {
	    					vote = 0;
	    					voted = new ArrayList();
	    					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time set night");
	    					
	    					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast Night Vote passed");
	    					
	    				}
	    				
	    				}
	    				
	    		    	return true;
	    			}else if (args[0].equalsIgnoreCase("wv")) {
	    				
	    				
	    				if (voted.contains(sender.getName())) {
	    					sender.sendMessage(prf+ChatColor.RED+"You have already voted this cycle");
	    				}else {
	    				vote = vote+1;
	    				voted.add(sender.getName());
	    				sender.sendMessage(prf+ChatColor.GREEN+"Voted for sun");
	    				
	    				int majority = Bukkit.getOnlinePlayers().size()/2;
	    				
	    				if (vote >= majority) {
	    					vote = 0;
	    					voted = new ArrayList();
	    					
	    					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather world sun");
	    					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast Weather Vote passed");
	    					
	    				}
	    				
	    				}
	    				
	    		    	return true;
	    			}else if (args[0].equalsIgnoreCase("job")) {
	    				
	    				//TODO FINISH Employee Hiring
	    				if (args[1].isEmpty()) {
	    					sender.sendMessage(prf+ChatColor.RED+"Please Specify WHO offered you a job");
	    				}else {
	    					Player bo = Bukkit.getPlayer(args[1]);
	    					
	    					if (pe.contains(Bukkit.getPlayer(sender.getName()))) {
	    						
	    						if (args[2].isEmpty()) {
	    							sender.sendMessage(prf+ChatColor.RED+"Please Specify a Accept or Deny");
	    						}else if (args[2].equalsIgnoreCase("accept")) {
	    								pe.remove(bo);
	    								bo.sendMessage(ChatColor.GREEN+sender.getName()+" Has accepted your job offering");
	    								sender.sendMessage(prf+ChatColor.GREEN+"Job Accepted, Now Get to Work!");
	    								econ.withdrawPlayer(bo.getName(), 10000);
	    								econ.depositPlayer(p.getName(), 1000);
	    								config.set("PlayerData."+sender.getName()+".Employer", args[1]);
	    	    						config.set("PlayerData."+sender.getName()+".Salary", 1000);
	    	    						//config.set("PlayerData."+pl.getName()+".Employer", n);
	    	    						//config.set("PlayerData."+pl.getName()+".Salary", Integer.valueOf(args[2]));
	    	    						
	    	    						List <String> el = new ArrayList();
	    	    	    				el = config.getStringList("PlayerData."+args[1]+".Employed");
	    	    	    				el.add(sender.getName());
	    	    	    				config.set("PlayerData."+sender.getName()+".Employed", el);
	    	    	    				try {
	    	    	    					saveConfigs();
	    	    	    			    } catch (Exception e) {
	    	    	    			    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    	    	    			    	}
	    								
	    								List<String> bso = config.getStringList("PlayerData.BusinessOwners");
	    	    						String b = args[1];
	    	    						String n = sender.getName();
	    	    						
	    	    						if (bso.contains(b)) {
	    	    							int rev = config.getInt("PlayerData."+b+".Business.Revenue");
	    	    	    		    		int size = config.getInt("PlayerData."+b+".Business.Employee.Count")+1;
	    	    	    		    		int oc = config.getInt("PlayerData."+b+".Business.OperationCost");
	    	    	    		    		int sh = config.getInt("PlayerData."+b+".Business.Shares");
	    	    	    		    		
	    	    	    		    		int profit = rev-oc;
	    	    	    		    		
	    	    	    		    		Business bs = new Business(b, rev, oc, profit, sh, size);
	    	    	    		    		
	    	    	    		    		
	    	    	    		    		//TODO FINISH LOGIC AND EMPLOYEE FUNCTIONS
	    	    	    		    		//config.set("PlayerData."+b+".Business.Employee.Count", size);
	    	    						}
	    								
	    							}else{
	    								p.sendMessage(ChatColor.RED+sender.getName()+" Has denied your job offering");
	    								sender.sendMessage(prf+ChatColor.RED+"Job Denied");
	    								pe.remove(p);
	    								
	    								
	    							}
	    						
	    				
	    					}else {
	    						sender.sendMessage(prf+ChatColor.RED+"No Pending Job offers");
	    					}
	    					
	    					
	    				}
	    				
	    		    	return true;
	    			}else if (args[0].equalsIgnoreCase("bsbuy")) {
	    				
	    				
	    				
	    					if (args[1].isEmpty()) {
	    						sender.sendMessage(prf+ChatColor.RED+"Specify a business owner");
	    					}else {
	    						List<String> bso = config.getStringList("PlayerData.BusinessOwners");
	    						String b = args[1];
	    						String n = sender.getName();
	    						
	    						if (args[1].equalsIgnoreCase(sender.getName())) {
	    							b = sender.getName();
	    						}
	    						
	    						if (isBusiness(b)) {
	    							
	    							int rev = config.getInt("PlayerData."+b+".Business.Revenue");
	    	    		    		int size = config.getInt("PlayerData."+b+".Business.Employee.Count")+1;
	    	    		    		int oc = config.getInt("PlayerData."+b+".Business.OperationCost");
	    	    		    		int sh = config.getInt("PlayerData."+b+".Business.Shares");
	    	    		    		List<String> shs = config.getStringList("PlayerData."+b+".Business.ShareHolders");
	    	    		    		int cs = 0;
	    	    		    		
	    	    		    		int profit = rev-oc;
	    	    		    		
	    	    		    		Business bs = new Business(b, rev, oc, profit, sh, size);
	    	    		    		
	    	    		    		if (!bls.contains(bs)) {
	    	    		    			bls.add(bs);
	    	    		    		}
	    	    		    		
	    	    		    		if (args[2].isEmpty()) {
	    	    		    		
	    	    		    		if (econ.has(p.getName(), bs.getStockPrice())) {
	    	    		    			econ.withdrawPlayer(p.getName(), bs.getStockPrice());
	    	    		    			stockPurchaseEvent spe = new stockPurchaseEvent(Bukkit.getPlayer(sender.getName()), "PlayerStocks", 1, psp);
	                                    Bukkit.getServer().getPluginManager().callEvent(spe);
	    	    		    			bs.setShares(bs.getShares()+1);
	    	    		    			if (shs.contains(p.getName())) {
	    	    		    				cs = config.getInt("PlayerData."+b+".Business.ShareHolderShares."+p.getName());
	    	    		    				
	    	    		    			}else {
	    	    		    				shs.add(p.getName());
	    	    		    			}
	    	    		    			cs = cs+1;
	    	    		    			config.set("PlayerData."+b+".Business.ShareHolderShares."+p.getName(), cs);
	    	    		    			config.set("PlayerData."+b+".Business.ShareHolders", shs);
	    	    		    			config.set("PlayerData."+n+".Stocks.Business."+bs.getOwner()+".shares", config.getInt("PlayerData."+n+".Stocks.Business."+bs.getOwner()+".shares")+1);
	    	    		    			try {
	    	    		    				saveConfigs();
	    	    		    		    } catch (Exception e) {
	    	    		    		    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    	    		    		    	}
	    	    		    			sender.sendMessage(prf+ChatColor.GREEN+"You bought 1 share of "+bs.getOwner()+"'s business for $"+bs.getStockPrice());
	    	    		    			
	    	    		    			Player bo = Bukkit.getPlayer(b);
	    	    		    			
	    	    		    			if (bo.isOnline()) {
	    	    		    			Bukkit.getPlayer(b).sendMessage(ChatColor.GREEN+""+n+" Just bought 1 share of your business!");
	    	    		    			}
	    	    		    			bs.setRev(bs.getRev()+bs.getStockPrice());
	    	    		    			config.set("PlayerData."+b+".Business.Revenue", rev+bs.getStockPrice());
	    	    		    			config.set("PlayerData."+b+".Business.Shares", sh+1);
	    	    		    			econ.depositPlayer(b, bs.getStockPrice()/2);
	    	    		    			try {
	    	    		    				saveConfigs();
	    	    		    		    } catch (Exception e) {
	    	    		    		    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    	    		    		    	}
	    	    		    		}else{
	    	    		    			sender.sendMessage(prf+"Not Enough Money");
	    	    		    		}
	    	    		    		}else {
	    	    		    			
	    	    		    			int a = Integer.parseInt(args[2]);
	    	    		    			double prs = bs.getStockPrice()*a;
	    	    		    			
	    	    		    			if (econ.has(p.getName(), prs)) {
		    	    		    			econ.withdrawPlayer(p.getName(), prs);
		    	    		    			
		    	    		    			stockPurchaseEvent spe = new stockPurchaseEvent(Bukkit.getPlayer(sender.getName()), "PlayerStocks", a, psp);
		                                    Bukkit.getServer().getPluginManager().callEvent(spe);
		    	    		    			
		                                    bs.setShares(bs.getShares()+a);
		    	    		    			config.set("PlayerData."+n+".Stocks.Business."+bs.getOwner()+".shares", config.getInt("PlayerData."+n+".Stocks.Business."+bs.getOwner()+".shares")+a);
		    	    		    			if (shs.contains(p.getName())) {
		    	    		    				cs = config.getInt("PlayerData."+b+".Business.ShareHolderShares."+p.getName());
		    	    		    				
		    	    		    			}else {
		    	    		    				shs.add(p.getName());
		    	    		    			}
		    	    		    			
		    	    		    			cs = cs+a;
		    	    		    			config.set("PlayerData."+b+".Business.ShareHolderShares."+p.getName(), cs);
		    	    		    			config.set("PlayerData."+b+".Business.ShareHolders", shs);
		    	    		    			config.set("PlayerData."+n+".Stocks.Business."+bs.getOwner()+".shares", config.getInt("PlayerData."+n+".Stocks.Business."+bs.getOwner()+".shares")+1);
		    	    		    			try {
		    	    		    				saveConfigs();
		    	    		    		    } catch (Exception e) {
		    	    		    		    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
		    	    		    		    	}
		    	    		    			sender.sendMessage(prf+ChatColor.GREEN+"You bought "+a+" shares of "+bs.getOwner()+"'s business for $"+prs);
		    	    		    			
		    	    		    			Player bo = Bukkit.getPlayer(b);
		    	    		    			
		    	    		    			if (bo.isOnline()) {
		    	    		    			Bukkit.getPlayer(b).sendMessage(ChatColor.GREEN+""+n+" Just bought "+a+" shares of your business!");
		    	    		    			}
		    	    		    			bs.setRev(bs.getRev()+prs);
		    	    		    			config.set("PlayerData."+b+".Business.Revenue", rev+prs);
		    	    		    			config.set("PlayerData."+b+".Business.Shares", sh+a);
		    	    		    			try {
		    	    		    				saveConfigs();
		    	    		    		    } catch (Exception e) {
		    	    		    		    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
		    	    		    		    	}
		    	    		    			econ.depositPlayer(b, prs/2);
		    	    		    		}else{
		    	    		    			sender.sendMessage(prf+"Not Enough Money");
		    	    		    		}
	    	    		    			
	    	    		    		}
	    							
	    						}else {
	    							sender.sendMessage(prf+ChatColor.RED+"That player does not own a business");
	    						}
	    						
	    					}
	    		    	return true;
	    			}else if (args[0].equalsIgnoreCase("bssell")) {
	    				
	    				//TODO FINISH BTOP
	    				
	    					if (args[1].isEmpty()) {
	    						
	    					}else {
	    						List<String> bso = config.getStringList("PlayerData.BusinessOwners");
	    						String b = args[1];
	    						String n = sender.getName();
	    						
	    						
	    						String msg = "";
	    						for (String st : args) {
	    							msg = msg+st;
	    						}
	    						
	    						
	    						
	    						if (args[1].equalsIgnoreCase(sender.getName())) {
	    							b = sender.getName();
	    						}
	    						
	    						if (isBusiness(b)) {
	    							
	    							int rev = config.getInt("PlayerData."+b+".Business.Revenue");
	    	    		    		int size = config.getInt("PlayerData."+b+".Business.Employee.Count")+1;
	    	    		    		int oc = config.getInt("PlayerData."+b+".Business.OperationCost");
	    	    		    		int sh = config.getInt("PlayerData."+b+".Business.Shares");
	    	    		    		List<String> shs = config.getStringList("PlayerData."+b+".Business.ShareHolders");
	    	    		    		int cs = 0;
	    	    		    		int profit = rev-oc;
	    	    		    		
	    	    		    		Business bs = new Business(b, rev, oc, profit, sh, size);
	    	    		    		if (args[2].isEmpty()) {
	    	    		    			
	    	    		    		if (config.getInt("PlayerData."+n+".Stocks.Business."+bs.getOwner()+".shares") >= 1) {
	    	    		    			econ.depositPlayer(n, bs.getStockPrice());
	    	    		    			
	    	    		    			stockSaleEvent spe = new stockSaleEvent(Bukkit.getPlayer(sender.getName()), "PlayerStocks", 1, psp);
	                                    Bukkit.getServer().getPluginManager().callEvent(spe);
	    	    		    			
	                                    if (shs.contains(p.getName())) {
	    	    		    				cs = config.getInt("PlayerData."+b+".Business.ShareHolderShares."+p.getName());
	    	    		    				shs.add(p.getName());
	    	    		    			}
	    	    		    			cs = cs-1;
	                                    
	    	    		    			if (cs <= 0) {
	    	    		    				shs.remove(p.getName());
	    	    		    			}
	    	    		    			
	    	    		    			config.set("PlayerData."+b+".Business.ShareHolderShares."+p.getName(), cs);
	    	    		    			config.set("PlayerData."+b+".Business.ShareHolders", shs);
	    	    		    			
	    	    		    			bs.setShares(bs.getShares()-1);
	    	    		    			config.set("PlayerData."+n+".Stocks.Business."+bs.getOwner()+".shares", config.getInt("PlayerData."+n+".Stocks.Business."+bs.getOwner()+".shares")-1);
	    	    		    			sender.sendMessage(prf+ChatColor.GREEN+"You sold 1 share of "+bs.getOwner()+"'s business for $"+bs.getStockPrice());
	    	    		    			Player bo = Bukkit.getPlayer(b);
	    	    		    			
	    	    		    			if (bo.isOnline()) {
	    	    		    				Bukkit.getPlayer(b).sendMessage(ChatColor.GREEN+""+n+" Just sold 1 share of your business!");
	    	    		    			}
	    	    		    			bs.setRev(bs.getRev()-bs.getStockPrice());
	    	    		    			config.set("PlayerData."+b+".Business.Revenue", rev-bs.getStockPrice());
	    	    		    			config.set("PlayerData."+b+".Business.Shares", sh-1);
	    	    		    			econ.withdrawPlayer(b, bs.getStockPrice()/2);
	    	    		    		}
	    							
	    	    		    		}else {
	    	    		    			
	    	    		    			int a = Integer.parseInt(args[2]);
	    	    		    			double prs = bs.getStockPrice()*a;
	    	    		    			
	    	    		    			if (config.getInt("PlayerData."+n+".Stocks.Business."+bs.getOwner()+".shares") >= a) {
		    	    		    			econ.depositPlayer(n, prs);
		    	    		    			
		    	    		    			stockSaleEvent spe = new stockSaleEvent(Bukkit.getPlayer(sender.getName()), "PlayerStocks", a, psp);
		                                    Bukkit.getServer().getPluginManager().callEvent(spe);
		    	    		    			
		    	    		    			bs.setShares(bs.getShares()-a);
		    	    		    			config.set("PlayerData."+n+".Stocks.Business."+bs.getOwner()+".shares", config.getInt("PlayerData."+n+".Stocks.Business."+bs.getOwner()+".shares")-a);
		    	    		    			sender.sendMessage(prf+ChatColor.GREEN+"You sold "+stocks.format(a)+" shares of "+bs.getOwner()+"'s business for $"+price.format(prs));
		    	    		    			Player bo = Bukkit.getPlayer(b);
		    	    		    			
		    	    		    			if (bo.isOnline()) {
		    	    		    				Bukkit.getPlayer(b).sendMessage(ChatColor.GREEN+""+n+" Just sold "+stocks.format(a)+" shares of your business!");
		    	    		    			}
		    	    		    			bs.setRev(bs.getRev()-bs.getStockPrice());
		    	    		    			config.set("PlayerData."+b+".Business.Revenue", rev-prs);
		    	    		    			config.set("PlayerData."+b+".Business.Shares", sh-a);
		    	    		    			econ.withdrawPlayer(b, prs/2);
		    	    		    		}
	    	    		    		}
	    						}else {
	    							sender.sendMessage(prf+ChatColor.RED+"That player does not own a business");
	    						}
	    						
	    					}
	    		    	return true;
	    			}
	    			
	    			//businessLoop();
	    			//https://discord.gg/GuBQv6M
	    			if (args[0].equalsIgnoreCase("discord")) {
	    				sender.sendMessage(prf+ChatColor.GREEN+"https://discord.gg/GuBQv6M");
	    				return true;
	    			}else
	    			if (args[0].equalsIgnoreCase("bal")) {
	    				String n = sender.getName();
	    				int S = config.getInt("PlayerStocks.PlayerData."+n+".Shares");
	    				if (S >= 1) {
	    					double v = S*psp;
	    					sender.sendMessage(prf+ChatColor.GOLD+"You have "+stocks.format(S)+" shares worth $"+price.format(v)+" at $"+price.format(psp)+" per share");
	    				}else {
	    					sender.sendMessage(prf+ChatColor.RED+""+ChatColor.BOLD+"You do not own any PlayerStock Shares.");
	    				}
	    			return true;
	    			}else if (args[0].equalsIgnoreCase("who")) {
	    				sender.sendMessage(prf+ChatColor.GOLD+""+ChatColor.BOLD+"Created and Developed by "+ChatColor.AQUA+""+ChatColor.BOLD+" Wintergrasped"+ChatColor.GOLD+""+ChatColor.BOLD+" For"+ChatColor.RED+""+ChatColor.BOLD+" Small Time Survival");
	    			
	    			
	    			
	    			
	    			
	    			//TODO Finish employees and business systems
	    			//========================SV SUB SECTION====================================
	    			}else if (args[0].equalsIgnoreCase("sv")) {
	    				String n = sender.getName();
	    				if (args.length >= 1) {
	    					
	    					//if (args[0].equalsIgnoreCase("command_here")) {
	    						
	    					//}
	    					
	    					if (args[1].equalsIgnoreCase("lotto")) {
	    						int ltn = 0;
	    						ltn = random(100000,999000);
	    						sender.sendMessage(prf+"You bought a lotto ticket #"+ltn);
	    						config.set("PlayerData.LottoTicketNumber", ltn);
	    						try {
	    							saveConfigs();
	    					    } catch (Exception e) {
	    					    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    					    	}
	    						return true;
	    					}
	    					
	    					if (args[1].equalsIgnoreCase("business")) {
	    						
	    						if (econ.has(p.getName(), prc)) {
	    							String ubi ="";
	    							ubi = ""+random(2500000, 9999999)+Bukkit.getPlayer(sender.getName()).getUniqueId();
	    							//if (config.get("PlayerData."+n+".UBI")) {
	    								
	    							//}
	    							sender.sendMessage(prf+"You have purchased your business license UBI:"+ubi);
	    							config.set("PlayerData."+n+".UBI", ubi);
	    							config.set("PlayerData."+n+".Business.Tier", 1);
	    							config.set("PlayerData."+n+".Business.Employees", 0);
	    							List<String> bso = config.getStringList("PlayerData.BusinessOwners");
	    							bso.add(n);
	    							config.set("PlayerData.BusinessOwners", bso);
	    							econ.withdrawPlayer(p.getName(), prc);
	    							try {
	    								saveConfigs();
	    						    } catch (Exception e) {
	    						    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    						    	}
	    						}else {
	    							sender.sendMessage(prf+"You are too broke for this.");
	    							sender.sendMessage(prf+"A Business license costs $"+price.format(prc));
	    						}
	    						return true;
	    					}else if (args[1].equalsIgnoreCase("corp")) {
	    						
	    						int cprc = cCost;
	    						
	    						if (econ.has(p.getName(), cprc)) {
	    							String ubi ="";
	    							ubi = ""+random(2500000, 9999999)+Bukkit.getPlayer(sender.getName()).getUniqueId();
	    							//if (config.get("PlayerData."+n+".UBI")) {
	    								
	    							//}
	    							sender.sendMessage(prf+"You have purchased your Corperation license UCI:"+ubi);
	    							config.set("PlayerData."+n+".UCI", ubi);
	    							config.set("PlayerData."+n+".Corp.Tier", 1);
	    							config.set("PlayerData."+n+".Corp.Employees", 0);
	    							List<String> cso = config.getStringList("PlayerData.CorpOwners");
	    							cso.add(n);
	    							config.set("PlayerData.CorpOwners", cso);
	    							econ.withdrawPlayer(n, cprc);
	    							try {
	    								saveConfigs();
	    						    } catch (Exception e) {
	    						    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    						    	}
	    						}else {
	    							sender.sendMessage(prf+"You are too broke for this.");
	    							sender.sendMessage(prf+"A Corp license costs $"+price.format(cprc));
	    						}
	    						//A thingy
	    						return true;
	    					}else if (args[1].equalsIgnoreCase("train")) {
	    						if (isBusiness(sender.getName())) {
	    							List<Integer> eso = config.getIntegerList("PlayerData."+sender.getName()+".Business.EmployeeID.List");
	    						if (args.length <= 2) {
	    							sender.sendMessage(ChatColor.RED+"Specify and Employee ID");
	    						}
	    						
	    						
	    						if (eso.contains(Integer.valueOf(args[2]))) {
	    						if (econ.has(sender.getName(), 5500)) {
	    						config.set("PlayerData."+n+".Business.Employees."+args[2]+".TrainingCount", 0);
	    						config.set("PlayerData."+n+".Business.Employees."+args[2]+".IsTraining", true);
	    						config.set("PlayerData."+n+".Business.Employees."+args[2]+".Training", config.getInt("PlayerData."+n+".Business.Employees."+args[2]+".Training")+1);
	    	    				config.set("PlayerData."+n+".Business.Employees."+args[2]+".Revenue", 0);
	    	    				config.set("PlayerData."+n+".Business.Employees."+args[2]+".Mood", random(4,10));
	    	    				try {
	    	    					saveConfigs();
	    	    			    } catch (Exception e) {
	    	    			    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    	    			    	}
	    	    				sender.sendMessage(ChatColor.GREEN+"You sent "+args[2]+" to training");
	    						}else {
	    							sender.sendMessage(ChatColor.RED+"You Need $5500");
	    						}
	    						
	    						}else {
	    							sender.sendMessage(ChatColor.RED+"You dont have an employee ID "+args[2]);
	    							sender.sendMessage(ChatColor.RED+"$2 fine for making me add this code cause im a salty dev");
	    							econ.withdraw(sender.getName(), 2);
	    						}
	    						
	    						
	    						}else {
	    							sender.sendMessage(ChatColor.RED+"You need to own a business to have an employee to train");
	    							sender.sendMessage(ChatColor.RED+"$2 fine for making me add this code");
	    							econ.withdraw(sender.getName(), 2);
	    						}
	    					}else if (args[1].equalsIgnoreCase("party")) {
	    						
	    						if (econ.has(sender.getName(), 1500)) {
	    						List<Integer> eso = config.getIntegerList("PlayerData."+sender.getName()+".Business.EmployeeID.List");
	    		    				for (int es : eso) {
	    		    					config.set("PlayerData."+n+".Business.Employees."+eso+".Mood", random(5,10));
	    		    				}
	    		    				sender.sendMessage(ChatColor.GREEN+"You threw a company party for your employees");
	    						}else {
	    							List<Integer> eso = config.getIntegerList("PlayerData."+sender.getName()+".Business.EmployeeID.List");
	    		    				for (int es : eso) {
	    		    					config.set("PlayerData."+n+".Business.Employees."+eso+".Mood", random(0,4));
	    		    				}
	    		    				sender.sendMessage(ChatColor.RED+"You couldn't afford a company party for your employees. Everyone is not happy");
	    						}
	    						try {
	    							saveConfigs();
	    					    } catch (Exception e) {
	    					    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    					    	}
	    						
	    					}else if (args[1].equalsIgnoreCase("hire")) {
	    						
	    						
	    						sender.sendMessage("AI Employee's are currently disabled. Please hire your neighbor instead");
	    						
	    						/*
	    						if (isBusiness(sender.getName())) {
	    						
	    							int size = config.getInt("PlayerData."+sender.getName()+".Business.Employee.Count")+1;
	    							int wh = config.getInt("PlayerData."+sender.getName()+".Properties.warehouses");
									int fc = config.getInt("PlayerData."+sender.getName()+".Properties.factory");
	    							
	    							if (size >= maxBusSize) {
	    								
	    								
	    								if (isCorp(sender.getName())) {
	    								
	    									int mbc = maxBusSize+(config.getInt("WareHouseWorkers")*wh);
	    									mbc = mbc+(config.getInt("FactoryWorkers")*fc);
	    									
	    											if (size >= mbc) {
	    												
	    												sender.sendMessage(ChatColor.RED+"You need to buy more Warehouses / Factoryies to expand more");
	    												return true;
	    											}
	    									
	    									int prcs = employeeCost;
	    		    						if (econ.has(p.getName(), prcs)) {
	    		    							int ec = config.getInt("PlayerData."+sender.getName()+".Business.Employee.Count");
	    		    							int id = gid;
	    		    							
	    		    							econ.withdrawPlayer(p.getName(), prcs);
	    		    							
	    		    							id = ec+1;
	    		    							
	    		    							if (id <= 1) {
	    		    								id = 1;
	    		    							}
	    		    							
	    		    							String IDS = id+"";
	    		    							//if (id == 0) {
	    		    								//id = gid+1;
	    		    							//}
	    		    							
	    		    							List<Integer> eids = config.getIntegerList("PlayerData."+sender.getName()+".Business.EmployeeID.List");
	    		    							
	    		    							while (eids.contains(id)) {
	    		    								id = id+1;
	    		    							}
	    		    							
	    		    							/*Collections.sort(eids);
	    		    							if (!eids.isEmpty()) {
	    		    								id = eids.get(eids.size()-1)+1;
	    		    								if (eids.contains(id)) {
	    		    									id = id+1;
	    		    								}
	    		    								if (eids.contains(id)) {
	    		    									id = id+10;
	    		    								}
	    		    								if (eids.contains(id)) {
	    		    									id = id+20;
	    		    								}
	    		    								if (eids.contains(id)) {
	    		    									id = id+30;
	    		    								}
	    		    								if (eids.contains(id)) {
	    		    									id = id+40;
	    		    								}
	    		    								if (eids.contains(id)) {
	    		    									id = id+50;
	    		    								}
	    		    								if (eids.contains(id)) {
	    		    									id = id+60;
	    		    								}
	    		    								if (eids.contains(id)) {
	    		    									id = id+70;
	    		    								}
	    		    								if (eids.contains(id)) {
	    		    									id = id+80;
	    		    								}
	    		    								if (eids.contains(id)) {
	    		    									id = id+90;
	    		    								}
	    		    								if (eids.contains(id)) {
	    		    									id = id+100;
	    		    								}
	    		    								if (eids.contains(id)) {
	    		    									id = id+200;
	    		    								}
	    		    								if (eids.contains(id)) {
	    		    									id = id+300;
	    		    								}
	    		    							}else {
	    		    								id = 1;
	    		    							}
	    		    							
	    		    							Employee a = new Employee(id, random(10,100), random2(10,200));
	    		    							
	    		    							
	    		    							
	    		    							config.set("PlayerData."+n+".Business.Employee.Count", ec);
	    		    							config.set("PlayerData."+n+".Business.Employees."+IDS+".Salary", a.getSalary());
	    			    	    				config.set("PlayerData."+n+".Business.Employees."+IDS+".Revenue", a.getRevenue());
	    			    	    				config.set("PlayerData."+n+".Business.Employees."+IDS+".Mood", 10);
	    			    	    				
	    	    								eids.add(id);
	    			    	    				config.set("PlayerData."+n+".Business.EmployeeID.List", eids);
	    			    	    				 sender.sendMessage(prf+ChatColor.GOLD+"You have hired an employee");
	    			    	    				 try {
	    			    	    						saveConfigs();
	    			    	    				    } catch (Exception e) {
	    			    	    				    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    			    	    				    	}
	    			    	    				 gid = gid+1;
	    			    
	    		    						}else {
	    		    							sender.sendMessage(prf+"You are too broke for this.");
	    		    							sender.sendMessage(prf+"A Business license costs $"+price.format(prc));
	    		    						}
	    									
	    									
	    									
	    								}else{
	    									sender.sendMessage(prf+ChatColor.RED+"You need a Corporation License to hire more employees!");
	    								}
	    								
	    								
	    							}else{
	    							
	    						//int prc = config.getInt("Economy.Prices.Employee");
	    						int prcs = 10000;
	    						if (econ.has(p.getName(), prcs)) {
	    							int ec = config.getInt("PlayerData."+n+".Business.Employee.Count");
	    							int id = gid;
	    							
	    							econ.withdrawPlayer(p.getName(), prcs);
	    							
	    							id = ec+1;
	    							
	    							if (id <= 1) {
	    								id = 1;
	    							}
	    							
	    							String IDS = id+"";
	    							//if (id == 0) {
	    								//id = gid+1;
	    							//}
	    							int r1 = random(10,100);
	    							int r2 = random(10,100);
	    							
	    							if (r1==r2) {
	    								r2 = random(20,110);
	    							}
	    							List<Integer> eids = config.getIntegerList("PlayerData."+n+".Business.EmployeeID.List");
	    							while (eids.contains(id)) {
	    								id = id+1;
	    							}
	    							Employee a = new Employee(id, r1, r2);
	    							
	    							ec = ec+1;
	    							config.set("PlayerData."+n+".Business.Employee.Count", ec);
	    							config.set("PlayerData."+n+".Business.Employees."+id+".Salary", a.getSalary());
		    	    				config.set("PlayerData."+n+".Business.Employees."+id+".Revenue", a.getRevenue());
		    	    				
    								eids.add(id);
		    	    				config.set("PlayerData."+n+".Business.EmployeeID.List", eids);
		    	    				 sender.sendMessage(prf+ChatColor.GOLD+"You have hired an employee");
		    	    				 try {
		    	    						saveConfigs();
		    	    				    } catch (Exception e) {
		    	    				    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
		    	    				    	}
		    	    				 gid = gid+1;
		    
	    						}else {
	    							sender.sendMessage(prf+"You are too broke for this.");
	    							sender.sendMessage(prf+"An employee costs $"+prcs);
	    						}
	    						return true;
	    							}
	    						}else {
	    							sender.sendMessage(prf+"Uhhh... Maybe you should own a business first?");
	    						}
	    						return true;
	    						
	    						
	    						*/
	    						
	    						
	    					}else if (args[1].equalsIgnoreCase("fire")) {
	    						
	    						if (args[2].isEmpty()) {
	    							
	    						}else {
	    						
	    								int id = Integer.parseInt(args[2]);
	    								int sal = config.getInt("PlayerData."+n+".Business.Employees."+id+".Salary");
	    								int rev = config.getInt("PlayerData."+n+".Business.Employees."+id+".Revenue");
	    								Employee a = new Employee(id, sal, rev);
	    								int ec = config.getInt("PlayerData."+n+".Business.Employee.Count");
	    								if (ec >=1 ) {
	    								ec = ec-1;
	    								}else{
	    									ec = 0;
	    								}
	    								List<Integer> eids = config.getIntegerList("PlayerData."+n+".Business.EmployeeID.List");
	    								
	    								if (eids.contains(id)) {
	    								eids.remove((eids.indexOf(id)));
	    								config.set("PlayerData."+n+".Business.Employee.Count", ec);
	    								config.set("PlayerData."+n+".Business.Employees."+id, null);
	    								config.set("PlayerData."+n+".Business.Employees."+id+".Revenue", null);
	    								config.set("PlayerData."+n+".Business.Employees."+id+".Salary", null);
	    								config.set("PlayerData."+n+".Business.EmployeeID.List", eids);
	    								saveConfigs();
	    								sender.sendMessage(prf+"You fired Employee ID: "+args[2]);
	    								}else{
	    									sender.sendMessage(prf+"Invalid Employee ID: "+args[2]);
	    								}
	    							return true;
	    						}
	    					}else if (args[1].equalsIgnoreCase("employee")) {
	    						
	    						if (args[2].isEmpty()) {
	    							
	    						}else {
	    							if (args[2].equalsIgnoreCase("list")) {
	    								int ec = config.getInt("PlayerData."+n+".Business.Employee.Count");
	    								sender.sendMessage(prf+ChatColor.GOLD+"Your Active Employees: "+ec);
	    								List<Integer> ems = config.getIntegerList("PlayerData."+n+".Business.EmployeeID.List");
	    								//sender.sendMessage(prf+"List > "+ems);
	    								for (int ei : ems) {
	    									int sal = config.getInt("PlayerData."+n+".Business.Employees."+ei+".Salary");
	    									int rev = config.getInt("PlayerData."+n+".Business.Employees."+ei+".Revenue");
	    									//Employee e = new Employee(ei, sal, rev);
	    									sender.sendMessage(prf+"Employee ID: "+ei+" Salary: "+sal+" Revenue: "+rev+"");
	    								}
	    								saveConfigs();
	    								return true;
	    							}
	    						}
	    					}
	    					
	    						//List<Integer> eso = config.getIntegerList("PlayerData."+b+".Business.Employees");
	    	    				//int sal = config.getInt("PlayerData."+b+".Business.Employees."+es+".Salary");
	    	    				//int rev = config.getInt("PlayerData."+b+".Business.Employees."+es+".Revenue");;
	    					
	    					
	    				}
	    			}else if (args[0].equalsIgnoreCase("roll")) {
	    				String n = sender.getName();

	    					if (econ.has(p.getName(), 1000)) {
	    						econ.withdrawPlayer(p.getName(), 1000);
	    						int i = random(1,100);
	    						int b = random(1, 100);
	    						
	    						if (i==b) {
	    							int w = random(1000,50000);
	    							econ.depositPlayer(p.getName(), w);
	    							sender.sendMessage(prf+ChatColor.GOLD+"You won $"+w);
	    						}else if (i >= b) {
	    							int w = random(100,990);
	    							econ.depositPlayer(p.getName(), w);
	    							sender.sendMessage(prf+ChatColor.GOLD+"You won $"+w);
	    						}else {
	    							sender.sendMessage(prf+ChatColor.RED+"You Rolled "+i+" the target was "+b);
	    						}
	    					}else {
	    						sender.sendMessage(prf+ChatColor.RED+"Not Enough money");
	    					}
	    					return true;
	    				}
	    		}
	    	
	    			
	    			
	    			if (args[0].equalsIgnoreCase("sell")) {
	    				
	    				if (args.length <= 1) {
	    				String n = sender.getName();
	    				int S = config.getInt("PlayerStocks.PlayerData."+n+".Shares");
	    				if (S >= 1) {
	    					config.set("PlayerStocks.PlayerData."+n+".Shares", S-1);
	    					econ.depositPlayer(n, psp);
	    					
	    					stockSaleEvent spe = new stockSaleEvent(Bukkit.getPlayer(sender.getName()), "PlayerStocks", 1, psp);
                            Bukkit.getServer().getPluginManager().callEvent(spe);
	    					
	    					saveConfigs();
	    					sender.sendMessage(prf+"You sold one PlayerStock share for $"+price.format(psp));
	    				}else {
	    					sender.sendMessage(prf+"You dont have enough PlayerStock shares.");
	    				}
	    				
	    				}else{
			    				String n = sender.getName();
			    				int S = config.getInt("PlayerStocks.PlayerData."+n+".Shares");
			    				int a = Integer.parseInt(args[1]);
			    				if (S >= a) {
			    					config.set("PlayerStocks.PlayerData."+n+".Shares", S-a);
			    					econ.depositPlayer(n, psp*a);
			    					
			    					stockSaleEvent spe = new stockSaleEvent(Bukkit.getPlayer(sender.getName()), "PlayerStocks", a, psp);
                                    Bukkit.getServer().getPluginManager().callEvent(spe);
			    					
			    					saveConfigs();
			    					sender.sendMessage(prf+"You sold "+a+" PlayerStock shares for $"+price.format((psp*a)));
			    				}else {
			    					sender.sendMessage(prf+"You dont have enough PlayerStock shares.");
			    				}

			    			}
	    				
	    				return true;
	    			}
	    		
	  
	    	}else if (cmd.getName().equalsIgnoreCase("ecodebug")) {
				
	    		
	    		//config.set("PlayerStocks.JoinMultiplyer", 1.005);
	    		saveConfigs();
				sender.sendMessage(prf+""+psp);
				sender.sendMessage(prf+""+psjm);
				sender.sendMessage(prf+""+joins);
				//joins = 0;
			return true;
			
		}else if (cmd.getName().equalsIgnoreCase("psh")) {
			
			int i = 1;
			p = Bukkit.getPlayer(sender.getName());
			PlayerIntro.marketHelp(this, p);
			if (args.length == 0) {
				sender.sendMessage(prf+ChatColor.AQUA+""+ChatColor.BOLD+"PlayerStocks are a stock you can buy shares of the price moves up and down based on player activity on the sevrer. More active player means you can make more profit on your PlayerStocks so the more active the server is the more active your PlayerStock investment is.");
				
				sender.sendMessage(prf+ChatColor.GREEN+"Commands: ");
				sender.sendMessage(prf+ChatColor.GOLD+"/psp"+ ChatColor.RED +" :"+ChatColor.GOLD+" shows current price of 1 share of PlayerStocks");
				sender.sendMessage(prf+ChatColor.GOLD+"/se buy "+ ChatColor.RED+":"+ChatColor.GOLD+" buys one share at current price");
				sender.sendMessage(prf+ChatColor.GOLD+"/se sell "+ ChatColor.RED+":"+ChatColor.GOLD+" sells one share at current price");
				sender.sendMessage(prf+ChatColor.GOLD+"/se bal "+ ChatColor.RED+":"+ChatColor.GOLD+" shows the value of your shares");
				sender.sendMessage(prf+ChatColor.GOLD+"/se sv "+ChatColor.GOLD+" lotto : buys a lotto ticket");
				sender.sendMessage(prf+ChatColor.GOLD+"/se sv business "+ChatColor.RED+":"+ChatColor.GOLD+" buys a business license");
				sender.sendMessage(prf+ChatColor.GOLD+"/se sv hire "+ChatColor.RED+":"+ChatColor.GOLD+" hires 1 Employee");
				sender.sendMessage(prf+ChatColor.GOLD+"/se sv fire [Employee ID] "+ChatColor.RED+":"+ChatColor.GOLD+" fires the specified ID");
				sender.sendMessage(prf+ChatColor.GOLD+"/se sv employee list "+ChatColor.RED+":"+ChatColor.GOLD+" lists your employees");
				
				sender.sendMessage(prf+ChatColor.AQUA+"do /psh 1 for next page");
			
			return true;
			}else if (Integer.parseInt(args[0]) == 1) {
				
				
				sender.sendMessage(prf+ChatColor.GOLD+"/se roll "+ChatColor.RED+":"+ChatColor.GOLD+" costs $1,000 rolls a number for a chance to win money 1:100 chance of over $50K 1:50 chance of some smaller ammount.");
				sender.sendMessage(prf+ChatColor.GOLD+"/econ price "+ChatColor.RED+":"+ChatColor.GOLD+" displays the price list");
				sender.sendMessage(prf+ChatColor.GOLD+"/se who "+ChatColor.RED+":"+ChatColor.GOLD+" does a thing");
				sender.sendMessage(prf+ChatColor.GOLD+"/se dv "+ChatColor.RED+":"+ChatColor.GOLD+" Vote for day");
				sender.sendMessage(prf+ChatColor.GOLD+"/se wv "+ChatColor.RED+":"+ChatColor.GOLD+" Vote for sun");
				sender.sendMessage(prf+ChatColor.BLUE+"/se blist"+ChatColor.RED+ChatColor.BOLD+":"+ChatColor.BLUE+" Lists all Business owners and their starts");
				sender.sendMessage(prf+ChatColor.BLUE+"/se bslist"+ChatColor.RED+ChatColor.BOLD+":"+ChatColor.BLUE+" Lists all Business owners and their Stock starts");
				sender.sendMessage(prf+ChatColor.BLUE+"/se bsbuy {OWNER NAME} {NUMBER OF SHARE}"+ChatColor.RED+ChatColor.BOLD+":"+ChatColor.BLUE+" buys specified number of shares of specified business");
				sender.sendMessage(prf+ChatColor.BLUE+"/se bssell {OWNER NAME} {NUMBER OF SHARE}"+ChatColor.RED+ChatColor.BOLD+":"+ChatColor.BLUE+" sells specified number of shares of specified business");
				sender.sendMessage(prf+ChatColor.AQUA+"do /psh 2 for next page");
				
				
			} else if (Integer.parseInt(args[0]) == 2) {
				
				sender.sendMessage(prf+ChatColor.BLUE+"/se buyshop {ShopName} "+ChatColor.RED+ChatColor.BOLD+":"+ChatColor.BLUE+" buys the specified shop");
				sender.sendMessage(prf+ChatColor.BLUE+"Business Owners: /se dbs {Ammount} "+ChatColor.RED+ChatColor.BOLD+":"+ChatColor.BLUE+" turns the Specified ammount of the item your holding in for business supplies (Must be applicable to your industry)");
				sender.sendMessage(prf+ChatColor.BLUE+"Employees: /se dbs {Ammount} {BusinessOwner}"+ChatColor.RED+ChatColor.BOLD+":"+ChatColor.BLUE+" turns the Specified ammount of the item your holding in for business supplies (Must be applicable to your industry)");
				sender.sendMessage(prf + ChatColor.BLUE + "/se si {Industry} " + ChatColor.RED + ChatColor.BOLD + ":"
						+ ChatColor.BLUE
						+ " Select the Industry for you business (Mining, Timber, Redstone or Farming) {USE THIS ONLY ONCE OR YOU WILL BE ECO BANNED!}");
				sender.sendMessage(prf+ChatColor.BLUE+"/se cs"+ChatColor.RED+ChatColor.BOLD+":"+ChatColor.BLUE+" Checks your business supplies");
				sender.sendMessage(prf+ChatColor.BLUE+"/se rst"+ChatColor.RED+ChatColor.BOLD+":"+ChatColor.BLUE+" Completely Resets your business");
				sender.sendMessage(prf+ChatColor.BLUE+"/se sv corp"+ChatColor.RED+ChatColor.BOLD+":"+ChatColor.BLUE+" buys a Corporation License for $1,000,000");
				sender.sendMessage(prf+ChatColor.AQUA+"/se rentstop {PropertyName}"+ChatColor.RED+ChatColor.BOLD+":"+ChatColor.AQUA+" immediatley stops the rental contract on the specified property");
				sender.sendMessage(prf+ChatColor.AQUA+"/se hireplayer {PLAYERNAME} {SALARY}"+ChatColor.RED+ChatColor.BOLD+":"+ChatColor.AQUA+" hires specified player at specified salary");
				sender.sendMessage(prf+ChatColor.AQUA+"/se cb {PLAYERNAME}"+ChatColor.RED+ChatColor.BOLD+":"+ChatColor.AQUA+" Check to see if a plyer has a business license");
				sender.sendMessage(prf+ChatColor.AQUA+"do /psh 3 for next page");
				
			}else if (Integer.parseInt(args[0]) == 3) {
				
				sender.sendMessage(prf+ChatColor.GOLD+"/bl"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Shows your balance");
				sender.sendMessage(prf+ChatColor.GOLD+"/seco who {PlayerName}"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Shows that players balance and if they own a Business");
				sender.sendMessage(prf+ChatColor.GOLD+"/markets"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Shows the Stock market GUI");
				sender.sendMessage(prf+ChatColor.GOLD+"/seco item"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Shows the propper name for the item your holding");
				sender.sendMessage(prf+ChatColor.GOLD+"/stocks"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Shows the price of the Industry Stocks");
				sender.sendMessage(prf+ChatColor.GOLD+"/stocks buy {Industry Name} {Ammount}"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Buys the Specified number of shares in the Specified Industry");
				sender.sendMessage(prf+ChatColor.GOLD+"/stocks sell {Industry Name} {Ammount}"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Sells the Specified number of shares in the Specified Industry");
				sender.sendMessage(prf+ChatColor.GOLD+"/seco rs"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Registers your shop license");
				sender.sendMessage(prf+ChatColor.GOLD+"/seco rented"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Shows the property's that you are currently renting(As a Tenant)");
				sender.sendMessage(prf+ChatColor.GOLD+"/seco rentals"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Shows the property's that you currently have rented out (As a Landlord)");
				sender.sendMessage(prf+ChatColor.AQUA+"do /psh 4 for next page");
				
			}else if (Integer.parseInt(args[0]) == 4) {
				
				sender.sendMessage(prf+ChatColor.GOLD+"/seco laon apply"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Shows your loan approval");
				sender.sendMessage(prf+ChatColor.GOLD+"/seco loan pay <Ammount>"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Pay the specified ammount towards you loan");
				sender.sendMessage(prf+ChatColor.GOLD+"/ccs <Player Name>"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Shows the Specified Players CreditScore");
				sender.sendMessage(prf+ChatColor.GOLD+"/getvalue"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Shows the value of the selected property");
				sender.sendMessage(prf+ChatColor.GOLD+"/seco property create <Property Name>"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Buys the selected property");
				
				sender.sendMessage(prf+ChatColor.GOLD+"/seco property sell <Property Name> <Price>"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Lists the specified property fo sale");
				sender.sendMessage(prf+ChatColor.GOLD+"/pos1"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Sets Corner 1 of your property selection");
				sender.sendMessage(prf+ChatColor.GOLD+"/pos2"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Sets Corner 2 of your property selection");
				sender.sendMessage(prf+ChatColor.GOLD+"//expand vert"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Sets your property to extend from Bedrock to sky limit");
				sender.sendMessage(prf+ChatColor.GOLD+"/buywarehouse"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Buys and spawns a warehouse infront of you and registers it to your business");
				sender.sendMessage(prf+ChatColor.GOLD+"/buyfactory"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Buys and spawns a factory infront of you and registers it to your business");
				sender.sendMessage(prf+ChatColor.GOLD+"/buyhouse"+ChatColor.RED+ChatColor.BOLD+" :"+ChatColor.BLUE+" Buys and spawns a house infront of you");
			}
				//=================================================================================
				//TODO ADD /ps cb {PLAYERNAME} AND ADD /ps bw AND /ps hireplayer {PLAYERNAME} {SALARY}
				//=================================================================================
				
				
				return true;
			
		}
	    
	    	if (cmd.getName().equalsIgnoreCase("mine")) {
	    		Property M = null;
	    		
	    		for (Property PR : prys) {
	    			
	    			if (PR.getName().equalsIgnoreCase("PublicMine")) {
	    				M = PR;
	    			}
	    			
	    		}
	    		
	    		M.addTrusted(sender.getName());
	    		saveProperty();
	    	}
	    	
	    	if (cmd.getName().equalsIgnoreCase("setCity")) {
	    		
	    		if (sender.isOp()) {
	    			
	    			String Name = sender.getName();
	    			Player Player = Bukkit.getPlayer(Name);
	    			Location loc = Player.getLocation();
	    			
	    			config.set("CityData.Center.X", loc.getBlockX());
	    			config.set("CityData.Center.Y", loc.getBlockY());
	    			config.set("CityData.Center.Z", loc.getBlockZ());
	    			config.set("CityData.Center.World", loc.getWorld().getName());
	    			saveConfigs();
	    			
	    			sender.sendMessage(ChatColor.GREEN+"City Center set as X: "+loc.getBlockX()+" Y: "+loc.getBlockY()+" Z: "+loc.getBlockZ()+" In world: "+loc.getWorld().getName());
	    			
	    			
	    		}

	    	}
	    	
	    	
	    if (cmd.getName().equalsIgnoreCase("stocks")) { // If the player typed /basic then do the following, note: If you only registered this executor for one command, you don't need this
	    
	    		new CommandHandler(p, cmd.getName(), args).stocks();
	    	
	    	return true;
	    	}
	    
	    if (cmd.getName().equalsIgnoreCase("ccs")) {
	    	
	    	//Player p = Bukkit.getPlayer(sender.getName());
	    	if (args.length == 0) {
    			sender.sendMessage(prf+"Do /psh for a command list");
    			return true;
    		}
	    	sender.sendMessage(ChatColor.GREEN+args[0]+" Score is "+price.format(econ.getScore(args[0])));
	    	return true;
	    	
	    }
	    
	    if (cmd.getName().equalsIgnoreCase("wcsp")) {
			econ.addPayment(sender.getName());
	    	saveConfigs();
	    	return true;
	    }
	    
	    if (cmd.getName().equalsIgnoreCase("value")) {
	    	
	    	double value = 0;
			Material M = p.getInventory().getItemInMainHand().getType();
			
			if (config.getStringList("ServerData.PricedMaterials").contains(M.name())) {
				value = config.getDouble("ServerData.Materials.Value."+M.name());
				sender.sendMessage(ChatColor.GREEN+"Value: $"+price.format(value));
			}else{
				
				if (M.equals(Material.AIR)) {
					sender.sendMessage("Air has no value");
				}else{
					sender.sendMessage(ChatColor.RED+"That block has no set value");
				}
				
			}
	    	
	    	
	    	saveConfigs();
	    	return true;
	    }
	    
	    if (cmd.getName().equalsIgnoreCase("setvalue")) {
	    	if (args.length == 0) {
    			sender.sendMessage(prf+"Do /psh for a command list");
    			return true;
    		}
	    	if (p.isOp()) {
	    		
	    		ItemStack I = p.getInventory().getItemInMainHand();
	    		Material M = I.getType();
	    		String NM = M.name();
	    		if (NM.equals(Material.AIR.name())) {
	    			
	    		}else {
	    		List<String> VL = config.getStringList("ServerData.PricedMaterials");
	    		
	    		if (VL.contains(NM)) {
	    			
	    		}else {
	    			VL.add(NM);
	    		}
	    		config.set("ServerData.PricedMaterials", VL);
	    		//config.getInt("ServerData.Materials.Value."+M.name());
	    		config.set("ServerData.Materials.Value."+M.name(), Double.valueOf(args[0]));
	    		sender.sendMessage("Set the Value of "+NM+" to $"+args[0]);
	    		saveConfigs();
	    		}
	    		
	    	}
	    	saveConfigs();
	    	return true;
	    }
	    
	    if (cmd.getName().equalsIgnoreCase("getvalue")) {
	    	
	    		org.bukkit.entity.Player player = Bukkit.getPlayer(sender.getName());
				BukkitPlayer actor = BukkitAdapter.adapt(player);
				SessionManager manager = WorldEdit.getInstance().getSessionManager();
				LocalSession localSession = manager.get(actor);
				
				Region region = null;
				com.sk89q.worldedit.world.World selectionWorld = localSession.getSelectionWorld();
				try {
				    if (selectionWorld == null) throw new IncompleteRegionException();
				    region = localSession.getSelection(selectionWorld);
				} catch (IncompleteRegionException ex) {
				    actor.printError("Please make a region selection first.");
				    
				}
	    		Region RR = region;
	    		
	    		propertyValueRequestEvent pvr = new propertyValueRequestEvent(p, RR);
	    		Bukkit.getServer().getPluginManager().callEvent(pvr);
	    		//payCycleEvent pce = new payCycleEvent(pls, bls);
                //Bukkit.getServer().getPluginManager().callEvent(pce);
	    	
	    	saveConfigs();
	    	return true;
	    }
	    
	    if (cmd.getName().equalsIgnoreCase("wcsa")) {
	    	econ.addAcc(sender.getName());
	    	saveConfigs();
	    	return true;
	    }
	    
	    if (cmd.getName().equalsIgnoreCase("buyhouse")) {
	    	
	    	
	    	
	    	if (econ.has(sender.getName(), 10000)) {
	    		econ.withdraw(sender.getName(), 10000);
	    		pasteSchem(Bukkit.getPlayer(sender.getName()), new File(this.getDataFolder().getAbsolutePath() + "/house.schem"));
	    	}else {
	    		sender.sendMessage(ChatColor.RED+"Not Enough Money");
	    		sender.sendMessage(ChatColor.RED+"Houses cost "+ChatColor.AQUA+price.format(10000));
	    	}
	    	
			
			//localSession.setClipboard();
	    	
	    	saveConfigs();
	    	return true;
	    }
	    
	    if (cmd.getName().equalsIgnoreCase("buywarehouse")) {

	    	if (econ.has(sender.getName(), config.getDouble("WareHouseCost"))) {
	    		econ.withdraw(sender.getName(), config.getDouble("WareHouseCost"));
	    		pasteSchem(Bukkit.getPlayer(sender.getName()), new File(this.getDataFolder().getAbsolutePath() + "/warehouse1.schem"));
	    		int wh = config.getInt("PlayerData."+sender.getName()+".Properties.warehouses");
	    		config.set("PlayerData."+sender.getName()+".Properties.warehouses", (wh+1));
	    	}else {
	    		sender.sendMessage(ChatColor.RED+"Not Enough Money");
	    		sender.sendMessage(ChatColor.RED+"Warehouses cost "+ChatColor.AQUA+price.format(config.getDouble("WareHouseCost")));
	    	}
	    	
			
			//localSession.setClipboard();
	    	
	    	saveConfigs();
	    	return true;
	    }
	    
	    if (cmd.getName().equalsIgnoreCase("buyfactory")) {
	    	
	    	int wh = config.getInt("PlayerData."+sender.getName()+".Properties.warehouses");
    		if (wh >= 5) {
	    	
	    	if (econ.has(sender.getName(), config.getDouble("FactoryCost"))) {
	    		econ.withdraw(sender.getName(), config.getDouble("FactoryCost"));
	    		pasteSchem(Bukkit.getPlayer(sender.getName()), new File(this.getDataFolder().getAbsolutePath() + "/factory1.schem"));
	    		int fc = config.getInt("PlayerData."+sender.getName()+".Properties.factory");
	    		config.set("PlayerData."+sender.getName()+".Properties.factory", (fc+1));
	    		
	    	}else {
	    		sender.sendMessage(ChatColor.RED+"Not Enough Money");
	    		sender.sendMessage(ChatColor.RED+"Factories cost "+ChatColor.AQUA+price.format(config.getDouble("FactoryCost")));
	    	}
	    	
			
			//localSession.setClipboard();
    		}else {
    			sender.sendMessage(ChatColor.RED+"You must own atleast 5 warehouses before buying a factory");
    		}
	    	saveConfigs();
	    	return true;
	    }
	    
	    if (cmd.getName().equalsIgnoreCase("wcslp")) {
	    	econ.addLatePayment(sender.getName());
	    	saveConfigs();
	    	return true;
	    }
	    
	    if (cmd.getName().equalsIgnoreCase("markets")) {
	    	g.openMarketInventory(Bukkit.getPlayer(sender.getName()));
	    	//TODO GUI
	    	saveConfigs();
	    	return true;
	    }
	    
	    if (cmd.getName().equalsIgnoreCase("business")) {
	    	g.openBusInventory(Bukkit.getPlayer(sender.getName()));
	    	//TODO GUI
	    	saveConfigs();
	    	return true;
	    }
	    
	    if (cmd.getName().equalsIgnoreCase("trust")) {

	    	if (args.length >= 2) {
	    		
	    		for (Property PR : prys) {
		    		
	    			if (PR.getOwner().equals(sender.getName())) {
	    				if (PR.getName().equalsIgnoreCase(args[1])) {
	    					PR.addTrusted(args[0]);
		    				sender.sendMessage(ChatColor.GREEN+"Added "+args[0]+" to "+args[1]+"'s Trusted List");
	    				}
	    			}
	    		
	    		}
	    		
	    	}else {
	    		
	    		for (Property PR : prys) {
	    		
	    			if (PR.getOwner().equals(sender.getName())) {
	    				PR.addTrusted(args[0]);
	    				sender.sendMessage(ChatColor.GREEN+"Added "+args[0]+" to this properties Trusted List");
	    			}
	    		
	    		}
	    		
	    	}	
	    	saveProperty();
	    	return true;
	    }
	    
	    if (cmd.getName().equalsIgnoreCase("untrust")) {

	    	for (Property PR : prys) {
	    		
	    		if (PR.getOwner().equals(sender.getName())) {
	    			PR.removeTrusted(args[0]);
	    			sender.sendMessage(ChatColor.GREEN+"Removed "+args[0]+" from this properties Trusted List");
	    			Zone Z = new Zone();
	    			
	    		}
	    		
	    	}
	    	saveProperty();
	    	return true;
	    }
	    
	    if (cmd.getName().equalsIgnoreCase("bl")) {
	    	double bl = econ.getBalance(sender.getName());
	    	String bls = null;
	    	
	    	
	    		sender.sendMessage(ChatColor.GREEN+"Balance: $"+price.format(econ.getBalance(sender.getName())));
	    	
	    	
	    	
	    	return true;
	    }else if (cmd.getName().equalsIgnoreCase("seco")) {
	    	if (args.length == 0) {
    			sender.sendMessage(prf+"Do /psh for a command list");
    			return true;
    		}
	    	if (args[0].equalsIgnoreCase("give")) {
	    		if (args.length >= 1) {
	    			if (sender.isOp()) {
	    				
	    					Double II = Double.valueOf(args[2]);
	    					int III = Integer.valueOf(args[3]);
	    					if (III <= 0) {
	    						III = 1;
	    					}
	    					//sender.sendMessage("1: "+II);
	    					//sender.sendMessage("2: "+III);
	    					
	    					Double ds = II*III;
	    					sender.sendMessage("Total: "+ds);
	    					econ.deposit(args[1], ds);
	    				
	    			}
	    		}else {
	    			sender.sendMessage(ChatColor.RED+"Format: /seco give {PlayerName} {Ammount} {Ammount Multiplyer} -- Multiplyer is Optional");
	    		}
	    	}else if (args[0].equalsIgnoreCase("voted")) {

	    		String NM = args[1];
	    		econ.deposit(NM, 500);
	    		int s = config.getInt("PlayerStocks.PlayerData."+NM+".Shares");
				config.set("PlayerStocks.PlayerData."+NM+".Shares", s+2);
				saveConfigs();
	    		
	    	}else if (args[0].equalsIgnoreCase("take")) {
	    		if (args.length >= 1) {
	    			if (sender.isOp()) {
	    			econ.withdraw(args[1], Integer.valueOf(args[2]));
	    			}
	    		}else {
	    			//Needs Name
	    		}//int s = config.getInt("PlayerData."+b+".Business.Supplies");
	    	}else if (args[0].equalsIgnoreCase("loan")) {
	    		if (args.length >= 1) {

	    			if (args[1].equalsIgnoreCase("apply")) {
	    				
	    				double s = econ.getScore(sender.getName());
	    				double rt = 0.52;
	    				int tier = 0;
	    				int am = 0;
	    				
	    				if (isBusiness(sender.getName())) {
	    					
	    					if (s < 500) {
		    					rt = 0.25;
		    					tier = 1;
		    				}else if (s <= 600) {
		    					rt = 0.15;
		    					tier = 2;
		    				}else if (s <= 700) {
		    					rt = 0.05;
		    					tier = 3;
		    				}else if (s <= 800) {
		    					rt = 0.04;
		    					tier = 4;
		    				}else if (s <= 900) {
		    					rt = 0.03;
		    					tier = 5;
		    				}else if (s <= 1000) {
		    					rt = 0.02;
		    					tier = 6;
		    				}else if (s <= 1500) {
		    					rt = 0.01;
		    					tier = 7;
		    				}else if (s <= 2000) {
		    					rt = 0.01;
		    					tier = 8;
		    				}else {
		    					rt = 0.005;
		    					tier = 10;
		    				}
	    					
	    				}else {
	    				
	    				if (s < 500) {
	    					rt = 0.55;
	    					tier = 1;
	    				}else if (s <= 600) {
	    					rt = 0.45;
	    					tier = 2;
	    				}else if (s <= 700) {
	    					rt = 0.35;
	    					tier = 3;
	    				}else if (s <= 800) {
	    					rt = 0.25;
	    					tier = 4;
	    				}else if (s <= 900) {
	    					rt = 0.15;
	    					tier = 5;
	    				}else if (s <= 1000) {
	    					rt = 0.05;
	    					tier = 6;
	    				}else if (s <= 1500) {
	    					rt = 0.02;
	    					tier = 7;
	    				}else if (s <= 2000) {
	    					rt = 0.01;
	    					tier = 8;
	    				}else {
	    					rt = 0.009;
	    					tier = 10;
	    				}
	    				}
	    				
	    				if (tier == 1) {
	    					am = 500;
	    				}else if (tier == 2) {
	    					am = 1500;
	    				}else if (tier == 3) {
	    					am = 2500;
	    				}else if (tier == 4) {
	    					am = 5000;
	    				}else if (tier == 5) {
	    					am = 10000;
	    				}else if (tier == 6) {
	    					am = 22000;
	    				}else if (tier == 7) {
	    					am = 25000;
	    				}else if (tier == 8) {
	    					am = 50000;
	    				}else if (tier == 10) {
	    					am = 150000;
	    				}
	    				if (isBusiness(sender.getName())) {
	    					am = am*100;
	    				}
	    				sender.sendMessage(ChatColor.GREEN+"Loan Aproval:");
	    				sender.sendMessage(ChatColor.GREEN+"$"+price.format(am));
	    				sender.sendMessage(ChatColor.GREEN+"Interest Rate: %"+price.format((rt*100)));
	    				sender.sendMessage(ChatColor.GREEN+"Do /seco loan take {Loan Term (In Days)}");
	    				config.set("PlayerData."+sender.getName()+".Bank.Loan.Aproval.Ammount", am);
	    				config.set("PlayerData."+sender.getName()+".Bank.Loan.Aproval.Rate", rt);
	    				saveConfigs();
	    			}else if (args[1].equalsIgnoreCase("take")) {
	    				
	    				
	    				int term = Integer.valueOf(args[2]);
	    				double rt = config.getDouble("PlayerData."+sender.getName()+".Bank.Loan.Aproval.Rate");
	    				int am = config.getInt("PlayerData."+sender.getName()+".Bank.Loan.Aproval.Ammount");
	    				
	    				if (am == 0) {
	    					sender.sendMessage(ChatColor.RED+"You Need to do '/seco loan apply' first.");
	    				}else {
	    					
	    					double lnm = am*(1+(rt*term));
	    					double mp = (am*(1+(rt*term)))/term;
	    					if (config.getDouble("PlayerData."+sender.getName()+".Bank.Loan.debt") > 0) {
	    						lnm = lnm+config.getDouble("PlayerData."+sender.getName()+".Bank.Loan.debt");
	    						econ.addLatePayment(sender.getName());
	    					}
	    					config.set("PlayerData."+sender.getName()+".Bank.Loan.debt", lnm);
	    					config.set("PlayerData."+sender.getName()+".Bank.Loan.term", term);
	    					config.set("PlayerData."+sender.getName()+".Bank.Loan.MinPayment", term);
	    					config.set("PlayerData."+sender.getName()+".Bank.Loan.Rate", rt);
	    					saveConfigs();
	    					econ.deposit(sender.getName(), am);
	    					sender.sendMessage(ChatColor.GREEN+"Loan for $"+price.format(lnm)+" taken");
	    					econ.addAcc(sender.getName());
	    					
	    				}
	    				
	    			}else if (args[1].equalsIgnoreCase("view")) {
	    				
	    				
	    				saveConfigs();
	    				double rt = config.getDouble("PlayerData."+sender.getName()+".Bank.Loan.Rate");
	    				int am = config.getInt("PlayerData."+sender.getName()+".Bank.Loan.Ammount");

	    				double lnm = config.getDouble("PlayerData."+sender.getName()+".Bank.Loan.debt");
	    				int term = config.getInt("PlayerData."+sender.getName()+".Bank.Loan.term");
	    				double mp = config.getInt("PlayerData."+sender.getName()+".Bank.Loan.MinPayment");

	    				sender.sendMessage(ChatColor.GREEN+"Current Loan:");
	    				sender.sendMessage(ChatColor.GREEN+"Amount: $"+price.format(lnm));
	    				sender.sendMessage(ChatColor.GREEN+"Interest: %"+price.format(rt*100));
	    				sender.sendMessage(ChatColor.GREEN+"Minimum Payment: $"+price.format(mp));
	    				saveConfigs();
	    			}else if (args[1].equalsIgnoreCase("pay")) {
	    				
	    				double rt = config.getDouble("PlayerData."+sender.getName()+".Bank.Loan.Aproval.Rate");
	    				int am = config.getInt("PlayerData."+sender.getName()+".Bank.Loan.Aproval.Ammount");

	    				double lnm = config.getDouble("PlayerData."+sender.getName()+".Bank.Loan.debt");
	    				int term = config.getInt("PlayerData."+sender.getName()+".Bank.Loan.term");
	    				double mp = config.getInt("PlayerData."+sender.getName()+".Bank.Loan.MinPayment");
	    				
	    				int pay = Integer.valueOf(args[2]);
	    				
	    				if (econ.has(sender.getName(), pay)) {
	    					
	    					econ.withdraw(sender.getName(), pay);
	    					lnm = lnm-pay;
	    					config.set("PlayerData."+sender.getName()+".Bank.Loan.debt", lnm);
	    					econ.addPayment(sender.getName());
	    					sender.sendMessage(ChatColor.GREEN+"Loan Payment Recieved");
	    					if (lnm <= 0) {
	    						config.set("PlayerData."+sender.getName()+".Bank.Loan.debt", 0);
	    						econ.addPayment(sender.getName());
	    						econ.addPayment(sender.getName());
	    						econ.addPayment(sender.getName());
	    						econ.addPayment(sender.getName());
	    						econ.addPayment(sender.getName());
	    						econ.removeAcc(sender.getName());
	    						sender.sendMessage(ChatColor.GREEN+"Loan Paid Off!");
	    					}
	    					
	    				}
	    				
	    			}
	    			
	    		}else {
	    			//Needs Name
	    		}//int s = config.getInt("PlayerData."+b+".Business.Supplies");
	    	}else if (args[0].equalsIgnoreCase("set")) {
	    		if (args.length >= 1) {
	    			if (sender.isOp()) {
	    			econ.set(args[1], Double.valueOf(args[2]));
	    			}
	    		}else {
	    			//Needs Name
	    		}
	    	}else if (args[0].equalsIgnoreCase("setsupplies")) {
	    			if (sender.isOp()) {
	    				int sv = Integer.valueOf(args[2]);
	    				config.set("PlayerData."+args[1]+".Business.Supplies", sv);
	    				sender.sendMessage("Set "+args[1]+" Supplies to "+args[2]);
	    				saveConfigs();
	    			}

	    	}else if (args[0].equalsIgnoreCase("rs")) {
	    		if (econ.has(sender.getName(), rsp)) {
	    			
	    			csid = csid+1;
	    			econ.withdraw(sender.getName(), rsp);
	    			sli.add(csid);
	    			config.set("ServerData.ShopIDS", sli);
	    			config.set("ServerData."+sender.getName()+".ShopID", csid);
	    			config.set("ServerData.ShopOwner."+csid, sender.getName());
	    			saveConfigs();
	    			sender.sendMessage(ChatColor.GREEN+"You have registered a shop Your Shop ID is "+ChatColor.GOLD+csid);
	    			sender.sendMessage(ChatColor.RED+"DO NOT LOSE THIS ID THERE IS NO WAY TO RETEIVE IF YOU FORGET AND OR LOSE IT ID: "+ChatColor.GOLD+csid);
	    			
	    		}else {
	    			sender.sendMessage(ChatColor.RED+"You Need $"+price.format(rsp));
	    		}
	    	}else if (args[0].equalsIgnoreCase("addstock")) {
	    		
	    		Player pl = Bukkit.getPlayer(sender.getName());
	    		int id = Integer.valueOf(args[1]);
	    		ItemStack i = Bukkit.getPlayer(sender.getName()).getInventory().getItemInMainHand();
	    		int qi = i.getAmount();
	    		Material m = i.getType();
	    		
	    		int q = qi+config.getInt("ServerData.Shops."+id+".Inventory."+m);
	    		
	    		List<String> ItemInventory = config.getStringList("ServerData.Shops."+id+".ItemInventory");
	    		
	    		if (ItemInventory.contains(m.name())) {
	    			
	    		}else {
	    			ItemInventory.add(m.name());
	    			config.set("ServerData.Shops."+id+".ItemInventory", ItemInventory);
	    			sender.sendMessage(ChatColor.GREEN+"Added New Item to Shop Inventory");
	    		}
	    		
	    		config.set("ServerData.Shops."+id+".Inventory."+m.name(), q);
	    		pl.getInventory().getItemInHand().setAmount(p.getInventory().getItemInHand().getAmount()-qi);
	    		sender.sendMessage(ChatColor.GREEN+"You added "+stocks.format(qi)+" "+m.name()+" to your shops inventory");
	    		sender.sendMessage(ChatColor.GREEN+"You have "+stocks.format(q)+" "+m.name()+" in your shops inventory");
	    		saveConfigs();
	    		
	    		
	    	}else if (args[0].equalsIgnoreCase("pay")) {
	    		if (args.length >= 1) {
	    			int am = Integer.valueOf(args[2]);
	    			if (econ.has(sender.getName(), am)) {
	    				econ.withdraw(sender.getName(), am);
	    				econ.deposit(args[1], am);
	    				sender.sendMessage(ChatColor.GREEN+"You paid "+args[1]+" $"+price.format(am));
	    				Bukkit.getPlayer(args[1]).sendMessage(ChatColor.GREEN+sender.getName()+" Paid You $"+price.format(am));
	    			}
	    		}else {
	    			//Needs Name
	    		}
	    	}else if (args[0].equalsIgnoreCase("item")) {
	    		Player pl = Bukkit.getPlayer(sender.getName());
	    		String nn = pl.getItemInHand().getType().name();
	    		sender.sendMessage(ChatColor.GREEN+"That is: "+nn);
	    	}else if (args[0].equalsIgnoreCase("checkP")) {
	    		loadProperty();
	    		List<String> S = new ArrayList();
	    		//S.add(args[1]);
	    		//for (Property P : prys) {
	    			//S.add(P.getName());
	    		//}
	    		S = config.getStringList("ServerData.Propertys");
	    		for (String SS : S) {
	    			sender.sendMessage(SS);
	    		}
	    		
	    		for (Property PS : prys) {
	    			sender.sendMessage(PS.getName());
	    		}
	    		saveConfigs();
	    	}else if (args[0].equalsIgnoreCase("maddP")) {
	    		List<String> S = new ArrayList();
	    		
	    		loadProperty();
	    		
	    		S.add(args[1]);
	    		for (Property P : prys) {
	    			S.add(P.getName());
	    		}
	    		config.set("ServerData.Propertys", S);
	    		for (String SS : S) {
	    			sender.sendMessage(SS);
	    			String NM = config.getString("PropertyData."+args[1]+".Name");
		    		String ON = config.getString("PropertyData."+args[1]+".Owner");
		    		int MNX = config.getInt("PropertyData."+args[1]+".MinX");
		    		int MNY = config.getInt("PropertyData."+args[1]+".MinY");
		    		int MNZ = config.getInt("PropertyData."+args[1]+".MinZ");
		    		
		    		int MXX = config.getInt("PropertyData."+args[1]+".MaxX");
		    		int MXY = config.getInt("PropertyData."+args[1]+".MaxY");
		    		int MXZ = config.getInt("PropertyData."+args[1]+".MaxZ");
		    		
		    		String WW = config.getString("PropertyData."+args[1]+".World");
		    		
		    		Location L1 = new Location(Bukkit.getWorld(WW), MNX, MNY, MNZ);
		    		Location L2 = new Location(Bukkit.getWorld(WW), MXX, MXY, MXZ);
		    		
		    		Property PRR = new Property(NM, ON, L1, L2);
		    		
		    		if (prys.contains(PRR)) {
		    			
		    		}else {
		    			prys.add(PRR);
		    		}
	    		}
	    		
	    		for (Property PS : prys) {
	    			sender.sendMessage(PS.getName());
	    		}
	    		saveConfigs();
	    	}
	    	
	    	else if (args[0].equalsIgnoreCase("property")) {
	    		if (args.length == 0) {
	    			sender.sendMessage(prf+"Do /psh for a command list");
	    			return true;
	    		}
	    		if (args.length >= 2) {
	    			if (args[1].equalsIgnoreCase("create")) {
	    				createProperty(args, p);
	    			}else if (args[1].equalsIgnoreCase("sell")) {
	    				
	    				if (args.length <= 3) {
	    					sender.sendMessage(ChatColor.RED+"Usage: /seco property sell {PropertyName} {Price} {SaleType}");
	    				}else {
	    				String NM = config.getString("PropertyData."+args[2]+".Name");
			    		String ON = config.getString("PropertyData."+args[2]+".Owner");
			    		int MNX = config.getInt("PropertyData."+args[2]+".MinX");
			    		int MNY = config.getInt("PropertyData."+args[2]+".MinY");
			    		int MNZ = config.getInt("PropertyData."+args[2]+".MinZ");
			    		
			    		int MXX = config.getInt("PropertyData."+args[2]+".MaxX");
			    		int MXY = config.getInt("PropertyData."+args[2]+".MaxY");
			    		int MXZ = config.getInt("PropertyData."+args[2]+".MaxZ");
			    		
			    		String WW = config.getString("PropertyData."+args[2]+".World");
			    		
				    	if (WW == null) {
				    		WW = "RPWorld";
				    	}	
			    		
			    		Location L1 = new Location(Bukkit.getWorld(WW), MNX, MNY, MNZ);
			    		Location L2 = new Location(Bukkit.getWorld(WW), MXX, MXY, MXZ);
			    		
			    		Property PRR = new Property(NM, ON, L1, L2);
	    				
	    				if (sender.isOp()) {
	    					
	    					PRR.setForSale(true);
    						PRR.setPrice(Integer.valueOf(args[3]));
    						
    						if (args[4].equalsIgnoreCase("rent")) {
    							PRR.SetSaleType("rent");
    						}
    						
    						if (args[4].equalsIgnoreCase("sell")) {
    							PRR.SetSaleType("sell");
    						}
    						propertySales.add(PRR.getName());
    						
    						config.set("PropertyData."+PRR.getName()+".SalePrice", Double.valueOf(args[3]));
							config.set("PropertyData."+PRR.getName()+".SaleType", PRR.getSaleType());
							config.set("PropertyData."+PRR.getName()+".ForSale", true);
    						
    						config.set("PropertyData.PropertySales", propertySales);
    						Propertysave(PRR);
    						
    						saveConfigs();
    						PropertyUpdateEvent pvr = new PropertyUpdateEvent();
    			    		Bukkit.getServer().getPluginManager().callEvent(pvr);
	    					
	    					
	    				}else {
	    					
	    					if (PRR.getOwner().equalsIgnoreCase(sender.getName())) {
	    						
	    						PRR.setForSale(true);
	    						PRR.setPrice(Integer.valueOf(args[3]));
	    						
	    						if (args[4].equalsIgnoreCase("rent")) {
	    							PRR.SetSaleType("rent");
	    						}
	    						
	    						if (args[4].equalsIgnoreCase("sell")) {
	    							PRR.SetSaleType("sell");
	    						}
	    						propertySales.add(PRR.getName());
	    						
	    						config.set("PropertyData."+PRR.getName()+".SalePrice", Double.valueOf(args[3]));
								config.set("PropertyData."+PRR.getName()+".SaleType", PRR.getSaleType());
								config.set("PropertyData."+PRR.getName()+".ForSale", true);
	    						
	    						config.set("PropertyData.PropertySales", propertySales);
	    						Propertysave(PRR);
	    						saveConfigs();
	    						PropertyUpdateEvent pvr = new PropertyUpdateEvent();
	    			    		Bukkit.getServer().getPluginManager().callEvent(pvr);
	    					}
	    					
	    				}
    				//config.getStringList("PropertyData.PropertySales");
	    				}
	    			}else if (args[1].equalsIgnoreCase("info")) {
	    				loadProperty();
	    				boolean found =false;
	    				
	    				for (Property PR : prys) {
	    					
	    					if (PR.getName().equalsIgnoreCase(args[2])) {
	    						sender.sendMessage("Name: "+PR.getName());
	    						sender.sendMessage("Owner: "+PR.getOwner());
	    						sender.sendMessage("Min X: "+PR.getMinLoc().getX());
	    						sender.sendMessage("Min Z: "+PR.getMinLoc().getZ());
	    						sender.sendMessage("Max X: "+PR.getMaxLoc().getBlockX());
	    						sender.sendMessage("Max Z: "+PR.getMaxLoc().getBlockZ());
	    						sender.sendMessage("ForSale: "+PR.getForSale());
	    						sender.sendMessage("Price: "+price.format(PR.getPrice()));
	    						sender.sendMessage("Sale Type: "+PR.getSaleType());
	    						sender.sendMessage("Zone: "+PR.getZone());
	    						found = true;

	    					}
	    					
	    				}
	    				
	    				if (!found) {
	    					sender.sendMessage(ChatColor.RED+"Property Not Found!");
	    				}
    				//config.getStringList("PropertyData.PropertySales");
	    				
	    			}
	    		}else {
	    			sender.sendMessage(ChatColor.RED+"Usage: /seco property create {PropertyName} {OwnerName}");
	    		}
	    	}
	    	
	    	
	    	else if (args[0].equalsIgnoreCase("who")) {
	    		sender.sendMessage(prf+ChatColor.GREEN+"Balance: "+price.format(econ.getBalance(args[1])));
	    		sender.sendMessage(prf+ChatColor.GREEN+"Is Business: "+isBusiness(args[1]));
	    		sender.sendMessage(prf+"Profile: http://secomc.com/about.php?UUID="+Bukkit.getPlayer(args[1]).getUniqueId());
	    	}else if (args[0].equalsIgnoreCase("addrev")) {
	    		if (isBusiness(args[1])) {
		          int rev = config.getInt("PlayerData."+args[1]+".Business.Revenue");
		          config.set("PlayerData."+args[1]+".Business.Revenue", rev+Integer.valueOf(args[2]));	
	    		}
	    	}else if (args[0].equalsIgnoreCase("rented")) {

	    		rls = new ArrayList();
		    	for (String r : rented) {
		    		
		    		String t = config.getString("ServerData.Rentals."+r+".Tenant");
		    		String ll = config.getString("ServerData.Rentals."+r+".LandLord");
		    		double prc = config.getDouble("ServerData.Rentals."+r+".Rent");
		    		//config.set("ServerData.Rented", rented);
		    		
		    		Rental rl = new Rental(r, t, ll, prc, "PayCycle");
		    		rls .add(rl);
		    	}
		    		sender.sendMessage(ChatColor.GREEN+"You are Currently Renting:");
		    		for (Rental rr : rls) {
		    			
		    			if (rr.gettenant().equalsIgnoreCase(sender.getName())) {
		    				sender.sendMessage(prf+ChatColor.GREEN+"=====================================");
		    				sender.sendMessage(prf+ChatColor.GREEN+"Region Name: "+rr.getRegion());
		    				sender.sendMessage(prf+ChatColor.GREEN+"LandLord Name: "+rr.getLandLord());
		    				sender.sendMessage(prf+ChatColor.GREEN+"Rent: "+price.format(rr.getprice()));
		    				sender.sendMessage(prf+ChatColor.GREEN+"=====================================");
		    			}
		    			
		    		}

	    		
	    		
		    	}else if (args[0].equalsIgnoreCase("rentals")) {

		    		rls = new ArrayList();
			    	for (String r : rented) {
			    		
			    		String t = config.getString("ServerData.Rentals."+r+".Tenant");
			    		String ll = config.getString("ServerData.Rentals."+r+".LandLord");
			    		double prc = config.getDouble("ServerData.Rentals."+r+".Rent");
			    		//config.set("ServerData.Rented", rented);
			    		
			    		Rental rl = new Rental(r, t, ll, prc, "PayCycle");
			    		rls .add(rl);
			    	}
			    		sender.sendMessage(ChatColor.GREEN+"You are Currently Renting:");
			    		for (Rental rr : rls) {
			    			
			    			if (rr.getLandLord().equalsIgnoreCase(sender.getName())) {
			    				sender.sendMessage(prf+ChatColor.GREEN+"Region: "+rr.getRegion()+" For: $"+price.format(rr.getprice())+" To: "+rr.gettenant());

			    			}
			    			
			    		}

		    		
		    		
			    	}else if (args[0].equalsIgnoreCase("evict")) {
			    		
			    		
			    		String LN = sender.getName();
			    		String TN = args[1];
			    		String RR = "None";
			    		
			    		
			    		for (Rental r : rls) {
			    			
			    			if (r.gettenant().equalsIgnoreCase(TN)) {
			    				if (r.getLandLord().equalsIgnoreCase(LN)) {
			    					RR = r.getName();	
			    				}
			    			}
			    			
			    		}
			    		
			    		if (RR == "None") {
			    			sender.sendMessage(ChatColor.RED+"Region Not Found");
			    			return true;
			    		}else {
			    			
			    			sender.sendMessage(ChatColor.GREEN+"You issued an eviction for "+TN);
			    			
			    			Eviction E = new Eviction(LN, TN, RR);
			    		
			    			evs.add(E);
			    		}
			    		
				    	}else if (args[0].equalsIgnoreCase("r")) {
				    		
				    		World WW = Bukkit.getPlayer(sender.getName()).getWorld();
				    		FileConfiguration WGF = getWGF(WW.getName());
				    		
				    		List<String> RS = WGF.getStringList("regions");
				    		List<String> RO = new ArrayList();
				    		
				    		for (String RR : RS) {
				    			
				    			if (WGF.getStringList("regions."+RR+".owners").contains(sender.getName())) {
				    				RO.add(RR);
				    				sender.sendMessage(ChatColor.GREEN+"Found "+RR);
				    			}
				    			
				    		}
				    		
				    		for (String OR : RO) {
				    			sender.sendMessage(ChatColor.GREEN+"You own "+OR);
				    		}
				    		
				    		
				    		
				    		
					    	}
	    	return true;
	    }
	    	srnts = rnts;
	    	return true; 
	    }
	    
	    
	    public void loadWG() {
	    	if (WGLoaded = false) {
	    	gg = this.getServer().getPluginManager().getPlugin("STSEcon").getDataFolder().getPath();
	        FNS = this.getServer().getPluginManager().getPlugin("STSEcon").getDataFolder().getPath()+"/shop1.shem";
	        
	    	
	        //WorldEdit WorldGuard = ;
			//WorldGuard.getInstance();
	    	//container = WorldGuard.getInstance().getPlatform().getRegionContainer();
	    	//wgRM = container.get(weWorld);
	    	
	    	if (wgRM == null) {
	    		
	    	}
	    	
	        //WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
	        
	    	//wgRM = wg.getRegionManager(Bukkit.getWorld("world"));
	    	
	    	srs = config.getStringList("Business.ServerData.ShopRegions");
	    	wrs = config.getStringList("Business.ServerData.WareHouseRegions");
	    	frs = config.getStringList("Business.ServerData.FactoryRegions");
	    	
	    	sr = new ArrayList();
	    	wr = new ArrayList();
	    	fr = new ArrayList();
	    	
	    	
	    	for (String SS : srs) {
	    		sr.add(wgRM.getRegion(SS));
	    	}
	    	for (String WS : wrs) {
	    		wr.add(wgRM.getRegion(WS));
	    	}
	    	for (String FS : frs) {
	    		fr.add(wgRM.getRegion(FS));
	    	}
	    	
	    	WGLoaded = true;
	    	
	    	}
	    	
	    }
	    
	    
	    @EventHandler
	    public void onPlayerJoin(PlayerJoinEvent e) {
	    	
	    	if (sjp.contains(e.getPlayer().getName())) {
	    		spawnJohn(e.getPlayer());
	    	}
	    	
	    	if (econ.exists(e.getPlayer().getName()) == false) {
	    		econ.create(e.getPlayer().getName());
	    		//#TODO Run New Player Intro.
	    		PlayerIntro.begin(this, e.getPlayer());
	    		e.getPlayer().sendMessage(ChatColor.RED+"This server is under active development THIS IS AN ALPHA TEST be aware there WILL be bugs glitches and constant reloads / restarts");
	    	}else {
	    		PlayerIntro.motd(this, e.getPlayer());
	    	}
	    	
	    	joins = joins+joinMult;
	    	stockCalc();
	    	String n = e.getPlayer().getName();
	    	int S = config.getInt("PlayerStocks.PlayerData."+n+".Shares");
	    	
			if (S >= 1) {
				double v = S*psp;
				e.getPlayer().sendMessage(prf+"You have "+S+" shares worth $"+price.format(v)+" at $"+price.format(psp)+" per share");
			}else {
				e.getPlayer().sendMessage(prf+"You do not own any PlayerStock Shares. /psh for command list");
			}
			srnts = rnts;
			
			//config.set("PlayerData."+P.getName()+".MaxHP", P.getMaxHealth());
			if (config.getBoolean("PlayerData."+e.getPlayer().getName()+".AdditionalHP")) {
				int MHP = config.getInt("PlayerData."+e.getPlayer().getName()+".MaxHP");
				e.getPlayer().setMaxHealth(MHP);
				e.getPlayer().setHealth(e.getPlayer().getMaxHealth());
			}
			ScoreBoard.loadBoard(this, e.getPlayer());
	    }
	    
	    @EventHandler
	    public void onQuit(PlayerQuitEvent e) {
	    	
	    	if (joins >= leavedec) {
	    	joins = joins - leavedec;
	    	}
	    	
	    	//if (ps_price >= 3.25) {
	    	//ps_price = ps_price - 3.25;
	    	//}

	    	stockCalc();
	    }
	    
	    public void stockCalc() {
	    	
	    	/**double tm = 0;
	    	double r = 0;
	    	int p = Bukkit.getOnlinePlayers().size();
	    	joins = joins/100;
	    	tm = (tm+(joins*psjm)/100);
	    	tm = (tm+(p*psm)/100);
	    	tm = tm+plm;
	    	tm = tm/10;
	    	tm = tm+1;
	    	//ps_price = (ps_base+(Bukkit.getOnlinePlayers().size()*psm))*(joins*psjm);
	    	//ps_price = ps_price+joins*psjm;
	    	//ps_price = ps_base*(joins*psjm);
	    	ps_price = ps_price*tm;
	    	psp = ps_price;**/
	    	
	    	ps_price = ps_price+joins;
	    	ps_price = ps_price+plm;
	    	
	    	psp = ps_price;
	    	if (loggingPRC <= 0.05) {
	    		loggingPRC = 0.06;
	    	}
	    	
	    	if (miningPRC <= 0.05) {
	    		miningPRC = 0.06;
	    	}
	    	
	    	if (lumberPRC <= 0.05) {
	    		lumberPRC = 0.06;
	    	}
	    	
	    	if (farmingPRC <= 0.05) {
	    		farmingPRC = 0.06;
	    	}
	    	
	    	if (redstonePRC <= 0.05) {
	    		redstonePRC = 0.06;
	    	}
	    }
	    
	    public int random(int min, int max) {

	        Random rand = new Random();
	        rand.setSeed(Bukkit.getServer().getWorld("World").getFullTime()+Bukkit.getBannedPlayers().size()+Bukkit.getOnlinePlayers().size()+System.currentTimeMillis()+System.nanoTime()+Bukkit.getOnlinePlayers().size()+Bukkit.getOperators().size()+Bukkit.getPort()+Bukkit.getOfflinePlayers().length);
	        int randomNum = rand.nextInt((max - min) + 1) + min;

	        return randomNum;
	    }

	    public synchronized void repeater() {
	    	new Repeating(this);
	    }
	    
	    public void mainLoop() {
	    	Main MM = this;
	    	Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

				@Override
				public void run() {
					
					payCycleEvent pce = new payCycleEvent(pls, bls);
	                Bukkit.getServer().getPluginManager().callEvent(pce);
					repeater();
					randomEvents();
					
					
				}
	    	    
	    	}, 36000, 36000);
	    	
	    	//Bukkit.getScheduler().sched
	    }
	    
	    public void playerUpdateLoop() {
	    	Main MM = this;
	    	Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {

				@Override
				public void run() {
					
					MySQL.updatePlayerStats();
					
				}
	    	    
	    	}, 6000, 6000);
	    	
	    	//Bukkit.getScheduler().sched
	    }
	    
	    public void updateLoop() {
	    	
	    	Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

				@Override
				public void run() {
					//sean the miner -794, -12, -1448
				  	//joe the miner -788, -10, -1456
					
					if (Bukkit.getOnlinePlayers() == null) {
						return;
					}
					
					if (Bukkit.getOnlinePlayers().size() == 0) {
						return;
					}
					
					for (String JN : config.getStringList("SpawnJohnPlayers")) {
			    		Player JP = Bukkit.getPlayer(JN); 
			    		
			    		if (Bukkit.getOnlinePlayers().contains(JP)) {
			    			sjp.add(JP);
			    		}
			    	}
					boolean spj = false;
					for (Player pj : sjp) {
						if (Bukkit.getOnlinePlayers().contains(pj)) {
							spj = true;
						}
					}
					
					if (spj) {
						spawnJohn();
					}else{
						
						if (john) {
							if (UnDead_John != null) {
								UnDead_John.remove();
							}else {
								return;
							}
						}
						
					}
					
				}
	    	    
	    	}, 202, 202);
	    }
	    
	    public void NPCLoop() {
	    	
	    	Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

				@Override
				public void run() {
					//sean the miner -794, -12, -1448
				  	//joe the miner -788, -10, -1456
					for (String N : config.getStringList("QuestData.SpawnMinersfor")) {
						
						if (msl.contains(Bukkit.getPlayer(N))) {
							
						}else {
							if (NPCLib.getInstance().getAllPersonalNPCs(Bukkit.getPlayer(N)).contains("stsecon.sean")) {
								
								NPCLib.getInstance().getPersonalNPC(Bukkit.getPlayer(N), "stsecon.sean").show();
								NPCLib.getInstance().getPersonalNPC(Bukkit.getPlayer(N), "stsecon.joe").show();
							} else {
							if (Bukkit.getPlayer(N) == null) {
								if (msl.contains(Bukkit.getPlayer(N))) {
									msl.remove(Bukkit.getPlayer(N));
								}
							}else {
								spawnPlayer("Sean", "", Bukkit.getPlayer(N), new Location (Bukkit.getWorld("world"), -794, -12, -1448));
			    				spawnPlayer("joe", "", Bukkit.getPlayer(N), new Location (Bukkit.getWorld("world"), -788, -10, -1456));
			    				msl.add(Bukkit.getPlayer(N));
							}
							}
						}
			    	}
					
				}
	    	    
	    	}, 201, 201);
	    }

	    
	    public void stocksLoop() {
	    	Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
	    	    public void run() {
	    	    	psm = 0.002;
	    	    	psjm = 0.003;
	    	    	psmd = 0.05;
	    	    	ps_base = 150;
	    	    	ps_price = 150;
	    	    	psp = 0;
	    	    	joins = 1;
	    	    	plm = 0.0003;
	    	    	stockCalc();
	    	    }
	    	}, payCycle, payCycle);
	    }
	    
	    
	    public void giveStock(String n, int ammount) {
	    	int s = config.getInt("PlayerStocks.PlayerData."+n+".Shares");
			config.set("PlayerStocks.PlayerData."+n+".Shares", s+ammount);
		try {
			saveConfigs();
	    } catch (Exception e) {
	    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    	}
	    }
	    
	    public static void update() {
	    	srnts = InteractListener.rntss;
	    }
	    
	    public static void saveAll() {
	    	//saveConfigs();
	    }
	    
	    @EventHandler
	    public void onRented(Rented rt) {
	    	String r = rt.r;
	    	
	    	Bukkit.getLogger().log(Level.INFO, "Saved Owner: "+rt.getLandLord());
	    	
	    	config.set("ServerData.Rentals."+r+".Tenant", rt.getName());
            config.set("ServerData.Rentals."+r+".LandLord", rt.getLandLord());
            config.set("ServerData.Rentals."+r+".Rent", rt.getPrice());
            rented = config.getStringList("ServerData.Rented");
            rented.add(rt.getRegion());
            config.set("ServerData.Rented", rented);
            econ.addAcc(rt.getName());
            saveConfigs();
            
            if (isBusiness(rt.getLandLord())) {
            	int rev = config.getInt("PlayerData."+rt.getLandLord()+".Business.Revenue");
            	config.set("PlayerData."+rt.getLandLord()+".Business.Revenue", rev+rt.getPrice());
            	
            }
            
            Property PR = null;
            
	        for (Property PRS : prys) {
	        	if (PRS.getName().equals(rt.getRegion())) {
	        		PR = PRS;
	        	}
	        }    
            
	        PR.addTrusted(rt.getName());
	        saveProperty();
	        
	        saveConfigs();
	    }
	    
	    @EventHandler
	    public void onRentStopped(RentalStopEvent rt) {
			saveConfigs();
            Property PR = null;
            
            for (Property P : prys) {
            	
            	if (P.getName().equals(rt.getRegion())) {
            		PR = P;
            	}
            	
            }
            
            for (String NM : PR.getTrusted()) {
            	PR.removeTrusted(NM);
            	PR.setRenter("None");
            }
        	econ.removeAcc(rt.getName());
            
	    }
	    
	    @EventHandler
	    public void onPropertySale(PropertySale rt) {
			
	    	if (isBusiness(rt.getSeller())) {
	    		int rv = config.getInt("PlayerData."+rt.getSeller()+".Business.Revenue");
	    		config.set("PlayerData."+rt.getSeller()+".Business.Revenue", rv+rt.getPrice());
	    	}
	    	saveConfigs();
            
	    }
	    
	    public void marketAdd(String I,double p) {
	    	if (I.equalsIgnoreCase("Mining")) {
	    		miningPRC = miningPRC+p;
	    	}else if (I.equalsIgnoreCase("Logging")) {
	    		loggingPRC = loggingPRC+p;
	    	}else if (I.equalsIgnoreCase("Lumber")) {
	    		lumberPRC = lumberPRC+p;
	    	}else if (I.equalsIgnoreCase("Farming")) {
	    		farmingPRC = farmingPRC+p;
	    	}
	    }
	    
	    public void marketSubtract(String I,double p) {
	    	if (I.equalsIgnoreCase("Mining")) {
	    		miningPRC = miningPRC-p;
	    	}else if (I.equalsIgnoreCase("Logging")) {
	    		loggingPRC = loggingPRC-p;
	    	}else if (I.equalsIgnoreCase("Lumber")) {
	    		lumberPRC = lumberPRC-p;
	    	}else if (I.equalsIgnoreCase("Farming")) {
	    		farmingPRC = farmingPRC-p;
	    	}
	    }
	    
	    public void AddRental(String Region, int Price, String Owner) {
	    	
	    	String R = Region;
	    	int P = Price;
	    	String O = Owner;
	    	
	    	config.set("ServerData.Rentals."+R+".LandLord", O);
	    	config.set("ServerData.Rentals."+R+".Rent", P);
	    	
	    	//String t = config.getString("ServerData.Rentals."+r+".Tenant");
	    	
	    }
	    
	    public void randomEvents() {
	    	
	    	int r1 = random(1,100);
	    	int r2 = random(0,150);
	    	
	    	if (r1 == r2) {
	    		
	    		int e = random(1,13);
	    		int d = random(1,90);
	    		
	    		if (e == 1) {
	    			
	    			farmingPRC = farmingPRC*(d/100);
	    			this.getLogger().log(Level.INFO, "[STSEcon] Random Event(1) Triggered");
	    		}else if (e == 2) {
	    			
	    			loggingPRC = loggingPRC*(d/100);
	    			this.getLogger().log(Level.INFO, "[STSEcon] Random Event(2) Triggered");
	    			
	    		}else if (e == 3) {
	    			
	    			miningPRC = miningPRC*(d/100);
	    			this.getLogger().log(Level.INFO, "[STSEcon] Random Event(3) Triggered");
	    			
	    		}else if (e == 4) {
	    			
	    			loggingPRC = loggingPRC*((d/100)+1);
	    			this.getLogger().log(Level.INFO, "[STSEcon] Random Event(4) Triggered");
	    			
	    		}else if (e == 5) {
	    			
	    			miningPRC = miningPRC*((d/100)+1);
	    			this.getLogger().log(Level.INFO, "[STSEcon] Random Event(5) Triggered");
	    			
	    		}else if (e == 6) {
	    			
	    			farmingPRC = farmingPRC*((d/100)+1);
	    			this.getLogger().log(Level.INFO, "[STSEcon] Random Event(6) Triggered");
	    			
	    		}else if (e == 7) {
	    			
	    			miningPRC = miningPRC*(d/100);
	    			loggingPRC = loggingPRC*((d/100)+1);
	    			this.getLogger().log(Level.INFO, "[STSEcon] Random Event(7) Triggered");
	    			
	    		}else if (e == 8) {
	    			
	    			loggingPRC = loggingPRC*(d/100);
	    			miningPRC = miningPRC*((d/100)+1);
	    			this.getLogger().log(Level.INFO, "[STSEcon] Random Event(8) Triggered");
	    			
	    		}else if (e == 9) {
	    			
	    			farmingPRC = farmingPRC*(d/100);
	    			loggingPRC = loggingPRC*((d/100)+1);
	    			this.getLogger().log(Level.INFO, "[STSEcon] Random Event(9) Triggered");
	    			
	    		}else if (e == 10) {
	    			
	    			loggingPRC = loggingPRC+(random(1,90)/10);
	    			this.getLogger().log(Level.INFO, "[STSEcon] Random Event(10) Triggered");
	    			
	    		}else if (e == 11) {
	    			
	    			miningPRC = miningPRC+(random(1,90)/10);
	    			this.getLogger().log(Level.INFO, "[STSEcon] Random Event(11) Triggered");
	    			
	    		}else if (e == 12) {
	    			
	    			farmingPRC = farmingPRC+(random(1,90)/10);
	    			this.getLogger().log(Level.INFO, "[STSEcon] Random Event(12) Triggered");
	    			
	    		}else{
	    			this.getLogger().log(Level.INFO, "[STSEcon] Random Event(13) Triggered");
	    			return;
	    		}
	    		
	    
	    		
	    	}
	    	
	    }
	    
	    public void buyPS(Player p, int ss) {
	    	String n = p.getName();
	    	if (econ.has(p.getName(), psp)) {
				int s = config.getInt("PlayerStocks.PlayerData."+n+".Shares");
				config.set("PlayerStocks.PlayerData."+n+".Shares", s+ss);
				econ.withdrawPlayer(p.getName(), psp*ss);
				p.sendMessage(prf+ChatColor.GOLD+"You purchased "+stocks.format(ss)+" shares of Player Stocks for $"+price.format(psp*ss));
				saveConfigs();
			}else {
				p.sendMessage(prf+"Not Enough Moneys!");
			}
	    }
	    
	    public void sellPS(Player p, int ss) {
	    	String n = p.getName();
	    	int s = config.getInt("PlayerStocks.PlayerData."+n+".Shares");
	    	if (s >= ss) {
				
				config.set("PlayerStocks.PlayerData."+n+".Shares", s-ss);
				econ.depositPlayer(p.getName(), psp*ss);
				p.sendMessage(prf+ChatColor.GOLD+"You sold "+stocks.format(ss)+" shares of Player Stocks for $"+price.format(psp*ss));
				saveConfigs();
			}else {
				p.sendMessage(prf+"Not Enough Moneys!");
			}
	    }
	    
	    public FileConfiguration getWGF(String WN) {
WG = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
	    	

	    		FileConfiguration RFN = new YamlConfiguration();
	    		File RFG = new File(WG.getDataFolder()+"/worlds/"+WN+"/", "regions.yml");
	    		//Bukkit.getPlayer("Wintergrasped").sendMessage(RFG.getAbsolutePath().toString());
	    		try {
					RFN.load(RFG);
					Bukkit.getPlayer("Wintergrasped").sendMessage(RFN.getList("regions")+"");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	    		//RF.add(RFN);
	    		
	    		return RFN;
	    		
	    	}
	    
	    public void saveProperty() {
	    	
	    	List<String> PRN = new ArrayList();
	    	
	    	for (Property PR : prys) {
	    		
	    		
	    		
	    		Location L1 = PR.getMinLoc();
	    		Location L2 = PR.getMaxLoc();
	    		
	    		
	    		config.set("PropertyData."+PR.getName()+".Name", PR.getName());
	    		config.set("PropertyData."+PR.getName()+".Owner", PR.getOwner());
	    		config.set("PropertyData."+PR.getName()+".MinX", L1.getX());
	    		config.set("PropertyData."+PR.getName()+".MinY", L1.getY());
	    		config.set("PropertyData."+PR.getName()+".MinZ", L1.getZ());
	    		
	    		config.set("PropertyData."+PR.getName()+".MaxX", L2.getX());
	    		config.set("PropertyData."+PR.getName()+".MaxY", L2.getY());
	    		config.set("PropertyData."+PR.getName()+".MaxZ", L2.getZ());
	    		config.set("PropertyData."+PR.getName()+".World", L1.getWorld().getName());
	    		config.set("PropertyData."+PR.getName()+".Trusted", PR.getTrusted());
	    		config.set("PropertyData."+PR+".Renter", PR.getRenter());
	    		saveConfigs();
	    		String WW = config.getString("PropertyData."+PR.getName()+".World");
	    		
	    		if (WW == null) {
	    			WW = "RPWorld";
	    		}
	    		
	    		PRN.add(PR.getName());
	    		
	    	}
	    	
	    	config.set("ServerData.Propertys", PRN);
	    	saveConfigs();
	    }
	    
	    public void loadProperty() {
	    	
	    	prys = new ArrayList();
	    	
	    	for (String PR : config.getStringList("ServerData.Propertys")) {
	    		
	    		String NM = config.getString("PropertyData."+PR+".Name");
	    		String ON = config.getString("PropertyData."+PR+".Owner");
	    		String renter = config.getString("PropertyData."+PR+".Renter");
	    		String Zone = config.getString("PropertyData."+PR+".Zone");
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
	    		
	    		PRR.setZone(Zone);
	    		PRR.setRenter(renter);
	    		if (config.getBoolean("PropertyData."+PR+".ForSale")) {
	    			PRR.setPrice(config.getInt("PropertyData."+PR+".Price"));
	    			PRR.SetSaleType(config.getString("PropertyData."+PR+".SaleType"));
	    			PRR.setForSale(true);
	    		}
	    		
	    		for (String TRP : config.getStringList("PropertyData."+PR+".Trusted")) {
	    			PRR.addTrusted(TRP);
	    		}
	    		
	    		if (prys.contains(PRR)) {
	    			
	    		}else {
	    			prys.add(PRR);
	    		}
	    		
	    	}
	    }
	    
	    public void Propertysave(Property PR) {

	    		
	    		Location L1 = PR.getMinLoc();
	    		Location L2 = PR.getMaxLoc();
	    		
	    		
	    		config.set("PropertyData."+PR.getName()+".Name", PR.getName());
	    		config.set("PropertyData."+PR.getName()+".Owner", PR.getOwner());
	    		config.set("PropertyData."+PR.getName()+".MinX", L1.getX());
	    		config.set("PropertyData."+PR.getName()+".MinY", L1.getY());
	    		config.set("PropertyData."+PR.getName()+".MinZ", L1.getZ());
	    		
	    		config.set("PropertyData."+PR.getName()+".MaxX", L2.getX());
	    		config.set("PropertyData."+PR.getName()+".MaxY", L2.getY());
	    		config.set("PropertyData."+PR.getName()+".MaxZ", L2.getZ());
	    		config.set("PropertyData."+PR.getName()+".World", L1.getWorld().getName());
	    		config.set("PropertyData."+PR.getName()+".ForSale", PR.getForSale());
	    		config.set("PropertyData."+PR.getName()+".Price", PR.getPrice());
	    		config.set("PropertyData."+PR.getName()+".SaleType", PR.getSaleType());
	    		
	    		String WW = config.getString("PropertyData."+PR.getName()+".World");
	    		

	    	saveConfigs();
	    }

	    public void syncPRYS(List<Property> prr) {
	    	prys = prr;
	    }
	    
	    
	    public void pasteSchem(Player player, File myfile) {
	    	
	    	ClipboardFormat format = ClipboardFormats.findByFile(myfile);
	    	ClipboardReader reader = null;
	    	Clipboard clipboard = null;
			try {
				reader = format.getReader(new FileInputStream(myfile));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        try {
				clipboard = reader.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        	// We need to adapt our world into a format that worldedit accepts. This looks like this:
	        	// Ensure it is using com.sk89q... otherwise we'll just be adapting a world into the same world.
	        	com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(player.getLocation().getWorld());
	        	             
	        	EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld,
	        	        -1);

	        	// Saves our operation and builds the paste - ready to be completed.
	        		ClipboardHolder CH = new ClipboardHolder(clipboard);
	        		Transform T = T = new AffineTransform().rotateY(90);
	        		if (player.getFacing().equals(BlockFace.EAST)){
	        			T = new AffineTransform().rotateY(90);
	        		}else if (player.getFacing().equals(BlockFace.WEST)){
	        			T = new AffineTransform().rotateY(270);
	        		}else if (player.getFacing().equals(BlockFace.NORTH)){
	        			T = new AffineTransform().rotateY(180);
	        		}else if (player.getFacing().equals(BlockFace.SOUTH)){
	        			T = new AffineTransform().rotateY(0);
	        		}
	        		
	        		
	        		CH.setTransform(T);
	        	    Operation operation = CH.createPaste(editSession)
	        	            .to(BlockVector3.at(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ())).ignoreAirBlocks(true).build();

	        	    try { // This simply completes our paste and then cleans up.
	        	        Operations.complete(operation);
	        	        editSession.flushSession();

	        	    } catch (WorldEditException e) { // If worldedit generated an exception it will go here
	        	        player.sendMessage(ChatColor.RED + "OOPS! Something went wrong, please contact an administrator");
	        	        e.printStackTrace();
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
	    
	    public boolean overlapsProperty(World W, Location Min, Location Max) {
	    	
	    	BlockVector3 VMin = BlockVector3.at(Min.getBlockX(), Min.getBlockY(), Min.getBlockZ());
	    	BlockVector3 VMax = BlockVector3.at(Max.getBlockX(), Max.getBlockY(), Max.getBlockZ());
	    	Region R = new CuboidRegion(VMin, VMax);
	    	
	    	
	    	
	    	config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
	    		for (BlockVector3 point : R) {
	    			
	    			Location L = new Location(W, point.getBlockX(), point.getBlockY(), point.getBlockZ());
	    			if (isOnProperty(L)) {
	    				return true;
	    			}
	    			
	    		}
	    	
	    	return false;
	    }
	    
	    public boolean isOnProperty(Location LL) {
			
			
			for (Property PR : prys) {
				
				Location LMN = PR.getMinLoc();
				Location LMX = PR.getMaxLoc();
				int x = LL.getBlockX();
				int z = LL.getBlockZ();
				
				int lx = LMN.getBlockX();
				int lz = LMN.getBlockZ();
				
				int hx = LMX.getBlockX();
				int hz = LMX.getBlockZ();
				
				if (x >= lx) {

					if (z >= lz) {

						if (x <= hx) {

							if (z <= hz) {

								return true;
							}else {

							}
						}else {

						}
					}else{

					}
				
				}else {

				}
				
			}
			
			
			return false;
		}
	    public double getPropertyValue(Region R, World W) {
	    	
	    	//MySQL.syncDB();
	    	double value = 0;
	    	config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
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
	    
	    public Main MainC() {
	    	return this;
	    }
	    
	    public boolean propertyExists(String N) {
	    	for (Property PR : prys) {
	    		
	    		if (PR.getName().equalsIgnoreCase(N)) {
	    			return true;
	    		}
	    		
	    	}
	    	return false;
	    }
	    
	    public Property getProperty(String N) {
	    	
	    	Property PRR;
	    	
	    	for (Property PR : prys) {
	    		
	    		if (PR.getName().equalsIgnoreCase(N)) {
	    			return PR;
	    		}
	    		
	    	}
	    	
	    	return null;
	    }
	    
	    public int random2(int min, int max) {

	        Random rand = new Random();
	        rand.setSeed(Bukkit.getServer().getWorld("World").getFullTime()+Bukkit.getBannedPlayers().size()+Bukkit.getOnlinePlayers().size()+System.currentTimeMillis()+System.nanoTime()+Bukkit.getOnlinePlayers().size()+Bukkit.getOperators().size()+Bukkit.getPort()+Bukkit.getOfflinePlayers().length+Bukkit.getIdleTimeout()+Bukkit.getIPBans().size()+Bukkit.getWorlds().size()+Bukkit.getName().getBytes().length+Bukkit.getWorld("world").getSeed());
	        int randomNum = rand.nextInt((max - min) + 1) + min;

	        return randomNum;
	    }

	    public void updateMap() {
	    	  Plugin dynmap;
	    	  DynmapAPI api;
	    	  MarkerAPI markerapi;
	    	  int updatesPerTick = 20;
	    	  
	    	  FileConfiguration cfg;
	    	  
	    	  MarkerSet set;
	    	  MarkerSet cityset;
	    	  long updperiod;
	    	  boolean use3d;
	    	  String infowindow;

	    	  boolean stop;
	    	  int maxdepth;
	    	  
	    	  
	    	    PluginManager pm = getServer().getPluginManager();
	    	    
	    	    dynmap = pm.getPlugin("dynmap");
	    	    api = (DynmapAPI)dynmap;
	    	    markerapi = api.getMarkerAPI();
	    	    cfg = getConfig();
	    	    markerapi.createMarkerSet("STSECO", "Properties", null, true);
	    	    List<String> pns = new ArrayList();

	    	    if(api.getMarkerAPI().getMarkerSet("STSECO")==null){
	    	    	api.getMarkerAPI().createMarkerSet("STSECO", "Properties", null, true).setLabelShow(true);
	    	    	api.getMarkerAPI().createMarkerSet("STSECO_Citys", "City", null, true).setLabelShow(true);
	    	    	api.getMarkerAPI().getMarkerSet("STSECO").setHideByDefault(false);
	    	    	
	    	    	}
	    	    if(api.getMarkerAPI().getMarkerSet("STSECO_Citys")==null){

	    	    	api.getMarkerAPI().createMarkerSet("STSECO_Citys", "City", null, true).setLabelShow(true);
	    	    	api.getMarkerAPI().getMarkerSet("STSECO_Citys").setHideByDefault(false);
	    	    	
	    	    	}
	    	    set = api.getMarkerAPI().getMarkerSet("STSECO");
	    	    cityset = api.getMarkerAPI().getMarkerSet("STSECO_Citys");
	    	    String color="ACFFBA";
	    	    
	    	    Location Center = new Location(Bukkit.getWorld(config.getString("CityData.Center.World")), config.getInt("CityData.Center.X"),config.getInt("CityData.Center.Y"),config.getInt("CityData.Center.Z"));
	    		int Radius = 2000;
	    	  
	    	  cityset.createCircleMarker("Spawn", "City Limits", false, "world", Center.getX(), Center.getY(), Center.getZ(), Radius, Radius, true);
	    	  
	    	    for (Property PR : prys) {
	    	    	pns.add(PR.getName());
 	    	
	    	    	
	    	        //double[] x = {PR.getMinLoc().getBlockX(), PR.getMinLoc().getBlockZ()};
	    	        //double[] z = {PR.getMaxLoc().getBlockX(), PR.getMaxLoc().getBlockZ()};
	    	    	
	    	    	double[] z = {PR.getMinLoc().getZ(), PR.getMaxLoc().getZ()};
	    	    	double[] x = {PR.getMinLoc().getX(), PR.getMaxLoc().getX()};
	    	    	
	    	    	String name = PR.getName();
	    	    	String lbl = "prop_"+prys.indexOf(PR);
	    	    	set.createAreaMarker(lbl, PR.getOwner()+"'s "+PR.getName(), false, "world", x, z, true);
	    	    	set.findAreaMarker(lbl).setLabel(PR.getOwner()+"s "+PR.getName());
	    	    	set.findAreaMarker(lbl).setLineStyle(3,0.8,Integer.parseInt(color, 16));
	    	    	set.findAreaMarker(lbl).setFillStyle(0.5,Integer.parseInt("ACFFBA", 16));
	    	    	set.findAreaMarker(lbl).setBoostFlag(true);
	    	    	
	    	    	
	    	    	
	    	    }
	    	    
	    	  set.setMarkerSetLabel("Properties");
	    	  set.setLayerPriority(10);
	    	  set.setHideByDefault(false);
	    	 
	    	  //newmap.put(markerid, m);
	    	  
	    	  
	    }
	    private Map<String, AreaMarker> resareas = new HashMap<>();
	    
	    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) { 
	    	List<String> re = new ArrayList();
	    	List<String> se = new ArrayList();
	    	List<String> seco = new ArrayList();
	    	List<String> seco_property = new ArrayList();
	    	List<String> sv = new ArrayList();
	    	List<String> seco_WHO = new ArrayList();
	    	List<String> player = new ArrayList();
	    	
	    	se.add("sv");
	    	se.add("wv");
	    	se.add("nv");
	    	se.add("dv");
	    	se.add("bslist");
	    	se.add("price");
	    	se.add("cb");
	    	se.add("rst");
	    	se.add("cs");
	    	se.add("si");
	    	se.add("dbs");
	    	se.add("bw");
	    	se.add("hireplayer");
	    	se.add("buyshop");
	    	se.add("rentstop");
	    	se.add("bsbuy");
	    	se.add("bssell");
	    	se.add("bal");
	    	se.add("buy");
	    	se.add("who");
	    	se.add("roll");
	    	se.add("sell");
	    	
	    	sv.add("business");
	    	sv.add("corp");
	    	sv.add("hire");
	    	sv.add("fire");
	    	
	    	for (Player P : Bukkit.getOnlinePlayers()) {
	    		player.add(P.getName());
	    	}
	    	
	    	seco.add("property");
	    	seco.add("addstock");
	    	seco.add("who");
	    	seco.add("pay");
	    	
	    	
	    	seco_property.add("create");
	    	
	    	Collections.sort(se);
	    	Collections.sort(sv);
	    	Collections.sort(seco);
	    	Collections.sort(seco_property);
	    	Collections.sort(player);
	    	
	    	if (cmd.getName().equalsIgnoreCase("se")) {
	    		re = new ArrayList();
	    		
	    		if (args[0].equals("")) {
	    			re = se;
	    		}else {
	    			re = new ArrayList();
	    		for (String S : se) {
	    			
	    			if (S.startsWith(args[0].toLowerCase())) {
	    				re.add(S);
	    			}
	    			
	    		}
	    		}
	    		if (args[0].equals("sv")) {
	    			re = new ArrayList();
	    			re = sv;
	    		}else if (args.length > 1){
	    			re = new ArrayList();
	    		for (String S : sv) {
	    			
	    			if (S.startsWith(args[1].toLowerCase())) {
	    				re.add(S);
	    			}
	    			
	    		}
	    		
	    		}
	    		
	    	}
	    	
	    	
	    	
	    	if (cmd.getName().equalsIgnoreCase("seco")) {
	    		
	    		re = new ArrayList();
	    		if (args[0].equals("")) {
	    			re = seco;
	    		}else {
	    		for (String S : seco) {
	    			
	    			if (S.startsWith(args[0].toLowerCase())) {
	    				re.add(S);
	    			}
	    			
	    		}
	    		}
	    		if (args[0].equals("property")) {
	    			re = new ArrayList();
	    			re = seco_property;
	    		}else if (args.length > 1){
	    			re = new ArrayList();
	    		for (String S : seco_property) {
	    			
	    			if (S.startsWith(args[1].toLowerCase())) {
	    				re.add(S);
	    			}
	    			
	    		}
	    		
	    		}
	    		if (args[0].equals("who")) {
	    			re = new ArrayList();
	    			re = player;
	    		}else if (args.length > 1){
	    			re = new ArrayList();
	    		for (String S : player) {
	    			
	    			if (S.startsWith(args[1].toLowerCase())) {
	    				re.add(S);
	    			}
	    			
	    		}
	    		
	    		}
	    		
	    		if (args[0].equals("pay")) {
	    			re = new ArrayList();
	    			re = player;
	    		}else if (args.length > 1){
	    			re = new ArrayList();
	    		for (String S : player) {
	    			
	    			if (S.startsWith(args[1].toLowerCase())) {
	    				re.add(S);
	    			}
	    			
	    		}
	    		
	    		}
	    		
	    		
	    	}
	    	Collections.sort(re);
	    	return re;
	    }
	    
		public void businessLoop(String b) {

			if (econ == null) {
				econ = new Econ();
			}

			//M.bso = M.config.getStringList("PlayerData.BusinessOwners");
			bls = new ArrayList();
			pls = new ArrayList();

			
				// Do Business maths here
				
				if (b == null) {
					Bukkit.getPlayer("Wintergrasped").sendMessage("Null B B is "+b);
					return;
				}else {
					Bukkit.getPlayer("Wintergrasped").sendMessage(b);
				}
				
				Player bp = Bukkit.getPlayer(b);

				// s = config.getInt("PlayerData."+b+".Business.Supplies");
				int e = config.getInt("PlayerData." + b + ".Business.Employee.Count");
				int arev = config.getInt("PlayerData." + b + ".Business.Revenue");
				int asal = config.getInt("PlayerData." + b + ".Business.OperationCost");
				int RV = config.getInt("PlayerData." + b + ".Business.Revenue");

				int rv = config.getInt("PlayerData." + b + ".Business.Revenue");
				int size = config.getInt("PlayerData." + b + ".Business.Employee.Count") + 1;
				int oc = config.getInt("PlayerData." + b + ".Business.OperationCost");
				int sh = config.getInt("PlayerData." + b + ".Business.Shares");
				int PRF = RV - oc;
				int s = config.getInt("PlayerData." + b + ".Business.Supplies");
				double ns = size * 1.25;

				Business bl = new Business(b, rv, oc, PRF, sh, size);
				bls.add(bl);
				pls.add(Bukkit.getPlayer(b));

				// config.set("PlayerData."+sender.getName()+".Employer", args[1]);
				// config.set("PlayerData."+sender.getName()+".Salary",
				// Integer.parseInt(args[2]));

				// config.set("PlayerData."+sender.getName()+".Employed", pe);

				if (s <= ns) {
					econ.withdrawPlayer(b, 250000);

					if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(b))) {

						Bukkit.getPlayer(b)
						        .sendMessage(prf + ChatColor.RED + " Current Supplies: " + s + " Needed Supplies: " + ns);

						Bukkit.getPlayer(b).sendMessage(prf + ChatColor.RED
						        + "You didn't have enough Business supplies when the fines system goes live you will have to pay $250,000 every time this happens");

					}
				}

				if (e >= 0) {
					eso = config.getIntegerList("PlayerData." + b + ".Business.EmployeeID.List");
					for (int es : config.getIntegerList("PlayerData." + b + ".Business.EmployeeID.List")) {
						int sal = config.getInt("PlayerData." + b + ".Business.Employees." + es + ".Salary");
						int rev = config.getInt("PlayerData." + b + ".Business.Employees." + es + ".Revenue");
						int mood = config.getInt("PlayerData." + b + ".Business.Employees." + es + ".Mood");
						int LTraining = config.getInt("PlayerData." + b + ".Business.Employees." + es + ".LastTraining");
						
						
						if (bp == null) {
							//Bukkit.getPlayer("Wintergrasped").sendMessage("Null bp ");
							//return;
						}
						
						Employee a = new Employee(es, sal, rev);
						// econ.withdrawPlayer(b, sal);
						// econ.depositPlayer(b, rev);
						int rt = random(1, 200);
						int rc = random2(1, 200);
						if (rt == rc) {
							sal = random(100, 1000);
							rev = random2(500, 1000);
						}

						int rst = random(1, 350);
						int rsc = random2(1, 340);

						if (rev >= sal) {

							if (rst == rsc) {
								//if (bp.isOnline()) {
									//Bukkit.getPlayer(b).sendMessage(ChatColor.RED + "Employee " + a.getID() + " Randomly Quit");
								//}
								sal = 0;
								rev = 0;
								eso.remove((eso.indexOf(a.getID())));
								config.set("PlayerData." + b + ".Business.Employees." + es + ".Revenue", 0);
								config.set("PlayerData." + b + ".Business.Employees." + es + ".Salary", 0);
								saveConfigs();
							}

						}

						if (mood <= 2) {
							int c = random(1, 4);
							int d = random2(1, 4);

							if (c == d) {
								//if (bp.isOnline()) {
									//Bukkit.getPlayer(b).sendMessage(ChatColor.RED + "Employee " + a.getID() + " Rage Quit");
								//}
								sal = 0;
								rev = 0;
								eso.remove((eso.indexOf(a.getID())));
								config.set("PlayerData." + b + ".Business.Employees." + es + ".Revenue", 0);
								config.set("PlayerData." + b + ".Business.Employees." + es + ".Salary", 0);
								saveConfigs();
							}

						}

						// config.set("PlayerData."+n+".Business.Employees."+args[2]+".TrainingCount",
						// 0);
						// config.set("PlayerData."+n+".Business.Employees."+args[2]+".IsTraining",
						// true);
						// config.set("PlayerData."+n+".Business.Employees."+args[2]+".Training",
						// config.getInt("PlayerData."+n+".Business.Employees."+args[2]+".Training")+1);
						// config.set("PlayerData."+n+".Business.Employees."+args[2]+".Revenue", 0);
						// config.set("PlayerData."+n+".Business.Employees."+args[2]+".Mood",
						// random(4,10));
						boolean isTraining = config
						        .getBoolean("PlayerData." + b + ".Business.Employees." + es + ".IsTraining");
						if (isTraining) {
							config.set("PlayerData." + b + ".Business.Employees." + es + ".LastTraining", 0);
							int TrainingCount = config
							        .getInt("PlayerData." + b + ".Business.Employees." + es + ".TrainingCount");
							TrainingCount = TrainingCount + 1;

							config.set("PlayerData." + b + ".Business.Employees." + es + ".TrainingCount", TrainingCount);
							saveConfigs();

							int T = config.getInt("PlayerData." + b + ".Business.Employees." + es + ".Training");

							if (TrainingCount >= config.getInt("TrainingDuration")) {
								TrainingCount = 0;
								T = T + 1;
								int NS = 0;
								if (T > 1 && T < 2) {
									NS = random(30, 220);
								} else if (T > 2 && T < 3) {
									NS = random(40, 230);
								} else if (T > 3 && T < 4) {
									NS = random(50, 320);
								} else if (T > 4 && T < 5) {
									NS = random(60, 330);
								} else if (T > 5 && T < 6) {
									NS = random(70, 340);
								} else if (T > 6 && T < 7) {
									NS = random(80, 350);
								} else if (T > 7 && T < 8) {
									NS = random(90, 360);
								} else if (T > 8 && T < 9) {
									NS = random(100, 370);
								} else if (T > 9 && T < 10) {
									NS = random(110, 380);
								} else if (T >= 10) {
									NS = random(120, 420);
								}

								config.set("PlayerData." + b + ".Business.Employees." + es + ".TrainingCount", 0);
								config.set("PlayerData." + b + ".Business.Employees." + es + ".IsTraining", false);
								config.set("PlayerData." + b + ".Business.Employees." + es + ".Revenue", random(30, 220));
								config.set("PlayerData." + b + ".Business.Employees." + es + ".Training", T);
								saveConfigs();

							}

						} else {
							config.set("PlayerData." + b + ".Business.Employees." + es + ".LastTraining", LTraining + 1);
							saveConfigs();
						}

						if (LTraining >= random(10, 20)) {
							mood = mood - 1;
							config.set("PlayerData." + b + ".Business.Employees." + es + ".Mood", mood);
							saveConfigs();
						}

						if ((rst + 1) == (rsc - 1)) {
							int EI = a.getID();
							int evn = random(1, 7);

							if (evn == 1) {
								int LI = random(1000, 250000);
								//if (bp.isOnline()) {
									Bukkit.getPlayer(b).sendMessage(ChatColor.RED + "Employee " + EI+ " Was injured on the job L&I Charged $" + LI);
									econ.withdraw(b, LI);
								//}
							} else if (evn == 2) {
								int LI = random(10000, 950000);
								//if (bp.isOnline()) {
									Bukkit.getPlayer(b).sendMessage(ChatColor.RED + "Employee " + EI + " Was injured on the job L&I Charged $" + LI);
									econ.withdraw(b, LI);
								//}
							} else if (evn == 3) {
								int LI = random(10000, 950000);
								//if (bp.isOnline()) {
									Bukkit.getPlayer(b).sendMessage(ChatColor.RED + "Employee " + EI + " Has taken a paid medical leave");
									config.set("PlayerData." + b + ".Business.Employees." + es + ".Revenue", 0);
								//}
							} else if (evn == 4) {
								int LI = random(10000, 950000);
								//if (bp.isOnline()) {
									Bukkit.getPlayer(b).sendMessage(ChatColor.RED + "Employee " + EI + " Has taken a paid vacation");
									config.set("PlayerData." + b + ".Business.Employees." + es + ".Revenue", 0);
								//}
							} else if (evn == 5) {
								int LI = random(100000, 1950000);
								//if (bp.isOnline()) {
									Bukkit.getPlayer(b).sendMessage(ChatColor.RED + "Employee " + EI+ " injured themself and a few other L&I charged $" + LI);
									econ.withdraw(b, LI);
								//}
							} else if (evn == 6) {
								int LI = random(1000000, 3950000);
								//if (bp.isOnline()) {
									Bukkit.getPlayer(b).sendMessage(ChatColor.RED + "Employee " + EI+ " severly injured themself and a few other L&I charged $" + LI);
									econ.withdraw(b, LI);
								//}
							} else if (evn == 7) {
								int LI = random(1000, 50000);
								//if (bp.isOnline()) {
									Bukkit.getPlayer(b).sendMessage(ChatColor.RED+ "A power surge damaged some critical equipment repairs cost $" + LI);
									econ.withdraw(b, LI);
								//}
							}

						}

						bp = Bukkit.getPlayer(b);

						if (rev < 0) {
							if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(b))) {
								Bukkit.getPlayer(b).sendMessage(ChatColor.RED+ "Business Error: 'Negative REV' - Please Report this to Wintergrasped");
							}
							rev = 0;
						}

						arev = arev + rev;

						if (arev < 0) {
							if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(b))) {
								Bukkit.getPlayer(b).sendMessage(ChatColor.RED+ "Business Error: 'Negative AREV' - Please Report this to Wintergrasped");
							}
							arev = 0;
						}
						asal = asal + sal;

						if (asal < 0) {
							if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(b))) {
								Bukkit.getPlayer(b).sendMessage(ChatColor.RED+ "Business Error: 'Negative ASAL' - Please Report this to Wintergrasped");
							}
							asal = 0;
						}

						// End Of Employee Loop
					}
				}

				for (String pe : config.getStringList("PlayerData." + b + ".Employed")) {
					if (Bukkit.getPlayer(pe).isOnline()) {
						int ss = config.getInt("PlayerStocks.PlayerData." + pe + ".Salary");
						econ.withdrawPlayer(b, ss);
						econ.depositPlayer(pe, ss);
					}
				}

				double cll = bl.getStockPrice() * bl.getShares();
				double ars = arev - cll; // cll= 32400

				if (cll < 0) {
					if (Bukkit.getPlayer(b).isOnline()) {
						Bukkit.getPlayer(b).sendMessage(
						        ChatColor.RED + "Business Error Negative CLL Please report this in #bugs CLL:" + cll);

					}
					ars = arev * 2.5;
				}

				// arev = 3245
				int dv = config.getInt("PayCycleDivider");
				double div = 0;

				config.set("PlayerBus." + b + ".CLL", cll);
				config.set("PlayerBus." + b + ".arev", arev);
				config.set("PlayerBus." + b + ".asal", asal);
				config.set("PlayerBus." + b + ".ars", ars);

				if (ars > (arev * 3)) {
					if (Bukkit.getPlayer(b).isOnline()) {
						Bukkit.getPlayer(b).sendMessage(ChatColor.RED
						        + "Business Error ars greater then 3x arev. Post in bugs - ARS: " + ars + " AREV:" + arev);

					}
					ars = arev * 2.5;
				}

				List<String> ShareHolders = config.getStringList("PlayerData." + b + ".Business.ShareHolders");

				if (ShareHolders.size() >= 0) {
					div = arev * 0.20;
					int tds = 0;
					for (String SN : ShareHolders) {
						tds = tds + config.getInt("PlayerData." + b + ".Business.ShareHolderShares." + SN);
					}

					double tdiv = div / tds;
					config.set("PlayerBus." + b + ".tdiv", tdiv);
					if (tdiv >= config.getDouble("MaxDividen")) {
						tdiv = config.getDouble("MaxDividen");
					}
					for (String SN : ShareHolders) {
						int ss = config.getInt("PlayerData." + b + ".Business.ShareHolderShares." + SN);
						econ.deposit(SN, (tdiv * ss));
					}
					config.set("BusinessData." + b + ".LastDiv", tdiv);
				} else {
					config.set("BusinessData." + b + ".LastDiv", arev * 0.20);
				}

				// config.set("PlayerData."+b+".Business.ShareHolderShares."+p.getName(), cs);
				// config.set("PlayerData."+b+".Business.ShareHolders", cs);

				double pds = ars;

				int wh = config.getInt("PlayerData." + b + ".Properties.warehouses");
				int fc = config.getInt("PlayerData." + b + ".Properties.factory");
				double pt = (wh * 100000) * 0.12;
				pt = pt + ((fc * 1000000) * 0.12);

				if (ars < 0) {
					if (Bukkit.getPlayer(b).isOnline()) {
						Bukkit.getPlayer(b).sendMessage(ChatColor.RED + "Business Error 'Negative ARS' Please report this to Wintergrasped");
					}
					ars = 100;
				}
				Bukkit.getPlayer(b).sendMessage(ChatColor.GREEN + "Business Income: $" + price.format(pds));
				Bukkit.getPlayer(b).sendMessage(ChatColor.GREEN + "Business Tax: $" + price.format(pds * 0.097));
				Bukkit.getPlayer(b).sendMessage(ChatColor.GREEN + "Comercial Property Tax: $" + price.format(pt));

				econ.withdrawPlayer(b, pt);
				pds = pds - (pds * 0.097);
				if (pds >= 10000 && pds <= 10000) {
					Bukkit.getPlayer(b).sendMessage(ChatColor.GREEN + "You paid $" + (price.format(pds * 0.14)) + " of Capital Gains Tax");
					pds = pds * 0.96;
				} else if (pds >= 100001 && pds <= 500000) {
					Bukkit.getPlayer(b).sendMessage(ChatColor.GREEN + "You paid $" + (price.format(pds * 0.2)) + " of Capital Gains Tax");
					pds = pds * 0.8;
				} else if (pds >= 500001 && pds <= 900000) {
					Bukkit.getPlayer(b).sendMessage(ChatColor.GREEN + "You paid $" + (price.format(pds * 0.3)) + " of Capital Gains Tax");
					pds = pds * 0.7;
				} else if (pds >= 900001 && pds <= 1000000) {
					Bukkit.getPlayer(b).sendMessage(ChatColor.GREEN + "You paid $" + (price.format(pds * 0.4)) + " of Capital Gains Tax");
					pds = pds * 0.6;
				} else if (pds >= 1000001 && pds <= 1500000) {
					Bukkit.getPlayer(b).sendMessage(ChatColor.GREEN + "You paid $" + (price.format(pds * 0.5)) + " of Capital Gains Tax");
					pds = pds * 0.5;
				} else if (pds >= 1500001 && pds <= 1800000) {
					Bukkit.getPlayer(b).sendMessage(ChatColor.GREEN + "You paid $" + (price.format(pds * 0.6)) + " of Capital Gains Tax");
					pds = pds * 0.4;
				} else if (pds >= 1800001 && pds <= 2000000) {
					Bukkit.getPlayer(b).sendMessage(ChatColor.GREEN + "You paid $" + (price.format(pds * 0.7)) + " of Capital Gains Tax");
					pds = pds * 0.3;
				} else if (pds >= 2000001 && pds <= 2300000) {
					Bukkit.getPlayer(b).sendMessage(ChatColor.GREEN + "You paid $" + (price.format(pds * 0.8)) + " of Capital Gains Tax");
					pds = pds * 0.2;
				} else if (pds >= 2300001) {
					Bukkit.getPlayer(b).sendMessage(ChatColor.GREEN + "You paid $" + (price.format(pds * 0.9)) + " of Capital Gains Tax");
					pds = pds * 0.1;
				}

				if (pds >= 100000) {
					pds = pds / dv;
				}

				if (pds > 0) {
					econ.depositPlayer(b, pds);
				} else {
					if (Bukkit.getPlayer(b).isOnline()) {
						Bukkit.getPlayer(b)
						        .sendMessage(ChatColor.RED + "Business Error Negative Deposit. Post in bugs - PDS: " + pds);
					}
				}

				if (asal >= 0) {
					econ.withdrawPlayer(b, asal);
				} else {
					if (Bukkit.getPlayer(b).isOnline()) {
						Bukkit.getPlayer(b).sendMessage(
						        ChatColor.RED + "Business Error Negative Deposit. Post in bugs - ASAL: " + asal);
					}
				}

				Bukkit.getPlayer(b).sendMessage(prf + "You paid your employees $" + price.format(asal));

				Bukkit.getPlayer(b).sendMessage(prf + "Your employees earned you $" + price.format(arev));

				config.set("PlayerData." + b + ".Business.Revenue", arev + cll);
				config.set("PlayerData." + b + ".Business.OperationCost", asal);
				config.set("PlayerData." + b + ".Business.Supplies", s - ns);
				saveConfigs();

			

		}
		
		
		public void syncConfig() {
			
			
			config.set("Business.ServerData.ShopRegions", srs);
	    	
	    	
	    	config.set("Business.ServerData.WareHouseRegions", wrs);
	    	
	    	
	    	config.set("Business.ServerData.FactoryRegions", frs);
	    	
	    	config.set("ServerData.StockPrice", ps_price);
	    	
	    	config.set("ServerData.LoggingStockPrice", loggingPRC);
	    	config.set("ServerData.MiningStockPrice", miningPRC);
	    	config.set("ServerData.FarmingStockPrice", farmingPRC);
	    	config.set("ServerData.LumberStockPrice", lumberPRC);
	    	
	    	config.set("ServerData.Joins", joins);
	    	config.set("ServerData.PSJM", psjm);
	    	config.set("ServerData.PSM", psm);
	    	config.set("ServerData.PSMD", psmd);
	    	config.set("ServerData.PSP", psp);
	    	config.set("ServerData.PLM", plm);
	    	config.set("ServerData.Rented", rented);
	    	config.set("SeverData.csid", csid);
	    	
	    	config.set("PropertyData.PropertySales", propertySales);
	    	
	    	saveConfigs();
	    	
	    	for (Eviction r : evs) {
	    		
	    		evn.add(r.getTenant());
	    		config.set("SeverData.evictions."+r.getTenant()+".Tenant", r.getTenant());
	    		config.set("SeverData.evictions."+r.getTenant()+".Landlord", r.getLandlord());
	    		config.set("SeverData.evictions."+r.getTenant()+".Region", r.getRegion());
	    		
	    	}
	    	saveProperty();
	    	List<String> PRN = new ArrayList();
	    	
	    	for (Property PR : prys) {
	    		
	    		
	    		
	    		
	    		Location L1 = PR.getMinLoc();
	    		Location L2 = PR.getMaxLoc();
	    		
	    		
	    		config.set("PropertyData."+PR.getName()+".Name", PR.getName());
	    		config.set("PropertyData."+PR.getName()+".Owner", PR.getOwner());
	    		config.set("PropertyData."+PR.getName()+".MinX", L1.getX());
	    		config.set("PropertyData."+PR.getName()+".MinY", L1.getY());
	    		config.set("PropertyData."+PR.getName()+".MinZ", L1.getZ());
	    		
	    		config.set("PropertyData."+PR.getName()+".MaxX", L2.getX());
	    		config.set("PropertyData."+PR.getName()+".MaxY", L2.getY());
	    		config.set("PropertyData."+PR.getName()+".MaxZ", L2.getZ());
	    		
	    		config.set("PropertyData."+PR.getName()+".World", PR.getMinLoc().getWorld().getName());
	    		config.set("PropertyData."+PR.getName()+".Zone", PR.getZone());
	    		PRN.add(PR.getName());
	    		
	    	}
	    	
	    	config.set("ServerData.Propertys", PRN);
	    	
	    	config.set("SeverData.eviction.Names", evn);
	    	
	    	saveConfigs();
		}
		
		public void syncPrices() {
			
			
			loggingPRC = config.getDouble("ServerData.LoggingStockPrice");
	    	miningPRC = config.getDouble("ServerData.MiningStockPrice");
	    	farmingPRC = config.getDouble("ServerData.FarmingStockPrice");
	    	lumberPRC = config.getDouble("ServerData.LumberStockPrice");
	    	redstonePRC = config.getDouble("ServerData.RedStoneStockPrice");
	    	
	    	if (loggingPRC <= 0.05) {
	    		loggingPRC = 0.06;
	    	}
	    	
	    	if (miningPRC <= 0.05) {
	    		miningPRC = 0.06;
	    	}
	    	
	    	if (lumberPRC <= 0.05) {
	    		lumberPRC = 0.06;
	    	}
	    	
	    	if (farmingPRC <= 0.05) {
	    		farmingPRC = 0.06;
	    	}
	    	
	    	if (redstonePRC <= 0.05) {
	    		redstonePRC = 0.06;
	    	}
			
	    	config.set("ServerData.LoggingStockPrice", loggingPRC);
	    	config.set("ServerData.MiningStockPrice", miningPRC);
	    	config.set("ServerData.FarmingStockPrice", farmingPRC);
	    	config.set("ServerData.LumberStockPrice", lumberPRC);
	    	
	    	
	    	saveConfigs();
		}
		
		
		@EventHandler
		public void SyncEvent(SyncPrices SP) {
			
			loggingPRC = SP.getLogging();
			miningPRC = SP.getMining();
			farmingPRC = SP.getFarming();
			
			config.set("ServerData.LoggingStockPrice", loggingPRC);
	    	config.set("ServerData.MiningStockPrice", miningPRC);
	    	config.set("ServerData.FarmingStockPrice", farmingPRC);
	    	config.set("ServerData.LumberStockPrice", lumberPRC);
	    	
	    	
	    	saveConfigs();
			
		}

		
		public void loadWorth(Player p) {
			final File myfile= new File(this.getDataFolder()+"/worth.yml");
			FileConfiguration wc = YamlConfiguration.loadConfiguration(myfile);
			
			Bukkit.getLogger().log(Level.INFO, "Loading worth file "+this.getDataFolder().toString()+"/worth.yml");
			
			ItemStack IS = new ItemStack(Material.ACACIA_BOAT, 1);
			
			Material[] M = Material.values();
			
			p.sendMessage(prf+" Loading Econ Values");
			Bukkit.getLogger().log(Level.INFO, "Loading Econ Values");
			
			for (Material MM : M) {
				p.sendMessage(prf+"Checking "+MM.name());
				if (wc.getDouble("worth."+MM.name().toLowerCase()) == 0) {
					p.sendMessage(prf+""+MM.name()+" No Value");
					Bukkit.getLogger().log(Level.INFO, MM.name()+" has No Value");
				}else{
					double v = wc.getDouble("worth."+MM.name().toLowerCase());
					config.set("Sell.Worth."+MM.name(), v);
					saveConfigs();
					p.sendMessage(prf+""+MM.name()+" Value $"+v);
					Bukkit.getLogger().log(Level.INFO, MM.name()+" Worth $"+v);
				}
				
			}
			
		}
		
		public void spawnJohn() {
			//-963, 27, -1462
			if (john) {
				if (UnDead_John != null) {
					if (UnDead_John.isDead()) {
						
					}else {
						return;
					}
				}
			}else {
			Bukkit.getLogger().log(Level.INFO, "Spawning Undead John");
			Location spawnLocation = new Location(Bukkit.getWorld("world"), -963, 27, -1462);
			spawnLocation.setYaw(-90);
			Entity ZombieEntity = Bukkit.getWorld("world").spawnEntity(spawnLocation, EntityType.ZOMBIE);
			Zombie zombie = (Zombie) ZombieEntity;
			zombie.setBaby(false);
			zombie.setRemoveWhenFarAway(false);
			zombie.setCanPickupItems(false);
			zombie.setTarget(null);
			zombie.setAI(false);
			zombie.setCustomName("UnDead_John");
			zombie.setCustomNameVisible(false);
			
			
			

			
			UnDead_John = zombie;
			john = true;
			}
		}
		
		public void spawnJoe(Location l) {
			// Create a new instance of an EntityType (in this case, a zombie)
			EntityType zombie = EntityType.ZOMBIE;
			
			World world = l.getWorld();

			// Create a new instance of the entity using the EntityType
			LivingEntity raidBoss = (LivingEntity) world.spawnEntity(l, zombie);

			// Set the health of the raid boss to a higher value than a normal zombie
			raidBoss.setMaxHealth(200);
			raidBoss.setHealth(200);
			raidBoss.setCustomName("Joe");
			raidBoss.setCustomNameVisible(true);
			// You can also add custom attributes to the raid boss, 
			// such as increased damage or resistance to certain types of damage
			
			AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 20, AttributeModifier.Operation.ADD_NUMBER);
			raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
			
			
		}
		
		public void spawnFred(Location l) {
			// Create a new instance of an EntityType (in this case, a zombie)
			EntityType skeleton = EntityType.SKELETON;
			
			World world = l.getWorld();

			// Create a new instance of the entity using the EntityType
			LivingEntity raidBoss = (LivingEntity) world.spawnEntity(l, skeleton);

			// Set the health of the raid boss to a higher value than a normal zombie
			raidBoss.setMaxHealth(200);
			raidBoss.setHealth(200);

			raidBoss.setCustomName("Fred");
			raidBoss.setCustomNameVisible(true);
			// You can also add custom attributes to the raid boss, 
			// such as increased damage or resistance to certain types of damage
			
			AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 20, AttributeModifier.Operation.ADD_NUMBER);
			raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
			raidBoss.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
			//raidBoss.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addModifier(A);
			//raidBoss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(A);
			//raidBoss.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).addModifier(A);
			raidBoss.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
			
			
			
		}
		
		public void spawnBob(Location l) {
			// Create a new instance of an EntityType (in this case, a zombie)
			EntityType skeleton = EntityType.WITHER_SKELETON;
			
			World world = l.getWorld();

			// Create a new instance of the entity using the EntityType
			LivingEntity raidBoss = (LivingEntity) world.spawnEntity(l, skeleton);

			// Set the health of the raid boss to a higher value than a normal zombie
			raidBoss.setMaxHealth(800);
			raidBoss.setHealth(800);

			// You can also add custom attributes to the raid boss, 
			// such as increased damage or resistance to certain types of damage
			
			AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 50, AttributeModifier.Operation.ADD_NUMBER);
			A.getUniqueId();
			raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
			raidBoss.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
			raidBoss.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addModifier(A);
			raidBoss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(A);
			raidBoss.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).addModifier(A);
			raidBoss.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
			raidBoss.setCustomName("Bob");
			raidBoss.setCustomNameVisible(true);
			
			
			
			raidBoss.setCustomNameVisible(true);
			
			
		}
		
		public void spawnWolf(Location l) {
			// Create a new instance of an EntityType (in this case, a zombie)
			EntityType skeleton = EntityType.WOLF;
			
			World world = l.getWorld();

			// Create a new instance of the entity using the EntityType
			LivingEntity raidBoss = (LivingEntity) world.spawnEntity(l, skeleton);

			// Set the health of the raid boss to a higher value than a normal zombie
			raidBoss.setMaxHealth(800);
			raidBoss.setHealth(800);

			// You can also add custom attributes to the raid boss, 
			// such as increased damage or resistance to certain types of damage
			
			AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 50, AttributeModifier.Operation.ADD_NUMBER);
			A.getUniqueId();
			raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
			raidBoss.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
			raidBoss.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addModifier(A);
			raidBoss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(A);
			raidBoss.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).addModifier(A);
			raidBoss.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
			
			
			
		}
		
		
		
		public void spawnJJ(Location l) {
			// Create a new instance of an EntityType (in this case, a zombie)
			EntityType skeleton = EntityType.WARDEN;
			
			World world = l.getWorld();

			// Create a new instance of the entity using the EntityType
			
			LivingEntity raidBoss = (LivingEntity) world.spawnEntity(l, skeleton);

			// Set the health of the raid boss to a higher value than a normal zombie
			raidBoss.setMaxHealth(1800);
			raidBoss.setHealth(1800);

			// You can also add custom attributes to the raid boss, 
			// such as increased damage or resistance to certain types of damage
			
			AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 70, AttributeModifier.Operation.ADD_NUMBER);
			
			raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
			
			raidBoss.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
			raidBoss.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addModifier(A);
			raidBoss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(A);
			raidBoss.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).addModifier(A);
			raidBoss.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
			raidBoss.setCustomName("JJ");
			raidBoss.setCustomNameVisible(true);

			
			
		}
		
		boolean bob_1 = false;
		boolean bob_2 = false;
		boolean bob_3 = false;
		boolean bob_4 = false;
		boolean bob_5 = false;
		boolean bob_6 = false;
		boolean bob_7 = false;
		boolean bob_8 = false;
		
		
		@EventHandler
		public void entityDamaged(EntityDamageByEntityEvent e) {
			LivingEntity boss = (LivingEntity) e.getEntity();
			EntityDamageByEntityEvent event = e;
			
			
			if (e.getDamager().getType().equals(EntityType.PLAYER)) {
				if (e.getDamager().isOp()) {
					Player p = Bukkit.getPlayer(e.getDamager().getName());
					if (p.getInventory().getItemInMainHand().getType().equals(Material.BLAZE_ROD)) {
	        			p.getLocation().getWorld().spawnParticle(Particle.SWEEP_ATTACK, p.getLocation(), 1);
	        			p.playSound(p.getLocation(), Sound.ENTITY_GHAST_WARN, 3, 1);
	        			e.getEntity().setFireTicks(60);
	        			e.setDamage(e.getDamage()+8);
	        			p.getInventory().remove(new ItemStack(Material.BLAZE_ROD, 1));
	        		}
				}
			}
			
			if (e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase("arena")) {
				if (e.getEntityType().equals(EntityType.WITHER_SKELETON)) {
					
					boss = (LivingEntity) e.getEntity();
					
					if (boss.getMaxHealth() == 800) {
						Location l = e.getEntity().getLocation();
						
						int a = random(1,85);
						int b = random(1,65);
						
						
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.75) {
							if (bob_1) {
								
							}else {
								bob_1 = true;
								World w = Bukkit.getWorld("arena");
								//R
								Location playerLoc = boss.getLastDamageCause().getEntity().getLocation();
								TNTPrimed tnt = playerLoc.getWorld().spawn(playerLoc, TNTPrimed.class);
								tnt.setFuseTicks(30);
								tnt.setIsIncendiary(true);
								tnt.setYield(0);
								
								w.getBlockAt(new Location(w, -120, 119, 145)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -121, 119, 145)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -122, 119, 145)).setType(Material.OBSIDIAN);
								
								w.getBlockAt(new Location(w, -120, 119, 144)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -121, 119, 144)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -122, 119, 144)).setType(Material.OBSIDIAN);
								
								w.getBlockAt(new Location(w, -120, 119, 145)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -120, 119, 145)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -120, 119, 145)).setType(Material.OBSIDIAN);
								
							}
						}
						
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.55) {
							if (bob_2) {
								
							}else {
								bob_2 = true;
								World w = Bukkit.getWorld("arena");
								//R
								Location playerLoc = boss.getLastDamageCause().getEntity().getLocation();
								TNTPrimed tnt = playerLoc.getWorld().spawn(playerLoc, TNTPrimed.class);
								tnt.setFuseTicks(30);
								tnt.setIsIncendiary(true);
								tnt.setYield(0);
								
								w.getBlockAt(new Location(w, -120, 119, 145)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -121, 119, 145)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -122, 119, 145)).setType(Material.LAVA);
								
								w.getBlockAt(new Location(w, -120, 119, 144)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -121, 119, 144)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -122, 119, 144)).setType(Material.LAVA);
								
								w.getBlockAt(new Location(w, -120, 119, 143)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -121, 119, 143)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -122, 119, 143)).setType(Material.LAVA);
							}
						}
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.50) {
							if (bob_3) {
								
							}else {
								bob_3 = true;
								World w = Bukkit.getWorld("arena");
								//L
								Location playerLoc = boss.getLastDamageCause().getEntity().getLocation();
								TNTPrimed tnt = playerLoc.getWorld().spawn(playerLoc, TNTPrimed.class);
								tnt.setFuseTicks(30);
								tnt.setIsIncendiary(true);
								tnt.setYield(0);
								
								w.getBlockAt(new Location(w, -120, 119, 130)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -121, 119, 130)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -122, 119, 130)).setType(Material.OBSIDIAN);
								
								w.getBlockAt(new Location(w, -120, 119, 129)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -121, 119, 129)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -122, 119, 129)).setType(Material.OBSIDIAN);
								
								w.getBlockAt(new Location(w, -120, 119, 128)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -121, 119, 128)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -122, 119, 129)).setType(Material.OBSIDIAN);
							}
						}
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.45) {
							if (bob_4) {
								
							}else {
								bob_4 = true;
								World w = Bukkit.getWorld("arena");
								//R
								Location playerLoc = boss.getLastDamageCause().getEntity().getLocation();
								TNTPrimed tnt = playerLoc.getWorld().spawn(playerLoc, TNTPrimed.class);
								tnt.setFuseTicks(30);
								tnt.setIsIncendiary(true);
								tnt.setYield(0);
								
								w.getBlockAt(new Location(w, -120, 119, 145)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -121, 119, 145)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -122, 119, 145)).setType(Material.OBSIDIAN);
								
								w.getBlockAt(new Location(w, -120, 119, 144)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -121, 119, 144)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -122, 119, 144)).setType(Material.OBSIDIAN);
								
								w.getBlockAt(new Location(w, -120, 119, 143)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -121, 119, 143)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -122, 119, 143)).setType(Material.OBSIDIAN);
							}
						}
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.43) {
							if (bob_5) {
								
							}else {
								bob_5 = true;
								World w = Bukkit.getWorld("arena");
								//L
								Location playerLoc = boss.getLastDamageCause().getEntity().getLocation();
								TNTPrimed tnt = playerLoc.getWorld().spawn(playerLoc, TNTPrimed.class);
								tnt.setFuseTicks(30);
								tnt.setIsIncendiary(true);
								tnt.setYield(0);
								
								w.getBlockAt(new Location(w, -120, 119, 130)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -121, 119, 130)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -122, 119, 130)).setType(Material.LAVA);
								
								w.getBlockAt(new Location(w, -120, 119, 129)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -121, 119, 129)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -122, 119, 129)).setType(Material.LAVA);
								
								w.getBlockAt(new Location(w, -120, 119, 128)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -121, 119, 128)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -122, 119, 129)).setType(Material.LAVA);
							}
						}
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.40) {
							if (bob_6) {
								
							}else {
								bob_6 = true;
								World w = Bukkit.getWorld("arena");
								//L
								Location playerLoc = boss.getLastDamageCause().getEntity().getLocation();
								TNTPrimed tnt = playerLoc.getWorld().spawn(playerLoc, TNTPrimed.class);
								tnt.setFuseTicks(30);
								tnt.setIsIncendiary(true);
								tnt.setYield(0);
								
								w.getBlockAt(new Location(w, -120, 119, 130)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -121, 119, 130)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -122, 119, 130)).setType(Material.OBSIDIAN);
								
								w.getBlockAt(new Location(w, -120, 119, 129)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -121, 119, 129)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -122, 119, 129)).setType(Material.OBSIDIAN);
								
								w.getBlockAt(new Location(w, -120, 119, 128)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -121, 119, 128)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -122, 119, 129)).setType(Material.OBSIDIAN);
								
								w.getBlockAt(new Location(w, -120, 119, 145)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -121, 119, 145)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -122, 119, 145)).setType(Material.OBSIDIAN);
								
								w.getBlockAt(new Location(w, -120, 119, 144)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -121, 119, 144)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -122, 119, 144)).setType(Material.OBSIDIAN);
								
								w.getBlockAt(new Location(w, -120, 119, 143)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -121, 119, 143)).setType(Material.OBSIDIAN);
								w.getBlockAt(new Location(w, -122, 119, 143)).setType(Material.OBSIDIAN);
							}
						}
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.30) {
							if (bob_7) {
								
							}else {
								bob_7 = true;
								World w = Bukkit.getWorld("arena");
								//L
								Location playerLoc = boss.getLastDamageCause().getEntity().getLocation();
								TNTPrimed tnt = playerLoc.getWorld().spawn(playerLoc, TNTPrimed.class);
								tnt.setFuseTicks(30);
								tnt.setIsIncendiary(true);
								tnt.setYield(0);
								
								w.getBlockAt(new Location(w, -120, 119, 130)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -121, 119, 130)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -122, 119, 130)).setType(Material.LAVA);
								
								w.getBlockAt(new Location(w, -120, 119, 129)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -121, 119, 129)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -122, 119, 129)).setType(Material.LAVA);
								
								w.getBlockAt(new Location(w, -120, 119, 128)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -121, 119, 128)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -122, 119, 129)).setType(Material.LAVA);
								
								w.getBlockAt(new Location(w, -120, 119, 145)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -121, 119, 145)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -122, 119, 145)).setType(Material.LAVA);
								
								w.getBlockAt(new Location(w, -120, 119, 144)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -121, 119, 144)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -122, 119, 144)).setType(Material.LAVA);
								
								w.getBlockAt(new Location(w, -120, 119, 143)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -121, 119, 143)).setType(Material.LAVA);
								w.getBlockAt(new Location(w, -122, 119, 143)).setType(Material.LAVA);
							}
						}
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.18) {
							if (bob_8) {
								
							}else {
								bob_8 = true;
								World w = Bukkit.getWorld("arena");
								//L
								Location playerLoc = boss.getLastDamageCause().getEntity().getLocation();
								TNTPrimed tnt = playerLoc.getWorld().spawn(playerLoc, TNTPrimed.class);
								tnt.setFuseTicks(30);
								tnt.setIsIncendiary(true);
								tnt.setYield(0);
								
								w.getBlockAt(new Location(w, -120, 119, 130)).setType(Material.AIR);
								w.getBlockAt(new Location(w, -121, 119, 130)).setType(Material.AIR);
								w.getBlockAt(new Location(w, -122, 119, 130)).setType(Material.AIR);
								
								w.getBlockAt(new Location(w, -120, 119, 129)).setType(Material.AIR);
								w.getBlockAt(new Location(w, -121, 119, 129)).setType(Material.AIR);
								w.getBlockAt(new Location(w, -122, 119, 129)).setType(Material.AIR);
								
								w.getBlockAt(new Location(w, -120, 119, 128)).setType(Material.AIR);
								w.getBlockAt(new Location(w, -121, 119, 128)).setType(Material.AIR);
								w.getBlockAt(new Location(w, -122, 119, 129)).setType(Material.AIR);
								
								w.getBlockAt(new Location(w, -120, 119, 145)).setType(Material.AIR);
								w.getBlockAt(new Location(w, -121, 119, 145)).setType(Material.AIR);
								w.getBlockAt(new Location(w, -122, 119, 145)).setType(Material.AIR);
								
								w.getBlockAt(new Location(w, -120, 119, 144)).setType(Material.AIR);
								w.getBlockAt(new Location(w, -121, 119, 144)).setType(Material.AIR);
								w.getBlockAt(new Location(w, -122, 119, 144)).setType(Material.AIR);
								
								w.getBlockAt(new Location(w, -120, 119, 143)).setType(Material.AIR);
								w.getBlockAt(new Location(w, -121, 119, 143)).setType(Material.AIR);
								w.getBlockAt(new Location(w, -122, 119, 143)).setType(Material.AIR);
							}
						}
						
						
						
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.6) {
							
							 a = random(6,85);
							 b = random(1,95);
							
							if (a == b) {
								spawnJoe(l);
							}
							
							if (b == 15) {
								spawnFred(l);
							}
							
							if (a == 6) {
								LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add3 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add4 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add5 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
							}
							
							if (b == 9) {
								LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add3 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add4 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add5 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
							}
							
							if (b == 20) {
								LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
								LivingEntity add3 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
								LivingEntity add4 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
								LivingEntity add5 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
							}
							
							if (a == 21) {
								LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
								LivingEntity add3 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
								LivingEntity add4 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
								LivingEntity add5 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
							}
							
							
						}
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.5) {
							
							 a = random(1,55);
							 b = random(1,35);
							
							if (a == b) {
								spawnJoe(l);
							}
							
							if (b == 15) {
								spawnFred(l);
							}
							
							if (a == 6) {
								LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add3 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add4 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add5 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
							}
							
							if (b == 9) {
								LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add3 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add4 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add5 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
							}
							
							if (b == 20) {
								LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
								LivingEntity add3 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
								LivingEntity add4 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
								LivingEntity add5 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
							}
							
							if (a == 21) {
								LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
								LivingEntity add3 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
								LivingEntity add4 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
								LivingEntity add5 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
							}
							
							
						}
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.1) {
							a = random(1,85);
							b = random(1,65);
							if (a==b) {
								Location playerLoc = boss.getLastDamageCause().getEntity().getLocation();
								TNTPrimed tnt = playerLoc.getWorld().spawn(playerLoc, TNTPrimed.class);
								tnt.setFuseTicks(30);
								tnt.setIsIncendiary(true);
								tnt.setYield(0);
							}
							LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
						
							if (boss.getHealth() / boss.getMaxHealth() <= 0.02) {
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
							}
						}
					}
					
				}
			}else {
				if (e.getEntity().getType().equals(EntityType.WARDEN)) {
					
					//Bukkit.getLogger().log(Level.INFO, "Warden Damaged By "+e.getDamager().getType().toString()+" With Name of "+e.getDamager().getName());
					
					if (boss.getMaxHealth() == 1800) {
						if (e.getDamager().getType().equals(EntityType.PLAYER)) {
							
						}else {
							Entity damager = e.getDamager();
					        if (damager instanceof Projectile) {
					            Projectile projectile = (Projectile) damager;
					            if (projectile.getShooter() instanceof Player) {
					                Player ppl = (Player) projectile.getShooter();
					                if (ppl.isFlying()) {
										ppl.setFireTicks(50);
										ppl.setFoodLevel(0);
										ppl.setHealth(10);
										ppl.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 1));
										ppl.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 50, 1));
										ppl.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 50, 1));
									}
					                
					            }
					        }else {
							return;
					        }
						}
						Entity plp = e.getDamager();
						Player ppl = Bukkit.getPlayer(plp.getUniqueId());
						if (ppl.isFlying()) {
							ppl.setFireTicks(50);
							ppl.setFoodLevel(0);
							ppl.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 1));
							ppl.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 50, 1));
							ppl.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 50, 1));
						}
					}else {
						return;
					}
					
					boss = (LivingEntity) e.getEntity();
					
					if (boss.getMaxHealth() == 1800) {
						Location l = e.getEntity().getLocation();
						
						int a = random(1,85);
						int b = random(1,65);
						
						Location ldl = e.getDamager().getLocation();
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.75) {
							if (bob_1) {
								
							}else {
								bob_1 = true;
								World w = e.getEntity().getLocation().getWorld();
								ldl = e.getDamager().getLocation();
								//R
								Location playerLoc = e.getDamager().getLocation();
								TNTPrimed tnt = playerLoc.getWorld().spawn(playerLoc, TNTPrimed.class);
								tnt.setFuseTicks(30);
								tnt.setIsIncendiary(true);
								tnt.setYield(0);
								
								if (e.getDamager().getType().equals(EntityType.PLAYER)) {
									Entity plp = e.getDamager();
									plp.setFireTicks(55);
								}
								
								w.getBlockAt(ldl).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -121, 119, 145)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -122, 119, 145)).setType(Material.OBSIDIAN);
								
								//w.getBlockAt(new Location(w, -120, 119, 144)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -121, 119, 144)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -122, 119, 144)).setType(Material.OBSIDIAN);
								
								//w.getBlockAt(new Location(w, -120, 119, 145)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -120, 119, 145)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -120, 119, 145)).setType(Material.OBSIDIAN);
								
							}
						}
						
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.55) {
							if (bob_2) {
								
							}else {
								bob_2 = true;
								World w = e.getEntity().getLocation().getWorld();
								//R
								ldl = e.getDamager().getLocation();
								Location playerLoc = e.getDamager().getLocation();
								TNTPrimed tnt = playerLoc.getWorld().spawn(playerLoc, TNTPrimed.class);
								tnt.setFuseTicks(30);
								tnt.setIsIncendiary(true);
								tnt.setYield(0);
								
								if (e.getDamager().getType().equals(EntityType.PLAYER)) {
									Entity plp = e.getDamager();
									Player ppl = Bukkit.getPlayer(plp.getName());
									ppl.setHealth((ppl.getMaxHealth()*0.1));
								}
								
								w.getBlockAt(ldl).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -121, 119, 145)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -122, 119, 145)).setType(Material.LAVA);
								
								//w.getBlockAt(new Location(w, -120, 119, 144)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -121, 119, 144)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -122, 119, 144)).setType(Material.LAVA);
								
								//w.getBlockAt(new Location(w, -120, 119, 143)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -121, 119, 143)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -122, 119, 143)).setType(Material.LAVA);
							}
						}
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.50) {
							if (bob_3) {
								
							}else {
								bob_3 = true;
								ldl = e.getDamager().getLocation();
								World w = e.getEntity().getLocation().getWorld();
								//L
								Location playerLoc = e.getDamager().getLocation();
								TNTPrimed tnt = playerLoc.getWorld().spawn(playerLoc, TNTPrimed.class);
								tnt.setFuseTicks(30);
								tnt.setIsIncendiary(true);
								tnt.setYield(0);
								
								if (e.getDamager().getType().equals(EntityType.PLAYER)) {
									Entity plp = e.getDamager();
									Player ppl = Bukkit.getPlayer(plp.getName());
									ppl.setHealth((ppl.getMaxHealth()*0.22));
								}
								
								w.getBlockAt(ldl).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -121, 119, 130)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -122, 119, 130)).setType(Material.OBSIDIAN);
								
								//w.getBlockAt(new Location(w, -120, 119, 129)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -121, 119, 129)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -122, 119, 129)).setType(Material.OBSIDIAN);
								
								//w.getBlockAt(new Location(w, -120, 119, 128)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -121, 119, 128)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -122, 119, 129)).setType(Material.OBSIDIAN);
							}
						}
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.45) {
							if (bob_4) {
								
							}else {
								bob_4 = true;
								World w = e.getEntity().getLocation().getWorld();
								//R
								ldl = e.getDamager().getLocation();
								Location playerLoc = e.getDamager().getLocation();
								TNTPrimed tnt = playerLoc.getWorld().spawn(playerLoc, TNTPrimed.class);
								tnt.setFuseTicks(30);
								tnt.setIsIncendiary(true);
								tnt.setYield(0);
								
								if (e.getDamager().getType().equals(EntityType.PLAYER)) {
									Entity plp = e.getDamager();
									Player ppl = Bukkit.getPlayer(plp.getName());
									ppl.setHealth((ppl.getMaxHealth()*0.30));
									ppl.setFoodLevel(0);
								}
								
								w.getBlockAt(ldl).setType(Material.OBSIDIAN);
								spawnBob(ldl);
								//w.getBlockAt(new Location(w, -121, 119, 145)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -122, 119, 145)).setType(Material.OBSIDIAN);
								
								//w.getBlockAt(new Location(w, -120, 119, 144)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -121, 119, 144)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -122, 119, 144)).setType(Material.OBSIDIAN);
								
								//w.getBlockAt(new Location(w, -120, 119, 143)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -121, 119, 143)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -122, 119, 143)).setType(Material.OBSIDIAN);
							}
						}
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.43) {
							if (bob_5) {
								
							}else {
								bob_5 = true;
								ldl = e.getDamager().getLocation();
								World w = e.getEntity().getLocation().getWorld();
								//L
								Location playerLoc = e.getDamager().getLocation();
								TNTPrimed tnt = playerLoc.getWorld().spawn(playerLoc, TNTPrimed.class);
								tnt.setFuseTicks(30);
								tnt.setIsIncendiary(true);
								tnt.setYield(0);
								
								if (e.getDamager().getType().equals(EntityType.PLAYER)) {
									Entity plp = e.getDamager();
									Player ppl = Bukkit.getPlayer(plp.getName());
									ppl.setHealth((ppl.getMaxHealth()*0.15));
									ppl.setFoodLevel(0);
								}
								
								w.getBlockAt(ldl).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -121, 119, 130)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -122, 119, 130)).setType(Material.LAVA);
								
								//w.getBlockAt(new Location(w, -120, 119, 129)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -121, 119, 129)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -122, 119, 129)).setType(Material.LAVA);
								
								//w.getBlockAt(new Location(w, -120, 119, 128)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -121, 119, 128)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -122, 119, 129)).setType(Material.LAVA);
							}
						}
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.40) {
							if (bob_6) {
								
							}else {
								bob_6 = true;
								ldl = e.getDamager().getLocation();
								World w = e.getEntity().getLocation().getWorld();
								//L
								Location playerLoc = e.getDamager().getLocation();
								TNTPrimed tnt = playerLoc.getWorld().spawn(ldl, TNTPrimed.class);
								tnt.setFuseTicks(30);
								tnt.setIsIncendiary(true);
								tnt.setYield(0);
								
								if (e.getDamager().getType().equals(EntityType.PLAYER)) {
									Entity plp = e.getDamager();
									Player ppl = Bukkit.getPlayer(plp.getName());
									ppl.setHealth((ppl.getMaxHealth()*0.8));
									ppl.setFoodLevel(0);
								}
								
								w.getBlockAt(ldl).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -121, 119, 130)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -122, 119, 130)).setType(Material.OBSIDIAN);
								
								//w.getBlockAt(new Location(w, -120, 119, 129)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -121, 119, 129)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -122, 119, 129)).setType(Material.OBSIDIAN);
								
								//w.getBlockAt(new Location(w, -120, 119, 128)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -121, 119, 128)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -122, 119, 129)).setType(Material.OBSIDIAN);
								
								//w.getBlockAt(new Location(w, -120, 119, 145)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -121, 119, 145)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -122, 119, 145)).setType(Material.OBSIDIAN);
								
								//w.getBlockAt(new Location(w, -120, 119, 144)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -121, 119, 144)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -122, 119, 144)).setType(Material.OBSIDIAN);
								
								//w.getBlockAt(new Location(w, -120, 119, 143)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -121, 119, 143)).setType(Material.OBSIDIAN);
								//w.getBlockAt(new Location(w, -122, 119, 143)).setType(Material.OBSIDIAN);
							}
						}
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.30) {
							if (bob_7) {
								
							}else {
								bob_7 = true;
								ldl = e.getDamager().getLocation();
								World w = e.getEntity().getLocation().getWorld();
								//L
								Location playerLoc = e.getDamager().getLocation();
								TNTPrimed tnt = playerLoc.getWorld().spawn(ldl, TNTPrimed.class);
								tnt.setFuseTicks(30);
								tnt.setIsIncendiary(true);
								tnt.setYield(0);
								
								if (e.getEntity().getLastDamageCause().getEntityType().equals(EntityType.PLAYER)) {
									Entity plp = e.getEntity().getLastDamageCause().getEntity();
									Player ppl = Bukkit.getPlayer(plp.getName());
									if (ppl.isFlying()) {
										ppl.setFireTicks(50);
									}
								}
								
								w.getBlockAt(ldl).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -121, 119, 130)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -122, 119, 130)).setType(Material.LAVA);
								
								//w.getBlockAt(new Location(w, -120, 119, 129)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -121, 119, 129)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -122, 119, 129)).setType(Material.LAVA);
								
								//w.getBlockAt(new Location(w, -120, 119, 128)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -121, 119, 128)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -122, 119, 129)).setType(Material.LAVA);
								
								//w.getBlockAt(new Location(w, -120, 119, 145)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -121, 119, 145)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -122, 119, 145)).setType(Material.LAVA);
								
								//w.getBlockAt(new Location(w, -120, 119, 144)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -121, 119, 144)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -122, 119, 144)).setType(Material.LAVA);
								
								//w.getBlockAt(new Location(w, -120, 119, 143)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -121, 119, 143)).setType(Material.LAVA);
								//w.getBlockAt(new Location(w, -122, 119, 143)).setType(Material.LAVA);
							}
						}
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.18) {
							if (bob_8) {
								
							}else {
								bob_8 = true;
								ldl = e.getDamager().getLocation();
								World w = e.getEntity().getLocation().getWorld();
								//L
								Location playerLoc = e.getDamager().getLocation();
								TNTPrimed tnt = playerLoc.getWorld().spawn(ldl, TNTPrimed.class);
								tnt.setFuseTicks(30);
								tnt.setIsIncendiary(true);
								tnt.setYield(0);
								
								w.getBlockAt(new Location(w, -120, 119, 130)).setType(Material.AIR);
								//w.getBlockAt(new Location(w, -121, 119, 130)).setType(Material.AIR);
								//w.getBlockAt(new Location(w, -122, 119, 130)).setType(Material.AIR);
								
								//w.getBlockAt(new Location(w, -120, 119, 129)).setType(Material.AIR);
								//w.getBlockAt(new Location(w, -121, 119, 129)).setType(Material.AIR);
								//w.getBlockAt(new Location(w, -122, 119, 129)).setType(Material.AIR);
								
								//w.getBlockAt(new Location(w, -120, 119, 128)).setType(Material.AIR);
								//w.getBlockAt(new Location(w, -121, 119, 128)).setType(Material.AIR);
								//w.getBlockAt(new Location(w, -122, 119, 129)).setType(Material.AIR);
								
								//w.getBlockAt(new Location(w, -120, 119, 145)).setType(Material.AIR);
								//w.getBlockAt(new Location(w, -121, 119, 145)).setType(Material.AIR);
								//w.getBlockAt(new Location(w, -122, 119, 145)).setType(Material.AIR);
								
								//w.getBlockAt(new Location(w, -120, 119, 144)).setType(Material.AIR);
								//w.getBlockAt(new Location(w, -121, 119, 144)).setType(Material.AIR);
								//w.getBlockAt(new Location(w, -122, 119, 144)).setType(Material.AIR);
								
								//w.getBlockAt(new Location(w, -120, 119, 143)).setType(Material.AIR);
								//w.getBlockAt(new Location(w, -121, 119, 143)).setType(Material.AIR);
								//w.getBlockAt(new Location(w, -122, 119, 143)).setType(Material.AIR);
							}
						}
						
						
						
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.6) {
							
							 a = random(6,85);
							 b = random(1,95);
							
							if (a == b) {
								spawnJoe(l);
							}
							
							if (b == 15) {
								spawnFred(l);
							}
							
							if (a == 6) {
								LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add3 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add4 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add5 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
							}
							
							if (b == 9) {
								LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add3 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add4 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add5 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
							}
							
							if (b == 20) {
								LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
								LivingEntity add3 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
								LivingEntity add4 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
								LivingEntity add5 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
							}
							
							if (a == 21) {
								LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
								LivingEntity add3 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
								LivingEntity add4 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
								LivingEntity add5 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
							}
							
							
						}
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.5) {
							
							 a = random(1,55);
							 b = random(1,35);
							
							if (a == b) {
								spawnJoe(l);
							}
							
							if (b == 15) {
								spawnFred(l);
							}
							
							if (a == 6) {
								LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add3 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add4 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add5 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
							}
							
							if (b == 9) {
								LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add3 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add4 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add5 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
							}
							
							if (b == 20) {
								LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
								LivingEntity add3 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
								LivingEntity add4 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
								LivingEntity add5 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
							}
							
							if (a == 21) {
								LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
								LivingEntity add3 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
								LivingEntity add4 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
								LivingEntity add5 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
							}
							
							
						}
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.1) {
							a = random(1,85);
							b = random(1,65);
							if (a==b) {
								Location playerLoc = boss.getLastDamageCause().getEntity().getLocation();
								TNTPrimed tnt = playerLoc.getWorld().spawn(playerLoc, TNTPrimed.class);
								tnt.setFuseTicks(30);
								tnt.setIsIncendiary(true);
								tnt.setYield(0);
							}
							LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
						
							if (boss.getHealth() / boss.getMaxHealth() <= 0.02) {
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
							}
						}
					}
					
				}
			}
			
		}
		
	    private boolean bobIsDead = false;
	    @EventHandler
	    public void onEntityDeath(EntityDeathEvent event) {
	    	
	    	if (event.getEntity().getKiller() == null) {
	    		return;
	    	}
	    	
	        if (event.getEntity().getType() == EntityType.WITHER_SKELETON) {
	            LivingEntity killed = event.getEntity();
	           if (killed.getLocation().getWorld().getName().toString().equalsIgnoreCase("arena")) {
	                bobIsDead = true;
	                
	                
	    			Party targetParty = null;
	    			
	    			boolean partyExists = false;
	    			
	    			for (Party pp : pts) {
	    				if (pp.getName().equalsIgnoreCase(event.getEntity().getKiller().getName())) {
	    					partyExists = true;
	    					targetParty = pp;
	    				}
	    			}
	    			
	    			Player k = event.getEntity().getKiller();
	    			int basereward = 75000;
	    			
	    			if (!config.getBoolean("PlayerData."+k.getName()+".BossFights.Bob")) {
	    				config.set("PlayerData."+k.getName()+".BossFights.Bob", true);
	    				basereward = 775000;
	    			}
	    			
	    			
	    			
	    			if (partyExists) {
	    				
	    				int reward = basereward/targetParty.getMembers().size();
	    				String Killer = event.getEntity().getKiller().getName();
	    				
	    				for (Player lp : Bukkit.getOnlinePlayers()) {
	    					lp.sendMessage(Killer+"'s party has defeated bob!");
	    					
	    					for (Player ll : targetParty.getMembers()) {
	    						lp.sendMessage(ll+"'s party has defeated bob!");
	    					}
	    				}
	    				
	    				for (Player pl : targetParty.getMembers()) {
	    					econ.deposit(pl.getName(), reward);
	    					config.set("PlayerData."+pl.getName()+".RaidData.Boss.Kills", config.getInt("PlayerData."+pl.getName()+".RaidData.Boss.Kills")+1);
	    					config.set("PlayerData."+pl.getName()+".RaidData.Boss.Bob.Kills", config.getInt("PlayerData."+pl.getName()+".RaidData.Boss.Bob.Kill")+1);
	    					pl.getInventory().addItem(getRandomDrop());
	    					pl.getInventory().addItem(getRandomDrop());
	    					pl.getInventory().addItem(getRandomDrop());
	    					saveConfigs();
	    				}
	    			}else{
	    				econ.deposit(event.getEntity().getKiller().getName(), basereward);
	    				Player pl = Bukkit.getPlayer(event.getEntity().getKiller().getName());
	    				config.set("PlayerData."+pl.getName()+".RaidData.Boss.Kills", config.getInt("PlayerData."+pl.getName()+".RaidData.Boss.Kills")+1);
    					config.set("PlayerData."+pl.getName()+".RaidData.Boss.Bob.Kills", config.getInt("PlayerData."+pl.getName()+".RaidData.Boss.Bob.Kill")+1);
    					pl.getInventory().addItem(getRandomDrop());
    					pl.getInventory().addItem(getRandomDrop());
    					pl.getInventory().addItem(getRandomDrop());
    					pl.getInventory().addItem(getRandomDrop());
    					pl.getInventory().addItem(getRandomDrop());
    					saveConfigs();
	    				
	    			}
	    			
	    			
	            }
	           
	           
	           
	        }
	        
	        Player pl = Bukkit.getPlayer(event.getEntity().getKiller().getName());
	        
	        
	        
	        if (this.getConfig().getInt("PlayerData."+pl.getName()+".QuestData.QuestStage") == 6) {
	        	if (event.getEntity().getCustomName() == null) {
	        		return;
	        	}else if (event.getEntity().getCustomName().equalsIgnoreCase("joe")) {
	        		pl.getInventory().addItem(getQuestPaper("Secovia Employee Log Book Name: Joe"));
	        	}
	        }else if (this.getConfig().getInt("PlayerData."+pl.getName()+".QuestData.QuestStage") == 7) {
	        	if (event.getEntity().getCustomName() == null) {
	        		return;
	        	}else if (event.getEntity().getCustomName().equalsIgnoreCase("Fred")) {
	        		pl.getInventory().addItem(getQuestPaper("Secovia Employee Log Book Name: Fred"));
	        	}
	        }
	        
	    }

		
	    public void createNPC() {
	
	    	Location l = new Location(Bukkit.getWorld("world"),-1008,77,-1603);
	    	Location ll = new Location(Bukkit.getWorld("world"),-1008,78,-1611);
	    	
	    	NPC.Global npc = NPCLib.getInstance().generateGlobalNPC(this, "Mark", l);
	    	
	    	List<String> info = new ArrayList();
	    	info.add("Mark");
	    	info.add("The Stock Broker");
	    	npc.setSkin("eyJ0aW1lc3RhbXAiOjE1ODEyNTg5NzQzNzEsInByb2ZpbGVJZCI6IjczODJkZGZiZTQ4NTQ1NWM4MjVmOTAwZjg4ZmQzMmY4IiwicHJvZmlsZU5hbWUiOiJZYU9PUCIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjhkNGQwZjdmYmIyY2MwYWNjMDI4MThiZDhkZjA1NTk0NjU3Mzg4NjVmZDg2M2FjMTc1M2ViNGVmZDJhMjgxOSJ9fX0=", "DEucePZDjL5QUaq0Dx9s5Wn1HHxuyREZpy4Lt1h17TMGsz2uYxfwR9o/oUNhCxQoq5sqQ7JlT5p/DdpbaK0jALAeUXxhWBgLOzII0jIx1EPqVhgSSJmvyx+YZfj5lF6eAhN1WgaPPDSzf5klJ8/5lf9JzZJ64uMLWY5wf2rtCVejHTeZMj+iujTwURPpWPcLRC1FziYSvwyHQAi7U8ZfZoBv2gd/CObXFjzURkFrQA1Py4D3NI7kT1P1sN+HaMdPOHMS/QtdurTP3N5ZCkZnyQH0aEd6BUj9WzsRrMlIdj6KjWrkzVgbXItuUIDoSfGSFVoUJzWgFR+edWmaurWhtO/Z4rIkc57b4H6uPvwZeMEfuEH+105aIVZer99d9TRqU2VGTooangpM6ycsndSweJxOrKblVVgOaoqaj/EEu+UAKZePsWBZYl2FQ3qN/lOf/Ux9vE2kUfZNyOiSpiCeid3CDqzkiliOizSyXCSsOYSVVBgwyeEOD2RA30PZ4idxbKTK6aGczkuJYfmITLpEVcUr8C8xJHGrCc3LILii4eVC9gfbpIQ+xpAOfCcjcZolSYI+Oc2YvO2Q40VTkMf746syDoTjWvffEfaO44Znx2ToMCcw5tB0aaT1u/66VugpcXqXHnSab/5ZE+bgha3DdwE7B9eLfOStcQJQwxVYL9E=");
	    	npc.lookAt(ll);
	    	npc.setText(info);
	    	npc.setCollidable(true);
	    	
	    	npc.show();
	    	npc.setAutoShow(true);
	    	
	    	createBanker();
	    }
	    
	    public void createBanker() {
	    	Location l = new Location(Bukkit.getWorld("world"),-1006,77,-1603);
	    	Location ll = new Location(Bukkit.getWorld("world"),-1006,78,-1611);
	    	NPC.Global npc = NPCLib.getInstance().generateGlobalNPC(this, "Jasmine", l);
	    	
	    	List<String> info = new ArrayList();
	    	info.add("Jasmine");
	    	info.add("The Banker");
	    	npc.setSkin("ewogICJ0aW1lc3RhbXAiIDogMTYwMTUzNjg5MjUzNSwKICAicHJvZmlsZUlkIiA6ICI2Njg5MDJmYjI1YTY0NDBhODBmM2Y2MjZhYTk0MzBmYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJCYW5rZXIiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmZkODNhY2NhOWFmM2JiYWQ3MDVmNzE0MzU1ZDk0MTA3NDEyY2E0ZWJiZDRjZTkzOTE2MGMxYmUxMGNjZDFhMiIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9LAogICAgIkNBUEUiIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2U3ZGZlYTE2ZGM4M2M5N2RmMDFhMTJmYWJiZDEyMTYzNTljMGNkMGVhNDJmOTk5OWI2ZTk3YzU4NDk2M2U5ODAiCiAgICB9CiAgfQp9", "bDx+lnoFu4H3MAq94IMUZYYRkhuMtjQu1fYC1qKINGLr8Iff+PIMedctrAKPbFjwYsNqoMQi6SR72ScV3Ehh9A/luNYPoOUIE91SMWmA8tTM7HWt/s6QzvZ24jZUsMjqabFff9mxLQfoyu577c5CUF3qlWryZzS2QXUFSPwgnE/viXlB4WIi5Wrl0UVLPZplgW8EPRPXQ6VdZ3FPWhlKelU5PKPAS1qNLuQy42jnAN7b3H2sGI/yyFVqa0V7eKX6LbGSp++6ZBUqznOvu49R1gMAVK1RcpZTaBaHQm8Qndz+bQWTnqcKUlhIWAINkdkcRQD4aa0J3PPv8B5lK0YAyNw3iK5PR9CN70WUxtEnz5dUqT/Kk7cIj4VA4XQ8Byu9/sQqm48FBuu5KVeqjtgtOjM3zbweRdesHss/dSh/MjrOFpO5rCQnTu30f44ovB3mCgxcO5ygDqWCmm05wvdFq7MYf95AnItSTNEYxDRF38YoJc52/CdLRobvozga75h+eUHhWaUSHIL21i1fRzUMsFDJOOmCLf96nif1xsXPH0hFitYvIlB76YR2ouT2DcJX0fQaQinn1omHuQde31YrEx9L488mFSSfZH11WeobSQX0RnpdGsYExNP2X9L7v3Ls+NvUK8aZkOB/VBipEOhhHqkCvz2o/SS8rMKRDxmqA9M=");
	    	
	    	npc.setText(info);
	    	npc.setCollidable(true);
	    	npc.lookAt(ll);
	    	npc.show();
	    	npc.setAutoShow(true);
	    	createTutorial();
	    }
	    
	    public void createTutorial() {
	    	Location l = new Location(Bukkit.getWorld("world"),-935,73,-1541);
	    	
	    	NPC.Global npc = NPCLib.getInstance().generateGlobalNPC(this, "Walter", l);
	    	
	    	List<String> info = new ArrayList();
	    	info.add("Walter");
	    	info.add("The Welcomer");
	    	npc.setText(info);
	    	npc.setCollidable(true);
	    	
	    	npc.show();
	    	npc.setAutoShow(true);
	    	createQuestNPC();
	    }
	    
	    public void createQuestNPC() {
	    	Location l = new Location(Bukkit.getWorld("world"),-1000,77,-1601);
	    	Location ll = new Location(Bukkit.getWorld("world"),-1000,77,-1610);
	    	
	    	NPC.Global npc = NPCLib.getInstance().generateGlobalNPC(this, "jim", l);
	    	
	    	List<String> info = new ArrayList();
	    	info.add("Jim");
	    	info.add("The Elder");
	    	npc.setText(info);
	    	npc.setSkin("ewogICJ0aW1lc3RhbXAiIDogMTU5NTg3NTg4Nzk3NiwKICAicHJvZmlsZUlkIiA6ICI3MmNiMDYyMWU1MTA0MDdjOWRlMDA1OTRmNjAxNTIyZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJNb3M5OTAiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTY2NTkxZmMzNDkwOWQzYmY0MjZmNWQzMjExMGI4Nzg5YmM2ZmI3MTY4ZGI4OTc0MTJkZjNiZjU5MDFhZGY2NiIKICAgIH0KICB9Cn0=","jMEM4FBcyp+gLHrchP1RO5hhvR20A9gfsO0gxeBIfn4fEef/tybWXgtomDT6IOp7xynDQd3fXiCatZnodEtbPOKhjcErPfhxOT1VgMsQC+Q0rO0c/cCA+nJfu7tFT4Wips6v/1ThgGHw9YLl+4D+5joRyMOVMj+peuOgDSFfnGe5gRdosAVuqV8GfBCcg70EfK6YE+Uy9RcL/PdmKRRoYWPDDk1JSe3YjysU7wEbpkTUQ5ogDsl8SJCO9bk4b1DJn2j9nI3abTYFL9RtQo8NYFKR2B9fsP8S9W50/KNdVpTxqZPpuD2ob3yM9H8jblVR0nwZGCJWAEP/QozTWjFdqarlFhT5YWWJQ+md5wAbCYdZ8gDVWsrJWm93TTLs0yp2Rfo6DYEajugrtJ8th5tclpS8DIzluMCZEO7ZKB7NcxtsUt9q4vr7LIJqEWvrg2X3SuLkVl2Py3Voq3YIoJhUDV8OXpy0v/YyN+nVlgGOxfD4nqNIFWoouXxhVo47COz4ntXdKmCPE9fgM+01qJFEEVguqA7j9uOc70JpQDNb/FWofm6aE6usyKQeTAjOYnXph4oiD5QXJ1Te/40eXki/N0pHJ8MfS70CrLDtyr43NDnS4fs8BcGNF2zfa0Y5GAstMVsOi5/6gu/AQ12f/zDrwweTFBlw4TJqmioW//h53kg=");
	    	npc.setCollidable(true);
	    	npc.lookAt(ll);
	    	npc.show();
	    	npc.setAutoShow(true);
	    	
	    	
	    	
	    	
	    	Location l1 = new Location(Bukkit.getWorld("world"),-992,83,-1608);
	    	Location ll1 = new Location(Bukkit.getWorld("world"),-1013,83,-1608);
	    	
	    	NPC.Global npc1 = NPCLib.getInstance().generateGlobalNPC(this, "mike", l1);
	    	
	    	List<String> info1 = new ArrayList();
	    	info1.add("Mike");
	    	info1.add("The Investigator");
	    	npc1.setText(info1);
	    	npc1.setCollidable(true);
	    	npc1.setSkin("ewogICJ0aW1lc3RhbXAiIDogMTYzMzIwNjc3NzkxNiwKICAicHJvZmlsZUlkIiA6ICIwNjNhMTc2Y2RkMTU0ODRiYjU1MjRhNjQyMGM1YjdhNCIsCiAgInByb2ZpbGVOYW1lIiA6ICJkYXZpcGF0dXJ5IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzU3NjYxOWFkMjg5MzU1MjMzMDNjNzU1OTQ3NGRhMTJlMmU0OTBlMzI5MjMyZjM1NTVkYTU0MTYwN2EwYjQ3NWIiCiAgICB9CiAgfQp9", "vNV4DiOPIEM9As5pPrAJlRGvMH04Te4oqZ+5u1XhiErFvYSzdH2SIx/DIlizuOxLkvkPE0by2E2K97wge8/vxgqP8m7SeCIb2m6YF0/5bXlGjdw2BIBG49zunMo4BFiTcvYPk0CrKafPhCrVo/RUs4QqmO1nEXQXVSJ7m6va94XxnETF4H9IpWuZeAoA7Go6g2NqcVhJoD6LJoB+E38yFXQ96T7PQg2+qUvKMvvPRonq+EtqQKh8qqie7SH7GGk/mUaGDMoJuQi9IAFJm/u0RQSfTjEcp2/8FPY5KMSevx44XPMu3AJRwRoujxS0uiXa/0drdZLLbN10azZer0tgdzZTLX7emGVrBmsJXnKAgrBhS+Wuf7JIq4hkOAetD29pnj0hz77cuDiy1HGaohmTtR5TbH98qMqBFk31p83jYoFPmzoqrTRBxDCM+zboClCNMZk3wNs4njxO2SMaeLKWt4u4/8myAbf1LYf22SeZWokn9oEJsRlE3G8x3k5KFV+cYWmao4HFaBZIzYrPIxB/7udCUBnOYuMS9TyYAOLwa6CvHxL+TAA1tMiC7++dA2M2bnsa6f+DFo/DjmP7GIFKjCiitxInJ64PviwrexLYJSqL9tmeC1JlR0zUqkNOpbWeT5rFBdX6dZ91O4SD/S9i6J82eU96EVHHnCMBhuMLBcY=");
	    	npc1.lookAt(ll1);
	    	npc1.show();
	    	npc1.setAutoShow(true);
	    	
	    }
	    
	    public void spawnJohn(Player p) {
	    	Location l = new Location(Bukkit.getWorld("world"),-817,29,-1501);
	    	
	    	NPC.Personal npc = NPCLib.getInstance().generatePersonalNPC(p, this, "john", l);
	    	
	    	List<String> info = new ArrayList();
	    	info.add("John");
	    	npc.setText(info);
	    	npc.setCollidable(true);
	    	
	    	npc.create();
	    	npc.show();
	    	
	    }
	    
	    public void spawnNPC(String Name, String Title, Location loc) {
	    	Location l = loc;
	    	
	    	NPC.Global npc = NPCLib.getInstance().generateGlobalNPC(this, Name, l);
	    	
	    	List<String> info = new ArrayList();
	    	info.add(Name);
	    	info.add(Title);
	    	npc.setText(info);
	    	npc.setCollidable(true);
	    	
	    	npc.show();
	    	npc.setAutoShow(true);
	    	
	    	
	    }
	    @EventHandler
	    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
	        Player player = event.getPlayer();
	        Entity entity = event.getRightClicked();
	        
	        if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.RAW_GOLD)) {
	        	if (entity.getType().equals(EntityType.ZOMBIE)) {
	        		player.getInventory().addItem(new ItemStack(Material.ZOMBIE_SPAWN_EGG,1));
	        		player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount()-1);
	        		entity.remove();
	        	}else if (entity.getType().equals(EntityType.SKELETON)) {
	        		player.getInventory().addItem(new ItemStack(Material.SKELETON_SPAWN_EGG,1));
	        		player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount()-1);
	        		entity.remove();
	        	}else if (entity.getType().equals(EntityType.SLIME)) {
	        		player.getInventory().addItem(new ItemStack(Material.SLIME_SPAWN_EGG,1));
	        		player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount()-1);
	        		entity.remove();
	        	}else if (entity.getType().equals(EntityType.BLAZE)) {
	        		player.getInventory().addItem(new ItemStack(Material.BLAZE_SPAWN_EGG,1));
	        		player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount()-1);
	        		entity.remove();
	        	}else if (entity.getType().equals(EntityType.WOLF)) {
	        		player.getInventory().addItem(new ItemStack(Material.WOLF_SPAWN_EGG,1));
	        		player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount()-1);
	        		entity.remove();
	        	}else if (entity.getType().equals(EntityType.WITHER_SKELETON)) {
	        		player.getInventory().addItem(new ItemStack(Material.WITHER_SKELETON_SPAWN_EGG,1));
	        		player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount()-1);
	        		entity.remove();
	        	}else if (entity.getType().equals(EntityType.AXOLOTL)) {
	        		player.getInventory().addItem(new ItemStack(Material.AXOLOTL_SPAWN_EGG,1));
	        		player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount()-1);
	        		entity.remove();
	        	}else if (entity.getType().equals(EntityType.COW)) {
	        		player.getInventory().addItem(new ItemStack(Material.COW_SPAWN_EGG,1));
	        		player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount()-1);
	        		entity.remove();
	        	}else if (entity.getType().equals(EntityType.CHICKEN)) {
	        		player.getInventory().addItem(new ItemStack(Material.CHICKEN_SPAWN_EGG,1));
	        		player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount()-1);
	        		entity.remove();
	        	}else if (entity.getType().equals(EntityType.SPIDER)) {
	        		player.getInventory().addItem(new ItemStack(Material.SPIDER_SPAWN_EGG,1));
	        		player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount()-1);
	        		entity.remove();
	        	}else if (entity.getType().equals(EntityType.CAVE_SPIDER)) {
	        		player.getInventory().addItem(new ItemStack(Material.CAVE_SPIDER_SPAWN_EGG,1));
	        		player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount()-1);
	        		entity.remove();
	        	}else if (entity.getType().equals(EntityType.CREEPER)) {
	        		player.getInventory().addItem(new ItemStack(Material.CREEPER_SPAWN_EGG,1));
	        		player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount()-1);
	        		entity.remove();
	        	}else if (entity.getType().equals(EntityType.FOX)) {
	        		player.getInventory().addItem(new ItemStack(Material.FOX_SPAWN_EGG,1));
	        		player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount()-1);
	        		entity.remove();
	        	}
	        	
	        }
	        
	        if (entity instanceof Zombie) {
	            Zombie zombie = (Zombie) entity;
	            if(zombie.getCustomName().equals("UnDead_John")){
	            	if (player.getInventory().contains(getQuestPaper("A thick stack of Papers signed John"))) {
	            		return;
	            		//this.getConfig().set("PlayerData."+player.getName()+".QuestData.QuestStage", 4);
	            	}else if (config.getInt("PlayerData."+event.getPlayer().getName()+".QuestData.QuestStage") == 4) {
	            		
	            	
	            		player.sendMessage(prf+"Hey, Its john..... Yes the same john");
	                	player.sendMessage(prf+"They caught me. They heard about our previous incounter");
	                	player.sendMessage(prf+"They have now made me one of their permanent prisoners as they do with any official with rank.");
	                	player.sendMessage(prf+"Here take this to Mike he will know what to do...... I hope.");
	                	player.sendMessage(prf+"Now leave the caves before you also become one of their prisoners till the end of time.");
	                //zombie.remove();
	                	event.getPlayer().getInventory().addItem(getQuestPaper("A thick stack of Papers signed John"));
	            	}
	            }
	        }
	        
	        if (entity.getType().equals(EntityType.WOLF)) {
	        
	        	if (player.getInventory().getItemInMainHand().getType().equals(Material.BEDROCK)) {
	        		LivingEntity le = (LivingEntity) entity;
	        		le.setMaxHealth(800);
	        		le.setHealth(800);
	        		AttributeModifier A = new AttributeModifier(le.getUniqueId(), "generic.attackDamage", 30, AttributeModifier.Operation.ADD_NUMBER);
	        		AttributeModifier AA = new AttributeModifier(le.getUniqueId(), "generic.armor", 30, AttributeModifier.Operation.ADD_NUMBER);
	        		AttributeModifier AAA = new AttributeModifier(le.getUniqueId(), "generic.armor_toughness", 30, AttributeModifier.Operation.ADD_NUMBER);
	    			
	    			le.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
	    			le.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
	    			le.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
	        	}
	        	
	        	 
	        	
	        }
	    }
	    
	    @EventHandler
	    public void onNPCInteract(NPC.Events.Interact event){
	        Player player = event.getPlayer();
	        NPC npc = event.getNPC();
	        NPC.Interact.ClickType clickType = event.getClickType();
	        
	        Bukkit.getLogger().log(Level.INFO, player.getName()+" touched "+npc.getCode());
	        if (npc.getCode().equalsIgnoreCase("stsecon.global_mark")) {
	        	g.openMarketInventory(player);
	        } else
	        
	        if (npc.getCode().equalsIgnoreCase("stsecon.global_walter")) {
	        	String pr = ChatColor.GOLD+"["+ChatColor.GREEN+"Walter"+ChatColor.GOLD+"] "+ChatColor.AQUA;
	        	//player.sendMessage(pr+"Welcome to SecMC buddy I am Walter here to give you the quick and easy way to get started on this server.");
	        	//player.sendMessage(pr+"You see there are many ways to get started here but by far the easiest if your askin me is just walk out of town and get your start that way you dont have to worry about buying your land to get started.");
	        	//player.sendMessage(pr+"Out there outside of city limits you can build freely and sell any of your harvest to the shop keeper or to other players.");
	        	//player.sendMessage(pr+"Now what I did was built a nice little homestead outside of town and just bought the land when I had the money but you could always come back into town and buy a nice slice of land here in the city.");
	        	walterText(player);
	        } else 
	        	
	        	if (npc.getCode().equalsIgnoreCase("stsecon.global_jim")) {
	        	String pr = ChatColor.GOLD+"["+ChatColor.GREEN+"Jim"+ChatColor.GOLD+"] "+ChatColor.AQUA;
	        	
	        	String[] dialogue = {" Could you take this note to investigator Mike Upstairs.", 
	        			"I think he could use the information to get him closer",
	        			"to the 'Black Hand' and the people behind it."};
				playStory(player, pr, dialogue);
	        	
	        	//player.sendMessage(pr+player.getName()+" Could you take this note to investigator Mike over at the PD? I think he could use the information to get him closer to the 'Black Hand' and the people behind it.");
	        	player.getInventory().addItem(getQuestKey("Take this note to mike the investigator"));
	        } else 
	        	
	        	if (npc.getCode().equalsIgnoreCase("stsecon.global_mike")) {
	        		
	        		if (player.getInventory().contains(getQuestKey("Take this note to mike the investigator"))) {
	        				
	        			player.getInventory().remove(getQuestKey("Take this note to mike the investigator"));
	        			
	        				String pr = ChatColor.GOLD+"["+ChatColor.GREEN+"Mike"+ChatColor.GOLD+"] "+ChatColor.AQUA;
	        				
	        				String[] dialogue = {" This...... ",
	        						"This is more information then I have been able to",
	        						" dig up in the last five years.",
	        						" It details many of the activeties I have suspected the Black Hand of for a long time.", 
	        						"It says that one of the old headquarters for them is in the cave right under the city!",
	        						"I dont have authority to be in the cave system", 
	        						"that would take a willing and skilled crafter such as yourself", 
	        						"Could you venture into the cave system under the city", 
	        						"To see if you can find any clues of the black hand down there?"};
	        				playStory(player, pr, dialogue);
	        				
	        				//player.sendMessage(pr+player.getName()+" This...... This is more information then I have been able to dig up in the last five years. It details many of the activeties I have suspected the Black Hand of a long time.");
	        				//player.sendMessage(pr+player.getName()+" It says that one of the old headquarters for them is in the cave right under the city! I dont have authority to be in the cave system that would take a willing and skilled crafter such as yourself. Could you venture into the cave system under the city and see if you can find any clues of the black hand down there?");
	        				player.getInventory().addItem(getQuestKey("Key to the caves, This key allows you to enter the cave system below the city"));
	        				spawnJohn(player);
	        				//getQuestPaper("Business Card: Investigator John, Sector: Secovia Investigations Department, Start Date: 6-21-12 Badge Number: 0621338644")
	        			}else if (player.getInventory().contains(getQuestPaper("Business Card: Investigator John, Sector: Secovia Investigations Department, Start Date: 6-21-12 Badge Number: 0621338644"))) {
	        				
	        				player.getInventory().remove(getQuestPaper("Business Card: Investigator John, Sector: Secovia Investigations Department, Start Date: 6-21-12 Badge Number: 0621338644"));
	        				
	        				String pr = ChatColor.GOLD+"["+ChatColor.GREEN+"Mike"+ChatColor.GOLD+"] "+ChatColor.AQUA;
	        				Bukkit.getLogger().log(Level.INFO, "Setting PlayerData."+player.getName()+".QuestData.JohnsCard.Completed to 1");
	        				Bukkit.getLogger().log(Level.INFO, "PlayerData."+player.getName()+".QuestData.QuestStage to 2");
	        				this.getConfig().set("PlayerData."+player.getName()+".QuestData.JohnsCard.Completed", 1);
	        				Bukkit.getLogger().log(Level.INFO, "PlayerData."+player.getName()+".QuestData.QuestStage to 2");
	        				this.getConfig().set("PlayerData."+player.getName()+".QuestData.QuestStage", 2);
	        				Bukkit.getLogger().log(Level.INFO, "Saving Config");
	        				saveConfigs();
	        				Bukkit.getLogger().log(Level.INFO, "Config Saved, Checking data.");
	        				
	        				setJohnsData(player.getName());
	        				
	        				config = this.getConfig();
	        				String[] dialogue = {"What? He took the key?",
	        						"I've never heard of this john person before",
	        						"It smells fishy to me",
	        						"Especially that he says Only investigators are allowed in the cave", 
	        						"The department has made it clear it is",
	        						"too dangerous for any invistagor to be in the cave system.", 
	        						"I feel like we are on to somthing", 
	        						"Go explore the cave system some more and see if you can find", 
	        						"anything or anyone else down there.",
	        						"Also here is your payment for find this info"};
	        				playStory(player, pr, dialogue);
	        				
	        				econ.deposit(player.getName(), 500);
	        				
	        				List<String> sm = config.getStringList("QuestData.SpawnMinersfor");
	        				sm.add(player.getName());
	        				config.set("QuestData.SpawnMinersfor", sm);
	        				saveConfigs();
	        				//getQuestPaper("Secovia Mining LTD, Meeting Minutes")
	        			}else if (player.getInventory().contains(getQuestPaper("Secovia Mining LTD, Meeting Minutes"))) {
	        				
	        				event.getPlayer().getInventory().remove(getQuestPaper("Secovia Mining LTD, Meeting Minutes"));
	        				config.set("QuestData."+player.getName()+".SecoviaMining.Completed", 1);
	        				this.getConfig().set("PlayerData."+player.getName()+".QuestData.QuestStage", 3);
	        				saveConfigs();
	        				this.getConfig().set("PlayerData."+player.getName()+".QuestData.SecoviaMining.Completed", 1);
	        				saveConfigs();
	        				
	        				String pr = ChatColor.GOLD+"["+ChatColor.GREEN+"Mike"+ChatColor.GOLD+"] "+ChatColor.AQUA;
	        				String[] dialogue = {"You found these in the cave?",
	        						"It apears to be the minutes log of a company meeting",
	        						"from the Secovia Mining company",
	        						"as far I knew that company", 
	        						"had no operations anywhere near here",
	        						"however they went under some time ago", 
	        						"I am not sure why these would be in the cave system", 
	        						"and you said you found what looked like a meeting room?", 
	        						"I wounder if the Black Hand was using this info or......",
	        						"if they were involved with Secovia Mining",
	        						"I will add this to the evidence.",
	        						"Heres you payment for these.",
	        						"See if you can find anything else down there."};
	        				playStory(player, pr, dialogue);
	        				econ.deposit(player.getName(), 700);
	        				
	        				
	        			}else if (player.getInventory().contains(getQuestPaper("Secovia Mining LTD, Managers Log"))) {
	        				
	        				event.getPlayer().getInventory().remove(getQuestPaper("Secovia Mining LTD, Managers Log"));
	        				config.set("QuestData."+player.getName()+".SecoviaMining2.Completed", 1);
	        				this.getConfig().set("PlayerData."+player.getName()+".QuestData.QuestStage", 4);
	        				saveConfigs();
	        				this.getConfig().set("PlayerData."+player.getName()+".QuestData.SecoviaMining.Completed", 1);
	        				saveConfigs();
	        				
	        				String pr = ChatColor.GOLD+"["+ChatColor.GREEN+"Mike"+ChatColor.GOLD+"] "+ChatColor.AQUA;
	        				String[] dialogue = {"More Secovia Mining Papers?",
	        						"it is interesting that all of these are down there.",
	        						"I am not sure if this was",
	        						"a last effort from them to save the company", 
	        						"or if the Black Hand was using these documents",
	        						"for either insider trading", 
	        						"or even as a cover for their own operations", 
	        						"This is some great evidence you are finding", 
	        						"However we are still missing peices", 
	        						"Go see if you can find any more"};
	        				playStory(player, pr, dialogue);
	        				spawnJohn();
	        				sjp.add(player);
	        				
	        				List<String> sjpn = new ArrayList();
	        				for (Player pj : sjp) {
	        					sjpn.add(pj.getName());
	        				}
	        				config.set("SpawnJohnPlayers", sjpn);
	        		    	saveConfigs();
	        				
	        		    	econ.deposit(player.getName(), 810);
	        				
	        				
	        			} else if (player.getInventory().contains(getQuestPaper("A thick stack of Papers signed John"))) {
	        				
	        				event.getPlayer().getInventory().remove(getQuestPaper("A thick stack of Papers signed John"));
	        				config.set("QuestData."+player.getName()+".UnDeadJohn.Completed", 1);
	        				this.getConfig().set("PlayerData."+player.getName()+".QuestData.QuestStage", 5);
	        				saveConfigs();

	        				
	        				String pr = ChatColor.GOLD+"["+ChatColor.GREEN+"Mike"+ChatColor.GOLD+"] "+ChatColor.AQUA;
	        				String[] dialogue = {"What's this? John is a Zombie?",
	        						"Prisoner till end of time?",
	        						"Oh, wow this details many inside dealings",
	        						"of the Black Hand and their tactics", 
	        						"It says that any Members of the Black Hand",
	        						"are Forever members even after death.", 
	        						"It seems the Black Hand is using the Undead world", 
	        						"to make it so they never die out", 
	        						"They have been practicing the UnDead transformations", 
	        						"and even Resurection.", 
	        						"I think this is closely tied to Secovia Mining", 
	        						"In Some way or another.", 
	        						"I think a team of crafters such as you", 
	        						"Should go pay a visit to the old Secovia Mining HQ", 
	        						"It is located at X: 653 Y: 71 Z: -1856"};
	        				playStory(player, pr, dialogue);
	        				sjp.remove(player);
	        				econ.deposit(player.getName(), 950);
	        				
	        				
	        			} else if (player.getInventory().contains(getQuestPaper("Secovia Mining LTD, ROI Data"))) {
	        				
	        				event.getPlayer().getInventory().remove(getQuestPaper("Secovia Mining LTD, ROI Data"));
	        				//config.set("QuestData."+player.getName()+".SecoviaMiningHQ.Completed", 1);
	        				this.getConfig().set("PlayerData."+player.getName()+".QuestData.QuestStage", 6);
	        				saveConfigs();

	        				
	        				String pr = ChatColor.GOLD+"["+ChatColor.GREEN+"Mike"+ChatColor.GOLD+"] "+ChatColor.AQUA;
	        				String[] dialogue = {"Woah, These have alot of very interesting data",
	        						"They show records of the 'Buyout'",
	        						"Secovia Mining was bought out by",
	        						"An anonymous entity", 
	        						"These recors are all from after",
	        						"However..... It semms that some of these", 
	        						"are real and others have been changed", 
	        						"It's almost as if somone was covering up", 
	        						"Some of these transactions..... But,", 
	        						"Why?", 
	        						"Some transactions removed some ammounts changed", 
	        						"Some changed by a couple dollars some by a few thousand.", 
	        						"This seems to look like someone was embezzling", 
	        						"This looks like they were trying to cover it up.", 
	        						"We need to see if we can find anything else like this.", 
	        						"Quickly before they realize you were there."};
	        				playStory(player, pr, dialogue);
	        				//Spawn Joe at 610, 72 -1828
	        				spawnJoe(new Location(Bukkit.getWorld("world"), 610, 72, -1828));
	        				econ.deposit(player.getName(), 950);
	        				
	        				
	        			} else if (player.getInventory().contains(getQuestPaper("Secovia Employee Log Book Name: Joe"))) {
	        				
	        				event.getPlayer().getInventory().remove(getQuestPaper("Secovia Employee Log Book Name: Joe"));
	        				//config.set("QuestData."+player.getName()+".SecoviaJoe.Completed", 1);
	        				this.getConfig().set("PlayerData."+player.getName()+".QuestData.QuestStage", 7);
	        				saveConfigs();

	        				
	        				String pr = ChatColor.GOLD+"["+ChatColor.GREEN+"Mike"+ChatColor.GOLD+"] "+ChatColor.AQUA;
	        				String[] dialogue = {"Whats this an oddly tuff Zombie attacked you", "and dropped this?",
	        						"This is a log of Employee's Activities",
	        						"The guys name was Joe",
	        						"He was an employee for some time", 
	        						"Interesting part is the logs continue after the Buyout",
	        						"and a little bit after an Entry reads 'Elkhurst Lab Visit'", 
	        						"Then the next reads 'UnPaid Sick Leave'", 
	        						"Then it ends", 
	        						"I think we need to investigate this Lab.", 
	        						"Go see if you can find any clues in that lab."};
	        				playStory(player, pr, dialogue);
	        				econ.deposit(player.getName(), 950);
	        				
	        				
	        			} else if (player.getInventory().contains(getQuestPaper("Secovia Employee Log Book Name: Fred"))) {
	        				
	        				event.getPlayer().getInventory().remove(getQuestPaper("Secovia Employee Log Book Name: Fred"));
	        				//config.set("QuestData."+player.getName()+".SecoviaFred.Completed", 1);
	        				this.getConfig().set("PlayerData."+player.getName()+".QuestData.QuestStage", 8);
	        				saveConfigs();

	        				
	        				String pr = ChatColor.GOLD+"["+ChatColor.GREEN+"Mike"+ChatColor.GOLD+"] "+ChatColor.AQUA;
	        				String[] dialogue = {"Whats this an oddly tuff Skeleton attacked you", "and dropped this?",
	        						"This is another log of Employee's Activities",
	        						"The guys name was Fred",
	        						"He was an employee for some time", 
	        						"I guess he was working in a Minerals Lab",
	        						"and later on started in Biology", 
	        						"it details finding including UnDead Transformations!", 
	        						"I think we found the source of these Transformations", 
	        						"I think we need to investigate this Lab. more"};
	        				playStory(player, pr, dialogue);
	        				econ.deposit(player.getName(), 950);
	        				
	        				
	        			}else{
	        				String pr = ChatColor.GOLD+"["+ChatColor.GREEN+"Mike"+ChatColor.GOLD+"] "+ChatColor.AQUA;
	        				String[] dialogue = {"Good day Crafter."};
	        				playStory(player, pr, dialogue);
	        				//player.sendMessage("Good day Crafter.");
	        			}
	        	} else 
		        	
		        	if (npc.getCode().equalsIgnoreCase("stsecon.john")) {
		        		
		        		if (player.getInventory().contains(getQuestKey("Key to the caves, This key allows you to enter the cave system below the city"))) {
	        				
		        			player.getInventory().remove(getQuestKey("Key to the caves, This key allows you to enter the cave system below the city"));
		        			player.getInventory().addItem(getQuestPaper("Business Card: Investigator John, Sector: Secovia Investigations Department, Start Date: 6-21-12 Badge Number: 0621338644"));
		        			String pr = ChatColor.GOLD+"["+ChatColor.GREEN+"John"+ChatColor.GOLD+"] "+ChatColor.AQUA;
			        		String[] dialogue = {"Oh? Hello there", 
				        			"Im john im one of the investigators",
				        			"Do you have the key to be down here?",
				        			"Oh you do. Give me that.", 
				        			"I dont know who gave you this or told you", 
				        			"That you could be down here but,",
				        			"Here take my card and contact me if you find any other clues",
				        			"But, you cant be down here Only investigators can be"};
							playStory(player, pr, dialogue);
		        		}
		        		
		        	}
	        
	    }
	    
	    public void setJohnsData(String Name) {
	    	Bukkit.getLogger().log(Level.INFO, "Setting PlayerData."+Name+".QuestData.JohnsCard.Completed to 1");
			Bukkit.getLogger().log(Level.INFO, "PlayerData."+Name+".QuestData.QuestStage to 2");
			this.getConfig().set("PlayerData."+Name+".QuestData.JohnsCard.Completed", 1);
			Bukkit.getLogger().log(Level.INFO, "PlayerData."+Name+".QuestData.QuestStage to 2");
			this.getConfig().set("PlayerData."+Name+".QuestData.QuestStage", 2);
			Bukkit.getLogger().log(Level.INFO, "Saving Config");
			saveConfigs();
			Bukkit.getLogger().log(Level.INFO, "Config Saved, Checking data.");
			
			if (config.getInt("PlayerData."+Name+".QuestData.JohnsCard.Completed") == 1) {
				Bukkit.getLogger().log(Level.INFO, "Save Completed");
			}else {
				Bukkit.getLogger().log(Level.INFO, "Erorr Trying Again");
				setJohnsData(Name);
			}
	    }
	    
	    public void playStory(Player player, String prefix , String[] dialogue) {
	    	 int delay = 6; // delay between messages in seconds
			 String pr = prefix;
			  Plugin pln = this;
			  
			  for (int i = 0; i < dialogue.length; i++) {
				    final int index = i;
			  
			  
				    Bukkit.getScheduler().runTaskLater(this, new Runnable() {
				        public void run() {
				        	
				            player.sendMessage(pr+dialogue[index]);
				        	//ActionBarAPI.getActionBar().sendMessage(player, pr+dialogue[index]);
				            

				        	player.sendTitle(pr, dialogue[index], 20, 20*20, 40);
				        }
				    }, delay * 20 * i);
			  }
	    }
	    
	  public void walterText(Player player) {
		  int delay = 10; // delay between messages in seconds
		  
		  
		  
		  
		  String pr = ChatColor.GOLD+"["+ChatColor.GREEN+"Walter"+ChatColor.GOLD+"] "+ChatColor.AQUA;
		  String[] dialogue = {"Welcome to SecMC buddy I am Walter here to give you ",
				  "the quick and easy way to get started on this server.",
				  "You see there are many ways to get started here but by far",
				  "the easiest if your askin me is just walk out of town and,",
				  "get your start that way you dont have to worry about buying",
				  " your land to get started.",
				  "Out there outside of city limits ",
				  "you can build freely and sell any of your harvest ",
				  "to the shop keeper or to other players.",
				  "Now what I did was built a nice little homestead outside of town ",
				  "I just bought the land when I had the money but,",
				  "you could always come back into town and buy a nice slice of land here in the city.",
				  "Some of the more city folk perfer to ",
				  "find a property for rent here in town and just rent their place",
				  "while they work their way up to buying some land",
				  "This method isn't for me as I perfer to own my stuff but,",
				  "that is an option if you like to go that way", 
				  "Shoot maybe after you buy some land you could buy some extra to rent to new players",
				  "Sounds like there could be an investment oppurtunity there.",
				  "Anywho thats about the limit of my knowledge ",
				  "if you want or need to learn more ",
				  "feel free to go over to the bank and talk to Jasmine.",
				  "she will give you the rundown on the local banking systems", 
				  "Now if your ready to start getting into real estate ",
				  "you can go talk to john in the real estate office",
				  "He will get you the info on how to start that venture.",
				  "Have great adventures friend"};
		  Plugin pln = this;
		  
		  for (int i = 0; i < dialogue.length; i++) {
			    final int index = i;
		  
		  
			    Bukkit.getScheduler().runTaskLater(this, new Runnable() {
			        public void run() {
			        	
			            player.sendMessage(pr+dialogue[index]);
			        	//ActionBarAPI.getActionBar().sendMessage(player, pr+dialogue[index]);
			            

			        	player.sendTitle(pr, dialogue[index], 20, 20*20, 40);
			        }
			    }, delay * 20 * i);
		  }
	  }
	  
	  public ItemStack getQuestKey(String lore) {
		  
		  ItemMeta IM = null;
		  
		  List<String> l = new ArrayList();
		  l.add(lore);
		  ItemStack is = new ItemStack(Material.BONE);
		  IM = is.getItemMeta();
		  IM.setDisplayName("Quest Item");
		  is.setAmount(1);
		  //is.addEnchantment(Enchantment.BINDING_CURSE, 1);
		  IM.setLore(l);
		  is.setItemMeta(IM);
		  
		  
		  return is;
	  }
	  
	  public ItemStack getQuestPaper(String lore) {
		  
		  ItemMeta IM = null;
		  
		  List<String> l = new ArrayList();
		  l.add(lore);
		  ItemStack is = new ItemStack(Material.PAPER);
		  IM = is.getItemMeta();
		  IM.setDisplayName("Quest Item");
		  is.setAmount(1);
		  //is.addEnchantment(Enchantment.BINDING_CURSE, 1);
		  IM.setLore(l);
		  is.setItemMeta(IM);
		  
		  
		  return is;
	  }
	    //sean the miner -794, -12, -1448
	  	//joe the miner -788, -10, -1456
	  
	  public void spawnPlayer( String Name,String inf, Player p, Location L) {
			NPC.Personal npc = NPCLib.getInstance().generatePersonalNPC(p, this, Name, L);
	    	
	    	List<String> info = new ArrayList();
	    	info.add(Name);
	    	info.add(inf);
	    	npc.setText(info);
	    	npc.setCollidable(true);
	    	
	    	npc.create();
	    	npc.show();
		}
	  
	  public NPC getPlayerNPC( String Name,String inf, Player p, Location L) {
			NPC.Personal npc = NPCLib.getInstance().generatePersonalNPC(p, this, Name, L);
	    	
	    	List<String> info = new ArrayList();
	    	info.add(Name);
	    	info.add(inf);
	    	npc.setText(info);
	    	npc.setCollidable(true);
	    	
	    	npc.create();
	    	npc.show();
	    	
	    	return npc;
		}
		
		public void spawnGlobal(String Name,String inf, Location L) {
			NPC.Global npc = NPCLib.getInstance().generateGlobalNPC(this, Name, L);
	    	
	    	List<String> info = new ArrayList();
	    	info.add(Name);
	    	info.add(inf);
	    	npc.setText(info);
	    	npc.setCollidable(true);
	    	
	    	npc.setAutoShow(true);
	    	
		}
		
		
		public ItemStack getRandomDrop() {
			ItemStack IS = new ItemStack(Material.BONE, 1); 
			ItemMeta IM = IS.getItemMeta();
			
			if (random(1,10)==8) {
				IS.setType(Material.ENCHANTED_BOOK);
				IS.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, random(1,15));
			}
			
			if (random(1,100)==69) {
				IS.setType(Material.ENCHANTED_BOOK);
				IS.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, random(1,20));
			}
			
			if (random(1,33)== 24) {
				IS.setType(Material.ENCHANTED_BOOK);
				IS.addEnchantment(Enchantment.DAMAGE_ALL, random(1,15));
			}
			
			if (random(1,33)== 24) {
				IS.setType(Material.ENCHANTED_BOOK);
				IS.addEnchantment(Enchantment.ARROW_DAMAGE, random(1,15));
			}
			
			if (random(1,25)==15) {
				IS = InfusedArmor.RegenNBoots();
			}
			
			if (random(1,25)==3) {
				IS = InfusedArmor.RegenNHelm();
			}
			
			if (random(1,25)==6) {
				IS = InfusedArmor.RegenNPants();
			}
			
			if (random(1,25)==20) {
				IS = InfusedArmor.RegenNChest();
			}
			
			if (random(1,40)==23) {
				IS = InfusedArmor.StrNHelm();
			}
			
			if (random(1,40)==35) {
				IS = InfusedArmor.StrNChest();
			}
			
			if (random(1,40)==7) {
				IS = InfusedArmor.StrNPants();
			}
			
			if (random(1,45)==39) {
				IS = InfusedArmor.StrNBoots();
			}
			
			return IS;
		}
		
		
		
		public String randomName() {
			Names.add("William");
			Names.add("Jeffery");
			Names.add("Robert");
			Names.add("Daniel");
			Names.add("Charles");
			Names.add("Paul");
			Names.add("Pattrick");
			Names.add("Randy");
			Names.add("Larry");
			Names.add("George");
			Names.add("Ronald");
			Names.add("Peter");
			Names.add("Todd");
			Names.add("Gary");
			Names.add("Edward");
			Names.add("Dennis");
			Names.add("Randall");
			Names.add("Steve");
			Names.add("Dean");
			Names.add("Michael");
			Names.add("Thomas");
			Names.add("Richard");
			Names.add("David");
			Names.add("James");
			Names.add("Scott");
			Names.add("Steven");
			Names.add("Brian");
			Names.add("Kenneth");
			Names.add("Christopher");
			
			String Name = Names.get(random2(0,Names.size()));
			
			return Name;
		}
		

		public void saveConfigs() {
			try {
				this.saveConfig();
		    } catch (Exception e) {
		    	  this.getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
		    	}
		}
		
		
		
		public void createProperty(String[] ars, Player p) {
			Player sender = p;
    		Player player = p;
    		String[] args = ars;
    		p.sendMessage(ChatColor.GREEN+"Loading Property......");
		    	Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
		    		
					
					@Override
					public void run() {
						
						
						BukkitPlayer actor = BukkitAdapter.adapt(p);
						SessionManager manager = WorldEdit.getInstance().getSessionManager();
						LocalSession localSession = manager.get(actor);
						
						Region region = null;
						com.sk89q.worldedit.world.World selectionWorld = localSession.getSelectionWorld();
						try {
						    if (selectionWorld == null) throw new IncompleteRegionException();
						    region = localSession.getSelection(selectionWorld);
						} catch (IncompleteRegionException ex) {
						    actor.printError(TextComponent.of("Please make a region selection first."));
						    
						}
						
						int xx = region.getMinimumPoint().getX();
						int yy = region.getMinimumPoint().getY();
						int zz = region.getMinimumPoint().getZ();
						
						int x2 = region.getMaximumPoint().getX();
						int y2 = region.getMaximumPoint().getY();
						int z2 = region.getMaximumPoint().getZ();
						
						double value = getPropertyValue(region, player.getWorld());
						prm = (BlocksSold/30000000)+1;
						value = value*prm;
						BlocksSold = BlocksSold+region.getArea();
						config.set("ServerData.BlocksSold", BlocksSold);
						saveConfig();
						com.sk89q.worldedit.world.World WW = region.getWorld();
						Location LL = new Location(Bukkit.getWorld(WW.getName()),xx,yy,zz);
						Location L2 = new Location(Bukkit.getWorld(WW.getName()),x2,y2,z2);
						if (player.isOp()) {
							Property pr = new Property(args[2], sender.getName(), LL, L2);
							
							if (econ.has(player.getName(), value)) {
								econ.withdraw(player.getName(), value);
								prys.add(pr);
								sender.sendMessage(ChatColor.GREEN+"Created Property "+args[2]+" for $"+price.format(value));
								saveProperty();
								PropertyUpdateEvent pvr = new PropertyUpdateEvent();
					    		Bukkit.getServer().getPluginManager().callEvent(pvr);
					    		updateMap();
					    		////MySQL.syncDB();
							}else {
								player.sendMessage(ChatColor.RED+"You Need $"+price.format(getPropertyValue(region, player.getWorld())));
							}
						}else {
						if (overlapsProperty(player.getWorld(), LL, L2)) {
							
							sender.sendMessage(ChatColor.RED+"That Selection overlaps another Property");
							
							
							
						}else {
							boolean NM = false;
							for (Property PRT : prys) {
								if (PRT.getName().equalsIgnoreCase(args[2])) {
									NM = true;
								}
							}
							
							if (NM) {
								sender.sendMessage(ChatColor.RED+"A property with that name already exists please use a different name.");
							}else {
							Property pr = new Property(args[2], sender.getName(), LL, L2);
							pr.setZone("Res");
							if (econ.has(player.getName(), value)) {
								econ.withdraw(player.getName(), value);
								prys.add(pr);
								sender.sendMessage(ChatColor.GREEN+"Created Property "+args[2]+" for $"+price.format(value));
								saveProperty();
								PropertyUpdateEvent pvr = new PropertyUpdateEvent();
					    		Bukkit.getServer().getPluginManager().callEvent(pvr);
					    		////MySQL.syncDB();
							}else {
								player.sendMessage(ChatColor.RED+"You Need $"+price.format(value));
							}
						}
						}
						}
						
					}
		    	    
		    	} );
		    	
		    	//Bukkit.getScheduler().sched
		    }
}

/*
 * Add Spawn trigger and Boss Spawn Trigger X100 Y19 Z-25
 * Boss 1 Spawn X149 Y23 Z-74 The Abomination
 * Boss Fight 1 Start X130 Y18 Z-55
 * Boss 2 Spawn Trigger 2 X136 Y26 Z-27
 * Boss 2 Spawn X126 Y34 Z24 The Bloodthirsty
 * Boss 2 Fight Start X126 Y33 Z9
 * Boss 3 Spawn X131 Y46 Z-59 The Necromancer
 * Boss 3 Spawn Trigger X124 Y43 Z-26
 * Boss 3 Trigger X119 Y45 Z-33
 * Boss 4 Spawn Trigger X110 Y79 Z-57
 * Boss 4 Trigger X141 Y79 Z-51
 * Boss 4 Spawn X141 Y80 Z-66 The Ghost King
 * 
 */
