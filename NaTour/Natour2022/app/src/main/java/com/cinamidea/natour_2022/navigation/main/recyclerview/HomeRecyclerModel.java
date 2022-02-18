package com.cinamidea.natour_2022.navigation.main.recyclerview;

import androidx.annotation.NonNull;

import com.cinamidea.natour_2022.auth.signin.SigninFragment;
import com.cinamidea.natour_2022.utilities.http.RoutesHTTP;
import com.cinamidea.natour_2022.utilities.http.UsersHTTP;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeRecyclerModel implements HomeRecyclerContract.Model {

    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .build();

    @Override
    public void insertFavourite(String route_name, String id_token, OnFinishedListener listener) {

        Request request = RoutesHTTP.insertFavouriteRoute(route_name, SigninFragment.current_username, id_token);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                listener.onError("Network error");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                int response_code = response.code();
                String response_body = response.body().string();
                switch (response_code) {
                    case 200:
                        listener.onSuccess(response_body);
                        break;
                    case 400:
                        listener.onError(response_body);
                        break;
                    case 401:
                        listener.onUserUnauthorized("Invalid session, please sign in again");
                        break;
                    case 500:
                        listener.onNetworkError(response_body);
                        break;
                    default:
                        return;
                }
            }
        });

    }

    @Override
    public void deleteFavourite(String route_name, String id_token, OnFinishedListener listener) {

        Request request = RoutesHTTP.deleteFavouriteRoute(SigninFragment.current_username, id_token, route_name);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                listener.onError("Network error");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                int response_code = response.code();
                String response_body = response.body().string();
                switch (response_code) {
                    case 200:
                        listener.onSuccess(response_body);
                        break;
                    case 400:
                        listener.onError(response_body);
                        break;
                    case 401:
                        listener.onUserUnauthorized("Invalid session, please sign in again");
                        break;
                    case 500:
                        listener.onNetworkError(response_body);
                        break;
                    default:
                        return;
                }
            }
        });
    }

    @Override
    public void insertToVisit(String route_name, String id_token, OnFinishedListener listener) {

        Request request = RoutesHTTP.insertToVisitRoute(route_name, SigninFragment.current_username, id_token);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                listener.onError("Network error");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                int response_code = response.code();
                String response_body = response.body().string();
                switch (response_code) {
                    case 200:
                        listener.onSuccess(response_body);
                        break;
                    case 400:
                        listener.onError(response_body);
                        break;
                    case 401:
                        listener.onUserUnauthorized("Invalid session, please sign in again");
                        break;
                    case 500:
                        listener.onNetworkError(response_body);
                        break;
                    default:
                        return;
                }
            }
        });
    }

    @Override
    public void deleteToVisit(String route_name, String id_token, OnFinishedListener listener) {

        Request request = RoutesHTTP.deleteToVisitRoute(SigninFragment.current_username, id_token, route_name);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                listener.onError("Network error");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                int response_code = response.code();
                String response_body = response.body().string();
                switch (response_code) {
                    case 200:
                        listener.onSuccess(response_body);
                        break;
                    case 400:
                        listener.onError(response_body);
                        break;
                    case 401:
                        listener.onUserUnauthorized("Invalid session, please sign in again");
                        break;
                    case 500:
                        listener.onNetworkError(response_body);
                        break;
                    default:
                        return;
                }
            }
        });

    }

}
