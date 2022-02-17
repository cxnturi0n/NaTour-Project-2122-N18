package com.cinamidea.natour_2022.navigation.profile;

import android.app.Activity;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.navigation.main.RecyclerViewAdapter;

import java.io.InputStream;
import java.util.ArrayList;

public interface ProfileMyRoadsContract {
    interface View{
        void loadRoutes(ArrayList<Route> routes, ArrayList<Route> fav_routes);
        void displayError(String message);
        void logOutUnauthorizedUser();
    }


    interface Model {
        interface OnFinishedListener{
            void onSuccess(ArrayList<Route> user_routes, ArrayList<Route> fav_routes);
            void onError(String message);
            void onUserUnauthorized(String message);
            void onNetworkError(String message);

        }

        void getUserRoutes(String id_token,OnFinishedListener listener);

    }


    interface Presenter {
        void getRoutes(String id_token );
    }




}
