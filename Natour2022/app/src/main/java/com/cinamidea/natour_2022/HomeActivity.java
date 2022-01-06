package com.cinamidea.natour_2022;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cinamidea.natour_2022.auth.SigninFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private CircleImageView imgbutton_avatar;
    private Intent intent;
    private TextView textview_username;

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
        textview_username = findViewById(R.id.activityHome_usernameText);

        textview_username.setText(SigninFragment.chat_username);
        intent = new Intent(HomeActivity.this, ProfileActivity.class);

    }

}