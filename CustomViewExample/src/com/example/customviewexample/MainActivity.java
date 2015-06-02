package com.example.customviewexample;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Resources res = getResources();

		setContentView(R.layout.activity_main);

		RelativeLayout layout = (RelativeLayout) findViewById(R.id.container);
		

		final PieChart pie = (PieChart) this.findViewById(R.id.Pie);
		pie.addItem("Agamemnon", 2, res.getColor(R.color.seafoam));
		pie.addItem("Bocephus", 3.5f, res.getColor(R.color.chartreuse));
		pie.addItem("Calliope", 2.5f, res.getColor(R.color.emerald));
		pie.addItem("Daedalus", 3, res.getColor(R.color.bluegrass));
		pie.addItem("Euripides", 1, res.getColor(R.color.turquoise));
		pie.addItem("Ganymede", 3, res.getColor(R.color.slate));
		final MyTriangle triangle = (MyTriangle) findViewById(R.id.triangle);
		Toast.makeText(MainActivity.this, "p1: " + triangle.p1.x + " / " + triangle.p1.y
										+ "p2: " + triangle.p2.x + " / " + triangle.p2.y
										+ "p3: " + triangle.p3.x + " / " + triangle.p3.y, Toast.LENGTH_LONG).show();
		triangle.setBackgroundColor(res.getColor(R.color.bluegrass));
		triangle.setOnTouchListener(new MyOnTouchListener());

		((Button) findViewById(R.id.Reset))
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View view) {
						pie.setCurrentItem(0);
						
						
					}
				});
	}

	public class MyOnTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			/*Toast.makeText(MainActivity.this,
					"Event: " + event.getX() + " : " + event.getY(),
					Toast.LENGTH_SHORT).show();*/
			
			Point point = new Point((int)event.getX(), (int)event.getY());
			
			Toast.makeText(MainActivity.this,
					"Point: " + point.x + " : " + point.y,
					Toast.LENGTH_SHORT).show(); 
			
			boolean x = MyTriangle.isPointInsideTriangle(point);
			String response;
			if(x) {
				response = "dentro";
			} else {
				response = "fuori";
			}
			
			Toast.makeText(MainActivity.this,
					response,
					Toast.LENGTH_SHORT).show(); 
			
			return false;
		}
	}
}
