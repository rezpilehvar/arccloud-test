package com.ronaksoftware.musicchi.ui.fragments.login;

import android.content.Context;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.ronaksoftware.musicchi.ApplicationLoader;
import com.ronaksoftware.musicchi.R;
import com.ronaksoftware.musicchi.network.ResponseEnvelope;
import com.ronaksoftware.musicchi.network.request.LoginRequest;
import com.ronaksoftware.musicchi.network.request.SendCodeRequest;
import com.ronaksoftware.musicchi.network.response.AuthorizationResponse;
import com.ronaksoftware.musicchi.network.response.SendCodeResponse;
import com.ronaksoftware.musicchi.ui.presenter.ActionBar;
import com.ronaksoftware.musicchi.ui.presenter.ActionBarMenu;
import com.ronaksoftware.musicchi.ui.presenter.AlertDialog;
import com.ronaksoftware.musicchi.ui.presenter.BaseViewController;
import com.ronaksoftware.musicchi.ui.presenter.Theme;
import com.ronaksoftware.musicchi.utils.DisplayUtility;
import com.ronaksoftware.musicchi.utils.LayoutHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class EnterCodeViewController extends BaseViewController {
    private static final int doneButtonID = 0;

    private EditText codeField;
    private boolean requestInProgress = false;
    private CompositeDisposable disposables = new CompositeDisposable();

    private SendCodeResponse sendCodeResponse;
    private String phoneNumber;

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
    public View createView(Context context) {

        actionBar.setTitle("Enter Code");
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == doneButtonID) {
                    onNextPress();
                }
            }
        });
        ActionBarMenu menu = actionBar.createMenu();
        menu.addItemWithWidth(doneButtonID, R.drawable.ic_done, DisplayUtility.dp(56));

        FrameLayout contentView = new FrameLayout(context);

        codeField = new EditText(context);
        codeField.setHint("Code");
        codeField.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
        codeField.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        codeField.setInputType(InputType.TYPE_CLASS_PHONE);
        contentView.addView(codeField, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 16, 0, 16, 0));

        fragmentView = contentView;
        return fragmentView;
    }

    private void onNextPress() {
        if (requestInProgress) {
            return;
        }

        String code = codeField.getText().toString();

        if (code.isEmpty()) {
            DisplayUtility.shakeView(codeField, 2, 0);
            return;
        }

        requestInProgress = true;

        final AlertDialog progressDialog = new AlertDialog(getParentActivity(), 3);
        progressDialog.setOnCancelListener(dialog -> {
            setVisibleDialog(null);
        });
        setVisibleDialog(progressDialog);
        showDialog(progressDialog);

        disposables.add(ApplicationLoader.musicChiApi.login(LoginRequest.create(code, sendCodeResponse.getPhoneCodeHash(), phoneNumber)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseEnvelope<AuthorizationResponse>>() {
            @Override
            public void accept(ResponseEnvelope<AuthorizationResponse> sendCodeRequestResponseEnvelope) throws Exception {
                requestInProgress = false;
                progressDialog.dismiss();
                visibleDialog = null;


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
                        DisplayUtility.shakeView(codeField, 2, 0);
                    }
                }
                requestInProgress = false;
                progressDialog.dismiss();
                visibleDialog = null;
            }
        }));

    }

}
