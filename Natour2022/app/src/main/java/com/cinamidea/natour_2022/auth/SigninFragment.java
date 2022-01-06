package com.cinamidea.natour_2022.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cinamidea.natour_2022.HomeActivity;
import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth_util.AWSCognitoAuthentication;
import com.cinamidea.natour_2022.auth_util.GetTokensCallback;
import com.cinamidea.natour_2022.auth_util.GoogleAuthentication;

public class SigninFragment extends CustomAuthFragment {

    private Button button_signin;
    private TextView text_forgotpwd;
    private EditText edit_user;
    private EditText edit_password;
    private Button button_googlesignin;

    public static String chat_username;

    private GoogleAuthentication google_auth = new GoogleAuthentication(this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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

        setupAnimation();

    }

    private void setListeners() {

        button_signin.setOnClickListener(v -> {


            runAnimation(button_signin);

            Intent home_intent = new Intent(getActivity(), HomeActivity.class);
            home_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            String username = edit_user.getText().toString();
            String password = edit_password.getText().toString();

            AWSCognitoAuthentication auth = new AWSCognitoAuthentication();

            auth.getIdNRefreshTokens(username, password, new GetTokensCallback(getActivity(), username));

            //Set username per l'utente della chat
            chat_username = username;
        });

        text_forgotpwd.setOnClickListener(view -> runHandledIntent(new Intent(getActivity(), ResetCRActivity.class)));

        button_googlesignin.setOnClickListener(view -> {
            google_auth.signIn(new Intent(getActivity(), HomeActivity.class));
        });

    }

}