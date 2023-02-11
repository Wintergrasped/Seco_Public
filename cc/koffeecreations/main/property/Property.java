package cc.koffeecreations.main.property;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Property {
	
	
	public String N;
	public String O;
	public String C;
	public Location l1;
	public Location l2;
	public List<String> Trusted = new ArrayList();
	public boolean ForSale = false;
	public int Price = 0;
	public String SaleType = "None";
	public String RentedBy = "None";
	public String Zone = "Res";
	public boolean inDB = false;
	public boolean rented = false;
	
	public Property (String Name, String Owner, Location L1, Location L2) {
		
		N = Name;
		O = Owner;
		l1 = L1;
		l2 = L2;
		
	}
	
	public String getOwner() {
		return O;
	}
	
	public String getName() {
		return N;
	}
	
	public String getRenter() {
		return RentedBy;
	}
	
	public Location getMinLoc() {
		return l1;
	}
	
	public Location getMaxLoc() {
		return l2;
	}
	
	public List<String> getTrusted() {
		return Trusted;
	}
	
	public void addTrusted(String N) {
		Trusted.add(N);
	}
	
	public void removeTrusted(String N) {
		Trusted.remove(N);
	}
	
	public void setOwner(String Owner) {
		O = Owner;
	}
	
	public boolean getForSale() {
		return ForSale;
	}
	
	public void setForSale(boolean fs) {
		ForSale = fs;
	}
	
	public int getPrice() {
		return Price;
	}
	
	public void setPrice(int PRC) {
		Price = PRC;
	}
	
	public String getSaleType() {
		return SaleType;
	}
	
	public void SetSaleType(String ST) {
		SaleType = ST;
	}
	
	public void setRenter(String Renter) {
		RentedBy = Renter;
	}
	
	public String getZone() {
		
		return Zone;
	}
	
	public void setZone(String Z) {
		
		Zone = Z;
	}
	
	public boolean isZone(String Z) {
		
		if (Zone.equalsIgnoreCase(Z)) {
			return true;
		}else{
			return false;
		}
		
	}
	
	public boolean inDB() {
		return inDB;
	}
	
	public void setinDB(Boolean DB) {
		inDB=DB;
	}

	public boolean rented() {
		return rented;
	}
	
	public void setRented(boolean R) {
		rented = R;
	}
	
}
