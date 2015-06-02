package com.example.customviewexample;

import android.view.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * @author Atrix1987
 * 
 */
public class MyTriangle extends LinearLayout {

	static Point p1,p2,p3;
    /**
     * @param context
     */
    public MyTriangle(Context context) {
        super(context);
        commonConstructor(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public MyTriangle(Context context, AttributeSet attrs) {
        super(context, attrs);
        commonConstructor(context);
    }

    /**
     * 
     */
    Paint trianglePaint;
    /**
     * 
     */
    Path trianglePath;

    /**
     * @param context
     */
    private void commonConstructor(Context context) {
        trianglePaint = new Paint();
        trianglePaint.setStyle(Style.FILL);
        trianglePaint.setColor(Color.RED);
        p1 = new Point();
        p1.x = 100;
        p1.y = 100;
        trianglePath = getEquilateralTriangle(p1, 200, Direction.SOUTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Log.i("Sample", "inside ondraw");
        
        //avoid creating objects in onDraw
        int height = canvas.getHeight();
        int width = canvas.getWidth();
        
        
        canvas.drawPath(trianglePath, trianglePaint);
        canvas.scale(1,-1,width,-height);
        
    }

    private Path getEquilateralTriangle(Point p1, int width, Direction direction) {
        Log.i("Sample", "inside getEqui");
        p2 = null;
        p3 = null;

        if (direction == Direction.NORTH) {
            p2 = new Point(p1.x + width, p1.y);
            p3 = new Point(p1.x + (width / 2), p1.y - width);
        } else if (direction == Direction.SOUTH) {
            p2 = new Point(p1.x + width, p1.y);
            p3 = new Point(p1.x + (width / 2), p1.y + width);
        } else if (direction == Direction.EAST) {
            p2 = new Point(p1.x, p1.y + width);
            p3 = new Point(p1.x - width, p1.y + (width / 2));
        } else if (direction == Direction.WEST) {
            p2 = new Point(p1.x, p1.y + width);
            p3 = new Point(p1.x + width, p1.y + (width / 2));
        }

        Path path = new Path();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);

        return path;
    }

    public enum Direction {
        NORTH, SOUTH, EAST, WEST;
    }
    
    public static boolean isPointInsideTriangle(Point p) {
    	
    	float alpha = ((p2.y - p3.y)*(p.x - p3.x) + (p3.x - p2.x)*(p.y - p3.y)) /
    	        ((p2.y - p3.y)*(p1.x - p3.x) + (p3.x - p2.x)*(p1.y - p3.y));
    	float beta = ((p3.y - p1.y)*(p.x - p3.x) + (p1.x - p3.x)*(p.y - p3.y)) /
    	       ((p2.y - p3.y)*(p1.x - p3.x) + (p3.x - p2.x)*(p1.y - p3.y));
    	float gamma = 1.0f - alpha - beta;
    	
    	if(alpha > 0f && beta >0f && gamma > 0f)
    		return true;
    	
    	return false;
    }
}
