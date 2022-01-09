package com.cinamidea.natour_2022.auth_util;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.AuthActivity;
import com.cinamidea.natour_2022.auth.SigninFragment;

import io.getstream.chat.android.client.models.User;

public class ConfirmSignupCallback implements AuthenticationCallback {

    private Activity activity;

    public ConfirmSignupCallback(Activity activity) {

        this.activity = activity;
    }

    @Override
    public void handleStatus200(String response) {

        //Successful registration
        //Creating channel in chat
        User chat_user = new User();
        chat_user.setId(SigninFragment.chat_username);
        chat_user.setName(SigninFragment.chat_username);

        //Emptying tokens
        activity.getSharedPreferences("natour_tokens",MODE_PRIVATE).edit().clear().commit();

        //Switching to Signin Activity
        Intent intent = new Intent(activity, AuthActivity.class);
        intent.putExtra("key","signin");
        activity.startActivity(intent);
    }

    @Override
    public void handleStatus400(String response) {
        activity.runOnUiThread(() -> {
            Dialog dialog = new Dialog(activity);
            dialog.setContentView(R.layout.error_message_layout);
            dialog.getWindow().setBackgroundDrawable(activity.getDrawable(R.drawable.background_alert_dialog));
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
        activity.runOnUiThread(() -> {
            Dialog dialog = new Dialog(activity);
            dialog.setContentView(R.layout.error_message_layout);
            dialog.getWindow().setBackgroundDrawable(activity.getDrawable(R.drawable.background_alert_dialog));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ((TextView) dialog.findViewById(R.id.messageError_message)).setText(message);
            dialog.show();
        });
    }
}
