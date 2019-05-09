package team39.challenges;

import team39.technical.LineFollower;

public class Challenge1 {
	LineFollower robot;
	private final float GRID_ANGLE1 = -360;
	private final float GRID_ANGLE2 = -180;

	public Challenge1() {
		robot = new LineFollower();
	}

	public void run() {
		robot.confirm();
		gridTask();
		findPole();
		mazeTask();
	}

	private void gridTask() {
		robot.runUntilAngle(GRID_ANGLE1, true);
		robot.changeLane();
		robot.runUntilAngle(GRID_ANGLE2, false);
		robot.rotateUntilAngle(-robot.getInitialAngle() + 180, robot.POWER);
	}

	private void findPole() {

	}

	private void mazeTask() {

	}
}
