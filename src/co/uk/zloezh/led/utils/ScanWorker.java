package co.uk.zloezh.led.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.uk.zloezh.led.ArduinoLEDTool;
import co.uk.zloezh.led.object.LEDScreen;

//Worker class to perform scanning
class ScanWorker implements Runnable {
    private InetAddress address;
    private static String iPAdress;
    private int port;
    private LEDScreen screen;
    protected static final Logger logger = LogManager.getLogger();

    public ScanWorker(InetAddress address, int port, LEDScreen lScreen ) {
    	
        this.address = address;
        this.port = port;
        this.screen = lScreen;
    }

    @Override
    public void run() {
       String iP = this.checkIp();
       if(!iP.isEmpty()) {
    	   synchronized(this){
    		   this.screen.setiPAdress(iP);
    		   ArduinoLEDTool.infoArea.setText(iP);
    	   }
       }
    }
    
    public String checkIp() {
    	 try {
         	
             URL url = new URL("http://" + address.getHostAddress() + "/cmd=1");
             logger.debug("Checking: " + url);
             HttpURLConnection connection = (HttpURLConnection) url.openConnection();
             connection.setRequestMethod("GET");
             connection.setConnectTimeout(350); //set timeout
             //System.out.println("address: " + address);
             int responseCode = connection.getResponseCode();
             if (responseCode == HttpURLConnection.HTTP_OK) {
                // System.out.println("Device found at: " + address.getHostAddress());
                 try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                     String line;
                     while ((line = reader.readLine()) != null) {
                         if (line.contains("refreshRate=")) {
                         	logger.info("Screen IP Found using: " + url);
                             synchronized(this){
                            	//this.screen.setiPAdress(address.getHostAddress());
                             	return address.getHostAddress();
                             }
                             //break;
                         }
                     }
                 }
                 Thread.currentThread().getThreadGroup().interrupt();
             }

             connection.disconnect();
         } catch (IOException e) {
             // Ignore connection errors
         }
    	 return "";
    }
}
