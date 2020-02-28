package com.ronaksoftware.musicchi.ui.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ronaksoftware.musicchi.UserConfigs;
import com.ronaksoftware.musicchi.ui.presenter.ActionBar;
import com.ronaksoftware.musicchi.ui.presenter.BackDrawable;
import com.ronaksoftware.musicchi.ui.presenter.BaseViewController;
import com.ronaksoftware.musicchi.ui.presenter.Theme;
import com.ronaksoftware.musicchi.utils.Browser;
import com.ronaksoftware.musicchi.utils.DisplayUtility;
import com.ronaksoftware.musicchi.utils.LayoutHelper;
import com.ronaksoftware.musicchi.utils.TypefaceUtility;

public class UpdateViewController extends BaseViewController {
    private boolean force;

    private FrameLayout contentView;

    public UpdateViewController(boolean force) {
        this.force = force;
    }


    @Override
    public View createView(Context context) {
        fragmentView = contentView = new FrameLayout(context);
        contentView.setBackground(new ColorDrawable(Color.parseColor("#1A1E2A")));

        FrameLayout actionBarView = new FrameLayout(context);
        contentView.addView(actionBarView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, 56, Gravity.TOP | Gravity.LEFT, 0, Build.VERSION.SDK_INT >= 21 ? 24 : 0, 0, 0));
        actionBarView.setBackground(Theme.createRoundRectDrawable(new float[]{0, 0, DisplayUtility.dp(18), 0}, Color.parseColor("#FD0C6B")));

        TextView titleTextView = new TextView(context);
        titleTextView.setText("بروز رسانی");
        titleTextView.setTextColor(Color.WHITE);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        titleTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans-Medium.ttf"));
        actionBarView.addView(titleTextView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.TOP | Gravity.RIGHT, 0, 16, 20, 0));

        if (!force) {
            ImageView backButton = new ImageView(context);
            backButton.setScaleType(ImageView.ScaleType.CENTER);
            backButton.setImageDrawable(new BackDrawable(false));
            backButton.setBackground(Theme.createSelectorDrawable(Color.WHITE));
            actionBarView.addView(backButton, LayoutHelper.createFrame(56, 56, Gravity.TOP | Gravity.LEFT, 0, 0, 0, 0));
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finishFragment(true);
                }
            });
        }


        LinearLayout centerLinear = new LinearLayout(context);
        centerLinear.setBackground(null);
        centerLinear.setOrientation(LinearLayout.VERTICAL);
        contentView.addView(centerLinear, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 40, 0, 40, 0));

        TextView descriptionTitleTextView = new TextView(context);
        descriptionTitleTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans-Medium.ttf"));
        descriptionTitleTextView.setText("نسخه ی جدید از موزیکچی آماده دانلود می باشد.");
        descriptionTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        descriptionTitleTextView.setTextColor(Color.WHITE);
        centerLinear.addView(descriptionTitleTextView, LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL));

        TextView descriptionContentTextView = new TextView(context);
        descriptionContentTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans.ttf"));
        descriptionContentTextView.setText("لطفا اپلیکیشن را بروز نمایید.");
        descriptionContentTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        descriptionContentTextView.setTextColor(Color.WHITE);
        centerLinear.addView(descriptionContentTextView, LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL));

        TextView updateButton = new TextView(context);
        updateButton.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans-Medium.ttf"));
        updateButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        updateButton.setTextColor(Color.WHITE);
        updateButton.setGravity(Gravity.CENTER);
        updateButton.setText("بروز رسانی");
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Browser.openUrl(getParentActivity(), UserConfigs.storeLink);
            }
        });
        updateButton.setBackground(Theme.getRoundRectSelectorDrawable(Color.WHITE, Color.parseColor("#FD0C6B"), DisplayUtility.dp(4)));
        centerLinear.addView(updateButton, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 50, 0, 18, 0, 0));

        if (!force) {
            TextView ignoreButton = new TextView(context);
            ignoreButton.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans-Medium.ttf"));
            ignoreButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            ignoreButton.setTextColor(Color.WHITE);
            ignoreButton.setGravity(Gravity.CENTER);
            ignoreButton.setText("فعلا نه");
            ignoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finishFragment(true);
                }
            });
            ignoreButton.setBackground(Theme.getRoundRectSelectorDrawable(Color.WHITE, Color.parseColor("#11151E"), DisplayUtility.dp(4)));
            centerLinear.addView(ignoreButton, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 50, 0, 8, 0, 0));
        }


        return fragmentView;
    }

    @Override
    protected ActionBar createActionBar(Context context) {
        return null;
    }
}
