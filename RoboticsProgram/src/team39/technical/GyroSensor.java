package team39.technical;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;

public class GyroSensor {
	private EV3GyroSensor gyroSensor;
	private SampleProvider provider;
	private float[] sample;
	
	public GyroSensor(Port port) {
		gyroSensor = new EV3GyroSensor(port);
		provider = gyroSensor.getAngleMode();
		sample = new float[provider.sampleSize()];
	}
	
	public float getSample() {
		provider.fetchSample(sample, 0);
		return sample[0];
	}
	
	public void reset() {
		gyroSensor.reset();
	}
}
