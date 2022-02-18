package com.cinamidea.natour_2022.auth.changepassword;

import com.cinamidea.natour_2022.utilities.UserType;

public class ChangePasswordPresenter implements ChangePasswordContract.Presenter, ChangePasswordContract.Model.OnFinishedListener {

    private final ChangePasswordContract.View view;
    private final ChangePasswordContract.Model model;

    public ChangePasswordPresenter(ChangePasswordContract.View view) {
        this.view = view;
        this.model = new ChangePasswordModel();
    }

    @Override
    public void changePasswordButtonPressed(UserType user_type, String old_password, String new_password) {
        model.changePassword(user_type, old_password, new_password, this);
    }


    @Override
    public void onSuccess() {
        view.onPasswordChanged();
    }

    @Override
    public void onError(String response) {
        view.displayError(response);
    }

    @Override
    public void onUserUnauthorized(String response) {
        view.logOutUnauthorizedUser();
    }

    @Override
    public void onNetworkError(String response) {
        view.displayError(response);
    }

}
