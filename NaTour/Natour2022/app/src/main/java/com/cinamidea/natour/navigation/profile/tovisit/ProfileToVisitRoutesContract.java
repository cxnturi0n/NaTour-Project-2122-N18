package com.cinamidea.natour.navigation.profile.tovisit;

import com.cinamidea.natour.entities.Route;

import java.util.ArrayList;

public interface ProfileToVisitRoutesContract {
    interface View{
        void loadRoutes(ArrayList<Route> to_visit_routes,ArrayList<Route> fav_routes);
        void displayError(String message);
        void logOutUnauthorizedUser();
    }


    interface Model {
        interface OnFinishedListener{
            void onSuccess(ArrayList<Route> routes);
            void onError(String message);
            void onUserUnauthorized();
        }

        interface OnFavouriteRoutesFetchedListener {
            void onSuccess(ArrayList<Route> favourite_routes);
        }

        void getToVisitRoutes(String username, String id_token, OnFinishedListener listener);
        void getFavouriteRoutes(String username, String id_token, OnFavouriteRoutesFetchedListener listener);

    }


    interface Presenter {
        void getToVisitRoutes(String username, String id_token);
    }

}
