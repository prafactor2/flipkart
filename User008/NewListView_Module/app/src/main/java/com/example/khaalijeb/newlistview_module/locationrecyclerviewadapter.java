package com.example.khaalijeb.newlistview_module;

import android.app.Activity;
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

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

/**
 * Created by Lord Voldemort on 05/11/2015.
 */
public class locationrecyclerviewadapter extends RecyclerView.Adapter<locationrecyclerviewadapter.MyViewholder> {

    private ClickListener clickListener;
    private LayoutInflater inflator;
    private Context mcontext;




    List<locationdata> data = Collections.emptyList();

    public locationrecyclerviewadapter(Context context, List<locationdata> y) {

        inflator = LayoutInflater.from(context);
        this.data = y;
        this.mcontext = context;



    }



    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflator.inflate(R.layout.location_item, parent, false);
    //    Activity activity = (Activity) mcontext;
        MyViewholder holder = new MyViewholder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, int position) {

        locationdata current = data.get(position);
        holder.location.setText(current.location);



    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ClickListener {
        void itemClicked(View v, int position);


    }

    class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //    private Activity mActivity;
        TextView location;



        public MyViewholder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            location = (TextView) itemView.findViewById(R.id.location);
        //    this.mActivity = mActivity;



        }

        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();
            Activity act = (Activity) context;
            Intent returnIntent = new Intent();
            int position = getAdapterPosition();
            returnIntent.putExtra("auto_detect", "no");
            returnIntent.putExtra("result", data.get(position).location);
            act.setResult(Activity.RESULT_OK, returnIntent);
            returnIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            act.finish();

        }
    }
}