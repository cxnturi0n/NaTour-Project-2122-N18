package com.cinamidea.natour_2022.auth.signup.presenters;

import com.cinamidea.natour_2022.auth.signup.contracts.SignUpContract;
import com.cinamidea.natour_2022.auth.signup.models.SignUpModel;

public class SignUpPresenter implements SignUpContract.Presenter, SignUpContract.Model.OnFinishListener{

    private final SignUpContract.View view;
    private final SignUpContract.Model model;

    public SignUpPresenter(SignUpContract.View view){
        this.view = view;
        this.model = new SignUpModel();
    }

    @Override
    public void signUpButtonPressed(String username, String email, String password) {
        model.signUp(username, email, password, this);
    }

    @Override
    public void onSuccess(String message) {
        view.signUpSuccess();
    }

    @Override
    public void onError(String message) {
        view.displayError(message);
    }
}
