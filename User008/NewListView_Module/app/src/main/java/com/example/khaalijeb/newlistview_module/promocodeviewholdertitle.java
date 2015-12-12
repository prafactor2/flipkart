package com.example.khaalijeb.newlistview_module;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Lord Voldemort on 29/11/2015.
 */
public class promocodeviewholdertitle extends RecyclerView.ViewHolder {


    TextView title;


    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public promocodeviewholdertitle(View itemView) {
        super(itemView);

       title = (TextView)itemView.findViewById(R.id.title);
    }
}