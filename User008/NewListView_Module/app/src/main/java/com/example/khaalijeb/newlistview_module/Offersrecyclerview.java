package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.squareup.picasso.Picasso;


import java.util.Collections;
import java.util.List;


/**
 * Created by Lord Voldemort on 17/09/2015.
 */

public class Offersrecyclerview extends RecyclerView.Adapter<Offersrecyclerview.MyViewholder> {

    private ClickListener clickListener;
    private LayoutInflater inflator;
    private Context mcontext;
    Typeface font;
    CoordinatorLayout snackbar;

    public Button b1;


    List<offersdata> data = Collections.emptyList();

    public Offersrecyclerview(Context context, List<offersdata> y, Typeface font,CoordinatorLayout snackbar) {

        inflator = LayoutInflater.from(context);
        this.data = y;
        this.mcontext = context;
        this.font = font;
        this.snackbar = snackbar;


    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflator.inflate(R.layout.custom_row6, parent, false);
        MyViewholder holder = new MyViewholder(view);
        b1 = (Button) view.findViewById(R.id.getcode);



        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, int position) {

        final offersdata current = data.get(position);
        holder.title.setText(current.details);
        holder.address.setText(current.categories);

        Picasso.with(mcontext).load("http://192.168.0.21:1337/show_all_offers/" + current.offercover).resize(350, 100).centerCrop()
                .into(holder.offercover, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("cover", "success");
                    }

                    @Override
                    public void onError() {
                        Log.d("cover", "failure");
                    }
                });
        if (current.brandlogo == "") {

        } else {
            Picasso.with(mcontext).load("http://192.168.0.21:1337/show_brand_icon/" + current.brandlogo).resize(350, 100).centerCrop()
                    .into(holder.brandlogo, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d("cover", "success");
                        }

                        @Override
                        public void onError() {
                            Log.d("cover", "failure");
                        }
                    });
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Snackbar.make(snackbar, current.snackbar, Snackbar.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ClickListener {
        void itemClicked(View v, int position);


    }

    class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;

        TextView address;


        ImageView offercover;
        ImageView brandlogo;


        public MyViewholder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.title);
            //  icon = (ImageView) itemView.findViewById(R.id.listicon);
            address = (TextView) itemView.findViewById(R.id.categories);
            // type = (TextView) itemView.findViewById(R.id.type);
          //  rating = (TextView) itemView.findViewById(R.id.rating);

            offercover = (ImageView)itemView.findViewById(R.id.offercover);
            brandlogo = (ImageView)itemView.findViewById(R.id.brandlogo);


        }

        @Override
        public void onClick(View v) {

            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }

        }
    }
}
