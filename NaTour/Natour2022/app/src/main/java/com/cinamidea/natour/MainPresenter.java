package com.cinamidea.natour;

import android.content.SharedPreferences;

import com.cinamidea.natour.utilities.UserType;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class MainPresenter implements MainContract.Presenter, MainContract.Model.OnFinishListener{

    private final MainContract.View view;
    private final MainContract.Model model;

    public MainPresenter(MainContract.View view, MainContract.Model model){
        this.view = view;
        this.model = model;
    }

    @Override
    public void googleSilentSignIn(GoogleSignInClient client, SharedPreferences google_preferences) {
        model.googleSilentSignIn(client, google_preferences, this);
    }

    @Override
    public void cognitoSilentSignIn(UserType user_type) {
        model.cognitoSilentSignIn(user_type, this);
    }

    @Override
    public void onSuccess() {
        view.silentSignIn();
    }

    @Override
    public void onUserUnauthorized(String message) {
        view.displayError(message);
    }

    @Override
    public void onError(String message) {
        view.displayError(message);
    }

}
