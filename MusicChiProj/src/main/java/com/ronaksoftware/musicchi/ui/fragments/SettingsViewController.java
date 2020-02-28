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
import android.widget.Toast;

import com.ronaksoftware.musicchi.ApplicationLoader;
import com.ronaksoftware.musicchi.R;
import com.ronaksoftware.musicchi.network.ResponseEnvelope;
import com.ronaksoftware.musicchi.network.request.LogoutRequest;
import com.ronaksoftware.musicchi.network.response.BooleanResponse;
import com.ronaksoftware.musicchi.ui.cells.SettingsCell;
import com.ronaksoftware.musicchi.ui.presenter.ActionBar;
import com.ronaksoftware.musicchi.ui.presenter.AlertDialog;
import com.ronaksoftware.musicchi.ui.presenter.BackDrawable;
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

public class SettingsViewController extends BaseViewController {
    private FrameLayout contentView;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void onFragmentDestroy() {
        disposables.dispose();
    }

    @Override
    public View createView(Context context) {
        fragmentView = contentView = new FrameLayout(context);
        contentView.setBackground(new ColorDrawable(Color.parseColor("#1A1E2A")));

        FrameLayout actionBarView = new FrameLayout(context);
        contentView.addView(actionBarView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, 56, Gravity.TOP | Gravity.LEFT, 0, Build.VERSION.SDK_INT >= 21 ? 24 : 0, 0, 0));
        actionBarView.setBackground(Theme.createRoundRectDrawable(new float[]{0, 0, DisplayUtility.dp(18), 0}, Color.parseColor("#FD0C6B")));

        TextView titleTextView = new TextView(context);
        titleTextView.setText("تنظیمات");
        titleTextView.setTextColor(Color.WHITE);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        titleTextView.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans-Medium.ttf"));
        actionBarView.addView(titleTextView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.TOP | Gravity.RIGHT, 0, 16, 20, 0));

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

        LinearLayout itemsContainer = new LinearLayout(context);
        itemsContainer.setOrientation(LinearLayout.VERTICAL);
        contentView.addView(itemsContainer, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.TOP | Gravity.LEFT, 0, 56 + (Build.VERSION.SDK_INT >= 21 ? DisplayUtility.statusBarHeight : 0), 0, 0));


        for (int i = 0; i < 2; i++) {
            SettingsCell settingsCell = new SettingsCell(context);
            switch (i) {
                case 0:
                    settingsCell.update(R.drawable.compare_arrows_24px, "ارسال بازخورد");
                    break;
                case 1:
                    settingsCell.update(R.drawable.arrow_right_alt_24px, "خروج از حساب کاربری");
                    break;
//                case 2:
//                    settingsCell.update(R.drawable.arrow_right_alt_24px,"خروج از حساب کاربری و لغو اشتراک");
//                    break;
            }
            settingsCell.setTag(i);
            settingsCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();

                    if (position == 0) {
                        AlertUtility.createFeedBackAlert(SettingsViewController.this, new AlertUtility.FeedBackAlertDelegate() {
                            @Override
                            public void onSendPressed(String text) {

                            }
                        });
                    }else if (position == 1) {

                        AlertUtility.createLogoutAlert(SettingsViewController.this, new Runnable() {
                            @Override
                            public void run() {
                                AlertUtility.createLogoutAlert2(SettingsViewController.this, new Runnable() {
                                    @Override
                                    public void run() {
                                        showLoading();
                                        disposables.add(ApplicationLoader.musicChiApi.logout(LogoutRequest.create(false)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseEnvelope<BooleanResponse>>() {
                                            @Override
                                            public void accept(ResponseEnvelope<BooleanResponse> booleanResponseResponseEnvelope) throws Exception {
                                                hideLoading();
                                                ApplicationLoader.logout(true);
                                            }
                                        }, new Consumer<Throwable>() {
                                            @Override
                                            public void accept(Throwable throwable) throws Exception {
                                                hideLoading();
                                                Toast.makeText(getParentActivity(), "ناموفق", Toast.LENGTH_LONG).show();
                                            }
                                        }));
                                    }
                                });
                            }
                        });
                    }
//
//                    if (position == 1 || position == 2) {
//                        String message;
//                        if (position == 1) {
//                            message = "آیا مایل به خروج از حساب کاربری خود هستید؟";
//                        }else {
//                            message = "آیا مایل به خروج از حساب کاربری و لغو اشتراک هستید؟";
//                        }
//                        showDialog(new AlertDialog.Builder(getParentActivity())
//                                .setTitle("خروج")
//                                .setMessage(message)
//                                .setNegativeButton("لغو", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        dialogInterface.dismiss();
//                                    }
//                                })
//                                .setNeutralButton(position == 1 ? "خروج" : "خروج و لغو", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        showLoading();
//
//                                        disposables.add(ApplicationLoader.musicChiApi.logout(LogoutRequest.create(position == 2)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseEnvelope<BooleanResponse>>() {
//                                            @Override
//                                            public void accept(ResponseEnvelope<BooleanResponse> booleanResponseResponseEnvelope) throws Exception {
//                                                hideLoading();
//                                                UserConfigs.phoneNumber = null;
//                                                UserConfigs.sessionID = null;
//                                                UserConfigs.userID = null;
//                                                UserConfigs.username = null;
//                                                UserConfigs.save();
//                                                SearchHistoryController.getInstance().data.clear();
//                                                SearchHistoryController.getInstance().save();
//                                                EventController.authChanged.onNext(new Object());
//                                                SoundRecognizer.getInstance().stopRecognize();
//                                                MediaController.getInstance().cleanupPlayer(false);
//                                            }
//                                        }, new Consumer<Throwable>() {
//                                            @Override
//                                            public void accept(Throwable throwable) throws Exception {
//                                                hideLoading();
//                                                Toast.makeText(getParentActivity(),"ناموفق",Toast.LENGTH_LONG).show();
//                                            }
//                                        }));
//                                    }
//                                })
//                                .create());
//                    }
                }
            });
            itemsContainer.addView(settingsCell, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 48, 0, 8, 0, 8));
        }


        return fragmentView;
    }

    private AlertDialog progressDialog;

    private void showLoading() {
        progressDialog = new AlertDialog(getParentActivity(), 3);
        progressDialog.setOnCancelListener(dialog -> {
            setVisibleDialog(null);
        });
        showDialog(progressDialog);
    }

    private void hideLoading() {
        progressDialog.dismiss();
        setVisibleDialog(null);
    }

    @Override
    protected ActionBar createActionBar(Context context) {
        return null;
    }
}
