package com.example.squaredemo.di.application;

import com.example.squaredemo.ui.MainActivity;
import com.example.squaredemo.di.main.MainModule;
import com.example.squaredemo.di.main.MainViewModelsModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    //declare Activities here!

    //now MainActivity is a potential client that I can inject dependencies into
    //Automatically creates a subcomponent --> for SCOPING!!!
    @ContributesAndroidInjector(
            modules = {MainViewModelsModule.class, MainModule.class}
    )
    abstract MainActivity contributeMainActivity();
}
