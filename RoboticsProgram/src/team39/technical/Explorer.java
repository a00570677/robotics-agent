package team39.technical;

import java.util.Random;

import lejos.hardware.Button;
import team39.technical.sensors.ColorSensor;

public class Explorer extends Robot {

	private final float POWER = 150;
	public int whiteColor, redColor, blueColor;

	public Explorer() {
		super(ColorSensor.Mode.RED);
		calibrateColor();
	}

	public void run() {
		confirm();
		float offset = POWER/2, count = 0;
		while (Button.ENTER.isUp()) {
			int color = (int) colorSensor.getSample();
			if (color == blueColor)
				romanticBehavior();
			else if (color == redColor)
				killBehavior();
			else if (color == whiteColor)
				avoidEdge();
			advance(POWER + offset, POWER - offset);
			if (count%20==0)
				offset *= -1;
			count++;
		}
		stop();
	}
	
	private void avoidEdge() {
		stop();
		gyroSensor.reset();
		int angle = (new Random()).nextInt(30) + 160;
		rotateUntilAngle(angle, POWER);
	}
	
	private void calibrateColor() {
		whiteColor = (int) getColor("white");
		redColor = (int) getColor("red");
		blueColor = (int) getColor("blue");
	}
}
