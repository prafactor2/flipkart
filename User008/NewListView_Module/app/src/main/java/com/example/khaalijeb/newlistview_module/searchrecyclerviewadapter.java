package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;


/**
 * Created by Lord Voldemort on 17/09/2015.
 */

public class searchrecyclerviewadapter extends RecyclerView.Adapter<searchrecyclerviewadapter.MyViewholder> {

    private ClickListener clickListener;
    private LayoutInflater inflator;
    private Context mcontext;
    Typeface font;

    public Button b1;
    public ImageButton b2;

    List<information> data = Collections.emptyList();

    public searchrecyclerviewadapter(Context context, List<information> y, Typeface font) {

        inflator = LayoutInflater.from(context);
        this.data = y;
        this.mcontext = context;
        this.font = font;


    }



    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflator.inflate(R.layout.custom_row3, parent, false);
        MyViewholder holder = new MyViewholder(view);

         b1 = (Button)view.findViewById(R.id.pay);
        b2 = (ImageButton)view.findViewById(R.id.gallery);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, int position) {

        information current = data.get(position);
        holder.title.setText(current.title);
        //   holder.icon.setImageResource(current.iconId);
        holder.address.setText(current.Address);
        holder.type.setText(current.type);
        holder.rating.setText("" + current.rating);
        holder.distance.setText(current.distance + "km");
        holder.address.setTypeface(font);
        holder.title.setTypeface(font);

        if (current.total_photos == 0) {
            // No Photos To Show Do Something Here.
        } else if (current.total_photos == 1) {
            Picasso.with(mcontext).load("http://192.168.0.21:1337/show_nearby_search_merchant_photos/" + current.photo_1).resize(350, 100).centerCrop()
                    .into(holder.image1, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                        }
                    });
        } else if (current.total_photos == 2) {
            Picasso.with(mcontext).load("http://192.168.0.21:1337/show_nearby_search_merchant_photos/" + current.photo_1).resize(350, 100).centerCrop()
                    .into(holder.image1, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                        }
                    });

            Picasso.with(mcontext).load("http://192.168.0.21:1337/show_nearby_search_merchant_photos/" + current.photo_2).resize(350, 100).centerCrop()
                    .into(holder.image2, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                        }
                    });
        } else {
            Log.d("hii", "photo_1" + current.photo_1);
            Log.d("hii", "photo_2" + current.photo_2);

            Picasso.with(mcontext).load("http://192.168.0.21:1337/show_nearby_search_merchant_photos/" + current.photo_1).resize(350, 100).centerCrop()
                    .into(holder.image1, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                        }
                    });

            Picasso.with(mcontext).load("http://192.168.0.21:1337/show_nearby_search_merchant_photos/" + current.photo_2).resize(350, 100).centerCrop()
                    .into(holder.image2, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                        }
                    });
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mcontext, MainActivity.class);
                mcontext.startActivity(i);

            }

        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mcontext, gallery.class);
                mcontext.startActivity(i);

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
        ImageView icon;
        TextView address;
        TextView type;
        TextView rating;
        TextView distance;
        ImageView image1;
        ImageView image2;


        public MyViewholder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.listtext);
            //  icon = (ImageView) itemView.findViewById(R.id.listicon);
            address = (TextView) itemView.findViewById(R.id.address);
            type = (TextView) itemView.findViewById(R.id.type);
            rating = (TextView) itemView.findViewById(R.id.rating);
            distance = (TextView) itemView.findViewById(R.id.distance);
            image1 = (ImageView) itemView.findViewById(R.id.image1);
            image2 = (ImageView) itemView.findViewById(R.id.image2);
        }

        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();
            Intent intent = new Intent(context, merchantprofile.class);
            int position = getAdapterPosition();
            Log.d("pos", "position: " + position);
            Log.d("cover", "jeb_no: " + data.get(position).jeb_no);
            Bundle b = new Bundle();
            b.putString("from_where", "search");
            b.putString("shop_name", data.get(position).title); // inside bundle you can many types of values
            b.putString("merchant_jeb_no", data.get(position).jeb_no);
            b.putDouble("merchant_rating", data.get(position).rating);
            b.putString("merchant_address", data.get(position).Address);
            b.putString("area1", data.get(position).area1);
            b.putInt("total_photos", data.get(position).total_photos);
            if (data.get(position).total_photos == 0) {

            } else if (data.get(position).total_photos == 1) {
                b.putString("merchant_photo_1", data.get(position).photo_1);
            } else if (data.get(position).total_photos == 2) {
                b.putString("merchant_photo_1", data.get(position).photo_1);
                b.putString("merchant_photo_2", data.get(position).photo_2);
            } else {
                b.putString("merchant_photo_1", data.get(position).photo_1);
                b.putString("merchant_photo_2", data.get(position).photo_2);
            }
            intent.putExtras(b);
            context.startActivity(intent);
        }
    }
}

