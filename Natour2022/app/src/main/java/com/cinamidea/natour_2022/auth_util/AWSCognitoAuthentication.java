package com.cinamidea.natour_2022.auth_util;


import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AWSCognitoAuthentication {

    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .build();

    private Request request;

    public AWSCognitoAuthentication() {
    }


    private void handleAuthentication(AuthenticationCallback callback) {


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.handleRequestException(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                int response_code = response.code();
                String response_body = response.body().string();
                switch(response_code){
                    case 200 :
                        Log.e("200", response_body);
                        callback.handleStatus200(response_body);
                        break;
                    case 400 :
                        Log.e("400", response_body);
                        callback.handleStatus400(response_body);
                        break;
                    case 401 :
                        Log.e("401", response_body);
                        callback.handleStatus401(response_body);
                        break;
                    case 500 :
                        callback.handleStatus500(response_body);
                        break;
                    default:
                        return;
                }
            }
        });
    }


    public void signUp(String username, String email, String password, AuthenticationCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users";

        String request_body = "{\"username\":" + username + ",\"email\":" + email + ",\"password\":" + password + "}";

        request = getPostRequest(url, request_body);

        handleAuthentication(callback);

    }

    public void confirmSignUp(String username, String confirmation_code, AuthenticationCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/confirmation";

        String request_body = "{\"confirmation_code\":" + confirmation_code +"}";

        request = getPostRequest(url, request_body);

        handleAuthentication(callback);
    }

    public void tokenLogin(String id_token, AuthenticationCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/auth/token/validation";

        Headers headers = new Headers.Builder().add("Authorization", "\""+id_token+"\"").build();

        request = getGetRequest(url, headers);

        handleAuthentication(callback);


    }

    public void getIdNRefreshTokens(String username, String password, AuthenticationCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/auth/token";

        String request_body = "{\"username\":" + username + ",\"password\":" + password + ",\"grant_type\":\"PASSWORD\"}";

        request = getPostRequest(url, request_body);

        handleAuthentication(callback);
    }

    public void refreshToken(String username, String refresh_token, AuthenticationCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/auth/token";

        String request_body = "{\"username\":" + username + ",\"refresh_token\":"+refresh_token +",\"grant_type\":\"REFRESH_TOKEN\"}";

        request = getPostRequest(url, request_body);

        handleAuthentication(callback);
    }

    public void getCodeForPasswordReset(String username, AuthenticationCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/password/reset-code";

        request = getGetRequest(url, null);

        handleAuthentication(callback);
    }

    public void resetPassword(String username, String password, String confirmation_code, AuthenticationCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/password";

        String request_body = "{\"password\":" + password + ",\"confirmation_code\":" + confirmation_code + "}";

        request = getPutRequest(url, request_body);

        handleAuthentication(callback);

    }

    public Request getPostRequest(String url, String request_body) {

        RequestBody body = RequestBody.create(request_body, MediaType.parse("application/json"));

        return new Request.Builder()
                .url(url)
                .post(body)
                .build();

    }

    public Request getPutRequest(String url, String request_body) {

        RequestBody body = RequestBody.create(request_body, MediaType.parse("application/json"));

        return new Request.Builder()
                .url(url)
                .put(body)
                .build();

    }

    public Request getGetRequest(String url, Headers headers) {

        return headers!=null ? new Request.Builder().url(url).headers(headers).build() : new Request.Builder().url(url).build();
    }

}