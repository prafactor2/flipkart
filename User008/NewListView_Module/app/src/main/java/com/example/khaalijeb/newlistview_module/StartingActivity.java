package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONObject;

public class StartingActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    ImageView one;
    ImageView two;
    ImageView three;
    ImageView four;
    ImageView five;
    TextView signup;
    TextView login;

    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private CallbackManager mCallbackmanager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.d("api", "facebook success");
            AccessToken accessToken = loginResult.getAccessToken();
            com.facebook.Profile profile = com.facebook.Profile.getCurrentProfile();
            if (profile != null) {
                Toast.makeText(getBaseContext(), "Welcome " + profile.getName() + profile.getLinkUri() + profile.getId(), Toast.LENGTH_LONG).show();
            }
            //
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            // Application code
                            Log.d("facebook", "response: " + response.toString());
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender, birthday");
            request.setParameters(parameters);
            request.executeAsync();
            //
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };

    private void GooglesignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_starting);
        //
        LoginButton loginButton = (LoginButton) findViewById(R.id.facebook_signup);
        loginButton.setReadPermissions("public_profile", "email", "user_birthday");
        mCallbackmanager = CallbackManager.Factory.create();
        loginButton.registerCallback(mCallbackmanager, mCallback);
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(com.facebook.Profile oldProfile, com.facebook.Profile currentProfile) {

            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
        //

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PROFILE))
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestProfile()
                .requestEmail()
                .build();

/*
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();
*/

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .build();

        SignInButton signInButton = (SignInButton) findViewById(R.id.google_signup);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                GooglesignIn();
            }
        });

        //

        one = (ImageView) findViewById(R.id.one);
        two = (ImageView) findViewById(R.id.two);
        three = (ImageView) findViewById(R.id.three);
        four = (ImageView) findViewById(R.id.four);
        five = (ImageView) findViewById(R.id.five);
        signup = (TextView) findViewById(R.id.signup);
        login = (TextView) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartingActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartingActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });

        // setText(Integer.toString(stringArray.length));

        PagerAdapter adapter = new ImagePagerAdapter(this, imageArra, stringArray);
        ViewPager myPager = (ViewPager) findViewById(R.id.pager);
        myPager.setAdapter(adapter);
        myPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        one.setImageResource(R.drawable.cv);
                        two.setImageResource(R.drawable.cd);
                        three.setImageResource(R.drawable.cd);
                        four.setImageResource(R.drawable.cd);
                        five.setImageResource(R.drawable.cd);

                        break;
                    case 1:
                        one.setImageResource(R.drawable.cd);
                        two.setImageResource(R.drawable.cv);
                        three.setImageResource(R.drawable.cd);
                        four.setImageResource(R.drawable.cd);
                        five.setImageResource(R.drawable.cd);

                        break;
                    case 2:
                        one.setImageResource(R.drawable.cd);
                        two.setImageResource(R.drawable.cd);
                        three.setImageResource(R.drawable.cv);
                        four.setImageResource(R.drawable.cd);
                        five.setImageResource(R.drawable.cd);

                        break;
                    case 3:
                        one.setImageResource(R.drawable.cd);
                        two.setImageResource(R.drawable.cd);
                        three.setImageResource(R.drawable.cd);
                        four.setImageResource(R.drawable.cv);
                        five.setImageResource(R.drawable.cd);

                        break;
                    case 4:
                        one.setImageResource(R.drawable.cd);
                        two.setImageResource(R.drawable.cd);
                        three.setImageResource(R.drawable.cd);
                        four.setImageResource(R.drawable.cd);
                        five.setImageResource(R.drawable.cv);

                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private int imageArra[] = {R.drawable.levis, R.drawable.nike, R.drawable.nike, R.drawable.levis, R.drawable.nike};

    private String[] stringArray = new String[]{"Image a", "Image b", "Image c", "Image d", "Image e"};

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("api", "google api connected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("api", "google api OnConnectedSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("api", "google api OnConnectionFailed");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        Log.d("api", "onactivityresult fragment requestcode: " + requestCode);
        if (requestCode == RC_SIGN_IN) {
            Log.d("api", "google fragment onactivityresult");
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            Log.d("api", "facebook fragment onactivityresult" + "requestcode: " + requestCode);
            mCallbackmanager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("google", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {
                Toast.makeText(getBaseContext(), "Google Sign In Welcome " + acct.getDisplayName() + acct.getGrantedScopes() + acct.getEmail() + acct.getPhotoUrl() + acct.getId(), Toast.LENGTH_LONG).show();
                Log.d("api", "Google Sign In Welcome " + acct.getDisplayName() + acct.getGrantedScopes() + acct.getEmail() + acct.getPhotoUrl() + acct.getId());
            }
            Log.d("api", "Google Sign In Welcome " + acct.toString() + acct.describeContents() + acct.getGrantedScopes() + acct.getEmail() + acct.getPhotoUrl() + acct.getId());

            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Log.d("api", "true");
                Toast.makeText(getBaseContext(), "true", Toast.LENGTH_LONG).show();

                Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
                String personGooglePlusProfile = currentPerson.getUrl();
                int persongender = currentPerson.getGender();
                String personbirthday = currentPerson.getBirthday();
                Log.d("api", "connected" + "gender: " + persongender + " birthday: " + personbirthday);
            } else {
                Toast.makeText(getBaseContext(), "false", Toast.LENGTH_LONG).show();
                Log.d("api", "false");
            }
        } else {

        }
    }
}

