package team39.technical;

public class PID {

	static final float TARGET_POWER = 80;
	private float blackColor;
	private float whiteColor;
	private float averageColor;
	private float proportionalK;
	private float derivativeK;
	private float lastError;

	public PID(float blackColor, float whiteColor, float averageColor) {
		this.blackColor = blackColor;
		this.whiteColor = whiteColor;
		this.averageColor = averageColor;
		proportionalK = getProportionalityConstant();
		derivativeK = getDerivativeConstant();
		lastError = 0;
	}

	private float getError(float sample) {
		return sample - averageColor;
	}

	private float getProportionalityConstant() {
		float errorWhite = whiteColor - averageColor;
		float errorBlack = blackColor - averageColor;
		float slope = TARGET_POWER / errorWhite; // what to divide?
		System.out.println(slope);
		return slope*5f;
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
