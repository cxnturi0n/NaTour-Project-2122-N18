package com.cinamidea.natour_2022.navigation.profile.tovisit;

import androidx.annotation.NonNull;

import com.cinamidea.natour_2022.auth.signin.SigninFragment;
import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.utilities.ResponseDeserializer;
import com.cinamidea.natour_2022.utilities.http.RoutesHTTP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProfileRoutesToVisitModel implements ProfileRoutesToVisitContract.Model{
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .build();


    @Override
    public void getToVisitRoutes(String id_token, OnFinishedListener listener) {
        Request request = RoutesHTTP.getToVisitRoutes(SigninFragment.current_username, id_token);

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
                        ArrayList<Route> to_visit_routes = ResponseDeserializer.jsonToRoutesList(response_body);
                        listener.onSuccess(to_visit_routes);
                        break;
                    case 401:
                        listener.onUserUnauthorized();
                        break;
                    default:
                        listener.onError(ResponseDeserializer.jsonToMessage(response_body));
                }
            }
        });
    }

    @Override
    public void getFavouriteRoutes(String id_token, OnFavouriteRoutesFetchedListener listener) {

        Request request = RoutesHTTP.getFavouriteRoutes(SigninFragment.current_username, id_token);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                int response_code = response.code();
                String response_body = response.body().string();
                    if(response_code == 200)
                        listener.onSuccess(ResponseDeserializer.jsonToRoutesList(response_body));
            }
        });
    }



}
