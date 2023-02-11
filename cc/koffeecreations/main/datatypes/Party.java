package cc.koffeecreations.main.datatypes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class Party {

	public ArrayList<Player> Players = new ArrayList();
	public ArrayList<Player> Invited = new ArrayList();
	public String N = "Party";
	
	
	public Party(String Name,ArrayList<Player> pls) {
		
		Players = pls;
		N = Name;
		
	}
	
	
	public boolean isMember(Player p) {
		if (Players.contains(p)) {
			return true;
		}else{
			return false;
		}
	}
	
	public void addPlayer(Player p) {
		
		
		if (Players.contains(p)) {
			
		}else {
			Players.add(p);
		}
		
	}
	
	public void removePlayer(Player p) {
		
		if (Players.contains(p)) {
			Players.remove(p);
		}else {
			
		}
		
	}
	
	public void invite(Player p) {
		Invited.add(p);
	}
	
	public boolean isInvited(Player p) {
		if (Invited.contains(p)) {
			return true;
		}else {
			return false;
		}
	}
	
	public String getName() {
		return N;
	}
	
	public List<Player> getMembers() {
		return Players;
	}
	
}
