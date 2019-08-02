package com.gyros.dagger2login.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.gyros.dagger2login.R;
import com.gyros.dagger2login.models.Post;
import com.gyros.dagger2login.ui.main.Resource;
import com.gyros.dagger2login.utils.VerticalSpaceItemDecoration;
import com.gyros.dagger2login.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PostsFragment extends DaggerFragment {

    private static final String TAG = "PostsFragment";

    private PostsViewModel postsViewModel;
    private RecyclerView recyclerView;

    @Inject
    ViewModelProviderFactory modelProviderFactory;

    @Inject
    PostRecyclerAdapter postRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);
        postsViewModel = ViewModelProviders.of(this,modelProviderFactory).get(PostsViewModel.class);
        initRecyclerView();
        subscribeObservers();
    }

    private void subscribeObservers(){
        postsViewModel.observePosts().removeObservers(getViewLifecycleOwner());
        postsViewModel.observePosts().observe(getViewLifecycleOwner(),
                new Observer<Resource<List<Post>>>() {
                    @Override
                    public void onChanged(Resource<List<Post>> listResource) {
                        if(listResource != null){
                            Log.d(TAG,"onChanged: "+listResource.data);
                            switch (listResource.status){
                                case LOADING:{
                                    Log.d(TAG,"onChanged: LOADING");
                                    break;
                                }
                                case SUCCESS:{
                                    Log.d(TAG,"onChanged: SUCCESS");
                                    postRecyclerAdapter.setPosts(listResource.data);
                                    break;
                                }
                                case ERROR:{
                                    Log.d(TAG,"onChanged: SUCCESS");
                                    break;
                                }
                            }
                        }
                    }
                });
    }

    private void initRecyclerView(){
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(15);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(postRecyclerAdapter);
    }
}
