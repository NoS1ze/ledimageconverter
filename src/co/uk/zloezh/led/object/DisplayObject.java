package co.uk.zloezh.led.object;

import co.uk.zloezh.led.thread.LEDScreenProjected;

public abstract class DisplayObject implements LEDScreenProjected {
	private boolean isActive = true;
	
	
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
