package com.cinamidea.natour.navigation.profile.created;

import com.cinamidea.natour.entities.Route;

import java.util.ArrayList;

public interface ProfileMyRoadsContract {
    interface View{
        void loadRoutes(ArrayList<Route> routes, ArrayList<Route> fav_routes);
        void displayError(String message);
        void logOutUnauthorizedUser();
    }


    interface Model {
        interface OnFinishedListener{
            void onSuccess(ArrayList<Route> user_routes);
            void onError(String message);
            void onUserUnauthorized();
        }

        interface OnFavouriteRoutesFetchedListener {
            void onSuccess(ArrayList<Route> favourite_routes);
        }

        void getUserRoutes(String username, String id_token,OnFinishedListener listener);
        void getFavouriteRoutes(String username, String id_token, OnFavouriteRoutesFetchedListener listener);

    }


    interface Presenter {
        void getMyRoutes(String username, String id_token);
    }

}
