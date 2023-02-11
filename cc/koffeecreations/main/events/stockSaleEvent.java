package cc.koffeecreations.main.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class stockSaleEvent extends Event{
	
	private static final HandlerList handlers = new HandlerList();
	
	
	public int a;
	public double prc;
	String m;
	Player p;
	
	public stockSaleEvent(Player player, String market, int ammount, double price) {
	
		p = player;
		m = market;
		a = ammount;
		prc = price;
		
		
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public String getMarket() {
		return m;
	}
	
	public int getAmmount() {
		return a;
	}
	
	public double getPrice() {
		return prc;
	}
	
	public double getTotalPrice() {
		return (prc*a);
	}
	
	
	
	public HandlerList getHandlers() {
	    return handlers;
	}

	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
