package chvck.colourMate.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import chvck.colourMate.R;

public abstract class ColourActivity extends Activity {
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
				newColour();
				return true;
			case R.id.help:
				loadHelp();
				return true;
		    default:
		        return super.onOptionsItemSelected(item);
		}
	}
		
	protected void newColour(){
		Intent intent = new Intent();
		intent.setClass(this, chvck.colourMate.activities.ColourMateCamera.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);
	}
	
	protected void loadHelp() {
		Intent intent = new Intent();
		intent.setClass(this, chvck.colourMate.activities.Help.class);
		intent.putExtra("title", helpTitle);
		intent.putExtra("body", helpBody);
		this.startActivity(intent);
	}
}
