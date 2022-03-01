package com.cinamidea.natour_2022.user.changepassword;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cinamidea.natour_2022.utilities.ResponseDeserializer;
import com.cinamidea.natour_2022.utilities.UserType;
import com.cinamidea.natour_2022.utilities.http.AuthenticationHTTP;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChangePasswordModel implements ChangePasswordContract.Model{

    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .build();

    @Override
    public void changePassword(UserType user_type, String old_password, String new_password, OnFinishedListener listener) {
        Log.e("f", user_type.getAccessToken());
        Request request = AuthenticationHTTP.changePassword(user_type.getUsername(), old_password, new_password, user_type.getAccessToken());
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
                        listener.onSuccess();
                        break;
                    case 400:
                        listener.onError(ResponseDeserializer.jsonToMessage(response_body));
                        break;
                    case 401:
                        listener.onUserUnauthorized("Invalid session, please sign in again");
                        break;
                    default:
                        return;
                }
            }
        });


    }
}