package team39.challenges;

import team39.technical.Explorer;

public class Challenge3 implements Challenge {
	Explorer robot;

	public Challenge3() {
		robot = new Explorer();
	}

	public void run() {
		robot.run();
	}
}
