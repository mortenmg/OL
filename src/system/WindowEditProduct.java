package system;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

// Swing Program Template
@SuppressWarnings("serial")
public class WindowEditProduct extends JFrame implements ActionListener {

	// Import modules
	//	protected static Database Database;

	// Name-constants to define the various dimensions
	public static final int h = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static final int w = Toolkit.getDefaultToolkit().getScreenSize().width;


	//Local variables
	static List<String> Products = new ArrayList<String>();
	JPanel panelProductList, panelMain,panelButtons;
	JTextField textfieldName,textCurrentStock,textfieldPrice,textfieldBarcode,textfieldTotalStock;
	private JButton btnCancel,btnSave;
	private JLabel labelName,labelCurrentStock,labelPrice,labelBarcode,labelType,labelTotalStock;
	private JComboBox<String> comboBoxType;
	private Component rigidArea,rigidArea_1,rigidArea_2,rigidArea_3;
	private String name,type,price,barcode, soundstring;
	private int totalStock,currentStock,bought, soundmod, sound;
	private static String selectedProduct;
	private JCheckBox checkBoxDelete;
	private JPanel panel;
	private boolean delete = false;
	private JLabel labelBought;
	private JTextField textfieldBought;
	private JPanel panel_1;
	private JButton btnTotalStockEdit;
	private JTextField textFieldWaste;
	private JLabel labelWaste;
	private JLabel labelEnableSoundboard;
	private JCheckBox chckbxSoundboard;
	private JLabel lblSoundOnPurchase;
	private JLabel lblSoundMultiplierbuy;
	private JTextField textFieldSoundMultiply;
	private JTextField textFieldSound;


	/** Constructor to setup the UI components */
	public WindowEditProduct() {
		getProductInfo(selectedProduct);
		setUndecorated(true);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		getContentPane().setBackground(new Color (240,240,240,150));
		setBackground(new Color (240,240,240,150));
		setAlwaysOnTop(true);

		String[] types = {"Beer","Cider","Soda","Cocoa","Other"};

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		panelMain = new JPanel();
		panel.add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new GridLayout(1, 0, 0, 0));


		panelProductList = new JPanel();
		panelProductList.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelProductList.setBorder(new TitledBorder(null, "Edit Product", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelProductList.setBackground(Color.WHITE);
		panelMain.add(panelProductList);
		GridBagLayout gbl_panelProductList = new GridBagLayout();
		gbl_panelProductList.columnWidths = new int[]{0, 0, 0};
		gbl_panelProductList.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelProductList.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panelProductList.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelProductList.setLayout(gbl_panelProductList);

		labelName = new JLabel("Name:");
		GridBagConstraints gbc_labelName = new GridBagConstraints();
		gbc_labelName.insets = new Insets(0, 0, 5, 5);
		gbc_labelName.anchor = GridBagConstraints.WEST;
		gbc_labelName.gridx = 0;
		gbc_labelName.gridy = 0;
		panelProductList.add(labelName, gbc_labelName);

		textfieldName = new JTextField();
		textfieldName.setText(name);
		GridBagConstraints gbc_textfieldName = new GridBagConstraints();
		gbc_textfieldName.insets = new Insets(0, 0, 5, 0);
		gbc_textfieldName.fill = GridBagConstraints.BOTH;
		gbc_textfieldName.gridx = 1;
		gbc_textfieldName.gridy = 0;
		panelProductList.add(textfieldName, gbc_textfieldName);
		textfieldName.setColumns(10);
		textfieldName.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				textfieldName.selectAll();
			}
		});

		labelType = new JLabel("Type:");
		GridBagConstraints gbc_labelType = new GridBagConstraints();
		gbc_labelType.insets = new Insets(0, 0, 5, 5);
		gbc_labelType.anchor = GridBagConstraints.WEST;
		gbc_labelType.gridx = 0;
		gbc_labelType.gridy = 1;
		panelProductList.add(labelType, gbc_labelType);
		//Product.getType();

		comboBoxType = new JComboBox<String>();
		for (String t : types){
			comboBoxType.addItem(t);
		}
		comboBoxType.setBackground(Color.WHITE);
		GridBagConstraints gbc_comboBoxType = new GridBagConstraints();
		gbc_comboBoxType.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxType.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxType.gridx = 1;
		gbc_comboBoxType.gridy = 1;
		panelProductList.add(comboBoxType, gbc_comboBoxType);
		comboBoxType.setSelectedItem(type);

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

		textfieldBarcode = new JTextField();
		textfieldBarcode.getDocument().addDocumentListener(new TextFieldDocumentListener(textfieldBarcode, dataType.productbarcode));
		textfieldBarcode.setText(barcode);
		textfieldBarcode.setColumns(10);
		GridBagConstraints gbc_textfieldBarcode = new GridBagConstraints();
		gbc_textfieldBarcode.insets = new Insets(0, 0, 5, 0);
		gbc_textfieldBarcode.fill = GridBagConstraints.BOTH;
		gbc_textfieldBarcode.gridx = 1;
		gbc_textfieldBarcode.gridy = 3;
		panelProductList.add(textfieldBarcode, gbc_textfieldBarcode);
		textfieldBarcode.setColumns(10);
		textfieldBarcode.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				textfieldBarcode.selectAll();
			}
		});

		textfieldPrice = new JTextField();
		textfieldPrice.getDocument().addDocumentListener(new TextFieldDocumentListener(textfieldPrice, dataType.decimal));
		textfieldPrice.setText(price);
		textfieldPrice.setColumns(10);
		GridBagConstraints gbc_textfieldPrice = new GridBagConstraints();
		gbc_textfieldPrice.insets = new Insets(0, 0, 5, 0);
		gbc_textfieldPrice.fill = GridBagConstraints.BOTH;
		gbc_textfieldPrice.gridx = 1;
		gbc_textfieldPrice.gridy = 2;
		panelProductList.add(textfieldPrice, gbc_textfieldPrice);
		textfieldPrice.setColumns(10);
		textfieldPrice.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				textfieldPrice.selectAll();
			}
		});

		labelCurrentStock = new JLabel("Current stock:");
		GridBagConstraints gbc_labelAmount = new GridBagConstraints();
		gbc_labelAmount.insets = new Insets(0, 0, 5, 5);
		gbc_labelAmount.anchor = GridBagConstraints.WEST;
		gbc_labelAmount.gridx = 0;
		gbc_labelAmount.gridy = 4;
		panelProductList.add(labelCurrentStock, gbc_labelAmount);

		textCurrentStock = new JTextField();
		textCurrentStock.getDocument().addDocumentListener(new TextFieldDocumentListener(textCurrentStock, dataType.integer));
		textCurrentStock.getDocument().addDocumentListener(new WasteDocumentListener());
		textCurrentStock.setText(""+currentStock);
		textCurrentStock.setColumns(10);
		GridBagConstraints gbc_textfieldAmount = new GridBagConstraints();
		gbc_textfieldAmount.insets = new Insets(0, 0, 5, 0);
		gbc_textfieldAmount.fill = GridBagConstraints.BOTH;
		gbc_textfieldAmount.gridx = 1;
		gbc_textfieldAmount.gridy = 4;
		panelProductList.add(textCurrentStock, gbc_textfieldAmount);
		textCurrentStock.setColumns(10);
		textCurrentStock.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				textCurrentStock.selectAll();
			}
		});

		textfieldTotalStock = new JTextField();
		textfieldTotalStock.setEditable(false);
		textfieldTotalStock.setText(""+totalStock);
		GridBagConstraints gbc_textfieldTotalStock = new GridBagConstraints();
		gbc_textfieldTotalStock.insets = new Insets(0, 0, 5, 0);
		gbc_textfieldTotalStock.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfieldTotalStock.gridx = 1;
		gbc_textfieldTotalStock.gridy = 5;
		panelProductList.add(textfieldTotalStock, gbc_textfieldTotalStock);
		textfieldTotalStock.setColumns(10);

		labelBought = new JLabel("Bought:");
		GridBagConstraints gbc_labelBought = new GridBagConstraints();
		gbc_labelBought.anchor = GridBagConstraints.WEST;
		gbc_labelBought.insets = new Insets(0, 0, 5, 5);
		gbc_labelBought.gridx = 0;
		gbc_labelBought.gridy = 6;
		panelProductList.add(labelBought, gbc_labelBought);

		textfieldBought = new JTextField();
		textfieldBought.setEditable(false);
		textfieldBought.setText(""+bought);
		GridBagConstraints gbc_textfieldBought = new GridBagConstraints();
		gbc_textfieldBought.insets = new Insets(0, 0, 5, 0);
		gbc_textfieldBought.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfieldBought.gridx = 1;
		gbc_textfieldBought.gridy = 6;
		panelProductList.add(textfieldBought, gbc_textfieldBought);
		textfieldBought.setColumns(10);

		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 5;
		panelProductList.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{115, 115, 0};
		gbl_panel_1.rowHeights = new int[]{25, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);

		labelTotalStock = new JLabel("Total Stock:");
		labelTotalStock.setBackground(Color.WHITE);
		GridBagConstraints gbc_labelTotalStock = new GridBagConstraints();
		gbc_labelTotalStock.anchor = GridBagConstraints.WEST;
		gbc_labelTotalStock.fill = GridBagConstraints.VERTICAL;
		gbc_labelTotalStock.insets = new Insets(0, 0, 0, 5);
		gbc_labelTotalStock.gridx = 0;
		gbc_labelTotalStock.gridy = 0;
		panel_1.add(labelTotalStock, gbc_labelTotalStock);

		btnTotalStockEdit = new JButton("Edit");
		btnTotalStockEdit.addActionListener(this);
		GridBagConstraints gbc_btnTotalStockEdit = new GridBagConstraints();
		gbc_btnTotalStockEdit.anchor = GridBagConstraints.EAST;
		gbc_btnTotalStockEdit.fill = GridBagConstraints.VERTICAL;
		gbc_btnTotalStockEdit.gridx = 1;
		gbc_btnTotalStockEdit.gridy = 0;
		panel_1.add(btnTotalStockEdit, gbc_btnTotalStockEdit);
		
		labelWaste = new JLabel("Waste:");
		GridBagConstraints gbc_lblWaste = new GridBagConstraints();
		gbc_lblWaste.insets = new Insets(0, 0, 5, 5);
		gbc_lblWaste.anchor = GridBagConstraints.WEST;
		gbc_lblWaste.gridx = 0;
		gbc_lblWaste.gridy = 7;
		panelProductList.add(labelWaste, gbc_lblWaste);
		
		textFieldWaste = new JTextField();
		textFieldWaste.setEditable(false);
		textFieldWaste.setText(""+(totalStock-bought-currentStock));
		GridBagConstraints gbc_textFieldWaste = new GridBagConstraints();
		gbc_textFieldWaste.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldWaste.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldWaste.gridx = 1;
		gbc_textFieldWaste.gridy = 7;
		panelProductList.add(textFieldWaste, gbc_textFieldWaste);
		textFieldWaste.setColumns(10);
		textFieldWaste.getDocument().addDocumentListener(new WasteDocumentListener());
		
		labelEnableSoundboard = new JLabel("Sounds for Silverjuke:");
		GridBagConstraints gbc_labelEnableSoundboard = new GridBagConstraints();
		gbc_labelEnableSoundboard.anchor = GridBagConstraints.WEST;
		gbc_labelEnableSoundboard.insets = new Insets(0, 0, 5, 5);
		gbc_labelEnableSoundboard.gridx = 0;
		gbc_labelEnableSoundboard.gridy = 8;
		panelProductList.add(labelEnableSoundboard, gbc_labelEnableSoundboard);
		
		chckbxSoundboard = new JCheckBox("Check to enable");
		chckbxSoundboard.setBackground(Color.WHITE);
		GridBagConstraints gbc_chckbxSoundboard = new GridBagConstraints();
		gbc_chckbxSoundboard.anchor = GridBagConstraints.WEST;
		gbc_chckbxSoundboard.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxSoundboard.gridx = 1;
		gbc_chckbxSoundboard.gridy = 8;
		panelProductList.add(chckbxSoundboard, gbc_chckbxSoundboard);
		if(sound == 1)chckbxSoundboard.setSelected(true);
		
		lblSoundOnPurchase = new JLabel("Sound on purchase:");
		GridBagConstraints gbc_lblSoundOnPurchase = new GridBagConstraints();
		gbc_lblSoundOnPurchase.anchor = GridBagConstraints.WEST;
		gbc_lblSoundOnPurchase.insets = new Insets(0, 0, 5, 5);
		gbc_lblSoundOnPurchase.gridx = 0;
		gbc_lblSoundOnPurchase.gridy = 9;
		panelProductList.add(lblSoundOnPurchase, gbc_lblSoundOnPurchase);
		
		//Sound drop down menu (out commented due to missing getSound method)
//		comboBoxSound = new JComboBox(sounds);
//		comboBoxSound.setBackground(Color.WHITE);
//		comboBoxSound.setSelectedIndex(-1);
//		GridBagConstraints gbc_comboBox = new GridBagConstraints();
//		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
//		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
//		gbc_comboBox.gridx = 1;
//		gbc_comboBox.gridy = 9;
//		panelProductList.add(comboBoxSound, gbc_comboBox);
//		comboBoxSound.setSelectedItem(soundstring);
		
		//Sound text field
		textFieldSound = new JTextField();
		textFieldSound.setEditable(false);
		textFieldSound.setText(soundstring);
		GridBagConstraints gbc_textFieldSound = new GridBagConstraints();
		gbc_textFieldSound.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldSound.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSound.gridx = 1;
		gbc_textFieldSound.gridy = 9;
		panelProductList.add(textFieldSound, gbc_textFieldSound);
		textFieldSound.setEditable(true);
		textFieldSound.setColumns(10);
		
		lblSoundMultiplierbuy = new JLabel("Sound multiplier (buy X to play sound)");
		GridBagConstraints gbc_lblSoundMultiplierbuy = new GridBagConstraints();
		gbc_lblSoundMultiplierbuy.anchor = GridBagConstraints.WEST;
		gbc_lblSoundMultiplierbuy.insets = new Insets(0, 0, 0, 5);
		gbc_lblSoundMultiplierbuy.gridx = 0;
		gbc_lblSoundMultiplierbuy.gridy = 10;
		panelProductList.add(lblSoundMultiplierbuy, gbc_lblSoundMultiplierbuy);
		
		textFieldSoundMultiply = new JTextField();
		textFieldSoundMultiply.getDocument().addDocumentListener(new TextFieldDocumentListener(textFieldSoundMultiply, dataType.integer));
		GridBagConstraints gbc_textFieldSoundMultiply = new GridBagConstraints();
		gbc_textFieldSoundMultiply.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSoundMultiply.gridx = 1;
		gbc_textFieldSoundMultiply.gridy = 10;
		panelProductList.add(textFieldSoundMultiply, gbc_textFieldSoundMultiply);
		textFieldSoundMultiply.setColumns(10);
		textFieldSoundMultiply.setText(""+soundmod);
		//Generate top panel
		JPanel panelBottom = new JPanel();
		panel.add(panelBottom, BorderLayout.SOUTH);
		panelBottom.setBackground(Color.WHITE);
		panelBottom.setLayout(new GridLayout(1, 0, 0, 0));

		checkBoxDelete = new JCheckBox("Delete");
		checkBoxDelete.setEnabled(false);
		checkBoxDelete.setBackground(Color.WHITE);
		checkBoxDelete.addActionListener(this);
		panelBottom.add(checkBoxDelete);

		panelButtons = new JPanel();
		panelButtons.setBackground(Color.WHITE);
		FlowLayout fl_panelButtons = (FlowLayout) panelButtons.getLayout();
		fl_panelButtons.setAlignment(FlowLayout.RIGHT);
		panelBottom.add(panelButtons);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		panelButtons.add(btnCancel);

		btnSave = new JButton("Save");
		btnSave.addActionListener(this);
		panelButtons.add(btnSave);


		//Semi-transparent background for menu
		rigidArea = Box.createRigidArea(new Dimension(20, h/2-200));
		getContentPane().add(rigidArea, BorderLayout.NORTH);

		rigidArea_1 = Box.createRigidArea(new Dimension(20, h/2-200));
		getContentPane().add(rigidArea_1, BorderLayout.SOUTH);

		rigidArea_2 = Box.createRigidArea(new Dimension(w/2-300, 20));
		getContentPane().add(rigidArea_2, BorderLayout.WEST);

		rigidArea_3 = Box.createRigidArea(new Dimension(w/2-300, 20));
		getContentPane().add(rigidArea_3, BorderLayout.EAST);


		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Exit when close button clicked
		setTitle("Edit Product"); // "this" JFrame sets title
		setLocationRelativeTo(null);
		setVisible(true);   // show it
	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == btnTotalStockEdit){
			System.out.println("edit button clicked");
			if(!textfieldTotalStock.isEditable()){
				btnTotalStockEdit.setText("Lock");
				textfieldTotalStock.setEditable(true);
			}
			else{
				btnTotalStockEdit.setText("Edit");
				textfieldTotalStock.setEditable(false);
			}
		}

		if(e.getSource() == checkBoxDelete){
			if(checkBoxDelete.isSelected() == true){
				String[] options = {"Yes", "No"};
				int response = JOptionPane.showOptionDialog(getContentPane(), "You are about to delete " + textfieldName.getText() +  ". \nAre you sure you wish to continue?", "Delete Product Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, "delete");
				if (response == JOptionPane.YES_OPTION) {
					delete = true;
				}
				if (response == JOptionPane.NO_OPTION){
					checkBoxDelete.setSelected(false);
				}
			}
		}

		if(e.getSource() == btnSave){
			if (delete == true){
				saveProductInfo();
				WindowAdmin.writeToConsole("Product Edit: " + textfieldName.getText() + " was deleted");
				this.dispose();
			}
			if(delete == false){
				saveProductInfo();
				WindowAdmin.writeToConsole("Product Edit: " + textfieldName.getText() + " was edited");
				this.dispose();
			}
		}
		if(e.getSource() == btnCancel){
			WindowAdmin.writeToConsole("Product Edit: " + textfieldName.getText() + " was canceled");
			this.dispose();
		}
	}

	private void saveProductInfo() {
		System.out.println(selectedProduct);
		System.out.println(textfieldName.getText());
		System.out.println(comboBoxType.getSelectedItem().toString());
		System.out.println(Float.parseFloat(textfieldPrice.getText()));
		System.out.println(Long.parseLong(textfieldBarcode.getText()));
		System.out.println(Integer.parseInt(textfieldTotalStock.getText()));
		System.out.println(Integer.parseInt(textCurrentStock.getText()));
		System.out.println(chckbxSoundboard.isSelected());
		int sound = 0;
		if (chckbxSoundboard.isSelected()){sound = 1;}
		String soundstring = "";
//		if(comboBoxSound.getSelectedIndex() >= 0){
//			System.out.println(comboBoxSound.getSelectedItem().toString());
//			soundstring = comboBoxSound.getSelectedItem().toString();
		if(textFieldSound.getText().length() > 0){
			System.out.println(textFieldSound.getText());
			soundstring = textFieldSound.getText();
		}
		int soundmod = 0;
		if(textFieldSoundMultiply.getText().length() > 0){
			soundmod = Integer.parseInt(textFieldSoundMultiply.getText());
		}
//		Database.editProduct(selectedProduct,textfieldName.getText(),comboBoxType.getSelectedItem().toString(),Float.parseFloat(textfieldPrice.getText()),Long.parseLong(textfieldBarcode.getText()),Integer.parseInt(textfieldTotalStock.getText()),Integer.parseInt(textCurrentStock.getText()));
		Database.editProduct(selectedProduct,textfieldName.getText(),comboBoxType.getSelectedItem().toString(),Float.parseFloat(textfieldPrice.getText()),Long.parseLong(textfieldBarcode.getText()),Integer.parseInt(textfieldTotalStock.getText()),Integer.parseInt(textCurrentStock.getText()), sound, soundstring, soundmod);
		WindowAdmin.updateProductList();
		WindowAdmin.searchProduct("");
	}
	public void getProductInfo(String name){
		List<Product> products = Database.getProductByName(name);
		if (products.size() <= 0){
			System.out.println("Product not found");
		}
		else if (products.size() > 1){
			//Handle too many users found
			System.out.println("Too many found");
		}
		else{
			Product product = products.get(0);
			this.name = product.getName();
			this.type = product.getType();
			this.price = ""+product.getPrice();
			this.barcode = ""+product.getBarcode();
			this.totalStock = product.getTotalStock();
			this.currentStock = product.getCurrentStock();
			this.bought = product.getBought();
			this.sound = product.sound();
			this.soundstring = product.soundstring();
			this.soundmod = product.soundmod();
			System.out.println("Total Stock:"+product.getTotalStock());
		}
	}

	//Realtime updater for waste calculations
	class WasteDocumentListener implements DocumentListener {

		public void insertUpdate(DocumentEvent e) {
			updateLog(e);
		}
		public void removeUpdate(DocumentEvent e) {
			updateLog(e);
		}
		public void changedUpdate(DocumentEvent e) {
//			updateLog(e);
			//Plain text components don't fire these events.
		}
		public void updateLog(DocumentEvent e) {
			try{
				//This line causes exception only once due to update before initialised
				int total = Integer.parseInt(textfieldTotalStock.getText());
				int bought = Integer.parseInt(textfieldBought.getText());
				int current = Integer.parseInt(textCurrentStock.getText());
				int waste = total - bought - current;
				textFieldWaste.setText(String.valueOf(waste));
			}
			catch(Exception ex){
				System.out.println("Ignore the following stacktrace, (edit current stock)");
				ex.printStackTrace();
			}
		}
	}

	/** The entry main() method */
	public static void main(String selProduct) {
		selectedProduct = selProduct;
		// Run GUI codes in the Event-Dispatching thread for thread safety
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WindowEditProduct();  // Let the constructor do the job
			}
		});
	}
}