package chvck.colourMate.activities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import chvck.colourMate.R;
import chvck.colourMate.classes.Preview;

public class ColourMateCamera extends Activity {
	Preview preview;
	ProgressDialog dialog;
	boolean takingPhoto = false;
	boolean focused = false;
	boolean flash = false;
	ImageButton flashButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//setup the surface for the camera preview
		preview = new Preview(this);
		((FrameLayout) findViewById(R.id.preview)).addView(preview);

		flashButton = (ImageButton) findViewById(R.id.flash);
		//make it look pretty by adding transparency
		flashButton.setAlpha(150);
		flashButton.setFocusable(false);

		//setup the onClick
		flashButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) { 
				if (flash) {
					flash = false;
					flashButton.setBackgroundResource(R.drawable.no_flash);
				} else {
					flash = true;
					flashButton.setBackgroundResource(R.drawable.flash);
				}
			}
		});

		Button capture = (Button) findViewById(R.id.buttonCapture);
		//make it look pretty by adding transparency
		capture.getBackground().setAlpha(150);
		capture.setOnClickListener(new OnClickListener() {
			public void onClick(View v) { 
				focus();
			}
		});

		//try to give focus so that normal camera button should work
		capture.requestFocus();
	}

	private class AutoFocusCallBack implements Camera.AutoFocusCallback {
		public void onAutoFocus(boolean success, Camera camera) {
			//update the flag used in onKeyDown()
			focused = success; 
			//we don't actually care if the camera has focused, we just want colours not sharp
			//images
			setFlash();
			takePicture();
		}
	}

	private void setFlash() {
		Parameters params = preview.camera.getParameters();
		if (flash) {
			if (params.getFlashMode() != null) {
				params.setFlashMode(Parameters.FLASH_MODE_ON);
				preview.camera.setParameters(params);
			}
		} else {
			if (params.getFlashMode() != null) {
				params.setFlashMode(Parameters.FLASH_MODE_OFF);
				preview.camera.setParameters(params);
			}
		}
	}

	private void focus() {
		if (!takingPhoto) {
			//whilst focusing doesn't really matter we'll still try to do it right
			String focusMode = preview.camera.getParameters().getFocusMode();
			if (focusMode != null) {
				if (focusMode.equalsIgnoreCase(Parameters.FOCUS_MODE_AUTO) || focusMode.equalsIgnoreCase(Parameters.FOCUS_MODE_MACRO)) {				    		
					AutoFocusCallBack autoFocusCallBack = new AutoFocusCallBack();
					preview.camera.autoFocus(autoFocusCallBack);		    			
				} else {
					setFlash();
					takePicture();
				}
			} else {
				setFlash();
				takePicture();

			}
		}
	}

	public final boolean onKeyUp(int keyCode, KeyEvent event) {
		synchronized(this) {
			if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_CAMERA) {
				focused = false;
			}
		}
		return true;
	}

	private void takePicture() {
		takingPhoto = true;
		focused = false;

		preview.camera.takePicture(null, null, jpegCallback);	  
	}

	// Handles data for jpeg picture
	PictureCallback jpegCallback = new PictureCallback() { // <8>
		public void onPictureTaken(byte[] data, Camera camera) {
			//get the data
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			showDialog(0);
			takingPhoto = false;
			//process the colours, we don't need a handle on this task
			new ProcessColoursTask().execute(bitmap);
		}
	};

	//the dialog box to show whilst processing
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0: {
			dialog = new ProgressDialog(this);
			dialog.setMessage("Processing colours...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;
		} 
		}
		return null;
	}

	//end the app
	@Override
	public void onBackPressed() {
		finish();
	}

	//popup for when menu is pressed
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.popup, menu);
		return true;
	}

	//handle the options in the popup
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		}
		return true;
	}

	protected void loadColourSelection(ArrayList<Integer> colours) {
		Intent intent = new Intent();
		intent.setClass(this, chvck.colourMate.activities.PickColour.class);
		intent.putExtra("colours", colours);
		startActivity(intent);
	}


	//----------------------------------------------------------------------------   

	public class ProcessColoursTask extends AsyncTask<Bitmap, Void, ArrayList<Integer>> { 

		protected ArrayList<Integer> doInBackground(Bitmap...bitmapParam) {
			//we need to work out a scale in case the bitmap changes the size
			//of the image taken
			Bitmap bitmap = bitmapParam[0];
			int preview_width = preview.getWidth();
			int preview_height = preview.getHeight();

			int img_width = bitmap.getWidth();
			int img_height = bitmap.getHeight();

			float x_scale = (float) img_width / preview_width;
			float y_scale = (float) img_height / preview_height;

			//0,0 top left I think
			float x_distance_from_centre = (20 * x_scale);
			float y_distance_from_centre = (20 * y_scale);

			int x = (int) ((img_width/2) - x_distance_from_centre); 
			int y = (int) ((img_height/2) - y_distance_from_centre); 
			int width = (int) ((img_width/2) + x_distance_from_centre) - x;
			int height = (int) ((img_height/2) + y_distance_from_centre) - y;
			int stride = width;
			int[] colours = new int[width * height];
			bitmap.getPixels(colours, 0, stride, x, y, width, height);


			//create a hashtable (histogram) of colours and their frequencies
			HashMap<Integer, Integer> colour_freq = new HashMap<Integer, Integer>();
			for (int colour : colours) {
				if (colour_freq.containsKey(colour)) {
					int value = (Integer) colour_freq.get(colour);
					colour_freq.put(colour, value + 1);
				} else {
					colour_freq.put(colour, 1);
				}
			}

			//get an iterator for the keys in the histogram
			Collection<Integer> c = colour_freq.keySet();
			Iterator<Integer> e = c.iterator();
			final ArrayList<Integer> colours_sorted = new ArrayList<Integer>();
			while (e.hasNext()) {
				//get the colour
				int colour = e.next();

				//Initialise the values
				if (colours_sorted.size() == 0) {
					colours_sorted.add(colour);
					continue;
				}

				//we keep a sorted list so iterate over the sorted array
				for (int i = 0; i < colours_sorted.size(); i++) {
					//if the frequency of this colour is higher than the current
					//sorted element then insert it and remove the last element
					//in the array 
					if ((Integer) colour_freq.get(colour) > 
					(Integer) colour_freq.get(colours_sorted.get(i))) {
						colours_sorted.add(i, colour);
						if (colours_sorted.size() >=10) {
							colours_sorted.remove(colours_sorted.size() - 1);
						}
						break;
					} else {
						//value isn't higher than any other so whack it on the end
						//as the array is still too small
						if (colours_sorted.size() < 10) {
							colours_sorted.add(colour);
							break;
						}
					}
				}

			}


			Collections.sort(colours_sorted);

			return colours_sorted;
		}

		protected void onPostExecute(ArrayList<Integer> colours) {
			removeDialog(0);
			loadColourSelection(colours);
		}
	}
}