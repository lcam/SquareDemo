package com.example.squaredemo.di.main;

import com.example.squaredemo.network.MainApi;
import com.example.squaredemo.network.MainNetworkClient;
import com.example.squaredemo.network.RequestContent;
import com.example.squaredemo.ui.MainRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @Provides
    static MainRecyclerAdapter provideMainRecyclerAdapter() {
        return new MainRecyclerAdapter();
    }

    @Provides
    static MainApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }

    @Provides
    static MainNetworkClient provideMainNetworkClient(MainApi mainApi) {
        return new MainNetworkClient(mainApi);
    }

    @Provides
    static RequestContent provideRequestContent(MainNetworkClient client) {
        return new RequestContent(client);
    }
}
