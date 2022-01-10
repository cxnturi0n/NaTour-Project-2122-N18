package com.cinamidea.natour_2022.auth_util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cinamidea.natour_2022.HomeActivity;
import com.cinamidea.natour_2022.MainActivity;
import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.AuthActivity;
import com.cinamidea.natour_2022.auth.SigninFragment;

public class GoogleSignUpCallback implements AuthenticationCallback{

    private AppCompatActivity activity;
    private String username;
    private String id_token;
    private GoogleAuthentication google_auth;

    public GoogleSignUpCallback(AppCompatActivity activity, String username, String id_token, GoogleAuthentication google_auth){
        this.activity = activity;
        this.id_token = id_token;
        this.username = username;
        this.google_auth = google_auth;
    }

    @Override
    public void handleStatus200(String response) {
        //Salviamo il token di google nelle shared preferences
        SharedPreferences sharedPreferences = activity.getSharedPreferences("google_token", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("id_token",id_token).commit();

        //Teniamo traccia dell'username per la chat
        SigninFragment.chat_username=username;

        activity.startActivity(new Intent(activity, HomeActivity.class));
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
        google_auth.signOut();
        activity.startActivity(new Intent(activity, AuthActivity.class));
    }

    @Override
    public void handleStatus401(String response) {

        activity.runOnUiThread(() -> {
            Dialog dialog = new Dialog(activity);
            dialog.setContentView(R.layout.error_message_layout);
            dialog.getWindow().setBackgroundDrawable(activity.getDrawable(R.drawable.background_alert_dialog));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ((TextView) dialog.findViewById(R.id.messageError_message)).setText(response);
            dialog.show();
        });
        google_auth.signOut();
        activity.startActivity(new Intent(activity, AuthActivity.class));

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
