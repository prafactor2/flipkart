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
 * Created by Lord Voldemort on 15/11/2015.
 */
public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewholder>{

    private ClickListener clickListener;
    private LayoutInflater inflator;
    private Context mcontext;
    Typeface font;

    //  public Button b1;

    List<brandshopsearchdata> data = Collections.emptyList();
    public MyAdapter2(Context context, List<brandshopsearchdata> y,Typeface font){

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

        View view = inflator.inflate(R.layout.custom_rowx, parent, false);
        MyViewholder holder = new MyViewholder(view);

        // b1 = (Button)view.findViewById(R.id.b1);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, int position) {

        brandshopsearchdata current = data.get(position);

        holder.brandname.setText(current.brandname);
        holder.rating.setText(current.rating);
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ClickListener {
        void itemClicked(View v, int position);


    }

    class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

       TextView brandname;
        ImageView brandlogo;
        TextView rating;



        public MyViewholder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            brandname = (TextView)itemView.findViewById(R.id.brandname);
            brandlogo = (ImageView)itemView.findViewById(R.id.brandlogo);
            rating = (TextView)itemView.findViewById(R.id.rating);


        }

        @Override
        public void onClick(View v) {

            Context context = itemView.getContext();
            Intent intent = new Intent(context, brandprofile.class);
            int position = getAdapterPosition();
            Bundle b = new Bundle();
            Log.d("timeline", "brand_id:" + data.get(position).brand_id);
            Log.d("brand_id", "brand_id2: " + data.get(position).brand_id);
            b.putString("brand_id", data.get(position).brand_id);
            intent.putExtras(b);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
    }
}