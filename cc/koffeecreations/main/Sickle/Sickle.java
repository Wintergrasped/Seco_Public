package cc.koffeecreations.main.Sickle;

import com.sk89q.worldedit.bukkit.BukkitAdapter;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionQuery;

import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.Directional;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class Sickle implements Listener {

	  public int output_min = 1;
	  
	  public int output_max = 2;
	  
	  public boolean onlyHoe = false;
	  
	  public boolean HoeDurability = false;
	  
	  public boolean isWorldGuard = false;
	  
	  public boolean bypassWorldGuard = false;

	  public FileConfiguration config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();

	  	
	  	public int FV = config.getInt("ServerData.FarmingVolume"); //Value for tracking volume in Farming Industry
	  	public int LV = config.getInt("ServerData.LoggingVolume"); //Value for tracking volume in Logging Industry
	  	public int MV = config.getInt("ServerData.MiningVolume"); //Value for tracking volume in Mining Industry
	  	double miningPRC = config.getDouble("ServerData.MiningStockPrice");
	  	double loggingPRC = config.getDouble("ServerData.LoggingStockPrice");
	  	double farmingPRC = config.getDouble("ServerData.FarmingStockPrice");
	  
	  
	  
	  public Sickle() {
		  setVariables();
		  config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
	  }
	  
	  @EventHandler
	  public void rightClick(PlayerInteractEvent e) {
	    if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
	      Player p = e.getPlayer();
	      Block b = e.getClickedBlock();
	      Material M = b.getType();
	      
	      
	      switch (M) {
		    
		    case WHEAT: 
		    	harvestCrop(p, b, Material.WHEAT, Material.WHEAT, Material.WHEAT_SEEDS, ((Ageable)b.getBlockData()).getMaximumAge());
		    	break;
		    case CARROTS: 
		    	harvestCrop(p, b, Material.CARROT, Material.CARROTS, Material.CARROT, ((Ageable)b.getBlockData()).getMaximumAge());
		    	break;
		    case POTATOES: 
		    	harvestCrop(p, b, Material.POTATO, Material.POTATOES, Material.POTATO, ((Ageable)b.getBlockData()).getMaximumAge());
		    	break;
		    case BEETROOTS: 
		    	harvestCrop(p, b, Material.BEETROOT, Material.BEETROOTS, Material.BEETROOT_SEEDS, ((Ageable)b.getBlockData()).getMaximumAge());
		    	break;
		    case NETHER_WART: 
		    	harvestCrop(p, b, Material.NETHER_WART, Material.NETHER_WART, Material.NETHER_WART, ((Ageable)b.getBlockData()).getMaximumAge());
		    	break;
		    case MELON: 
		    	harvestStem(p, b, Material.ATTACHED_MELON_STEM, Material.MELON, Material.MELON_SEEDS);
		    	break;
		    case PUMPKIN: 
		    	harvestStem(p, b, Material.ATTACHED_PUMPKIN_STEM, Material.PUMPKIN, Material.PUMPKIN_SEEDS);
		    	break;
		    default:
		    	return;
		    }
	      
	      
	      /*
	      if (b.getType() == Material.WHEAT) {
	        
	        harvestCrop(p, b, Material.WHEAT, Material.WHEAT, Material.WHEAT_SEEDS, ((Ageable)b.getBlockData()).getMaximumAge());
	      } else if (b.getType() == Material.CARROTS) {
	        
	        harvestCrop(p, b, Material.CARROT, Material.CARROTS, Material.CARROT, ((Ageable)b.getBlockData()).getMaximumAge());
	      } else if (b.getType() == Material.POTATOES) {
	        
	        harvestCrop(p, b, Material.POTATO, Material.POTATOES, Material.POTATO, ((Ageable)b.getBlockData()).getMaximumAge());
	      } else if (b.getType() == Material.BEETROOTS) {
	        
	        harvestCrop(p, b, Material.BEETROOT, Material.BEETROOTS, Material.BEETROOT_SEEDS, ((Ageable)b.getBlockData()).getMaximumAge());
	      } else if (b.getType() == Material.NETHER_WART) {
	        
	        harvestCrop(p, b, Material.NETHER_WART, Material.NETHER_WART, Material.NETHER_WART, ((Ageable)b.getBlockData()).getMaximumAge());
	      } else if (b.getType() == Material.COCOA) {
	        
	        harvestCocoa(p, b);
	      } else if (b.getType() == Material.MELON) {
	        
	        harvestStem(p, b, Material.ATTACHED_MELON_STEM, Material.MELON, Material.MELON_SEEDS);
	      } else if (b.getType() == Material.PUMPKIN) {
	        
	        harvestStem(p, b, Material.ATTACHED_PUMPKIN_STEM, Material.PUMPKIN, Material.PUMPKIN_SEEDS);
	      } 
	    }
	    
	     */
	    }
	  }
	  
	  public void durabilityDown(Player p) {
	    if (p.getGameMode() == GameMode.CREATIVE)
	      return;
	    
	    ItemStack item = p.getItemInHand();
	    if (item.getItemMeta().isUnbreakable())
	      return; 
	    if (randomNumber(0, 100) < 100 / (item.getItemMeta().getEnchantLevel(Enchantment.DURABILITY) + 1)) {
	      item.setDurability((short)(item.getDurability() + 1));
	      if (item.getDurability() >= item.getType().getMaxDurability()) {
	        item.setType(Material.AIR);
	        item.setAmount(0);
	      } 
	      p.setItemInHand(item);
	      p.updateInventory();
	    } 
	  }
	  
	  public int harvestCrop(Player p, Block b, Material output, Material seeds, Material droppedSeeds, int full) {
	    if (b.getType() == seeds && 
	      b.getData() == full) {
	      int data = 0;
	     
	      //SickleEvent event = new SickleEvent(p, b);
	      //Bukkit.getServer().getPluginManager().callEvent(event);
	      onHarvest();
	      b.setType(seeds);
	      BlockState bs = b.getState();
	      bs.setRawData((byte)data);
	      b.setBlockData(bs.getBlockData());
	      int rand = randomNumber(output_min, output_max);
	      if (p.getItemInHand().getEnchantments().containsValue(Enchantment.LOOT_BONUS_BLOCKS)) {
	    	  rand = randomNumber(output_min*2, output_max*2);
	      }
	     
	      p.getInventory().addItem(new ItemStack(output, rand));
	      p.playSound(p.getLocation(), Sound.BLOCK_CROP_BREAK, 10, 10);
	      if (randomNumber(0, 100) < 25)
	        
	    	  p.getInventory().addItem(new ItemStack(droppedSeeds, 1));
	      if (HoeDurability)
	        durabilityDown(p); 
	      
	      return rand;
	    } 
	    return -1;
	  }
	  
	  public int harvestStem(Player p, Block b, Material stem, Material block, Material seeds) {
	    if (b.getType() == block) {
	      Location loc = b.getLocation();
	      World w = b.getWorld();
	      Location[] dir = { new Location(w, loc.getX() - 1.0D, loc.getY(), loc.getZ()), 
	          new Location(w, loc.getX() + 1.0D, loc.getY(), loc.getZ()), 
	          new Location(w, loc.getX(), loc.getY(), loc.getZ() - 1.0D), 
	          new Location(w, loc.getX(), loc.getY(), loc.getZ() + 1.0D) };
	      int count = 0;
	      byte b1;
	      int i;
	      Location[] arrayOfLocation1;
	      for (i = (arrayOfLocation1 = dir).length, b1 = 0; b1 < i; ) {
	        Location l = arrayOfLocation1[b1];
	        Block sided = w.getBlockAt(l);
	        if (sided.getType() == stem) {
	          //SickleEvent event = new SickleEvent(p, b);
	          //Bukkit.getServer().getPluginManager().callEvent(event);
	          
	          if (((Directional)sided.getBlockData()).getFacing() == sided.getFace(b))
	            b.breakNaturally(); 
	          onHarvest();
	          count++;
	          if (count == 1) {
	            if (HoeDurability)
	              durabilityDown(p); 
	           
	          } 
	        } 
	        b1++;
	      } 
	      return count;
	    } 
	    return -1;
	  }
	  
	  public void harvestCocoa(Player p, Block b) {
	    if (b.getType() == Material.COCOA) {
	      int full = b.getData() / 4;
	      int face = b.getData() - 8;
	      if (full == 2) {
	        //SickleEvent event = new SickleEvent(p, b);
	        //Bukkit.getServer().getPluginManager().callEvent(event);
	        onHarvest();
	        b.breakNaturally();
	        if (HoeDurability)
	          durabilityDown(p); 
	       
	        b.setType(Material.COCOA);
	        BlockState bs = b.getState();
	        bs.setRawData((byte)face);
	        b.setBlockData(bs.getBlockData());
	        
	      } 
	    } 
	  }
	  

	  public int randomNumber(int min, int max) {
		  Random rand = new Random();
	        rand.setSeed(Bukkit.getServer().getWorld("World").getFullTime()+Bukkit.getBannedPlayers().size()+Bukkit.getOnlinePlayers().size()+System.currentTimeMillis()+System.nanoTime()+Bukkit.getOnlinePlayers().size()+Bukkit.getOperators().size()+Bukkit.getPort()+Bukkit.getOfflinePlayers().length+Bukkit.getIdleTimeout()+Bukkit.getIPBans().size()+Bukkit.getWorlds().size()+Bukkit.getName().getBytes().length+Bukkit.getWorld("world").getSeed());
	        int randomNum = rand.nextInt((max - min) + 1) + min;

	        return randomNum;
	  }

	  
	  private void setVariables() {
		  config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();

	      output_min = 1;
	      output_max = 4;
	      onlyHoe = false;

	      HoeDurability = false;
	      isWorldGuard = true; 
	  }
	 
	  
	  private boolean checkWorld(Player p, Block b) {
	    boolean result = false;
	    
	    if (isWorldGuard) {
	      RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
	      com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(b.getLocation());
	      if (bypassWorldGuard || query.testState(loc, WorldGuardPlugin.inst().wrapPlayer(p), new StateFlag[] { Flags.BUILD })) {
	        if (onlyHoe) {
	          if (p.getItemInHand().getType() == Material.WOODEN_HOE || 
	            p.getItemInHand().getType() == Material.STONE_HOE || 
	            p.getItemInHand().getType() == Material.IRON_HOE || 
	            p.getItemInHand().getType() == Material.GOLDEN_HOE || 
	            p.getItemInHand().getType() == Material.DIAMOND_HOE)
	            return false; 
	          return true;
	        } 
	        return false;
	      } 
	      return true;
	    } 
	    if (onlyHoe) {
	      if (p.getItemInHand().getType() == Material.WOODEN_HOE || 
	        p.getItemInHand().getType() == Material.STONE_HOE || 
	        p.getItemInHand().getType() == Material.IRON_HOE || 
	        p.getItemInHand().getType() == Material.GOLDEN_HOE || 
	        p.getItemInHand().getType() == Material.DIAMOND_HOE)
	        return false; 
	      return true;
	    } 
	    return result;
	  }

	  	
	  	
	  	@EventHandler
	  	public void onHarvest() {
	  		
	  		refreshPrices();
	  		farmingPRC = farmingPRC+0.001;
	  		loggingPRC = loggingPRC-0.008;
	  		miningPRC = miningPRC-0.008;
	  		
	  		config.set("ServerData.MiningStockPrice", miningPRC);
	  		config.set("ServerData.LoggingStockPrice", loggingPRC);
	  		config.set("ServerData.FarmingStockPrice", farmingPRC);
	  		Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
	  		checkPrices();
	  		
	  	}
	  	
	  	public void refreshPrices() {
	  		miningPRC = config.getDouble("ServerData.MiningStockPrice");
	  		loggingPRC = config.getDouble("ServerData.LoggingStockPrice");
	  		farmingPRC = config.getDouble("ServerData.FarmingStockPrice");
	  	}
	  	
	  	public void checkPrices() {
	  		
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
	  		try {
	  			Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
	  	    } catch (Exception e) {
	  	    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	  	    }
	  	}
	  	
	  }

