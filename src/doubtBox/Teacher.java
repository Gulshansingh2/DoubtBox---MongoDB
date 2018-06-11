package doubtBox;

public class Teacher {
	private String strtName, strIP, strTime;
	
	public Teacher(String strtName, String strIP, String strTime) {
		this.strtName = strtName;
		this.strIP = strIP;
		this.strTime = strTime;
	}

		
	public String getStrtName() {
		return strtName;
	}
	
	public void setStrtName(String strtName) {
		this.strtName = strtName;
	}

	
	public String getStrIP() {
		return strIP;
	}

		
	public void setStrIP(String strIP) {
		this.strIP = strIP;
	}

	
	public String getStrTime() {
		return strTime;
	}

	
	public void setStrTime(String strTime) {
		this.strTime = strTime;
	}
	

}
