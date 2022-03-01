package com.cinamidea.natour_2022.navigation.compilation.presenters;

import com.cinamidea.natour_2022.navigation.compilation.contracts.CompilationRecyclerContract;
import com.cinamidea.natour_2022.navigation.compilation.models.CompilationRecyclerModel;

public class CompilationRecyclerPresenter implements CompilationRecyclerContract.Presenter, CompilationRecyclerContract.Model.OnFinishedListener {

    private final CompilationRecyclerContract.View view;
    private final CompilationRecyclerContract.Model model;

    public CompilationRecyclerPresenter(CompilationRecyclerContract.View view, CompilationRecyclerContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void insertIntoCompilationButtonClicked(String username, String route_name, String compilation_id, String id_token) {

        model.insertIntoCompilationButtonClicked(username, route_name, compilation_id, id_token, this);

    }


    @Override
    public void onSuccess() {

        view.addedToCompilation();

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
