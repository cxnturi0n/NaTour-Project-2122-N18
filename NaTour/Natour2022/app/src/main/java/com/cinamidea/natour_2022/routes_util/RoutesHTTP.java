package com.cinamidea.natour_2022.routes_util;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cinamidea.natour_2022.auth.SigninFragment;
import com.cinamidea.natour_2022.routes_callbacks.RoutesCallback;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RoutesHTTP {

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .build();

    private static Request request;

    private static void handleHttpRequest(RoutesCallback callback) {


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

    public static Request getGetRequest(String url, Headers headers) {

        return headers != null ? new Request.Builder().url(url).headers(headers).build() : new Request.Builder().url(url).build();
    }


    public static void insertRoute(String user_type, Route route, String id_token, RoutesCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes";
        String tags = (route.getTags().length() > 0) ? route.getTags() : null;

        Gson gson = new Gson();

        String json_coords = gson.toJson(route.getCoordinates());

        String request_body = "{\"name\":" + route.getName() + ",\"user_type\":" + user_type + ",\"action\":" + "INSERT"
                + ",\"description\":" + route.getDescription() + ",\"level\":" + route.getLevel() +
                ",\"duration\":" + route.getDuration() + ",\"report_count\":" + route.getReport_count() + ",\"disability_access\":"
                + route.isDisability_access() + ",\"creator_username\":" + route.getCreator_username() +
                ",\"coordinates\":" + json_coords + ",\"tags\":" + tags + ",\"image_base64\":" + route.getImage_base64() + ",\"length\":" + route.getLength() + "}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getPostRequest(url, request_body, header);

        handleHttpRequest(callback);

    }

    //action:GET_ALL
    public static void getAllRoutes(String user_type, String id_token, RoutesCallback callback) {
        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        String request_body = "{\"user_type\":" + user_type + ",\"action\":" + "GET_ALL" + "}";

        request = getPostRequest(url, request_body, header);

        handleHttpRequest(callback);


    }

    public static void getNRoutes(String user_type, String id_token, int start, int end, RoutesCallback callback) {
        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        String request_body = "{\"user_type\":" + user_type + ",\"action\":" + "GET_N" +
                ",\"start\":" + start + ",\"end\":" + end + "}";

        request = getPostRequest(url, request_body, header);

        handleHttpRequest(callback);

    }


    public static void insertFavouriteRoute(String user_type, String route_name, String username, String id_token, RoutesCallback callback) {
        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes";


        String request_body = "{\"user_type\":" + user_type + ",\"action\":" + "INSERT_FAVOURITE" +
                ",\"username\":" + username + ",\"name\":" + route_name + "}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getPostRequest(url, request_body, header);

        handleHttpRequest(callback);
    }

    public static void insertToVisitRoute(String user_type, String route_name, String username, String id_token, RoutesCallback callback) {
        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes";

        String request_body = "{\"user_type\":" + user_type + ",\"action\":" + "INSERT_TOVISIT" +
                ",\"username\":" + username + ",\"name\":" + route_name + "}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getPostRequest(url, request_body, header);

        handleHttpRequest(callback);
    }

    public static void getFavouriteRoutes(String user_type, String username, String id_token, RoutesCallback callback) {
        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes";


        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        String request_body = "{\"user_type\":" + user_type + ",\"action\":" + "GET_PERSONAL_FAVOURITES" +
                ",\"username\":" + username + "}";

        request = getPostRequest(url, request_body, header);

        handleHttpRequest(callback);
    }

    public static void getFavouriteRoutesName(String user_type, String username, String id_token, RoutesCallback callback) {
        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes";


        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        String request_body = "{\"user_type\":" + user_type + ",\"action\":" + "GET_PERSONAL_FAVOURITES_NAMES" +
                ",\"username\":" + username + "}";

        request = getPostRequest(url, request_body, header);

        handleHttpRequest(callback);
    }

    public static void deleteFavouriteRoute(String user_type, String username, String id_token, String route_name, RoutesCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes";


        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        String request_body = "{\"user_type\":" + user_type + ",\"action\":" + "DELETE_FAVOURITE" +
                ",\"username\":" + username + ",\"name\":" + route_name + "}";

        request = getPostRequest(url, request_body, header);

        handleHttpRequest(callback);

    }

    public static void deleteToVisitRoute(String user_type, String username, String id_token, String route_name, RoutesCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes";


        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        String request_body = "{\"user_type\":" + user_type + ",\"action\":" + "DELETE_TOVISIT" +
                ",\"username\":" + username + ",\"name\":" + route_name + "}";

        request = getPostRequest(url, request_body, header);

        handleHttpRequest(callback);

    }

    public static void getToVisitRoutes(String user_type, String username, String id_token, RoutesCallback callback) {
        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        String request_body = "{\"user_type\":" + user_type + ",\"action\":" + "GET_PERSONAL_TOVISIT" +
                ",\"username\":" + username + "}";

        request = getPostRequest(url, request_body, header);

        handleHttpRequest(callback);

    }

    public static void getUserRoutes(String user_type, String creator_username, String id_token, RoutesCallback callback) {
        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        String request_body = "{\"user_type\":" + user_type + ",\"action\":" + "GET_PERSONAL" + ",\"creator_username\":" + creator_username + "}";

        request = getPostRequest(url, request_body, header);

        handleHttpRequest(callback);
    }


    public static void getRoutesByLevel(String user_type, String id_token, String level, RoutesCallback callback) {
        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        String request_body = "{\"user_type\":" + user_type + ",\"action\":" + "GET_BY_LEVEL" +
                ",\"level\":" + level + "}";

        request = getPostRequest(url, request_body, header);

        handleHttpRequest(callback);

    }

    //TODO:Put immagine nel bucket
    /*public static void putImageInBucket(String user_type, String id_token, Base64 image,RoutesCallback callback){
        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        String request_body = "{\"user_type\":" + user_type + ",\"action\":" + "GET_BY_LEVEL" +
                ",\"level\":" + level + "}";


    }


     */


}