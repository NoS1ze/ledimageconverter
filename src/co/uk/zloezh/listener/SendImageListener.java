package co.uk.zloezh.listener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import co.uk.zloezh.LEDDisplayFile;

public class SendImageListener implements ItemListener{

	private LEDDisplayFile displayObject;
	
	public SendImageListener(LEDDisplayFile dObject) {
		this.displayObject = dObject;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

}
