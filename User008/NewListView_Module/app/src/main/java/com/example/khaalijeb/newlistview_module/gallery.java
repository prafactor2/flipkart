package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class gallery extends AppCompatActivity implements galleryrecyclerviewadapter.ClickListener {

    private static galleryrecyclerviewadapter mAdapter;

    private RecyclerView mRecyclerView;
    Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/cae.ttf");
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);

        mAdapter = new galleryrecyclerviewadapter(gallery.this, getData(c),font);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(c,2));
        mAdapter.setClickListener(this);
    }

    public List<information> getData(Context c) {
        List<information> data = new ArrayList<>();



        int[] icon = {R.drawable.aa, R.drawable.aa, R.drawable.aa, R.drawable.aa, R.drawable.aa,R.drawable.aa,R.drawable.aa,R.drawable.aa,R.drawable.aa};
        for (int i = 0; i < icon.length; i++) {
            information current = new information();
               current.image = icon[i];

            data.add(current);
        }

        return data;
    }



    @Override
    public void itemClicked(View v, int position) {

    }
}
