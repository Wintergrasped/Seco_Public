package cc.koffeecreations.main.events;


import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EconWithdrawEvent extends Event{
	
	private static final HandlerList handlers = new HandlerList();
	
public String N = null;
public double A = 0;


	public EconWithdrawEvent(String Name, double Ammount) {
		N = Name;
		A = Ammount;
	}




	public HandlerList getHandlers() {
	    return handlers;
	}

	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
