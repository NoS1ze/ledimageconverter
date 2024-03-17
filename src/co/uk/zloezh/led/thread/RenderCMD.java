package co.uk.zloezh.led.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.uk.zloezh.led.object.LEDScreen;
import co.uk.zloezh.led.utils.HTTPUtils;

public class RenderCMD extends RenderThread {

	private String cMD;
	
	public RenderCMD(String pCMD,LEDScreen screen) {
		super(screen);
		this.cMD = pCMD;
		// TODO Auto-generated constructor stub
	}
	
	
	public void run() {
		
		logger.info("Started");
		String responseString = HTTPUtils.sendCommandViaHttp(this.cMD ,screen);
	        
	}
	
}
