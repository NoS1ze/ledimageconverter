package co.uk.zloezh.led.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JTextArea;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.uk.zloezh.led.ArduinoLEDTool;
import co.uk.zloezh.led.PropertiesObject;
import co.uk.zloezh.led.object.LEDFrame;
import co.uk.zloezh.led.object.LEDScreen;


public class HTTPUtils {
	
	protected static final Logger logger = LogManager.getLogger();
	protected static final PropertiesObject properties = PropertiesObject.getInstance();
	
	public static void sendFrame( LEDFrame frame, LEDScreen screen) {
		try {
			 
	         logger.info("Connecting to: " + screen.iPAdress);
			 URL url = new URL("http://" + screen.iPAdress);

	         // Create connection object
	         HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	         // Set the request method to POST
	         connection.setRequestMethod("POST");

	         // Enable input/output streams
	         connection.setDoInput(true);
	         connection.setDoOutput(true);

	         // Set request parameters
	         String param1 = "";
	         
	         long[] array = frame.getHexArray();
	         for (int i = 0; i < array.length ; i++) {
	         //for (int i = 0; i < 4; i++) {
	        	// if(array[i] != 0) {
	        		 param1 += "!" + i + "!" + String.format("0x%08X", array[i]);
	        	// }
	        	 
	         }
	         param1 += "!";

	         String params = "rst=1&image=" + param1 + "&passiveMode=1";
	         
	        // connection.addRequestProperty("Referrer", params);

	         
	         logger.debug("Sending Params:" + params);
	         // Write parameters to the connection
	         OutputStream outputStream = connection.getOutputStream();
	         outputStream.write(params.getBytes(StandardCharsets.UTF_8));
	         outputStream.write("\n".getBytes(StandardCharsets.UTF_8));
	         
	         boolean set = true;
	       //  while(set) {
	        	 logger.info(".");
	//	         outputStream.write(params.getBytes(StandardCharsets.UTF_8));
		//         outputStream.write("\n".getBytes(StandardCharsets.UTF_8));
		        // outputStream.flush();
		        // Thread.sleep(3000);
	        // }

	         
	         
	         outputStream.flush();
	         outputStream.close();
	         
	         


	         // Get the response from the server
	         BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	         StringBuilder response = new StringBuilder();
	         String line;

	         while ((line = reader.readLine()) != null) {
	             response.append(line);
	         }
	         reader.close();

	         // Print the response
	         logger.info("Response: " + response.toString());

	         // Disconnect the connection
	         connection.disconnect();
			}catch(IOException e) {
				e.printStackTrace();
			}
	            
		
	}
	
	public static String sendCommandViaHttp( String command, LEDScreen screen) {
		try {
			 
	         logger.info("Connecting to: " + screen.iPAdress);
			 URL url = new URL("http://" + screen.iPAdress);

	         // Create connection object
	         HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	         connection.setConnectTimeout(350); //set timeout
	         // Set the request method to POST
	         connection.setRequestMethod("POST");

	         // Enable input/output streams
	         connection.setDoInput(true);
	         connection.setDoOutput(true);

	         String params = command;
	         
	        // connection.addRequestProperty("Referrer", params);

	         
	         logger.info("Sending Params:" + params);
	         // Write parameters to the connection
	         OutputStream outputStream = connection.getOutputStream();
	         outputStream.write(params.getBytes(StandardCharsets.UTF_8));
	         outputStream.write("\n".getBytes(StandardCharsets.UTF_8));     
	         outputStream.flush();
	         outputStream.close();
	         
	         


	         // Get the response from the server
	         BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	         StringBuilder response = new StringBuilder();
	         String line;

	         while ((line = reader.readLine()) != null) {
	             response.append(line);
	         }
	         reader.close();
	         
	         String responseParams =  response.toString();
	         // Print the response


	         // Disconnect the connection
	         connection.disconnect();
	         logger.debug("Response: " + responseParams);
	         return responseParams;
			}catch(IOException e) {
				e.printStackTrace();
			}
	            
		return "";
	}
	
	
	public static void SendFrameViaUDP(LEDFrame frame, LEDScreen screen) {
		
	        // Define the target IP address and port number
	      //  String ipAddress = "192.168.1.230"; // Change this to the IP address of the receiver
		    String ipAddress = screen.getiPAdress(); // Change this to the IP address of the receiver
		    
		    int port = Integer.valueOf(properties.getProperty("screen.port.udp")); // Change this to the port number of the receiver
	        
	        // Message to be sent
	     // Set request parameters
	         String pixels = "rst=1&passiveMode=1&image=!";
	         
	         try {
	        	 
	        	 DatagramSocket socket = new DatagramSocket();
	        	 socket.setSoTimeout(400);
	         long[] array = frame.getHexArray();
	         for (int i = 0; i < array.length ; i++) {
	         //for (int i = 0; i < 500; i++) {
	        	// if(array[i] != 0) {
	        	 pixels += i + "!" + String.format("0x%08X", array[i]) + "!";
	        	// }
	        		 
	        		 if ((i % 250 == 0)&&(i != 0)) {
	     	            // Create a UDP socket
	        			
	     	           
	     	            
	     	            // Convert the message to bytes
	     	            byte[] sendData = pixels.getBytes();
	     	            
	     	            // Create a DatagramPacket to send the data
	     	            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(ipAddress), port);
	     	           // Thread.sleep(55);
	     	            // Send the packet
	     	            socket.send(sendPacket);
	     	            
	     	           logger.debug("Message sent: " + i + " "+ pixels);
	     	            
	     	            byte[] receiveData = new byte[1024];
	     	            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	     	            socket.receive(receivePacket);
	     	            String modifiedSentence = new String(receivePacket.getData());
	     	           logger.debug("FROM SERVER:" + modifiedSentence);
	        			
	     	            pixels = "passiveMode=1&image=!";
	        		 }
	        	 
	         }
	         
	         if(!pixels.equals("passiveMode=1&image=!")) {
	        	 byte[] sendData = pixels.getBytes();
  	            
  	            // Create a DatagramPacket to send the data
  	            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(ipAddress), port);
  	          //Thread.sleep(55);
  	            // Send the packet
  	            socket.send(sendPacket);
  	            
  	          logger.debug("Message sent: " + pixels);
  	       
  	            byte[] receiveData = new byte[1024];
  	            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
  	            socket.receive(receivePacket);
  	            String modifiedSentence = new String(receivePacket.getData());
  	          logger.debug("FROM SERVER:"  + modifiedSentence);
	         }
	         //param1 += "!";

	        // String params = "rst=1&image=" + param1 + "&passiveMode=1\n";
	         String params = "passiveMode=1&rndr=1";
	         
	         byte[] sendData = params.getBytes();
	            
	            // Create a DatagramPacket to send the data
	            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(ipAddress), port);
	           // Thread.sleep(55);
	            // Send the packet
	            socket.send(sendPacket);
	            
	            logger.debug("Message sent: " + params);
	            
	            byte[] receiveData = new byte[1024];
	            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	            socket.receive(receivePacket);
	            String modifiedSentence = new String(receivePacket.getData());
	            logger.debug("FROM SERVER:" + modifiedSentence);

	            // Close the socket
	            socket.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	public static void SendCommandViaUDP(LEDFrame frame, LEDScreen screen) {
		
        // Define the target IP address and port number
		String ipAddress = screen.getiPAdress();
		int port = Integer.valueOf(properties.getProperty("screen.port.udp"));
        
        // Message to be sent
     // Set request parameters
         String pixels = "rst=1&passiveMode=1&image=!";
         
         try {
        	 
        	 DatagramSocket socket = new DatagramSocket();
        	 socket.setSoTimeout(400);
         long[] array = frame.getHexArray();
         for (int i = 0; i < array.length ; i++) {
         //for (int i = 0; i < 500; i++) {
        	// if(array[i] != 0) {
        	 pixels += i + "!" + String.format("0x%08X", array[i]) + "!";
        	// }
        		 
        		 if ((i % 250 == 0)&&(i != 0)) {
     	            // Create a UDP socket
        			
     	           
     	            
     	            // Convert the message to bytes
     	            byte[] sendData = pixels.getBytes();
     	            
     	            // Create a DatagramPacket to send the data
     	            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(ipAddress), port);
     	           // Thread.sleep(55);
     	            // Send the packet
     	            socket.send(sendPacket);
     	            
     	           logger.debug("Message sent: " + i + " "+ pixels);
     	            
     	            byte[] receiveData = new byte[1024];
     	            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
     	            socket.receive(receivePacket);
     	            String modifiedSentence = new String(receivePacket.getData());
     	           logger.debug("FROM SERVER:" + modifiedSentence);
        			
     	            pixels = "passiveMode=1&image=!";
        		 }
        	 
         }
         
         if(!pixels.equals("passiveMode=1&image=!")) {
        	 byte[] sendData = pixels.getBytes();
	            
	            // Create a DatagramPacket to send the data
	            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(ipAddress), port);
	          //Thread.sleep(55);
	            // Send the packet
	            socket.send(sendPacket);
	            
	          logger.debug("Message sent: " + pixels);
	       
	            byte[] receiveData = new byte[1024];
	            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	            socket.receive(receivePacket);
	            String modifiedSentence = new String(receivePacket.getData());
	          logger.debug("FROM SERVER:"  + modifiedSentence);
         }
         //param1 += "!";

        // String params = "rst=1&image=" + param1 + "&passiveMode=1\n";
         String params = "passiveMode=1&rndr=1";
         
         byte[] sendData = params.getBytes();
            
            // Create a DatagramPacket to send the data
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(ipAddress), port);
           // Thread.sleep(55);
            // Send the packet
            socket.send(sendPacket);
            
            logger.debug("Message sent: " + params);
            
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData());
            logger.debug("FROM SERVER:" + modifiedSentence);

            // Close the socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
}
	
	public static void SendFrameViaUDPOLD(LEDFrame frame, LEDScreen screen) {
		
        // Define the target IP address and port number
		String ipAddress = screen.getiPAdress();
		int port = Integer.valueOf(properties.getProperty("screen.port.udp"));
        
        // Message to be sent
     // Set request parameters
         String pixels = "!";
         
         try {
        	 
        	 DatagramSocket socket = new DatagramSocket();
         long[] array = frame.getHexArray();
         for (int i = 0; i < array.length ; i++) {
         //for (int i = 0; i < 500; i++) {
        	// if(array[i] != 0) {
        	 pixels += i + "!" + String.format("0x%08X", array[i]) + "!";
        	// }
        		 
        		 if ((i % 250 == 0)&&(i != 0)) {
     	            // Create a UDP socket
        			
     	           
     	            
     	            // Convert the message to bytes
     	            byte[] sendData = pixels.getBytes();
     	            
     	            // Create a DatagramPacket to send the data
     	            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(ipAddress), port);
     	            //Thread.sleep(55);
     	            // Send the packet
     	            socket.send(sendPacket);
     	            
     	           logger.debug("Message sent: " + i + " "+ pixels);
     	            
     	            byte[] receiveData = new byte[1024];
     	         //   DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
     	         //   socket.receive(receivePacket);
     	         //   String modifiedSentence = new String(receivePacket.getData());
     	         //  logger.debug("FROM SERVER:" + modifiedSentence);
        			
     	            pixels = "!";
        		 }
        	 
         }
         
         if(!pixels.equals("!")) {
        	 byte[] sendData = pixels.getBytes();
	            
	            // Create a DatagramPacket to send the data
	            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(ipAddress), port);
	         // Thread.sleep(55);
	            // Send the packet
	            socket.send(sendPacket);
	            
	          logger.debug("Message sent: " + pixels);
	       
	            byte[] receiveData = new byte[1024];
	        //    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	         //   socket.receive(receivePacket);
	        //    String modifiedSentence = new String(receivePacket.getData());
	       //   logger.debug("FROM SERVER:" + modifiedSentence);
         }
         //param1 += "!";

        // String params = "rst=1&image=" + param1 + "&passiveMode=1\n";
         String params = "passiveMode=1&rndr=1";
         
         byte[] sendData = params.getBytes();
            
            // Create a DatagramPacket to send the data
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(ipAddress), port);
           // Thread.sleep(55);
            // Send the packet
            socket.send(sendPacket);
            
            logger.debug("Message sent: " + params);
            
            byte[] receiveData = new byte[1024];
         //   DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
         //   socket.receive(receivePacket);
         //   String modifiedSentence = new String(receivePacket.getData());
        //    logger.debug("FROM SERVER:" + modifiedSentence);

            // Close the socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
}

	public static String scanScreenIP(LEDScreen screen) {
		//int port = 80; // HTTP port
        //int maxThreads = 50; // Maximum number of threads
        int maxThreads  = Integer.valueOf(properties.getProperty("screen.ip.scan.threads")); // Maximum number of threads
		int port = Integer.valueOf(properties.getProperty("screen.port.http"));
        
        
        try {
	        ScanWorker ipChecker = new ScanWorker(InetAddress.getByName(screen.getiPAdress()), port, screen);
	        String iP = ipChecker.checkIp();
	        if(!iP.isEmpty()) {
	     	   
	     		logger.info("Screen IP Valid: " + screen.getiPAdress());
	     		ArduinoLEDTool.infoArea.setText(screen.getiPAdress());
	     		
	        }else {
	        	logger.debug("IP Not responding, Scanning for screen IP.");
		        
	            // Get the local network address
	        	String localIP = InetAddress.getLocalHost().getHostAddress();
	            InetAddress localAddress = InetAddress.getByName(localIP.substring(0, localIP.lastIndexOf('.') + 1) + "1"); // Assuming a typical /24 subnet
	
	            ExecutorService executor = Executors.newFixedThreadPool(maxThreads);
	
	            // Iterate through all possible IP addresses in the subnet
	            for (int i = 1; i <= 254; i++) { // Assuming /24 subnet
	                InetAddress address = InetAddress.getByName(localAddress.getHostAddress().substring(0, localAddress.getHostAddress().lastIndexOf('.') + 1) + i);
	
	                Runnable worker = new ScanWorker(address, port, screen);
	                executor.execute(worker);
	            }
	            executor.shutdown();
	        }
	            
        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
        
		//return iPAdress;
	}
}
