import java.net.*;
import java.util.StringTokenizer;
import java.io.*;
public class ChatServer{
	private final static int PORT = 5000;
		public static void main(String[] args) throws IOException 
	    { 
			DatagramSocket socket = new DatagramSocket(PORT);
        	DatagramSocket ds = new DatagramSocket(1234);
        	DatagramSocket s = new DatagramSocket();
	        while (true) 
	        { 
	        	DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
				socket.receive(request);
				byte[] requestBuffer = request.getData();
				String User = new String(requestBuffer);
				String New_User = User + " has joined the chat room";
			    s.setBroadcast(true);
			    DatagramPacket dp = new DatagramPacket(New_User.getBytes(), New_User.length(), new InetSocketAddress("255.255.255.255", 5000));
			    socket.send(New_User);
				
		        byte[] buf = null; 
		        DatagramPacket DpReceive = null; 
		        DatagramPacket DpSend = null; 
	            buf = new byte[65535]; 
	  
	            DpReceive = new DatagramPacket(buf, buf.length); 
	  
	            ds.receive(DpReceive); 
	  
	            String inp = new String(buf, 0, buf.length); 
	  
	            //To remove extra spaces. 
	            inp=inp.trim(); 
	            System.out.println("Chat message Received- " + inp); 
	  
	            if (inp.equals("Quit")) 
	            { 
	                System.out.println("Client sent Quit.....EXITING system"); 
	                break;
	            } 
	            
	
	            System.out.println("Sending the message...");  
	            buf = inp.getBytes();  
	            int port = DpReceive.getPort(); 
	  
	            DpSend = new DatagramPacket(buf, buf.length, 
	                          InetAddress.getLocalHost(), port); 
	            ds.send(DpSend); 
	        } 
	    } 
	} 