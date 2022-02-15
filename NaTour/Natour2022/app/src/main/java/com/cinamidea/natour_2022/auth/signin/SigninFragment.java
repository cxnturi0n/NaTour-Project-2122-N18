package com.cinamidea.natour_2022.auth.signin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.CustomAuthFragment;
import com.cinamidea.natour_2022.auth.resetpassword.views.ResetCRActivity;
import com.cinamidea.natour_2022.navigation.main.HomeActivity;
import com.cinamidea.natour_2022.utilities.UserType;
import com.cinamidea.natour_2022.utilities.auth.GoogleAuthentication;

public class SigninFragment extends CustomAuthFragment implements SignInContract.View{

    public static String current_username;
    public static boolean is_admin = false;
    private GoogleAuthentication google_auth;
    private Button button_signin;
    private TextView text_forgotpwd;
    private EditText edit_user;
    private EditText edit_password;
    private Button button_googlesignin;

    private SignInContract.Presenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        google_auth = new GoogleAuthentication((AppCompatActivity) getActivity());

        UserType type = new UserType(getContext());
        presenter = new SignInPresenter(this, new SignInModel(), type, google_auth);

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

            String username = edit_user.getText().toString();
            String password = edit_password.getText().toString();

            presenter.cognitoSignInButtonClicked(username, password);

            //Set username per l'utente della chat
            current_username = username;
        });

        text_forgotpwd.setOnClickListener(view -> runHandledIntent(new Intent(getActivity(), ResetCRActivity.class)));

        button_googlesignin.setOnClickListener(view -> {

            presenter.googleSignInButtonClicked(google_auth);

        });

    }

    @Override
    public void signInSuccess() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
    }

    @Override
    public void displayError(String message) {
        //TODO Toast sign in andato male
    }


}