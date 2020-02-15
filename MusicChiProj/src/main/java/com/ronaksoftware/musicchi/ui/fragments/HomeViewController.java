package com.ronaksoftware.musicchi.ui.fragments;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.ronaksoftware.musicchi.ui.presenter.BaseViewController;
import com.ronaksoftware.musicchi.ui.presenter.Theme;

public class HomeViewController extends BaseViewController {
    @Override
    public View createView(Context context) {
        fragmentView = new View(context);
//        fragmentView.setBackgroundColor(Theme.getColor(Theme.key_avatar_backgroundBlue));
        return fragmentView;
    }
}
