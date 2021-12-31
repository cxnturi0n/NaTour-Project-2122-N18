package com.cinamidea.natour_2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private CircleImageView imgbutton_avatar;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupViewComponents();

        imgbutton_avatar.setOnClickListener(view -> {
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });


    }

    private void setupViewComponents() {

        imgbutton_avatar = findViewById(R.id.activityHome_avatar);

        intent = new Intent(HomeActivity.this, ProfileActivity.class);

    }

}