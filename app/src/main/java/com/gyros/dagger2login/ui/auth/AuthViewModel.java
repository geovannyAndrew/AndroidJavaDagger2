package com.gyros.dagger2login.ui.auth;

import androidx.lifecycle.ViewModel;

import com.gyros.dagger2login.network.auth.AuthApi;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {

    private AuthApi authApi;

    @Inject
    public  AuthViewModel(AuthApi authApi){
        this.authApi = authApi;
    }
}
