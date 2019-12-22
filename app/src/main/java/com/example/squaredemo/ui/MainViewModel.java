package com.example.squaredemo.ui;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.squaredemo.models.ListData;
import com.example.squaredemo.network.RequestContent;


import javax.inject.Inject;

import io.reactivex.Observable;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";

    private RequestContent requestContent;
    private Observable<Resource<ListData>> employeeList;

    @Inject
    public MainViewModel(RequestContent requestContent) {
        this.requestContent = requestContent;
    }

    public Observable<Resource<ListData>> getEmployees() {
        //UI data survives config changes
        if(employeeList == null){
            employeeList = requestContent.queryEmployees();
        }

        return employeeList;
    }
}
