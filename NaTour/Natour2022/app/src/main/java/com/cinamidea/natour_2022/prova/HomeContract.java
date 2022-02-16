package com.cinamidea.natour_2022.prova;

import com.cinamidea.natour_2022.entities.Route;

import java.util.ArrayList;

public interface HomeContract {

    interface View {

        void loadRoutes(ArrayList<Route> routes, ArrayList<Route> fav_routes);

    }

    interface Presenter {

        void getAllRoutesButtonClicked(String id_token);
        void getRoutesByDifficultyButtonClicked(String id_token, String difficulty);

    }

    interface Model {

        interface OnFinishedListener {
            void onSuccess(ArrayList<Route> routes, ArrayList<Route> fav_routes);
            void onError(String response);
            void onUserUnauthorized(String response);
            void onNetworkError(String response);
        }

        void getAllRoutes(String id_token, OnFinishedListener listener);
        void getRoutesByDifficulty(String id_token, String difficulty, OnFinishedListener listener);

    }

}
