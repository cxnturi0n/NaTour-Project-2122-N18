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
    public void insertFavouriteButtonClicked(String username, String route_name, String id_token) {

        model.insertFavourite(username, route_name, id_token, new HomeRecyclerContract.Model.OnFinishedListener() {
            @Override
            public void onSuccess(String response) {
                view.addedToFavourites(position);
            }

            @Override
            public void onError(String response) {
                view.addFavouriteError();
            }

            @Override
            public void onUserUnauthorized(String response) {
                view.addFavouriteError();
            }

            @Override
            public void onNetworkError(String response) {
                view.addFavouriteError();
            }
        });

    }

    @Override
    public void deleteFavouriteButtonClicked(String username, String route_name, String id_token) {

        model.deleteFavourite(username, route_name, id_token, new HomeRecyclerContract.Model.OnFinishedListener() {
            @Override
            public void onSuccess(String response) {
                view.deletedFromFavourites(position);
            }

            @Override
            public void onError(String response) {
                view.deleteFavouriteError(response);
            }

            @Override
            public void onUserUnauthorized(String response) {
                view.deleteFavouriteError(response);
            }

            @Override
            public void onNetworkError(String response) {
                view.deleteFavouriteError(response);
            }
        });

    }

    @Override
    public void insertToVisitButtonClicked(String username, String route_name, String id_token) {

        model.insertToVisit(username, route_name, id_token, new HomeRecyclerContract.Model.OnToVisitUpdated() {
            @Override
            public void onSuccess(String response) {
                view.addedToVisit();
            }

            @Override
            public void onError(String response) {
                view.addToVisitError(response);
            }
        });

    }

    @Override
    public void deleteToVisitButtonClicked(String username, String route_name, String id_token) {

        model.deleteToVisit(username, route_name, id_token, new HomeRecyclerContract.Model.OnToVisitUpdated() {
            @Override
            public void onSuccess(String response) {
                view.deletedFromToVisit(position);
            }

            @Override
            public void onError(String response) {
                view.deleteToVisitError(response);
            }

        });

    }
}
