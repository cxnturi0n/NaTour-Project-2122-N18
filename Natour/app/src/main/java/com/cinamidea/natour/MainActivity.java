package com.cinamidea.natour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button_signin, button_signup;
    private Animation scale_up, scale_down;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_run);

        animHomepage();
        loadButtons();
        buttonsListener();

        intent = new Intent(MainActivity.this, AuthActivity.class);

    }

    private void animHomepage() {

        MotionLayout ml = findViewById(R.id.motionlayout);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                ml.transitionToEnd();
            }
        }, 1000);

    }

    private void loadButtons() {

        button_signin = findViewById(R.id.signin);
        button_signup = findViewById(R.id.signup);
        scale_up = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(this, R.anim.scale_down);

    }

    private void buttonsListener() {

        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button_signin.startAnimation(scale_up);
                button_signin.startAnimation(scale_down);
                intent.putExtra("key", "signin");
                MainActivity.this.startActivity(intent);


            }
        });

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button_signup.startAnimation(scale_up);
                button_signup.startAnimation(scale_down);
                intent.putExtra("key", "signup");
                MainActivity.this.startActivity(intent);

            }
        });

    }

}