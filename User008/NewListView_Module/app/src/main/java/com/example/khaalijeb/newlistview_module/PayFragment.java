package com.example.khaalijeb.newlistview_module;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajdeep Singh on 03-06-2015.
 */
public class PayFragment extends Fragment {
    Context c;
    private int flag;
    Button mPayFragmentPayButton;
    TextView mPayFragmentMobileNoText;
    TextView mPayFragmentAmountText;
    String mPayFragmentMobileString;
    String mPayFragmentAmountString;
    String mCurrentWalletAmount;
    int mCurrentWalletAmountInteger;
    int mEnteredAmountInteger;
    int mAmountLeft;
    String mAmountLeftString;
    String mReceiverBalance;
    String mUserId;
    String mUserIdFile;
    SharedPreferences mSharedPreferences;

    // boolean check;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.pay_fragment_layout, container, false);
       /* c = getActivity();
        flag = 0;*/


        RecyclerView rvContacts = (RecyclerView)v.findViewById(R.id.rvContacts);

        ImageRecyclerViewAdapter adapter = new ImageRecyclerViewAdapter(getActivity(),getData2(c));

        // Attach the adapter to the recyclerview to populate item
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(c);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvContacts.setLayoutManager(mLinearLayoutManager);

        return v;
    }

    public List<Imagedata> getData2(Context c) {
        List<Imagedata> data = new ArrayList<>();



        int[] icon = {R.drawable.levislogo1, R.drawable.nike, R.drawable.levislogo1, R.drawable.nike, R.drawable.levislogo1,R.drawable.nike,R.drawable.levislogo1,R.drawable.nike,R.drawable.levislogo1};
        for (int i = 0; i < icon.length; i++) {
            Imagedata current = new Imagedata();
            current.offersimage = icon[i];

            data.add(current);
        }

        return data;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPayFragmentMobileNoText = (TextView) getView().findViewById(R.id.mPayMobile);
        mPayFragmentAmountText = (TextView) getView().findViewById(R.id.mPayAmount);
        mPayFragmentPayButton = (Button) getView().findViewById(R.id.mPayButton);
        Button addmoney = (Button)getView().findViewById(R.id.addmoney);
        ImageView paymentoptionicon = (ImageView)getView().findViewById(R.id.paymenticon);
        TextView paymentoption = (TextView)getView().findViewById(R.id.paymentoption);
        final ImageView shopsearch = (ImageView) getView().findViewById(R.id.searchedit);
        final ImageView nearby = (ImageView)getView().findViewById(R.id.nearbyicon);
        LinearLayout linearlayout = (LinearLayout) getView().findViewById(R.id.mainlayout);
        TextView t = (TextView)getView().findViewById(R.id.balancetext);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/final.ttf");
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cae.ttf");
        t.setTypeface(font2);
        mPayFragmentMobileNoText.setTypeface(font);
        mPayFragmentMobileNoText.setHintTextColor(getResources().getColor(R.color.hintcolor));
        mPayFragmentPayButton.setTypeface(font);
        mPayFragmentAmountText.setTypeface(font);
        mPayFragmentAmountText.setHintTextColor(getResources().getColor(R.color.hintcolor));

        shopsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ShopSearch.class);
                startActivity(i);

            }
        });

        nearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Search.class);
                startActivity(i);
            }
        });

        addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),PaymentOption.class);
                startActivity(i);
            }
        });
        paymentoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),PaymentOption.class);
                startActivity(i);
            }
        });
        paymentoptionicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),PaymentOption.class);
                startActivity(i);
            }
        });

        mPayFragmentPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // t3.setText(bal1);

               Intent i = new Intent(getActivity(),PromoCodeActivity.class);

                startActivity(i);

            }
        });
    }

}
