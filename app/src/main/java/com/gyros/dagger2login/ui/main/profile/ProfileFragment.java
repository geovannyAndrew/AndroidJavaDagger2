package com.gyros.dagger2login.ui.main.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.gyros.dagger2login.R;
import com.gyros.dagger2login.models.User;
import com.gyros.dagger2login.ui.auth.AuthResource;
import com.gyros.dagger2login.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ProfileFragment extends DaggerFragment {

    private static final String TAG = "ProfileFragment";


    private ProfileViewModel profileViewModel;
    private TextView email, username, website;

    @Inject
    ViewModelProviderFactory providerFactory;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"ProfileFragment onViewCreated");
        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        website = view.findViewById(R.id.website);
        profileViewModel = ViewModelProviders.of(this,providerFactory).get(ProfileViewModel.class);
        subscribeObservers();
    }


    private void subscribeObservers(){
        profileViewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());
        profileViewModel.getAuthenticatedUser().observe(getViewLifecycleOwner(),
                new Observer<AuthResource<User>>() {
                    @Override
                    public void onChanged(AuthResource<User> userAuthResource) {
                        if(userAuthResource != null){
                            switch (userAuthResource.status){
                                case AUTHENTICATED:{
                                    setUserDetails(userAuthResource.data);
                                    break;
                                }
                                case ERROR:{
                                    setErrorDetails(userAuthResource.message);
                                    break;
                                }
                            }
                        }
                    }
                });
    }

    private void setErrorDetails(String message) {
        email.setText(message);
        website.setText("error");
        username.setText("error");
    }

    private void setUserDetails(User data) {
        email.setText(data.getEmail());
        website.setText(data.getWebsite());
        username.setText(data.getUsername());
    }
}
