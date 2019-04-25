package team39.technical;

public class PID {

	private final float TARGET_POWER;
	private final float BLACK_COLOR;
	private final float WHITE_COLOR;
	private final float AVERAGE_COLOR;
	private float proportionalK;
	private float derivativeK;
	private float lastError;

	public PID(float blackColor, float whiteColor, float averageColor, float power) {
		BLACK_COLOR = blackColor;
		WHITE_COLOR = whiteColor;
		AVERAGE_COLOR = averageColor;
		TARGET_POWER = power;
		proportionalK = getProportionalityConstant();
		derivativeK = getDerivativeConstant();
		lastError = 0;
	}

	private float getError(float sample) {
		return sample - AVERAGE_COLOR;
	}

	private float getProportionalityConstant() {
		float maxError = BLACK_COLOR - AVERAGE_COLOR;
		float slope = TARGET_POWER / maxError;
		return slope;
	}
	
	private float getDerivativeConstant() {
		return 0;
	}

	private float getPTerm(float error) {
		return proportionalK * error;
	}

	private float getDTerm(float error) {
		float derivative = error - lastError;
		lastError = error;
		return derivativeK * derivative;
	}

	public float getTurn(float sample) {
		float error = getError(sample);
		return getPTerm(error) + getDTerm(error);
	}
}
