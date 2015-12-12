package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Lord Voldemort on 29/11/2015.
 */
public class ComplexRecyclerViewAdapterPromoCode extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Typeface font;

    private LayoutInflater inflator;
    // The items to display in your RecyclerView
    private List<Object> items;
    Context mcontext;
    private CustomAlertDialog customAlertDialog;

    private final int TITLE = 0, SUMMARY = 1, REDEEM = 2, WHERE = 3;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ComplexRecyclerViewAdapterPromoCode(Context context, List<Object> items, Typeface font) {

        inflator = LayoutInflater.from(context);
        this.items = items;
        this.font = font;
        this.mcontext = context;


    }

    private ClickListener clickListener;



    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof promocodeoffertitledata) {
            return TITLE;
        } else if (items.get(position) instanceof promocodeoffersummarydata) {
            return SUMMARY;
        } else if (items.get(position) instanceof promocodeofferredeemdata) {
            return REDEEM;
        } else if (items.get(position) instanceof promocodeofferwheredata) {
            return WHERE;
        }
        return -1;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)  {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case TITLE:
                View v1 = inflater.inflate(R.layout.promocodeoffertitle_layout, viewGroup, false);
                viewHolder = new promocodeviewholdertitle(v1);
                break;

            case SUMMARY:
                View v2 = inflater.inflate(R.layout.promocodeoffersummary_layout, viewGroup, false);
                viewHolder = new promocodeviewholdersummary(v2);
                break;

            case REDEEM:
                View v3 = inflater.inflate(R.layout.promocodeofferredeem_layout, viewGroup, false);
                viewHolder = new promocodeviewholderredeem(v3);
                break;

            case WHERE:
                View v4 = inflater.inflate(R.layout.promocodeofferwhere_layout, viewGroup, false);
                viewHolder = new promocodeviewholderwhere(v4);
                break;

            default:
                View v = inflater.inflate(R.layout.simpletextviewholder, viewGroup, false);
                viewHolder = new RecyclerViewSimpleTextViewHolder(v);
        }
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)  {
        switch (viewHolder.getItemViewType()) {
            case TITLE:
               promocodeviewholdertitle vh1 = (promocodeviewholdertitle) viewHolder;
                configureViewHolder1(vh1, position);

                break;
            case SUMMARY:
                promocodeviewholdersummary vh2 = (promocodeviewholdersummary) viewHolder;
                configureViewHolder2(vh2, position);
                break;
            case REDEEM:
                promocodeviewholderredeem vh3 = (promocodeviewholderredeem) viewHolder;
                configureViewHolder3(vh3, position);
                break;
            case WHERE:
                promocodeviewholderwhere vh4 = (promocodeviewholderwhere) viewHolder;
                configureViewHolder4(vh4, position);
                break;

            default:

                //  RecyclerViewSimpleTextViewHolder vh = (RecyclerViewSimpleTextViewHolder) viewHolder;
                // configureDefaultViewholder(vh);
        }
    }


    private void configureDefaultViewholder(RecyclerViewSimpleTextViewHolder vh) {

        vh.getLabel().setText((""));
    }

    private void configureViewHolder1(promocodeviewholdertitle vh1, int position) {
        promocodeoffertitledata title = (promocodeoffertitledata) items.get(position);
        if (title != null) {

            vh1.getTitle().setText(title.title);
            vh1.getTitle().setTypeface(font);

        }
    }



    private void configureViewHolder2(final promocodeviewholdersummary vh2, int position) {

        final promocodeoffersummarydata summary = (promocodeoffersummarydata) items.get(position);
        if (summary != null) {

            vh2.getSummary().setText(summary.summary);

        }

    }


    private void configureViewHolder3(final promocodeviewholderredeem vh3, int position) {

        promocodeofferredeemdata redeem = (promocodeofferredeemdata) items.get(position);
        if (redeem != null) {

            vh3.getRedeem().setText(redeem.redeem);
            vh3.getMore().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(vh3.getRedeem().getVisibility() == View.VISIBLE){

                        vh3.getRedeem().setVisibility(View.GONE);
                        vh3.getDownline().setVisibility(View.GONE);

                    }else {

                        vh3.getRedeem().setVisibility(View.VISIBLE);
                        vh3.getDownline().setVisibility(View.VISIBLE);

                    }
                }
            });
        }
    }

    private void configureViewHolder4(promocodeviewholderwhere vh4, int position) {

        promocodeofferwheredata where = (promocodeofferwheredata) items.get(position);
        if (where != null) {

            vh4.getAddress().setText(where.address);
            vh4.getMobilenumber().setText(where.mobilenumber);


        }
    }



    public interface ClickListener {

        void viewClicked(View v, int viewtype);
    }
}
