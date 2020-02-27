package com.ronaksoftware.musicchi.ui.cells;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ronaksoftware.musicchi.models.SearchHistory;
import com.ronaksoftware.musicchi.ui.presenter.Theme;
import com.ronaksoftware.musicchi.utils.DisplayUtility;
import com.ronaksoftware.musicchi.utils.LayoutHelper;

public class SearchHistoryCell extends FrameLayout {
    private TextView artistTextView;
    private TextView titleTextView;
    private TextView releaseTextView;

    public SearchHistoryCell(@NonNull Context context) {
        super(context);
        setBackground(Theme.getRoundRectSelectorDrawable(Color.WHITE, Color.parseColor("#11151E"), DisplayUtility.dp(12)));

        artistTextView = new TextView(context);
        artistTextView.setTextColor(Color.WHITE);
        artistTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        artistTextView.setSingleLine(true);
        artistTextView.setEllipsize(TextUtils.TruncateAt.END);
        addView(artistTextView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.LEFT | Gravity.TOP, 12, 12, 12, 0));

        titleTextView = new TextView(context);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        titleTextView.setTextColor(Color.WHITE);
        titleTextView.setSingleLine(true);
        titleTextView.setEllipsize(TextUtils.TruncateAt.END);
        addView(titleTextView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.BOTTOM | Gravity.LEFT, 12, 0, 123 + 12, 12));

        releaseTextView = new TextView(context);
        releaseTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        releaseTextView.setTextColor(Color.WHITE);
        releaseTextView.setSingleLine(true);
        releaseTextView.setEllipsize(TextUtils.TruncateAt.END);
        addView(releaseTextView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.RIGHT | Gravity.BOTTOM, 40, 0, 12, 12));
    }

    public void update(SearchHistory searchHistory) {
        artistTextView.setText(searchHistory.getArtist());
        titleTextView.setText(searchHistory.getTitle());
        releaseTextView.setText(searchHistory.getReleaseDate());
    }
}
