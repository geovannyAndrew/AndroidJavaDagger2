package com.gyros.dagger2login.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.gyros.dagger2login.SessionManager;
import com.gyros.dagger2login.models.User;
import com.gyros.dagger2login.ui.auth.AuthResource;

import javax.inject.Inject;


public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";

    private final SessionManager sessionManager;

    @Inject
    public ProfileViewModel(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        Log.d(TAG,"ProfileViewModel ");
    }

    public LiveData<AuthResource<User>> getAuthenticatedUser(){
        return sessionManager.getAuthUser();
    }
}
