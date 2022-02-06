package com.cinamidea.natour_2022.callbacks.user;

import android.app.Activity;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.cinamidea.natour_2022.callbacks.HTTPCallback;

import org.apache.commons.lang3.StringEscapeUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class PutProfileImageCallback implements HTTPCallback {

    private Activity activity;
    private CircleImageView avatar;
    private byte[] image_as_byte_array;

    public PutProfileImageCallback(Activity activity, CircleImageView avatar, byte[] image_as_byte_array) {
        this.activity = activity;
        this.avatar = avatar;
        this.image_as_byte_array = image_as_byte_array;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void handleStatus200(String response) {

        activity.runOnUiThread(() -> {

            Glide.with(activity).load(image_as_byte_array).circleCrop().into(avatar);

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
