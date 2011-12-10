package chvck.colourMate.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import chvck.colourMate.R;

public class Help extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    TextView title = (TextView) findViewById(R.id.help_title);
	    TextView body = (TextView) findViewById(R.id.help_body);
	    
	    Bundle extras = getIntent().getExtras();
	    String titleText = extras.getString("title");
	    String bodyText = extras.getString("body");
	    
	    title.setText(titleText);
	    body.setText(bodyText);
	}
	
}
