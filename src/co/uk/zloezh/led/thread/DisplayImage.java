package co.uk.zloezh.led.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import co.uk.zloezh.led.object.LEDFrame;

public class DisplayImage extends Thread{
	
	LEDFrame frame;
	
	public DisplayImage(LEDFrame cFrame) {
		this.frame = cFrame;
	}
	
	public void run() {

		try {
			 
			 URL url = new URL("https://reqbin.com/echo");

	         // Create connection object
	         HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	         // Set the request method to POST
	         connection.setRequestMethod("POST");

	         // Enable input/output streams
	         connection.setDoInput(true);
	         connection.setDoOutput(true);

	         // Set request parameters
	         String param1 = "";
	         
	         long[] array = this.frame.getHexArray();
	         for (int i = 0; i < array.length ; i++) {
	        	 param1 += "-" + i + "-" + array[i];
	         }
	         param1 += "-";
	         
	         String param2 = "1";
	         String params = "image=" + param1 + "&listening=" + param2;

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
