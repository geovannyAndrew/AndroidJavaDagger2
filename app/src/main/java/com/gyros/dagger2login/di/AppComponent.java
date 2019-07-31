package com.gyros.dagger2login.di;

import android.app.Application;

import com.gyros.dagger2login.BaseApplication;
import com.gyros.dagger2login.SessionManager;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                AppModule.class,
                ViewModelFactoryModule.class,

        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {

    SessionManager sessionManager();

    @Component.Builder
    interface  Builder{

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

}
