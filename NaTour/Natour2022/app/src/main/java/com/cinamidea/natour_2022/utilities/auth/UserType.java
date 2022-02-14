package com.cinamidea.natour_2022.utilities.auth;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


public class UserType {

    private final String user_type;
    private String id_token;

    public UserType(Context context) {

        SharedPreferences cognito_shared_preferences = context.getSharedPreferences("natour_tokens", MODE_PRIVATE);

        id_token = cognito_shared_preferences.getString("id_token", null);
        if (id_token != null)
            user_type = "Cognito";
        else {
            id_token = context.getSharedPreferences("google_token", MODE_PRIVATE).getString("id_token", null);
            user_type = "Google";
        }

    }

    public String getId_token() {
        return id_token;
    }

    public String getUser_type() {
        return user_type;
    }


}