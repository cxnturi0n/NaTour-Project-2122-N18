package com.cinamidea.natour_2022.auth.signin;

import androidx.annotation.NonNull;

import com.cinamidea.natour_2022.utilities.auth.GoogleAuthentication;
import com.cinamidea.natour_2022.utilities.http.AuthenticationHTTP;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SignInModel implements SignInContract.Model {

    final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .build();

    @Override
    public void cognitoSignIn(String username, String password, OnFinishListener listener) {

        Request request = AuthenticationHTTP.signInAndGetTokensRequest(username, password);

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
                    listener.onFailure(message);
            }
        });

    }

    @Override
    public void googleSignIn(GoogleAuthentication google_auth) {
        google_auth.silentSignIn();
    }

}
