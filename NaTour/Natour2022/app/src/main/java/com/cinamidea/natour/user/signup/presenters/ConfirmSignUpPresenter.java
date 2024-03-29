package com.cinamidea.natour.user.signup.presenters;

import com.cinamidea.natour.user.signup.contracts.ConfirmSignUpContract;

public class ConfirmSignUpPresenter implements ConfirmSignUpContract.Presenter, ConfirmSignUpContract.Model.OnFinishListener {

    private final ConfirmSignUpContract.View view;
    private final ConfirmSignUpContract.Model model;


    public ConfirmSignUpPresenter(ConfirmSignUpContract.View view, ConfirmSignUpContract.Model model){
        this.view = view;
        this.model = model;
    }

    @Override
    public void confirmSignUpButtonPressed(String username, String confirmation_code) {
        model.confirmSignUp(username, confirmation_code, this);
    }

    @Override
    public void onSuccess(String message) {
        view.confirmSignUpSuccess();
    }

    @Override
    public void onError(String message) {
        view.displayError(message);
    }
}
