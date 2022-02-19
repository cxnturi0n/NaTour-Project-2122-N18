package com.cinamidea.natour_2022.utilities.http.callbacks.routes;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

import com.cinamidea.natour_2022.utilities.http.callbacks.HTTPCallback;

public class CreateCompilationCallback implements HTTPCallback {

    private Activity activity;
    private ProgressBar progressBar;

    public CreateCompilationCallback(Activity activity, ProgressBar progressBar) {
        this.activity = activity;
        this.progressBar = progressBar;
    }

    @Override
    public void handleStatus200(String response) {

        activity.runOnUiThread(() -> {

            progressBar.setVisibility(View.GONE);
            activity.finish();

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
