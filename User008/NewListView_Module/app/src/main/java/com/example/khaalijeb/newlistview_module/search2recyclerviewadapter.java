package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;


/**
 * Created by Lord Voldemort on 17/09/2015.
 */

public class search2recyclerviewadapter extends RecyclerView.Adapter<search2recyclerviewadapter.MyViewholder> {

    private ClickListener clickListener;
    private LayoutInflater inflator;
    private Context mcontext;
    Typeface font;

    public Button b1;

    List<information> data = Collections.emptyList();

    public search2recyclerviewadapter(Context context, List<information> y, Typeface font) {

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

        View view = inflator.inflate(R.layout.custom_row4, parent, false);
        MyViewholder holder = new MyViewholder(view);

        b1 = (Button) view.findViewById(R.id.pay);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, int position) {

        information current = data.get(position);
        holder.title.setText(current.title);
        //   holder.icon.setImageResource(current.iconId);
        holder.address.setText(current.Address);
        //  holder.type.setText("TYPE : " + current.type);
        holder.rating.setText("" + current.rating);
        holder.distance.setText(current.distance + "km");
        holder.address.setTypeface(font);
        holder.title.setTypeface(font);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mcontext, MainActivity.class);
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
        TextView rating;
        TextView distance;


        public MyViewholder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.listtext);
            //  icon = (ImageView) itemView.findViewById(R.id.listicon);
            address = (TextView) itemView.findViewById(R.id.address);
            // type = (TextView) itemView.findViewById(R.id.type);
            rating = (TextView) itemView.findViewById(R.id.rating);
            distance = (TextView) itemView.findViewById(R.id.distance);


        }

        @Override
        public void onClick(View v) {

            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }

        }
    }
}
