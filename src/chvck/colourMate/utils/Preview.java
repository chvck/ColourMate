package chvck.colourMate.utils;

import java.io.IOException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Preview extends SurfaceView implements SurfaceHolder.Callback { 
	protected final Paint rectanglePaint = new Paint();
	SurfaceHolder mHolder;  
	public Camera camera;
	private float horizontalDistance;
	private float verticalDistance;

	public Preview(Context context) {
		super(context);

		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		mHolder = getHolder();  
		mHolder.addCallback(this);  
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	  
	// Called once the holder is ready
	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, acquire the camera and tell it where
		// to draw.
		setWillNotDraw(false);
		camera = Camera.open();
		try {
			camera.setPreviewDisplay(holder);
		} catch (IOException exception) {
			camera.release();
			camera = null;
			// TODO: add more exception handling logic here
		}
	}

	public void onPause() {
		camera.release();
	}

	public void onResume() {
		camera = Camera.open();
	}

	// Called when the holder is destroyed
	public void surfaceDestroyed(SurfaceHolder holder) {  // <14>
		camera.stopPreview();
		camera.release();
		camera = null;
	}

	// Called when holder has changed
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) { // <15>
		Camera.Parameters parameters = camera.getParameters();
        parameters.setPreviewSize(w, h);
        requestLayout();
        
		camera.setParameters(parameters);
		camera.startPreview();
	}
	
	public float getHorizontalDistance(){
		return horizontalDistance;
	}

	public float getVerticallDistance(){
		return verticalDistance;
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		//work out where we need to draw our little square area
		int width = this.getWidth();
		int height = this.getHeight();

		rectanglePaint.setStyle(Style.STROKE);
		rectanglePaint.setARGB(150, 0, 255, 0);

		horizontalDistance = (float) width / 10;
		verticalDistance = (float) height / 10 ;
		
		float halfWidth =  (float) width / 2;
		float halfHeight = (float) height / 2;
		float halfHorizontalDistance = (float) horizontalDistance / 2;
		float halfVerticalDistance = (float) verticalDistance / 2;
				
		float left = halfWidth - horizontalDistance; 
		float right = halfWidth + horizontalDistance; 
		float top = halfHeight - verticalDistance; 
		float bottom = halfHeight + verticalDistance;
		
		//draw it
		//top left
		canvas.drawLine(left, top + halfVerticalDistance, left, top, rectanglePaint);
		canvas.drawLine(left, top, left + halfHorizontalDistance, top, rectanglePaint);

		//bottom left
		canvas.drawLine(left, bottom, left, bottom - halfVerticalDistance, rectanglePaint);
		canvas.drawLine(left, bottom, left + halfHorizontalDistance, bottom, rectanglePaint);

		//top right
		canvas.drawLine(right, top + halfVerticalDistance, right, top, rectanglePaint);
		canvas.drawLine(right - halfHorizontalDistance, top, right, top, rectanglePaint);

		//bottom right
		canvas.drawLine(right, bottom, right, bottom - halfVerticalDistance, rectanglePaint);
		canvas.drawLine(right - halfHorizontalDistance, bottom, right, bottom, rectanglePaint);

	}

}