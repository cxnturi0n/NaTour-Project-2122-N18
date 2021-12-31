package com.cinamidea.natour_2022.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cinamidea.natour_2022.HomeActivity;
import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth_util.AWSCognitoAuthentication;

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

        button_back.setOnClickListener(v -> finish());

        button_verify.setOnClickListener(view -> {
            String confirmation_code = pin_view.getText().toString();
            AWSCognitoAuthentication auth = new AWSCognitoAuthentication(this);
            auth.initiateConfirmSignUp(username, confirmation_code);
            auth.handleAuthentication(() -> {
                startActivity(intent);
            });
        });
    }



}