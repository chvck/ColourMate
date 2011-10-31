package chvck.colourMate.activities;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import chvck.colourMate.R;

public class PickColour extends ColourActivity {
	protected ProgressDialog dialog;
	public ArrayList<Integer> colours;
	public Context context = this;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		// Set Activity Layout
		setContentView(R.layout.pickcolour);

		//get the colours
		Bundle extras = getIntent().getExtras();
		colours = extras.getIntegerArrayList("colours");

		GridView gridView = (GridView) findViewById(R.id.buttonsGridView);
		gridView.setAdapter(new ButtonAdapter(this));        
	}

	public void selectColour(int colour) {
		Intent intent = new Intent();
		intent.setClass(this, chvck.colourMate.activities.SelectedColour.class);
		intent.putExtra("colour", colour);
		startActivityForResult(intent, 0);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			finish();
		}
	}


	//----------------------------------------------------------------------------
	
	public class ButtonAdapter extends BaseAdapter {
		private Context mContext;

		// Gets the context so it can be used later
		public ButtonAdapter(Context c) {
			mContext = c;
		}

		// Total number of things contained within the adapter
		public int getCount() {
			return colours.size();
		}

		// Require for structure, not really used in my code.
		public Object getItem(int position) {
			return null;
		}

		// Require for structure, not really used in my code. Can
		// be used to get the id of an item in the adapter for
		// manual control.
		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			Button button;
			if (convertView == null) {
				button = new Button(mContext);
				button.setLayoutParams(new GridView.LayoutParams(100, 100));
				button.setPadding(8, 8, 8, 8);
			} else {
				button = (Button) convertView;
			}
			button.setText("");
			//btn.setBackgroundResource(R.drawable.button);
			final int col = colours.get(position);
			button.setBackgroundColor(col);
			Drawable backgroundRes = button.getBackground();
			Drawable drawableRes = mContext.getResources().getDrawable(R.drawable.white_background);
			Drawable[] drawableLayers = { backgroundRes, drawableRes };
			LayerDrawable ld = new LayerDrawable(drawableLayers);
			button.setBackgroundDrawable(ld);
			button.setId(position);

			button.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					selectColour(col);
				}
			});

			return button;
		}
	}
}