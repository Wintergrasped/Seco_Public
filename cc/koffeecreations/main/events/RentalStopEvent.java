package cc.koffeecreations.main.events;

import cc.koffeecreations.main.unknow.Rental;
import cc.koffeecreations.main.property.Property;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RentalStopEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	public String r = "";
	public String n = "";
	public String ll = "";
	public double p = 0;
	public Rental rnt;
	public String stp = "";
	
	//new Rental(r, player.getName(), o, price, "PayCycle");
	
	//public Rented(String Region, String PlayerName, String LandLord, double price) {
	public RentalStopEvent(Rental rr, String StoppedBy) {    
		rnt = rr;
		r = rr.R;
		n = rr.N;
		ll = rr.o;
		p = rr.prs;
		stp = StoppedBy;
		
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
	
	public double getPrice() {
		return p;
	
	}
	
	public String getWhoStopped() {
		return stp;
	
	}
}
