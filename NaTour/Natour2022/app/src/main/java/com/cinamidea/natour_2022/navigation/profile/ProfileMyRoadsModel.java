package com.cinamidea.natour_2022.navigation.profile;

import androidx.annotation.NonNull;

import com.cinamidea.natour_2022.auth.signin.SigninFragment;
import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.utilities.http.RoutesHTTP;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProfileMyRoadsModel implements ProfileMyRoadsContract.Model{
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .build();

    @Override
    public void getUserRoutes(String id_token, OnFinishedListener listener) {
        Request request = RoutesHTTP.getUserRoutes(SigninFragment.current_username,id_token);
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
                        ArrayList<Route> routes = jsonToRoutesParsing(response_body);
                        ArrayList<Route> fav_routes = getFavouriteRoutes(id_token);
                        listener.onSuccess(routes, fav_routes);
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

    private ArrayList<Route> getFavouriteRoutes(String id_token) {

        Request request = RoutesHTTP.getFavouriteRoutes(SigninFragment.current_username, id_token);

        final ArrayList<Route>[] fav_routes = new ArrayList[]{new ArrayList<>()};

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                int response_code = response.code();
                String response_body = response.body().string();
                if(response_code == 200) {
                    fav_routes[0] = jsonToRoutesParsing(response_body);
                }
            }
        });

        return fav_routes[0];

    }

    private final ArrayList<Route> jsonToRoutesParsing(String response) {
        Gson gson = new Gson();
        ArrayList<Route> routes = new ArrayList<>();
        Route[] routes_array = gson.fromJson(removeQuotesAndUnescape(response), Route[].class);
        for (int i = 0; i < routes_array.length; i++) {

            routes.add(routes_array[i]);
        }

        return routes;

    }

    private final String removeQuotesAndUnescape(String uncleanJson) {
        String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");
        return StringEscapeUtils.unescapeJava(noQuotes);
    }


}
