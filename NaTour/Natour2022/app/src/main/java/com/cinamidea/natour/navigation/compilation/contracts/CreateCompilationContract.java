package com.cinamidea.natour.navigation.compilation.contracts;

import com.cinamidea.natour.entities.RoutesCompilation;

public interface CreateCompilationContract {

    interface View {

        void compilationCreated();
        void displayError(String message);
        void logOutUnauthorizedUser();
    }

    interface Presenter {

        void createCompilationButtonClicked(String username, RoutesCompilation routesCompilation, String id_token);

    }

    interface Model {

        interface OnFinishedListener {

            void onSuccess();
            void onError(String message);
            void onUserUnauthorized();

        }

        void createCompilation(String username, RoutesCompilation routesCompilation, String id_token, OnFinishedListener listener);

    }


}
