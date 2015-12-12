package com.example.khaalijeb.newlistview_module;

        import android.app.Activity;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.location.Address;
        import android.location.Geocoder;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.ImageView;
        import android.widget.Toast;

        import com.google.android.gms.common.ConnectionResult;
        import com.google.android.gms.common.GoogleApiAvailability;

        import org.json.JSONException;

        import java.io.IOException;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;


public class Splashscreen extends Activity {

    double latitude = 0;
    double longitude = 0;

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, 9000)
                        .show();
            } else {
                Log.d("app-register", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    public void getlocation() throws IOException, JSONException {
        String finalAddress = "";
        GPSTracker gps = new GPSTracker(Splashscreen.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Log.d("hii", "latitude: " + latitude);
            Log.d("hii", "longitude: " + longitude);
        //    latitude = 12.872274;
        //    longitude = 77.645199;
            Toast.makeText(getBaseContext(), "Your Location is -\nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_SHORT).show();
        } else {
            gps.showSettingsAlert();
        }
    }

    public void getAuto_Detect() throws IOException, JSONException {
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        Geocoder geoCoder = new Geocoder(getApplicationContext());
        List<Address> matches = null;
        try {
            getlocation();
            matches = geoCoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String auto_detect = "";
        Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
        if (bestMatch == null) {


        } else {
            Toast.makeText(getBaseContext(), auto_detect, Toast.LENGTH_LONG).show();
            Log.d("address", "" + matches.get(0));
            Log.d("address", "" + matches.get(0).getMaxAddressLineIndex());
            int len = matches.get(0).getMaxAddressLineIndex();
            String area1 = "", city = "";
            /*
            for (int i = 0; i <= len; i++) {
                Log.d("address", "i:" + i + ", " + matches.get(0).getAddressLine(i));
                if (i == 0) {
                    if (matches.get(0).getAddressLine(0).equals("Unnamed Rd")) {
                        location = "";
                    } else {
                        location = matches.get(0).getAddressLine(0);
                    }
                } else if (i < len - 1){
                    location = location +  matches.get(0).getAddressLine(i);
                }
            }
            */
            String str = matches.get(0).getAddressLine(len - 1);
            String[] splited = str.split(",");
            city = splited[0];
            String str1 = matches.get(0).getAddressLine(len - 2);
            String[] splitedd = str1.split(",");
            area1 = splitedd[splitedd.length - 1];
            area1 = area1.trim();
            Log.d("address", "" + "area1: " + area1);
            Log.d("address", "" + "city: " + city);
            settings.edit().putString("auto_detect_city", city).apply();
            settings.edit().putString("auto_detect_area1", area1).apply();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        try {
            getAuto_Detect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean s = settings.getBoolean("my_first_time", true);
        String token = settings.getString("reg_id", "");
        boolean sentTokenToServer = settings.getBoolean("sentTokenToServer", false);
        Log.d("comments", "" + "my_first_time: " + s);
        Log.d("comments", "" + "token: " + token);
        if (settings.getBoolean("my_first_time", true)) {
            Toast.makeText(getBaseContext(), "First Time of app", Toast.LENGTH_LONG).show();
            settings.getString("app_last_start_time", "");

            Date currentDate = new Date();
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            settings.edit().putString("app_last_start_time", formattedDate).apply();
            settings.getInt("follow_notifications", 0);
            settings.getInt("notifications_notifications", 0);
            settings.getString("email_id", "");
            settings.getString("mobile_no", "");
            settings.getString("auto_detect_city", "");
            settings.getString("fauto_detect_area1", "");
            settings.edit().putBoolean("login", false).apply();
            if (checkPlayServices()) {
                settings.edit().putBoolean("my_first_time", false).apply();
                Intent intent = new Intent(this, RegistrationIntentService.class);
                Bundle b = new Bundle();
                b.putString("status", "register"); // inside bundle you can many types of values
                intent.putExtras(b);
                startService(intent);
                final ImageView splash = (ImageView) findViewById(R.id.splashimage);

                Thread timerThread = new Thread(){
                    public void run(){
                        try{
                            final Animation animationFadeIn = AnimationUtils.loadAnimation(Splashscreen.this, R.anim.fade_in);
                            final Animation animationFadeOut = AnimationUtils.loadAnimation(Splashscreen.this, R.anim.fade_out);
                            splash.startAnimation(animationFadeOut);
                            splash.startAnimation(animationFadeIn);

                            sleep(5000);

                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }finally{
                            Intent i = new Intent(Splashscreen.this,StartingActivity.class);
                            startActivity(i);
                        }
                    }
                };
                timerThread.start();
            } else {
                Toast.makeText(getBaseContext(), "Sorry App is not supported", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getBaseContext(), "Not First Time of app", Toast.LENGTH_LONG).show();
            if (settings.getBoolean("login", false)) {
                final ImageView splash = (ImageView) findViewById(R.id.splashimage);

                Thread timerThread = new Thread(){
                    public void run(){
                        try{
                            final Animation animationFadeIn = AnimationUtils.loadAnimation(Splashscreen.this, R.anim.fade_in);
                            final Animation animationFadeOut = AnimationUtils.loadAnimation(Splashscreen.this, R.anim.fade_out);
                            splash.startAnimation(animationFadeOut);
                            splash.startAnimation(animationFadeIn);

                            sleep(5000);

                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }finally{
                            Intent i = new Intent(Splashscreen.this, MainActivity.class);
                            startActivity(i);
                        }
                    }
                };
                timerThread.start();
            } else {
                final ImageView splash = (ImageView) findViewById(R.id.splashimage);

                Thread timerThread = new Thread(){
                    public void run(){
                        try{
                            final Animation animationFadeIn = AnimationUtils.loadAnimation(Splashscreen.this, R.anim.fade_in);
                            final Animation animationFadeOut = AnimationUtils.loadAnimation(Splashscreen.this, R.anim.fade_out);
                            splash.startAnimation(animationFadeOut);
                            splash.startAnimation(animationFadeIn);

                            sleep(5000);

                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }finally{
                            Intent i = new Intent(Splashscreen.this,StartingActivity.class);
                            startActivity(i);
                        }
                    }
                };
                timerThread.start();
            }
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        finish();
    }

}