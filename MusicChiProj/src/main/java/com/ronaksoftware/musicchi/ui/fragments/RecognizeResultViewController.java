package com.ronaksoftware.musicchi.ui.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ronaksoftware.musicchi.ApplicationLoader;
import com.ronaksoftware.musicchi.R;
import com.ronaksoftware.musicchi.controllers.EventController;
import com.ronaksoftware.musicchi.controllers.MediaController;
import com.ronaksoftware.musicchi.models.Song;
import com.ronaksoftware.musicchi.network.ResponseEnvelope;
import com.ronaksoftware.musicchi.network.response.SearchSongsListResponse;
import com.ronaksoftware.musicchi.network.response.SoundSearchResponse;
import com.ronaksoftware.musicchi.ui.cells.Holder;
import com.ronaksoftware.musicchi.ui.cells.SongCell;
import com.ronaksoftware.musicchi.ui.components.CircleImageView;
import com.ronaksoftware.musicchi.ui.presenter.ActionBar;
import com.ronaksoftware.musicchi.ui.presenter.BackDrawable;
import com.ronaksoftware.musicchi.ui.presenter.BaseViewController;
import com.ronaksoftware.musicchi.ui.presenter.Theme;
import com.ronaksoftware.musicchi.utils.DisplayUtility;
import com.ronaksoftware.musicchi.utils.LayoutHelper;
import com.ronaksoftware.musicchi.utils.Queues;
import com.ronaksoftware.musicchi.utils.TypefaceUtility;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RecognizeResultViewController extends BaseViewController implements View.OnClickListener {
    private FrameLayout contentView;
    private RecyclerView songsListView;
    private ListAdapter listAdapter;
    private ProgressBar progressBar;

    private HashMap<String,Song> songDict = new HashMap<>();
    private ArrayList<Song> songs = new ArrayList<>();
    private SoundSearchResponse soundSearchResponse;

    private CompositeDisposable disposables = new CompositeDisposable();

    public RecognizeResultViewController(SoundSearchResponse soundSearchResponse) {
        this.soundSearchResponse = soundSearchResponse;
    }

    @Override
    public boolean onFragmentCreate() {
        if (soundSearchResponse == null) {
            return false;
        }

        songs.clear();
        for (int i = 0 ; i < soundSearchResponse.getSongs().size() ; i++) {
            Song song = soundSearchResponse.getSongs().get(i);
            if (!songDict.containsKey(song.getId())) {
                songDict.put(song.getId(), song);
                songs.add(song);
            }
        }

        search();

        disposables.add(EventController.songPlayingDidStart.observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object[]>() {
            @Override
            public void accept(Object[] objects) throws Exception {
                if (listAdapter != null) {
                    listAdapter.notifyDataSetChanged();
                }
            }
        }));
        disposables.add(EventController.songPlayingDidReset.observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object[]>() {
            @Override
            public void accept(Object[] objects) throws Exception {
                if (listAdapter != null) {
                    listAdapter.notifyDataSetChanged();
                }
            }
        }));
        disposables.add(EventController.songPlayingProgressDidChanged.observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object[]>() {
            @Override
            public void accept(Object[] objects) throws Exception {
                String id = (String) objects[0];
                float progress = (float) objects[1];

                if (songsListView != null) {
                    int count = songsListView.getChildCount();
                    for (int a = 0; a < count; a++) {
                        View view = songsListView.getChildAt(a);
                        if (view instanceof SongCell) {
                            SongCell cell = (SongCell) view;
                            Song songObject = cell.getCurrentSongObject();
                            if (songObject != null && songObject.getId() != null && songObject.getId().equals(id)) {
                                cell.updateProgress(progress);
                            }
                        }
                    }
                }
            }
        }));

        return super.onFragmentCreate();
    }

    private void search() {
        disposables.add(ApplicationLoader.musicChiApi.search().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseEnvelope<SearchSongsListResponse>>() {
            @Override
            public void accept(ResponseEnvelope<SearchSongsListResponse> searchSongsListResponseResponseEnvelope) throws Exception {
                for (int i = 0 ; i < searchSongsListResponseResponseEnvelope.getPayload().getSongs().size() ; i++) {
                    Song song = searchSongsListResponseResponseEnvelope.getPayload().getSongs().get(i);
                    if (!songDict.containsKey(song.getId())) {
                        songDict.put(song.getId(), song);
                        songs.add(song);
                    }
                }

                Queues.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            progressBar.setVisibility(View.GONE);

                            if (listAdapter != null) {
                                listAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                search();

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        }));
    }

    @Override
    public void onFragmentDestroy() {
        disposables.dispose();
    }

    @Override
    protected ActionBar createActionBar(Context context) {
        return null;
    }

    @Override
    public View createView(Context context) {
        fragmentView = contentView = new FrameLayout(context);
        contentView.setBackground(new ColorDrawable(Color.parseColor("#1A1E2A")));

        FrameLayout actionBarView = new FrameLayout(context);
        contentView.addView(actionBarView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, 160, Gravity.TOP | Gravity.LEFT, 0, Build.VERSION.SDK_INT >= 21 ? 24 : 0, 0, 0));
        actionBarView.setBackground(Theme.createRoundRectDrawable(new float[]{0, 0, DisplayUtility.dp(18), 0}, Color.parseColor("#FD0C6B")));

        TextView titleTextView = new TextView(context);
        titleTextView.setText("نتایج جستجو");
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


        CircleImageView coverImageView = new CircleImageView(context);
        coverImageView.setImageResource(R.drawable.avatar);
        actionBarView.addView(coverImageView, LayoutHelper.createFrame(60, 60, Gravity.TOP | Gravity.RIGHT, 0, 84, 20, 0));

        TextView artistTextView = new TextView(context);
        artistTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        artistTextView.setTextColor(Color.WHITE);
        artistTextView.setText(soundSearchResponse.getInfo().getArtists().get(0));
        artistTextView.setSingleLine(true);
        artistTextView.setEllipsize(TextUtils.TruncateAt.END);
        actionBarView.addView(artistTextView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.TOP | Gravity.LEFT, 20, 84, 60 + 20 + 12, 0));

        TextView songNameTextView = new TextView(context);
        songNameTextView.setText(soundSearchResponse.getInfo().getTitle());
        songNameTextView.setTextColor(Color.WHITE);
        songNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        songNameTextView.setEllipsize(TextUtils.TruncateAt.END);
        songNameTextView.setSingleLine(true);
        actionBarView.addView(songNameTextView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.TOP | Gravity.LEFT, 20, 107, 60 + 20 + 12, 0));

        TextView releaseDateTextView = new TextView(context);
        releaseDateTextView.setText(soundSearchResponse.getInfo().getReleaseDate());
        releaseDateTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        releaseDateTextView.setTextColor(Color.WHITE);
        releaseDateTextView.setSingleLine(true);
        releaseDateTextView.setEllipsize(TextUtils.TruncateAt.END);
        actionBarView.addView(releaseDateTextView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.TOP | Gravity.LEFT, 20, 130, 60 + 20 + 12, 0));

        TextView songListTitle = new TextView(context);
        songListTitle.setTypeface(TypefaceUtility.getTypeface("fonts/IRANSans-Medium.ttf"));
        songListTitle.setText("موسیقی های مرتبط");
        songListTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        songListTitle.setTextColor(Color.WHITE);
        contentView.addView(songListTitle, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.RIGHT | Gravity.TOP, 0, 170 + (Build.VERSION.SDK_INT >= 21 ? 24 : 0), 20, 0));

        songsListView = new RecyclerView(context);
        songsListView.setAdapter(listAdapter = new ListAdapter());
        songsListView.setLayoutManager(new LinearLayoutManager(context));
        contentView.addView(songsListView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, Gravity.TOP | Gravity.LEFT, 0, 212 + (Build.VERSION.SDK_INT >= 21 ? 24 : 0), 0, 0));


        progressBar = new ProgressBar(context);
        contentView.addView(progressBar, LayoutHelper.createFrame(56, 56, Gravity.CENTER));

        return fragmentView;
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        Song song = songs.get(position);

        if (MediaController.getInstance().isPlayingSong(song)) {
            MediaController.getInstance().cleanupPlayer(true);
        } else {
            MediaController.getInstance().playSong(song);
        }
    }

    @Override
    public void onPause() {
        MediaController.getInstance().cleanupPlayer(false);

        super.onPause();
    }

    private class ListAdapter extends RecyclerView.Adapter<Holder> {

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            SongCell songCell = new SongCell(contentView.getContext());
            songCell.setOnClickListener(RecognizeResultViewController.this);
            songCell.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtility.dp(50)));
            ((RecyclerView.LayoutParams) songCell.getLayoutParams()).leftMargin = DisplayUtility.dp(20);
            ((RecyclerView.LayoutParams) songCell.getLayoutParams()).rightMargin = DisplayUtility.dp(20);
            ((RecyclerView.LayoutParams) songCell.getLayoutParams()).topMargin = DisplayUtility.dp(6);
            ((RecyclerView.LayoutParams) songCell.getLayoutParams()).bottomMargin = DisplayUtility.dp(6);

            return new Holder(songCell);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            Song song = songs.get(position);
            SongCell songCell = (SongCell) holder.itemView;
            songCell.setTag(position);
            songCell.update(song);
        }

        @Override
        public int getItemCount() {
            return songs.size();
        }
    }
}
