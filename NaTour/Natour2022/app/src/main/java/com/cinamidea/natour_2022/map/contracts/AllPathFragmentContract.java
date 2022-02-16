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
            void onStatus200(Route[] routes);
            void onStatus400(String response);
            void onStatus401(String response);
            void onStatus500(String response);
        }

        void getAllRoutes(OnFinishedListener listener, String id_token);

    }

    interface Presenter{
        void getAllRoutesOnCreate(String id_token);
    }



}
