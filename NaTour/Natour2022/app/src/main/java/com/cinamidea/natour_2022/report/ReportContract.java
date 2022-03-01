package com.cinamidea.natour_2022.report;

import com.cinamidea.natour_2022.entities.Report;

public interface ReportContract {

    interface View {

        void reportDone(String message);
        void displayError(String message);
        void logOutUnauthorizedUser();
    }

    interface Presenter {

        void sendReportButtonClicked(String id_token, Report report);

    }

    interface Model {

        interface OnFinishListener {
            void onSuccess(String message);
            void onError(String message);
            void onUserUnauthorized();
        }

        void sendReport(String id_token, Report report, OnFinishListener listener);

    }

}
