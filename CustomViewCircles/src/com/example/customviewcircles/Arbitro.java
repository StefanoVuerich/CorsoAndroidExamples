package com.example.customviewcircles;

import com.example.customviewcircles.customviews.Cerchio;
import com.example.customviewcircles.customviews.Punto;


public class Arbitro {
	
	private Arbitro() {}
	
	private static Arbitro mInstance;
	
	public static Arbitro getInstance() {
		if(mInstance == null)
			mInstance = new Arbitro();
		
		return mInstance;
	}
	
	public int isPointInsideCircle(Punto punto) {

		for (Cerchio cerchio : CirclesCounter.getInstance().getCircles()) {
			
			double result = Math.sqrt(Math.pow((cerchio.punto.x - punto.x),2) + Math.pow((cerchio.punto.y - punto.y), 2));
			
			if(result <= cerchio.raggio) 
				return cerchio.indexInArray;
		}
		
		return -1;
	}
}
