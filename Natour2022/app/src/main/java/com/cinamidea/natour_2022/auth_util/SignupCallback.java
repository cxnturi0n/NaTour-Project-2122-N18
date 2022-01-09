package com.cinamidea.natour_2022.auth_util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cinamidea.natour_2022.R;
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
            Dialog dialog = new Dialog(activity);
            dialog.setContentView(R.layout.error_message_layout);
            dialog.getWindow().setBackgroundDrawable(activity.getDrawable(R.drawable.message_notification_background));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ((TextView) dialog.findViewById(R.id.messageError_message)).setText(response);
            dialog.show();
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
