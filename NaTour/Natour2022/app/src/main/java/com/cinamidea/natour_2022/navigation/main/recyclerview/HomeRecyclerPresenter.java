package com.cinamidea.natour_2022.navigation.main.recyclerview;

public class HomeRecyclerPresenter implements HomeRecyclerContract.Presenter {

    private final HomeRecyclerContract.Model model;
    private final HomeRecyclerContract.View view;
    private final int position;

    public HomeRecyclerPresenter(HomeRecyclerContract.View view, int position) {
        this.model = new HomeRecyclerModel();
        this.view = view;
        this.position = position;
    }

    @Override
    public void insertFavouriteButtonClicked(String route_name, String id_token) {

        model.insertFavourite(route_name, id_token, new HomeRecyclerContract.Model.OnFinishedListener() {
            @Override
            public void onSuccess(String response) {
                view.addedToFavourites(position);
            }

            @Override
            public void onError(String response) {
                view.addedToFavouritesError();
            }

            @Override
            public void onUserUnauthorized(String response) {
                view.addedToFavouritesError();
            }

            @Override
            public void onNetworkError(String response) {
                view.addedToFavouritesError();
            }
        });

    }

    @Override
    public void deleteFavouriteButtonClicked(String route_name, String id_token) {

        model.deleteFavourite(route_name, id_token, new HomeRecyclerContract.Model.OnFinishedListener() {
            @Override
            public void onSuccess(String response) {
                view.deletedFromFavourites(position);
            }

            @Override
            public void onError(String response) {
                view.deletedFromFavouritesError();
            }

            @Override
            public void onUserUnauthorized(String response) {
                view.deletedFromFavouritesError();
            }

            @Override
            public void onNetworkError(String response) {
                view.deletedFromFavouritesError();
            }
        });

    }

    @Override
    public void insertToVisitButtonClicked(String route_name, String id_token) {

        model.insertToVisit(route_name, id_token, new HomeRecyclerContract.Model.OnFinishedListener() {
            @Override
            public void onSuccess(String response) {
                view.addedToVisit();
            }

            @Override
            public void onError(String response) {

            }

            @Override
            public void onUserUnauthorized(String response) {

            }

            @Override
            public void onNetworkError(String response) {

            }
        });

    }

    @Override
    public void deleteToVisitButtonClicked(String route_name, String id_token) {

        model.deleteToVisit(route_name, id_token, new HomeRecyclerContract.Model.OnFinishedListener() {
            @Override
            public void onSuccess(String response) {
                view.deletedFromToVisit(position);
            }

            @Override
            public void onError(String response) {

            }

            @Override
            public void onUserUnauthorized(String response) {

            }

            @Override
            public void onNetworkError(String response) {

            }
        });

    }
}
