package chvck.colourMate.classes;

import java.util.ArrayList;

import android.graphics.Color;

public class SquareGenerator extends Generator {

	public SquareGenerator(int colourParam, String angleParam) {
		super(colourParam, angleParam);
	}

	public ArrayList<Integer> generateNewColours() {
		float[] hsv = new float[3];
		Color.RGBToHSV(Color.red(colour), Color.green(colour), Color.blue(colour), hsv);
		final float hue = hsv[0];
		final float saturation = hsv[1];
		final float value = hsv[2];
	
		float colour1 = hue + 90;
		float colour2 = hue + 180;
		float colour3 = hue + 270;
		
		int bound = getBound();
		
		if (angle.equalsIgnoreCase("exact")) {
			makeNewColour(colour1, saturation, value);
			makeNewColour(colour2, saturation, value);
			makeNewColour(colour3, saturation, value);
		} else {
			for (int i=(-bound);i<=bound;i++) {
				float colour1Hue = colour1 + i;
				float colour2Hue = colour2 + i;
				float colour3Hue = colour3 + i;
				
				makeNewColour(colour1Hue, saturation, value);
				makeNewColour(colour2Hue, saturation, value);
				makeNewColour(colour3Hue, saturation, value);
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
}
