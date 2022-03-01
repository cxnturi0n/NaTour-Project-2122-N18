package com.cinamidea.natour_2022.user.signup.presenters;

import com.cinamidea.natour_2022.MainContract;
import com.cinamidea.natour_2022.user.signup.contracts.SignUpContract;
import com.cinamidea.natour_2022.user.signup.models.SignUpModel;

public class SignUpPresenter implements SignUpContract.Presenter, SignUpContract.Model.OnFinishListener{

    private final SignUpContract.View view;
    private final SignUpContract.Model model;

    public SignUpPresenter(MainContract.View view, MainContract.Model model){
        this.view = view;
        this.model = model;
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
