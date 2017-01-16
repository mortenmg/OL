package system;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;

// Swing Program Template
@SuppressWarnings("serial")
public class WindowAdvancedSettings extends JFrame implements ActionListener {

	// Import modules
	//	protected static Database Database;

	// Name-constants to define the various dimensions
	public static final int h = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static final int w = Toolkit.getDefaultToolkit().getScreenSize().width;
	JPanel panelButtonList, panelMain;

	//Local variables
	static List<String> users = new ArrayList<String>();
	private JButton btnSaveAndExit,btnClearUserlist,btnClearProductlist,btnRestorebanner,btnSavePassword;
	private JPanel panelButtons,panelAddPassword,panelBottom;
	private JPasswordField textfieldPassword;
	private JButton btnDeveloperBanner;
	private JButton btnClearUserPrefs;
	private JButton btnChangeDatabase;
	private JButton btnExportUsers;
	private JButton btnExportProducts;
	private JPanel soundboardSettings;
	private JTextField TextFieldIP;
	private JTextField textFieldPort;
	private JPanel statsSettings;
	private JTextField maxNameLength;
	private JTextField topBuyers;
	private JLabel lblNameLength;
	private JLabel lblTopBuyers;
	private JPanel panelWelcomeLabel;
	private JTextField textfieldWelcomeText;
	private JButton btnSaveWelcomeText;
    private JCheckBox cbNeutralStats;

	/** Constructor to setup the UI components */
	public WindowAdvancedSettings() {
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setAlwaysOnTop(true);

		panelMain = new JPanel();
		getContentPane().add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new GridLayout(1, 0, 0, 0));


		panelButtonList = new JPanel();
		panelButtonList.setBorder(new TitledBorder(null, "Advanced Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelButtonList.setBackground(Color.WHITE);
		panelMain.add(panelButtonList);
		GridBagLayout gbl_panelButtonList = new GridBagLayout();
		gbl_panelButtonList.columnWidths = new int[]{0, 0};
		gbl_panelButtonList.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 70, 0, 0, 0, 0};
		gbl_panelButtonList.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelButtonList.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		panelButtonList.setLayout(gbl_panelButtonList);

		btnClearUserlist = new JButton("Clear Userlist");
		btnClearUserlist.addActionListener(this);
		GridBagConstraints gbc_buttonClearUserlist = new GridBagConstraints();
		gbc_buttonClearUserlist.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonClearUserlist.insets = new Insets(0, 0, 5, 0);
		gbc_buttonClearUserlist.gridx = 0;
		gbc_buttonClearUserlist.gridy = 0;
		panelButtonList.add(btnClearUserlist, gbc_buttonClearUserlist);

		btnClearProductlist = new JButton("Clear Productlist");
		btnClearProductlist.addActionListener(this);
		GridBagConstraints gbc_buttonClearProductlist = new GridBagConstraints();
		gbc_buttonClearProductlist.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonClearProductlist.insets = new Insets(0, 0, 5, 0);
		gbc_buttonClearProductlist.gridx = 0;
		gbc_buttonClearProductlist.gridy = 1;
		panelButtonList.add(btnClearProductlist, gbc_buttonClearProductlist);

		btnRestorebanner = new JButton("Restore Default Banner");
		btnRestorebanner.addActionListener(this);
		GridBagConstraints gbc_buttonRestorebanner = new GridBagConstraints();
		gbc_buttonRestorebanner.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonRestorebanner.insets = new Insets(0, 0, 5, 0);
		gbc_buttonRestorebanner.gridx = 0;
		gbc_buttonRestorebanner.gridy = 2;
		panelButtonList.add(btnRestorebanner, gbc_buttonRestorebanner);
		
		btnDeveloperBanner = new JButton("Restore Developer Banner");
		btnDeveloperBanner.addActionListener(this);
		GridBagConstraints gbc_btnDeveloperBanner = new GridBagConstraints();
		gbc_btnDeveloperBanner.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDeveloperBanner.insets = new Insets(0, 0, 5, 0);
		gbc_btnDeveloperBanner.gridx = 0;
		gbc_btnDeveloperBanner.gridy = 3;
		panelButtonList.add(btnDeveloperBanner, gbc_btnDeveloperBanner);
		
		btnClearUserPrefs = new JButton("Clear user preferences");
		btnClearUserPrefs.addActionListener(this);
		GridBagConstraints gbc_btnClearUserPrefs = new GridBagConstraints();
		gbc_btnClearUserPrefs.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClearUserPrefs.insets = new Insets(0, 0, 5, 0);
		gbc_btnClearUserPrefs.gridx = 0;
		gbc_btnClearUserPrefs.gridy = 4;
		panelButtonList.add(btnClearUserPrefs, gbc_btnClearUserPrefs);
		
		btnChangeDatabase = new JButton("Change Database");
		btnChangeDatabase.addActionListener(this);
		GridBagConstraints gbc_btnChangeDatabase = new GridBagConstraints();
		gbc_btnChangeDatabase.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnChangeDatabase.insets = new Insets(0, 0, 5, 0);
		gbc_btnChangeDatabase.gridx = 0;
		gbc_btnChangeDatabase.gridy = 5;
		panelButtonList.add(btnChangeDatabase, gbc_btnChangeDatabase);
		
		btnExportUsers = new JButton("Export Users");
		btnExportUsers.addActionListener(this);
		GridBagConstraints gbc_btnExportUsers = new GridBagConstraints();
		gbc_btnExportUsers.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnExportUsers.insets = new Insets(0, 0, 5, 0);
		gbc_btnExportUsers.gridx = 0;
		gbc_btnExportUsers.gridy = 6;
		panelButtonList.add(btnExportUsers, gbc_btnExportUsers);
		btnExportUsers.setEnabled(false);
		
		btnExportProducts = new JButton("Export Products");
		btnExportProducts.addActionListener(this);
		GridBagConstraints gbc_btnExportProducts = new GridBagConstraints();
		gbc_btnExportProducts.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnExportProducts.insets = new Insets(0, 0, 5, 0);
		gbc_btnExportProducts.gridx = 0;
		gbc_btnExportProducts.gridy = 7;
		panelButtonList.add(btnExportProducts, gbc_btnExportProducts);
		btnExportProducts.setEnabled(false);

		panelAddPassword = new JPanel();
		panelAddPassword.setBackground(Color.WHITE);
		panelAddPassword.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Change Password", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelAddPassword = new GridBagConstraints();
		gbc_panelAddPassword.insets = new Insets(0, 0, 5, 0);
		gbc_panelAddPassword.anchor = GridBagConstraints.NORTH;
		gbc_panelAddPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelAddPassword.gridx = 0;
		gbc_panelAddPassword.gridy = 8;
		panelButtonList.add(panelAddPassword, gbc_panelAddPassword);
		GridBagLayout gbl_panelAddPassword = new GridBagLayout();
		gbl_panelAddPassword.columnWidths = new int[]{0, 0};
		gbl_panelAddPassword.rowHeights = new int[]{0, 0, 0};
		gbl_panelAddPassword.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelAddPassword.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelAddPassword.setLayout(gbl_panelAddPassword);

		textfieldPassword = new JPasswordField();
		GridBagConstraints gbc_textfieldPassword = new GridBagConstraints();
		gbc_textfieldPassword.insets = new Insets(0, 0, 5, 0);
		gbc_textfieldPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfieldPassword.gridx = 0;
		gbc_textfieldPassword.gridy = 0;
		panelAddPassword.add(textfieldPassword, gbc_textfieldPassword);
		textfieldPassword.setColumns(10);

		btnSavePassword = new JButton("Save");
		btnSavePassword.addActionListener(this);
		GridBagConstraints gbc_btnSavePassword = new GridBagConstraints();
		gbc_btnSavePassword.anchor = GridBagConstraints.EAST;
		gbc_btnSavePassword.gridx = 0;
		gbc_btnSavePassword.gridy = 1;
		panelAddPassword.add(btnSavePassword, gbc_btnSavePassword);
		
		panelWelcomeLabel = new JPanel();
		panelWelcomeLabel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Change welcome text", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelWelcomeLabel.setBackground(Color.WHITE);
		GridBagConstraints gbc_panelWelcomeLabel = new GridBagConstraints();
		gbc_panelWelcomeLabel.insets = new Insets(0, 0, 5, 0);
		gbc_panelWelcomeLabel.fill = GridBagConstraints.BOTH;
		gbc_panelWelcomeLabel.gridx = 0;
		gbc_panelWelcomeLabel.gridy = 9;
		panelButtonList.add(panelWelcomeLabel, gbc_panelWelcomeLabel);
		GridBagLayout gbl_panelWelcomeLabel = new GridBagLayout();
		gbl_panelWelcomeLabel.columnWidths = new int[]{0, 0};
		gbl_panelWelcomeLabel.rowHeights = new int[]{0, 0, 0};
		gbl_panelWelcomeLabel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelWelcomeLabel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelWelcomeLabel.setLayout(gbl_panelWelcomeLabel);
		
		textfieldWelcomeText = new JTextField();
		textfieldWelcomeText.setText(prefs.welcomeText);
		textfieldWelcomeText.setColumns(10);
		GridBagConstraints gbc_textfieldWelcomeText = new GridBagConstraints();
		gbc_textfieldWelcomeText.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfieldWelcomeText.insets = new Insets(0, 0, 5, 0);
		gbc_textfieldWelcomeText.gridx = 0;
		gbc_textfieldWelcomeText.gridy = 0;
		panelWelcomeLabel.add(textfieldWelcomeText, gbc_textfieldWelcomeText);
		
		btnSaveWelcomeText = new JButton("Save");
		btnSaveWelcomeText.addActionListener(this);
		GridBagConstraints gbc_btnSaveWelcomeText = new GridBagConstraints();
		gbc_btnSaveWelcomeText.anchor = GridBagConstraints.EAST;
		gbc_btnSaveWelcomeText.gridx = 0;
		gbc_btnSaveWelcomeText.gridy = 1;
		panelWelcomeLabel.add(btnSaveWelcomeText, gbc_btnSaveWelcomeText);
		
		soundboardSettings = new JPanel();
		soundboardSettings.setBackground(Color.WHITE);
		soundboardSettings.setBorder(new TitledBorder(null, "Soundboard Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_soundboardSettings = new GridBagConstraints();
		gbc_soundboardSettings.insets = new Insets(0, 0, 5, 0);
		gbc_soundboardSettings.fill = GridBagConstraints.BOTH;
		gbc_soundboardSettings.gridx = 0;
		gbc_soundboardSettings.gridy = 10;
		panelButtonList.add(soundboardSettings, gbc_soundboardSettings);
		soundboardSettings.setLayout(new GridLayout(1, 0, 0, 0));
		
		TextFieldIP = new JTextField();
		TextFieldIP.setEnabled(true);
		TextFieldIP.setAlignmentX(Component.LEFT_ALIGNMENT);
		TextFieldIP.setText(prefs.IP);
		soundboardSettings.add(TextFieldIP);
		TextFieldIP.setColumns(10);
		
		textFieldPort = new JTextField();
		textFieldPort.setEnabled(true);
		textFieldPort.setText(prefs.port);
		soundboardSettings.add(textFieldPort);
		textFieldPort.setColumns(10);
		
		statsSettings = new JPanel();
		statsSettings.setBackground(Color.WHITE);
		statsSettings.setBorder(new TitledBorder(null, "Stats settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_statsSettings = new GridBagConstraints();
		gbc_statsSettings.fill = GridBagConstraints.BOTH;
		gbc_statsSettings.gridx = 0;
		gbc_statsSettings.gridy = 11;
		panelButtonList.add(statsSettings, gbc_statsSettings);
		GridBagLayout gbl_statsSettings = new GridBagLayout();
		gbl_statsSettings.columnWidths = new int[]{95, 95, 0};
		gbl_statsSettings.rowHeights = new int[]{14, 0, 0};
		gbl_statsSettings.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_statsSettings.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		statsSettings.setLayout(gbl_statsSettings);
		
		lblNameLength = new JLabel("Name Length");
		GridBagConstraints gbc_lblNameLength = new GridBagConstraints();
		gbc_lblNameLength.insets = new Insets(0, 0, 5, 5);
		gbc_lblNameLength.gridx = 0;
		gbc_lblNameLength.gridy = 0;
		statsSettings.add(lblNameLength, gbc_lblNameLength);
		
		lblTopBuyers = new JLabel("Top Buyers");
		GridBagConstraints gbc_lblTopBuyers = new GridBagConstraints();
		gbc_lblTopBuyers.insets = new Insets(0, 0, 5, 0);
		gbc_lblTopBuyers.gridx = 1;
		gbc_lblTopBuyers.gridy = 0;
		statsSettings.add(lblTopBuyers, gbc_lblTopBuyers);

		maxNameLength = new JTextField();
		maxNameLength.setText(""+prefs.nameMaxLength);
		GridBagConstraints gbc_maxNameLength = new GridBagConstraints();
		gbc_maxNameLength.fill = GridBagConstraints.BOTH;
		gbc_maxNameLength.insets = new Insets(0, 0, 0, 5);
		gbc_maxNameLength.gridx = 0;
		gbc_maxNameLength.gridy = 1;
		statsSettings.add(maxNameLength, gbc_maxNameLength);
		maxNameLength.setColumns(10);
		
		topBuyers = new JTextField();
		topBuyers.setText(""+prefs.topBuyers);
		GridBagConstraints gbc_topBuyers = new GridBagConstraints();
		gbc_topBuyers.fill = GridBagConstraints.BOTH;
		gbc_topBuyers.gridx = 1;
		gbc_topBuyers.gridy = 1;
		statsSettings.add(topBuyers, gbc_topBuyers);
		topBuyers.setColumns(10);

        //todo Fix check button
        cbNeutralStats = new JCheckBox("Neutral stats");
        GridBagConstraints gbc_cbNeutralStats = new GridBagConstraints();
        gbc_cbNeutralStats.insets = new Insets(0, 0, 5, 0);
        gbc_cbNeutralStats.gridx = 0;
        gbc_cbNeutralStats.gridy = 2;
        cbNeutralStats.setBackground(Color.WHITE);
        statsSettings.add(cbNeutralStats, gbc_cbNeutralStats);
        cbNeutralStats.setSelected(prefs.neutralStats);

		//Generate top panel
		panelBottom = new JPanel();
		panelBottom.setBackground(Color.WHITE);
		getContentPane().add(panelBottom, BorderLayout.SOUTH);
		panelBottom.setLayout(new GridLayout(1, 0, 0, 0));

		panelButtons = new JPanel();
		panelButtons.setBackground(Color.WHITE);
		FlowLayout fl_panelButtons = (FlowLayout) panelButtons.getLayout();
		fl_panelButtons.setAlignment(FlowLayout.RIGHT);
		panelBottom.add(panelButtons);

		btnSaveAndExit = new JButton("Save & Exit");
		btnSaveAndExit.addActionListener(this);
		panelButtons.add(btnSaveAndExit);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Exit when close button clicked
		setTitle("Advanced Settings"); // "this" JFrame sets title
		setSize(231, 650);  // or pack() the components.
		setLocationRelativeTo(null);
		setVisible(true);   // show it
	}

	public void actionPerformed(ActionEvent e){
		if (e.getSource() == btnClearProductlist){	
			String[] options = {"Yes", "No"};
			int response = JOptionPane.showOptionDialog(getContentPane(), "You are about to clear the product list. Notice that this will also reset the transaction log. \n \nAre you sure you wish to continue?", "Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, "delete");
			if (response == JOptionPane.YES_OPTION) {
				Database.clearProducts(); //This option also removes all entries from the transaction log
				WindowAdmin.updateProductList();
				WindowAdmin.searchProduct("");
			}
		}

		if (e.getSource() == btnClearUserlist){
			String[] options = {"Yes", "No"};
			int response = JOptionPane.showOptionDialog(getContentPane(), "You are about to clear the user list. Notice that this will also clear the transaction log. \n \nAre you sure you wish to continue?", "Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, "delete");
			if (response == JOptionPane.YES_OPTION) {
				Database.clearUsers(); //This option also removes all entries from the transaction log
				WindowAdmin.updateUserList();
				WindowAdmin.searchUser("");
			}
		}
		
		if (e.getSource() == btnRestorebanner){
			prefs.setDefaultBanner(prefs.defaultBanner);
		}
		
		if (e.getSource() == btnDeveloperBanner){
			prefs.setDefaultBanner(prefs.devBanner);
		}
		
		if (e.getSource() == btnClearUserPrefs){
			String[] options = {"Yes", "No"};
			int response = JOptionPane.showOptionDialog(getContentPane(), "You are about to clear the user settings, this includes any changes to password, banner, statistisc and soundboard settings. \nIf you click yes, the program will clear the settings and shut down. \nAre you sure you wish to continue?", "Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, "delete");
			if (response == JOptionPane.YES_OPTION) {
				WindowAdmin.writeToConsole("Advanced Settings: User preferences cleared");
				prefs.clearSettings();
				System.exit(0);
			}
		}
		
		if(e.getSource() == btnChangeDatabase){
			String[] options = {"Yes", "No"};
			int response = JOptionPane.showOptionDialog(getContentPane(), "You are about to change database. \nIf you click yes, the program will shut down. \nPlease restart the program and follow the instructions on the screen \nAre you sure you wish to continue?", "Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, "delete");
			if (response == JOptionPane.YES_OPTION) {
				prefs.setFirstRun(true);
				System.exit(0);
			}
		}
		
		if(e.getSource() == btnExportUsers){
			Database.exportUsers("C:/users.csv");
		}
		
		if(e.getSource() == btnExportProducts){
		}
		
		if (e.getSource() == btnSavePassword){
			prefs.setAdminPassword(textfieldPassword.getPassword());
		}
		if (e.getSource() == btnSaveWelcomeText){
			prefs.setWelcomeLabel(textfieldWelcomeText.getText());
			System.out.println("newWelcomeLable = "+textfieldWelcomeText.getText());
		}

		if (e.getSource() == btnSaveAndExit){
			prefs.setSoundBoardIP(TextFieldIP.getText(), textFieldPort.getText());
			prefs.setMaxNameLength(Integer.parseInt(maxNameLength.getText()));
			prefs.setTopBuyers(Integer.parseInt(topBuyers.getText()));
			prefs.setNeutralStats(cbNeutralStats.isSelected()); //todo set to check button
			prefs.getPrefs();
			WindowAdmin.writeToConsole("Advanced settings was closed");
			dispose();
		}
	}

	/** The entry main() method */
	public static void main(String[] args) {
		// Run GUI codes in the Event-Dispatching thread for thread safety
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WindowAdvancedSettings();  // Let the constructor do the job
			}
		});
	}
}