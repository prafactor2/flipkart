package com.example.khaalijeb.newlistview_module;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lord Voldemort on 26/09/2015.
 */
public class Notifications extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notifications,container,false);
        return view;
    }

    public interface MyNotificationsListener{
        public Integer nonotifications();
    }

    private MyNotificationsListener callerActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callerActivity = (MyNotificationsListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onViewSelected");
        }
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            Log.d("attach", "visible onmenu of HomeFragment");
            final String PREFS_NAME = "MyPrefsFile";
            SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
            Integer x = settings.getInt("notifications_notifications", 0);
            if (x != 0) {
                settings.edit().putInt("notifications_notifications", 0).commit();
                callerActivity.nonotifications();
                Log.d("onattach", "heeeee");
            }
        }
    }

}
