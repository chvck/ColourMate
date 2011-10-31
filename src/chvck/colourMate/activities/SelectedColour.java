package chvck.colourMate.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import chvck.colourMate.R;

public class SelectedColour extends ColourActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectedcolour);

		//get all the text views
		TextView baseValue = (TextView) findViewById(R.id.baseValue);
		TextView hexValue = (TextView) findViewById(R.id.hexValue);
		TextView rgbValue = (TextView) findViewById(R.id.rgbValue);
		TextView hsvValue = (TextView) findViewById(R.id.hsvValue);
		TextView intValue = (TextView) findViewById(R.id.intValue);
		ImageView colourBox = (ImageView) findViewById(R.id.colourBox);

		//get the colour
		Bundle extras = getIntent().getExtras();
		final int colour = extras.getInt("colour");

		Button button = (Button) findViewById(R.id.selectGenerators);
		button.getBackground().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.DST);
		button.setTextColor(Color.BLACK);
		button.setTypeface(null, Typeface.BOLD);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				schemes(colour);
			}
		});
		

		colourBox.setBackgroundColor(colour);

		Drawable backgroundRes = colourBox.getBackground();
		Drawable drawableRes = this.getResources().getDrawable(R.drawable.white_background_small);
		Drawable[] drawableLayers = {backgroundRes, drawableRes};
		LayerDrawable ld = new LayerDrawable(drawableLayers);
		colourBox.setBackgroundDrawable(ld);


		float[] hsv = new float[3];
		Color.RGBToHSV(Color.red(colour), Color.green(colour), Color.blue(colour), hsv);

		//work out what base colour it is
		String actualColour = "";
		if (hsv[0] == 0) {
			actualColour = "It's Black!";
		}
		else if (hsv[0] >=1 && hsv[0] <60) {
			actualColour = "Red";
		} else if (hsv[0] >=60 && hsv[0] <120) {
			actualColour = "Yellow";
		} else if (hsv[0] >=120 && hsv[0] <180) {
			actualColour = "Green";
		} else if (hsv[0] >=180 && hsv[0] <240) {
			actualColour = "Cyan";
		} else if (hsv[0] >=240 && hsv[0] <300) {
			actualColour = "Blue";
		} else if (hsv[0] >=300 && hsv[0] <360) {
			actualColour = "Magenta";
		}

		//show all the information
		baseValue.setText(actualColour);
		hexValue.setText("#" + Integer.toHexString(colour).substring(2));
		rgbValue.setText(Color.red(colour) + "," + Color.green(colour) + "," + Color.blue(colour));
		hsvValue.setText(Math.round(hsv[0]) + "," + Round(hsv[1], 2) + "," + Round(hsv[2], 2));
		intValue.setText("" + colour);

	}

	private void schemes(int colour) {
		Intent intent = new Intent();
		intent.setClass(this, chvck.colourMate.activities.PickScheme.class);
		intent.putExtra("colour", colour);
		startActivity(intent);
	}

	public static float Round(float Rval, int Rpl) {
		float p = (float)Math.pow(10,Rpl);
		Rval = Rval * p;
		float tmp = Math.round(Rval);
		return (float)tmp/p;
	}

}
