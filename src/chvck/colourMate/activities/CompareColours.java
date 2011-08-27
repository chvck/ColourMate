package chvck.colourMate.activities;

import chvck.colourMate.R;
import chvck.colourMate.R.id;
import chvck.colourMate.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CompareColours extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.compare);
	    
	    ImageView newColourView = new ImageView(this);
	    ImageView newColourView2 = new ImageView(this);
	    ImageView origColourView = new ImageView(this);
	    
	    LinearLayout ll = (LinearLayout) findViewById(R.id.ll_compare);

        Bundle extras = getIntent().getExtras();
        int[] newColours = extras.getIntArray("colour");
	    final int origColour = extras.getInt("origColour");
	    
	    newColourView.setBackgroundColor(newColours[0]);
	    newColourView2.setBackgroundColor(newColours[1]);
	    origColourView.setBackgroundColor(origColour);
	    
	    ll.addView(newColourView);
	    ll.addView(newColourView2);
	    ll.addView(origColourView);
	    
	    /*Button button = (Button) findViewById(R.id.select_colour);
	    button.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v) {
			    use(newColour);
			}
        });*/
	}
	
	private void use(int colour) {
		Intent intent = new Intent();
		intent.setClass(this, chvck.colourMate.activities.SelectedColour.class);
		intent.putExtra("colour", colour);
		startActivityForResult(intent, 0);
	}
}
