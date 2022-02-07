package com.cinamidea.natour_2022.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.cinamidea.natour_2022.R;

public class AdminActivity extends AppCompatActivity {

    private ImageButton button_back;
    private Button button_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        setupXMLComponents();
        listeners();

    }

    private void setupXMLComponents() {

        button_back.findViewById(R.id.activityAdmin_backbutton);
        button_send.findViewById(R.id.activityAdmin_send);

    }

    private final void listeners() {

        button_back.setOnClickListener(view -> finish());

        button_send.setOnClickListener(view -> {



        });

    }

}