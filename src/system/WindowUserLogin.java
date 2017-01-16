package system;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.*;
import org.jfree.data.category.*;
import org.jfree.chart.plot.*;

@SuppressWarnings("serial")
public class WindowUserLogin extends JFrame implements ActionListener {

	// Name-constants to define the various dimensions
	public static final int h = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static final int w = Toolkit.getDefaultToolkit().getScreenSize().width;
	private static JPanel panelContent;
	private JPanel panelMain;
	private static JPanel panelGraphs;
	private JLabel labelWelcome, labelScanYourBarcode, labelBarcode;
	public static JTextField textFieldTemp;
	public static CategoryDataset dataset = createDataset();
	public static JFreeChart chart = createChart(dataset);
	public static ChartPanel chartPanel;

	/** Constructor to setup the UI components */
	public WindowUserLogin() {
		//Generate top panel
		JPanel panelTop = new JPanel();
		panelTop.setBackground(Color.WHITE);
		getContentPane().add(panelTop, BorderLayout.NORTH);

		JLabel labelBanner = new JLabel();
		labelBanner.setIcon(prefs.banner);
		panelTop.add(labelBanner);

		panelMain = new JPanel();
		getContentPane().add(panelMain, BorderLayout.CENTER);
		GridBagLayout gbl_panelMain = new GridBagLayout();
		gbl_panelMain.columnWidths = new int[]{1920, 0};
		gbl_panelMain.rowHeights = new int[]{1056, 0};
		gbl_panelMain.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelMain.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelMain.setLayout(gbl_panelMain);

		//Create the user list panel
		panelContent = new JPanel();
		panelContent.setBorder(null);
		panelContent.setBackground(Color.WHITE);
		GridBagConstraints gbc_panelContent = new GridBagConstraints();
		gbc_panelContent.fill = GridBagConstraints.BOTH;
		gbc_panelContent.gridx = 0;
		gbc_panelContent.gridy = 0;
		panelMain.add(panelContent, gbc_panelContent);
		GridBagLayout gbl_panelContent = new GridBagLayout();
		gbl_panelContent.columnWidths = new int[]{0, 0};
		gbl_panelContent.rowHeights = new int[]{50, 49, 0, 0, 0};
		gbl_panelContent.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelContent.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelContent.setLayout(gbl_panelContent);

		labelWelcome = new JLabel();
		labelWelcome.setText(prefs.welcomeText);
		labelWelcome.setFont(new Font("Verdana", Font.BOLD, 20));
		GridBagConstraints gbc_lblWelcomeToRustur = new GridBagConstraints();
		gbc_lblWelcomeToRustur.insets = new Insets(0, 0, 5, 0);
		gbc_lblWelcomeToRustur.gridx = 0;
		gbc_lblWelcomeToRustur.gridy = 0;
		panelContent.add(labelWelcome, gbc_lblWelcomeToRustur);

		labelScanYourBarcode = new JLabel("Scan your barcode to login");
		labelScanYourBarcode.setFont(new Font("Verdana", Font.BOLD, 20));
		GridBagConstraints gbc_lblScanYourBarcode = new GridBagConstraints();
		gbc_lblScanYourBarcode.insets = new Insets(0, 0, 5, 0);
		gbc_lblScanYourBarcode.gridx = 0;
		gbc_lblScanYourBarcode.gridy = 1;
		panelContent.add(labelScanYourBarcode, gbc_lblScanYourBarcode);

		labelBarcode = new JLabel("");
		labelBarcode.setIcon(new ImageIcon(WindowUserLogin.class.getResource("/images/barcodeSmall.png")));
		labelBarcode.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				WindowAdminLogon.main(null);
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}
		});

		panelGraphs = new JPanel();
		panelGraphs.setBackground(Color.WHITE);
		panelGraphs.setLayout(new BoxLayout(panelGraphs, BoxLayout.X_AXIS));
		chartPanel = new ChartPanel(chart);
		chartPanel.setBackground(Color.WHITE);
		panelGraphs.add(chartPanel);
		chartPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		GridBagConstraints gbc_panelGraphs = new GridBagConstraints();
		gbc_panelGraphs.fill = GridBagConstraints.BOTH;
		gbc_panelGraphs.gridx = 0;
		gbc_panelGraphs.gridy = 3;
		panelContent.add(panelGraphs, gbc_panelGraphs);

		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 0;
		gbc_label.gridy = 2;
		panelContent.add(labelBarcode, gbc_label);
		textFieldTemp = new JTextField();
		textFieldTemp.setCaretColor(Color.WHITE);
		textFieldTemp.setForeground(Color.WHITE);
		textFieldTemp.setBorder(null);
		textFieldTemp.setBackground(Color.WHITE);
		getContentPane().add(textFieldTemp, BorderLayout.SOUTH);
		textFieldTemp.setColumns(10);
		textFieldTemp.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					if (textFieldTemp.getText().length()<5 && textFieldTemp.getText().length()>3){
						try{
							List<User> users = Database.getUserByBarcode(Integer.parseInt(textFieldTemp.getText()));
							if(User.exists(users)){
								WindowUser.main(users.get(0));
//								dispose();
							}
							textFieldTemp.setText("");
						}
						catch(Exception e1){
							e1.printStackTrace();
						}	
					}
					else {
						textFieldTemp.setText("");
					}
				}
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Exit when close button clicked
		setTitle("AdminWindow"); // "this" JFrame sets title
		setSize(w, h);  // or pack() the components
		setExtendedState(Frame.MAXIMIZED_BOTH);  //full screen
		setUndecorated(true);	// no window-top-bar
		setResizable(false);
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		setVisible(true);   // show it

		//disable keys  
		//		KeyboardFocusManager.getCurrentKeyboardFocusManager()  
		//		.addKeyEventDispatcher(new KeyEventDispatcher(){
		//			public boolean dispatchKeyEvent(KeyEvent e){  	
		//				return true;  
		//			}  
		//		});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e2){
				String[] options = {"Yes", "No"};
				int response = JOptionPane.showOptionDialog(getContentPane(), "You are trying to shut down the program. \nAre you sure you wish to continue?", "Shutdown Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, "");
				if (response == JOptionPane.YES_OPTION) {
					setDefaultCloseOperation(EXIT_ON_CLOSE);
				}
				if (response == JOptionPane.NO_OPTION) {
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				}
			}	
		});
	}
	
	public static void updateChart(){
		System.out.println("UpdateChart");
		chartPanel.removeAll();
		dataset = createDataset();
		chart = createChart(dataset);
		chartPanel.setChart(chart);
	}
	
	private static CategoryDataset createDataset() {
        DefaultCategoryDataset result = new DefaultCategoryDataset();

	    //region Bund og start forfra
        ResultSet rs = null;
        if (prefs.normalizeStats) {
            rs = Database.getTable("SELECT * FROM stats ORDER BY split DESC");
        } else {
            rs = Database.getTable("SELECT * FROM stats ORDER BY total DESC");
        }

        int i = 0;
        try{
            while(rs.next()){
                //If stats is NOT neutralized
                if(i < prefs.topBuyers){
                    if(!prefs.neutralStats) {
                        int teamsize = 1;
                        if (prefs.normalizeStats){teamsize = rs.getInt("teamsize");}
                        result.addValue(rs.getInt("beer")/teamsize,  "beer",     rs.getString("team"));
                        result.addValue(rs.getInt("cider")/teamsize, "cider",    rs.getString("team"));
                        result.addValue(rs.getInt("soda")/teamsize,  "soda",     rs.getString("team"));
                        result.addValue(rs.getInt("cocoa")/teamsize, "cocoa",    rs.getString("team"));
                        result.addValue(rs.getInt("other")/teamsize, "other",    rs.getString("team"));
                    }
                    else{
                        if (prefs.normalizeStats){result.addValue(rs.getInt("split"), "Units per member", rs.getString("team"));}
                        else{result.addValue(rs.getInt("total"), "Units per member", rs.getString("team"));}
                    }
                    i++;
                }
                else{
                    break;
                }
            }
        }
        catch(SQLException e){

        }

        //endregion

        /*
		List<String> teamStats = Database.getTeamstats();
		List<String> teamStatsSort = new ArrayList<String>();



		//Sorting
			for(int i = 0; i < prefs.topBuyers;i++){
				int max = 0;
				String remember ="";
				for(String x : teamStats){
					String[] str = x.split(",");
					if (Integer.parseInt(str[6]) > max){
						max = Integer.parseInt(str[6]);
						remember = x;
					}
				}
				if (!remember.equals("")){
					teamStatsSort.add(remember);
				}
//				System.out.println("add "+remember);
				teamStats.remove(remember);
			}
			
		//Sorting end
		
//		System.out.println(teamStats.get(0));
//		System.out.println(teamStatsSort.get(1)); //This tests if the sort is correct, but crashes the program if there is no data
		for (String s : teamStatsSort){
			String[] amt = s.split(",");
			System.out.println("in the loop "+s);
			//amt[0] is the name of the group
			//amt[6] is the total
			
			//Controls the max length displayed in the stats
			if (amt[0].length() > prefs.nameMaxLength){
				amt[0]= amt[0].substring(0,prefs.nameMaxLength);				
			}
			//If stats is NOT neutralized
			if(!prefs.neutralStats) {
				result.addValue(Integer.parseInt(amt[1]), "beer", amt[0]);
				result.addValue(Integer.parseInt(amt[2]), "Cider", amt[0]);
				result.addValue(Integer.parseInt(amt[3]), "Soda", amt[0]);
				result.addValue(Integer.parseInt(amt[4]), "Cocoa", amt[0]);
				result.addValue(Integer.parseInt(amt[5]), "Other", amt[0]);
			}

			//If stats is neutralized
			else{
				int res = 0;
				for(int i=1; i<=5; i++){
					res += Integer.parseInt(amt[i]);
				}
				result.addValue(res, "Units", amt[0]);
			}
		}
		*/
		return result;
	}

 	public static JFreeChart createChart(final CategoryDataset dataset) {
		final JFreeChart chart = ChartFactory.createStackedBarChart(
				"Team statistics", 	//Title
				"", 				//Horizontal description
				"Amount", 			//Vertical description
				dataset,
				PlotOrientation.VERTICAL,
				true,
				true,
				false
				);

		final CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setNoDataMessage("No data to display");
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setOutlineVisible(false);
		return chart;
	}

	public void actionPerformed(ActionEvent e) {
	}

	/** The entry main() method */
	public static void main(String[] args) {
		//Connect to DB
		//		Database.connectToDB();

		// Run GUI codes in the Event-Dispatching thread for thread safety
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WindowUserLogin();  // Let the constructor do the job
				textFieldTemp.requestFocus();
			}
		});
		WindowAdmin.writeToConsole("System have launched successfully");
	}
}