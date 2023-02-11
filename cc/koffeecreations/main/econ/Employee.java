package cc.koffeecreations.main.econ;

public class Employee {

	public int i;
	public int s;
	public int r;
	
	public Employee(int ID, int sal, int rev) {
		
	i = ID;
	s = sal;
	r = rev;
	
		
	}
	
	public int getID() {
		return i;
	}
	
	public int getSalary() {
		return s;
	}
	
	public int getRevenue() {
		return r;
	}
}
