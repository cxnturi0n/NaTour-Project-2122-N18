package com.cinamidea.natour_2022.map.contracts;

import com.cinamidea.natour_2022.entities.Route;


public interface CreatePathActivityContract {

    interface View{
        void showLoadingDialog();
        void dismissLoadingDialog();
        void showToastAddedRoute();
        void backToHomeAfterInsertedRoute();

    }


    interface Model {
        interface OnFinishedListener{
            void onSuccess(String response);
            void onError(String response);
            void onUserUnauthorized(String response);
            void onNetworkError(String response);
        }

        void insertRoute(OnFinishedListener listener, String id_token, Route route);
    }


    interface Presenter{
        void continueButtonClick(String id_token,Route route);
    }

}
