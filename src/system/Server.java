package system;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements ConstAndUtill{
	private static Vector<Socket> socketlist;
	private static ArrayList <Client> userlist = new ArrayList<Client>();
	private ServerSocket ssocket;
	public static Socket socket;
	private boolean done = false;
	public Service service;

	public Server() throws UnknownHostException{
		System.out.println("Initializing");
		socketlist = new Vector<Socket> (BACKLOG);
		try {
			ssocket = new ServerSocket (SERVER_PORT);
		}
		catch (Exception ex){
		}
		start();
	}

	public void start() throws UnknownHostException{
		Socket userSocket = null;
		Client client = null;

		System.out.println("Server successfully started at "
				+InetAddress.getLocalHost().toString()
				+" port "+SERVER_PORT);

		while(!done){
			try{
				userSocket = ssocket.accept();
				if (userSocket != null){
					synchronized(socketlist){
						socketlist.addElement(userSocket);
					}

					System.out.println("User socket = "+userSocket);
					BufferedReader br = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
					boolean login = false;
					while (login == false){
						String loginstring = br.readLine();
						if (!loginstring.equals(null)){
							if (loginstring.startsWith("1:")){
								int ctrlchar = loginstring.indexOf(":");
								String userName = loginstring.substring(ctrlchar+1);
								client = new Client(userName, userSocket);
								userlist.add(client);
								System.out.println(userName+" have logged in.");
								writeToClients(PUBLIC_MESSAGE+":"+userName+" have logged in.");
								writeToClients(USERS+":"+getUserList());
								writeToUser("Welcome To Epyks",client);	
								login = true;
							}
						}
					}
					service = new Service(userSocket,client);
				}
			}

			//if the socket fails
			catch (Exception ex){
				try {
					userSocket.close();
				}
				catch (Exception ex1){
					System.out.println("ERROR TERMINATING SOCKET");
				}	
			}	
		}
	}
	public static synchronized void ProcessClientMessage (String s, Client user){
		if (!s.equals(null)){


			System.out.println("Client input: "+s);
			int svrchar = s.indexOf(":");
			String cmd = s.substring(0,svrchar);
			String str = s.substring(svrchar+1);
			int svrcmd = Integer.parseInt(cmd);

			switch(svrcmd){

			case CLIENT_LOGOUT:
				removeClient(user);
				break;

			case PUBLIC_MESSAGE:
				if (str.startsWith("/w")){
					String target = "";
					int wsplit = str.indexOf(" ");
					str = str.substring(wsplit+1);
					int namesplit = str.indexOf(" ");
					target = str.substring(0,namesplit);
					System.out.println("PM Target: "+target);
					str = str.substring(namesplit+1);
					System.out.println("PM String: "+str);
					pmToUser(str,user,target);
				}
				else{
					writeToClients(PUBLIC_MESSAGE+":"+user.userName+": "+str);
				}

				break;

			case USERS:
				writeToClients(USERS+":"+getUserList());
				break;
			}
		}
	}

	public static synchronized void writeToClients(String s){
		PrintWriter pw;
		for (int i = 0; i < userlist.size(); i++){
			try {
				pw = new PrintWriter (userlist.get(i).socket.getOutputStream());
				pw.println(s);
				pw.flush();	
			}
			catch (Exception e){
				System.out.println("Output exception writeToClient() " +e);
			}
		}
	}

	public static synchronized void writeToUser (String s, Client user){
		PrintWriter pw;
		try {
			pw = new PrintWriter (user.socket.getOutputStream());
			pw.println(PRIVATE_MESSAGE+": "+s);
			pw.flush();
		} catch (Exception e) {
			System.out.println("writeToUser() exception "+e);
		}
	}

	public static synchronized void pmToUser (String s, Client user, String target){
		PrintWriter pw;
		boolean found = false;
		int i = 0;
		while (found == false && i < userlist.size()-1){
			System.out.println(userlist.size());
			if (userlist.get(i).userName.equals(target)){
				try {
					pw = new PrintWriter (userlist.get(i).socket.getOutputStream());
					pw.println(PRIVATE_MESSAGE+":"+"PM from "+user.userName+": "+s);
					pw.flush();
					if (user != null){
						pw = new PrintWriter (user.socket.getOutputStream());
						pw.println(PRIVATE_MESSAGE+":"+"PM to "+target+": "+s);
						pw.flush();
					}
					found = true;
				} catch (IOException e) {
					System.out.println("Private Message Exception " +e);
				}
			}
			if (userlist.size() > i-1){
				i++;
			}
		}
		if (found == false){
			try {
				System.out.println("Target dosen't exist");
				pw = new PrintWriter (user.socket.getOutputStream());
				pw.println(PRIVATE_MESSAGE+":"+" Failed to find target");
				pw.flush();
				found = true;
			} catch (IOException e) {
				System.out.println("Something went wrong with a PM");
			}
		}

	}

	public static String getUserList (){
		String users = "";
		for (int i = 0; i < userlist.size(); i++){
			users = users + userlist.get(i).userName;
			if (i < userlist.size()-1){
				users = users+",";
			}
		}
		System.out.println("Users: "+users);
		return users;
	}

	public static void removeClient(Client user){
		if (user != null){
			try {
				socketlist.remove(user.socket);
				userlist.remove(user);
				user.socket.close();
				writeToClients(PUBLIC_MESSAGE+":"+user.userName+" have logged out");
				System.out.println(user.userName+" have logged out");
				writeToClients(USERS+":"+getUserList());
			} catch (Exception e){

			}
		}
	}


	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		Server chatserver = new Server();
	}
}

class Service implements Runnable, ConstAndUtill
{
	private BufferedReader br;
	private Socket socket;
	private boolean done=false;
	private Thread thread;
	private Client user;

	//Starts a new (multi)thread for each client.
	public Service(Socket usrsocket,Client user)
	{
		try	{
			this.socket = usrsocket;
			this.user = user;
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			thread=new Thread(this,"SERVICE");
			thread.start();
		}
		catch(Exception e){
			System.out.println("service constructor: "+e);
		}
	}
	
	//Client input listener
	public void run()
	{
		System.out.println("Client created");
		String message;
		while(!done)
		{
			try	{

				message = br.readLine();
				Server.ProcessClientMessage(message, user);
			}
			catch(Exception e) {
				done = true;
				try	{
					System.out.println("Closeing client socket");
					Server.removeClient(user);
					socket.close();
				} catch(Exception se) {
					System.out.println("ERROR CLOSING SOCKET "+se);
				}
				//System.out.println("SERVICE THREAD EXCEPTION"+e);
			}
		}//END WHILE
	}
}
