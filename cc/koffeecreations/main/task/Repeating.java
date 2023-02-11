package cc.koffeecreations.main.task;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import cc.koffeecreations.main.*;
import cc.koffeecreations.main.econ.Business;
import cc.koffeecreations.main.econ.Econ;
import cc.koffeecreations.main.events.RentalStopEvent;
import cc.koffeecreations.main.events.payCycleEvent;
import cc.koffeecreations.main.unknow.Eviction;
import cc.koffeecreations.main.unknow.Rental;
import cc.koffeecreations.main.property.Property;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.ChatColor;

public class Repeating {

	
	boolean debug = true;
	
	List<String> prices = new ArrayList();
	List<String> bso = new ArrayList();
	List<Integer> eso = new ArrayList();
	Econ econ = new Econ();
	Main M;
	public Player blp;

	FileConfiguration config = null;
	public DecimalFormat price = new DecimalFormat("0.00");

	public Repeating(Main MM) {
		M = MM;
		bso = M.bso;
		econ = new Econ();
		mainLoop();
		prices = config.getStringList("ServerData.PricedMaterials");
		config = Bukkit.getServer().getPluginManager().getPlugin("STSEcon").getConfig();
		price.setGroupingUsed(true);
		price.setGroupingSize(3);
		
	}

	@SuppressWarnings("deprecation")
	public synchronized void mainLoop() {
		
		//TODO Add Debt Calculations and Missed Payment Counter
		
		config = Bukkit.getServer().getPluginManager().getPlugin("STSEcon").getConfig();
		prices = config.getStringList("ServerData.PricedMaterials");
		
		int S = 0;
		
		if (econ == null) {
			econ = new Econ();
		}
		businessLoop();
		M.plm = 0.003;
		if (Bukkit.getOnlinePlayers().size() >= 1) {
			
			for (Player p : Bukkit.getOnlinePlayers()) {

				int sm = 0;
				
				double pt = 0;
				M.plm = M.plm + 0.003;
				M.giveStock(p.getName(), 1);
				econ.setCashFlow(p.getName(), 0);

				if (config.getString("PlayerData." + p.getName() + ".Employer") == null) {
					econ.deposit(p.getName(), 100);
				}

				for (Property PR : M.prys) {

					if (PR.getOwner().equalsIgnoreCase(p.getName())) {

						BlockVector3 mn = BlockVector3.at(PR.getMinLoc().getBlockX(), PR.getMinLoc().getBlockY(),
						        PR.getMinLoc().getBlockZ());
						BlockVector3 mx = BlockVector3.at(PR.getMaxLoc().getBlockX(), PR.getMaxLoc().getBlockY(),
						        PR.getMaxLoc().getBlockZ());
						World W = PR.getMaxLoc().getWorld();
						Region RR = new CuboidRegion(BukkitAdapter.adapt(W), mn, mx);

						pt = pt + (getPropertyValue(RR, W) * 0.0255);

						if (bso.contains(p.getName())) {
							//Bukkit.getLogger().log(Level.INFO, "Found Business Owner : "+p.getName());
							config = Bukkit.getServer().getPluginManager().getPlugin("STSEcon").getConfig();
							
							if (PR.getZone() == null) {
								PR.setZone(config.getString("PropertyData."+PR.getName()+".Zone"));
							}
							
							if (PR.getZone() == null) {
								PR.setZone("res");
							}
							
							//Bukkit.getLogger().log(Level.INFO, "Found Land : "+PR.getName()+" Zoned IND?: "+PR.isZone("ind"));
							//PR.setZone(config.getString("PropertyData."+PR.getName()+".Zone"));
							if (PR.isZone("ind")) {
								
								//Bukkit.getLogger().log(Level.INFO, "Found Industrial Land : "+PR.getName());
								for (BlockVector3 point : RR) {

									Location L = new Location(W, point.getBlockX(), point.getBlockY(),
									        point.getBlockZ());
									Material MM = L.getBlock().getType();
									Block B = L.getBlock();
									

									if (MM.equals(Material.CHEST)) {
										//Bukkit.getLogger().log(Level.INFO, "Found Chest Industrial Land : "+PR.getName());
										Business BB = getBusiness(p.getName());
										//config.getInt("PlayerData."+sender.getName()+".Business.Storage");
										
										S = S + 1;
										
										//Bukkit.getLogger().log(Level.INFO, "Added Storage Total : "+S);
										sm = sm+1;
										
									} else if (MM.equals(Material.BARREL)) {
										Business BB = getBusiness(p.getName());
										//Bukkit.getLogger().log(Level.INFO, "Found Chest Industrial Land : "+PR.getName());
										
										S = S + 1;
										//config.set("PlayerData." + p.getName() + ".Business.Storage", S);
										//Bukkit.getLogger().log(Level.INFO, "Added Storage Total : "+S);
										sm = sm+1;
										
									} else if (MM.equals(Material.BLACK_BED)) {
										B.breakNaturally();
										
										Entity TNT = B.getWorld().spawnEntity(B.getLocation(), EntityType.PRIMED_TNT);
										TNT.setFireTicks(20);
										
					                  
									} else if (MM.equals(Material.BLUE_BED)) {
										B.breakNaturally();
										Entity TNT = B.getWorld().spawnEntity(B.getLocation(), EntityType.PRIMED_TNT);
										TNT.setFireTicks(20);
									} else if (MM.equals(Material.WHITE_BED)) {
										B.breakNaturally();
										Entity TNT = B.getWorld().spawnEntity(B.getLocation(), EntityType.PRIMED_TNT);
										TNT.setFireTicks(20);
									} else if (MM.equals(Material.RED_BED)) {
										B.breakNaturally();
										Entity TNT = B.getWorld().spawnEntity(B.getLocation(), EntityType.PRIMED_TNT);
										TNT.setFireTicks(20);
									} else if (MM.equals(Material.YELLOW_BED)) {
										B.breakNaturally();
										Entity TNT = B.getWorld().spawnEntity(B.getLocation(), EntityType.PRIMED_TNT);
										TNT.setFireTicks(20);
									} else if (MM.equals(Material.BROWN_BED)) {
										B.breakNaturally();
										Entity TNT = B.getWorld().spawnEntity(B.getLocation(), EntityType.PRIMED_TNT);
										TNT.setFireTicks(20);
									} else if (MM.equals(Material.CYAN_BED)) {
										B.breakNaturally();
										Entity TNT = B.getWorld().spawnEntity(B.getLocation(), EntityType.PRIMED_TNT);
										TNT.setFireTicks(20);
									} else if (MM.equals(Material.GRAY_BED)) {
										B.breakNaturally();
										Entity TNT = B.getWorld().spawnEntity(B.getLocation(), EntityType.PRIMED_TNT);
										TNT.setFireTicks(20);
									} else if (MM.equals(Material.GREEN_BED)) {
										B.breakNaturally();
										Entity TNT = B.getWorld().spawnEntity(B.getLocation(), EntityType.PRIMED_TNT);
										TNT.setFireTicks(20);
									} else if (MM.equals(Material.LIME_BED)) {
										B.breakNaturally();
										Entity TNT = B.getWorld().spawnEntity(B.getLocation(), EntityType.PRIMED_TNT);
										TNT.setFireTicks(20);
									} else if (MM.equals(Material.MAGENTA_BED)) {
										B.breakNaturally();
										Entity TNT = B.getWorld().spawnEntity(B.getLocation(), EntityType.PRIMED_TNT);
										TNT.setFireTicks(20);
									} else if (MM.equals(Material.ORANGE_BED)) {
										B.breakNaturally();
										Entity TNT = B.getWorld().spawnEntity(B.getLocation(), EntityType.PRIMED_TNT);
										TNT.setFireTicks(20);
									} else if (MM.equals(Material.PINK_BED)) {
										B.breakNaturally();
										Entity TNT = B.getWorld().spawnEntity(B.getLocation(), EntityType.PRIMED_TNT);
										TNT.setFireTicks(20);
									} else if (MM.equals(Material.PURPLE_BED)) {
										B.breakNaturally();
										Entity TNT = B.getWorld().spawnEntity(B.getLocation(), EntityType.PRIMED_TNT);
										TNT.setFireTicks(20);
									} else if (MM.equals(Material.LIGHT_BLUE_BED)) {
										B.breakNaturally();
										Entity TNT = B.getWorld().spawnEntity(B.getLocation(), EntityType.PRIMED_TNT);
										TNT.setFireTicks(20);
									} else if (MM.equals(Material.LIGHT_GRAY_BED)) {
										B.breakNaturally();
										Entity TNT = B.getWorld().spawnEntity(B.getLocation(), EntityType.PRIMED_TNT);
										TNT.setFireTicks(20);
									}

								}

							}
						}

					}
					
				}
				
				if (bso.contains(p.getName())) {
					if (sm > 0) {
						int sp = sm*100;
						econ.withdrawPlayer(p.getName(), sp);
						p.sendMessage(ChatColor.GREEN+"You paid "+ChatColor.GOLD+"$"+sp+ChatColor.GREEN+" for indistrial Storage permits");
					}
					config.set("PlayerData." + p.getName() + ".Business.Storage", S);
				}
				econ.withdraw(p.getName(), pt);
				if (pt == 0) {
					
				}else{
				p.sendMessage(ChatColor.GREEN + "You paid " + ChatColor.GOLD + "$" + M.price.format(pt) + ""
				        + ChatColor.GREEN + " Of property tax");
				}
			}
			M.stockCalc();
			rentalLoop();
			M.MySQL.asyncDB();

			//payCycleEvent pce = new payCycleEvent(M.pls, M.bls);
			//Bukkit.getServer().getPluginManager().callEvent(pce);

		} else {

		}
		// plm = Bukkit.getOnlinePlayers().size()*plm;
		M.stockCalc();
		M.randomEvents();
		if (M.loggingPRC <= 0.05) {
			M.loggingPRC = 0.06;
		}

		if (M.miningPRC <= 0.05) {
			M.miningPRC = 0.06;
		}

		if (M.lumberPRC <= 0.05) {
			M.lumberPRC = 0.06;
		}

		if (M.farmingPRC <= 0.05) {
			M.farmingPRC = 0.06;
		}

		if (M.redstonePRC <= 0.05) {
			M.redstonePRC = 0.06;
		}

		// int md = m,mv();

	}

	public synchronized void businessLoop() {
		if (debug) {
			Bukkit.getLogger().log(Level.INFO, "Bso Size"+bso.size());
			for (String b : bso) {
				Bukkit.getLogger().log(Level.INFO, "Bso Item "+b);
			}
		}
		M.bso = config.getStringList("PlayerData.BusinessOwners");
		
			new BusinessTask(M, bso);
		
		
		//new BusinessTask(M, blp).businessLoop();
		
/*
		if (econ == null) {
			econ = new Econ();
		}

		//M.bso = config.getStringList("PlayerData.BusinessOwners");
		M.bls = new ArrayList();
		M.pls = new ArrayList();
		if (debug) {
			Bukkit.getLogger().log(Level.INFO, "Bso Size"+bso.size());
			for (String b : bso) {
				Bukkit.getLogger().log(Level.INFO, "Bso Item "+b);
			}
		}
		for (String b : bso) {
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
			blp = Bukkit.getPlayer(bl.getOwner());
			M.bls.add(bl);
			M.pls.add(Bukkit.getPlayer(b));

			// config.set("PlayerData."+sender.getName()+".Employer", args[1]);
			// config.set("PlayerData."+sender.getName()+".Salary",
			// Integer.parseInt(args[2]));

			// config.set("PlayerData."+sender.getName()+".Employed", pe);

			if (s <= ns) {
				econ.withdrawPlayer(b, 250000);

				if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(b))) {

					Bukkit.getPlayer(b)
					        .sendMessage(M.prf + ChatColor.RED + " Current Supplies: " + s + " Needed Supplies: " + ns);

					Bukkit.getPlayer(b).sendMessage(M.prf + ChatColor.RED
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
							M.saveConfig();
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
							M.saveConfig();
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
						M.saveConfig();

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
							M.saveConfig();

						}

					} else {
						config.set("PlayerData." + b + ".Business.Employees." + es + ".LastTraining", LTraining + 1);
						M.saveConfig();
					}

					if (LTraining >= random(10, 20)) {
						mood = mood - 1;
						config.set("PlayerData." + b + ".Business.Employees." + es + ".Mood", mood);
						M.saveConfig();
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
			
			if (debug) {
				//Bukkit.getLogger().log(Level.INFO, "B "+b);
				//Bukkit.getLogger().log(Level.INFO, "Player B "+Bukkit.getPlayer(b).getName());
				Bukkit.getLogger().log(Level.INFO, "Bso Size "+bso.size());
				
			}
			b = bl.getOwner();
			
			if (b == null) {
				Bukkit.getLogger().log(Level.INFO, "B Null Line 542");
			}else {
				
				if (blp==null) {
					Bukkit.getLogger().log(Level.INFO, "Player Blp Null Line 546");
				}else {
					blp.sendMessage(ChatColor.GREEN + "Business Income: $" + M.price.format(pds));
					blp.sendMessage(ChatColor.GREEN + "Business Tax: $" + M.price.format(pds * 0.097));
					blp.sendMessage(ChatColor.GREEN + "Comercial Property Tax: $" + M.price.format(pt));
					econ.withdrawPlayer(b, pt);
				}
			}
			
			pds = pds - (pds * 0.097);
			if (pds >= 10000 && pds <= 10000) {
				blp.sendMessage(ChatColor.GREEN + "You paid $" + (price.format(pds * 0.14)) + " of Capital Gains Tax");
				pds = pds * 0.96;
			} else if (pds >= 100001 && pds <= 500000) {
				blp.sendMessage(ChatColor.GREEN + "You paid $" + (price.format(pds * 0.2)) + " of Capital Gains Tax");
				pds = pds * 0.8;
			} else if (pds >= 500001 && pds <= 900000) {
				blp.sendMessage(ChatColor.GREEN + "You paid $" + (price.format(pds * 0.3)) + " of Capital Gains Tax");
				pds = pds * 0.7;
			} else if (pds >= 900001 && pds <= 1000000) {
				blp.sendMessage(ChatColor.GREEN + "You paid $" + (price.format(pds * 0.4)) + " of Capital Gains Tax");
				pds = pds * 0.6;
			} else if (pds >= 1000001 && pds <= 1500000) {
				blp.sendMessage(ChatColor.GREEN + "You paid $" + (price.format(pds * 0.5)) + " of Capital Gains Tax");
				pds = pds * 0.5;
			} else if (pds >= 1500001 && pds <= 1800000) {
				blp.sendMessage(ChatColor.GREEN + "You paid $" + (price.format(pds * 0.6)) + " of Capital Gains Tax");
				pds = pds * 0.4;
			} else if (pds >= 1800001 && pds <= 2000000) {
				blp.sendMessage(ChatColor.GREEN + "You paid $" + (price.format(pds * 0.7)) + " of Capital Gains Tax");
				pds = pds * 0.3;
			} else if (pds >= 2000001 && pds <= 2300000) {
				blp.sendMessage(ChatColor.GREEN + "You paid $" + (price.format(pds * 0.8)) + " of Capital Gains Tax");
				pds = pds * 0.2;
			} else if (pds >= 2300001) {
				blp.sendMessage(ChatColor.GREEN + "You paid $" + (price.format(pds * 0.9)) + " of Capital Gains Tax");
				pds = pds * 0.1;
			}

			if (pds >= 100000) {
				pds = pds / dv;
			}

			if (pds > 0) {
				econ.depositPlayer(b, pds);
			} else {
				if (blp.isOnline()) {
					blp
					        .sendMessage(ChatColor.RED + "Business Error Negative Deposit. Post in bugs - PDS: " + pds);
				}
			}

			if (asal >= 0) {
				econ.withdrawPlayer(b, asal);
			} else {
				if (blp.isOnline()) {
					blp.sendMessage(
					        ChatColor.RED + "Business Error Negative Deposit. Post in bugs - ASAL: " + asal);
				}
			}

			blp.sendMessage(M.prf + "You paid your employees $" + M.price.format(asal));

			blp.sendMessage(M.prf + "Your employees earned you $" + M.price.format(arev));

			config.set("PlayerData." + b + ".Business.Revenue", arev + cll);
			config.set("PlayerData." + b + ".Business.OperationCost", asal);
			config.set("PlayerData." + b + ".Business.Supplies", s - ns);
			M.saveConfig();

		}
*/
	}

	public synchronized void rentalLoop() {

		for (Eviction EV : M.evs) {

			String r = EV.getRegion();
			if (M.rented.contains(r)) {
				String t = config.getString("ServerData.Rentals." + r + ".Tenant");
				String ll = config.getString("ServerData.Rentals." + r + ".LandLord");
				double prc = config.getDouble("ServerData.Rentals." + r + ".Rent");

				// config.set("ServerData.Rentals."+r+".Tenant", rt.getName());
				// config.set("ServerData.Rentals."+r+".LandLoard", rt.getLandLord());
				// config.set("ServerData.Rentals."+r+".Rent", rt.getPrice());

				if (t.equalsIgnoreCase(EV.getTenant())) {
					M.rented.remove(r);

					if (Bukkit.getPlayer(t).isOnline()) {
						Player l = Bukkit.getPlayer(t);
						l.sendMessage(M.prf + "Rental contract stopped for " + r);
					}

					if (Bukkit.getPlayer(ll).isOnline()) {
						Player l = Bukkit.getPlayer(ll);
						l.sendMessage(M.prf + "Rental contract stopped for " + r);
					}

					Rental rnt = new Rental(r, t, ll, prc, "PayCycle");

					RentalStopEvent rts = new RentalStopEvent(rnt, EV.getLandlord());
					Bukkit.getServer().getPluginManager().callEvent(rts);

					config.set("ServerData.Rentals." + r, null);
					config.set("ServerData.Rented", M.rented);

					config.set("ServerData.Rentals." + r + ".Tenant", "None");

					try {
		    			Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
		    	    } catch (Exception ex) {
		    	    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + ex.getMessage());
		    	    }

				} else if (ll.equalsIgnoreCase(EV.getLandlord())) {
					M.rented.remove(r);

					if (Bukkit.getPlayer(t).isOnline()) {
						Player l = Bukkit.getPlayer(t);
						l.sendMessage(M.prf + "Rental contract stopped for " + r);
					}

					if (Bukkit.getPlayer(ll).isOnline()) {
						Player l = Bukkit.getPlayer(ll);
						l.sendMessage(M.prf + "Rental contract stopped for " + r);
					}

					Rental rnt = new Rental(r, t, ll, prc, "PayCycle");

					RentalStopEvent rts = new RentalStopEvent(rnt, ll);
					Bukkit.getServer().getPluginManager().callEvent(rts);
					config.set("ServerData.Rentals." + r, null);
					config.set("ServerData.Rented", M.rented);

					try {
		    			Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
		    	    } catch (Exception ex) {
		    	    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + ex.getMessage());
		    	    }

				}

			}
			M.evs.remove(EV);
		}

		if (econ == null) {
			econ = new Econ();
		}
		M.rls = new ArrayList();
		for (String r : M.rented) {

			String t = config.getString("ServerData.Rentals." + r + ".Tenant");
			String ll = config.getString("ServerData.Rentals." + r + ".LandLord");
			double prc = config.getDouble("ServerData.Rentals." + r + ".Rent");
			// config.set("ServerData.Rented", rented);

			Rental rl = new Rental(r, t, ll, prc, "PayCycle");
			M.rls.add(rl);

			if (econ.has(t, prc)) {
				econ.withdrawPlayer(t, prc);
				econ.addPayment(t);
			} else {
				econ.withdrawPlayer(t, prc);
				econ.addLatePayment(t);
			}
			// econ.withdrawPlayer(t, prc);
			econ.depositPlayer(ll, prc);
			if (M.isBusiness(ll)) {
				int rev = config.getInt("PlayerData." + ll + ".Business.Revenue");
				config.set("PlayerData." + ll + ".Business.Revenue", rev + prc);

			}

		}

	}

	public double getPropertyValue(Region R, World W) {

		double value = 0;
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		for (BlockVector3 point : R) {

			Location L = new Location(W, point.getBlockX(), point.getBlockY(), point.getBlockZ());
			Material MM = L.getBlock().getType();

			if (prices.contains(MM.name())) {
				value = value + config.getDouble("ServerData.Materials.Value." + MM.name());
			} else {

				if (MM.equals(Material.AIR)) {

				} else {
					value = value + 0.0001;
				}

			}
		}

		return value;
	}

	public int random(int min, int max) {

		Random rand = new Random();
		rand.setSeed(Bukkit.getServer().getWorld("World").getFullTime() + Bukkit.getBannedPlayers().size()
		        + Bukkit.getOnlinePlayers().size() + System.currentTimeMillis() + System.nanoTime()
		        + Bukkit.getOnlinePlayers().size() + Bukkit.getOperators().size() + Bukkit.getPort()
		        + Bukkit.getOfflinePlayers().length);
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public int random2(int min, int max) {

		Random rand = new Random();
		rand.setSeed(Bukkit.getServer().getWorld("World").getFullTime() + Bukkit.getBannedPlayers().size()
		        + Bukkit.getOnlinePlayers().size() + System.currentTimeMillis() + System.nanoTime()
		        + Bukkit.getOnlinePlayers().size() + Bukkit.getOperators().size() + Bukkit.getPort()
		        + Bukkit.getOfflinePlayers().length + Bukkit.getIdleTimeout() + Bukkit.getIPBans().size()
		        + Bukkit.getWorlds().size() + Bukkit.getName().getBytes().length + Bukkit.getWorld("world").getSeed());
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public Business getBusiness(String Name) {

		if (M.isBusiness(Name)) {
			bso = config.getStringList("PlayerData.BusinessOwners");
			for (String b : bso) {

				if (b == Name) {

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
					Business bl = new Business(b, rv, oc, PRF, sh, size);
					return bl;

				}
			}
		}

		return null;
	}
}
