package co.uk.zloezh.led.listener;

import javax.swing.JList;
import javax.swing.JSlider;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import co.uk.zloezh.led.object.LEDScreen;
import co.uk.zloezh.led.utils.HTTPUtils;

public class RotationTimeListener implements ListSelectionListener {

	private LEDScreen screen;
	
	
	public RotationTimeListener(LEDScreen screen) {
		super();
		this.screen = screen;
	}


	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList list = (JList)e.getSource();
		Integer time = (Integer.valueOf((String)list.getSelectedValue()))*1000*60;
		HTTPUtils.sendCommandViaHttp("/brwsr=1/parameters?rotationTime=" + time + " ",screen);
		///brwsr=1/parameters?brightness=6&updates=100&rotationTime=600001

	}

}
