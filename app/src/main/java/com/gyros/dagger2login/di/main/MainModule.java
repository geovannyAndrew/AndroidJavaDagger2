package com.gyros.dagger2login.di.main;

import com.gyros.dagger2login.network.main.MainApi;
import com.gyros.dagger2login.ui.main.posts.PostRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @Provides
    static MainApi provideMainApi(Retrofit retrofit){
        return retrofit.create(MainApi.class);
    }

    @Provides
    static PostRecyclerAdapter providePostsAdapter(){
        return new PostRecyclerAdapter();
    }

}
