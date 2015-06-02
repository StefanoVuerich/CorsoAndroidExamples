package com.example.customviewcircles.customviews;

import com.example.customviewcircles.CirclesCounter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CustomCircles extends View {

	Paint paint;

	public CustomCircles(Context context) {
		super(context);
		init();
	}

	public CustomCircles(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CustomCircles(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private void init() {
		paint = new Paint();
		paint.setColor(Color.parseColor("#000000"));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int x = canvas.getWidth();
		int y = canvas.getHeight();
		
		int count = 0;
		
		for (Cerchio cerchio : CirclesCounter.getInstance().getCircles()) {
			canvas.drawCircle(cerchio.punto.x, cerchio.punto.y, cerchio.raggio, paint);
			++count;
		}
		Log.v("jajajaFM", "draw cerchio " + count);
	}
}
