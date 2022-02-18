package com.cinamidea.natour_2022.navigation.main.recyclerview;

public interface HomeRecyclerContract {

    interface View {

        void addedToFavourites(int position);
        void addedToFavouritesError();
        void deletedFromFavourites(int position);
        void deletedFromFavouritesError();

        void addedToVisit();
        void deletedFromToVisit(int position);
        void ToVisitError(String message);

    }

    interface Presenter {

        void insertFavouriteButtonClicked(String route_name, String id_token);
        void insertToVisitButtonClicked(String route_name, String id_token);
        void deleteFavouriteButtonClicked(String route_name, String id_token);
        void deleteToVisitButtonClicked(String route_name, String id_token);
    }

    interface Model {

        interface OnFinishedListener {

            void onSuccess(String response);
            void onError(String response);
            void onUserUnauthorized(String response);
            void onNetworkError(String response);

        }

        void insertFavourite(String route_name, String id_token, OnFinishedListener onFinishedListener);
        void deleteFavourite(String route_name, String id_token, OnFinishedListener onFinishedListener);

        void insertToVisit(String route_name, String id_token, OnFinishedListener onFinishedListener);
        void deleteToVisit(String route_name, String id_token, OnFinishedListener onFinishedListener);

    }

}
