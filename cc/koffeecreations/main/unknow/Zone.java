package cc.koffeecreations.main.unknow;

public class Zone {

	public String Type;
	
	public Zone() {
	
		
		
	}
	
	public String getType() {
		
		return Type;
	}
	
	public void setType(String T) {
		Type = T;
	}
	
	public boolean isType(String T) {
		if (Type.equalsIgnoreCase(T)) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isInd() {
		
		if (Type.equalsIgnoreCase("ind")) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isRes() {
		
		if (Type.equalsIgnoreCase("res")) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isCom() {
		
		if (Type.equalsIgnoreCase("com")) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isRec() {
		
		if (Type.equalsIgnoreCase("rec")) {
			return true;
		}else {
			return false;
		}
	}
	
	
}
