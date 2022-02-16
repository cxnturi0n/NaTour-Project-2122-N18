package com.cinamidea.natour_2022.map.models;

import androidx.annotation.NonNull;

import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.map.contracts.CreatePathActivityContract;
import com.cinamidea.natour_2022.utilities.UserType;
import com.cinamidea.natour_2022.utilities.http.RoutesHTTP;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CreatePathActivityModel implements CreatePathActivityContract.Model {

    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .build();


    @Override
    public void insertRoute(OnFinishedListener listener ,String id_token ,Route route) {
        Request request = RoutesHTTP.insertRoute(route, id_token);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                listener.onStatus400("Network error");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                int response_code = response.code();
                String response_body = response.body().string();
                switch (response_code) {
                    case 200:
                        listener.onStatus200(response_body);
                        break;
                    case 400:
                        listener.onStatus400(response_body);
                        break;
                    case 401:
                        listener.onStatus401(response_body);
                        break;
                    case 500:
                        listener.onStatus500(response_body);
                        break;
                    default:
                        return;
                }
            }
        });
    }
}
