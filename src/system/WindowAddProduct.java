package system;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;

// Swing Program Template
@SuppressWarnings("serial")
public class WindowAddProduct extends JFrame implements ActionListener {

	// Import modules
	//	protected static Database Database;

	// Name-constants to define the various dimensions
	public static final int h = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static final int w = Toolkit.getDefaultToolkit().getScreenSize().width;
	JPanel panelProductList, panelMain,panelButtons;
	JTextField textFieldName,textFieldTotalStock,textFieldPrice,textFieldBarcode;

	// private variables of UI components
	// ......

	//Local variables
	static List<String> users = new ArrayList<String>();
	private JButton buttonCancel,buttonAdd;
	private JLabel labelName,labelTotalStock,labelPrice,labelBarcode,labelType;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxType;
	private JPanel panel;
	private Component rigidArea;
	private Component rigidArea_1;
	private Component rigidArea_2;
	private Component rigidArea_3;

	/** Constructor to setup the UI components */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public WindowAddProduct() {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setUndecorated(true);
		getContentPane().setBackground(new Color (240,240,240,150));
		setBackground(new Color (240,240,240,150));
		setAlwaysOnTop(true);

		String[] Strings = {"Beer","Cider","Soda","Cocoa","Other"};

		panel = new JPanel();
		panel.setOpaque(false);
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		panelMain = new JPanel();
		panel.add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new GridLayout(1, 0, 0, 0));


		panelProductList = new JPanel();
		panelProductList.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Add Product", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelProductList.setBackground(Color.WHITE);
		panelMain.add(panelProductList);
		GridBagLayout gbl_panelProductList = new GridBagLayout();
		gbl_panelProductList.columnWidths = new int[]{0, 0, 0};
		gbl_panelProductList.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panelProductList.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panelProductList.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelProductList.setLayout(gbl_panelProductList);

		labelName = new JLabel("Name:");
		GridBagConstraints gbc_labelName = new GridBagConstraints();
		gbc_labelName.insets = new Insets(0, 0, 5, 5);
		gbc_labelName.anchor = GridBagConstraints.WEST;
		gbc_labelName.gridx = 0;
		gbc_labelName.gridy = 0;
		panelProductList.add(labelName, gbc_labelName);

		textFieldName = new JTextField();
		textFieldName.setText("Name");
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldName.fill = GridBagConstraints.BOTH;
		gbc_textFieldName.gridx = 1;
		gbc_textFieldName.gridy = 0;
		panelProductList.add(textFieldName, gbc_textFieldName);
		textFieldName.setColumns(10);
		textFieldName.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				textFieldName.selectAll();
			}
		});
		//Product.getType();

		comboBoxType = new JComboBox(Strings);
		comboBoxType.setBackground(Color.WHITE);
		GridBagConstraints gbc_comboBoxType = new GridBagConstraints();
		gbc_comboBoxType.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxType.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxType.gridx = 1;
		gbc_comboBoxType.gridy = 1;
		panelProductList.add(comboBoxType, gbc_comboBoxType);

		labelTotalStock = new JLabel("Total Stock:");
		GridBagConstraints gbc_labelTotalStock = new GridBagConstraints();
		gbc_labelTotalStock.insets = new Insets(0, 0, 5, 5);
		gbc_labelTotalStock.anchor = GridBagConstraints.WEST;
		gbc_labelTotalStock.gridx = 0;
		gbc_labelTotalStock.gridy = 4;
		panelProductList.add(labelTotalStock, gbc_labelTotalStock);

		textFieldTotalStock = new JTextField();
		textFieldTotalStock.getDocument().addDocumentListener(new TextFieldDocumentListener(textFieldTotalStock, dataType.integer));
		textFieldTotalStock.setText("Total Stock");
		textFieldTotalStock.setColumns(10);
		GridBagConstraints gbc_textFieldTotalStock = new GridBagConstraints();
		gbc_textFieldTotalStock.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldTotalStock.fill = GridBagConstraints.BOTH;
		gbc_textFieldTotalStock.gridx = 1;
		gbc_textFieldTotalStock.gridy = 4;
		panelProductList.add(textFieldTotalStock, gbc_textFieldTotalStock);
		textFieldTotalStock.setColumns(10);
		textFieldTotalStock.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				textFieldTotalStock.selectAll();
			}
		});

		labelPrice = new JLabel("Price:");
		GridBagConstraints gbc_labelPrice = new GridBagConstraints();
		gbc_labelPrice.insets = new Insets(0, 0, 5, 5);
		gbc_labelPrice.anchor = GridBagConstraints.WEST;
		gbc_labelPrice.gridx = 0;
		gbc_labelPrice.gridy = 2;
		panelProductList.add(labelPrice, gbc_labelPrice);

		labelBarcode = new JLabel("Barcode:");
		GridBagConstraints gbc_labelBarcode = new GridBagConstraints();
		gbc_labelBarcode.insets = new Insets(0, 0, 5, 5);
		gbc_labelBarcode.anchor = GridBagConstraints.WEST;
		gbc_labelBarcode.gridx = 0;
		gbc_labelBarcode.gridy = 3;
		panelProductList.add(labelBarcode, gbc_labelBarcode);

		textFieldBarcode = new JTextField();
		textFieldBarcode.getDocument().addDocumentListener(new TextFieldDocumentListener(textFieldBarcode, dataType.productbarcode));
		textFieldBarcode.setText("Barcode");
		textFieldBarcode.setColumns(10);
		GridBagConstraints gbc_textFieldBarcode = new GridBagConstraints();
		gbc_textFieldBarcode.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldBarcode.fill = GridBagConstraints.BOTH;
		gbc_textFieldBarcode.gridx = 1;
		gbc_textFieldBarcode.gridy = 3;
		panelProductList.add(textFieldBarcode, gbc_textFieldBarcode);
		textFieldBarcode.setColumns(10);
		textFieldBarcode.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				textFieldBarcode.selectAll();
			}
		});

		textFieldPrice = new JTextField();
		textFieldPrice.getDocument().addDocumentListener(new TextFieldDocumentListener(textFieldPrice,dataType.decimal));
		textFieldPrice.setText("Price");
		textFieldPrice.setColumns(10);
		GridBagConstraints gbc_textFieldPrice = new GridBagConstraints();
		gbc_textFieldPrice.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldPrice.fill = GridBagConstraints.BOTH;
		gbc_textFieldPrice.gridx = 1;
		gbc_textFieldPrice.gridy = 2;
		panelProductList.add(textFieldPrice, gbc_textFieldPrice);
		textFieldPrice.setColumns(10);
		textFieldPrice.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				textFieldPrice.selectAll();
			}
		});

		labelType = new JLabel("Type:");
		GridBagConstraints gbc_labelType = new GridBagConstraints();
		gbc_labelType.insets = new Insets(0, 0, 5, 5);
		gbc_labelType.anchor = GridBagConstraints.WEST;
		gbc_labelType.gridx = 0;
		gbc_labelType.gridy = 1;
		panelProductList.add(labelType, gbc_labelType);
		//Generate top panel.
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

		rigidArea = Box.createRigidArea(new Dimension(20, h/2-150));
		getContentPane().add(rigidArea, BorderLayout.SOUTH);

		rigidArea_2 = Box.createRigidArea(new Dimension(20, h/2-150));
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
		if (e.getSource() == buttonAdd){
			Product product = new Product(0, textFieldName.getText(),comboBoxType.getSelectedItem().toString(),"soundstring", Float.parseFloat(textFieldPrice.getText()),Long.parseLong(textFieldBarcode.getText()),Integer.parseInt(textFieldTotalStock.getText()),0, 0, 0, 0);
			Database.addProduct(product);
			WindowAdmin.updateProductList();
			WindowAdmin.searchProduct("");
			WindowAdmin.writeToConsole("Product Add: " + textFieldName.getText() + " was added");
			this.dispose();

		}
		if (e.getSource() == buttonCancel){
			WindowAdmin.writeToConsole("Product Add: " + textFieldName.getText() + " was canceled");
			this.dispose();
		}
	}

	/** The entry main() method */
	public static void main(String[] args) {
		// Run GUI codes in the Event-Dispatching thread for thread safety
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WindowAddProduct();  // Let the constructor do the job
			}
		});
	}
}