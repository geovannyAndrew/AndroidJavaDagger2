package com.gyros.dagger2login.di;

import androidx.lifecycle.ViewModelProvider;

import com.gyros.dagger2login.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModuleFactory(ViewModelProviderFactory modelProviderFactory);


}
