package com.cinamidea.natour_2022.auth_util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.cinamidea.natour_2022.auth.PinActivity;

public class SignupCallback implements AuthenticationCallback{


    private String username;
    private Activity activity;

    public SignupCallback(String username, Activity activity){
        this.username = username;
        this.activity = activity;
    }

    @Override
    public void handleStatus200(String response) {

        Intent intent = new Intent(activity, PinActivity.class);
        intent.putExtra("username", username);

         activity.startActivity(intent);

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
