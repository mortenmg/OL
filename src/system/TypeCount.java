package system;

public class TypeCount {

	private int beer,cider,soda,cocoa,other;
	
	public TypeCount (int beer, int cider, int soda, int cocoa, int other){
		this.beer = beer;
		this.cider = cider;
		this.soda = soda;
		this.cocoa = cocoa;
		this.other = other;
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
	
}
