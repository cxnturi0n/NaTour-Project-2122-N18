package com.cinamidea.natour_2022.auth_callbacks;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.cinamidea.natour_2022.MainActivity;
import com.cinamidea.natour_2022.auth_util.GoogleAuthentication;

public class ChangePasswordCallback implements AuthenticationCallback{

    Activity activity;

    public ChangePasswordCallback(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void handleStatus200(String response) {

        activity.runOnUiThread(() -> {

            SharedPreferences natour_shared_pref;

            natour_shared_pref = activity.getSharedPreferences("natour_tokens", MODE_PRIVATE);
            String id_token = natour_shared_pref.getString("id_token", null);

            natour_shared_pref.edit().clear().commit();

            Intent intent = new Intent(activity, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);

        });

    }

    @Override
    public void handleStatus400(String response) {

        //Errore nella vecchia password o nella nuova
        Log.e("400",response);
    }

    @Override
    public void handleStatus401(String response) {

        //Errore validita access token
        Log.e("401",response);
    }

    @Override
    public void handleStatus500(String response) {

    }

    @Override
    public void handleRequestException(String message) {

    }


}