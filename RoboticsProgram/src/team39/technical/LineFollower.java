package team39.technical;

import lejos.hardware.Button;

public class LineFollower extends Robot {
	private PID controller;

	public LineFollower() {
		super(ColorSensor.Mode.RED);
		calibrate();
	}

	public void run() {
		confirm();
		float turn, powerLeft, powerRight;
		while (Button.ENTER.isUp()) {
			turn = controller.getTurn(colorSensor.getSample());
			System.out.println(turn);
			powerLeft = PID.TARGET_POWER + turn;
			powerRight = PID.TARGET_POWER - turn;
			advance(powerLeft, powerRight);
		}
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
		controller = new PID(blackColor, whiteColor, averageColor);
	}

	private void calibrateGyro() {
		System.out.println("Press for initial angle...");
		Button.waitForAnyPress();
		gyroSensor.reset();
		System.out.println("Gyro sensor calibrated.");
	}
}
