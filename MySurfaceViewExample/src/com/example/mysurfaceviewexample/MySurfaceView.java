package com.example.mysurfaceviewexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements Runnable, Callback {

	SurfaceHolder holder = null;
	Thread thread = null;
	boolean isItOK = false;
	Bitmap ball;
	static float x, y;
	CirclesHandler ballsHandler;
	Context context;

	public MySurfaceView(Context context) {
		super(context);
		holder = getHolder();
		holder.addCallback(this);
		this.context = context;
		holder.setFixedSize(1192, 768);
		// isItOK = true;
		// thread = new Thread(this);
		// thread.run();
	}
	
	

	public static void updatePoint(float x, float y) {
		MySurfaceView.x = x;
		MySurfaceView.y = y;
	}

	@Override
	public void run() {
		x = y = 0;
		while (isItOK) {

			// Log.v("jajaja", "thread is running");

			try {
				thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// drawing in canvas
			if (!holder.getSurface().isValid()) {
				Log.v("jajaja", "holder not ready");
				continue; // avoid execution of below code and restart while
							// loop
			}
			Canvas canvas = holder.lockCanvas();// lock the door
			canvas.drawARGB(255, 150, 150, 10);
			
			Paint paint = new Paint();
			paint.setColor(Color.parseColor("#0000FF"));

			/*for (Cerchio cerchio : CirclesHandler.get().getCircleList()) {
				// Bitmap ball =
				// BallsHandler.get(context).getBallsList().get(i);

				
				
				canvas.drawCircle(cerchio.punto.x, cerchio.punto.y, cerchio.raggio,
						paint);				Log.v("jajaja", "draw circle");

				// canvas.drawBitmap(ball, ball.getWidth()*i, ball.getWidth()*i,
				// null);
			}*/
			
			holder.unlockCanvasAndPost(canvas);// unlock the door and show paint
			Log.v("jajaja", "unlock canvas");

		}
	}

	public void pause() {
		isItOK = false;
		while (true) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		}
		thread = null;
	}

	public void resume() {
		thread = new Thread(this);
		isItOK = true;
		thread.start();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		/*
		 * Log.v("jajaja", "surface created");
		 * 
		 * Canvas canvas = holder.lockCanvas();// lock the door
		 * canvas.drawARGB(255, 150, 150, 10);
		 * 
		 * /* ball = BitmapFactory.decodeResource(getResources(),
		 * R.drawable.scrubber);
		 * 
		 * 
		 * canvas.drawBitmap(ball, x, y, null);
		 */
		
		Canvas canvas = holder.lockCanvas();

		

		holder.unlockCanvasAndPost(canvas);// unlock the door and show paint
		// Log.v("jajaja", "unlock canvas"); // to the world
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

}
