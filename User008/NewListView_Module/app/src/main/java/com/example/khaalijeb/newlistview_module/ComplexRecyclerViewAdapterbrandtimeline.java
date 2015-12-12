package com.example.khaalijeb.newlistview_module;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import models.success_message;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by Lord Voldemort on 03/11/2015.
 */
public class ComplexRecyclerViewAdapterbrandtimeline extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Typeface font;
    private LayoutInflater inflator;
    // The items to display in your RecyclerView
    private List<Object> items;
    Context mcontext;


    private final int HEADER = 0, DESC = 1, STOCK = 4, OFFERS = 5, OTHERS = 6;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ComplexRecyclerViewAdapterbrandtimeline(Context context, List<Object> items, Typeface font) {

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
        if (items.get(position) instanceof brandheaderdata) {
            return HEADER;
        } else if (items.get(position) instanceof branddescdata) {
            return DESC;
        } else if(items.get(position) instanceof brandstockdata) {
            return STOCK;
        } else if (items.get(position) instanceof brandoffersdata) {
            return OFFERS;
        } else if (items.get(position) instanceof brandothersdata) {
            return OTHERS;
        }
        return -1;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {

            case HEADER:
                View v1 = inflater.inflate(R.layout.layout_viewholderbrandheader2, viewGroup, false);
                viewHolder = new ViewHolderBrandheader(v1);
                break;

            case DESC:
                View v2 = inflater.inflate(R.layout.layout_viewholderbranddesc, viewGroup, false);
                viewHolder = new ViewHolderBranddesc(v2);
                break;

            case STOCK:
                View v5 = inflater.inflate(R.layout.layout_viewholderstock, viewGroup, false);
                viewHolder = new ViewHolderStock(v5);
                break;

            case OFFERS:
                View v6 = inflater.inflate(R.layout.layout_viewholderoffers, viewGroup, false);
                viewHolder = new ViewHolderOffers(v6);
                break;

            case OTHERS:
                View v7 = inflater.inflate(R.layout.layout_viewholderothers, viewGroup, false);
                viewHolder = new ViewHolderOthers(v7);
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

            case STOCK:
                ViewHolderStock vh5 = (ViewHolderStock) viewHolder;
                configureViewHolder5(vh5, position);
                break;
            case OFFERS:

                ViewHolderOffers vh6 = (ViewHolderOffers) viewHolder;
                configureViewHolder6(vh6, position);
                break;
            case OTHERS:

                ViewHolderOthers vh7 = (ViewHolderOthers) viewHolder;
                configureViewHolder7(vh7, position);
                break;

            default:

                //  RecyclerViewSimpleTextViewHolder vh = (RecyclerViewSimpleTextViewHolder) viewHolder;
                // configureDefaultViewholder(vh);
        }
    }


    private void configureDefaultViewholder(RecyclerViewSimpleTextViewHolder vh) {

        vh.getLabel().setText((""));
    }

    private void configureViewHolder1(final ViewHolderBrandheader vh1, int position) {
        final brandheaderdata header = (brandheaderdata) items.get(position);
        if (header.followed.equals("yes")) {
            vh1.getUnfollow().setVisibility(View.VISIBLE);
            vh1.getFollow().setVisibility(View.INVISIBLE);
        } else {
            vh1.getUnfollow().setVisibility(View.INVISIBLE);
            vh1.getFollow().setVisibility(View.VISIBLE);
        }
        if (header != null) {
            vh1.getFollow().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String PREFS_NAME = "MyPrefsFile";
                    if (brandtimeline.follow_change == 0) {
                        brandtimeline.follow_change = 1;
                    } else {
                        brandtimeline.follow_change = 0;
                    }
                    SharedPreferences settings = mcontext.getSharedPreferences(PREFS_NAME, 0);
                    String mobile_no = settings.getString("mobile_no", "");
                    Log.d("timeline", "header.brand_id: " + header.brand_id);
                    Follow_requestData(header.brand_id, mobile_no, 1);
                    vh1.getFollow().setVisibility(View.INVISIBLE);
                    vh1.getUnfollow().setVisibility(View.VISIBLE);

                }
            });
            vh1.getUnfollow().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String PREFS_NAME = "MyPrefsFile";
                    if (brandtimeline.follow_change == 0) {
                        brandtimeline.follow_change = 1;
                    } else {
                        brandtimeline.follow_change = 0;
                    }
                    SharedPreferences settings = mcontext.getSharedPreferences(PREFS_NAME, 0);
                    String mobile_no = settings.getString("mobile_no", "");

                    Follow_requestData(header.brand_id, mobile_no, 0);
                    vh1.getFollow().setVisibility(View.VISIBLE);
                    vh1.getUnfollow().setVisibility(View.INVISIBLE);
                }
            });
            vh1.getTitle().setText(header.title);
            if (header.brandicon == "" || header.brandicon == "") {
                // Future Stuff
            } else {
                Picasso.with(mcontext).load("http://192.168.0.3:1337/show_brand_cover/" + header.brandicon)
                        .into(vh1.getBrandicon(), new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
            }
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

        }
    }

    private void configureViewHolder2(final ViewHolderBranddesc vh2, int position) {

        final branddescdata desc = (branddescdata) items.get(position);
        if (desc != null) {

            vh2.getDesc().setText(desc.desc);

        }

    }

    private void configureViewHolder5(final ViewHolderStock vh5, int position) {
        final brandstockdata stock = (brandstockdata) items.get(position);

        if (stock.upvoted.equals("yes")) {
            vh5.getUnlikebutton().setVisibility(View.INVISIBLE);
            vh5.getLikebutton().setVisibility(View.VISIBLE);
        } else {
            vh5.getUnlikebutton().setVisibility(View.VISIBLE);
            vh5.getLikebutton().setVisibility(View.INVISIBLE);
        }
        vh5.getLikebutton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh5.getLikes().setText("" + (Integer.parseInt(vh5.getLikes().getText().toString()) - 1));
                final String PREFS_NAME = "MyPrefsFile";

                SharedPreferences settings = mcontext.getSharedPreferences(PREFS_NAME, 0);
                String mobile_no = settings.getString("mobile_no", "");

                UserDownvote_requestData(stock.post_id, mobile_no);

                vh5.getUnlikebutton().setVisibility(View.VISIBLE);
                vh5.getLikebutton().setVisibility(View.INVISIBLE);
            }
        });
        vh5.getUnlikebutton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh5.getLikes().setText("" + (Integer.parseInt(vh5.getLikes().getText().toString()) + 1));
                final String PREFS_NAME = "MyPrefsFile";

                SharedPreferences settings = mcontext.getSharedPreferences(PREFS_NAME, 0);
                String mobile_no = settings.getString("mobile_no", "");

                UserUpvote_requestData(stock.post_id, mobile_no);
                vh5.getLikebutton().setVisibility(View.VISIBLE);
                vh5.getUnlikebutton().setVisibility(View.INVISIBLE);
            }
        });
/*
        vh5.getFollowbutton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vh5.getTick().setVisibility(View.VISIBLE);
                vh5.getFollowbutton().setVisibility(View.GONE);

            }
        });
        */
        if (stock != null) {

            if (stock.brandicon == "" || stock.brandicon == null) {

            } else {
                Picasso.with(mcontext).load("http://192.168.0.3:1337/show_brand_icon/" + stock.brandicon)
                        .into(vh5.getBrandicon(), new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
            }
            if (stock.brandphoto == "" || stock.brandphoto == null) {

            } else {
                Picasso.with(mcontext).load("http://192.168.0.3:1337/show_brand_post/" + stock.brandphoto)
                        .into(vh5.getBrandphoto(), new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
            }
            vh5.getTick().setVisibility(View.INVISIBLE);
            vh5.getTitle().setText(stock.Title);
            vh5.getLikes().setText(stock.likes);
            vh5.getBrandname().setText(stock.brandname);

        }
    }

    private void configureViewHolder6(final ViewHolderOffers vh6, int position) {

        final brandoffersdata offers = (brandoffersdata) items.get(position);
        if (offers.upvoted.equals("yes")) {
            vh6.getUnlikebutton().setVisibility(View.INVISIBLE);
            vh6.getLikebutton().setVisibility(View.VISIBLE);
        } else {
            vh6.getUnlikebutton().setVisibility(View.VISIBLE);
            vh6.getLikebutton().setVisibility(View.INVISIBLE);
        }
        vh6.getLikebutton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh6.getLikes().setText("" + (Integer.parseInt(vh6.getLikes().getText().toString()) - 1));
                final String PREFS_NAME = "MyPrefsFile";

                SharedPreferences settings = mcontext.getSharedPreferences(PREFS_NAME, 0);
                String mobile_no = settings.getString("mobile_no", "");

                UserDownvote_requestData(offers.post_id, mobile_no);

                vh6.getUnlikebutton().setVisibility(View.VISIBLE);
                vh6.getLikebutton().setVisibility(View.INVISIBLE);
            }
        });


        vh6.getUnlikebutton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh6.getLikes().setText("" + (Integer.parseInt(vh6.getLikes().getText().toString()) + 1));
                final String PREFS_NAME = "MyPrefsFile";

                SharedPreferences settings = mcontext.getSharedPreferences(PREFS_NAME, 0);
                String mobile_no = settings.getString("mobile_no", "");

                UserUpvote_requestData(offers.post_id, mobile_no);
                vh6.getLikebutton().setVisibility(View.VISIBLE);
                vh6.getUnlikebutton().setVisibility(View.INVISIBLE);
            }
        });
/*
        vh6.getFollowbutton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vh6.getTick().setVisibility(View.VISIBLE);
                vh6.getFollowbutton().setVisibility(View.GONE);

            }
        });
        */
        if (offers != null) {

            if (offers.Brandicon == "" || offers.Brandicon == null) {

            } else {
                Picasso.with(mcontext).load("http://192.168.0.21:1337/show_brand_icon/" + offers.Brandicon)
                        .into(vh6.getBrandicon(), new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
            }
            if (offers.offerphoto == "" || offers.offerphoto == null) {

            } else {
                Picasso.with(mcontext).load("http://192.168.0.3:1337/show_all_offers/" + offers.offerphoto)
                        .into(vh6.getOfferphoto(), new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
            }
            vh6.getTitle().setText(offers.Title);

            vh6.getLikes().setText(offers.likes);
            vh6.getDesc().setText(offers.desc);
            vh6.getDate().setText(offers.date);
            vh6.getTick().setVisibility(View.INVISIBLE);
            vh6.getOffercode().setText(offers.code);
            vh6.getTnc().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(mcontext);
                    dialog.setContentView(R.layout.dialog);
                    dialog.setTitle("Terms & Conditions");

                    // set the custom dialog components - text, image and button
                    TextView text = (TextView) dialog.findViewById(R.id.text);
                 /*   text.setText("1. This voucher entitles you to a Rs. 500/- discount on a minimum bill value of Rs. 2000/- at Peter England exclusive branded outlets only.\n\n" +
                            "2. This discount is valid on apparel products only \n\n" +
                            "3. One voucher is valid per visit \n\n" +
                            "4. One customer can avail the offer only once (valid on a single bill and a registered mobile number)\n\n" +
                            "5. To avail the discount you are required to present the print out/ e-copy/ unique voucher code at the Peter England store and register your details\n\n" +
                            "6. This offer is valid only on Full MRP Merchandise\n\n" +
                            "7. Offer under this voucher is not valid in conjunction with any other offer / scheme / discount coupons/ discounted products \n\n" +
                            "8. Voucher cannot be exchanged for cash\n\n" +
                            "9. Offer valid between Oct 9th, 2015 � Oct 31st , 2015 \n\n" +
                            "10. The merchant store�s rules and regulations apply \n\n" +
                            "11. Please visit http://www.peterengland.com/store.php for store locations\n\n");
*/
                    text.setText(offers.terms);
                    text.setEms(30);
                    Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });
        }

    }


    private void configureViewHolder7(final ViewHolderOthers vh7, int position) {

        final brandothersdata others = (brandothersdata) items.get(position);

        if (others.upvoted.equals("yes")) {
            vh7.getUnlikebutton().setVisibility(View.INVISIBLE);
            vh7.getLikebutton().setVisibility(View.VISIBLE);
        } else {
            vh7.getUnlikebutton().setVisibility(View.VISIBLE);
            vh7.getLikebutton().setVisibility(View.INVISIBLE);
        }
        vh7.getLikebutton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh7.getLikes().setText("" + (Integer.parseInt(vh7.getLikes().getText().toString()) - 1));
                final String PREFS_NAME = "MyPrefsFile";

                SharedPreferences settings = mcontext.getSharedPreferences(PREFS_NAME, 0);
                String mobile_no = settings.getString("mobile_no", "");

                UserDownvote_requestData(others.post_id, mobile_no);

                vh7.getUnlikebutton().setVisibility(View.VISIBLE);
                vh7.getLikebutton().setVisibility(View.INVISIBLE);
            }
        });
        vh7.getUnlikebutton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh7.getLikes().setText("" + (Integer.parseInt(vh7.getLikes().getText().toString()) + 1));
                final String PREFS_NAME = "MyPrefsFile";

                SharedPreferences settings = mcontext.getSharedPreferences(PREFS_NAME, 0);
                String mobile_no = settings.getString("mobile_no", "");

                UserUpvote_requestData(others.post_id, mobile_no);
                vh7.getLikebutton().setVisibility(View.VISIBLE);
                vh7.getUnlikebutton().setVisibility(View.INVISIBLE);
            }
        });
/*
        vh7.getFollowbutton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vh7.getTick().setVisibility(View.VISIBLE);
                vh7.getFollowbutton().setVisibility(View.GONE);

            }
        });
        */
        if (others != null) {
            if (others.brandicon == "" || others.brandicon == null) {

            } else {
                Picasso.with(mcontext).load("http://192.168.0.21:1337/show_brand_icon/" + others.brandicon)
                        .into(vh7.getBrandicon(), new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
            }
            if (others.brandphoto == "" || others.brandphoto == null) {

            } else {
                Picasso.with(mcontext).load("http://192.168.0.3:1337/show_brand_post/" + others.brandphoto)
                        .into(vh7.getBrandphoto(), new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
            }
            vh7.getTick().setVisibility(View.INVISIBLE);
            vh7.getTitle().setText(others.Title);
            vh7.getLikes().setText(others.likes);
            vh7.getBrandname().setText(others.brandname);

        }
    }

    public static String USER_FOLLOW_ENDPOINT = "http://192.168.0.3:1337/user_follow";

    public void Follow_requestData(String brand_id, String jeeb_no, Integer follow) {
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(mcontext);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(USER_FOLLOW_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_User_Follow(jeeb_no, brand_id, follow, new Callback<success_message>() {
                @Override
                public void success(success_message data, Response response) {
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + data.getSuccess());
                    Log.d("hii", "Message : " + data.getMessage());
                    if (data.getSuccess() == 1) {
                        Log.d("hii", "removed follow_notifications");
                    } else {
                        Log.d("hii", "remove follow notifications error" + data.getMessage());
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.d("hii", "failure");
                    Log.e("hii", retrofitError.toString());
                }
            });
        } else {
            Log.d("hii", "api null");
        }
    }

    public static String USER_UPVOTE_ENDPOINT = "http://192.168.0.3:1337/user_upvote_post";

    public void UserUpvote_requestData(String post_id, String jeeb_no) {
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(mcontext);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(USER_UPVOTE_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_User_Upvote(jeeb_no, post_id, new Callback<success_message>() {
                @Override
                public void success(success_message data, Response response) {
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + data.getSuccess());
                    Log.d("hii", "Message : " + data.getMessage());
                    if (data.getSuccess() == 1) {
                        Log.d("hii", "removed follow_notifications");
                    } else {
                        Log.d("hii", "remove follow notifications error" + data.getMessage());
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.d("hii", "failure");
                    Log.e("hii", retrofitError.toString());
                }
            });
        } else {
            Log.d("hii", "api null");
        }
    }

    public static String USER_DOWNVOTE_ENDPOINT = "http://192.168.0.3:1337/user_downvote_post";

    public void UserDownvote_requestData(String post_id, String jeeb_no) {
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(mcontext);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(USER_DOWNVOTE_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_User_Downvote(jeeb_no, post_id, new Callback<success_message>() {
                @Override
                public void success(success_message data, Response response) {
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + data.getSuccess());
                    Log.d("hii", "Message : " + data.getMessage());
                    if (data.getSuccess() == 1) {
                        Log.d("hii", "removed follow_notifications");
                    } else {
                        Log.d("hii", "remove follow notifications error" + data.getMessage());
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.d("hii", "failure");
                    Log.e("hii", retrofitError.toString());
                }
            });
        } else {
            Log.d("hii", "api null");
        }
    }
}

