package team39.technical;

import lejos.hardware.Button;
import team39.technical.sensors.ColorSensor;

public class LineFollower extends Robot {

	public final float POWER;
	public final float TURN_TRESHOLD = 130;
	public float redColor, blueColor;
	private PID controller;

	public LineFollower(boolean withColors, float power) {
		super(ColorSensor.Mode.RED);
		POWER  = power;
		calibrateColor(withColors);
	}
	
	public void approach() {
		while(colorSensor.getSample() < controller.WHITE_COLOR && Button.ENTER.isUp())
			advance(POWER);
		stop();
	}

	public void changeLane() {
		rotateUntilAngle(45, POWER);
		stop();
	}

	public void run() {
		run(false);
	}

	private void run(boolean left) {
		float turn;
		float powerLeft, powerRight;
		turn = controller.getTurn(colorSensor.getSample());
		if (turn > TURN_TRESHOLD)
			rotate(POWER, left);
		else if (turn < -TURN_TRESHOLD)
			rotate(POWER, !left);
		else {
			powerLeft = POWER + turn;
			powerRight = POWER - turn;
			advance(powerLeft, powerRight);
			//advance(POWER);
		}
	}

	public void runUntilAngle(float angle, boolean left) {
		while (Button.ENTER.isUp() && getAngle() < angle) {
			run(left);
		}
		resetGyro();
	}
	
	public void runUntilBlue() {
		while (Button.ENTER.isUp() && colorSensor.getSample() < blueColor + 10 && colorSensor.getSample() > blueColor - 10 ) {
			advance(POWER);
		}
		stop();
	}

	private void calibrateColor(boolean withColors) {
		float whiteColor = getColor("white");
		float blackColor = getColor("black");
		float averageColor = (whiteColor + blackColor) / 2;
		System.out.println("Average: " + averageColor);
		if (withColors) {
			redColor = getColor("red");
			blueColor = getColor("blue");
		}
		controller = new PID(blackColor, whiteColor, averageColor, POWER);
	}
}
