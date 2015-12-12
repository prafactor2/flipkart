package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import models.Login;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by Rajdeep Singh on 16-06-2015.
 */
public class LoginFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    TextView textView;
    GoogleApiClient mGoogleApiClient;

    public static String mSharedPreferencesFile = "MY_SHARED_FILE";
    public static String mUserNameFile = "USERNAME_FILE";
    SharedPreferences mSharedPreferences;
    Button button;
    TextView name,pass;
    String uname = "hi";
    String upass = "234";
    EditText USER, PASS;

    public static final String LOGIN_ENDPOINT = "http://192.168.0.9:1337/login";
    private static final int RC_SIGN_IN = 9001;

    private void printHashKey() {
        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo(
                    "com.example.khaalijeb.newlistview_module",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    String username, password;

    private void GooglesignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void GooglesignOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                });
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public void Login_requestData() {
        Log.d("hii", "request data");
        final String PREFS_NAME = "MyPrefsFile";

        final SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString("reg_id", "");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(getActivity());
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(LOGIN_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_Login(username, password, token, new Callback<Login>() {
                @Override
                public void success(Login login, Response response) {
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + login.getSuccess());
                    Log.d("hii", "Message : " + login.getMessage());
                    if (login.getSuccess() == 1) {
                        settings.edit().putBoolean("login", true).apply();
                        settings.edit().putString("email_id", login.getEmail_id()).apply();
                        settings.edit().putString("mobile_no", login.getMobile_no()).apply();
                        settings.edit().putInt("follow_notifications", login.getTot_follow_not()).apply();
                        settings.edit().putInt("notifications_notifications", login.getTot_not()).apply();
                        Log.d("hii", "success");
                        Toast.makeText(getActivity(), login.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("not", "total_follow_not: " + login.getTot_follow_not());
                        Log.d("hii", "ye hai" + (new Gson().toJson(login)));
                        Intent it = new Intent(getActivity(), MainActivity.class);
                        startActivity(it);
                    } else {
                        Log.d("hii", "invalid credentials");
                        Toast.makeText(getActivity(), login.getMessage(), Toast.LENGTH_SHORT).show();
                        USER.setText("");
                        PASS.setText("");
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.d("hii", "failure");
                    Log.e("hii", retrofitError.toString());

                    Toast.makeText(getActivity(), retrofitError.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d("hii", "api null");
        }
    }

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
                Toast.makeText(getActivity(), "Welcome " + profile.getName() + profile.getLinkUri() + profile.getId(), Toast.LENGTH_LONG).show();
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

    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.facebook_login_button);
        loginButton.setReadPermissions("public_profile", "email", "user_birthday");
        loginButton.setFragment(this);
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.activity_login, container, false);
        TextView txt = (TextView) v.findViewById(R.id.textView);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/final.ttf");
        txt.setTypeface(font);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        com.facebook.Profile profile = com.facebook.Profile.getCurrentProfile();
        if (profile != null) {
            Toast.makeText(getActivity(), "Welcome " + profile.getName(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        USER = (EditText) getActivity().findViewById(R.id.username);
        PASS = (EditText) getActivity().findViewById(R.id.password);
        boolean check_net = isOnline();
        if (check_net) {
            Toast.makeText(getActivity(), "Network Connected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "No Network Connection", Toast.LENGTH_SHORT).show();
        }
        Log.d("hii", "Login oncreate");

        Button signin = (Button)getView().findViewById((R.id.button));

        signin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("hii", "login pressed");
                username = USER.getText().toString();
                password = PASS.getText().toString();
                Login_requestData();
                Log.d("hii", "after Requesting");
            }
        });

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

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .build();

        SignInButton signInButton = (SignInButton) getActivity().findViewById(R.id.goole_sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                GooglesignIn();
            }
        });
    /*    Button google_logout = (Button) getView().findViewById(R.id.google_signout);
        google_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        }); */
    }

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
                Toast.makeText(getActivity(), "Google Sign In Welcome " + acct.getDisplayName() + acct.getGrantedScopes() + acct.getEmail() + acct.getPhotoUrl() + acct.getId(), Toast.LENGTH_LONG).show();
                Log.d("api", "Google Sign In Welcome " + acct.getDisplayName() + acct.getGrantedScopes() + acct.getEmail() + acct.getPhotoUrl() + acct.getId());
            }
            Log.d("api", "Google Sign In Welcome " + acct.toString() + acct.describeContents() + acct.getGrantedScopes() + acct.getEmail() + acct.getPhotoUrl() + acct.getId());

            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Log.d("api", "true");
                Toast.makeText(getActivity(), "true", Toast.LENGTH_LONG).show();

                Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
                String personGooglePlusProfile = currentPerson.getUrl();
                int persongender = currentPerson.getGender();
                String personbirthday = currentPerson.getBirthday();
                Log.d("api", "connected" + "gender: " + persongender + " birthday: " + personbirthday);
            } else {
                Toast.makeText(getActivity(), "false", Toast.LENGTH_LONG).show();
                Log.d("api", "false");
            }
        } else {

        }
    }
}
