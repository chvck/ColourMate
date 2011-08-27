package chvck.colourMate.activities;

import java.util.ArrayList;

import android.app.Activity;
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
import chvck.colourMate.classes.AnalogGenerator;
import chvck.colourMate.classes.ComplimentaryGenerator;
import chvck.colourMate.classes.MonoGenerator;
import chvck.colourMate.classes.SplitCompGenerator;
import chvck.colourMate.classes.SquareGenerator;
import chvck.colourMate.classes.TetradicGenerator;
import chvck.colourMate.classes.TriadicGenerator;

public class GenerateNewColours extends Activity {
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
			final int newColour = newColours.get(i);
			
			//setup the new button
			Button button = new Button(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
			params.setMargins(10, 2, 10, 0);
			button.setHeight(40);
			button.setLayoutParams(params);
			button.setBackgroundColor(newColour);
			button.setLongClickable(true);

			button.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					compare(newColour, colour, generator);
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
	}

	private void compare(int newColour, int origColour, String generator) {
		Intent intent = new Intent();
		intent.setClass(this, chvck.colourMate.activities.CompareColours.class);
		intent.putExtra("colour", newColour);
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
