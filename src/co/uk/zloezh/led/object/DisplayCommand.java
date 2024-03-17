package co.uk.zloezh.led.object;

import java.io.File;

import co.uk.zloezh.led.PropertiesObject;
import co.uk.zloezh.led.thread.DisplayGif;
import co.uk.zloezh.led.thread.RenderCMD;
import co.uk.zloezh.led.utils.HTTPUtils;

public class DisplayCommand extends DisplayObject {

	private String command;
	
	public DisplayCommand() {
		super();
		PropertiesObject properties = PropertiesObject.getInstance();
		this.setIconFile( new File( System.getProperty("user.dir") + properties.getProperty("images.icon.cmd"))); 
	}
	
	
	public DisplayCommand(String pName, String pCommand) {
		super();
		this.setName(pName);
		this.setCommand(pCommand);
		PropertiesObject properties = PropertiesObject.getInstance();
		this.setIconFile( new File( System.getProperty("user.dir") + properties.getProperty("images.icon.cmd"))); 
		// TODO Auto-generated constructor stub
	}


	public String getCommand() {
		return command;
	}


	public void setCommand(String command) {
		this.command = command;
	}


	@Override
	public Thread render(LEDScreen screen) {

		
		RenderCMD thread = new RenderCMD(command, screen);
		thread.start();
		this.setRenderThread(thread);
		//this.setRendering(true);
		return thread;
	}


	@Override
	public String toString() {
		return "DisplayCommand [command=" + command + ", name=" + name + ", renderThread=" + renderThread
				+ ", iconFile=" + iconFile + "]";
	}
	
	
	
}
