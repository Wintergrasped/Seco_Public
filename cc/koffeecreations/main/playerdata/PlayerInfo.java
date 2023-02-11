package cc.koffeecreations.main.playerdata;

import java.util.ArrayList;
import java.util.List;

public class PlayerInfo {
	
	boolean debug = true;
	
	public String N;
	public String ID;

	public String H;
	public String H_E1;
	public String H_E2;
	public String H_E3;
	public String H_E4;
	
	public String C;
	public String C_E1;
	public String C_E2;
	public String C_E3;
	public String C_E4;
	
	public String L;
	public String L_E1;
	public String L_E2;
	public String L_E3;
	public String L_E4;
	
	public String B;
	public String B_E1;
	public String B_E2;
	public String B_E3;
	public String B_E4;
	
	public PlayerInfo (String Name, String UUID, String Head, String Head_E1, String Head_E2, String Head_E3, String Head_E4, String Chest, String Chest_E1, String Chest_E2, String Chest_E3, String Chest_E4, String Legs, String Legs_E1, String Legs_E2, String Legs_E3, String Legs_E4, String Boots, String Boots_E1, String Boots_E2, String Boots_E3, String Boots_E4) {
		H = Head;
		H_E1 = Head_E1;
		H_E2 = Head_E2;
		H_E3 = Head_E3;
		H_E4 = Head_E4;
		
		C = Chest;
		C_E1 = Chest_E1;
		C_E2 = Chest_E2;
		C_E3 = Chest_E3;
		C_E4 = Chest_E4;
		
		L = Legs;
		L_E1 = Legs_E1;
		L_E2 = Legs_E2;
		L_E3 = Legs_E3;
		L_E4 = Legs_E4;
		
		B = Boots;
		B_E1 = Boots_E1;
		B_E2 = Boots_E2;
		B_E3 = Boots_E3;
		B_E4 = Boots_E4;
		
	}
	
	public String getHead() {
		return H;
	}
	
	public String getChest() {
		return C;
	}
	
	public String getLegs() {
		return L;
	}
	
	public String getBoots() {
		return B;
	}
	
	public List<String> getHeadEnchants() {
		List<String> E = new ArrayList();
		E.add(H_E1);
		E.add(H_E2);
		E.add(H_E3);
		E.add(H_E4);
		
		return E;
	}
	
	public List<String> getChestEnchants() {
		List<String> E = new ArrayList();
		E.add(C_E1);
		E.add(C_E2);
		E.add(C_E3);
		E.add(C_E4);
		
		return E;
	}
	
	public List<String> getLegEnchants() {
		List<String> E = new ArrayList();
		E.add(L_E1);
		E.add(L_E2);
		E.add(L_E3);
		E.add(L_E4);
		
		return E;
	}
	
	public List<String> getBootEnchants() {
		List<String> E = new ArrayList();
		E.add(B_E1);
		E.add(B_E2);
		E.add(B_E3);
		E.add(B_E4);
		
		return E;
	}
	
}
