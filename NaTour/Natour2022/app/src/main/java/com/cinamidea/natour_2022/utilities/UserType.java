package com.cinamidea.natour_2022.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class UserType {

    private SharedPreferences shared_preferences;
    private String user_type;

    public UserType(Context context) {
        if (context.getSharedPreferences("Cognito", Context.MODE_PRIVATE).getString("id_token", null) != null) {
            this.shared_preferences = context.getSharedPreferences("Cognito", Context.MODE_PRIVATE);
            this.user_type = "Cognito";
        }
        else {
            this.shared_preferences = context.getSharedPreferences("Google", Context.MODE_PRIVATE);
            this.user_type = "Google";
        }
    }

    public String getUserType(){
        return user_type;
    }

    public String getUsername() {
        return shared_preferences.getString("username", null);
    }

    public void setUsername(String username) {
        shared_preferences.edit().putString("username", username).commit();
    }

    public String getIdToken() {
        return shared_preferences.getString("id_token", null);
    }

    public void setIdToken(String id_token) {
        shared_preferences.edit().putString("id_token", id_token).commit();
    }

    public String getAccessToken() {
        return shared_preferences.getString("access_token", null);
    }

    public void setAccessToken(String access_token) {
        shared_preferences.edit().putString("access_Token", access_token).commit();
    }

    public String getRefreshToken() {
        return shared_preferences.getString("refresh_token", null);
    }

    public void setRefreshToken(String refresh_token) {
        shared_preferences.edit().putString("refresh_token", refresh_token).commit();
    }

    public void clear() {
        shared_preferences.edit().clear().commit();
    }

}
