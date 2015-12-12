package com.example.khaalijeb.newlistview_module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import models.merchant_all_photos;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class GridViewActivity extends Activity {

    public static String MERCHANT_ALL_PHOTOS_ENDPOINT = "http://192.168.0.21:1337/merchant_all_photos";

    private GridView photoGrid;
    private int mPhotoSize, mPhotoSpacing;
    private ImageAdapter imageAdapter;
    String merchant_jeb_no;
    Integer page;
    int firstVis = 0;
    int preLast = 0;
    Integer prev_total;
    boolean stay_on_this_page;
    String photo_1, photo_2;

    // Some items to add to the GRID
    public static final ArrayList<String> items = new ArrayList<>();
 /*   static final int[] ICONS = new int[]
            {
                    R.drawable.k1, R.drawable.b,
                    R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f,
                    R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j,
                    R.drawable.k, R.drawable.l, R.drawable.m, R.drawable.n,
                    R.drawable.b1, R.drawable.b, R.drawable.c, R.drawable.d,
                    R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h,
                    R.drawable.i, R.drawable.j, R.drawable.k, R.drawable.l,
                    R.drawable.m, R
                    .drawable.n, R.drawable.k3, R.drawable.b,
                    R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f,
                    R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j,
                    R.drawable.k, R.drawable.l, R.drawable.m, R.drawable.n,
                    R.drawable.b1, R.drawable.b, R.drawable.c, R.drawable.d,
                    R.drawable.e, R.drawable.f
            }; */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        items.clear();
        page = 0;
        preLast = 0;
        Bundle b = getIntent().getExtras();
        merchant_jeb_no = b.getString("merchant_jeb_no");
        photo_1 = b.getString("photo_1");
        photo_2 = b.getString("photo_2");
        items.add(photo_1);
        items.add(photo_2);
        Merchant_All_Photos_requestData();

        // get the photo size and spacing
        mPhotoSize = getResources().getDimensionPixelSize(R.dimen.photo_size);
        mPhotoSpacing = getResources().getDimensionPixelSize(R.dimen.photo_spacing);

        // initialize image adapter

        photoGrid = (GridView) findViewById(R.id.albumGrid);

        //start sent image to full screen
        imageAdapter = new ImageAdapter();
        /**
         * On Click event for Single Gridview Item
         * */
        photoGrid.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // Sending image id to FullScreenActivity
                Intent i = new Intent(getApplicationContext(), SwipeActivity.class);
                // passing array index
                i.putExtra("id", position);
                startActivity(i);
            }
        });
        //end sent image to full screen

        // set image adapter to the GridView

        // get the view tree observer of the grid and set the height and numcols dynamically
        photoGrid.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (imageAdapter.getNumColumns() == 0) {
                    final int numColumns = (int) Math.floor(photoGrid.getWidth() / (mPhotoSize + mPhotoSpacing));
                    if (numColumns > 0) {
                        final int columnWidth = (photoGrid.getWidth() / numColumns) - mPhotoSpacing;
                        imageAdapter.setNumColumns(numColumns);
                        imageAdapter.setItemHeight(columnWidth);

                    }
                }
            }
        });
    }

    // ///////// ImageAdapter class /////////////////
    public class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private int mItemHeight = 0;
        private int mNumColumns = 0;
        private LinearLayout.LayoutParams mImageViewLayoutParams;

        public ImageAdapter() {
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mImageViewLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
        }

        public int getCount() {
            return items.size();
        }

        // set numcols
        public void setNumColumns(int numColumns) {
            mNumColumns = numColumns;
        }

        public int getNumColumns() {
            return mNumColumns;
        }

        // set photo item height
        public void setItemHeight(int height) {
            if (height == mItemHeight) {
                return;
            }
            mItemHeight = height;
            mImageViewLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, mItemHeight);
            notifyDataSetChanged();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view, ViewGroup parent) {

            if (view == null)
                view = mInflater.inflate(R.layout.photo_item, null);

            ImageView cover = (ImageView) view.findViewById(R.id.cover);
         //   TextView title = (TextView) view.findViewById(R.id.title);

            cover.setLayoutParams(mImageViewLayoutParams);

            // Check the height matches our calculated column width
            if (cover.getLayoutParams().height != mItemHeight) {
                cover.setLayoutParams(mImageViewLayoutParams);
            }
            if (items.get(position % items.size()) == null || items.get(position % items.size()) == "") {
                // Future
            } else {
                if (position == 0 || position == 1) {
                    Picasso.with(getBaseContext()).load("http://192.168.0.21:1337/show_nearby_search_merchant_photos/" + items.get(position % items.size()))
                            .into(cover, new com.squareup.picasso.Callback() {
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
                Picasso.with(getBaseContext()).load("http://192.168.0.21:1337/show_merchant_all_photos/" + items.get(position % items.size()))
                        .into(cover, new com.squareup.picasso.Callback() {
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
           // title.setText(CONTENT[position % CONTENT.length]);

            return view;
        }
    }

    public void Merchant_All_Photos_requestData() {
        Log.d("hii", "Merchant Profile With Photos Request Data");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(this);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(MERCHANT_ALL_PHOTOS_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_Merchant_all_photos(merchant_jeb_no, page, new Callback<merchant_all_photos>() {
                @Override
                public void success(final merchant_all_photos server_data, Response response) {
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + server_data.getSuccess());
                    Log.d("hii", "Message : " + server_data.getMessage());
                    if (server_data.getSuccess() == 1) {
                        //Future        NO_MERCHANT.setVisibility(View.INVISIBLE);
                        Integer total_photos = server_data.getPhotos().size();

                        for (int i = 0; i < total_photos; i++) {
                            String photo = server_data.getPhotos().get(i).getPhoto();
                            Log.d("photo", "" + photo);
                            items.add(photo);
                        }
                        photoGrid.setAdapter(imageAdapter);
                    } else {
                        Log.d("hii", server_data.getMessage());
                        Toast.makeText(getBaseContext(), server_data.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.d("hii", "failure");
                    Log.e("hii", retrofitError.toString());
                    Toast.makeText(getBaseContext(), retrofitError.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d("hii", "api null");
        }
    }
}
