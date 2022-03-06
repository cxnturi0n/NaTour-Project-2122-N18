package com.cinamidea.natour.map.presenters;

import com.cinamidea.natour.entities.Route;
import com.cinamidea.natour.map.contracts.CreatePathActivityContract;

public class CreatePathActivityPresenter implements CreatePathActivityContract.Presenter{

    private CreatePathActivityContract.View view;
    private CreatePathActivityContract.Model model;

    public CreatePathActivityPresenter(CreatePathActivityContract.View view, CreatePathActivityContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void continueButtonClick(String id_token, Route route) {
        view.showLoadingDialog();
        model.insertRoute(new CreatePathActivityContract.Model.OnFinishedListener() {
            @Override
            public void onSuccess(String response_body) {
                view.dismissLoadingDialog();
                //Show toast
                view.showToastAddedRoute();
            }

            @Override
            public void onError(String response_body) {
                view.displayError(response_body);
            }

            @Override
            public void onUserUnauthorized() {
                view.logOutUnauthorizedUser();
            }
        },id_token,route);
    }
}
