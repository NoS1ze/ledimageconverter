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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import java.util.Map;


import co.uk.zloezh.led.listener.*;
import co.uk.zloezh.led.object.*;


import java.awt.BorderLayout;
//import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

public class ArduinoLEDTool extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JLabel label;
	static JTextField t;
	static File  pixelFile; 
	//static JTextField txtW,txtH;
	private static JSlider brightSlider,rateSlider;
	private static JCheckBox rotationChekBox;
	private static JList rotationTime;
	
	//private static Properties properties;
	private static List<DisplayObject> objectList;
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
            
            
            
            String filePath = "appfiles/commands.json";

            // Create ObjectMapper instance
            ObjectMapper mapper = new ObjectMapper();


            Map<String, List<Map<String, String>>> jsonData = mapper.readValue(new File(filePath), Map.class);

            // Extracting the "commands" array
            List<Map<String, String>> commands = jsonData.get("commands");

            // Displaying each command
            for (Map<String, String> command : commands) {
                String id = command.get("id");
                String commandString = command.get("command");
                System.out.println("ID: " + id + ", Command: " + commandString);
                DisplayCommand cmd = new DisplayCommand(id,commandString);
                objectList.add(cmd);
                logger.debug("Added to Array: " + cmd);
            }
            
           
        } catch (IOException e) {
            System.err.println("Error loading properties: " + e.getMessage());
           
        }
    }
	
	
	public static void setPropertiesTab(int brightness, int rate, boolean rotation, String rotationTime) {
		brightSlider.setValue(brightness);
		rateSlider.setValue(rate);
		rotationChekBox.setSelected(rotation);
		logger.debug("Protperties Tab: brightess->{}, refresh rate->{}, autorotation->{}, rotation time->{}.", brightness, rate, rotation, rotationTime);
		//rotationTime.
	}
	
	public static void setScreenAutoRotation(boolean rotation) {
		rotationChekBox.setSelected(rotation);
		logger.debug("Protperties Tab: autorotation->{}.", rotation);
		//rotationTime.
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


	        for (DisplayObject item : objectList) {
	                
	                JPanel rowPanel = new JPanel(new GridLayout(1, 4));
	                rowPanel.setPreferredSize(new Dimension(50,50));
	                BufferedImage img= ImageIO.read(item.getIconFile());
	                Image image = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
	                ImageIcon icon=new ImageIcon(image);
	                JLabel imgLabel = new JLabel();
	                imgLabel.setIcon(icon);
	                imgLabel.setBounds(0, 0,  50, 50);   
	                rowPanel.add(imgLabel);
	                rowPanel.add(new JLabel(item.getName()));

	                

	                
	                
	                JButton actionButton = null;
	                if(item instanceof DisplayGifFile) {
	                	actionButton = new JButton("Play");
	                	//actionButton.setPreferredSize(new Dimension(40, 40));
	                	//actionButton.setMaximumSize(new Dimension(40, 40));
	                	//playButtons.put(item, actionButton);
	                	SendGifListener bListener = new SendGifListener(item, ArduinoLEDTool.screen);
	                	actionButton.addActionListener(bListener); 
	                }else if((item instanceof DisplayImageFile) || (item instanceof DisplayCommand)){
	                	actionButton = new JButton("Set");
	                	//actionButton.setPreferredSize(new Dimension(40, 40));
		                RenderScreenListener bListener = new RenderScreenListener(item, ArduinoLEDTool.screen);
	                	actionButton.addActionListener(bListener);   
	                }
	                //RenderScreenListener bListener = new RenderScreenListener(item, ArduinoLEDTool.screen);
                	//actionButton.addActionListener(bListener); 
	                
	                JCheckBox  checkbox = new JCheckBox ();    
	                checkbox.setBounds(0, 0,  50, 50);    
	                checkbox.setSelected(item.isActive());
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
	        

	        
	        
	        JPanel jPanelSecond = new JPanel();
	        
		       
	        JPanel jPanelSettings = new JPanel(new GridLayout(5, 1));
	        
	        
	        JPanel jBrightnessPanel = new JPanel(new BorderLayout());
	        brightSlider = new JSlider(0, 255, 64);
	        brightSlider.addChangeListener( new BrightnessListener(screen));
	        //brightSlider.add
	        jBrightnessPanel.add(new JLabel("Brightness:"),BorderLayout.LINE_START);
	        jBrightnessPanel.add(brightSlider,BorderLayout.CENTER);
	        jPanelSettings.add(jBrightnessPanel);
	        
	        
	        JPanel jRatePanel = new JPanel(new BorderLayout());
	        rateSlider = new JSlider(1, 200, 100); 
	        rateSlider.addChangeListener( new RateListener(screen));
	        jRatePanel.add(new JLabel("Updates Per Second:"),BorderLayout.LINE_START);
	        jRatePanel.add(rateSlider,BorderLayout.CENTER);
	        jPanelSettings.add(jRatePanel);
	        
	        JPanel jAutoRotationPanel = new JPanel(new BorderLayout());
	        
	       // jPanelSecond.add(new JButton("Page_End"),BorderLayout.PAGE_END);
	        rotationChekBox = new JCheckBox(); 
	        rotationChekBox.setLabel("In Screen Autorotation");
	        rotationChekBox.setBounds(0, 0,  50, 50);    
	        rotationChekBox.setSelected(true);
	        rotationChekBox.setEnabled(false);
	        JButton switchButton = new JButton("Switch");
	        switchButton.addActionListener(new RotationListener(screen));
	        //rotationChekBox.addItemListener(new RotationListener(screen));
            //jPanelSettings.add(rotationChekBox);
	        jAutoRotationPanel.add(switchButton,BorderLayout.LINE_START);
	        jAutoRotationPanel.add(rotationChekBox,BorderLayout.CENTER);
	        jPanelSettings.add(jAutoRotationPanel);
            
            JPanel jRotationPanel = new JPanel(new BorderLayout());
            jRotationPanel.add(new JLabel("Rotation(min):"),BorderLayout.LINE_START);
            String week[]= { "1","2","4","8","16","32","64"};
            rotationTime = new JList(week); //data has type Object[]
            rotationTime.setSelectedIndex(3);
            rotationTime.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            rotationTime.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            rotationTime.setVisibleRowCount(-1);
            rotationTime.addListSelectionListener(new RotationTimeListener(screen));            
            jRotationPanel.add(rotationTime,BorderLayout.CENTER);
            jPanelSettings.add(jRotationPanel);
           // JScrollPane listScroller = new JScrollPane(list);
          //  listScroller.setPreferredSize(new Dimension(250, 80));
            
            
            
	        
	        jPanelSecond.setLayout(new BorderLayout());
	       // jPanelSecond.add(new JButton("Page_Start"),BorderLayout.PAGE_START);
	        //jPanelSecond.add(new JButton("Line_End"),BorderLayout.LINE_END);
	      //  jPanelSecond.add(new JButton("Line Start"),BorderLayout.LINE_START);
	      //  jPanelSecond.add(new JButton("Page_End"),BorderLayout.PAGE_END);
	        jPanelSecond.add(jPanelSettings,BorderLayout.CENTER);
	        

	        JTabbedPane jTabbedPane = new JTabbedPane();
	        jTabbedPane.addChangeListener(new TabListener(screen));
	        //JPanel jPanelFirst = new JPanel();

	        //jPanelFirst.setLayout(null);
	        jTabbedPane.addTab("Library", mainPanel);
	        
	        jTabbedPane.addTab("Screen Configuration", jPanelSecond);
	 
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


