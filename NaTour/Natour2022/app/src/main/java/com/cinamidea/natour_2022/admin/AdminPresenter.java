package com.cinamidea.natour_2022.admin;

import com.cinamidea.natour_2022.utilities.UserType;

public class AdminPresenter implements AdminContract.Presenter, AdminContract.Model.OnFinishListener {

    private final AdminContract.View view;
    private final AdminContract.Model model;

    public AdminPresenter(AdminContract.View view) {
        this.view = view;
        this.model = new AdminModel();
    }

    @Override
    public void onSendButtonClicked(UserType user_type, String subject, String body) {
        model.sendMail(user_type, subject, body, this);
    }

    @Override
    public void onSuccess(String message) {

        view.mailSent(message);

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
