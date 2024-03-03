package co.uk.zloezh.led.thread;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ibasco.image.gif.GifFrame;
import com.ibasco.image.gif.GifImageReader;

import co.uk.zloezh.led.object.DisplayGifFile;
import co.uk.zloezh.led.object.LEDFrame;
import co.uk.zloezh.led.object.LEDScreen;
import co.uk.zloezh.led.utils.HTTPUtils;
import co.uk.zloezh.led.utils.LEDDIsplayObjectUtils;

public class DisplayGif extends RenderImage{
	

	DisplayGifFile gifObject;
	LEDScreen screen;
	
	public DisplayGif(DisplayGifFile cGgifObject, LEDScreen cScreen) {
		super(cScreen);

		this.gifObject = cGgifObject;

		

	}
	

	public void run() {
		logger.info("Started");
	        while (true) {
	        	logger.info("." + this.isPauseThreadFlag());
	            	this.checkForPaused();

	            		try {
	            			var file = gifObject.getFile();
						    String[] imageatt = new String[]{
						            "imageLeftPosition",
						            "imageTopPosition",
						            "imageWidth",
						            "imageHeight"
						    };    
						    ImageReader reader = (ImageReader)ImageIO.getImageReadersByFormatName("gif").next();
						    ImageInputStream ciis = ImageIO.createImageInputStream(file);
						    reader.setInput(ciis, false);

						    int noi = reader.getNumImages(true);
						    BufferedImage master = null;

						    	for (int i = 0; i < noi; i++) {
	
							    	logger.info("Image: " + i);
							    	this.checkForPaused();
							        BufferedImage image = reader.read(i);
							        IIOMetadata metadata = reader.getImageMetadata(i);
							        Node tree = metadata.getAsTree("javax_imageio_gif_image_1.0");
							        NodeList children = tree.getChildNodes();
			
							        for (int j = 0; j < children.getLength(); j++) {

							            Node nodeItem = children.item(j);
							            if(nodeItem.getNodeName().equals("ImageDescriptor")){
							                Map<String, Integer> imageAttr = new HashMap<String, Integer>();
			
							                for (int k = 0; k < imageatt.length; k++) {

							                    NamedNodeMap attr = nodeItem.getAttributes();
							                    Node attnode = attr.getNamedItem(imageatt[k]);
							                    imageAttr.put(imageatt[k], Integer.valueOf(attnode.getNodeValue()));
							                }
							                if(i==0){
							                    master = new BufferedImage(imageAttr.get("imageWidth"), imageAttr.get("imageHeight"), BufferedImage.TYPE_INT_ARGB);

							                }
							                master.getGraphics().drawImage(image, imageAttr.get("imageLeftPosition"), imageAttr.get("imageTopPosition"), null);
							                
							                LEDDIsplayObjectUtils instance = LEDDIsplayObjectUtils.getInstance();
							        		long[] hexArray = instance.hexFromImage(master);
							        		LEDFrame frame = instance.generateLedFrame(hexArray);
										   

							        		HTTPUtils.SendFrameViaUDP(frame, screen);
	
							        		break;
							            }
							        }
						    	}// ImageIO.write(master, "GIF", new File( i + ".gif")); 
						    
						} catch (IOException e) {
						    e.printStackTrace();
						}
	            	//}
	            
	            
	        }
	    }

}
