/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 12/28/18 2:47 PM
 */

package com.mobileprogramming.android.stickydemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * StickyDemo created by jamesmobileprogramming on 12/28/18.
 */
public class PointerDrawable extends Drawable {

    private final Paint mPaint = new Paint();
    private boolean mEnabled;

    @Override
    public void draw(@NonNull Canvas canvas) {

        float cx = canvas.getWidth()/2;
        float cy = canvas.getHeight()/2;

        if (mEnabled) {
            mPaint.setColor(Color.GREEN);
            canvas.drawCircle(cx, cy, 10, mPaint);
        } else {
            mPaint.setColor(Color.GRAY);
            canvas.drawText("X", cx, cy, mPaint);
        }

    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }
}
