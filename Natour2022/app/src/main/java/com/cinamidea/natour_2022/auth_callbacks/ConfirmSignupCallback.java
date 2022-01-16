package com.cinamidea.natour_2022.auth_callbacks;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cinamidea.natour_2022.MainActivity;
import com.cinamidea.natour_2022.R;

public class ConfirmSignupCallback implements AuthenticationCallback {

    private Activity activity;

    public ConfirmSignupCallback(Activity activity) {

        this.activity = activity;
    }

    @Override
    public void handleStatus200(String response) {

        //Emptying tokens
        activity.getSharedPreferences("natour_tokens",MODE_PRIVATE).edit().clear().commit();

        setupSuccessDialog("Registrazione avvenuta con successo");

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

        setupErrorDialog(message);

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
                Intent intent = new Intent(activity, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            });

            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    dialog.hide();
                    Intent intent = new Intent(activity, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                }
            });

        });

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
