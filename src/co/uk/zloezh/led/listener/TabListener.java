package co.uk.zloezh.led.listener;

import java.awt.event.ItemEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.*;

import co.uk.zloezh.led.ArduinoLEDTool;
import co.uk.zloezh.led.object.LEDScreen;
import co.uk.zloezh.led.utils.HTTPUtils;

public class TabListener implements ChangeListener {

	private LEDScreen screen;
	
	public TabListener(LEDScreen screen) {
		super();
		this.screen = screen;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
		
		JTabbedPane pane = (JTabbedPane) e.getSource();
        //System.out.println("Selected paneNo : " + pane.getSelectedIndex());
        if(pane.getSelectedIndex() == 1) {
        	String responseString = HTTPUtils.sendCommandViaHttp("/cmd=1" ,screen);
			boolean autoRotation = false;
			int brightness = 1,rate = 1;
			String rotationTime = "";
			
			
			String patternString = "autoRotation=(\\d+)";
	        
	        Pattern pattern = Pattern.compile(patternString);
	        Matcher matcher = pattern.matcher(responseString);
	        
	        if (matcher.find()) {
	            String value = matcher.group(1);
	        //  System.out.println("value: " + value);
	            if(value.equals("1")) {
	            	autoRotation = true;
	            }
	            
	        } else {
	            //System.out.println("Brightness value not found.");
	        } 
	        
	        patternString = "brightness=(\\d+)";
	        pattern = Pattern.compile(patternString);
	        matcher = pattern.matcher(responseString);
	        
	        if (matcher.find()) {
	            String value = matcher.group(1);
	            brightness = Integer.valueOf(value);
	            
	        } else {
	            //System.out.println("Brightness value not found.");
	        } 
	        
	        patternString = "refreshRate=(\\d+)";
	        pattern = Pattern.compile(patternString);
	        matcher = pattern.matcher(responseString);
	        
	        if (matcher.find()) {
	            String value = matcher.group(1);
	            rate = Integer.valueOf(value);
	            
	        } else {
	            //System.out.println("Brightness value not found.");
	        } 
	        
	        patternString = "rotationTime=(\\d+)";
	        pattern = Pattern.compile(patternString);
	        matcher = pattern.matcher(responseString);
	        
	        if (matcher.find()) {
	            String value = matcher.group(1);
	            rotationTime = value;
	            
	        } else {
	            //System.out.println("Brightness value not found.");
	        } 
	        
	        
	        ArduinoLEDTool.setPropertiesTab(brightness, rate, autoRotation, rotationTime);
        }
	}

}
