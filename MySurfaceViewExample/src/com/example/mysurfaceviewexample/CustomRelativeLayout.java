package com.example.mysurfaceviewexample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class CustomRelativeLayout extends RelativeLayout {

	public CustomRelativeLayout(Context context) {
		super(context);
	}

	public CustomRelativeLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		for (int i = 0; i < getChildCount(); ++i) {

			View v = getChildAt(i);
			Punto center = ((CustomCircles) v).getCentro();
			int radius = ((CustomCircles) v).getRadius();
			v.layout(center.x - radius, center.y - radius, center.x + radius,
					center.y + radius);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		for (int i = 0; i < getChildCount(); ++i) {

			View v = getChildAt(i);
			Punto center = ((CustomCircles)v).getCentro();
			v.measure(center.x * 2, center.y *2);
		}
		
		  int desiredWidth = 100;
		    int desiredHeight = 100;

		    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		    int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		    int width;
		    int height;

		    //Measure Width
		    if (widthMode == MeasureSpec.EXACTLY) {
		        //Must be this size
		        width = widthSize;
		    } else if (widthMode == MeasureSpec.AT_MOST) {
		        //Can't be bigger than...
		        width = Math.min(desiredWidth, widthSize);
		    } else {
		        //Be whatever you want
		        width = desiredWidth;
		    }

		    //Measure Height
		    if (heightMode == MeasureSpec.EXACTLY) {
		        //Must be this size
		        height = heightSize;
		    } else if (heightMode == MeasureSpec.AT_MOST) {
		        //Can't be bigger than...
		        height = Math.min(desiredHeight, heightSize);
		    } else {
		        //Be whatever you want
		        height = desiredHeight;
		    }

		    setMeasuredDimension(width, height);
	}

}
