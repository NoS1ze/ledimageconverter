package co.uk.zloezh;

import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import javax.swing.filechooser.*;
import javax.imageio.*;
import org.apache.commons.io.FilenameUtils;
import java.awt.Color;

public class ArduinoLEDTool extends JFrame implements ActionListener {
	
	static JLabel label;
	static JTextField t;
	static File  pixelFile; 
	static JTextField txtW,txtH;
    
	ArduinoLEDTool()
    {
    }
	
	public static void main(String args[]){
		
		try {
		
			// make an object of the class filechooser
	        ArduinoLEDTool ledToolFrame = new ArduinoLEDTool();
	        
			//Creating the Frame
	        JFrame frame = new JFrame("Chat Frame");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(400, 400);
	
	        //Creating the MenuBar and adding components
	        JPanel topPanel = new JPanel(); // the panel is not visible in output
	       
	
	        // button to open open dialog
	        JButton btnOpenFile = new JButton("open");
	        // button to open save dialog
	        JButton btnGenerateFile = new JButton("generate");
	        label = new JLabel("no file selected");
	 
	        topPanel.add(btnOpenFile);
	        topPanel.add(btnGenerateFile);
	        topPanel.add(label);
	        
	        btnOpenFile.addActionListener(ledToolFrame);
	        btnGenerateFile.addActionListener(ledToolFrame);
	        
	        
	        /*
	        JMenuBar menuBar = new JMenuBar();
	        JMenu mFile = new JMenu("FILE");
	        JMenu mHelp = new JMenu("Help");
	        menuBar.add(mFile);
	        menuBar.add(mHelp);
	        JMenuItem m11 = new JMenuItem("Open");
	        JMenuItem m22 = new JMenuItem("Save as");
	        mFile.add(m11);
	        mHelp.add(m22); 
	        
	        m11.addActionListener(f1);
	        */
	
	        
	        
	        //Creating the panel at bottom and adding components
	        JPanel panel = new JPanel(); // the panel is not visible in output
	        JLabel labelW = new JLabel("W");
	        JLabel labelH = new JLabel("H");
	        txtW = new JTextField(4); 
			txtH = new JTextField(4);

	        panel.add(labelW); // Components Added using Flow Layout
	        panel.add(txtW);
	        panel.add(labelH); // Components Added using Flow Layout
	        panel.add(txtH);
	
	
	        // Text Area at the Center
	        JTextArea ta = new JTextArea();
	        
	        //Adding Components to the frame.
	       
	 
	        //frame.getContentPane().add(BorderLayout.NORTH, mb);
	        frame.getContentPane().add(BorderLayout.NORTH, topPanel);
	        frame.getContentPane().add(BorderLayout.CENTER, ta);
	        frame.getContentPane().add(BorderLayout.SOUTH, panel);
	        frame.setVisible(true);
    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 
        
    
    }
    public void actionPerformed(ActionEvent evt)
    {
        // if the user presses the save button show the save dialog
        String com = evt.getActionCommand();
        
        System.out.println("com " + com);
 
        if (com.equals("generate")) {
        	 try {
                 
					BufferedImage img = ImageIO.read(pixelFile);
					
			        String filename = pixelFile.getName();
			        String filepath = pixelFile.getAbsolutePath();
			        String pathwithoutname = filepath.substring(0, filepath.length() - filename.length());
			        String fileNameWithOutExt = FilenameUtils.removeExtension(filename);
					
					//String labelText = img.getHeight() + "x" + img.getHeight() + " " + filename;
					//System.out.println("labelText" + labelText);
					//label.setText(labelText);
					
					int size = img.getWidth() * img.getHeight();
					txtW.setText(String.valueOf(img.getWidth()));
					txtH.setText(String.valueOf(img.getHeight()));
				    FileWriter writer = new FileWriter(pathwithoutname + fileNameWithOutExt + ".txt");
				    writer.append("Original file WxH: " + img.getWidth() + "x" + img.getHeight() + System.lineSeparator());
				    writer.append("Pixels: " + size + System.lineSeparator());
					
				    //Write original Hex
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
				            String hex = String.format("#%02x%02x%02x", red, green, blue);  
				            writer.append(hex+",");
				            long rgbValue = ((long) red << 16) | ((long) green << 8) | blue;
				            hexArray[i++] = rgbValue;
				         
				         }
				      }
				    writer.append(System.lineSeparator());
					writer.flush();
					
					LedImageConverter ledUtils = new LedImageConverter(writer, hexArray, img.getWidth(), img.getHeight());
					ledUtils.writeLedString();
					
				    writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            
            	//label.setText("123");
           
        // if the user presses the open dialog show the open dialog
        }else {
            // create an object of JFileChooser class
            JFileChooser jFileDialog = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int r = jFileDialog.showOpenDialog(null);

            if (r == JFileChooser.APPROVE_OPTION){
                pixelFile = new File(jFileDialog.getSelectedFile().getAbsolutePath());  
                label.setText(pixelFile.getName());
            }
            // if the user cancelled the operation
            else
            	label.setText("the user cancelled the operation");
        }
    }
}


