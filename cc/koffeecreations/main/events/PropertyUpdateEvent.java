package cc.koffeecreations.main.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.sk89q.worldedit.regions.Region;

public class PropertyUpdateEvent extends Event{
	
	private static final HandlerList handlers = new HandlerList();



	public PropertyUpdateEvent() {

	}


	public HandlerList getHandlers() {
	    return handlers;
	}

	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
