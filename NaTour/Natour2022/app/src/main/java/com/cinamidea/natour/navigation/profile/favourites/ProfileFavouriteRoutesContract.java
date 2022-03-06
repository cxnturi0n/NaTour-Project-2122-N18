package com.cinamidea.natour.navigation.profile.favourites;
import com.cinamidea.natour.entities.Route;

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
        void getFavRoutes(String username, String id_token, ProfileFavouriteRoutesContract.Model.OnFinishedListener listener);

    }

    interface Presenter {
        void getFavouriteRoutes(String username, String id_token);
    }

}
