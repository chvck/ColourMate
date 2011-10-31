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
	
	@Override
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
		newColour(this);
		return true;
	}
	
	public static void newColour(Context contextParam){
		Intent intent = new Intent();
		intent.setClass(contextParam, chvck.colourMate.activities.ColourMateCamera.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		contextParam.startActivity(intent);
	}
}
