package com.cinamidea.natour_2022.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cinamidea.natour_2022.HomeActivity;
import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth_util.Authentication;

public class PinActivity extends AppCompatActivity {

    private ImageButton button_back;
    private Intent intent;
    private String username;
    private com.chaos.view.PinView pin_view;
    private Button button_verify;
    private TextView tw_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        button_back = findViewById(R.id.activityPin_backbutton);
        button_verify = findViewById(R.id.activityPin_button);
        pin_view = findViewById(R.id.activityPin_pin);
        tw_email = findViewById(R.id.activityPin_mail);
        tw_email.setText(getIntent().getStringExtra("email"));
        username = getIntent().getStringExtra("username");

        intent = new Intent(this, HomeActivity.class);

        setListeners();

    }



    private void setListeners() {

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button_verify.setOnClickListener(view -> {
            String confirmation_code = pin_view.getText().toString();
            Authentication auth = new Authentication(this);
            auth.initiateConfirmSignUp(username, confirmation_code);
            auth.handleAuthentication(() -> {
                startActivity(intent);
            });
        });
    }



}