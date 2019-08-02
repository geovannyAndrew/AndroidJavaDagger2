package com.gyros.dagger2login.di.auth;

import com.gyros.dagger2login.models.User;
import com.gyros.dagger2login.network.auth.AuthApi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AuthModule {

    @AuthScope
    @Provides
    @Named("auth_user")
    static User provideUser(){
        return new User();
    }

    @AuthScope
    @Provides
    static AuthApi provideAuthApi(Retrofit retrofit){
        return  retrofit.create(AuthApi.class);
    }
}
