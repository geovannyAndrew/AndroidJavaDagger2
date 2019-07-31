package com.gyros.dagger2login.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.gyros.dagger2login.SessionManager;
import com.gyros.dagger2login.models.User;
import com.gyros.dagger2login.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    public static final String TAG = "AuthViewModel";

    private final AuthApi authApi;

    private SessionManager sessionManager;

    @Inject
    public  AuthViewModel(AuthApi authApi, SessionManager sessionManager){
        this.authApi = authApi;
        this.sessionManager = sessionManager;
    }

    public void authenticateWithId(int userId){

        Log.d(TAG,"authenticateWithId");
        sessionManager.authenticateWithId(queryUserId(userId));
    }

    private LiveData<AuthResource<User>> queryUserId(int userId){
        return LiveDataReactiveStreams.fromPublisher(
                authApi.getUser(userId)
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(Throwable throwable) throws Exception {
                                User errorUser = new User();
                                errorUser.setId(-1);
                                return errorUser;
                            }
                        })
                        .map(new Function<User, AuthResource<User>>() {
                            @Override
                            public AuthResource<User> apply(User user) throws Exception {
                                if(user.getId() == -1){
                                    return AuthResource.error("Could not authenticate",user);
                                }
                                return AuthResource.authenticated(user);
                            }
                        })
                        .subscribeOn(Schedulers.io())
        );
    }

    public LiveData<AuthResource<User>> observeAuthState(){
        return sessionManager.getAuthUser();
    }
}
