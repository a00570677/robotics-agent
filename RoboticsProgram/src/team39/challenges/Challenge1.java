package team39.challenges;

import team39.technical.LineFollower;

public class Challenge1 {
	LineFollower robot;
	private final float GRID_ANGLE1 = 360;
	private final float GRID_ANGLE2 = 180;
	private final float DISTANCE = 150;
	private final float POWER = 400;

	public Challenge1() {
		robot = new LineFollower(true, POWER);
	}

	public void run() {
		robot.confirm();
		gridTask();
		findPole();
		mazeTask();
	}

	private void gridTask() {
		robot.approach();
		robot.runUntilAngle(GRID_ANGLE1, false);
		robot.changeLane();
		robot.runUntilAngle(GRID_ANGLE2, true);
	}

	private void findPole() {
		robot.rotateUntilDistance(DISTANCE, robot.POWER);
		robot.runUntilBlue();
	}

	private void mazeTask() {
		//TODAY
	}
}
