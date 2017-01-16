package system;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

class transactionWindow{
	private JTable table;
	private JFrame frame;
	public JFrame TransactionWindow(User user){
		try {
			if (user != null){
				table = new JTable(Database.buildTableModel(Database.getUserTransactions(user)));			
			}
			else{
				table = new JTable(Database.buildTableModel(Database.getAllUserTransactions()));
			}
			frame = new JFrame();
			TableColumnModel tcm = table.getColumnModel();
			tcm.getColumn(0).setPreferredWidth(50); //id
			tcm.getColumn(1).setPreferredWidth(200); //Timestamp
			tcm.getColumn(2).setPreferredWidth(100); //Username
			tcm.getColumn(3).setPreferredWidth(50); //User
			tcm.getColumn(4).setPreferredWidth(100); //Product barcode
			tcm.getColumn(5).setPreferredWidth(60); //Amount
			tcm.getColumn(6).setPreferredWidth(80); //Product name

			JScrollPane sp = new JScrollPane();
			frame.add(sp);
			sp.setViewportView(table);
			frame.setAlwaysOnTop( true );
			if (user != null){
				frame.setTitle("Transaction history for " + user.getName() +" (User ID: "+user.getID()+")");				
			}
			else {
				frame.setTitle("Transaction history");
			}
			frame.pack();
			frame.setSize(700, 500);
			frame.setLocationRelativeTo(null);
			frame.setVisible( true );
			frame.requestFocus();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return frame;
	}
}

