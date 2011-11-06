package chvck.colourMate.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.SurfaceView;

public class PreviewSurface extends SurfaceView {
	protected final Paint rectanglePaint = new Paint();
	public PreviewSurface(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas){
		//work out where we need to draw our little square area
		int width = this.getWidth();
		int height = this.getHeight();

		rectanglePaint.setStyle(Style.STROKE);
		rectanglePaint.setARGB(150, 0, 255, 0);

		int left = (width/2)-20; 
		int right = (width/2)+20; 
		int top = (height/2)-20; 
		int bottom = (height/2)+20;

		//draw it
		//top left
		canvas.drawLine(left, top+10, left, top, rectanglePaint);
		canvas.drawLine(left, top, left+10, top, rectanglePaint);

		//bottom left
		canvas.drawLine(left, bottom, left, bottom-10, rectanglePaint);
		canvas.drawLine(left, bottom, left+10, bottom, rectanglePaint);

		//top right
		canvas.drawLine(right, top+10, right, top, rectanglePaint);
		canvas.drawLine(right-10, top, right, top, rectanglePaint);

		//bottom right
		canvas.drawLine(right, bottom, right, bottom-10, rectanglePaint);
		canvas.drawLine(right-10, bottom, right, bottom, rectanglePaint);

	}
}
