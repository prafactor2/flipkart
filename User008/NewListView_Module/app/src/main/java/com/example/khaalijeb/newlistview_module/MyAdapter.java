package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


/**
 * Created by Lord Voldemort on 17/09/2015.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewholder>{

    private ClickListener clickListener;
    private LayoutInflater inflator;
    private Context mcontext;
    Typeface font;

  //  public Button b1;

    List<information> data = Collections.emptyList();
    public MyAdapter(Context context, List<information> y,Typeface font){

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

        View view = inflator.inflate(R.layout.custom_row2, parent, false);
        MyViewholder holder = new MyViewholder(view);

       // b1 = (Button)view.findViewById(R.id.b1);
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
        holder.votes.setText(current.votes);
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ClickListener {
        void itemClicked(View v, int position);


    }

    class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        ImageView icon;
        TextView address;
        TextView type;
        TextView rating;
        TextView distance;
        TextView votes;


        public MyViewholder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.listtext);
          //  icon = (ImageView) itemView.findViewById(R.id.listicon);
            address =(TextView) itemView.findViewById(R.id.address);
            type = (TextView) itemView.findViewById(R.id.type);
            rating = (TextView)itemView.findViewById(R.id.rating);
            distance = (TextView)itemView.findViewById(R.id.distance);
            votes = (TextView)itemView.findViewById(R.id.votes);


        }

        @Override
        public void onClick(View v) {

            Context context = itemView.getContext();
            Intent intent = new Intent(context, merchantprofile.class);
            int position = getAdapterPosition();
            Log.d("pos", "position: " + position);
            Log.d("cover", "jeb_no: " + data.get(position).jeb_no);
            Bundle b = new Bundle();
            b.putString("from_where", "with_photos");
            b.putString("shop_name", data.get(position).title); // inside bundle you can many types of values
            b.putString("merchant_jeb_no", data.get(position).jeb_no);
            b.putDouble("merchant_rating", data.get(position).rating);
            b.putString("merchant_address", data.get(position).Address);
            b.putString("area1", data.get(position).area1);
        /*    b.putInt("total_photos", data.get(position).total_photos);
            if (data.get(position).total_photos == 0) {

            } else if (data.get(position).total_photos == 1) {
                b.putString("merchant_photo_1", data.get(position).photo_1);
            } else if (data.get(position).total_photos == 2) {
                b.putString("merchant_photo_1", data.get(position).photo_1);
                b.putString("merchant_photo_2", data.get(position).photo_2);
            } else {
                b.putString("merchant_photo_1", data.get(position).photo_1);
                b.putString("merchant_photo_2", data.get(position).photo_2);
            }*/
            intent.putExtras(b);
            context.startActivity(intent);

        }

    }

}
