package co.uk.zloezh.led.object;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.uk.zloezh.led.thread.LEDScreenProjected;

public abstract class DisplayObject implements LEDScreenProjected {
	
	protected static final Logger logger = LogManager.getLogger();
	private boolean isActive = true;
	protected String name;
	
	public String getName() {
		return this.name;
	}
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	

	public Thread render(LEDScreen screen) {
		// TODO Auto-generated method stub
		return null;
	}

}
