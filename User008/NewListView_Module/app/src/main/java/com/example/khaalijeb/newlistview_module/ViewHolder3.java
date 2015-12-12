package com.example.khaalijeb.newlistview_module;

/**
 * Created by Lord Voldemort on 04/10/2015.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class ViewHolder3 extends RecyclerView.ViewHolder {


    private TextView label3;


    public ViewHolder3(View v) {
        super(v);
        label3 = (TextView)v.findViewById(R.id.text3);

    }
    public TextView getLabel3() {
        return label3;
    }

    public void setLabel3(TextView label3) {
        this.label3 = label3;
    }


}
