package system;
import java.util.List;


public class User {
	
	private int id, barcode;
	private  int beer;
	private  int cider;
	private  int soda;
	private  int cocoa;
	private int other;
	private  String name;
	private  String sex;
	private  String study;
	private  String team;
	private  String rank;
	
	public User (int id, 
				String name, 
				String sex,
				int barcode, 
				String study, 
				String team, 
				String rank, 
				int beer, 
				int cider, 
				int soda, 
				int cocoa,
				int other){
		
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.barcode = barcode;
		this.study = study;
		this.team = team;
		this.rank = rank;
		this.beer = beer;
		this.cider = cider;
		this.soda = soda;
		this.cocoa = cocoa;
		this.other = other;
		
	}
	
	public int getID(){
		return id;
	}
	
	public String getName(){
		return name;
	}

	public String getSex(){
		return sex;
	}
	
	public int getBarcode(){
		return barcode;
	}
	
	public String getStudy(){
		return study;
	}
	
	public String getTeam(){
		return team;
	}
	
	public String getRank(){
		return rank;
	}
	
	public int getBeerCount(){
		return beer;
	}
	
	public int getCiderCount(){
		return cider;
	}
	
	public int getSodaCount(){
		return soda;
	}
	
	public int getCocoaCount(){
		return cocoa;
	}
	
	public int getOtherCount(){
		return other;
	}
	
	public static boolean exists(List<User> users){
		if(users.size() == 1){
			return true;
		} else if(users.size() != 1){
			return false;
		}
		return false;
	}
	
	
	
}
