package com.gyros.dagger2login.ui.auth;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.gyros.dagger2login.R;
import com.gyros.dagger2login.models.User;
import com.gyros.dagger2login.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    public static final String TAG = "AuthActivity";

    private AuthViewModel viewModel;

    private EditText editUserId;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        editUserId = findViewById(R.id.user_id_input);
        findViewById(R.id.login_button).setOnClickListener(this);
        viewModel = ViewModelProviders.of(this,providerFactory).get(AuthViewModel.class);

        setLogo();
        subscribeObservers();
    }


    private void subscribeObservers(){
        viewModel.observeUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user!=null){
                    Log.d(TAG,"onChanged: "+user.getEmail());
                }
            }
        });
    }

    private void setLogo(){
        requestManager.load(logo).into((ImageView) findViewById(R.id.login_logo));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button:
                attempLogin();
                break;
        }
    }

    private void attempLogin() {
        if(TextUtils.isEmpty(editUserId.getText().toString())){
            return;
        }
        viewModel.authenticateWithId(Integer.parseInt(editUserId.getText().toString()));
    }
}
