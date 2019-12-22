package com.example.squaredemo.network;

import com.example.squaredemo.models.ListData;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MainApi {

    //https://s3.amazonaws.com/sq-mobile-interview/
    @GET("employees.json")
    Single<ListData> getEmployees(
    );
}
