package cc.koffeecreations.main.property;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PropertySale extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	
	String r;
	String b;
	String s;
	int p;
	
	public PropertySale(String Region, String Buyer, String Seller, int price) {
		
		
		String r = Region;
		String b = Buyer;
		String s = Seller;
		int p;
		
	}
	

	public HandlerList getHandlers() {
	    return handlers;
	}

	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	public String getBuyer() {
		return b;
	}
	
	public String getSeller() {
		return s;
	}
	
	public String getRegion() {
		return r;
	}
	
	public int getPrice() {
		return p;
	}
	
}
