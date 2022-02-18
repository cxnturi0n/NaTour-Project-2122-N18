package com.cinamidea.natour_2022.navigation.profile.favourites;
import com.cinamidea.natour_2022.entities.Route;

import java.util.ArrayList;

public interface ProfileFavouriteRoutesContract {

    interface View{
        void loadRoutes(ArrayList<Route> fav_routes);
        void displayError(String message);
        void logOutUnauthorizedUser();
    }

    interface Model {
        interface OnFinishedListener{

            void onSuccess(ArrayList<Route> fav_routes);
            void onError(String response);
            void onUserUnauthorized();
        }
        void getFavRoutes(String id_token, ProfileFavouriteRoutesContract.Model.OnFinishedListener listener);

    }

    interface Presenter {
        void getFavourite(String id_token);
    }

}
