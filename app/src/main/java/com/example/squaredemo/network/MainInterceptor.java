package com.example.squaredemo.network;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MainInterceptor implements Interceptor {

    @Inject
    public MainInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request()
                .newBuilder()
                .build();
        return chain.proceed(request);
    }
}
