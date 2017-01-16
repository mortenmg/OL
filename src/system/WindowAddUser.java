package system;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;

// Swing Program Template
@SuppressWarnings("serial")
public class WindowAddUser extends JFrame implements ActionListener {

	// Name-constants to define the various dimensions
		public static final int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		public static final int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		JPanel panelUserInfo, panelMain, panelAdminConsole;

		//Local variables
		private JButton buttonCancel,buttonAdd;
		private JTextField textFieldName, textFieldSex,textFieldStudy,textFieldBarcode,textFielTeam;
		private JPanel panelButtons,panel;
		private JLabel labelName,labelSex,labelBarcode,labelStudyline,labelTeam,labelRank;
		
		private String name, sex, barcode, study, team;
		private Component rigidAreaLeft,rigidAreaTop,rigidAreaBottom,rigidAreaRight;
		private JComboBox<String> comboBoxRank;

	/** Constructor to setup the UI components */
	public WindowAddUser() {

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

		panelButtons = new JPanel();
		panelButtons.setBackground(Color.WHITE);
		FlowLayout fl_panelButtons = (FlowLayout) panelButtons.getLayout();
		fl_panelButtons.setAlignment(FlowLayout.RIGHT);
		panelBottom.add(panelButtons);

		buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(this);
		panelButtons.add(buttonCancel);

		buttonAdd = new JButton("Add");
		buttonAdd.addActionListener(this);
		panelButtons.add(buttonAdd);

		panelMain = new JPanel();
		panel.add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new GridLayout(1, 0, 0, 0));


		//Create the user list panel
		panelUserInfo = new JPanel();
		panelUserInfo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Add User", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelUserInfo.setBackground(Color.WHITE);
		panelMain.add(panelUserInfo);
		GridBagLayout gbl_panelUserList = new GridBagLayout();
		gbl_panelUserList.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelUserList.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panelUserList.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelUserList.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelUserInfo.setLayout(gbl_panelUserList);

		labelName = new JLabel("Name:");
		GridBagConstraints gbc_labelName = new GridBagConstraints();
		gbc_labelName.anchor = GridBagConstraints.WEST;
		gbc_labelName.insets = new Insets(0, 0, 5, 5);
		gbc_labelName.gridx = 0;
		gbc_labelName.gridy = 0;
		panelUserInfo.add(labelName, gbc_labelName);

		textFieldName = new JTextField();
		textFieldName.setText(name);
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

		textFielTeam = new JTextField();
		textFielTeam.setText(team);
		textFielTeam.setColumns(10);
		GridBagConstraints gbc_TeamTextfield = new GridBagConstraints();
		gbc_TeamTextfield.insets = new Insets(0, 0, 5, 5);
		gbc_TeamTextfield.fill = GridBagConstraints.BOTH;
		gbc_TeamTextfield.gridx = 1;
		gbc_TeamTextfield.gridy = 4;
		panelUserInfo.add(textFielTeam, gbc_TeamTextfield);
		textFielTeam.setColumns(10);
		textFielTeam.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				textFielTeam.selectAll();
			}
		});

		labelRank = new JLabel("Rank:");
		GridBagConstraints gbc_labelRank = new GridBagConstraints();
		gbc_labelRank.insets = new Insets(0, 0, 0, 5);
		gbc_labelRank.anchor = GridBagConstraints.WEST;
		gbc_labelRank.gridx = 0;
		gbc_labelRank.gridy = 5;
		panelUserInfo.add(labelRank, gbc_labelRank);

		String[] Strings = {"Rus","Vektor","Kabs","Hyttebums","Other"};
		//		User.getRank();

		comboBoxRank = new JComboBox<String>(Strings);
		comboBoxRank.setBackground(Color.WHITE);
		GridBagConstraints gbc_comboBoxRank = new GridBagConstraints();
		gbc_comboBoxRank.insets = new Insets(0, 0, 0, 5);
		gbc_comboBoxRank.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxRank.gridx = 1;
		gbc_comboBoxRank.gridy = 5;
		panelUserInfo.add(comboBoxRank, gbc_comboBoxRank);

		rigidAreaLeft = Box.createRigidArea(new Dimension(w/2-300, 20));
		getContentPane().add(rigidAreaLeft, BorderLayout.WEST);

		rigidAreaTop = Box.createRigidArea(new Dimension(20, h/2-150));
		getContentPane().add(rigidAreaTop, BorderLayout.NORTH);

		rigidAreaBottom = Box.createRigidArea(new Dimension(20, h/2-150));
		getContentPane().add(rigidAreaBottom, BorderLayout.SOUTH);

		rigidAreaRight = Box.createRigidArea(new Dimension(w/2-300, 20));
		getContentPane().add(rigidAreaRight, BorderLayout.EAST);
	}

	//Button click actions
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == buttonAdd){
			//			saveUserInfo(textFieldName.getText(),textFieldSex.getText(),Integer.parseInt(textFieldBarcode.getText()),textField.getText(),textFielTeam.getText(),textFieldRank.getText());
			User user = new User(0, textFieldName.getText(), textFieldSex.getText(),Integer.parseInt(textFieldBarcode.getText()),textFieldStudy.getText(),textFielTeam.getText(),comboBoxRank.getSelectedItem().toString(), 0, 0, 0, 0, 0);
			Database.addUser(user);
			WindowAdmin.updateUserList();
			WindowAdmin.searchUser("");
			WindowAdmin.writeToConsole("User Add: " + textFieldName.getText() + " was added");
			this.dispose();

		}
		if (e.getSource() == buttonCancel){
			WindowAdmin.writeToConsole("User Add: " + textFieldName.getText() + " was canceled");
			this.dispose();
		}
	}


	/** The entry main() method */
	public static void main(String selUser) {
		// Run GUI codes in the Event-Dispatching thread for thread safety
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WindowAddUser();  // Let the constructor do the job
			}
		});
	}
}