package com.example.khaalijeb.newlistview_module;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.net.URLEncoder;

import models.Apply_Coupon;
import models.Bill_Generator;
import models.Login;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class PromoCodeActivity extends AppCompatActivity {


    interface OlaMoneyInteface {
        @android.webkit.JavascriptInterface
        void onPaymentDone(String jsonResponse);
    }

    public static final String BILL_GENERATOR_ENDPOINT = "http://192.168.0.9:1337/bill_generator";
    public static final String APPLY_COUPON_ENDPOINT = "http://192.168.0.9:1337/apply_coupon";

    PromoCodePagerAdapter mAdapter;
    TabLayout mTabLayout;
    ViewPager mPager;
    String bill = "";
    double amount = 10;
    String title[] = {"Hot Deals", "Food", "Wellness", "Fashion"};
    int numoftab = 4;
    EditText SPASS;
    String promo_code;
    int ctr = 0;

    private boolean check_olacabs() {
        PackageManager pm = getPackageManager();

        try {
            pm.getPackageInfo("com.olacabs.customer", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

  //  WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_code);
    /*    webView = (WebView) findViewById(R.id.webView);
        webView.addJavascriptInterface(new OlaMoneyInteface() {
            @Override
            @android.webkit.JavascriptInterface
            public void onPaymentDone(String jsonResponse) {
                Log.d("ola", "onPaymentDone");
            //Control is returned to the native code
            //    You can get the data in jsonResponse
            //
            }
        }, "OlaMoney"); */

        ctr = 0;
        Bill_generator_requestData();
        SPASS = (EditText) findViewById(R.id.spass);
        mAdapter = new PromoCodePagerAdapter(getSupportFragmentManager(),title,numoftab);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mTabLayout.setTabsFromPagerAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        ImageView back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        Button skip = (Button)findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(PromoCodeActivity.this,"Skip Clicked",Toast.LENGTH_LONG).show();
            }
        });
        Button APPLY = (Button)findViewById(R.id.apply);
        APPLY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("promo_code", "promo_code applied");
                Apply_PromoCode_requestData();
            }
        });

        Button proceed = (Button)findViewById(R.id.proceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bill_generated_proceed(1);
            }
        });

    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        Log.d("pay", "data: " + data);
        if (data == null) {

        } else {
            String result=data.getStringExtra("payment_result");
            Log.d("pay", "payment_result: " + result);
        }
        Log.d("pay", "pay fragment requestcode: " + requestCode);
        Log.d("pay", "pay fragment resultcode: " + resultCode);
        switch (resultCode) {
            case 100:
                Log.d("ola", "success");
                //   show_success();
            case 104:
                Log.d("ola", "failed");
                //   show_failed();
            default:
                Log.d("ola", "default");
                //   show_default();
        }
    }

    public void Bill_generator_requestData() {
        Log.d("hii", "request data");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(this);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(BILL_GENERATOR_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "10");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "20");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_Bill_generator(amount, new Callback<Bill_Generator>() {
                @Override
                public void success(Bill_Generator login, Response response) {
                    Log.d("pay", "Success : " + login.getSuccess());
                    Log.d("pay", "Message : " + login.getMessage());
                    if (login.getSuccess() == 1) {
                        bill = login.getBill();
                        Log.d("pay", "bill: " + bill);
                        bill_generated_proceed(0);
                    } else {

                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.d("pay", "failure");
                    Log.e("pay", retrofitError.toString());

                    Toast.makeText(getBaseContext(), retrofitError.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d("pay", "api null");
        }
    }

    public void Apply_PromoCode_requestData() {
        Log.d("hii", "request data");
        final String PREFS_NAME = "MyPrefsFile";

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString("reg_id", "");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(this);
        promo_code = SPASS.getText().toString();
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(APPLY_COUPON_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_ApplyCoupon(amount, promo_code, new Callback<Apply_Coupon>() {
                @Override
                public void success(Apply_Coupon server_data, Response response) {
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + server_data.getSuccess());
                    Log.d("hii", "Message : " + server_data.getMessage());
                    if (server_data.getSuccess() == 1) {
                        Log.d("promo_code", "promo_code applied success");
                        if (server_data.getType().equals("off")) {
                            Toast.makeText(getBaseContext(), "Offer: " + server_data.getOff(), Toast.LENGTH_SHORT).show();
                        } else if (server_data.getType().equals("cashback")) {
                            Toast.makeText(getBaseContext(), "CashBack: " + server_data.getCashback(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("promo_code", "promo_code applied failed");
                        Log.d("hii", "invalid credentials");
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

    public void bill_generated_proceed(int from) {
        ctr++;
        Log.d("pay", "bill_generated_proceed: from: " + from + "ctr: " + ctr);
        if (ctr == 2) {
            if (check_olacabs()) {
                Log.d("pay", "ola is present" + "check_olacabs: " + check_olacabs());
                try {
                    Log.d("pay", "bill: " + bill);
                    Intent intent = new Intent("com.olacabs.olamoney.pay");
                    intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    intent.putExtra("bill", bill); //Pass the string representation of the json bill
                //    intent.setPackage("com.test.olacabs");
                        intent.setPackage("com.olacabs.customer");
                    startActivityForResult(intent, 100);
                } catch (ActivityNotFoundException e) {
                    /*Activity not found exception - route the flow through webview*/
                }
            } else {
                Log.d("pay", "ola is not present" + "check_olacabs: " + check_olacabs());
                StringBuffer buffer=new StringBuffer("https://om.olacabs.com/olamoney/webview/index.html");
            //    StringBuffer buffer=new StringBuffer("http://sandbox.olamoney.com/olamoney/webview/index.html");
                String base64_bill = Base64.encodeToString(bill.getBytes(), Base64.DEFAULT);
                buffer.append("?bill="+base64_bill);
                buffer.append("&phone="+ URLEncoder.encode("7899861294"));
            //    webView.getSettings().setJavaScriptEnabled(true);
            //    webView.setWebViewClient(new WebViewClient());
            //    webView.loadUrl(buffer.toString());
            }
        } else {
            if (from == 1) {
                // REMEMBER IN FUTURE I HAVE TO SHOW A PROGRESS DIIALOG HERE
            } else {

            }
        }
    }
}
