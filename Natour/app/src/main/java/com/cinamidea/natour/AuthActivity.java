package com.cinamidea.natour;

import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AuthActivity extends AppCompatActivity {

    protected ImageView auth_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_auth);

        auth_image = findViewById(R.id.auth_image);
//        auth_image.setImageResource(R.drawable.ic_signup_image); //Così si cambia l'immagine (controllare la key dell'intent)


    }

}
