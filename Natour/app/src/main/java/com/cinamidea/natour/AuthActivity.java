package com.cinamidea.natour;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AuthActivity extends AppCompatActivity {

    protected ImageView auth_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

       // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_auth);
        auth_image = findViewById(R.id.activityAuth_image);


        String data = getIntent().getExtras().getString("key");
        if(data.equals("signin")) {

            auth_image.setImageResource(R.drawable.ic_signin_image);
            changeFragment(new SigninFragment());

        }

        else {

            auth_image.setImageResource(R.drawable.ic_signup_image);
            changeFragment(new SignupFragment());

        }


    }

    private void changeFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activityAuth_framelayout, fragment);
        fragmentTransaction.commit();

    }


}
