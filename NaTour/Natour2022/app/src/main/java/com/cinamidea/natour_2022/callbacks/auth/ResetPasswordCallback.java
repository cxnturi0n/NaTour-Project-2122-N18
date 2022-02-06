package com.cinamidea.natour_2022.callbacks.auth;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.callbacks.HTTPCallback;

public class ResetPasswordCallback implements HTTPCallback {


    private Activity activity;

    public ResetPasswordCallback(Activity activity){
        this.activity = activity;
    }
    @Override
    public void handleStatus200(String response) {
        setupSuccessDialog(response);
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

    private void setupSuccessDialog(String message) {

        activity.runOnUiThread(() -> {
            Dialog dialog = new Dialog(activity);
            dialog.setContentView(R.layout.success_message_layout);
            dialog.getWindow().setBackgroundDrawable(activity.getDrawable(R.drawable.background_alert_dialog));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ((TextView) dialog.findViewById(R.id.messageSuccess_message)).setText(message);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();

            dialog.findViewById(R.id.messageSuccess_button).setOnClickListener(view -> {
                dialog.hide();
                activity.finish();
            });

            dialog.setOnCancelListener(dialogInterface -> {
                dialog.hide();
                activity.finish();
            });

        });

    }

}
