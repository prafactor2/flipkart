package com.example.khaalijeb.newlistview_module;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Lord Voldemort on 29/11/2015.
 */
public class promocodeviewholderwhere extends RecyclerView.ViewHolder {


    TextView address;
    TextView mobilenumber;


    public TextView getAddress() {
        return address;
    }

    public void setAddress(TextView address) {
        this.address = address;
    }

    public TextView getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(TextView mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public promocodeviewholderwhere(View itemView) {
        super(itemView);

        address = (TextView)itemView.findViewById(R.id.address);
        mobilenumber = (TextView)itemView.findViewById(R.id.mobilenumber);
    }
}