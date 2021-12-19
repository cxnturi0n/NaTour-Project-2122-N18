package com.cinamidea.natour_2022;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AuthActivity extends AppCompatActivity {

    protected ImageView auth_image;
    protected ImageButton button_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_auth);
        auth_image = findViewById(R.id.activityAuth_image);
        button_back = findViewById(R.id.activityAuth_back);


        String data = getIntent().getExtras().getString("key");
        if(data.equals("signin")) {

            auth_image.setImageResource(R.drawable.image_signin);
            changeFragment(new SigninFragment());

        }

        else {

            auth_image.setImageResource(R.drawable.image_signup);
            changeFragment(new SignupFragment());

        }


        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();


            }
        });


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void changeFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activityAuth_framelayout, fragment);
        fragmentTransaction.commit();

    }



}
