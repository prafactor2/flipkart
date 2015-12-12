package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Lord Voldemort on 13/11/2015.
 */
public class RecyclerViewStores extends RecyclerView.Adapter<RecyclerViewStores.MyViewholder>{

    private ClickListener clickListener;
    private LayoutInflater inflator;
    private Context mcontext;

    public Button b1;

    List<storedata> data = Collections.emptyList();
    public RecyclerViewStores(Context context, List<storedata> y){

        inflator = LayoutInflater.from(context);
        this.data = y;
        this.mcontext = context;


    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflator.inflate(R.layout.layout_stores, parent, false);
        MyViewholder holder = new MyViewholder(view);

        // b1 = (Button)view.findViewById(R.id.b1);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, int position) {

        storedata current = data.get(position);
        //   holder.title.setText(current.title);
       holder.area1.setText(current.area1);
        holder.storeaddress.setText(current.storeaddress);
        holder.distance.setText(current.distance + "km");



  /*      b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mcontext,Main2Activity.class);
                mcontext.startActivity(i);

            }

        });

*/
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ClickListener {
        void itemClicked(View v, int position);


    }

    class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //   TextView title;
       TextView area1;
        TextView storeaddress;
        TextView distance;


        public MyViewholder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            //   title = (TextView) itemView.findViewById(R.id.listtext);
            area1 = (TextView)itemView.findViewById(R.id.area1);
            storeaddress = (TextView)itemView.findViewById(R.id.storeaddress);
            distance = (TextView)itemView.findViewById(R.id.distance);


        }

        @Override
        public void onClick(View v) {

            Context context = itemView.getContext();
            Intent intent = new Intent(context, merchantprofile.class);
            int position = getAdapterPosition();
            Bundle b = new Bundle();
            Log.d("timeline", "brand_id:" + data.get(position).jeb_no);
            b.putString("from_where", "stores");
            b.putString("merchant_jeb_no", data.get(position).jeb_no);
            b.putString("merchant_address", data.get(position).storeaddress);
            b.putString("area1", data.get(position).area1);
            intent.putExtras(b);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
    }
}
