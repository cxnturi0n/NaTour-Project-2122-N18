package com.cinamidea.natour_2022.users_util;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cinamidea.natour_2022.routes_callbacks.RoutesCallback;
import com.cinamidea.natour_2022.user_callbacks.UsersCallback;

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

public class UsersHTTP {

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .build();

    private static Request request;

    private static void handleHttpRequest(UsersCallback callback) {


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.handleRequestException(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                int response_code = response.code();
                String response_body = response.body().string();
                switch (response_code) {
                    case 200:
                        Log.e("200", response_body);
                        callback.handleStatus200(response_body);
                        break;
                    case 400:
                        Log.e("400", response_body);
                        callback.handleStatus400(response_body);
                        break;
                    case 401:
                        Log.e("401", response_body);
                        callback.handleStatus401(response_body);
                        break;
                    case 500:
                        callback.handleStatus500(response_body);
                        break;
                    default:
                        return;
                }
            }
        });
    }

    public static Request getPostRequest(String url, String request_body, Headers headers) {

        RequestBody body = RequestBody.create(request_body, MediaType.parse("application/json"));

        return headers != null ? new Request.Builder()
                .url(url)
                .post(body).headers(headers)
                .build() : new Request.Builder()
                .url(url)
                .post(body)
                .build();

    }

    public static void putProfileImage(String user_type, String image_base64, String username, String id_token, UsersCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/image";

        String request_body = "{\"user_type\":" + user_type + ",\"action\":" + "INSERT_USER_PROFILE_IMAGE" +
                ",\"image_base64\":" + image_base64 + "}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getPostRequest(url, request_body, header);

        handleHttpRequest(callback);
    }

    public static void getProfileImage(String user_type, String username, String id_token, UsersCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/image";

        String request_body = "{\"user_type\":" + user_type + ",\"action\":" + "GET_USER_PROFILE_IMAGE" + "}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getPostRequest(url, request_body, header);

        handleHttpRequest(callback);
    }

}
