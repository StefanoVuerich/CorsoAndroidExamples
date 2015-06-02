package com.example.customviewcircles;

import java.util.ArrayList;

import com.example.customviewcircles.customviews.Cerchio;

public class CirclesCounter {
	
	private static ArrayList<Cerchio> circles;
	private static CirclesCounter mInstance;
	
	private CirclesCounter() {}
	
	public static CirclesCounter getInstance() {
		if(mInstance == null) {
			mInstance = new CirclesCounter();
			circles = new ArrayList<Cerchio>();
		}
		
		return mInstance;
	}

	public void addCircle(Cerchio cerchio) {
		cerchio.indexInArray = circles.size();
		circles.add(cerchio);
	}

	public ArrayList<Cerchio> getCircles() {
		return circles;
	}
}
