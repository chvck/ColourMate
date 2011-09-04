package chvck.colourMate.generators;

import java.util.ArrayList;

import android.graphics.Color;

public class TriadicGenerator extends Generator {

	public TriadicGenerator(int colourParam, String angleParam) {
		super(colourParam, angleParam);
	}
	
	public ArrayList<Integer> generateNewColours() {
		float[] hsv = new float[3];
		Color.RGBToHSV(Color.red(colour), Color.green(colour), Color.blue(colour), hsv);
		final float hue = hsv[0];
		final float saturation = hsv[1];
		final float value = hsv[2];
		
		float compliment1 = hue + 120;
		float compliment2 = hue - 120;
		
		final int bound = getBound();

		if (angle.equalsIgnoreCase("exact")) {
			makeNewColour(compliment1, saturation, value);
			makeNewColour(compliment2, saturation, value);
		} else {
			for (int i=(-bound);i<=bound;i++) {
				float hue1 = compliment1 + i;
				float hue2 = compliment2 + i;
				
				makeNewColour(hue1, saturation, value);
				makeNewColour(hue2, saturation, value);
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
