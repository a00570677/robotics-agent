package team39.technical;

import java.util.Random;

import lejos.hardware.Button;
import team39.technical.sensors.ColorSensor;

public class Explorer extends Robot {

	private final float POWER = 300;
	private final float POLE_DISTANCE = 20;
	private final float OSCILLATOR_LOOPS = 150;
	public final int BLUE_ID = 1, RED_ID = 13, WHITE_ID = 6;

	public Explorer() {
		super(ColorSensor.Mode.COLORS);
	}

	public void run() {
		confirm();
		subsumptionArch();
		stop();
	}

	private void avoidEdge1() {
		resetGyro();
		int angle = (new Random()).nextInt(90) + 120;
		rotateUntilAngle(angle, POWER);
	}
	
	private void avoidEdge() {
		resetGyro();
		float angle = rotateUntilBlack();
		gyroSensor.reset();
		rotateUntilAngle(angle, POWER);
	}
	
	private float rotateUntilBlack() {
		while ((int)colorSensor.getSample() == WHITE_ID && Button.ENTER.isUp()) {
			rotate(POWER, false);
		}
		stop();
		return getAngle();
	}

	private void subsumptionArch() {
		float offset = POWER / 2, loopCount = 0;
		boolean goDirectly = false;
		while (Button.ENTER.isUp()) {
			int color = (int) colorSensor.getSample();
			if (getDistance() < POLE_DISTANCE)
				goDirectly = true;
			if (color == BLUE_ID) {
				romanticBehavior();
				goDirectly = false;
			} else if (color == RED_ID) {
				killBehavior();
				goDirectly = false;
			} else if (color == WHITE_ID) {
				avoidEdge();
				goDirectly = false;
			}
			if (!goDirectly)
				advance(POWER + offset, POWER - offset);
			else
				advance(POWER);
			if (loopCount % OSCILLATOR_LOOPS == 0)
				offset *= -1;
			loopCount++;
		}
	}
}
