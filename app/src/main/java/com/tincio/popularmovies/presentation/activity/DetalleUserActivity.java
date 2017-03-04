package com.tincio.popularmovies.presentation.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tincio.popularmovies.R;
import com.tincio.popularmovies.presentation.util.Constants;

public class DetalleUserActivity extends AppCompatActivity {


    String email ;
    String birthday ;
    String id ;
    String gender;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_detalle_user);
        String name = getIntent().getExtras().getString("name");
        String id = getIntent().getExtras().getString("id");
        String gender = getIntent().getExtras().getString("gender");
        String email = getIntent().getExtras().getString("email");
        String ID =  getIntent().getExtras().getString("id");

        String imageURL = "http://graph.facebook.com/"+ID+"/picture?type=small";
        final TextView textoUser = (TextView) findViewById(R.id.micuenta_nombreusuario);
        final TextView micuenta_correo = (TextView) findViewById(R.id.micuenta_correo
        );
        final ImageView foto = (ImageView) findViewById(R.id.micuenta_imagen_usuario);

        if (textoUser != null) {
            textoUser.setText(name);
        }
        if (micuenta_correo !=null){

            micuenta_correo.setText(email);
        }

      //  Picasso.with(this).load(imageURL).into(foto);
         //micuenta_correo
        Log.d("URL",imageURL);

     //   Picasso.with(DetalleUserActivity.this).load(imageURL).placeholder(this.getResources().getDrawable(R.drawable.com_facebook_button_login_logo)).error(this.getResources().getDrawable(R.drawable.com_facebook_button_icon_blue)).into(foto);


       // final ImageView img = (ImageView)
}
}
