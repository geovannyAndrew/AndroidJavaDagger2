package com.gyros.dagger2login.ui.auth;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.gyros.dagger2login.R;
import com.gyros.dagger2login.models.User;
import com.gyros.dagger2login.ui.main.MainActivity;
import com.gyros.dagger2login.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    public static final String TAG = "AuthActivity";

    private AuthViewModel viewModel;

    private EditText editUserId;
    private ProgressBar progressBar;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    @Inject
    @Named("auth_user")
    User userAuth;

    @Inject
    @Named("app_user")
    User userApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        editUserId = findViewById(R.id.user_id_input);
        progressBar = findViewById(R.id.progress_bar);
        findViewById(R.id.login_button).setOnClickListener(this);
        viewModel = ViewModelProviders.of(this,providerFactory).get(AuthViewModel.class);

        setLogo();
        subscribeObservers();

        Log.d(TAG, "onCreate: user auth "+userAuth);
        Log.d(TAG, "onCreate: user app "+userApp);
    }


    private void subscribeObservers(){
        viewModel.observeAuthState().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource != null){
                    switch (userAuthResource.status){
                        case LOADING:
                            showProgressBar(true);
                            break;
                        case AUTHENTICATED:
                            showProgressBar(false);
                            onLoginSuccess();
                            Log.d(TAG,"onChanged: LOGIN SUCCESS " + userAuthResource.data.getEmail());
                            break;
                        case NOT_AUTHENTICATED:
                            showProgressBar(false);
                            break;
                        case ERROR:
                            showProgressBar(false);
                            Toast.makeText(AuthActivity.this,userAuthResource.message,Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }
        });
    }

    private void showProgressBar(boolean isVisible){
        if(isVisible){
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.GONE);
        }
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

    private void onLoginSuccess(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
