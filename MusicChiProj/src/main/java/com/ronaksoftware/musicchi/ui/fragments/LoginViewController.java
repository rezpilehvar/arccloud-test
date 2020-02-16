package com.ronaksoftware.musicchi.ui.fragments;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ronaksoftware.musicchi.utils.LayoutHelper;

public class LoginViewController extends HomeViewController {

    @Override
    public View createView(Context context) {
        FrameLayout contentView = new FrameLayout(context);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        contentView.addView(linearLayout, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));

        EditText usernameEditText = new EditText(context);
        usernameEditText.setHint("Username");
        linearLayout.addView(usernameEditText, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        EditText passwordEditText = new EditText(context);
        passwordEditText.setHint("Password");
        linearLayout.addView(passwordEditText, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.LEFT, 0, 12, 0, 0));

        Button submitButton = new Button(context);
        submitButton.setText("Login");

        return fragmentView;
    }
}
