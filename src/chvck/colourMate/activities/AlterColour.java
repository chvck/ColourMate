package chvck.colourMate.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import chvck.colourMate.R;

public class AlterColour extends ColourActivity {
	private float hueValue;
	private float satValue;
	private float valueValue;
	TextView hue;
	TextView sat;
	TextView value;
	ImageView colourBox;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.altercolour);
	
		//get the colour
		Bundle extras = getIntent().getExtras();
		final int colour = extras.getInt("colour");
		
		//get the buttons and textboxes
		colourBox = (ImageView) findViewById(R.id.alterColourBox);
		
		ImageButton leftHue = (ImageButton) findViewById(R.id.imageButton1);
		ImageButton rightHue = (ImageButton) findViewById(R.id.imageButton2);
		ImageButton leftSat = (ImageButton) findViewById(R.id.imageButton3);
		ImageButton rightSat = (ImageButton) findViewById(R.id.imageButton4);
		ImageButton leftValue = (ImageButton) findViewById(R.id.imageButton5);
		ImageButton rightValue = (ImageButton) findViewById(R.id.imageButton6);
		Button use = (Button) findViewById(R.id.useColour);
		
		hue = (TextView) findViewById(R.id.hueValue);
		sat = (TextView) findViewById(R.id.satValue);
		value = (TextView) findViewById(R.id.valueValue);
		
		float[] hsv = new float[3];
		Color.RGBToHSV(Color.red(colour), Color.green(colour), Color.blue(colour), hsv);
		
		hueValue = Math.round(hsv[0]);
		satValue = Round(hsv[1], 2);
		valueValue = Round(hsv[2], 2);
		               
		setTextsToValues();
		
		leftHue.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setHue(-1);
				setTextsToValues();
			}
		});
		rightHue.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setHue(1);
				setTextsToValues();
			}
		});		
		leftSat.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setSat((float) -0.02);
				setTextsToValues();
			}
		});
		rightSat.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setSat((float) 0.02);
				setTextsToValues();
			}
		});		
		leftValue.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setValue((float) -0.02);
				setTextsToValues();
			}
		});
		rightValue.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setValue((float) 0.02);
				setTextsToValues();
			}
		});
		use.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				selectColour();
			}
		});
		
	}
	
	public void selectColour() {
		float[] hsv = new float[3];
		hsv[0] = hueValue;
		hsv[1] = satValue;
		hsv[2] = valueValue;
		
		Intent intent = new Intent();
		intent.setClass(this, chvck.colourMate.activities.SelectedColour.class);
		intent.putExtra("colour", Color.HSVToColor(hsv));
		startActivityForResult(intent, 0);
	}
	
	private void setHue(int value) {
		hueValue += value;
		if (hueValue < 0) {
			hueValue = 359;
		} 
		if (hueValue > 359) {
			hueValue = 0;
		}
	}
	
	private void setSat(float value) {
		satValue += value;
		if (satValue >= 1) {
			satValue = 0;
		} 
		if (satValue < 0) {
			satValue = (float) 0.99;
		}	
	}
	
	private void setValue(float value) {
		valueValue += value;
		if (valueValue >= 1) {
			valueValue = 0;
		} 
		if (valueValue < 0) {
			valueValue = (float) 0.99;
		}	
	}
	
	private void setTextsToValues() {
		float[] hsv = new float[3];
		hsv[0] = hueValue;
		hsv[1] = satValue;
		hsv[2] = valueValue;
		
		colourBox.setBackgroundColor(Color.HSVToColor(hsv));
		hue.setText(String.valueOf(Math.round(hueValue)));
		sat.setText(String.valueOf(Round(satValue, 2)));
		value.setText(String.valueOf(Round(valueValue, 2)));
	}
	
	private static float Round(float Rval, int Rpl) {
		float p = (float)Math.pow(10,Rpl);
		Rval = Rval * p;
		float tmp = Math.round(Rval);
		return (float)tmp/p;
	}
	
	
}
