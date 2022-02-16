package com.cinamidea.natour_2022.map.presenters;

import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.map.contracts.AllPathFragmentContract;
import com.cinamidea.natour_2022.map.models.AllPathFragmentModel;

public class AllPathFragmentPresenter implements AllPathFragmentContract.Presenter{
    private AllPathFragmentContract.View allPathFragments;
    private AllPathFragmentContract.Model model;

    public AllPathFragmentPresenter(AllPathFragmentContract.View allPathFragments) {
        this.allPathFragments = allPathFragments;
        this.model = new AllPathFragmentModel();
    }

    @Override
    public void getAllRoutesOnCreate(String id_token) {
        allPathFragments.showLoadingDialog();
        model.getAllRoutes(new AllPathFragmentContract.Model.OnFinishedListener() {
            @Override
            public void onStatus200(Route[] routes) {
                allPathFragments.dismissLoadingDialog();
                allPathFragments.updateLocationUI();
                allPathFragments.drawRoutes(routes);
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

        }, id_token);

    }



}
