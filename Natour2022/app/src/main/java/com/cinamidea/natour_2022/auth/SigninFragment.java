package com.cinamidea.natour_2022.auth;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cinamidea.natour_2022.HomeActivity;
import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth_util.AWSCognitoAuthentication;
import com.cinamidea.natour_2022.auth_util.AuthenticationCallback;
import com.cinamidea.natour_2022.auth_util.GoogleAuthentication;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class SigninFragment extends CustomAuthFragment {

    private Button button_signin;
    private Intent home_intent, forgotpwd_intent;
    private TextView text_forgotpwd;
    private EditText edit_user;
    private EditText edit_password;
    private Button button_googlesignin;
    private Intent googlesignin_intent;

    private GoogleAuthentication google_auth = new GoogleAuthentication(this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewComponents(view);
        setListeners();

    }

    @Override
    protected void setupViewComponents(View view) {

        button_signin = view.findViewById(R.id.fragmentSignin_signin);
        text_forgotpwd = view.findViewById(R.id.fragmentSignin_forgotpassword);
        edit_user = view.findViewById(R.id.fragmentSignin_username);
        edit_password = view.findViewById(R.id.fragmentSignin_password);
        button_googlesignin = view.findViewById(R.id.fragmentSignin_signinwithgoogle);

        home_intent = new Intent(getActivity(), HomeActivity.class);
        forgotpwd_intent = new Intent(getActivity(), ResetCRActivity.class);
        googlesignin_intent = new Intent(getActivity(), HomeActivity.class);

        setupAnimation();

    }

    private void setListeners() {

        button_signin.setOnClickListener(v -> {

            runAnimation(button_signin);

            String username = edit_user.getText().toString();
            String password = edit_password.getText().toString();

            home_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            AWSCognitoAuthentication auth = new AWSCognitoAuthentication();

            SharedPreferences userDetails = getContext().getSharedPreferences("natour_tokens", MODE_PRIVATE);

            String id_token = userDetails.getString("id_token",null);

            //If shared preferences are empty then fetch tokens
            if(id_token == null){
                auth.getIdNRefreshTokens(username, password);
                auth.handleAuthentication(new AuthenticationCallback() {
                    @Override
                    public void handleStatus200(String response) {

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
                return;
            }

            //If shared preferences are not empty then user can login with id_token
            // if it is expired(error 401) then new tokens are fetched
            // if it is invalid(error 401), go back to login fragment and delete tokens
            auth.tokenLogin(id_token);
            auth.handleAuthentication(new AuthenticationCallback() {
                @Override
                public void handleStatus200(String response) {

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

        });

        text_forgotpwd.setOnClickListener(view -> runHandledIntent(forgotpwd_intent));

        button_googlesignin.setOnClickListener(view -> {
            google_auth.signIn(googlesignin_intent);
        });

    }


}