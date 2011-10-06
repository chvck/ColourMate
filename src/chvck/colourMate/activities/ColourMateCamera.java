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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import chvck.colourMate.R;
import chvck.colourMate.utils.Preview;

public class ColourMateCamera extends Activity {
	private static Preview preview;
	private ProgressDialog dialog;
	private boolean takingPhoto = false;
	private boolean flash = false;
	private ImageButton flashButton;

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
			//while focusing doesn't really matter we'll still try to do it right
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

	private void takePicture() {
		takingPhoto = true;

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

	//the dialog box to show while processing
	@Override
	public Dialog onCreateDialog(int id) {
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

	protected void loadColourSelection(ArrayList<Integer> colours) {
		Intent intent = new Intent();
		intent.setClass(this, chvck.colourMate.activities.PickColour.class);
		intent.putExtra("colours", colours);
		startActivity(intent);
	}

	private class AutoFocusCallBack implements Camera.AutoFocusCallback {
		public void onAutoFocus(boolean success, Camera camera) {
			//we don't actually care if the camera has focused, we just want colours not sharp
			//images
			setFlash();
			takePicture();
		}
	}
	
	//----------------------------------------------------------------------------   

	public class ProcessColoursTask extends AsyncTask<Bitmap, Void, ArrayList<Integer>> { 

		protected ArrayList<Integer> doInBackground(Bitmap...bitmapParam) {
			//we need to work out a scale in case the bitmap changes the size
			//of the image taken
			Bitmap bitmap = bitmapParam[0];
			int previewWidth = preview.getWidth();
			int previewHeight = preview.getHeight();

			int imgWidth = bitmap.getWidth();
			int imgHeight = bitmap.getHeight();

			float xScale = (float) imgWidth / previewWidth;
			float yScale = (float) imgHeight / previewHeight;

			//0,0 top left I think
			float xDistanceFromCentre = (20 * xScale);
			float yDistanceFromCentre = (20 * yScale);

			int x = (int) ((imgWidth/2) - xDistanceFromCentre); 
			int y = (int) ((imgHeight/2) - yDistanceFromCentre); 
			int width = (int) ((imgWidth/2) + xDistanceFromCentre) - x;
			int height = (int) ((imgHeight/2) + yDistanceFromCentre) - y;
			int stride = width;
			int[] colours = new int[width * height];
			bitmap.getPixels(colours, 0, stride, x, y, width, height);


			//create a hashtable (histogram) of colours and their frequencies
			HashMap<Integer, Integer> colourFreq = new HashMap<Integer, Integer>();
			for (int colour : colours) {
				if (colourFreq.containsKey(colour)) {
					int value = (Integer) colourFreq.get(colour);
					colourFreq.put(colour, value + 1);
				} else {
					colourFreq.put(colour, 1);
				}
			}

			//get an iterator for the keys in the histogram
			Collection<Integer> c = colourFreq.keySet();
			Iterator<Integer> e = c.iterator();
			ArrayList<Integer> coloursSorted = new ArrayList<Integer>();
			while (e.hasNext()) {
				//get the colour
				int colour = e.next();

				//Initialise the values
				if (coloursSorted.size() == 0) {
					coloursSorted.add(colour);
					continue;
				}

				//we keep a sorted list so iterate over the sorted array
				for (int i = 0; i < coloursSorted.size(); i++) {
					//if the frequency of this colour is higher than the current
					//sorted element then insert it and remove the last element
					//in the array 
					if ((Integer) colourFreq.get(colour) > 
					(Integer) colourFreq.get(coloursSorted.get(i))) {
						coloursSorted.add(i, colour);
						if (coloursSorted.size() >=10) {
							coloursSorted.remove(coloursSorted.size() - 1);
						}
						break;
					} else {
						//value isn't higher than any other so whack it on the end
						//as the array is still too small
						if (coloursSorted.size() < 10) {
							coloursSorted.add(colour);
							break;
						}
					}
				}

			}


			Collections.sort(coloursSorted);

			return coloursSorted;
		}

		protected void onPostExecute(ArrayList<Integer> colours) {
			removeDialog(0);
			loadColourSelection(colours);
		}
	}
}