package com.example.khaalijeb.newlistview_module;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Lord Voldemort on 26/10/2015.
 */
public class ViewHolderBranddesc extends RecyclerView.ViewHolder {


    TextView desc;

    public TextView getDesc() {
        return desc;
    }

    public void setDesc(TextView desc) {
        this.desc = desc;
    }

    public ViewHolderBranddesc(View itemView) {
        super(itemView);

        desc = (TextView)itemView.findViewById(R.id.desc);
    }
}
