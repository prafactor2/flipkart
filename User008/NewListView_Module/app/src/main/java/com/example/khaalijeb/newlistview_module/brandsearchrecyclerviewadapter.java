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
 * Created by Lord Voldemort on 06/11/2015.
 */
public class brandsearchrecyclerviewadapter extends RecyclerView.Adapter<brandsearchrecyclerviewadapter.MyViewholder> {

    private ClickListener clickListener;
    private LayoutInflater inflator;
    private Context mcontext;
    Typeface font;

    List<allbrandsdata> data = Collections.emptyList();

    public brandsearchrecyclerviewadapter(Context context, List<allbrandsdata> y, Typeface font) {

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

        View view = inflator.inflate(R.layout.brandsearchlayout, parent, false);
        MyViewholder holder = new MyViewholder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, int position) {

        allbrandsdata current = data.get(position);

        holder.brandname.setText(current.brandname);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ClickListener {
        void itemClicked(View v, int position);

    }

    class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView brandlogo;
        TextView brandname;
        TextView category;


        public MyViewholder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            brandname = (TextView)itemView.findViewById(R.id.brandname);

            brandlogo = (ImageView)itemView.findViewById(R.id.brandlogo);
            category = (TextView)itemView.findViewById(R.id.category);

        }

        @Override
        public void onClick(View v) {

        //    if (clickListener != null) {
          //      clickListener.itemClicked(v, getAdapterPosition());
                Context context = itemView.getContext();
                Intent intent = new Intent(context, brandtimeline.class);
                int position = getAdapterPosition();
                Bundle b = new Bundle();
                Log.d("timeline", "brand_id:" + data.get(position).brand_id);
                b.putString("brand_id", data.get(position).brand_id);
                intent.putExtras(b);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
        //    }

        }
    }
}
