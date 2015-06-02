package com.example.mysurfaceviewexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CustomCircles extends View implements View.OnClickListener {

	private Punto centro;
	private Paint paint;
	private int radius;
	private int id;

	public CustomCircles(Context context, Punto centro, int radius, int id) {
		this(context);
		this.centro = centro;
		this.radius = radius;
		this.id = id;
	}

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
	
	public Punto getCentro() {
		return centro;
	}

	public int getRadius() {
		return radius;
	}

	public void setCentro(Punto centro) {
		this.centro = centro;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	private void init() {

		this.setOnClickListener(this);
		paint = new Paint();
		paint.setColor(Color.parseColor("#000000"));
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawCircle(radius, radius, radius, paint);
	}
	
	private void changeColor() {
		this.paint.setColor(Color.parseColor("#0000FF"));
		invalidate();
		
	}

	@Override
	public void onClick(View v) {
		Log.v("jajaja", "Clicked " + this.id);
		changeColor();
	}
}
