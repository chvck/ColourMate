package chvck.colourMate.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ImageButton;
import chvck.colourMate.activities.ColourMateCamera;

public class CameraTest extends ActivityInstrumentationTestCase2<ColourMateCamera> {
	private ColourMateCamera mActivity;
	private Button captureButton;
	private ImageButton flashButton;
	
	public CameraTest() {
		super("chvck.colourMate.activities", ColourMateCamera.class);
	}
	
	@Override
    protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		mActivity = this.getActivity();
		
		captureButton = (Button) mActivity.findViewById(chvck.colourMate.R.id.buttonCapture);
		flashButton = (ImageButton) mActivity.findViewById(chvck.colourMate.R.id.flash);
	}
	
	public void testPreconditions() {
		assertNotNull(captureButton);
		assertNotNull(flashButton);
	}
	
	public void testCaptureText() {
		assertEquals(mActivity.getString(chvck.colourMate.R.string.button_capture), (String) captureButton.getText());
	}
}
