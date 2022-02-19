package com.cinamidea.natour_2022.navigation.compilation.presenters;

import com.cinamidea.natour_2022.entities.RoutesCompilation;
import com.cinamidea.natour_2022.navigation.compilation.contracts.CompilationContract;
import com.cinamidea.natour_2022.navigation.compilation.models.CompilationModel;

import java.util.ArrayList;

public class CompilationPresenter implements CompilationContract.Presenter, CompilationContract.Model.OnFinishedListener {

    private final CompilationContract.View view;
    private final CompilationContract.Model model;

    public CompilationPresenter(CompilationContract.View view) {
        this.view = view;
        this.model = new CompilationModel();
    }

    @Override
    public void getUserCompilationsButtonClicked(String username, String id_token) {
        model.getUserCompilations(username, id_token, this);
    }


    @Override
    public void onSuccess(ArrayList<RoutesCompilation> compilations) {
        view.loadCompilations(compilations);
    }

    @Override
    public void onError(String response) {
        view.displayError(response);
    }

    @Override
    public void onUserUnauthorized() {
        view.logOutUnauthorizedUser();
    }

}
