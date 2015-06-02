package com.example.customviewcircles.customviews;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Deck extends ViewGroup {

	private Activity mainActivity;
	private int width;
	private int height;
	private Paint paint;
	
	public Deck(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public Deck(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public Deck(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public Deck(Activity activity, int width, int height) {
		super((Context)activity);
		this.mainActivity = activity;
		this.width = width;
		this.height = height;
		this.paint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		paint.setColor(Color.MAGENTA);
        paint.setStrokeWidth(3);
        canvas.drawRect(0, 0, width, height, paint);
        Log.v("jajaja", "drawed rect");
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		
	}

	
}
