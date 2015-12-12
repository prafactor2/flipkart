package com.example.khaalijeb.newlistview_module;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Lord Voldemort on 29/11/2015.
 */
public class promocodeviewholdersummary extends RecyclerView.ViewHolder {


    TextView summary;


    public TextView getSummary() {
        return summary;
    }

    public void setSummary(TextView summary) {
        this.summary = summary;
    }

    public promocodeviewholdersummary(View itemView) {
        super(itemView);

        summary = (TextView)itemView.findViewById(R.id.summary);
    }
}