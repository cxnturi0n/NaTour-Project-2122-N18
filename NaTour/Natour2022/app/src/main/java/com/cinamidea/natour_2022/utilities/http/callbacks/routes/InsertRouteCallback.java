package com.cinamidea.natour_2022.utilities.http.callbacks.routes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Toast;

import com.cinamidea.natour_2022.navigation.main.views.HomeActivity;
import com.cinamidea.natour_2022.utilities.http.callbacks.HTTPCallback;

public class InsertRouteCallback implements HTTPCallback {

    private final Activity activity;
    private final ProgressDialog progress_dialog;

    public InsertRouteCallback(Activity activity, ProgressDialog progress_dialog) {
        this.activity = activity;
        this.progress_dialog = progress_dialog;

    }

    @Override
    public void handleStatus200(String response) {
        activity.runOnUiThread(() -> {
            progress_dialog.dismiss();
            Toast.makeText(activity, "Route added", Toast.LENGTH_SHORT).show();
            activity.startActivity(new Intent(activity.getApplicationContext(), HomeActivity.class));
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