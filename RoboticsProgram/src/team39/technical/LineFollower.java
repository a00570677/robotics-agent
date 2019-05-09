package team39.technical;

import lejos.hardware.Button;

public class LineFollower extends Robot {

	public final float POWER;
	public final float TURN_TRESHOLD = 130;
	public float redColor, blueColor;
	private PID controller;

	public LineFollower(boolean withColors, float power) {
		super(ColorSensor.Mode.RED);
		POWER  = power;
		calibrate(withColors);
	}

	public void changeLane() {
		rotateUntilAngle(45, POWER);
		stop();
	}

	public void run() {
		run(true);
	}
	

	public void approach() {
		while(colorSensor.getSample() < controller.WHITE_COLOR && Button.ENTER.isUp())
			advance(POWER);
		stop();
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
		while (Button.ENTER.isUp() && getAngle() < angle) {
			run(right);
		}
		resetGyro();
	}
	
	public void runUntilBlue() {
		while (Button.ENTER.isUp() && colorSensor.getSample() < blueColor + 10 && colorSensor.getSample() > blueColor - 10 ) {
			advance(POWER);
		}
		stop();
	}

	private void calibrate(boolean withColors) {
		calibrateColor(withColors);
		calibrateGyro();
	}

	private void calibrateColor(boolean withColors) {
		System.out.println("Press for white color...");
		float whiteColor = getColor();
		System.out.println("Value of white : " + whiteColor);
		System.out.println("Press for black color...");
		float blackColor = getColor();
		System.out.println("Value of black : " + blackColor);
		float averageColor = (whiteColor + blackColor) / 2;
		System.out.println("Average: " + averageColor);
		if (withColors) {
			System.out.println("Press for red color...");
			redColor = getColor();
			System.out.println("Value of red : " + redColor);
			System.out.println("Press for blue color...");
			blueColor = getColor();
			System.out.println("Value of blue : " + blueColor);
		}
		controller = new PID(blackColor, whiteColor, averageColor, POWER);
	}

	private void calibrateGyro() {
		System.out.println("Press for initial angle...");
		Button.waitForAnyPress();
		gyroSensor.reset();
		System.out.println("Gyro sensor calibrated.");
	}
}
