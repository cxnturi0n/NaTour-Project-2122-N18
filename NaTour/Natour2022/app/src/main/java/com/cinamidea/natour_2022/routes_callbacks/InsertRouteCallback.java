package com.cinamidea.natour_2022.routes_callbacks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

import com.cinamidea.natour_2022.map.CreatePathActivity;

public class InsertRouteCallback implements RoutesCallback{

    private Activity activity;
    private ProgressDialog progress_dialog;

    public InsertRouteCallback(Activity activity, ProgressDialog progress_dialog){
        this.activity = activity;
        this.progress_dialog = progress_dialog;

    }

    @Override
    public void handleStatus200(String response) {
        activity.runOnUiThread(() -> {
            progress_dialog.dismiss();
            activity.startActivity(new Intent(activity, CreatePathActivity.class));

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