package chvck.colourMate.generators;

import java.util.ArrayList;

import android.graphics.Color;

public class AnalogGenerator extends Generator {

	public AnalogGenerator(int colourParam, String angleParam) {
		super(colourParam, angleParam);
	}

	//analogous scheming is all about altering the hue
	public ArrayList<Integer> generateNewColours() {
		float[] hsv = new float[3];
		Color.RGBToHSV(Color.red(colour), Color.green(colour), Color.blue(colour), hsv);
		final float hue = hsv[0];
		final float saturation = hsv[1];
		final float value = hsv[2];

		int divisor = getDivisor();
		int loops = 60 / divisor;

		for (int i = 0; i < loops; i++ ){
			//60 degrees is the angle between two colours
			float newLowerHue = hue - divisor;
			float newUpperHue = hue + divisor;

			newLowerHue = fixHue(newLowerHue);
			newUpperHue = fixHue(newUpperHue);

			final float[] newLowerHsv = {newLowerHue, saturation, value};
			final float[] newUpperHsv = {newUpperHue, saturation, value};
			newColours.add(Color.HSVToColor(newLowerHsv));
			newColours.add(Color.HSVToColor(newUpperHsv));
		}

		return newColours;
	}

	private int getDivisor() {
		int divisor = 60;
		if (angle.equalsIgnoreCase("close")) {
			divisor = 10;
		} else if (angle.equalsIgnoreCase("wide")) {
			divisor  = 5;
		}

		return divisor;
	}

}
