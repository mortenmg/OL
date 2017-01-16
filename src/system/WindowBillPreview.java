package system;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

class billPreviewWindow{
	private JTable table;
	private JFrame frame;
	public JFrame BillPreview(List<String> ranks, double fee){
		try {
			table = new JTable(Database.getBillPreview(Database.getBill(), ranks, fee));
			frame = new JFrame();
			TableColumnModel tcm = table.getColumnModel();
			tcm.getColumn(0).setPreferredWidth(60); //Userid
			tcm.getColumn(1).setPreferredWidth(150); //User name
			tcm.getColumn(2).setPreferredWidth(100); //Price
//			tcm.getColumn(3).setPreferredWidth(100); //Waste
//			tcm.getColumn(4).setPreferredWidth(100); //Total Price
			JScrollPane sp = new JScrollPane();
			frame.add(sp);
			sp.setViewportView(table);
			frame.setAlwaysOnTop( true );
			frame.setTitle("Bill preview");
			frame.pack();
			frame.setSize(700, 500);
			frame.setLocationRelativeTo(null);
			frame.setVisible( true );
			frame.toFront();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return frame;
	}
}

