package com.ronaksoftware.musicchi.controllers;

import android.annotation.SuppressLint;

import com.google.gson.reflect.TypeToken;
import com.ronaksoftware.musicchi.ApplicationLoader;
import com.ronaksoftware.musicchi.models.SearchHistory;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SearchHistoryController {
    private static volatile SearchHistoryController Instance;

    public static SearchHistoryController getInstance() {
        SearchHistoryController localInstance = Instance;
        if (localInstance == null) {
            synchronized (SearchHistoryController.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new SearchHistoryController();
                }
            }
        }
        return localInstance;
    }

    public ArrayList<SearchHistory> data = new ArrayList<>();

    private SearchHistoryController() {
        load();
    }

    private void load() {
        String dataString = PreferenceController.getInstance().getMainPreferences().getString("searchHistory", null);

        if (dataString == null || dataString.length() == 0) {
            return;
        }

        Type listType = new TypeToken<ArrayList<SearchHistory>>() {
        }.getType();
        ArrayList<SearchHistory> data = ApplicationLoader.gson.fromJson(dataString, listType);

        if (data != null && data.size() != 0) {
            this.data.addAll(data);
        }
    }

    @SuppressLint("ApplySharedPref")
    public void save() {
        if (data.size() == 0) {
            PreferenceController.getInstance().getMainPreferences().edit().putString("searchHistory", null).commit();
            return;
        }

        Type listType = new TypeToken<ArrayList<SearchHistory>>() {
        }.getType();
        String jsonData = ApplicationLoader.gson.toJson(data, listType);

        if (jsonData == null || jsonData.length() == 0) {
            return;
        }

        PreferenceController.getInstance().getMainPreferences().edit().putString("searchHistory", jsonData).commit();
    }
}
