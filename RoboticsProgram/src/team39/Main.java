package team39;

import team39.technical.LineFollower;

public class Main {
	static LineFollower robot;
	public static void main(String[] args) {
		robot = new LineFollower();
		robot.run();
	}
}
