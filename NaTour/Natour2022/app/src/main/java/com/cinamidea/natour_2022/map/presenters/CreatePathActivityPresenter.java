package com.cinamidea.natour_2022.map.presenters;

import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.map.contracts.CreatePathActivityContract;
import com.cinamidea.natour_2022.map.models.CreatePathActivityModel;

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
                view.backToHomeAfterInsertedRoute();
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
