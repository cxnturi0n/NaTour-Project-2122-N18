package com.cinamidea.natour_2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PinActivity extends AppCompatActivity {

    private ImageButton button_back;
    private TextView textvw_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        button_back = findViewById(R.id.activityPin_backbutton);
        textvw_email = findViewById(R.id.activityPin_mail);

        String email = getIntent().getExtras().getString("email");
        maskEmail(email);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void maskEmail(String email) {

        StringBuilder sb = new StringBuilder(email);
        for (int i = 3; i < sb.length() && sb.charAt(i) != '@'; ++i) {
            sb.setCharAt(i, '*');
        }
        email = sb.toString();

        textvw_email.setText(email);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}