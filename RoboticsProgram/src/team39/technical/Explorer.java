package team39.technical;

import java.util.Random;

import lejos.hardware.Button;

public class Explorer extends Robot {

	private final float POWER = 150;

	public Explorer() {
		super(ColorSensor.Mode.COLORS);
	}

	public void run() {
		confirm();
		float offset = 0, change = 1;
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
			advance(POWER + offset, POWER - offset);
			if (offset >= POWER || offset <= 0)
				change *= -1;
			offset += change;
		}
		stop();
	}
	

	private void avoidEdge() {
		stop();
		gyroSensor.reset();
		int angle = (new Random()).nextInt(20) + 170;
		rotateUntilAngle(angle, POWER);
	}
	
	public void print() {
		resetGyro();
		while(Button.ENTER.isUp())
			System.out.println(getAngle());
	}
}
