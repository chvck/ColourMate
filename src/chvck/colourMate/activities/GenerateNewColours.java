package chvck.colourMate.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import chvck.colourMate.R;
import chvck.colourMate.generators.AnalogGenerator;
import chvck.colourMate.generators.ComplimentaryGenerator;
import chvck.colourMate.generators.MonoGenerator;
import chvck.colourMate.generators.SplitCompGenerator;
import chvck.colourMate.generators.SquareGenerator;
import chvck.colourMate.generators.TetradicGenerator;
import chvck.colourMate.generators.TriadicGenerator;

public class GenerateNewColours extends ColourActivity {
	LinearLayout ll;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.generator);

		ll = (LinearLayout) findViewById(R.id.generator_layout);

		Bundle extras = getIntent().getExtras();
		final String generator = extras.getString("generator");
		final String angle = extras.getString("angle");
		final int colour = extras.getInt("colour");

		ImageView iv = (ImageView) findViewById(R.id.generator_box);
		iv.setBackgroundColor(colour);

		Log.i("colourmate", generator);

		ArrayList<Integer> newColours = null;
		if (generator.equalsIgnoreCase("comp")) {
			ComplimentaryGenerator gen = new ComplimentaryGenerator(colour, angle);
			newColours = gen.generateNewColours();
		} else if (generator.equalsIgnoreCase("chrom")) {
			MonoGenerator gen = new MonoGenerator(colour, angle);
			newColours = gen.generateNewColours();
		} else if (generator.equalsIgnoreCase("analog")) {
			AnalogGenerator gen = new AnalogGenerator(colour, angle);
			newColours = gen.generateNewColours();
		} else if (generator.equalsIgnoreCase("triad")) {
			TriadicGenerator gen = new TriadicGenerator(colour, angle);
			newColours = gen.generateNewColours();
		} else if (generator.equalsIgnoreCase("splitComp")) {
			SplitCompGenerator gen = new SplitCompGenerator(colour, angle);
			newColours = gen.generateNewColours();
		} else if (generator.equalsIgnoreCase("tetradic")) {
			TetradicGenerator gen = new TetradicGenerator(colour, angle);
			newColours = gen.generateNewColours();
		} else if (generator.equalsIgnoreCase("square")) {
			SquareGenerator gen = new SquareGenerator(colour, angle);
			newColours = gen.generateNewColours();
		}

		for (int i = 0;i < newColours.size();i++) {
			int[] colours = null;
			final int newColour = newColours.get(i);

			//setup the new button
			Button button = new Button(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
			params.setMargins(10, 2, 10, 0);
			if (generator.equalsIgnoreCase("triad") || generator.equalsIgnoreCase("splitComp") 
					|| generator.equalsIgnoreCase("analog")) {
				colours = new int[2];
				if (i % 2 == 0) {
					params.setMargins(10, 8, 10, 0);				
				} 
				if (i % 1 == 0 && i != 0){	
					colours[0] = newColour;
					colours[1] = newColours.get(i - 1);									
				} else {
					colours[0] = newColour;	
					colours[1] = newColours.get(i + 1);
				}
			} else if (generator.equalsIgnoreCase("tetradic") || generator.equalsIgnoreCase("square")) {
				colours = new int[3];
				if (i % 3 == 0) {
					params.setMargins(10, 8, 10, 0);
				}
				if (i % 2 == 0 && i != 0) {
					colours[0] = newColour;
					colours[1] = newColours.get(i - 1);	
					colours[2] = newColours.get(i - 2);	
				} else if (i % 1 == 0 && i != 0) {
					colours[0] = newColour;
					colours[1] = newColours.get(i - 1);	
					colours[2] = newColours.get(i + 1);	
				} else {
					colours[0] = newColour;
					colours[1] = newColours.get(i + 1);	
					colours[2] = newColours.get(i + 2);	
				}
			} else {
				colours = new int[1];
				colours[0] = newColour;
			}

			button.setHeight(40);
			button.setLayoutParams(params);
			button.setBackgroundColor(newColour);
			button.setLongClickable(true);

			final int[] finalColours = colours;
			button.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					compare(finalColours, colour, generator);
				}
			});

			button.setOnLongClickListener(new OnLongClickListener() {
				public boolean onLongClick(View v) {
					select(newColour);
					return true;
				} 	
			});
			ll.addView(button);
		}
		
		helpTitle = "Selecting a scheme";
		helpBody = "Colours are grouped into schemes, when a colour is selected a new screen will display " +
				"showing all the colours in that scheme.";
	}

	private void compare(int[] newColours, int origColour, String generator) {
		Intent intent = new Intent();
		intent.setClass(this, chvck.colourMate.activities.CompareColours.class);
		intent.putExtra("colours", newColours);
		intent.putExtra("origColour", origColour);
		startActivityForResult(intent, 0);
	}

	private void select(int colour) {
		Intent intent = new Intent();
		intent.setClass(this, chvck.colourMate.activities.SelectedColour.class);
		intent.putExtra("colour", colour);
		startActivityForResult(intent, 0);
	}
}
