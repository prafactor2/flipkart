package com.example.khaalijeb.newlistview_module;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import models.Login;

/**
 * Created by Lord Voldemort on 23/11/2015.
 */
public class LoginActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new LoginFragment ()).commit();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
    /*    Log.d("api", "onactivityresult requestcode: " + requestCode);
        if (requestCode == 9001) {
            Log.d("api", "google onactivity result");
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        //    handleSignInResult(result);
        } else {
            Log.d("api", "facebook onactivityresult" + "requestcode: " + requestCode);
        //    mCallbackmanager.onActivityResult(requestCode, resultCode, data);
        }
        */
    }
}