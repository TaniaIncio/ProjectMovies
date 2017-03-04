package com.tincio.popularmovies.presentation.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.tincio.popularmovies.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private Button btnIniciar;
    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        btnIniciar= (Button)findViewById(R.id.btn_ingreso);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {



            //https://graph.facebook.com/" + userID + "/picture?type=large

            @Override
            public void onSuccess(LoginResult loginResult) {
               // goMainScreen();
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try {
                                    String email = object.getString("email");

                                    String id = object.getString("id");
                                    String gender = object.getString("gender");
                                    String name =  object.getString("name");
                                    Intent intent = new Intent(LoginActivity.this, DetalleUserActivity.class);
                                    intent.putExtra("name",name);
                                    intent.putExtra("id",id);
                                    intent.putExtra("email",email);
                                    intent.putExtra("gender",gender);
                                    startActivity(intent);




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Error login", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Error Login", Toast.LENGTH_SHORT).show();
            }

        });


        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.tincio.popularmovies",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance("SHA");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                md.update(signature.toByteArray());
                Log.d("KeyHash11dfdf111:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        //
    }
}
