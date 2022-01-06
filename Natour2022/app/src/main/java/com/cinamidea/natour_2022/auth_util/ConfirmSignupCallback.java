package com.cinamidea.natour_2022.auth_util;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.cinamidea.natour_2022.MainActivity;
import com.cinamidea.natour_2022.auth.SigninFragment;

import io.getstream.chat.android.client.models.User;

public class ConfirmSignupCallback implements AuthenticationCallback {

    private Activity activity;

    public ConfirmSignupCallback(Activity activity) {

        this.activity = activity;
    }

    @Override
    public void handleStatus200(String response) {

        User chat_user = new User();
        chat_user.setId(SigninFragment.chat_username);
        chat_user.setName(SigninFragment.chat_username);
        activity.getSharedPreferences("natour_tokens",MODE_PRIVATE).edit().clear().commit();
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
