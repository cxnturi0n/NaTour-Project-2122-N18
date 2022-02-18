package com.cinamidea.natour_2022.navigation.main.recyclerview;

public interface HomeRecyclerContract {

    interface View {

        void addedToFavourites(int position);
        void addFavouriteError();

        void deletedFromFavourites(int position);
        void deleteFavouriteError(String message);

        void addedToVisit();
        void addToVisitError(String message);

        void deletedFromToVisit(int position);
        void deleteToVisitError(String message);

    }

    interface Presenter {

        void insertFavouriteButtonClicked(String username, String route_name, String id_token);
        void insertToVisitButtonClicked(String username,String route_name, String id_token);
        void deleteFavouriteButtonClicked(String username,String route_name, String id_token);
        void deleteToVisitButtonClicked(String username,String route_name, String id_token);
    }

    interface Model {

        interface OnFinishedListener {

            void onSuccess(String message);
            void onError(String message);
            void onUserUnauthorized(String message);
            void onNetworkError(String message);
        }

        interface OnToVisitUpdated {

            void onSuccess(String message);
            void onError(String message);

        }

        void insertFavourite(String username, String route_name, String id_token, OnFinishedListener onFinishedListener);
        void deleteFavourite(String username, String route_name, String id_token, OnFinishedListener onFinishedListener);

        void insertToVisit(String username, String route_name, String id_token, OnToVisitUpdated onFinishedListener);
        void deleteToVisit(String username, String route_name, String id_token, OnToVisitUpdated onFinishedListener);

    }

}
