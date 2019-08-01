package com.gyros.dagger2login.network.main;

import com.gyros.dagger2login.models.Post;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApi {

    @GET("posts")
    Flowable<List<Post>> getPostFromUser(
            @Query("userId") int id
    );
}
