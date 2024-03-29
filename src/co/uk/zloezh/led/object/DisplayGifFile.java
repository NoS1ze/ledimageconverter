package co.uk.zloezh.led.object;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.uk.zloezh.led.thread.DisplayGif;
import co.uk.zloezh.led.thread.RenderThread;
import co.uk.zloezh.led.utils.LEDDIsplayObjectUtils;



public class DisplayGifFile extends DisplayFile{
	


	
	public DisplayGifFile(File objectFile) throws IOException {
		super(objectFile);
		
		

	}
	
	public DisplayGifFile(String filePath) throws IOException {
		
		super(filePath);

	}
	


	@Override
	public Thread render(LEDScreen screen) {
		
		DisplayGif thred = new DisplayGif(this, screen);
		thred.start();
		this.setRenderThread(thred);
		this.setRendering(true);
		return thred;

	}

	@Override
	public String toString() {
		return "DisplayGifFile [file=" + file + ", name=" + name + "]";
	}
	
	

}
