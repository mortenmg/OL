package system;
import java.io.*;
import java.net.*;


public class ServerThread extends Thread  {
	
	public Server parent;
    private PrintWriter pw;
    private BufferedReader br;
    private Socket sock;
	private boolean done;
    
    
	
    public ServerThread(Server p, Socket s) {
    	this.parent = p;
        sock = s;
        System.out.println("Your socket is: "+sock);
        try {
            sock.setSoTimeout(60000);
        } catch (SocketException ex) {
        }

        try {
            pw = new PrintWriter(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException ex) {
        }
    }
    
    public void run() {
    	System.out.println("server thread have started");
        String s = "";

        pw.println("You have connected to IM Project 0.1");
        pw.flush();

        pw.println("Welcome");
        pw.flush();
        
    	while (!done){
    		 try {
                 s = br.readLine();
                 if (s.equals(null)){
                	 try {
                         System.out.println("Unresponsive client");
                         sock.shutdownOutput();
                         sock.shutdownInput();
                         sock.close();
                         this.done = true;
                     } 
                     catch (Exception ex1) { 
                    	 
                     }
                	 
                 }
                 else{
                     System.out.println(s);
                     pw.println("You send: "+s);
                     pw.flush();
                 }

             } 
    		 catch (Exception ex) {
                 try {
                     System.out.println("Unresponsive client");
                     sock.shutdownOutput();
                     sock.shutdownInput();
                     sock.close();
                     this.done = true;
                 } 
                 catch (Exception ex1) { 
                	 
                 }
             }
 
    	}
    	System.out.println("While loop have ended: Thread terminated");
    }
}