/*Madison Brooks and Bailey Freund
  CIS 457-20 */
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;

class udpclient{
	public static void main(String args[]){
		try{
			DatagramChannel sc = DatagramChannel.open(); 
//changing the transport layer protocal


			Console cons = System.console();
			String ipaddr = cons.readLine("Enter an IP Address: ");
			String portnumS = cons.readLine("Enter a port number: ");
			int portnum = Integer.parseInt(portnumS);

			Boolean again = true; 
			while(again){
				String m = cons.readLine("Enter your message: ");
				if(m.equals("exit")){
					again = false;				
				}
				ByteBuffer buf = ByteBuffer.wrap(m.getBytes());
				sc.send(buf,new InetSocketAddress(ipaddr,portnum)); 
			
				ByteBuffer buffer = ByteBuffer.allocate(4096);
				SocketAddress clientaddr = sc.receive(buffer); //reading the socket
				String message = new String(buffer.array());  
				//returns a socket address of whoever sent the packet
				System.out.println(message);
				m = message;
			}
		}catch(IOException e){
			System.out.println("Got an IO Exception");
			}
		}
	}