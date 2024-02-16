package co.uk.zloezh.led.object;

public class LEDScreen {
	public String iPAdress;
	public String direction;
	public int width;
	public int hight;
	public int brightness = 64;
	
	public LEDScreen(String cIPAdress, int cWidth, int cHight, String cDirection ) {
		this.width = cWidth;
		this.hight = cHight;
		this.iPAdress = cIPAdress;
		this.direction = cDirection;
		
	}

}
