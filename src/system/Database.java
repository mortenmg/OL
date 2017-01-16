package system;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class Database {

	//Definitions
	private static Connection c = null;
	private static Statement stmt = null;
	private static Statement stmtStack = null;

	//Test
	public static void main( String args[] )
	{

		String s = "2,6";
		System.out.println(s.substring(0, s.indexOf(",")));
		System.out.println(s.substring(s.indexOf(",")+1,s.length()));

		//Initialise database
		//		connectToDB();
		//		// Get max ID
		//		try {
		//			System.out.println(""+ stmt.executeQuery("SELECT MAX(ID) FROM USERS").getInt(1));
		//		} catch (SQLException e) {
		//			e.printStackTrace();
		//		}
		//				disconnectFromDB();
		//				importUsers("C:/Users/Graver/workspace/OLProgram/OL/Test Data/UTD 300.csv");
		//		
		//				editUser("retardo",1001);
		//				displayTable("SELECT * FROM USERS where BARCODE=1001");
		//		
		//				//Display the database
		//				//displayTable("SELECT * FROM USERS;"); //Select all
		//				
		//				//Exports all data into a .csv file
		//				export("C:/Users/Graver/workspace/OLProgram/OL/Test Data/export.csv"); 
	}
	public static void testDB(){
		try {
			if(c.isClosed()){
				connectToDB();
			}
			stmt = c.createStatement();
			String sql = "SELECT * FROM TABLETHATDOSENTEXIST;";
			ResultSet rs = stmt.executeQuery(sql);
			stmt.close();
			while (rs.next()){
				System.out.println(rs.getWarnings());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Connections
	public static void connectToDB(){
		try {
			Class.forName("org.sqlite.JDBC");
//			c = DriverManager.getConnection("jdbc:sqlite:beerRus2015.db"); //Original, this works! (Use for manual debug)
			c = DriverManager.getConnection("jdbc:sqlite:"+prefs.databasePath);
			if (c.isClosed()){
				//				WindowAdmin.writeToConsole("Database: Failed to open database"); //Cannot write to log here, log is not created
			}
			else{
				//				WindowAdmin.writeToConsole("Database: Opened database successfully "); //Cannot write to log here, log is not created
			}
			c.setAutoCommit(false);
			try{
				stmt = c.createStatement();
				stmt.executeQuery("SELECT * FROM USERS;");
				stmt.close();
				//				System.out.println("Table already exists");

			}
			catch (SQLException e){
				//				e.printStackTrace();
				createLogTable();
				createUserTable();
				createProductTable();
				createTransactionTable();
				createDEVTransactionTable(); //TODO Test transaction table
				createTransactionAccumulationView();
				createStatsView();
			}
			//region Backwards compatability check for 1.9 to 1.10 (Soundboard update)
			try{
				stmt = c.createStatement();
				stmt.executeQuery("SELECT soundstring FROM PRODUCTS");
				stmt.close();
			}
			catch(SQLException e){
				stmt = c.createStatement();
				stmt.addBatch("ALTER TABLE PRODUCTS ADD SOUND BOOLEAN NOT NULL DEFAULT 0");
				stmt.addBatch("ALTER TABLE PRODUCTS ADD SOUNDSTRING TEXT");
				stmt.addBatch("ALTER TABLE PRODUCTS ADD SOUNDMOD INT");
				stmt.executeBatch();
				stmt.close();
			}
			//endregion
			//region Backwards compatability check for 1.11 and 1.12 to 1.13 (Detailed transactions update)
			try {
				stmt = c.createStatement();
				stmt.executeQuery("SELECT * FROM DEVTRANSACTIONS");
				stmt.executeQuery("SELECT * FROM trans");
				stmt.close();
			} 
			catch (SQLException e) {
				WindowAdmin.writeToConsole("Database: is not up to date, attempting to update.");
				createDEVTransactionTable();
				createTransactionAccumulationView();
				WindowAdmin.writeToConsole("Database: Update succeeded.");
			}
			//endregion
			//region Backwards compatability check for 1.13 -> 1.14
			try{
				stmt = c.createStatement();
				stmt.executeQuery("SELECT * FROM stats");
                stmt.executeQuery("SELECT sex FROM users");
				stmt.close();
			}
			catch(SQLException e){
				createStatsView();
                stmt = c.createStatement();
                stmt.executeUpdate("ALTER TABLE users ADD SEX CHAR(1)");
                stmt.close();
			}
			//endregion
		}
		catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			e.printStackTrace();
		}
	}

	public static void disconnectFromDB(){
		try {
			c.close();
			if(c.isClosed()){
				WindowAdmin.writeToConsole("Database: Disconnected from database");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void setDatabasePath(String s){
		if(!s.isEmpty()){
			prefs.setDatabasePath(s+".db");
		}
		else{
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Database files", "db");
			fc.setFileFilter(filter);
			int result = fc.showOpenDialog(fc);
			switch (result){
			case JFileChooser.APPROVE_OPTION:
				String path = fc.getSelectedFile().getAbsolutePath();
				prefs.setDatabasePath(path);
				break;
			case JFileChooser.CANCEL_OPTION:
				System.exit(0);
				break;
			case JFileChooser.ERROR_OPTION:
				System.exit(0);
				break;
				
			}
		}
	}

	//region Creates the data tables
	public static void createUserTable(){
		try {
			stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS USERS" +
					"(ID INT PRIMARY 	KEY     	NOT NULL, " +
					" NAME           	TEXT    	NOT NULL, " + 
					" SEX       		CHAR(1)	    		, " +
					" BARCODE        	KEY			NOT NULL, " + 
					" STUDY         	TEXT				, " +
					" TEAM				TEXT		NOT NULL, " +
					" RANK				TEXT		NOT NULL, " +
					" BEER				INT					, " +
					" CIDER				INT					, " +
					" SODA				INT					, " +
					" COCOA				INT					, " +
					" OTHER				INT					)"; 
			stmt.executeUpdate(sql);
			stmt.close();
			WindowAdmin.writeToConsole("Database: Table USERS created successfully");

		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			e.printStackTrace();
			System.exit(0);
		}
	}
	public static void createProductTable(){
		try {
			stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS PRODUCTS" +
					"(ID 				INT    		NOT NULL, " +
					" NAME           	TEXT    	NOT NULL, " + 
					" TYPE	         	TEXT		NOT NULL, " +
					" PRICE				FLOAT		NOT NULL, " +
					" BARCODE			LONG		NOT NULL, " +
					" TOTALSTOCK		INT			NOT NULL, " +
					" CURRENTSTOCK		INT			NOT NULL, " +
					" BOUGHT			INT					, " +
					" SOUND				BOOLEAN		NOT NULL, " +
					" SOUNDSTRING		TEXT				, " +
					" SOUNDMOD			INT					, " +
					" PRIMARY KEY (ID,BARCODE))"; 
			stmt.executeUpdate(sql);
			stmt.close();
			//			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			e.printStackTrace();
			System.exit(0);
		}
		WindowAdmin.writeToConsole("Database: Table PRODUCTS created successfully");
	}
	public static void createLogTable(){
		try {
			stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS LOG" +
					"(ID INT PRIMARY			KEY			NOT NULL, " +
					"TIME						TEXT		NOT NULL, " +
					"EVENT						TEXT		NOT NULL)";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			e.printStackTrace();
			System.exit(0);
		}
		WindowAdmin.writeToConsole("Database: Table LOG created successfully");
	}
	public static void createTransactionTable() {
		try {
			stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS TRANSACTIONS" +
					"(ID INT PRIMARY			KEY			NOT NULL, " +
					"TIME						TEXT		NOT NULL, " +
					"USER						TEXT		NOT NULL) ";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			e.printStackTrace();
			System.exit(0);
		}
		WindowAdmin.writeToConsole("Database: Table TRANSACTIONS created successfully");
	}
	//TODO In development, a new normalized Transaction Table
	public static void createDEVTransactionTable() {
		try {
			stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS DEVTRANSACTIONS" +
					"(ID 						INT 		NOT NULL, " +
					"TIME						TEXT		NOT NULL, " +
					"USERID						TEXT		NOT NULL, " + 
					"PRODUCT					LONG		NOT NULL, " +
					"AMOUNT						INT			NOT NULL, " +
					"PRIMARY KEY (ID,USERID,PRODUCT), " + 
					"FOREIGN KEY (PRODUCT) REFERENCES PRODUCTS(BARCODE))";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			e.printStackTrace();
			System.exit(0);
		}
		WindowAdmin.writeToConsole("Database: Table DEVTRANSACTIONS created successfully"); //TODO Change log message
	}
	public static void createTransactionAccumulationView(){
		try{
			stmt = c.createStatement();
			String sql = "CREATE VIEW IF NOT EXISTS trans AS " +
						"SELECT (users.id) AS userid, " +
						"users.name," +
						"users.rank," +
						"(devtransactions.id) AS transactionid, " + 
						"devtransactions.product, " +
						"sum(devtransactions.amount) AS amount, " +
						"products.price," + 
						"(sum(devtransactions.amount)*price) AS totalprice " +
						"FROM ((devtransactions JOIN users ON devtransactions.userid = users.id) " +
						"JOIN products ON devtransactions.product = products.barcode) GROUP BY userid, product";
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			e.printStackTrace();
			System.exit(0);
		}
	}
	public static void createStatsView() {
		try{
			stmt = c.createStatement();
			String sql = "CREATE VIEW IF NOT EXISTS stats AS SELECT team,  COUNT(*) AS teamsize, SUM(beer) AS beer, SUM(cider) AS cider, SUM(soda) AS soda, SUM(cocoa) AS cocoa,SUM(other) AS other," +
					"SUM(beer) + SUM(cider) + SUM(soda) + SUM(cocoa) + SUM(other) AS total, (SUM(beer) + SUM(cider) + SUM(soda) + SUM(cocoa) + SUM(other))/COUNT(*) AS split " +
					"FROM USERS " +
					"GROUP BY team " +
					"ORDER BY (total/teamsize) DESC";
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			e.printStackTrace();
			System.exit(0);
		}
	}
	//endregion

	//region Add and imports
	public static void importUsers(String path) {
		try {
			if(c.isClosed()){
				connectToDB();
			}
			stmt = c.createStatement();

			//IMPORT FROM CSV FILE START
			BufferedReader br = null;
			String line = "";
			String csvSplitChar = "";
			File file = new File (path);
			if (file != null){
				try {
//					br = new BufferedReader(new FileReader(file));
                    br  = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")); //TODO Fix charset
					if(br.readLine().contains(";")){
						csvSplitChar = ";";
					}
					else{
						csvSplitChar = ",";
					}
					br.close();
					br = new BufferedReader(new FileReader(file));
//                    System.out.println(br.readLine()); //Test line, enabling this skips the first line.
					int i = stmt.executeQuery("SELECT MAX(ID) FROM USERS").getInt(1)+1;
					//					br.readLine(); //Skip the first line
					while ((line = br.readLine()) != null) {
						line = line.replace("'", "''");
						String[] user = line.split(csvSplitChar);
						//System.out.println(""+user[0]+" "+user[1]+" "+user[2]+" "+user[3]+" "+user[4]+" "+user[5]);
                        try {
                            String sql = "INSERT INTO USERS (ID,NAME,BARCODE,STUDY,TEAM,RANK,SEX) " +
                                    "VALUES (" + i + ",'" + user[0] + "','" + user[1] + "','" + user[2] + "','" + user[3] + "','" + user[4].toUpperCase() + "','" + user[5] + "');";
                            stmt.executeUpdate(sql);
                            i++;
                        }
                        catch (ArrayIndexOutOfBoundsException e){
                            e.printStackTrace();
                            break;
                        }
					}
					WindowAdmin.writeToConsole("Database: "+i+" Users imported successfully");

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			stmt.close();
			c.commit();
		} catch ( Exception e ) {
			e.printStackTrace();
			System.exit(0);
		}

	}
	public static void addUser(User user) {
		try {
			if(c.isClosed()){
				connectToDB();
			}
			stmt = c.createStatement();

			//IMPORT FROM CSV FILE START
			try {
				int i = stmt.executeQuery("SELECT MAX(ID) FROM USERS").getInt(1)+1;
				String sql = "INSERT INTO USERS (ID,NAME,SEX,BARCODE,STUDY,TEAM,RANK) " +
						"VALUES ("+i+",'"+user.getName()+"','"+user.getSex()+"','"+user.getBarcode()+"','"+user.getStudy()+"','"+user.getTeam()+"','"+user.getRank().toUpperCase()+"');";
				stmt.executeUpdate(sql);

			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt.close();
			c.commit();
			//			c.close();
		} catch ( Exception e ) {
			e.printStackTrace();
			System.exit(0);
		}
		WindowAdmin.writeToConsole("Database: User added successfully");
	}
	public static void addProduct(Product product) {
		try {
			if(c.isClosed()){
				connectToDB();
			}
			stmt = c.createStatement();

			//IMPORT FROM CSV FILE START
			try {
				int i = stmt.executeQuery("SELECT MAX(ID) FROM PRODUCTS").getInt(1)+1;
				product.setID(i);
				String sql = "INSERT INTO PRODUCTS (ID,NAME,TYPE,PRICE,BARCODE,TOTALSTOCK,CURRENTSTOCK,BOUGHT,SOUND) " +
						"VALUES ("+i+",'"+product.getName()+"','"
						+product.getType()+"','"
						+product.getPrice()+"','"
						+product.getBarcode()+"','"
						+product.getTotalStock()+"','"
						+product.getTotalStock()+"','"
						+product.getBought()+"','"
						+product.sound()+"');";
				stmt.executeUpdate(sql);

			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt.close();
			c.commit();
			//			c.close();
		} catch ( Exception e ) {
			e.printStackTrace();
			System.exit(0);
		}
		WindowAdmin.writeToConsole("Database: Product "+product.getName().toUpperCase()+" was added successfully");
		transactionAddCol(product);
	}
	//endregion

	//region Edits, remove and clear
	public static void editUser(int selectedUser, String name, String sex, int barcode, String study, String team, String rank){
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String sql = "UPDATE USERS set NAME = '"+name+"',SEX = '"+sex+"',BARCODE = '"+barcode+"',STUDY = '"+study+"',TEAM = '"+team+"',RANK = '"+rank.toUpperCase()+"' where ID='"+selectedUser+"';"; // This is the update string for changeing username
			stmt.executeUpdate(sql);
			c.commit();			

			stmt.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			e.printStackTrace();
			System.exit(0);
		}
		WindowAdmin.writeToConsole("Database: Edit user  done successfully");
	}
	public static void editProduct(String selectedProduct, String name, String type, Float price, long barcode, int totalstock,int currentstock, int sound, String soundstring, int soundmod) {
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String sql = "UPDATE PRODUCTS set NAME = '"+name+"',TYPE = '"+type+"',PRICE = '"+price+"',BARCODE = '"+barcode+"',TOTALSTOCK ='"+totalstock+"',CURRENTSTOCK = '"+currentstock+"',sound = '"+sound+"',SOUNDSTRING = '"+soundstring+"',SOUNDMOD = '"+soundmod+"' where NAME='"+selectedProduct+"';"; // This is the update string for changeing username
			System.out.println(sql);
			stmt.executeUpdate(sql);
			c.commit();

			stmt.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			e.printStackTrace();
			System.exit(0);
		}
		if (selectedProduct.equals(name)){
			WindowAdmin.writeToConsole("Database: Edit product "+selectedProduct.toUpperCase()+" done successfully");
		}
		else {
			WindowAdmin.writeToConsole("Database: Edit product "+selectedProduct.toUpperCase()+" changed to "+name.toUpperCase()+" done successfully");
		}

	}

	public static void removeUser(int id) {
		Statement stmt = null;
		try{
			stmt = c.createStatement();
			String sql = "DELETE FROM USERS where ID = '"+id+"';";
			stmt.executeUpdate(sql);
			sql = "DELETE FROM TRANSACTIONS where USER = '"+id+"';";
			stmt.executeUpdate(sql);
			c.commit();
		}
		catch (Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			e.printStackTrace();
		}

	}

	public static void clearProducts(){
		try{
			stmt = c.createStatement();
			String sql = "DROP TABLE IF EXISTS PRODUCTS;";
			stmt.executeUpdate(sql);
			sql = "DROP TABLE IF EXISTS TRANSACTIONS;";
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			WindowAdmin.writeToConsole("Database: Table PRODUCTS have been cleared successfully");
			WindowAdmin.writeToConsole("Database: Table TRANSACTIONS have been removed successfully");
			createProductTable();
			createTransactionTable();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void clearUsers(){
		try{
			stmt = c.createStatement();
			String sql = "DELETE FROM USERS;";
			stmt.executeUpdate(sql);
			sql = "DELETE FROM TRANSACTIONS;";
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			WindowAdmin.writeToConsole("Database: Table USERS have been cleared successfully");
			WindowAdmin.writeToConsole("Database: Table TRANSACTIONS have been cleared successfully");
//			createUserTable();
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}
	//endregion

	//region Get data to GUI

	public static List<String> getRanks(){
		List<User> users = getUsers();
		List<String> ranks = new ArrayList<String>();
		for (User u : users){
			if (!ranks.contains(u.getRank())){
				ranks.add(u.getRank());
			}
		}
		return ranks;

	}
	public static List<String> getTeamNames(){
		ResultSet rs = null;
		List<String> teams = new ArrayList<String>();
		try {
			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT DISTINCT team FROM USERS;");
			while(rs.next()){
				teams.add(rs.getString("team"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return teams;
	}
	public static List<User> getUsers(){
		ResultSet rs = null;
		List<User> users = new ArrayList<User>();
		try {
			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT * FROM USERS;");
			while(rs.next()){
				User user = new User(rs.getInt("id"), rs.getString("name"),rs.getString("sex"), rs.getInt("barcode"), rs.getString("study"), rs.getString("team"), rs.getString("rank"), rs.getInt("beer"), rs.getInt("cider"), rs.getInt("soda"), rs.getInt("cocoa"), rs.getInt("other"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}
//	public static List<User> getUserByName(String name){
//		ResultSet rs = null;
//		List<User> users = new ArrayList<User>();
//		try {
//			stmt = c.createStatement();
//			rs = stmt.executeQuery("SELECT * FROM USERS WHERE name='"+name+"';");
//			while(rs.next()){
//				User user = new User(rs.getInt("id"), rs.getString("name"), rs.getInt("barcode"), rs.getString("study"), rs.getString("team"), rs.getString("rank"), rs.getInt("beer"), rs.getInt("cider"), rs.getInt("soda"), rs.getInt("cocoa"), rs.getInt("other"));
//				users.add(user);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return users;
//	}
	public static List<User> getUserByBarcode(long l){
		ResultSet rs = null;
		List<User> users = new ArrayList<User>();
		try {
			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT * FROM USERS WHERE barcode='"+l+"';");
			while(rs.next()){
				User user = new User(rs.getInt("id"), rs.getString("name"),rs.getString("sex"),rs.getInt("barcode"), rs.getString("study"), rs.getString("team"), rs.getString("rank"), rs.getInt("beer"), rs.getInt("cider"), rs.getInt("soda"), rs.getInt("cocoa"), rs.getInt("other"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}
	public static List<Product> getProducts(){
		//Create new entry in the transaction log for the current transatction
		List<Product> products = new ArrayList<Product>();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCTS");
			while (rs.next()){
				Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("soundstring"),rs.getString("type"), rs.getFloat("price"), rs.getLong("barcode"), rs.getInt("totalStock"), rs.getInt("currentStock"), rs.getInt("bought"), rs.getInt("soundmod"), rs.getInt("sound"));
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}
	public static List<Product> getProductByName(String name) {
		ResultSet rs = null;
		List<Product> products = new ArrayList<Product>();
		try {
			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT * FROM PRODUCTS WHERE name='"+name+"';");
			while(rs.next()){
				// ID,NAME,TYPE,PRICE,BARCODE,CURRENTSTOCK,TOTALSTOCK,BOUGHT
				Product product = new Product(rs.getInt("id"), rs.getString("name"),rs.getString("type"), rs.getString("soundstring"), rs.getFloat("price"), rs.getLong("barcode"), rs.getInt("totalStock"), rs.getInt("currentStock"), rs.getInt("bought"), rs.getInt("soundmod"), rs.getInt("sound"));
				products.add(product);
				System.out.println("directly from db: type = " + products.get(0).getType());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}
	public static List<Product> getProductByBarcode(long barcode){
		ResultSet rs = null;
		List<Product> products = new ArrayList<Product>();
		try {
			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT * FROM PRODUCTS WHERE barcode='"+barcode+"';");
			while(rs.next()){
				Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("soundstring"),rs.getString("type"), rs.getFloat("price"), rs.getLong("barcode"), rs.getInt("totalStock"), rs.getInt("currentStock"), rs.getInt("bought"), rs.getInt("soundmod"), rs.getInt("sound"));
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}
	public static ResultSet getTable(String SQLstmt){
		ResultSet rs = null;
		try {
			stmt = c.createStatement();
			rs = stmt.executeQuery( SQLstmt );
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	public static ResultSet getUserTransactions(User user){
		ResultSet rs = null;
		try {
			if(c.isClosed()){
				connectToDB();
			}
			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT DEVTRANSACTIONS.ID,TIME,USERS.NAME,USERID,PRODUCT,AMOUNT,PRODUCTS.NAME FROM ((DEVTRANSACTIONS JOIN PRODUCTS ON DEVTRANSACTIONS.PRODUCT=PRODUCTS.BARCODE) JOIN USERS ON DEVTRANSACTIONS.USERID=USERS.ID) WHERE USERID = "+user.getID()+";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	public static ResultSet getAllUserTransactions(){
		ResultSet rs = null;
		try {
			if(c.isClosed()){
				connectToDB();
			}
			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT DEVTRANSACTIONS.ID,TIME,USERS.NAME,USERID,PRODUCT,AMOUNT,PRODUCTS.NAME FROM ((DEVTRANSACTIONS JOIN PRODUCTS ON DEVTRANSACTIONS.PRODUCT=PRODUCTS.BARCODE) JOIN USERS ON DEVTRANSACTIONS.USERID=USERS.ID)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	public static ResultSet getStats(){
	    return getTable("SELECT * FROM stats");
    }
	public static PieDataset getSingleUserStats(User user){
		ResultSet rs = null;
		try {
			if(c.isClosed()){
				connectToDB();
			}
			stmt = c.createStatement();
			final DefaultPieDataset result = new DefaultPieDataset();
			int beer = 0,cider = 0,soda = 0,cocoa = 0,other = 0;
			rs = stmt.executeQuery("SELECT sum(amount), type FROM (devtransactions JOIN products ON product = barcode) WHERE USERID = '"+user.getID()+"' GROUP BY type");

			while(rs.next()){
				int amt = rs.getInt(1);
				String t = rs.getString(2);
				System.out.println("Dataset creation type: " +t+" amt: "+amt);
				if (t.equals("Beer"))
					beer = amt;
				else if (t.equals("Cider"))
					cider = amt;
				else if (t.equals("Cocoa"))
					cocoa = amt;
				else if (t.equals("Soda"))
					soda = amt;
				else if (t.equals("Other"))
					other = amt;

			}

			result.setValue("Beer: "+beer, beer);
			result.setValue("Cider: "+cider,cider);
			result.setValue("Cocoa: "+cocoa,cocoa);
			result.setValue("Soda: "+soda,soda);
			result.setValue("Other: "+other,(double) other);
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}
	//endregion

	//region Exports
	public static void exportUsers(String fileName){
		try
		{ 
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM USERS;" ); //Select ALL
			FileWriter writer = new FileWriter(fileName);
			//			rs.next(); // skip first line

			writer.append("id"+";");
			writer.append("Name"+";");
			//			writer.append("Studynumber"+";");
			writer.append("Barcode"+";");
			writer.append("Retning"+";");
			writer.append("Team"+";");
			writer.append("Rank"+";");
			writer.append("Beer"+";");
			writer.append("Cider"+";");
			writer.append("Soda"+";");
			writer.append("Cocoa");
			writer.append('\n');

			while ( rs.next() ) {
				int 	id	 		= rs.getInt("id");
				String 	name 		= rs.getString("name");
				//				String 	studynumber = rs.getString("studynumber");
				int		barcode		= rs.getInt("barcode");
				String 	study 		= rs.getString("retning");
				String	team		= rs.getString("team");
				String	rank		= rs.getString("rank");
				int		beer		= rs.getInt("beer");
				int		cider		= rs.getInt("cider");
				int		soda		= rs.getInt("soda");
				int		cocoa		= rs.getInt("cocoa");
				int		other		= rs.getInt("other");

				writer.append(id+";");
				writer.append(name+";");
				//				writer.append(studynumber+";");
				writer.append(barcode+";");
				writer.append(study+";");
				writer.append(team+";");
				writer.append(rank+";");
				writer.append(beer+";");
				writer.append(cider+";");
				writer.append(soda+";");
				writer.append(cocoa+";");
				writer.append(other+"");
				writer.append('\n');

			}
			writer.flush();
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		} 
	}
	public static void exportLog(String fileName){
		try{ 
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM USERS;" ); //Select ALL
			FileWriter writer = new FileWriter(fileName);
			//			rs.next(); // skip first line

			writer.append("id"+";");
			writer.append("Time"+";");
			writer.append("Event"+";");
			writer.append('\n');

			while ( rs.next() ) {
				int 	id	 		= rs.getInt("id");
				String 	time 		= rs.getString("time");
				String 	event		 = rs.getString("event");

				writer.append(id+";");
				writer.append(time+";");
				writer.append(event+";");
				writer.append('\n');

			}
			writer.flush();
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		} 
	}
	public static void exportTransactionLog(String fileName){
		try{ 
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM TRANSACTIONS;" ); //Select ALL
			List <Product> products = getProducts();
			FileWriter writer = new FileWriter(fileName);
			//			rs.next(); // skip first line

			//Write the titles on the first line
			writer.append("id"+";");
			writer.append("Time"+";");
			writer.append("User"+";");
			for(Product p : products){
				writer.append(p.getName());
				if (products.indexOf(p)<products.size())
					writer.append(";");
			}
			writer.append('\n');


			while ( rs.next() ) {
				int 	id	 		= rs.getInt("id");
				String 	time 		= rs.getString("time");
				String 	user		 = rs.getString("user");

				writer.append(id+";");
				writer.append(time+";");
				writer.append(user+";");
				for(Product p : products){
					writer.append(""+rs.getInt("I"+p.getID()));
					if (products.indexOf(p)<products.size())
						writer.append(";");
				}
				writer.append('\n');

			}
			writer.flush();
			writer.close();
			WindowAdmin.writeToConsole("Database: Successfully exported the transaction log");
		}
		catch(Exception e){
			e.printStackTrace();
			WindowAdmin.writeToConsole("Database: Failed to export the transaction log");
		} 
	}
	public static void exportBill(String fileName, String Delimiter,boolean AllShare, List<String> shares, double fee){
		try{ 
			System.out.println("In DB delimiter = "+Delimiter);
			DecimalFormat df = new DecimalFormat("#"+Delimiter+"##"); //Default delimiter should be dot " , " (Inverse logic "," = "." and visa versa)
			List <User> users = getUsers();	
			List <Product> products = getProducts();
			stmt = c.createStatement();
			FileWriter writer = new FileWriter(fileName);


			//Write the titles on the first line
			writer.append("User"+";");
			for(Product p : products){
				writer.append(p.getName()+";");
			}
			writer.append("Price;");
			writer.append("Waste;");
			writer.append("Total price;");
			writer.append('\n');

			//Calculate the waste
			writer.append("WASTE;");
			double totalWaste = 0;
			for (Product p : products){
				int waste = p.getTotalStock() - p.getBought() - p.getCurrentStock();
				writer.append(waste+";");
				totalWaste += waste * p.getPrice();
			}
			totalWaste = totalWaste + fee;
			writer.append(df.format(totalWaste)+"");
			writer.append('\n');

			//Calculate the waste portion paid by every user
			double sharedWaste = 0;

			//Calculate how many uses to share waste on
			if(!AllShare && shares.size()>=1){
				sharedWaste = getSharedWaste(shares, totalWaste);
			}
			//If AllShare is checked, calculate the shared waste for every user
			else{
				float usersSize = users.size();
				sharedWaste = totalWaste / usersSize;
				System.out.println("Calculated shared waste (shared waste "+AllShare+"): "+sharedWaste);
				System.out.println("Users: "+users.size());
				System.out.println("totalWaste: " + df.format(totalWaste));
				System.out.println("sharedWaste: " + df.format(sharedWaste));
				System.out.println("Recalculated: " + (df.format(totalWaste / usersSize)));
				System.out.println("Rounded: " + roundDecimal(sharedWaste));
			}
			System.out.println("Just before Calculate total purchase");
			//Calculate total purchase
			double totalPrice = 0;
			for(User u : users){
				totalPrice = 0;
				writer.append(u.getName()+";");
				
				//Calculate price using old DB
				for(Product p : products){
					ResultSet rs = stmt.executeQuery("SELECT * FROM TRANSACTIONS where USER = '"+u.getID()+"';");
					int counter = 0;
					while(rs.next()){
						counter += rs.getInt("I"+p.getID());
					}
					writer.append(counter+";");
					totalPrice += counter * p.getPrice();
				}
				writer.append(df.format(roundDecimal(totalPrice))+";");
				
				//TODO New DB Calculation test!
				//Calculate price using new DB
				ResultSet RS = stmt.executeQuery("SELECT SUM(totalprice) FROM trans WHERE userid = '" + u.getID() + "' GROUP BY userid");
				int tp = 0;
				while(RS.next()){
					tp = RS.getInt(1);
					System.out.println(""+tp);
				}

				//Apply waste to the users according to settings
				if(AllShare){ //Apply waste to everyone
					writer.append(df.format(roundDecimal(sharedWaste))+";");
					writer.append(df.format(roundDecimal(totalPrice+sharedWaste))+"");	

					//					writer.append("0;"); //Write 0
					//					writer.append(df.format(totalPrice)+""); //Write total price 
				}
				else{		
					if(shares.contains(u.getRank())){
						writer.append(df.format(roundDecimal(sharedWaste))+";");
						writer.append(df.format(roundDecimal(totalPrice+sharedWaste))+"");
					}
					else{
						writer.append("0;");
						writer.append(df.format(roundDecimal(totalPrice))+"");
					}
				}

				writer.append('\n');
			}

			//flush to the csv file
			writer.flush();
			writer.close();
			WindowAdmin.writeToConsole("Database: Successfully exported the transaction log");
		}
		catch(Exception e){
			e.printStackTrace();
			WindowAdmin.writeToConsole("Database: Failed to export the transaction log");
		} 
	}
	public static double getSharedWaste(List<String> ranks, double totalWaste)
			throws SQLException {
		double sharedWaste;
		stmt = c.createStatement();
		String sql = "SELECT count(ID) FROM USERS WHERE ";
		
		boolean first = true;
		for (String s:ranks){
			if(first){
				sql = sql + "RANK = '" + s + "'";
				first = false;
			}
			else{
				sql = sql + " OR RANK = '" + s +"'";					
			}
		}
		System.out.println("Generate bill SQL String: " + sql);
		ResultSet rs = stmt.executeQuery(sql);
		int devider = rs.getInt(1);
		stmt.close();
		System.out.println("shared waste calculated by: totWaste: " + totalWaste + " and devider: " + devider);
		sharedWaste = totalWaste / devider;
		return sharedWaste;
	}
	public static String getUserPrice(int id){
		List <Product> products = getProducts();
		DecimalFormat df = new DecimalFormat("#.##");
		float totalPrice = 0;
		try {
			stmt = c.createStatement();
			ResultSet rs = null;
			for(Product p : products){
				rs = stmt.executeQuery("SELECT * FROM TRANSACTIONS where USER = '"+id+"';");
				int counter = 0;
				while(rs.next()){
					counter += rs.getInt("I"+p.getID());
				}
				totalPrice += counter * p.getPrice();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ""+df.format(totalPrice);
	}
	public static ResultSet getBill() {
		return getTable("SELECT userid, name, rank ,sum(totalprice) AS price FROM trans GROUP BY userid");
	}
	//endregion

	//region Logs
	public static void writeLog(String time, String event){
		try {
			if(c.isClosed()){
				connectToDB();
			}
			event = event.replace("'","''");
			stmt = c.createStatement();
			int i = stmt.executeQuery("SELECT MAX(ID) FROM LOG").getInt(1)+1;
			String sql = "INSERT INTO LOG (ID,TIME,EVENT)" +
					"VALUES ("+i+",'"+time+"','"+event+"');";
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
		}
		catch ( Exception e ) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	public static void writeTransaction(User user, TypeCount typeCount, List<String> productCounter){
		long time = System.currentTimeMillis();
		try {
			if(c.isClosed()){
				connectToDB();
			}
			System.out.println("Databse - Time to connect to db: " + (System.currentTimeMillis() - time));
			//Update typeCount on user for user statistics
			stmt = c.createStatement();
			System.out.println("Database: User logged in is: "+user.getName());

			//This SQL Statement updates the product type statistics of the single user.
			System.out.println("Database: add Typecount the database USER");
			String sql = "UPDATE USERS set BEER = "+(user.getBeerCount()+typeCount.getBeerCount())+", CIDER = "+(user.getCiderCount()+typeCount.getCiderCount())+", SODA = "+(user.getSodaCount()+typeCount.getSodaCount())+", COCOA = "+(user.getCocoaCount()+typeCount.getCocoaCount())+", OTHER = "+(user.getOtherCount() + typeCount.getOtherCount())+" where ID = '"+user.getID()+"';";
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			System.out.println("Database: Typecount successfully put to database");
			System.out.println("Databse - Time to add typecount to user: " + (System.currentTimeMillis() - time));
			List<Product> products = getProducts();
			String allproducts = getCommaSeperatedProducIDs(products);
			System.out.println("Database: allProducts: "+allproducts);
			//SQL statement

			String sqlValues = "";
			for(String s : productCounter){
				sqlValues = sqlValues + s.substring(s.indexOf(",")+1,s.length());
				if (productCounter.size()-1>productCounter.indexOf(s)){
					sqlValues = sqlValues + ",";
				}
			}

			System.out.println("Database: sqlValues: "+sqlValues);
			System.out.println("Comma seperated strings created successfully");
			//sql = "INSERT  INTO TRANSACTIONS (ID,TIME,USER,'"+allproducts+"')" +
			//"VALUES ("+i+",'"+time+"','"+user.getID()+"','"+sqlValues+"";

			//Create a time-stamp
			Date dNow = new Date( );
			SimpleDateFormat date = new SimpleDateFormat ("EEEEEEEEE dd.MM.yyyy 'at' HH:mm:ss", Locale.ENGLISH);
			String timestamp = date.format(dNow);

			//Do the SQL
			int id = stmt.executeQuery("SELECT MAX(ID) FROM TRANSACTIONS").getInt(1)+1;
			sql = "INSERT INTO TRANSACTIONS (ID,TIME,USER,"+allproducts+") VALUES ("+id+",'"+timestamp+"','"+user.getID()+"',"+sqlValues+");";
			System.out.println("Database: "+sql);
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			System.out.println("Databse - Time to add the new transaction: " + (System.currentTimeMillis() - time));

			stockChange(products,productCounter);
			System.out.println("Databse - Time to change stock: " + (System.currentTimeMillis() - time));


		}
		catch (Exception e ) {
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("Databse - Time to complete the checkout: " + (System.currentTimeMillis() - time));
	}
	//TODO Experimenting with new transaction log
	public static void devWriteTransaction(User user,List<Product> product,List<Integer> amount ){
		try {
			if(c.isClosed()){
				connectToDB();
			}
			//Start a statement
			stmt = c.createStatement();
			
			//Create a timestamp
			Date dNow = new Date( );
			SimpleDateFormat date = new SimpleDateFormat ("EEEEEEEEE dd.MM.yyyy 'at' HH:mm:ss", Locale.ENGLISH);
			String timestamp = date.format(dNow);
			
			//Inserting the purchase one line at a time
			int devid = stmt.executeQuery("SELECT MAX(ID) FROM DEVTRANSACTIONS").getInt(1)+1;
			for(Product p : product){
				int index = product.indexOf(p);
				String sql = "INSERT INTO DEVTRANSACTIONS (ID,TIME,USERID,PRODUCT,AMOUNT) VALUES ("+devid+",'"+timestamp+"','"+user.getID()+"',"+p.getBarcode()+","+amount.get(index)+");";
				stmt.executeUpdate(sql);
			}
			stmt.close();
			c.commit();
		}
		catch (Exception e ) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	public static void stockChange(List<Product> products, List<String> productCounter){		
		//Product stock change
		ResultSet rs = null;
		long time = System.currentTimeMillis();
		try {
			stmtStack = c.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(String s : productCounter){
			try {
				String id = s.substring(0, s.indexOf(","));
				String amt = s.substring(s.indexOf(",")+1,s.length());

				if (Integer.parseInt(amt) > 0){
					//get the product info
					//Get current bought count
					String sql = "SELECT BOUGHT, CURRENTSTOCK FROM PRODUCTS WHERE ID = '"+id+"';";
					stmt = c.createStatement();
					rs = stmt.executeQuery(sql);
					int bought = rs.getInt("BOUGHT");
					int currentstock = rs.getInt("CURRENTSTOCK");
					bought = bought + Integer.parseInt(amt);
					currentstock = currentstock - Integer.parseInt(amt);
					stmt.close();

					System.out.println("Database - Stock change after select: " + (System.currentTimeMillis() - time));

					//Update with new data
					sql = "UPDATE PRODUCTS set BOUGHT = '"+bought+"', CURRENTSTOCK = '"+currentstock+"' where ID='"+id+"';";
					stmtStack.addBatch(sql);
					System.out.println("Database - Stock change after update: " + (System.currentTimeMillis() - time));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			stmtStack.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Database - Stock change takes: " + (System.currentTimeMillis()-time));
	}
	public static void transactionAddCol(Product product){
		try {
			if(c.isClosed()){
				connectToDB();
			}
			System.out.println("Database: Attempt to create column for the product: "+product.getID());
			stmt = c.createStatement();
			String sql = "ALTER TABLE TRANSACTIONS" +
					" ADD COLUMN I"+product.getID()+" INT;";
			System.out.println("SQL string: "+sql);
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
		}
		catch(Exception e){
			WindowAdmin.writeToConsole("Database: Failed to create product entry in the transaction log, please retry");
			System.out.println("Database transactionAddCol "+e);
		}
	}
	//endregion

	//region Tools
	public static void displayTable(String SQLstmt){
		try {
			if (c.isClosed()){
				connectToDB();
			}
			stmt = c.createStatement();
			//			ResultSet rs = stmt.executeQuery( "SELECT * FROM USERS;" ); //Select ALL
			ResultSet rs = stmt.executeQuery( SQLstmt );
			while ( rs.next() ) {
				int 	id	 		= rs.getInt("id");
				String 	name 		= rs.getString("name");
				String 	studynumber = rs.getString("studynumber");
				int		barcode		= rs.getInt("barcode");
				String 	study 		= rs.getString("study");
				String	team		= rs.getString("team");
				String	rank		= rs.getString("rank");
				int		beer		= rs.getInt("beer");
				int		cider		= rs.getInt("cider");
				int		soda		= rs.getInt("soda");
				int		cocoa		= rs.getInt("cocoa");

				System.out.println( "ID = " + id );
				System.out.println( "NAME = " + name );
				System.out.println( "STUDYNUMBER = " + studynumber );
				System.out.println( "BARCODE = " + barcode );
				System.out.println( "STUDY = " + study );
				System.out.println( "GROUP = " + team );
				System.out.println( "RANK = " + rank );
				System.out.println( "BEERS = " + beer );
				System.out.println( "CIDERS = " + cider );
				System.out.println( "SODA = " + soda );
				System.out.println( "COCOAS = " + cocoa );
				System.out.println();
			}
			rs.close();
			stmt.close();
			//			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("Display table done successfully");
	}
	public static String getCommaSeperatedProducIDs(List<Product> products){
		//Create a comma separated string containing all product ID's
		System.out.println("Start creating comma separated strings");
		String allproducts = "";
		for (Product p : products){
			allproducts = allproducts + "I"+p.getID();
			if (products.size()-1>products.indexOf(p)){
				//add to list
				allproducts = allproducts + ",";
			}
		}
		return allproducts;

	}
	public static List<String> getTeamstats(){
		int performanceGetTeams = 0;
		List<User> users = getUsers();
		List<String> teams = new ArrayList<String>();
		List<String> teamStats = new ArrayList<String>();
		for (User u : users){
			performanceGetTeams++;
			if (!teams.contains(u.getTeam())){
				teams.add(u.getTeam());
			}
		}
		int performanceCaltulateStats = 0;
		List<User> doneUsers = new ArrayList<User>(); //Optimization testing
		for (String t : teams){
			int beer = 0;
			int cider = 0;
			int soda = 0;
			int cocoa = 0;
			int other = 0;
			int total = 0; //Testing purpose
			for (User u : users){
				performanceCaltulateStats++;
				if (u.getTeam().equals(t)){
					beer += u.getBeerCount();
					cider += u.getCiderCount();
					soda += u.getSodaCount();
					cocoa += u.getCocoaCount();
					other += u.getOtherCount();
					doneUsers.add(u); //Optimization testing
				}	
			}
			for(User u : doneUsers){
				users.remove(u);
				performanceCaltulateStats++;
			}
			doneUsers.clear();
			total += beer+cider+soda+cocoa+other; //Testig purpose
			teamStats.add(t+","+beer+","+cider+","+soda+","+cocoa+","+other+","+total); //Remove total, it is for testing only
			//			System.out.println("Total: "+total);
		}	
		System.out.println("Performance of finding all teams in a total of "+ users.size()+" users = " +performanceGetTeams + "iterations");
		System.out.println("Performance of calculating the teams stats = " + performanceCaltulateStats + " iterations");

		//Calculation with a differnt approach (Optimization)
		//Loop through users, and for every user add the purchase amount to the team so you only loop through the user base once.
		return teamStats;
	}
	public static BigDecimal roundDecimal(double value){
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, RoundingMode.UP);
		return bd;
	}
	public static DefaultTableModel buildTableModel(ResultSet rs)
        throws SQLException {

    ResultSetMetaData metaData = rs.getMetaData();
    // names of columns
    Vector<String> columnNames = new Vector<String>();
    int columnCount = metaData.getColumnCount();
    for (int column = 1; column <= columnCount; column++) {
        columnNames.add(metaData.getColumnName(column));
    }

    // data of the table
    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
    while (rs.next()) {
        Vector<Object> vector = new Vector<Object>();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            vector.add(rs.getObject(columnIndex));
        }
        data.add(vector);
    }
    return new DefaultTableModel(data,columnNames);
	}
	public static DefaultTableModel getBillPreview(ResultSet rs, List<String>ranks, double fee) throws SQLException {
		List <Product> products = getProducts();
		
		double totalWaste = 0;
		for (Product p : products){
			int waste = p.getTotalStock() - p.getBought() - p.getCurrentStock();
			totalWaste += waste * p.getPrice();
		}
		BigDecimal sharedWaste = roundDecimal(getSharedWaste(ranks, totalWaste+fee));
		ResultSetMetaData metaData = rs.getMetaData();
		// names of columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
		}
		columnNames.add("waste");
		columnNames.add("totalprice");

		// data of the table
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			if(ranks.contains(rs.getObject(3))){
				vector.add(sharedWaste);
			}
			else{
				vector.add(0);
			}
			BigDecimal total = roundDecimal(Double.parseDouble(vector.get(3).toString()) + Double.parseDouble(vector.get(4).toString()));
			vector.add(total);
			data.add(vector);
		}
		return new DefaultTableModel(data,columnNames);
		
	}

	//endregion
}