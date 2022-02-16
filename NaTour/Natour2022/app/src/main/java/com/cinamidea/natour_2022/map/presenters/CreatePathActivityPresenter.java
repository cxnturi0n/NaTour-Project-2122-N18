package com.cinamidea.natour_2022.map.presenters;

import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.map.contracts.CreatePathActivityContract;
import com.cinamidea.natour_2022.map.models.CreatePathActivityModel;
import com.cinamidea.natour_2022.utilities.UserType;

public class CreatePathActivityPresenter implements CreatePathActivityContract.Presenter{

    private CreatePathActivityContract.View view;
    private CreatePathActivityContract.Model model;

    public CreatePathActivityPresenter(CreatePathActivityContract.View view) {
        this.view = view;
        this.model = new CreatePathActivityModel();
    }

    @Override
    public void continueButtonClick(String id_token, Route route) {
        view.showLoadingDialog();
        model.insertRoute(new CreatePathActivityContract.Model.OnFinishedListener() {
            @Override
            public void onStatus200(String response_body) {
                view.dismissLoadingDialog();
                //Show toast
                view.showToastAddedRoute();
                view.backToHomeAfterInsertedRoute();
            }

            @Override
            public void onStatus400(String response_body) {

            }

            @Override
            public void onStatus401(String response_body) {

            }

            @Override
            public void onStatus500(String response_body) {

            }
        },id_token,route);
    }
}
