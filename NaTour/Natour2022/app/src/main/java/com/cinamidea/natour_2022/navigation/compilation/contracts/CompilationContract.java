package com.cinamidea.natour_2022.navigation.compilation.contracts;

import com.cinamidea.natour_2022.entities.RoutesCompilation;
import com.cinamidea.natour_2022.navigation.main.recyclerview.HomeRecyclerContract;

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
