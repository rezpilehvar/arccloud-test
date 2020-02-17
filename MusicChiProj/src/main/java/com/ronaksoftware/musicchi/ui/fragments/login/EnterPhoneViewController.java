package com.ronaksoftware.musicchi.ui.fragments.login;

import android.content.Context;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

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

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;

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
    public View createView(Context context) {

        actionBar.setTitle("Enter Phone Number");
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

                if (sendCodeRequestResponseEnvelope.getPayload().isRegistered()) {
                    presentFragment(new EnterCodeViewController(sendCodeRequestResponseEnvelope.getPayload(), phoneNumber));
                }
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
