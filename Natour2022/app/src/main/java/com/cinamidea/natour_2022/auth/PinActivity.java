package com.cinamidea.natour_2022.auth;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth_util.AWSCognitoAuthentication;
import com.cinamidea.natour_2022.auth_util.ConfirmSignupCallback;

public class PinActivity extends AppCompatActivity {

    private ImageButton button_back;
    private String username;
    private com.chaos.view.PinView pin_view;
    private Button button_verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        setupComponents();
        setListeners();

    }

    private void setupComponents() {

        button_back = findViewById(R.id.activityPin_backbutton);
        button_verify = findViewById(R.id.activityPin_button);
        pin_view = findViewById(R.id.activityPin_pin);
        username = getIntent().getStringExtra("username");

    }



    private void setListeners() {

        button_back.setOnClickListener(v -> finish());

        button_verify.setOnClickListener(view -> {
            String confirmation_code = pin_view.getText().toString();

            AWSCognitoAuthentication auth = new AWSCognitoAuthentication();

            auth.confirmSignUp(username,confirmation_code, new ConfirmSignupCallback(this));

        });
    }



}