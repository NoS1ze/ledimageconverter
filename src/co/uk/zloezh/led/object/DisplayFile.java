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



public class DisplayFile extends DisplayObject{
	
	protected static final Logger logger = LogManager.getLogger();
	private String extension;
	File file;
	//LEDFrame frame;

	
	public DisplayFile(File objectFile) throws IOException {
		this.file = objectFile;
		this.extension = FilenameUtils.getExtension(objectFile.getName());
		this.name = objectFile.getName();
		
		/*BufferedImage img = ImageIO.read(objectFile);
		LEDDIsplayObjectUtils instance = LEDDIsplayObjectUtils.getInstance();
		long[] hexArray = instance.hexFromImage(img);
		this.frame = instance.generateLedFrame(hexArray); */
		logger.info(this.file.getName() + " Loaded");
	}
	
	public DisplayFile(String filePath) throws IOException {
		
		file = new File( filePath); 
		this.extension = FilenameUtils.getExtension(file.getName());
		this.name = file.getName();
		
		/*BufferedImage img = ImageIO.read(file);
		LEDDIsplayObjectUtils instance = LEDDIsplayObjectUtils.getInstance();
		long[] hexArray = instance.hexFromImage(img);
		this.frame = instance.generateLedFrame(hexArray); */
		logger.info(this.file.getName() + " Loaded");
	}
	
	public File getFile() {
		return file;
	}
	public void setFile(File objectFile) {
		this.file = objectFile;
	}

	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Override
	public Thread render(LEDScreen screen) {

		return null;
	}

	@Override
	public String toString() {
		return "DisplayFile [extension=" + extension + ", file=" + file + "]";
	}
	
	

}
