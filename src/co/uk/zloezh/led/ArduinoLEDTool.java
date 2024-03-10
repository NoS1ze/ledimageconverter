package co.uk.zloezh.led;

import java.io.*;
import java.util.Properties;

import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.filechooser.*;
import javax.imageio.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import co.uk.zloezh.led.listener.*;
import co.uk.zloezh.led.object.*;


import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
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
	private static List<DisplayFile> objectList;
	private static HashMap<DisplayObject, JButton> playButtons;  
	public static LEDScreen screen;
	protected static final Logger logger = LogManager.getLogger();
	
	ArduinoLEDTool()
    {
		PropertiesObject properties = PropertiesObject.getInstance();
		
		screen = new LEDScreen(properties.getProperty("screen.ip"),Integer.valueOf(properties.getProperty("screen.width")),Integer.valueOf(properties.getProperty("screen.height")),properties.getProperty("screen.direction"));
		try {
            
			playButtons = new HashMap<DisplayObject, JButton>();
			
            String directoryPath = System.getProperty("user.dir") +  properties.getProperty("images.path");

            // Create a File object for the directory
            File directory = new File(directoryPath);

            // Get a list of all files in the directory
            File[] files = directory.listFiles();

            // Check if the directory exists and is indeed a directory
            if (directory.exists() && directory.isDirectory()) {
                // Check if there are any files in the directory
                if (files != null && files.length > 0) {
                   // System.out.println("Files in directory:");
                    objectList = new ArrayList<>();
                    for (File file : files) {
                       // System.out.println(file.getName());
                    	String extension = FilenameUtils.getExtension(file.getName());
                		if(extension.equals("png")) {
                			DisplayImageFile imageObject = new DisplayImageFile(file);
                			objectList.add(imageObject);
                			logger.debug("Added to Array: " + imageObject);
                		}
                		if(extension.equals("gif")) {
                			DisplayGifFile gifObject = new DisplayGifFile(file);
                			objectList.add(gifObject);
                			logger.debug("Added to Array: " + gifObject);
                			
                		}
                       
                        
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

			//System.out.println("asda");
			// make an object of the class filechooser
	        ArduinoLEDTool ledToolFrame = new ArduinoLEDTool();
	        
			//Creating the Frame
	        JFrame frame = new JFrame("Image Tool");
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
	        //JPanel labelPane = new JPanel();
	        //labelPane.setLayout(null);
	        
	       // tablePanel.setPreferredSize(new Dimension(600,600));
	        
	        labelPane.add(new JLabel("Image:"));
	        labelPane.add(new JLabel("Name:"));
	        labelPane.add(new JLabel("Active:"));
	        labelPane.add(new JLabel("Action:"));
	        tablePanel.add(labelPane);
	        
	        labelPane.setPreferredSize(new Dimension(50,50));


	        for (DisplayFile item : objectList) {
	                
	                JPanel rowPanel = new JPanel(new GridLayout(1, 4));
	                rowPanel.setPreferredSize(new Dimension(50,50));
	                BufferedImage img= ImageIO.read(item.getFile());
	                ImageIcon icon=new ImageIcon(img);
	                JLabel imgLabel = new JLabel();
	                imgLabel.setIcon(icon);
	                imgLabel.setBounds(0, 0,  50, 50);   
	                rowPanel.add(imgLabel);
	                rowPanel.add(new JLabel(item.getName()));

	                

	                
	                
	                JButton actionButton;
	                if(item.getExtension().equals("gif") ) {
	                	actionButton = new JButton("Play");
	                	//playButtons.put(item, actionButton);
	                	SendGifListener bListener = new SendGifListener(item, ArduinoLEDTool.screen);
	                	actionButton.addActionListener(bListener); 
	                }else {
	                	actionButton = new JButton("Set");
		                RenderScreenListener bListener = new RenderScreenListener(item, ArduinoLEDTool.screen);
	                	actionButton.addActionListener(bListener);   
	                }
	                //RenderScreenListener bListener = new RenderScreenListener(item, ArduinoLEDTool.screen);
                	//actionButton.addActionListener(bListener); 
	                
	                Checkbox checkbox = new Checkbox();    
	                checkbox.setBounds(0, 0,  50, 50);    
	                checkbox.setState(item.isActive());
	                ActiveCheckBoxListener listener = new ActiveCheckBoxListener(item,actionButton );
	                checkbox.addItemListener(listener); 
	                
	                rowPanel.add(checkbox);
	                rowPanel.add(actionButton);
	                tablePanel.add(rowPanel);

	        }

	        JScrollPane scrollPane = new JScrollPane(tablePanel);
	        
	        mainPanel.add(scrollPane, BorderLayout.CENTER);
	     //   mainPanel.setPreferredSize(new Dimension(400,400));
	       // setSize(Dimension)
	        
	        
	
	
	        // Text Area at the Center
	        JTextArea infoText = new JTextArea();
	        
	        //Adding Components to the frame.
	        
	        JTabbedPane jTabbedPane = new JTabbedPane();
	        //JPanel jPanelFirst = new JPanel();

	        //jPanelFirst.setLayout(null);
	        jTabbedPane.addTab("Library", mainPanel);
	        
	        
	        JPanel jPanelSecond = new JPanel();
	        
		       
	        JPanel jPanelSettings = new JPanel(new GridLayout(5, 1));
	        
	        
	        JPanel jBrightnessPanel = new JPanel(new BorderLayout());
	        JSlider brightSlider = new JSlider(0, 255, 64);
	        brightSlider.addChangeListener( new BrightnessListener(screen));
	        //brightSlider.add
	        jBrightnessPanel.add(new JLabel("Brightness:"),BorderLayout.LINE_START);
	        jBrightnessPanel.add(brightSlider,BorderLayout.CENTER);
	        jPanelSettings.add(jBrightnessPanel);
	        
	        
	        JPanel jRatePanel = new JPanel(new BorderLayout());
	        JSlider rateSlider = new JSlider(1, 200, 100); 
	        rateSlider.addChangeListener( new RateListener(screen));
	        jRatePanel.add(new JLabel("Updates Per Second:"),BorderLayout.LINE_START);
	        jRatePanel.add(rateSlider,BorderLayout.CENTER);
	        jPanelSettings.add(jRatePanel);
	        
            
	        Checkbox rotation = new Checkbox(); 
            rotation.setLabel("In Screen Autorotation");
            rotation.setBounds(0, 0,  50, 50);    
            rotation.setState(true);
            rotation.addItemListener(new RotationListener(screen));
            jPanelSettings.add(rotation);
            
            JPanel jRotationPanel = new JPanel(new BorderLayout());
            jRotationPanel.add(new JLabel("Rotation(min):"),BorderLayout.LINE_START);
            String week[]= { "1","2","4","8","16","32","64"};
            JList list = new JList(week); //data has type Object[]
            list.setSelectedIndex(3);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            list.setVisibleRowCount(-1);
            list.addListSelectionListener(new RotationTimeListener(screen));            
            jRotationPanel.add(list,BorderLayout.CENTER);
            jPanelSettings.add(jRotationPanel);
           // JScrollPane listScroller = new JScrollPane(list);
          //  listScroller.setPreferredSize(new Dimension(250, 80));
            
            
            
	        
	        jPanelSecond.setLayout(new BorderLayout());
	       // jPanelSecond.add(new JButton("Page_Start"),BorderLayout.PAGE_START);
	        //jPanelSecond.add(new JButton("Line_End"),BorderLayout.LINE_END);
	      //  jPanelSecond.add(new JButton("Line Start"),BorderLayout.LINE_START);
	        jPanelSecond.add(new JButton("Page_End"),BorderLayout.PAGE_END);
	        jPanelSecond.add(jPanelSettings,BorderLayout.CENTER);
	        

	        
	        jTabbedPane.addTab("Configuration", jPanelSecond);
	       
	 
	        frame.getContentPane().add(BorderLayout.NORTH, menuBar);

	      //  frame.getContentPane().add(BorderLayout.CENTER, tablePanel);
	        
	       // frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
	        frame.getContentPane().add(BorderLayout.CENTER, jTabbedPane);
	       // frame.getContentPane().add(BorderLayout.SOUTH, infoText);

	        
	       

	        frame.setVisible(true);
	        

    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 
        
    
    }
    
}


