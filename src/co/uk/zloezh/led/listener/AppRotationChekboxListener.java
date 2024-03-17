package co.uk.zloezh.led.listener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;

import co.uk.zloezh.led.object.DisplayObject;


public class AppRotationChekboxListener implements ItemListener {

		
	private static Boolean autorotation = false;
	
	public AppRotationChekboxListener(Boolean delay) {
		this.autorotation = delay;
		
		
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) { 
			this.autorotation = true;
        } else { 
        	this.autorotation = false;
        } 
	}

}
