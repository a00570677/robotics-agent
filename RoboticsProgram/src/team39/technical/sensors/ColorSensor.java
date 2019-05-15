package team39.technical.sensors;

import lejos.hardware.port.*;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;

public class ColorSensor {
	public static enum Mode {COLORS, RED};
	private EV3ColorSensor colorSensor;
	private SampleProvider provider;
	private float[] sample;
	
	public ColorSensor(Port port, Mode mode) {
		colorSensor = new EV3ColorSensor(port);
		if (mode == Mode.COLORS)
			provider = colorSensor.getColorIDMode();
		else if (mode == Mode.RED)
			provider = colorSensor.getRedMode();
			colorSensor.setFloodlight(Color.RED);
			colorSensor.setFloodlight(true);
		sample = new float[provider.sampleSize()];
	}
	
	public float getSample() {
		provider.fetchSample(sample, 0);
		return sample[0];
	}
}
