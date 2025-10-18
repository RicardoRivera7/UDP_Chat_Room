# UDP_Chat_Room
Utilizes the UDP network protocol to create a chat room with a client and a server. Must run two different versions of the client class to have 2 users to test, but functions as a normal chat room with user inputs for chat names


<h1>Chat Client</h1>


```
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class ChatClient
{
	private final static int PORT = 5000;
	
public static void main(String args[]) throws IOException 
{ 
	
	 Scanner sc = new Scanner(System.in); 
	
	 	DatagramSocket socket = new DatagramSocket(0);
        DatagramSocket ds = new DatagramSocket(); 
        DatagramSocket s = new DatagramSocket();
        InetAddress ip = InetAddress.getLocalHost(); 
        byte buf[] = null; 
        System.out.print("Enter User name: ");
        String User = sc.nextLine();
        byte[] UserBuffer = User.getBytes();
        DatagramPacket request = new DatagramPacket(UserBuffer, UserBuffer.length, ip, PORT);
		socket.send(request);
		
       
 
        while (true) 
        { 
              
            String inp = sc.nextLine(); 
            buf = new byte[65535]; 
            
            String temp = User + ": " + inp;
            
            buf = temp.getBytes(); 
            
            DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, 1234);  
            ds.send(DpSend); 
  
            if (inp.equals("Quit")) 
            {
            	String User_Quit = User + " has left the chat room";
                s.setBroadcast(true);
                DatagramPacket dn = new DatagramPacket(User_Quit.getBytes(), User_Quit.length(), new InetSocketAddress("255.255.255.255", 5000));
                s.send(dn);
            	break; 
            }
                
  
            buf = new byte[65535]; 
            DatagramPacket DpReceive = new DatagramPacket(buf, buf.length); 
            ds.receive(DpReceive); 
  
            System.out.println(new String(buf,0,buf.length));
          
            s.setBroadcast(true);
            DatagramPacket de = new DatagramPacket(new byte[1024], 1024);
            s.receive(de);
        } 
    } 
}

```

<br/>
<br/>
<br/>

<h1>Chat Server</h1>

```

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

```
