package team39.challenges;

import lejos.utility.Delay;
import team39.technical.LineFollower;
import team39.technical.sensors.ColorSensor;

public class Challenge4 implements Challenge {
	LineFollower robot;
	private final float POLE_DISTANCE = 30;
	private final float POWER = 150;
	private final float TRESHOLD = 120;
	private final float ANGLE1 = 180;
	private boolean foodIsLeft;

	public Challenge4() {
		robot = new LineFollower(POWER, TRESHOLD);
	}
	
	public void run() {
		robot.confirm();
		enterPassage();
		crossPassage();
		selectSide();
		findFood();
		collectFood();
		returnToPassage();
		crossPassage();
		dropFood();
	}

	private void enterPassage() {
		robot.rotateUntilAngle(-10, POWER);
		robot.approachLine();
		robot.advance(POWER);
		Delay.msDelay(2000);
	}

	private void crossPassage() {
		robot.runUntilDistance(POLE_DISTANCE, false);
	}

	private void selectSide() {
		robot.colorSensor.setMode(ColorSensor.Mode.COLORS);
		foodIsLeft = robot.runUntilColor();
		robot.resetGyro();
		robot.rotateUntilAngle(180, POWER);
		robot.resetGyro();
		robot.colorSensor.setMode(ColorSensor.Mode.RED);
		robot.approachLine();
		robot.runUntilAngle(ANGLE1, !foodIsLeft);
	}

	private void findFood() {
		float closest = robot.seekClosestAngle();
		robot.resetGyro();
		robot.rotateUntilAngle(closest, POWER);
	}

	private void collectFood() {
		// TODO Auto-generated method stub
		
	}

	private void returnToPassage() {
		// TODO Auto-generated method stub
		
	}

	private void dropFood() {
		// TODO Auto-generated method stub
		
	}
}
