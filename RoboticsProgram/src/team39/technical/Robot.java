package team39.technical;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.*;
import lejos.utility.Delay;
import team39.technical.sensors.ColorSensor;
import team39.technical.sensors.DistanceSensor;
import team39.technical.sensors.GyroSensor;
import team39.technical.sensors.ColorSensor.Mode;

public abstract class Robot {
	protected EV3LargeRegulatedMotor mLeft;
	protected EV3LargeRegulatedMotor mRight;
	protected ColorSensor colorSensor;
	protected GyroSensor gyroSensor;
	protected DistanceSensor distanceSensor;

	public Robot(Mode mode) {
		colorSensor = new ColorSensor(SensorPort.S3, mode);
		gyroSensor = new GyroSensor(SensorPort.S4);
		distanceSensor = new DistanceSensor(SensorPort.S2);
		
		mLeft = new EV3LargeRegulatedMotor(MotorPort.D);
		mRight = new EV3LargeRegulatedMotor(MotorPort.A);
	}

	public abstract void run();

	protected void advance(float powerLeft, float powerRight) {
		mLeft.setSpeed(powerLeft);
		mRight.setSpeed(powerRight);
		mLeft.forward();
		mRight.forward();
	}

	protected void advance(float power) {
		advance(power, power);
	}

	protected void rotateUntilAngle(double angle, float power) {
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
		while(Button.ENTER.isUp() && getDistance() > distance)
			rotate(power, false);
		stop();
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

	protected void stop() {
		mLeft.stop();
		mRight.stop();
	}

	protected void resetGyro() {
		stop();
		gyroSensor.reset();
	}

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

	public void confirm() {
		System.out.println("Press button to start.");
		Button.waitForAnyPress();
		System.out.println("Running...");
		Delay.msDelay(1000);
		gyroSensor.reset();
	}

}
