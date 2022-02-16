package com.cinamidea.natour_2022.map.contracts;

import android.location.Location;

import com.cinamidea.natour_2022.entities.Route;

public interface AllPathFragmentContract {

    interface View{
        void drawRoutes(Route[] routes);
        Location getCurrentLocation();
        void updateLocationUI();
        void showGPSDisabledDialog();
        void getLocationPermission();
        void showLoadingDialog();
        void dismissLoadingDialog();

    }


    interface Model{
        Route[] jsonToRoutesParsing(String response);
        interface OnFinishedListener{
            void onSuccess(Route[] routes);
            void onError(String response);
            void onUserUnauthorized(String response);
            void onNetworkError(String response);
        }

        void getAllRoutes(OnFinishedListener listener, String id_token);

    }

    interface Presenter{
        void getAllRoutesOnCreate(String id_token);
    }



}
