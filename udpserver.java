/*Madison Brooks and Bailey Freund
  October 2, 2017
  CIS 457-20 */
  import java.io.*;
  import java.net.*;
  import java.nio.*;
  import java.nio.channels.*;
  
  class udpserver{
      public static void main(String args[]){
          try{
              Console cons = System.console();
              
              //Madison New Code
              String portStr = cons.readLine("Enter a port number: ");
              //String portStr = "9876"; //defult
              int portnum=9876; //defult
              Boolean portNotValid = false;
              if(portStr.matches("^[0-9]*$")){
              		portnum = Integer.parseInt(portStr);
              		if(portnum<1024 || 49151<portnum){
			  			portNotValid = true;
					}
            	}else{
            		portNotValid = true;
            	}
				while(portNotValid){
					System.out.println("INVALID PORT NUMBER!");
					portStr = cons.readLine("Enter port number to listen on: ");
					if(portStr.matches("^[0-9]*$")){
            			 portnum = Integer.parseInt(portStr);
            			if(1024<portnum && portnum<49151){
							portNotValid = false;
						}
            		}else{
            				portNotValid = true;
            		}
				}
				System.out.println("Now using port number "+portnum);
              //end Madison new code
              DatagramChannel c = DatagramChannel.open();
              c.bind(new InetSocketAddress(portnum));
              while(true){
                  ByteBuffer buffer = ByteBuffer.allocate(1024); //1024 is the most amount of data we can send at a time
                  SocketAddress clientaddr = c.receive(buffer); //reading the socket
                  String message = new String(buffer.array());  
  //returns a socket address of whoever sent the packet
                  System.out.println(message);
                  buffer.flip();
                  c.send(buffer,clientaddr); //will this work = heck yeah!
              }
          }catch(IOException e){
              System.out.println("Got an IO Exception");
          }
  
      }
}

/*
Specs for the project so we don't have to keep looking at the website:

-UDP (datagram) sockets must be used.

-The data in each packet (not including any header information you add) must be 1024 bytes or less.

-You must implement a sliding window of 5 packets for your file transfer. Stop-and-wait protocols will not receive full credit. Specifically,you must not wait for all acks of a window to be recieved before moving the window and sending new data. (AKA make it slide)

-The file transfer must be reliable (recover from any packet loss, packet corruption, packet duplication, and packet reordering).

-The client and server should each print to the screen each time they send or recieve a packet.

- Do not load the entire file you will be sending into memory at once. Instead, only read as far as you are currently sending. So, when sending the first 5 packets, only read the first 5 packets worth of data from the file. Read more of the file when you are ready to send it. Keep all data in memory until it is acknowledged. If you are resending data, you must not re-read it from the file




*/














