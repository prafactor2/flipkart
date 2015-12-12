package com.example.khaalijeb.newlistview_module;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddMoneyActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        Button b1 = (Button)findViewById(R.id.b1);
        Button b2 = (Button)findViewById(R.id.b2);
        Button b3 = (Button)findViewById(R.id.b3);
        Button b4 = (Button)findViewById(R.id.b4);
        EditText amount = (EditText)findViewById(R.id.username);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        EditText amount = (EditText)findViewById(R.id.username);
        // getId() returns this view's identifier.
        if(v.getId() == R.id.b1){

            Toast.makeText(this,"b1",Toast.LENGTH_LONG).show();
            amount.setText("100");


        }else if(v.getId() == R.id.b2){

            Toast.makeText(this,"b2",Toast.LENGTH_LONG).show();
            amount.setText("200");

        }else if(v.getId() == R.id.b3){

            Toast.makeText(this,"b3",Toast.LENGTH_LONG).show();
            amount.setText("500");

        }else if(v.getId() == R.id.b4){

            Toast.makeText(this,"b4",Toast.LENGTH_LONG).show();
            amount.setText("1000");

        }

    }
}
