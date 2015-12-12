package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Typeface font;
    private LayoutInflater inflator;
    private List<Object> items;
    Context mcontext;
    String photo_1, photo_2, merchant_jeb_no;

    private final int USER = 0, IMAGE = 1, CONTENT = 2, MENU = 3,PHOTOS = 4, REVIEW = 5, REVIEW2 = 6;

    public ComplexRecyclerViewAdapter(Context context,List<Object> items, Typeface font) {

        inflator = LayoutInflater.from(context);
        this.items = items;
        this.font = font;
        this.mcontext = context;
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof User) {
            return USER;
        } else if (items.get(position) instanceof Header) {
            return IMAGE;
        } else if (items.get(position) instanceof CONTENT){
            return CONTENT;
        } else if (items.get(position) instanceof Menu){
            return MENU;
        } else if (items.get(position) instanceof photos){
            return PHOTOS;
        }   else if(items.get(position) instanceof REVIEW){
            return REVIEW;
        }   else if(items.get(position) instanceof REVIEW2){
            return REVIEW2;
        }
        return -1;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case USER:
                View v1 = inflater.inflate(R.layout.layout_viewholder1, viewGroup, false);
                viewHolder = new ViewHolder1(v1);
                break;

            case IMAGE:
                View v2 = inflater.inflate(R.layout.layout_viewholder2, viewGroup, false);
                viewHolder = new ViewHolder2(v2);
                break;
            case CONTENT:

                View v3 = inflater.inflate(R.layout.layout_viewholder3,viewGroup,false);
                viewHolder = new ViewHolder3(v3);
                break;
            case MENU:
                View v4 = inflater.inflate(R.layout.layout_viewholder4,viewGroup,false);
                viewHolder = new ViewHolder4(v4);
                break;

            case PHOTOS:
                View v5 = inflater.inflate(R.layout.photosholder,viewGroup,false);
                viewHolder = new photosholder(v5);
                break;
            case REVIEW:

                View v6 = inflater.inflate(R.layout.layout_viewholder5,viewGroup,false);
                viewHolder = new ViewHolder5(v6);
                break;

            case REVIEW2:
                View v7 = inflater.inflate(R.layout.layout_viewholder6,viewGroup,false);
                viewHolder = new ViewHolder6(v7);
                break;

            default:
                View v = inflater.inflate(R.layout.simpletextviewholder,viewGroup,false);
                viewHolder = new RecyclerViewSimpleTextViewHolder(v);
        }
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case USER:
                ViewHolder1 vh1 = (ViewHolder1) viewHolder;
                configureViewHolder1(vh1, position);

                break;
            case IMAGE:
                ViewHolder2 vh2 = (ViewHolder2) viewHolder;
                configureViewHolder2(vh2);
                break;
            case CONTENT:
                ViewHolder3 vh3 = (ViewHolder3) viewHolder;
                configureViewHolder3(vh3, position);
                break;
            case MENU:
                ViewHolder4 vh4 = (ViewHolder4) viewHolder;
                configureViewHolder4(vh4, position);
                break;
            case PHOTOS:
                photosholder vh5 = (photosholder) viewHolder;
                configurephotosholder(vh5);
                break;

            case REVIEW:
                ViewHolder5 vh6 = (ViewHolder5) viewHolder;
                configureViewHolder5(vh6, position);
                break;

            case REVIEW2:
                ViewHolder6 vh7 = (ViewHolder6) viewHolder;
                configureViewHolder6(vh7,position);
                break;
            default:

                //  RecyclerViewSimpleTextViewHolder vh = (RecyclerViewSimpleTextViewHolder) viewHolder;
                // configureDefaultViewholder(vh);
        }
    }


    private void configureDefaultViewholder(RecyclerViewSimpleTextViewHolder vh){

        vh.getLabel().setText((""));
    }

    private void configureViewHolder1(ViewHolder1 vh1, int position) {
        User user = (User) items.get(position);
        if (user != null) {
            vh1.getLabel1().setText(user.name);
            //  vh1.getLabel1().setTypeface(font);
            vh1.getLabel2().setText(user.hometown);
            vh1.getLabel2().setTypeface(font);
        }
    }

    private void configureViewHolder2(final ViewHolder2 vh2) {

        //    vh2.getImageView().setImageResource(Header.header);
        vh2.getMerchant_title().setText(Header.merchant_title);
        vh2.getMerchant_Area().setText(Header.merchant_area);
        vh2.getMerchant_votes().setText(Header.merchant_votes);
        vh2.getMerchant_rating().setText(Header.merchant_rating);
        vh2.getMerchant_review().setText(Header.merchant_review);

        if (Header.header == "" || Header.header == null) {
            // No Cover To show Do Something Here
        } else {
            Picasso.with(mcontext).load("http://192.168.0.21:1337/show_merchant_cover/" + Header.header).resize(350, 100).centerCrop()
                    .into(vh2.getImageView(), new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d("cover", "success");
                        }

                        @Override
                        public void onError() {
                            Log.d("cover", "failure");
                        }
                    });
        }
        vh2.getLike().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                vh2.getLike().setImageResource(R.drawable.heart);

                Toast.makeText(mcontext, "Add to Favourites", Toast.LENGTH_SHORT).show();


            }
        });

    }
    private void configureViewHolder3(ViewHolder3 vh3, int position) {

        CONTENT content = (CONTENT) items.get(position);

        vh3.getLabel3().setText(content.text);

    }

    private void configureViewHolder4(ViewHolder4 vh4, int position) {
        Menu menu = (Menu) items.get(position);
        if (menu!= null) {
            vh4.getLabel3().setText(menu.name);
            //  vh1.getLabel1().setTypeface(font);

            if (menu.total_photos == 0) {

            } else if (menu.total_photos == 1) {
                Picasso.with(mcontext).load("http://192.168.0.3:1337/show_nearby_search_merchant_photos/" + menu.menu).resize(350, 100).centerCrop()
                        .into(vh4.getImage(), new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d("cover", "success");
                            }

                            @Override
                            public void onError() {
                                Log.d("cover", "failure");
                            }
                        });
            } else if (menu.total_photos == 2) {
                photo_1 = menu.menu;
                photo_2 = menu.menu2;
                Picasso.with(mcontext).load("http://192.168.0.3:1337/show_nearby_search_merchant_photos/" + menu.menu).resize(350, 100).centerCrop()
                        .into(vh4.getImage(), new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d("cover", "success");
                            }

                            @Override
                            public void onError() {
                                Log.d("cover", "failure");
                            }
                        });
                Picasso.with(mcontext).load("http://192.168.0.3:1337/show_nearby_search_merchant_photos/" + menu.menu2).resize(350, 100).centerCrop()
                        .into(vh4.getImage2(), new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d("cover", "success");
                            }

                            @Override
                            public void onError() {
                                Log.d("cover", "failure");
                            }
                        });
            } else {
                photo_1 = menu.menu;
                photo_2 = menu.menu2;
                Picasso.with(mcontext).load("http://192.168.0.3:1337/show_nearby_search_merchant_photos/" + menu.menu).resize(350, 100).centerCrop()
                        .into(vh4.getImage(), new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d("cover", "success");
                            }

                            @Override
                            public void onError() {
                                Log.d("cover", "failure");
                            }
                        });
                Picasso.with(mcontext).load("http://192.168.0.3:1337/show_nearby_search_merchant_photos/" + menu.menu2).resize(350, 100).centerCrop()
                        .into(vh4.getImage2(), new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d("cover", "success");
                            }

                            @Override
                            public void onError() {
                                Log.d("cover", "failure");
                            }
                        });
            }

            vh4.getLabel3().setTypeface(font);
            vh4.getImage3().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mcontext, GridViewActivity.class);
                    Bundle b = new Bundle();
                    b.putString("merchant_jeb_no", Header.merchant_jeb_no);
                    b.putString("photo_1", photo_1);
                    b.putString("photo_2", photo_2);
                    i.putExtras(b);
                    mcontext.startActivity(i);

                }
            });
        }
    }


    private void configurephotosholder(photosholder vh5) {

        vh5.getImagex().setImageResource(photos.header);
        vh5.getImagex().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcontext, GridViewActivity2.class);
                Bundle b = new Bundle();
                b.putString("merchant_id", Header.merchant_jeb_no);
                i.putExtras(b);
                mcontext.startActivity(i);
            }
        });

    }
    private void configureViewHolder5(ViewHolder5 vh6, int position) {

        REVIEW review = (REVIEW) items.get(position);
        if(review != null) {
            vh6.getName().setText(review.name);
            vh6.getReview().setText(review.review);
            vh6.getRating().setText(review.rating);
            vh6.getName().setTypeface(font);

        }

    }
    private void configureViewHolder6(ViewHolder6 vh7, int position) {

        REVIEW2 review = (REVIEW2) items.get(position);
        if(review != null) {
            vh7.getName().setText(review.name);
            vh7.getReview().setText(review.review);
            vh7.getRating().setText(review.rating);
            vh7.getName().setTypeface(font);
            vh7.getButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mcontext, Reviewactivity.class);
                    Bundle b = new Bundle();
                    b.putString("merchant_id", Header.merchant_jeb_no);
                    i.putExtras(b);
                    mcontext.startActivity(i);
                }
            });
        }
    }
}