package com.example.squaredemo.di.main;

import androidx.lifecycle.ViewModel;

import com.example.squaredemo.di.application.ViewModelKey;
import com.example.squaredemo.ui.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {
    //mapping view model into multibinding
    //similar to ViewModelFactoryModule, but this time I am providing dependency to MainViewModel itself
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindProfileViewModel(MainViewModel viewModel);
}
