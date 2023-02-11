package cc.koffeecreations.main.econ;

public class EconLog {

	
	public String N;
	public double A;
	public String T;
	public double B;
	
	public EconLog(String Name, double Ammount, String Type, double Bal) {
		
		N = Name;
		A = Ammount;
		T = Type;
		B = Bal;
		
	}
	
	
	public String getName() {
		return N;
	}
	
	public double getAmmount() {
		return A;
	}
	
	public String getType() {
		return T;
	}
	
	public double getBal() {
		return B;
	}
	
}
