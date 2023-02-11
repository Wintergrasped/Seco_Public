package cc.koffeecreations.spells;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Healer implements Listener {

	public FileConfiguration config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();

	public Healer() {
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
	}
	
	
	@EventHandler
	public void onSpell(PlayerInteractEvent e) {
		
		if (config.getString("PlayerData."+e.getPlayer().getName()+".Class") == null) {
			return;
		}
		
		if (config.getString("PlayerData."+e.getPlayer().getName()+".Class").equalsIgnoreCase("Healer")) {
			
			if (!e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {
				return;
			}
			
			if (!e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
				return;
			}
			
			if (e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {
				
				if (!e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
					return;
				}else if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().contains("Rejuve 1")) {
					
					for (Player pl : playersInRange(e.getPlayer().getLocation(), 5)) {

							if (pl.getHealth()+2 > pl.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
								pl.setHealth(pl.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
							}else {
								pl.setHealth(pl.getHealth()+2);
							}
							pl.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 40, 1));
						
					}
					removeWand(e.getPlayer().getInventory().getItemInMainHand(), e.getPlayer());
					
				}else if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().contains("Mass Heal 1")) {
					
					for (Player pl : playersInRange(e.getPlayer().getLocation(), 8)) {
						
							if (pl.getHealth()+4 > pl.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
								pl.setHealth(pl.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
							}else {
								pl.setHealth(pl.getHealth()+4);
							}
						
					}
					removeWand(e.getPlayer().getInventory().getItemInMainHand(), e.getPlayer());
					
				}else if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().contains("Prot Wand 1")) {
					
					for (Player pl : playersInRange(e.getPlayer().getLocation(), 8)) {
						
							if (pl.getHealth()+2 > pl.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
								pl.setHealth(pl.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
							}else {
								pl.setHealth(pl.getHealth()+2);
							}
						pl.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 2));
						
					}
					removeWand(e.getPlayer().getInventory().getItemInMainHand(), e.getPlayer());
					
				}
				
				
				
				
				
				
				
			}
			
		}else {
			return;
		}
		
	}
	
	
	
	
	
	public void removeWand(ItemStack wand, Player p) {
		if (wand.getAmount() > 1) {
			
			p.getInventory().remove(wand);
			wand.setAmount(wand.getAmount()-1);
			p.getInventory().addItem(wand);
			
		}else{
			p.getInventory().remove(wand);
		}
	}
	
	public List<Player> playersInRange(Location L1, double distance) {
		
		List<Player> pls = new ArrayList();
		
		for (Player pl : Bukkit.getOnlinePlayers()) {
			if (withinDistance(pl.getLocation(), L1, distance)) {
				pls.add(pl);
			}
		}
		
		
		return pls;
		
	}
	
	public boolean withinDistance(Location loc1, Location loc2, double distance) {
        double xDiff = loc1.getX() - loc2.getX();
        double yDiff = loc1.getY() - loc2.getY();
        double zDiff = loc1.getZ() - loc2.getZ();
        double distanceSquared = xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;
        return distanceSquared <= distance * distance;
    }
}
