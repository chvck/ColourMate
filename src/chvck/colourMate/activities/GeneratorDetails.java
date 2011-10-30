package chvck.colourMate.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import chvck.colourMate.R;

public class GeneratorDetails extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.generatordetails);
	    
	    TextView title = (TextView) findViewById(R.id.details_title);
	    TextView details = (TextView) findViewById(R.id.details_detail);
	    LinearLayout layout = (LinearLayout) findViewById(R.id.details_ll);
	    
   	    //get the selected generator
	    Bundle extras = getIntent().getExtras();
	    String generator = extras.getString("generator");

	    if (generator.equalsIgnoreCase("chrom")) {
	    	title.setText("Monochromatic");
	    	details.setText("A monochromatic color scheme consists of using different tints and shades " +
	    			"of one single color. These color schemes are easy to get right and can be very effective, " +
	    			"soothing and authoritative. They do, however, lack the diversity of hues found in other color schemes " +
	    			"and are less vibrant.");
	    	ImageView example = new ImageView(this);
	    	example.setBackgroundColor(Color.parseColor("#FF2300"));
	    	example.setMinimumHeight(15);
	    	example.setMinimumWidth(15);
	    	layout.addView(example);
	    	example = new ImageView(this);
	    	example.setBackgroundColor(Color.parseColor("#FF5A00"));
	    	example.setMinimumHeight(15);
	    	example.setMinimumWidth(15);
	    	layout.addView(example); 
	    	example = new ImageView(this);
	    	example.setBackgroundColor(Color.parseColor("#FF8673"));
	    	example.setMinimumHeight(15);
	    	example.setMinimumWidth(15);
	    	layout.addView(example);
	    	example = new ImageView(this);
	    	example.setBackgroundColor(Color.parseColor("#A61700"));
	    	example.setMinimumHeight(15);
	    	example.setMinimumWidth(15);
	    	layout.addView(example);
	    } else if (generator.equalsIgnoreCase("comp")) {
	    	title.setText("Complimentary");
	    	details.setText("Complementary colors are colors that are opposite each other on the color wheel." +
	    			"This scheme is good for creating a high contrast between colours which gives the scheme" +
	    			"a vibrant, energetic look that is good for grabbing attention.\n\n" +
	    			"Tips\n Try to use a warm colour alongside a cool colour to help with balance.\n"+
	    			"Don't use this scheme for text!");
	    	TextView example = new TextView(this);
	    	example.setBackgroundColor(Color.RED);
	    	example.setTextColor(Color.GREEN);
	    	example.setText("This is an example of using complimentary colours for text");
	    	layout.addView(example);
	    	example = new TextView(this);
	    	example.setBackgroundColor(Color.GREEN);
	    	example.setTextColor(Color.RED);
	    	example.setText("This is an example of using complimentary colours for text");
	    	layout.addView(example);
	    } else if (generator.equalsIgnoreCase("analog")) {
	    	title.setText("Analogous");
	    	details.setText("Analogous colors are colors that are adjacent to each other on the color wheel. " +
	    			"Analogous color schemes are often found in nature and are pleasing to the eye. " +
	    			"The combination of these colors give a bright and cheery effect in the area, and are able " +
	    			"to accommodate many changing moods. When using the analogous color scheme, you should " +
	    			"make sure there is one hue as the main color. Try to avoid combining warm and cold colours " +
	    			"in this scheme");
	    	ImageView example = new ImageView(this);
	    	example.setBackgroundColor(Color.parseColor("#FF2300"));
	    	example.setMinimumHeight(15);
	    	example.setMinimumWidth(15);
	    	layout.addView(example);
	    	example = new ImageView(this);
	    	example.setBackgroundColor(Color.parseColor("#FFB300"));
	    	example.setMinimumHeight(15);
	    	example.setMinimumWidth(15);
	    	layout.addView(example);
	    	example = new ImageView(this);
	    	example.setBackgroundColor(Color.parseColor("#A61700"));
	    	example.setMinimumHeight(15);
	    	example.setMinimumWidth(15);
	    	layout.addView(example);
	    } else if (generator.equalsIgnoreCase("triad")) {
	    	title.setText("Triadic");
	    	details.setText("The triadic colour scheme uses three colours that are equally spaced around." + 
	    			"the colour wheel. This colour scheme tends to be vibrant with strong visual contrast." +
	    			"When using this colour scheme one dominant colour should be used with with the other " +
	    			"colours as accents.");
	    	ImageView example = new ImageView(this);
	    	example.setBackgroundColor(Color.parseColor("#FF2300"));
	    	example.setMinimumHeight(15);
	    	example.setMinimumWidth(15);
	    	layout.addView(example);
	    	example = new ImageView(this);
	    	example.setBackgroundColor(Color.parseColor("#1533AD"));
	    	example.setMinimumHeight(15);
	    	example.setMinimumWidth(15);
	    	layout.addView(example);
	    	example = new ImageView(this);
	    	example.setBackgroundColor(Color.parseColor("#E9FB00"));
	    	example.setMinimumHeight(15);
	    	example.setMinimumWidth(15);
	    	layout.addView(example);
	    }
	    
	}
	
}
