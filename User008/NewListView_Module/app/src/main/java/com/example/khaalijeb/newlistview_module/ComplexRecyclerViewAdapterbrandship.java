package com.example.khaalijeb.newlistview_module;

/**
 * Created by Lord Voldemort on 04/10/2015.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by Lord Voldemort on 01/09/2015.
 */
public class ComplexRecyclerViewAdapterbrandship extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Typeface font;
    private LayoutInflater inflator;
    // The items to display in your RecyclerView
    private List<Object> items;
    Context mcontext;


    private final int STOCK = 0, OFFERS = 1, OTHERS = 2;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ComplexRecyclerViewAdapterbrandship(Context context, List<Object> items, Typeface font) {

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
        if (items.get(position) instanceof brandstockdata) {
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
            case STOCK:
                View v1 = inflater.inflate(R.layout.layout_viewholderstock, viewGroup, false);
                viewHolder = new ViewHolderStock(v1);
                break;

            case OFFERS:
                View v2 = inflater.inflate(R.layout.layout_viewholderoffers, viewGroup, false);
                viewHolder = new ViewHolderOffers(v2);
                break;
            case OTHERS:

                View v3 = inflater.inflate(R.layout.layout_viewholderothers, viewGroup, false);
                viewHolder = new ViewHolderOthers(v3);
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
            case STOCK:
                ViewHolderStock vh1 = (ViewHolderStock) viewHolder;
                configureViewHolder1(vh1, position);

                break;
            case OFFERS:
                ViewHolderOffers vh2 = (ViewHolderOffers) viewHolder;
                configureViewHolder2(vh2, position);
                break;
            case OTHERS:
                ViewHolderOthers vh3 = (ViewHolderOthers) viewHolder;
                configureViewHolder3(vh3, position);
                break;

            default:

                //  RecyclerViewSimpleTextViewHolder vh = (RecyclerViewSimpleTextViewHolder) viewHolder;
                // configureDefaultViewholder(vh);
        }
    }


    private void configureDefaultViewholder(RecyclerViewSimpleTextViewHolder vh) {

        vh.getLabel().setText((""));
    }

    private void configureViewHolder1(final ViewHolderStock vh1, int position) {
        final brandstockdata stock = (brandstockdata) items.get(position);

        if (stock.upvoted.equals("yes")) {
            vh1.getUnlikebutton().setVisibility(View.INVISIBLE);
            vh1.getLikebutton().setVisibility(View.VISIBLE);
        } else {
            vh1.getUnlikebutton().setVisibility(View.VISIBLE);
            vh1.getLikebutton().setVisibility(View.INVISIBLE);
        }
        if (stock.followed.equals("yes")) {
            vh1.getTick().setVisibility(View.VISIBLE);
        } else {
            vh1.getTick().setVisibility(View.INVISIBLE);
        }
        if (stock != null) {

            if (stock.brandicon == "" || stock.brandicon == null) {

            } else {
                Picasso.with(mcontext).load("http://192.168.0.3:1337/show_brand_icon/" + stock.brandicon)
                        .into(vh1.getBrandicon(), new com.squareup.picasso.Callback() {
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
                        .into(vh1.getBrandphoto(), new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
            }
            vh1.getTitle().setText(stock.Title);
            vh1.getLikes().setText(stock.likes);
            vh1.getLikes().setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                }
            });
            vh1.getBrandname().setText(stock.brandname);
            vh1.getLikebutton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vh1.getLikes().setText("" + (Integer.parseInt(vh1.getLikes().getText().toString()) - 1));
                    final String PREFS_NAME = "MyPrefsFile";
                    SharedPreferences settings = mcontext.getSharedPreferences(PREFS_NAME, 0);
                    String mobile_no = settings.getString("mobile_no", "");
                    UserDownvote_requestData(stock.post_id, mobile_no);
                    vh1.getUnlikebutton().setVisibility(View.VISIBLE);
                    vh1.getLikebutton().setVisibility(View.INVISIBLE);
                }
            });

            vh1.getUnlikebutton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vh1.getLikes().setText("" + (Integer.parseInt(vh1.getLikes().getText().toString()) + 1));
                    final String PREFS_NAME = "MyPrefsFile";
                    SharedPreferences settings = mcontext.getSharedPreferences(PREFS_NAME, 0);
                    String mobile_no = settings.getString("mobile_no", "");
                    UserUpvote_requestData(stock.post_id, mobile_no);
                    vh1.getLikebutton().setVisibility(View.VISIBLE);
                    vh1.getUnlikebutton().setVisibility(View.INVISIBLE);
                }
            });


        }
    }

    private void configureViewHolder2(final ViewHolderOffers vh2, int position) {

        final brandoffersdata offers = (brandoffersdata) items.get(position);



/*        vh2.getFollowbutton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vh2.getTick().setVisibility(View.VISIBLE);
                vh2.getFollowbutton().setVisibility(View.GONE);

            }
        });
*/
        Log.d("brandship", "folowed: " + offers.followed);
        Log.d("upvoted", offers.upvoted);
        if (offers.upvoted.equals("yes")) {
            vh2.getUnlikebutton().setVisibility(View.INVISIBLE);
            vh2.getLikebutton().setVisibility(View.VISIBLE);
        } else {
            vh2.getUnlikebutton().setVisibility(View.VISIBLE);
            vh2.getLikebutton().setVisibility(View.INVISIBLE);
        }
        if (offers.followed.equals("yes")) {
            vh2.getTick().setVisibility(View.VISIBLE);
        } else {
            vh2.getTick().setVisibility(View.INVISIBLE);
        }
        if (offers != null) {
            if (offers.Brandicon == "" || offers.Brandicon == null) {

            } else {
                Picasso.with(mcontext).load("http://192.168.0.21:1337/show_brand_icon/" + offers.Brandicon)
                        .into(vh2.getBrandicon(), new com.squareup.picasso.Callback() {
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
                        .into(vh2.getOfferphoto(), new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
            }
            vh2.getTitle().setText(offers.Title);
            vh2.getLikes().setText(offers.likes);
            vh2.getLikes().setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                }
            });
            vh2.getDesc().setText(offers.desc);
            vh2.getDate().setText(offers.date);
            vh2.getOffercode().setText(offers.code);
            vh2.getTnc().setOnClickListener(new View.OnClickListener() {
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
            vh2.getLikebutton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vh2.getLikes().setText("" + (Integer.parseInt(vh2.getLikes().getText().toString()) - 1));
                    final String PREFS_NAME = "MyPrefsFile";

                    SharedPreferences settings = mcontext.getSharedPreferences(PREFS_NAME, 0);
                    String mobile_no = settings.getString("mobile_no", "");

                    UserDownvote_requestData(offers.post_id, mobile_no);

                    vh2.getUnlikebutton().setVisibility(View.VISIBLE);
                    vh2.getLikebutton().setVisibility(View.INVISIBLE);
                }
            });


            vh2.getUnlikebutton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vh2.getLikes().setText("" + (Integer.parseInt(vh2.getLikes().getText().toString()) + 1));
                    final String PREFS_NAME = "MyPrefsFile";

                    SharedPreferences settings = mcontext.getSharedPreferences(PREFS_NAME, 0);
                    String mobile_no = settings.getString("mobile_no", "");

                    UserUpvote_requestData(offers.post_id, mobile_no);
                    vh2.getLikebutton().setVisibility(View.VISIBLE);
                    vh2.getUnlikebutton().setVisibility(View.INVISIBLE);
                }
            });

        }

    }


    private void configureViewHolder3(final ViewHolderOthers vh3, int position) {

        final brandothersdata others = (brandothersdata) items.get(position);

        if (others.upvoted.equals("yes")) {
            vh3.getUnlikebutton().setVisibility(View.INVISIBLE);
            vh3.getLikebutton().setVisibility(View.VISIBLE);
        } else {
            vh3.getUnlikebutton().setVisibility(View.VISIBLE);
            vh3.getLikebutton().setVisibility(View.INVISIBLE);
        }
        if (others.followed.equals("yes")) {
            vh3.getTick().setVisibility(View.VISIBLE);
        } else {
            vh3.getTick().setVisibility(View.INVISIBLE);
        }
        if (others != null) {

            if (others.brandicon == "" || others.brandicon == null) {

            } else {
                Picasso.with(mcontext).load("http://192.168.0.3:1337/show_brand_icon/" + others.brandicon)
                        .into(vh3.getBrandicon(), new com.squareup.picasso.Callback() {
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
                        .into(vh3.getBrandphoto(), new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
            }
            vh3.getTitle().setText(others.Title);
            vh3.getLikes().setText(others.likes);
            vh3.getLikes().setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                }
            });
            vh3.getBrandname().setText(others.brandname);
            vh3.getLikebutton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vh3.getLikes().setText("" + (Integer.parseInt(vh3.getLikes().getText().toString()) - 1));
                    final String PREFS_NAME = "MyPrefsFile";
                    SharedPreferences settings = mcontext.getSharedPreferences(PREFS_NAME, 0);
                    String mobile_no = settings.getString("mobile_no", "");
                    UserDownvote_requestData(others.post_id, mobile_no);
                    vh3.getUnlikebutton().setVisibility(View.VISIBLE);
                    vh3.getLikebutton().setVisibility(View.INVISIBLE);
                }
            });

            vh3.getUnlikebutton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vh3.getLikes().setText("" + (Integer.parseInt(vh3.getLikes().getText().toString()) + 1));
                    final String PREFS_NAME = "MyPrefsFile";
                    SharedPreferences settings = mcontext.getSharedPreferences(PREFS_NAME, 0);
                    String mobile_no = settings.getString("mobile_no", "");
                    UserUpvote_requestData(others.post_id, mobile_no);
                    vh3.getLikebutton().setVisibility(View.VISIBLE);
                    vh3.getUnlikebutton().setVisibility(View.INVISIBLE);
                }
            });


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