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

    public static final String key_windowBackgroundWhite = "windowBackgroundWhite";
    public static final String key_windowBackgroundUnchecked = "windowBackgroundUnchecked";
    public static final String key_windowBackgroundChecked = "windowBackgroundChecked";
    public static final String key_windowBackgroundCheckText = "windowBackgroundCheckText";
    public static final String key_progressCircle = "progressCircle";
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
    public static final String key_switchTrack = "switchTrack";
    public static final String key_switchTrackChecked = "switchTrackChecked";
    public static final String key_switchTrackBlue = "switchTrackBlue";
    public static final String key_switchTrackBlueChecked = "switchTrackBlueChecked";
    public static final String key_switchTrackBlueThumb = "switchTrackBlueThumb";
    public static final String key_switchTrackBlueThumbChecked = "switchTrackBlueThumbChecked";
    public static final String key_switchTrackBlueSelector = "switchTrackBlueSelector";
    public static final String key_switchTrackBlueSelectorChecked = "switchTrackBlueSelectorChecked";
    public static final String key_switch2Track = "switch2Track";
    public static final String key_switch2TrackChecked = "switch2TrackChecked";
    public static final String key_checkboxSquareBackground = "checkboxSquareBackground";
    public static final String key_checkboxSquareCheck = "checkboxSquareCheck";
    public static final String key_checkboxSquareUnchecked = "checkboxSquareUnchecked";
    public static final String key_checkboxSquareDisabled = "checkboxSquareDisabled";
    public static final String key_windowBackgroundGray = "windowBackgroundGray";
    public static final String key_windowBackgroundGrayShadow = "windowBackgroundGrayShadow";
    public static final String key_emptyListPlaceholder = "emptyListPlaceholder";
    public static final String key_divider = "divider";
    public static final String key_graySection = "graySection";
    public static final String key_graySectionText = "key_graySectionText";
    public static final String key_radioBackground = "radioBackground";
    public static final String key_radioBackgroundChecked = "radioBackgroundChecked";
    public static final String key_checkbox = "checkbox";
    public static final String key_checkboxDisabled = "checkboxDisabled";
    public static final String key_checkboxCheck = "checkboxCheck";
    public static final String key_fastScrollActive = "fastScrollActive";
    public static final String key_fastScrollInactive = "fastScrollInactive";
    public static final String key_fastScrollText = "fastScrollText";

    public static final String key_inappPlayerPerformer = "inappPlayerPerformer";
    public static final String key_inappPlayerTitle = "inappPlayerTitle";
    public static final String key_inappPlayerBackground = "inappPlayerBackground";
    public static final String key_inappPlayerPlayPause = "inappPlayerPlayPause";
    public static final String key_inappPlayerClose = "inappPlayerClose";

    public static final String key_returnToCallBackground = "returnToCallBackground";
    public static final String key_returnToCallText = "returnToCallText";

    public static final String key_contextProgressInner1 = "contextProgressInner1";
    public static final String key_contextProgressOuter1 = "contextProgressOuter1";
    public static final String key_contextProgressInner2 = "contextProgressInner2";
    public static final String key_contextProgressOuter2 = "contextProgressOuter2";
    public static final String key_contextProgressInner3 = "contextProgressInner3";
    public static final String key_contextProgressOuter3 = "contextProgressOuter3";
    public static final String key_contextProgressInner4 = "contextProgressInner4";
    public static final String key_contextProgressOuter4 = "contextProgressOuter4";

    public static final String key_avatar_text = "avatar_text";
    public static final String key_avatar_backgroundSaved = "avatar_backgroundSaved";
    public static final String key_avatar_backgroundArchived = "avatar_backgroundArchived";
    public static final String key_avatar_backgroundArchivedHidden = "avatar_backgroundArchivedHidden";
    public static final String key_avatar_backgroundRed = "avatar_backgroundRed";
    public static final String key_avatar_backgroundOrange = "avatar_backgroundOrange";
    public static final String key_avatar_backgroundViolet = "avatar_backgroundViolet";
    public static final String key_avatar_backgroundGreen = "avatar_backgroundGreen";
    public static final String key_avatar_backgroundCyan = "avatar_backgroundCyan";
    public static final String key_avatar_backgroundBlue = "avatar_backgroundBlue";
    public static final String key_avatar_backgroundPink = "avatar_backgroundPink";
    public static final String key_avatar_backgroundGroupCreateSpanBlue = "avatar_backgroundGroupCreateSpanBlue";

    public static final String key_avatar_backgroundInProfileBlue = "avatar_backgroundInProfileBlue";
    public static final String key_avatar_backgroundActionBarBlue = "avatar_backgroundActionBarBlue";
    public static final String key_avatar_actionBarSelectorBlue = "avatar_actionBarSelectorBlue";
    public static final String key_avatar_actionBarIconBlue = "avatar_actionBarIconBlue";
    public static final String key_avatar_subtitleInProfileBlue = "avatar_subtitleInProfileBlue";

    public static final String key_avatar_nameInMessageRed = "avatar_nameInMessageRed";
    public static final String key_avatar_nameInMessageOrange = "avatar_nameInMessageOrange";
    public static final String key_avatar_nameInMessageViolet = "avatar_nameInMessageViolet";
    public static final String key_avatar_nameInMessageGreen = "avatar_nameInMessageGreen";
    public static final String key_avatar_nameInMessageCyan = "avatar_nameInMessageCyan";
    public static final String key_avatar_nameInMessageBlue = "avatar_nameInMessageBlue";
    public static final String key_avatar_nameInMessagePink = "avatar_nameInMessagePink";

    public static String[] keys_avatar_background = {key_avatar_backgroundRed, key_avatar_backgroundOrange, key_avatar_backgroundViolet, key_avatar_backgroundGreen, key_avatar_backgroundCyan, key_avatar_backgroundBlue, key_avatar_backgroundPink};
    public static String[] keys_avatar_nameInMessage = {key_avatar_nameInMessageRed, key_avatar_nameInMessageOrange, key_avatar_nameInMessageViolet, key_avatar_nameInMessageGreen, key_avatar_nameInMessageCyan, key_avatar_nameInMessageBlue, key_avatar_nameInMessagePink};

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
    public static final String key_actionBarDefaultArchived = "actionBarDefaultArchived";
    public static final String key_actionBarDefaultArchivedSelector = "actionBarDefaultArchivedSelector";
    public static final String key_actionBarDefaultArchivedIcon = "actionBarDefaultArchivedIcon";
    public static final String key_actionBarDefaultArchivedTitle = "actionBarDefaultArchivedTitle";
    public static final String key_actionBarDefaultArchivedSearch = "actionBarDefaultArchivedSearch";
    public static final String key_actionBarDefaultArchivedSearchPlaceholder = "actionBarDefaultSearchArchivedPlaceholder";

    public static final String key_actionBarBrowser = "actionBarBrowser";

    public static final String key_chats_onlineCircle = "chats_onlineCircle";
    public static final String key_chats_unreadCounter = "chats_unreadCounter";
    public static final String key_chats_unreadCounterMuted = "chats_unreadCounterMuted";
    public static final String key_chats_unreadCounterText = "chats_unreadCounterText";
    public static final String key_chats_name = "chats_name";
    public static final String key_chats_nameArchived = "chats_nameArchived";
    public static final String key_chats_secretName = "chats_secretName";
    public static final String key_chats_secretIcon = "chats_secretIcon";
    public static final String key_chats_nameIcon = "chats_nameIcon";
    public static final String key_chats_pinnedIcon = "chats_pinnedIcon";
    public static final String key_chats_archiveBackground = "chats_archiveBackground";
    public static final String key_chats_archivePinBackground = "chats_archivePinBackground";
    public static final String key_chats_archiveIcon = "chats_archiveIcon";
    public static final String key_chats_archiveText = "chats_archiveText";
    public static final String key_chats_message = "chats_message";
    public static final String key_chats_messageArchived = "chats_messageArchived";
    public static final String key_chats_message_threeLines = "chats_message_threeLines";
    public static final String key_chats_draft = "chats_draft";
    public static final String key_chats_nameMessage = "chats_nameMessage";
    public static final String key_chats_nameMessageArchived = "chats_nameMessageArchived";
    public static final String key_chats_nameMessage_threeLines = "chats_nameMessage_threeLines";
    public static final String key_chats_nameMessageArchived_threeLines = "chats_nameMessageArchived_threeLines";
    public static final String key_chats_attachMessage = "chats_attachMessage";
    public static final String key_chats_actionMessage = "chats_actionMessage";
    public static final String key_chats_date = "chats_date";
    public static final String key_chats_pinnedOverlay = "chats_pinnedOverlay";
    public static final String key_chats_tabletSelectedOverlay = "chats_tabletSelectedOverlay";
    public static final String key_chats_sentCheck = "chats_sentCheck";
    public static final String key_chats_sentReadCheck = "chats_sentReadCheck";
    public static final String key_chats_sentClock = "chats_sentClock";
    public static final String key_chats_sentError = "chats_sentError";
    public static final String key_chats_sentErrorIcon = "chats_sentErrorIcon";
    public static final String key_chats_verifiedBackground = "chats_verifiedBackground";
    public static final String key_chats_verifiedCheck = "chats_verifiedCheck";
    public static final String key_chats_muteIcon = "chats_muteIcon";
    public static final String key_chats_mentionIcon = "chats_mentionIcon";
    public static final String key_chats_menuTopShadow = "chats_menuTopShadow";
    public static final String key_chats_menuTopShadowCats = "chats_menuTopShadowCats";
    public static final String key_chats_menuBackground = "chats_menuBackground";
    public static final String key_chats_menuItemText = "chats_menuItemText";
    public static final String key_chats_menuItemCheck = "chats_menuItemCheck";
    public static final String key_chats_menuItemIcon = "chats_menuItemIcon";
    public static final String key_chats_menuName = "chats_menuName";
    public static final String key_chats_menuPhone = "chats_menuPhone";
    public static final String key_chats_menuPhoneCats = "chats_menuPhoneCats";
    public static final String key_chats_menuTopBackgroundCats = "chats_menuTopBackgroundCats";
    public static final String key_chats_menuTopBackground = "chats_menuTopBackground";
    public static final String key_chats_menuCloud = "chats_menuCloud";
    public static final String key_chats_menuCloudBackgroundCats = "chats_menuCloudBackgroundCats";
    public static final String key_chats_actionIcon = "chats_actionIcon";
    public static final String key_chats_actionBackground = "chats_actionBackground";
    public static final String key_chats_actionPressedBackground = "chats_actionPressedBackground";
    public static final String key_chats_actionUnreadIcon = "chats_actionUnreadIcon";
    public static final String key_chats_actionUnreadBackground = "chats_actionUnreadBackground";
    public static final String key_chats_actionUnreadPressedBackground = "chats_actionUnreadPressedBackground";
    public static final String key_chats_archivePullDownBackground = "chats_archivePullDownBackground";
    public static final String key_chats_archivePullDownBackgroundActive = "chats_archivePullDownBackgroundActive";

    public static final String key_chat_attachMediaBanBackground = "chat_attachMediaBanBackground";
    public static final String key_chat_attachMediaBanText = "chat_attachMediaBanText";
    public static final String key_chat_attachCheckBoxCheck = "chat_attachCheckBoxCheck";
    public static final String key_chat_attachCheckBoxBackground = "chat_attachCheckBoxBackground";
    public static final String key_chat_attachPhotoBackground = "chat_attachPhotoBackground";
    public static final String key_chat_attachActiveTab = "chat_attachActiveTab";
    public static final String key_chat_attachUnactiveTab = "chat_attachUnactiveTab";
    public static final String key_chat_attachPermissionImage = "chat_attachPermissionImage";
    public static final String key_chat_attachPermissionMark = "chat_attachPermissionMark";
    public static final String key_chat_attachPermissionText = "chat_attachPermissionText";
    public static final String key_chat_attachEmptyImage = "chat_attachEmptyImage";

    public static final String key_chat_inPollCorrectAnswer = "chat_inPollCorrectAnswer";
    public static final String key_chat_outPollCorrectAnswer = "chat_outPollCorrectAnswer";
    public static final String key_chat_inPollWrongAnswer = "chat_inPollWrongAnswer";
    public static final String key_chat_outPollWrongAnswer = "chat_outPollWrongAnswer";

    public static final String key_chat_attachGalleryBackground = "chat_attachGalleryBackground";
    public static final String key_chat_attachGalleryIcon = "chat_attachGalleryIcon";
    public static final String key_chat_attachAudioBackground = "chat_attachAudioBackground";
    public static final String key_chat_attachAudioIcon = "chat_attachAudioIcon";
    public static final String key_chat_attachFileBackground = "chat_attachFileBackground";
    public static final String key_chat_attachFileIcon = "chat_attachFileIcon";
    public static final String key_chat_attachContactBackground = "chat_attachContactBackground";
    public static final String key_chat_attachContactIcon = "chat_attachContactIcon";
    public static final String key_chat_attachLocationBackground = "chat_attachLocationBackground";
    public static final String key_chat_attachLocationIcon = "chat_attachLocationIcon";
    public static final String key_chat_attachPollBackground = "chat_attachPollBackground";
    public static final String key_chat_attachPollIcon = "chat_attachPollIcon";

    public static final String key_chat_status = "chat_status";
    public static final String key_chat_inRedCall = "chat_inUpCall";
    public static final String key_chat_inGreenCall = "chat_inDownCall";
    public static final String key_chat_outGreenCall = "chat_outUpCall";
    public static final String key_chat_inBubble = "chat_inBubble";
    public static final String key_chat_inBubbleSelected = "chat_inBubbleSelected";
    public static final String key_chat_inBubbleShadow = "chat_inBubbleShadow";
    public static final String key_chat_outBubble = "chat_outBubble";
    public static final String key_chat_outBubbleGradient = "chat_outBubbleGradient";
    public static final String key_chat_outBubbleGradientSelectedOverlay = "chat_outBubbleGradientSelectedOverlay";
    public static final String key_chat_outBubbleSelected = "chat_outBubbleSelected";
    public static final String key_chat_outBubbleShadow = "chat_outBubbleShadow";
    public static final String key_chat_messageTextIn = "chat_messageTextIn";
    public static final String key_chat_messageTextOut = "chat_messageTextOut";
    public static final String key_chat_messageLinkIn = "chat_messageLinkIn";
    public static final String key_chat_messageLinkOut = "chat_messageLinkOut";
    public static final String key_chat_serviceText = "chat_serviceText";
    public static final String key_chat_serviceLink = "chat_serviceLink";
    public static final String key_chat_serviceIcon = "chat_serviceIcon";
    public static final String key_chat_serviceBackground = "chat_serviceBackground";
    public static final String key_chat_serviceBackgroundSelected = "chat_serviceBackgroundSelected";
    public static final String key_chat_shareBackground = "chat_shareBackground";
    public static final String key_chat_shareBackgroundSelected = "chat_shareBackgroundSelected";
    public static final String key_chat_muteIcon = "chat_muteIcon";
    public static final String key_chat_lockIcon = "chat_lockIcon";
    public static final String key_chat_outSentCheck = "chat_outSentCheck";
    public static final String key_chat_outSentCheckSelected = "chat_outSentCheckSelected";
    public static final String key_chat_outSentCheckRead = "chat_outSentCheckRead";
    public static final String key_chat_outSentCheckReadSelected = "chat_outSentCheckReadSelected";
    public static final String key_chat_outSentClock = "chat_outSentClock";
    public static final String key_chat_outSentClockSelected = "chat_outSentClockSelected";
    public static final String key_chat_inSentClock = "chat_inSentClock";
    public static final String key_chat_inSentClockSelected = "chat_inSentClockSelected";
    public static final String key_chat_mediaSentCheck = "chat_mediaSentCheck";
    public static final String key_chat_mediaSentClock = "chat_mediaSentClock";
    public static final String key_chat_inMediaIcon = "chat_inMediaIcon";
    public static final String key_chat_outMediaIcon = "chat_outMediaIcon";
    public static final String key_chat_inMediaIconSelected = "chat_inMediaIconSelected";
    public static final String key_chat_outMediaIconSelected = "chat_outMediaIconSelected";
    public static final String key_chat_mediaTimeBackground = "chat_mediaTimeBackground";
    public static final String key_chat_outViews = "chat_outViews";
    public static final String key_chat_outViewsSelected = "chat_outViewsSelected";
    public static final String key_chat_inViews = "chat_inViews";
    public static final String key_chat_inViewsSelected = "chat_inViewsSelected";
    public static final String key_chat_mediaViews = "chat_mediaViews";
    public static final String key_chat_outMenu = "chat_outMenu";
    public static final String key_chat_outMenuSelected = "chat_outMenuSelected";
    public static final String key_chat_inMenu = "chat_inMenu";
    public static final String key_chat_inMenuSelected = "chat_inMenuSelected";
    public static final String key_chat_mediaMenu = "chat_mediaMenu";
    public static final String key_chat_outInstant = "chat_outInstant";
    public static final String key_chat_outInstantSelected = "chat_outInstantSelected";
    public static final String key_chat_inInstant = "chat_inInstant";
    public static final String key_chat_inInstantSelected = "chat_inInstantSelected";
    public static final String key_chat_sentError = "chat_sentError";
    public static final String key_chat_sentErrorIcon = "chat_sentErrorIcon";
    public static final String key_chat_selectedBackground = "chat_selectedBackground";
    public static final String key_chat_previewDurationText = "chat_previewDurationText";
    public static final String key_chat_previewGameText = "chat_previewGameText";
    public static final String key_chat_inPreviewInstantText = "chat_inPreviewInstantText";
    public static final String key_chat_outPreviewInstantText = "chat_outPreviewInstantText";
    public static final String key_chat_inPreviewInstantSelectedText = "chat_inPreviewInstantSelectedText";
    public static final String key_chat_outPreviewInstantSelectedText = "chat_outPreviewInstantSelectedText";
    public static final String key_chat_secretTimeText = "chat_secretTimeText";
    public static final String key_chat_stickerNameText = "chat_stickerNameText";
    public static final String key_chat_botButtonText = "chat_botButtonText";
    public static final String key_chat_botProgress = "chat_botProgress";
    public static final String key_chat_inForwardedNameText = "chat_inForwardedNameText";
    public static final String key_chat_outForwardedNameText = "chat_outForwardedNameText";
    public static final String key_chat_inViaBotNameText = "chat_inViaBotNameText";
    public static final String key_chat_outViaBotNameText = "chat_outViaBotNameText";
    public static final String key_chat_stickerViaBotNameText = "chat_stickerViaBotNameText";
    public static final String key_chat_inReplyLine = "chat_inReplyLine";
    public static final String key_chat_outReplyLine = "chat_outReplyLine";
    public static final String key_chat_stickerReplyLine = "chat_stickerReplyLine";
    public static final String key_chat_inReplyNameText = "chat_inReplyNameText";
    public static final String key_chat_outReplyNameText = "chat_outReplyNameText";
    public static final String key_chat_stickerReplyNameText = "chat_stickerReplyNameText";
    public static final String key_chat_inReplyMessageText = "chat_inReplyMessageText";
    public static final String key_chat_outReplyMessageText = "chat_outReplyMessageText";
    public static final String key_chat_inReplyMediaMessageText = "chat_inReplyMediaMessageText";
    public static final String key_chat_outReplyMediaMessageText = "chat_outReplyMediaMessageText";
    public static final String key_chat_inReplyMediaMessageSelectedText = "chat_inReplyMediaMessageSelectedText";
    public static final String key_chat_outReplyMediaMessageSelectedText = "chat_outReplyMediaMessageSelectedText";
    public static final String key_chat_stickerReplyMessageText = "chat_stickerReplyMessageText";
    public static final String key_chat_inPreviewLine = "chat_inPreviewLine";
    public static final String key_chat_outPreviewLine = "chat_outPreviewLine";
    public static final String key_chat_inSiteNameText = "chat_inSiteNameText";
    public static final String key_chat_outSiteNameText = "chat_outSiteNameText";
    public static final String key_chat_inContactNameText = "chat_inContactNameText";
    public static final String key_chat_outContactNameText = "chat_outContactNameText";
    public static final String key_chat_inContactPhoneText = "chat_inContactPhoneText";
    public static final String key_chat_inContactPhoneSelectedText = "chat_inContactPhoneSelectedText";
    public static final String key_chat_outContactPhoneText = "chat_outContactPhoneText";
    public static final String key_chat_outContactPhoneSelectedText = "chat_outContactPhoneSelectedText";
    public static final String key_chat_mediaProgress = "chat_mediaProgress";
    public static final String key_chat_inAudioProgress = "chat_inAudioProgress";
    public static final String key_chat_outAudioProgress = "chat_outAudioProgress";
    public static final String key_chat_inAudioSelectedProgress = "chat_inAudioSelectedProgress";
    public static final String key_chat_outAudioSelectedProgress = "chat_outAudioSelectedProgress";
    public static final String key_chat_mediaTimeText = "chat_mediaTimeText";
    public static final String key_chat_adminText = "chat_adminText";
    public static final String key_chat_adminSelectedText = "chat_adminSelectedText";
    public static final String key_chat_inTimeText = "chat_inTimeText";
    public static final String key_chat_outTimeText = "chat_outTimeText";
    public static final String key_chat_inTimeSelectedText = "chat_inTimeSelectedText";
    public static final String key_chat_outTimeSelectedText = "chat_outTimeSelectedText";
    public static final String key_chat_inAudioPerformerText = "chat_inAudioPerfomerText";
    public static final String key_chat_inAudioPerformerSelectedText = "chat_inAudioPerfomerSelectedText";
    public static final String key_chat_outAudioPerformerText = "chat_outAudioPerfomerText";
    public static final String key_chat_outAudioPerformerSelectedText = "chat_outAudioPerfomerSelectedText";
    public static final String key_chat_inAudioTitleText = "chat_inAudioTitleText";
    public static final String key_chat_outAudioTitleText = "chat_outAudioTitleText";
    public static final String key_chat_inAudioDurationText = "chat_inAudioDurationText";
    public static final String key_chat_outAudioDurationText = "chat_outAudioDurationText";
    public static final String key_chat_inAudioDurationSelectedText = "chat_inAudioDurationSelectedText";
    public static final String key_chat_outAudioDurationSelectedText = "chat_outAudioDurationSelectedText";
    public static final String key_chat_inAudioSeekbar = "chat_inAudioSeekbar";
    public static final String key_chat_inAudioCacheSeekbar = "chat_inAudioCacheSeekbar";
    public static final String key_chat_outAudioSeekbar = "chat_outAudioSeekbar";
    public static final String key_chat_outAudioCacheSeekbar = "chat_outAudioCacheSeekbar";
    public static final String key_chat_inAudioSeekbarSelected = "chat_inAudioSeekbarSelected";
    public static final String key_chat_outAudioSeekbarSelected = "chat_outAudioSeekbarSelected";
    public static final String key_chat_inAudioSeekbarFill = "chat_inAudioSeekbarFill";
    public static final String key_chat_outAudioSeekbarFill = "chat_outAudioSeekbarFill";
    public static final String key_chat_inVoiceSeekbar = "chat_inVoiceSeekbar";
    public static final String key_chat_outVoiceSeekbar = "chat_outVoiceSeekbar";
    public static final String key_chat_inVoiceSeekbarSelected = "chat_inVoiceSeekbarSelected";
    public static final String key_chat_outVoiceSeekbarSelected = "chat_outVoiceSeekbarSelected";
    public static final String key_chat_inVoiceSeekbarFill = "chat_inVoiceSeekbarFill";
    public static final String key_chat_outVoiceSeekbarFill = "chat_outVoiceSeekbarFill";
    public static final String key_chat_inFileProgress = "chat_inFileProgress";
    public static final String key_chat_outFileProgress = "chat_outFileProgress";
    public static final String key_chat_inFileProgressSelected = "chat_inFileProgressSelected";
    public static final String key_chat_outFileProgressSelected = "chat_outFileProgressSelected";
    public static final String key_chat_inFileNameText = "chat_inFileNameText";
    public static final String key_chat_outFileNameText = "chat_outFileNameText";
    public static final String key_chat_inFileInfoText = "chat_inFileInfoText";
    public static final String key_chat_outFileInfoText = "chat_outFileInfoText";
    public static final String key_chat_inFileInfoSelectedText = "chat_inFileInfoSelectedText";
    public static final String key_chat_outFileInfoSelectedText = "chat_outFileInfoSelectedText";
    public static final String key_chat_inFileBackground = "chat_inFileBackground";
    public static final String key_chat_outFileBackground = "chat_outFileBackground";
    public static final String key_chat_inFileBackgroundSelected = "chat_inFileBackgroundSelected";
    public static final String key_chat_outFileBackgroundSelected = "chat_outFileBackgroundSelected";
    public static final String key_chat_inVenueInfoText = "chat_inVenueInfoText";
    public static final String key_chat_outVenueInfoText = "chat_outVenueInfoText";
    public static final String key_chat_inVenueInfoSelectedText = "chat_inVenueInfoSelectedText";
    public static final String key_chat_outVenueInfoSelectedText = "chat_outVenueInfoSelectedText";
    public static final String key_chat_mediaInfoText = "chat_mediaInfoText";
    public static final String key_chat_linkSelectBackground = "chat_linkSelectBackground";
    public static final String key_chat_textSelectBackground = "chat_textSelectBackground";
    public static final String key_chat_wallpaper = "chat_wallpaper";
    public static final String key_chat_wallpaper_gradient_to = "chat_wallpaper_gradient_to";
    public static final String key_chat_wallpaper_gradient_rotation = "chat_wallpaper_gradient_rotation";
    public static final String key_chat_messagePanelBackground = "chat_messagePanelBackground";
    public static final String key_chat_messagePanelShadow = "chat_messagePanelShadow";
    public static final String key_chat_messagePanelText = "chat_messagePanelText";
    public static final String key_chat_messagePanelHint = "chat_messagePanelHint";
    public static final String key_chat_messagePanelCursor = "chat_messagePanelCursor";
    public static final String key_chat_messagePanelIcons = "chat_messagePanelIcons";
    public static final String key_chat_messagePanelSend = "chat_messagePanelSend";
    public static final String key_chat_messagePanelVoiceLock = "key_chat_messagePanelVoiceLock";
    public static final String key_chat_messagePanelVoiceLockBackground = "key_chat_messagePanelVoiceLockBackground";
    public static final String key_chat_messagePanelVoiceLockShadow = "key_chat_messagePanelVoiceLockShadow";
    public static final String key_chat_messagePanelVideoFrame = "chat_messagePanelVideoFrame";
    public static final String key_chat_topPanelBackground = "chat_topPanelBackground";
    public static final String key_chat_topPanelClose = "chat_topPanelClose";
    public static final String key_chat_topPanelLine = "chat_topPanelLine";
    public static final String key_chat_topPanelTitle = "chat_topPanelTitle";
    public static final String key_chat_topPanelMessage = "chat_topPanelMessage";
    public static final String key_chat_reportSpam = "chat_reportSpam";
    public static final String key_chat_addContact = "chat_addContact";
    public static final String key_chat_inLoader = "chat_inLoader";
    public static final String key_chat_inLoaderSelected = "chat_inLoaderSelected";
    public static final String key_chat_outLoader = "chat_outLoader";
    public static final String key_chat_outLoaderSelected = "chat_outLoaderSelected";
    public static final String key_chat_inLoaderPhoto = "chat_inLoaderPhoto";
    public static final String key_chat_inLoaderPhotoSelected = "chat_inLoaderPhotoSelected";
    public static final String key_chat_inLoaderPhotoIcon = "chat_inLoaderPhotoIcon";
    public static final String key_chat_inLoaderPhotoIconSelected = "chat_inLoaderPhotoIconSelected";
    public static final String key_chat_outLoaderPhoto = "chat_outLoaderPhoto";
    public static final String key_chat_outLoaderPhotoSelected = "chat_outLoaderPhotoSelected";
    public static final String key_chat_outLoaderPhotoIcon = "chat_outLoaderPhotoIcon";
    public static final String key_chat_outLoaderPhotoIconSelected = "chat_outLoaderPhotoIconSelected";
    public static final String key_chat_mediaLoaderPhoto = "chat_mediaLoaderPhoto";
    public static final String key_chat_mediaLoaderPhotoSelected = "chat_mediaLoaderPhotoSelected";
    public static final String key_chat_mediaLoaderPhotoIcon = "chat_mediaLoaderPhotoIcon";
    public static final String key_chat_mediaLoaderPhotoIconSelected = "chat_mediaLoaderPhotoIconSelected";
    public static final String key_chat_inLocationBackground = "chat_inLocationBackground";
    public static final String key_chat_inLocationIcon = "chat_inLocationIcon";
    public static final String key_chat_outLocationBackground = "chat_outLocationBackground";
    public static final String key_chat_outLocationIcon = "chat_outLocationIcon";
    public static final String key_chat_inContactBackground = "chat_inContactBackground";
    public static final String key_chat_inContactIcon = "chat_inContactIcon";
    public static final String key_chat_outContactBackground = "chat_outContactBackground";
    public static final String key_chat_outContactIcon = "chat_outContactIcon";
    public static final String key_chat_inFileIcon = "chat_inFileIcon";
    public static final String key_chat_inFileSelectedIcon = "chat_inFileSelectedIcon";
    public static final String key_chat_outFileIcon = "chat_outFileIcon";
    public static final String key_chat_outFileSelectedIcon = "chat_outFileSelectedIcon";
    public static final String key_chat_replyPanelIcons = "chat_replyPanelIcons";
    public static final String key_chat_replyPanelClose = "chat_replyPanelClose";
    public static final String key_chat_replyPanelName = "chat_replyPanelName";
    public static final String key_chat_replyPanelMessage = "chat_replyPanelMessage";
    public static final String key_chat_replyPanelLine = "chat_replyPanelLine";
    public static final String key_chat_searchPanelIcons = "chat_searchPanelIcons";
    public static final String key_chat_searchPanelText = "chat_searchPanelText";
    public static final String key_chat_secretChatStatusText = "chat_secretChatStatusText";
    public static final String key_chat_fieldOverlayText = "chat_fieldOverlayText";
    public static final String key_chat_stickersHintPanel = "chat_stickersHintPanel";
    public static final String key_chat_botSwitchToInlineText = "chat_botSwitchToInlineText";
    public static final String key_chat_unreadMessagesStartArrowIcon = "chat_unreadMessagesStartArrowIcon";
    public static final String key_chat_unreadMessagesStartText = "chat_unreadMessagesStartText";
    public static final String key_chat_unreadMessagesStartBackground = "chat_unreadMessagesStartBackground";
    public static final String key_chat_inlineResultIcon = "chat_inlineResultIcon";
    public static final String key_chat_emojiPanelBackground = "chat_emojiPanelBackground";
    public static final String key_chat_emojiPanelBadgeBackground = "chat_emojiPanelBadgeBackground";
    public static final String key_chat_emojiPanelBadgeText = "chat_emojiPanelBadgeText";
    public static final String key_chat_emojiSearchBackground = "chat_emojiSearchBackground";
    public static final String key_chat_emojiSearchIcon = "chat_emojiSearchIcon";
    public static final String key_chat_emojiPanelShadowLine = "chat_emojiPanelShadowLine";
    public static final String key_chat_emojiPanelEmptyText = "chat_emojiPanelEmptyText";
    public static final String key_chat_emojiPanelIcon = "chat_emojiPanelIcon";
    public static final String key_chat_emojiBottomPanelIcon = "chat_emojiBottomPanelIcon";
    public static final String key_chat_emojiPanelIconSelected = "chat_emojiPanelIconSelected";
    public static final String key_chat_emojiPanelStickerPackSelector = "chat_emojiPanelStickerPackSelector";
    public static final String key_chat_emojiPanelStickerPackSelectorLine = "chat_emojiPanelStickerPackSelectorLine";
    public static final String key_chat_emojiPanelBackspace = "chat_emojiPanelBackspace";
    public static final String key_chat_emojiPanelMasksIcon = "chat_emojiPanelMasksIcon";
    public static final String key_chat_emojiPanelMasksIconSelected = "chat_emojiPanelMasksIconSelected";
    public static final String key_chat_emojiPanelTrendingTitle = "chat_emojiPanelTrendingTitle";
    public static final String key_chat_emojiPanelStickerSetName = "chat_emojiPanelStickerSetName";
    public static final String key_chat_emojiPanelStickerSetNameHighlight = "chat_emojiPanelStickerSetNameHighlight";
    public static final String key_chat_emojiPanelStickerSetNameIcon = "chat_emojiPanelStickerSetNameIcon";
    public static final String key_chat_emojiPanelTrendingDescription = "chat_emojiPanelTrendingDescription";
    public static final String key_chat_botKeyboardButtonText = "chat_botKeyboardButtonText";
    public static final String key_chat_botKeyboardButtonBackground = "chat_botKeyboardButtonBackground";
    public static final String key_chat_botKeyboardButtonBackgroundPressed = "chat_botKeyboardButtonBackgroundPressed";
    public static final String key_chat_emojiPanelNewTrending = "chat_emojiPanelNewTrending";
    public static final String key_chat_messagePanelVoicePressed = "chat_messagePanelVoicePressed";
    public static final String key_chat_messagePanelVoiceBackground = "chat_messagePanelVoiceBackground";
    public static final String key_chat_messagePanelVoiceShadow = "chat_messagePanelVoiceShadow";
    public static final String key_chat_messagePanelVoiceDelete = "chat_messagePanelVoiceDelete";
    public static final String key_chat_messagePanelVoiceDuration = "chat_messagePanelVoiceDuration";
    public static final String key_chat_recordedVoicePlayPause = "chat_recordedVoicePlayPause";
    public static final String key_chat_recordedVoicePlayPausePressed = "chat_recordedVoicePlayPausePressed";
    public static final String key_chat_recordedVoiceProgress = "chat_recordedVoiceProgress";
    public static final String key_chat_recordedVoiceProgressInner = "chat_recordedVoiceProgressInner";
    public static final String key_chat_recordedVoiceDot = "chat_recordedVoiceDot";
    public static final String key_chat_recordedVoiceBackground = "chat_recordedVoiceBackground";
    public static final String key_chat_recordVoiceCancel = "chat_recordVoiceCancel";
    public static final String key_chat_recordTime = "chat_recordTime";
    public static final String key_chat_messagePanelCancelInlineBot = "chat_messagePanelCancelInlineBot";
    public static final String key_chat_gifSaveHintText = "chat_gifSaveHintText";
    public static final String key_chat_gifSaveHintBackground = "chat_gifSaveHintBackground";
    public static final String key_chat_goDownButton = "chat_goDownButton";
    public static final String key_chat_goDownButtonShadow = "chat_goDownButtonShadow";
    public static final String key_chat_goDownButtonIcon = "chat_goDownButtonIcon";
    public static final String key_chat_goDownButtonCounter = "chat_goDownButtonCounter";
    public static final String key_chat_goDownButtonCounterBackground = "chat_goDownButtonCounterBackground";
    public static final String key_chat_secretTimerBackground = "chat_secretTimerBackground";
    public static final String key_chat_secretTimerText = "chat_secretTimerText";
    public static final String key_chat_outTextSelectionHighlight = "chat_outTextSelectionHighlight";
    public static final String key_chat_inTextSelectionHighlight = "chat_inTextSelectionHighlight";
    public static final String key_chat_TextSelectionCursor = "chat_TextSelectionCursor";

    public static final String key_passport_authorizeBackground = "passport_authorizeBackground";
    public static final String key_passport_authorizeBackgroundSelected = "passport_authorizeBackgroundSelected";
    public static final String key_passport_authorizeText = "passport_authorizeText";

    public static final String key_profile_creatorIcon = "profile_creatorIcon";
    public static final String key_profile_title = "profile_title";
    public static final String key_profile_actionIcon = "profile_actionIcon";
    public static final String key_profile_actionBackground = "profile_actionBackground";
    public static final String key_profile_actionPressedBackground = "profile_actionPressedBackground";
    public static final String key_profile_verifiedBackground = "profile_verifiedBackground";
    public static final String key_profile_verifiedCheck = "profile_verifiedCheck";
    public static final String key_profile_status = "profile_status";

    public static final String key_sharedMedia_startStopLoadIcon = "sharedMedia_startStopLoadIcon";
    public static final String key_sharedMedia_linkPlaceholder = "sharedMedia_linkPlaceholder";
    public static final String key_sharedMedia_linkPlaceholderText = "sharedMedia_linkPlaceholderText";
    public static final String key_sharedMedia_photoPlaceholder = "sharedMedia_photoPlaceholder";
    public static final String key_sharedMedia_actionMode = "sharedMedia_actionMode";

    public static final String key_featuredStickers_addedIcon = "featuredStickers_addedIcon";
    public static final String key_featuredStickers_buttonProgress = "featuredStickers_buttonProgress";
    public static final String key_featuredStickers_addButton = "featuredStickers_addButton";
    public static final String key_featuredStickers_addButtonPressed = "featuredStickers_addButtonPressed";
    public static final String key_featuredStickers_removeButtonText = "featuredStickers_removeButtonText";
    public static final String key_featuredStickers_buttonText = "featuredStickers_buttonText";
    public static final String key_featuredStickers_unread = "featuredStickers_unread";

    public static final String key_stickers_menu = "stickers_menu";
    public static final String key_stickers_menuSelector = "stickers_menuSelector";

    public static final String key_changephoneinfo_image = "changephoneinfo_image";
    public static final String key_changephoneinfo_image2 = "changephoneinfo_image2";

    public static final String key_groupcreate_hintText = "groupcreate_hintText";
    public static final String key_groupcreate_cursor = "groupcreate_cursor";
    public static final String key_groupcreate_sectionShadow = "groupcreate_sectionShadow";
    public static final String key_groupcreate_sectionText = "groupcreate_sectionText";
    public static final String key_groupcreate_spanText = "groupcreate_spanText";
    public static final String key_groupcreate_spanBackground = "groupcreate_spanBackground";
    public static final String key_groupcreate_spanDelete = "groupcreate_spanDelete";

    public static final String key_contacts_inviteBackground = "contacts_inviteBackground";
    public static final String key_contacts_inviteText = "contacts_inviteText";

    public static final String key_login_progressInner = "login_progressInner";
    public static final String key_login_progressOuter = "login_progressOuter";

    public static final String key_musicPicker_checkbox = "musicPicker_checkbox";
    public static final String key_musicPicker_checkboxCheck = "musicPicker_checkboxCheck";
    public static final String key_musicPicker_buttonBackground = "musicPicker_buttonBackground";
    public static final String key_musicPicker_buttonIcon = "musicPicker_buttonIcon";

    public static final String key_picker_enabledButton = "picker_enabledButton";
    public static final String key_picker_disabledButton = "picker_disabledButton";
    public static final String key_picker_badge = "picker_badge";
    public static final String key_picker_badgeText = "picker_badgeText";

    public static final String key_location_sendLocationBackground = "location_sendLocationBackground";
    public static final String key_location_sendLocationIcon = "location_sendLocationIcon";
    public static final String key_location_sendLocationText = "location_sendLocationText";
    public static final String key_location_sendLiveLocationBackground = "location_sendLiveLocationBackground";
    public static final String key_location_sendLiveLocationIcon = "location_sendLiveLocationIcon";
    public static final String key_location_sendLiveLocationText = "location_sendLiveLocationText";
    public static final String key_location_liveLocationProgress = "location_liveLocationProgress";
    public static final String key_location_placeLocationBackground = "location_placeLocationBackground";
    public static final String key_location_actionIcon = "location_actionIcon";
    public static final String key_location_actionActiveIcon = "location_actionActiveIcon";
    public static final String key_location_actionBackground = "location_actionBackground";
    public static final String key_location_actionPressedBackground = "location_actionPressedBackground";

    public static final String key_dialog_liveLocationProgress = "dialog_liveLocationProgress";

    public static final String key_files_folderIcon = "files_folderIcon";
    public static final String key_files_folderIconBackground = "files_folderIconBackground";
    public static final String key_files_iconText = "files_iconText";

    public static final String key_sessions_devicesImage = "sessions_devicesImage";

    public static final String key_calls_callReceivedGreenIcon = "calls_callReceivedGreenIcon";
    public static final String key_calls_callReceivedRedIcon = "calls_callReceivedRedIcon";

    public static final String key_undo_background = "undo_background";
    public static final String key_undo_cancelColor = "undo_cancelColor";
    public static final String key_undo_infoColor = "undo_infoColor";

    public static final String key_sheet_scrollUp = "key_sheet_scrollUp";
    public static final String key_sheet_other = "key_sheet_other";

    public static final String key_wallet_blackBackground = "wallet_blackBackground";
    public static final String key_wallet_graySettingsBackground = "wallet_graySettingsBackground";
    public static final String key_wallet_grayBackground = "wallet_grayBackground";
    public static final String key_wallet_whiteBackground = "wallet_whiteBackground";
    public static final String key_wallet_blackBackgroundSelector = "wallet_blackBackgroundSelector";
    public static final String key_wallet_whiteText = "wallet_whiteText";
    public static final String key_wallet_blackText = "wallet_blackText";
    public static final String key_wallet_statusText = "wallet_statusText";
    public static final String key_wallet_grayText = "wallet_grayText";
    public static final String key_wallet_grayText2 = "wallet_grayText2";
    public static final String key_wallet_greenText = "wallet_greenText";
    public static final String key_wallet_redText = "wallet_redText";
    public static final String key_wallet_dateText = "wallet_dateText";
    public static final String key_wallet_commentText = "wallet_commentText";
    public static final String key_wallet_releaseBackground = "wallet_releaseBackground";
    public static final String key_wallet_pullBackground = "wallet_pullBackground";
    public static final String key_wallet_buttonBackground = "wallet_buttonBackground";
    public static final String key_wallet_buttonPressedBackground = "wallet_buttonPressedBackground";
    public static final String key_wallet_buttonText = "wallet_buttonText";
    public static final String key_wallet_addressConfirmBackground = "wallet_addressConfirmBackground";

    //ununsed
    public static final String key_chat_outBroadcast = "chat_outBroadcast";
    public static final String key_chat_mediaBroadcast = "chat_mediaBroadcast";

    public static final String key_player_actionBar = "player_actionBar";
    public static final String key_player_actionBarSelector = "player_actionBarSelector";
    public static final String key_player_actionBarTitle = "player_actionBarTitle";
    public static final String key_player_actionBarTop = "player_actionBarTop";
    public static final String key_player_actionBarSubtitle = "player_actionBarSubtitle";
    public static final String key_player_actionBarItems = "player_actionBarItems";
    public static final String key_player_background = "player_background";
    public static final String key_player_time = "player_time";
    public static final String key_player_progressBackground = "player_progressBackground";
    public static final String key_player_progressCachedBackground = "key_player_progressCachedBackground";
    public static final String key_player_progress = "player_progress";
    public static final String key_player_placeholder = "player_placeholder";
    public static final String key_player_placeholderBackground = "player_placeholderBackground";
    public static final String key_player_button = "player_button";
    public static final String key_player_buttonActive = "player_buttonActive";

    private static HashMap<String, Integer> defaultColors = new HashMap<>();

    private static Paint maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public static Drawable moveUpDrawable;

    static {
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
        defaultColors.put(key_switchTrack, 0xffb0b5ba);
        defaultColors.put(key_switchTrackChecked, 0xff52ade9);
        defaultColors.put(key_switchTrackBlue, 0xff828e99);
        defaultColors.put(key_switchTrackBlueChecked, 0xff3c88c7);
        defaultColors.put(key_switchTrackBlueThumb, 0xffffffff);
        defaultColors.put(key_switchTrackBlueThumbChecked, 0xffffffff);
        defaultColors.put(key_switchTrackBlueSelector, 0x17404a53);
        defaultColors.put(key_switchTrackBlueSelectorChecked, 0x21024781);
        defaultColors.put(key_switch2Track, 0xfff57e7e);
        defaultColors.put(key_switch2TrackChecked, 0xff52ade9);
        defaultColors.put(key_checkboxSquareBackground, 0xff43a0df);
        defaultColors.put(key_checkboxSquareCheck, 0xffffffff);
        defaultColors.put(key_checkboxSquareUnchecked, 0xff737373);
        defaultColors.put(key_checkboxSquareDisabled, 0xffb0b0b0);
        defaultColors.put(key_listSelector, 0x0f000000);
        defaultColors.put(key_radioBackground, 0xffb3b3b3);
        defaultColors.put(key_radioBackgroundChecked, 0xff37a9f0);
        defaultColors.put(key_windowBackgroundGray, 0xfff0f0f0);
        defaultColors.put(key_windowBackgroundGrayShadow, 0xff000000);
        defaultColors.put(key_emptyListPlaceholder, 0xff959595);
        defaultColors.put(key_divider, 0xffd9d9d9);
        defaultColors.put(key_graySection, 0xfff5f5f5);
        defaultColors.put(key_graySectionText, 0xff82878A);
        defaultColors.put(key_contextProgressInner1, 0xffbfdff6);
        defaultColors.put(key_contextProgressOuter1, 0xff2b96e2);
        defaultColors.put(key_contextProgressInner2, 0xffbfdff6);
        defaultColors.put(key_contextProgressOuter2, 0xffffffff);
        defaultColors.put(key_contextProgressInner3, 0xffb3b3b3);
        defaultColors.put(key_contextProgressOuter3, 0xffffffff);
        defaultColors.put(key_contextProgressInner4, 0xffcacdd0);
        defaultColors.put(key_contextProgressOuter4, 0xff2f3438);
        defaultColors.put(key_fastScrollActive, 0xff52a3db);
        defaultColors.put(key_fastScrollInactive, 0xffc9cdd1);
        defaultColors.put(key_fastScrollText, 0xffffffff);

        defaultColors.put(key_avatar_text, 0xffffffff);

        defaultColors.put(key_avatar_backgroundSaved, 0xff66bffa);
        defaultColors.put(key_avatar_backgroundArchived, 0xffa9b6c1);
        defaultColors.put(key_avatar_backgroundArchivedHidden, 0xff66bffa);
        defaultColors.put(key_avatar_backgroundRed, 0xffe56555);
        defaultColors.put(key_avatar_backgroundOrange, 0xfff28c48);
        defaultColors.put(key_avatar_backgroundViolet, 0xff8e85ee);
        defaultColors.put(key_avatar_backgroundGreen, 0xff76c84d);
        defaultColors.put(key_avatar_backgroundCyan, 0xff5fbed5);
        defaultColors.put(key_avatar_backgroundBlue, 0xff549cdd);
        defaultColors.put(key_avatar_backgroundPink, 0xfff2749a);
        defaultColors.put(key_avatar_backgroundGroupCreateSpanBlue, 0xffe6eff7);

        defaultColors.put(key_avatar_backgroundInProfileBlue, 0xff5085b1);
        defaultColors.put(key_avatar_backgroundActionBarBlue, 0xff598fba);
        defaultColors.put(key_avatar_subtitleInProfileBlue, 0xffd7eafa);
        defaultColors.put(key_avatar_actionBarSelectorBlue, 0xff4981ad);
        defaultColors.put(key_avatar_actionBarIconBlue, 0xffffffff);

        defaultColors.put(key_avatar_nameInMessageRed, 0xffca5650);
        defaultColors.put(key_avatar_nameInMessageOrange, 0xffd87b29);
        defaultColors.put(key_avatar_nameInMessageViolet, 0xff4e92cc);
        defaultColors.put(key_avatar_nameInMessageGreen, 0xff50b232);
        defaultColors.put(key_avatar_nameInMessageCyan, 0xff379eb8);
        defaultColors.put(key_avatar_nameInMessageBlue, 0xff4e92cc);
        defaultColors.put(key_avatar_nameInMessagePink, 0xff4e92cc);

        defaultColors.put(key_actionBarDefault, 0xff527da3);
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

        defaultColors.put(key_actionBarBrowser, 0xffffffff);

        defaultColors.put(key_actionBarDefaultArchived, 0xff6f7a87);
        defaultColors.put(key_actionBarDefaultArchivedSelector, 0xff5e6772);
        defaultColors.put(key_actionBarDefaultArchivedIcon, 0xffffffff);
        defaultColors.put(key_actionBarDefaultArchivedTitle, 0xffffffff);
        defaultColors.put(key_actionBarDefaultArchivedSearch, 0xffffffff);
        defaultColors.put(key_actionBarDefaultArchivedSearchPlaceholder, 0x88ffffff);

        defaultColors.put(key_chats_onlineCircle, 0xff4bcb1c);
        defaultColors.put(key_chats_unreadCounter, 0xff4ecc5e);
        defaultColors.put(key_chats_unreadCounterMuted, 0xffc6c9cc);
        defaultColors.put(key_chats_unreadCounterText, 0xffffffff);
        defaultColors.put(key_chats_archiveBackground, 0xff66a9e0);
        defaultColors.put(key_chats_archivePinBackground, 0xff9faab3);
        defaultColors.put(key_chats_archiveIcon, 0xffffffff);
        defaultColors.put(key_chats_archiveText, 0xffffffff);
        defaultColors.put(key_chats_name, 0xff222222);
        defaultColors.put(key_chats_nameArchived, 0xff525252);
        defaultColors.put(key_chats_secretName, 0xff00a60e);
        defaultColors.put(key_chats_secretIcon, 0xff19b126);
        defaultColors.put(key_chats_nameIcon, 0xff242424);
        defaultColors.put(key_chats_pinnedIcon, 0xffa8a8a8);
        defaultColors.put(key_chats_message, 0xff8b8d8f);
        defaultColors.put(key_chats_messageArchived, 0xff919191);
        defaultColors.put(key_chats_message_threeLines, 0xff8e9091);
        defaultColors.put(key_chats_draft, 0xffdd4b39);
        defaultColors.put(key_chats_nameMessage, 0xff3c7eb0);
        defaultColors.put(key_chats_nameMessageArchived, 0xff8b8d8f);
        defaultColors.put(key_chats_nameMessage_threeLines, 0xff424449);
        defaultColors.put(key_chats_nameMessageArchived_threeLines, 0xff5e5e5e);
        defaultColors.put(key_chats_attachMessage, 0xff3c7eb0);
        defaultColors.put(key_chats_actionMessage, 0xff3c7eb0);
        defaultColors.put(key_chats_date, 0xff95999C);
        defaultColors.put(key_chats_pinnedOverlay, 0x08000000);
        defaultColors.put(key_chats_tabletSelectedOverlay, 0x0f000000);
        defaultColors.put(key_chats_sentCheck, 0xff46aa36);
        defaultColors.put(key_chats_sentReadCheck, 0xff46aa36);
        defaultColors.put(key_chats_sentClock, 0xff75bd5e);
        defaultColors.put(key_chats_sentError, 0xffd55252);
        defaultColors.put(key_chats_sentErrorIcon, 0xffffffff);
        defaultColors.put(key_chats_verifiedBackground, 0xff33a8e6);
        defaultColors.put(key_chats_verifiedCheck, 0xffffffff);
        defaultColors.put(key_chats_muteIcon, 0xffbdc1c4);
        defaultColors.put(key_chats_mentionIcon, 0xffffffff);
        defaultColors.put(key_chats_menuBackground, 0xffffffff);
        defaultColors.put(key_chats_menuItemText, 0xff444444);
        defaultColors.put(key_chats_menuItemCheck, 0xff598fba);
        defaultColors.put(key_chats_menuItemIcon, 0xff889198);
        defaultColors.put(key_chats_menuName, 0xffffffff);
        defaultColors.put(key_chats_menuPhone, 0xffffffff);
        defaultColors.put(key_chats_menuPhoneCats, 0xffc2e5ff);
        defaultColors.put(key_chats_menuCloud, 0xffffffff);
        defaultColors.put(key_chats_menuCloudBackgroundCats, 0xff427ba9);
        defaultColors.put(key_chats_actionIcon, 0xffffffff);
        defaultColors.put(key_chats_actionBackground, 0xff65a9e0);
        defaultColors.put(key_chats_actionPressedBackground, 0xff569dd6);
        defaultColors.put(key_chats_actionUnreadIcon, 0xff737373);
        defaultColors.put(key_chats_actionUnreadBackground, 0xffffffff);
        defaultColors.put(key_chats_actionUnreadPressedBackground, 0xfff2f2f2);
        defaultColors.put(key_chats_menuTopBackgroundCats, 0xff598fba);
        defaultColors.put(key_chats_archivePullDownBackground, 0xffc6c9cc);
        defaultColors.put(key_chats_archivePullDownBackgroundActive, 0xff66a9e0);

        defaultColors.put(key_chat_attachMediaBanBackground, 0xff464646);
        defaultColors.put(key_chat_attachMediaBanText, 0xffffffff);
        defaultColors.put(key_chat_attachCheckBoxCheck, 0xffffffff);
        defaultColors.put(key_chat_attachCheckBoxBackground, 0xff39b2f7);
        defaultColors.put(key_chat_attachPhotoBackground, 0x0c000000);
        defaultColors.put(key_chat_attachActiveTab, 0xff33a7f5);
        defaultColors.put(key_chat_attachUnactiveTab, 0xff92999e);
        defaultColors.put(key_chat_attachPermissionImage, 0xff333333);
        defaultColors.put(key_chat_attachPermissionMark, 0xffe25050);
        defaultColors.put(key_chat_attachPermissionText, 0xff6f777a);
        defaultColors.put(key_chat_attachEmptyImage, 0xffcccccc);

        defaultColors.put(key_chat_attachGalleryBackground, 0xff459df5);
        defaultColors.put(key_chat_attachGalleryIcon, 0xffffffff);
        defaultColors.put(key_chat_attachAudioBackground, 0xffeb6060);
        defaultColors.put(key_chat_attachAudioIcon, 0xffffffff);
        defaultColors.put(key_chat_attachFileBackground, 0xff34b9f1);
        defaultColors.put(key_chat_attachFileIcon, 0xffffffff);
        defaultColors.put(key_chat_attachContactBackground, 0xfff2c04b);
        defaultColors.put(key_chat_attachContactIcon, 0xffffffff);
        defaultColors.put(key_chat_attachLocationBackground, 0xff60c255);
        defaultColors.put(key_chat_attachLocationIcon, 0xffffffff);
        defaultColors.put(key_chat_attachPollBackground, 0xfff2c04b);
        defaultColors.put(key_chat_attachPollIcon, 0xffffffff);

        defaultColors.put(key_chat_inPollCorrectAnswer, 0xff60c255);
        defaultColors.put(key_chat_outPollCorrectAnswer, 0xff60c255);
        defaultColors.put(key_chat_inPollWrongAnswer, 0xffeb6060);
        defaultColors.put(key_chat_outPollWrongAnswer, 0xffeb6060);

        defaultColors.put(key_chat_status, 0xffd5e8f7);
        defaultColors.put(key_chat_inGreenCall, 0xff00c853);
        defaultColors.put(key_chat_inRedCall, 0xffff4848);
        defaultColors.put(key_chat_outGreenCall, 0xff00c853);
        defaultColors.put(key_chat_shareBackground, 0x66728fa6);
        defaultColors.put(key_chat_shareBackgroundSelected, 0x99728fa6);
        defaultColors.put(key_chat_lockIcon, 0xffffffff);
        defaultColors.put(key_chat_muteIcon, 0xffb1cce3);
        defaultColors.put(key_chat_inBubble, 0xffffffff);
        defaultColors.put(key_chat_inBubbleSelected, 0xffecf7fd);
        defaultColors.put(key_chat_inBubbleShadow, 0xff1d3753);
        defaultColors.put(key_chat_outBubble, 0xffefffde);
        defaultColors.put(key_chat_outBubbleGradientSelectedOverlay, 0x14000000);
        defaultColors.put(key_chat_outBubbleSelected, 0xffd9f7c5);
        defaultColors.put(key_chat_outBubbleShadow, 0xff1e750c);
        defaultColors.put(key_chat_inMediaIcon, 0xffffffff);
        defaultColors.put(key_chat_inMediaIconSelected, 0xffeff8fe);
        defaultColors.put(key_chat_outMediaIcon, 0xffefffde);
        defaultColors.put(key_chat_outMediaIconSelected, 0xffe1f8cf);
        defaultColors.put(key_chat_messageTextIn, 0xff000000);
        defaultColors.put(key_chat_messageTextOut, 0xff000000);
        defaultColors.put(key_chat_messageLinkIn, 0xff2678b6);
        defaultColors.put(key_chat_messageLinkOut, 0xff2678b6);
        defaultColors.put(key_chat_serviceText, 0xffffffff);
        defaultColors.put(key_chat_serviceLink, 0xffffffff);
        defaultColors.put(key_chat_serviceIcon, 0xffffffff);
        defaultColors.put(key_chat_mediaTimeBackground, 0x66000000);
        defaultColors.put(key_chat_outSentCheck, 0xff5db050);
        defaultColors.put(key_chat_outSentCheckSelected, 0xff5db050);
        defaultColors.put(key_chat_outSentCheckRead, 0xff5db050);
        defaultColors.put(key_chat_outSentCheckReadSelected, 0xff5db050);
        defaultColors.put(key_chat_outSentClock, 0xff75bd5e);
        defaultColors.put(key_chat_outSentClockSelected, 0xff75bd5e);
        defaultColors.put(key_chat_inSentClock, 0xffa1aab3);
        defaultColors.put(key_chat_inSentClockSelected, 0xff93bdca);
        defaultColors.put(key_chat_mediaSentCheck, 0xffffffff);
        defaultColors.put(key_chat_mediaSentClock, 0xffffffff);
        defaultColors.put(key_chat_inViews, 0xffa1aab3);
        defaultColors.put(key_chat_inViewsSelected, 0xff93bdca);
        defaultColors.put(key_chat_outViews, 0xff6eb257);
        defaultColors.put(key_chat_outViewsSelected, 0xff6eb257);
        defaultColors.put(key_chat_mediaViews, 0xffffffff);
        defaultColors.put(key_chat_inMenu, 0xffb6bdc5);
        defaultColors.put(key_chat_inMenuSelected, 0xff98c1ce);
        defaultColors.put(key_chat_outMenu, 0xff91ce7e);
        defaultColors.put(key_chat_outMenuSelected, 0xff91ce7e);
        defaultColors.put(key_chat_mediaMenu, 0xffffffff);
        defaultColors.put(key_chat_outInstant, 0xff55ab4f);
        defaultColors.put(key_chat_outInstantSelected, 0xff489943);
        defaultColors.put(key_chat_inInstant, 0xff3a8ccf);
        defaultColors.put(key_chat_inInstantSelected, 0xff3079b5);
        defaultColors.put(key_chat_sentError, 0xffdb3535);
        defaultColors.put(key_chat_sentErrorIcon, 0xffffffff);
        defaultColors.put(key_chat_selectedBackground, 0x280a90f0);
        defaultColors.put(key_chat_previewDurationText, 0xffffffff);
        defaultColors.put(key_chat_previewGameText, 0xffffffff);
        defaultColors.put(key_chat_inPreviewInstantText, 0xff3a8ccf);
        defaultColors.put(key_chat_outPreviewInstantText, 0xff55ab4f);
        defaultColors.put(key_chat_inPreviewInstantSelectedText, 0xff3079b5);
        defaultColors.put(key_chat_outPreviewInstantSelectedText, 0xff489943);
        defaultColors.put(key_chat_secretTimeText, 0xffe4e2e0);
        defaultColors.put(key_chat_stickerNameText, 0xffffffff);
        defaultColors.put(key_chat_botButtonText, 0xffffffff);
        defaultColors.put(key_chat_botProgress, 0xffffffff);
        defaultColors.put(key_chat_inForwardedNameText, 0xff3886c7);
        defaultColors.put(key_chat_outForwardedNameText, 0xff55ab4f);
        defaultColors.put(key_chat_inViaBotNameText, 0xff3a8ccf);
        defaultColors.put(key_chat_outViaBotNameText, 0xff55ab4f);
        defaultColors.put(key_chat_stickerViaBotNameText, 0xffffffff);
        defaultColors.put(key_chat_inReplyLine, 0xff599fd8);
        defaultColors.put(key_chat_outReplyLine, 0xff6eb969);
        defaultColors.put(key_chat_stickerReplyLine, 0xffffffff);
        defaultColors.put(key_chat_inReplyNameText, 0xff3a8ccf);
        defaultColors.put(key_chat_outReplyNameText, 0xff55ab4f);
        defaultColors.put(key_chat_stickerReplyNameText, 0xffffffff);
        defaultColors.put(key_chat_inReplyMessageText, 0xff000000);
        defaultColors.put(key_chat_outReplyMessageText, 0xff000000);
        defaultColors.put(key_chat_inReplyMediaMessageText, 0xffa1aab3);
        defaultColors.put(key_chat_outReplyMediaMessageText, 0xff65b05b);
        defaultColors.put(key_chat_inReplyMediaMessageSelectedText, 0xff89b4c1);
        defaultColors.put(key_chat_outReplyMediaMessageSelectedText, 0xff65b05b);
        defaultColors.put(key_chat_stickerReplyMessageText, 0xffffffff);
        defaultColors.put(key_chat_inPreviewLine, 0xff70b4e8);
        defaultColors.put(key_chat_outPreviewLine, 0xff88c97b);
        defaultColors.put(key_chat_inSiteNameText, 0xff3a8ccf);
        defaultColors.put(key_chat_outSiteNameText, 0xff55ab4f);
        defaultColors.put(key_chat_inContactNameText, 0xff4e9ad4);
        defaultColors.put(key_chat_outContactNameText, 0xff55ab4f);
        defaultColors.put(key_chat_inContactPhoneText, 0xff2f3438);
        defaultColors.put(key_chat_inContactPhoneSelectedText, 0xff2f3438);
        defaultColors.put(key_chat_outContactPhoneText, 0xff354234);
        defaultColors.put(key_chat_outContactPhoneSelectedText, 0xff354234);
        defaultColors.put(key_chat_mediaProgress, 0xffffffff);
        defaultColors.put(key_chat_inAudioProgress, 0xffffffff);
        defaultColors.put(key_chat_outAudioProgress, 0xffefffde);
        defaultColors.put(key_chat_inAudioSelectedProgress, 0xffeff8fe);
        defaultColors.put(key_chat_outAudioSelectedProgress, 0xffe1f8cf);
        defaultColors.put(key_chat_mediaTimeText, 0xffffffff);
        defaultColors.put(key_chat_inTimeText, 0xffa1aab3);
        defaultColors.put(key_chat_outTimeText, 0xff70b15c);
        defaultColors.put(key_chat_adminText, 0xffc0c6cb);
        defaultColors.put(key_chat_adminSelectedText, 0xff89b4c1);
        defaultColors.put(key_chat_inTimeSelectedText, 0xff89b4c1);
        defaultColors.put(key_chat_outTimeSelectedText, 0xff70b15c);
        defaultColors.put(key_chat_inAudioPerformerText, 0xff2f3438);
        defaultColors.put(key_chat_inAudioPerformerSelectedText, 0xff2f3438);
        defaultColors.put(key_chat_outAudioPerformerText, 0xff354234);
        defaultColors.put(key_chat_outAudioPerformerSelectedText, 0xff354234);
        defaultColors.put(key_chat_inAudioTitleText, 0xff4e9ad4);
        defaultColors.put(key_chat_outAudioTitleText, 0xff55ab4f);
        defaultColors.put(key_chat_inAudioDurationText, 0xffa1aab3);
        defaultColors.put(key_chat_outAudioDurationText, 0xff65b05b);
        defaultColors.put(key_chat_inAudioDurationSelectedText, 0xff89b4c1);
        defaultColors.put(key_chat_outAudioDurationSelectedText, 0xff65b05b);
        defaultColors.put(key_chat_inAudioSeekbar, 0xffe4eaf0);
        defaultColors.put(key_chat_inAudioCacheSeekbar, 0x3fe4eaf0);
        defaultColors.put(key_chat_outAudioSeekbar, 0xffbbe3ac);
        defaultColors.put(key_chat_outAudioCacheSeekbar, 0x3fbbe3ac);
        defaultColors.put(key_chat_inAudioSeekbarSelected, 0xffbcdee8);
        defaultColors.put(key_chat_outAudioSeekbarSelected, 0xffa9dd96);
        defaultColors.put(key_chat_inAudioSeekbarFill, 0xff72b5e8);
        defaultColors.put(key_chat_outAudioSeekbarFill, 0xff78c272);
        defaultColors.put(key_chat_inVoiceSeekbar, 0xffdee5eb);
        defaultColors.put(key_chat_outVoiceSeekbar, 0xffbbe3ac);
        defaultColors.put(key_chat_inVoiceSeekbarSelected, 0xffbcdee8);
        defaultColors.put(key_chat_outVoiceSeekbarSelected, 0xffa9dd96);
        defaultColors.put(key_chat_inVoiceSeekbarFill, 0xff72b5e8);
        defaultColors.put(key_chat_outVoiceSeekbarFill, 0xff78c272);
        defaultColors.put(key_chat_inFileProgress, 0xffebf0f5);
        defaultColors.put(key_chat_outFileProgress, 0xffdaf5c3);
        defaultColors.put(key_chat_inFileProgressSelected, 0xffcbeaf6);
        defaultColors.put(key_chat_outFileProgressSelected, 0xffc5eca7);
        defaultColors.put(key_chat_inFileNameText, 0xff4e9ad4);
        defaultColors.put(key_chat_outFileNameText, 0xff55ab4f);
        defaultColors.put(key_chat_inFileInfoText, 0xffa1aab3);
        defaultColors.put(key_chat_outFileInfoText, 0xff65b05b);
        defaultColors.put(key_chat_inFileInfoSelectedText, 0xff89b4c1);
        defaultColors.put(key_chat_outFileInfoSelectedText, 0xff65b05b);
        defaultColors.put(key_chat_inFileBackground, 0xffebf0f5);
        defaultColors.put(key_chat_outFileBackground, 0xffdaf5c3);
        defaultColors.put(key_chat_inFileBackgroundSelected, 0xffcbeaf6);
        defaultColors.put(key_chat_outFileBackgroundSelected, 0xffc5eca7);
        defaultColors.put(key_chat_inVenueInfoText, 0xffa1aab3);
        defaultColors.put(key_chat_outVenueInfoText, 0xff65b05b);
        defaultColors.put(key_chat_inVenueInfoSelectedText, 0xff89b4c1);
        defaultColors.put(key_chat_outVenueInfoSelectedText, 0xff65b05b);
        defaultColors.put(key_chat_mediaInfoText, 0xffffffff);
        defaultColors.put(key_chat_linkSelectBackground, 0x3362a9e3);
        defaultColors.put(key_chat_textSelectBackground, 0x6662a9e3);
        defaultColors.put(key_chat_emojiPanelBackground, 0xfff0f2f5);
        defaultColors.put(key_chat_emojiPanelBadgeBackground, 0xff4da6ea);
        defaultColors.put(key_chat_emojiPanelBadgeText, 0xffffffff);
        defaultColors.put(key_chat_emojiSearchBackground, 0xffe5e9ee);
        defaultColors.put(key_chat_emojiSearchIcon, 0xff94a1af);
        defaultColors.put(key_chat_emojiPanelShadowLine, 0x12000000);
        defaultColors.put(key_chat_emojiPanelEmptyText, 0xff949ba1);
        defaultColors.put(key_chat_emojiPanelIcon, 0xff9da4ab);
        defaultColors.put(key_chat_emojiBottomPanelIcon, 0xff8c9197);
        defaultColors.put(key_chat_emojiPanelIconSelected, 0xff2b97e2);
        defaultColors.put(key_chat_emojiPanelStickerPackSelector, 0xffe2e5e7);
        defaultColors.put(key_chat_emojiPanelStickerPackSelectorLine, 0xff56abf0);
        defaultColors.put(key_chat_emojiPanelBackspace, 0xff8c9197);
        defaultColors.put(key_chat_emojiPanelMasksIcon, 0xffffffff);
        defaultColors.put(key_chat_emojiPanelMasksIconSelected, 0xff62bfe8);
        defaultColors.put(key_chat_emojiPanelTrendingTitle, 0xff222222);
        defaultColors.put(key_chat_emojiPanelStickerSetName, 0xff828b94);
        defaultColors.put(key_chat_emojiPanelStickerSetNameHighlight, 0xff278ddb);
        defaultColors.put(key_chat_emojiPanelStickerSetNameIcon, 0xffb1b6bc);
        defaultColors.put(key_chat_emojiPanelTrendingDescription, 0xff8a8a8a);
        defaultColors.put(key_chat_botKeyboardButtonText, 0xff36474f);
        defaultColors.put(key_chat_botKeyboardButtonBackground, 0xffe4e7e9);
        defaultColors.put(key_chat_botKeyboardButtonBackgroundPressed, 0xffccd1d4);
        defaultColors.put(key_chat_unreadMessagesStartArrowIcon, 0xffa2b5c7);
        defaultColors.put(key_chat_unreadMessagesStartText, 0xff5695cc);
        defaultColors.put(key_chat_unreadMessagesStartBackground, 0xffffffff);
        defaultColors.put(key_chat_inFileIcon, 0xffa2b5c7);
        defaultColors.put(key_chat_inFileSelectedIcon, 0xff87b6c5);
        defaultColors.put(key_chat_outFileIcon, 0xff85bf78);
        defaultColors.put(key_chat_outFileSelectedIcon, 0xff85bf78);
        defaultColors.put(key_chat_inLocationBackground, 0xffebf0f5);
        defaultColors.put(key_chat_inLocationIcon, 0xffa2b5c7);
        defaultColors.put(key_chat_outLocationBackground, 0xffdaf5c3);
        defaultColors.put(key_chat_outLocationIcon, 0xff87bf78);
        defaultColors.put(key_chat_inContactBackground, 0xff72b5e8);
        defaultColors.put(key_chat_inContactIcon, 0xffffffff);
        defaultColors.put(key_chat_outContactBackground, 0xff78c272);
        defaultColors.put(key_chat_outContactIcon, 0xffefffde);
        defaultColors.put(key_chat_outBroadcast, 0xff46aa36);
        defaultColors.put(key_chat_mediaBroadcast, 0xffffffff);
        defaultColors.put(key_chat_searchPanelIcons, 0xff676a6f);
        defaultColors.put(key_chat_searchPanelText, 0xff676a6f);
        defaultColors.put(key_chat_secretChatStatusText, 0xff7f7f7f);
        defaultColors.put(key_chat_fieldOverlayText, 0xff3a8ccf);
        defaultColors.put(key_chat_stickersHintPanel, 0xffffffff);
        defaultColors.put(key_chat_replyPanelIcons, 0xff57a8e6);
        defaultColors.put(key_chat_replyPanelClose, 0xff8e959b);
        defaultColors.put(key_chat_replyPanelName, 0xff3a8ccf);
        defaultColors.put(key_chat_replyPanelMessage, 0xff222222);
        defaultColors.put(key_chat_replyPanelLine, 0xffe8e8e8);
        defaultColors.put(key_chat_messagePanelBackground, 0xffffffff);
        defaultColors.put(key_chat_messagePanelText, 0xff000000);
        defaultColors.put(key_chat_messagePanelHint, 0xffa4acb3);
        defaultColors.put(key_chat_messagePanelCursor, 0xff54a1db);
        defaultColors.put(key_chat_messagePanelShadow, 0xff000000);
        defaultColors.put(key_chat_messagePanelIcons, 0xff8e959b);
        defaultColors.put(key_chat_messagePanelVideoFrame, 0xff4badf7);
        defaultColors.put(key_chat_recordedVoicePlayPause, 0xffffffff);
        defaultColors.put(key_chat_recordedVoicePlayPausePressed, 0xffd9eafb);
        defaultColors.put(key_chat_recordedVoiceDot, 0xffda564d);
        defaultColors.put(key_chat_recordedVoiceBackground, 0xff67b2eb);
        defaultColors.put(key_chat_recordedVoiceProgress, 0xffa2cef8);
        defaultColors.put(key_chat_recordedVoiceProgressInner, 0xffffffff);
        defaultColors.put(key_chat_recordVoiceCancel, 0xff999999);
        defaultColors.put(key_chat_messagePanelSend, 0xff62b0eb);
        defaultColors.put(key_chat_messagePanelVoiceLock, 0xffa4a4a4);
        defaultColors.put(key_chat_messagePanelVoiceLockBackground, 0xffffffff);
        defaultColors.put(key_chat_messagePanelVoiceLockShadow, 0xff000000);
        defaultColors.put(key_chat_recordTime, 0xff4d4c4b);
        defaultColors.put(key_chat_emojiPanelNewTrending, 0xff4da6ea);
        defaultColors.put(key_chat_gifSaveHintText, 0xffffffff);
        defaultColors.put(key_chat_gifSaveHintBackground, 0xcc111111);
        defaultColors.put(key_chat_goDownButton, 0xffffffff);
        defaultColors.put(key_chat_goDownButtonShadow, 0xff000000);
        defaultColors.put(key_chat_goDownButtonIcon, 0xff8e959b);
        defaultColors.put(key_chat_goDownButtonCounter, 0xffffffff);
        defaultColors.put(key_chat_goDownButtonCounterBackground, 0xff4da2e8);
        defaultColors.put(key_chat_messagePanelCancelInlineBot, 0xffadadad);
        defaultColors.put(key_chat_messagePanelVoicePressed, 0xffffffff);
        defaultColors.put(key_chat_messagePanelVoiceBackground, 0xff5795cc);
        defaultColors.put(key_chat_messagePanelVoiceShadow, 0x0d000000);
        defaultColors.put(key_chat_messagePanelVoiceDelete, 0xff737373);
        defaultColors.put(key_chat_messagePanelVoiceDuration, 0xffffffff);
        defaultColors.put(key_chat_inlineResultIcon, 0xff5795cc);
        defaultColors.put(key_chat_topPanelBackground, 0xffffffff);
        defaultColors.put(key_chat_topPanelClose, 0xff8c959a);
        defaultColors.put(key_chat_topPanelLine, 0xff6c9fd2);
        defaultColors.put(key_chat_topPanelTitle, 0xff3a8ccf);
        defaultColors.put(key_chat_topPanelMessage, 0xff999999);
        defaultColors.put(key_chat_reportSpam, 0xffcf5957);
        defaultColors.put(key_chat_addContact, 0xff4a82b5);
        defaultColors.put(key_chat_inLoader, 0xff72b5e8);
        defaultColors.put(key_chat_inLoaderSelected, 0xff65abe0);
        defaultColors.put(key_chat_outLoader, 0xff78c272);
        defaultColors.put(key_chat_outLoaderSelected, 0xff6ab564);
        defaultColors.put(key_chat_inLoaderPhoto, 0xffa2b8c8);
        defaultColors.put(key_chat_inLoaderPhotoSelected, 0xffa2b5c7);
        defaultColors.put(key_chat_inLoaderPhotoIcon, 0xfffcfcfc);
        defaultColors.put(key_chat_inLoaderPhotoIconSelected, 0xffebf0f5);
        defaultColors.put(key_chat_outLoaderPhoto, 0xff85bf78);
        defaultColors.put(key_chat_outLoaderPhotoSelected, 0xff7db870);
        defaultColors.put(key_chat_outLoaderPhotoIcon, 0xffdaf5c3);
        defaultColors.put(key_chat_outLoaderPhotoIconSelected, 0xffc0e8a4);
        defaultColors.put(key_chat_mediaLoaderPhoto, 0x66000000);
        defaultColors.put(key_chat_mediaLoaderPhotoSelected, 0x7f000000);
        defaultColors.put(key_chat_mediaLoaderPhotoIcon, 0xffffffff);
        defaultColors.put(key_chat_mediaLoaderPhotoIconSelected, 0xffd9d9d9);
        defaultColors.put(key_chat_secretTimerBackground, 0xcc3e648e);
        defaultColors.put(key_chat_secretTimerText, 0xffffffff);

        defaultColors.put(key_profile_creatorIcon, 0xff3a95d5);
        defaultColors.put(key_profile_actionIcon, 0xff81868a);
        defaultColors.put(key_profile_actionBackground, 0xffffffff);
        defaultColors.put(key_profile_actionPressedBackground, 0xfff2f2f2);
        defaultColors.put(key_profile_verifiedBackground, 0xffb2d6f8);
        defaultColors.put(key_profile_verifiedCheck, 0xff4983b8);
        defaultColors.put(key_profile_title, 0xffffffff);
        defaultColors.put(key_profile_status, 0xffd7eafa);

        defaultColors.put(key_player_actionBar, 0xffffffff);
        defaultColors.put(key_player_actionBarSelector, 0x0f000000);
        defaultColors.put(key_player_actionBarTitle, 0xff2f3438);
        defaultColors.put(key_player_actionBarTop, 0x99000000);
        defaultColors.put(key_player_actionBarSubtitle, 0xff8a8a8a);
        defaultColors.put(key_player_actionBarItems, 0xff8a8a8a);
        defaultColors.put(key_player_background, 0xffffffff);
        defaultColors.put(key_player_time, 0xff8c9296);
        defaultColors.put(key_player_progressBackground, 0xffe9eff5);
        defaultColors.put(key_player_progressCachedBackground, 0xffe9eff5);
        defaultColors.put(key_player_progress, 0xff4b9fe3);
        defaultColors.put(key_player_placeholder, 0xffa8a8a8);
        defaultColors.put(key_player_placeholderBackground, 0xfff0f0f0);
        defaultColors.put(key_player_button, 0xff333333);
        defaultColors.put(key_player_buttonActive, 0xff4ca8ea);

        defaultColors.put(key_sheet_scrollUp, 0xffe1e4e8);
        defaultColors.put(key_sheet_other, 0xffc9cdd3);

        defaultColors.put(key_files_folderIcon, 0xffffffff);
        defaultColors.put(key_files_folderIconBackground, 0xff5dafeb);
        defaultColors.put(key_files_iconText, 0xffffffff);

        defaultColors.put(key_sessions_devicesImage, 0xff969696);

        defaultColors.put(key_passport_authorizeBackground, 0xff45abef);
        defaultColors.put(key_passport_authorizeBackgroundSelected, 0xff409ddb);
        defaultColors.put(key_passport_authorizeText, 0xffffffff);

        defaultColors.put(key_location_sendLocationBackground, 0xff469df6);
        defaultColors.put(key_location_sendLocationIcon, 0xffffffff);
        defaultColors.put(key_location_sendLocationText, 0xff1c8ad8);
        defaultColors.put(key_location_sendLiveLocationBackground, 0xff4fc244);
        defaultColors.put(key_location_sendLiveLocationIcon, 0xffffffff);
        defaultColors.put(key_location_sendLiveLocationText, 0xff36ab24);
        defaultColors.put(key_location_liveLocationProgress, 0xff359fe5);
        defaultColors.put(key_location_placeLocationBackground, 0xff4ca8ea);
        defaultColors.put(key_location_actionIcon, 0xff3a4045);
        defaultColors.put(key_location_actionActiveIcon, 0xff4290e6);
        defaultColors.put(key_location_actionBackground, 0xffffffff);
        defaultColors.put(key_location_actionPressedBackground, 0xfff2f2f2);

        defaultColors.put(key_dialog_liveLocationProgress, 0xff359fe5);

        defaultColors.put(key_calls_callReceivedGreenIcon, 0xff00c853);
        defaultColors.put(key_calls_callReceivedRedIcon, 0xffff4848);

        defaultColors.put(key_featuredStickers_addedIcon, 0xff50a8eb);
        defaultColors.put(key_featuredStickers_buttonProgress, 0xffffffff);
        defaultColors.put(key_featuredStickers_addButton, 0xff50a8eb);
        defaultColors.put(key_featuredStickers_addButtonPressed, 0xff439bde);
        defaultColors.put(key_featuredStickers_removeButtonText, 0xff5093d3);
        defaultColors.put(key_featuredStickers_buttonText, 0xffffffff);
        defaultColors.put(key_featuredStickers_unread, 0xff4da6ea);

        defaultColors.put(key_inappPlayerPerformer, 0xff2f3438);
        defaultColors.put(key_inappPlayerTitle, 0xff2f3438);
        defaultColors.put(key_inappPlayerBackground, 0xffffffff);
        defaultColors.put(key_inappPlayerPlayPause, 0xff62b0eb);
        defaultColors.put(key_inappPlayerClose, 0xffa8a8a8);

        defaultColors.put(key_returnToCallBackground, 0xff44a1e3);
        defaultColors.put(key_returnToCallText, 0xffffffff);

        defaultColors.put(key_sharedMedia_startStopLoadIcon, 0xff36a2ee);
        defaultColors.put(key_sharedMedia_linkPlaceholder, 0xfff0f3f5);
        defaultColors.put(key_sharedMedia_linkPlaceholderText, 0xffb7bec3);
        defaultColors.put(key_sharedMedia_photoPlaceholder, 0xffedf3f7);
        defaultColors.put(key_sharedMedia_actionMode, 0xff4687b3);

        defaultColors.put(key_checkbox, 0xff5ec245);
        defaultColors.put(key_checkboxCheck, 0xffffffff);
        defaultColors.put(key_checkboxDisabled, 0xffb0b9c2);

        defaultColors.put(key_stickers_menu, 0xffb6bdc5);
        defaultColors.put(key_stickers_menuSelector, 0x0f000000);

        defaultColors.put(key_changephoneinfo_image, 0xffb8bfc5);
        defaultColors.put(key_changephoneinfo_image2, 0xff50a7ea);

        defaultColors.put(key_groupcreate_hintText, 0xffa1aab3);
        defaultColors.put(key_groupcreate_cursor, 0xff52a3db);
        defaultColors.put(key_groupcreate_sectionShadow, 0xff000000);
        defaultColors.put(key_groupcreate_sectionText, 0xff7c8288);
        defaultColors.put(key_groupcreate_spanText, 0xff222222);
        defaultColors.put(key_groupcreate_spanBackground, 0xfff2f2f2);
        defaultColors.put(key_groupcreate_spanDelete, 0xffffffff);

        defaultColors.put(key_contacts_inviteBackground, 0xff55be61);
        defaultColors.put(key_contacts_inviteText, 0xffffffff);

        defaultColors.put(key_login_progressInner, 0xffe1eaf2);
        defaultColors.put(key_login_progressOuter, 0xff62a0d0);

        defaultColors.put(key_musicPicker_checkbox, 0xff29b6f7);
        defaultColors.put(key_musicPicker_checkboxCheck, 0xffffffff);
        defaultColors.put(key_musicPicker_buttonBackground, 0xff5cafea);
        defaultColors.put(key_musicPicker_buttonIcon, 0xffffffff);
        defaultColors.put(key_picker_enabledButton, 0xff19a7e8);
        defaultColors.put(key_picker_disabledButton, 0xff999999);
        defaultColors.put(key_picker_badge, 0xff29b6f7);
        defaultColors.put(key_picker_badgeText, 0xffffffff);

        defaultColors.put(key_chat_botSwitchToInlineText, 0xff4391cc);

        defaultColors.put(key_undo_background, 0xea272f38);
        defaultColors.put(key_undo_cancelColor, 0xff85caff);
        defaultColors.put(key_undo_infoColor, 0xffffffff);

        defaultColors.put(key_wallet_blackBackground, 0xff000000);
        defaultColors.put(key_wallet_graySettingsBackground, 0xfff0f0f0);
        defaultColors.put(key_wallet_grayBackground, 0xff292929);
        defaultColors.put(key_wallet_whiteBackground, 0xffffffff);
        defaultColors.put(key_wallet_blackBackgroundSelector, 0x40ffffff);
        defaultColors.put(key_wallet_whiteText, 0xffffffff);
        defaultColors.put(key_wallet_blackText, 0xff222222);
        defaultColors.put(key_wallet_statusText, 0xff808080);
        defaultColors.put(key_wallet_grayText, 0xff777777);
        defaultColors.put(key_wallet_grayText2, 0xff666666);
        defaultColors.put(key_wallet_greenText, 0xff37a818);
        defaultColors.put(key_wallet_redText, 0xffdb4040);
        defaultColors.put(key_wallet_dateText, 0xff999999);
        defaultColors.put(key_wallet_commentText, 0xff999999);
        defaultColors.put(key_wallet_releaseBackground, 0xff307cbb);
        defaultColors.put(key_wallet_pullBackground, 0xff212121);
        defaultColors.put(key_wallet_buttonBackground, 0xff47a1e6);
        defaultColors.put(key_wallet_buttonPressedBackground, 0xff2b8cd6);
        defaultColors.put(key_wallet_buttonText, 0xffffffff);
        defaultColors.put(key_wallet_addressConfirmBackground, 0x0d000000);
        defaultColors.put(key_chat_outTextSelectionHighlight, 0x2E3F9923);
        defaultColors.put(key_chat_inTextSelectionHighlight, 0x5062A9E3);
        defaultColors.put(key_chat_TextSelectionCursor, 0xFF419FE8);
    }

    private static Method StateListDrawable_getStateDrawableMethod;
    private static Field BitmapDrawable_mColorFilter;


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

    public static Drawable createEmojiIconSelectorDrawable(Context context, int resource, int defaultColor, int pressedColor) {
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
        stateListDrawable.setEnterFadeDuration(1);
        stateListDrawable.setExitFadeDuration(200);
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, pressedDrawable);
        stateListDrawable.addState(new int[]{}, defaultDrawable);
        return stateListDrawable;
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
