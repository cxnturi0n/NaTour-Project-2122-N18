package com.cinamidea.natour.navigation.compilation.contracts;

import com.cinamidea.natour.entities.Route;

import java.util.ArrayList;

public interface CompilationRoutesContract {

    interface View {

        void loadRoutes(ArrayList<Route> routes, ArrayList<Route> fav_routes);
        void displayError(String message);
        void logOutUnauthorizedUser();

    }

    interface Presenter {

        void getRoutesButtonClicked(String username, String compilation_id, String id_token);

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

        void getRoutes(String username, String compilation_id, String id_token, OnFinishedListener listener);
        void getFavourites(String username, String id_token, OnFavouriteRoutesFetchedListener listener);

    }

}
