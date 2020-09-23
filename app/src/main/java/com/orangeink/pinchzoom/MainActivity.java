package com.orangeink.pinchzoom;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.orangeink.pinchzoom.databinding.ActivityMainBinding;

@SuppressLint("ClickableViewAccessibility")
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ScaleGestureDetector scaleGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        scaleGestureDetector = new ScaleGestureDetector(this, new simpleOnScaleGestureListener());
        binding.textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getPointerCount() != 1) {
                    //When 2 pointers are present
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:

                        case MotionEvent.ACTION_MOVE:
                            //Disallow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                            scaleGestureDetector.onTouchEvent(event);
                            break;

                        case MotionEvent.ACTION_UP:
                            //Allow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return true;
            }
        });
    }

    public class simpleOnScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float size = binding.textView.getTextSize();
            float factor = detector.getScaleFactor();
            int increase = 0;
            if(factor > 1.0f)
                increase = 2;
            else if(factor < 1.0f)
                increase = -2;
            size += increase;
            binding.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            binding.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

}