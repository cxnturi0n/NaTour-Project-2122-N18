package com.cinamidea.natour_2022.prova;

import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.navigation.profile.created.ProfileMyRoadsContract;

import java.util.ArrayList;

public interface HomeContract {

    interface View {

        void loadRoutes(ArrayList<Route> routes, ArrayList<Route> fav_routes);
        void displayError(String message);
        void logOutUnauthorizedUser();
    }

    interface Presenter {

        void getAllRoutesButtonClicked(String id_token);
        void getRoutesByDifficultyButtonClicked(String id_token, String difficulty);

    }

    interface Model {

        interface OnFinishedListener {
            void onSuccess(ArrayList<Route> routes);
            void onError(String response);
            void onUserUnauthorized(String response);
            void onNetworkError(String response);
        }

        void getAllRoutes(String id_token, OnFinishedListener listener);
        void getFavouriteRoutes(String id_token, OnFinishedListener listener);
        void getRoutesByDifficulty(String id_token, String difficulty, OnFinishedListener listener);
    }

}
