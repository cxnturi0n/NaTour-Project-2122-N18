package com.cinamidea.natour_2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.cinamidea.natour_2022.auth.AuthActivity;

import io.getstream.chat.android.client.ChatClient;
import io.getstream.chat.android.client.logger.ChatLogLevel;
import io.getstream.chat.android.client.models.User;
import io.getstream.chat.android.livedata.ChatDomain;

public class MainActivity extends AppCompatActivity {

    private Button button_signin, button_signup;
    private Animation anim_scale_up, anim_scale_down;
    private Intent intent;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startup();

        setupViewComponents();
        runButtonListeners();

    }

    private void chat() {

        try {
            ChatClient client = new ChatClient.Builder("nbcrsrs5wrj8", getApplicationContext())
                    .logLevel(ChatLogLevel.ALL) // Set to NOTHING in prod
                    .build();
            new ChatDomain.Builder(client, getApplicationContext()).build();

            User user = new User();
            user.setId("Fabio9161998");
            user.setName("Fabiolino");

            String token = client.devToken(user.getId());
            client.connectUser(
                    user,
                    token
            ).enqueue();
        }catch(Exception e){
            Log.e("ERRORR",e.getMessage());
        }

    }

    private void setupViewComponents() {

        button_signin =  findViewById(R.id.signin);
        button_signup =  findViewById(R.id.signup);
        anim_scale_up = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        anim_scale_down = AnimationUtils.loadAnimation(this, R.anim.scale_down);
        intent = new Intent(MainActivity.this, AuthActivity.class);

    }

    private void startup() {

        MotionLayout ml = findViewById(R.id.motionlayout);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                ml.transitionToEnd();
            }
        }, 1000);

    }

    private void runAnimation(Button button) {

        button.startAnimation(anim_scale_up);
        button.startAnimation(anim_scale_down);

    }

    private void runIntent(Intent intent) {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(intent);

            }
        },170);

    }

    private void runButtonListeners() {

        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("key", "signin");
                runAnimation(button_signin);
                runIntent(intent);

            }
        });

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("key", "signup");
                runAnimation(button_signup);
                runIntent(intent);


            }
        });

    }

}