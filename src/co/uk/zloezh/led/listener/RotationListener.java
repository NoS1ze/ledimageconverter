package co.uk.zloezh.led.listener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import co.uk.zloezh.led.ArduinoLEDTool;
import co.uk.zloezh.led.object.DisplayObject;
import co.uk.zloezh.led.object.LEDScreen;
import co.uk.zloezh.led.utils.HTTPUtils;


public class RotationListener implements ActionListener {

		
	private LEDScreen screen;
	
	public RotationListener(LEDScreen iScreen) {
		super();
		this.screen = iScreen;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e)  {
		String responseString = "";
		
		//if (e.getStateChange() == ItemEvent.SELECTED) { 
			//displayObject.setActive(true);
	//		responseString = HTTPUtils.sendCommandViaHttp("/cmd=1/rotationSwitch" ,screen);
           // System.out.println("Feature is enabled."); 
      //  } else { 
            // Respond when the checkbox is deselected (unchecked). 
        	responseString = HTTPUtils.sendCommandViaHttp("/cmd=1/rotationSwitch" ,screen);
        	//displayObject.setActive(true);
           // System.out.println("Feature is disabled."); 
       // } 
		
		
        	boolean autoRotation = false;
        	String patternString = "autoRotation=(\\d+)";
            
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(responseString);
            
            if (matcher.find()) {
                String value = matcher.group(1);
              System.out.println("value: " + value);
                if(value.equals("1")) {
                	autoRotation = true;
                }
                
            } else {
                //System.out.println("Brightness value not found.");
            } 
            
            ArduinoLEDTool.setScreenAutoRotation(autoRotation);
	}

}
