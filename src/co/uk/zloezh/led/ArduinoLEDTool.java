package co.uk.zloezh.led;

import java.io.*;
import java.util.Properties;

import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.filechooser.*;
import javax.imageio.*;
import org.apache.commons.io.FilenameUtils;

import co.uk.zloezh.led.listener.GeneratePixelArrayListener;
import co.uk.zloezh.led.listener.SendImageListener;
import co.uk.zloezh.led.object.FileImage;
import co.uk.zloezh.led.object.LEDScreen;
import co.uk.zloezh.led.listener.ActiveCheckBoxListener;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.GridLayout;

public class ArduinoLEDTool extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JLabel label;
	static JTextField t;
	static File  pixelFile; 
	static JTextField txtW,txtH;
	//private static Properties properties;
	private static List<FileImage> objectList;
	public static LEDScreen screen;
	
	ArduinoLEDTool()
    {
		PropertiesObject properties = PropertiesObject.getInstance();
		
		screen = new LEDScreen(properties.getProperty("screen.ip"),Integer.valueOf(properties.getProperty("screen.width")),Integer.valueOf(properties.getProperty("screen.height")),properties.getProperty("screen.direction"));
		try {
            
            String directoryPath = System.getProperty("user.dir") +  properties.getProperty("images.path");

            // Create a File object for the directory
            File directory = new File(directoryPath);

            // Get a list of all files in the directory
            File[] files = directory.listFiles();

            // Check if the directory exists and is indeed a directory
            if (directory.exists() && directory.isDirectory()) {
                // Check if there are any files in the directory
                if (files != null && files.length > 0) {
                    System.out.println("Files in directory:");
                    objectList = new ArrayList<>();
                    for (File file : files) {
                        System.out.println(file.getName());
                        FileImage object = new FileImage(file.getCanonicalPath());
                        objectList.add(object);
                    }
                } else {
                    System.out.println("Directory is empty.");
                }
            } else {
                System.out.println("Directory does not exist or is not a directory.");
            }
           
        } catch (IOException e) {
            System.err.println("Error loading properties: " + e.getMessage());
           
        }
    }
	
	public static void main(String args[]){
		
		try {


			// make an object of the class filechooser
	        ArduinoLEDTool ledToolFrame = new ArduinoLEDTool();
	        
			//Creating the Frame
	        JFrame frame = new JFrame("Chat Frame");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(400, 400);
	

	        //Menu
	        
	        JMenuBar menuBar = new JMenuBar();
	        JMenu mUtils = new JMenu("Utils");
	        //JMenu mHelp = new JMenu("Help");
	        menuBar.add(mUtils);
	        //menuBar.add(mHelp);
	        JMenuItem genArray = new JMenuItem("Convert Image");
	        GeneratePixelArrayListener menuListener = new GeneratePixelArrayListener();
	        
	        genArray.addActionListener(menuListener);
	       // JMenuItem m22 = new JMenuItem("Save as");
	        mUtils.add(genArray);
	       // mHelp.add(m22); 

	        
	
	        JPanel mainPanel = new JPanel(new BorderLayout());
	        JPanel tablePanel = new JPanel(new GridLayout(objectList.size() + 1 , 1)); // +1 for header row 
	        JPanel labelPane = new JPanel(new GridLayout(1, 4));
	        
	        labelPane.add(new JLabel("Image:"));
	        labelPane.add(new JLabel("Name:"));
	        labelPane.add(new JLabel("Active:"));
	        labelPane.add(new JLabel("Action:"));
	        tablePanel.add(labelPane);


	        for (FileImage item : objectList) {
	                
	                JPanel rowPanel = new JPanel(new GridLayout(1, 4));
	                BufferedImage img= ImageIO.read(item.getFile());
	                ImageIcon icon=new ImageIcon(img);
	                JLabel imgLabel = new JLabel();
	                imgLabel.setIcon(icon);
	                imgLabel.setBounds(100, 100,  50, 50);   
	                rowPanel.add(imgLabel);
	                rowPanel.add(new JLabel(item.getFile().getName()));

	                

	                
	                
	                JButton actionButton;
	                if(item.getExtension().equals("gif") ) {
	                	actionButton = new JButton("Play");
	                }else {
	                	actionButton = new JButton("Set");
	                	SendImageListener listener = new SendImageListener(item, ledToolFrame.screen);
	                	actionButton.addActionListener(listener); 
	                }
	                
	                Checkbox checkbox = new Checkbox();    
	                checkbox.setBounds(100, 100,  50, 50);    
	                checkbox.setState(item.isActive());
	                ActiveCheckBoxListener listener = new ActiveCheckBoxListener(item,actionButton );
	                checkbox.addItemListener(listener); 
	                
	                rowPanel.add(checkbox);
	                rowPanel.add(actionButton);
	                tablePanel.add(rowPanel);

	        }

	        JScrollPane scrollPane = new JScrollPane(tablePanel);
	        
	        mainPanel.add(scrollPane, BorderLayout.CENTER);
	        
	        
	        
	
	
	        // Text Area at the Center
	        JTextArea infoText = new JTextArea();
	        
	        //Adding Components to the frame.
	       
	 
	        frame.getContentPane().add(BorderLayout.NORTH, menuBar);

	        frame.getContentPane().add(BorderLayout.CENTER, tablePanel);
	       // frame.getContentPane().add(BorderLayout.SOUTH, infoText);

	        
	       

	        frame.setVisible(true);
	        

    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 
        
    
    }
    
}


