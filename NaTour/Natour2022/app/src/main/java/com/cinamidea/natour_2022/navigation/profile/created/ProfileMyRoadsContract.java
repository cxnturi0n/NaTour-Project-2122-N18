package com.cinamidea.natour_2022.navigation.profile.created;

import com.cinamidea.natour_2022.entities.Route;
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

        void getUserRoutes(String id_token,OnFinishedListener listener);
        void getFavouriteRoutes(String id_token, OnFavouriteRoutesFetchedListener listener);

    }


    interface Presenter {
        void getRoutes(String id_token);
    }

}
