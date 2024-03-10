package co.uk.zloezh.led.listener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;

import co.uk.zloezh.led.object.DisplayObject;
import co.uk.zloezh.led.object.LEDScreen;
import co.uk.zloezh.led.utils.HTTPUtils;


public class RotationListener implements ItemListener {

		
	private LEDScreen screen;
	
	public RotationListener(LEDScreen iScreen) {
		super();
		this.screen = iScreen;
		
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) { 
			//displayObject.setActive(true);
			HTTPUtils.sendCommandViaHttp("/brwsr=1/rotationSwitch" ,screen);
           // System.out.println("Feature is enabled."); 
        } else { 
            // Respond when the checkbox is deselected (unchecked). 
        	HTTPUtils.sendCommandViaHttp("/brwsr=1/rotationSwitch" ,screen);
        	//displayObject.setActive(true);
           // System.out.println("Feature is disabled."); 
        } 
	}

}
