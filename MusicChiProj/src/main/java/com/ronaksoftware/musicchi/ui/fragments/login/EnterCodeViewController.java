package com.ronaksoftware.musicchi.ui.fragments.login;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ronaksoftware.musicchi.ApplicationLoader;
import com.ronaksoftware.musicchi.R;
import com.ronaksoftware.musicchi.UserConfigs;
import com.ronaksoftware.musicchi.controllers.EventController;
import com.ronaksoftware.musicchi.network.ErrorResponse;
import com.ronaksoftware.musicchi.network.ResponseEnvelope;
import com.ronaksoftware.musicchi.network.request.LoginRequest;
import com.ronaksoftware.musicchi.network.request.RegisterRequest;
import com.ronaksoftware.musicchi.network.request.SendCodeRequest;
import com.ronaksoftware.musicchi.network.response.AuthorizationResponse;
import com.ronaksoftware.musicchi.network.response.SendCodeResponse;
import com.ronaksoftware.musicchi.ui.components.HintEditText;
import com.ronaksoftware.musicchi.ui.presenter.ActionBar;
import com.ronaksoftware.musicchi.ui.presenter.ActionBarMenu;
import com.ronaksoftware.musicchi.ui.presenter.AlertDialog;
import com.ronaksoftware.musicchi.ui.presenter.BackDrawable;
import com.ronaksoftware.musicchi.ui.presenter.BaseViewController;
import com.ronaksoftware.musicchi.ui.presenter.Theme;
import com.ronaksoftware.musicchi.utils.DisplayUtility;
import com.ronaksoftware.musicchi.utils.LayoutHelper;
import com.ronaksoftware.musicchi.utils.Queues;
import com.ronaksoftware.musicchi.utils.TypeUtility;
import com.ronaksoftware.musicchi.utils.TypefaceUtility;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class EnterCodeViewController extends BaseViewController {

    private HintEditText codeField;
    private boolean requestInProgress = false;
    private CompositeDisposable disposables = new CompositeDisposable();

    private SendCodeResponse sendCodeResponse;
    private String phoneNumber;

    private boolean ignoreOnPhoneChange;

    public EnterCodeViewController(SendCodeResponse sendCodeResponse, String phoneNumber) {
        this.sendCodeResponse = sendCodeResponse;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean onFragmentCreate() {
        if (sendCodeResponse == null || phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        return super.onFragmentCreate();
    }

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
        topTextView.setText(R.string.auth_enter_code_topText);
        topTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        topTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        topTextView.setTextColor(Color.WHITE);
        topTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans.ttf"));

        ImageView logoImageView = new ImageView(context);
        logoImageView.setImageResource(R.drawable.logo);
        logoImageView.setScaleType(ImageView.ScaleType.CENTER);
        contentView.addView(logoImageView,LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT,LayoutHelper.WRAP_CONTENT,Gravity.CENTER_HORIZONTAL , 0,50 + 56 + (Build.VERSION.SDK_INT >= 21 ? 24 : 0) , 0,0));


        codeField = new HintEditText(context);
        codeField.setInputType(InputType.TYPE_CLASS_PHONE);
        codeField.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteInputField));
        codeField.setHintColor(Theme.getColor(Theme.key_windowBackgroundWhiteInputField));
        codeField.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
        codeField.setPadding(0, 0, 0, DisplayUtility.dp(8));
        codeField.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteInputField));
        codeField.setCursorSize(DisplayUtility.dp(20));
        codeField.setCursorWidth(1.5f);
        codeField.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        codeField.setMaxLines(1);
        codeField.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        codeField.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        codeField.setHintText("- - - -");

        codeField.addTextChangedListener(new TextWatcher() {
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
                int start = codeField.getSelectionStart();
                String phoneChars = "0123456789";
                String str = codeField.getText().toString();
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
                String hint = codeField.getHintText();
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
                codeField.setText(builder);
                if (start >= 0) {
                    codeField.setSelection(start <= codeField.length() ? start : codeField.length());
                }
                codeField.onTextChange();
                ignoreOnPhoneChange = false;
            }
        });
        codeField.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_NEXT) {
                onNextPress();
                return true;
            }
            return false;
        });
        contentView.addView(codeField, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 16, 0, 16, 0));

        TextView doneButton = new TextView(context);
        doneButton.setText(R.string.confirm);
        doneButton.setTextColor(Color.WHITE);
        doneButton.setBackground(Theme.getRoundRectSelectorDrawable(Color.WHITE,Color.parseColor("#FD0C6B"),DisplayUtility.dp(60)));
        doneButton.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans-Medium.ttf"));
        doneButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
        doneButton.setGravity(Gravity.CENTER);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNextPress();
            }
        });
        contentView.addView(doneButton,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,40,Gravity.BOTTOM | Gravity.LEFT , 20,0,20,52));

        fragmentView = contentView;
        return fragmentView;
    }

    private void onNextPress() {
        if (requestInProgress) {
            return;
        }

        String code = codeField.getText().toString();

        code = code.replaceAll(" ","");

        if (code.isEmpty()) {
            DisplayUtility.shakeView(codeField, 2, 0);
            return;
        }

        requestInProgress = true;

        final AlertDialog progressDialog = new AlertDialog(getParentActivity(), 3);
        progressDialog.setOnCancelListener(dialog -> {
            setVisibleDialog(null);
        });
        showDialog(progressDialog);

        String finalCode = code;
        disposables.add(ApplicationLoader.musicChiApi.login(LoginRequest.create(code, sendCodeResponse.getPhoneCodeHash(), phoneNumber)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseEnvelope<AuthorizationResponse>>() {
            @Override
            public void accept(ResponseEnvelope<AuthorizationResponse> sendCodeRequestResponseEnvelope) throws Exception {
                Queues.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        requestInProgress = false;
                        progressDialog.dismiss();
                        visibleDialog = null;
                        DisplayUtility.hideKeyboard(codeField);

                        UserConfigs.sessionID = sendCodeRequestResponseEnvelope.getPayload().getSerssionID();
                        UserConfigs.username = sendCodeRequestResponseEnvelope.getPayload().getUsername();
                        UserConfigs.phoneNumber = sendCodeRequestResponseEnvelope.getPayload().getPhone();
                        UserConfigs.userID = sendCodeRequestResponseEnvelope.getPayload().getUserID();
                        UserConfigs.save();
                        EventController.authChanged.onNext(new Object());
                    }
                }, 2000);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (throwable instanceof IllegalStateException) {
                    throwable.printStackTrace();
                }

                requestInProgress = false;
                progressDialog.dismiss();
                visibleDialog = null;

                if (throwable instanceof HttpException) {
                    HttpException error = (HttpException) throwable;

                    if (error.code() == 400) {
                        if (error.getMessage() != null && error.response() != null && error.response().errorBody() != null && error.response().errorBody() != null) {
                            ErrorResponse errorResponse = TypeUtility.parseErrorResponse(error.response());

                            if (errorResponse.getPayload().equals("PHONE_NOT_VALID")) {
                                disposables.add(ApplicationLoader.musicChiApi.register(RegisterRequest.create(finalCode, sendCodeResponse.getPhoneCodeHash(), phoneNumber, "")).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseEnvelope<AuthorizationResponse>>() {
                                    @Override
                                    public void accept(ResponseEnvelope<AuthorizationResponse> authorizationResponseResponseEnvelope) throws Exception {

                                        Queues.runOnUIThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.dismiss();
                                                setVisibleDialog(null);
                                                requestInProgress = false;
                                                DisplayUtility.hideKeyboard(codeField);

                                                UserConfigs.sessionID = authorizationResponseResponseEnvelope.getPayload().getSerssionID();
                                                UserConfigs.username = authorizationResponseResponseEnvelope.getPayload().getUsername();
                                                UserConfigs.phoneNumber = authorizationResponseResponseEnvelope.getPayload().getPhone();
                                                UserConfigs.userID = authorizationResponseResponseEnvelope.getPayload().getUserID();
                                                UserConfigs.save();
                                                EventController.authChanged.onNext(new Object());
                                            }
                                        }, 2000);
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {

                                        progressDialog.dismiss();
                                        setVisibleDialog(null);
                                        requestInProgress = false;
                                    }
                                }));

//                                presentFragment(new RegisterViewController(sendCodeResponse, phoneNumber, code), true);
                            } else {
                                DisplayUtility.shakeView(codeField, 2, 0);
                                Toast.makeText(getParentActivity(),"کد وارد شده صحیح نمیباشد",Toast.LENGTH_LONG).show();
                            }

                            return;
                        }

                        DisplayUtility.shakeView(codeField, 2, 0);
                    }
                }
            }
        }));

    }

}
