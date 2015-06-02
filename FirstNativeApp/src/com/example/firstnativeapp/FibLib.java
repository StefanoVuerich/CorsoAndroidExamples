package com.example.firstnativeapp;

public class FibLib {

	public static long fibJR(long n) {
		return n <= 0 ? 0 : n == 1 ? 1 : fibJR(n - 1) + fibJR(n - 2);
	}

	public native static long fibNR(long n);

	public static long fibJI(long n) {
		long previus = -1;
		long result = 1;
		for (long i = 0; i <= n; i++) {
			long sum = result + previus;
			previus = result;
			result = sum;
		}
		return result;
	}
	
	public native static long fibNI(long n);
	
	static {
		System.loadLibrary("com_example_firstnativeapp_FibLib");
	}
}
