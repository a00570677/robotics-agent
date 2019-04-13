package team39.technical;

import lejos.hardware.port.*;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class ColorSensor {
	private EV3ColorSensor colorSensor;
	private SampleProvider provider;
	private float[] sample;
	
	public ColorSensor(Port port) {
		colorSensor = new EV3ColorSensor(port);
		provider = colorSensor.getRedMode();
		sample = new float[provider.sampleSize()];
	}
	
	public float getSample() {
		provider.fetchSample(sample, 0);
		return sample[0];
	}
}
