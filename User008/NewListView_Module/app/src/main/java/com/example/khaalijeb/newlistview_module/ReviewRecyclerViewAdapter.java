package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Lord Voldemort on 03/11/2015.
 */
public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.MyViewholder> {

    private ClickListener clickListener;
    private LayoutInflater inflator;
    private Context mcontext;
    Typeface font;

    List<brandreviewdata> data = Collections.emptyList();

    public ReviewRecyclerViewAdapter(Context context, List<brandreviewdata> y, Typeface font) {

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

        View view = inflator.inflate(R.layout.layout_viewholder5, parent, false);
        MyViewholder holder = new MyViewholder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, int position) {

        brandreviewdata current = data.get(position);
        holder.username.setText(current.username);
        //   holder.icon.setImageResource(current.iconId);
        holder.rating.setText("" + current.rating);
        holder.review.setText(current.review);



    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ClickListener {
        void itemClicked(View v, int position);


    }

    class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView username;
        TextView rating;
        TextView review;

        public MyViewholder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            username = (TextView)itemView.findViewById(R.id.text1);
            rating = (TextView)itemView.findViewById(R.id.rating);
            review = (TextView)itemView.findViewById(R.id.text2);

        }

        @Override
        public void onClick(View v) {

            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }

        }
    }
}
