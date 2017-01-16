package system;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class SoundBoard implements Runnable{
	private String sample;
	private Thread t;

	SoundBoard (String input){
		sample = input;
	}
	public void run(){
		try {
			play(sample);
		} catch (Exception e) {
			WindowAdmin.writeToConsole("Soundboard: Sound could not play");
			e.printStackTrace();
		}
	}
	public void start (String string)
	{
		if (t == null)
		{
			t = new Thread(this);
			t.start ();
		}
	}

	public static void play(String sample) throws Exception {
		DatagramSocket socket = new DatagramSocket();
		byte[] out = (sample).getBytes();
		InetAddress IP = InetAddress.getByName(prefs.IP);
		DatagramPacket p = new DatagramPacket(out, out.length, IP, Integer.parseInt(prefs.port));

		System.out.println("Sending packet to "+prefs.IP+":"+prefs.port+" in order to play: "+sample);
		socket.send(p);
		socket.close();
	}
}