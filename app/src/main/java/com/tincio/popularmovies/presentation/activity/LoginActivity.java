package com.tincio.popularmovies.presentation.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.renderscript.ScriptGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.tincio.popularmovies.R;
import com.tincio.popularmovies.data.model.UserRealm;
import com.tincio.popularmovies.presentation.presenter.UserMoviePresenter;
import com.tincio.popularmovies.presentation.view.UserMovieView;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements UserMovieView{

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private Button btnIniciar;
    private EditText user_input;
    private  EditText user_contrasenia;

    UserMoviePresenter presenter;
    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        presenter = new UserMoviePresenter();
        presenter.setView(this);
        presenter.getUser();


        setContentView(R.layout.activity_login);
        btnIniciar= (Button)findViewById(R.id.btn_ingreso);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.loginButton);
        user_contrasenia = (EditText) findViewById(R.id.input_contrasenia);
        user_input = (EditText) findViewById(R.id.input_iniciar_sesion);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarUsuario()) {

                    try {
                        UserRealm user = new UserRealm();
                        user.setName(user_input.getText().toString());
                        user.setId("");
                        user.setGenere("");
                        user.setEmail("");
                        user.setBirthday("");
                        presenter.saveUser(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
        });
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

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
                                    UserRealm user = new UserRealm();
                                    user.setName(object.getString("name"));
                                    user.setId(object.getString("id"));
                                    user.setGenere(object.getString("gender"));
                                    user.setEmail(object.getString("email"));
                                    user.setBirthday(object.getString("birthday"));
                                    presenter.saveUser(user);
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

    private boolean validarUsuario(){

        if(!this.user_input.getText().toString().isEmpty() && !this.user_contrasenia.getText().toString().isEmpty()){
                return  true;
        }else{
            Toast.makeText(getApplicationContext(),"Usuario y Contraseña Requeridos",Toast.LENGTH_SHORT).show();
             return  false;
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
        presenter.getUser();
    }

    @Override
    public void showUser(UserRealm user, String responseError) {
                 if (user!=null){
                     Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                     startActivity(intent);
                 }else{
                     LoginManager.getInstance().logOut();
                 }
    }

    @Override
    public void saveUser(String response) {
        Log.d("prueba",response);
        if(response==getResources().getString(R.string.response_succesfull)){
            Intent intent = new Intent(LoginActivity.this, DetalleUserActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void showLoading() {
        Log.d("cargando","Cargando");
    }

    @Override
    public void closeLoading() {
        Log.d("Guardado","Data ya guardada");
    }
}
