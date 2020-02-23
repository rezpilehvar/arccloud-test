package com.ronaksoftware.musicchi.ui.fragments.login;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ronaksoftware.musicchi.ApplicationLoader;
import com.ronaksoftware.musicchi.R;
import com.ronaksoftware.musicchi.network.ResponseEnvelope;
import com.ronaksoftware.musicchi.network.request.SendCodeRequest;
import com.ronaksoftware.musicchi.network.response.SendCodeResponse;
import com.ronaksoftware.musicchi.ui.presenter.ActionBar;
import com.ronaksoftware.musicchi.ui.presenter.ActionBarMenu;
import com.ronaksoftware.musicchi.ui.presenter.AlertDialog;
import com.ronaksoftware.musicchi.ui.presenter.BaseViewController;
import com.ronaksoftware.musicchi.ui.presenter.Theme;
import com.ronaksoftware.musicchi.utils.DisplayUtility;
import com.ronaksoftware.musicchi.utils.LayoutHelper;
import com.ronaksoftware.musicchi.utils.TypefaceUtility;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class EnterPhoneViewController extends BaseViewController {
    private static final int doneButtonID = 0;

    private EditText phoneNumberField;
    private boolean requestInProgress = false;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        disposables.dispose();
    }

    @Override
    protected ActionBar createActionBar(Context context) {
        return null;
    }

    @Override
    public View createView(Context context) {
        FrameLayout contentView = new FrameLayout(context);

        FrameLayout topView = new FrameLayout(context);
        contentView.addView(topView,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,56,Gravity.TOP | Gravity.LEFT ,0, Build.VERSION.SDK_INT >= 21 ? 24 : 0,0,0));
        topView.setBackgroundColor(Color.parseColor("#FD0C6B"));

        TextView topTextView = new TextView(context);
        topView.addView(topTextView,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,LayoutHelper.WRAP_CONTENT,Gravity.CENTER , 20,0,20,0));
        topTextView.setText(R.string.auth_enter_phone_topText);
        topTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        topTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        topTextView.setTextColor(Color.WHITE);
        topTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans.ttf"));

        ImageView logoImageView = new ImageView(context);
        logoImageView.setImageResource(R.drawable.logo);
        logoImageView.setScaleType(ImageView.ScaleType.CENTER);
        contentView.addView(logoImageView,LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT,LayoutHelper.WRAP_CONTENT,Gravity.CENTER_HORIZONTAL , 0,50 + 56 + (Build.VERSION.SDK_INT >= 21 ? 24 : 0) , 0,0));

        FrameLayout centerFrame = new FrameLayout(context);
        contentView.addView(centerFrame,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,50,Gravity.TOP | Gravity.LEFT , 0,156 + 56 + (Build.VERSION.SDK_INT >= 21 ? 24 : 0),0,0));
        centerFrame.setBackgroundColor(Color.parseColor("#FD0C6B"));
        TextView centerTextView = new TextView(context);
        centerTextView.setText(R.string.auth_enter_phone_centerText);
        centerTextView.setTextColor(Color.WHITE);
        centerTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        centerTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        centerTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans.ttf"));
        centerFrame.addView(centerTextView,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,LayoutHelper.WRAP_CONTENT,Gravity.CENTER,20,0,20,0));


        phoneNumberField = new EditText(context);
        phoneNumberField.setHint("Phone number");
        phoneNumberField.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
        phoneNumberField.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        phoneNumberField.setInputType(InputType.TYPE_CLASS_PHONE);
        contentView.addView(phoneNumberField, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 16, 0, 16, 0));

        fragmentView = contentView;
        return fragmentView;
    }

    private void onNextPress() {
        if (requestInProgress) {
            return;
        }

        String phoneNumber = phoneNumberField.getText().toString();

        if (phoneNumber.isEmpty()) {
            DisplayUtility.shakeView(phoneNumberField, 2, 0);
            return;
        }

        requestInProgress = true;

        final AlertDialog progressDialog = new AlertDialog(getParentActivity(), 3);
        progressDialog.setOnCancelListener(dialog -> {
            setVisibleDialog(null);
        });
        setVisibleDialog(progressDialog);
        showDialog(progressDialog);

        disposables.add(ApplicationLoader.musicChiApi.sendCode(SendCodeRequest.create(phoneNumber)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseEnvelope<SendCodeResponse>>() {
            @Override
            public void accept(ResponseEnvelope<SendCodeResponse> sendCodeRequestResponseEnvelope) throws Exception {
                requestInProgress = false;
                progressDialog.dismiss();
                visibleDialog = null;

                presentFragment(new EnterCodeViewController(sendCodeRequestResponseEnvelope.getPayload(), phoneNumber));
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (throwable instanceof IllegalStateException) {
                    throwable.printStackTrace();
                }

                if (throwable instanceof HttpException) {
                    HttpException error = (HttpException) throwable;

                    if (error.code() == 400) {
                        DisplayUtility.shakeView(phoneNumberField, 2, 0);
                    }
                }
                requestInProgress = false;
                progressDialog.dismiss();
                visibleDialog = null;
            }
        }));

    }

}
