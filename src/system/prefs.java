package system;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/*
 * List of settings strings
 * Path string for banner
 * User defined admin password
 * image paths for group images ???
 * 
 */
public class prefs {
	private static Preferences prefs = Preferences.userRoot().node("OLSettings");

	public static String defaultBanner = "/images/banner2.png";
	public static String devBanner = "/images/banner.png";
	public static boolean customBanner = false;
    public static boolean neutralStats = false;
	public static int ID = 0;
    public static String IP = "127.0.0.1";
	public static String path = "";
	public static ImageIcon banner = null;
	public static String customAdminPassword = ""; //Custom admin password, not implemented
	public static String ccIP,port; //Soundboard network settings
	public static String welcomeText;
	public static String databasePath;
	public static boolean firstRun;
	private static boolean retry = false;
	
	public static boolean studynumberEnabled = false;
	
// Following variables is for statistic settings only (charts)
	public static boolean sortByTopBuyers = true;
	public static int topBuyers;
	public static int nameMaxLength;
	public static boolean normalizeStats = true;

	public static void clearSettings(){
		try {
			prefs.clear();
			prefs.removeNode();
			WindowAdmin.writeToConsole("Prefs: User preferences cleared");
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}
	
	public static void getPrefs(){
		ID = prefs.getInt("Test3",5);
		path = prefs.get("banner", defaultBanner);
		IP = prefs.get("SoundBoardIP", "0.0.0.0");
		port = prefs.get("SoundBoardPort", "1990");
		topBuyers = prefs.getInt("topBuyers", 10);
		nameMaxLength = prefs.getInt("maxNameLength", 10);
		customAdminPassword = prefs.get("password","OLProgram");
		welcomeText = prefs.get("welcomeLabel","Welcome to Rustur");
		databasePath = prefs.get("DatabasePath", "beer.db");
		firstRun = prefs.getBoolean("firstRun", true);
        neutralStats = prefs.getBoolean("neutralStats", true);
		normalizeStats = prefs.getBoolean("normalizeStats", true);
		
		System.out.println(path);
		customBanner = prefs.getBoolean("customBanner", false);
		try {
			if(!customBanner){
				banner = new ImageIcon(prefs.getClass().getResource(path));
			}
			else{
				BufferedImage img = null;
				img = ImageIO.read(new File(path));				
				banner = new ImageIcon(img);

			}
		}
		catch(Exception e){
			if(!retry){
				System.out.println("Could not find banner, retrying with default");
				prefs.put("banner", defaultBanner);
				e.printStackTrace();
				getPrefs();
				retry = true;
			}
			else{
				System.out.println("Failed to fix problem, program terminating");
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
	
	public static void setBanner(String path){
		prefs.putBoolean("customBanner", true);
		prefs.put("banner", path);
		WindowAdmin.writeToConsole("Prefs: Custom banner is set");
		
	}
	
	public static void setDefaultBanner(String path) {
		prefs.putBoolean("customBanner", false);
		defaultBanner = path;
		prefs.put("banner",path);
		WindowAdmin.writeToConsole("Prefs: Default banner is set");
	}
	
	public static void setSoundBoardIP(String IP, String port){
		prefs.put("SoundBoardIP", IP);
		prefs.put("SoundBoardPort", port);
		WindowAdmin.writeToConsole("Prefs. New soundboard ip and port is set");
	}
	
	//Change name length on stats
	public static void setMaxNameLength(int i){
		prefs.putInt("maxNameLength", i);
		WindowAdmin.writeToConsole("Prefs: Max name length changed to "+i);
	}
	
	//Change name length on stats
	public static void setTopBuyers(int i){
		prefs.putInt("topBuyers", i);
		WindowAdmin.writeToConsole("Prefs: Stats changed to show top "+i+" buyers");
	}
	public static void setAdminPassword (char [] password){
		if (password.length >= 1){
			String passwordString = "";
			for (char c: password){
				passwordString = passwordString+c;
			}
			prefs.put("password",passwordString);
			WindowAdmin.writeToConsole("Prefs: Admin password is changed to: "+passwordString);		
		}
		else{
			WindowAdmin.writeToConsole("New password was not set, field probably empty");
		}
	}
	public static void setWelcomeLabel (String welcomeLabel){
		prefs.put("welcomeLabel",welcomeLabel);
		WindowAdmin.writeToConsole("Prefs: welcomeLabel is changed to: "+welcomeLabel);
	}
	

	public static void setDatabasePath(String path) {
		prefs.put("DatabasePath",path);
		System.out.println("Prefs: DB Path set to: " + path);
		
	}
	public static void setFirstRun(Boolean b){
		prefs.putBoolean("firstRun", b);
	}

    public static void setNeutralStats(Boolean b) {
        prefs.putBoolean("neutralStats", b);
    }
	public static void setNormaliseStats(Boolean b) {
		prefs.putBoolean("normalizeStats", b);
	}
}
