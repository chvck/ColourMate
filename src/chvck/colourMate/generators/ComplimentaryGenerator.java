package chvck.colourMate.generators;

import java.util.ArrayList;

import android.graphics.Color;

public class ComplimentaryGenerator extends Generator {

	public ComplimentaryGenerator(int colourParam, String angleParam) {
		super(colourParam, angleParam);
	}

	public ArrayList<Integer> generateNewColours() {
		float[] hsv = new float[3];
		Color.RGBToHSV(Color.red(colour), Color.green(colour), Color.blue(colour), hsv);
		final float hue = hsv[0];
		final float saturation = hsv[1];
		final float value = hsv[2];

		float compliment = getCompliment(hue);
		int boundary = getBound();

		final int lowerBound = (int) compliment - boundary;
		final int upperBound = (int) compliment + boundary;

		if (angle.equalsIgnoreCase("exact")) {
			makeNewColour(compliment, saturation, value);
		} else {
			for (int i=lowerBound;i<=upperBound;i++) {
				makeNewColour(i, saturation, value);
			}
		}
		
		return newColours;
	}

	private void makeNewColour(float hue, float saturation, float value) {
		float newHue = hue;

		newHue = fixHue(newHue);

		final float[] newHsv = {newHue, saturation, value};
		if (isValidHsv(newHsv)) {
			newColours.add(Color.HSVToColor(newHsv));
		}
	}

	private int getBound() {
		if (angle.equalsIgnoreCase("close")) {
			return 5;
		} else if (angle.equalsIgnoreCase("wide")) {
			return 10;
		}

		return 0;
	}

	private float getCompliment(float hue) {
		if (hue<180) {
			return (180 + hue);
		} else {
			return (hue - 180);
		}
	}
}
