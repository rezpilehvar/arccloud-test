package com.ronaksoftware.musicchi.ui.fragments;

import android.content.Context;
import android.view.View;

import com.ronaksoftware.musicchi.controllers.VoiceController;
import com.ronaksoftware.musicchi.ui.presenter.BaseViewController;

public class HomeViewController extends BaseViewController {
    @Override
    public View createView(Context context) {
        fragmentView = new View(context);
        VoiceController.getInstance();

//        fragmentView.setBackgroundColor(Theme.getColor(Theme.key_avatar_backgroundBlue));
        return fragmentView;
    }
}
