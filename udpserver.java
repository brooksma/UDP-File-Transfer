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
            	fileStr = fileStr.trim(); // have to trim leading and trailing spaces due to making string from oversized buffer
            	File f = new File(fileStr);
            	String filename = f.getName();
            	boolean fileExists = f.exists();
            	if(fileExists){
                	System.out.println("File exists");
                
                	long filesize = f.length();
                	String sizeStr = Long.toString(filesize);
                	ByteBuffer sizebuffer = ByteBuffer.wrap(sizeStr.getBytes());
                	c.send(sizebuffer,clientaddr); 
                	//this send matches up with the clients recive so that the client has the size of the file.
                	
                	FileInputStream instream = new FileInputStream(f);
                	FileChannel fc = instream.getChannel();
                	ByteBuffer buf = ByteBuffer.allocate(1024);
                	int bytesread = fc.read(buf);
                	//TODO: making sure a number is being send with the packet so we know which packet to resend if need be ect.
                	
                	int currWindow = 0;
                	
                	while(bytesread != -1){
		            	currWindow=0; //start all over again with the window system
						while(currWindow<6){
							//@TODO knowing what packet number we just recived
							buf.flip();
				  		    c.send(buf, clientaddr);
				  		    buf.compact();
							bytesread = fc.read(buf);
							//@TODO: add awknowlagements in here (can we shift the window or do we wait? resend?)
							currWindow++;
							//endSize = newfile.length();
							//int endSize = Integer.parseInt(endSize);
						}
                	}
                }
             }
        }catch(IOException e){
            System.out.println("Got an IO Exception");
        }

    }
}
