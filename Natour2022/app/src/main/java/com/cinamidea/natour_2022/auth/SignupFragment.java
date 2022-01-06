package com.cinamidea.natour_2022.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth_util.AWSCognitoAuthentication;
import com.cinamidea.natour_2022.auth_util.AuthenticationCallback;
import com.cinamidea.natour_2022.auth_util.SignupCallback;

public class SignupFragment extends CustomAuthFragment {

    private Button signup_button;
    private EditText edit_user;
    private EditText edit_email;
    private EditText edit_password;

    public static String chat_username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewComponents(view);
        setListeners();

    }

    @Override
    protected void setupViewComponents(@NonNull View view) {

        signup_button = view.findViewById(R.id.fragmentSignup_signup);
        edit_user = view.findViewById(R.id.fragmentSignup_username);
        edit_email = view.findViewById(R.id.fragmentSignup_email);
        edit_password = view.findViewById(R.id.fragmentSignup_password);

        setupAnimation();



    }

    private void setListeners() {

        signup_button.setOnClickListener(v -> {

            String username = edit_user.getText().toString();
            String email = edit_email.getText().toString();
            String password = edit_password.getText().toString();

            runAnimation(signup_button);

            Intent intent = new Intent(getActivity(), PinActivity.class);

            intent.putExtra("username", username);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            AWSCognitoAuthentication auth = new AWSCognitoAuthentication();
            chat_username=username;
            auth.signUp(username, email, password, new SignupCallback(username, getContext()));

        });

    }
    
}