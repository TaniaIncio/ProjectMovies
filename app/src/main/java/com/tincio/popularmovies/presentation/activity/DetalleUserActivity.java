package com.tincio.popularmovies.presentation.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tincio.popularmovies.R;
import com.tincio.popularmovies.data.model.UserRealm;
import com.tincio.popularmovies.presentation.presenter.DetallePresenter;
import com.tincio.popularmovies.presentation.presenter.UserMoviePresenter;
import com.tincio.popularmovies.presentation.util.Constants;
import com.tincio.popularmovies.presentation.view.DetalleMoveView;
import com.tincio.popularmovies.presentation.view.UserMovieView;

public class DetalleUserActivity extends AppCompatActivity implements DetalleMoveView {


    String email ;
    String birthday ;
    String id ;
    String gender;
    String userID;
    DetallePresenter presenter;
    TextView textoUser;
    TextView micuenta_correo;
    ImageView foto;
    TextView text_cumpleannios;
    Button btn;
    Button close_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_user);
        textoUser = (TextView) findViewById(R.id.micuenta_nombreusuario);
        micuenta_correo = (TextView) findViewById(R.id.micuenta_correo
        );
         foto = (ImageView) findViewById(R.id.micuenta_imagen_usuario);
         btn = (Button) findViewById(R.id.click);
         close_btn = (Button) findViewById(R.id.btn_cerrar_session);
         text_cumpleannios = (TextView) findViewById(R.id.cumpleannios);


        presenter = new DetallePresenter();
        presenter.setView(this);

        presenter.getUserDB();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalleUserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               presenter.closeSession();
            }
        });
}


    @Override
    public void ShowgetUserDB(UserRealm user, String responseError) {
      if (user!=null) {
          String imageURL = "http://graph.facebook.com/" + user.getId() + "/picture?type=small";
          text_cumpleannios.setText(user.getBirthday());
          textoUser.setText(user.getName());
          micuenta_correo.setText(user.getEmail());

          Picasso.with(DetalleUserActivity.this).load(imageURL).placeholder(this.getResources().getDrawable(R.drawable.com_facebook_profile_picture_blank_square)).error(this.getResources().getDrawable(R.drawable.com_facebook_profile_picture_blank_square)).into(foto);
          Log.d("SAVEUSER", responseError);
      }else{
          Log.d("errorDB",responseError);
      }
    }

    @Override
    public void ShowResultClose(String message) {
        if (message==getResources().getString(R.string.response_succesfull)){
            Intent intent = new Intent(DetalleUserActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            Log.d("errorCloseSessionDB",message);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }
}
