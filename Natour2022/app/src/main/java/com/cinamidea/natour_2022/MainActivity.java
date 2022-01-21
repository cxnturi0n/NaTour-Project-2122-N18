package com.cinamidea.natour_2022;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import com.cinamidea.natour_2022.auth.AuthActivity;
import com.cinamidea.natour_2022.auth.SigninFragment;
import com.cinamidea.natour_2022.auth_callbacks.TokenLoginCallback;
import com.cinamidea.natour_2022.auth_util.AuthenticationHTTP;
import com.cinamidea.natour_2022.auth_util.GoogleAuthentication;
import com.cinamidea.natour_2022.routes_callbacks.RoutesCallback;
import com.cinamidea.natour_2022.routes_util.RoutesHTTP;
import com.google.android.gms.auth.api.signin.GoogleSignIn;

public class MainActivity extends AppCompatActivity {

    private Button button_signin, button_signup;
    private Animation anim_scale_up, anim_scale_down;
    private Handler handler = new Handler();
    private GoogleAuthentication google_auth;
    private SharedPreferences natour_shared_pref;


   @Override
    protected void onResume() {

        super.onResume();

        String id_token = natour_shared_pref.getString("id_token", null);

        if (id_token != null)
            cognitoSilentLogin();

        else if (GoogleSignIn.getLastSignedInAccount(this) != null)
            googleSilentLogin();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        google_auth = new GoogleAuthentication(this);

        natour_shared_pref = getSharedPreferences("natour_tokens", MODE_PRIVATE);

        String id_token = natour_shared_pref.getString("id_token", null);


        RoutesHTTP.getUserRoutes("Cognito", "angelino98", id_token, new RoutesCallback() {
            @Override
            public void handleStatus200(String response) {
                Log.e("GET USER ROUTE",response);
            }

            @Override
            public void handleStatus400(String response) {

            }

            @Override
            public void handleStatus401(String response) {

            }

            @Override
            public void handleStatus500(String response) {

            }

            @Override
            public void handleRequestException(String message) {

            }
        });







        //Controllo se l utente puo loggare in automatico(o con cognito o con google). Se no, allora alloca le componenti dell' activity (Non richiamo direttamente il metodo (Ho fatto i controlli per evitare di ripetere due volte
        //il silent sign in visto che l onresume parte in ogni caso dopo l oncreate

        if (id_token != null || GoogleSignIn.getLastSignedInAccount(this) != null)
            return;

        setContentView(R.layout.activity_main);

        startup();

        setupViewComponents();
        runButtonListeners();


        //getSharedPreferences("natour_tokens", MODE_PRIVATE).edit().clear().commit();

    }

    private void setupViewComponents() {

        button_signin = findViewById(R.id.signin);
        button_signup = findViewById(R.id.signup);
        anim_scale_up = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        anim_scale_down = AnimationUtils.loadAnimation(this, R.anim.scale_down);


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

        handler.postDelayed(() -> startActivity(intent), 170);

    }

    private void runButtonListeners() {

        button_signin.setOnClickListener(v -> {

            //otherwise signin with username and pwd
            Intent intent = new Intent(this, AuthActivity.class);
            intent.putExtra("key", "signin");
            runAnimation(button_signin);
            runIntent(intent);

        });

        button_signup.setOnClickListener(v -> {

            Intent intent = new Intent(this, AuthActivity.class);
            intent.putExtra("key", "signup");
            runAnimation(button_signup);
            runIntent(intent);


        });

    }


    private void cognitoSilentLogin() {

        String id_token = natour_shared_pref.getString("id_token", null);
        AuthenticationHTTP.tokenLogin(id_token, new TokenLoginCallback(this));
        SigninFragment.chat_username = natour_shared_pref.getString("username", null);

    }

    private void googleSilentLogin() {

        //Se L utente ha gia loggato precedentemente con google allora accedo in background (Senza mostrare la gui di google, altrimenti l utente è libero di fare signin regolarmente)
        google_auth.silentSignIn();

    }

}