package com.cinamidea.natour_2022.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.cinamidea.natour_2022.R;

public class ChangePasswordActivity extends AppCompatActivity {

    ImageButton button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        setupComponents();
        setListeners();

    }

    private void setupComponents() {

        button_back = findViewById(R.id.activityChangePassw_back);

    }

    private void setListeners() {

        button_back.setOnClickListener(view -> finish());

    }

}