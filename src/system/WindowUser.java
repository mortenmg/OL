package system;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.general.*;
import org.jfree.util.Rotation;

// Swing Program Template
@SuppressWarnings("serial")
public class WindowUser extends JFrame implements ActionListener {

	// Name-constants to define the various dimensions
	public static final int h = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static final int w = Toolkit.getDefaultToolkit().getScreenSize().width;

	// Definition of GUI Elements
	private JPanel panelLeft, panelMain, panelConsole, panelCenter;
	private JScrollPane scrollPaneShoppingBag;
	static JList<String> listShoppingBag;
	static JTextArea console;
	JLabel labelBanner;
	public static User user;
	DefaultListModel<String> listModelShoppingBag = new DefaultListModel<String>();
	static List<String> ShoppingBag = new ArrayList<String>();
//	PieDataset dataset = createSampleDataset();
	private PieDataset dataset = Database.getSingleUserStats(user);
	private JFreeChart chart = createChart(dataset);
	public int counter = 0;

	//Local variables
	private JPanel panelRight,panelGuide,panelUserInfo,panelGraphs;
	private JLabel labelName,labelStudynumber,labelWelcome,
	labelStudy,labelTeam,labelRank,labelName_1,
	//	labelStudyNumber_1,
	labelStudy_1,labelTeam_1,labelRank_1,labelGroupPicture;
	private static JTextField textFieldTemp;
	private JTextPane textAreaGuide;


	/** Constructor to setup the UI components */
	public WindowUser() {
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

		labelBanner = new JLabel("");
		labelBanner.setHorizontalTextPosition(SwingConstants.CENTER);
		labelBanner.setHorizontalAlignment(SwingConstants.CENTER);
		labelBanner.setIcon(prefs.banner);
		panelTop.add(labelBanner);

		panelMain = new JPanel();
		getContentPane().add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new GridLayout(1, 0, 0, 0));

		//Create the left panel
		panelLeft = new JPanel();
		panelLeft.setBorder(new TitledBorder(null, "Shopping Bag", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelLeft.setBackground(Color.WHITE);
		panelMain.add(panelLeft);
		GridBagLayout gbl_panelLeft = new GridBagLayout();
		gbl_panelLeft.columnWidths = new int[]{0, 0};
		gbl_panelLeft.rowHeights = new int[]{0, 0};
		gbl_panelLeft.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelLeft.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelLeft.setLayout(gbl_panelLeft);

		scrollPaneShoppingBag = new JScrollPane();
		GridBagConstraints gbc_scrollPaneShoppingBag = new GridBagConstraints();
		gbc_scrollPaneShoppingBag.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneShoppingBag.gridx = 0;
		gbc_scrollPaneShoppingBag.gridy = 0;
		panelLeft.add(scrollPaneShoppingBag, gbc_scrollPaneShoppingBag);

		//		updateShoppingBag();
		for (String product : ShoppingBag){
			listModelShoppingBag.addElement(product);
		}

		listShoppingBag = new JList<String>(listModelShoppingBag);
		listShoppingBag.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 30));
		listShoppingBag.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneShoppingBag.setViewportView(listShoppingBag);

		panelCenter = new JPanel();
		panelMain.add(panelCenter);
		panelCenter.setLayout(new GridLayout(0, 1, 0, 0));

		panelGuide = new JPanel();
		panelGuide.setBorder(new TitledBorder(null, "Guide", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelGuide.setBackground(Color.WHITE);
		panelCenter.add(panelGuide);
		panelGuide.setLayout(new GridLayout(1, 0, 0, 0));

		textAreaGuide = new JTextPane();
		StyledDocument doc = textAreaGuide.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		textAreaGuide.setEditable(false);
		textAreaGuide.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 30));
		textAreaGuide.setText("1. Scan your products to buy. \n2. Scan a multiplier to buy more. \n3. Scan a userbarcode to logout.");

		panelGuide.add(textAreaGuide);

		panelConsole = new JPanel();
		panelConsole.setBorder(new TitledBorder(null, "Console", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConsole.setBackground(Color.WHITE);
		panelCenter.add(panelConsole);
		panelConsole.setLayout(new GridLayout(0, 1, 0, 0));

		console = new JTextArea();
		console.setEditable(false);
		console.setForeground(new Color(0, 0, 0));
		console.setBackground(Color.WHITE);
		panelConsole.add(console);
		console.setFont(new Font("Arial", Font.PLAIN, 16));
		console.setBorder(null);

		panelRight = new JPanel();
		panelMain.add(panelRight);
		panelRight.setLayout(new GridLayout(0, 1, 0, 0));

		panelUserInfo = new JPanel();
		panelUserInfo.setBackground(Color.WHITE);
		panelUserInfo.setBorder(new TitledBorder(null, "User Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelRight.add(panelUserInfo);
		GridBagLayout gbl_panelUserInfo = new GridBagLayout();
		gbl_panelUserInfo.columnWidths = new int[]{0, 0, 0};
		gbl_panelUserInfo.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelUserInfo.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panelUserInfo.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panelUserInfo.setLayout(gbl_panelUserInfo);

		labelWelcome = new JLabel("Welcome " + user.getName());
		labelWelcome.setFont(new Font("Tahoma", Font.BOLD, 23));

		GridBagConstraints gbc_labelWelcome = new GridBagConstraints();
		gbc_labelWelcome.anchor = GridBagConstraints.NORTH;
		gbc_labelWelcome.gridwidth = 4;
		gbc_labelWelcome.insets = new Insets(0, 0, 5, 0);
		gbc_labelWelcome.gridx = 0;
		gbc_labelWelcome.gridy = 0;
		panelUserInfo.add(labelWelcome, gbc_labelWelcome);

		labelName = new JLabel("Name:");
		labelName.setFont(new Font("Tahoma", Font.PLAIN, 18));

		GridBagConstraints gbc_labelName = new GridBagConstraints();
		gbc_labelName.anchor = GridBagConstraints.NORTHWEST;
		gbc_labelName.insets = new Insets(0, 0, 5, 5);
		gbc_labelName.gridx = 0;
		gbc_labelName.gridy = 1;
		panelUserInfo.add(labelName, gbc_labelName);

		labelName_1 = new JLabel("" + user.getName());
		GridBagConstraints gbc_labelName_1 = new GridBagConstraints();
		gbc_labelName_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_labelName_1.insets = new Insets(0, 0, 5, 5);
		gbc_labelName_1.gridx = 1;
		gbc_labelName_1.gridy = 1;
		panelUserInfo.add(labelName_1, gbc_labelName_1);

		labelStudynumber = new JLabel("Studynumber:");
		labelStudynumber.setFont(new Font("Tahoma", Font.PLAIN, 18));

		GridBagConstraints gbc_labelStudynumber = new GridBagConstraints();
		gbc_labelStudynumber.anchor = GridBagConstraints.NORTHWEST;
		gbc_labelStudynumber.insets = new Insets(0, 0, 5, 5);
		gbc_labelStudynumber.gridx = 0;
		gbc_labelStudynumber.gridy = 2;
		panelUserInfo.add(labelStudynumber, gbc_labelStudynumber);

		//		labelStudyNumber_1 = new JLabel("" + user.getStudyNumber());
		//		GridBagConstraints gbc_labelStudyNumber_1 = new GridBagConstraints();
		//		gbc_labelStudyNumber_1.anchor = GridBagConstraints.WEST;
		//		gbc_labelStudyNumber_1.insets = new Insets(0, 0, 5, 5);
		//		gbc_labelStudyNumber_1.gridx = 1;
		//		gbc_labelStudyNumber_1.gridy = 2;
		//		panelUserInfo.add(labelStudyNumber_1, gbc_labelStudyNumber_1);

		labelStudy = new JLabel("Study:");
		labelStudy.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_labelStudy = new GridBagConstraints();
		gbc_labelStudy.anchor = GridBagConstraints.NORTHWEST;
		gbc_labelStudy.insets = new Insets(0, 0, 5, 5);
		gbc_labelStudy.gridx = 0;
		gbc_labelStudy.gridy = 3;
		panelUserInfo.add(labelStudy, gbc_labelStudy);

		labelStudy_1 = new JLabel("" + user.getStudy());
		GridBagConstraints gbc_labelStudyline_1 = new GridBagConstraints();
		gbc_labelStudyline_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_labelStudyline_1.insets = new Insets(0, 0, 5, 5);
		gbc_labelStudyline_1.gridx = 1;
		gbc_labelStudyline_1.gridy = 3;
		panelUserInfo.add(labelStudy_1, gbc_labelStudyline_1);

		labelTeam = new JLabel("Team:");
		labelTeam.setFont(new Font("Tahoma", Font.PLAIN, 18));

		GridBagConstraints gbc_labelTeam = new GridBagConstraints();
		gbc_labelTeam.anchor = GridBagConstraints.NORTHWEST;
		gbc_labelTeam.insets = new Insets(0, 0, 5, 5);
		gbc_labelTeam.gridx = 0;
		gbc_labelTeam.gridy = 4;
		panelUserInfo.add(labelTeam, gbc_labelTeam);

		labelTeam_1 = new JLabel("" + user.getTeam());
		GridBagConstraints gbc_labelGroup_1 = new GridBagConstraints();
		gbc_labelGroup_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_labelGroup_1.insets = new Insets(0, 0, 5, 5);
		gbc_labelGroup_1.gridx = 1;
		gbc_labelGroup_1.gridy = 4;
		panelUserInfo.add(labelTeam_1, gbc_labelGroup_1);

		labelRank = new JLabel("Rank:");
		labelRank.setFont(new Font("Tahoma", Font.PLAIN, 18));

		GridBagConstraints gbc_labelRank = new GridBagConstraints();
		gbc_labelRank.anchor = GridBagConstraints.NORTHWEST;
		gbc_labelRank.insets = new Insets(0, 0, 5, 5);
		gbc_labelRank.gridx = 0;
		gbc_labelRank.gridy = 5;
		panelUserInfo.add(labelRank, gbc_labelRank);

		labelRank_1 = new JLabel("" + user.getRank());
		GridBagConstraints gbc_labelRank_1 = new GridBagConstraints();
		gbc_labelRank_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_labelRank_1.insets = new Insets(0, 0, 5, 5);
		gbc_labelRank_1.gridx = 1;
		gbc_labelRank_1.gridy = 5;
		panelUserInfo.add(labelRank_1, gbc_labelRank_1);

		labelGroupPicture = new JLabel("");
		labelGroupPicture.setEnabled(false);
		labelGroupPicture.setBounds(new Rectangle(0, 0, 20, 20));
		labelGroupPicture.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		labelGroupPicture.setBackground(Color.RED);
		GridBagConstraints gbc_labelGroupPicture = new GridBagConstraints();
		gbc_labelGroupPicture.fill = GridBagConstraints.BOTH;
		gbc_labelGroupPicture.gridheight = 10;
		gbc_labelGroupPicture.gridwidth = 2;
		gbc_labelGroupPicture.gridx = 0;
		gbc_labelGroupPicture.gridy = 6;
//		panelUserInfo.add(labelGroupPicture, gbc_labelGroupPicture);

		panelGraphs = new JPanel();
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBackground(Color.WHITE);
		panelGraphs.setBackground(Color.WHITE);
		panelGraphs.setBorder(new TitledBorder(null, "Graphs", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelGraphs.setLayout(new GridLayout(0, 1, 0, 0));
		panelGraphs.add(chartPanel);
		panelRight.add(panelGraphs);

		textFieldTemp = new JTextField();
		textFieldTemp.setCaretColor(Color.WHITE);
		textFieldTemp.setForeground(Color.WHITE);
		textFieldTemp.setBorder(null);
		textFieldTemp.setBackground(Color.WHITE);
		getContentPane().add(textFieldTemp, BorderLayout.SOUTH);
		textFieldTemp.setColumns(10);
		textFieldTemp.addKeyListener(new KeyListener(){
			String inputPrevious = "";
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_ENTER){ //When scanner is done it automatically press enter
					String input = textFieldTemp.getText(); //Reads input from scanner
					textFieldTemp.setText("");
					try{
						//Scan the cancle option (404)
						if(input.length()==3 && input.equals("404")){
							dispose();
							System.out.println("WindowUser: Cancled");
						}

						//Scan multipliers
						else if (input.length()<3 && input.length()>0){
							System.out.println("Multiplier");
							System.out.println("Input = "+input);
							System.out.println("InputPrevious = "+inputPrevious);
							int multiplier = Integer.parseInt(input);
							if(!inputPrevious.isEmpty() && multiplier <= 30){
								System.out.println("Input is a valid multiplier: "+multiplier);
								List<Product> products = Database.getProductByBarcode(Long.parseLong(inputPrevious));
								if(Product.exists(products)){
									String name = products.get(0).getName();
									boolean exists = false;
									int i = 0;
									while(!exists){
										if(i<listModelShoppingBag.size()){
											if(listModelShoppingBag.get(i).toString().contains(name)){
												listModelShoppingBag.remove(i);
												listModelShoppingBag.add(i, multiplier + " x " + name);
												exists = true;
											}
										}
										i++;
									}
								}
								if (multiplier==30){SoundBoard.play("hahgay");}
							}
						}

						//Scan user
						//TODO Improve database so this is much simpler
						else if (input.length()<5){
							long time = System.currentTimeMillis();
							List<String> sounds = new ArrayList<String>();
							List<User> users = Database.getUserByBarcode(Long.parseLong(input));
							//When a user logs out, count what the user have purchased.
							if(User.exists(users)){
								System.out.println("WindowUser: User exists, logging out");
								if(!listModelShoppingBag.isEmpty()){
									System.out.println("WindowUser: User have items in shopping bag: Start analyse");
									//Analyse the shopping bag and send to database for storage.

									//Create list of existing products with a counter starting at 0
									List<String> commaSeperatedCounter = new ArrayList<String>();
									try {
										ResultSet rs = Database.getTable("SELECT id FROM PRODUCTS;");

										while (rs.next()){
											commaSeperatedCounter.add(""+rs.getInt("id")+",0");
											System.out.println(""+rs.getInt("id")+",0");
										}
									}
									catch (SQLException e1) {
										e1.printStackTrace();
									}
									//Lists to contain products and amounts to be saved in the database (New Transaction system)
									List<Product> Ps = new ArrayList<Product>();
									List<Integer> As = new ArrayList<Integer>();
									
									//Loop through the shopping-bag and create a comma separated list of strings in the format (productID,amount)
									for (int i = 0; i<listModelShoppingBag.size();i++){
										String item = listModelShoppingBag.get(i);
										System.out.println("WindowUser: Item: "+listModelShoppingBag.get(i)+" is being processed");
										int amt = 0;
										if (item.matches(".*[0-9].x")){
											System.out.println("WindowUser: Item has multiplier");
											@SuppressWarnings("resource")
											Scanner in = new Scanner(listModelShoppingBag.get(i)).useDelimiter("[^0-9]+");
											amt = in.nextInt();
											System.out.println("WindowUser: Item multiplier is: "+amt);
											in.close();
											item = item.substring(item.indexOf("x")+2, item.length());
											System.out.println("WindowUser: New input string "+item+" and amt = "+amt );
										}
										else{
											System.out.println("WindowUser: There's only one");
											amt = 1;
										}
										System.out.println("WindowUser: Begin constructing commaSeparatedCounter");
										System.out.println("WindowUser: Find product with name: "+item);
										List<Product> products = Database.getProductByName(item);
										Product product = products.get(0);
										for (String p : commaSeperatedCounter){
											if (p.equals(""+product.getID()+",0")){
												commaSeperatedCounter.set(commaSeperatedCounter.indexOf(p), p.substring(0,p.length()-1)+amt);
											}
										}
										System.out.println("WindowUser: successfully created commaSeparatedCounter");
										System.out.println("WindowUSer: commaSeparatedCounter test data:");
										for(String s : commaSeperatedCounter){
											System.out.println("WindowUSer: "+s);
										}
										
										//TODO New transaction method starts here
										Ps.add(product);
										As.add(amt);										
									}
									Database.devWriteTransaction(user, Ps, As);
									Ps.clear(); 
									As.clear();
									//New transaction method ends here
									
									
									//Simple typecount
									System.out.println("Starting simple typecount");
									int beer=0,cider=0,soda=0,cocoa = 0,other=0;
									for (int i = 0; i<listModelShoppingBag.size();i++){
										//Chop string
										String item = listModelShoppingBag.get(i);
										int amt = 0;
										if (item.matches("[0-9]* x .*")){
											System.out.println("WindowUser: simple typecount: Item has multiplier");
											@SuppressWarnings("resource")
											Scanner in = new Scanner(listModelShoppingBag.get(i)).useDelimiter("[^0-9]+");
											amt = in.nextInt();
											in.close();
											item = item.substring(item.indexOf("x")+2, item.length());
											System.out.println("New input string "+item);
										}
										else{
											System.out.println("WindowUser: simple typecount: There's only one");
											amt = 1;
										}
										System.out.println("WindowUser: After if/else");
										System.out.println("WindowUser: Name of item: "+item);
										List<Product> products = Database.getProductByName(item);
										Product product = products.get(0);

										System.out.println("product info: sound: "+ product.sound() + " mod: " + product.soundmod() + " string: " + product.soundstring());
										//Add sounds to the sound playlist
										if (product.sound() != 0){
											if (product.soundmod() <= amt){
												sounds.add(product.soundstring());
											}
										}


										//Analyse the product type
										if (product.getType().toLowerCase().equals("beer")){beer += amt;}
										if (product.getType().toLowerCase().equals("cider")){cider += amt;}
										if (product.getType().toLowerCase().equals("soda")){soda += amt;}
										if (product.getType().toLowerCase().equals("cocoa")){cocoa += amt;}
										if (product.getType().toLowerCase().equals("other")){other += amt;}
									}
									TypeCount typeCount = new TypeCount(beer,cider,soda,cocoa,other);
									long timeDb = System.currentTimeMillis();
									Database.writeTransaction(user, typeCount,commaSeperatedCounter);
									System.out.println("Time to save transaction in db: " + (System.currentTimeMillis() - timeDb));
									inputPrevious = "";

									//TODO Insert method to play sound for given conditions with the AwesomeButton server
									//Playing can be done by using the Soundboard class
									System.out.println("soundboard after this");
									long timeSound = System.currentTimeMillis();
									if(!sounds.isEmpty()){
										System.out.println("Play sound: " + sounds.get(0));
//										SoundBoard.play(sounds.get(0));
										SoundBoard sb = new SoundBoard(sounds.get(0));
										sb.start(sounds.get(0));
									}
									System.out.println("soundboard time: "+  (System.currentTimeMillis() - timeSound));


									//Dispose the user window (LOGOUT)
									WindowUserLogin.updateChart();
									time = System.currentTimeMillis() - time;
									System.out.println("Time to execute checkout: " + time);
									//									WindowUserLogin.main(null);
									dispose();
								}
								else{
									dispose();
								}
							}
						}

						//Scan product
						else{
							System.out.println("Search for product by barcode");
							inputPrevious = input;
							List<Product> products = Database.getProductByBarcode(Long.parseLong(input));
							if(Product.exists(products)){
								String name = products.get(0).getName();
								boolean exists = false;
								int i = 0;
								while(!exists){
									if(i<listModelShoppingBag.size()){
										if(listModelShoppingBag.get(i).toString().contains(name)){
											int amt = 0;									
											try{
												@SuppressWarnings("resource")
												Scanner in = new Scanner(listModelShoppingBag.get(i)).useDelimiter("[^0-9]+");
												amt = in.nextInt();
												in.close();
											}
											catch(Exception e2){
												amt = 1;
											}

											listModelShoppingBag.remove(i);
											listModelShoppingBag.add(i,amt + 1 + " x " + name);
											exists = true;
										}
									}
									else if(i>= listModelShoppingBag.size()){
										listModelShoppingBag.addElement(name);
//										SoundBoard.play("hagay");
										exists = true;
										System.out.println("Play sound");
									}	
									i++;
								}
								writeToConsole("You added 1 " + name + " to your shopping bag");
							}

						}
					}
					catch (Exception e1){

					}
				}
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Exit when close button clicked
		setTitle("....."); // "this" JFrame sets title
		setLocationRelativeTo(null);
		setVisible(true);   // show it
	}

	public void actionPerformed(ActionEvent e){

	}

	public static void writeToConsole(String str){
		//		Date dNow = new Date( );
		//		SimpleDateFormat date = new SimpleDateFormat ("EEEEEEEEE dd.MM.yyyy 'at' HH:mm:ss", Locale.ENGLISH);
		//		console.append(date.format(dNow) + ": " + str + "\n");
		console.append(str+"\n");
	}

//	public PieDataset createSampleDataset() {
//
//		final DefaultPieDataset result = new DefaultPieDataset();
//		result.setValue("Beer: "+user.getBeerCount(), new Double(user.getBeerCount()));
//		result.setValue("Cider: "+user.getCiderCount(), new Double(user.getCiderCount()));
//		result.setValue("Cocoa: "+user.getCocoaCount(), new Double(user.getCocoaCount()));
//		result.setValue("Soda: "+user.getSodaCount(), new Double(user.getSodaCount()));
//		result.setValue("Other: "+user.getOtherCount(), new Double(user.getOtherCount()));
//		return result;
//
//	}
	public JFreeChart createChart(final PieDataset dataset) {

		final JFreeChart chart = ChartFactory.createPieChart3D(
				"Amount of Beer !",  // chart title
				dataset,                // data
				true,                   // include legend
				true,
				false
				);

		final PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		//        plot.setForegroundAlpha(0.5f);					//see through 
		plot.setNoDataMessage("No data to display");
		plot.setBackgroundPaint(Color.WHITE);
		plot.setOutlineVisible(false);
		//		final Rotator rotator = new Rotator(plot);
		//        rotator.start();
		return chart;
	}
	//	class Rotator extends Timer implements ActionListener {
	//	    /** The plot. */
	//	    private PiePlot3D plot;
	//	    /** The angle. */
	//	    private int angle = 20;
	//
	//	    Rotator(final PiePlot3D plot) {
	//	        super(100, null);								//speed
	//	        this.plot = plot;
	//	        addActionListener(this);
	//	    }
	//	    public void actionPerformed(final ActionEvent event) {
	//	        this.plot.setStartAngle(this.angle);
	//	        this.angle = this.angle + 1;
	//	        if (this.angle == 360) {
	//	            this.angle = 0;
	//	        }
	//	    }
	//	}
	//	public static void updateShoppingBag(){
	//		ShoppingBag.clear();
	//		try {
	//			ResultSet rs = Database.getUserTable("SELECT name FROM USERS");
	//
	//			while ( rs.next() ) {
	//				ShoppingBag.add(rs.getString("name"));
	//			}
	//		} catch (SQLException e) {
	//			e.printStackTrace();
	//		}
	//	}

	/** The entry main() method */
	public static void main(User userLogin) {
		// Run GUI codes in the Event-Dispatching thread for thread safety
		user = userLogin;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WindowUser();  // Let the constructor do the job
				textFieldTemp.requestFocus();

				//		updateShoppingBag();
			}
		});
	}
}