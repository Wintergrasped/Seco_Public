package cc.koffeecreations.main.events;


import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class SyncPrices extends Event {
    private static final HandlerList handlers = new HandlerList();


    
    double Mining = 0;
    double Logging = 0;
    double Farming = 0;
    
    public SyncPrices(double M, double L, double F) {
        Mining = M;
        Logging = L;
        Farming = F;
    }

    public double getFarming() {
    	return Farming;
    }
    
    public double getMining() {
    	return Mining;
    }
    
    public double getLogging() {
    	return Logging;
    }

	@Override
	public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}