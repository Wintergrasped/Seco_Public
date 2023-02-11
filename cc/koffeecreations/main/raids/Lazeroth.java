package cc.koffeecreations.main.raids;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import cc.koffeecreations.main.Items.InfusedArmor;
import cc.koffeecreations.main.Items.InfusedWeapons;


/*
 *TODO Add Spawn trigger and Boss Spawn Trigger X100 Y19 Z-25
 *TODO Boss 1 Spawn X149 Y23 Z-74 The Abomination
 *TODO Boss Fight 1 Start X130 Y18 Z-55
 *TODO Boss 2 Spawn Trigger 2 X136 Y26 Z-27
 *TODO Boss 2 Spawn X126 Y34 Z24 The Bloodthirsty
 *TODO Boss 2 Fight Start X126 Y33 Z9
 *TODO Boss 3 Spawn X131 Y46 Z-59 The Necromancer
 *TODO Boss 3 Spawn Trigger X124 Y43 Z-26
 *TODO Boss 3 Trigger X119 Y45 Z-33
 *TODO Boss 4 Spawn Trigger X110 Y79 Z-57
 *TODO Boss 4 Trigger X141 Y79 Z-51
 *TODO Boss 4 Spawn X141 Y80 Z-66 The Ghost King
 * 
 */


public class Lazeroth implements Listener {

	
	public boolean debug = false;
	
	public String ActiveFight = "None";
	public boolean isActive = false;
	public int phase = 0;
	
	public UUID ActiveID;
	
	public Location Abom = new Location(Bukkit.getWorld("Lazeroth"), 149, 28, -74);
	public Location Blood = new Location(Bukkit.getWorld("Lazeroth"), 126, 34, 24);
	public Location Narc = new Location(Bukkit.getWorld("Lazeroth"), 131, 46, -59);
	public Location King = new Location(Bukkit.getWorld("Lazeroth"), 141, 80, -66);
	
	public Location Abom_Trig = new Location(Bukkit.getWorld("Lazeroth"), 100, 19, -25);
	public Location Blood_Trig = new Location(Bukkit.getWorld("Lazeroth"), 136, 26, -27);
	public Location Narc_Trig = new Location(Bukkit.getWorld("Lazeroth"), 124, 43, -26);
	public Location King_Trig = new Location(Bukkit.getWorld("Lazeroth"), 141, 79, -51);
	
	
	public Lazeroth() {
		Abom = new Location(Bukkit.getWorld("Lazeroth"), 149, 28, -74);
		Blood = new Location(Bukkit.getWorld("Lazeroth"), 126, 34, 24);
		Narc = new Location(Bukkit.getWorld("Lazeroth"), 131, 46, -59);
		King = new Location(Bukkit.getWorld("Lazeroth"), 141, 80, -66);
		
		Abom_Trig = new Location(Bukkit.getWorld("Lazeroth"), 100, 19, -25);
		Blood_Trig = new Location(Bukkit.getWorld("Lazeroth"), 136, 26, -27);
		Narc_Trig = new Location(Bukkit.getWorld("Lazeroth"), 124, 43, -26);
		King_Trig = new Location(Bukkit.getWorld("Lazeroth"), 141, 79, -51);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		if (e.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase("Lazeroth")) {
			
			
			boolean A = triggerCheck(e.getPlayer().getLocation(), Abom_Trig, 5);
			boolean B = triggerCheck(e.getPlayer().getLocation(), Blood_Trig, 5);
			boolean N = triggerCheck(e.getPlayer().getLocation(), Narc_Trig, 5);
			boolean K = triggerCheck(e.getPlayer().getLocation(), King_Trig, 5);
			
			if (A) {
				//Spawn Abom
				if (!isActive) {
					isActive = true;
					spawnAbom(Abom);
					ActiveFight = "Abom";
					Bukkit.getLogger().log(Level.INFO, "Abom Spawned");
				}
			}else if (B)  {
				//Spawn BloodThirsty
				if (!isActive) {
					isActive = true;
					spawnBlood(Blood);
					ActiveFight = "Blood";
				}
			}else if (N)  {
				//Spawn Narc
				if (!isActive) {
					isActive = true;
					spawnNarc(Narc);
					ActiveFight = "Narc";
				}
			}else if (K)  {
				//Spawn King
				if (!isActive) {
					isActive = true;
					spawnKing(King);
					ActiveFight = "King";
				}
			}
			
			
		}else{
			return;
		}
		
	}
	
	public void spawnAbom(Location l) {
		isActive = true;
					EntityType skeleton = EntityType.ZOMBIFIED_PIGLIN;
					World world = l.getWorld();
					
					LivingEntity raidBoss = (LivingEntity) world.spawnEntity(l, skeleton);
					
					raidBoss.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1250);
					raidBoss.setHealth(1250);
					ActiveID = UUID.randomUUID();
					ActiveFight = "Abom";
					AttributeModifier A = new AttributeModifier(ActiveID, "generic.attackDamage", 70, AttributeModifier.Operation.ADD_NUMBER);
					A.getUniqueId();
					raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
					raidBoss.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(10);
					//raidBoss.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(10);
					//raidBoss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(10);
					//raidBoss.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).setBaseValue(10);
					raidBoss.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(10);

					
					raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
					raidBoss.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
					//raidBoss.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addModifier(A);
					//raidBoss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(A);
					//raidBoss.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).addModifier(A);
					raidBoss.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
					raidBoss.setCustomName("Abom");
					raidBoss.setCustomNameVisible(true);
					//raidBoss.setCustomNameVisible(true);
	}
	
	public void spawnBlood(Location l) {
		isActive = true;
		EntityType skeleton = EntityType.BLAZE;
		World world = l.getWorld();
		LivingEntity raidBoss = (LivingEntity) world.spawnEntity(l, skeleton);
		raidBoss.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1540);
		raidBoss.setHealth(1540);
		ActiveID = UUID.randomUUID();
		ActiveFight = "Blood";
		AttributeModifier A = new AttributeModifier(ActiveID, "generic.attackDamage", 83, AttributeModifier.Operation.ADD_NUMBER);
		A.getUniqueId();
		raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(10);
		//raidBoss.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(10);
		//raidBoss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(10);
		//raidBoss.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).setBaseValue(10);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(10);
		
		raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
		//raidBoss.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addModifier(A);
		//raidBoss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(A);
		//raidBoss.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).addModifier(A);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
		raidBoss.setCustomName("Blood");
		raidBoss.setCustomNameVisible(true);

}
	
	public void spawnNarc(Location l) {
		isActive = true;
		EntityType skeleton = EntityType.WITHER_SKELETON;
		World world = l.getWorld();
		LivingEntity raidBoss = (LivingEntity) world.spawnEntity(l, skeleton);
		raidBoss.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1670);
		raidBoss.setHealth(1670);
		ActiveID = UUID.randomUUID();
		ActiveFight = "Narc";
		AttributeModifier A = new AttributeModifier(ActiveID, "generic.attackDamage", 91, AttributeModifier.Operation.ADD_NUMBER);
		A.getUniqueId();
		
		raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(10);
		//raidBoss.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(10);
		//raidBoss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(10);
		//raidBoss.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).setBaseValue(10);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(10);
		
		raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
		//raidBoss.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addModifier(A);
		//raidBoss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(A);
		//raidBoss.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).addModifier(A);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
		raidBoss.setCustomName("Narc");
		raidBoss.setCustomNameVisible(true);

}
	
	public void spawnKing(Location l) {
		isActive = true;
		EntityType skeleton = EntityType.IRON_GOLEM;
		World world = l.getWorld();
		LivingEntity raidBoss = (LivingEntity) world.spawnEntity(l, skeleton);
		raidBoss.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(2048);
		raidBoss.setHealth(2048);
		ActiveID = UUID.randomUUID();
		ActiveFight = "King";
		AttributeModifier A = new AttributeModifier(ActiveID, "generic.attackDamage", 104, AttributeModifier.Operation.ADD_NUMBER);
		A.getUniqueId();
		raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(10);
		//raidBoss.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(10);
		//raidBoss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(10);
		//raidBoss.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).setBaseValue(10);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(10);
		
		raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
		//raidBoss.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addModifier(A);
		//raidBoss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(A);
		//raidBoss.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).addModifier(A);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
		raidBoss.setCustomName("Darking");
		raidBoss.setCustomNameVisible(true);
}
	
	public void spawnZombieAdd(Location l) {
		EntityType skeleton = EntityType.ZOMBIE;
		World world = l.getWorld();
		LivingEntity raidBoss = (LivingEntity) world.spawnEntity(l, skeleton);
		raidBoss.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(120);
		raidBoss.setHealth(120);
		ActiveID = UUID.randomUUID();
		AttributeModifier A = new AttributeModifier(ActiveID, "generic.attackDamage", 5, AttributeModifier.Operation.ADD_NUMBER);
		A.getUniqueId();
		raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(10);
		//raidBoss.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(10);
		//raidBoss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(10);
		//raidBoss.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).setBaseValue(10);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(10);
		
		raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
		//raidBoss.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addModifier(A);
		//raidBoss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(A);
		//raidBoss.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).addModifier(A);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
		//raidBoss.setCustomName("Darking");
		//raidBoss.setCustomNameVisible(true);
}
	
	public void spawnSkeletonAdd(Location l) {
		EntityType skeleton = EntityType.SKELETON;
		World world = l.getWorld();
		LivingEntity raidBoss = (LivingEntity) world.spawnEntity(l, skeleton);
		raidBoss.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(120);
		raidBoss.setHealth(120);
		ActiveID = UUID.randomUUID();
		AttributeModifier A = new AttributeModifier(ActiveID, "generic.attackDamage", 5, AttributeModifier.Operation.ADD_NUMBER);
		A.getUniqueId();
		raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(10);
		//raidBoss.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(10);
		//raidBoss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(10);
		//raidBoss.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).setBaseValue(10);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(10);
		
		raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
		//raidBoss.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addModifier(A);
		//raidBoss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(A);
		//raidBoss.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).addModifier(A);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
		//raidBoss.setCustomName("Darking");
		//raidBoss.setCustomNameVisible(true);
}
	public void onDeath(EntityDeathEvent e) {
		//TODO Not Detecting Abom death or probably any other boss
		if (!(ActiveID == null)) {
			if (e.getEntity().getUniqueId().equals(ActiveID)) {
				isActive = false;
				ActiveID = null;
			}
		}
		
		if (!(e.getEntity().getCustomName() == null)) {
			if (e.getEntity().getCustomName().equalsIgnoreCase(ActiveFight)) {
				isActive = false;
				ActiveID = null;
				phase = 0;
				Player p = e.getEntity().getKiller();
				p.getInventory().addItem(getRandomDrop());
				p.getInventory().addItem(getRandomDrop());
				p.getInventory().addItem(getRandomDrop());
				p.getInventory().addItem(getRandomDrop());
				p.getInventory().addItem(getRandomDrop());
				p.getInventory().addItem(getRandomDrop());
				p.getInventory().addItem(getRandomDrop());
				p.getInventory().addItem(getRandomDrop());
			}
		}
	}
	
	
	public boolean triggerCheck(Location L1, Location L2, int distance) {
		return withinDistance(L1, L2, distance);
	}
	
	public boolean withinDistance(Location loc1, Location loc2, double distance) {
        double xDiff = loc1.getX() - loc2.getX();
        double yDiff = loc1.getY() - loc2.getY();
        double zDiff = loc1.getZ() - loc2.getZ();
        double distanceSquared = xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;
        return distanceSquared <= distance * distance;
    }
	
	
	@EventHandler
	public void abomFight(EntityDamageByEntityEvent e) {
		if (debug) {
			Bukkit.getLogger().log(Level.INFO, e.getDamager().getName()+" hit "+e.getEntity().getCustomName());
		}
		if (ActiveFight.equalsIgnoreCase("Abom")) {
			
			
			if (e.getEntity().getCustomName() == null) {
				return;
			}
			
			if (e.getEntity().getCustomName().equalsIgnoreCase("Abom")) {
				if (debug) {
					Bukkit.getLogger().log(Level.INFO, "Abom Was hit");
				}
				LivingEntity le = (LivingEntity) e.getEntity();
				double hpc = le.getHealth()/le.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
				Player pl = Bukkit.getPlayer(e.getDamager().getName());
				if (debug) {
					Bukkit.getLogger().log(Level.INFO, "Abom hpc "+hpc);
				}
				if (hpc <= 0.90) {
					if (phase >= 1) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Abom Pahse 1 Trigger");
					}
					phase = 1;
					abomPhase(1, e.getEntity(), pl);
					}
				}
				if (hpc <= 0.82) {
					if (phase >= 2) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Abom Pahse 2 Trigger");
					}
					phase = 2;
					abomPhase(2, e.getEntity(), pl);
					}
				}
				if (hpc <= 0.75) {
					if (phase >= 3) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Abom Pahse 3 Trigger");
					}
					phase = 3;
					abomPhase(3, e.getEntity(), pl);
					}
				}
				if (hpc <= 0.67) {
					if (phase >= 4) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Abom Pahse 4 Trigger");
					}
					phase = 4;
					abomPhase(4, e.getEntity(), pl);
					}
				}
				if (hpc <= 0.55) {
					if (phase >= 5) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Abom Pahse 5 Trigger");
					}
					phase = 5;
					abomPhase(5, e.getEntity(), pl);
					}
				}
				if (hpc <= 0.40) {
					if (phase >= 6) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Abom Pahse 6 Trigger");
					}
					phase = 6;
					abomPhase(6, e.getEntity(), pl);
					}
				}
				if (hpc <= 0.26) {
					if (phase >= 7) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Abom Pahse 7 Trigger");
					}
					phase = 7;
					abomPhase(7, e.getEntity(), pl);
					}
				}
				
			}
		}
		
	}
	
	@EventHandler
	public void bloodFight(EntityDamageByEntityEvent e) {
		if (debug) {
			Bukkit.getLogger().log(Level.INFO, e.getDamager().getName()+" hit "+e.getEntity().getCustomName());
		}
		if (ActiveFight.equalsIgnoreCase("Blood")) {
			
			
			if (e.getEntity().getCustomName() == null) {
				return;
			}
			
			if (e.getEntity().getCustomName().equalsIgnoreCase("Blood")) {
				if (debug) {
					Bukkit.getLogger().log(Level.INFO, "Blood Was hit");
				}
				LivingEntity le = (LivingEntity) e.getEntity();
				double hpc = le.getHealth()/le.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
				Player pl = Bukkit.getPlayer(e.getDamager().getName());
				if (debug) {
					Bukkit.getLogger().log(Level.INFO, "Blood hpc "+hpc);
				}
				if (hpc <= 0.90) {
					if (phase >= 1) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Blood Pahse 1 Trigger");
					}
					phase = 1;
					bloodPhase(1, e.getEntity(), pl);
					}
				}
				if (hpc <= 0.82) {
					if (phase >= 2) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Blood Pahse 2 Trigger");
					}
					phase = 2;
					bloodPhase(2, e.getEntity(), pl);
					}
				}
				if (hpc <= 0.75) {
					if (phase >= 3) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Blood Pahse 3 Trigger");
					}
					phase = 3;
					bloodPhase(3, e.getEntity(), pl);
					}
				}
				if (hpc <= 0.67) {
					if (phase >= 4) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Blood Pahse 4 Trigger");
					}
					phase = 4;
					bloodPhase(4, e.getEntity(), pl);
					}
				}
				if (hpc <= 0.55) {
					if (phase >= 5) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Blood Pahse 5 Trigger");
					}
					phase = 5;
					bloodPhase(5, e.getEntity(), pl);
					}
				}
				if (hpc <= 0.40) {
					if (phase >= 6) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Blood Pahse 6 Trigger");
					}
					phase = 6;
					bloodPhase(6, e.getEntity(), pl);
					}
				}
				if (hpc <= 0.26) {
					if (phase >= 7) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Blood Pahse 7 Trigger");
					}
					phase = 7;
					bloodPhase(7, e.getEntity(), pl);
					}
				}
				
			}
		}
		
	}
	
	@EventHandler
	public void narcFight(EntityDamageByEntityEvent e) {
		if (debug) {
			Bukkit.getLogger().log(Level.INFO, e.getDamager().getName()+" hit "+e.getEntity().getCustomName());
		}
		if (ActiveFight.equalsIgnoreCase("Narc")) {
			
			
			if (e.getEntity().getCustomName() == null) {
				return;
			}
			
			if (e.getEntity().getCustomName().equalsIgnoreCase("Narc")) {
				if (debug) {
					Bukkit.getLogger().log(Level.INFO, "Blood Was hit");
				}
				LivingEntity le = (LivingEntity) e.getEntity();
				double hpc = le.getHealth()/le.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
				Player pl = Bukkit.getPlayer(e.getDamager().getName());
				if (debug) {
					Bukkit.getLogger().log(Level.INFO, "Blood hpc "+hpc);
				}
				if (hpc <= 0.90) {
					if (phase >= 1) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Blood Pahse 1 Trigger");
					}
					phase = 1;
					narcPhase(1, e.getEntity(), pl);
					}
				}
				if (hpc <= 0.82) {
					if (phase >= 2) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Blood Pahse 2 Trigger");
					}
					phase = 2;
					narcPhase(2, e.getEntity(), pl);
					}
				}
				if (hpc <= 0.75) {
					if (phase >= 3) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Blood Pahse 3 Trigger");
					}
					phase = 3;
					narcPhase(3, e.getEntity(), pl);
					}
				}
				if (hpc <= 0.67) {
					if (phase >= 4) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Blood Pahse 4 Trigger");
					}
					phase = 4;
					narcPhase(4, e.getEntity(), pl);
					}
				}
				if (hpc <= 0.55) {
					if (phase >= 5) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Blood Pahse 5 Trigger");
					}
					phase = 5;
					narcPhase(5, e.getEntity(), pl);
					}
				}
				if (hpc <= 0.40) {
					if (phase >= 6) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Blood Pahse 6 Trigger");
					}
					phase = 6;
					narcPhase(6, e.getEntity(), pl);
					}
				}
				if (hpc <= 0.26) {
					if (phase >= 7) {
						
					}else {
					if (debug) {
						Bukkit.getLogger().log(Level.INFO, "Blood Pahse 7 Trigger");
					}
					phase = 7;
					narcPhase(7, e.getEntity(), pl);
					}
				}
				
			}
		}
		
	}
	
	
	public void abomPhase(int p, Entity B, Player pl) {
		if (phase == 0) {
			return;
		}else{
			
			if (p == 1) {
				yell(ChatColor.RED+"Abom:", ChatColor.RED+"You will soon join me and the others", B.getLocation());
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					ps.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1));
				}
			}else if (p == 2) {
				yell(ChatColor.RED+"Abom:", ChatColor.RED+"I will not go down that easy Lets get this party Started", B.getLocation());
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					ps.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1));
					spawnZombieAdd(ps.getLocation());
				}
			}else if (p == 3) {
				yell(ChatColor.RED+"Abom:", ChatColor.RED+"Ahh I see you think you can fight me? and expect to win?", B.getLocation());
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					ps.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 1));
					spawnZombieAdd(ps.getLocation());
					spawnSkeletonAdd(ps.getLocation());
				}
			}else if (p == 4) {
				yell(ChatColor.RED+"Abom:", ChatColor.RED+"You are truly weak", B.getLocation());
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					ps.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
					spawnSkeletonAdd(ps.getLocation());
				}
			}else if (p == 5) {
				yell(ChatColor.RED+"Abom:", ChatColor.RED+"Lets take this up a notch and truly party", B.getLocation());
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					ps.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
					spawnSkeletonAdd(ps.getLocation());
					spawnSkeletonAdd(ps.getLocation());
					spawnZombieAdd(ps.getLocation());
					spawnZombieAdd(ps.getLocation());
				}
			}else if (p == 5) {
				yell(ChatColor.RED+"Abom:", ChatColor.RED+"I've just about had enough with you", B.getLocation());
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					
					ps.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
					ps.setFireTicks(60);
					
					spawnSkeletonAdd(ps.getLocation());
					spawnSkeletonAdd(ps.getLocation());

				}
			}else if (p == 6) {
				yell(ChatColor.RED+"Abom:", ChatColor.RED+"I will END You!", B.getLocation());
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					
					ps.setFireTicks(80);
					ps.setFoodLevel(1);
					ps.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
					spawnSkeletonAdd(ps.getLocation());
					spawnSkeletonAdd(ps.getLocation());
					spawnZombieAdd(ps.getLocation());
					spawnZombieAdd(ps.getLocation());

				}
			}else if (p == 7) {
				yell(ChatColor.RED+"Abom:", ChatColor.RED+"Feel the life drain from your veins!", B.getLocation());
				double drained = 0;
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					drained = drained+ps.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()*0.2;
					ps.setHealth(ps.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()*0.2);

				}
				LivingEntity LB = (LivingEntity) B;
				LB.setHealth(LB.getHealth()+drained);
			}
			
			
		}
	}
	
	
	public void bloodPhase(int p, Entity B, Player pl) {
		if (phase == 0) {
			return;
		}else{
			
			if (p == 1) {
				yell(ChatColor.RED+"Blood:", ChatColor.RED+"I will drain your veins of blood!", B.getLocation());
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					ps.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 1));
				}
			}else if (p == 2) {
				yell(ChatColor.RED+"Blood:", ChatColor.RED+"I will end you!", B.getLocation());
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					ps.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1));
					spawnZombieAdd(ps.getLocation());
					spawnZombieAdd(ps.getLocation());
					spawnZombieAdd(ps.getLocation());
				}
			}else if (p == 3) {
				yell(ChatColor.RED+"Blood:", ChatColor.RED+"Your blood is mine!", B.getLocation());
				double drained = 0;
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					drained = drained+ps.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()*0.2;
					ps.setHealth(ps.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()*0.2);
					spawnSkeletonAdd(ps.getLocation());
					spawnSkeletonAdd(ps.getLocation());
				}
				LivingEntity LB = (LivingEntity) B;
				LB.setHealth(LB.getHealth()+drained);
				
			}else if (p == 4) {
				yell(ChatColor.RED+"Blood:", ChatColor.RED+"Enough! Your mind shall bend!", B.getLocation());
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					ps.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
					spawnSkeletonAdd(ps.getLocation());
					spawnSkeletonAdd(ps.getLocation());
					spawnSkeletonAdd(ps.getLocation());
				}
			}else if (p == 5) {
				yell(ChatColor.RED+"Blood:", ChatColor.RED+"You will not defeat me!", B.getLocation());
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					ps.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
					spawnSkeletonAdd(ps.getLocation());
					spawnSkeletonAdd(ps.getLocation());
					spawnZombieAdd(ps.getLocation());
					spawnZombieAdd(ps.getLocation());
				}
			}else if (p == 6) {
				yell(ChatColor.RED+"Blood:", ChatColor.RED+"You will be Slain!", B.getLocation());
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					
					ps.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
					ps.setFoodLevel(1);
					ps.setFireTicks(60);
					
					spawnSkeletonAdd(ps.getLocation());
					spawnSkeletonAdd(ps.getLocation());

				}
			}else if (p == 7) {
				yell(ChatColor.RED+"Blood:", ChatColor.RED+"This is the end of your voyage!", B.getLocation());
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					
					ps.setFireTicks(80);
					ps.setFoodLevel(1);
					ps.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
					spawnSkeletonAdd(ps.getLocation());
					spawnSkeletonAdd(ps.getLocation());
					spawnZombieAdd(ps.getLocation());
					spawnZombieAdd(ps.getLocation());

				}
			}else if (p == 8) {
				yell(ChatColor.RED+"Blood:", ChatColor.RED+"Your blood is now mine!", B.getLocation());
				double drained = 0;
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					drained = drained+ps.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()*0.2;
					ps.setHealth(ps.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()*0.2);
					spawnSkeletonAdd(ps.getLocation());
					spawnSkeletonAdd(ps.getLocation());
					spawnZombieAdd(ps.getLocation());
					spawnZombieAdd(ps.getLocation());

				}
				LivingEntity LB = (LivingEntity) B;
				LB.setHealth(LB.getHealth()+drained);
				
			}
			
			
		}
	}
	
	
	
	public void narcPhase(int p, Entity B, Player pl) {
		if (phase == 0) {
			return;
		}else{
			
			if (p == 1) {
				yell(ChatColor.RED+"Narc:", ChatColor.RED+"Come closer I want to play a game!", B.getLocation());
				List<Location> ls = new ArrayList();
				
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					ls.add(ps.getLocation());
				}
				
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					Location t = ls.get(random(0,ls.size()));
					ps.teleport(t);
					ls.remove(t);
				}
				
			}else if (p == 2) {
				yell(ChatColor.RED+"Narc:", ChatColor.RED+"Ahh Jokes on you!", B.getLocation());
				Player pls = playersinArea(B.getLocation(), 10).get(random(0,playersinArea(B.getLocation(), 10).size()-1));
				Location pp = pls.getLocation();
				Location bb = B.getLocation();
				
				pls.teleport(bb);
				B.teleport(pp);
				
			}else if (p == 3) {
				yell(ChatColor.RED+"Narc:", ChatColor.RED+"Your life is mine!", B.getLocation());
				double drained = 0;
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					drained = drained+ps.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()*0.2;
					ps.setHealth(ps.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()*0.2);
					spawnSkeletonAdd(ps.getLocation());
					spawnSkeletonAdd(ps.getLocation());
				}
				LivingEntity LB = (LivingEntity) B;
				LB.setHealth(LB.getHealth()+drained);
				
			}else if (p == 4) {
				yell(ChatColor.RED+"Narc:", ChatColor.RED+"Enough! Your mind shall be lost!", B.getLocation());
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					ps.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
					spawnSkeletonAdd(ps.getLocation());
					spawnSkeletonAdd(ps.getLocation());
					spawnSkeletonAdd(ps.getLocation());
				}
				Player pls = playersinArea(B.getLocation(), 10).get(random(0,playersinArea(B.getLocation(), 10).size()));
				Location pp = pls.getLocation();
				Location bb = B.getLocation();
				
				pls.teleport(bb);
				B.teleport(pp);
			}else if (p == 5) {
				yell(ChatColor.RED+"Narc:", ChatColor.RED+"You will not defeat me! You will lose your mind!", B.getLocation());
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					ps.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
					spawnSkeletonAdd(ps.getLocation());
					spawnSkeletonAdd(ps.getLocation());
					spawnZombieAdd(ps.getLocation());
					spawnZombieAdd(ps.getLocation());
				}
				
				List<Location> ls = new ArrayList();
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					ls.add(ps.getLocation());
				}
				
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					Location t = ls.get(random(0,ls.size()-1));
					ps.teleport(t);
					ls.remove(t);
				}
			}else if (p == 6) {
				yell(ChatColor.RED+"Narc:", ChatColor.RED+"You shall be Slain!", B.getLocation());
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					
					ps.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
					ps.setFoodLevel(1);
					ps.setFireTicks(60);
					
					spawnSkeletonAdd(ps.getLocation());
					spawnSkeletonAdd(ps.getLocation());

				}
			}else if (p == 7) {
				yell(ChatColor.RED+"Narc:", ChatColor.RED+"This is the end of your sanity!", B.getLocation());
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					
					ps.setFireTicks(80);
					ps.setFoodLevel(1);
					ps.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 1));
					ps.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
					spawnSkeletonAdd(ps.getLocation());
					spawnSkeletonAdd(ps.getLocation());
					spawnZombieAdd(ps.getLocation());
					spawnZombieAdd(ps.getLocation());

				}
			}else if (p == 8) {
				yell(ChatColor.RED+"Narc:", ChatColor.RED+"Your blood is now mine!", B.getLocation());
				double drained = 0;
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					drained = drained+ps.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()*0.2;
					ps.setHealth(ps.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()*0.2);
					spawnSkeletonAdd(ps.getLocation());
					spawnSkeletonAdd(ps.getLocation());
					spawnZombieAdd(ps.getLocation());
					spawnZombieAdd(ps.getLocation());

				}
				
				List<Location> ls = new ArrayList();
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					ls.add(ps.getLocation());
				}
				
				for (Player ps : playersinArea(B.getLocation(), 10)) {
					Location t = ls.get(random(0,ls.size()-1));
					ps.teleport(t);
					ls.remove(t);
				}
				
				LivingEntity LB = (LivingEntity) B;
				LB.setHealth(LB.getHealth()+drained);
				
			}
			
			
		}
	}
	
	
	
	public void yell(String Send, String Mes, Location l) {
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			
			if (withinDistance(l, p.getLocation(), 15)) {
				p.sendMessage(Send+" "+Mes);
			}
			
		}
		
	}
	
	public List<Player> playersinArea(Location L, int Radius) {
		
		List<Player> pl = new ArrayList();
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (withinDistance(p.getLocation(), L, Radius)) {
				pl.add(p);
			}
		}
		
		return pl;
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
			IS.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, random(6,20));
		}
		
		if (random(1,100)==88) {
			IS.setType(Material.ENCHANTED_BOOK);
			IS.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, random(10,25));
		}
		
		if (random(1,33)== 24) {
			IS.setType(Material.ENCHANTED_BOOK);
			IS.addEnchantment(Enchantment.DAMAGE_ALL, random(1,15));
		}
		
		if (random(1,73)== 70) {
			IS.setType(Material.ENCHANTED_BOOK);
			IS.addEnchantment(Enchantment.DAMAGE_ALL, random(10,25));
		}
		
		if (random(1,33)== 24) {
			IS.setType(Material.ENCHANTED_BOOK);
			IS.addEnchantment(Enchantment.ARROW_DAMAGE, random(1,15));
		}
		
		if (random(1,133)== 93) {
			IS.setType(Material.ENCHANTED_BOOK);
			IS.addEnchantment(Enchantment.ARROW_DAMAGE, random(10,25));
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
		
		
		//Weapons
		if (random(1,45)==39) {
			IS = InfusedWeapons.StrNSword();
		}
		
		if (random(1,45)==40) {
			IS = InfusedWeapons.RegenNSword();
		}
		
		if (random(1,45)==42) {
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
