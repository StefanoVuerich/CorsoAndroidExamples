package com.example.layoutwithninecustomcircles;

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

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	private void init() {

		
	}

	public void changeColor() {
		this.paint.setColor(Color.parseColor("#0000FF"));
		// this.invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		
	Log.v("jajaja", "w->" + canvas.getWidth() + " - h->" + canvas.getHeight());
		paint = new Paint();
		paint.setColor(Color.parseColor("#000000"));

		// canvas.drawRect(100, 100, 100, 100, paint);

		canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, canvas.getHeight()/2, paint);
	}
}
