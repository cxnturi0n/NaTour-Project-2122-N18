package com.cinamidea.natour.navigation.compilation.contracts;

import com.cinamidea.natour.entities.RoutesCompilation;

import java.util.ArrayList;

public interface CompilationContract {

    interface View {

        void loadCompilations(ArrayList<RoutesCompilation> compilations);
        void displayError(String message);
        void logOutUnauthorizedUser();

    }

    interface Presenter {

        void getUserCompilationsButtonClicked(String username, String id_token);

    }

    interface Model {

        interface OnFinishedListener {

            void onSuccess(ArrayList<RoutesCompilation> compilations);
            void onError(String response);
            void onUserUnauthorized();

        }

        void getUserCompilations(String username, String id_token, OnFinishedListener listener);

    }

}
