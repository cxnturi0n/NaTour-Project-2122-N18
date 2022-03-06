package com.cinamidea.natour.navigation.profile.favourites;

import com.cinamidea.natour.entities.Route;

import java.util.ArrayList;

public class ProfileFavouriteRoutesPresenter implements ProfileFavouriteRoutesContract.Presenter {

    private ProfileFavouriteRoutesContract.Model model;
    private ProfileFavouriteRoutesContract.View view;

    public ProfileFavouriteRoutesPresenter(ProfileFavouriteRoutesContract.View view, ProfileFavouriteRoutesContract.Model model) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getFavouriteRoutes(String username, String id_token) {
        model.getFavRoutes(username, id_token, new ProfileFavouriteRoutesContract.Model.OnFinishedListener() {
            @Override
            public void onSuccess(ArrayList<Route> fav_routes) {
                view.loadRoutes(fav_routes);
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
