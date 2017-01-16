package system;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

// Swing Program Template
@SuppressWarnings("serial")
public class WindowBillSettings extends JFrame implements ActionListener {

	// Import modules
	//	protected static Database Database;

	// Name-constants to define the various dimensions
	public static final int h = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static final int w = Toolkit.getDefaultToolkit().getScreenSize().width;
	JPanel panelMain,panelButtons;

	// private variables of UI components
	// ......

	//Local variables
	static List<String> users = new ArrayList<String>();
	private JButton btnCancel,btnSave;
	private JPanel panel;
	private Component rigidArea;
	private Component rigidArea_1;
	private Component rigidArea_2;
	private Component rigidArea_3;
	private JPanel panel_1;
	private JCheckBox chkbxShareWaste;
	private JPanel panel_2;
	private JRadioButton rdbtnDelimiterDot;
	private JRadioButton rdbtnDelmiterComma;
	private JPanel panelWasteApplication;
	private JPanel panel_3;
	private JLabel lblAdditionalFee;
	JTextField textFieldAddFee;
	Type type;
	private JButton btnPreview;

	/** Constructor to setup the UI components */
	public WindowBillSettings() {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setUndecorated(true);
		getContentPane().setBackground(new Color (240,240,240,150));
		setBackground(new Color (240,240,240,150));
		setAlwaysOnTop(true);

		panel = new JPanel();
		panel.setOpaque(false);
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		panelMain = new JPanel();
		panel.add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new GridLayout(1, 0, 0, 0));

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Bill Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBackground(Color.WHITE);
		panelMain.add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);

		panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 5, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel_1.add(panel_3, gbc);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gridBagLayout);

		lblAdditionalFee = new JLabel("Additional fee (devides over all users who pays for waste)");
		GridBagConstraints gbc_lblAdditionalFee = new GridBagConstraints();
		gbc_lblAdditionalFee.insets = new Insets(0, 0, 0, 5);
		gbc_lblAdditionalFee.anchor = GridBagConstraints.WEST;
		gbc_lblAdditionalFee.gridx = 0;
		gbc_lblAdditionalFee.gridy = 0;
		panel_3.add(lblAdditionalFee, gbc_lblAdditionalFee);

		textFieldAddFee = new JTextField();
		textFieldAddFee.setText("0");
		GridBagConstraints gbc_textFieldAddFee = new GridBagConstraints();
		gbc_textFieldAddFee.anchor = GridBagConstraints.EAST;
		gbc_textFieldAddFee.gridx = 1;
		gbc_textFieldAddFee.gridy = 0;
		panel_3.add(textFieldAddFee, gbc_textFieldAddFee);
		textFieldAddFee.setColumns(10);
		textFieldAddFee.getDocument().addDocumentListener(new TextFieldDocumentListener(textFieldAddFee, dataType.decimal));

		chkbxShareWaste = new JCheckBox("Everyone shares waste");
		chkbxShareWaste.setEnabled(true);
		chkbxShareWaste.setBackground(Color.WHITE);
		GridBagConstraints gbc_chkbxShareWaste = new GridBagConstraints();
		gbc_chkbxShareWaste.insets = new Insets(0, 0, 5, 0);
		gbc_chkbxShareWaste.fill = GridBagConstraints.BOTH;
		gbc_chkbxShareWaste.gridx = 0;
		gbc_chkbxShareWaste.gridy = 1;
		panel_1.add(chkbxShareWaste, gbc_chkbxShareWaste);
		chkbxShareWaste.addActionListener(this);

		panelWasteApplication = new JPanel();
		panelWasteApplication.setBorder(new TitledBorder(null, "Waste Application", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelWasteApplication.setBackground(Color.WHITE);
		GridBagConstraints gbc_panelWasteApplication = new GridBagConstraints();
		gbc_panelWasteApplication.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelWasteApplication.insets = new Insets(0, 0, 5, 0);
		gbc_panelWasteApplication.gridx = 0;
		gbc_panelWasteApplication.gridy = 2;
		panel_1.add(panelWasteApplication, gbc_panelWasteApplication);

		//Dynamic checkboxes
		List<String> groups = Database.getRanks();

		panelWasteApplication.removeAll();
		gbc_panelWasteApplication.gridx = 0;
		gbc_panelWasteApplication.gridy = 0;
		GridBagLayout gbl_panelWasteApplication = new GridBagLayout();
		gbl_panelWasteApplication.columnWidths = new int[]{0};
		gbl_panelWasteApplication.rowHeights = new int[]{0};
		gbl_panelWasteApplication.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panelWasteApplication.rowWeights = new double[]{Double.MIN_VALUE};
		panelWasteApplication.setLayout(gbl_panelWasteApplication);

		for (String g:groups){
			gbc_panelWasteApplication.gridx=0;
			gbc_panelWasteApplication.gridy++;
			gbc_panelWasteApplication.anchor = GridBagConstraints.NORTH;
			panelWasteApplication.add(new JCheckBox(g),gbc_panelWasteApplication);
		}
		for (Component component : panelWasteApplication.getComponents()){
			component.setBackground(Color.WHITE);
		}

		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Decimal seperator", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.anchor = GridBagConstraints.WEST;
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 3;
		panel_1.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		panel_2.setLayout(gbl_panel_2);


		//Delimiter settings (Caution LOGIC IS REVERSE! Comma is dot and dot is comma)
		rdbtnDelimiterDot = new JRadioButton(", / Comma (Default)");
		rdbtnDelimiterDot.setBackground(Color.WHITE);
		rdbtnDelimiterDot.setSelected(true);
		GridBagConstraints gbc_rdbtnDelimiterDot = new GridBagConstraints();
		gbc_rdbtnDelimiterDot.anchor = GridBagConstraints.WEST;
		gbc_rdbtnDelimiterDot.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnDelimiterDot.gridx = 0;
		gbc_rdbtnDelimiterDot.gridy = 0;
		panel_2.add(rdbtnDelimiterDot, gbc_rdbtnDelimiterDot);

		rdbtnDelmiterComma = new JRadioButton("- / Dot");
		rdbtnDelmiterComma.setBackground(Color.WHITE);
		GridBagConstraints gbc_rdbtnDelmiterComma = new GridBagConstraints();
		gbc_rdbtnDelmiterComma.anchor = GridBagConstraints.WEST;
		gbc_rdbtnDelmiterComma.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnDelmiterComma.gridx = 1;
		gbc_rdbtnDelmiterComma.gridy = 0;
		panel_2.add(rdbtnDelmiterComma, gbc_rdbtnDelmiterComma);

		//Delimiter group
		ButtonGroup delimiter = new ButtonGroup();
		delimiter.add(rdbtnDelimiterDot);
		delimiter.add(rdbtnDelmiterComma);

		//Generate top panel.
		JPanel panelBottom = new JPanel();
		panel.add(panelBottom, BorderLayout.SOUTH);
		panelBottom.setBackground(Color.WHITE);
		panelBottom.setLayout(new GridLayout(1, 0, 0, 0));

		//Right alining panel at bottom
		panelButtons = new JPanel();
		panelButtons.setBackground(Color.WHITE);
		FlowLayout fl_panelButtons = (FlowLayout) panelButtons.getLayout();
		fl_panelButtons.setAlignment(FlowLayout.RIGHT);

		//Left aligning panel at bottom
		JPanel panelBottomLeft = new JPanel();
		FlowLayout fl_panelBottomLeft = (FlowLayout) panelBottomLeft.getLayout();
		fl_panelBottomLeft.setAlignment(FlowLayout.LEFT);
		panelBottomLeft.setBackground(Color.WHITE);
		panelBottom.add(panelBottomLeft);

		btnPreview = new JButton("Preview");
		btnPreview.addActionListener(this);
		btnPreview.setSize(10, 10);
		panelBottomLeft.add(btnPreview);

		panelBottom.add(panelButtons);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		panelButtons.add(btnCancel);

		btnSave = new JButton("Save");
		btnSave.addActionListener(this);
		panelButtons.add(btnSave);

		//		Transparent white space --- Comment these out to see the designer window
		rigidArea = Box.createRigidArea(new Dimension(20, h/2-200));
		getContentPane().add(rigidArea, BorderLayout.SOUTH);

		rigidArea_2 = Box.createRigidArea(new Dimension(20, h/2-200));
		getContentPane().add(rigidArea_2, BorderLayout.NORTH);

		rigidArea_1 = Box.createRigidArea(new Dimension(w/2-300, 20));
		getContentPane().add(rigidArea_1, BorderLayout.WEST);

		rigidArea_3 = Box.createRigidArea(new Dimension(w/2-300, 20));
		getContentPane().add(rigidArea_3, BorderLayout.EAST);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Exit when close button clicked
		setTitle("Add Product"); // "this" JFrame sets title
		setSize(231, 301);  // or pack() the components
		setLocationRelativeTo(null);
		setVisible(true);   // show it
	}

	public void actionPerformed(ActionEvent e){
		if (e.getSource() == btnPreview){
			//TODO Preview bill
			List<String> ranks = selectedRanks();
			if(!chkbxShareWaste.isSelected() && ranks.isEmpty()){
				JOptionPane.showMessageDialog(this, "Please choose who will pay for the waste");
			}
			else if (chkbxShareWaste.isSelected() && !ranks.isEmpty()){
				JOptionPane.showMessageDialog(this, "Please only select everyone or the groups you want to share between");
			}
			else{
				@SuppressWarnings("unused")
				JFrame frame = new billPreviewWindow().BillPreview(ranks, Double.parseDouble(textFieldAddFee.getText()));
			}
		}
		if (e.getSource() == btnSave){
			//Check if export is possible
			List<String> ranks = selectedRanks();
			if(!chkbxShareWaste.isSelected() && ranks.isEmpty()){
				JOptionPane.showMessageDialog(this, "Please choose who will pay for the waste");
			}
			else if (chkbxShareWaste.isSelected() && !ranks.isEmpty()){
				JOptionPane.showMessageDialog(this, "Please only select everyone or the groups you want to share between");
			}
			else{
				//Filechooser
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Comma sepereted file .csv", "csv");
				fc.setFileFilter(filter);
				int result = fc.showSaveDialog(this);
				switch (result) {
				case JFileChooser.APPROVE_OPTION:
					String path = fc.getSelectedFile().getAbsolutePath();
					String delimiter = ".";
					System.out.println("Delimiter is : "+delimiter);
					if (rdbtnDelmiterComma.isSelected()){
						delimiter = ",";
					}
					//Check what checkboxes is selected

					//Run the actual export
					double fee = 0;
					if (textFieldAddFee.getText().length() > 0){
						fee = Double.parseDouble(textFieldAddFee.getText());
					}

					//If noone is selected to pay the waste


					Database.exportBill(path+".csv",delimiter,chkbxShareWaste.isSelected(),ranks,fee); //Args = (filepath,delimiter,ignore crew,share waste)					

					break;
				case JFileChooser.CANCEL_OPTION:
					WindowAdmin.writeToConsole("Generate was canceled");
					break;
				case JFileChooser.ERROR_OPTION:
					WindowAdmin.writeToConsole("There was an error during generation of the bill");
					break;
				}
				this.dispose();
			}
		}
		if (e.getSource() == btnCancel){
			this.dispose();
		}
	}

	private List<String> selectedRanks() {
		List<String> ranks = new ArrayList<String>();
		for (Component component : panelWasteApplication.getComponents()){
			if(((JCheckBox)component).isSelected()){						
				System.out.println("Checkboxes checked "+((JCheckBox)component).getText());
				ranks.add(((JCheckBox)component).getText());
			}
		}
		return ranks;
	}
	/** The entry main() method */
	public static void main() {
		// Run GUI codes in the Event-Dispatching thread for thread safety
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WindowBillSettings();  // Let the constructor do the job
			}
		});
	}
}