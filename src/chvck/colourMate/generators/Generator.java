package chvck.colourMate.generators;

import java.util.ArrayList;

public class Generator {
	int colour;
	String angle;
	ArrayList<Integer> newColours;

	public Generator(int colourParam, String angleParam){
		colour = colourParam;
		angle = angleParam;
		newColours = new ArrayList<Integer>();
	}
	
	public ArrayList<Integer> generateNewColours() {
		return newColours;
	}
	
	protected float fixHue(float hue) {
		if (hue < 0) {
			hue = 360 + hue;
		} else if (hue > 360) {
			hue = hue - 360;
		}

		return hue;
	}
	
	protected boolean isValidHsv(float[] hsv) {
		if (!(hsv[0] <= 360 && hsv[0] >= 0)) {
			return false;
		}
		
		if (!(hsv[1] <=1 && hsv[1] >= 0)) {
			return false;
		}

		if (!(hsv[2] <=1 && hsv[2] >= 0)) {
			return false;
		}
		return true;
	}
}
