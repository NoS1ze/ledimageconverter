package co.uk.zloezh.led.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.uk.zloezh.led.object.LEDFrame;
import co.uk.zloezh.led.object.LEDScreen;

public class HTTPUtils {
	
	protected static final Logger logger = LogManager.getLogger();
	
	public static void sendFrame( LEDFrame frame, LEDScreen screen) {
		try {
			 
	         logger.info("Connecting to: " + screen.iPAdress);
			 URL url = new URL("https://" + screen.iPAdress);

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
	        	 param1 += "-" + i + "-" + String.format("0x%08X", array[i]);
	         }
	         param1 += "-";
	         
	         String param2 = "1";
	         String params = "image=" + param1 + "&listening=" + param2;

	         
	         logger.debug("Sending Params:" + params);
	         // Write parameters to the connection
	         OutputStream outputStream = connection.getOutputStream();
	         outputStream.write(params.getBytes(StandardCharsets.UTF_8));
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
	         System.out.println("Response: " + response.toString());

	         // Disconnect the connection
	         connection.disconnect();
			}catch(IOException e) {
				e.printStackTrace();
			}
	            
		
	}

}
