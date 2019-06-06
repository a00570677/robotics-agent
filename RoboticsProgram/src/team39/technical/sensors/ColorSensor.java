package team39.technical.sensors;

import lejos.hardware.port.*;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;

public class ColorSensor {
	public static enum Mode {COLORS, RED};
	public EV3ColorSensor colorSensor;
	public SampleProvider provider;
	private float[] sample;
	
	public ColorSensor(Port port, Mode mode) {
		colorSensor = new EV3ColorSensor(port);
		setMode(mode);
		sample = new float[provider.sampleSize()];
	}
	
	public float getSample() {
		provider.fetchSample(sample, 0);
		return sample[0];
	}
	
	public void setMode(Mode mode) {
		if (mode == Mode.COLORS) {
			provider = colorSensor.getColorIDMode();
			colorSensor.setFloodlight(false);
		}
		else if (mode == Mode.RED) {
			provider = colorSensor.getRedMode();
			colorSensor.setFloodlight(Color.RED);
			colorSensor.setFloodlight(true);
		}
	}
}
 