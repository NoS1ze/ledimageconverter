package co.uk.zloezh.led.object;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.uk.zloezh.led.thread.LEDScreenProjected;

import co.uk.zloezh.led.thread.RenderThread;

public abstract class DisplayObject implements LEDScreenProjected {
	
	protected static final Logger logger = LogManager.getLogger();
	private boolean isActive = true;
	private boolean isRendering = false;
	protected String name;
	protected RenderThread renderThread;
	protected File iconFile;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String pName) {
		this.name = pName;
	}
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	

	public Thread render(LEDScreen screen) {
		// TODO Auto-generated method stub
		this.isRendering = true;
		return null;
	}

	public boolean isRendering() {
		return isRendering;
	}

	public void setRendering(boolean isRendering) {
		this.isRendering = isRendering;
	}

	public RenderThread getRenderThread() {
		return renderThread;
	}

	public void setRenderThread(RenderThread renderThread) {
		this.renderThread = renderThread;
	}

	public File getIconFile() {
		return iconFile;
	}

	public void setIconFile(File iconFile) {
		this.iconFile = iconFile;
	}

	
}
