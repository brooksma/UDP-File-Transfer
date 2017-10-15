/*Madison Brooks and Bailey Freund
October 2, 2017
CIS 457-20 */
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.io.FileOutputStream;
import java.io.File;

class udpserver{
    
    public udpserver(){} //empty constructor

    public static void main(String args[]){
        
        try{
            udpserver server = new udpserver();
            System.out.println("Below is a list of available files: ");
            File curDir = new File(".");
            File[] filesList = curDir.listFiles();
                for(File f : filesList){
                        if(f.isFile()){
                                System.out.println("\t"+f.getName());
                        }
                }


            Console cons = System.console();
            Boolean portNotValid = false;
            String portStr = cons.readLine("Enter port number to listen on: ");
            int portInt = 9876; //default port number - will be changed to whatever user inputs
            if(portStr.matches("^[0-9]*$")){
                    portInt = Integer.parseInt(portStr);
                    if(portInt<1024 || 49151<portInt){
                		portNotValid = true;
                	}
            }else{
                    portNotValid = true;
                }
            while(portNotValid){
                System.out.println("INVALID PORT NUMBER!");
                portStr = cons.readLine("Enter port number to listen on: ");
                if(portStr.matches("^[0-9]*$")){
                	portInt = Integer.parseInt(portStr);
                    if(1024<portInt && portInt<49151){
                    	portNotValid = false;
                    }
                }else{
                	portNotValid = true;
                  }
            }
            System.out.println("Now using port number "+portInt);


            DatagramChannel c = DatagramChannel.open();
            c.bind(new InetSocketAddress(portInt));

            
            while(true){
            	ByteBuffer buffer = ByteBuffer.allocate(1024); //our buffer can only be 1024
            	SocketAddress clientaddr = c.receive(buffer); //reading in the file name b/c its the first thing being sent
            	System.out.println("A client is requesting information");
            	
            	String fileStr = new String(buffer.array());
            	fileStr = fileStr.trim();
            	File f = new File(fileStr);
            	String filename = f.getName();
            	boolean fileExists = f.exists();
            	
            	if(fileExists){
                	System.out.println("File exists");
                	
                	FileInputStream instream = new FileInputStream(f);
                	FileChannel fc = instream.getChannel();
                	ByteBuffer buf = ByteBuffer.allocate(1024);
                	int bytesread = fc.read(buf);
                	
	/*Code below is for sending a file
							buf.flip();
				  		    c.send(buf, clientaddr);
				  		    buf.compact();
							bytesread = fc.read(buf);
							currWindow++;
						*/
                	
                }
             }
        }catch(IOException e){
            System.out.println("Got an IO Exception");
        }

    }
}




	class slidingWindow(){
	
		public static byte[] getPNumBytes(int number){
			//will return the number of which packet it is in 4 bytes
			byte[] pnum = new byte[4];
			pnum = number.toByteArray;
			return pnum;
		}
		public static boolean isAknowlaged(){
			//will return whether or not the package was aknowlaged by the client.
		}
	
	
	/*
	@TODO: for the sliding window:
			-send packet number with packet
			-for simplicity just number packets 1 to 4billion (no need to wrap around and reuse numbers)
			-only send 5 packets at a time
			-recive aknowlagements for all packets and keep the last one that is IN ORDER 
				for example if we get aknowlagements 1 2 4 5 then the variable last aknowlaged would be 2
			-HAVE variable last packet recived (as decribed above)
			-keep all packets we sent in an array (the contents of the buffers)
			-resend missing packets
				also off of the example above (aknowlagements for 1 2 4 5) MOVE the window by sending packets 6 and 7
	*/
	
	}
	
	
	
	
	
