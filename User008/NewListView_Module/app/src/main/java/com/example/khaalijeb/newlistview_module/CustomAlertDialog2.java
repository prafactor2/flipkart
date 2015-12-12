package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

import models.Coupon_Info;
import models.merchant_profile;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by Lord Voldemort on 29/11/2015.
 */
public class CustomAlertDialog2 extends DialogFragment {
    private RecyclerView mRecyclerView;
    private ComplexRecyclerViewAdapterPromoCode mAdapter;
    Context c;
    Typeface font;
    ArrayList<Object> items = new ArrayList<>();
    String _id;
    String title;
    String brand_icon;
    String merchant_name;
    String expir_date;
    double amount;


    // this method create view for your Dialog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate layout with recycler view
        View v = inflater.inflate(R.layout.layout_customdialog2, container, false);
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cae.ttf");
        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(c));
        Bundle b = getActivity().getIntent().getExtras();
        _id = b.getString("_id");
        title = b.getString("title");
        brand_icon = b.getString("brand_icon");
        merchant_name = b.getString("merchant_name");
        amount = b.getDouble("amount");
        expir_date = b.getString("expir_date");

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDialog().dismiss();
            }
        });


    }

    public ArrayList<Object> getSampleArrayList(Context c) {
/*
        items.add(new promocodeoffertitledata("Upto Rs. 2000 Cashback on International Hotels for HDFC Cards"));
        items.add(new promocodeoffersummarydata("Get 15% cashback on domestic hotel bookings on Standard Chartered bank cards. Maximum cashback is Rs. 1750. Minimum booking amount should be Rs. 4000. Use coupon code to avail this offer"));
        items.add(new promocodeofferredeemdata("Get 15% cashback on domestic hotel bookings on Standard Chartered bank cards. Maximum cashback is Rs. 1750. Minimum booking amount should be Rs. 4000. Use coupon code to avail this offer"));
        items.add(new promocodeofferwheredata("No. 3, RMZ Infinity - Tower E 3rd, 4th, and 5th Floors, Old Madras Road, Bengaluru, Karnataka 560016", "9690493040"));
*/
        return items;
    }
/*
    public void Merchant_Profile_requestData() {
        Log.d("hii", "Merchant Profile Request Data");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(this);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(MERCHANT_PROFILE_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_Coupon_Info(_id, new Callback<Coupon_Info>() {
                @Override
                public void success(final Coupon_Info server_data, Response response) {
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + server_data.getSuccess());
                    Log.d("hii", "Message : " + server_data.getMessage());
                    if (server_data.getSuccess() == 1) {
                        //Future        NO_MERCHANT.setVisibility(View.INVISIBLE);
                        String cover;
                        String how = server_data.getHow();
                        String where = server_data.getWhere();
                        String summary = server_data.getSummary();
                        items.add(new promocodeoffertitledata(title));
                        items.add(new promocodeoffersummarydata(summary));
                        items.add(new promocodeofferredeemdata(how));
                        items.add(new promocodeofferwheredata(where));

                        mAdapter = new ComplexRecyclerViewAdapterPromoCode(getActivity(), items, font);
                        mRecyclerView.setAdapter(mAdapter);
                    } else {
                        Log.d("hii", server_data.getMessage());
                        Toast.makeText(getContext(), server_data.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.d("hii", "failure");
                    Log.e("hii", retrofitError.toString());
                    Toast.makeText(getContext(), retrofitError.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d("hii", "api null");
        }
    }
    */
}