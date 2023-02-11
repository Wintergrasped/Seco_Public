package cc.koffeecreations.main.task;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import cc.koffeecreations.main.Main;
import org.bukkit.ChatColor;

public class PlayerIntro {

	
	public static void begin(Main M, Player p) {
		
		
		p.sendTitle(ChatColor.AQUA+"Welcome to Seco MC", ChatColor.GOLD+"To get Started do "+ChatColor.BOLD+"/start", 20, 200, 20);
		p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 15, 15);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(M, new Runnable() {

			@Override
			public void run() {
				p.sendTitle(ChatColor.AQUA+"What to Know?", ChatColor.GRAY+"You start in City Limits. To build in City Limits you must own the land.", 20, 200, 20);
				p.playSound(p.getLocation(), Sound.ITEM_CROP_PLANT, 15, 15);
			}
			
			
		}, 240);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(M, new Runnable() {

			@Override
			public void run() {
				p.sendTitle(ChatColor.AQUA+"How to Build", ChatColor.GRAY+"To be able to build do /warp out", 20, 200, 20);
				p.playSound(p.getLocation(), Sound.ITEM_CROP_PLANT, 15, 15);
				
			}
			
			
		}, 480);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(M, new Runnable() {

			@Override
			public void run() {
				p.sendTitle(ChatColor.AQUA+"The Wiki", ChatColor.GRAY+"It will be very difficult to learn this server without reading the wiki at /wiki", 20, 200, 20);
				p.playSound(p.getLocation(), Sound.ITEM_CROP_PLANT, 15, 15);
				
			}
			
			
		}, 720);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(M, new Runnable() {

			@Override
			public void run() {
				p.sendTitle(ChatColor.AQUA+"The Discord", ChatColor.GRAY+"Alot of helpful players and staff are avalible in the discord at /discord", 20, 200, 20);
				p.playSound(p.getLocation(), Sound.ITEM_CROP_PLANT, 15, 15);
				
			}
			
			
		}, 960);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(M, new Runnable() {

			@Override
			public void run() {
				
				p.sendTitle(ChatColor.AQUA+"Help Menu", ChatColor.GRAY+"Doing /start and /psh will display the servers help menus", 20, 200, 20);
				p.playSound(p.getLocation(), Sound.ITEM_CROP_PLANT, 15, 15);
				
			}
			
			
		}, 1200);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(M, new Runnable() {

			@Override
			public void run() {
				p.sendTitle(ChatColor.AQUA+"Have Fun", ChatColor.GRAY+"Enjoy the server in your own play style", 20, 200, 20);
				p.playSound(p.getLocation(), Sound.ITEM_HONEY_BOTTLE_DRINK, 15, 15);
				
			}
			
			
		}, 1440);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(M, new Runnable() {

			@Override
			public void run() {
				//song(M, p);
				
			}
			
			
		}, 985);
		
	}
	
	
	
	public static void motd(Main M, Player p) {
		
		
		p.sendTitle(ChatColor.AQUA+"Seco MC", ChatColor.GOLD+M.config.getString("ServerData.MOTD"), 20, 150, 20);
		p.playSound(p.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 15, 15);
		
		
	}
	
	public static void song(Main M, Player p) {
		//20 5 20 20 11 6 4
		
		p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 100, 0);//D
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(M, new Runnable() {

			@Override
			public void run() {
				
				p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 100, 2);//B
				
			}
			
			
		}, 10);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(M, new Runnable() {

			@Override
			public void run() {
				
				p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 100, 0);//D
				
			}
			
			
		}, 20);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(M, new Runnable() {

			@Override
			public void run() {
				
				p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 100, 0);//D
				
			}
			
			
		}, 30);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(M, new Runnable() {

			@Override
			public void run() {
				
				p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 100, 2);//F
				
			}
			
			
		}, 40);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(M, new Runnable() {

			@Override
			public void run() {
				
				p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 100, 2);//C
				
			}
			
			
		}, 50);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(M, new Runnable() {

			@Override
			public void run() {
				
				p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 100, 0);//B
				
			}
			
			
		}, 60);
		
	}
	
public static void marketHelp(Main M, Player p) {
		
		
		p.sendTitle(ChatColor.AQUA+"Stock Types", ChatColor.GOLD+" We have 3 main stock types Player, Industry, Business", 20, 150, 20);
		p.playSound(p.getLocation(), Sound.ITEM_HONEY_BOTTLE_DRINK, 15, 15);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(M, new Runnable() {

			@Override
			public void run() {
				p.sendTitle(ChatColor.AQUA+"Player Stocks", ChatColor.GRAY+"Change price based off player traffic on the server. You earn 1 per hour and can buy and sell freely", 20, 150, 20);
				p.playSound(p.getLocation(), Sound.ITEM_CROP_PLANT, 15, 15);
			}
			
			
		}, 200);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(M, new Runnable() {

			@Override
			public void run() {
				p.sendTitle(ChatColor.AQUA+"Industry Stocks", ChatColor.GRAY+"Change Price based off player activeties. You can buy and sell these freely", 20, 150, 20);
				p.playSound(p.getLocation(), Sound.ITEM_CROP_PLANT, 15, 15);
				
			}
			
			
		}, 375);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(M, new Runnable() {

			@Override
			public void run() {
				p.sendTitle(ChatColor.AQUA+"Business Stocks", ChatColor.GRAY+"Business Stocks are driven by a players business perforamnce and also earn you dividends", 20, 150, 20);
				p.playSound(p.getLocation(), Sound.ITEM_CROP_PLANT, 15, 15);
				
			}
			
			
		}, 575);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(M, new Runnable() {

			@Override
			public void run() {
				p.sendTitle(ChatColor.AQUA+"Buying and Selling", ChatColor.GRAY+"All of the mentioned stock types can be bought and sold in /markets", 20, 150, 20);
				p.playSound(p.getLocation(), Sound.ITEM_CROP_PLANT, 15, 15);
				
			}
			
			
		}, 775);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(M, new Runnable() {

			@Override
			public void run() {
				p.sendTitle(ChatColor.AQUA+"Help Menu", ChatColor.GRAY+"Doing /start and /psh will display the servers help menus", 20, 150, 20);
				p.playSound(p.getLocation(), Sound.ITEM_CROP_PLANT, 15, 15);
				
			}
			
			
		}, 975);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(M, new Runnable() {

			@Override
			public void run() {
				p.sendTitle(ChatColor.AQUA+"Have Fun", ChatColor.GRAY+"This server is ment to embrace many different play styles play the game as youd like.", 20, 150, 20);
				p.playSound(p.getLocation(), Sound.ITEM_CROP_PLANT, 15, 15);
				
			}
			
			
		}, 975);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(M, new Runnable() {

			@Override
			public void run() {
				//song(M, p);
				
			}
			
			
		}, 985);
		
	}
	
}
//TODO Change color of command, Slow Down, Take away Tink Sound
