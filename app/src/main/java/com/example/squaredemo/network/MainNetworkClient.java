package com.example.squaredemo.network;

import android.util.Log;

import com.example.squaredemo.models.ListData;

import javax.inject.Inject;

import io.reactivex.Single;

public class MainNetworkClient {
    private static final String TAG = "MainNetworkClient";

    private final MainApi mainApi;

    @Inject
    public MainNetworkClient(MainApi mainApi) {
        this.mainApi = mainApi;
    }

    public Single<ListData> requestEmployees() {
        Log.d(TAG, "requestEmployees: about to make api request");
        return mainApi.getEmployees();
    }
}
