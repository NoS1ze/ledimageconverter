package co.uk.zloezh.led.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.uk.zloezh.led.object.LEDScreen;

public class RenderThread extends Thread {
	protected LEDScreen screen;
	protected static final Logger logger = LogManager.getLogger();
	private final Object LOCK_OBJECT = new Object();
    private boolean pauseThreadFlag = false;
	
	public RenderThread(LEDScreen screen) {
		super();
		this.screen = screen;
	}
	
	public boolean isPauseThreadFlag() {
		return pauseThreadFlag;
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
