package com.cinamidea.natour_2022.map.contracts;

import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.utilities.UserType;


public interface CreatePathActivityContract {

    interface View{
        void showLoadingDialog();
        void dismissLoadingDialog();
        void showToastAddedRoute();
        void backToHomeAfterInsertedRoute();

    }


    interface Model {
        interface OnFinishedListener{
            void onStatus200(String response);
            void onStatus400(String response);
            void onStatus401(String response);
            void onStatus500(String response);
        }

        void insertRoute(OnFinishedListener listener, String id_token, Route route);
    }


    interface Presenter{
        void continueButtonClick(String id_token,Route route);
    }

}
