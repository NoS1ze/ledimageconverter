package co.uk.zloezh.listener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;

import co.uk.zloezh.LEDDisplayObject;

public class ActiveCheckBoxListener implements ItemListener {

		
	private LEDDisplayObject displayObject;
	private JButton button;
	
	public ActiveCheckBoxListener(LEDDisplayObject displayObjectC, JButton buttonC) {
		this.displayObject = displayObjectC;
		this.button = buttonC;
		
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) { 
			displayObject.setActive(true);
            System.out.println("Feature is enabled."); 
        } else { 
            // Respond when the checkbox is deselected (unchecked). 
        	displayObject.setActive(true);
            System.out.println("Feature is disabled."); 
        } 
	}

}
