package team39.technical.sensors;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class DistanceSensor {
	private EV3UltrasonicSensor distanceSensor;
	private SampleProvider provider;
	private float[] sample;
	
	public DistanceSensor(Port port) {
		distanceSensor = new EV3UltrasonicSensor(port);
		provider = distanceSensor.getDistanceMode();
		sample = new float[provider.sampleSize()];
	}
	
	public float getSample() {
		provider.fetchSample(sample, 0);
		return sample[0] * 100;
	}
}
