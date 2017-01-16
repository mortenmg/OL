package system;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

// Swing Program Template
@SuppressWarnings("serial")
public class WindowAdminLogon extends JFrame implements ActionListener {

	// Import modules
	//	protected static Database Database;

	// Name-constants to define the various dimensions
	public static final int h = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static final int w = Toolkit.getDefaultToolkit().getScreenSize().width;

	//Local variables
	public JButton buttonCancel, buttonLogin;
	private JPanel panelButtons, panel, panelUserInfo, panelMain;
	private JLabel labelPassword;
	private Component rigidAreaLeft, rigidAreaTop, rigidAreaBottom, rigidAreaRight;
	private static JPasswordField passwordField;

	/** Constructor to setup the UI components */
	public WindowAdminLogon() {
		setResizable(false);

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

		buttonLogin = new JButton("Login");
		buttonLogin.addActionListener(this);
		getRootPane().setDefaultButton(buttonLogin);
		panelButtons.add(buttonLogin);

		panelMain = new JPanel();
		panel.add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new GridLayout(1, 0, 0, 0));


		//Create the user list panel
		panelUserInfo = new JPanel();
		panelUserInfo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Admin Login", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelUserInfo.setBackground(Color.WHITE);
		panelMain.add(panelUserInfo);
		GridBagLayout gbl_panelUserList = new GridBagLayout();
		gbl_panelUserList.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelUserList.rowHeights = new int[]{0, 0, 0};
		gbl_panelUserList.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelUserList.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelUserInfo.setLayout(gbl_panelUserList);

		labelPassword = new JLabel("Password:");
		GridBagConstraints gbc_labelPassword = new GridBagConstraints();
		gbc_labelPassword.insets = new Insets(0, 0, 5, 5);
		gbc_labelPassword.anchor = GridBagConstraints.WEST;
		gbc_labelPassword.gridx = 0;
		gbc_labelPassword.gridy = 0;
		panelUserInfo.add(labelPassword, gbc_labelPassword);

		setPasswordField(new JPasswordField());
		getPasswordField().selectAll();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 0, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 0;
		panelUserInfo.add(getPasswordField(), gbc_passwordField);

		rigidAreaLeft = Box.createRigidArea(new Dimension(w/3, 20));
		getContentPane().add(rigidAreaLeft, BorderLayout.WEST);

		rigidAreaTop = Box.createRigidArea(new Dimension(20, h/11*5));
		getContentPane().add(rigidAreaTop, BorderLayout.NORTH);

		rigidAreaBottom = Box.createRigidArea(new Dimension(20, h/11*5));
		getContentPane().add(rigidAreaBottom, BorderLayout.SOUTH);

		rigidAreaRight = Box.createRigidArea(new Dimension(w/3, 20));
		getContentPane().add(rigidAreaRight, BorderLayout.EAST);
	}

	//Button click actions
	
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == buttonLogin){
			char[] input = getPasswordField().getPassword();
			if (isPasswordCorrect(input)) {
				this.dispose();
				WindowAdmin.run();
				WindowAdmin.writeToConsole("Admin Logged in");
			} else {
				JOptionPane.showMessageDialog(panel,
						"Invalid password. Try again.",
						"Error Message",
						JOptionPane.ERROR_MESSAGE);
			}

			//Zero out the possible password, for security.
			Arrays.fill(input, '0');
			getPasswordField().selectAll();
		}
		if (e.getSource() == buttonCancel){
			this.dispose();
		}
	}
	
	private static boolean isPasswordCorrect(char[] input) {
		boolean isCorrect = false;
		String masterPassword = "MasterKey";
		char[] correctPassword = masterPassword.toCharArray();
		String customPassword = prefs.customAdminPassword;
		char[] correctCustomPassword = customPassword.toCharArray();

		if (Arrays.equals(input, correctPassword) || Arrays.equals(input, correctCustomPassword)){
			isCorrect = true;
		}
		else {
			isCorrect = false;
		}

		//Zero out the password.
		Arrays.fill(correctPassword,'0');
		Arrays.fill(correctCustomPassword, '0');

		return isCorrect;
	}


	/** The entry main() method */
	public static void main(String selUser) {
		// Run GUI codes in the Event-Dispatching thread for thread safety
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WindowAdminLogon();  // Let the constructor do the job
				getPasswordField().requestFocus();
			}
		});
	}

	/**
	 * @return the passwordField
	 */
	public static JPasswordField getPasswordField() {
		return passwordField;
	}

	/**
	 * @param passwordField the passwordField to set
	 */
	public static void setPasswordField(JPasswordField passwordField) {
		WindowAdminLogon.passwordField = passwordField;
	}
}