package com.example.customviewcircles.fragments;

import com.example.customviewcircles.Arbitro;
import com.example.customviewcircles.CirclesCounter;
import com.example.customviewcircles.MainActivity;
import com.example.customviewcircles.R;
import com.example.customviewcircles.R.layout;
import com.example.customviewcircles.customviews.Cerchio;
import com.example.customviewcircles.customviews.CustomCircles;
import com.example.customviewcircles.customviews.Deck;
import com.example.customviewcircles.customviews.Punto;
import com.example.customviewcircles.nativeaudio.CanPlayHandler;
import com.example.customviewcircles.nativeaudio.OpenSLES;
import com.example.customviewcircles.nativeaudio.Sample;
import com.example.customviewcircles.nativeaudio.SlotManager;
import com.example.customviewcircles.threading.PressedButtonRunnable;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.location.Criteria;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SamplerButtonsFragment extends Fragment implements OnTouchListener {

	public static final String _TAG = "CanvasFragment";
	private LinearLayout layout;

	private final static int CIRCLE_PER_ROW = 3;
	private final static int ROWS = 3;

	int circlePerRow = 3;
	int rows = 3;

	Deck deck;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(
				R.layout.sampler_buttons_fragment_layout, container, false);

		final Bundle mySavedInstanceState = savedInstanceState;

		layout = (LinearLayout) rootView
				.findViewById(R.id.samplerButtonsLayout);
		layout.setOnTouchListener(this);

		ViewTreeObserver vto = layout.getViewTreeObserver();

		vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				Log.v("jajaja", "global layout w: " + layout.getMeasuredWidth()
						+ " - h: " + layout.getMeasuredHeight());

				int width = layout.getMeasuredWidth();
				int height = layout.getMeasuredHeight();

				// createRectangle(width, height);
				if (mySavedInstanceState == null && CirclesCounter.getInstance().getCircles().isEmpty()) {
					addCircles(width, height);
				}
				drawCircles();

			}

		});

		SlotManager slotsManager = SlotManager.getInstance();

		Log.v("jajaja", "on create view sampler buttons fragment");

		return rootView;
	}

	private void createRectangle(int width, int height) {
		Deck deck = new Deck(getActivity(), width, height);
		layout.addView(deck);
	}

	private void addCircles(int width, int height) {

		int radius = (width / CIRCLE_PER_ROW) / 2;

		int circleX, circleY;

		// calcolo Y
		for (int x = 0; x < ROWS; ++x) {
			if (x == 0)
				circleY = radius;
			else
				circleY = radius + ((radius * 2) * x);

			// calcolo X
			int j = 0;
			for (j = 0; j < CIRCLE_PER_ROW; ++j) {
				if (j == 0)
					circleX = radius;
				else
					circleX = radius + ((radius * 2) * j);

				CirclesCounter.getInstance().addCircle(
						new Cerchio(new Punto(circleX, circleY), radius));
				/*
				 * Log.v("jajaja", "aggiunto cerchio a singleton. x = " +
				 * circleX + " y = " + circleY);
				 */
			}
		}
	}

	private void drawCircles() {

		// for (Cerchio cerchio : CirclesCounter.getInstance().getCircles()) {
		CustomCircles circle = new CustomCircles(getActivity());

		Log.v("jajaja", "new circles");

		layout.addView(circle);

		Log.v("jajaja", "add circles to layout");
		// }
	}

	public static SamplerButtonsFragment getInstance() {
		return new SamplerButtonsFragment();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		float x = event.getX();
		float y = event.getY();
		int circle = Arbitro.getInstance().isPointInsideCircle(
				new Punto((int) x, (int) y));

		// Log.v("jajaja", "touched circle " + circle);

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// CanPlayHandler.getInstance().setCanPlay(true);
			Log.v("jajaja", "action down - value: "
					+ CanPlayHandler.getInstance().canPlay());
			// avviaCalcolo();
			if (CanPlayHandler.getInstance().canPlay()) {

				// get slots
				Sample[] slots = SlotManager.getInstance().getSlots();

				// avoid out of bound exception
				if (circle > -1 && circle < slots.length) {
					Sample sample = slots[circle];
					String name = sample.getName();
					OpenSLES.getIntance().playTrack(getActivity().getAssets(),
							name);

					CanPlayHandler.getInstance().setCanPlay(false);
				}
			}

		}

		if (event.getAction() == MotionEvent.ACTION_UP) {
			Log.v("jajaja", "actionUp");
			// interrompiCalcolo();
			OpenSLES.getIntance().stopTrack();
			CanPlayHandler.getInstance().setCanPlay(true);
			/*
			 * }
			 */
		}
		return true;
	}

	PressedButtonRunnable mRunnable;
	Thread vThread;

	private void avviaCalcolo() {

		// Primo modo
		// mThread = new MyThread(this);
		// mThread.start();
		// Secondo Modo
		mRunnable = new PressedButtonRunnable(SamplerButtonsFragment.this);
		vThread = new Thread(mRunnable);
		vThread.start();
	}

	private void interrompiCalcolo() {
		mRunnable.stopRunnable();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		// Log.v("jajaja","finalize " + this.getClass().getSimpleName());
	}

	public void showSampleLoader() {
		// Log.v("jajaja", "show sample loader from fragment");
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(getActivity(), "Loader", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}
}
