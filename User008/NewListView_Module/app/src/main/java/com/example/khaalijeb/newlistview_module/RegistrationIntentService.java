package com.example.khaalijeb.newlistview_module;

/**
 * Created by Davinci on 9/10/2015.
 */

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;

import models.Login;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class RegistrationIntentService extends IntentService {
    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};

    public static final String APP_REGISTER_IDS = "http://192.168.0.21:1337/app_register_ids";


    public RegistrationIntentService() {
            super(TAG);
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            final String PREFS_NAME = "MyPrefsFile";

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
         //   SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
         //   final String PREFS_NAME = "MyPrefsFile";

          //  SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
          //  SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

            try {
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken("260512142507" ,
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                Log.d("token", "token: " + token);
                Log.d("app-register", "1: " + token);
                Log.i(TAG, "GCM Registration Token: " + token);

                // TODO: Implement this method to send any registration to your app's servers.
                settings.edit().putString("reg_id", token).apply();
                sendRegistrationToServer(settings, token);

        //        subscribeTopics(token);
            } catch (Exception e) {
                Log.d(TAG, "Failed to complete token refresh", e);
                // If an exception happens while fetching the new token or updating our registration data
                // on a third-party server, this ensures that we'll attempt the update at a later time.
                settings.edit().putBoolean("sentTokenToServer", false).apply();
            }
            // Notify UI that registration has completed, so the progress indicator can be hidden.
            Intent registrationComplete = new Intent("registrationComplete");
            LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
        }

        private void sendRegistrationToServer(SharedPreferences settings, String token) {
            App_Register_Ids_requestData(settings, token);
            // Add custom implementation, as needed.
        }

    public void App_Register_Ids_requestData(final SharedPreferences settings,  String token) {
        Log.d("hii", "request data");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(RegistrationIntentService.this);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(APP_REGISTER_IDS).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
    //        username = USER.getText().toString();

            api.post_App_Register_Ids(token, new Callback<Login>() {
                @Override
                public void success(Login login, Response response) {
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + login.getSuccess());
                    Log.d("hii", "Message : " + login.getMessage());
                    if (login.getSuccess() == 1) {
                        Log.d("hii", "success");
                        settings.edit().putBoolean("sentTokenToServer", true).apply();
                        Toast.makeText(getBaseContext(), login.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("hii", "ye hai" + (new Gson().toJson(login)));
                    } else {
                        settings.edit().putBoolean("sentTokenToServer", false).apply();
                        Log.d("hii", "invalid credentials");
                        Toast.makeText(getBaseContext(), login.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    settings.edit().putBoolean("sentTokenToServer", false).apply();
                    Log.d("hii", "failure");
                    Log.e("hii", retrofitError.toString());
                }
            });
        } else {
            Log.d("hii", "api null");
        }

    }

        /**
         * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
         *
         * @param token GCM token
         * @throws IOException if unable to reach the GCM PubSub service
         */
        // [START subscribe_topics]
        private void subscribeTopics(String token) throws IOException {
            GcmPubSub pubSub = GcmPubSub.getInstance(this);
            for (String topic : TOPICS) {
                pubSub.subscribe(token, "/topics/" + topic, null);
            }
        }
        // [END subscribe_topics]

    }