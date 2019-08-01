package com.gyros.dagger2login.di;

import com.gyros.dagger2login.di.auth.AuthModule;
import com.gyros.dagger2login.di.auth.AuthViewModelsModule;
import com.gyros.dagger2login.di.main.MainFragmentBuildersModule;
import com.gyros.dagger2login.di.main.MainViewModelsModule;
import com.gyros.dagger2login.ui.auth.AuthActivity;
import com.gyros.dagger2login.ui.main.MainActivity;

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

    @ContributesAndroidInjector(
            modules = {
                    MainFragmentBuildersModule.class,
                    MainViewModelsModule.class
            }
    )
    abstract MainActivity contributeMainActivity();



}
