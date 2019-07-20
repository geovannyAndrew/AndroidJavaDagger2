package com.gyros.dagger2login.di;

import android.app.Application;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Named(value = "string1")
    static String someString(){
        return "This is a string injected";
    }

    @Provides
    @Named(value = "string2")
    static String someString2(){
        return "This is another string injected";
    }

    @Provides
    static  boolean getApp(Application application){
        return application == null
    }
}
