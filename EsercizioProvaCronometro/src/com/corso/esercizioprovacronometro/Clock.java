package com.corso.esercizioprovacronometro;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Clock extends View {

	Bitmap clock;
	Paint linePaint;
	int endLineX, endLineY;
	int width, height;
	int currentTime;

	String[] colors = { "#000000", "#0000FF", "#0000CC", "#FF0000", "#009900",
			"#00FF99", "#99CC00", "#FF00FF", "#6600CC", "#996633" };

	public Clock(Context context) {
		super(context);
		init();
	}

	public Clock(Context context, int width, int height) {
		super(context);
		this.width = width;
		this.height = height;
		init();
	}

	private void init() {
		currentTime = 0;
		endLineX = width / 2;
		endLineY = 0;
		linePaint = new Paint();
		linePaint.setColor(Color.parseColor("#000000"));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawRect(0, 0, currentTime, 100, linePaint);
	}

	public void update(int update) {

		if (update != -1) {
			currentTime = currentTime + (width / 10);
			if (currentTime >= width) {
				currentTime = 0;
				Random rn = new Random();
				int result = rn.nextInt(9 - 0 + 1) + 0;
				linePaint.setColor(Color.parseColor(colors[result]));
			}

		} else {

			currentTime = 0;

		}

		invalidate();
	}
}
