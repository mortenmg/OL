package system;

import javax.swing.JOptionPane;

//This class will initialize the program and load custom settings such as banners, group images, behaivure, connections and the local/remote database
//Always run this first

public class init {
	
	public static void main (String [] args){
		prefs.getPrefs();
		firstRun();
		Database.connectToDB();
		WindowUserLogin.main(null);
	}
	
	private static void firstRun(){
		if(system.prefs.firstRun){
			initializePrefs();
			String[] options = new String[] {"Create New", "Use Existing"};
			int response = JOptionPane.showOptionDialog(null, "You are starting the beer system for the first time. \nDo you wish to create a new database?", "Beer system - First run", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, "delete");
			if (response == JOptionPane.YES_OPTION) {
				String inputValue = JOptionPane.showInputDialog("Please input the name of the database");
				Database.setDatabasePath(inputValue);
			}
			if(response == JOptionPane.NO_OPTION){
				Database.setDatabasePath("");
			}
			if(response == JOptionPane.CLOSED_OPTION){
				System.exit(0);
			}
			prefs.setFirstRun(false);
			prefs.getPrefs();
		}
	}

	private static void initializePrefs() {
		prefs.setNeutralStats(true);
		prefs.setNormaliseStats(true);
	}
}

//FIXME Notes here

//SQL Command to make an individual reciept
//SELECT DEVTRANSACTIONS.ID,TIME,USER,PRODUCT,AMOUNT,NAME FROM (DEVTRANSACTIONS JOIN PRODUCTS ON DEVTRANSACTIONS.PRODUCT=PRODUCTS.BARCODE)