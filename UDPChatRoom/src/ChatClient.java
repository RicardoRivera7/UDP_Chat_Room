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