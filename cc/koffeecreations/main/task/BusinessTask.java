package cc.koffeecreations.main.task;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import cc.koffeecreations.main.econ.Business;
import cc.koffeecreations.main.econ.Econ;
import cc.koffeecreations.main.econ.Employee;
import cc.koffeecreations.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class BusinessTask {
	
	public static Main M;

	public DecimalFormat price = new DecimalFormat("0.00");
	
	public BusinessTask(Main MM, List<String> PS) {
		M = MM;
		
		businessLoop(PS);
	}
	
    public synchronized void businessLoop(List<String> PL) {
    	
    	FileConfiguration config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
    	
    	boolean online = false;
    	for (String b : PL) {
    	OfflinePlayer bp = Bukkit.getOfflinePlayer(b);
    	Player bpl = Bukkit.getPlayer(b);
    	if (bp.isOnline()) {
    		bpl = Bukkit.getPlayer(b);
    		online = true;
    	}
    	
    	if (M.econ == null) {
    		M.econ = new Econ();
    	}
    	
    	M.bso = config.getStringList("PlayerData.BusinessOwners");
    	M.bls = new ArrayList();
    	M.pls = new ArrayList();
    	
    		//Do Business maths here
    		
    		
    		
    		//s = config.getInt("PlayerData."+b+".Business.Supplies");
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
    		double ns = size*1.25;
    		
    		Business bl = new Business(b, rv, oc, PRF, sh, size);
    		M.bls.add(bl);
    		
    		if (online) {
    			M.pls.add(bpl);
    		}
    		
    		
    		//config.set("PlayerData."+sender.getName()+".Employer", args[1]);
			//config.set("PlayerData."+sender.getName()+".Salary", Integer.parseInt(args[2]));

			//config.set("PlayerData."+sender.getName()+".Employed", pe);

    		
    		
    		if (s <= ns) {
    			//M.econ.withdrawPlayer(b, 250000);
    			
    			if (Bukkit.getOnlinePlayers().contains(bp)) {
    				
    			
    				Bukkit.getPlayer(b).sendMessage(M.prf+ChatColor.RED+" Current Supplies: "+s+" Needed Supplies: "+ns);

    				Bukkit.getPlayer(b).sendMessage(M.prf+ChatColor.RED+"You didn't have enough Business supplies when the fines system goes live you will have to pay $250,000 every time this happens");
    			
    			}
    		}
    		
    		if (e >= 0) {
    			List<Integer>eso = config.getIntegerList("PlayerData."+b+".Business.EmployeeID.List");
    			for (int es : config.getIntegerList("PlayerData."+b+".Business.EmployeeID.List")) {
    				int sal = config.getInt("PlayerData."+b+".Business.Employees."+es+".Salary");
    				int rev = config.getInt("PlayerData."+b+".Business.Employees."+es+".Revenue");
    				int mood = config.getInt("PlayerData."+b+".Business.Employees."+es+".Mood");
    				int LTraining = config.getInt("PlayerData."+b+".Business.Employees."+es+".LastTraining");
    				OfflinePlayer PLY = bp;
    				Employee a = new Employee(es, sal, rev);
    				//econ.withdrawPlayer(b, sal);
    				//econ.depositPlayer(b, rev);
    				int rt = random(1,200);
    				int rc = random2(1,200);
    				if (rt == rc) {
    					sal = random(100,1000);
    					rev = random2(500,1000);
    				}
    				
    				int rst = random(1,350);
    				int rsc = random2(1,340);
    				
    				if (rev >= sal) {
    					
    					if (rst == rsc) {
    						if (online) {
    							bpl.sendMessage(ChatColor.RED+"Employee "+a.getID()+" Randomly Quit");
    						}
    						sal = 0;
    						rev = 0;
    						eso.remove((eso.indexOf(a.getID())));
    						config.set("PlayerData."+b+".Business.Employees."+es+".Revenue", 0);
    						config.set("PlayerData."+b+".Business.Employees."+es+".Salary", 0);
    						try {
    							Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
    					    } catch (Exception e1) {
    					    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e1.getMessage());
    					    }
    					}
    					
    				}
    				
    				
    				if (mood <= 2) {
    					int c = random(1,4);
    					int d = random2(1,6);
    					
    					if (c == d) {
    						if (online) {
    							bpl.sendMessage(ChatColor.RED+"Employee "+a.getID()+" Rage Quit");
    						}
    						sal = 0;
    						rev = 0;
    						eso.remove((eso.indexOf(a.getID())));
    						config.set("PlayerData."+b+".Business.Employees."+es+".Revenue", 0);
    						config.set("PlayerData."+b+".Business.Employees."+es+".Salary", 0);
    						try {
    							Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
    					    } catch (Exception e1) {
    					    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e1.getMessage());
    					    }
    					}
    					
    				}
    				

    				
    				//config.set("PlayerData."+n+".Business.Employees."+args[2]+".TrainingCount", 0);
					//config.set("PlayerData."+n+".Business.Employees."+args[2]+".IsTraining", true);
					//config.set("PlayerData."+n+".Business.Employees."+args[2]+".Training", config.getInt("PlayerData."+n+".Business.Employees."+args[2]+".Training")+1);
    				//config.set("PlayerData."+n+".Business.Employees."+args[2]+".Revenue", 0);
    				//config.set("PlayerData."+n+".Business.Employees."+args[2]+".Mood", random(4,10));
    				boolean isTraining = config.getBoolean("PlayerData."+b+".Business.Employees."+es+".IsTraining");
    				if (isTraining) {
    					config.set("PlayerData."+b+".Business.Employees."+es+".LastTraining", 0);
    					int TrainingCount = config.getInt("PlayerData."+b+".Business.Employees."+es+".TrainingCount");
    					TrainingCount = TrainingCount+1;
    					
    					config.set("PlayerData."+b+".Business.Employees."+es+".TrainingCount", TrainingCount);
    					try {
    						Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
    				    } catch (Exception e1) {
    				    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e1.getMessage());
    				    }
    					
    					int T = config.getInt("PlayerData."+b+".Business.Employees."+es+".Training");
    					
    					if (TrainingCount >= config.getInt("TrainingDuration")) {
    						TrainingCount = 0;
    						T = T +1;
    						int NS = 0;
    						if (T > 1 && T < 2) {
    							NS = random(30,220);
    						}else if (T > 2 && T < 3) {
    							NS = random(40,230);
    						}else if (T > 3 && T < 4) {
    							NS = random(50,320);
    						}else if (T > 4 && T < 5) {
    							NS = random(60,330);
    						}else if (T > 5 && T < 6) {
    							NS = random(70,340);
    						}else if (T > 6 && T < 7) {
    							NS = random(80,350);
    						}else if (T > 7 && T < 8) {
    							NS = random(90,360);
    						}else if (T > 8 && T < 9) {
    							NS = random(100,370);
    						}else if (T > 9 && T < 10) {
    							NS = random(110,380);
    						}else if (T >= 10) {
    							NS = random(120,420);
    						}
    						
    						config.set("PlayerData."+b+".Business.Employees."+es+".TrainingCount", 0);
    						config.set("PlayerData."+b+".Business.Employees."+es+".IsTraining", false);
    						config.set("PlayerData."+b+".Business.Employees."+es+".Revenue", random(30,220));
    						config.set("PlayerData."+b+".Business.Employees."+es+".Training", T);
    						try {
    							Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
    					    } catch (Exception e1) {
    					    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e1.getMessage());
    					    }
    						
    					}
    					
    				}else {
    					config.set("PlayerData."+b+".Business.Employees."+es+".LastTraining", LTraining+1);
    					try {
    						Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
    				    } catch (Exception e1) {
    				    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e1.getMessage());
    				    }
    				}
    				
    				if (LTraining >= random(10,20)) {
    					mood = mood-1;
    					config.set("PlayerData."+b+".Business.Employees."+es+".Mood", mood);
    					try {
    						Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
    				    } catch (Exception e1) {
    				    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e1.getMessage());
    				    }
    				}
    				
    				
    				if ((rst+1) == (rsc-1)) {
    					int EI = a.getID();
    					int evn = random(1,7);
    					
    					if (evn == 1) {
    						int LI = random(1000, 250000);
    						if (online) {
    							bpl.sendMessage(ChatColor.RED+"Employee "+EI+" Was injured on the job L&I Charged $"+LI);
    							M.econ.withdraw(b, LI);
    						}
    					}else if (evn == 2) {
    						int LI = random(10000, 950000);
    						if (online) {
    							bpl.sendMessage(ChatColor.RED+"Employee "+EI+" Was injured on the job L&I Charged $"+LI);
    							M.econ.withdraw(b, LI);
    						}
    					}else if (evn == 3) {
    						int LI = random(10000, 950000);
    						if (online) {
    							bpl.sendMessage(ChatColor.RED+"Employee "+EI+" Has taken a paid medical leave");
    							config.set("PlayerData."+b+".Business.Employees."+es+".Revenue", 0);
    						}
    					}else if (evn == 4) {
    						int LI = random(10000, 950000);
    						if (PLY.isOnline()) {
    							//PLY.sendMessage(ChatColor.RED+"Employee "+EI+" Has taken a paid vacation");
    							config.set("PlayerData."+b+".Business.Employees."+es+".Revenue", 0);
    						}
    					}else if (evn == 5) {
    						int LI = random(100000, 1950000);
    						if (online) {
    							bpl.sendMessage(ChatColor.RED+"Employee "+EI+" injured themself and a few other L&I charged $"+LI);
    							M.econ.withdraw(b, LI);
    						}
    					}else if (evn == 6) {
    						int LI = random(1000000, 3950000);
    						if (online) {
    							bpl.sendMessage(ChatColor.RED+"Employee "+EI+" severly injured themself and a few other L&I charged $"+LI);
    							M.econ.withdraw(b, LI);
    						}
    					}else if (evn == 7) {
    						int LI = random(1000, 50000);
    						if (online) {
    							bpl.sendMessage(ChatColor.RED+"A power surge damaged some critical equipment repairs cost $"+LI);
    							M.econ.withdraw(b, LI);
    						}
    					}
    					
    				}
    				
    				bp = Bukkit.getOfflinePlayer(b);
    				
    				if (rev < 0) {
    					if (online) {
    					bpl.sendMessage(ChatColor.RED+"Business Error: 'Negative REV' - Please Report this to Wintergrasped");
    					}
    					rev=0;
    				}
    				
    				arev = arev+rev;
    				
    				if (arev < 0) {
    					if (online) {
    						bpl.sendMessage(ChatColor.RED+"Business Error: 'Negative AREV' - Please Report this to Wintergrasped");
    					}
    						arev=0;
    				}
    				asal = asal+sal;
    				
    				if (asal < 0) {
    					if (online) {
    						bpl.sendMessage(ChatColor.RED+"Business Error: 'Negative ASAL' - Please Report this to Wintergrasped");
    					}
    					asal=0;
    				}
    				
    				//End Of Employee Loop
    			}
    		}
    		
    		for (String pe : config.getStringList("PlayerData."+b+".Employed")) {
    			if (Bukkit.getOfflinePlayer(pe).isOnline()) {
    				int ss = config.getInt("PlayerStocks.PlayerData."+pe+".Salary");
    				M.econ.withdrawPlayer(b, ss);
    				M.econ.depositPlayer(pe, ss);
    			}
    		}
    		
    		
    		double cll = bl.getStockPrice()*bl.getShares();
    		double ars = arev-cll; //cll= 32400
    		
    		if (cll < 0) {
    			if (online) {
    				bpl.sendMessage(ChatColor.RED+"Business Error Negative CLL Please report this in #bugs CLL:"+cll);
    				
    			}
    			ars = arev*2.5;
    		}
    		
    		//arev = 3245
    		int dv = 2;
    		double div = 0;
    		
    		config.set("PlayerBus."+b+".CLL", cll);
    		config.set("PlayerBus."+b+".arev", arev);
    		config.set("PlayerBus."+b+".asal", asal);
    		config.set("PlayerBus."+b+".ars", ars);
    		
    		if (ars > (arev*3)) {
    			if (online) {
    				bpl.sendMessage(ChatColor.RED+"Business Error ars greater then 3x arev. Post in bugs - ARS: "+ars+" AREV:"+arev);
    				
    			}
    			ars = arev*2.5;
    		}
    		
    		List<String> ShareHolders = config.getStringList("PlayerData."+b+".Business.ShareHolders");
    		
    		if (ShareHolders.size() >= 0) {
    		div = arev*0.20;
    		int tds = 0;
    		for (String SN : ShareHolders) {
    			tds = tds+config.getInt("PlayerData."+b+".Business.ShareHolderShares."+SN);
    		}
    		
    		double tdiv = div/tds;
    		config.set("PlayerBus."+b+".tdiv", tdiv);
    		if (tdiv >= config.getDouble("MaxDividen")) {
    			tdiv = config.getDouble("MaxDividen");
    		}
    		for (String SN : ShareHolders) {
    			int ss = config.getInt("PlayerData."+b+".Business.ShareHolderShares."+SN);
    			M.econ.deposit(SN, (tdiv*ss));
    		}
    		config.set("BusinessData."+b+".LastDiv", tdiv);
    		}else {
    			config.set("BusinessData."+b+".LastDiv", arev*0.20);
    		}
    		
    		
    		//config.set("PlayerData."+b+".Business.ShareHolderShares."+p.getName(), cs);
			//config.set("PlayerData."+b+".Business.ShareHolders", cs);
    		
    		
    		double pds = ars;
    		
    		
    		
    		int wh = config.getInt("PlayerData."+b+".Properties.warehouses");
			int fc = config.getInt("PlayerData."+b+".Properties.factory");
			double pt = (wh*100000)*0.12;
    		pt = pt+((fc*1000000)*0.12);
    		
    		if (ars < 0) {
    			if (online) {
    			bpl.sendMessage(ChatColor.RED+"Business Error 'Negative ARS' Please report this to Wintergrasped");
    			}
    			ars = 100;
    		}
    		
    		if (online) {
    		bpl.sendMessage(ChatColor.GREEN+"Business Income: $"+M.price.format(pds));
    		bpl.sendMessage(ChatColor.GREEN+"Business Tax: $"+M.price.format(pds*0.097));
    		bpl.sendMessage(ChatColor.GREEN+"Comercial Property Tax: $"+M.price.format(pt));
    		}
    		
    		M.econ.withdrawPlayer(b, pt);
    		pds = pds-(pds*0.097);
    		if (pds >= 10000 && pds <= 10000) {
    			if (online) {
    			bpl.sendMessage(ChatColor.GREEN+"You paid $"+(price.format(pds*0.14))+" of Capital Gains Tax");
    			}
    			pds = pds*0.96;
    		}else if (pds >= 100001 && pds <= 500000) {
    			if (online) {
    			bpl.sendMessage(ChatColor.GREEN+"You paid $"+(price.format(pds*0.2))+" of Capital Gains Tax");
    			}
    			pds = pds*0.8;
    		}else if (pds >= 500001 && pds <= 900000) {
    			if (online) {
    			bpl.sendMessage(ChatColor.GREEN+"You paid $"+(price.format(pds*0.3))+" of Capital Gains Tax");
    			}
    			pds = pds*0.7;
    		}else if (pds >= 900001 && pds <= 1000000) {
    			if (online) {
    			bpl.sendMessage(ChatColor.GREEN+"You paid $"+(price.format(pds*0.4))+" of Capital Gains Tax");
    			}
    			pds = pds*0.6;
    		}else if (pds >= 1000001 && pds <= 1500000) {
    			if (online) {
    			bpl.sendMessage(ChatColor.GREEN+"You paid $"+(price.format(pds*0.5))+" of Capital Gains Tax");
    			}
    			pds = pds*0.5;
    		}else if (pds >= 1500001 && pds <= 1800000) {
    			if (online) {
    			bpl.sendMessage(ChatColor.GREEN+"You paid $"+(price.format(pds*0.6))+" of Capital Gains Tax");
    			}
    			pds = pds*0.4;
    		}else if (pds >= 1800001 && pds <= 2000000) {
    			if (online) {
    			bpl.sendMessage(ChatColor.GREEN+"You paid $"+(price.format(pds*0.7))+" of Capital Gains Tax");
    			}
    			pds = pds*0.3;
    		}else if (pds >= 2000001 && pds <= 2300000) {
    			if (online) {
    			bpl.sendMessage(ChatColor.GREEN+"You paid $"+(price.format(pds*0.8))+" of Capital Gains Tax");
    			}
    			pds = pds*0.2;
    		}else if (pds >= 2300001) {
    			if (online) {
    			bpl.sendMessage(ChatColor.GREEN+"You paid $"+(price.format(pds*0.9))+" of Capital Gains Tax");
    			}
    			pds = pds*0.1;
    		}
    		
    		if (pds >= 100000) {
    			pds = pds/dv;
    			
    			if (pds >= 2000000) {
    				pds = random(1875211 ,2000000);
    			}
    			
    		}
    		
    		if (pds >= 0) {
    			M.econ.depositPlayer(b, pds);
    		}else{
    			if (online) {
    				bpl.sendMessage(ChatColor.RED+"Business Error Negative Deposit. Post in bugs - PDS: "+pds);
    			}
    		}
    		
    		if (asal >= 0) {
    			M.econ.withdrawPlayer(b, asal);
    		}else {
    			//if (Bukkit.getOfflinePlayer(b).isOnline()) {
    				//Bukkit.getOfflinePlayer(b).sendMessage(ChatColor.RED+"Business Error Negative Deposit. Post in bugs - ASAL: "+asal);
    			//}
    		}
    		
    		//bp.sendMessage(M.prf+"You paid your employees $"+M.price.format(asal));
    		
    		//bp.sendMessage(M.prf+"Your employees earned you $"+M.price.format(arev));
    		
    		
    		config.set("PlayerData."+b+".Business.Revenue", arev+cll);
    		config.set("PlayerData."+b+".Business.OperationCost", asal);
    		config.set("PlayerData."+b+".Business.Supplies", s-ns);
    		try {
    			Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
    	    } catch (Exception ex) {
    	    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + ex.getMessage());
    	    }
    	}
    	
    }
    
    public int random(int min, int max) {

        Random rand = new Random();
        rand.setSeed(Bukkit.getServer().getWorld("World").getFullTime()+Bukkit.getBannedPlayers().size()+Bukkit.getOnlinePlayers().size()+System.currentTimeMillis()+System.nanoTime()+Bukkit.getOnlinePlayers().size()+Bukkit.getOperators().size()+Bukkit.getPort()+Bukkit.getOfflinePlayers().length);
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
    
    public int random2(int min, int max) {

        Random rand = new Random();
        rand.setSeed(Bukkit.getServer().getWorld("World").getFullTime()+Bukkit.getBannedPlayers().size()+Bukkit.getOnlinePlayers().size()+System.currentTimeMillis()+System.nanoTime()+Bukkit.getOnlinePlayers().size()+Bukkit.getOperators().size()+Bukkit.getPort()+Bukkit.getOfflinePlayers().length+Bukkit.getIdleTimeout()+Bukkit.getIPBans().size()+Bukkit.getWorlds().size()+Bukkit.getName().getBytes().length+Bukkit.getWorld("world").getSeed());
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
