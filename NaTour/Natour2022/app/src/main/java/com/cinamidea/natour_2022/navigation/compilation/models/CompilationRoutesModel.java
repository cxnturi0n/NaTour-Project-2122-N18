package com.cinamidea.natour_2022.navigation.compilation.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cinamidea.natour_2022.navigation.compilation.contracts.CompilationRoutesContract;
import com.cinamidea.natour_2022.utilities.ResponseDeserializer;
import com.cinamidea.natour_2022.utilities.http.RoutesHTTP;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CompilationRoutesModel implements CompilationRoutesContract.Model {

    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .build();

    @Override
    public void getRoutes(String username,String compilation_id, String id_token, OnFinishedListener listener) {

        Request request = RoutesHTTP.getUserRoutesCompilation(username, compilation_id, id_token);

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
                        Log.e("ciao",response_body);
                        listener.onSuccess(ResponseDeserializer.jsonToRoutesList(response_body));
                        break;
                    case 400:
                    case 500:
                        listener.onError(response_body);
                        break;
                    case 401:
                        listener.onUserUnauthorized();
                        break;
                    default:
                        return;
                }
            }
        });

    }

    @Override
    public void getFavourites(String id_token,String username, OnFavouriteRoutesFetchedListener listener) {

        Request request = RoutesHTTP.getFavouriteRoutes(username, id_token);

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
