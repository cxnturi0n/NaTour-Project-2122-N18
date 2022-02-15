package com.cinamidea.natour_2022.admin;

import androidx.annotation.NonNull;

import com.cinamidea.natour_2022.utilities.ResponseDeserializer;
import com.cinamidea.natour_2022.utilities.UserType;
import com.cinamidea.natour_2022.utilities.http.AdminHTTP;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AdminModel implements AdminContract.Model {

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .build();

    @Override
    public void sendMail(UserType user_type, String subject, String body, OnFinishListener listener) {

        if(isSendable(subject, body)) {

            Request request = AdminHTTP.sendMail(user_type.getIdToken(), subject, body);

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    listener.onFailure(e.getMessage());
                }
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    int response_code = response.code();
                    String message = response.body().string();
                    if(response_code == 200)
                        listener.onSuccess(message);
                    else
                        listener.onFailure(ResponseDeserializer.jsonToMessage(message));
                }
            });

        }

    }

    private final boolean isSendable(String subject, String body) {
        return subject.length() >= 0 && body.length() >= 0;
    }

}
