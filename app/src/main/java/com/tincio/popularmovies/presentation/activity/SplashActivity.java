package com.tincio.popularmovies.presentation.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.tincio.popularmovies.R;
import com.tincio.popularmovies.data.model.UserRealm;
import com.tincio.popularmovies.presentation.presenter.UserMoviePresenter;
import com.tincio.popularmovies.presentation.view.SplashView;
import com.tincio.popularmovies.presentation.view.UserMovieView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

public class SplashActivity extends AppCompatActivity implements UserMovieView {

    @BindView(R.id.txt_mensaje)
    TextView txtMensaje;
    // public TareaWSListarValores llenarValores;
    Thread timerThread;

    private static final int TIME_INTERVAL = 5000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    private Context ctx;
    boolean flag;
    UserMoviePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new UserMoviePresenter();
        presenter.setView(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_splash);
        ctx = this;
        ButterKnife.bind(this);
        txtMensaje = (TextView) findViewById(R.id.txt_mensaje);
        mBackPressed = System.currentTimeMillis();

       /* ImageView imgView=(ImageView) findViewById(R.id.imageView);
        Ion.with(imgView)
                .animateGif(AnimateGifMode.ANIMATE)
                .load("android.resource://" + getPackageName() + "/" + R.drawable.splash)
                .withBitmapInfo();*/

        presenter.getUser();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void goToNextActivity(UserRealm user) {

        if (user != null) {

            goToMainActivity();

        }
        else {
            goToLogin();
        }

    }


    private void goToMainActivity(){
        Intent intent;
        intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToLogin(){
        Intent intent;
        intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // block back press button
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showUser(final UserRealm user, String responseError) {
        timerThread = new Thread() {
            public void run() {
                try {
                    sleep(TIME_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    goToNextActivity(user);
                }
            }
        };
        timerThread.start();
    }

    @Override
    public void saveUser(String response) {

    }
}
