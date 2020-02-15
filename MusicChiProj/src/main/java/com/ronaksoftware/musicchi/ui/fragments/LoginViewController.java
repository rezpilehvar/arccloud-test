package com.ronaksoftware.musicchi.ui.fragments;

import android.content.Context;
import android.view.View;

public class LoginViewController extends HomeViewController {

    @Override
    public View createView(Context context) {
        fragmentView = new View(context);

        return fragmentView;
    }
}
