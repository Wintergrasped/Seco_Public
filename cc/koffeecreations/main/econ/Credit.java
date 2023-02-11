package cc.koffeecreations.main.econ;

import org.bukkit.entity.Player;

public class Credit {

	
	
	double pm = 1.02;
	double lpm = 1.10;
	double am = 1.05;
	
	double base = 100;
	
	double score;
	Player ps;
	int pmts;
	int acc;
	int lpmts;
	
	
	
	public Credit(Player p, int payments, int accounts, int LatePayments) {
		
		ps = p;
		pmts = payments;
		acc = accounts;
		lpmts = LatePayments;
		
	}
	
	public double getScore() {
		
		double sm = pmts*pm;
		sm = sm+(acc*am);
		//sm = sm/(lpmts*lpm);
		score = base*sm;
		
		return score;
	}
	
}
