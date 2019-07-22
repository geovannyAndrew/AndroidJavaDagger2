package com.gyros.dagger2login.di;

import com.gyros.dagger2login.di.auth.AuthModule;
import com.gyros.dagger2login.di.auth.AuthViewModelsModule;
import com.gyros.dagger2login.ui.auth.AuthActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {
                    AuthViewModelsModule.class,
                    AuthModule.class
            }
    )
    abstract AuthActivity contributeAuthActivity();



}
