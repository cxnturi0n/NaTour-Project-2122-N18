package com.cinamidea.natour_2022.auth_callbacks;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.PinActivity;
import com.cinamidea.natour_2022.auth_callbacks.AuthenticationCallback;

public class SignupCallback implements AuthenticationCallback {


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

        setupErrorDialog(response);

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

    private void setupErrorDialog(String message) {

        activity.runOnUiThread(() -> {
            Dialog dialog = new Dialog(activity);
            dialog.setContentView(R.layout.error_message_layout);
            dialog.getWindow().setBackgroundDrawable(activity.getDrawable(R.drawable.background_alert_dialog));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ((TextView) dialog.findViewById(R.id.messageError_message)).setText(message);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();

            dialog.findViewById(R.id.messageError_button).setOnClickListener(view -> {
                dialog.hide();
            });

            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    dialog.hide();
                }
            });

        });

    }
}
