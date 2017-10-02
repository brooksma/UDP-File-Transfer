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
              String portnumS = cons.readLine("Enter a port number: ");
              int portnum = Integer.parseInt(portnumS);
              DatagramChannel c = DatagramChannel.open();
              c.bind(new InetSocketAddress(portnum));
              while(true){
                  ByteBuffer buffer = ByteBuffer.allocate(4096);
                  SocketAddress clientaddr = c.receive(buffer); //reading the socket
                  String message = new String(buffer.array());  
  //returns a socket address of whoever sent the packet
                  System.out.println(message);
                  buffer.flip();
                  c.send(buffer,clientaddr); //will this work
              }
          }catch(IOException e){
              System.out.println("Got an IO Exception");
          }
  
      }
  }