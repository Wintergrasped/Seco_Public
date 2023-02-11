package cc.koffeecreations.main.events;

import cc.koffeecreations.main.unknow.Rental;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class Rented extends Event {
	private static final HandlerList handlers = new HandlerList();

	public String r = "";
	public String n = "";
	public String ll = "";
	public double p = 0;
	public Rental rnt;
	
	//new Rental(r, player.getName(), o, price, "PayCycle");
	
	//public Rented(String Region, String PlayerName, String LandLord, double price) {
	public Rented(Rental rr) {    
		rnt = rr;
		r = rr.getRegion();
		n = rr.getName();
		ll = rr.getLandLord();
		p = rr.getprice();
		
    }
	
	public HandlerList getHandlers() {
	    return handlers;
	}

	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	public String getName() {
		return n;
	}
	
	public String getRegion() {
		return r;
	}
	
	public String getLandLord() {
		return ll;
	}
	
	public String gettenant() {
		return ll;
	}
	
	public double getPrice() {
		return p;
	}
}
