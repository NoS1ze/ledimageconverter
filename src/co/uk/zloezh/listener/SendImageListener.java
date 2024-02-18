package co.uk.zloezh.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import co.uk.zloezh.LEDDisplayFile;

public class SendImageListener implements ActionListener{

	private LEDDisplayFile displayObject;
	
	public SendImageListener(LEDDisplayFile dObject) {
		this.displayObject = dObject;
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
