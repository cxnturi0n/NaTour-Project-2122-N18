package com.cinamidea.natour_2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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
        setContentView(R.layout.activity_main);

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

                intent.putExtra("key", "signin");
                buttonAnimation(button_signin);

            }
        });

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("key", "signup");
                buttonAnimation(button_signup);


            }
        });

    }

    private void buttonAnimation(Button button) {

        button.startAnimation(scale_up);
        button.startAnimation(scale_down);
        button.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        }, 200);

    }

}