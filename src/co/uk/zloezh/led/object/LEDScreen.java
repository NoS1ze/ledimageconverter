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

	public String getiPAdress() {
		return iPAdress;
	}

	public void setiPAdress(String iPAdress) {
		this.iPAdress = iPAdress;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHight() {
		return hight;
	}

	public void setHight(int hight) {
		this.hight = hight;
	}

	
}
