package com.cinamidea.natour.navigation.compilation.contracts;



public interface CompilationRecyclerContract {

    interface View {

        void addedToCompilation();
        void displayError(String message);
        void logOutUnauthorizedUser();
    }

    interface Presenter {

        void insertIntoCompilationButtonClicked(String username, String route_name, String compilation_id, String id_token);
    }

    interface Model {

        interface OnFinishedListener {

            void onSuccess();
            void onError(String message);
            void onUserUnauthorized();
        }

        void insertIntoCompilationButtonClicked(String username, String route_name, String compilation_id, String id_token, OnFinishedListener listener);

    }

}
