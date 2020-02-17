package com.ronaksoftware.musicchi.ui.fragments.login;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ronaksoftware.musicchi.ApplicationLoader;
import com.ronaksoftware.musicchi.R;
import com.ronaksoftware.musicchi.UserConfigs;
import com.ronaksoftware.musicchi.controllers.EventController;
import com.ronaksoftware.musicchi.network.ResponseEnvelope;
import com.ronaksoftware.musicchi.network.request.RegisterRequest;
import com.ronaksoftware.musicchi.network.response.AuthorizationResponse;
import com.ronaksoftware.musicchi.network.response.SendCodeResponse;
import com.ronaksoftware.musicchi.ui.presenter.ActionBar;
import com.ronaksoftware.musicchi.ui.presenter.ActionBarMenu;
import com.ronaksoftware.musicchi.ui.presenter.AlertDialog;
import com.ronaksoftware.musicchi.ui.presenter.BackDrawable;
import com.ronaksoftware.musicchi.ui.presenter.BaseViewController;
import com.ronaksoftware.musicchi.ui.presenter.Theme;
import com.ronaksoftware.musicchi.utils.DisplayUtility;
import com.ronaksoftware.musicchi.utils.LayoutHelper;
import com.ronaksoftware.musicchi.utils.Queues;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RegisterViewController extends BaseViewController {
    private static final int doneButtonID = 0;

    private EditText usernameField;

    private CompositeDisposable disposables = new CompositeDisposable();
    private SendCodeResponse sendCodeResponse;
    private String phoneNumber;
    private String phoneCode;

    private boolean requestInProgress = false;


    public RegisterViewController(SendCodeResponse sendCodeResponse, String phoneNumber, String phoneCode) {
        this.sendCodeResponse = sendCodeResponse;
        this.phoneNumber = phoneNumber;
        this.phoneCode = phoneCode;
    }

    @Override
    public boolean onFragmentCreate() {
        if (sendCodeResponse == null || phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public void onFragmentDestroy() {
        disposables.dispose();
        super.onFragmentDestroy();
    }

    @Override
    public View createView(Context context) {
        actionBar.setTitle("Enter Phone Number");
        actionBar.setBackButtonDrawable(new BackDrawable(false));

        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == -1) {
                    finishFragment(true);
                } else if (id == doneButtonID) {
                    onNextPress();
                }
            }
        });

        ActionBarMenu menu = actionBar.createMenu();
        menu.addItemWithWidth(doneButtonID, R.drawable.ic_done, DisplayUtility.dp(56));

        FrameLayout contentView = new FrameLayout(context);

        usernameField = new EditText(context);
        usernameField.setHint("Username (Optional)");
        usernameField.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
        usernameField.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        contentView.addView(usernameField, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 16, 0, 16, 0));

        Button submitButton = new Button(context);
        submitButton.setText("Register");

        fragmentView = contentView;
        return fragmentView;
    }

    private void onNextPress() {
        if (requestInProgress) {
            return;
        }

        requestInProgress = true;

        String username = usernameField.getText().toString();

        final AlertDialog progressDialog = new AlertDialog(getParentActivity(), 3);
        progressDialog.setOnCancelListener(dialog -> {
            setVisibleDialog(null);
        });
        setVisibleDialog(progressDialog);
        showDialog(progressDialog);

        disposables.add(ApplicationLoader.musicChiApi.register(RegisterRequest.create(phoneCode, sendCodeResponse.getPhoneCodeHash(), phoneNumber, username)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseEnvelope<AuthorizationResponse>>() {
            @Override
            public void accept(ResponseEnvelope<AuthorizationResponse> authorizationResponseResponseEnvelope) throws Exception {

                Queues.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        setVisibleDialog(null);
                        requestInProgress = false;
                        DisplayUtility.hideKeyboard(usernameField);

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
    }
}
