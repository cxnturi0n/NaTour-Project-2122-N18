package com.cinamidea.natour_2022.navigation.profile.favourites;

import com.cinamidea.natour_2022.entities.Route;

import java.util.ArrayList;

public class ProfileFavouriteRoutesPresenter implements ProfileFavouriteRoutesContract.Presenter {

    private ProfileFavouriteRoutesContract.Model model;
    private ProfileFavouriteRoutesContract.View view;

    public ProfileFavouriteRoutesPresenter(ProfileFavouriteRoutesContract.View view) {
        this.model = new ProfileFavouriteRoutesModel();
        this.view = view;
    }

    @Override
    public void getFavourite(String id_token) {
        model.getFavRoutes(id_token, new ProfileFavouriteRoutesContract.Model.OnFinishedListener() {
            @Override
            public void onSuccess(ArrayList<Route> fav_routes) {
                view.loadRoutes(fav_routes);
            }

            @Override
            public void onError(String response) {

            }

            @Override
            public void onUserUnauthorized(String response) {

            }

            @Override
            public void onNetworkError(String response) {

            }
        });
    }
}
