package com.gyros.dagger2login;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.gyros.dagger2login.models.User;
import com.gyros.dagger2login.ui.auth.AuthActivity;
import com.gyros.dagger2login.ui.auth.AuthResource;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Inject
    protected SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeObserver();
    }

    private void subscribeObserver(){
        sessionManager.getAuthUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource != null){
                    switch (userAuthResource.status){
                        case LOADING:

                            break;
                        case AUTHENTICATED:

                            Log.d(TAG,"onChanged: LOGIN SUCCESS " + userAuthResource.data.getEmail());
                            break;
                        case NOT_AUTHENTICATED:
                            goToLoginActivity();
                            break;
                        case ERROR:
                            Toast.makeText(BaseActivity.this,userAuthResource.message,Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }
        });
    }

    private void goToLoginActivity(){
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

}
