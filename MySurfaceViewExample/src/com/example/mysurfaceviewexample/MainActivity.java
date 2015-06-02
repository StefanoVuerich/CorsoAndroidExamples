package com.example.mysurfaceviewexample;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewTreeObserver;

public class MainActivity extends Activity {

	MySurfaceView view;
	CustomRelativeLayout layout;
	List<CustomCircles> circlesArr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		layout = (CustomRelativeLayout) findViewById(R.id.customRelativeLayout);

		ViewTreeObserver vto = layout.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

				int width = layout.getMeasuredWidth();
				int height = layout.getMeasuredHeight();
				
				int radius = calculateCircleRadius(height);
				calculateCirclesPosition(radius);
			}
		});
	}

	int circlesPerRow = 3;
	int rows = 3;
	
 private void calculateCirclesPosition(int radius) {
	 
		int index = 0;
		circlesArr = new ArrayList<CustomCircles>();
		
		for (int i = 0; i < rows; ++i) {
			int y = radius + ((radius * 2) * i);
					
			for (int j = 0; j < circlesPerRow; ++j) {
				
				int x = radius + ((radius * 2) * j);
				Punto centro = new Punto(x, y);
				Cerchio cerchio = new Cerchio(centro, radius);
				cerchio.indexInArray = index;
				
				CirclesHandler.get().getCircleList().add(cerchio);
				
				CustomCircles circle = new CustomCircles(this, centro,
						radius, index++);
				
				circlesArr.add(circle);
					
				layout.addView(circle);	
			}
		}
	}

	private int calculateCircleRadius(int height) {
		return (height / 3) / 2;
	}
}
