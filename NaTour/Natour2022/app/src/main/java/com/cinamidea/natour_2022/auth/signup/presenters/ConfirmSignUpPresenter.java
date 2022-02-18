package com.cinamidea.natour_2022.auth.signup.presenters;

import com.cinamidea.natour_2022.auth.signup.contracts.ConfirmSignUpContract;
import com.cinamidea.natour_2022.auth.signup.models.ConfirmSignUpModel;

public class ConfirmSignUpPresenter implements ConfirmSignUpContract.Presenter, ConfirmSignUpContract.Model.OnFinishListener {

    private final ConfirmSignUpContract.View view;
    private final ConfirmSignUpContract.Model model;


    public ConfirmSignUpPresenter(ConfirmSignUpContract.View view) {
        this.view = view;
        this.model = new ConfirmSignUpModel();
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
