package co.uk.zloezh.led.listener;

import java.awt.event.*;

import javax.swing.JButton;

import co.uk.zloezh.led.object.DisplayObject;

import co.uk.zloezh.led.object.LEDScreen;


public class PauseRenderListener implements ActionListener{

	private DisplayObject displayObject;
	private LEDScreen screen;
	
	public PauseRenderListener(DisplayObject dObject, LEDScreen cScreen) {
		this.displayObject = dObject;
		this.screen = cScreen;
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {

		JButton button = (JButton) e.getSource();
		ResumeRenderListener newistener = new ResumeRenderListener(this.displayObject, this.screen);
		button.setText("Resume");
		button.removeActionListener(this);
		button.addActionListener(newistener);
		try {
			this.displayObject.getRenderThread().pauseThread();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
