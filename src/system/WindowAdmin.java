package system;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

// Swing Program Template
@SuppressWarnings("serial")
public class WindowAdmin extends JFrame implements ActionListener {

	// Import modules
	//	protected static Database Database;

	// Name-constants to define the various dimensions
	public static final int h = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static final int w = Toolkit.getDefaultToolkit().getScreenSize().width;

	// Definition of GUI Elements
	JButton btnImportUsers, btnImportProducts , btnEditBanner, btnTransactonHistory, btnGenerateBill, btnAdvancedSettings, btnEditUser, btnAddUser, btnEditProduct, btnAddProduct, btnExitProgram, btnReturn;
	JPanel panelUserList, panelProcuctList, panelMain, panelUserButtons, panelAdminConsole, panelAdmin, panelProductButtons;
	JTextField searchFieldUser, searchFieldProduct;
	static JTextArea console;
	JScrollPane scrollPaneUser, scrollPaneProduct,scrollPaneConsole;
	static JList<String> listUsers,listProducts;
	JLabel banner;
	ImageIcon imageIcon = (prefs.banner);
//	ImageIcon imageIcon = new ImageIcon(WindowAdmin.class.getResource("/images/banner2.png"));
//	ImageIcon imageIcon = new ImageIcon("/images/banner2.png");
//	ImageIcon imageIcon = new ImageIcon(prefs.banner);

	//Local variables
	static List<String> users = new ArrayList<String>();
	static List<String> products = new ArrayList<String>();

	/** Constructor to setup the UI components */
	public WindowAdmin() {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setUndecorated(true);
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setAlwaysOnTop(true);
		//Generate top panel
		JPanel panelTop = new JPanel();
		panelTop.setBackground(Color.WHITE);
		getContentPane().add(panelTop, BorderLayout.NORTH);
		panelTop.setLayout(new GridLayout(1, 0, 0, 0));

		banner = new JLabel(imageIcon);
//		{ 
//
//	        public void paintComponent (Graphics g) 
//	        { 
//	        	
//	            super.paintComponent (g); 
//	            g.drawImage (imageIcon.getImage(), 0, 0, getWidth (), getHeight (), null); 
//	        } 
//	    }; 
	    banner.setBounds(360,300,200,200);
		banner.setHorizontalTextPosition(SwingConstants.CENTER);
		banner.setHorizontalAlignment(SwingConstants.CENTER);
		banner.setMinimumSize(new Dimension(w,h/10));
//		banner.setPreferredSize(new Dimension(w,h/3));
		banner.setMaximumSize(new Dimension(w,h/3));
		panelTop.add(banner);
		panelMain = new JPanel();
		getContentPane().add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new GridLayout(1, 0, 0, 0));


		//Create the user list panel
		panelUserList = new JPanel();
		panelUserList.setBorder(new TitledBorder(null, "Users", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelUserList.setBackground(Color.WHITE);
		panelMain.add(panelUserList);
		GridBagLayout gbl_panelUserList = new GridBagLayout();
		gbl_panelUserList.columnWidths = new int[]{0, 0};
		gbl_panelUserList.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panelUserList.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelUserList.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelUserList.setLayout(gbl_panelUserList);

		searchFieldUser = new JTextField();
		searchFieldUser.setText("Type to search users");
		GridBagConstraints gbc_searchFieldUser = new GridBagConstraints();
		gbc_searchFieldUser.insets = new Insets(0, 0, 5, 0);
		gbc_searchFieldUser.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchFieldUser.gridx = 0;
		gbc_searchFieldUser.gridy = 0;
		panelUserList.add(searchFieldUser, gbc_searchFieldUser);
		searchFieldUser.setColumns(10);
		searchFieldUser.getDocument().addDocumentListener(new UserDocumentListener());
		searchFieldUser.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				searchFieldUser.selectAll();
			}
		});

		panelUserButtons = new JPanel();
		panelUserButtons.setBackground(Color.WHITE);
		GridBagConstraints gbc_panelUserButtons = new GridBagConstraints();
		gbc_panelUserButtons.insets = new Insets(0, 0, 5, 0);
		gbc_panelUserButtons.fill = GridBagConstraints.BOTH;
		gbc_panelUserButtons.gridx = 0;
		gbc_panelUserButtons.gridy = 1;
		panelUserList.add(panelUserButtons, gbc_panelUserButtons);
		panelUserButtons.setLayout(new GridLayout(0, 2, 0, 0));

		btnEditUser = new JButton("Edit User");
		btnEditUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listUsers.getSelectedIndex() != -1){
					WindowEditUser.main(listUsers.getSelectedValue());
				}
			}
		});
		panelUserButtons.add(btnEditUser);

		btnAddUser = new JButton("Add User");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowAddUser.main(null);
			}
		});
		panelUserButtons.add(btnAddUser);

		scrollPaneUser = new JScrollPane();
		GridBagConstraints gbc_scrollPaneUser = new GridBagConstraints();
		gbc_scrollPaneUser.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneUser.gridx = 0;
		gbc_scrollPaneUser.gridy = 2;
		panelUserList.add(scrollPaneUser, gbc_scrollPaneUser);
		DefaultListModel<String> listModelUser = new DefaultListModel<String>();
		updateUserList();
		for (String user : users){
			listModelUser.addElement(user);
		}
		listUsers = new JList<String>(listModelUser);
		listUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneUser.setViewportView(listUsers);


		//Create the product list panel
		panelProcuctList = new JPanel();
		panelProcuctList.setBorder(new TitledBorder(null, "Products", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelProcuctList.setBackground(Color.WHITE);
		panelMain.add(panelProcuctList);
		GridBagLayout gbl_panelProcuctList = new GridBagLayout();
		gbl_panelProcuctList.columnWidths = new int[]{0, 0};
		gbl_panelProcuctList.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panelProcuctList.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelProcuctList.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelProcuctList.setLayout(gbl_panelProcuctList);

		searchFieldProduct = new JTextField();
		searchFieldProduct.setText("Type to search products");
		searchFieldProduct.setToolTipText("");
		searchFieldProduct.setColumns(10);
		GridBagConstraints gbc_searchFieldProduct = new GridBagConstraints();
		gbc_searchFieldProduct.insets = new Insets(0, 0, 5, 0);
		gbc_searchFieldProduct.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchFieldProduct.gridx = 0;
		gbc_searchFieldProduct.gridy = 0;
		panelProcuctList.add(searchFieldProduct, gbc_searchFieldProduct);
		searchFieldProduct.setColumns(10);
		searchFieldProduct.getDocument().addDocumentListener(new ProductDocumentListener());
		searchFieldProduct.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				searchFieldProduct.selectAll();
			}
		});

		panelProductButtons = new JPanel();
		panelProductButtons.setBackground(Color.WHITE);
		GridBagConstraints gbc_panelProductButtons = new GridBagConstraints();
		gbc_panelProductButtons.insets = new Insets(0, 0, 5, 0);
		gbc_panelProductButtons.fill = GridBagConstraints.BOTH;
		gbc_panelProductButtons.gridx = 0;
		gbc_panelProductButtons.gridy = 1;
		panelProcuctList.add(panelProductButtons, gbc_panelProductButtons);
		panelProductButtons.setLayout(new GridLayout(0, 2, 0, 0));

		btnEditProduct = new JButton("Edit Product");
		btnEditProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listProducts.getSelectedIndex() != -1){
					WindowEditProduct.main(listProducts.getSelectedValue());
				}
			}
		});
		panelProductButtons.add(btnEditProduct);

		btnAddProduct = new JButton("Add Product");
		btnAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowAddProduct.main(null);
			}
		});

		panelProductButtons.add(btnAddProduct);

		scrollPaneProduct = new JScrollPane();
		GridBagConstraints gbc_scrollPaneProduct = new GridBagConstraints();
		gbc_scrollPaneProduct.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneProduct.gridx = 0;
		gbc_scrollPaneProduct.gridy = 2;
		panelProcuctList.add(scrollPaneProduct, gbc_scrollPaneProduct);
		DefaultListModel<String> listModelProduct = new DefaultListModel<String>();
		updateProductList();
		for (String product : products){
			listModelProduct.addElement(product);
		}

		listProducts = new JList<String>(listModelProduct);
		listProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneProduct.setViewportView(listProducts);

		panelAdmin = new JPanel();
		panelMain.add(panelAdmin);
		panelAdmin.setLayout(new GridLayout(0, 1, 0, 0));

		panelAdminConsole = new JPanel();
		panelAdminConsole.setBorder(new TitledBorder(null, "Console", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelAdminConsole.setBackground(Color.WHITE);
		panelAdmin.add(panelAdminConsole);
		panelAdminConsole.setLayout(new GridLayout(0, 1, 0, 0));
		
		scrollPaneConsole = new JScrollPane();
		panelAdminConsole.add(scrollPaneConsole);
		
		console = new JTextArea();
		console.setEditable(false);
		scrollPaneConsole.setViewportView(console);
		updateConsole();

		//Generate (admin button) panel
		JPanel panelAdminButtons = new JPanel();
		panelAdmin.add(panelAdminButtons);
		panelAdminButtons.setBackground(Color.WHITE);
		panelAdminButtons.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Admin buttons", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelAdminButtons.setLayout(new GridLayout(0, 2, 0, 0));

		// Setup JButtons and add them to the panel
		btnImportUsers = new JButton("Import Users");
		btnImportUsers.setBackground(Color.WHITE);
		btnImportUsers.addActionListener(this);
		panelAdminButtons.add(btnImportUsers);

		btnEditBanner = new JButton("Edit Banner");
		btnEditBanner.setBackground(Color.WHITE);
		btnEditBanner.addActionListener(this);
		panelAdminButtons.add(btnEditBanner);

		btnImportProducts = new JButton("Import Products");
		btnImportProducts.setBackground(Color.WHITE);
		btnImportProducts.addActionListener(this);
		panelAdminButtons.add(btnImportProducts);
		btnImportProducts.setEnabled(false);

		btnTransactonHistory = new JButton("Transaction History");
		btnTransactonHistory.setBackground(Color.WHITE);
		btnTransactonHistory.addActionListener(this);

		btnGenerateBill = new JButton("Generate Bill");
		btnGenerateBill.setBackground(Color.WHITE);
		btnGenerateBill.addActionListener(this);
		panelAdminButtons.add(btnGenerateBill);
		panelAdminButtons.add(btnTransactonHistory);

		btnAdvancedSettings = new JButton("Advanced Settings");
		btnAdvancedSettings.setBackground(Color.WHITE);
		btnAdvancedSettings.addActionListener(this);
		panelAdminButtons.add(btnAdvancedSettings);

		btnExitProgram = new JButton("Exit Program");
		btnExitProgram.setBackground(Color.WHITE);
		btnExitProgram.addActionListener(this);

		btnReturn = new JButton("Return");
		btnReturn.setBackground(Color.WHITE);
		btnReturn.addActionListener(this);
		panelAdminButtons.add(btnReturn);
		panelAdminButtons.add(btnExitProgram);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Exit when close button clicked
		setTitle("AdminWindow"); // "this" JFrame sets title
		setSize(960, 617);  // or pack() the components 960 x 617
		setLocationRelativeTo(null);
		setVisible(true);   // show it
	}
	


	public void actionPerformed(ActionEvent e){
		if(e.getSource() == btnImportUsers){
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV File", "CSV");
			fc.setFileFilter(filter);
			int result = fc.showOpenDialog(this);
			switch (result) {
			case JFileChooser.APPROVE_OPTION:
				String path = fc.getSelectedFile().getAbsolutePath();
				System.out.println("Path: " + path);
				Database.importUsers(path);
				writeToConsole("CSV was imported");
				break;
			case JFileChooser.CANCEL_OPTION:
				writeToConsole("Import users was canceled");
				break;
			case JFileChooser.ERROR_OPTION:
				writeToConsole("Error during import CSV");
				break;
			}
			updateUserList();
			searchUser("");
		}
		if(e.getSource() == btnImportProducts){
			writeToConsole("MISSING_1");
		}
		if(e.getSource() == btnTransactonHistory){
			@SuppressWarnings("unused")
			JFrame frame = new transactionWindow().TransactionWindow(null);
		}
		if(e.getSource() == btnGenerateBill){
			WindowBillSettings.main();
		}
		
		if(e.getSource() == btnAdvancedSettings){
			String[] options = {"Yes", "No"};
			int response = JOptionPane.showOptionDialog(getContentPane(), "You are about to enter the advanced settings, Changes here should be made with caution. \nAre you sure you wish to continue?", "Advanced Settings Warning", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, "delete");
			if (response == JOptionPane.YES_OPTION) {
				writeToConsole("Advanced settings was opened");
				WindowAdvancedSettings.main(options);
			}
		}

		if(e.getSource() == btnEditBanner){
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg and png files", "png","jpg");
			fc.setFileFilter(filter);
			int result = fc.showOpenDialog(this);
			switch (result) {
			case JFileChooser.APPROVE_OPTION:
				writeToConsole("New banner was saved");
				String path = fc.getSelectedFile().getAbsolutePath();
				System.out.println("Banner path: "+path);
				BufferedImage img = null;
				try {
					img = ImageIO.read(new File(path));
				} catch (IOException e1) {
				}
				banner.setIcon(new ImageIcon(img));
				prefs.setBanner(path);

				break;
			case JFileChooser.CANCEL_OPTION:
				writeToConsole("Edit banner was canceled");
				break;
			case JFileChooser.ERROR_OPTION:
				writeToConsole("There was an error during edit banner");
				break;
			}

		}
		if(e.getSource() == btnReturn){
			writeToConsole("Admin was returned to login");
			this.dispose();
		}
		//		if(e.getSource() == btnDefaultBanner){
		//			banner.setIcon(new ImageIcon(AdminTestPage.class.getResource("/images/banner.png")));
		//		}
		if(e.getSource() == btnExitProgram){
			writeToConsole("System exit");
			System.exit(0);
		}
	}
	
	public static void writeToConsole(String str){
		Date dNow = new Date( );
	    SimpleDateFormat date = new SimpleDateFormat ("EEEEEEEEE dd.MM.yyyy 'at' HH:mm:ss: ", Locale.ENGLISH);
	    Database.writeLog(date.format(dNow), str);
	    if (console != null){	    	
	    	updateConsole();
	    }
		
	}
	private static void updateConsole() {
		try {
			console.setText("");
			ResultSet rs = Database.getTable("SELECT * FROM LOG");
			while (rs.next()){
				console.append(rs.getString("time")+rs.getString("event") +"\n");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	public static void updateUserList(){
		//    	Database.connectToDB(); //This like is not nessesary in the final program since the connection should already be made
		users.clear();
		try {
			ResultSet rs = Database.getTable("SELECT barcode, name FROM USERS ORDER BY barcode ASC");

			while ( rs.next() ) {
				users.add(rs.getString("barcode")+ " - " +rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateProductList() {
		products.clear();
		try {
			ResultSet rs = Database.getTable("SELECT name FROM PRODUCTS;");

			while (rs.next()){
				products.add(rs.getString("name"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}


	}
	
	//FIXME (Critical) Edit user fetch by name, should fetch by ID.
	public static void searchUser(String searchString) {
		DefaultListModel<String> listModelUser = (DefaultListModel<String>)listUsers.getModel();
		// Case�sensitive.
		listModelUser.clear();
		for (String user : users){
			if (user.toLowerCase().contains(searchString.toLowerCase())){
				listModelUser.addElement(user);
			}	
		}
	}
	//FIXME Get products by id instead of name
	public static void searchProduct(String searchString) {
		DefaultListModel<String> listModelProduct = (DefaultListModel<String>)listProducts.getModel();
		// Case�sensitive.
		listModelProduct.clear();
		for (String product : products){
			if (product.toLowerCase().contains(searchString.toLowerCase())){
				listModelProduct.addElement(product);
			}	
		}
	}
	//Realtime search listener for the user list
	class UserDocumentListener implements DocumentListener {

		public void insertUpdate(DocumentEvent e) {
			updateLog(e);
		}
		public void removeUpdate(DocumentEvent e) {
			updateLog(e);
		}
		public void changedUpdate(DocumentEvent e) {
			//Plain text components don't fire these events.
		}
		public void updateLog(DocumentEvent e) {
			searchUser(searchFieldUser.getText());
		}
	}
	//Realtime search listener for the product list
	class ProductDocumentListener implements DocumentListener {

		public void insertUpdate(DocumentEvent e) {
			updateLog(e);
		}
		public void removeUpdate(DocumentEvent e) {
			updateLog(e);
		}
		public void changedUpdate(DocumentEvent e) {
			//Plain text components don't fire these events.
		}
		public void updateLog(DocumentEvent e) {
			searchProduct(searchFieldProduct.getText());
		}
	}

	/** The entry main() method */
	public static void run () {
		//Connect to DB for test purpess
//		Database.connectToDB();
		// Run GUI codes in the Event-Dispatching thread for thread safety
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WindowAdmin();  // Let the constructor do the job
			}
		});
	}
}