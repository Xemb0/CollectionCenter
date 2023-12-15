package com.test.samplecollection;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class CustomRecyclerView extends RecyclerView {
    private float startX = 0;
    private float startY = 0;
    private static final int TOUCH_SLOP = 10; // Adjust this value as needed

    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Store the initial touch X and Y coordinates
                startX = e.getX();
                startY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // Calculate the distance moved in X and Y directions
                float distanceX = Math.abs(e.getX() - startX);
                float distanceY = Math.abs(e.getY() - startY);

                // If the distance moved horizontally is greater than the slop value and greater than the vertical distance
                if (distanceX > TOUCH_SLOP && distanceX > distanceY) {
                    getParent().requestDisallowInterceptTouchEvent(true); // Disallow parent to intercept touch events
                    return true; // Intercept the touch event
                }
                break;
        }
        return super.onInterceptTouchEvent(e);
    }
}
