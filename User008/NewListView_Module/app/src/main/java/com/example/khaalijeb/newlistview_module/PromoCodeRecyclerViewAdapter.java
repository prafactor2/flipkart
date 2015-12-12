package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
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
 * Created by Lord Voldemort on 28/11/2015.
 */
public class PromoCodeRecyclerViewAdapter extends RecyclerView.Adapter<PromoCodeRecyclerViewAdapter.MyViewholder> {

    private ClickListener clickListener;
    private LayoutInflater inflator;
    private Context mcontext;
    Typeface font;
    public Button b1;
    PromoCodeActivity main;
    public ImageButton b2;
    List<promocodedata> data = Collections.emptyList();

    public PromoCodeRecyclerViewAdapter(Context context, List<promocodedata> y, Typeface font, PromoCodeActivity main) {

        inflator = LayoutInflater.from(context);
        this.data = y;
        this.mcontext = context;
        this.font = font;
        this.main = main;

    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflator.inflate(R.layout.promocode_item_layout, parent, false);
        MyViewholder holder = new MyViewholder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, int position) {

        promocodedata current = data.get(position);
        if (current.brandicon == "" || current.brandicon == null) {

        } else {
                Picasso.with(mcontext).load("http://192.168.0.9:1337/show_brand_icon/" + current.brandicon)
                        .into(holder.brandicon, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
        }
        holder.brandname.setText(current.brandname);
        holder.title.setText(current.title);
        if (current.Amount == 0) {
            holder.free.setText("Free");
        } else {
            holder.free.setText("" + current.Amount + "Rs");
        }
        holder.free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.taken.setVisibility(View.VISIBLE);
                holder.free.setVisibility(View.INVISIBLE);
            }
        });

        holder.taken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.taken.setVisibility(View.INVISIBLE);
                holder.free.setVisibility(View.VISIBLE);
            }
        });

        holder.seemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            showEditDialog();

            }
        });

    }

    private void showEditDialog() {
        FragmentManager fm = main.getSupportFragmentManager();
        CustomAlertDialog2 editNameDialog = new CustomAlertDialog2();
        editNameDialog.show(fm, "f1");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ClickListener {
        void itemClicked(View v, int position);


    }

    class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView brandicon;
        TextView brandname;
        TextView title;
        Button free;
        Button taken;
        TextView seemore;


        public MyViewholder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);

            brandicon = (ImageView) itemView.findViewById(R.id.icon);
            brandname = (TextView) itemView.findViewById(R.id.brandname);
            title = (TextView) itemView.findViewById(R.id.title);
            free = (Button)itemView.findViewById(R.id.free);
            taken = (Button)itemView.findViewById(R.id.taken);
            seemore = (TextView)itemView.findViewById(R.id.seemore);

        }

        @Override
        public void onClick(View v) {

            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }

        }
    }
}
