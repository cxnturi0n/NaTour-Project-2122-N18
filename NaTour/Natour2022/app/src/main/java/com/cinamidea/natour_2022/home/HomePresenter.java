package com.cinamidea.natour_2022.home;

import com.cinamidea.natour_2022.entities.Route;

import java.util.ArrayList;

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View view;
    private final HomeContract.Model model;


    public HomePresenter(HomeContract.View view) {
        this.view = view;
        this.model = new HomeModel();
    }

    @Override
    public void getAllRoutesButtonClicked(String username, String id_token) {

        model.getAllRoutes(username, id_token, new HomeContract.Model.OnFinishedListener() {
            @Override
            public void onSuccess(ArrayList<Route> returned_routes) {
                model.getFavouriteRoutes(username, id_token, returned_favourite_routes -> view.loadRoutes(returned_routes, returned_favourite_routes));
            }

            @Override
            public void onError(String response) {
                view.displayError(response);
            }

            @Override
            public void onUserUnauthorized() {
                view.logOutUnauthorizedUser();
            }


        });

    }

    @Override
    public void getRoutesByDifficultyButtonClicked(String username, String id_token, String difficulty) {

        model.getRoutesByDifficulty(username, id_token, difficulty, new HomeContract.Model.OnFinishedListener() {
            @Override
            public void onSuccess(ArrayList<Route> routes) {
                model.getFavouriteRoutes(username, id_token, returned_favourite_routes -> view.loadRoutes(routes, returned_favourite_routes));
            }

            @Override
            public void onError(String response) {
                view.displayError(response);
            }

            @Override
            public void onUserUnauthorized() {
                view.logOutUnauthorizedUser();
            }

        });

    }

}
