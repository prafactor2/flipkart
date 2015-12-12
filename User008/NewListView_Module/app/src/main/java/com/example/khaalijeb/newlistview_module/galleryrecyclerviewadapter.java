package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


/**
 * Created by Lord Voldemort on 17/09/2015.
 */

public class galleryrecyclerviewadapter extends RecyclerView.Adapter<galleryrecyclerviewadapter.MyViewholder> {

    private ClickListener clickListener;
    private LayoutInflater inflator;
    private Context mcontext;
    Typeface font;

    public Button b1;
    public ImageButton b2;

    List<information> data = Collections.emptyList();

    public galleryrecyclerviewadapter(Context context, List<information> y, Typeface font) {

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

        View view = inflator.inflate(R.layout.custom_row5, parent, false);
        MyViewholder holder = new MyViewholder(view);

        b1 = (Button)view.findViewById(R.id.pay);
        b2 = (ImageButton)view.findViewById(R.id.gallery);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, int position) {

        information current = data.get(position);
        holder.icon.setImageResource(current.image);



    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ClickListener {
        void itemClicked(View v, int position);


    }

    class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView icon;



        public MyViewholder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

              icon = (ImageView) itemView.findViewById(R.id.listicon);



        }

        @Override
        public void onClick(View v) {

            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }

        }
    }
}
