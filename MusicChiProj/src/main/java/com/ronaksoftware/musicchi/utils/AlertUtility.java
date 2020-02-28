package com.ronaksoftware.musicchi.utils;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ronaksoftware.musicchi.R;
import com.ronaksoftware.musicchi.ui.presenter.AlertDialog;
import com.ronaksoftware.musicchi.ui.presenter.BaseViewController;
import com.ronaksoftware.musicchi.ui.presenter.Theme;

public class AlertUtility {
    public static Dialog createTermsAlert(BaseViewController viewController) {
        FrameLayout contentView = new FrameLayout(viewController.getParentActivity());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(viewController.getParentActivity(),0,false);
        alertDialogBuilder.setView(contentView);
        AlertDialog alertDialog = alertDialogBuilder.create();

        Drawable backgroundDrawable = Theme.createRoundRectDrawable(DisplayUtility.dp(20), Color.WHITE);
        contentView.setBackground(backgroundDrawable);

        LinearLayout buttonsContainer = new LinearLayout(viewController.getParentActivity());
        buttonsContainer.setOrientation(LinearLayout.HORIZONTAL);
        contentView.addView(buttonsContainer,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,48, Gravity.BOTTOM , 0,8,0,0));

        TextView titleTextView = new TextView(viewController.getParentActivity());
        titleTextView.setTextColor(Color.BLACK);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP , 14);
        titleTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans-Medium.ttf"));
        titleTextView.setText("شرایط سرویس موزیک چی");
        titleTextView.setGravity(Gravity.CENTER);
        contentView.addView(titleTextView,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,48));

        TextView doneButton = new TextView(viewController.getParentActivity());
        doneButton.setText("موافقم");
        doneButton.setGravity(Gravity.CENTER);
        doneButton.setBackground(Theme.getRoundRectSelectorDrawable(Color.WHITE ,Color.parseColor("#FD0C6B"), new float[]{0,0,DisplayUtility.dp(20),DisplayUtility.dp(20)}));
        doneButton.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans-Medium.ttf"));
        doneButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
        doneButton.setTextColor(Color.WHITE);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        buttonsContainer.addView(doneButton,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,LayoutHelper.MATCH_PARENT));

        ScrollView scrollView = new ScrollView(viewController.getParentActivity());
        contentView.addView(scrollView,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,LayoutHelper.MATCH_PARENT,Gravity.TOP | Gravity.LEFT,0,56,0,48));

        FrameLayout frameLayout = new FrameLayout(viewController.getParentActivity());
        scrollView.addView(frameLayout);
        frameLayout.setPadding(DisplayUtility.dp(12),DisplayUtility.dp(6),DisplayUtility.dp(12),DisplayUtility.dp(6));

        TextView termsTextView = new TextView(viewController.getParentActivity());
        termsTextView.setText(R.string.terms);
        termsTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        termsTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans.ttf"));
        termsTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
        frameLayout.addView(termsTextView,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,LayoutHelper.WRAP_CONTENT));


        viewController.showDialog(alertDialog);
        return alertDialog;
    }

    public static Dialog createFeedBackAlert(BaseViewController viewController , FeedBackAlertDelegate delegate) {
        FrameLayout contentView = new FrameLayout(viewController.getParentActivity());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(viewController.getParentActivity(),0,false);
        alertDialogBuilder.setView(contentView);
        AlertDialog alertDialog = alertDialogBuilder.create();

        Drawable backgroundDrawable = Theme.createRoundRectDrawable(DisplayUtility.dp(20), Color.WHITE);
        contentView.setBackground(backgroundDrawable);

        LinearLayout buttonsContainer = new LinearLayout(viewController.getParentActivity());
        buttonsContainer.setOrientation(LinearLayout.HORIZONTAL);
        contentView.addView(buttonsContainer,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,48, Gravity.BOTTOM , 0,8,0,0));

        TextView titleTextView = new TextView(viewController.getParentActivity());
        titleTextView.setTextColor(Color.BLACK);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP , 14);
        titleTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans-Medium.ttf"));
        titleTextView.setText("لطفا نظرات خود را درباره موزیکجی ارسال کنید.");
        titleTextView.setGravity(Gravity.CENTER);
        contentView.addView(titleTextView,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,48));

        ScrollView scrollView = new ScrollView(viewController.getParentActivity());
        contentView.addView(scrollView,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,LayoutHelper.WRAP_CONTENT,Gravity.TOP | Gravity.LEFT,0,56,0,48));

        LinearLayout linearLayout = new LinearLayout(viewController.getParentActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(linearLayout);
        linearLayout.setPadding(DisplayUtility.dp(12),DisplayUtility.dp(12),DisplayUtility.dp(12),DisplayUtility.dp(12));

        TextView termsTextView = new TextView(viewController.getParentActivity());
        termsTextView.setText("نظرات شما برای ما از اهمیت زیادی برخوردار است. ما با کمک نظرات شما تغییرات و بروزرسانی های موزیکچی را برنامه ریزی میکنیم و شما با ارسال نظراتتان آن را به اپلیکیشن کاربردی و دوست داشتنی تبدیل میکند.");
        termsTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        termsTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans.ttf"));
        termsTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
        linearLayout.addView(termsTextView,LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT,LayoutHelper.WRAP_CONTENT));

        final EditText feedbackLabel = new EditText(viewController.getParentActivity());
        feedbackLabel.setHint("لطفا نظراتتان را اینجا بنویسید");
        feedbackLabel.setTextColor(Color.BLACK);
        feedbackLabel.setHintTextColor(Color.BLACK);
        feedbackLabel.setGravity(Gravity.TOP | Gravity.RIGHT);
        feedbackLabel.setBackground(Theme.createRoundRectDrawable(DisplayUtility.dp(8),Color.parseColor("#E5E9F5")));
        feedbackLabel.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans-Light.ttf"));
        feedbackLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        feedbackLabel.setPadding(DisplayUtility.dp(8),DisplayUtility.dp(4),DisplayUtility.dp(8),DisplayUtility.dp(4));
        feedbackLabel.setMinHeight(DisplayUtility.dp(70));
        linearLayout.addView(feedbackLabel,LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT,LayoutHelper.WRAP_CONTENT,0,12,0,12));

        TextView doneButton = new TextView(viewController.getParentActivity());
        doneButton.setText("ارسال");
        doneButton.setGravity(Gravity.CENTER);
        doneButton.setBackground(Theme.getRoundRectSelectorDrawable(Color.WHITE ,Color.parseColor("#FD0C6B"), new float[]{0,0,DisplayUtility.dp(20),DisplayUtility.dp(20)}));
        doneButton.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans-Medium.ttf"));
        doneButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
        doneButton.setTextColor(Color.WHITE);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (feedbackLabel.getText().length() == 0) {
                    DisplayUtility.shakeView(feedbackLabel,2,0);
                    return;
                }
                delegate.onSendPressed(feedbackLabel.getText().toString());
                alertDialog.dismiss();
            }
        });
        buttonsContainer.addView(doneButton,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,LayoutHelper.MATCH_PARENT));

        viewController.showDialog(alertDialog);
        return alertDialog;
    }

    public interface FeedBackAlertDelegate {
        void onSendPressed(String text);
    }

    public static Dialog createLogoutAlert(BaseViewController viewController , Runnable doneCallback) {
        FrameLayout contentView = new FrameLayout(viewController.getParentActivity());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(viewController.getParentActivity(),0,false);
        alertDialogBuilder.setView(contentView);
        AlertDialog alertDialog = alertDialogBuilder.create();

        Drawable backgroundDrawable = Theme.createRoundRectDrawable(DisplayUtility.dp(20), Color.WHITE);
        contentView.setBackground(backgroundDrawable);

        LinearLayout buttonsContainer = new LinearLayout(viewController.getParentActivity());
        buttonsContainer.setOrientation(LinearLayout.HORIZONTAL);
        contentView.addView(buttonsContainer,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,48, Gravity.BOTTOM , 0,8,0,0));

        TextView cancelButton = new TextView(viewController.getParentActivity());
        cancelButton.setText("انصراف");
        cancelButton.setGravity(Gravity.CENTER);
        cancelButton.setBackground(Theme.getRoundRectSelectorDrawable(Color.WHITE ,Color.parseColor("#FD0C6B"), new float[]{0,0,0,DisplayUtility.dp(20)}));
        cancelButton.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans-Medium.ttf"));
        cancelButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
        cancelButton.setTextColor(Color.WHITE);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        buttonsContainer.addView(cancelButton,LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT,LayoutHelper.MATCH_PARENT,0.5f,Gravity.LEFT,0,0,0,0));


        TextView doneButton = new TextView(viewController.getParentActivity());
        doneButton.setText("بله");
        doneButton.setGravity(Gravity.CENTER);
        doneButton.setBackground(Theme.getRoundRectSelectorDrawable(Color.WHITE ,Color.parseColor("#FD0C6B"), new float[]{0,0,DisplayUtility.dp(20),0}));
        doneButton.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans-Medium.ttf"));
        doneButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
        doneButton.setTextColor(Color.WHITE);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doneCallback.run();
            }
        });
        buttonsContainer.addView(doneButton,LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT,LayoutHelper.MATCH_PARENT,0.5f,Gravity.LEFT,0,0,0,0));


        TextView titleTextView = new TextView(viewController.getParentActivity());
        titleTextView.setTextColor(Color.BLACK);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP , 14);
        titleTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans-Medium.ttf"));
        titleTextView.setText("خروج از حساب کاربری");
        titleTextView.setGravity(Gravity.CENTER);
        contentView.addView(titleTextView,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,48));

        ScrollView scrollView = new ScrollView(viewController.getParentActivity());
        contentView.addView(scrollView,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,LayoutHelper.MATCH_PARENT,Gravity.TOP | Gravity.LEFT,0,56,0,48));

        FrameLayout frameLayout = new FrameLayout(viewController.getParentActivity());
        scrollView.addView(frameLayout);
        frameLayout.setPadding(DisplayUtility.dp(12),DisplayUtility.dp(6),DisplayUtility.dp(12),DisplayUtility.dp(6));

        TextView termsTextView = new TextView(viewController.getParentActivity());
        termsTextView.setText("پس از خروج , قادر به ادامه استفاده از سرویس موزیکچی نخواهید بود.");
        termsTextView.setGravity(Gravity.CENTER);
        termsTextView.setTextColor(Color.BLACK);
        termsTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans.ttf"));
        termsTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
        frameLayout.addView(termsTextView,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,LayoutHelper.WRAP_CONTENT));


        viewController.showDialog(alertDialog);
        return alertDialog;
    }

    public static Dialog createLogoutAlert2(BaseViewController viewController , Runnable doneCallback) {
        FrameLayout contentView = new FrameLayout(viewController.getParentActivity());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(viewController.getParentActivity(),0,false);
        alertDialogBuilder.setView(contentView);
        AlertDialog alertDialog = alertDialogBuilder.create();

        Drawable backgroundDrawable = Theme.createRoundRectDrawable(DisplayUtility.dp(20), Color.WHITE);
        contentView.setBackground(backgroundDrawable);

        LinearLayout buttonsContainer = new LinearLayout(viewController.getParentActivity());
        buttonsContainer.setOrientation(LinearLayout.HORIZONTAL);
        contentView.addView(buttonsContainer,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,48, Gravity.BOTTOM , 0,8,0,0));

        TextView cancelButton = new TextView(viewController.getParentActivity());
        cancelButton.setText("انصراف");
        cancelButton.setGravity(Gravity.CENTER);
        cancelButton.setBackground(Theme.getRoundRectSelectorDrawable(Color.WHITE ,Color.parseColor("#FD0C6B"), new float[]{0,0,0,DisplayUtility.dp(20)}));
        cancelButton.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans-Medium.ttf"));
        cancelButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
        cancelButton.setTextColor(Color.WHITE);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        buttonsContainer.addView(cancelButton,LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT,LayoutHelper.MATCH_PARENT,0.5f,Gravity.LEFT,0,0,0,0));


        TextView doneButton = new TextView(viewController.getParentActivity());
        doneButton.setText("بله");
        doneButton.setGravity(Gravity.CENTER);
        doneButton.setBackground(Theme.getRoundRectSelectorDrawable(Color.WHITE ,Color.parseColor("#FD0C6B"), new float[]{0,0,DisplayUtility.dp(20),0}));
        doneButton.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans-Medium.ttf"));
        doneButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
        doneButton.setTextColor(Color.WHITE);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                doneCallback.run();
            }
        });
        buttonsContainer.addView(doneButton,LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT,LayoutHelper.MATCH_PARENT,0.5f,Gravity.LEFT,0,0,0,0));


        TextView titleTextView = new TextView(viewController.getParentActivity());
        titleTextView.setTextColor(Color.BLACK);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP , 14);
        titleTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans-Medium.ttf"));
        titleTextView.setText("تایید خروج");
        titleTextView.setGravity(Gravity.CENTER);
        contentView.addView(titleTextView,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,48));

        ScrollView scrollView = new ScrollView(viewController.getParentActivity());
        contentView.addView(scrollView,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,LayoutHelper.MATCH_PARENT,Gravity.TOP | Gravity.LEFT,0,56,0,48));

        FrameLayout frameLayout = new FrameLayout(viewController.getParentActivity());
        scrollView.addView(frameLayout);
        frameLayout.setPadding(DisplayUtility.dp(12),DisplayUtility.dp(6),DisplayUtility.dp(12),DisplayUtility.dp(6));

        TextView termsTextView = new TextView(viewController.getParentActivity());
        termsTextView.setText("برای لغو اشتراک کلمه off را به شماره ۴۰۶۵۵۵ پیامک نمایید. از خروج اطمینان دارید؟");
        termsTextView.setTextColor(Color.BLACK);
        termsTextView.setGravity(Gravity.CENTER);
        termsTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans.ttf"));
        termsTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
        frameLayout.addView(termsTextView,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,LayoutHelper.WRAP_CONTENT));


        viewController.showDialog(alertDialog);
        return alertDialog;
    }
}
