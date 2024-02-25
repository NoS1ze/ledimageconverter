package co.uk.zloezh.led.listener;

import java.awt.event.*;

import javax.swing.JButton;

import co.uk.zloezh.led.object.DisplayObject;

import co.uk.zloezh.led.object.LEDScreen;


public class RenderScreenListener implements ActionListener{

	private DisplayObject displayObject;
	private LEDScreen screen;
	
	public RenderScreenListener(DisplayObject dObject, LEDScreen cScreen) {
		this.displayObject = dObject;
		this.screen = cScreen;
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {

		displayObject.render(this.screen);

	}

}
