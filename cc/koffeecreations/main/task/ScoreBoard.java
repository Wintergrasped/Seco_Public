package cc.koffeecreations.main.task;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import cc.koffeecreations.main.Main;

public class ScoreBoard {

	public static DecimalFormat price = new DecimalFormat("0.00");
	
	ScoreboardManager manager = Bukkit.getScoreboardManager();
    final Scoreboard board = manager.getNewScoreboard();
    final Objective objective = board.registerNewObjective("test", "dummy");
    
    public static void loadBoard(Main M,Player p) {
    	
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(M, new Runnable() {
            public void run() { 
            	
            	if (M == null) {

            	}
                ScoreboardManager manager = Bukkit.getScoreboardManager();
                final Scoreboard board = manager.getNewScoreboard();
                final Objective objective = board.registerNewObjective("test", "dummy");        
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                objective.setDisplayName(ChatColor.RED + "Seco Stats");
                
                Double BL = M.econ.getBalance(p.getName());
                
                int SB = BL.intValue();

                Score score = objective.getScore("Balance: "+ChatColor.GREEN+"$"+price.format(BL));
                Double S = M.config.getDouble("PlayerStocks.PlayerData."+p.getName()+".Shares");
                int ss = S.intValue();
                score.setScore(0);            
                Score score1 = objective.getScore("Player Stock Shares: "+ChatColor.GREEN+ss);
                score1.setScore(0);
                	
                Score score2 = objective.getScore("Mining Shares: "+ChatColor.GREEN+M.config.getInt("PlayerData."+p.getName()+".Stocks.Shares.Mining"));
                score2.setScore(0);                        

                Score score3 = objective.getScore("Farming Shares: "+ChatColor.GREEN+M.config.getInt("PlayerData."+p.getName()+".Stocks.Shares.Farming"));
                score3.setScore(0);
                
                Score score4 = objective.getScore("Logging Shares: "+ChatColor.GREEN+M.config.getInt("PlayerData."+p.getName()+".Stocks.Shares.Logging"));
                score4.setScore(0);
                
                p.setScoreboard(board);
            }
        },0, 20 * 10);
    }
    
	
}
