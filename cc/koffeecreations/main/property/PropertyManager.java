package cc.koffeecreations.main.property;

import cc.koffeecreations.main.econ.Econ;
import cc.koffeecreations.main.Main;
import cc.koffeecreations.main.events.PropertyUpdateEvent;
import cc.koffeecreations.main.events.propertyValueRequestEvent;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class PropertyManager implements Listener{

	public boolean debug = false;
	
	FileConfiguration config;
	
	public DecimalFormat price = new DecimalFormat("0.00");
	
	List<Property> prys = new ArrayList();
	
	public Econ econ;

	public Main M;
	
	public boolean PropertyEnforce = true;
	
	public PropertyManager() {
		
		
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
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
    		for (String TRP : config.getStringList("PropertyData."+PR+".Trusted")) {
    			PRR.addTrusted(TRP);
    		}
    		
    		prys.add(PRR);
    		
    		//M = Main.getInstance;
    		econ = new Econ();;
    		
    	}
		
	}
	
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		
		Player W = Bukkit.getPlayer("Wintergrasped");
		
		if (debug) {
		W.sendMessage("Block Break Event");
		}
		if (e.getBlock().getLocation().getWorld().getName().equalsIgnoreCase("world_nether")) {
			
		}else{
			if (debug) {
			W.sendMessage("was in RP World");
			}
			if (e.getPlayer().isOp()) {
				if (debug) {
				W.sendMessage("was OP");
				}
			}else{
				if (debug) {
				W.sendMessage("Was Not Op");
				}
				Location L = e.getBlock().getLocation();
				
				
				if (isOnProperty(L)) {
					Property pr = getLocProperty(L);
					
					if (debug) {
						W.sendMessage("Was on property");
						}
					if (debug) {
						W.sendMessage("Property Owner: "+pr.getOwner());
						W.sendMessage("PlayerName: "+e.getPlayer().getName());
						}
					if (pr.getOwner().equalsIgnoreCase(e.getPlayer().getName())) {
						if (debug) {
							W.sendMessage("Was Owner");
							}
						
						
						
						return;
					}else{
						if (debug) {
							W.sendMessage("Was Not On own property");
							}
						if (pr.getTrusted().contains(e.getPlayer().getName())) {
							
						}else{
							if (isCity(e.getPlayer().getLocation())) {
								if (PropertyEnforce) {
									e.setCancelled(true);
									e.getPlayer().sendMessage(ChatColor.RED+"You can only Break on your own property!");
								}
							}
							return;
						}
						
					}
					
				}else {
					if (debug) {
						W.sendMessage("Was Not On property");
						}
					if (isCity(e.getPlayer().getLocation())) {
						if (PropertyEnforce) {
							e.setCancelled(true);
							e.getPlayer().sendMessage(ChatColor.RED+"You can only Break on your own property!");
						}
					}
					return;
				}
				
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		
		Player PL = e.getPlayer();
		
		if (e.getBlock().getLocation().getWorld().getName().equalsIgnoreCase("world_nether")) {
			
		}else{
			
			if (e.getPlayer().isOp()) {
				
				
Location L = e.getBlock().getLocation();
				
				
				if (isOnProperty(L)) {
					Property pr = getLocProperty(L);
					
					if (pr.getOwner().equalsIgnoreCase(e.getPlayer().getName())) {
						
						if (e.getBlock().getType().equals(Material.GOLD_BLOCK)) {
							reloadPrys();
							e.getPlayer().sendMessage("Placed a Buy Command");
							List<String> PropertySales = config.getStringList("PropertyData.PropertySales");
							
							if (pr.getForSale()) {
								
								int price = config.getInt("PropertyData."+pr.getName()+".SalePrice");
								String SaleType = config.getString("PropertyData."+pr.getName()+".SaleType");
								
								if (econ.has(PL.getName(), price)) {
									
									econ.withdraw(PL.getName(), price);
									econ.deposit(pr.getOwner(), price);
									pr.setOwner(PL.getName());
									PL.sendMessage("Bought "+pr.getName());
									pr.setForSale(false);
									config.set("PropertyData."+pr.getName()+".ForSale", false);
									//Main.getInstance.saveConfig();
									//Main.getInstance.syncPRYS(prys);
									//Main.getInstance.Propertysave(pr);
									
									
								}
							
						}
						}
						return;
					}else{
						
						if (pr.getTrusted().contains(e.getPlayer().getName())) {
							
						}else{
							
							return;
						}
						
					}
					
				}else {
				
					return;
				}
				
			}else{
				
				Location L = e.getBlock().getLocation();
				
				
				if (isOnProperty(L)) {
					Property pr = getLocProperty(L);
					
					if (pr.getOwner().equalsIgnoreCase(e.getPlayer().getName())) {
						
						if (e.getBlock().getType().equals(Material.GOLD_BLOCK)) {
							reloadPrys();
							e.getPlayer().sendMessage("Placed a Buy Command");
							List<String> PropertySales = config.getStringList("PropertyData.PropertySales");
							
							if (pr.getForSale()) {
								
								int price = config.getInt("PropertyData."+pr.getName()+".SalePrice");
								String SaleType = config.getString("PropertyData."+pr.getName()+".SaleType");
								
								if (econ.has(PL.getName(), price)) {
									
									econ.withdraw(PL.getName(), price);
									econ.deposit(pr.getOwner(), price);
									pr.setOwner(PL.getName());
									PL.sendMessage("Bought "+pr.getName());
									pr.setForSale(false);
									config.set("PropertyData."+pr.getName()+".ForSale", false);
									try {
										Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
								    } catch (Exception e1) {
								    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e1.getMessage());
								    }
									Main.getInstance.syncPRYS(prys);
									Main.getInstance.Propertysave(pr);
									
									
								}
							
						}
						}
						return;
					}else{
						
						if (pr.getTrusted().contains(e.getPlayer().getName())) {
							
						}else{
							if (PropertyEnforce) {
								e.setCancelled(true);
								e.getPlayer().sendMessage(ChatColor.RED+"You can only Build on your own property!");
								}
							return;
						}
						
					}
					
				}else {
					
					if (isCity(e.getPlayer().getLocation())) {
						if (PropertyEnforce) {
							e.setCancelled(true);
							e.getPlayer().sendMessage(ChatColor.RED+"You can only Build on your own property!");
						}
					}
					return;
				}
				
				
			}
			
		}
		
	}
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		Location L = e.getPlayer().getLocation();
	
		Player PL = e.getPlayer();
		
		if (e.getClickedBlock() == null) {
			return;
		}
		
		if (e.getClickedBlock().getLocation().getWorld().getName().equalsIgnoreCase("world_nether")) {
			
		}else{
			
			if (e.getPlayer().isOp()) {
				
				return;
				
			}else{
				
				
				
				
				if (isOnProperty(L)) {
					Property pr = getLocProperty(L);
					
					if (pr.getOwner().equalsIgnoreCase(e.getPlayer().getName())) {
						
						
						return;
					}else{
						
						if (pr.getTrusted().contains(e.getPlayer().getName())) {
							
						}else{
							if (PropertyEnforce) {
								
								if (e.getClickedBlock().getType().equals(Material.CHEST)) {
									
								} else if (e.getClickedBlock().getType().equals(Material.ENDER_CHEST)) {
									
								} else if (e.getClickedBlock().getType().equals(Material.CRAFTING_TABLE)) {
									
								}else {
								
									e.setCancelled(true);
									//e.getPlayer().sendMessage(ChatColor.RED+"You can only Interact on your own property!");
								}
							}
							return;
						}
						
					}
					
				}else {
					
					if (isCity(e.getPlayer().getLocation())) {
						if (PropertyEnforce) {
							
							if (e.getClickedBlock().getType().equals(Material.CHEST)) {
								
							} else if (e.getClickedBlock().getType().equals(Material.ENDER_CHEST)) {
								
							} else if (e.getClickedBlock().getType().equals(Material.CRAFTING_TABLE)) {
								
							}else {
							
								e.setCancelled(true);
								//e.getPlayer().sendMessage(ChatColor.RED+"You can only Interact on your own property!");
							}
						}
					}
					return;
				}
				
				
			}
			
		}
		
	}

	
	public boolean isOnProperty(Location LL) {
		
		Player W = Bukkit.getPlayer("Wintergrasped");
		for (Property PR : prys) {
			
			Location LMN = PR.getMinLoc();
			Location LMX = PR.getMaxLoc();
			int x = LL.getBlockX();
			int z = LL.getBlockZ();
			
			int lx = LMN.getBlockX();
			int lz = LMN.getBlockZ();
			
			int hx = LMX.getBlockX();
			int hz = LMX.getBlockZ();
			
			if (x > lx) {
				
				if (debug) {
					W.sendMessage("X > LX");
				}
				
				if (z > lz) {
					
					if (debug) {
						W.sendMessage("Z > LZ");
					}
					
					if (x < hx) {
						
						if (debug) {
							W.sendMessage("X < HX");
						}
						
						if (z < hz) {
							
							if (debug) {
								W.sendMessage("Z < HZ");
							}
							
							return true;
						}else {
							if (debug) {
								W.sendMessage("Not Z < HZ");
							}
						}
					}else {
						if (debug) {
							W.sendMessage("Not X < HX");
						}
					}
				}else{
					if (debug) {
						W.sendMessage("Not Z > LZ");
					}
				}
			
			}else {
				if (debug) {
					W.sendMessage("Not X > LX");
				}
			}
			
		}
		
		
		return false;
	}
	
public Property getLocProperty(Location LL) {
	Player W = Bukkit.getPlayer("Wintergrasped");
	reloadPrys();
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
				
				if (debug) {
					W.sendMessage("X > LX");
				}
				
				if (z >= lz) {
					
					if (debug) {
						W.sendMessage("Z > LZ");
					}
					
					if (x <= hx) {
						
						if (debug) {
							W.sendMessage("X < HX");
						}
						
						if (z <= hz) {
							
							if (debug) {
								W.sendMessage("Z < HZ");
								W.sendMessage(PR.getName());
								W.sendMessage(PR.getOwner());
							}
							
							return PR;
						}else {
							if (debug) {
								W.sendMessage("Not Z < HZ");
							}
						}
					}else {
						if (debug) {
							W.sendMessage("Not X < HX");
						}
					}
				}else{
					if (debug) {
						W.sendMessage("Not Z > LZ");
					}
				}
			
			}else {
				if (debug) {
					W.sendMessage("Not X > LX");
				}
			}
			
		}
		
		
		return null;
	}

	public void reloadPrys() {
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
    		for (String TRP : config.getStringList("PropertyData."+PR+".Trusted")) {
    			PRR.addTrusted(TRP);
    		}
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


    		
    		if (config.getBoolean("PropertyData."+PR.getName()+".ForSale")) {
    			config.getInt("PropertyData."+PR.getName()+".Price");
    			config.getString("PropertyData."+PR.getName()+".SaleType");
    		}
    		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
    		reloadPrys();

	}
	
	}
	
	@EventHandler
	public void valueRequest(propertyValueRequestEvent e) {
		
		double v = getPropertyValue(e.getRegion(), e.getPlayer().getWorld());
		
		
		
		price.setGroupingUsed(true);
		price.setGroupingSize(3);
		e.getPlayer().sendMessage(ChatColor.GREEN+"Property Value $"+price.format(v));
		
	}

	@EventHandler
	public void Update(PropertyUpdateEvent e) {
		
		reloadPrys();
		
	}
	
public double getPropertyValue(Region R, World W) {
	
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

public void save() {
	try {
		Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
    } catch (Exception e) {
    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
    }
}

public boolean overlaps(World W, Location Min, Location Max) {
	
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

public boolean isCity(Location L) {
	
	if (!L.getWorld().getName().equals(config.getString("CityData.Center.World"))) {
		return false;
	}
	Location Center = new Location(Bukkit.getWorld(config.getString("CityData.Center.World")), config.getInt("CityData.Center.X"),config.getInt("CityData.Center.Y"),config.getInt("CityData.Center.Z"));
	int Radius = 2000;
	
	if (L.distance(Center) < Radius) {
		return true;
	}else {
		return false;
	}
}

}
