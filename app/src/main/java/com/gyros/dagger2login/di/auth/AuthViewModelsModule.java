package com.gyros.dagger2login.di.auth;

import androidx.lifecycle.ViewModel;

import com.gyros.dagger2login.di.ViewModelKey;
import com.gyros.dagger2login.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);
}
