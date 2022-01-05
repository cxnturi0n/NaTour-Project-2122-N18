package com.cinamidea.natour_2022.auth_util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.cinamidea.natour_2022.HomeActivity;
import com.cinamidea.natour_2022.MainActivity;
import com.cinamidea.natour_2022.auth.PinActivity;

public class ConfirmSignupCallback implements AuthenticationCallback {

    private Activity activity;

    public ConfirmSignupCallback(Activity activity) {

        this.activity = activity;
    }

    @Override
    public void handleStatus200(String response) {

        activity.startActivity(new Intent(activity, MainActivity.class));

    }

    @Override
    public void handleStatus400(String response) {
        activity.runOnUiThread(() -> {
            Toast toast = Toast.makeText(activity,
                    response,
                    Toast.LENGTH_SHORT);
            toast.show();
        });
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
