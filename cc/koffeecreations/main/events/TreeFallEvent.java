package cc.koffeecreations.main.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TreeFallEvent extends Event {

	
	int count = 0;
	
	private static final HandlerList handlers = new HandlerList();
	
	public TreeFallEvent(int c) {
		
		count = c;
		
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int c) {
		count = c;
	}
	
	public HandlerList getHandlers() {
	    return handlers;
	}

	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
