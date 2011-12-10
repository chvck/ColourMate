package chvck.colourMate.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import chvck.colourMate.R;

public class ColourActivity extends Activity {
	protected String helpTitle;
	protected String helpBody;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.popup, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.new_colour:
				newColour(this);
				return true;
			case R.id.help:
				loadHelp(this);
				return true;
		    default:
		        return super.onOptionsItemSelected(item);
		}
	}
	
	protected void newColour(Context contextParam){
		Intent intent = new Intent();
		intent.setClass(contextParam, chvck.colourMate.activities.ColourMateCamera.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		contextParam.startActivity(intent);
	}
	
	protected void loadHelp(Context contextParam) {
		Intent intent = new Intent();
		intent.setClass(contextParam, chvck.colourMate.activities.Help.class);
		intent.putExtra("title", helpTitle);
		intent.putExtra("body", helpBody);
		contextParam.startActivity(intent);
	}
}
