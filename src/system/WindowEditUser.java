package system;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.general.*;
import org.jfree.util.Rotation;

// Swing Program Template
@SuppressWarnings("serial")
public class WindowEditUser extends JFrame implements ActionListener {

	// Name-constants to define the various dimensions
	public static final int h = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static final int w = Toolkit.getDefaultToolkit().getScreenSize().width;
	JPanel panelUserInfo, panelMain, panelGraphs;

	//Local variables
	private JButton buttonCancel,buttonSave;
	private JTextField textFieldName,textFieldSex,textFieldStudy,textFieldBarcode,textFieldTeam;
	private JPanel panelButtons,panel;
	private JLabel labelName, labelSex,labelBarcode,labelStudyline,labelTeam,labelRank;
	@SuppressWarnings("unused")
	private static String name, sex, barcode, study, team, rank, selUser;
	private static int id;
	private static int selectedUser;
	private Component rigidAreaLeft,rigidAreaTop,rigidAreaBottom,rigidAreaRight;
	private JComboBox<String> comboBoxRank;
	private JCheckBox checkBoxDelete;
	private boolean delete = false;
	private static User user;
	
	PieDataset dataset = Database.getSingleUserStats(user);
	JFreeChart chart = createChart(dataset);
	private JButton btnTransactionDetails;

	/** Constructor to setup the UI components */
	public WindowEditUser() {

		setExtendedState(Frame.MAXIMIZED_BOTH);
		setUndecorated(true);
		getContentPane().setBackground(new Color (240,240,240,150));
		setBackground(new Color (240,240,240,150));
		setAlwaysOnTop(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Exit when close button clicked
		setTitle("Edit User"); // "this" JFrame sets title
		setLocationRelativeTo(null);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e2){
			}	
		});
		getContentPane().setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setOpaque(false);
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		//Generate top panel
		JPanel panelBottom = new JPanel();
		panel.add(panelBottom, BorderLayout.SOUTH);
		panelBottom.setBackground(Color.WHITE);
		panelBottom.setLayout(new GridLayout(1, 0, 0, 0));

		checkBoxDelete = new JCheckBox("Delete");
		checkBoxDelete.setBackground(Color.WHITE);
		checkBoxDelete.addActionListener(this);
		panelBottom.add(checkBoxDelete);

		panelButtons = new JPanel();
		panelButtons.setBackground(Color.WHITE);
		FlowLayout fl_panelButtons = (FlowLayout) panelButtons.getLayout();
		fl_panelButtons.setAlignment(FlowLayout.RIGHT);
		panelBottom.add(panelButtons);

		buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(this);
		
		btnTransactionDetails = new JButton("Details");
		panelButtons.add(btnTransactionDetails);
		btnTransactionDetails.setBackground(Color.WHITE);
		panelButtons.add(buttonCancel);
		btnTransactionDetails.addActionListener(this);

		buttonSave = new JButton("Save");
		buttonSave.addActionListener(this);
		panelButtons.add(buttonSave);

		panelMain = new JPanel();
		panel.add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new GridLayout(1, 0, 0, 0));


		//Create the user list panel
		panelUserInfo = new JPanel();
		panelUserInfo.setBorder(new TitledBorder(null, "Edit User", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelUserInfo.setBackground(Color.WHITE);
		panelMain.add(panelUserInfo);
		GridBagLayout gbl_panelUserList = new GridBagLayout();
		gbl_panelUserList.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelUserList.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelUserList.columnWeights = new double[]{1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelUserList.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelUserInfo.setLayout(gbl_panelUserList);

		labelName = new JLabel("Name:");
		GridBagConstraints gbc_labelName = new GridBagConstraints();
		gbc_labelName.anchor = GridBagConstraints.WEST;
		gbc_labelName.insets = new Insets(0, 0, 5, 5);
		gbc_labelName.gridx = 0;
		gbc_labelName.gridy = 0;
		panelUserInfo.add(labelName, gbc_labelName);

		textFieldName = new JTextField();
		textFieldName.setText(user.getName());
		GridBagConstraints gbc_nameTextfield = new GridBagConstraints();
		gbc_nameTextfield.insets = new Insets(0, 0, 5, 5);
		gbc_nameTextfield.fill = GridBagConstraints.BOTH;
		gbc_nameTextfield.gridx = 1;
		gbc_nameTextfield.gridy = 0;
		panelUserInfo.add(textFieldName, gbc_nameTextfield);
		textFieldName.setColumns(10);
		textFieldName.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				textFieldName.selectAll();
			}
		});

		labelSex = new JLabel("Sex:");
		GridBagConstraints gbc_labelSex = new GridBagConstraints();
		gbc_labelSex.insets = new Insets(0, 0, 5, 5);
		gbc_labelSex.anchor = GridBagConstraints.WEST;
		gbc_labelSex.gridx = 0;
		gbc_labelSex.gridy = 1;
		panelUserInfo.add(labelSex, gbc_labelSex);

		textFieldSex = new JTextField();
		textFieldSex.getDocument().addDocumentListener(new TextFieldDocumentListener(textFieldSex, dataType.sex));
		textFieldSex.setText(sex);
		textFieldSex.setColumns(10);
		GridBagConstraints gbc_studienumberTextfield = new GridBagConstraints();
		gbc_studienumberTextfield.insets = new Insets(0, 0, 5, 5);
		gbc_studienumberTextfield.fill = GridBagConstraints.BOTH;
		gbc_studienumberTextfield.gridx = 1;
		gbc_studienumberTextfield.gridy = 1;
		panelUserInfo.add(textFieldSex, gbc_studienumberTextfield);
		textFieldSex.setColumns(10);
		textFieldSex.setEditable(true);
		textFieldSex.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				textFieldSex.selectAll();
			}
		});


		labelBarcode = new JLabel("Barcode:");
		GridBagConstraints gbc_labelBarcode = new GridBagConstraints();
		gbc_labelBarcode.insets = new Insets(0, 0, 5, 5);
		gbc_labelBarcode.anchor = GridBagConstraints.WEST;
		gbc_labelBarcode.gridx = 0;
		gbc_labelBarcode.gridy = 2;
		panelUserInfo.add(labelBarcode, gbc_labelBarcode);

		textFieldBarcode = new JTextField();
		textFieldBarcode.getDocument().addDocumentListener(new TextFieldDocumentListener(textFieldBarcode, dataType.userbarcode));
		textFieldBarcode.setText(barcode);
		textFieldBarcode.setColumns(10);
		GridBagConstraints gbc_barcodeTextfield = new GridBagConstraints();
		gbc_barcodeTextfield.insets = new Insets(0, 0, 5, 5);
		gbc_barcodeTextfield.fill = GridBagConstraints.BOTH;
		gbc_barcodeTextfield.gridx = 1;
		gbc_barcodeTextfield.gridy = 2;
		panelUserInfo.add(textFieldBarcode, gbc_barcodeTextfield);
		textFieldBarcode.setColumns(10);
		textFieldBarcode.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				textFieldBarcode.selectAll();
			}
		});

		labelStudyline = new JLabel("Study:");
		GridBagConstraints gbc_labelStudyline = new GridBagConstraints();
		gbc_labelStudyline.insets = new Insets(0, 0, 5, 5);
		gbc_labelStudyline.anchor = GridBagConstraints.WEST;
		gbc_labelStudyline.gridx = 0;
		gbc_labelStudyline.gridy = 3;
		panelUserInfo.add(labelStudyline, gbc_labelStudyline);

		textFieldStudy = new JTextField();
		textFieldStudy.setText(study);
		textFieldStudy.setColumns(10);
		GridBagConstraints gbc_studylineTextfield = new GridBagConstraints();
		gbc_studylineTextfield.insets = new Insets(0, 0, 5, 5);
		gbc_studylineTextfield.fill = GridBagConstraints.BOTH;
		gbc_studylineTextfield.gridx = 1;
		gbc_studylineTextfield.gridy = 3;
		panelUserInfo.add(textFieldStudy, gbc_studylineTextfield);
		textFieldStudy.setColumns(10);
		textFieldStudy.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				textFieldStudy.selectAll();
			}
		});

		labelTeam = new JLabel("Team:");
		GridBagConstraints gbc_labelTeam = new GridBagConstraints();
		gbc_labelTeam.insets = new Insets(0, 0, 5, 5);
		gbc_labelTeam.anchor = GridBagConstraints.WEST;
		gbc_labelTeam.gridx = 0;
		gbc_labelTeam.gridy = 4;
		panelUserInfo.add(labelTeam, gbc_labelTeam);

		textFieldTeam = new JTextField();
		textFieldTeam.setText(team);
		textFieldTeam.setColumns(10);
		GridBagConstraints gbc_TeamTextfield = new GridBagConstraints();
		gbc_TeamTextfield.insets = new Insets(0, 0, 5, 5);
		gbc_TeamTextfield.fill = GridBagConstraints.BOTH;
		gbc_TeamTextfield.gridx = 1;
		gbc_TeamTextfield.gridy = 4;
		panelUserInfo.add(textFieldTeam, gbc_TeamTextfield);
		textFieldTeam.setColumns(10);
		textFieldTeam.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				textFieldTeam.selectAll();
			}
		});

		labelRank = new JLabel("Rank:");
		GridBagConstraints gbc_labelRank = new GridBagConstraints();
		gbc_labelRank.insets = new Insets(0, 0, 5, 5);
		gbc_labelRank.anchor = GridBagConstraints.WEST;
		gbc_labelRank.gridx = 0;
		gbc_labelRank.gridy = 5;
		panelUserInfo.add(labelRank, gbc_labelRank);

		
		//Prepare ranks combo box
		List<String> ranks = Database.getRanks();
		String[] types = new String[ranks.size()];
		int rs = 0;
		for(String r : ranks){
			types[rs] = r;
			rs++;
		}
//		String[] types = {"Rus","Vektor","Kabs","Hyttebums","Other"};

		comboBoxRank = new JComboBox<String>(types);
		comboBoxRank.setBackground(Color.WHITE);
		comboBoxRank.setSelectedItem(rank);
		GridBagConstraints gbc_comboBoxRank = new GridBagConstraints();
		gbc_comboBoxRank.insets = new Insets(0, 0, 0, 5);
		gbc_comboBoxRank.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxRank.gridx = 1;
		gbc_comboBoxRank.gridy = 5;
		panelUserInfo.add(comboBoxRank, gbc_comboBoxRank);

		panelGraphs = new JPanel();
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBackground(Color.WHITE);
		panelGraphs.add(chartPanel);
		panelMain.add(panelGraphs);
		panelGraphs.setLayout(new GridLayout(0, 1, 0, 0));
		
		rigidAreaLeft = Box.createRigidArea(new Dimension(w/2-300, 20));
		getContentPane().add(rigidAreaLeft, BorderLayout.WEST);

		rigidAreaTop = Box.createRigidArea(new Dimension(20, h/2-150));
		getContentPane().add(rigidAreaTop, BorderLayout.NORTH);

		rigidAreaBottom = Box.createRigidArea(new Dimension(20, h/2-150));
		getContentPane().add(rigidAreaBottom, BorderLayout.SOUTH);

		rigidAreaRight = Box.createRigidArea(new Dimension(w/2-300, 20));
		getContentPane().add(rigidAreaRight, BorderLayout.EAST);
	}

	private JFreeChart createChart(PieDataset dataset) {

		final JFreeChart chart = ChartFactory.createPieChart3D(
				"Total price: "+Database.getUserPrice(user.getID())+",- kr.",  // chart title
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
	
	//Button click actions
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == checkBoxDelete){
			if(checkBoxDelete.isSelected() == true){
				String[] options = {"Yes", "No"};
				int response = JOptionPane.showOptionDialog(getContentPane(), "You are about to delete " + textFieldName.getText() +  ". \nAre you sure you wish to continue?", "Delete User Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, "delete");
				if (response == JOptionPane.YES_OPTION) {
					delete = true;
				}
				if (response == JOptionPane.NO_OPTION){
					checkBoxDelete.setSelected(false);
				}
			}
		}
		if (e.getSource() == buttonSave){
			if (delete == true){
				Database.removeUser(id);
				WindowAdmin.updateUserList();
				WindowAdmin.searchUser("");
				WindowAdmin.writeToConsole("User Edit: " + textFieldName.getText() + " was deleted");
				this.dispose();
			}
			if (delete == false){
				saveUserInfo(textFieldName.getText(),Integer.parseInt(textFieldBarcode.getText()),textFieldStudy.getText(),textFieldTeam.getText(),comboBoxRank.getSelectedItem().toString());
				WindowAdmin.updateUserList();
				WindowAdmin.searchUser("");
				this.dispose();
			}


		}
		if (e.getSource() == buttonCancel){
			WindowAdmin.writeToConsole("User Edit: " + textFieldName.getText() + " was canceled");
			this.dispose();
		}
		if (e.getSource() == btnTransactionDetails){
			@SuppressWarnings("unused")
			JFrame frame = new transactionWindow().TransactionWindow(user);
		}
	}

	//Reads the selected user info into the Edit Page
	public static void getUserInfo(int bc){
		List<User> users = Database.getUserByBarcode(bc);
		if (users.size() <= 0){
			//Throw no users found exception
		}
		else if (users.size() > 1){
			//Handle too many users found
		}
		else{
			User u = users.get(0);
			System.out.println(u.getName());
			user = u;
			id = u.getID();
			name = u.getName();
			sex = user.getSex();
			barcode = ""+bc;
			study = u.getStudy();
			team = u.getTeam();
			rank = u.getRank();

		}
	}

	public void saveUserInfo(String name,int barcode, String study, String team, String rank){
		List<User> users = Database.getUserByBarcode(barcode);
		if(users.isEmpty() || users.get(0).getID() == id){
			Database.editUser(id,name,sex,barcode,study,team,rank);
			WindowAdmin.writeToConsole("User Edit: " + textFieldName.getText() + " was edited");
		}
		else{
			WindowAdmin.writeToConsole("Barcode change for user " + selUser + " is not allowed, barcode already in use");
		}
	}

	/** The entry main() method */
	public static void main(String user) {
		selUser = user;
		selectedUser = Integer.parseInt(user.substring(0, 4));
		System.out.println("Barcode to search for: " + selectedUser);
		getUserInfo(selectedUser);
		// Run GUI codes in the Event-Dispatching thread for thread safety
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WindowEditUser();  // Let the constructor do the job
			}
		});
	}
}