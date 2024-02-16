package co.uk.zloezh.led.object;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;

import co.uk.zloezh.led.thread.DisplayImage;
import co.uk.zloezh.led.utils.LEDDIsplayObjectUtils;



public class FileImage extends DisplayObject{
	
	private String extension;
	File file;
	LEDFrame frame;

	
	public FileImage(File objectFile) throws IOException {
		this.file = objectFile;
		this.extension = FilenameUtils.getExtension(objectFile.getName());
		
		BufferedImage img = ImageIO.read(objectFile);
		LEDDIsplayObjectUtils instance = LEDDIsplayObjectUtils.getInstance();
		long[] hexArray = instance.hexFromImage(img);
		this.frame = instance.generateLedFrame(hexArray);
	}
	
	public FileImage(String filePath) throws IOException {
		
		file = new File( filePath); 
		this.extension = FilenameUtils.getExtension(file.getName());
	
		BufferedImage img = ImageIO.read(file);
		LEDDIsplayObjectUtils instance = LEDDIsplayObjectUtils.getInstance();
		long[] hexArray = instance.hexFromImage(img);
		this.frame = instance.generateLedFrame(hexArray);
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
		
		Thread thred = new DisplayImage(this.frame);
		thred.start();
		return thred;
	}
	

}
