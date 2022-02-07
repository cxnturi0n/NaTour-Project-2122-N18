package com.cinamidea.natour_2022.utilities.http;


import android.util.Log;

import com.cinamidea.natour_2022.utilities.http.callbacks.HTTPCallback;

import okhttp3.Headers;

public class AuthenticationHTTP extends OkHTTPRequest {

    public void signUp(String username, String email, String password, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users";

        String request_body = "{\"username\":" + username + ",\"email\":" + email + ",\"password\":" + password + "}";

        request = getPostRequest(url, request_body, null);

        startHttpRequest(callback);

    }

    public void googleSignUp(String username, String email, String id_token, HTTPCallback callback) {


        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/auth/google/users";

        String request_body = "{\"username\":" + username + ", \"email\":" + email + "}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getPostRequest(url, request_body, header);

        startHttpRequest(callback);

    }

    public void confirmSignUp(String username, String confirmation_code, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/confirmation";

        String request_body = "{\"confirmation_code\":" + confirmation_code + "}";

        request = getPostRequest(url, request_body, null);

        startHttpRequest(callback);
    }

    public void tokenLogin(String id_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/auth/token/validation";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getGetRequest(url, header);

        startHttpRequest(callback);


    }

    public void getIdNRefreshTokens(String username, String password, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/auth/token";

        String request_body = "{\"username\":" + username + ",\"password\":" + password + ",\"grant_type\":\"PASSWORD\"}";

        request = getPostRequest(url, request_body, null);

        startHttpRequest(callback);
    }

    public void refreshToken(String username, String refresh_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/auth/token";

        String request_body = "{\"username\":" + username + ",\"refresh_token\":" + refresh_token + ",\"grant_type\":\"REFRESH_TOKEN\"}";

        request = getPostRequest(url, request_body, null);

        startHttpRequest(callback);
    }

    public void getCodeForPasswordReset(String username, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/password/reset-code";

        request = getGetRequest(url, null);

        startHttpRequest(callback);
    }

    public void resetPassword(String username, String password, String confirmation_code, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/password";

        String request_body = "{\"password\":" + password + ",\"confirmation_code\":" + confirmation_code + "}";

        request = getPutRequest(url, request_body);

        startHttpRequest(callback);

    }

    public void changePassword(String username, String old_password, String new_password, String access_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/password";

        String request_body = "{\"old_password\":" + old_password + ",\"password\":" + new_password + ",\"confirmation_code\":" + null + ",\"access_token\":" + access_token + "}";

        request = getPutRequest(url, request_body);

        startHttpRequest(callback);

    }


}