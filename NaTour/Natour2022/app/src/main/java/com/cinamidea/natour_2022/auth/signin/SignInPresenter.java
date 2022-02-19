package com.cinamidea.natour_2022.auth.signin;

import android.content.SharedPreferences;

public class SignInPresenter implements SignInContract.Presenter, SignInContract.Model.OnFinishListenerCognito, SignInContract.Model.OnFinishListenerGoogle {

    // creating object of View Interface
    private final SignInContract.View view;

    // creating object of Model Interface
    private final SignInContract.Model model;

    public SignInPresenter(SignInContract.View view) {
        this.view = view;
        this.model = new SignInModel();
    }

    @Override
    public void cognitoSignInButtonClicked(String username, String password, SharedPreferences cognito_preferences) {
        model.cognitoSignIn(username, password, cognito_preferences, this);
    }


    @Override
    public void googleSignUpButtonClicked(String username, String email, String id_token, SharedPreferences shared_preferences) {
        model.googleSignUp(username, email, id_token, shared_preferences, this);
    }

    @Override
    public void onSuccess() {
        view.signInSuccess();
    }


    @Override
    public void onError(String message) {
        view.displayError(message);
    }

}
