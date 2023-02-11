package cc.koffeecreations.main.playerdata;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import cc.koffeecreations.main.Main;

public class DataHandler {

	boolean debug = true;
	
	public Plugin m;
	public FileConfiguration config;
	public FileConfiguration df;
	public File myfile;
	
	public String Head;
	public String Head_E1;
	public String Head_E2;
	public String Head_E3;
	public String Head_E4;
	
	public String Chest;
	public String Chest_E1;
	public String Chest_E2;
	public String Chest_E3;
	public String Chest_E4;
	
	public String Legs;
	public String Legs_E1;
	public String Legs_E2;
	public String Legs_E3;
	public String Legs_E4;
	
	public String Boots;
	public String Boots_E1;
	public String Boots_E2;
	public String Boots_E3;
	public String Boots_E4;
	
	
	
	
	public DataHandler(String Name) {
		
		m = Bukkit.getPluginManager().getPlugin("STSEcon");
		myfile = new File(m.getDataFolder()+"/UserData/"+Name+".yml");
		if (!myfile.exists()) {
            myfile.getParentFile().mkdirs();
            try {
            	df = YamlConfiguration.loadConfiguration(myfile);
				df.save(myfile);
				
				df = YamlConfiguration.loadConfiguration(myfile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            Bukkit.getLogger().log(Level.INFO,m.getDataFolder()+"/UserData/"+Name+".yml"+" Does not exist. Creating it.");
         }
		config = m.getConfig();
		df = YamlConfiguration.loadConfiguration(myfile);
		Bukkit.getLogger().log(Level.INFO,"Loading "+m.getDataFolder()+"/UserData/"+Name+".yml");

		
	}
	
	

	
	
	public void addRaid(String Name) {
		
	
		df.set("Raids.Completed", df.getInt("Raids.Completed")+1);
		saveData(Name);
		
	}
	
	public void addRaidDeath(String Name) {
		
		
		df.set("Raids.Deaths", df.getInt("Raids.Deaths")+1);
		saveData(Name);
		
	}
	
	public void addBob(String Name) {
		
		
		df.set("Raids.Bob", df.getInt("Raids.Bob")+1);
		saveData(Name);
		
	}
	
	public void addFred(String Name) {
		
		
		df.set("Raids.Fred", df.getInt("Raids.Fred")+1);
		saveData(Name);
		
	}
	
	public void addJoe(String Name) {
		
		
		df.set("Raids.Joe", df.getInt("Raids.Joe")+1);
		saveData(Name);
		
	}
	
	public void addJJ(String Name) {
		
		
		df.set("Raids.JJ", df.getInt("Raids.JJ")+1);
		saveData(Name);
		
	}

	public PlayerInfo getPlayerInfo(Player player) {
		Player p = player;
		String Name = player.getName();
		SaveGear(p);
		
		if (debug) {
			Bukkit.getLogger().log(Level.INFO, "DH.GetPlayerInfo for "+player.getName());
			
			Bukkit.getLogger().log(Level.INFO, "Head "+Head);
			Bukkit.getLogger().log(Level.INFO, "Head E1"+Head_E1);
			Bukkit.getLogger().log(Level.INFO, "Head E2"+Head_E2);
			Bukkit.getLogger().log(Level.INFO, "Head E3"+Head_E3);
			Bukkit.getLogger().log(Level.INFO, "Head E4"+Head_E4);
			
			Bukkit.getLogger().log(Level.INFO, "Chest "+Chest);
			Bukkit.getLogger().log(Level.INFO, "Chest E1"+Chest_E1);
			Bukkit.getLogger().log(Level.INFO, "Chest E2"+Chest_E2);
			Bukkit.getLogger().log(Level.INFO, "Chest E3"+Chest_E3);
			Bukkit.getLogger().log(Level.INFO, "Chest E4"+Chest_E4);
			
			Bukkit.getLogger().log(Level.INFO, "Legs "+Legs);
			Bukkit.getLogger().log(Level.INFO, "Legs E1"+Legs_E1);
			Bukkit.getLogger().log(Level.INFO, "Legs E2"+Legs_E2);
			Bukkit.getLogger().log(Level.INFO, "Legs E3"+Legs_E3);
			Bukkit.getLogger().log(Level.INFO, "Legs E4"+Legs_E4);
			
			Bukkit.getLogger().log(Level.INFO, "Boots "+Boots);
			Bukkit.getLogger().log(Level.INFO, "Boots E1"+Boots_E1);
			Bukkit.getLogger().log(Level.INFO, "Boots E2"+Boots_E2);
			Bukkit.getLogger().log(Level.INFO, "Boots E3"+Boots_E3);
			Bukkit.getLogger().log(Level.INFO, "Boots E4"+Boots_E4);
		}
		
		Head = getHead(p);
		Chest = getChest(p);
		Legs = getLegs(p);
		Boots = getBoots(p);
		
		PlayerInfo pi = new PlayerInfo(Name, player.getUniqueId().toString(), getHead(p), Head_E1, Head_E2, Head_E3, 
				Head_E4, getChest(p), Chest_E1, Chest_E2, Chest_E3, Chest_E4, getLegs(p), Legs_E1, Legs_E2, Legs_E3,
				Legs_E4, getBoots(p), Boots_E1, Boots_E2, Boots_E3, Boots_E4);
		
		
		return pi;
	}
	
	public String getHead(Player player) {
		if (player.getInventory().getHelmet() == null) {
			return "None";
		}else {
			return player.getInventory().getHelmet().getType().name();
		}
	}
	
	public String getChest(Player player) {
		if (player.getInventory().getChestplate() == null) {
			return "None";
		}else {
			return player.getInventory().getChestplate().getType().name();
		}
	}
	
	public String getLegs(Player player) {
		if (player.getInventory().getLeggings() == null) {
			return "None";
		}else {
			return player.getInventory().getLeggings().getType().name();
		}
	}
	
	public String getBoots(Player player) {
		if (player.getInventory().getBoots() == null) {
			return "None";
		}else {
			return player.getInventory().getBoots().getType().name();
		}
	}
	
	public void SaveGear(Player player) {
		
		String Name = player.getName();
		
		ItemStack Head = null;
		ItemStack Chest = null;
		ItemStack Legs = null;
		ItemStack Boots = null;
		
		boolean h = false;
		boolean c = false;
		boolean l = false;
		boolean b = false;
		
		if (player.getInventory().getHelmet() == null) {
			
		}else {
			Head = player.getInventory().getHelmet();
			h = true ;
		}
		
		if (player.getInventory().getChestplate() == null) {
			
		}else {
			Chest = player.getInventory().getChestplate();
			c = true ;
		}
		
		if (player.getInventory().getLeggings() == null) {
			
		}else {
			Legs = player.getInventory().getLeggings();
			l = true ;
		}
		
		if (player.getInventory().getBoots() == null) {
			
		}else {
			Boots = player.getInventory().getBoots();
			b = true ;
		}
		

		if (h) {
		if(Head.hasItemMeta() && Head.getItemMeta().hasEnchants()){
			int el = 1;
		    Map<Enchantment, Integer> enchantments = Head.getEnchantments();
		    for (Enchantment enchantment : enchantments.keySet()) {
		        int level = enchantments.get(enchantment);
		        setHeadEnchant(Name, el, enchantment.getName(), level);
		        if (debug) {
		        	Bukkit.getLogger().log(Level.INFO, "Saving Head Enchant Slot "+el+" as "+enchantment.getName()+" "+level+" for "+Name);
		        }
		        el++;
		    }
		    
		}else {
			setHeadEnchant(Name, 1, "None", 0);
			setHeadEnchant(Name, 2, "None", 0);
			setHeadEnchant(Name, 3, "None", 0);
			setHeadEnchant(Name, 4, "None", 0);
			if (debug) {
	        	Bukkit.getLogger().log(Level.INFO, "No Head Enchants"+Name);
	        }
		}
			setHeadType(Name, Head.getType().name());
		}else {
			setHeadType(Name, "None");
		}
		
		if (c) {
		if(Chest.hasItemMeta() && Chest.getItemMeta().hasEnchants()){
			int el = 1;
			Map<Enchantment, Integer> enchantments = Chest.getEnchantments();
		    for (Enchantment enchantment : enchantments.keySet()) {
		        int level = enchantments.get(enchantment);
		        setChestEnchant(Name, el, enchantment.getName(), level);
		        if (debug) {
		        	Bukkit.getLogger().log(Level.INFO, "Saving Chest Enchant Slot "+el+" as "+enchantment.getName()+" "+level+" for "+Name);
		        }
		        el++;
		    }
		    
		}else {
			setChestEnchant(Name, 1, "None", 0);
			setChestEnchant(Name, 2, "None", 0);
			setChestEnchant(Name, 3, "None", 0);
			setChestEnchant(Name, 4, "None", 0);
			if (debug) {
	        	Bukkit.getLogger().log(Level.INFO, "No Chest Enchants"+Name);
	        }
		}
		setChestType(Name, Chest.getType().name());
		}else {
			setChestType(Name, "None");
		}
		
		if (l) {
		if(Legs.hasItemMeta() && Legs.getItemMeta().hasEnchants()){
			int el = 1;
		    Map<Enchantment, Integer> enchantments = Legs.getEnchantments();
		    for (Enchantment enchantment : enchantments.keySet()) {
		        int level = enchantments.get(enchantment);
		        setLegEnchant(Name, el, enchantment.getName(), level);
		        if (debug) {
		        	Bukkit.getLogger().log(Level.INFO, "Saving Leg Enchant Slot "+el+" as "+enchantment.getName()+" "+level+" for "+Name);
		        }
		        el++;
		    }
		    
		}else {
			setLegEnchant(Name, 1, "None", 0);
			setLegEnchant(Name, 2, "None", 0);
			setLegEnchant(Name, 3, "None", 0);
			setLegEnchant(Name, 4, "None", 0);
			if (debug) {
	        	Bukkit.getLogger().log(Level.INFO, "No Leg Enchants"+Name);
	        }
		}
		setLegsType(Name, Legs.getType().name());
		}else {
			setLegsType(Name, "None");
		}
		
		if (b) {
		if(Boots.hasItemMeta() && Boots.getItemMeta().hasEnchants()){
			int el = 1;
		    Map<Enchantment, Integer> enchantments = Boots.getEnchantments();
		    for (Enchantment enchantment : enchantments.keySet()) {
		        int level = enchantments.get(enchantment);
		        setBootEnchant(Name, el, enchantment.getName(), level);
		        if (debug) {
		        	Bukkit.getLogger().log(Level.INFO, "Saving Boot Enchant Slot "+el+" as "+enchantment.getName()+" "+level+" for "+Name);
		        }
		        el++;
		    }
		    
		}else {
			setBootEnchant(Name, 1, "None", 0);
			setBootEnchant(Name, 2, "None", 0);
			setBootEnchant(Name, 3, "None", 0);
			setBootEnchant(Name, 4, "None", 0);
			if (debug) {
	        	Bukkit.getLogger().log(Level.INFO, "No Boot Enchants"+Name);
	        }
		}
		setBootsType(Name, Boots.getType().name());
		}else {
			setBootsType(Name, "None");
		}
			
		
		
		
		
		
		
		df.set("Raids.Completed", df.getInt("Raids.Completed")+1);
		saveData(Name);
		
	}
	
	public void setHeadType(String Name, String type) {
		
		df.set("Gear.Head.Type", type);
		saveData(Name);
	}
	
	public void setHeadEnchant(String Name, int Slot, String type, int level) {
		
		
		if (Slot == 1) {
			df.set("Gear.Head.E1", type+" "+level);
			Head_E1 = type+" "+level;
			saveData(Name);
		}else if (Slot == 2) {
			df.set("Gear.Head.E2", type+" "+level);
			Head_E2 = type+" "+level;
			saveData(Name);
		}else if (Slot == 3) {
			df.set("Gear.Head.E3", type+" "+level);
			Head_E3 = type+" "+level;
			saveData(Name);
		}else if (Slot == 4) {
			df.set("Gear.Head.E4", type+" "+level);
			Head_E4 = type+" "+level;
			saveData(Name);
		}else if (Slot == 5) {
			df.set("Gear.Head.E5", type+" "+level);
			saveData(Name);
		}else if (Slot == 6) {
			df.set("Gear.Head.E6", type+" "+level);
			saveData(Name);
		}else if (Slot == 7) {
			df.set("Gear.Head.E7", type+" "+level);
			saveData(Name);
		}else if (Slot == 8) {
			df.set("Gear.Head.E8", type+" "+level);
			saveData(Name);
		}else if (Slot == 9) {
			df.set("Gear.Head.E9", type+" "+level);
			saveData(Name);
		}

	}
	
	public void setChestEnchant(String Name, int Slot, String type, int level) {
		
		
		if (Slot == 1) {
			df.set("Gear.Chest.E1", type+" "+level);
			Chest_E1 = type+" "+level;
			saveData(Name);
		}else if (Slot == 2) {
			df.set("Gear.Chest.E2", type+" "+level);
			Chest_E2 = type+" "+level;
			saveData(Name);
		}else if (Slot == 3) {
			df.set("Gear.Chest.E3", type+" "+level);
			Chest_E3 = type+" "+level;
			saveData(Name);
		}else if (Slot == 4) {
			df.set("Gear.Chest.E4", type+" "+level);
			Chest_E4 = type+" "+level;
			saveData(Name);
		}else if (Slot == 5) {
			df.set("Gear.Chest.E5", type+" "+level);
			saveData(Name);
		}else if (Slot == 6) {
			df.set("Gear.Chest.E6", type+" "+level);
			saveData(Name);
		}else if (Slot == 7) {
			df.set("Gear.Chest.E7", type+" "+level);
			saveData(Name);
		}else if (Slot == 8) {
			df.set("Gear.Chest.E8", type+" "+level);
			saveData(Name);
		}else if (Slot == 9) {
			df.set("Gear.Chest.E9", type+" "+level);
			saveData(Name);
		}

	}
	
	public void setLegEnchant(String Name, int Slot, String type, int level) {
		
		
		if (Slot == 1) {
			df.set("Gear.Leg.E1", type+" "+level);
			Legs_E1 = type+" "+level;
			saveData(Name);
		}else if (Slot == 2) {
			df.set("Gear.Leg.E2", type+" "+level);
			Legs_E2 = type+" "+level;
			saveData(Name);
		}else if (Slot == 3) {
			df.set("Gear.Leg.E3", type+" "+level);
			Legs_E3 = type+" "+level;
			saveData(Name);
		}else if (Slot == 4) {
			df.set("Gear.Leg.E4", type+" "+level);
			Legs_E4 = type+" "+level;
			saveData(Name);
		}else if (Slot == 5) {
			df.set("Gear.Leg.E5", type+" "+level);
			saveData(Name);
		}else if (Slot == 6) {
			df.set("Gear.Leg.E6", type+" "+level);
			saveData(Name);
		}else if (Slot == 7) {
			df.set("Gear.Leg.E7", type+" "+level);
			saveData(Name);
		}else if (Slot == 8) {
			df.set("Gear.Leg.E8", type+" "+level);
			saveData(Name);
		}else if (Slot == 9) {
			df.set("Gear.Leg.E9", type+" "+level);
			saveData(Name);
		}

	}
	
	public void setBootEnchant(String Name, int Slot, String type, int level) {
	
		
		if (Slot == 1) {
			df.set("Gear.boot.E1", type+" "+level);
			Boots_E1 = type+" "+level;
			saveData(Name);
		}else if (Slot == 2) {
			df.set("Gear.boot.E2", type+" "+level);
			Boots_E2 = type+" "+level;
			saveData(Name);
		}else if (Slot == 3) {
			df.set("Gear.boot.E3", type+" "+level);
			Boots_E3 = type+" "+level;
			saveData(Name);
		}else if (Slot == 4) {
			df.set("Gear.boot.E4", type+" "+level);
			Boots_E4 = type+" "+level;
			saveData(Name);
		}else if (Slot == 5) {
			df.set("Gear.boot.E5", type+" "+level);
			saveData(Name);
		}else if (Slot == 6) {
			df.set("Gear.boot.E6", type+" "+level);
			saveData(Name);
		}else if (Slot == 7) {
			df.set("Gear.boot.E7", type+" "+level);
			saveData(Name);
		}else if (Slot == 8) {
			df.set("Gear.boot.E8", type+" "+level);
			saveData(Name);
		}else if (Slot == 9) {
			df.set("Gear.boot.E9", type+" "+level);
			saveData(Name);
		}

	}
	
	public void setChestType(String Name, String type) {
		
		df.set("Gear.Chest.Type", type);
		saveData(Name);
	}
	
	public void setLegsType(String Name, String type) {
		
		df.set("Gear.Leg.Type", type);
		saveData(Name);
	}
	
	public void setBootsType(String Name, String type) {
		
		df.set("Gear.Boot.Type", type);
		saveData(Name);
	}
	
	
	
	
	
	
	
	public void saveData(String Name) {
		//myfile = new File(Bukkit.getPluginManager().getPlugin("STSEcon").getDataFolder()+"/UserData/"+Name+".yml");
		try {
			df.save(myfile);
			df = YamlConfiguration.loadConfiguration(myfile);
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public FileConfiguration dataFile(String Name) {
		
		return df;
	}
	
	
	public void saveConfig() {
		try {
			Bukkit.getPluginManager().getPlugin("STSEcon").saveConfig();
	    } catch (Exception e) {
	    	Bukkit.getPluginManager().getPlugin("STSEcon").getLogger().log(Level.SEVERE, "An error occurred while saving the configuration: " + e.getMessage());
	    }
	}
}
