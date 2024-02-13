package co.uk.zloezh.listener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FilenameUtils;

import co.uk.zloezh.LEDFrame;
import co.uk.zloezh.LedImageConverter;
import co.uk.zloezh.utils.LEDDIsplayObjectUtils;

public class GeneratePixelArrayListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent evnt) {
		 // if the user presses the save button show the save dialog
        String com = evnt.getActionCommand();
        
        System.out.println("com " + com);
 

            // create an object of JFileChooser class
            JFileChooser jFileDialog = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int r = jFileDialog.showOpenDialog(null);

            if (r == JFileChooser.APPROVE_OPTION){
                File selectedFile = new File(jFileDialog.getSelectedFile().getAbsolutePath());  
                try {
                    
					BufferedImage img = ImageIO.read(selectedFile);
					
			        String filename = selectedFile.getName();
			        String filepath = selectedFile.getAbsolutePath();
			        String pathwithoutname = filepath.substring(0, filepath.length() - filename.length());
			        String fileNameWithOutExt = FilenameUtils.removeExtension(filename);
									
					int size = img.getWidth() * img.getHeight();
				    FileWriter writer = new FileWriter(pathwithoutname + fileNameWithOutExt + ".txt");
				    writer.append("Original file WxH: " + img.getWidth() + "x" + img.getHeight() + System.lineSeparator());
				    writer.append("Pixels: " + size + System.lineSeparator());
					
				    //Write original Hex
				    /*
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
				            String hex = String.format("0x%02x%02x%02x", red, green, blue);  
				            writer.append(hex+",");
				            long rgbValue = ((long) red << 16) | ((long) green << 8) | blue;
				            hexArray[i++] = rgbValue;
				         
				         }
				      }
				      */
					//writer.flush();
				    LEDDIsplayObjectUtils instance = LEDDIsplayObjectUtils.getInstance();
				    
				    long[] hexArray = instance.hexFromImage(img);
					LEDFrame frame = instance.generateLedFrame(hexArray);
					
					instance.writeFrameToFile(frame, writer);
				    instance.writeShapeToFile(frame, writer);
				    
					//LedImageConverter ledUtils = new LedImageConverter(writer, hexArray, img.getWidth(), img.getHeight());
					//ledUtils.writeLedString();
					//ledUtils.writeShapeString();
					
				    writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }else {
            	//label.setText("the user cancelled the operation");
            }
        }
		
	

}
