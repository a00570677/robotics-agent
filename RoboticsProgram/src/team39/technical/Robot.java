package team39.technical;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.*;
import lejos.utility.Delay;
import team39.technical.sensors.ColorSensor;
import team39.technical.sensors.DistanceSensor;
import team39.technical.sensors.GyroSensor;
import team39.technical.sensors.ColorSensor.Mode;

public abstract class Robot {
	protected EV3LargeRegulatedMotor mLeft;
	protected EV3LargeRegulatedMotor mRight;
	public ColorSensor colorSensor;
	protected GyroSensor gyroSensor;
	protected DistanceSensor distanceSensor;
	static EV3MediumRegulatedMotor claw;

	public Robot(Mode mode) {
		colorSensor = new ColorSensor(SensorPort.S4, mode);
		gyroSensor = new GyroSensor(SensorPort.S1);
		distanceSensor = new DistanceSensor(SensorPort.S2);

		mLeft = new EV3LargeRegulatedMotor(MotorPort.D);
		mRight = new EV3LargeRegulatedMotor(MotorPort.A);
		claw = new EV3MediumRegulatedMotor(MotorPort.C);
		claw.setSpeed(100);
	}

	public abstract void run();

	// MOVING METHODS
	// ----------------------------------------------------------------------------------

	protected void advance(float powerLeft, float powerRight) {
		mLeft.setSpeed(powerLeft);
		mRight.setSpeed(powerRight);
		mLeft.forward();
		mRight.forward();
	}

	public void advance(float power) {
		advance(power, power);
	}

	protected void rotate(float power, boolean clock) {
		mLeft.setSpeed(power);
		mRight.setSpeed(power);
		if (clock) {
			mLeft.forward();
			mRight.backward();
		} else {
			mLeft.backward();
			mRight.forward();
		}
	}

	public void rotateUntilAngle(double angle, float power) {
		if (angle >= 0) {
			while (gyroSensor.getSample() < angle && Button.ENTER.isUp()) {
				rotate(power, false);
			}
		} else {
			while (gyroSensor.getSample() > angle && Button.ENTER.isUp()) {
				rotate(power, true);
			}
		}
		stop();
	}

	public void rotateUntilDistance(float distance, float power) {
		while (Button.ENTER.isUp() && getDistance() > distance)
			rotate(power, false);
		stop();
	}

	public float seekClosestAngle() {
		resetGyro();
		float min = 1000, current, angle = 0;
		while (gyroSensor.getSample() < 360 && Button.ENTER.isUp()) {
			rotate(50, false);
			current = getDistance();
			if (current < min) {
				angle = getAngle();
				min = current;
			}
		}
		return angle;
	}

	public void stop() {
		mLeft.stop();
		mRight.stop();
	}

	// SENSOR METHODS
	// ----------------------------------------------------------------------------------

	public void resetGyro() {
		stop();
		gyroSensor.reset();
	}

	protected float getColor(String color) {
		System.out.println("Press for " + color + " color...");
		float colorValue = 0;
		while (Button.ENTER.isUp()) {
			colorValue = colorSensor.getSample();
		}
		System.out.println("Value of " + color + " : " + colorValue);
		Delay.msDelay(500);
		return colorValue;
	}

	protected float getAngle() {
		return gyroSensor.getSample();
	}

	protected float getDistance() {
		return distanceSensor.getSample();
	}

	// CLAW METHODS
	// ----------------------------------------------------------------------------------

	public void getFood() {
		if (getDistance() < 5) {
			closeClaw();
		}
	}

	public void closeClaw() {
		int counter = 0;
		while (Button.ENTER.isUp() && counter < 7500) {
			claw.backward();
			counter++;
		}
		claw.stop();
	}

	public void openClaw() {
		int counter = 0;
		while (Button.ENTER.isUp() && counter < 7500) {
			claw.forward();
			counter++;
		}
		claw.stop();
	}

	// BEHAVIOUR METHODS
	// ----------------------------------------------------------------------------------

	protected void killBehavior() {
		stop();
		Sound.systemSound(false, 2);
		Delay.msDelay(2000);
	}

	protected void romanticBehavior() {
		stop();
		Sound.systemSound(false, 3);
		Delay.msDelay(2000);
	}

	public void blueBehavior() {
		Sound.systemSound(false, 3);
		resetGyro();
		rotateUntilAngle(360, 300);
	}

	public void confirm() {
		System.out.println("Press button to start.");
		Button.waitForAnyPress();
		System.out.println("Running...");
		Delay.msDelay(1000);
		gyroSensor.reset();
	}

}
