package cc.koffeecreations.main.econ;

public class Business {

	
	public double r = 0;
	public double c = 0;
	public double p = 0;
	public double sp = 0;
	public double ss = 0;
	public double sz = 0;
	public boolean db = false;
	String o = "";
	
	public Business(String Owner, double revenue, double costs, double profit, double stockShares, double size) {
		
		
		r = revenue;
		c = costs;
		p = profit;
		ss = stockShares;
		o = Owner;
		sz = size;
	}
	
	public double getRev() {
		
		if (r < 0) {
			r = 0;
		}
		
		return r;
	}
	
	public double getCost() {
		
		if (c < 0) {
			c = 0;
		}
		
		return c;
	}
	
	public double getProfit() {
		return p;
	}
	
	public double getShares() {
		
		if (ss < 0) {
			ss = 0;
		}
		
		
		return ss;
	}
	
	public double getStockPrice() {
		
		double csl = 0;
		csl = p-c;
		csl = csl/(sz*5);
		csl = csl/sz;
		sp = csl+50;
		if (sp >= 2000) {
			sp = sp/80;
		}else if (sp >= 10000) {
			sp = sp/120;
		}else if (sp >= 100000) {
			sp = sp/180;
		}
		
		if (sp < 0.02) {
			sp = 0.02;
		}

		
		return sp;
	}
	
	public double getSize() {
		
		if (sz < 0) {
			sz = 0;
		}
		
		return sz;
	}
	
	public String getOwner() {
		return o;
	}
	
	public void setRev(double nrevenue) {
		r = nrevenue;
	}
	
	public void setCost(double nCost) {
		c = nCost;
	}
	
	public void setProfit(double nProfit) {
		p = nProfit;
	}
	
	public void setShares(double d) {
		ss = d;
	}
	
	public boolean inDB() {
		return db;
	}
	
	public void setDB(boolean d) {
		db = d;
	}
	
	
}
