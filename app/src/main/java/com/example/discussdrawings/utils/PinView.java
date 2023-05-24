package com.example.discussdrawings.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.discussdrawings.R;

public class PinView extends SubsamplingScaleImageView implements GestureDetector.OnDoubleTapListener {

    private final Paint paint = new Paint();
    private final PointF vPin = new PointF();
    private PointF sPin;
    private Bitmap pin;

    public PinView(Context context) {
        this(context, null);
    }

    public PinView(Context context, AttributeSet attr) {
        super(context, attr);
        initialise();
    }

    public void setPin(PointF sPin) {
        this.sPin = sPin;
        initialise();
        invalidate();
    }

    private void initialise() {
        float density = getResources().getDisplayMetrics().densityDpi;
        pin = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_push_pin_);
        if(pin != null){
            float w = (density / 420f) * pin.getWidth();
            float h = (density / 420f) * pin.getHeight();
            pin = Bitmap.createScaledBitmap(pin, (int)w, (int)h, true);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Don't draw pin before image is ready so it doesn't move around during setup.
        if (!isReady()) {
            return;
        }

        paint.setAntiAlias(true);

        if (sPin != null && pin != null) {
            sourceToViewCoord(sPin, vPin);
            float vX = vPin.x - (pin.getWidth()/2);
            float vY = vPin.y - pin.getHeight();
            canvas.drawBitmap(pin, vX, vY, paint);
        }

    }

    @Override
    public boolean onSingleTapConfirmed(@NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(@NonNull MotionEvent motionEvent) {
        Log.d("PinView", "double tap");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(@NonNull MotionEvent motionEvent) {
        return false;
    }


//    @Override
//    public boolean onSingleTapConfirmed(@NonNull MotionEvent motionEvent) {
//        return false;
//    }
//
//    @Override
//    public boolean onDoubleTap(@NonNull MotionEvent motionEvent) {
//        Log.d("ZoomClass:", String.format("x,y: %s , %s",motionEvent.getX(), motionEvent.getY()));
//        return false;
//    }
//
//    @Override
//    public boolean onDoubleTapEvent(@NonNull MotionEvent motionEvent) {
//        return false;
//    }
}
