package com.cinamidea.natour.home;

import com.cinamidea.natour.entities.Route;

import java.util.ArrayList;

public interface HomeContract {

    interface View {

        void loadRoutes(ArrayList<Route> routes, ArrayList<Route> fav_routes);
        void displayError(String message);
        void logOutUnauthorizedUser();
    }

    interface Presenter {

        void getAllRoutesButtonClicked(String username, String id_token);
        void getRoutesByDifficultyButtonClicked(String username, String id_token, String difficulty);

    }

    interface Model {

        interface OnFinishedListener {
            void onSuccess(ArrayList<Route> routes);
            void onError(String response);
            void onUserUnauthorized();
        }

        interface OnFavouriteRoutesFetchedListener {
            void onSuccess(ArrayList<Route> favourite_routes);
        }

        void getAllRoutes(String username, String id_token, OnFinishedListener listener);
        void getFavouriteRoutes(String username, String id_token, OnFavouriteRoutesFetchedListener listener);
        void getRoutesByDifficulty(String username, String id_token, String difficulty, OnFinishedListener listener);
    }

}
