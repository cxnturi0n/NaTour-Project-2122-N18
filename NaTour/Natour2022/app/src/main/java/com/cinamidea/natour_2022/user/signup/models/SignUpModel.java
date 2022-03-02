package com.cinamidea.natour_2022.user.signup.models;

import androidx.annotation.NonNull;

import com.cinamidea.natour_2022.user.signup.contracts.SignUpContract;
import com.cinamidea.natour_2022.utilities.ResponseDeserializer;
import com.cinamidea.natour_2022.utilities.http.AuthenticationHTTP;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SignUpModel implements SignUpContract.Model {

    final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .build();

    @Override
    public void signUp(String username, String email, String password, OnFinishListener listener) {

        Request request = AuthenticationHTTP.signUp(username, email, password);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                listener.onError(e.getMessage());
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                int response_code = response.code();
                String message = response.body().string();
                if(response_code == 200)
                    listener.onSuccess(message);
                else
                    listener.onError(message);
            }
        });

    }
}
