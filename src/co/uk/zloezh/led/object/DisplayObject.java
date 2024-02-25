package co.uk.zloezh.led.object;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.uk.zloezh.led.thread.LEDScreenProjected;
import co.uk.zloezh.led.thread.RenderImage;

public abstract class DisplayObject implements LEDScreenProjected {
	
	protected static final Logger logger = LogManager.getLogger();
	private boolean isActive = true;
	private boolean isRendering = false;
	protected String name;
	protected RenderImage renderThread;
	
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

	public boolean isRendering() {
		return isRendering;
	}

	public void setRendering(boolean isRendering) {
		this.isRendering = isRendering;
	}

	public RenderImage getRenderThread() {
		return renderThread;
	}

	public void setRenderThread(RenderImage renderThread) {
		this.renderThread = renderThread;
	}

	
}
