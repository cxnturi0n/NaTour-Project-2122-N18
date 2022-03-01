package com.cinamidea.natour_2022.navigation.compilation.presenters;

import android.util.Log;

import com.cinamidea.natour_2022.entities.RoutesCompilation;
import com.cinamidea.natour_2022.navigation.compilation.contracts.CreateCompilationContract;
import com.cinamidea.natour_2022.navigation.compilation.models.CreateCompilationModel;

public class CreateCompilationPresenter implements CreateCompilationContract.Presenter, CreateCompilationContract.Model.OnFinishedListener {

    private final CreateCompilationContract.View view;
    private final CreateCompilationContract.Model model;

    public CreateCompilationPresenter(CreateCompilationContract.View view, CreateCompilationContract.Model model) {
        this.view = view;
        this.model = model;
    }


    @Override
    public void createCompilationButtonClicked(String username, RoutesCompilation routesCompilation, String id_token) {

        model.createCompilation(username, routesCompilation, id_token, this);

    }

    @Override
    public void onSuccess() {

        view.compilationCreated();

    }

    @Override
    public void onError(String message) {
        view.displayError(message);
    }

    @Override
    public void onUserUnauthorized() {
        view.logOutUnauthorizedUser();
    }
}
