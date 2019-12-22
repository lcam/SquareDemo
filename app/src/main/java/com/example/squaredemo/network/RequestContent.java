package com.example.squaredemo.network;

import android.util.Log;

import com.example.squaredemo.models.Employee;
import com.example.squaredemo.models.ListData;
import com.example.squaredemo.ui.Resource;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RequestContent {
    private static final String TAG = "RequestContent";

    private MainNetworkClient mainNetworkClient;

    @Inject
    public RequestContent(MainNetworkClient mainNetworkClient) {
        this.mainNetworkClient = mainNetworkClient;
    }

    public Observable<Resource<ListData>> queryEmployees() {
        Single<ListData> source = mainNetworkClient.requestEmployees();

        return source.toObservable()
                //.onErrorReturnItem(defaultListData())
                .onErrorReturn(throwable -> defaultListData(throwable))
                .timeout(10, TimeUnit.SECONDS)
                .map(employees -> mapToResource(employees))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private ListData defaultListData(Throwable throwable) {
        Log.e(TAG, "defaultListData: ERROR " + throwable.getLocalizedMessage());
        Employee employee = new Employee("-1", "", "", "", "");
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(employee);
        return new ListData(employees);
    }

    private Resource<ListData> mapToResource(ListData employees){
        if(employees.getEmployees().size() > 0){
            if(employees.getEmployees().get(0).getUuid().equals("-1")){
                return Resource.error("Could not retrieve employees", (ListData)null);
            }
        }

        for(Employee employee : employees.getEmployees()){
            if(employee.getUuid() == null){
                return Resource.error("at least 1 employee is missing uuid info", (ListData)null);
            }
            if(employee.getFullName() == null){
                return Resource.error("at least 1 employee is missing name info", (ListData)null);
            }
            if(employee.getEmailAddress() == null){
                return Resource.error("at least 1 employee is missing email info", (ListData)null);
            }
            if(employee.getTeam() == null){
                return Resource.error("at least 1 employee is missing team info", (ListData)null);
            }
            if(employee.getEmployeeType() == null){
                return Resource.error("at least 1 employee is missing employee type info", (ListData)null);
            }
            //employee.setPhotoUrlSmall("test");
        }

        Log.d(TAG, "queryEmployees: SUCCESS");
        return Resource.success(employees);
    }
}
