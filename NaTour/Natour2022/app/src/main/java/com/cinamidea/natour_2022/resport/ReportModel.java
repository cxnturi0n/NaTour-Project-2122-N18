package com.cinamidea.natour_2022.resport;

import androidx.annotation.NonNull;

import com.cinamidea.natour_2022.entities.Report;
import com.cinamidea.natour_2022.utilities.ResponseDeserializer;
import com.cinamidea.natour_2022.utilities.http.ReportHTTP;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReportModel implements ReportContract.Model{

    final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .build();

    @Override
    public void sendReport(String id_token, Report report, OnFinishListener listener) {

        if(isReportable(report.getTitle(), report.getDescription())) {
            Request request = ReportHTTP.insertReport(report, id_token);

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

        }else{
            listener.onFailure("Title or description is too short");
        }

    }

    private boolean isReportable(String title, String description) {
        final int MIN_TITLE_LENGTH = 3;
        final int MIN_DESCRIPTION_LENGTH = 3;
        return title.length() >= MIN_TITLE_LENGTH && description.length() >= MIN_DESCRIPTION_LENGTH;
    }

}
