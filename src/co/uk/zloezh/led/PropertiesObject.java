package co.uk.zloezh.led;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesObject {
	
	private static final PropertiesObject instance = new PropertiesObject();
	private Properties properties; 
    // private constructor to avoid client applications using the constructor
    private PropertiesObject(){
		properties = new Properties();
		try {
            FileInputStream inputStream = new FileInputStream("appfiles/config.properties");
            properties.load(inputStream);
            inputStream.close();
		}catch(Exception e) {
			
		}
    	
    }

    public static PropertiesObject getInstance() {
        return instance;
    }
    
    public String getProperty(String key) {
    	return properties.getProperty(key);
    }
    
    

}
