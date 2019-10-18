package com.example.andrey.lop.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

public class MyImageView extends AppCompatImageView {

    private Context context;
    private GestureListener mGestureListener;
    private GestureDetector mGestureDetector;


    public MyImageView(Context context) {
        super(context);
        sharedConstructing(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sharedConstructing(context);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        sharedConstructing(context);
    }

    private void sharedConstructing(Context context) {
        super.setClickable(true);
        this.context = context;
        mGestureListener = new GestureListener();

        Log.e("Adding", "Listener:::");
        mGestureDetector = new GestureDetector(context, mGestureListener, null, true);

        setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);

                //..my other code logic
               // invalidate();
                return true; // indicate event was handled
            }
        });
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            System.out.println("onDoubleTap");
            Log.e("onDoubleTap", "onDoubleTap");
            return true;
        }

        @Override
        public void onLongPress(MotionEvent event) {
            Log.e("onLongPress", "onLongPress");
        }
    }
}
