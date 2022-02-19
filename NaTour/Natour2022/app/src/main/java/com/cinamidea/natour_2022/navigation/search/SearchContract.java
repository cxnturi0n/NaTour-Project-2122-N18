package com.cinamidea.natour_2022.navigation.search;

import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.entities.RouteFilters;
import com.cinamidea.natour_2022.navigation.compilation.contracts.CompilationRoutesContract;

import java.util.ArrayList;

public interface SearchContract {

    interface View {

        void loadResults(ArrayList<Route> filtered_routes, ArrayList<Route> favourite_routes);
        void displayError(String message);
        void logOutUnauthorizedUser();
    }

    interface Presenter {

        void searchButtonClicked(String username, RouteFilters route_filters, String id_token);

    }

    interface Model {

        interface OnFinishedListener {

            void onSuccess(ArrayList<Route>filtered_routes);
            void onError(String message);
            void onUserUnauthorized();

        }

        interface OnFavouriteRoutesFetchedListener {
            void onSuccess(ArrayList<Route> favourite_routes);
        }

        void getFilteredRoutes(String username, RouteFilters route_filters, String id_token, OnFinishedListener listener);

        void getFavourites(String username, String id_token, OnFavouriteRoutesFetchedListener listener);


    }

}
