package co.uk.zloezh;

import java.io.File;

import org.apache.commons.io.FilenameUtils;



public class LEDDisplayFile {
	
	private String extension;
	File file;
	private boolean isActive = true;
	
	public LEDDisplayFile(File objectFile) {
		this.file = objectFile;
		this.extension = FilenameUtils.getExtension(objectFile.getName());
	}
	
	public LEDDisplayFile(String filePath) {
		
		file = new File( filePath); 
		this.extension = FilenameUtils.getExtension(file.getName());
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

	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
