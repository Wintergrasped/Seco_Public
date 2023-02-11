package cc.koffeecreations.main.listener;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import cc.koffeecreations.main.Items.InfusedArmor;
import cc.koffeecreations.main.Items.InfusedWeapons;
import cc.koffeecreations.main.econ.Econ;

public class MobDrops implements Listener{

	Econ econ = new Econ();
	
	@EventHandler
	public void onKill(EntityDeathEvent e) {
		
		if (e.getEntity().getType().equals(EntityType.PLAYER)) {
			return;
		}
			if (e.getEntity().getKiller() == null) {
				return;
			}
			
			Player p = e.getEntity().getKiller();
			
			e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
			e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
			econ.deposit(p.getName(), random(1,20));
			
			if (e.getEntity().getType().equals(EntityType.WITHER_SKELETON)) {
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
			}else if (e.getEntity().getType().equals(EntityType.WARDEN)) {
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
			}else if (e.getEntity().getType().equals(EntityType.BLAZE)) {
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
			}else if (e.getEntity().getType().equals(EntityType.CREEPER)) {
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
			}else if (e.getEntity().getType().equals(EntityType.ELDER_GUARDIAN)) {
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
			}else if (e.getEntity().getType().equals(EntityType.GUARDIAN)) {
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
			}
			
			if (random(1,20) == random(1,20)) {
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
			}
			
		if (e.getEntity().getKiller().getType().equals(EntityType.WOLF)) {
			
			Wolf wolf = (Wolf) e.getEntity().getKiller();
			
			
			if (wolf.isTamed()) {
			
				AnimalTamer p1 = wolf.getOwner();
				
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				econ.deposit(p1.getName(), random(1,20));
				
				if (e.getEntity().getType().equals(EntityType.WITHER_SKELETON)) {
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				}else if (e.getEntity().getType().equals(EntityType.WARDEN)) {
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				}else if (e.getEntity().getType().equals(EntityType.BLAZE)) {
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				}else if (e.getEntity().getType().equals(EntityType.CREEPER)) {
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				}else if (e.getEntity().getType().equals(EntityType.ELDER_GUARDIAN)) {
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				}else if (e.getEntity().getType().equals(EntityType.GUARDIAN)) {
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				}
				
				if (random(1,20) == random(1,20)) {
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
					e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), getRandomDrop());
				}
				
			}
			
		}
		
		
		
	}
	
	
	public ItemStack getRandomDrop() {
		ItemStack IS = new ItemStack(Material.BONE, 1); 
		ItemMeta IM = IS.getItemMeta();
		
		if (random(1,7600) == 1352) {
			IS.setType(Material.ENCHANTED_BOOK);
			IS.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, random(1,15));
		}
		
		if (random(1,9000) == 9169) {
			IS.setType(Material.ENCHANTED_BOOK);
			IS.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, random(6,20));
		}
		
		if (random(1,7693) == 2243) {
			IS.setType(Material.ENCHANTED_BOOK);
			IS.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, random(1,15));
		}
		
		if (random(1,9693) == 3103) {
			IS.setType(Material.ENCHANTED_BOOK);
			IS.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, random(1,15));
		}
		
		if (random(1,9963) == 1284) {
			IS.setType(Material.ENCHANTED_BOOK);
			IS.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, random(1,15));
		}
		
		if (random(1,10325) == 3150) {
			IS = InfusedArmor.RegenNBoots();
		}
		
		if (random(1,10325) == 3300) {
			IS = InfusedArmor.RegenNHelm();
		}
		
		if (random(1,10325) == 4600) {
			IS = InfusedArmor.RegenNPants();
		}
		
		if (random(1,10325) == 3290) {
			IS = InfusedArmor.RegenNChest();
		}
		
		if (random(1,10340) == 5230) {
			IS = InfusedArmor.StrNHelm();
		}
		
		if (random(1,10340) == 4350) {
			IS = InfusedArmor.StrNChest();
		}
		
		if (random(1,10340) == 2700) {
			IS = InfusedArmor.StrNPants();
		}
		
		if (random(1,10345) == 5390) {
			IS = InfusedArmor.StrNBoots();
		}
		
		if (random(1,10345) == 2109) {
			IS = InfusedWeapons.StrNSword();
		}
		
		if (random(1,10345) == 5111) {
			IS = InfusedWeapons.RegenNSword();
		}
		
		if (random(1,10345) == 6088) {
			IS = InfusedWeapons.MightNSword();
		}
		
		return IS;
	}
	
	
	public int random(int min, int max) {

        Random rand = new Random();
        rand.setSeed(Bukkit.getServer().getWorld("World").getFullTime()+Bukkit.getBannedPlayers().size()+Bukkit.getOnlinePlayers().size()+System.currentTimeMillis()+System.nanoTime()+Bukkit.getOnlinePlayers().size()+Bukkit.getOperators().size()+Bukkit.getPort()+Bukkit.getOfflinePlayers().length+Bukkit.getIdleTimeout()+Bukkit.getIPBans().size()+Bukkit.getWorlds().size()+Bukkit.getName().getBytes().length+Bukkit.getWorld("world").getSeed());
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
	
}
