package com.cinamidea.natour_2022.navigation.profile.created;

import com.cinamidea.natour_2022.entities.Route;

import java.util.ArrayList;

public class ProfileMyRoadsPresenter implements ProfileMyRoadsContract.Presenter{
    private ProfileMyRoadsContract.Model model;
    private ProfileMyRoadsContract.View view;

    public ProfileMyRoadsPresenter(ProfileMyRoadsContract.View view, ProfileMyRoadsContract.Model model) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getMyRoutes(String username, String id_token) {

       model.getUserRoutes(username, id_token, new ProfileMyRoadsContract.Model.OnFinishedListener() {
           @Override
           public void onSuccess(ArrayList<Route> returned_user_routes) {

                model.getFavouriteRoutes(username,id_token, returned_favourite_routes -> view.loadRoutes(returned_user_routes, returned_favourite_routes));
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
