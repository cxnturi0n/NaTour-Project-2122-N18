package com.cinamidea.natour_2022.utilities.routes;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cinamidea.natour_2022.callbacks.HTTPCallback;
import com.cinamidea.natour_2022.entities.Report;
import com.cinamidea.natour_2022.entities.Route;
import com.google.gson.Gson;

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

public class RoutesHTTP {

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .build();

    private static Request request;

    private static void handleHttpRequest(HTTPCallback callback) {


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

    public static Request getDeleteRequest(String url, Headers headers) {

        return headers != null ? new Request.Builder().delete().url(url).headers(headers).build() : new Request.Builder().delete().url(url).build();
    }


    public static void insertRoute(String user_type, Route route, String id_token, HTTPCallback callback) {

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

    public static void getAllRoutes(String id_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes";

        Log.e("CAZZO",id_token);

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getGetRequest(url, header);

        handleHttpRequest(callback);


    }

    public static void getNRoutes(String id_token, int start, int end, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes?start="+start+"&end="+end;

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getGetRequest(url, header);

        handleHttpRequest(callback);

    }


    public static void insertFavouriteRoute(String route_name, String username, String id_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/"+username+"/favourite-routes";

        String request_body = "{\"route_name\":" + route_name + "}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getPostRequest(url, request_body, header);

        handleHttpRequest(callback);
    }

    public static void insertToVisitRoute(String route_name, String username, String id_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/"+username+"/tovisit-routes";

        String request_body = "{\"route_name\":" + route_name + "}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getPostRequest(url, request_body, header);

        handleHttpRequest(callback);
    }

    public static void insertReport(Report report, String id_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes/report";

        String request_body = "{\"issuer\":" + report.getIssuer() + ",\"route_name\":" + report.getRoute_name() + ",\"description\":"
                + report.getDescription() + ",\"title\":" + report.getTitle() + ",\"type\":" + report.getType() +"}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getPostRequest(url, request_body, header);

        handleHttpRequest(callback);
    }

    public static void getFavouriteRoutes(String username, String id_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/"+username+"/favourite-routes";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getGetRequest(url, header);

        handleHttpRequest(callback);
    }

    public static void deleteFavouriteRoute(String username, String id_token, String route_name, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/"+username+"/favourite-routes?route-name="+route_name;

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getDeleteRequest(url, header);

        handleHttpRequest(callback);

    }

    public static void deleteToVisitRoute(String username, String id_token, String route_name, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/"+username+"/tovisit-routes?route-name="+route_name;

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getDeleteRequest(url, header);

        handleHttpRequest(callback);

    }

    public static void getToVisitRoutes(String username, String id_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/"+username+"/tovisit-routes";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getGetRequest(url, header);

        handleHttpRequest(callback);

    }

    public static void getUserRoutes(String creator_username, String id_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes?creator-username="+creator_username;

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getGetRequest(url, header);

        handleHttpRequest(callback);
    }


    public static void getRoutesByLevel(String id_token, String level, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes?level="+level;

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getGetRequest(url, header);

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