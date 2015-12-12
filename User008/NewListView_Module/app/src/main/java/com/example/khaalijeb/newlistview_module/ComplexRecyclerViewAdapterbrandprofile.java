package com.example.khaalijeb.newlistview_module;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Lord Voldemort on 25/10/2015.
 */
public class ComplexRecyclerViewAdapterbrandprofile extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Typeface font;
    private LayoutInflater inflator;
    // The items to display in your RecyclerView
    private List<Object> items;
    Context mcontext;


    private final int HEADER = 0, DESC = 1, REVIEW = 2, REVIEW2 = 3;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ComplexRecyclerViewAdapterbrandprofile(Context context, List<Object> items, Typeface font) {

        inflator = LayoutInflater.from(context);
        this.items = items;
        this.font = font;
        this.mcontext = context;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof brandheaderdata2) {
            return HEADER;
        } else if (items.get(position) instanceof branddescdata) {
            return DESC;
        } else if (items.get(position) instanceof brandreviewdata) {
            return REVIEW;
        } else if (items.get(position) instanceof brandreviewdata2) {
            return REVIEW2;
        }
        return -1;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case HEADER:
                View v1 = inflater.inflate(R.layout.layout_viewholderbrandheader1, viewGroup, false);
                viewHolder = new ViewHolderBrandheader(v1);
                break;

            case DESC:
                View v2 = inflater.inflate(R.layout.layout_viewholderbranddesc, viewGroup, false);
                viewHolder = new ViewHolderBranddesc(v2);
                break;
            case REVIEW:

                View v3 = inflater.inflate(R.layout.layout_viewholder5, viewGroup, false);
                viewHolder = new ViewHolder5(v3);
                break;

            case REVIEW2:

                View v4 = inflater.inflate(R.layout.layout_viewholder6, viewGroup, false);
                viewHolder = new ViewHolder6(v4);
                break;

            default:
                View v = inflater.inflate(R.layout.simpletextviewholder, viewGroup, false);
                viewHolder = new RecyclerViewSimpleTextViewHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case HEADER:
                ViewHolderBrandheader vh1 = (ViewHolderBrandheader) viewHolder;
                configureViewHolder1(vh1, position);

                break;
            case DESC:
                ViewHolderBranddesc vh2 = (ViewHolderBranddesc) viewHolder;
                configureViewHolder2(vh2, position);
                break;
            case REVIEW:
                ViewHolder5 vh3 = (ViewHolder5) viewHolder;
                configureViewHolder3(vh3, position);
                break;
            case REVIEW2:
                ViewHolder6 vh4 = (ViewHolder6) viewHolder;
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

    private void configureViewHolder1(ViewHolderBrandheader vh1, int position) {
        brandheaderdata2 header = (brandheaderdata2) items.get(position);
        if (header != null) {

            vh1.getTitle().setText(header.title);
            vh1.getRating().setText(header.rating);
            vh1.getReview().setText(header.review);
            if (header.brandcover == "" || header.brandcover == "") {
                // Future Stuff
            } else {
                Picasso.with(mcontext).load("http://192.168.0.3:1337/show_brand_cover/" + header.brandcover)
                        .into(vh1.getBrandcover(), new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
            }
            if (header.brandicon == "" || header.brandicon == null) {
                // Future Stuff
            } else {
                Picasso.with(mcontext).load("http://192.168.0.3:1337/show_brand_icon/" + header.brandicon)
                        .into(vh1.getBrandicon(), new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
            }


        }
    }

    private void configureViewHolder2(final ViewHolderBranddesc vh2, int position) {

        final branddescdata desc = (branddescdata) items.get(position);
        if (desc != null) {

            vh2.getDesc().setText(desc.desc);

        }

    }


    private void configureViewHolder3(ViewHolder5 vh3, int position) {

        brandreviewdata review = (brandreviewdata) items.get(position);
        if (review != null) {

           vh3.getReview().setText(review.review);
            vh3.getReview().setTypeface(font);
//            vh3.getRating().setText(review.rating);
  //          vh3.getName().setText(review.username);

        }
    }

    private void configureViewHolder4(ViewHolder6 vh4, int position) {

        brandreviewdata2 review2 = (brandreviewdata2) items.get(position);
        if (review2 != null) {

            vh4.getReview().setText(review2.review);
            vh4.getReview().setTypeface(font);
            vh4.getRating().setText("" + review2.rating);
            vh4.getName().setText(review2.username);
            vh4.getButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mcontext, Reviewactivity.class);
                    mcontext.startActivity(i);
                }
            });

        }
    }
}
