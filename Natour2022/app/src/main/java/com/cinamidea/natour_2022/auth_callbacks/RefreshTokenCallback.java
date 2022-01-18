package com.cinamidea.natour_2022.auth_callbacks;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import com.cinamidea.natour_2022.auth.AuthActivity;
import com.cinamidea.natour_2022.auth_callbacks.AuthenticationCallback;
import com.cinamidea.natour_2022.auth_callbacks.TokenLoginCallback;
import com.cinamidea.natour_2022.auth_util.AuthenticationHTTP;
import com.cinamidea.natour_2022.auth_util.Tokens;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringEscapeUtils;

public class RefreshTokenCallback implements AuthenticationCallback {

    private Activity activity;

    public RefreshTokenCallback(Activity activity){
        this.activity = activity;
    }
    @Override
    public void handleStatus200(String response) {

        SharedPreferences natour_tokens = activity.getSharedPreferences("natour_tokens", MODE_PRIVATE);

        setIdTokenInSharedPreferences(natour_tokens, response);

        String id_token = natour_tokens.getString("id_token",null);

        AuthenticationHTTP.tokenLogin(id_token, new TokenLoginCallback(activity));
    }

    @Override
    public void handleStatus400(String response) {

    }

    @Override
    public void handleStatus401(String response) {

        //Sessione scaduta, cancello i token e torno alla main activity

        activity.getSharedPreferences("natour_tokens",MODE_PRIVATE).edit().clear().commit();

        activity.startActivity(new Intent(activity, AuthActivity.class));

    }

    @Override
    public void handleStatus500(String response) {

    }

    @Override
    public void handleRequestException(String message) {

    }

    private void setIdTokenInSharedPreferences(SharedPreferences user_details, String response){

        Gson gson = new Gson();

        Tokens tokens = gson.fromJson(removeQuotesAndUnescape(response), Tokens.class);

        SharedPreferences.Editor editor = user_details.edit();

        editor.putString("id_token", tokens.getId_token());

        editor.putString("access_token", tokens.getAccess_token());

        editor.commit();
    }

    private String removeQuotesAndUnescape(String uncleanJson) {
        String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");
        return StringEscapeUtils.unescapeJava(noQuotes);
    }
}