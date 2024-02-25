package co.uk.zloezh.led.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.uk.zloezh.led.object.LEDScreen;

public class RenderImage extends Thread {
	protected LEDScreen screen;
	protected static final Logger logger = LogManager.getLogger();
	private final Object LOCK_OBJECT = new Object();
    private boolean pauseThreadFlag = false;
	
	public RenderImage(LEDScreen screen) {
		super();
		this.screen = screen;
	}
	
	public void pauseThread() throws InterruptedException {
        pauseThreadFlag = true;
        logger.info("Thread Paused");
    }

    public void resumeThread() {
        synchronized(LOCK_OBJECT) {
        	logger.info("Thread Resumed");
            pauseThreadFlag = false;
            LOCK_OBJECT.notify();
        }
    }
	
    protected void checkForPaused() {
        synchronized (LOCK_OBJECT) {
        	
            while (pauseThreadFlag) {
                try {
                	LOCK_OBJECT.wait();
                } catch (Exception e) {}
            }
        }
    }
	
}