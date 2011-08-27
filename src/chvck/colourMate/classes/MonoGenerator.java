package chvck.colourMate.classes;

import java.util.ArrayList;

import android.graphics.Color;

public class MonoGenerator extends Generator  {

	public MonoGenerator(int colourParam, String angleParam) {
		super(colourParam, angleParam);
	}

	//mono is about changing the saturation and lightness of the colour
	public ArrayList<Integer> generateNewColours() {
		float[] hsv = new float[3];
		Color.RGBToHSV(Color.red(colour), Color.green(colour), Color.blue(colour), hsv);
		final float hue = hsv[0];
		final float saturation = hsv[1];
		final float value = hsv[2];
		
		final int lowerBound = getLowerBound();
		final int upperBound = getUpperBound();
		
		for (int i=lowerBound;i<=upperBound;i++) {
			for (int j=lowerBound;j<=upperBound;j++) {
				final float newSaturation = saturation + ((float) j/10);
				final float newValue = value + ((float) i/10);
				
				final float[] newHsv = {hue, newSaturation, newValue};
				
				if (isValidHsv(newHsv)) {
					newColours.add(Color.HSVToColor(newHsv));
				}
			}
		}
			
		return newColours;
	}
	
	private int getLowerBound() {
		if (angle.equalsIgnoreCase("close")) {
			return -2;
		} else if (angle.equalsIgnoreCase("wide")) {
			return -4;
		}
		//if this gets hit something is very wrong
		return 0;
	}
	
	private int getUpperBound() {
		if (angle.equalsIgnoreCase("close")) {
			return 2;
		} else if (angle.equalsIgnoreCase("wide")) {
			return 4;
		}
		
		return 0;
	}
}
