package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lord Voldemort on 28/11/2015.
 */
public class PromoCodeItem3Fragment extends Fragment {

    RecyclerView mRecyclerView;
    PromoCodeRecyclerViewAdapter mAdapter;
    Context c;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_pc_item3_fragment, container, false);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cae.ttf");

        mAdapter = new PromoCodeRecyclerViewAdapter(getActivity(),getData(c),font, (PromoCodeActivity) getActivity());
        mRecyclerView = (RecyclerView)view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(c));

        return view;
    }


    public List<promocodedata> getData(Context c) {
        List<promocodedata> data = new ArrayList<>();
/*
        String[] brandname = getResources().getStringArray(R.array.brandname);
        String[] title = getResources().getStringArray(R.array.details2);


        int[] icon = {R.drawable.nike,R.drawable.nike, R.drawable.nike,R.drawable.nike, R.drawable.nike,R.drawable.nike,R.drawable.nike,R.drawable.nike,R.drawable.nike};
        for (int i = 0; i < icon.length; i++) {
            promocodedata current = new promocodedata();
            current.brandicon = icon[i];
            current.brandname = brandname[i];
            current.title = title[i];

            data.add(current);
        }
*/
        return data;
    }

}
