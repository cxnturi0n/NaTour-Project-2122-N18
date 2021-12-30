package com.cinamidea.natour_2022.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cinamidea.natour_2022.HomeActivity;
import com.cinamidea.natour_2022.R;

public class SigninFragment extends CustomAuthFragment {

    private Button button_signin;
    private Intent home_intent, forgotpwd_intent;
    private TextView text_forgotpwd;

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
        customListeners();

    }

    @Override
    protected void setupViewComponents(View view) {

        button_signin = view.findViewById(R.id.fragmentSignin_signin);
        text_forgotpwd = view.findViewById(R.id.fragmentSignin_forgotpassword);
        home_intent = new Intent(getActivity(), HomeActivity.class);
        forgotpwd_intent = new Intent(getActivity(), ResetCRActivity.class);

        setupAnimation();

    }

    private void customListeners() {

        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                runAnimation(button_signin);
                runHandledIntent(home_intent);

            }
        });

        text_forgotpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                runHandledIntent(forgotpwd_intent);

            }
        });

    }

}