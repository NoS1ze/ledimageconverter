package co.uk.zloezh.led;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.ibasco.image.gif.GifFrame;
import com.ibasco.image.gif.GifImageReader;

public class LedGifConverter {
	
	public static void main(String[] args) {
		if(args.length >=1 ) {
    
            //var file=new File("/home/user/example.gif");
			var file=new File(args[0]);
			
    		try (var reader = new GifImageReader(file)) {
    		    while (reader.hasRemaining()) {
    		        GifFrame frame = reader.read();
    		        //note: Use frame.getData() to extract the raw image data (in INT ARGB format)   
    		        System.out.printf("Index: %d, Frame: %d x %d", frame.getIndex(), frame.getWidth(), frame.getHeight());
    		        System.out.println();
    		    }
    		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
		try {
		 
			
		 URL url = new URL("http://example.com/api");

         // Create connection object
         HttpURLConnection connection = (HttpURLConnection) url.openConnection();

         // Set the request method to POST
         connection.setRequestMethod("POST");

         // Enable input/output streams
         connection.setDoInput(true);
         connection.setDoOutput(true);

         // Set request parameters
         String param1 = "param1_value";
         String param2 = "param2_value";
         String params = "param1=" + param1 + "&param2=" + param2;

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
