package com.cinamidea.natour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button button_signin, button_signup;
    Animation scale_up, scale_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_auth);

        animHomepage();
        button_signin = findViewById(R.id.signin);
        button_signup = findViewById(R.id.signup);
        scale_up = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button_signin.startAnimation(scale_up);
                button_signin.startAnimation(scale_down);

            }
        });

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button_signup.startAnimation(scale_up);
                button_signup.startAnimation(scale_down);

            }
        });

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

}