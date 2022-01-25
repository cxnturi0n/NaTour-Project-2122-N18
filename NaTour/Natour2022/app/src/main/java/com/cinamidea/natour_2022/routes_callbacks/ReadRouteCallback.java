package com.cinamidea.natour_2022.routes_callbacks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ReadRouteCallback implements RoutesCallback {

    private Activity activity;
    private ProgressDialog progress_dialog;

    public ReadRouteCallback(Activity activity, ProgressDialog progress_dialog) {
        this.activity = activity;
        this.progress_dialog = progress_dialog;
    }

    @Override
    public void handleStatus200(String response) {
        activity.runOnUiThread(() -> {
            progress_dialog.dismiss();
            System.out.println(response);
            Toast.makeText(activity, "Route read successfully", Toast.LENGTH_SHORT).show();


        });

    }

    @Override
    public void handleStatus400(String response) {

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
}
