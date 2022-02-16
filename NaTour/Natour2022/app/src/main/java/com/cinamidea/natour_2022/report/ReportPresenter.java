package com.cinamidea.natour_2022.report;

import com.cinamidea.natour_2022.entities.Report;

public class ReportPresenter implements ReportContract.Presenter, ReportContract.Model.OnFinishListener {

    private final ReportContract.View view;
    private final ReportContract.Model model;

    public ReportPresenter(ReportContract.View view) {
        this.view = view;
        this.model = new ReportModel();
    }


    @Override
    public void onSuccess(String message) {
        view.reportDone(message);
    }

    @Override
    public void onFailure(String message) {
        view.displayError(message);
    }

    @Override
    public void sendReportButtonClicked(String id_token, Report report) {
        model.sendReport(id_token, report, this);
    }
}
