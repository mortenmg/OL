package system;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class LaunchOptions extends JFrame{

	
		//Create the JFrame
		public LaunchOptions(){
			
		}
		
		/** The entry main() method */
		public static void main(String[] args) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new WindowUserLogin();  // Let the constructor do the job
				}
			});
		}
}


