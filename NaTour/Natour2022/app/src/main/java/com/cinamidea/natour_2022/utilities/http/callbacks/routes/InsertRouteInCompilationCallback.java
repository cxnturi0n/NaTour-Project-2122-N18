package com.cinamidea.natour_2022.utilities.http.callbacks.routes;

import android.app.Activity;
import android.util.Log;

import com.cinamidea.natour_2022.utilities.http.callbacks.HTTPCallback;

public class InsertRouteInCompilationCallback implements HTTPCallback {

    private Activity activity;

    public InsertRouteInCompilationCallback(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void handleStatus200(String response) {

        Log.e("Response", response);
        activity.runOnUiThread(() -> activity.finish());

    }

    @Override
    public void handleStatus400(String response) {

        Log.e("Response", response);

    }

    @Override
    public void handleStatus401(String response) {

        Log.e("Response", response);

    }

    @Override
    public void handleStatus500(String response) {

        Log.e("Response", response);

    }

    @Override
    public void handleRequestException(String message) {

    }
}
