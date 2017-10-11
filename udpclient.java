/*Madison Brooks and Bailey Freund
  CIS 457-20 */
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;

class udpclient{

	public udpclient(){}
	
		  /*
			Checks if valid ip
			@return True if string is a valid ip, else false
		  */
		  public static boolean isValidIP(String ip){
			  try{
				  if(ip == null || ip.isEmpty()){
				  	  return false;
				  }
	
				  String[] ipArr = ip.split("\\.");
				  
				  if( ipArr.length != 4 ){
				      return false;
				  }
	
				  for(String numStr : ipArr){
				      int num = Integer.parseInt(numStr);
				      if(num < 0 || num > 255){
					      return false;
				      }
				  }
	
				  if(ip.endsWith(".")){
				      return false;
				  }
	
				  return true;
	
			  } catch(NumberFormatException e){
				  return false; //means it wasn't a number
			  }
		  }

	public static void main(String args[]){
		try{
			DatagramChannel dc = DatagramChannel.open(); 
			Console cons = System.console();

			udpclient uc = new udpclient();
      	    String ipStr = "127.0.0.1"; // Default ip address
      	    boolean valid = false;
      	    while(valid == false){
          		ipStr = cons.readLine("Enter target IP address: ");
              	valid = uc.isValidIP(ipStr.trim());
          		if(!valid){
          		    System.out.println("IP address " + ipStr + " is not valid.");
          		    continue;
          		} else{
          		    valid = true;
          		}
      	    }


      	    String portStr = cons.readLine("Enter target port number: ");
	/*Validating the port number*/
              Boolean portNotValid = false; //checking to see if the port is valid
		      int portInt=9876; //declaring portInt so the code works
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
			System.out.println("Now using port number " + portInt); //end of checking port number

			int portnum = Integer.parseInt(portStr);

			Boolean again = true; 
			while(again){
					String m = cons.readLine("Enter a filename to transfer: ");
					if(m.equals("exit")){
						again = false;				
					}	
				ByteBuffer buf = ByteBuffer.wrap(m.getBytes());
				dc.send(buf,new InetSocketAddress(ipStr,portnum)); //sending file name
				//remember send only sends one at a time so this line above is fine
				
				
				ByteBuffer buffer = ByteBuffer.allocate(1024); //our buffer can only be 1024
				
				File newfile = new File(m); //make an empty file with that file name
				FileOutputStream outstream = new FileOutputStream(newfile);
          		FileChannel fc = outstream.getChannel();
          		
				dc.receive(buffer); // this recive is just getting the file size and nothing else
				String fileSizeStr = new String(buffer.array());
				System.out.println(fileSizeStr);
				
				ByteBuffer buff = ByteBuffer.allocate(1024); 
/*				
	below is the code for sending a packet of information

						buff.flip();
		      		    fc.write(buff);
		      		    buff.compact();
						dc.receive(buff);
*/

			}
		}catch(IOException e){
			System.out.println("Got an IO Exception");
			}
		}
		
	}
	
	
	
	
	class slidingWindow(){
	
	public static int getNumber(){
		//will return the number of which packet it is
	}
	
	
	
	/*
	@TODO: for the sliding window:
			-get packet number
			-keep track of the last packet number we recived IN ORDER
			-send an aknowlagement for that last packet
			-keep all packets we recived in an array
			-ask for missing packets
			-fill in the spaces where the missing packets were but also utilize the ones we already have
	*/
	
	
	
	}
