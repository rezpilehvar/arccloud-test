package com.ronaksoftware.musicchi.ui.presenter;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.view.animation.DecelerateInterpolator;

import com.ronaksoftware.musicchi.utils.DisplayUtility;

public class MenuDrawable extends Drawable {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean reverseAngle;
    private long lastFrameTime;
    private boolean animationInProgress;
    private float finalRotation;
    private float currentRotation;
    private int currentAnimationTime;
    private boolean rotateToBack = true;
    private DecelerateInterpolator interpolator = new DecelerateInterpolator();

    public MenuDrawable() {
        super();
        paint.setStrokeWidth(DisplayUtility.dp(2));
    }

    public void setRotateToBack(boolean value) {
        rotateToBack = value;
    }

    public void setRotation(float rotation, boolean animated) {
        lastFrameTime = 0;
        if (currentRotation == 1) {
            reverseAngle = true;
        } else if (currentRotation == 0) {
            reverseAngle = false;
        }
        lastFrameTime = 0;
        if (animated) {
            if (currentRotation < rotation) {
                currentAnimationTime = (int) (currentRotation * 200);
            } else {
                currentAnimationTime = (int) ((1.0f - currentRotation) * 200);
            }
            lastFrameTime = SystemClock.elapsedRealtime();
            finalRotation = rotation;
        } else {
            finalRotation = currentRotation = rotation;
        }
        invalidateSelf();
    }

    @Override
    public void draw(Canvas canvas) {
        if (currentRotation != finalRotation) {
            long newTime = SystemClock.elapsedRealtime();
            if (lastFrameTime != 0) {
                long dt = newTime - lastFrameTime;

                currentAnimationTime += dt;
                if (currentAnimationTime >= 200) {
                    currentRotation = finalRotation;
                } else {
                    if (currentRotation < finalRotation) {
                        currentRotation = interpolator.getInterpolation(currentAnimationTime / 200.0f) * finalRotation;
                    } else {
                        currentRotation = 1.0f - interpolator.getInterpolation(currentAnimationTime / 200.0f);
                    }
                }
            }
            lastFrameTime = newTime;
            invalidateSelf();
        }

        canvas.save();
        canvas.translate(getIntrinsicWidth() / 2, getIntrinsicHeight() / 2);
        float endYDiff;
        float endXDiff;
        float startYDiff;
        float startXDiff;
        int color1 = Theme.getColor(Theme.key_actionBarDefaultIcon);
        if (rotateToBack) {
            canvas.rotate(currentRotation * (reverseAngle ? -180 : 180));
            paint.setColor(color1);
            canvas.drawLine(-DisplayUtility.dp(9), 0, DisplayUtility.dp(9) - DisplayUtility.dp(3.0f) * currentRotation, 0, paint);
            endYDiff = DisplayUtility.dp(5) * (1 - Math.abs(currentRotation)) - DisplayUtility.dp(0.5f) * Math.abs(currentRotation);
            endXDiff = DisplayUtility.dp(9) - DisplayUtility.dp(2.5f) * Math.abs(currentRotation);
            startYDiff = DisplayUtility.dp(5) + DisplayUtility.dp(2.0f) * Math.abs(currentRotation);
            startXDiff = -DisplayUtility.dp(9) + DisplayUtility.dp(7.5f) * Math.abs(currentRotation);
        } else {
            canvas.rotate(currentRotation * (reverseAngle ? -225 : 135));
            int color2 = Theme.getColor(Theme.key_actionBarActionModeDefaultIcon);
            paint.setColor(DisplayUtility.getOffsetColor(color1, color2, currentRotation, 1.0f));
            canvas.drawLine(-DisplayUtility.dp(9) + DisplayUtility.dp(1) * currentRotation, 0, DisplayUtility.dp(9) - DisplayUtility.dp(1) * currentRotation, 0, paint);
            endYDiff = DisplayUtility.dp(5) * (1 - Math.abs(currentRotation)) - DisplayUtility.dp(0.5f) * Math.abs(currentRotation);
            endXDiff = DisplayUtility.dp(9) - DisplayUtility.dp(9) * Math.abs(currentRotation);
            startYDiff = DisplayUtility.dp(5) + DisplayUtility.dp(3.0f) * Math.abs(currentRotation);
            startXDiff = -DisplayUtility.dp(9) + DisplayUtility.dp(9) * Math.abs(currentRotation);
        }
        canvas.drawLine(startXDiff, -startYDiff, endXDiff, -endYDiff, paint);
        canvas.drawLine(startXDiff, startYDiff, endXDiff, endYDiff, paint);
        canvas.restore();
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return DisplayUtility.dp(24);
    }

    @Override
    public int getIntrinsicHeight() {
        return DisplayUtility.dp(24);
    }
}
