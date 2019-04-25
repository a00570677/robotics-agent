package team39.technical;

import java.util.Random;

import lejos.hardware.Button;

public class Explorer extends Robot {
	
	private final float POWER = 80;

	public Explorer() {
		super(ColorSensor.Mode.COLORS);
	}

	public void run() {
		confirm();
		// CHECK COLOR SENSOR
		while (Button.ENTER.isUp()) {
			int color = (int) colorSensor.getSample();
			System.out.println(color);
			switch (color) {
			case 2:
				romanticBehavior();
				break;
			case 0:
				killBehavior();
				break;
			case 6:
				avoidEdge();
				break;
			}
			advance(POWER);
		}
	}
	
	public void avoidEdge() {
		// CHECK GYRO SENSOR
		stop();
		gyroSensor.reset();
		int angle = (new Random()).nextInt(20) + 170;
		//int angle = 180;
		while(gyroSensor.getSample() < angle && Button.ENTER.isUp()) {
			rotate(POWER, true);
		}
		stop();
	}
}
