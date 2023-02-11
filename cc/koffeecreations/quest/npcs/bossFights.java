package cc.koffeecreations.quest.npcs;

import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.data.type.Fire;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class bossFights implements Listener{

	
	
	
	/*
	@EventHandler
	public void entityDamaged(EntityDamageEvent e) {
		
		switch (e.getEntity().getLocation().getWorld().getName()) {

			case "arena":
				
				switch (e.getEntityType()) {
				
				case WITHER_SKELETON:
					
					LivingEntity boss = (LivingEntity) e.getEntity();
					
					if (boss.getMaxHealth() == 800) {
						Location l = e.getEntity().getLocation();
						
						int a = random(1,85);
						int b = random(1,65);
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.5) {
							
							 a = random(1,85);
							 b = random(1,65);
							
							if (a == b) {
								spawnJoe(l);
							}
							
							if (b == 15) {
								spawnFred(l);
							}
							
							if (a == 6) {
								LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add3 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add4 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add5 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
							}
							
							if (b == 9) {
								LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add3 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add4 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
								LivingEntity add5 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
							}
							
							if (b == 20) {
								LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
								LivingEntity add3 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
								LivingEntity add4 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
								LivingEntity add5 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
							}
							
							if (a == 21) {
								LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
								LivingEntity add3 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
								LivingEntity add4 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
								LivingEntity add5 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CAVE_SPIDER);
							}
							
							
						}
						
						if (boss.getHealth() / boss.getMaxHealth() <= 0.1) {
							a = random(1,85);
							b = random(1,65);
							if (a==b) {
								Location playerLoc = boss.getLastDamageCause().getEntity().getLocation();
								TNTPrimed tnt = playerLoc.getWorld().spawn(playerLoc, TNTPrimed.class);
								tnt.setFuseTicks(30);
								tnt.setIsIncendiary(true);
								tnt.setYield(0);
							}
							LivingEntity add1 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);
						
							if (boss.getHealth() / boss.getMaxHealth() <= 0.02) {
								LivingEntity add2 = (LivingEntity) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.BLAZE);
							}
						}
					}
					
					break;
				
				}
				
				
				break;
			
		
		}
		
	}
	*/
	
	
	
	public void spawnBob(Location l) {
		// Create a new instance of an EntityType (in this case, a zombie)
		EntityType skeleton = EntityType.WITHER_SKELETON;
		
		World world = l.getWorld();

		// Create a new instance of the entity using the EntityType
		LivingEntity raidBoss = (LivingEntity) world.spawnEntity(l, skeleton);

		// Set the health of the raid boss to a higher value than a normal zombie
		raidBoss.setMaxHealth(800);
		raidBoss.setHealth(800);

		// You can also add custom attributes to the raid boss, 
		// such as increased damage or resistance to certain types of damage
		
		AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 50, AttributeModifier.Operation.ADD_NUMBER);
		A.getUniqueId();
		raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
		raidBoss.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addModifier(A);
		raidBoss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(A);
		raidBoss.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).addModifier(A);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
		raidBoss.setCustomName("Bob");
		
		
		
		raidBoss.setCustomNameVisible(true);
		
		
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

		// You can also add custom attributes to the raid boss, 
		// such as increased damage or resistance to certain types of damage
		
		AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 20, AttributeModifier.Operation.ADD_NUMBER);
		raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
		
		
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

		// You can also add custom attributes to the raid boss, 
		// such as increased damage or resistance to certain types of damage
		
		AttributeModifier A = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 20, AttributeModifier.Operation.ADD_NUMBER);
		raidBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(A);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR).addModifier(A);
		raidBoss.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addModifier(A);
		raidBoss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(A);
		raidBoss.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).addModifier(A);
		raidBoss.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).addModifier(A);
		
	}
	
	
	
	
	
	
	
	public void spawnFire(Location L){
        Location bossLoc = L;
        World world = bossLoc.getWorld();
        for(int i=0; i<5; i++){
            double x = bossLoc.getX() + (Math.random() * 3) - 1.5;
            double y = bossLoc.getY() + (Math.random() * 3) - 1.5;
            double z = bossLoc.getZ() + (Math.random() * 3) - 1.5;
            Location fireLoc = new Location(world, x, y, z);
            if (world.getBlockAt(bossLoc).getType().equals(Material.AIR)) {
            	world.getBlockAt(fireLoc).setType(Material.FIRE);
            }
        }
    }
	
	
	
	
	
	public int random(int min, int max) {

        Random rand = new Random();
        rand.setSeed(Bukkit.getServer().getWorld("World").getFullTime()+Bukkit.getBannedPlayers().size()+Bukkit.getOnlinePlayers().size()+System.currentTimeMillis()+System.nanoTime()+Bukkit.getOnlinePlayers().size()+Bukkit.getOperators().size()+Bukkit.getPort()+Bukkit.getOfflinePlayers().length+Bukkit.getIdleTimeout()+Bukkit.getIPBans().size()+Bukkit.getWorlds().size()+Bukkit.getName().getBytes().length+Bukkit.getWorld("world").getSeed());
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
	
	
	
	
}
