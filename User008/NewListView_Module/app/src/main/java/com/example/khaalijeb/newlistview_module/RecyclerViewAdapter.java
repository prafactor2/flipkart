package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Rajdeep Singh on 02-06-2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private ClickListener clickListener;
    private  LayoutInflater inflater;
    Typeface font;
    List<SingleView> data = Collections.emptyList();
    int flag;
    public RecyclerViewAdapter(Context context, List<SingleView> data, int flag, Typeface font) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.flag = flag;
        this.font = font;
    }
    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (flag == 0) {
            view = inflater.inflate(R.layout.row_view, parent, false);
        }
        else if (flag == 1) {
            view = inflater.inflate(R.layout.item_view, parent, false);
        }
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SingleView current = data.get(position);
        holder.text.setText(current.title);
        holder.text.setTypeface(font);
        holder.image.setImageResource(current.images);
    }
    @Override
    public int getItemCount() {

        return data.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            text = (TextView) itemView.findViewById(R.id.textView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }
        }


    }
    public interface ClickListener {
        public void itemClicked(View v, int position);
    }
}
