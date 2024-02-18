package co.uk.zloezh.led.thread;

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
import co.uk.zloezh.led.utils.HTTPUtils;

public class DisplayImage extends Thread{
	
	LEDFrame frame;
	LEDScreen screen;
	protected static final Logger logger = LogManager.getLogger();
	
	public DisplayImage(LEDFrame cFrame, LEDScreen cScreen) {
		this.frame = cFrame;
		this.screen = cScreen;
	}
	
	public void run() {
		
		logger.info("Started");
		HTTPUtils.sendFrame(frame, screen);
	        
	}

}
