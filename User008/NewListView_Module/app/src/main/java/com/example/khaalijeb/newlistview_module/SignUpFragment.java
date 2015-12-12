package com.example.khaalijeb.newlistview_module;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * Created by Rajdeep Singh on 16-06-2015.
 */
public class SignUpFragment extends Fragment {
    String mSignUpMobileString,mSignUpPassString,mSignUpEmailString;
    TextView mSignUpMobileText,mSignUpPassText,mSignUpEmailText;
    Button mSignUpButton;
    @Nullable


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_sign_up, container, false);
        TextView txt = (TextView) v.findViewById(R.id.textView);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/final.ttf");
        txt.setTypeface(font);
        ImageView close;
        close = (ImageView)v.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();
            }
        });
        return v;
    }

    private View.OnTouchListener otl = new View.OnTouchListener() {
        public boolean onTouch (View v, MotionEvent event) {

            if(event.getAction() == MotionEvent.ACTION_UP) {

                DialogFragment newFragment = new DateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");

            }
            return true; // the listener has consumed the event
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSignUpMobileText = (TextView) getView().findViewById(R.id.smobile);
        mSignUpEmailText = (TextView) getView().findViewById(R.id.smail);
        mSignUpPassText = (TextView) getView().findViewById(R.id.spass);
        mSignUpButton = (Button) getView().findViewById(R.id.signup);
        EditText sdob = (EditText)getView().findViewById(R.id.sdob);

        sdob.setOnTouchListener(otl);

        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        android.accounts.Account[] accounts = AccountManager.get(getContext()).getAccounts();
        String possibleEmail = "";
        for (android.accounts.Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                possibleEmail = account.name;
            }
        }
        //TextView t1 = (TextView)v.findViewById(R.id.account_email);
        mSignUpEmailText.setText(possibleEmail);

        /*Log.d("ee", mSignUpMobileString);
        Log.d("ee",mSignUpPassString);*/

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignUpMobileString = mSignUpMobileText.getText().toString();
                mSignUpEmailString = mSignUpEmailText.getText().toString();
                mSignUpPassString = mSignUpPassText.getText().toString();

                Context cv = getActivity();

                Operations op = new Operations(cv);
                op.putinfo(op, mSignUpMobileString, mSignUpPassString);
                Toast.makeText(cv,"Registration Completed", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getActivity(), MainActivity.class);
               /* Bundle b = new Bundle();
                b.putString("key1", mSignUpPassString);
       /* b.putString("key4",pass);
        Intent i = new Intent(getApplicationContext(),operations.class);
        i.putExtras(b);
       // Log.d("bb", "hii");*/
               // i.putExtras(b);
                startActivity(i);
            }
        });


    }


}
