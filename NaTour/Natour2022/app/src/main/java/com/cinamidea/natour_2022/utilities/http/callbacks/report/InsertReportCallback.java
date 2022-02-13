package com.cinamidea.natour_2022.utilities.http.callbacks.report;

import android.app.Activity;
import android.app.Dialog;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.utilities.http.callbacks.HTTPCallback;

//TODO: Sistemare le stringhe di errore in XML

public class InsertReportCallback implements HTTPCallback {

    private final Activity activity;

    public InsertReportCallback(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void handleStatus200(String response) {

        activity.runOnUiThread(() -> openSuccessDialog());

    }

    @Override
    public void handleStatus400(String response) {

        //TODO:

    }

    @Override
    public void handleStatus401(String response) {

    }

    @Override
    public void handleStatus500(String response) {

    }

    @Override
    public void handleRequestException(String message) {

    }

    private void openSuccessDialog() {

        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.success_message_layout);
        dialog.getWindow().setBackgroundDrawable(activity.getDrawable(R.drawable.background_alert_dialog));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ((TextView) dialog.findViewById(R.id.messageSuccess_message)).setText(R.string.activityReport_success);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        dialog.findViewById(R.id.messageSuccess_button).setOnClickListener(view -> {
            dialog.dismiss();
            activity.finish();
        });

        dialog.setOnCancelListener(dialogInterface -> {
            dialog.dismiss();
            activity.finish();
        });

    }

    private void openErrorDialog() {

        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.error_message_layout);
        dialog.getWindow().setBackgroundDrawable(activity.getDrawable(R.drawable.background_alert_dialog));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ((TextView) dialog.findViewById(R.id.messageError_message)).setText("Errore da catchare");
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        dialog.findViewById(R.id.messageError_button).setOnClickListener(view -> {
            dialog.dismiss();
            activity.finish();
        });

        dialog.setOnCancelListener(dialogInterface -> {
            dialog.dismiss();
            activity.finish();
        });

    }

}
