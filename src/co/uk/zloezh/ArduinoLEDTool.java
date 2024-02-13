package co.uk.zloezh;

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

import co.uk.zloezh.listener.ActiveCheckBoxListener;
import co.uk.zloezh.listener.GeneratePixelArrayListener;
import co.uk.zloezh.listener.SendImageListener;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.GridLayout;

public class ArduinoLEDTool extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JLabel label;
	static JTextField t;
	static File  pixelFile; 
	static JTextField txtW,txtH;
	private static Properties properties;
	private static List<LEDDisplayObject> objectList;
	
	ArduinoLEDTool()
    {
		properties = new Properties();
		try {
            FileInputStream inputStream = new FileInputStream("config.properties");
            properties.load(inputStream);
            inputStream.close();
            
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
                        LEDDisplayObject object = new LEDDisplayObject(file.getCanonicalPath());
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
	        
	        
	        
	        JMenuBar menuBar = new JMenuBar();
	        JMenu mUtils = new JMenu("Utils");
	        //JMenu mHelp = new JMenu("Help");
	        menuBar.add(mUtils);
	        //menuBar.add(mHelp);
	        JMenuItem genArray = new JMenuItem("Convert Image");
	       // JMenuItem m22 = new JMenuItem("Save as");
	        mUtils.add(genArray);
	       // mHelp.add(m22); 
	        GeneratePixelArrayListener menuListener = new GeneratePixelArrayListener();
	        
	        mUtils.addActionListener(menuListener);
	        
	
	        JPanel mainPanel = new JPanel(new BorderLayout());

	        JPanel tablePanel = new JPanel(new GridLayout(objectList.size() + 1 , 1)); // +1 for header row
	        
	        JPanel labelPane = new JPanel(new GridLayout(1, 4));
	        labelPane.add(new JLabel("Image:"));
	        labelPane.add(new JLabel("Name:"));
	        labelPane.add(new JLabel("Active:"));
	        labelPane.add(new JLabel("Action:"));
	        tablePanel.add(labelPane);


	        for (LEDDisplayObject item : objectList) {
	                
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
	       
	 
	        frame.getContentPane().add(BorderLayout.NORTH, menuBar);
	       // frame.getContentPane().add(BorderLayout.NORTH, topPanel);
	       // frame.getContentPane().add(BorderLayout.CENTER, ta);
	        frame.getContentPane().add(BorderLayout.CENTER, tablePanel);
	        frame.getContentPane().add(BorderLayout.SOUTH, panel);
	        
	        
	        

	        frame.setVisible(true);
	        
	        
	        
			if(args.length >=1 ) {
				pixelFile = new File( args[0]);  
	            label.setText(pixelFile.getName());

			}
    
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
				            String hex = String.format("0x%02x%02x%02x", red, green, blue);  
				            writer.append(hex+",");
				            long rgbValue = ((long) red << 16) | ((long) green << 8) | blue;
				            hexArray[i++] = rgbValue;
				         
				         }
				      }
					//writer.flush();
					
					LedImageConverter ledUtils = new LedImageConverter(writer, hexArray, img.getWidth(), img.getHeight());
					ledUtils.writeLedString();
					ledUtils.writeShapeString();
					
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


