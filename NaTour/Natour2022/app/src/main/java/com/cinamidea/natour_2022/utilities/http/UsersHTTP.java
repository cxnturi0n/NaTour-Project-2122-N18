package com.cinamidea.natour_2022.utilities.http;


import okhttp3.Headers;
import okhttp3.Request;

public class UsersHTTP extends OkHTTPRequest {


    public static Request putProfileImage(String image_base64, String username, String id_token) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/image";

        String request_body = "{\"image_base64\":" + image_base64 + "}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        return getPostRequest(url, request_body, header);
    }

    public static Request getProfileImage(String user_type, String username, String id_token) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/image";

        String request_body = "{\"user_type\":" + user_type + ",\"action\":" + "GET_USER_PROFILE_IMAGE" + "}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

       return getPostRequest(url, request_body, header);

    }

}
