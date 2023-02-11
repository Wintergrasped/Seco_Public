package cc.koffeecreations.main.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.sk89q.worldedit.regions.Region;

public class propertyValueRequestEvent extends Event{
	
	private static final HandlerList handlers = new HandlerList();

	Region RR;
	Player P;


	public propertyValueRequestEvent(Player PL, Region R) {
		RR = R;
		P = PL;
	}
	
	public Region getRegion() {
		return RR;
	}
	
	public Player getPlayer() {
		return P;
	}


	public HandlerList getHandlers() {
	    return handlers;
	}

	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
