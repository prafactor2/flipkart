package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lord Voldemort on 15/09/2015.
 */
public class RestaurantTab extends Fragment implements search2recyclerviewadapter.ClickListener{

    RecyclerView mRecyclerView;
    search2recyclerviewadapter mAdapter;
    Context c;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_all,container,false);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cae.ttf");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(c));
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

//        mAdapter.setClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab  = (FloatingActionButton)getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), ShopSearch.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void itemClicked(View v, int position) {

    }

}
