package com.ronaksoftware.musicchi.ui.presenter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ronaksoftware.musicchi.R;
import com.ronaksoftware.musicchi.utils.DisplayUtility;
import com.ronaksoftware.musicchi.utils.LayoutHelper;
import com.ronaksoftware.musicchi.controllers.LocaleController;
import com.ronaksoftware.musicchi.utils.TypefaceUtility;

import java.util.ArrayList;

public class ActionBar extends FrameLayout {

    public static class ActionBarMenuOnItemClick {
        public void onItemClick(int id) {

        }

        public boolean canOpenMenu() {
            return true;
        }
    }

    private ImageView backButtonImageView;
    private SimpleTextView titleTextView;
    private SimpleTextView subtitleTextView;
    private View actionModeTop;
    private ActionBarMenu menu;
    private ActionBarMenu actionMode;
    private boolean ignoreLayoutRequest;
    private boolean occupyStatusBar = Build.VERSION.SDK_INT >= 21;
    private boolean actionModeVisible;
    private boolean addToContainer = true;
    private boolean clipContent;
    private boolean interceptTouches = true;
    private int extraHeight;
    private AnimatorSet actionModeAnimation;
    private View actionModeExtraView;
    private View actionModeTranslationView;
    private View actionModeShowingView;
    private View[] actionModeHidingViews;

    private Paint.FontMetricsInt fontMetricsInt;
    private boolean manualStart;
    private Rect rect;

    private int titleRightMargin;

    private boolean allowOverlayTitle;
    private CharSequence lastTitle;
    private Runnable lastRunnable;
    private boolean titleOverlayShown;
    private Runnable titleActionRunnable;
    private boolean castShadows = true;

    protected boolean isSearchFieldVisible;
    protected int itemsBackgroundColor;
    protected int itemsActionModeBackgroundColor;
    protected int itemsColor;
    protected int itemsActionModeColor;
    private boolean isBackOverlayVisible;
    protected BaseViewController parentFragment;
    public ActionBarMenuOnItemClick actionBarMenuOnItemClick;

    public ActionBar(Context context) {
        super(context);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActionBar.this.isSearchFieldVisible()) {
                    return;
                }
                if (titleActionRunnable != null) {
                    titleActionRunnable.run();
                }
            }
        });
    }

    private void createBackButtonImage() {
        if (backButtonImageView != null) {
            return;
        }
        backButtonImageView = new ImageView(getContext());
        backButtonImageView.setScaleType(ImageView.ScaleType.CENTER);
        backButtonImageView.setBackgroundDrawable(Theme.createSelectorDrawable(itemsBackgroundColor));
        if (itemsColor != 0) {
            backButtonImageView.setColorFilter(new PorterDuffColorFilter(itemsColor, PorterDuff.Mode.MULTIPLY));
        }
        backButtonImageView.setPadding(DisplayUtility.dp(1), 0, 0, 0);
        addView(backButtonImageView, LayoutHelper.createFrame(54, 54, Gravity.LEFT | Gravity.TOP));

        backButtonImageView.setOnClickListener(v -> {
            if (!actionModeVisible && isSearchFieldVisible) {
                closeSearchField();
                return;
            }
            if (actionBarMenuOnItemClick != null) {
                actionBarMenuOnItemClick.onItemClick(-1);
            }
        });
        backButtonImageView.setContentDescription(LocaleController.getString("AccDescrGoBack", R.string.AccDescrGoBack));
    }

    public void setBackButtonDrawable(Drawable drawable) {
        if (backButtonImageView == null) {
            createBackButtonImage();
        }
        backButtonImageView.setVisibility(drawable == null ? GONE : VISIBLE);
        backButtonImageView.setImageDrawable(drawable);
        if (drawable instanceof BackDrawable) {
            BackDrawable backDrawable = (BackDrawable) drawable;
            backDrawable.setRotation(isActionModeShowed() ? 1 : 0, false);
            backDrawable.setRotatedColor(itemsActionModeColor);
            backDrawable.setColor(itemsColor);
        }
    }

    public void setBackButtonContentDescription(CharSequence description) {
        if (backButtonImageView != null) {
            backButtonImageView.setContentDescription(description);
        }
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean clip = clipContent && (child == titleTextView || child == subtitleTextView || child == actionMode || child == menu || child == backButtonImageView);
        if (clip) {
            canvas.save();
            canvas.clipRect(0, -getTranslationY() + (occupyStatusBar ? DisplayUtility.statusBarHeight : 0), getMeasuredWidth(), getMeasuredHeight());
        }
        boolean result = super.drawChild(canvas, child, drawingTime);
        if (clip) {
            canvas.restore();
        }
        return result;
    }

    @Override
    public void setTranslationY(float translationY) {
        super.setTranslationY(translationY);
        if (clipContent) {
            invalidate();
        }
    }

    public void setBackButtonImage(int resource) {
        if (backButtonImageView == null) {
            createBackButtonImage();
        }
        backButtonImageView.setVisibility(resource == 0 ? GONE : VISIBLE);
        backButtonImageView.setImageResource(resource);
    }

    private void createSubtitleTextView() {
        if (subtitleTextView != null) {
            return;
        }
        subtitleTextView = new SimpleTextView(getContext());
        subtitleTextView.setGravity(Gravity.LEFT);
        subtitleTextView.setVisibility(GONE);
        subtitleTextView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubtitle));
        addView(subtitleTextView, 0, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.LEFT | Gravity.TOP));
    }

    public void setAddToContainer(boolean value) {
        addToContainer = value;
    }

    public boolean getAddToContainer() {
        return addToContainer;
    }

    public void setClipContent(boolean value) {
        clipContent = value;
    }

    public void setSubtitle(CharSequence value) {
        if (value != null && subtitleTextView == null) {
            createSubtitleTextView();
        }
        if (subtitleTextView != null) {
            subtitleTextView.setVisibility(!TextUtils.isEmpty(value) && !isSearchFieldVisible ? VISIBLE : GONE);
            subtitleTextView.setText(value);
        }
    }

    private void createTitleTextView() {
        if (titleTextView != null) {
            return;
        }
        titleTextView = new SimpleTextView(getContext());
        titleTextView.setGravity(Gravity.LEFT);
        titleTextView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultTitle));
        titleTextView.setTypeface(TypefaceUtility.getTypeface("fonts/rmedium.ttf"));
        addView(titleTextView, 0, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.LEFT | Gravity.TOP));
    }

    public void setTitleRightMargin(int value) {
        titleRightMargin = value;
    }

    public void setTitle(CharSequence value) {
        if (value != null && titleTextView == null) {
            createTitleTextView();
        }
        if (titleTextView != null) {
            lastTitle = value;
            titleTextView.setVisibility(value != null && !isSearchFieldVisible ? VISIBLE : INVISIBLE);
            titleTextView.setText(value);
        }
    }

    public void setTitleColor(int color) {
        if (titleTextView == null) {
            createTitleTextView();
        }
        titleTextView.setTextColor(color);
    }

    public void setSubtitleColor(int color) {
        if (subtitleTextView == null) {
            createSubtitleTextView();
        }
        subtitleTextView.setTextColor(color);
    }

    public void setPopupItemsColor(int color, boolean icon, boolean forActionMode) {
        if (forActionMode && actionMode != null) {
            actionMode.setPopupItemsColor(color, icon);
        } else if (!forActionMode && menu != null) {
            menu.setPopupItemsColor(color, icon);
        }
    }

    public void setPopupItemsSelectorColor(int color, boolean forActionMode) {
        if (forActionMode && actionMode != null) {
            actionMode.setPopupItemsSelectorColor(color);
        } else if (!forActionMode && menu != null) {
            menu.setPopupItemsSelectorColor(color);
        }
    }

    public void setPopupBackgroundColor(int color, boolean forActionMode) {
        if (forActionMode && actionMode != null) {
            actionMode.redrawPopup(color);
        } else if (!forActionMode && menu != null) {
            menu.redrawPopup(color);
        }
    }

    public SimpleTextView getSubtitleTextView() {
        return subtitleTextView;
    }

    public SimpleTextView getTitleTextView() {
        return titleTextView;
    }

    public String getTitle() {
        if (titleTextView == null) {
            return null;
        }
        return titleTextView.getText().toString();
    }

    public String getSubtitle() {
        if (subtitleTextView == null) {
            return null;
        }
        return subtitleTextView.getText().toString();
    }

    public ActionBarMenu createMenu() {
        if (menu != null) {
            return menu;
        }
        menu = new ActionBarMenu(getContext(), this);
        addView(menu, 0, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.MATCH_PARENT, Gravity.RIGHT));
        return menu;
    }

    public void setActionBarMenuOnItemClick(ActionBarMenuOnItemClick listener) {
        actionBarMenuOnItemClick = listener;
    }

    public ActionBarMenuOnItemClick getActionBarMenuOnItemClick() {
        return actionBarMenuOnItemClick;
    }

    public View getBackButton() {
        return backButtonImageView;
    }

    public ActionBarMenu createActionMode() {
        return createActionMode(true);
    }

    public ActionBarMenu createActionMode(boolean needTop) {
        if (actionMode != null) {
            return actionMode;
        }
        actionMode = new ActionBarMenu(getContext(), this);
        actionMode.isActionMode = true;
        actionMode.setClickable(true);
        actionMode.setBackgroundColor(Theme.getColor(Theme.key_actionBarActionModeDefault));
        addView(actionMode, indexOfChild(backButtonImageView));
        actionMode.setPadding(0, occupyStatusBar ? DisplayUtility.statusBarHeight : 0, 0, 0);
        LayoutParams layoutParams = (LayoutParams) actionMode.getLayoutParams();
        layoutParams.height = LayoutHelper.MATCH_PARENT;
        layoutParams.width = LayoutHelper.MATCH_PARENT;
        layoutParams.bottomMargin = extraHeight;
        layoutParams.gravity = Gravity.RIGHT;
        actionMode.setLayoutParams(layoutParams);
        actionMode.setVisibility(INVISIBLE);

        if (occupyStatusBar && needTop && actionModeTop == null) {
            actionModeTop = new View(getContext());
            actionModeTop.setBackgroundColor(Theme.getColor(Theme.key_actionBarActionModeDefaultTop));
            addView(actionModeTop);
            layoutParams = (LayoutParams) actionModeTop.getLayoutParams();
            layoutParams.height = DisplayUtility.statusBarHeight;
            layoutParams.width = LayoutHelper.MATCH_PARENT;
            layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
            actionModeTop.setLayoutParams(layoutParams);
            actionModeTop.setVisibility(INVISIBLE);
        }

        return actionMode;
    }

    public void showActionMode() {
        showActionMode(null, null, null, null, null, 0);
    }

    public void showActionMode(View extraView, View showingView, View[] hidingViews, boolean[] hideView, View translationView, int translation) {
        if (actionMode == null || actionModeVisible) {
            return;
        }
        actionModeVisible = true;
        ArrayList<Animator> animators = new ArrayList<>();
        animators.add(ObjectAnimator.ofFloat(actionMode, View.ALPHA, 0.0f, 1.0f));
        if (hidingViews != null) {
            for (int a = 0; a < hidingViews.length; a++) {
                if (hidingViews[a] != null) {
                    animators.add(ObjectAnimator.ofFloat(hidingViews[a], View.ALPHA, 1.0f, 0.0f));
                }
            }
        }
        if (showingView != null) {
            animators.add(ObjectAnimator.ofFloat(showingView, View.ALPHA, 0.0f, 1.0f));
        }
        if (translationView != null) {
            animators.add(ObjectAnimator.ofFloat(translationView, View.TRANSLATION_Y, translation));
            actionModeTranslationView = translationView;
        }
        actionModeExtraView = extraView;
        actionModeShowingView = showingView;
        actionModeHidingViews = hidingViews;
        if (occupyStatusBar && actionModeTop != null) {
            animators.add(ObjectAnimator.ofFloat(actionModeTop, View.ALPHA, 0.0f, 1.0f));
        }
        if (actionModeAnimation != null) {
            actionModeAnimation.cancel();
        }
        actionModeAnimation = new AnimatorSet();
        actionModeAnimation.playTogether(animators);
        actionModeAnimation.setDuration(200);
        actionModeAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                actionMode.setVisibility(VISIBLE);
                if (occupyStatusBar && actionModeTop != null) {
                    actionModeTop.setVisibility(VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (actionModeAnimation != null && actionModeAnimation.equals(animation)) {
                    actionModeAnimation = null;
                    if (titleTextView != null) {
                        titleTextView.setVisibility(INVISIBLE);
                    }
                    if (subtitleTextView != null && !TextUtils.isEmpty(subtitleTextView.getText())) {
                        subtitleTextView.setVisibility(INVISIBLE);
                    }
                    if (menu != null) {
                        menu.setVisibility(INVISIBLE);
                    }
                    if (actionModeHidingViews != null) {
                        for (int a = 0; a < actionModeHidingViews.length; a++) {
                            if (actionModeHidingViews[a] != null) {
                                if (hideView == null || a >= hideView.length || hideView[a]) {
                                    actionModeHidingViews[a].setVisibility(INVISIBLE);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (actionModeAnimation != null && actionModeAnimation.equals(animation)) {
                    actionModeAnimation = null;
                }
            }
        });
        actionModeAnimation.start();
        if (backButtonImageView != null) {
            Drawable drawable = backButtonImageView.getDrawable();
            if (drawable instanceof BackDrawable) {
                ((BackDrawable) drawable).setRotation(1, true);
            }
            backButtonImageView.setBackgroundDrawable(Theme.createSelectorDrawable(itemsActionModeBackgroundColor));
        }
    }

    public void hideActionMode() {
        if (actionMode == null || !actionModeVisible) {
            return;
        }
        actionMode.hideAllPopupMenus();
        actionModeVisible = false;
        ArrayList<Animator> animators = new ArrayList<>();
        animators.add(ObjectAnimator.ofFloat(actionMode, View.ALPHA, 0.0f));
        if (actionModeHidingViews != null) {
            for (int a = 0; a < actionModeHidingViews.length; a++) {
                if (actionModeHidingViews != null) {
                    actionModeHidingViews[a].setVisibility(VISIBLE);
                    animators.add(ObjectAnimator.ofFloat(actionModeHidingViews[a], View.ALPHA, 1.0f));
                }
            }
        }
        if (actionModeTranslationView != null) {
            animators.add(ObjectAnimator.ofFloat(actionModeTranslationView, View.TRANSLATION_Y, 0.0f));
            actionModeTranslationView = null;
        }
        if (actionModeShowingView != null) {
            animators.add(ObjectAnimator.ofFloat(actionModeShowingView, View.ALPHA, 0.0f));
        }
        if (occupyStatusBar && actionModeTop != null) {
            animators.add(ObjectAnimator.ofFloat(actionModeTop, View.ALPHA, 0.0f));
        }
        if (actionModeAnimation != null) {
            actionModeAnimation.cancel();
        }
        actionModeAnimation = new AnimatorSet();
        actionModeAnimation.playTogether(animators);
        actionModeAnimation.setDuration(200);
        actionModeAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (actionModeAnimation != null && actionModeAnimation.equals(animation)) {
                    actionModeAnimation = null;
                    actionMode.setVisibility(INVISIBLE);
                    if (occupyStatusBar && actionModeTop != null) {
                        actionModeTop.setVisibility(INVISIBLE);
                    }
                    if (actionModeExtraView != null) {
                        actionModeExtraView.setVisibility(INVISIBLE);
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (actionModeAnimation != null && actionModeAnimation.equals(animation)) {
                    actionModeAnimation = null;
                }
            }
        });
        actionModeAnimation.start();
        if (!isSearchFieldVisible) {
            if (titleTextView != null) {
                titleTextView.setVisibility(VISIBLE);
            }
            if (subtitleTextView != null && !TextUtils.isEmpty(subtitleTextView.getText())) {
                subtitleTextView.setVisibility(VISIBLE);
            }
        }
        if (menu != null) {
            menu.setVisibility(VISIBLE);
        }
        if (backButtonImageView != null) {
            Drawable drawable = backButtonImageView.getDrawable();
            if (drawable instanceof BackDrawable) {
                ((BackDrawable) drawable).setRotation(0, true);
            }
            backButtonImageView.setBackgroundDrawable(Theme.createSelectorDrawable(itemsBackgroundColor));
        }
    }

    public void showActionModeTop() {
        if (occupyStatusBar && actionModeTop == null) {
            actionModeTop = new View(getContext());
            actionModeTop.setBackgroundColor(Theme.getColor(Theme.key_actionBarActionModeDefaultTop));
            addView(actionModeTop);
            LayoutParams layoutParams = (LayoutParams) actionModeTop.getLayoutParams();
            layoutParams.height = DisplayUtility.statusBarHeight;
            layoutParams.width = LayoutHelper.MATCH_PARENT;
            layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
            actionModeTop.setLayoutParams(layoutParams);
        }
    }

    public void setActionModeTopColor(int color) {
        if (actionModeTop != null) {
            actionModeTop.setBackgroundColor(color);
        }
    }

    public void setSearchTextColor(int color, boolean placeholder) {
        if (menu != null) {
            menu.setSearchTextColor(color, placeholder);
        }
    }

    public void setActionModeColor(int color) {
        if (actionMode != null) {
            actionMode.setBackgroundColor(color);
        }
    }

    public boolean isActionModeShowed() {
        return actionMode != null && actionModeVisible;
    }

    public void onSearchFieldVisibilityChanged(boolean visible) {
        isSearchFieldVisible = visible;
        if (titleTextView != null) {
            titleTextView.setVisibility(visible ? INVISIBLE : VISIBLE);
        }
        if (subtitleTextView != null && !TextUtils.isEmpty(subtitleTextView.getText())) {
            subtitleTextView.setVisibility(visible ? INVISIBLE : VISIBLE);
        }
        Drawable drawable = backButtonImageView.getDrawable();
        if (drawable instanceof MenuDrawable) {
            MenuDrawable menuDrawable = (MenuDrawable) drawable;
            menuDrawable.setRotateToBack(true);
            menuDrawable.setRotation(visible ? 1 : 0, true);
        }
    }

    public void setInterceptTouches(boolean value) {
        interceptTouches = value;
    }

    public void setExtraHeight(int value) {
        extraHeight = value;
        if (actionMode != null) {
            LayoutParams layoutParams = (LayoutParams) actionMode.getLayoutParams();
            layoutParams.bottomMargin = extraHeight;
            actionMode.setLayoutParams(layoutParams);
        }
    }

    public void closeSearchField() {
        closeSearchField(true);
    }

    public void closeSearchField(boolean closeKeyboard) {
        if (!isSearchFieldVisible || menu == null) {
            return;
        }
        menu.closeSearchField(closeKeyboard);
    }

    public void openSearchField(String text, boolean animated) {
        if (menu == null || text == null) {
            return;
        }
        menu.openSearchField(!isSearchFieldVisible, text, animated);
    }

    public void setSearchFieldText(String text) {
        menu.setSearchFieldText(text);
    }

    public void onSearchPressed() {
        menu.onSearchPressed();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (backButtonImageView != null) {
            backButtonImageView.setEnabled(enabled);
        }
        if (menu != null) {
            menu.setEnabled(enabled);
        }
        if (actionMode != null) {
            actionMode.setEnabled(enabled);
        }
    }

    @Override
    public void requestLayout() {
        if (ignoreLayoutRequest) {
            return;
        }
        super.requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int actionBarHeight = getCurrentActionBarHeight();
        int actionBarHeightSpec = MeasureSpec.makeMeasureSpec(actionBarHeight, MeasureSpec.EXACTLY);

        ignoreLayoutRequest = true;
        if (actionModeTop != null) {
            LayoutParams layoutParams = (LayoutParams) actionModeTop.getLayoutParams();
            layoutParams.height = DisplayUtility.statusBarHeight;
        }
        if (actionMode != null) {
            actionMode.setPadding(0, occupyStatusBar ? DisplayUtility.statusBarHeight : 0, 0, 0);
        }
        ignoreLayoutRequest = false;

        setMeasuredDimension(width, actionBarHeight + (occupyStatusBar ? DisplayUtility.statusBarHeight : 0) + extraHeight);

        int textLeft;
        if (backButtonImageView != null && backButtonImageView.getVisibility() != GONE) {
            backButtonImageView.measure(MeasureSpec.makeMeasureSpec(DisplayUtility.dp(54), MeasureSpec.EXACTLY), actionBarHeightSpec);
            textLeft = DisplayUtility.dp(DisplayUtility.isTablet() ? 80 : 72);
        } else {
            textLeft = DisplayUtility.dp(DisplayUtility.isTablet() ? 26 : 18);
        }

        if (menu != null && menu.getVisibility() != GONE) {
            int menuWidth;
            if (isSearchFieldVisible) {
                menuWidth = MeasureSpec.makeMeasureSpec(width - DisplayUtility.dp(DisplayUtility.isTablet() ? 74 : 66), MeasureSpec.EXACTLY);
            } else {
                menuWidth = MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST);
            }
            menu.measure(menuWidth, actionBarHeightSpec);
        }

        if (titleTextView != null && titleTextView.getVisibility() != GONE || subtitleTextView != null && subtitleTextView.getVisibility() != GONE) {
            int availableWidth = width - (menu != null ? menu.getMeasuredWidth() : 0) - DisplayUtility.dp(16) - textLeft - titleRightMargin;

            if (titleTextView != null && titleTextView.getVisibility() != GONE && subtitleTextView != null && subtitleTextView.getVisibility() != GONE) {
                titleTextView.setTextSize(DisplayUtility.isTablet() ? 20 : 18);
                subtitleTextView.setTextSize(DisplayUtility.isTablet() ? 16 : 14);
            } else {
                if (titleTextView != null && titleTextView.getVisibility() != GONE) {
                    titleTextView.setTextSize(!DisplayUtility.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 18 : 20);
                }
                if (subtitleTextView != null && subtitleTextView.getVisibility() != GONE) {
                    subtitleTextView.setTextSize(!DisplayUtility.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 14 : 16);
                }
            }

            if (titleTextView != null && titleTextView.getVisibility() != GONE) {
                titleTextView.measure(MeasureSpec.makeMeasureSpec(availableWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(DisplayUtility.dp(24), MeasureSpec.AT_MOST));
            }
            if (subtitleTextView != null && subtitleTextView.getVisibility() != GONE) {
                subtitleTextView.measure(MeasureSpec.makeMeasureSpec(availableWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(DisplayUtility.dp(20), MeasureSpec.AT_MOST));
            }
        }

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE || child == titleTextView || child == subtitleTextView || child == menu || child == backButtonImageView) {
                continue;
            }
            measureChildWithMargins(child, widthMeasureSpec, 0, MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY), 0);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int additionalTop = occupyStatusBar ? DisplayUtility.statusBarHeight : 0;

        int textLeft;
        if (backButtonImageView != null && backButtonImageView.getVisibility() != GONE) {
            backButtonImageView.layout(0, additionalTop, backButtonImageView.getMeasuredWidth(), additionalTop + backButtonImageView.getMeasuredHeight());
            textLeft = DisplayUtility.dp(DisplayUtility.isTablet() ? 80 : 72);
        } else {
            textLeft = DisplayUtility.dp(DisplayUtility.isTablet() ? 26 : 18);
        }

        if (menu != null && menu.getVisibility() != GONE) {
            int menuLeft = isSearchFieldVisible ? DisplayUtility.dp(DisplayUtility.isTablet() ? 74 : 66) : (right - left) - menu.getMeasuredWidth();
            menu.layout(menuLeft, additionalTop, menuLeft + menu.getMeasuredWidth(), additionalTop + menu.getMeasuredHeight());
        }

        if (titleTextView != null && titleTextView.getVisibility() != GONE) {
            int textTop;
            if (subtitleTextView != null && subtitleTextView.getVisibility() != GONE) {
                textTop = (getCurrentActionBarHeight() / 2 - titleTextView.getTextHeight()) / 2 + DisplayUtility.dp(!DisplayUtility.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 2 : 3);
            } else {
                textTop = (getCurrentActionBarHeight() - titleTextView.getTextHeight()) / 2;
            }
            titleTextView.layout(textLeft, additionalTop + textTop, textLeft + titleTextView.getMeasuredWidth(), additionalTop + textTop + titleTextView.getTextHeight());
        }
        if (subtitleTextView != null && subtitleTextView.getVisibility() != GONE) {
            int textTop = getCurrentActionBarHeight() / 2 + (getCurrentActionBarHeight() / 2 - subtitleTextView.getTextHeight()) / 2 - DisplayUtility.dp(!DisplayUtility.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 1 : 1);
            subtitleTextView.layout(textLeft, additionalTop + textTop, textLeft + subtitleTextView.getMeasuredWidth(), additionalTop + textTop + subtitleTextView.getTextHeight());
        }

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE || child == titleTextView || child == subtitleTextView || child == menu || child == backButtonImageView) {
                continue;
            }

            LayoutParams lp = (LayoutParams) child.getLayoutParams();

            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            int childLeft;
            int childTop;

            int gravity = lp.gravity;
            if (gravity == -1) {
                gravity = Gravity.TOP | Gravity.LEFT;
            }

            final int absoluteGravity = gravity & Gravity.HORIZONTAL_GRAVITY_MASK;
            final int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;

            switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
                case Gravity.CENTER_HORIZONTAL:
                    childLeft = (right - left - width) / 2 + lp.leftMargin - lp.rightMargin;
                    break;
                case Gravity.RIGHT:
                    childLeft = right - width - lp.rightMargin;
                    break;
                case Gravity.LEFT:
                default:
                    childLeft = lp.leftMargin;
            }

            switch (verticalGravity) {
                case Gravity.TOP:
                    childTop = lp.topMargin;
                    break;
                case Gravity.CENTER_VERTICAL:
                    childTop = (bottom - top - height) / 2 + lp.topMargin - lp.bottomMargin;
                    break;
                case Gravity.BOTTOM:
                    childTop = (bottom - top) - height - lp.bottomMargin;
                    break;
                default:
                    childTop = lp.topMargin;
            }
            child.layout(childLeft, childTop, childLeft + width, childTop + height);
        }
    }

    public void onMenuButtonPressed() {
        if (isActionModeShowed()) {
            return;
        }
        if (menu != null) {
            menu.onMenuButtonPressed();
        }
    }

    protected void onPause() {
        if (menu != null) {
            menu.hideAllPopupMenus();
        }
    }

    public void setAllowOverlayTitle(boolean value) {
        allowOverlayTitle = value;
    }

    public void setTitleActionRunnable(Runnable action) {
        lastRunnable = titleActionRunnable = action;
    }

    public void setTitleOverlayText(String title, int titleId, Runnable action) {
        if (!allowOverlayTitle || parentFragment.parentLayout == null) {
            return;
        }
        CharSequence textToSet = title != null ? LocaleController.getString(title, titleId) : lastTitle;
        if (textToSet != null && titleTextView == null) {
            createTitleTextView();
        }
        if (titleTextView != null) {
            titleOverlayShown = title != null;
            titleTextView.setVisibility(textToSet != null && !isSearchFieldVisible ? VISIBLE : INVISIBLE);
            titleTextView.setText(textToSet);
        }
        titleActionRunnable = action != null ? action : lastRunnable;
    }

    public boolean isSearchFieldVisible() {
        return isSearchFieldVisible;
    }

    public void setOccupyStatusBar(boolean value) {
        occupyStatusBar = value;
        if (actionMode != null) {
            actionMode.setPadding(0, occupyStatusBar ? DisplayUtility.statusBarHeight : 0, 0, 0);
        }
    }

    public boolean getOccupyStatusBar() {
        return occupyStatusBar;
    }

    public void setItemsBackgroundColor(int color, boolean isActionMode) {
        if (isActionMode) {
            itemsActionModeBackgroundColor = color;
            if (actionModeVisible) {
                if (backButtonImageView != null) {
                    backButtonImageView.setBackgroundDrawable(Theme.createSelectorDrawable(itemsActionModeBackgroundColor));
                }
            }
            if (actionMode != null) {
                actionMode.updateItemsBackgroundColor();
            }
        } else {
            itemsBackgroundColor = color;
            if (backButtonImageView != null) {
                backButtonImageView.setBackgroundDrawable(Theme.createSelectorDrawable(itemsBackgroundColor));
            }
            if (menu != null) {
                menu.updateItemsBackgroundColor();
            }
        }
    }

    public void setItemsColor(int color, boolean isActionMode) {
        if (isActionMode) {
            itemsActionModeColor = color;
            if (actionMode != null) {
                actionMode.updateItemsColor();
            }
            if (backButtonImageView != null) {
                Drawable drawable = backButtonImageView.getDrawable();
                if (drawable instanceof BackDrawable) {
                    ((BackDrawable) drawable).setRotatedColor(color);
                }
            }
        } else {
            itemsColor = color;
            if (backButtonImageView != null) {
                if (itemsColor != 0) {
                    backButtonImageView.setColorFilter(new PorterDuffColorFilter(itemsColor, PorterDuff.Mode.MULTIPLY));
                    Drawable drawable = backButtonImageView.getDrawable();
                    if (drawable instanceof BackDrawable) {
                        ((BackDrawable) drawable).setColor(color);
                    }
                }
            }
            if (menu != null) {
                menu.updateItemsColor();
            }
        }
    }

    public void setCastShadows(boolean value) {
        castShadows = value;
    }

    public boolean getCastShadows() {
        return castShadows;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event) || interceptTouches;
    }

    public static int getCurrentActionBarHeight() {
        if (DisplayUtility.isTablet()) {
            return DisplayUtility.dp(64);
        } else if (DisplayUtility.displaySize.x > DisplayUtility.displaySize.y) {
            return DisplayUtility.dp(48);
        } else {
            return DisplayUtility.dp(56);
        }
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }
}