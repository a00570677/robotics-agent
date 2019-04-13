package team39.technical;

import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.hardware.Button;

public class LineFollower {
	private PID controller;
	private EV3LargeRegulatedMotor mLeft;
	private EV3LargeRegulatedMotor mRight;
	private ColorSensor colorSensor;
	private GyroSensor gyroSensor;

	public LineFollower() {
		colorSensor = new ColorSensor(SensorPort.S1);
		gyroSensor = new GyroSensor(SensorPort.S2);
		mLeft = new EV3LargeRegulatedMotor(MotorPort.D);
		mRight = new EV3LargeRegulatedMotor(MotorPort.A);
		calibrate();
	}

	public void run() {
		System.out.println("Press button to run.");
		Button.waitForAnyPress();
		System.out.println("Running...");
		float turn, powerLeft, powerRight;
		while (Button.ENTER.isUp()) {
			turn = controller.getTurn(colorSensor.getSample());
			powerLeft = PID.TARGET_POWER + turn;
			powerRight = PID.TARGET_POWER - turn;
			advance(powerLeft, powerRight);
		}
	}

	private void advance(float powerLeft, float powerRight) {
		mLeft.setSpeed(powerLeft);
		mRight.setSpeed(powerRight);
		mLeft.forward();
		mRight.forward();
	}

	private void calibrate() {
		calibrateColor();
		calibrateGyro();
		System.out.println("Press button to start.");
		Button.waitForAnyPress();
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

	private float getColor() {
		float colorValue = 0;
		while (Button.ENTER.isUp()) {
			colorValue = colorSensor.getSample();
		}
		return colorValue;
	}
}
