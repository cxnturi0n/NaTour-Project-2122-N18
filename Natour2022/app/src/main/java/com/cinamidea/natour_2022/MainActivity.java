package com.cinamidea.natour_2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.cinamidea.natour_2022.auth.AuthActivity;
import com.cinamidea.natour_2022.auth_util.AWSCognitoAuthentication;
import com.cinamidea.natour_2022.auth_util.GetTokensCallback;
import com.cinamidea.natour_2022.auth_util.TokenLoginCallback;
import com.cinamidea.natour_2022.auth_util.Tokens;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;


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

/*        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            Log.e("s",account.getEmail());
            intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        else {*/
            setContentView(R.layout.activity_main);

            startup();

            setupViewComponents();
            runButtonListeners();

        //getSharedPreferences("natour_tokens", MODE_PRIVATE).edit().clear().commit();

        //}
    }

    private void setupViewComponents() {

        button_signin =  findViewById(R.id.signin);
        button_signup =  findViewById(R.id.signup);
        anim_scale_up = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        anim_scale_down = AnimationUtils.loadAnimation(this, R.anim.scale_down);
        intent = new Intent(this, AuthActivity.class);

    }

    private void startup() {

        MotionLayout ml = findViewById(R.id.motionlayout);
        Handler handler = new Handler();
        handler.postDelayed(() -> ml.transitionToEnd(), 1000);

    }

    private void runAnimation(Button button) {

        button.startAnimation(anim_scale_up);
        button.startAnimation(anim_scale_down);

    }

    private void runIntent(Intent intent) {

        handler.postDelayed(() -> startActivity(intent),170);

    }

    private void runButtonListeners() {

        button_signin.setOnClickListener(v -> {

            SharedPreferences user_details = getSharedPreferences("natour_tokens", MODE_PRIVATE);

            String id_token = user_details.getString("id_token",null);

            //If shared preferences are empty then fetch tokens
            if(id_token != null){
                AWSCognitoAuthentication auth = new AWSCognitoAuthentication();
                auth.tokenLogin(id_token, new TokenLoginCallback(this));
                return;
            }

            //otherwise signin with username and pwd
            intent.putExtra("key", "signin");
            runAnimation(button_signin);
            runIntent(intent);

        });

        button_signup.setOnClickListener(v -> {

            intent.putExtra("key", "signup");
            runAnimation(button_signup);
            runIntent(intent);


        });

    }

}