package chvck.colourMate.classes;

import java.util.ArrayList;

import android.graphics.Color;

public class TetradicGenerator extends Generator {

	public TetradicGenerator(int colourParam, String angleParam) {
		super(colourParam, angleParam);
	}

	public ArrayList<Integer> generateNewColours() {
		float[] hsv = new float[3];
		Color.RGBToHSV(Color.red(colour), Color.green(colour), Color.blue(colour), hsv);
		final float hue = hsv[0];
		final float saturation = hsv[1];
		final float value = hsv[2];

		float compliment = getCompliment(hue);
		float adjacent = hue + 60;
		float adjacentCompliment = getCompliment(adjacent);
		
		int boundary = getBound();

		if (angle.equalsIgnoreCase("exact")) {
			makeNewColour(compliment, saturation, value);
			makeNewColour(adjacent, saturation, value);
			makeNewColour(adjacentCompliment, saturation, value);
		} else {
			for (int i=(-boundary);i<=boundary;i++) {
				float complimentHue = compliment + i;
				float adjHue = adjacent + i;
				float adjacentComplimentHue = adjacentCompliment + i;
				
				makeNewColour(complimentHue, saturation, value);
				makeNewColour(adjHue, saturation, value);
				makeNewColour(adjacentComplimentHue, saturation, value);
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
