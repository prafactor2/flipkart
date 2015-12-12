package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Lord Voldemort on 05/11/2015.
 */
public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageRecyclerViewAdapter.MyViewholder> {

    private ClickListener clickListener;
    private LayoutInflater inflator;
    private Context mcontext;




    List<Imagedata> data = Collections.emptyList();

    public ImageRecyclerViewAdapter(Context context, List<Imagedata> y) {

        inflator = LayoutInflater.from(context);
        this.data = y;
        this.mcontext = context;



    }



    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflator.inflate(R.layout.image_item, parent, false);
        MyViewholder holder = new MyViewholder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, int position) {

        Imagedata current = data.get(position);
        holder.icon.setImageResource(current.offersimage);



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

            icon = (ImageView) itemView.findViewById(R.id.offerimagex);



        }

        @Override
        public void onClick(View v) {

            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }

        }
    }
}