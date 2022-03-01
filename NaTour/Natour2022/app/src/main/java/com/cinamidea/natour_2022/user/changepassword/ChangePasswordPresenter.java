package com.cinamidea.natour_2022.user.changepassword;

import com.cinamidea.natour_2022.utilities.UserType;

public class ChangePasswordPresenter implements ChangePasswordContract.Presenter, ChangePasswordContract.Model.OnFinishedListener {

    private final ChangePasswordContract.View view;
    private final ChangePasswordContract.Model model;

    public ChangePasswordPresenter(ChangePasswordContract.View view, ChangePasswordContract.Model model) {
        this.view = view;
        this.model = model;
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

}
