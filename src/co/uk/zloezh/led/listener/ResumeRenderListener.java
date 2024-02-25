package co.uk.zloezh.led.listener;

import java.awt.event.*;

import javax.swing.JButton;

import co.uk.zloezh.led.object.DisplayObject;

import co.uk.zloezh.led.object.LEDScreen;


public class ResumeRenderListener implements ActionListener{

	private DisplayObject displayObject;
	private LEDScreen screen;
	
	public ResumeRenderListener(DisplayObject dObject, LEDScreen cScreen) {
		this.displayObject = dObject;
		this.screen = cScreen;
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {

		JButton button = (JButton) e.getSource();
		PauseRenderListener newistener = new PauseRenderListener(this.displayObject, this.screen);
		button.setText("Pause");
		button.removeActionListener(this);
		button.addActionListener(newistener);
		this.displayObject.getRenderThread().resumeThread();
	}

}
