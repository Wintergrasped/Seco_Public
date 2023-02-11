package cc.koffeecreations.main.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import cc.koffeecreations.main.events.TreeFallEvent;



public class TreeFaller implements Listener{

	List<String> Logs = new ArrayList();
	boolean debug = false;
	
	
	public TreeFaller() {
		
		Logs.add(Material.OAK_LOG.toString());
		Logs.add(Material.DARK_OAK_LOG.toString());
		Logs.add(Material.BIRCH_LOG.toString());
		Logs.add(Material.ACACIA_LOG.toString());
		Logs.add(Material.JUNGLE_LOG.toString());
		Logs.add(Material.SPRUCE_LOG.toString());
		
		
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBreak(BlockBreakEvent E) {
		
		Block B = E.getBlock();
		boolean tree = false;
		boolean treeloc = false;
		int count = 0;
		Location L = E.getBlock().getLocation();
		List<Block> BL = new ArrayList();
		
		
		if (debug) {
			Bukkit.getLogger().log(Level.INFO, "Block Break");
		}
		if (Logs.contains(B.getType().toString())) {
			
			List<Block> blocks = new ArrayList();
			
			if (debug) {
				Bukkit.getLogger().log(Level.INFO, "Log Break");
			}
			
			

			
			tree = true;
			
			
			treeloc = true;
			
			Location LL = L;
			
			List<Block> BLK = new ArrayList();
			
			while(tree) {
				
				tree = false;
				
				
				LL.add(0,1,0);
				BLK = getNearbyBlocks(LL, 6);
				
				for (Block BB : BLK) {
					
					if (Logs.contains(BB.getType().toString())) {
						blocks.add(BB);
						count = count+1;
						tree = true;
					}
				
				}
			}
			
			Material M = E.getBlock().getType();
			for (Block BB : blocks) {
				
				if (debug) {
					Bukkit.getLogger().log(Level.INFO, "Breaking Log");
				}
				//M = BB.getType();
				BB.setType(Material.AIR);
				if (E.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STONE_AXE)) {
					E.getPlayer().getInventory().getItemInMainHand().setDurability(Short.parseShort((E.getPlayer().getInventory().getItemInMainHand().getDurability()-1)+""));
				}else if (E.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.WOODEN_AXE)) {
					E.getPlayer().getInventory().getItemInMainHand().setDurability(Short.parseShort((E.getPlayer().getInventory().getItemInMainHand().getDurability()-1)+""));
				}else if (E.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.IRON_AXE)) {
					E.getPlayer().getInventory().getItemInMainHand().setDurability(Short.parseShort((E.getPlayer().getInventory().getItemInMainHand().getDurability()-1)+""));
				}else if (E.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_AXE)) {
					E.getPlayer().getInventory().getItemInMainHand().setDurability(Short.parseShort((E.getPlayer().getInventory().getItemInMainHand().getDurability()-1)+""));
				}else if (E.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.NETHERITE_AXE)) {
					E.getPlayer().getInventory().getItemInMainHand().setDurability(Short.parseShort((E.getPlayer().getInventory().getItemInMainHand().getDurability()-1)+""));
				}else if (E.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.GOLDEN_AXE)) {
					E.getPlayer().getInventory().getItemInMainHand().setDurability(Short.parseShort((E.getPlayer().getInventory().getItemInMainHand().getDurability()-1)+""));
				}
				
				//E.getPlayer().getInventory().addItem(new ItemStack());
				
			}
			E.getPlayer().getInventory().addItem(new ItemStack(M, count));
			TreeFallEvent event = new TreeFallEvent(count);
		    Bukkit.getServer().getPluginManager().callEvent(event);
		}
	}
	
	
	
	public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        int y = location.getBlockY();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                   blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
 
        }
        return blocks;
    }
	
}
