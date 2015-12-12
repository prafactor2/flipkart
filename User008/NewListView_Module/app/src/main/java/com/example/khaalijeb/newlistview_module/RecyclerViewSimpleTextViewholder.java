package com.example.khaalijeb.newlistview_module;

/**
 * Created by Lord Voldemort on 04/10/2015.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;



import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Lord Voldemort on 01/09/2015.
 */
class RecyclerViewSimpleTextViewHolder extends RecyclerView.ViewHolder {

    TextView label;


    public RecyclerViewSimpleTextViewHolder(View v) {
        super(v);

        label = (TextView)v.findViewById(R.id.text);
    }

    public TextView getLabel() {
        return label;
    }

    public void setLabel(TextView label) {
        this.label = label;
    }


}
