package system;
import java.net.Socket;

public class Client {
	public String userName;
	public Socket socket;

	public Client(String userName,Socket userSocket)
	{
		this.userName=userName;
		this.socket=userSocket;
	}
}

