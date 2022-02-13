package com.cinamidea.natour_2022.utilities.http.callbacks.user;


import android.app.Activity;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.cinamidea.natour_2022.utilities.http.callbacks.HTTPCallback;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.Base64;

import de.hdodenhof.circleimageview.CircleImageView;

public class GetProfileImageCallback implements HTTPCallback {

    private final Activity activity;
    private final CircleImageView avatar;

    public GetProfileImageCallback(Activity activity, CircleImageView avatar) {
        this.activity = activity;
        this.avatar = avatar;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void handleStatus200(String response) {

        activity.runOnUiThread(() -> {

            byte[] image_array = Base64.getDecoder().decode(removeQuotesAndUnescape(response));
            Glide.with(activity).load(image_array).circleCrop().into(avatar);

        });

    }

    @Override
    public void handleStatus400(String response) {

    }

    @Override
    public void handleStatus401(String response) {

    }

    @Override
    public void handleStatus500(String response) {

    }

    @Override
    public void handleRequestException(String message) {

    }

    private String removeQuotesAndUnescape(String uncleanJson) {
        String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");
        return StringEscapeUtils.unescapeJava(noQuotes);
    }

}
