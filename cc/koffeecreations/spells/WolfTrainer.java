package cc.koffeecreations.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class WolfTrainer implements Listener {

	FileConfiguration config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		
		config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
		
		Entity entity = e.getDamager();
	    if (!(entity instanceof Wolf)) {
	        return;
	    }

	    Wolf wolf = (Wolf) entity;
	    if (!wolf.isTamed()) {
	        return;
	    }
	    
	    
	    
	    /*
	    Player owner = Bukkit.getPlayer(wolf.getOwner().getUniqueId());

	    double xp = config.getDouble("PlayerData."+owner.getName()+".Wolf."+wolf.getUniqueId()+".xp");
	    double level = config.getDouble("PlayerData."+owner.getName()+".Wolf."+wolf.getUniqueId()+".Level");
	    double clevel = config.getDouble("PlayerData."+owner.getName()+".Wolf."+wolf.getUniqueId()+".Level");
	    
	    xp = xp+1;
		
	    
	    if (xp >= (level*1.55)*250) {
	    	
	    	level = level+1;
	    	xp = 0;
	    	
	    }
	    
	    if (level > clevel) {
	    	wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(6.0);
	    if (level == 2) {
	    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40);
            wolf.setHealth(40);
			AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 2, AttributeModifier.Operation.ADD_NUMBER);
			A.getUniqueId();
			wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
			owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 2");
			owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
			owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    }else if (level == 3) {
	    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(60);
	    	wolf.setHealth(60);
			AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 4, AttributeModifier.Operation.ADD_NUMBER);
			A.getUniqueId();
			wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
			owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 3");
			owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
			owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    }else if (level == 4) {
	    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(80);
	    	wolf.setHealth(80);
			AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 6, AttributeModifier.Operation.ADD_NUMBER);
			A.getUniqueId();
			wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
			owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 4");
			owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
			owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    }else if (level == 5) {
	    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(90);
	    	wolf.setHealth(90);
			AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 8, AttributeModifier.Operation.ADD_NUMBER);
			A.getUniqueId();
			wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
			owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 5");
			owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
			owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    }else if (level == 6) {
	    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(100);
	    	wolf.setHealth(100);
			AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 12, AttributeModifier.Operation.ADD_NUMBER);
			A.getUniqueId();
			wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
			owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 6");
			owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
			owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    }else if (level == 7) {
	    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(120);
	    	wolf.setHealth(120);
			AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 14, AttributeModifier.Operation.ADD_NUMBER);
			A.getUniqueId();
			wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
			owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 7");
			owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
			owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    }else if (level == 8) {
	    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(120);
	    	wolf.setHealth(120);
			AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 18, AttributeModifier.Operation.ADD_NUMBER);
			A.getUniqueId();
			wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
			owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 8");
			owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
			owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    }else if (level == 9) {
	    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(140);
	    	wolf.setHealth(140);
			AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 22, AttributeModifier.Operation.ADD_NUMBER);
			A.getUniqueId();
			wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
			owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 9");
			owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
			owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    }else if (level == 10) {
	    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(140);
	    	wolf.setHealth(160);
			AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 26, AttributeModifier.Operation.ADD_NUMBER);
			A.getUniqueId();
			wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
			owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 10");
			owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
			owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    }else if (level == 11) {
	    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(180);
	    	wolf.setHealth(180);
			AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 28, AttributeModifier.Operation.ADD_NUMBER);
			A.getUniqueId();
			wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
			owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 11");
			owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
			owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    }else if (level == 12) {
	    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(200);
	    	wolf.setHealth(200);
			AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 30, AttributeModifier.Operation.ADD_NUMBER);
			A.getUniqueId();
			wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
			wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
			owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 12");
			owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
			owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
			owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
	    }
	    }else {
	    	if (level == 2) {
		    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40);
		    }else if (level == 3) {
		    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(60);
		    }else if (level == 4) {
		    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(80);
		    }else if (level == 5) {
		    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(90);
		    }else if (level == 6) {
		    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(100);
		    }else if (level == 7) {
		    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(120);
		    }else if (level == 8) {
		    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(120);
		    }else if (level == 9) {
		    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(140);
		    }else if (level == 10) {
		    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(140);
		    }else if (level == 11) {
		    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(180);
		    }else if (level == 12) {
		    	wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(200);
		    }
	    }
	    //int xp = config.getInt("PlayerData."+owner.getName()+".Wolf."+wolf.getUniqueId()+".xp");
	    //int level = config.getInt("PlayerData."+owner.getName()+".Wolf."+wolf.getUniqueId()+".xp");
		config.set("PlayerData."+owner.getName()+".Wolf."+wolf.getUniqueId()+".xp", xp);
		config.set("PlayerData."+owner.getName()+".Wolf."+wolf.getUniqueId()+".Level", level);
		try {
			Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
	    } catch (Exception ex) {
	    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + ex.getMessage());
	    }
		
			
			List<String> wolfs = config.getStringList("PlayerData.TamedWolfs");
			
			if (!wolfs.contains(wolf.getUniqueId().toString())) {
				wolfs.add(wolf.getUniqueId().toString());
				config.set("PlayerData.TamedWolfs", wolfs);
			}
			
			config.set("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".DataName", owner.getName()+"-"+level+"-"+wolf.getMaxHealth()+"");
			config.set("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".Saved", true);
			config.set("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".Name", wolf.getCustomName());
			config.set("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".MaxHealth", wolf.getMaxHealth());
			config.set("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".Age", wolf.getAge());
			config.set("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".Color", wolf.getCollarColor());
			config.set("PlayerData."+owner.getName()+".Wolfs."+wolf.getUniqueId()+".Level", config.getDouble("PlayerData."+owner.getName()+".Wolf."+wolf.getUniqueId()+".Level"));
			try {
    			Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
    	    } catch (Exception ex) {
    	    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + ex.getMessage());
    	    }

		*/
	}
	
	@EventHandler
	public void onDeath(EntityDeathEvent e) {

			config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
			
			Entity entity = e.getEntity().getKiller();
		    if (!(entity instanceof Wolf)) {
		        return;
		    }

		    Wolf wolf = (Wolf) entity;
		    if (!wolf.isTamed()) {
		        return;
		    }
		    /*
		    Player owner = Bukkit.getPlayer(wolf.getOwner().getUniqueId());

		    double xp = config.getDouble("PlayerData."+owner.getName()+".Wolf."+wolf.getUniqueId()+".xp");
		    double level = config.getDouble("PlayerData."+owner.getName()+".Wolf."+wolf.getUniqueId()+".Level");
		    
		    xp = xp+1;
			
		    
		    if (xp >= (level*1.55)*250) {
		    	
		    	level = level+1;
		    	xp = 0;
		    	
		    }
		    
		    if (level == 2) {
		    	wolf.setMaxHealth(40);
		    	wolf.setHealth(40);
				AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 2, AttributeModifier.Operation.ADD_NUMBER);
				A.getUniqueId();
				wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
				owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 2");
				owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
				owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
		    }else if (level == 3) {
		    	wolf.setMaxHealth(60);
		    	wolf.setHealth(60);
				AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 4, AttributeModifier.Operation.ADD_NUMBER);
				A.getUniqueId();
				wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
				owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 3");
				owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
				owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
		    }else if (level == 4) {
		    	wolf.setMaxHealth(80);
		    	wolf.setHealth(80);
				AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 6, AttributeModifier.Operation.ADD_NUMBER);
				A.getUniqueId();
				wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
				owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 4");
				owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
				owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
		    }else if (level == 5) {
		    	wolf.setMaxHealth(80);
		    	wolf.setHealth(80);
				AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 8, AttributeModifier.Operation.ADD_NUMBER);
				A.getUniqueId();
				wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
				owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 5");
				owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
				owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
		    }else if (level == 6) {
		    	wolf.setMaxHealth(100);
		    	wolf.setHealth(100);
				AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 12, AttributeModifier.Operation.ADD_NUMBER);
				A.getUniqueId();
				wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
				owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 6");
				owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
				owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
		    }else if (level == 7) {
		    	wolf.setMaxHealth(120);
		    	wolf.setHealth(120);
				AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 14, AttributeModifier.Operation.ADD_NUMBER);
				A.getUniqueId();
				wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
				owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 7");
				owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
				owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
		    }else if (level == 8) {
		    	wolf.setMaxHealth(120);
		    	wolf.setHealth(120);
				AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 18, AttributeModifier.Operation.ADD_NUMBER);
				A.getUniqueId();
				wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
				owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 8");
				owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
				owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
		    }else if (level == 9) {
		    	wolf.setMaxHealth(140);
		    	wolf.setHealth(140);
				AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 22, AttributeModifier.Operation.ADD_NUMBER);
				A.getUniqueId();
				wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
				owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 9");
				owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
				owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
		    }else if (level == 10) {
		    	wolf.setMaxHealth(160);
		    	wolf.setHealth(160);
				AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 26, AttributeModifier.Operation.ADD_NUMBER);
				A.getUniqueId();
				wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
				owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 10");
				owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
				owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
		    }else if (level == 11) {
		    	wolf.setMaxHealth(180);
		    	wolf.setHealth(180);
				AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 28, AttributeModifier.Operation.ADD_NUMBER);
				A.getUniqueId();
				wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
				owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 11");
				owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
				owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
		    }else if (level == 12) {
		    	wolf.setMaxHealth(200);
		    	wolf.setHealth(200);
				AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 30, AttributeModifier.Operation.ADD_NUMBER);
				A.getUniqueId();
				wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
				wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
				owner.sendMessage(ChatColor.DARK_AQUA+"You Wolf is now Level 12");
				owner.sendMessage(ChatColor.DARK_AQUA+"Health: "+wolf.getMaxHealth());
				owner.sendMessage(ChatColor.DARK_AQUA+"Damage: "+wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor: "+wolf.getAttribute(Attribute.GENERIC_ARMOR).getValue());
				owner.sendMessage(ChatColor.DARK_AQUA+"Armor Toughness: "+wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
		    }
		    
		    //int xp = config.getInt("PlayerData."+owner.getName()+".Wolf."+wolf.getUniqueId()+".xp");
		    //int level = config.getInt("PlayerData."+owner.getName()+".Wolf."+wolf.getUniqueId()+".xp");
			config.set("PlayerData."+owner.getName()+".Wolf."+wolf.getUniqueId()+".xp", xp);
			config.set("PlayerData."+owner.getName()+".Wolf."+wolf.getUniqueId()+".Level", level);
			try {
    			Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
    	    } catch (Exception ex) {
    	    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + ex.getMessage());
    	    }
		*/
	}
	
	
}
