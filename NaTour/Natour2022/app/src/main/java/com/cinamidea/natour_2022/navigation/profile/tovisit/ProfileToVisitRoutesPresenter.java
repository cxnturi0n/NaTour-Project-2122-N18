package com.cinamidea.natour_2022.navigation.profile.tovisit;


import com.cinamidea.natour_2022.entities.Route;

import java.util.ArrayList;

public class ProfileToVisitRoutesPresenter implements ProfileToVisitRoutesContract.Presenter{

    private ProfileToVisitRoutesContract.Model model;
    private ProfileToVisitRoutesContract.View view;

    public ProfileToVisitRoutesPresenter(ProfileToVisitRoutesContract.View view) {
        this.model= new ProfileToVisitRoutesModel();
        this.view = view;
    }


    @Override
    public void getToVisitRoutes(String id_token) {
        model.getToVisitRoutes(id_token, new ProfileToVisitRoutesContract.Model.OnFinishedListener() {
            @Override
            public void onSuccess(ArrayList<Route> returned_tovisit_routes) {
                model.getFavouriteRoutes(id_token, returned_favourite_routes -> {
                    view.loadRoutes(returned_tovisit_routes, returned_favourite_routes);
                });
            }

            @Override
            public void onError(String message) {
                view.displayError(message);
            }

            @Override
            public void onUserUnauthorized() {
                view.logOutUnauthorizedUser();
            }

        });
    }


}
