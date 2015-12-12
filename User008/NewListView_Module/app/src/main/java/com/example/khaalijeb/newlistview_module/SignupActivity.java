package com.example.khaalijeb.newlistview_module;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Lord Voldemort on 23/11/2015.
 */
public class SignupActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new SignUpFragment ()).commit();
        }
    }



}