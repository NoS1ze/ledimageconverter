package co.uk.zloezh.led.object;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.uk.zloezh.led.thread.DisplayImage;
import co.uk.zloezh.led.utils.LEDDIsplayObjectUtils;



public class DisplayImageFile extends DisplayFile{
	
	private LEDFrame frame;

	
	public DisplayImageFile(File objectFile) throws IOException {

		super(objectFile);
		BufferedImage img = ImageIO.read(objectFile);
		LEDDIsplayObjectUtils instance = LEDDIsplayObjectUtils.getInstance();
		long[] hexArray = instance.hexFromImage(img);
		this.frame = instance.generateLedFrame(hexArray);

	}
	
	public DisplayImageFile(String filePath) throws IOException {
		
		super(filePath);
		BufferedImage img = ImageIO.read(file);
		LEDDIsplayObjectUtils instance = LEDDIsplayObjectUtils.getInstance();
		long[] hexArray = instance.hexFromImage(img);
		this.frame = instance.generateLedFrame(hexArray);

	}
	
	public LEDFrame getLEDFrame(){
		return frame;
	}

	@Override
	public Thread render(LEDScreen screen) {
		
		DisplayImage thred = new DisplayImage(this.frame, screen);
		thred.start();
		this.setRenderThread(thred);
		return thred;
	}

	@Override
	public String toString() {
		return "DisplayImageFile [frame=" + frame + ", file=" + file + ", name=" + name + "]";
	}
	
	
	
}
