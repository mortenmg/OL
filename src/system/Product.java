package system;
import java.util.List;


public class Product {
	private int id, currentStock,totalStock, bought, soundmod, sound;
	private long barcode;
	private double price;
	private String name, type, soundstring;
	

	public Product (int id, 
			String name, 
			String type,
			String soundstring,
			double price, 
			long barcode, 
			int totalStock, 
			int currentStock, 
			int bought,
			int soundmod,
			int sound){
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
		this.barcode = barcode;
		this.totalStock = totalStock;
		this.currentStock = currentStock;
		this.bought = bought;
		this.sound = sound;
		this.soundmod = soundmod;
		this.soundstring = soundstring;
	}
	
	public int getID(){
		return id;
	}
	public void setID(int id){
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public String getType(){
		return type;
	}
	
	public double getPrice(){
		return price;
	}
	
	public long getBarcode(){
		return barcode;
	}
	
	public int getCurrentStock(){
		return currentStock;
	}
	
	public int getTotalStock(){
		return totalStock;
	}
	
	public int getBought(){
		return bought;
	}

	public static boolean exists(List<Product> products){
		if(products.size() == 1){
			return true;
		} else if(products.size() != 1){
			return false;
		}
		return false;
	}
	
	public int sound(){
		return sound;
	}
	public int soundmod(){
		return soundmod;
	}
	public String soundstring(){
		return soundstring;
	}
}
