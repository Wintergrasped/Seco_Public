package cc.koffeecreations.main.events;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import cc.koffeecreations.main.econ.Business;
import cc.koffeecreations.main.unknow.Rental;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class payCycleEvent extends Event{
	
	private static final HandlerList handlers = new HandlerList();
	
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	List<Business> bl = new ArrayList();
	List<Player> pl = new ArrayList();
	List<Rental> rl = new ArrayList();
	List<String> bos = new ArrayList();
	


	public payCycleEvent(List<Player> pls, List<Business> bls) {
		bl = bls;
		pl = pls;
		Bukkit.getLogger().log(Level.INFO, "PayCyle Event Ran");
		//rl = rls;
	}

	public HandlerList getHandlers() {
	    return handlers;
	}
	
	public List<Player> getPlayers() {
		return pl;
	}
	
	public List<Business> getBusiness() {
		return bl;
	}
	
	public List<String> getBusinessOwners() {
		
		bos = new ArrayList();
		
		for (Business b:bl) {
			bos .add(b.getOwner());
		}
		
		return bos;
	}

}
