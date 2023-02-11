package cc.koffeecreations.main.raids;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Elkhurst implements Listener {

	Location FD = new Location(Bukkit.getWorld("world"), -1300, 68, -949);
	Location fred = new Location(Bukkit.getWorld("world"), -1322, 92, -910);
	boolean FT = false;
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		if (triggerCheck(e.getPlayer().getLocation(), FD, 5)) {
			
			if (!FT) {
				
				FT = true;
				spawnFred(fred);
				
			}else {
				return;
			}
			
		}else {
			return;
		}
		
	}
	
	
	
	
	
	
	
	
	public void spawnFred(Location l) {
		// Create a new instance of an EntityType (in this case, a zombie)
		EntityType skeleton = EntityType.SKELETON;
		
		World world = l.getWorld();

		// Create a new instance of the entity using the EntityType
		LivingEntity raidBoss = (LivingEntity) world.spawnEntity(l, skeleton);

		// Set the health of the raid boss to a higher value than a normal zombie
		raidBoss.setMaxHealth(200);
		raidBoss.setHealth(200);
		raidBoss.setCustomName("Fred");
		raidBoss.setCustomNameVisible(true);

		// You can also add custom attributes to the raid boss, 
		// such as increased damage or resistance to certain types of damage
		
		AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 20, AttributeModifier.Operation.ADD_NUMBER);
		raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
		//raidBoss.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addModifier(A);
		//raidBoss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(A);
		//raidBoss.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).addModifier(A);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
		
	}
	
	public void spawnJoe(Location l) {
		// Create a new instance of an EntityType (in this case, a zombie)
		EntityType zombie = EntityType.ZOMBIE;
		
		World world = l.getWorld();

		// Create a new instance of the entity using the EntityType
		LivingEntity raidBoss = (LivingEntity) world.spawnEntity(l, zombie);

		// Set the health of the raid boss to a higher value than a normal zombie
		raidBoss.setMaxHealth(200);
		raidBoss.setHealth(200);
		raidBoss.setCustomName("Joe");
		raidBoss.setCustomNameVisible(true);
		// You can also add custom attributes to the raid boss, 
		// such as increased damage or resistance to certain types of damage
		
		AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 20, AttributeModifier.Operation.ADD_NUMBER);
		raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
		
		
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
}
