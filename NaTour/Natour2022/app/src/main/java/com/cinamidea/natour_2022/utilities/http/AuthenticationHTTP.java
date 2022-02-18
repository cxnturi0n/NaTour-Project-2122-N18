package com.cinamidea.natour_2022.utilities.http;


import android.util.Log;

import com.cinamidea.natour_2022.utilities.http.callbacks.HTTPCallback;

import okhttp3.Headers;
import okhttp3.Request;

public class AuthenticationHTTP extends OkHTTPRequest {



    public static Request signUp(String username, String email, String password) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users";

        String request_body = "{\"username\":" + username + ",\"email\":" + email + ",\"password\":" + password + "}";

        return getPostRequest(url, request_body, null);


    }

    public static Request googleSignUp(String username, String email, String id_token) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/auth/google/users";

        String request_body = "{\"username\":" + username + ", \"email\":" + email + "}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        return getPostRequest(url, request_body, header);

    }

    public static Request confirmSignUp(String username, String confirmation_code) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/confirmation";

        String request_body = "{\"confirmation_code\":" + confirmation_code + "}";

        return getPostRequest(url, request_body, null);
    }

    public static Request tokenLogin(String id_token) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/auth/token/validation";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        return getGetRequest(url, header);

    }


    public static Request signInAndGetTokensRequest(String username, String password) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/auth/token";

        String request_body = "{\"username\":" + username + ",\"password\":" + password + ",\"grant_type\":\"PASSWORD\"}";

        return getPostRequest(url, request_body, null);
    }



    public static Request refreshToken(String username, String refresh_token) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/auth/token";

        String request_body = "{\"username\":" + username + ",\"refresh_token\":" + refresh_token + ",\"grant_type\":\"REFRESH_TOKEN\"}";

        return getPostRequest(url, request_body, null);

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

    public static Request changePassword(String username, String old_password, String new_password, String access_token) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/password";

        String request_body = "{\"old_password\":" + old_password + ",\"password\":" + new_password + ",\"confirmation_code\":" + null + ",\"access_token\":" + access_token +"}";

        return getPutRequest(url, request_body, null);

    }


}