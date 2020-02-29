package com.ronaksoftware.musicchi.ui.fragments.login;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.CompoundButtonCompat;

import com.ronaksoftware.musicchi.ApplicationLoader;
import com.ronaksoftware.musicchi.R;
import com.ronaksoftware.musicchi.controllers.SoundRecognizer;
import com.ronaksoftware.musicchi.network.ResponseEnvelope;
import com.ronaksoftware.musicchi.network.request.SendCodeRequest;
import com.ronaksoftware.musicchi.network.response.SendCodeResponse;
import com.ronaksoftware.musicchi.ui.components.HintEditText;
import com.ronaksoftware.musicchi.ui.presenter.ActionBar;
import com.ronaksoftware.musicchi.ui.presenter.ActionBarMenu;
import com.ronaksoftware.musicchi.ui.presenter.AlertDialog;
import com.ronaksoftware.musicchi.ui.presenter.BaseViewController;
import com.ronaksoftware.musicchi.ui.presenter.Theme;
import com.ronaksoftware.musicchi.utils.AlertUtility;
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

    private HintEditText phoneNumberField;
    private LinearLayout termsContainer;

    private boolean requestInProgress = false;
    private CompositeDisposable disposables = new CompositeDisposable();

    private boolean ignoreOnPhoneChange;

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
        contentView.addView(topView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.TOP | Gravity.LEFT, 0, Build.VERSION.SDK_INT >= 21 ? 24 : 0, 0, 0));
        topView.setBackgroundColor(Color.parseColor("#FD0C6B"));

        TextView topTextView = new TextView(context);
        topView.addView(topTextView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 20, 0, 20, 0));
        topTextView.setText(R.string.auth_enter_phone_topText);
        topTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        topTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        topTextView.setTextColor(Color.WHITE);
        topTextView.setPadding(DisplayUtility.dp(20), DisplayUtility.dp(8), DisplayUtility.dp(20), DisplayUtility.dp(8));
        topTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans.ttf"));

        ImageView logoImageView = new ImageView(context);
        logoImageView.setImageResource(R.drawable.logo);
        logoImageView.setScaleType(ImageView.ScaleType.CENTER);
        contentView.addView(logoImageView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL, 0, 50 + 56 + 18 + (Build.VERSION.SDK_INT >= 21 ? 24 : 0), 0, 0));

        FrameLayout centerFrame = new FrameLayout(context);
        contentView.addView(centerFrame, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, 58, Gravity.TOP | Gravity.LEFT, 0, 156 + 56 + 18 + (Build.VERSION.SDK_INT >= 21 ? 24 : 0), 0, 0));
        centerFrame.setBackgroundColor(Color.parseColor("#FD0C6B"));
        TextView centerTextView = new TextView(context);
        centerTextView.setText(R.string.auth_enter_phone_centerText);
        centerTextView.setTextColor(Color.WHITE);
        centerTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        centerTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        centerTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans.ttf"));
        centerFrame.addView(centerTextView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 20, 0, 20, 0));


        phoneNumberField = new HintEditText(context);
        phoneNumberField.setInputType(InputType.TYPE_CLASS_PHONE);
        phoneNumberField.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteInputField));
        phoneNumberField.setHintColor(Theme.getColor(Theme.key_windowBackgroundWhiteInputField));
        phoneNumberField.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
        phoneNumberField.setPadding(0, 0, 0, DisplayUtility.dp(8));
        phoneNumberField.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteInputField));
        phoneNumberField.setCursorSize(DisplayUtility.dp(20));
        phoneNumberField.setCursorWidth(1.5f);
        phoneNumberField.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        phoneNumberField.setMaxLines(1);
        phoneNumberField.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        phoneNumberField.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        phoneNumberField.setHintText("09-- --- ----");

        phoneNumberField.addTextChangedListener(new TextWatcher() {
            private int characterAction = -1;
            private int actionPosition;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (count == 0 && after == 1) {
                    characterAction = 1;
                } else if (count == 1 && after == 0) {
                    if (s.charAt(start) == ' ' && start > 0) {
                        characterAction = 3;
                        actionPosition = start - 1;
                    } else {
                        characterAction = 2;
                    }
                } else {
                    characterAction = -1;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (ignoreOnPhoneChange) {
                    return;
                }
                int start = phoneNumberField.getSelectionStart();
                String phoneChars = "0123456789";
                String str = phoneNumberField.getText().toString();
                if (characterAction == 3) {
                    str = str.substring(0, actionPosition) + str.substring(actionPosition + 1, str.length());
                    start--;
                }
                StringBuilder builder = new StringBuilder(str.length());
                for (int a = 0; a < str.length(); a++) {
                    String ch = str.substring(a, a + 1);
                    if (phoneChars.contains(ch)) {
                        builder.append(ch);
                    }
                }
                ignoreOnPhoneChange = true;
                String hint = phoneNumberField.getHintText();
                if (hint != null) {
                    for (int a = 0; a < builder.length(); a++) {
                        if (a < hint.length()) {
                            if (hint.charAt(a) == ' ') {
                                builder.insert(a, ' ');
                                a++;
                                if (start == a && characterAction != 2 && characterAction != 3) {
                                    start++;
                                }
                            }
                        } else {
                            builder.insert(a, ' ');
                            if (start == a + 1 && characterAction != 2 && characterAction != 3) {
                                start++;
                            }
                            break;
                        }
                    }
                }
                phoneNumberField.setText(builder);
                if (start >= 0) {
                    phoneNumberField.setSelection(start <= phoneNumberField.length() ? start : phoneNumberField.length());
                }
                phoneNumberField.onTextChange();
                ignoreOnPhoneChange = false;
            }
        });
        phoneNumberField.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_NEXT) {
                DisplayUtility.hideKeyboard(phoneNumberField);
                return true;
            }
            return false;
        });
        contentView.addView(phoneNumberField, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 16, 0, 16, 0));


        TextView doneButton = new TextView(context);
        doneButton.setText(R.string.confirm);
        doneButton.setTextColor(Color.WHITE);
        doneButton.setBackground(Theme.getRoundRectSelectorDrawable(Color.WHITE, Color.parseColor("#FD0C6B"), DisplayUtility.dp(60)));
        doneButton.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans-Medium.ttf"));
        doneButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        doneButton.setGravity(Gravity.CENTER);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNextPress();
            }
        });
        contentView.addView(doneButton, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, 40, Gravity.BOTTOM | Gravity.LEFT, 20, 0, 20, 52));

        termsContainer = new LinearLayout(context);
        termsContainer.setOrientation(LinearLayout.HORIZONTAL);
        termsContainer.setGravity(Gravity.CENTER);
        termsContainer.setPadding(0, 0, 0, DisplayUtility.dp(12));
        contentView.addView(termsContainer, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL));

        TextView acceptTermsTextView = new TextView(context);
        acceptTermsTextView.setText("با شرایط سرویس موافقم");
        acceptTermsTextView.setTextColor(0xFFFD0C6B);
        acceptTermsTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        acceptTermsTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans.ttf"));
        termsContainer.addView(acceptTermsTextView);

        termsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertUtility.createTermsAlert(EnterPhoneViewController.this);
            }
        });

        fragmentView = contentView;
        return fragmentView;
    }

    private void onNextPress() {
        if (requestInProgress) {
            return;
        }

        String phoneNumber = phoneNumberField.getText().toString();

        if (phoneNumber.contains(" ")) {
            phoneNumber = phoneNumber.replaceAll(" ", "");
        }

        if (!phoneNumber.startsWith("2374")) {
            if (phoneNumber.startsWith("0")) {
                phoneNumber = phoneNumber.substring(1);
            }
            if (!phoneNumber.startsWith("+98") && !phoneNumber.startsWith("98") && !phoneNumber.startsWith("+")) {
                phoneNumber = "98" + phoneNumber;
            }
        }

        if (phoneNumber.isEmpty()) {
            DisplayUtility.shakeView(phoneNumberField, 2, 0);
            Toast.makeText(getParentActivity(), "شماره همراه خود را وارد کنید!", Toast.LENGTH_LONG).show();
            return;
        }

        requestInProgress = true;

        final AlertDialog progressDialog = new AlertDialog(getParentActivity(), 3);
        progressDialog.setOnCancelListener(dialog -> {
            setVisibleDialog(null);
        });
        setVisibleDialog(progressDialog);
        showDialog(progressDialog);

        String finalPhoneNumber = phoneNumber;
        disposables.add(ApplicationLoader.musicChiApi.sendCode(SendCodeRequest.create(phoneNumber)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseEnvelope<SendCodeResponse>>() {
            @Override
            public void accept(ResponseEnvelope<SendCodeResponse> sendCodeRequestResponseEnvelope) throws Exception {
                requestInProgress = false;
                progressDialog.dismiss();
                visibleDialog = null;

                presentFragment(new EnterCodeViewController(sendCodeRequestResponseEnvelope.getPayload(), finalPhoneNumber));
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
                    } else if (error.code() == 406) {
                        DisplayUtility.shakeView(phoneNumberField, 2, 0);
                        Toast.makeText(getParentActivity(), "فقط مشترکین همراه اول میتوانند ثبت نام کنند", Toast.LENGTH_LONG).show();
                    }
                }
                requestInProgress = false;
                progressDialog.dismiss();
                visibleDialog = null;
            }
        }));

    }

}
