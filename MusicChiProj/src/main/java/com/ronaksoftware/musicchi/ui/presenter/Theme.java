package com.ronaksoftware.musicchi.ui.presenter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Base64;
import android.util.StateSet;
import android.view.View;

import com.ronaksoftware.musicchi.ApplicationLoader;
import com.ronaksoftware.musicchi.R;
import com.ronaksoftware.musicchi.ui.components.CombinedDrawable;
import com.ronaksoftware.musicchi.utils.DisplayUtility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Theme {

    public static final String key_windowBackgroundWhite = "windowBackgroundWhite";
    public static final String key_progressCircle = "progressCircle";
    public static final String key_windowBackgroundGray = "windowBackgroundGray";

    public static final String key_actionBarDefault = "actionBarDefault";
    public static final String key_actionBarDefaultSelector = "actionBarDefaultSelector";
    public static final String key_actionBarWhiteSelector = "actionBarWhiteSelector";
    public static final String key_actionBarDefaultIcon = "actionBarDefaultIcon";
    public static final String key_actionBarActionModeDefault = "actionBarActionModeDefault";
    public static final String key_actionBarActionModeDefaultTop = "actionBarActionModeDefaultTop";
    public static final String key_actionBarActionModeDefaultIcon = "actionBarActionModeDefaultIcon";
    public static final String key_actionBarActionModeDefaultSelector = "actionBarActionModeDefaultSelector";
    public static final String key_actionBarDefaultTitle = "actionBarDefaultTitle";
    public static final String key_actionBarDefaultSubtitle = "actionBarDefaultSubtitle";
    public static final String key_actionBarDefaultSearch = "actionBarDefaultSearch";
    public static final String key_actionBarDefaultSearchPlaceholder = "actionBarDefaultSearchPlaceholder";
    public static final String key_actionBarDefaultSubmenuItem = "actionBarDefaultSubmenuItem";
    public static final String key_actionBarDefaultSubmenuItemIcon = "actionBarDefaultSubmenuItemIcon";
    public static final String key_actionBarDefaultSubmenuBackground = "actionBarDefaultSubmenuBackground";
    public static final String key_actionBarTabActiveText = "actionBarTabActiveText";
    public static final String key_actionBarTabUnactiveText = "actionBarTabUnactiveText";
    public static final String key_actionBarTabLine = "actionBarTabLine";
    public static final String key_actionBarTabSelector = "actionBarTabSelector";


    public static final String key_windowBackgroundUnchecked = "windowBackgroundUnchecked";
    public static final String key_windowBackgroundChecked = "windowBackgroundChecked";
    public static final String key_windowBackgroundCheckText = "windowBackgroundCheckText";
    public static final String key_listSelector = "listSelectorSDK21";
    public static final String key_windowBackgroundWhiteInputField = "windowBackgroundWhiteInputField";
    public static final String key_windowBackgroundWhiteInputFieldActivated = "windowBackgroundWhiteInputFieldActivated";
    public static final String key_windowBackgroundWhiteGrayIcon = "windowBackgroundWhiteGrayIcon";
    public static final String key_windowBackgroundWhiteBlueText = "windowBackgroundWhiteBlueText";
    public static final String key_windowBackgroundWhiteBlueText2 = "windowBackgroundWhiteBlueText2";
    public static final String key_windowBackgroundWhiteBlueText3 = "windowBackgroundWhiteBlueText3";
    public static final String key_windowBackgroundWhiteBlueText4 = "windowBackgroundWhiteBlueText4";
    public static final String key_windowBackgroundWhiteBlueText5 = "windowBackgroundWhiteBlueText5";
    public static final String key_windowBackgroundWhiteBlueText6 = "windowBackgroundWhiteBlueText6";
    public static final String key_windowBackgroundWhiteBlueText7 = "windowBackgroundWhiteBlueText7";
    public static final String key_windowBackgroundWhiteBlueButton = "windowBackgroundWhiteBlueButton";
    public static final String key_windowBackgroundWhiteBlueIcon = "windowBackgroundWhiteBlueIcon";
    public static final String key_windowBackgroundWhiteGreenText = "windowBackgroundWhiteGreenText";
    public static final String key_windowBackgroundWhiteGreenText2 = "windowBackgroundWhiteGreenText2";
    public static final String key_windowBackgroundWhiteRedText = "windowBackgroundWhiteRedText";
    public static final String key_windowBackgroundWhiteRedText2 = "windowBackgroundWhiteRedText2";
    public static final String key_windowBackgroundWhiteRedText3 = "windowBackgroundWhiteRedText3";
    public static final String key_windowBackgroundWhiteRedText4 = "windowBackgroundWhiteRedText4";
    public static final String key_windowBackgroundWhiteRedText5 = "windowBackgroundWhiteRedText5";
    public static final String key_windowBackgroundWhiteRedText6 = "windowBackgroundWhiteRedText6";
    public static final String key_windowBackgroundWhiteGrayText = "windowBackgroundWhiteGrayText";
    public static final String key_windowBackgroundWhiteGrayText2 = "windowBackgroundWhiteGrayText2";
    public static final String key_windowBackgroundWhiteGrayText3 = "windowBackgroundWhiteGrayText3";
    public static final String key_windowBackgroundWhiteGrayText4 = "windowBackgroundWhiteGrayText4";
    public static final String key_windowBackgroundWhiteGrayText5 = "windowBackgroundWhiteGrayText5";
    public static final String key_windowBackgroundWhiteGrayText6 = "windowBackgroundWhiteGrayText6";
    public static final String key_windowBackgroundWhiteGrayText7 = "windowBackgroundWhiteGrayText7";
    public static final String key_windowBackgroundWhiteGrayText8 = "windowBackgroundWhiteGrayText8";
    public static final String key_windowBackgroundWhiteGrayLine = "windowBackgroundWhiteGrayLine";
    public static final String key_windowBackgroundWhiteBlackText = "windowBackgroundWhiteBlackText";
    public static final String key_windowBackgroundWhiteHintText = "windowBackgroundWhiteHintText";
    public static final String key_windowBackgroundWhiteValueText = "windowBackgroundWhiteValueText";
    public static final String key_windowBackgroundWhiteLinkText = "windowBackgroundWhiteLinkText";
    public static final String key_windowBackgroundWhiteLinkSelection = "windowBackgroundWhiteLinkSelection";
    public static final String key_windowBackgroundWhiteBlueHeader = "windowBackgroundWhiteBlueHeader";

    public static final String key_dialogBackground = "dialogBackground";
    public static final String key_dialogBackgroundGray = "dialogBackgroundGray";
    public static final String key_dialogTextBlack = "dialogTextBlack";
    public static final String key_dialogTextLink = "dialogTextLink";
    public static final String key_dialogLinkSelection = "dialogLinkSelection";
    public static final String key_dialogTextRed = "dialogTextRed";
    public static final String key_dialogTextRed2 = "dialogTextRed2";
    public static final String key_dialogTextBlue = "dialogTextBlue";
    public static final String key_dialogTextBlue2 = "dialogTextBlue2";
    public static final String key_dialogTextBlue3 = "dialogTextBlue3";
    public static final String key_dialogTextBlue4 = "dialogTextBlue4";
    public static final String key_dialogTextGray = "dialogTextGray";
    public static final String key_dialogTextGray2 = "dialogTextGray2";
    public static final String key_dialogTextGray3 = "dialogTextGray3";
    public static final String key_dialogTextGray4 = "dialogTextGray4";
    public static final String key_dialogTextHint = "dialogTextHint";
    public static final String key_dialogInputField = "dialogInputField";
    public static final String key_dialogInputFieldActivated = "dialogInputFieldActivated";
    public static final String key_dialogCheckboxSquareBackground = "dialogCheckboxSquareBackground";
    public static final String key_dialogCheckboxSquareCheck = "dialogCheckboxSquareCheck";
    public static final String key_dialogCheckboxSquareUnchecked = "dialogCheckboxSquareUnchecked";
    public static final String key_dialogCheckboxSquareDisabled = "dialogCheckboxSquareDisabled";
    public static final String key_dialogScrollGlow = "dialogScrollGlow";
    public static final String key_dialogRoundCheckBox = "dialogRoundCheckBox";
    public static final String key_dialogRoundCheckBoxCheck = "dialogRoundCheckBoxCheck";
    public static final String key_dialogBadgeBackground = "dialogBadgeBackground";
    public static final String key_dialogBadgeText = "dialogBadgeText";
    public static final String key_dialogRadioBackground = "dialogRadioBackground";
    public static final String key_dialogRadioBackgroundChecked = "dialogRadioBackgroundChecked";
    public static final String key_dialogProgressCircle = "dialogProgressCircle";
    public static final String key_dialogLineProgress = "dialogLineProgress";
    public static final String key_dialogLineProgressBackground = "dialogLineProgressBackground";
    public static final String key_dialogButton = "dialogButton";
    public static final String key_dialogButtonSelector = "dialogButtonSelector";
    public static final String key_dialogIcon = "dialogIcon";
    public static final String key_dialogRedIcon = "dialogRedIcon";
    public static final String key_dialogGrayLine = "dialogGrayLine";
    public static final String key_dialogTopBackground = "dialogTopBackground";
    public static final String key_dialogCameraIcon = "dialogCameraIcon";
    public static final String key_dialog_inlineProgressBackground = "dialog_inlineProgressBackground";
    public static final String key_dialog_inlineProgress = "dialog_inlineProgress";
    public static final String key_dialogSearchBackground = "dialogSearchBackground";
    public static final String key_dialogSearchHint = "dialogSearchHint";
    public static final String key_dialogSearchIcon = "dialogSearchIcon";
    public static final String key_dialogSearchText = "dialogSearchText";
    public static final String key_dialogFloatingButton = "dialogFloatingButton";
    public static final String key_dialogFloatingButtonPressed = "dialogFloatingButtonPressed";
    public static final String key_dialogFloatingIcon = "dialogFloatingIcon";
    public static final String key_dialogShadowLine = "dialogShadowLine";
    public static final String key_dialogEmptyImage = "dialogEmptyImage";
    public static final String key_dialogEmptyText = "dialogEmptyText";


    private static HashMap<String, Integer> defaultColors = new HashMap<>();

    private static Paint maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    static {
        defaultColors.put(key_listSelector, 0x0f000000);

        defaultColors.put(key_dialogBackground, 0xffffffff);
        defaultColors.put(key_dialogBackgroundGray, 0xfff0f0f0);
        defaultColors.put(key_dialogTextBlack, 0xff222222);
        defaultColors.put(key_dialogTextLink, 0xff2678b6);
        defaultColors.put(key_dialogLinkSelection, 0x3362a9e3);
        defaultColors.put(key_dialogTextRed, 0xffcd5a5a);
        defaultColors.put(key_dialogTextRed2, 0xffde3a3a);
        defaultColors.put(key_dialogTextBlue, 0xff2f8cc9);
        defaultColors.put(key_dialogTextBlue2, 0xff3a95d5);
        defaultColors.put(key_dialogTextBlue3, 0xff3ec1f9);
        defaultColors.put(key_dialogTextBlue4, 0xff19a7e8);
        defaultColors.put(key_dialogTextGray, 0xff348bc1);
        defaultColors.put(key_dialogTextGray2, 0xff757575);
        defaultColors.put(key_dialogTextGray3, 0xff999999);
        defaultColors.put(key_dialogTextGray4, 0xffb3b3b3);
        defaultColors.put(key_dialogTextHint, 0xff979797);
        defaultColors.put(key_dialogIcon, 0xff676b70);
        defaultColors.put(key_dialogRedIcon, 0xffe14d4d);
        defaultColors.put(key_dialogGrayLine, 0xffd2d2d2);
        defaultColors.put(key_dialogTopBackground, 0xff6fb2e5);
        defaultColors.put(key_dialogInputField, 0xffdbdbdb);
        defaultColors.put(key_dialogInputFieldActivated, 0xff37a9f0);
        defaultColors.put(key_dialogCheckboxSquareBackground, 0xff43a0df);
        defaultColors.put(key_dialogCheckboxSquareCheck, 0xffffffff);
        defaultColors.put(key_dialogCheckboxSquareUnchecked, 0xff737373);
        defaultColors.put(key_dialogCheckboxSquareDisabled, 0xffb0b0b0);
        defaultColors.put(key_dialogRadioBackground, 0xffb3b3b3);
        defaultColors.put(key_dialogRadioBackgroundChecked, 0xff37a9f0);
        defaultColors.put(key_dialogProgressCircle, 0xff289deb);
        defaultColors.put(key_dialogLineProgress, 0xff527da3);
        defaultColors.put(key_dialogLineProgressBackground, 0xffdbdbdb);
        defaultColors.put(key_dialogButton, 0xff4991cc);
        defaultColors.put(key_dialogButtonSelector, 0x0f000000);
        defaultColors.put(key_dialogScrollGlow, 0xfff5f6f7);
        defaultColors.put(key_dialogRoundCheckBox, 0xff4cb4f5);
        defaultColors.put(key_dialogRoundCheckBoxCheck, 0xffffffff);
        defaultColors.put(key_dialogBadgeBackground, 0xff3ec1f9);
        defaultColors.put(key_dialogBadgeText, 0xffffffff);
        defaultColors.put(key_dialogCameraIcon, 0xffffffff);
        defaultColors.put(key_dialog_inlineProgressBackground, 0xf6f0f2f5);
        defaultColors.put(key_dialog_inlineProgress, 0xff6b7378);
        defaultColors.put(key_dialogSearchBackground, 0xfff2f4f5);
        defaultColors.put(key_dialogSearchHint, 0xff98a0a7);
        defaultColors.put(key_dialogSearchIcon, 0xffa1a8af);
        defaultColors.put(key_dialogSearchText, 0xff222222);
        defaultColors.put(key_dialogFloatingButton, 0xff4cb4f5);
        defaultColors.put(key_dialogFloatingButtonPressed, 0x0f000000);
        defaultColors.put(key_dialogFloatingIcon, 0xffffffff);
        defaultColors.put(key_dialogShadowLine, 0x12000000);
        defaultColors.put(key_dialogEmptyImage, 0xff9fa4a8);
        defaultColors.put(key_dialogEmptyText, 0xff8c9094);

        defaultColors.put(key_actionBarDefault, 0xffab47bc);
        defaultColors.put(key_actionBarDefaultIcon, 0xffffffff);
        defaultColors.put(key_actionBarActionModeDefault, 0xffffffff);
        defaultColors.put(key_actionBarActionModeDefaultTop, 0x10000000);
        defaultColors.put(key_actionBarActionModeDefaultIcon, 0xff676a6f);
        defaultColors.put(key_actionBarDefaultTitle, 0xffffffff);
        defaultColors.put(key_actionBarDefaultSubtitle, 0xffd5e8f7);
        defaultColors.put(key_actionBarDefaultSelector, 0xff406d94);
        defaultColors.put(key_actionBarWhiteSelector, 0x2f000000);
        defaultColors.put(key_actionBarDefaultSearch, 0xffffffff);
        defaultColors.put(key_actionBarDefaultSearchPlaceholder, 0x88ffffff);
        defaultColors.put(key_actionBarDefaultSubmenuItem, 0xff222222);
        defaultColors.put(key_actionBarDefaultSubmenuItemIcon, 0xff676b70);
        defaultColors.put(key_actionBarDefaultSubmenuBackground, 0xffffffff);
        defaultColors.put(key_actionBarActionModeDefaultSelector, 0xffe2e2e2);
        defaultColors.put(key_actionBarTabActiveText, 0xffffffff);
        defaultColors.put(key_actionBarTabUnactiveText, 0xffd5e8f7);
        defaultColors.put(key_actionBarTabLine, 0xffffffff);
        defaultColors.put(key_actionBarTabSelector, 0xff406d94);

        defaultColors.put(key_windowBackgroundWhite, 0xffffffff);
        defaultColors.put(key_windowBackgroundUnchecked, 0xff9da7b1);
        defaultColors.put(key_windowBackgroundChecked, 0xff579ed9);
        defaultColors.put(key_windowBackgroundCheckText, 0xffffffff);
        defaultColors.put(key_progressCircle, 0xff1c93e3);
        defaultColors.put(key_windowBackgroundWhiteGrayIcon, 0xff81868b);
        defaultColors.put(key_windowBackgroundWhiteBlueText, 0xff4092cd);
        defaultColors.put(key_windowBackgroundWhiteBlueText2, 0xff3a95d5);
        defaultColors.put(key_windowBackgroundWhiteBlueText3, 0xff2678b6);
        defaultColors.put(key_windowBackgroundWhiteBlueText4, 0xff1c93e3);
        defaultColors.put(key_windowBackgroundWhiteBlueText5, 0xff4c8eca);
        defaultColors.put(key_windowBackgroundWhiteBlueText6, 0xff3a8ccf);
        defaultColors.put(key_windowBackgroundWhiteBlueText7, 0xff377aae);
        defaultColors.put(key_windowBackgroundWhiteBlueButton, 0xff1e88d3);
        defaultColors.put(key_windowBackgroundWhiteBlueIcon, 0xff379de5);
        defaultColors.put(key_windowBackgroundWhiteGreenText, 0xff26972c);
        defaultColors.put(key_windowBackgroundWhiteGreenText2, 0xff37a818);
        defaultColors.put(key_windowBackgroundWhiteRedText, 0xffcd5a5a);
        defaultColors.put(key_windowBackgroundWhiteRedText2, 0xffdb5151);
        defaultColors.put(key_windowBackgroundWhiteRedText3, 0xffd24949);
        defaultColors.put(key_windowBackgroundWhiteRedText4, 0xffcf3030);
        defaultColors.put(key_windowBackgroundWhiteRedText5, 0xffed3939);
        defaultColors.put(key_windowBackgroundWhiteRedText6, 0xffff6666);
        defaultColors.put(key_windowBackgroundWhiteGrayText, 0xff838c96);
        defaultColors.put(key_windowBackgroundWhiteGrayText2, 0xff8a8a8a);
        defaultColors.put(key_windowBackgroundWhiteGrayText3, 0xff999999);
        defaultColors.put(key_windowBackgroundWhiteGrayText4, 0xff808080);
        defaultColors.put(key_windowBackgroundWhiteGrayText5, 0xffa3a3a3);
        defaultColors.put(key_windowBackgroundWhiteGrayText6, 0xff757575);
        defaultColors.put(key_windowBackgroundWhiteGrayText7, 0xffc6c6c6);
        defaultColors.put(key_windowBackgroundWhiteGrayText8, 0xff6d6d72);
        defaultColors.put(key_windowBackgroundWhiteGrayLine, 0xffdbdbdb);
        defaultColors.put(key_windowBackgroundWhiteBlackText, 0xff222222);
        defaultColors.put(key_windowBackgroundWhiteHintText, 0xffa8a8a8);
        defaultColors.put(key_windowBackgroundWhiteValueText, 0xff3a95d5);
        defaultColors.put(key_windowBackgroundWhiteLinkText, 0xff2678b6);
        defaultColors.put(key_windowBackgroundWhiteLinkSelection, 0x3362a9e3);
        defaultColors.put(key_windowBackgroundWhiteBlueHeader, 0xff3a95d5);
        defaultColors.put(key_windowBackgroundWhiteInputField, 0xffdbdbdb);
        defaultColors.put(key_windowBackgroundWhiteInputFieldActivated, 0xff37a9f0);
        defaultColors.put(key_windowBackgroundGray, 0xfff0f0f0);


    }

    private static Method StateListDrawable_getStateDrawableMethod;


    @SuppressLint("PrivateApi")
    private static Drawable getStateDrawable(Drawable drawable, int index) {
        if (Build.VERSION.SDK_INT >= 29 && drawable instanceof StateListDrawable) {
            return ((StateListDrawable) drawable).getStateDrawable(index);
        } else {
            if (StateListDrawable_getStateDrawableMethod == null) {
                try {
                    StateListDrawable_getStateDrawableMethod = StateListDrawable.class.getDeclaredMethod("getStateDrawable", int.class);
                } catch (Throwable ignore) {

                }
            }
            if (StateListDrawable_getStateDrawableMethod == null) {
                return null;
            }
            try {
                return (Drawable) StateListDrawable_getStateDrawableMethod.invoke(drawable, index);
            } catch (Exception ignore) {

            }
            return null;
        }
    }

    public static Drawable createEditTextDrawable(Context context, boolean alert) {
        Resources resources = context.getResources();
        Drawable defaultDrawable = resources.getDrawable(R.drawable.search_dark).mutate();
        defaultDrawable.setColorFilter(new PorterDuffColorFilter(getColor(alert ? key_dialogInputField : key_windowBackgroundWhiteInputField), PorterDuff.Mode.MULTIPLY));
        Drawable pressedDrawable = resources.getDrawable(R.drawable.search_dark_activated).mutate();
        pressedDrawable.setColorFilter(new PorterDuffColorFilter(getColor(alert ? key_dialogInputFieldActivated : key_windowBackgroundWhiteInputFieldActivated), PorterDuff.Mode.MULTIPLY));
        StateListDrawable stateListDrawable = new StateListDrawable() {
            @Override
            public boolean selectDrawable(int index) {
                if (Build.VERSION.SDK_INT < 21) {
                    Drawable drawable = Theme.getStateDrawable(this, index);
                    ColorFilter colorFilter = null;
                    if (drawable instanceof BitmapDrawable) {
                        colorFilter = ((BitmapDrawable) drawable).getPaint().getColorFilter();
                    } else if (drawable instanceof NinePatchDrawable) {
                        colorFilter = ((NinePatchDrawable) drawable).getPaint().getColorFilter();
                    }
                    boolean result = super.selectDrawable(index);
                    if (colorFilter != null) {
                        drawable.setColorFilter(colorFilter);
                    }
                    return result;
                }
                return super.selectDrawable(index);
            }
        };
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, pressedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_focused}, pressedDrawable);
        stateListDrawable.addState(StateSet.WILD_CARD, defaultDrawable);
        return stateListDrawable;
    }

    public static Drawable createSimpleSelectorDrawable(Context context, int resource, int defaultColor, int pressedColor) {
        Resources resources = context.getResources();
        Drawable defaultDrawable = resources.getDrawable(resource).mutate();
        if (defaultColor != 0) {
            defaultDrawable.setColorFilter(new PorterDuffColorFilter(defaultColor, PorterDuff.Mode.MULTIPLY));
        }
        Drawable pressedDrawable = resources.getDrawable(resource).mutate();
        if (pressedColor != 0) {
            pressedDrawable.setColorFilter(new PorterDuffColorFilter(pressedColor, PorterDuff.Mode.MULTIPLY));
        }
        StateListDrawable stateListDrawable = new StateListDrawable() {
            @Override
            public boolean selectDrawable(int index) {
                if (Build.VERSION.SDK_INT < 21) {
                    Drawable drawable = Theme.getStateDrawable(this, index);
                    ColorFilter colorFilter = null;
                    if (drawable instanceof BitmapDrawable) {
                        colorFilter = ((BitmapDrawable) drawable).getPaint().getColorFilter();
                    } else if (drawable instanceof NinePatchDrawable) {
                        colorFilter = ((NinePatchDrawable) drawable).getPaint().getColorFilter();
                    }
                    boolean result = super.selectDrawable(index);
                    if (colorFilter != null) {
                        drawable.setColorFilter(colorFilter);
                    }
                    return result;
                }
                return super.selectDrawable(index);
            }
        };
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, pressedDrawable);
        stateListDrawable.addState(StateSet.WILD_CARD, defaultDrawable);
        return stateListDrawable;
    }

    public static ShapeDrawable createCircleDrawable(int size, int color) {
        OvalShape ovalShape = new OvalShape();
        ovalShape.resize(size, size);
        ShapeDrawable defaultDrawable = new ShapeDrawable(ovalShape);
        defaultDrawable.getPaint().setColor(color);
        return defaultDrawable;
    }

    public static CombinedDrawable createCircleDrawableWithIcon(int size, int iconRes) {
        return createCircleDrawableWithIcon(size, iconRes, 0);
    }

    public static CombinedDrawable createCircleDrawableWithIcon(int size, int iconRes, int stroke) {
        Drawable drawable;
        if (iconRes != 0) {
            drawable = ApplicationLoader.applicationContext.getResources().getDrawable(iconRes).mutate();
        } else {
            drawable = null;
        }
        return createCircleDrawableWithIcon(size, drawable, stroke);
    }

    public static CombinedDrawable createCircleDrawableWithIcon(int size, Drawable drawable, int stroke) {
        OvalShape ovalShape = new OvalShape();
        ovalShape.resize(size, size);
        ShapeDrawable defaultDrawable = new ShapeDrawable(ovalShape);
        Paint paint = defaultDrawable.getPaint();
        paint.setColor(0xffffffff);
        if (stroke == 1) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(DisplayUtility.dp(2));
        } else if (stroke == 2) {
            paint.setAlpha(0);
        }
        CombinedDrawable combinedDrawable = new CombinedDrawable(defaultDrawable, drawable);
        combinedDrawable.setCustomSize(size, size);
        return combinedDrawable;
    }

    public static Drawable createRoundRectDrawableWithIcon(int rad, int iconRes) {
        ShapeDrawable defaultDrawable = new ShapeDrawable(new RoundRectShape(new float[]{rad, rad, rad, rad, rad, rad, rad, rad}, null, null));
        defaultDrawable.getPaint().setColor(0xffffffff);
        Drawable drawable = ApplicationLoader.applicationContext.getResources().getDrawable(iconRes).mutate();
        return new CombinedDrawable(defaultDrawable, drawable);
    }

    public static void setCombinedDrawableColor(Drawable combinedDrawable, int color, boolean isIcon) {
        if (!(combinedDrawable instanceof CombinedDrawable)) {
            return;
        }
        Drawable drawable;
        if (isIcon) {
            drawable = ((CombinedDrawable) combinedDrawable).getIcon();
        } else {
            drawable = ((CombinedDrawable) combinedDrawable).getBackground();
        }
        if (drawable instanceof ColorDrawable) {
            ((ColorDrawable) drawable).setColor(color);
        } else {
            drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
        }
    }

    public static Drawable createSimpleSelectorCircleDrawable(int size, int defaultColor, int pressedColor) {
        OvalShape ovalShape = new OvalShape();
        ovalShape.resize(size, size);
        ShapeDrawable defaultDrawable = new ShapeDrawable(ovalShape);
        defaultDrawable.getPaint().setColor(defaultColor);
        ShapeDrawable pressedDrawable = new ShapeDrawable(ovalShape);
        if (Build.VERSION.SDK_INT >= 21) {
            pressedDrawable.getPaint().setColor(0xffffffff);
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{StateSet.WILD_CARD},
                    new int[]{pressedColor}
            );
            return new RippleDrawable(colorStateList, defaultDrawable, pressedDrawable);
        } else {
            pressedDrawable.getPaint().setColor(pressedColor);
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
            stateListDrawable.addState(new int[]{android.R.attr.state_focused}, pressedDrawable);
            stateListDrawable.addState(StateSet.WILD_CARD, defaultDrawable);
            return stateListDrawable;
        }
    }

    public static Drawable createRoundRectDrawable(int rad, int defaultColor) {
        ShapeDrawable defaultDrawable = new ShapeDrawable(new RoundRectShape(new float[]{rad, rad, rad, rad, rad, rad, rad, rad}, null, null));
        defaultDrawable.getPaint().setColor(defaultColor);
        return defaultDrawable;
    }

    public static Drawable createSimpleSelectorRoundRectDrawable(int rad, int defaultColor, int pressedColor) {
        ShapeDrawable defaultDrawable = new ShapeDrawable(new RoundRectShape(new float[]{rad, rad, rad, rad, rad, rad, rad, rad}, null, null));
        defaultDrawable.getPaint().setColor(defaultColor);
        ShapeDrawable pressedDrawable = new ShapeDrawable(new RoundRectShape(new float[]{rad, rad, rad, rad, rad, rad, rad, rad}, null, null));
        pressedDrawable.getPaint().setColor(pressedColor);
        if (Build.VERSION.SDK_INT >= 21) {
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{StateSet.WILD_CARD},
                    new int[]{pressedColor}
            );
            return new RippleDrawable(colorStateList, defaultDrawable, pressedDrawable);
        } else {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
            stateListDrawable.addState(new int[]{android.R.attr.state_selected}, pressedDrawable);
            stateListDrawable.addState(StateSet.WILD_CARD, defaultDrawable);
            return stateListDrawable;
        }
    }

    public static Drawable createSelectorDrawableFromDrawables(Drawable normal, Drawable pressed) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, pressed);
        stateListDrawable.addState(StateSet.WILD_CARD, normal);
        return stateListDrawable;
    }

    public static Drawable getRoundRectSelectorDrawable(int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Drawable maskDrawable = createRoundRectDrawable(DisplayUtility.dp(3), 0xffffffff);
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{StateSet.WILD_CARD},
                    new int[]{(color & 0x00ffffff) | 0x19000000}
            );
            return new RippleDrawable(colorStateList, null, maskDrawable);
        } else {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, createRoundRectDrawable(DisplayUtility.dp(3), (color & 0x00ffffff) | 0x19000000));
            stateListDrawable.addState(new int[]{android.R.attr.state_selected}, createRoundRectDrawable(DisplayUtility.dp(3), (color & 0x00ffffff) | 0x19000000));
            stateListDrawable.addState(StateSet.WILD_CARD, new ColorDrawable(0x00000000));
            return stateListDrawable;
        }
    }

    public static Drawable createSelectorWithBackgroundDrawable(int backgroundColor, int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Drawable maskDrawable = new ColorDrawable(backgroundColor);
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{StateSet.WILD_CARD},
                    new int[]{color}
            );
            return new RippleDrawable(colorStateList, new ColorDrawable(backgroundColor), maskDrawable);
        } else {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(color));
            stateListDrawable.addState(new int[]{android.R.attr.state_selected}, new ColorDrawable(color));
            stateListDrawable.addState(StateSet.WILD_CARD, new ColorDrawable(backgroundColor));
            return stateListDrawable;
        }
    }

    public static Drawable getSelectorDrawable(boolean whiteBackground) {
        return getSelectorDrawable(getColor(key_listSelector), whiteBackground);
    }

    public static Drawable getSelectorDrawable(int color, boolean whiteBackground) {
        if (whiteBackground) {
            if (Build.VERSION.SDK_INT >= 21) {
                Drawable maskDrawable = new ColorDrawable(0xffffffff);
                ColorStateList colorStateList = new ColorStateList(
                        new int[][]{StateSet.WILD_CARD},
                        new int[]{color}
                );
                return new RippleDrawable(colorStateList, new ColorDrawable(getColor(key_windowBackgroundWhite)), maskDrawable);
            } else {
                StateListDrawable stateListDrawable = new StateListDrawable();
                stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(color));
                stateListDrawable.addState(new int[]{android.R.attr.state_selected}, new ColorDrawable(color));
                stateListDrawable.addState(StateSet.WILD_CARD, new ColorDrawable(getColor(key_windowBackgroundWhite)));
                return stateListDrawable;
            }
        } else {
            return createSelectorDrawable(color, 2);
        }
    }

    public static Drawable createSelectorDrawable(int color) {
        return createSelectorDrawable(color, 1, -1);
    }

    public static Drawable createSelectorDrawable(int color, int maskType) {
        return createSelectorDrawable(color, maskType, -1);
    }

    public static Drawable createSelectorDrawable(int color, int maskType, int radius) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= 21) {
            Drawable maskDrawable = null;
            if ((maskType == 1 || maskType == 5) && Build.VERSION.SDK_INT >= 23) {
                maskDrawable = null;
            } else if (maskType == 1 || maskType == 3 || maskType == 4 || maskType == 5 || maskType == 6 || maskType == 7) {
                maskPaint.setColor(0xffffffff);
                maskDrawable = new Drawable() {

                    RectF rect;

                    @Override
                    public void draw(Canvas canvas) {
                        Rect bounds = getBounds();
                        if (maskType == 7) {
                            if (rect == null) {
                                rect = new RectF();
                            }
                            rect.set(bounds);
                            canvas.drawRoundRect(rect, DisplayUtility.dp(6), DisplayUtility.dp(6), maskPaint);
                        } else {
                            int rad;
                            if (maskType == 1 || maskType == 6) {
                                rad = DisplayUtility.dp(20);
                            } else if (maskType == 3) {
                                rad = (Math.max(bounds.width(), bounds.height()) / 2);
                            } else {
                                rad = (int) Math.ceil(Math.sqrt((bounds.left - bounds.centerX()) * (bounds.left - bounds.centerX()) + (bounds.top - bounds.centerY()) * (bounds.top - bounds.centerY())));
                            }
                            canvas.drawCircle(bounds.centerX(), bounds.centerY(), rad, maskPaint);
                        }
                    }

                    @Override
                    public void setAlpha(int alpha) {

                    }

                    @Override
                    public void setColorFilter(ColorFilter colorFilter) {

                    }

                    @Override
                    public int getOpacity() {
                        return PixelFormat.UNKNOWN;
                    }
                };
            } else if (maskType == 2) {
                maskDrawable = new ColorDrawable(0xffffffff);
            }
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{StateSet.WILD_CARD},
                    new int[]{color}
            );
            RippleDrawable rippleDrawable = new RippleDrawable(colorStateList, null, maskDrawable);
            if (Build.VERSION.SDK_INT >= 23) {
                if (maskType == 1) {
                    rippleDrawable.setRadius(radius <= 0 ? DisplayUtility.dp(20) : radius);
                } else if (maskType == 5) {
                    rippleDrawable.setRadius(RippleDrawable.RADIUS_AUTO);
                }
            }
            return rippleDrawable;
        } else {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(color));
            stateListDrawable.addState(new int[]{android.R.attr.state_selected}, new ColorDrawable(color));
            stateListDrawable.addState(StateSet.WILD_CARD, new ColorDrawable(0x00000000));
            return stateListDrawable;
        }
    }

    public static void setDrawableColor(Drawable drawable, int color) {
        if (drawable == null) {
            return;
        }
        if (drawable instanceof ShapeDrawable) {
            ((ShapeDrawable) drawable).getPaint().setColor(color);
        } else {
            drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
        }
    }

    public static void setDrawableColorByKey(Drawable drawable, String key) {
        if (key == null) {
            return;
        }
        setDrawableColor(drawable, getColor(key));
    }

    public static int getColor(String key) {
        return getColor(key, null, false);
    }

    public static int getColor(String key, boolean[] isDefault) {
        return getColor(key, isDefault, false);
    }

    public static int getColor(String key, boolean[] isDefault, boolean ignoreAnimation) {
        return defaultColors.get(key);
    }
}
