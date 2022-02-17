package com.cinamidea.natour_2022.navigation.profile;

import com.cinamidea.natour_2022.entities.Route;

import java.util.ArrayList;

public class ProfileMyRoadsPresenter implements ProfileMyRoadsContract.Presenter{
    private ProfileMyRoadsContract.Model model;
    private ProfileMyRoadsContract.View view;

    public ProfileMyRoadsPresenter(ProfileMyRoadsContract.View view) {
        this.model = new ProfileMyRoadsModel();
        this.view = view;
    }

    @Override
    public void getRoutes(String id_token) {
       model.getUserRoutes(id_token, new ProfileMyRoadsContract.Model.OnFinishedListener() {
           @Override
           public void onSuccess(ArrayList<Route> user_routes, ArrayList<Route> fav_routes) {
                view.loadRoutes(user_routes,fav_routes);
           }

           @Override
           public void onError(String message) {
               view.displayError(message);
           }

           @Override
           public void onUserUnauthorized(String message) {
               view.logOutUnauthorizedUser();
           }

           @Override
           public void onNetworkError(String message) {
               view.displayError(message);
           }
       });
    }
}
