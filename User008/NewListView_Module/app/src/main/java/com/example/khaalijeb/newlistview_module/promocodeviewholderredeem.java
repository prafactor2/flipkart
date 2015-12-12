package com.example.khaalijeb.newlistview_module;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Lord Voldemort on 29/11/2015.
 */
public class promocodeviewholderredeem extends RecyclerView.ViewHolder {


    TextView redeem;
    ImageView more;
    View downline;


    public TextView getRedeem() {
        return redeem;
    }

    public void setRedeem(TextView redeem) {
        this.redeem = redeem;
    }

    public ImageView getMore() {
        return more;
    }

    public void setMore(ImageView more) {
        this.more = more;
    }

    public View getDownline() {
        return downline;
    }

    public void setDownline(View downline) {
        this.downline = downline;
    }

    public promocodeviewholderredeem(View itemView) {
        super(itemView);

        redeem = (TextView)itemView.findViewById(R.id.redeem);
        more = (ImageView)itemView.findViewById(R.id.more);
        downline = (View)itemView.findViewById(R.id.downline);

    }
}