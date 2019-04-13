package team39.technical;

public class PID {

	static final float TARGET_POWER = 50;
	private float blackColor;
	private float whiteColor;
	private float averageColor;
	private float proportionalK;
	private float integralK;
	private float derivativeK;
	private float integral;
	private float lastError;

	public PID(float blackColor, float whiteColor, float averageColor) {
		this.blackColor = blackColor;
		this.whiteColor = whiteColor;
		this.averageColor = averageColor;
		proportionalK = getProportionalityConstant();
		integralK = getIntegralConstant();
		derivativeK = getDerivativeConstant();
		integral = 0;
		lastError = 0;
	}

	private float getError(float sample) {
		return sample - averageColor;
	}

	// RECHECK
	private float getProportionalityConstant() {
		// float errorWhite = whiteColor - averageColor;
		float errorBlack = blackColor - averageColor;
		float slope = TARGET_POWER / errorBlack; // what to divide?
		return slope;
	}

	// RECHECK
	private float getIntegralConstant() {
		return proportionalK / 10;
	}

	// RECHECK
	private float getDerivativeConstant() {
		return proportionalK * 10;
	}

	private float getPTerm(float error) {
		return proportionalK * error;
	}

	private float getITerm(float error) {
		integral = integral + error;
		return integralK * integral;
	}

	private float getDTerm(float error) {
		float derivative = error - lastError;
		lastError = error;
		return derivativeK * derivative;
	}

	public float getTurn(float sample) {
		float error = getError(sample);
		return getPTerm(error) + getITerm(error) + getDTerm(error);
	}
}
