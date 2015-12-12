package com.example.khaalijeb.newlistview_module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import models.success_message;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by Lord Voldemort on 04/11/2015.
 */
public class BrandRecyclerViewAdapter extends RecyclerView.Adapter<BrandRecyclerViewAdapter.MyViewholder> {

    private ClickListener clickListener;
    private LayoutInflater inflator;
    private Context mcontext;
    Typeface font;

    private IObserver callerActivity;

    public interface IObserver {

        public void onItemClicked(int position);
    }

    List<allbrandsdata> data = Collections.emptyList();

    public BrandRecyclerViewAdapter(Context context, List<allbrandsdata> y, Typeface font, Activity act) {

        inflator = LayoutInflater.from(context);
        this.data = y;
        this.mcontext = context;
        this.font = font;

        callerActivity = (IObserver) act;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflator.inflate(R.layout.brandview, parent, false);
        MyViewholder holder = new MyViewholder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, int position) {

        final allbrandsdata current = data.get(position);
        holder.brandname.setText(current.brandname);
        holder.desc.setText(current.desc);
        holder.category.setText(current.category);
        if (current.followed.equals("yes")) {
            holder.followbutton.setVisibility(View.INVISIBLE);
            holder.unfollowbutton.setVisibility(View.VISIBLE);
            holder.tick.setVisibility(View.VISIBLE);
        } else {
            holder.followbutton.setVisibility(View.VISIBLE);
            holder.unfollowbutton.setVisibility(View.INVISIBLE);
            holder.tick.setVisibility(View.INVISIBLE);
        }
        holder.followbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String PREFS_NAME = "MyPrefsFile";

                SharedPreferences settings = mcontext.getSharedPreferences(PREFS_NAME, 0);
                String mobile_no = settings.getString("mobile_no", "");

                Follow_requestData(current.brand_id, mobile_no, 1);
                holder.followbutton.setVisibility(View.INVISIBLE);
                holder.unfollowbutton.setVisibility(View.VISIBLE);
                holder.tick.setVisibility(View.VISIBLE);
            }
        });

        holder.unfollowbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String PREFS_NAME = "MyPrefsFile";

                SharedPreferences settings = mcontext.getSharedPreferences(PREFS_NAME, 0);
                String mobile_no = settings.getString("mobile_no", "");

                Follow_requestData(current.brand_id, mobile_no, 0);
                holder.followbutton.setVisibility(View.VISIBLE);
                holder.unfollowbutton.setVisibility(View.INVISIBLE);
                holder.tick.setVisibility(View.INVISIBLE);

            }
        });
        if (current.brandlogo == "" || current.brandlogo == null) {

        } else {
            Picasso.with(mcontext).load("http://192.168.0.21:1337/show_brand_icon/" + current.brandlogo)
                    .into(holder.brandlogo, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                        }
                    });
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public interface ClickListener {
        void itemClicked(View v, int position);


    }

    class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView brandcover;
        ImageView brandlogo;
        TextView brandname;
        TextView desc;
        TextView category;
        Button followbutton;
        Button unfollowbutton;
        ImageView tick;

        public MyViewholder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            brandname = (TextView)itemView.findViewById(R.id.brandname);
            desc = (TextView)itemView.findViewById(R.id.desc);
            category = (TextView)itemView.findViewById(R.id.category);
            brandlogo = (ImageView)itemView.findViewById(R.id.brandlogo);
            followbutton = (Button)itemView.findViewById(R.id.followbutton);
            unfollowbutton = (Button)itemView.findViewById(R.id.unfollowbutton);
            tick = (ImageView)itemView.findViewById(R.id.tick);
        }

        @Override
        public void onClick(View v) {
//
            callerActivity.onItemClicked(getAdapterPosition());
            /*
            Context context = itemView.getContext();
            Intent intent = new Intent(context, brandtimeline.class);
            int position = getAdapterPosition();
            Bundle b = new Bundle();
            Log.d("timeline", "brand_id:" + data.get(position).brand_id);
            b.putString("brand_id", data.get(position).brand_id);
            intent.putExtras(b);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ((Activity)context).startActivityForResult(intent, 1); */
        //    context.startActivity(intent);

        }
    }

    public static String USER_FOLLOW_ENDPOINT = "http://192.168.0.21:1337/user_follow";

    public void Follow_requestData(String brand_id, String jeeb_no, Integer follow) {
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(mcontext);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(USER_FOLLOW_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_User_Follow(jeeb_no, brand_id, follow, new Callback<success_message>() {
                @Override
                public void success(success_message data, Response response) {
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + data.getSuccess());
                    Log.d("hii", "Message : " + data.getMessage());
                    if (data.getSuccess() == 1) {
                        Log.d("hii", "removed follow_notifications");
                    } else {
                        Log.d("hii", "remove follow notifications error" + data.getMessage());
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.d("hii", "failure");
                    Log.e("hii", retrofitError.toString());
                }
            });
        } else {
            Log.d("hii", "api null");
        }
    }
}
