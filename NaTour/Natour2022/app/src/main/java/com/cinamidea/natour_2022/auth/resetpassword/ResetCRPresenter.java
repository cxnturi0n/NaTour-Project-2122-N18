package com.cinamidea.natour_2022.auth.resetpassword;

public class ResetCRPresenter implements ResetCRContract.Presenter{

    private final ResetCRContract.View view;
    private final ResetCRContract.Model model;

    public ResetCRPresenter(ResetCRContract.View view, ResetCRContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void requestCodeButtonClicked(String username) {

        model.requestCode(username, new ResetCRContract.Model.OnFinishListener() {
            @Override
            public void onSuccess(String message) {
                view.changeFragmentToResetCRPassword();
            }

            @Override
            public void onFailure(String message) {
                view.displayUsernameNotFoundError(message);
            }
        });

    }

    @Override
    public void resetPasswordButtonClicked(String username, String password, String code) {

        model.resetPassword(username, password, code, new ResetCRContract.Model.OnFinishListener() {
            @Override
            public void onSuccess(String message) {
                view.resetDone(message);
            }

            @Override
            public void onFailure(String message) {
                view.displayResetError(message);
            }
        });

    }

}
