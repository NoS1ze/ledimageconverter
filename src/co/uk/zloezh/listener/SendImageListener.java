package co.uk.zloezh.listener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import co.uk.zloezh.LEDDisplayObject;

public class SendImageListener implements ItemListener{

	private LEDDisplayObject displayObject;
	
	public SendImageListener(LEDDisplayObject dObject) {
		this.displayObject = dObject;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

}
