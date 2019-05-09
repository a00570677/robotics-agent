package team39.challenges;

import team39.technical.LineFollower;;

public class Challenge2 {
	LineFollower robot;
	private final float POWER = 300;

	public Challenge2() {
		robot = new LineFollower(false, POWER);
	}

	public void run() {
		robot.run();
	}
}
