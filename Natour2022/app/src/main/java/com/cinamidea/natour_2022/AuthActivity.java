package com.cinamidea.natour_2022;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AuthActivity extends AppCompatActivity {

    private ImageView auth_image;
    private ImageButton button_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        setupViewComponents();

        // Prendo i parametri ricevuti tramite l'Intent e decido quale fragment utilizzare.
        setupFragment();


        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void setupViewComponents() {

        auth_image = findViewById(R.id.activityAuth_image);
        button_back = findViewById(R.id.activityAuth_back);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void changeFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activityAuth_framelayout, fragment);
        fragmentTransaction.commit();

    }

    private void setupFragment() {

        String data = getIntent().getExtras().getString("key");
        if(data.equals("signin")) {

            auth_image.setImageResource(R.drawable.image_signin);
            changeFragment(new SigninFragment());

        }

        else {

            auth_image.setImageResource(R.drawable.image_signup);
            changeFragment(new SignupFragment());

        }

    }



}
