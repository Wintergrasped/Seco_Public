package cc.koffeecreations.main.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import cc.koffeecreations.main.Items.InfusedArmor;
import cc.koffeecreations.main.events.ArmorEquipEvent;

public class InfusionListener implements Listener {

	public FileConfiguration config = Bukkit.getPluginManager().getPlugin("STSEcon").getConfig();
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		LivingEntity boss = (LivingEntity) e.getEntity();
		EntityDamageByEntityEvent event = e;
		
		boolean sh = false;
		boolean sc = false;
		boolean sp = false;
		boolean sb = false;
		
		boolean rh = false;
		boolean rc = false;
		boolean rp = false;
		boolean rb = false;
		
		int stc = 0;
		int rtc = 0;
		
		if (e.getDamager().getType().equals(EntityType.PLAYER)) {
			
			Player p = Bukkit.getPlayer(e.getDamager().getUniqueId());
			
			String cl = config.getString("PlayerData."+p.getName()+".Class");
			
			if (cl.equalsIgnoreCase("damage")) {
				
				if (p.getInventory().getItemInMainHand().hasItemMeta()) {
					
					
					if (p.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
						
						ItemStack mh = p.getInventory().getItemInMainHand();
						ItemMeta mhm = mh.getItemMeta();
						
						
						if (mhm.getLore().contains("Str Infusion 1")) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
						}
						
						if (mhm.getLore().contains("Regen Infusion 1")) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
						}
						
						if (mhm.getLore().contains("Might Infusion 1")) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
							e.setDamage(e.getDamage()+5);
						}
						
					}
					
					
				}
				
			}
			
		}
		
		if (e.getEntityType().equals(EntityType.PLAYER)) {
			
			Player p = Bukkit.getPlayer(e.getEntity().getName());
			
			ItemStack[] IS = p.getInventory().getArmorContents();
			
			if (!(p.getInventory().getChestplate() == null)) {
			
				if (p.getInventory().getChestplate().equals(InfusedArmor.StrNChest())) {
					sc = true;
					stc = stc+1;
					//p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
					
				}else if (p.getInventory().getChestplate().equals(InfusedArmor.RegenNChest())) {
					rc = true;
					rtc = rtc+1;
					//p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
					
				}
				
			}
			
			if (!(p.getInventory().getHelmet() == null)) {
				
				if (p.getInventory().getHelmet().equals(InfusedArmor.StrNHelm())) {
					sh = true;
					stc = stc+1;
					//p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
					
				}else if (p.getInventory().getHelmet().equals(InfusedArmor.RegenNHelm())) {
					rh = true;
					rtc = rtc+1;
					//p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
					
				}
				
			}
			
			if (!(p.getInventory().getLeggings() == null)) {
				
				if (p.getInventory().getLeggings().equals(InfusedArmor.StrNPants())) {
					sp = true;
					stc = stc+1;
					//p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
					
				}else if (p.getInventory().getLeggings().equals(InfusedArmor.RegenNPants())) {
					rp = true;
					rtc = rtc+1;
					//p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
					
				}
				
			}
			
			if (!(p.getInventory().getBoots() == null)) {
				
				if (p.getInventory().getBoots().equals(InfusedArmor.StrNBoots())) {
					sb = true;
					stc = stc+1;
					//p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
					
				}else if (p.getInventory().getBoots().equals(InfusedArmor.RegenNBoots())) {
					rb = true;
					rtc = rtc+1;
					//p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
					
				}
				
			}
			
			if (sh && sc && sp && sb) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400, 2));
			}else if (stc > 0) {
				
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, (100*stc), 1));
				
			}
			
			if (rh && rc && rp && rb) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 400, 2));
			}else if (rtc > 0) {
				
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, (100*rtc), 1));
				
			}
			
		}
	}
	
	
	
	@EventHandler
	public void onEquip(ArmorEquipEvent e) {
		if (!config.getString("PlayerData."+e.getPlayer().getName()+".Class").equalsIgnoreCase("Tank")) {
			if (e.getNewArmorPiece().getType().equals(Material.NETHERITE_CHESTPLATE)) {
				e.getPlayer().sendMessage(ChatColor.RED+"Only Tanks may wear Netherite");
				e.setCancelled(true);
			}else if (e.getNewArmorPiece().getType().equals(Material.NETHERITE_HELMET)) {
				e.getPlayer().sendMessage(ChatColor.RED+"Only Tanks may wear Netherite");
				e.setCancelled(true);
			}else if (e.getNewArmorPiece().getType().equals(Material.NETHERITE_LEGGINGS)) {
				e.getPlayer().sendMessage(ChatColor.RED+"Only Tanks may wear Netherite");
				e.setCancelled(true);
			}else if (e.getNewArmorPiece().getType().equals(Material.NETHERITE_BOOTS)) {
				e.getPlayer().sendMessage(ChatColor.RED+"Only Tanks may wear Netherite");
				e.setCancelled(true);
			}
			

		}
	}
	
}
