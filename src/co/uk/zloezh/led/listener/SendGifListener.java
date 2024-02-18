package co.uk.zloezh.led.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import co.uk.zloezh.led.object.DisplayObject;

import co.uk.zloezh.led.object.LEDScreen;


public class SendGifListener implements ActionListener{

	private DisplayObject displayObject;
	private LEDScreen screen;
	
	public SendGifListener(DisplayObject dObject, LEDScreen cScreen) {
		this.displayObject = dObject;
		this.screen = cScreen;
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		displayObject.render(this.screen);
		
	}

}
