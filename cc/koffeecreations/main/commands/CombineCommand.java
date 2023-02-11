package cc.koffeecreations.main.commands;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CombineCommand {

	
	public void Combine(Player p) {
		
		ItemStack mh = p.getInventory().getItemInMainHand();
		ItemStack oh = p.getInventory().getItemInOffHand();
		if (oh == null) {
			p.sendMessage(ChatColor.RED+"Nothing Detected in Off Hand");
			return;
		}
		
		if (oh.getType().equals(Material.ENCHANTED_BOOK)) {
			
			if (!(mh == null)) {
				
				if (p.getLevel() >= 25) {
					p.setLevel(p.getLevel()-25);
				Map<Enchantment, Integer> enchantments = oh.getEnchantments();
			    for (Enchantment enchantment : enchantments.keySet()) {
			        int level = enchantments.get(enchantment);
			        mh.addUnsafeEnchantment(enchantment, level);
			    }
			    oh.setAmount(0);
				}else {
					p.sendMessage(ChatColor.RED+"You need to be level 25 for this");
				}
			}else {
				p.sendMessage(ChatColor.RED+"Nothing Detected in Main Hand");
			}
			
		}else {
			if (!(mh == null)) {
				
				if (p.getLevel() >= 25) {
					p.setLevel(p.getLevel()-25);
				Map<Enchantment, Integer> enchantments = oh.getEnchantments();
			    for (Enchantment enchantment : enchantments.keySet()) {
			        int level = enchantments.get(enchantment);
			        mh.addUnsafeEnchantment(enchantment, level);
			    }
			    oh.setAmount(0);
				}else {
					p.sendMessage(ChatColor.RED+"You need to be level 25 for this");
				}
			}else {
				p.sendMessage(ChatColor.RED+"Nothing Detected in Main Hand");
			}
		}
		
	}
	
	
}
