package team39.technical;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.*;
import lejos.utility.Delay;
import team39.technical.ColorSensor.Mode;

public abstract class Robot {
	protected EV3LargeRegulatedMotor mLeft;
	protected EV3LargeRegulatedMotor mRight;
	protected ColorSensor colorSensor;
	protected GyroSensor gyroSensor;
	
	public Robot(Mode mode) {
		colorSensor = new ColorSensor(SensorPort.S1, ColorSensor.Mode.COLORS);
		gyroSensor = new GyroSensor(SensorPort.S2);
		mLeft = new EV3LargeRegulatedMotor(MotorPort.D);
		mRight = new EV3LargeRegulatedMotor(MotorPort.A);
	}
	
	abstract void run();
	
	protected void advance(float powerLeft, float powerRight) {
		mLeft.stop();
		mRight.stop();
		mLeft.setSpeed(powerLeft);
		mRight.setSpeed(powerRight);
		mLeft.forward();
		mRight.forward();
	}
	
	protected void advance(float power) {
		advance(power, power);
	}
	
	protected void killBehavior() {
		Sound.systemSound(false, 2);
		
	}
	
	protected void romanticBehavior() {
		Sound.systemSound(false, 3);
	}
	
	protected float getColor() {
		float colorValue = 0;
		while (Button.ENTER.isUp()) {
			colorValue = colorSensor.getSample();
		}
		return colorValue;
	}
	
	protected void confirm() {
		System.out.println("Press button to start.");
		Button.waitForAnyPress();
		System.out.println("Running...");
		Delay.msDelay(1000);
	}

}
