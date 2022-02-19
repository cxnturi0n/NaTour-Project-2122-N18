package com.cinamidea.natour_2022.navigation.main.models;

import androidx.annotation.NonNull;

import com.cinamidea.natour_2022.navigation.main.contracts.HomeActivityContract;
import com.cinamidea.natour_2022.utilities.http.UsersHTTP;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeActivityModel implements HomeActivityContract.Model {
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .build();


    @Override
    public void putProfileImage(String imageBase64, String current_username, String id_token,byte[] image_as_byte_array, OnFinishedListener listener) {
        Request request = UsersHTTP.putProfileImage(imageBase64,current_username,id_token);

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
}
