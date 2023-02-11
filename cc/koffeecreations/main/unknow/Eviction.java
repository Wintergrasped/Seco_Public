package cc.koffeecreations.main.unknow;

public class Eviction {
	
	
	
	public String Landlord;
	public String Tenant;
	public String Region;
	
	
	public Eviction(String L, String T, String R) {
	
		Landlord = L;
		Tenant = T;
		Region = R;
		
	}
	
	public String getLandlord() {
		return Landlord;
	}
	
	public String getTenant() {
		return Tenant;
	}
	
	public String getRegion() {
		return Region;
	}

}
