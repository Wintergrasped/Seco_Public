package cc.koffeecreations.main.Runnable;

import org.bukkit.scheduler.BukkitRunnable;

import cc.koffeecreations.main.Main;
import cc.koffeecreations.main.task.Repeating;

public class RepeatingRunnable extends BukkitRunnable {

	Main MM = null;
	
	
	public RepeatingRunnable(Main M) {
		MM = M;
	}
	
	@Override
	public void run() {
		
		new Repeating(MM);
		
	}

}
