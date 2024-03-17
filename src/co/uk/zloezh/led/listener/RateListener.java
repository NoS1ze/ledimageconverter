package co.uk.zloezh.led.listener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import co.uk.zloezh.led.object.LEDScreen;
import co.uk.zloezh.led.utils.HTTPUtils;

public class RateListener implements ChangeListener {
	private LEDScreen screen;
	
	

	public RateListener(LEDScreen screen) {
		super();
		this.screen = screen;
	}



	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider jSlider = (JSlider)e.getSource();
		
		if (jSlider.getValueIsAdjusting() == false) {
			HTTPUtils.sendCommandViaHttp("/cmd=1/parameters?updates=" + jSlider.getValue() + "&",screen);
		}
		
	}

}
