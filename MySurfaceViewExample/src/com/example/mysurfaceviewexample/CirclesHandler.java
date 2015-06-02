package com.example.mysurfaceviewexample;

import java.util.ArrayList;
import java.util.List;

public class CirclesHandler {
	
	public static List<Cerchio> listaCerchi;
	
	private CirclesHandler() {}
	
	private static CirclesHandler mInstance;
	
	public static CirclesHandler get() {
		
		if(mInstance == null) {
			mInstance = new CirclesHandler();
			listaCerchi = new ArrayList<Cerchio>();
		}
		return mInstance;
	}
	
	public List<Cerchio> getCircleList() {
		return listaCerchi;
	}

}
