package com.cinamidea.natour_2022.utilities.http;


import android.util.Log;

import com.cinamidea.natour_2022.utilities.http.callbacks.HTTPCallback;

import okhttp3.Headers;
import okhttp3.Request;

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

    public void signInAndGetTokens(String username, String password, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/auth/token";

        String request_body = "{\"username\":" + username + ",\"password\":" + password + ",\"grant_type\":\"PASSWORD\"}";

        request = getPostRequest(url, request_body, null);

        startHttpRequest(callback);
    }

    public static Request signInAndGetTokensRequest(String username, String password) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/auth/token";

        String request_body = "{\"username\":" + username + ",\"password\":" + password + ",\"grant_type\":\"PASSWORD\"}";

        return getPostRequest(url, request_body, null);
    }



    public void refreshToken(String username, String refresh_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/auth/token";

        String request_body = "{\"username\":" + username + ",\"refresh_token\":" + refresh_token + ",\"grant_type\":\"REFRESH_TOKEN\"}";

        request = getPostRequest(url, request_body, null);

        startHttpRequest(callback);
    }


    public static Request getCodeForPasswordReset(String username) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/password/reset-code";

        return getGetRequest(url, null);

    }

    public static Request resetPassword(String username, String password, String confirmation_code) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/password";

        String request_body = "{\"password\":" + password + ",\"confirmation_code\":" + confirmation_code + "}";

        return getPutRequest(url, request_body, null);


    }

    public void changePassword(String username, String old_password, String new_password, String access_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/password";

        Headers header = new Headers.Builder().add("Authorization", access_token).build();

        String request_body = "{\"old_password\":" + old_password + ",\"password\":" + new_password + ",\"confirmation_code\":" + null + "}";

        request = getPutRequest(url, request_body, header);

        startHttpRequest(callback);

    }


}