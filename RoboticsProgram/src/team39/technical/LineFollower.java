package team39.technical;

import lejos.hardware.Button;
import team39.technical.sensors.ColorSensor;

public class LineFollower extends Robot {

	public final float POWER;
	public final float TURN_TRESHOLD;
	public final int BLUE_ID = 1, RED_ID = 13;
	public float redColor, blueColor, initialAngle;
	private PID controller;

	public LineFollower(float power, float treshold) {
		super(ColorSensor.Mode.RED);
		POWER = power;
		TURN_TRESHOLD = treshold;
		calibrate();
	}

	// MOVING METHODS
	// ----------------------------------------------------------------------------------

	public void approachLine() {
		while (colorSensor.getSample() < controller.AVERAGE_COLOR && Button.ENTER.isUp())
			advance(POWER);
		stop();
	}

	public void changeLane() {
		rotateUntilAngle(45, POWER);
		stop();
	}

	public void run() {
		while (Button.ENTER.isUp())
			run(false);
	}

	private void run(boolean left) {
		float turn;
		// float powerLeft, powerRight;
		turn = controller.getTurn(colorSensor.getSample());
		if (turn > TURN_TRESHOLD)
			rotate(POWER, left);
		else if (turn < -TURN_TRESHOLD)
			rotate(POWER, !left);
		else {
			// powerLeft = POWER + turn;
			// powerRight = POWER - turn;
			// advance(powerLeft, powerRight);
			advance(POWER);
		}
	}

	public void runPID() {
		while (Button.ENTER.isUp()) {
			float turn;
			float powerLeft, powerRight;
			turn = controller.getTurn(colorSensor.getSample());
			powerLeft = POWER + turn;
			powerRight = POWER - turn;
			advance(powerLeft, powerRight);
		}
	}

	public void runUntilAngle(float angle, boolean left) {
		while (Button.ENTER.isUp() && getAngle() < angle) {
			run(left);
		}
		resetGyro();
	}

	public void runUntilDistance(float distance, boolean left) {
		while (Button.ENTER.isUp() && getDistance() > distance) {
			run(left);
		}
		stop();
	}

	public void runUntilBlue() {
		while (Button.ENTER.isUp() && colorSensor.getSample() != BLUE_ID) {
			advance(POWER);
		}
		stop();
	}

	public boolean runUntilColor() {
		while (Button.ENTER.isUp() && colorSensor.getSample() != BLUE_ID && colorSensor.getSample() != RED_ID) {
			advance(POWER);
		}
		stop();
		return (int) colorSensor.getSample() == RED_ID;
	}

	// CALIBRATION METHODS
	// ----------------------------------------------------------------------------------

	private void calibrate() {
		calibrateColor();
		calibrateGyro();
	}

	private void calibrateColor() {
		float whiteColor = getColor("white");
		float blackColor = getColor("black");
		float averageColor = (whiteColor + blackColor) / 2;
		System.out.println("Average: " + averageColor);
		controller = new PID(blackColor, whiteColor, averageColor, POWER);
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
