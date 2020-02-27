package com.ronaksoftware.musicchi.ui.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ronaksoftware.musicchi.controllers.SearchHistoryController;
import com.ronaksoftware.musicchi.models.SearchHistory;
import com.ronaksoftware.musicchi.ui.cells.Holder;
import com.ronaksoftware.musicchi.ui.cells.SearchHistoryCell;
import com.ronaksoftware.musicchi.ui.cells.SongCell;
import com.ronaksoftware.musicchi.ui.presenter.ActionBar;
import com.ronaksoftware.musicchi.ui.presenter.AlertDialog;
import com.ronaksoftware.musicchi.ui.presenter.BackDrawable;
import com.ronaksoftware.musicchi.ui.presenter.BaseViewController;
import com.ronaksoftware.musicchi.ui.presenter.Theme;
import com.ronaksoftware.musicchi.utils.DisplayUtility;
import com.ronaksoftware.musicchi.utils.LayoutHelper;
import com.ronaksoftware.musicchi.utils.TypefaceUtility;

import java.util.ArrayList;

public class SearchHistoryViewController extends BaseViewController implements View.OnClickListener {

    private ArrayList<SearchHistory> dataList = new ArrayList<>();

    private FrameLayout contentView;
    private RecyclerView listView;
    private ListAdapter listAdapter;

    @Override
    public boolean onFragmentCreate() {
        dataList = new ArrayList<>(SearchHistoryController.getInstance().data);
        return super.onFragmentCreate();
    }

    @Override
    public View createView(Context context) {
        fragmentView = contentView = new FrameLayout(context);
        contentView.setBackground(new ColorDrawable(Color.parseColor("#1A1E2A")));

        FrameLayout actionBarView = new FrameLayout(context);
        contentView.addView(actionBarView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, 56, Gravity.TOP | Gravity.LEFT, 0, Build.VERSION.SDK_INT >= 21 ? 24 : 0, 0, 0));
        actionBarView.setBackground(Theme.createRoundRectDrawable(new float[]{0, 0, DisplayUtility.dp(18), 0}, Color.parseColor("#FD0C6B")));

        TextView titleTextView = new TextView(context);
        titleTextView.setText("تاریخچه جستجو");
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

        listView = new RecyclerView(context);
        listView.setAdapter(listAdapter = new ListAdapter());
        listView.setLayoutManager(new LinearLayoutManager(context));
        listView.setPadding(0, DisplayUtility.dp(16), 0, 0);
        contentView.addView(listView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, Gravity.TOP | Gravity.LEFT, 0, 56 + (Build.VERSION.SDK_INT >= 21 ? 24 : 0), 0, 0));

        return fragmentView;
    }

    @Override
    protected ActionBar createActionBar(Context context) {
        return null;
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        showDialog(new AlertDialog.Builder(getParentActivity())
                .setTitle("حذف")
                .setMessage("آیا مایل به حذف این تاریخچه هستید")
                .setNegativeButton("لغو", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setNeutralButton("حذف", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SearchHistoryController.getInstance().data.remove(position);
                        SearchHistoryController.getInstance().save();
                        dataList.remove(position);
                        listAdapter.notifyItemRemoved(position);
                        dialogInterface.dismiss();
                    }
                })
                .create());
    }

    private class ListAdapter extends RecyclerView.Adapter<Holder> {

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            SearchHistoryCell searchHistoryCell = new SearchHistoryCell(parent.getContext());
            searchHistoryCell.setOnClickListener(SearchHistoryViewController.this);
            searchHistoryCell.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtility.dp(68)));
            ((RecyclerView.LayoutParams) searchHistoryCell.getLayoutParams()).leftMargin = DisplayUtility.dp(20);
            ((RecyclerView.LayoutParams) searchHistoryCell.getLayoutParams()).rightMargin = DisplayUtility.dp(20);
            ((RecyclerView.LayoutParams) searchHistoryCell.getLayoutParams()).topMargin = DisplayUtility.dp(6);
            ((RecyclerView.LayoutParams) searchHistoryCell.getLayoutParams()).bottomMargin = DisplayUtility.dp(6);

            return new Holder(searchHistoryCell);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            SearchHistoryCell historyCell = (SearchHistoryCell) holder.itemView;
            historyCell.setTag(position);
            historyCell.update(dataList.get(position));
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }
}
