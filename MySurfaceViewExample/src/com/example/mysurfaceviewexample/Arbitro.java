package com.example.mysurfaceviewexample;


public class Arbitro {
	
	private Arbitro() {}
	
	private static Arbitro mInstance;
	
	public static Arbitro getInstance() {
		if(mInstance == null)
			mInstance = new Arbitro();
		
		return mInstance;
	}
	
	public int isPointInsideCircle(Punto punto) {

		for (Cerchio cerchio : CirclesHandler.get().getCircleList()) {
			
			double result = Math.sqrt(Math.pow((cerchio.punto.x - punto.x),2) + Math.pow((cerchio.punto.y - punto.y), 2));
			
			if(result <= cerchio.raggio) 
				return cerchio.indexInArray;
		}
		
		return -1;
	}
}
