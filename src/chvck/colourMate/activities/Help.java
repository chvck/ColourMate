package chvck.colourMate.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import chvck.colourMate.R;

public class Help extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    setContentView(R.layout.help);

	    Bundle extras = getIntent().getExtras();
	    String titleText = extras.getString("title");
	    String bodyText = extras.getString("body");
   
	    TextView titleView = (TextView) findViewById(R.id.help_title);
	    TextView bodyView = (TextView) findViewById(R.id.help_body);
	    
	    titleView.setText(titleText);    
	    bodyView.setText(bodyText);
	}		
}
