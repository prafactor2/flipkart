package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerFragment extends Fragment implements RecyclerViewAdapter.ClickListener {
    Context c;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    Spinner spinner;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        c = getActivity();
        int flag = 0;
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cae.ttf");
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.listView);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), getData(c), flag, font);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });



        TextView txt = (TextView) v.findViewById(R.id.textView);
        txt.setTypeface(font);
        return v;


    }
    public static List<SingleView> getData(Context c) {
        List<SingleView> data = new ArrayList<>();

        Resources res = c.getResources();
        String[] titles =  res.getStringArray(R.array.titles);




        //  String[] description = res.getStringArray(R.array.description);
        int[] images = {R.drawable.ac, R.drawable.cred2, R.drawable.tran2, R.drawable.home2, R.drawable.about1,R.drawable.lg2};
        for (int i = 0; i < titles.length; i++) {
            SingleView current = new SingleView();
            current.title = titles[i];
            current.images = images[i];
            data.add(current);
        }
        return  data;
    }



    @Override
    public void itemClicked(View v, int position) {
        if (position == 0) {
            startActivity(new Intent(getActivity(),Profile.class));
            mDrawerLayout.closeDrawers();

        }
        if (position == 1) {
            startActivity(new Intent(getActivity(),WalletActivity.class));
            mDrawerLayout.closeDrawers();
        }
        if (position == 3) {
            startActivity(new Intent(getActivity(),WalletActivity.class));
            mDrawerLayout.closeDrawers();
        }
    }
    public void setUp(DrawerLayout drawerLayout, Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),mDrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



     /*   spinner = (Spinner) getView().findViewById(R.id.spinner1);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.CITIES,R.layout.spinner_layout);
        spinner.setAdapter(arrayAdapter);
*/

    }

}
