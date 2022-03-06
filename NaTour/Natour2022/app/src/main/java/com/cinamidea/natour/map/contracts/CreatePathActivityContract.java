package com.cinamidea.natour.map.contracts;

import com.cinamidea.natour.entities.Route;


public interface CreatePathActivityContract {

    interface View{
        void showLoadingDialog();
        void dismissLoadingDialog();
        void showToastAddedRoute();
        void backToHomeAfterInsertedRoute();
        void displayError(String message);
        void logOutUnauthorizedUser();
    }


    interface Model {
        interface OnFinishedListener{
            void onSuccess(String response);
            void onError(String response);
            void onUserUnauthorized();
        }

        void insertRoute(OnFinishedListener listener, String id_token, Route route);
    }


    interface Presenter{
        void continueButtonClick(String id_token,Route route);
    }

}
