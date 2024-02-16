package co.uk.zloezh.led.object;

public class LEDFrame {
	private long[] hexArray;
	
	public LEDFrame(long[] initArray) {
		this.hexArray = initArray;
	}

	public long[] getHexArray() {
		return hexArray;
	}

	public void setHexArray(long[] hexArray) {
		this.hexArray = hexArray;
	}
	


}
