package com.ronaksoftware.musicchi.ui.cells;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ronaksoftware.musicchi.utils.LayoutHelper;
import com.ronaksoftware.musicchi.utils.TypefaceUtility;

public class SettingsCell extends FrameLayout {
    private ImageView iconImageView;
    private TextView titleTextView;

    public SettingsCell(@NonNull Context context) {
        super(context);
        iconImageView = new ImageView(context);
        iconImageView.setScaleType(ImageView.ScaleType.CENTER);
        addView(iconImageView, LayoutHelper.createFrame(24,24, Gravity.RIGHT | Gravity.CENTER_VERTICAL , 20,0,20,0));

        titleTextView = new TextView(context);
        titleTextView.setTextColor(Color.WHITE);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP , 16);
        titleTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans.ttf"));
        addView(titleTextView,LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT,LayoutHelper.WRAP_CONTENT,Gravity.RIGHT | Gravity.CENTER_VERTICAL , 0,0,57,0));
    }

    public void update(int icon , String title) {
        titleTextView.setText(title);
        iconImageView.setImageResource(icon);
    }
}
