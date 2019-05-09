package team39.technical;

import lejos.hardware.Button;

public class LineFollower extends Robot {

	public final float POWER = 200;
	public final float TURN_TRESHOLD = 130;
	private float initialAngle;
	private PID controller;

	public LineFollower() {
		super(ColorSensor.Mode.RED);
		calibrate();
	}

	public double getInitialAngle() {
		return initialAngle;
	}

	public void changeLane() {
		rotateUntilAngle(-30, POWER);
		boolean check1 = false, check2 = false;
		while (!check2) {
			float turn = controller.getTurn(colorSensor.getSample());
			if (turn > TURN_TRESHOLD)
				check1 = true;
			else if (check1 && turn < -TURN_TRESHOLD)
				check2 = true;
			advance(POWER);
		}
		stop();
	}

	public void run() {
		run(true);
	}

	public void run(boolean right) {
		float turn;
//		float powerLeft, powerRight;
		turn = controller.getTurn(colorSensor.getSample());
		if (turn > TURN_TRESHOLD)
			rotate(POWER, right);
		else if (turn < -TURN_TRESHOLD)
			rotate(POWER, !right);
		else {
//			powerLeft = POWER + turn;
//			powerRight = POWER - turn;
//			advance(powerLeft, powerRight);
			advance(POWER);
		}
	}

	public void runUntilAngle(float angle, boolean right) {
		while (Button.ENTER.isUp() && getAngle() > angle) {
			run(right);
		}
		resetGyro();
	}

	private void calibrate() {
		calibrateColor();
		calibrateGyro();
	}

	private void calibrateColor() {
		System.out.println("Press for white color...");
		float whiteColor = getColor();
		System.out.println("Value of white : " + whiteColor);
		System.out.println("Press for black color...");
		float blackColor = getColor();
		System.out.println("Value of black : " + blackColor);
		float averageColor = (whiteColor + blackColor) / 2;
		System.out.println("Average: " + averageColor);
		controller = new PID(blackColor, averageColor, POWER);
	}

	private void calibrateGyro() {
		System.out.println("Press for orientation angle...");
		Button.waitForAnyPress();
		gyroSensor.reset();
		System.out.println("Press for initial angle...");
		Button.waitForAnyPress();
		initialAngle = getAngle();
		gyroSensor.reset();
		System.out.println("Gyro sensor calibrated.");
	}
}
