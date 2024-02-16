package co.uk.zloezh.led.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import co.uk.zloezh.led.object.FileImage;
import co.uk.zloezh.led.object.LEDScreen;


public class SendImageListener implements ActionListener{

	private FileImage displayObject;
	private LEDScreen screen;
	
	public SendImageListener(FileImage dObject, LEDScreen cScreen) {
		this.displayObject = dObject;
		this.screen = cScreen;
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		displayObject.render(this.screen);
		
	}

}
