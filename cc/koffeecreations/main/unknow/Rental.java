package cc.koffeecreations.main.unknow;

public class Rental {

	public String R;
	public String N;
	public double prs;
	public String l;
	public String o;
	
	//Name is Name of Renter
	//Region is name of Property
	//Length is always PayCycle
	public Rental(String Region, String Name, String Owner, double Price, String Length) {
		
		R = Region;
		N = Name;
		prs = Price;
		l = Length;
		o = Owner;
		
	}
	
	public String getName() {
		return N;
	}
	
	public String getRegion() {
		return R;
	}
	
	public String getLandLord() {
		return o;
	}
	
	public double getprice() {
		return prs;
	}
	
	public String gettenant() {
		return N;
	}
	
}
