package cc.koffeecreations.main.listener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import cc.koffeecreations.main.property.Property;
import cc.koffeecreations.main.property.PropertySale;
import cc.koffeecreations.main.*;
import cc.koffeecreations.main.econ.Econ;
import cc.koffeecreations.main.events.Rented;
import cc.koffeecreations.main.hooks.WorldGuardAPI;
import cc.koffeecreations.main.unknow.Rental;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public final class InteractListener implements Listener {

	
	public static List<Rental> rntss;
	public List<Rental> rnts;
	public Econ econ;
	public WorldGuardAPI wg;
	public double psp;
	public double farmingPRC;
	public double miningPRC;
	public double loggingPRC;
	public boolean debug = false;
	//public WorldGuardPlugin worldGuardPlugin;

	public DecimalFormat price = new DecimalFormat("0.00");


	private ArrayList<Player> entered = new ArrayList<>();
    private ArrayList<Player> left = new ArrayList<>();
	private List<Property> prys = new ArrayList();
	
	
	FileConfiguration config;
	
	public InteractListener () {
		
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		econ = new Econ();
		loadProperty();
		miningPRC = config.getDouble("ServerData.MiningStockPrice");
		loggingPRC = config.getDouble("ServerData.LoggingStockPrice");
		farmingPRC = config.getDouble("ServerData.FarmingStockPrice");
		//worldGuardPlugin = getWorldGuard();


		//c = WGP.getRegionContainer();
		
		
	}
	
	@EventHandler
    public void onPlayerClickSign(BlockBreakEvent event){
		rnts = Main.srnts;
        Player player = event.getPlayer();
        
       

        if(event.getBlock().getType().equals(Material.OAK_SIGN)){
            //if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
            //^^ choose actions ^^
        
                Sign sign = (Sign) event.getBlock().getState();
                //^^ .getState(); really important
                if(sign.getLine(0).equalsIgnoreCase("[ForSale]")){
                    int price = Integer.parseInt(sign.getLine(2));
                    String r = sign.getLine(1);
                    String o = sign.getLine(3);
                    Property P = null;
                    Player PL = event.getPlayer();
                    
                    for (Property PRS : prys) {
                    	if (PRS.getName().equals(r)) {
                    		P = PRS;
                    	}
                    }
                    if (econ.has(player.getName(), price)) {
                    	econ.withdrawPlayer(player.getName(), price);
                      	player.sendMessage(ChatColor.GREEN+"You have bought "+r+" for $"+price);
                      	econ.depositPlayer(o, price);
                      	Bukkit.getPlayer(o).sendMessage(ChatColor.GREEN+"You sold your property for $"+price);
                      	prys.remove(P);
						P.setOwner(PL.getName());
						PL.sendMessage("Bought "+P.getName());
						P.setForSale(false);
						prys.add(P);
						config.set("PropertyData."+P.getName()+".ForSale", false);
						config.set("PropertyData."+P.getName()+".Owner", PL.getName());
						Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
						econ.withdraw(event.getPlayer().getName(), price);
						econ.deposit(o, price);
						saveConfig();
                    	//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg removeowner "+r+" -w "+player.getWorld().getName()+" -a");
                    	//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg addowner "+r+" "+player.getName()+" -w "+player.getWorld().getName()+"");
                    }else {
                    	player.sendMessage(ChatColor.RED+"You too broke");
                    }
                    event.setCancelled(true);
                }else if(sign.getLine(0).equalsIgnoreCase("[ForRent]")){
                	loadPrys();
                    int price = Integer.parseInt(sign.getLine(2));
                    String r = sign.getLine(1);
                    String o = sign.getLine(3);
                    Property PR = null;
                    
                    for (Property PRS : prys) {
                    	if (PRS.getName().equals(r)) {
                    		PR = PRS;
                    	}
                    }
                    
                    if (PR.rented() == false) {
                    
                    if (econ.has(player.getName(), price)) {
                    	econ.withdrawPlayer(player.getName(), price);
                    	player.sendMessage(ChatColor.GREEN+"You have bought "+r+" for $"+price);
                    	///rg removeowner -w "world" -a tst123
                    	econ.depositPlayer(o, price);
                    	Bukkit.getPlayer(o).sendMessage(ChatColor.GREEN+"You rented your property for $"+price);
                    	PR.addTrusted(player.getName());
                    	PR.setRenter(player.getName());
                    	PR.setRented(true);
                    	Rental RR = new Rental(PR.getName(), PR.getRenter(), PR.getOwner(), price, "Month");
                    	Rented RE = new Rented(RR);
                    	updateData();
                    	//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg removeowner "+r+" -w "+player.getWorld().getName()+" -a");
                    	//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg addowner "+r+" "+player.getName()+" -w "+player.getWorld().getName()+"");
                    }else {
                    	player.sendMessage(ChatColor.RED+"You too broke");
                    }
                    rntss = rnts;
                    Main.update();
                    event.setCancelled(true);
                    }else{
                    	player.sendMessage(ChatColor.RED+"Property is already rented");
                    }
                }else if(sign.getLine(0).equalsIgnoreCase("[Shop]")){
                    int price = Integer.parseInt(sign.getLine(2));
                    int ID = Integer.valueOf(sign.getLine(1));
                    String in = sign.getLine(3);
                    List<String> si = config.getStringList("ServerData.Shops."+ID+".ItemInventory");
                    int inv = 0;
                    String o = config.getString("ServerData.ShopOwner."+ID);
                    
                    if (si.contains(in)) {
                    	inv = config.getInt("ServerData.Shops."+ID+".Inventory."+in);
                    }
                    
                    if (econ.has(player.getName(), price)) {
                    	if (inv >= 1) {
                    		econ.withdrawPlayer(player.getName(), price);
                        	player.sendMessage(ChatColor.GREEN+"You have bought "+in+" for $"+price);
                        	ItemStack is = new ItemStack(Material.getMaterial(in), 1);
                        	player.getInventory().addItem(is);
                        	econ.deposit(o, price);
                        	inv = inv-1;
                        	config.set("ServerData.Shops."+ID+".Inventory."+in, inv);
                        	
                        	if (isBusiness(o)) {
                          		int rv = config.getInt("PlayerData."+o+".Business.Revenue");
                          		rv = rv+price;
                          		config.set("PlayerData."+o+".Business.Revenue", rv);
                          	}
                        	
                        	saveConfig();
                        	//econ.depositPlayer(o, price);
                    	}else {
                    		player.sendMessage(ChatColor.RED+"Shop Out Of Stock");
                    	}
                    	
                    }else {
                    	player.sendMessage(ChatColor.RED+"You too broke");
                    }
                    rntss = rnts;
                    Main.update();
                    event.setCancelled(true);
                }else if(sign.getLine(0).equalsIgnoreCase("[Server]")){
                    int price = Integer.parseInt(sign.getLine(2));
                  
                    String in = sign.getLine(3);
                    ItemStack is = new ItemStack(Material.getMaterial(in), 1);
                    ItemStack ih = player.getItemInHand();
                    String type = sign.getLine(1);
                    	
                    	if (type.equalsIgnoreCase("Buy")) {
                    		

                            		if (econ.has(player.getName(), price)) {
                            			econ.withdraw(player.getName(), price);
                            			player.sendMessage(ChatColor.GREEN+"You bought "+in+" for $"+price);
                            			player.getInventory().addItem(is);
                            			return;
                            		}else {
                            			player.sendMessage(ChatColor.RED+"You dont have any moneys");
                            			return;
                            		}
                    	}else if (type.equalsIgnoreCase("Sell")) {
                    		
                    		if (ih.getType() == Material.getMaterial(in)) {
                    			econ.deposit(player.getName(), price);
                    			player.sendMessage(ChatColor.GREEN+"You sold "+in+" for $"+price);
                    			if (isBusiness(player.getName())) {
                              		int rv = config.getInt("PlayerData."+player.getName()+".Business.Revenue");
                              		rv = rv+price;
                              		config.set("PlayerData."+player.getName()+".Business.Revenue", rv);
                              	}
                    			player.getInventory().getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
                    			
                    		}else {
                    			player.sendMessage(ChatColor.RED+"You dont have any in your hand");
                    		}
                    		
                    		
                    	}
                    		//econ.deposit(player.getCustomName(), price);
                        	
                        	

                    rntss = rnts;
                    Main.update();
                    event.setCancelled(true);
                }
               //}
              }else if(event.getBlock().getType().equals(Material.OAK_WALL_SIGN)){
                  //if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
                      //^^ choose actions ^^
            	  Sign sign = (Sign) event.getBlock().getState();
            	  Property P = null ;
            	  String r = sign.getLine(1);
            	  for (Property PR : prys) {
            		  if (PR.getName().equalsIgnoreCase(r)) {
            			  P = PR;
            		  }
            	  }
            	  
      				Player PL = event.getPlayer();

                          
                          //^^ .getState(); really important
                          if(sign.getLine(0).equalsIgnoreCase("[ForSale]")){
                              int price = Integer.parseInt(sign.getLine(2));
                              
                              String o = sign.getLine(3);
                              
                              //Bukkit.getLogger().log(Level.INFO, player.getName()+" "+sign.getLine(0)+" Not [ForSale]");
                              
                              if (econ.has(player.getName(), price)) {
                              	econ.withdrawPlayer(player.getName(), price);
                              	player.sendMessage(ChatColor.GREEN+"You have bought "+r+" for $"+price);
                              	econ.depositPlayer(o, price);
                              	Bukkit.getPlayer(o).sendMessage(ChatColor.GREEN+"You sold your property for $"+price);
                              	prys.remove(P);
								P.setOwner(PL.getName());
								PL.sendMessage("Bought "+P.getName());
								P.setForSale(false);
								prys.add(P);
								config.set("PropertyData."+P.getName()+".ForSale", false);
								config.set("PropertyData."+P.getName()+".Owner", PL.getName());
								Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
								econ.withdraw(event.getPlayer().getName(), price);
								econ.deposit(o, price);
								saveConfig();
								
								updateData();                              }else {
                              	player.sendMessage(ChatColor.RED+"You too broke");
                              }
                              
                              PropertySale ps = new PropertySale(r, player.getName(), o, price);
                              Bukkit.getServer().getPluginManager().callEvent(ps);
                              
                              
                          event.setCancelled(true);
                          }else if(sign.getLine(0).equalsIgnoreCase("[ForRent]")){
                        	  loadPrys();
                              int price = Integer.parseInt(sign.getLine(2));
                              //String r = sign.getLine(1);
                              String o = sign.getLine(3);
                              Property PR = null;
                              
                              for (Property PRS : prys) {
                              	if (PRS.getName().equals(r)) {
                              		PR = PRS;
                              	}
                              }
                              if (PR.getRenter().equalsIgnoreCase("None")) {
                            	  
                            
                              if (econ.has(player.getName(), price)) {
                              	econ.withdrawPlayer(player.getName(), price);
                              	player.sendMessage(ChatColor.GREEN+"You have rented "+r+" for $"+price);
                              	///rg removeowner -w "world" -a tst123
                              	econ.depositPlayer(o, price);
                              	Bukkit.getPlayer(o).sendMessage(ChatColor.GREEN+"You rented your property for $"+price);
                              	PR.addTrusted(player.getName());
                              	PR.setRenter(player.getName());
                              	updateData();
                              	//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg removeowner "+r+" -w "+player.getWorld().getName()+" -a");
                              	//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg addowner "+r+" "+player.getName()+" -w "+player.getWorld().getName()+"");
                              }else {
                              	player.sendMessage(ChatColor.RED+"You too broke");
                              }
                          }
                              rntss = rnts;
                              Main.update();
                              event.setCancelled(true);
                          }else if(sign.getLine(0).equalsIgnoreCase("[Stock]")){
                        	  
                             String Type = sign.getLine(1);
                             String Action = sign.getLine(3);
                             String Market = sign.getLine(2);
                             int lm = config.getInt("PlayerData."+player.getName()+".Stocks.Shares.Lumber");
                     		int lms = config.getInt("PlayerData."+player.getName()+".Stocks.Shares.Logging");
                     		int mm = config.getInt("PlayerData."+player.getName()+".Stocks.Shares.Mining");
                     		int fm = config.getInt("PlayerData."+player.getName()+".Stocks.Shares.Farming");
                             
                     		miningPRC = config.getDouble("ServerData.MiningStockPrice");
                    		loggingPRC = config.getDouble("ServerData.LoggingStockPrice");
                    		farmingPRC = config.getDouble("ServerData.FarmingStockPrice");
                     		
                     		
                             if (Action.equalsIgnoreCase("buy")) {
                            	 
                            	 if (Type.equalsIgnoreCase("industry")) {
                            		 if (Market.equalsIgnoreCase("Mining")) {
                            			 
                            			 if (econ.has(player.getName(), miningPRC)) {
                            				 econ.withdraw(player.getName(), miningPRC);
                            				 mm = mm+1;
                            				 config.set("PlayerData."+player.getName()+".Stocks.Shares.Mining", mm);
                            				 player.sendMessage(ChatColor.GREEN+"Bought 1 share of Mining for $"+price.format(miningPRC)+ " You now have "+mm+" share(s)");
                            			 }else {
                            				 player.sendMessage(ChatColor.RED+"Not Enough Money");
                            			 }
                            			 
                            		 }else if (Market.equalsIgnoreCase("Logging")) {
                            			 
                            			 if (econ.has(player.getName(), loggingPRC)) {
                            				 econ.withdraw(player.getName(), loggingPRC);
                            				 lms = lms+1;
                            				 config.set("PlayerData."+player.getName()+".Stocks.Shares.Logging", lms);
                            				 player.sendMessage(ChatColor.GREEN+"Bought 1 share of Logging for $"+price.format(loggingPRC)+ " You now have "+lms+" share(s)");
                            			 }else {
                            				 player.sendMessage(ChatColor.RED+"Not Enough Money");
                            			 }
                            			 
                            		 }else if (Market.equalsIgnoreCase("Farming")) {
                            			 
                            			 if (econ.has(player.getName(), farmingPRC)) {
                            				 econ.withdraw(player.getName(), farmingPRC);
                            				 fm = fm+1;
                            				 config.set("PlayerData."+player.getName()+".Stocks.Shares.Farming", fm);
                            				 player.sendMessage(ChatColor.GREEN+"Bought 1 share of Farming for $"+price.format(farmingPRC)+ " You now have "+fm+" share(s)");
                            			 }else {
                            				 player.sendMessage(ChatColor.RED+"Not Enough Money");
                            			 }
                            			 
                            		 }
                            	 }
                            	 
                             }else if (Action.equalsIgnoreCase("sell")) {
                            	 if (Type.equalsIgnoreCase("industry")) {
                            		 if (Market.equalsIgnoreCase("Mining")) {
                            			 
                            			 if (mm >= 1) {
                            				 econ.deposit(player.getName(), miningPRC);
                            				 mm = mm-1;
                            				 config.set("PlayerData."+player.getName()+".Stocks.Shares.Mining", mm);
                            				 player.sendMessage(ChatColor.GREEN+"Sold 1 share of Mining for $"+price.format(miningPRC)+ " You now have "+mm+" share(s)");
                            			 }else {
                            				 player.sendMessage(ChatColor.RED+"You don't own any more shares");
                            			 }
                            			 
                            		 }else if (Market.equalsIgnoreCase("Logging")) {
                            			 
                            			 if (lms >= 1) {
                            				 econ.deposit(player.getName(), loggingPRC);
                            				 lms = lms-1;
                            				 config.set("PlayerData."+player.getName()+".Stocks.Shares.Logging", lms);
                            				 player.sendMessage(ChatColor.GREEN+"Sold 1 share of Logging for $"+price.format(loggingPRC)+ " You now have "+lms+" share(s)");
                            			 }else {
                            				 player.sendMessage(ChatColor.RED+"You don't own any more shares");
                            			 }
                            			 
                            		 }else if (Market.equalsIgnoreCase("Farming")) {
                            		
                            			 if (fm >= 1) {
                            				 econ.deposit(player.getName(), farmingPRC);
                            				 fm = fm-1;
                            				 config.set("PlayerData."+player.getName()+".Stocks.Shares.Farming", fm);
                            				 player.sendMessage(ChatColor.GREEN+"Sold 1 share of Farming for $"+price.format(farmingPRC)+ " You now have "+fm+" share(s)");
                            			 }else {
                            				 player.sendMessage(ChatColor.RED+"You don't own any more shares");
                            			 }
                            			 
                            		 }
                            	 }
                             }
                             
                             //player.sendMessage("This would "+Action+" 1 share of "+Market+" "+Type);
                             event.setCancelled(true);
                            
                             
                          }else if(sign.getLine(0).equalsIgnoreCase("[Shop]")){
                              int price = Integer.parseInt(sign.getLine(2));
                              int ID = Integer.valueOf(sign.getLine(1));
                              String in = sign.getLine(3);
                              List<String> si = config.getStringList("ServerData.Shops."+ID+".ItemInventory");
                              int inv = 0;
                              String o = config.getString("ServerData.ShopOwner."+ID);
                              
                              if (si.contains(in)) {
                              	inv = config.getInt("ServerData.Shops."+ID+".Inventory."+in);
                              }
                              
                              if (econ.has(player.getName(), price)) {
                              	if (inv >= 1) {
                              		econ.withdrawPlayer(player.getName(), price);
                                  	player.sendMessage(ChatColor.GREEN+"You have bought "+in+" for $"+price);
                                  	ItemStack is = new ItemStack(Material.getMaterial(in), 1);
                                  	player.getInventory().addItem(is);
                                  	econ.deposit(o, price);
                                  	inv = inv-1;
                                  	config.set("ServerData.Shops."+ID+".Inventory."+in, inv);
                                  	
                                  	if (isBusiness(o)) {
                                  		int rv = config.getInt("PlayerData."+o+".Business.Revenue");
                                  		rv = rv+price;
                                  		config.set("PlayerData."+o+".Business.Revenue", rv);
                                  	}
                                  	
                                  	saveConfig();
                                  	//econ.depositPlayer(o, price);
                              	}else {
                              		player.sendMessage(ChatColor.RED+"Shop Out Of Stock");
                              	}
                              	
                              }else {
                              	player.sendMessage(ChatColor.RED+"You too broke");
                              }
                              rntss = rnts;
                              Main.update();
                              event.setCancelled(true);
                          }else if(sign.getLine(0).equalsIgnoreCase("[Server]")){
                              int price = Integer.parseInt(sign.getLine(2));
                              
                              String in = sign.getLine(3);
                              ItemStack is = new ItemStack(Material.getMaterial(in), 1);
                              ItemStack ih = player.getItemInHand();
                              String type = sign.getLine(1);
                              	
                              	if (type.equalsIgnoreCase("Buy")) {
                              		

                                      		if (econ.has(player.getName(), price)) {
                                      			econ.withdraw(player.getName(), price);
                                      			player.sendMessage(ChatColor.GREEN+"You bought "+in+" for $"+price);
                                      			player.getInventory().addItem(is);
                                      			event.setCancelled(true);
                                      			
                                      		}else {
                                      			player.sendMessage(ChatColor.RED+"You dont have any moneys");
                                      			event.setCancelled(true);
                                      			
                                      		}
                              	}else if (type.equalsIgnoreCase("Sell")) {
                              		
                              		if (ih.getType() == Material.getMaterial(in)) {
                              			econ.deposit(player.getName(), price);
                              			player.sendMessage(ChatColor.GREEN+"You sold "+in+" for $"+price);
                              			if (isBusiness(player.getName())) {
                                        		int rv = config.getInt("PlayerData."+player.getName()+".Business.Revenue");
                                        		rv = rv+price;
                                        		config.set("PlayerData."+player.getName()+".Business.Revenue", rv);
                                        	}
                              			player.getInventory().getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
                              			
                              		}else {
                              			player.sendMessage(ChatColor.RED+"You dont have any in your hand");
                              		}
                              		
                              		
                              	}
                              		//econ.deposit(player.getCustomName(), price);
                                  	
                                  	

                              rntss = rnts;
                              Main.update();
                              event.setCancelled(true);
                          }
                  //}else {
                	//  Bukkit.getLogger().log(Level.INFO, player.getName()+" "+event.getAction()+" Not RightClick");
                 //}
            
              }else {
            	  //Bukkit.getLogger().log(Level.INFO, player.getName()+" "+event.getBlock().getType()+" Not Wallsign");
            	  return;
              }
        
        
        
	}
	
	@EventHandler
    public void onPlayerRClickSign(PlayerInteractEvent event){
		rnts = Main.srnts;
        Player player = event.getPlayer();
        loadProperty();
        
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
        	
        	if (event.getPlayer().isOp()) {
        		if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.FEATHER)) {
        			int x = event.getClickedBlock().getLocation().getBlockX();
        			int y = event.getClickedBlock().getLocation().getBlockY();
        			int z = event.getClickedBlock().getLocation().getBlockZ();
        			event.getPlayer().sendMessage("X:" +x+" Y: "+y+" Z:"+z);
        		}
        		
        		
        	}
        	
        	if (debug) {
        		Bukkit.getLogger().log(Level.INFO, "Interact Fired");
        	}
        	if (event.getClickedBlock() == null) {
        		if (debug) {
            		Bukkit.getLogger().log(Level.INFO, "Block Is Null Returning");
            	}
    			return;
    		}else {
    			if (debug) {
            		Bukkit.getLogger().log(Level.INFO, event.getClickedBlock().getType().toString()+" is the Type");
            	}
    		}
    		
    		if (event.getClickedBlock().getLocation().equals(new Location(Bukkit.getWorld("world"), -870, -1, -1447))) {
    			if (debug) {
            		Bukkit.getLogger().log(Level.INFO, "Location check of Stage 1 Quest Chest Passed");
            	}
    			if (event.getClickedBlock().getType().equals(Material.CHEST)) {
    				if (debug) {
    	        		Bukkit.getLogger().log(Level.INFO, "Material Check of Stage 1 Quest Chest Passed");
    	        	}
    				if (config.getInt("PlayerData."+event.getPlayer().getName()+".QuestData.JohnsCard.Completed")==1) {
    					
    					if (config.getInt("PlayerData."+player.getName()+".QuestData.QuestStage")==2) {
    						
    					
    						if (event.getPlayer().getInventory().contains(getQuestPaper("Secovia Mining LTD, Meeting Minutes"))) {
    						
    						}else {
    							event.getPlayer().getInventory().addItem(getQuestPaper("Secovia Mining LTD, Meeting Minutes"));
    							event.getPlayer().sendMessage("The chest contains a stack of papers. They are dusty and seem like they have been there for a while");
    						}
    						
    					}
    				
    				}else {
    					if (debug) {
    		        		Bukkit.getLogger().log(Level.INFO, "Check of Correct Quest Stage Passed, Giving Item");
    		        	}
    				}
    			}
    			
    		}else if (event.getClickedBlock().getLocation().equals(new Location(Bukkit.getWorld("world"), 613, 61, -1830))) {
    			if (debug) {
            		Bukkit.getLogger().log(Level.INFO, "Location check of Stage 4 Quest Chest Passed");
            	}
    			if (event.getClickedBlock().getType().equals(Material.BARREL)) {
    				if (debug) {
    	        		Bukkit.getLogger().log(Level.INFO, "Material Check of Stage 4 Quest Chest Passed");
    	        	}
    				if (config.getInt("PlayerData."+player.getName()+".QuestData.QuestStage")==5) {
    						
    					
    						if (event.getPlayer().getInventory().contains(getQuestPaper("Secovia Mining LTD, ROI Data"))) {
    						
    						}else {
    							config.set("PlayerData."+player.getName()+".SpawnBob", true);
    							saveConfig();
    							event.getPlayer().getInventory().addItem(getQuestPaper("Secovia Mining LTD, ROI Data"));
    							event.getPlayer().sendMessage("The barrel has a bunch of bank statments and a bunc of ROI papers");
    						}
    						
    				
    				}else {
    					if (debug) {
    		        		Bukkit.getLogger().log(Level.INFO, "Check of Correct Quest Stage Passed, Giving Item");
    		        	}
    				}
    			}
    			
    		}else if (event.getClickedBlock().getLocation().equals(new Location(Bukkit.getWorld("world"), -968, 29, -1448))) {
    			
    			if (event.getClickedBlock().getType().equals(Material.CHEST)) {
    				
    				if (config.getInt("QuestData."+player.getName()+".SecoviaMining.Completed")==1) {
    					
    					if (config.getInt("PlayerData."+player.getName()+".QuestData.QuestStage")==3) {
    						
    					
    						if (event.getPlayer().getInventory().contains(getQuestPaper("Secovia Mining LTD, Managers Log"))) {
    						
    						}else {
    							event.getPlayer().getInventory().addItem(getQuestPaper("Secovia Mining LTD, Managers Log"));
    							event.getPlayer().sendMessage("The chest contains a book labeled 'SM Managers log'");
    						}
    						
    					}
    				
    				}else {
    					if (debug) {
    		        		Bukkit.getLogger().log(Level.INFO, "Check of Correct Quest Stage Passed, Giving Item");
    		        	}
    				}
    			}
    			
    		}else{
    			if (debug) {
    				int x = event.getClickedBlock().getLocation().getBlockX();
    				int y = event.getClickedBlock().getLocation().getBlockY();
    				int z = event.getClickedBlock().getLocation().getBlockZ();
            		Bukkit.getLogger().log(Level.INFO, "Location Check Failed. Location of X:"+x+" Y:"+y+" Z:"+z);
            	}
    		}		
        	
        	
    			if (event.getPlayer().getItemInHand().getType().equals(Material.PAPER)) {
    				
    				Player pl = event.getPlayer();
    				
    				if (isOnProperty(event.getPlayer().getLocation())) {
    					
    					Property P = getLocProperty(event.getPlayer().getTargetBlockExact(0).getLocation());
    					
    					pl.sendMessage(ChatColor.GREEN+"Name: "+ChatColor.RED+P.getName());
    					pl.sendMessage(ChatColor.GREEN+"Owner: "+ChatColor.RED+P.getOwner());
    					pl.sendMessage(ChatColor.GREEN+"Min X: "+ChatColor.RED+P.getMinLoc().getBlockX()+ChatColor.GREEN+" Min Z: "+ChatColor.RED+P.getMinLoc().getBlockZ());
    					pl.sendMessage(ChatColor.GREEN+"Max X: "+ChatColor.RED+P.getMaxLoc().getBlockX()+ChatColor.GREEN+" Max Z: "+ChatColor.RED+P.getMaxLoc().getBlockZ());
    					pl.sendMessage(ChatColor.GREEN+"Trusted: ");
    					
    					for (String TR : P.getTrusted()) {
    						pl.sendMessage(ChatColor.RED+""+TR);
    					}

    				}else {
    					pl.sendMessage(ChatColor.RED+"No Property Found.");
    				}
    			
    		}else
        if(event.getClickedBlock().getType().equals(Material.OAK_SIGN)){
            //if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
            //^^ choose actions ^^
        
                Sign sign = (Sign) event.getClickedBlock().getState();
                //^^ .getState(); really important
                if(sign.getLine(0).equalsIgnoreCase("[ForSale]")){
                    int price = Integer.parseInt(sign.getLine(2));
                    String r = sign.getLine(1);
                    String o = sign.getLine(3);
                    Property PR = null;
                    
                    for (Property PRS : prys) {
                    	if (PRS.getName().equals(r)) {
                    		PR = PRS;
                    	}
                    }
                    if (econ.has(player.getName(), price)) {
                    	econ.withdrawPlayer(player.getName(), price);
                    	player.sendMessage(ChatColor.GREEN+"You have bought "+r+" for $"+price);
                    	///rg removeowner -w "world" -a tst123
                    	econ.depositPlayer(o, price);
                    	Bukkit.getPlayer(o).sendMessage(ChatColor.GREEN+"You sold your property for $"+price);
                    	PR.setOwner(player.getName());
                    	updateData();
                    	//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg removeowner "+r+" -w "+player.getWorld().getName()+" -a");
                    	//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg addowner "+r+" "+player.getName()+" -w "+player.getWorld().getName()+"");
                    }else {
                    	player.sendMessage(ChatColor.RED+"You too broke");
                    }
                    event.setCancelled(true);
                }else if(sign.getLine(0).equalsIgnoreCase("[ForRent]")){
                	loadPrys();
                    int price = Integer.parseInt(sign.getLine(2));
                    String r = sign.getLine(1);
                    String o = sign.getLine(3);
                    Property PR = null;
                    
                    for (Property PRS : prys) {
                    	if (PRS.getName().equals(r)) {
                    		PR = PRS;
                    	}
                    }
                    
                    if (PR.rented() == false) {
                    
                    if (econ.has(player.getName(), price)) {
                    	econ.withdrawPlayer(player.getName(), price);
                    	player.sendMessage(ChatColor.GREEN+"You have bought "+r+" for $"+price);
                    	///rg removeowner -w "world" -a tst123
                    	econ.depositPlayer(o, price);
                    	Bukkit.getPlayer(o).sendMessage(ChatColor.GREEN+"You rented your property for $"+price);
                    	PR.addTrusted(player.getName());
                    	PR.setRenter(player.getName());
                    	PR.setRented(true);
                    	updateData();
                    	//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg removeowner "+r+" -w "+player.getWorld().getName()+" -a");
                    	//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg addowner "+r+" "+player.getName()+" -w "+player.getWorld().getName()+"");
                    }else {
                    	player.sendMessage(ChatColor.RED+"You too broke");
                    }
                    rntss = rnts;
                    Main.update();
                    event.setCancelled(true);
                    }else{
                    	player.sendMessage(ChatColor.RED+"Property is already rented");
                    }
                }else if(sign.getLine(0).equalsIgnoreCase("[Shop]")){
                    int price = Integer.parseInt(sign.getLine(2));
                    int ID = Integer.valueOf(sign.getLine(1));
                    String in = sign.getLine(3);
                    List<String> si = config.getStringList("ServerData.Shops."+ID+".ItemInventory");
                    int inv = 0;
                    String o = config.getString("ServerData.ShopOwner."+ID);
                    
                    if (si.contains(in)) {
                    	inv = config.getInt("ServerData.Shops."+ID+".Inventory."+in);
                    }
                    
                    if (econ.has(player.getName(), price)) {
                    	if (inv >= 1) {
                    		econ.withdrawPlayer(player.getName(), price);
                        	player.sendMessage(ChatColor.GREEN+"You have bought "+in+" for $"+price);
                        	ItemStack is = new ItemStack(Material.getMaterial(in), 1);
                        	player.getInventory().addItem(is);
                        	econ.deposit(o, price);
                        	inv = inv-1;
                        	config.set("ServerData.Shops."+ID+".Inventory."+in, inv);
                        	
                        	if (isBusiness(o)) {
                          		int rv = config.getInt("PlayerData."+o+".Business.Revenue");
                          		rv = rv+price;
                          		config.set("PlayerData."+o+".Business.Revenue", rv);
                          	}
                        	
                        	saveConfig();
                        	//econ.depositPlayer(o, price);
                    	}else {
                    		player.sendMessage(ChatColor.RED+"Shop Out Of Stock");
                    	}
                    	
                    }else {
                    	player.sendMessage(ChatColor.RED+"You too broke");
                    }
                    rntss = rnts;
                    Main.update();
                    event.setCancelled(true);
                }else if(sign.getLine(0).equalsIgnoreCase("[Server]")){
                    int price = Integer.parseInt(sign.getLine(2));
                  
                    String in = sign.getLine(3);
                    ItemStack is = new ItemStack(Material.getMaterial(in), 1);
                    ItemStack ih = player.getItemInHand();
                    String type = sign.getLine(1);
                    	
                    	if (type.equalsIgnoreCase("Buy")) {
                    		

                            		if (econ.has(player.getName(), price)) {
                            			econ.withdraw(player.getName(), price);
                            			player.sendMessage(ChatColor.GREEN+"You bought "+in+" for $"+price);
                            			player.getInventory().addItem(is);
                            			return;
                            		}else {
                            			player.sendMessage(ChatColor.RED+"You dont have any moneys");
                            			return;
                            		}
                    	}else if (type.equalsIgnoreCase("Sell")) {
                    		
                    		if (ih.getType() == Material.getMaterial(in)) {
                    			econ.deposit(player.getName(), price);
                    			player.sendMessage(ChatColor.GREEN+"You sold "+in+" for $"+price);
                    			if (isBusiness(player.getName())) {
                              		int rv = config.getInt("PlayerData."+player.getName()+".Business.Revenue");
                              		rv = rv+price;
                              		config.set("PlayerData."+player.getName()+".Business.Revenue", rv);
                              	}
                    			player.getInventory().getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
                    			
                    		}else {
                    			player.sendMessage(ChatColor.RED+"You dont have any in your hand");
                    		}
                    		
                    		
                    	}
                    		//econ.deposit(player.getCustomName(), price);
                        	
                        	

                    rntss = rnts;
                    Main.update();
                    event.setCancelled(true);
                }
               //}
              }else if(event.getClickedBlock().getType().equals(Material.OAK_WALL_SIGN)){
                  //if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
                      //^^ choose actions ^^

                          Sign sign = (Sign) event.getClickedBlock().getState();
                          //^^ .getState(); really important
                          if(sign.getLine(0).equalsIgnoreCase("[ForSale]")){
                              int price = Integer.parseInt(sign.getLine(2));
                              String r = sign.getLine(1);
                              String o = sign.getLine(3);
                              
                              //Bukkit.getLogger().log(Level.INFO, player.getName()+" "+sign.getLine(0)+" Not [ForSale]");
                              
                              if (econ.has(player.getName(), price)) {
                              	econ.withdrawPlayer(player.getName(), price);
                              	player.sendMessage(ChatColor.GREEN+"You have bought "+r+" for $"+price);
                              	econ.depositPlayer(o, price);
                              	Bukkit.getPlayer(o).sendMessage(ChatColor.GREEN+"You sold your property for $"+price);
                            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg removeowner "+r+" -w "+player.getWorld().getName()+" -a ");
                              	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg addowner "+r+" "+player.getName()+" -w "+player.getWorld().getName()+"");
                              }else {
                              	player.sendMessage(ChatColor.RED+"You too broke");
                              }
                              
                              PropertySale ps = new PropertySale(r, player.getName(), o, price);
                              Bukkit.getServer().getPluginManager().callEvent(ps);
                              
                              
                          event.setCancelled(true);
                          }else if(sign.getLine(0).equalsIgnoreCase("[ForRent]")){
                        	  loadPrys();
                              int price = Integer.parseInt(sign.getLine(2));
                              String r = sign.getLine(1);
                              String o = sign.getLine(3);
                              Property PR = null;
                              
                              for (Property PRS : prys) {
                              	if (PRS.getName().equals(r)) {
                              		PR = PRS;
                              	}
                              }
                              
                              if (econ.has(player.getName(), price)) {
                              	econ.withdrawPlayer(player.getName(), price);
                              	player.sendMessage(ChatColor.GREEN+"You have rented "+r+" for $"+price);
                              	///rg removeowner -w "world" -a tst123
                              	econ.depositPlayer(o, price);
                              	Bukkit.getPlayer(o).sendMessage(ChatColor.GREEN+"You rented your property for $"+price);
                              	PR.addTrusted(player.getName());
                              	PR.setRenter(player.getName());
                              	updateData();
                              	//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg removeowner "+r+" -w "+player.getWorld().getName()+" -a");
                              	//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg addowner "+r+" "+player.getName()+" -w "+player.getWorld().getName()+"");
                              }else {
                              	player.sendMessage(ChatColor.RED+"You too broke");
                              }
                              rntss = rnts;
                              Main.update();
                              event.setCancelled(true);
                          }else if(sign.getLine(0).equalsIgnoreCase("[Shop]")){
                              int price = Integer.parseInt(sign.getLine(2));
                              int ID = Integer.valueOf(sign.getLine(1));
                              String in = sign.getLine(3);
                              List<String> si = config.getStringList("ServerData.Shops."+ID+".ItemInventory");
                              int inv = 0;
                              String o = config.getString("ServerData.ShopOwner."+ID);
                              
                              if (si.contains(in)) {
                              	inv = config.getInt("ServerData.Shops."+ID+".Inventory."+in);
                              }
                              
                              if (player.isSneaking()) {
                            	  if (econ.has(player.getName(), price*64)) {
                                    	if (inv >= 64) {
                                    		econ.withdrawPlayer(player.getName(), price*64);
                                        	player.sendMessage(ChatColor.GREEN+"You have bought "+in+" for $"+price*64);
                                        	ItemStack is = new ItemStack(Material.getMaterial(in), 64);
                                        	player.getInventory().addItem(is);
                                        	econ.deposit(o, price*64);
                                        	inv = inv-64;
                                        	config.set("ServerData.Shops."+ID+".Inventory."+in, inv);
                                        	
                                        	if (isBusiness(o)) {
                                        		int rv = config.getInt("PlayerData."+o+".Business.Revenue");
                                        		rv = rv+price;
                                        		config.set("PlayerData."+o+".Business.Revenue", rv);
                                        	}
                                        	
                                        	saveConfig();
                                        	
                                        	//econ.depositPlayer(o, price);
                                    	}else {
                                    		player.sendMessage(ChatColor.RED+"Shop Out Of Stock");
                                    		
                                    	}
                                    	
                                    }else {
                                    	player.sendMessage(ChatColor.RED+"You too broke");
                                    }
                              }else {
                              
                              if (econ.has(player.getName(), price)) {
                              	if (inv >= 1) {
                              		econ.withdrawPlayer(player.getName(), price);
                                  	player.sendMessage(ChatColor.GREEN+"You have bought "+in+" for $"+price);
                                  	ItemStack is = new ItemStack(Material.getMaterial(in), 1);
                                  	player.getInventory().addItem(is);
                                  	econ.deposit(o, price);
                                  	inv = inv-1;
                                  	config.set("ServerData.Shops."+ID+".Inventory."+in, inv);
                                  	
                                  	if (isBusiness(o)) {
                                  		int rv = config.getInt("PlayerData."+o+".Business.Revenue");
                                  		rv = rv+price;
                                  		config.set("PlayerData."+o+".Business.Revenue", rv);
                                  	}
                                  	
                                  	saveConfig();
                                  	
                                  	//econ.depositPlayer(o, price);
                              	}else {
                              		player.sendMessage(ChatColor.RED+"Shop Out Of Stock");
                              		
                              	}
                              	
                              }else {
                              	player.sendMessage(ChatColor.RED+"You too broke");
                              }
                              }
                              rntss = rnts;
                              Main.update();
                              event.setCancelled(true);
                              return;
                              
                          }else if(sign.getLine(0).equalsIgnoreCase("[Server]")){
                              int price = Integer.parseInt(sign.getLine(2));
                              
                              String in = sign.getLine(3);
                              ItemStack is = new ItemStack(Material.getMaterial(in), 1);
                              ItemStack ih = player.getItemInHand();
                              String type = sign.getLine(1);
                              	
                              	if (type.equalsIgnoreCase("Buy")) {
                              		

                                      		if (econ.has(player.getName(), price)) {
                                      			econ.withdraw(player.getName(), price);
                                      			player.sendMessage(ChatColor.GREEN+"You bought "+in+" for $"+price);
                                      			player.getInventory().addItem(is);
                                      			event.setCancelled(true);
                                      			
                                      		}else {
                                      			player.sendMessage(ChatColor.RED+"You dont have any moneys");
                                      			event.setCancelled(true);
                                      			
                                      		}
                              	}else if (type.equalsIgnoreCase("Sell")) {
                              		
                              		if (ih.getType() == Material.getMaterial(in)) {
                              			econ.deposit(player.getName(), price);
                              			player.sendMessage(ChatColor.GREEN+"You sold "+in+" for $"+price);
                              			if (isBusiness(player.getName())) {
                                        		int rv = config.getInt("PlayerData."+player.getName()+".Business.Revenue");
                                        		rv = rv+price;
                                        		config.set("PlayerData."+player.getName()+".Business.Revenue", rv);
                                        	}
                              			player.getInventory().getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
                              			
                              		}else {
                              			player.sendMessage(ChatColor.RED+"You dont have any in your hand");
                              		}
                              		
                              		
                              	}
                              		//econ.deposit(player.getCustomName(), price);
                                  	
                                  	

                              rntss = rnts;
                              Main.update();
                              event.setCancelled(true);
                          }
                  //}else {
                	//  Bukkit.getLogger().log(Level.INFO, player.getName()+" "+event.getAction()+" Not RightClick");
                 //}
            
              }else {
            	  //Bukkit.getLogger().log(Level.INFO, player.getName()+" "+event.getBlock().getType()+" Not Wallsign");
            	  return;
              }
        
        
        
	}else {
    		return;
    	}
	}
	
	
	
	
	
	
	
	@EventHandler

	public void signCreate(SignChangeEvent e)
	{
	    Player player = e.getPlayer();
	    boolean allowed = false;
	    boolean found = false;
	    if (e.getLine(0).equalsIgnoreCase("[ForSale]"))
	    {
	    	String PN = e.getLine(1);
	    	loadPrys();
	       if (player.isOp()) {
	    	   allowed = true;
	    	   for (Property PR : prys) {
	    		   
	    		   if (PR.getName().equalsIgnoreCase(PN)) {
	    			   found = true;
	    			   
	    		   }
	    		   
	    	   }
	       }else {
	    	   
	    	   Property PRR = null;
	    	   for (Property PR : prys) {
	    		   
	    		   if (PR.getName().equalsIgnoreCase(PN)) {
	    			   found = true;
	    			   PRR = PR;
	    		   }
	    		   
	    	   }
	    	   
	    	   if (found) {
	    		   if (PRR.getOwner().equalsIgnoreCase(e.getPlayer().getName())) {
	    			   allowed = true;
			   		}else {
			   			allowed = false;
			   		}
	    	   }
	       }
	    	   if (allowed) {
	    		   if (found) {
		    		   player.sendMessage(ChatColor.RED+"Created Sale Sign for "+ PN);
		    	   }else {
		    		   player.sendMessage(ChatColor.RED+"Property Not Found");
		    	   }
	    	   }else {
	    		   player.sendMessage(ChatColor.RED+"You do not have permission to create ForSale signs for other people");
		    	   e.setCancelled(true);
	    	   }
	    	   
	    	   
	       
	    }else if (e.getLine(0).equalsIgnoreCase("[ForRent]"))
	    {
	    	String PN = e.getLine(1);
	    	loadPrys();
	       if (player.isOp()) {
	    	   allowed = true;
	    	   for (Property PR : prys) {
	    		   
	    		   if (PR.getName().equalsIgnoreCase(PN)) {
	    			   found = true;
	    			   
	    		   }
	    		   
	    	   }
	       }else {
	    	   
	    	   
	    	   for (Property PR : prys) {
	    		   
	    		   if (PR.getName().equalsIgnoreCase(PN)) {
	    			   found = true;
	    			   if (PR.getOwner().equalsIgnoreCase(e.getPlayer().getName())) {
	    				   allowed = true;
	    			   }else {
	    				   allowed = false;
	    			   }
	    		   }
	    		   
	    	   }
	       }
	    	   if (allowed) {
	    		   
	    	   }else {
	    		   player.sendMessage(ChatColor.RED+"You do not have permission to create ForSale signs for other people");
		    	   e.setCancelled(true);
	    	   }
	    	   
	    	   if (found) {
	    		   player.sendMessage(ChatColor.RED+"Created Rent Sign for "+ PN);
	    	   }else {
	    		   player.sendMessage(ChatColor.RED+"Property Not Found");
	    	   }
	       
		    }else if (e.getLine(0).equalsIgnoreCase("[Server]"))
		    {
			       if (player.isOp()) {
			    	   
			       }else {
			    	   player.sendMessage(ChatColor.RED+"You do not have permission to create ForSale signs");
			    	   e.setCancelled(true);
			       }
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
	
	public void saveConfig() {
		
		try {
			Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
	    } catch (Exception e) {
	    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    }
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		
			
		}
/* public String getRegionID(Player p) {
    	
    	
    	
    	Location location = p.getLocation();
    	
    	RegionManager rm = getRegionManager(p.getWorld());

    	ApplicableRegionSet rs = getRegionSet(p.getLocation());
    	
    	 for (ProtectedRegion regions : rs) {
             if (regions.contains(p.getLocation().getBlockX() , p.getLocation().getBlockY(), p.getLocation().getBlockZ())) {
            	 return regions.getId();
             }
    	 }
    	
    	return null;
    }
*/
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
    		PRR.setRented(config.getBoolean("PropertyData."+PR+".Rented"));
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
    		config.set("PropertyData."+PR.getName()+".Rented", PR.rented());


    		
    		if (config.getBoolean("PropertyData."+PR.getName()+".ForSale")) {
    			config.getInt("PropertyData."+PR.getName()+".Price");
    			config.getString("PropertyData."+PR.getName()+".SaleType");
    		}
    		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
    		

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
				
				
				
				if (z > lz) {
					
					
					
					if (x < hx) {
						
						
						
						if (z < hz) {
							
							
							
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
	
	public Property getLocProperty(Location LL) {
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
				
				if (x >= lx) {
					
					
					
					if (z >= lz) {
						
						
						
						if (x <= hx) {
							
							
							if (z <= hz) {
								
								
								
								return PR;
							}else {
								
							}
						}else {
							
						}
					}else{
						
					}
				
				}else {
					
				}
				
			}
			
			
			return null;
		}
	
	public void loadProperty() {
    	
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
    		
    		if (WW == null) {
    			WW = "RPWorld";
    		}
    		
    		Location L1 = new Location(Bukkit.getWorld(WW), MNX, MNY, MNZ);
    		Location L2 = new Location(Bukkit.getWorld(WW), MXX, MXY, MXZ);
    		
    		Property PRR = new Property(NM, ON, L1, L2);
    		
    		PRR.setRented(config.getBoolean("PropertyData."+PR+".Rented"));
    		
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
}
