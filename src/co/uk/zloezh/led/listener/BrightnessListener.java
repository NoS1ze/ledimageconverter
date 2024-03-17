package co.uk.zloezh.led.listener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import co.uk.zloezh.led.object.LEDScreen;
import co.uk.zloezh.led.utils.HTTPUtils;

public class BrightnessListener implements ChangeListener {
	private LEDScreen screen;
	
	

	public BrightnessListener(LEDScreen screen) {
		super();
		this.screen = screen;
	}



	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider jSlider = (JSlider)e.getSource();
		
		if (jSlider.getValueIsAdjusting() == false) {
			String responseString = HTTPUtils.sendCommandViaHttp("/cmd=1/parameters?brightness=" + jSlider.getValue() + "&",screen);
			
			/*
			String patternString = "brightness=(\\d+)";
	        
	        Pattern pattern = Pattern.compile(patternString);
	        Matcher matcher = pattern.matcher(responseString);
	        
	        if (matcher.find()) {
	            String brightnessValue = matcher.group(1);
	            //System.out.println("Brightness value: " + brightnessValue);
	        } else {
	            //System.out.println("Brightness value not found.");
	        } */
		}
		
	}

}
