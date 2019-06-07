package team39.challenges;

import team39.technical.LineFollower;;

public class Challenge2 implements Challenge {
	LineFollower robot;
	private final float POWER = 150;
	private final float TRESHOLD = 120;

	public Challenge2() {
		robot = new LineFollower(POWER, TRESHOLD);
	}

	public void run() {
		robot.runPID();
	}
}
