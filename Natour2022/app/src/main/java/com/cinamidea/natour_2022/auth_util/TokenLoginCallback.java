package com.cinamidea.natour_2022.auth_util;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cinamidea.natour_2022.HomeActivity;
import com.cinamidea.natour_2022.MainActivity;
import com.cinamidea.natour_2022.R;

public class TokenLoginCallback implements AuthenticationCallback{

    private Activity activity;

    public TokenLoginCallback(Activity activity){
        this.activity = activity;
    }
    @Override
    public void handleStatus200(String response) {

        Intent intent = new Intent(activity, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }

    @Override
    public void handleStatus400(String response) {

    }

    @Override
    public void handleStatus401(String response) {

        //Se il token è scaduto allora uso il refresh token per ottenere un nuovo id token
        if(response.contains("expired")) {
            SharedPreferences natour_tokens = activity.getSharedPreferences("natour_tokens", MODE_PRIVATE);

            String username = natour_tokens.getString("username", null);
            String refresh_token = natour_tokens.getString("refresh_token",null);

            Authentication.refreshToken(username, refresh_token, new RefreshTokenCallback(activity));
            return;
        }

        //Se la sessione non è valida..
        activity.runOnUiThread(() -> {
            Dialog dialog = new Dialog(activity);
            dialog.setContentView(R.layout.error_message_layout);
            dialog.getWindow().setBackgroundDrawable(activity.getDrawable(R.drawable.background_alert_dialog));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ((TextView) dialog.findViewById(R.id.messageError_message)).setText("Session timed out, please sign in again");
            dialog.show();
        });

        //cancello i token..
        activity.getSharedPreferences("natour_tokens",MODE_PRIVATE).edit().clear().commit();

        //e torno alla main activity
        activity.startActivity(new Intent(activity, MainActivity.class));

    }

    @Override
    public void handleStatus500(String response) {



    }

    @Override
    public void handleRequestException(String message) {

    }

}
