package com.ronaksoftware.musicchi.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;

import com.ronaksoftware.musicchi.R;
import com.ronaksoftware.musicchi.UserConfigs;
import com.ronaksoftware.musicchi.ui.fragments.HomeViewController;
import com.ronaksoftware.musicchi.ui.fragments.LoginViewController;
import com.ronaksoftware.musicchi.ui.presenter.BaseViewController;
import com.ronaksoftware.musicchi.ui.presenter.DrawerLayoutContainer;
import com.ronaksoftware.musicchi.ui.presenter.PresenterLayout;
import com.ronaksoftware.musicchi.ui.presenter.Theme;
import com.ronaksoftware.musicchi.utils.DisplayUtility;

import java.util.ArrayList;

public class LaunchActivity extends Activity implements PresenterLayout.Delegate {

    private DrawerLayoutContainer drawerLayoutContainer;
    private PresenterLayout presenterLayout;
    private static ArrayList<BaseViewController> mainFragmentsStack = new ArrayList<>();

    private ActionMode visibleActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DisplayUtility.checkDisplaySize(this, getResources().getConfiguration());

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            DisplayUtility.statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTheme(R.style.Theme_MusicChi);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                setTaskDescription(new ActivityManager.TaskDescription(null, null, Theme.getColor(Theme.key_actionBarDefault) | 0xff000000));
            } catch (Exception ignore) {

            }
            try {
                getWindow().setNavigationBarColor(0xff000000);
            } catch (Exception ignore) {

            }
        }

        getWindow().setBackgroundDrawableResource(R.drawable.transparent);
        super.onCreate(savedInstanceState);

        drawerLayoutContainer = new DrawerLayoutContainer(this);
        drawerLayoutContainer.setBehindKeyboardColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        setContentView(drawerLayoutContainer);

        presenterLayout = new PresenterLayout(this);
        drawerLayoutContainer.addView(presenterLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        presenterLayout.setDrawerLayoutContainer(drawerLayoutContainer);
        presenterLayout.init(mainFragmentsStack);
        presenterLayout.setDelegate(this);

        if (UserConfigs.isAuthenticated) {
            presenterLayout.addFragmentToStack(new HomeViewController());
        } else {
            presenterLayout.addFragmentToStack(new LoginViewController());
        }

        presenterLayout.showLastFragment();

        checkSystemBarColors();

    }


    private void checkSystemBarColors() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int color = Theme.getColor(Theme.key_actionBarDefault, null, true);
            DisplayUtility.setLightStatusBar(getWindow(), color == Color.WHITE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                final Window window = getWindow();
                color = Theme.getColor(Theme.key_windowBackgroundGray, null, true);
                if (window.getNavigationBarColor() != color) {
                    window.setNavigationBarColor(color);
                    final float brightness = DisplayUtility.computePerceivedBrightness(color);
                    DisplayUtility.setLightNavigationBar(getWindow(), brightness >= 0.721f);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (presenterLayout.fragmentsStack.size() != 0) {
            BaseViewController fragment = presenterLayout.fragmentsStack.get(presenterLayout.fragmentsStack.size() - 1);
            fragment.onActivityResultFragment(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (presenterLayout.fragmentsStack.size() != 0) {
            BaseViewController fragment = presenterLayout.fragmentsStack.get(presenterLayout.fragmentsStack.size() - 1);
            fragment.onRequestPermissionsResultFragment(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenterLayout.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenterLayout.onResume();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        DisplayUtility.checkDisplaySize(this, newConfig);
        super.onConfigurationChanged(newConfig);
    }

    public void hideVisibleActionMode() {
        if (visibleActionMode == null) {
            return;
        }
        visibleActionMode.finish();
    }


    @Override
    public void onBackPressed() {
        if (drawerLayoutContainer.isDrawerOpened()) {
            drawerLayoutContainer.closeDrawer(false);
        } else {
            presenterLayout.onBackPressed();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (presenterLayout != null) {
            presenterLayout.onLowMemory();
        }
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        super.onActionModeStarted(mode);
        visibleActionMode = mode;
        try {
            Menu menu = mode.getMenu();
            if (menu != null) {
                presenterLayout.extendActionMode(menu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 23 && mode.getType() == ActionMode.TYPE_FLOATING) {
            return;
        }
        presenterLayout.onActionModeStarted(mode);
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);
        if (visibleActionMode == mode) {
            visibleActionMode = null;
        }
        if (Build.VERSION.SDK_INT >= 23 && mode.getType() == ActionMode.TYPE_FLOATING) {
            return;
        }
        presenterLayout.onActionModeFinished(mode);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (presenterLayout.fragmentsStack.size() == 1) {
            if (!drawerLayoutContainer.isDrawerOpened()) {
                if (getCurrentFocus() != null) {
                    DisplayUtility.hideKeyboard(getCurrentFocus());
                }
                drawerLayoutContainer.openDrawer(false);
            } else {
                drawerLayoutContainer.closeDrawer(false);
            }
        } else {
            presenterLayout.onKeyUp(keyCode, event);
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onPreIme() {
        return false;
    }

    @Override
    public boolean needPresentFragment(BaseViewController fragment, boolean removeLast, boolean forceWithoutAnimation, PresenterLayout layout) {
        return true;
    }

    @Override
    public boolean needAddFragmentToStack(BaseViewController fragment, PresenterLayout layout) {
        return true;
    }

    @Override
    public boolean needCloseLastFragment(PresenterLayout layout) {
        if (layout.fragmentsStack.size() <= 1) {
//            onFinish();
            finish();
            return false;
        }
//        if (layout.fragmentsStack.size() >= 2 && !(layout.fragmentsStack.get(0) instanceof LoginActivity)) {
//            drawerLayoutContainer.setAllowOpenDrawer(true, false);
//        }
        return true;
    }

    @Override
    public void onRebuildAllFragments(PresenterLayout layout, boolean last) {

    }
}
