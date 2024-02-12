package co.uk.zloezh;

public class LEDImageObject {
	
	private String name, extension, path;
	private boolean isActive = true;
	
	public void LEDImageObject(String filePath) {
		this.path = filePath;
	}
	
	public void LEDImageObject(String fileName, String fileExtension, String filePath) {
		this.name = fileName;
		this.extension = fileExtension;
		this.path = filePath;
	}
	
	public String getFileName() {
		return name;
	}
	public void setFileName(String fileName) {
		this.name = fileName;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
