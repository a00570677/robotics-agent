package team39.challenges;

import lejos.utility.Delay;
import team39.technical.LineFollower;
import team39.technical.sensors.ColorSensor;

public class Challenge1 implements Challenge {
	LineFollower robot;
	private final float GRID_ANGLE1 = 350;
	private final float GRID_ANGLE2 = 170;
	private final float POWER = 150;
	private final float TRESHOLD = 120;

	public Challenge1() {
		robot = new LineFollower(POWER, TRESHOLD);
	}

	public void run() {
		robot.confirm();
		gridTask();
		changeTask();
		mazeTask();
	}

	private void gridTask() {
		robot.approachLine();
		robot.runUntilAngle(GRID_ANGLE1, false);
		robot.changeLane();
		robot.runUntilAngle(GRID_ANGLE2, true);
	}

	private void changeTask() {
		seekPole();
		robot.colorSensor.setMode(ColorSensor.Mode.COLORS);
		robot.runUntilBlue();
	}

	private void seekPole() {
		robot.rotateUntilAngle(180 - robot.initialAngle, POWER);
		robot.advance(POWER);
		Delay.msDelay(12000);
		float closest = robot.seekClosestAngle();
		robot.resetGyro();
		robot.rotateUntilAngle(closest, POWER);
	}

	private void mazeTask() {
		robot.blueBehavior();
		robot.colorSensor.setMode(ColorSensor.Mode.RED);
		robot.run();
		// DETECT RED POLE
	}
}
