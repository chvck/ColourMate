package chvck.colourMate.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import chvck.colourMate.R;

public class GeneratorDetails extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.generatordetails);
	    
	    TextView title = (TextView) findViewById(R.id.details_title);
	    TextView details = (TextView) findViewById(R.id.details_detail);
	    
   	    //get the selected generator
	    Bundle extras = getIntent().getExtras();
	    String generator = extras.getString("generator");

	    if (generator.equalsIgnoreCase("chrom")) {
	    	title.setText("Monochromatic");
	    	details.setText("A monochromatic color scheme consists of different values (tints and shades) " +
	    			"of one single color. These color schemes are easy to get right and can be very effective, " +
	    			"soothing and authoritative. They do, however, lack the diversity of hues found in other color schemes " +
	    			"and are less vibrant.");
	    } else if (generator.equalsIgnoreCase("comp")) {
	    	title.setText("Complimentary");
	    	details.setText("Complementary colors are colors that are opposite each other on the color wheel, " +
	    			"such as blue and orange, red and green, purple and yellow. Complementary color schemes have a " +
	    			"more energetic feel.\n" + 
	    			"The high contrast between the colors creates a vibrant look, especially when used at full saturation. " +
	    			"Complementary colors can be tricky to use in large doses.");
	    }
	}
	
}
