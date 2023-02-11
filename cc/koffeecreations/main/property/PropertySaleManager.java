package cc.koffeecreations.main.property;

public class PropertySaleManager {

	
	String N;
	String S;
	Property P;
	int SP;
	String T;
	
	public PropertySaleManager(String Name, String Seller, Property Pr, int Price, String Type) {
		
		N = Name;
		S = Seller;
		P = Pr;
		SP = Price;
		T = Type;
		
	}
	
	public String getName() {
		return N;
	}
	
	public String getSeller() {
		return S;
	}
	
	public String getType() {
		return T;
	}
	
	public int getPrice() {
		return SP;
	}
	
	public Property getProperty() {
		return P;
	}
	
}
