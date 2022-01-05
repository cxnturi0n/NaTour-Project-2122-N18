package com.cinamidea.natour_2022.auth_util;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.cinamidea.natour_2022.HomeActivity;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;


public class GetTokensCallback implements AuthenticationCallback{

    private Activity activity;

    private String username;

    public GetTokensCallback(Activity activity, String username){
        this.activity = activity;
        this.username = username;
    }

    @Override
    public void handleStatus200(String response) {

        SharedPreferences natour_tokens = activity.getSharedPreferences("natour_tokens", MODE_PRIVATE);

        setTokensInSharedPreferences(natour_tokens, response);

        activity.startActivity(new Intent(activity, HomeActivity.class));

    }

    @Override
    public void handleStatus400(String response) {
        activity.runOnUiThread(() -> Toast.makeText(activity,
                response,
                Toast.LENGTH_SHORT).show());
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

    private void setTokensInSharedPreferences(SharedPreferences user_details, String response){


        Gson gson = new Gson();
        Tokens tokens = gson.fromJson(removeQuotesAndUnescape(response), Tokens.class);

        SharedPreferences.Editor editor = user_details.edit();

        editor.putString("id_token", tokens.getId_token());
        editor.putString("refresh_token", tokens.getRefresh_token());
        editor.putString("username", username);

        editor.commit();
    }

    private String removeQuotesAndUnescape(String uncleanJson) {
        String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");
        return StringEscapeUtils.unescapeJava(noQuotes);
    }


}

