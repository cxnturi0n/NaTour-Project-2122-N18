package com.cinamidea.natour_2022.utilities.http;

import com.cinamidea.natour_2022.utilities.http.callbacks.HTTPCallback;

import okhttp3.Headers;

public class UsersHTTP extends OkHTTPRequest {


    public void putProfileImage(String user_type, String image_base64, String username, String id_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/image";

        String request_body = "{\"user_type\":" + user_type + ",\"action\":" + "INSERT_USER_PROFILE_IMAGE" +
                ",\"image_base64\":" + image_base64 + "}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getPostRequest(url, request_body, header);

        startHttpRequest(callback);
    }

    public void getProfileImage(String user_type, String username, String id_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/image";

        String request_body = "{\"user_type\":" + user_type + ",\"action\":" + "GET_USER_PROFILE_IMAGE" + "}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getPostRequest(url, request_body, header);

        startHttpRequest(callback);
    }

}
