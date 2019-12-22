package com.example.squaredemo.di.application;

import com.example.squaredemo.network.MainInterceptor;
import com.example.squaredemo.util.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    //application level dependencies like retrofit, glide

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static OkHttpClient provideMainOkHttpInstance(MainInterceptor mainInterceptor, HttpLoggingInterceptor httpLoggingInterceptor){
        return new OkHttpClient().newBuilder()
                .addInterceptor(mainInterceptor)
                //.addInterceptor(httpLoggingInterceptor) // log API requests
                .build();
    }

    @Singleton
    @Provides
    static MainInterceptor provideMainInterceptor() {
        return new MainInterceptor();
    }

    @Singleton
    @Provides
    static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }
}
