package team39.technical;

import lejos.hardware.Button;

public class Explorer extends Robot{
	
	public Explorer() {
		super(ColorSensor.Mode.COLORS);
	}
	
	public void run() {
		confirm();
		while (Button.ENTER.isUp()) {
			
		}
	}
}
