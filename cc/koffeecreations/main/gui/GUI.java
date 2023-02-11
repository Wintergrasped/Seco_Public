package cc.koffeecreations.main.gui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import cc.koffeecreations.main.Main;
import cc.koffeecreations.main.econ.Business;
import cc.koffeecreations.main.econ.Econ;
import cc.koffeecreations.main.econ.Employee;
import cc.koffeecreations.main.events.stockPurchaseEvent;
import cc.koffeecreations.main.events.stockSaleEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.ChatColor;

public class GUI implements Listener {

	public Main main = (Main) Bukkit.getPluginManager().getPlugin("STSEcon");
	public FileConfiguration config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
	public Econ econ = new Econ();
	
	public DecimalFormat price = new DecimalFormat("0.00");
	
	public double psp;
	public double farmingPRC;
	public double miningPRC;
	public double loggingPRC;
	
	public Business CB;
	
	private final Inventory market_menu;
	private final Inventory ps_menu;
	private final Inventory is_main_menu;
	private final Inventory is_farming_menu;
	private final Inventory is_logging_menu;
	private final Inventory is_mining_menu;
	private final Inventory real_estate_menu;
	private final Inventory rent_menu;
	private final Inventory buy_menu;
	private final Inventory bus_menu;
	private final Inventory bstk_menu;
	private final Inventory bs_menu;
	private final Inventory emp_menu;
	private final Inventory emps_menu;
	private final Inventory lic_menu;

	
	public String prf = ChatColor.AQUA+"["+ChatColor.GREEN+"STS Stocks"+ChatColor.AQUA+"] ";
	
	public GUI() {
		// Create a new inventory, with no owner (as this isn't a real inventory), a
		// size of nine, called example
		market_menu = Bukkit.createInventory(null, 9, "Stock Market");
		ps_menu = Bukkit.createInventory(null, 9, "Player Stock Market");
		is_main_menu = Bukkit.createInventory(null, 9, "Industry Stock Market");
		is_farming_menu = Bukkit.createInventory(null, 9, "Farming Stock Market");
		is_logging_menu = Bukkit.createInventory(null, 9, "Logging Stock Market");
		is_mining_menu = Bukkit.createInventory(null, 9, "Mining Stock Market");
		real_estate_menu = Bukkit.createInventory(null, 9, "Real Estate Market");
		bus_menu = Bukkit.createInventory(null, 9, "Business Control Center");
		bstk_menu = Bukkit.createInventory(null, 9, "Business Stocks");
		bs_menu = Bukkit.createInventory(null, 9, "Business Stocks");
		rent_menu = Bukkit.createInventory(null, 9, "Rental Market");
		buy_menu = Bukkit.createInventory(null, 9, "Housing Market");
		emp_menu = Bukkit.createInventory(null, 9, "Employee Control");
		emps_menu = Bukkit.createInventory(null, 9, "Employee Control");
		lic_menu = Bukkit.createInventory(null, 9, "Licensing Menu");
		
		
		psp = psp = config.getDouble("ServerData.PSP");
		miningPRC = config.getDouble("ServerData.MiningStockPrice");
		loggingPRC = config.getDouble("ServerData.LoggingStockPrice");
		farmingPRC = config.getDouble("ServerData.FarmingStockPrice");

		// Put the items into the inventory
		initializeItems();
		
	}

	// You can call this whenever you want to put the items in
	public void initializeItems() {
		loadBusiness();
		main = (Main) Bukkit.getPluginManager().getPlugin("STSEcon");
		config = main.getConfig();
		psp = psp = config.getDouble("ServerData.PSP");
		miningPRC = config.getDouble("ServerData.MiningStockPrice");
		loggingPRC = config.getDouble("ServerData.LoggingStockPrice");
		farmingPRC = config.getDouble("ServerData.FarmingStockPrice");
		market_menu.addItem(createGuiItem(Material.DIAMOND_SWORD, "�bPlayer Stocks", "�aOpen The Player Stock Market", ""));
		market_menu.addItem(createGuiItem(Material.IRON_SWORD, "�bIndustry Stocks", "�aOpen The Industry Stock Market", ""));
		market_menu.addItem(createGuiItem(Material.GOLD_INGOT, "�bBusines Stocks", "�aOpen The Busines Stock Market", ""));

		ps_menu.addItem(createGuiItem(Material.IRON_SWORD, "�bBuy 1 Share", "�aBuys 1 share of Player Stock", ""));
		ps_menu.addItem(createGuiItem(Material.IRON_SWORD, "�bSell 1 Share", "�aSells 1 share of Player Stock", ""));
		ps_menu.addItem(createGuiItem(Material.RED_BED, "�bBack", "�aBack to Main Menu", ""));
		
		is_main_menu.addItem(createGuiItem(Material.WHEAT, "�bFarming", "�aOpen the Farming Market", ""));
		is_main_menu.addItem(createGuiItem(Material.OAK_LOG, "�bLogging", "�aOpen the Logging Marke", ""));
		is_main_menu.addItem(createGuiItem(Material.DIAMOND_ORE, "�bMining", "�aOpen the Mining Marke", ""));
		is_main_menu.addItem(createGuiItem(Material.RED_BED, "�bBack", "�aBack to Main Menu", ""));
		
		is_farming_menu.addItem(createGuiItem(Material.WHEAT_SEEDS, "�bBuy 1 Share", "�aBuys 1 share of Farming Stock", ""));
		is_farming_menu.addItem(createGuiItem(Material.WHEAT, "�bSell 1 Share", "�aSells 1 share of Farming Stock", ""));
		is_farming_menu.addItem(createGuiItem(Material.RED_BED, "�bBack", "�aBack to Main Menu", ""));
		
		is_logging_menu.addItem(createGuiItem(Material.OAK_SAPLING, "�bBuy 1 Share", "�aBuys 1 share of Logging Stock", ""));
		is_logging_menu.addItem(createGuiItem(Material.OAK_PLANKS, "�bSell 1 Share", "�aSells 1 share of Logging Stock", ""));
		is_logging_menu.addItem(createGuiItem(Material.RED_BED, "�bBack", "�aBack to Main Menu", ""));
		
		is_mining_menu.addItem(createGuiItem(Material.IRON_PICKAXE, "�bBuy 1 Share", "�aBuys 1 share of Mining Stock", ""));
		is_mining_menu.addItem(createGuiItem(Material.COBBLESTONE, "�bSell 1 Share", "�aSells 1 share of Mining Stock", ""));
		is_mining_menu.addItem(createGuiItem(Material.RED_BED, "�bBack", "�aBack to Main Menu", ""));
		
		bus_menu.addItem(createGuiItem(Material.PAPER, "�bLicensing Menu", "�aOpens Licensing Menu", ""));
		bus_menu.addItem(createGuiItem(Material.BEEHIVE, "�bEmployee Menu", "�aOpens Employee Menu", ""));
		bus_menu.addItem(createGuiItem(Material.BOOK, "�bStocks Menu", "�aOpens Stock Menu", ""));
		
		//bstk_menu
		for (Business B : main.bls) {
			String in = config.getString("PlayerData."+B.getOwner()+".Business.Industry");
			
			if (in.equalsIgnoreCase("farming")) {
				bstk_menu.addItem(createGuiItem(Material.WHEAT, "�b"+B.getOwner()+" Stocks Menu", "�aOpens the Business Stocks Menu", ""));
			}else if (in.equalsIgnoreCase("mining")) {
				bstk_menu.addItem(createGuiItem(Material.IRON_ORE, "�b"+B.getOwner()+" Stocks Menu", "�aOpens the Business Stocks Menu", ""));
			}else if (in.equalsIgnoreCase("logging")) {
				bstk_menu.addItem(createGuiItem(Material.OAK_LOG, "�b"+B.getOwner()+" Stocks Menu", "�aOpens the Business Stocks Menu", ""));
			}else {
				bstk_menu.addItem(createGuiItem(Material.STONE, "�b"+B.getOwner()+" Stocks Menu", "�aOpens the Business Stocks Menu", ""));
			}
		}
		if (!main.bls.isEmpty()) {
			CB = main.bls.get(0);
		}
		
		if (CB == null) {
			CB = new Business("None", 0, 0, 0, 0, 0);
		}
		
		bs_menu.addItem(createGuiItem(Material.BOOK, "�bBuy 1 Share", "�aBuys 1 share", ""));
		bs_menu.addItem(createGuiItem(Material.BLAZE_ROD, "�bSell 1 Share", "�aSells 1 share", ""));
		
		lic_menu.addItem(createGuiItem(Material.WRITABLE_BOOK, "�bBuy Business License", "�aBuy a Business License", ""));
		lic_menu.addItem(createGuiItem(Material.WRITABLE_BOOK, "�bUpgrade Business License", "�aBuy a Business License", ""));
		
		emp_menu.addItem(createGuiItem(Material.BOOK, "�bHire Employee", "�aHire an employee", ""));
		
		
	}

	public void employeeMenuList(String Name) {
		if (isBus(Name)) {
			List<Employee> emps = getEmployees(Name);
			
			for (Employee E : emps) {
				emps_menu.addItem(createGuiItem(Material.BOOK, "�bEmployee ID: "+E.getID(), "�aSalary: "+E.getSalary(), "�aRevenue: "+E.getRevenue()));
			}
		
		}
	}
	
	public void updateMenu() {
		main = (Main) Bukkit.getPluginManager().getPlugin("STSEcon");
		config = main.getConfig();
		psp = psp = config.getDouble("ServerData.PSP");
		miningPRC = config.getDouble("ServerData.MiningStockPrice");
		loggingPRC = config.getDouble("ServerData.LoggingStockPrice");
		farmingPRC = config.getDouble("ServerData.FarmingStockPrice");
		market_menu.clear();
		
		market_menu.addItem(createGuiItem(Material.DIAMOND_SWORD, "�bPlayer Stocks", "�aOpen The Player Stock Market", ""));
		market_menu.addItem(createGuiItem(Material.IRON_SWORD, "�bIndustry Stocks", "�aOpen The Industry Stock Market", ""));
		market_menu.addItem(createGuiItem(Material.GOLD_INGOT, "�bBusines Stocks", "�aOpen The Busines Stock Market", ""));
		
		ps_menu.clear();
		ps_menu.addItem(createGuiItem(Material.IRON_SWORD, "�bBuy 1 Share", "�aBuys 1 share of Player Stock", "�a $"+price.format(psp)));
		ps_menu.addItem(createGuiItem(Material.IRON_SWORD, "�bSell 1 Share", "�aSells 1 share of Player Stock", "�a $"+price.format(psp)));
		ps_menu.addItem(createGuiItem(Material.RED_BED, "�bBack", "�aBack to Main Menu", ""));
		
		is_main_menu.clear();
		is_main_menu.addItem(createGuiItem(Material.WHEAT, "�bFarming", "�aOpen the Farming Market", ""));
		is_main_menu.addItem(createGuiItem(Material.OAK_LOG, "�bLogging", "�aOpen the Logging Marke", ""));
		is_main_menu.addItem(createGuiItem(Material.DIAMOND_ORE, "�bMining", "�aOpen the Mining Marke", ""));
		is_main_menu.addItem(createGuiItem(Material.RED_BED, "�bBack", "�aBack to Main Menu", ""));
		
		is_farming_menu.clear();
		is_farming_menu.addItem(createGuiItem(Material.WHEAT_SEEDS, "�bBuy 1 Share", "�aBuys 1 share of Farming Stock", "�a $"+price.format(farmingPRC)));
		is_farming_menu.addItem(createGuiItem(Material.WHEAT, "�bSell 1 Share", "�aSells 1 share of Farming Stock", "�a $"+price.format(farmingPRC)));
		is_farming_menu.addItem(createGuiItem(Material.RED_BED, "�bBack", "�aBack to Main Menu", ""));
		
		is_logging_menu.clear();
		is_logging_menu.addItem(createGuiItem(Material.OAK_SAPLING, "�bBuy 1 Share", "�aBuys 1 share of Logging Stock", "�a $"+price.format(loggingPRC)));
		is_logging_menu.addItem(createGuiItem(Material.OAK_PLANKS, "�bSell 1 Share", "�aSells 1 share of Logging Stock", "�a $"+price.format(loggingPRC)));
		is_logging_menu.addItem(createGuiItem(Material.RED_BED, "�bBack", "�aBack to Main Menu", ""));
		
		is_mining_menu.clear();
		is_mining_menu.addItem(createGuiItem(Material.IRON_PICKAXE, "�bBuy 1 Share", "�aBuys 1 share of Mining Stock", "�a $"+price.format(miningPRC)));
		is_mining_menu.addItem(createGuiItem(Material.COBBLESTONE, "�bSell 1 Share", "�aSells 1 share of Mining Stock", "�a $"+price.format(miningPRC)));
		is_mining_menu.addItem(createGuiItem(Material.RED_BED, "�bBack", "�aBack to Main Menu", ""));
		
		bus_menu.clear();
		bus_menu.addItem(createGuiItem(Material.PAPER, "�bLicensing Menu", "�aOpens Licensing Menu", ""));
		bus_menu.addItem(createGuiItem(Material.BEEHIVE, "�bEmployee Menu", "�aOpens Employee Menu", ""));
		bus_menu.addItem(createGuiItem(Material.BOOK, "�bStocks Menu", "�aOpens Stock Menu", ""));
		
		bstk_menu.clear();
		
		if(main.bls == null) {
			
		}else {
		for (Business B : main.bls) {
			String in = config.getString("PlayerData."+B.getOwner()+".Business.Industry");
			
			if (in.equalsIgnoreCase("farming")) {
				bstk_menu.addItem(createGuiItem(Material.WHEAT, "�b"+B.getOwner()+" Stocks Menu", "�aOpens the Business Stocks Menu", ""));
			}else if (in.equalsIgnoreCase("mining")) {
				bstk_menu.addItem(createGuiItem(Material.IRON_ORE, "�b"+B.getOwner()+" Stocks Menu", "�aOpens the Business Stocks Menu", ""));
			}else if (in.equalsIgnoreCase("logging")) {
				bstk_menu.addItem(createGuiItem(Material.OAK_LOG, "�b"+B.getOwner()+" Stocks Menu", "�aOpens the Business Stocks Menu", ""));
			}else {
				bstk_menu.addItem(createGuiItem(Material.STONE, "�b"+B.getOwner()+" Stocks Menu", "�aOpens the Business Stocks Menu", ""));
			}
		}
		}
		bs_menu.clear();
		bs_menu.addItem(createGuiItem(Material.BOOK, "�bBuy 1 Share", "�aBuys 1 share for $"+price.format(CB.getStockPrice()), ""));
		bs_menu.addItem(createGuiItem(Material.BLAZE_ROD, "�bSell 1 Share", "�aSells 1 share for $"+price.format(CB.getStockPrice()), ""));
		
		lic_menu.clear();
		lic_menu.addItem(createGuiItem(Material.WRITABLE_BOOK, "�bBuy Business License", "�aBuy a Business License", ""));
		lic_menu.addItem(createGuiItem(Material.WRITABLE_BOOK, "�bUpgrade Business License", "�aBuy a Business License", ""));
		
		emp_menu.clear();
		emp_menu.addItem(createGuiItem(Material.BOOK, "�bHire Employee", "�aHire an employee", ""));
	}
	
	// Nice little method to create a gui item with a custom name, and description
	protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
		final ItemStack item = new ItemStack(material, 1);
		final ItemMeta meta = item.getItemMeta();

		// Set the name of the item
		meta.setDisplayName(name);

		// Set the lore of the item
		meta.setLore(Arrays.asList(lore));

		item.setItemMeta(meta);

		return item;
	}

	// You can open the inventory with this
	public void openMarketInventory(final HumanEntity ent) {
		updateMenu();
		ent.openInventory(market_menu);
	}
	
	public void openPSInventory(final HumanEntity ent) {
		updateMenu();
		ent.openInventory(ps_menu);
	}
	
	public void openISInventory(final HumanEntity ent) {
		updateMenu();
		ent.openInventory(is_main_menu);
	}
	
	public void openISFarmingInventory(final HumanEntity ent) {
		updateMenu();
		ent.openInventory(is_farming_menu);
	}
	
	public void openISLoggingInventory(final HumanEntity ent) {
		updateMenu();
		ent.openInventory(is_logging_menu);
	}
	
	public void openISMiningInventory(final HumanEntity ent) {
		updateMenu();
		ent.openInventory(is_mining_menu);
	}
	
	public void openBusInventory(final HumanEntity ent) {
		updateMenu();
		ent.openInventory(bus_menu);
	}
	
	public void openEmpInventory(final HumanEntity ent) {
		updateMenu();
		ent.openInventory(emp_menu);
	}
	
	public void openEmpsInventory(final HumanEntity ent) {
		updateMenu();
		ent.openInventory(emps_menu);
	}
	
	public void openLicInventory(final HumanEntity ent) {
		updateMenu();
		ent.openInventory(lic_menu);
	}
	
	public void openBSTKInventory(final HumanEntity ent) {
		updateMenu();
		ent.openInventory(bstk_menu);
	}
	
	public void openBSInventory(final HumanEntity ent) {
		updateMenu();
		ent.openInventory(bs_menu);
	}

	// Check for clicks on items
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent e) {
		// if (e.getInventory() != market_menu) return;

		if (e.getInventory() == market_menu) {
			e.setCancelled(true);

			final ItemStack clickedItem = e.getCurrentItem();

			// verify current item is not null
			if (clickedItem == null || clickedItem.getType().isAir())
				return;

			final Player p = (Player) e.getWhoClicked();

			// Using slots click is a best option for your inventory click's
			//p.sendMessage("You clicked at slot " + e.getRawSlot());

			if (e.getRawSlot() == 0) {
				// TODO Open PlayerStock GUI
				openPSInventory(p);
			} else if (e.getRawSlot() == 1) {
				// TODO Open Industry Stock GUI
				openISInventory(p);
			} else if (e.getRawSlot() == 2) {
				// TODO Open Industry Stock GUI
				openBSTKInventory(p);
			}
		} else if (e.getInventory() == ps_menu) {
			
			final ItemStack clickedItem = e.getCurrentItem();

			// verify current item is not null
			if (clickedItem == null || clickedItem.getType().isAir())
				return;

			final Player p = (Player) e.getWhoClicked();

			// Using slots click is a best option for your inventory click's
			//p.sendMessage("You clicked at slot " + e.getRawSlot());

			if (e.getRawSlot() == 0) {
				// TODO Buy 1 PlayerStock
				buyPS(p,1);
				
			} else if (e.getRawSlot() == 1) {
				// TODO Sell 1 PlayerStock
				sellPS(p,1);
			} else if (e.getRawSlot() == 2) {
				// TODO Open Industry Stock GUI
				openMarketInventory(p);
			}
			e.setCancelled(true);

		} else if (e.getInventory() == is_main_menu) {
			
			final ItemStack clickedItem = e.getCurrentItem();

			// verify current item is not null
			if (clickedItem == null || clickedItem.getType().isAir())
				return;

			final Player p = (Player) e.getWhoClicked();

			// Using slots click is a best option for your inventory click's
			//p.sendMessage("You clicked at slot " + e.getRawSlot());

			if (e.getRawSlot() == 0) {
				// TODO Buy 1 PlayerStock
				openISFarmingInventory(p);
				
			} else if (e.getRawSlot() == 1) {
				// TODO Sell 1 PlayerStock
				openISLoggingInventory(p);
				
			} else if (e.getRawSlot() == 2) {
				// TODO Sell 1 PlayerStock
				openISMiningInventory(p);
				
			} else if (e.getRawSlot() == 3) {
				// TODO Open Industry Stock GUI
				openMarketInventory(p);
			}
			e.setCancelled(true);

		} else if (e.getInventory() == is_farming_menu) {
			
			final ItemStack clickedItem = e.getCurrentItem();

			// verify current item is not null
			if (clickedItem == null || clickedItem.getType().isAir())
				return;

			final Player p = (Player) e.getWhoClicked();

			// Using slots click is a best option for your inventory click's
			//p.sendMessage("You clicked at slot " + e.getRawSlot());

			if (e.getRawSlot() == 0) {
				// TODO Buy 1 PlayerStock
				buyIS(p,"farming",1);
				
			} else if (e.getRawSlot() == 1) {
				// TODO Sell 1 PlayerStock
				sellIS(p,"farming",1);
			} else if (e.getRawSlot() == 2) {
				// TODO Open Industry Stock GUI
				openMarketInventory(p);
			}
			e.setCancelled(true);

		} else if (e.getInventory() == is_logging_menu) {
			final ItemStack clickedItem = e.getCurrentItem();

			// verify current item is not null
			if (clickedItem == null || clickedItem.getType().isAir())
				return;

			final Player p = (Player) e.getWhoClicked();

			// Using slots click is a best option for your inventory click's
			//p.sendMessage("You clicked at slot " + e.getRawSlot());

			if (e.getRawSlot() == 0) {
				// TODO Buy 1 PlayerStock
				buyIS(p,"logging",1);
				
			} else if (e.getRawSlot() == 1) {
				// TODO Sell 1 PlayerStock
				sellIS(p,"logging",1);
			} else if (e.getRawSlot() == 2) {
				// TODO Open Industry Stock GUI
				openMarketInventory(p);
			}
			e.setCancelled(true);

		} else if (e.getInventory() == is_mining_menu) {
			final ItemStack clickedItem = e.getCurrentItem();

			// verify current item is not null
			if (clickedItem == null || clickedItem.getType().isAir())
				return;

			final Player p = (Player) e.getWhoClicked();

			// Using slots click is a best option for your inventory click's
			//p.sendMessage("You clicked at slot " + e.getRawSlot());

			if (e.getRawSlot() == 0) {
				// TODO Buy 1 PlayerStock
				buyIS(p,"mining",1);
				
			} else if (e.getRawSlot() == 1) {
				// TODO Sell 1 PlayerStock
				sellIS(p,"mining",1);
			} else if (e.getRawSlot() == 2) {
				// TODO Open Industry Stock GUI
				openMarketInventory(p);
			}
			e.setCancelled(true);

		} else if (e.getInventory() == bus_menu) {
			final ItemStack clickedItem = e.getCurrentItem();

			// verify current item is not null
			if (clickedItem == null || clickedItem.getType().isAir())
				return;

			final Player p = (Player) e.getWhoClicked();

			// Using slots click is a best option for your inventory click's
			//p.sendMessage("You clicked at slot " + e.getRawSlot());

			if (e.getRawSlot() == 0) {
				// TODO Buy 1 PlayerStock
				openLicInventory(p);
				
			} else if (e.getRawSlot() == 1) {
				// TODO Sell 1 PlayerStock
				openEmpInventory(p);
			} else if (e.getRawSlot() == 2) {
				// TODO Open Industry Stock GUI
				//openMarketInventory(p);
			}
			e.setCancelled(true);

		} else if (e.getInventory() == emp_menu) {
			final ItemStack clickedItem = e.getCurrentItem();

			// verify current item is not null
			if (clickedItem == null || clickedItem.getType().isAir())
				return;

			final Player p = (Player) e.getWhoClicked();

			// Using slots click is a best option for your inventory click's
			//p.sendMessage("You clicked at slot " + e.getRawSlot());

			if (e.getRawSlot() == 0) {
				// TODO Hire Employee
				
			} else if (e.getRawSlot() == 1) {
				// TODO List Employees
				employeeMenuList(p.getName());
				openEmpsInventory(p);
			} else if (e.getRawSlot() == 2) {
				// TODO Open Industry Stock GUI
				//openMarketInventory(p);
			}
			e.setCancelled(true);

		} else if (e.getInventory() == bs_menu) {
			final ItemStack clickedItem = e.getCurrentItem();

			// verify current item is not null
			if (clickedItem == null || clickedItem.getType().isAir())
				return;

			final Player p = (Player) e.getWhoClicked();
			e.setCancelled(true);
			// Using slots click is a best option for your inventory click's
			//p.sendMessage("You clicked at slot " + e.getRawSlot());

			if (e.getRawSlot() == 0) {
				buyBusinessStock(Bukkit.getPlayer(e.getWhoClicked().getName()), CB);
				
			} else if (e.getRawSlot() == 1) {
				sellBusinessStock(Bukkit.getPlayer(e.getWhoClicked().getName()), CB);

			}
			e.setCancelled(true);

		} else if (e.getInventory() == bstk_menu) {
			final ItemStack clickedItem = e.getCurrentItem();

			// verify current item is not null
			if (clickedItem == null || clickedItem.getType().isAir())
				return;

			final Player p = (Player) e.getWhoClicked();

			// Using slots click is a best option for your inventory click's
			//p.sendMessage("You clicked at slot " + e.getRawSlot());
			
			CB = main.bls.get(e.getRawSlot());

				openBSInventory(p);
			e.setCancelled(true);

		} else if (e.getInventory() == lic_menu) {
			final ItemStack clickedItem = e.getCurrentItem();

			// verify current item is not null
			if (clickedItem == null || clickedItem.getType().isAir())
				return;

			final Player p = (Player) e.getWhoClicked();

			// Using slots click is a best option for your inventory click's
			//p.sendMessage("You clicked at slot " + e.getRawSlot());

			if (e.getRawSlot() == 0) {
				// TODO Buy Business License
				
			} else if (e.getRawSlot() == 1) {
				// TODO Upgrade Business License
			} 
			e.setCancelled(true);

		} else {
			return;
		}
	}

	private void sellBusinessStock(Player P,Business cB2) {
		String b = cB2.getOwner();

		Player p = P;
		String n = p.getName();
		int rev = config.getInt("PlayerData."+b+".Business.Revenue");
		int size = config.getInt("PlayerData."+b+".Business.Employee.Count")+1;
		int oc = config.getInt("PlayerData."+b+".Business.OperationCost");
		int sh = config.getInt("PlayerData."+b+".Business.Shares");
		List<String> shs = config.getStringList("PlayerData."+b+".Business.ShareHolders");
		int cs = 0;
		
		int profit = rev-oc;
		
		Business bs = new Business(b, rev, oc, profit, sh, size);
	if (config.getInt("PlayerData."+n+".Stocks.Business."+bs.getOwner()+".shares") >= 1) {	
		econ.depositPlayer(n, bs.getStockPrice());
	
		stockSaleEvent spe = new stockSaleEvent(p, "PlayerStocks", 1, psp);
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
		p.sendMessage(prf+ChatColor.GREEN+"You sold 1 share of "+bs.getOwner()+"'s business for $"+price.format(bs.getStockPrice()));
		Player bo = Bukkit.getPlayer(b);
		
		if (bo.isOnline()) {
			Bukkit.getPlayer(b).sendMessage(ChatColor.GREEN+""+n+" Just sold 1 share of your business!");
		}
		bs.setRev(bs.getRev()-bs.getStockPrice());
		config.set("PlayerData."+b+".Business.Revenue", rev-bs.getStockPrice());
		config.set("PlayerData."+b+".Business.Shares", sh-1);
		econ.withdrawPlayer(b, bs.getStockPrice()/2);
	}else {
		
		p.sendMessage(ChatColor.RED+" You dont have any shares in this");
		
	}
	}

	private void buyBusinessStock(Player P,Business cB2) {
	
		String b = cB2.getOwner();
		Player p = P;
		String n = p.getName();
		int rev = config.getInt("PlayerData."+b+".Business.Revenue");
		int size = config.getInt("PlayerData."+b+".Business.Employee.Count")+1;
		int oc = config.getInt("PlayerData."+b+".Business.OperationCost");
		int sh = config.getInt("PlayerData."+b+".Business.Shares");
		List<String> shs = config.getStringList("PlayerData."+b+".Business.ShareHolders");
		int cs = 0;
		
		int profit = rev-oc;
		
		Business bs = new Business(b, rev, oc, profit, sh, size);
	
		if (econ.has(p.getName(), bs.getStockPrice())) {
			econ.withdrawPlayer(p.getName(), bs.getStockPrice());
			stockPurchaseEvent spe = new stockPurchaseEvent(p, "PlayerStocks", 1, psp);
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
				Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
		    } catch (Exception e) {
		    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
		    }
			p.sendMessage(prf+ChatColor.GREEN+"You bought 1 share of "+bs.getOwner()+"'s business for $"+price.format(bs.getStockPrice()));
			
			Player bo = Bukkit.getPlayer(b);
			
			if (bo.isOnline()) {
			Bukkit.getPlayer(b).sendMessage(ChatColor.GREEN+""+n+" Just bought 1 share of your business!");
			}
			bs.setRev(bs.getRev()+bs.getStockPrice());
			config.set("PlayerData."+b+".Business.Revenue", rev+bs.getStockPrice());
			config.set("PlayerData."+b+".Business.Shares", sh+1);
			econ.depositPlayer(b, bs.getStockPrice()/2);
			try {
				Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
		    } catch (Exception e) {
		    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
		    }
	}
		
	}
	// Cancel dragging in our inventory
	@EventHandler
	public void onInventoryClick(final InventoryDragEvent e) {
		if (e.getInventory().equals(market_menu)) {
			e.setCancelled(true);
		}
	}
	
	public void buyPS(Player p, int ss) {
    	String n = p.getName();
    	if (econ.has(p.getName(), psp)) {
			int s = config.getInt("PlayerStocks.PlayerData."+n+".Shares");
			config.set("PlayerStocks.PlayerData."+n+".Shares", s+ss);
			econ.withdrawPlayer(p.getName(), psp*ss);
			p.sendMessage(ChatColor.GOLD+"You purchased "+ss+" shares of Player Stocks for $"+price.format(psp*ss));
			try {
				Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
		    } catch (Exception e) {
		    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
		    }
		}else {
			p.sendMessage(ChatColor.RED+"Not Enough Moneys!");
		}
    }
    
    public void sellPS(Player p, int ss) {
    	String n = p.getName();
    	int s = config.getInt("PlayerStocks.PlayerData."+n+".Shares");
    	if (s >= ss) {
			
			config.set("PlayerStocks.PlayerData."+n+".Shares", s-ss);
			econ.depositPlayer(p.getName(), psp*ss);
			p.sendMessage(ChatColor.GOLD+"You sold "+ss+" shares of Player Stocks for $"+price.format(psp*ss));
			try {
				Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
		    } catch (Exception e) {
		    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
		    }
		}else {
			p.sendMessage(ChatColor.RED+"Not Enough Moneys!");
		}
    }
    
    public void buyIS(Player p,String i, int ss) {
    	String n = p.getName();
    	
    	int lm = config.getInt("PlayerData."+p.getName()+".Stocks.Shares.Lumber");
		int lms = config.getInt("PlayerData."+p.getName()+".Stocks.Shares.Logging");
		int mm = config.getInt("PlayerData."+p.getName()+".Stocks.Shares.Mining");
		int fm = config.getInt("PlayerData."+p.getName()+".Stocks.Shares.Farming");
		
    	
    	if (i == "farming") {
    		if (econ.has(p.getName(), ss*farmingPRC)) {
    			config.set("PlayerData."+p.getName()+".Stocks.Shares.Farming", fm+ss);
    			econ.withdraw(p.getName(), ss*farmingPRC);
    			p.sendMessage(ChatColor.GOLD+"You bought 1 share of Farming");
    		}
    	}else if (i == "logging") {
    		if (econ.has(p.getName(), ss*loggingPRC)) {
    			config.set("PlayerData."+p.getName()+".Stocks.Shares.Logging", lms+ss);
    			econ.withdraw(p.getName(), ss*loggingPRC);
    			p.sendMessage(ChatColor.GOLD+"You bought 1 share of Logging");
    		}
    	}if (i == "mining") {
    		if (econ.has(p.getName(), ss*miningPRC)) {
    			config.set("PlayerData."+p.getName()+".Stocks.Shares.Mining", mm+ss);
    			econ.withdraw(p.getName(), ss*miningPRC);
    			p.sendMessage(ChatColor.GOLD+"You bought 1 share of Mining");
    		}
    	}
    }
    
    public void sellIS(Player p,String i, int ss) {
    	String n = p.getName();
    	
    	int lm = config.getInt("PlayerData."+p.getName()+".Stocks.Shares.Lumber");
		int lms = config.getInt("PlayerData."+p.getName()+".Stocks.Shares.Logging");
		int mm = config.getInt("PlayerData."+p.getName()+".Stocks.Shares.Mining");
		int fm = config.getInt("PlayerData."+p.getName()+".Stocks.Shares.Farming");
		
    	
    	if (i == "farming") {
    		if (fm >= ss) {
    			config.set("PlayerData."+p.getName()+".Stocks.Shares.Farming", fm-ss);
    			econ.deposit(p.getName(), ss*farmingPRC);
    			p.sendMessage(ChatColor.GOLD+"You sold 1 share of Farming");
    		}
    	}else if (i == "logging") {
    		if (lms >= ss) {
    			config.set("PlayerData."+p.getName()+".Stocks.Shares.Logging", lms-ss);
    			econ.deposit(p.getName(), ss*loggingPRC);
    			p.sendMessage(ChatColor.GOLD+"You sold 1 share of Logging");
    		}
    	}if (i == "mining") {
    		if (mm >= ss) {
    			config.set("PlayerData."+p.getName()+".Stocks.Shares.Mining", mm-ss);
    			econ.deposit(p.getName(), ss*miningPRC);
    			p.sendMessage(ChatColor.GOLD+"You sold 1 share of Mining");
    		}
    	}
    }
    
    public boolean isBus(String Name) {
    	
    	List<String> bs = config.getStringList("PlayerData.BusinessOwners");
    	
    	if (bs.contains(Name)) {
    		return true; 
    	}else { 
    		return false;
    	}
    	
    }
    
    public Business getBus(String Name) {
		
    	Business bsl = null;
    	List<String> bso = config.getStringList("PlayerData.BusinessOwners");
    	for (String b : bso) {
    		
    		if (b.equalsIgnoreCase(Name)) {
    			
    		
    		int rev = config.getInt("PlayerData."+b+".Business.Revenue");
    		int size = config.getInt("PlayerData."+b+".Business.Employee.Count")+1;
    		int oc = config.getInt("PlayerData."+b+".Business.OperationCost");
    		
    		int profit = rev-oc;
    		
    		bsl = new Business(b, rev, oc, profit, 0, size);
    		return bsl;
    	}
    		//sender.sendMessage(prf+ChatColor.RED+""+b+ChatColor.GREEN+" Size: "+ChatColor.RED+price.format(size)+ChatColor.GREEN+" Revenue: "+ChatColor.RED+price.format(rev)+ChatColor.GREEN+" Costs: "+ChatColor.RED+price.format(oc)+ChatColor.GREEN+" Profit: "+ChatColor.RED+price.format(profit));
    		
    	}
    	return bsl;
    }
    
    public List<Employee> getEmployees(String Name) {
    	List<Integer> ids = config.getIntegerList("PlayerData."+Name+".Business.EmployeeID.List");
    	List<Employee> em = new ArrayList();
    	for (int ei : ids) {
			int sal = config.getInt("PlayerData."+Name+".Business.Employees."+ei+".Salary");
			int rev = config.getInt("PlayerData."+Name+".Business.Employees."+ei+".Revenue");
			Employee E = new Employee(ei, sal, rev);
			em.add(E);
		}
    	return em;
    }
    
	public void loadBusiness() {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		for (OfflinePlayer P : Bukkit.getOfflinePlayers()) {
		
		if (isBus(P.getName())) {
			
			String b = P.getName();
			
			int rev = config.getInt("PlayerData."+b+".Business.Revenue");
    		int size = config.getInt("PlayerData."+b+".Business.Employee.Count")+1;
    		int oc = config.getInt("PlayerData."+b+".Business.OperationCost");
    		int sh = config.getInt("PlayerData."+b+".Business.Shares");

    		
    		int profit = rev-oc;
    		
    		Business bs = new Business(b, rev, oc, profit, sh, size);
    		
    		if (!main.bls.contains(bs)) {
    			main.bls.add(bs);
    		}
		}
	}
	}
}
