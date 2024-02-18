package co.uk.zloezh.led.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.uk.zloezh.led.PropertiesObject;
import co.uk.zloezh.led.object.LEDFrame;

public class LEDDIsplayObjectUtils {
	
	private static final LEDDIsplayObjectUtils instance = new LEDDIsplayObjectUtils();

	protected static final Logger logger = LogManager.getLogger();
	private  int width, hight;
	private  String direction;
	
    // private constructor to avoid client applications using the constructor
    private LEDDIsplayObjectUtils(){
    	PropertiesObject properties = PropertiesObject.getInstance();
    	this.width = Integer.valueOf(properties.getProperty("screen.width"));
    	this.hight = Integer.valueOf(properties.getProperty("screen.height"));
    	this.direction = properties.getProperty("screen.direction");
    	
    }

    public static LEDDIsplayObjectUtils getInstance() {
        return instance;
    }
    
    public LEDFrame generateLedFrame(long[] hexArray) {
		try {
			LEDDIsplayObjectUtils instance = LEDDIsplayObjectUtils.getInstance();
			int width = instance.width;
			int hight = instance.hight;
        	if(instance.direction.equals("BRU")) {
        		
	        	boolean reverse = false;  
	        	long[] reverseArray= new long[width*hight];
	        	int q = (hight*width)+hight -1;
	        	for (int i = 0; i < 25; i++) {
	        		reverse = !reverse;
	        		if(reverse) {
	        			q -= (hight-1);
	        		}else {
	        			q -= (hight+1);
	        		}
	
	        		for (int j = 0; j < hight; j++) {
	        			if(reverse) {
	        				q--;
	        			}else {
	        				q++;
	        			}
	        			logger.debug("i: " + i + ",j: "+ j + ",i+j: " + (i+j*width) + " " + String.format("0x%08X", hexArray[i+j*width]) + " Goes to: " + q);
	        			//System.out.println("i: " + i + ",j: "+ j + ",i+j: " + (i+j*width) + " " + String.format("0x%08X", hexArray[i+j*width]) + " Goes to: " + q);
	        			reverseArray[q] = hexArray[i+j*width];
	
	        		}
	
	        	}
	        	
	        	return new LEDFrame(reverseArray);
	        	
	
	        	
			}
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
    }

    public void writeFrameToFile(LEDFrame frame, FileWriter fileWriter) {
    	
    	try {
    		
    		fileWriter.append(System.lineSeparator());
    		fileWriter.append("FRAME String: " + System.lineSeparator());
        	
	    	long[] reverseArray = frame.getHexArray();
	    	
	    	for (int i = 0; i < reverseArray.length; i++) {
	    		
					fileWriter.append(String.format("0x%08X", reverseArray[i])+ ",");
	    	}
	    	
	    	fileWriter.append(System.lineSeparator());
	    	
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	logger.info("Image written to file");
    	
    }
    
    public void writeShapeToFile(LEDFrame frame, FileWriter fileWriter) {
        try {
        	
        	fileWriter.append(System.lineSeparator());
        	fileWriter.append("Shape indexes: " + System.lineSeparator());
        	long[] reverseArray = frame.getHexArray();
        	LEDDIsplayObjectUtils instance = LEDDIsplayObjectUtils.getInstance();
			int width = instance.width;
			int hight = instance.hight;
        	
        	long[] newFrameShadow = new long[width*hight];
        	int k =0;
        	for (int i = 0; i < reverseArray.length; i++) {

        		if (reverseArray[i] != 0) {	
        			newFrameShadow[k] = i;
        			fileWriter.append(newFrameShadow[k] + ",");
        			k++;
        		}
        	}
        	
        	fileWriter.append(System.lineSeparator() + "Size: " + --k);

        	
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Shape written to file");
	}

    public long[] hexFromImage(BufferedImage img) {
    	//Write original Hex
    	int size = img.getWidth() * img.getHeight();
	    long[] hexArray = new long[size];
	    int i =0;
	    for (int y = 0; y < img.getHeight(); y++) {
	         for (int x = 0; x < img.getWidth(); x++) {
	            //Retrieving contents of a pixel
	            int pixel = img.getRGB(x,y);
	            //Creating a Color object from pixel value
	            Color color = new Color(pixel, true);
	            //Retrieving the R G B values
	            int red = color.getRed();
	            int green = color.getGreen();
	            int blue = color.getBlue();

	            long rgbValue = ((long) red << 16) | ((long) green << 8) | blue;
	            hexArray[i++] = rgbValue;
	         
	         }
	      }
	    return hexArray;
    }

}
