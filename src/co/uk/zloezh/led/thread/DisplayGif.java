package co.uk.zloezh.led.thread;

import java.io.File;

import co.uk.zloezh.led.object.LEDFrame;
import co.uk.zloezh.led.object.LEDScreen;

public class DisplayGif extends Thread{
	
	LEDFrame frame;
	
	public DisplayGif(LEDFrame cFrame) {
		this.frame = cFrame;
	}
	
	public DisplayGif(File file, LEDScreen screen) {
		// TODO Auto-generated constructor stub
	}

	public void run() {
	        long startTime = System.currentTimeMillis();
	        int i = 0;
	        while (true) {
	            System.out.println(this.getName() + ": New Thread is running..." + i++);
	            try {
	                //Wait for one sec so it doesn't print too fast
	                Thread.sleep(1000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            
	        }
	    }

}
