package com.cinamidea.natour_2022.auth.signin;

import com.cinamidea.natour_2022.entities.Tokens;
import com.cinamidea.natour_2022.utilities.ResponseDeserializer;
import com.cinamidea.natour_2022.utilities.UserType;
import com.cinamidea.natour_2022.utilities.auth.GoogleAuthentication;
import com.google.gson.Gson;

public class SignInPresenter implements SignInContract.Presenter, SignInContract.Model.OnFinishListener {

    // creating object of View Interface
    private final SignInContract.View view;

    // creating object of Model Interface
    private final SignInContract.Model model;

    private final UserType user_type;

    private final GoogleAuthentication google_auth;

    private String username;

    public SignInPresenter(SignInContract.View view, SignInContract.Model model, UserType user_type, GoogleAuthentication google_auth){
        this.view = view;
        this.model = model;
        this.user_type = user_type;
        this.google_auth = google_auth;
    }

    @Override
    public void cognitoSignInButtonClicked(String username, String password) {
        this.username = username;
        model.cognitoSignIn(username, password, this);
    }

    @Override
    public void googleSignInButtonClicked(GoogleAuthentication google_auth) {
        model.googleSignIn(google_auth);
    }

    @Override
    public void onSuccess(String message) {

        Gson gson = new Gson();
        Tokens tokens = gson.fromJson(ResponseDeserializer.removeQuotesAndUnescape(message), Tokens.class);

        user_type.setUsername(username);
        user_type.setIdToken(tokens.getId_token());
        user_type.setAccessToken(tokens.getAccess_token());
        user_type.setRefreshToken(tokens.getRefresh_token());

        view.signInSuccess();
    }

    @Override
    public void onFailure(String message) {
        view.displayError(message);
    }

}
