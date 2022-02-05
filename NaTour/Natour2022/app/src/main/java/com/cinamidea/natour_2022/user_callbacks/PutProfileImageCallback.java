package com.cinamidea.natour_2022.user_callbacks;

import android.app.Activity;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.cinamidea.natour_2022.util.ResponseHTTP;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringEscapeUtils;

import java.nio.channels.Channel;
import java.util.Base64;

import de.hdodenhof.circleimageview.CircleImageView;
import io.getstream.chat.android.client.ChatClient;

public class PutProfileImageCallback implements UsersCallback{

    private Activity activity;
    private CircleImageView avatar;
    private byte[] image_as_byte_array;

    //TODO:CHAT
    private ChatClient client = ChatClient.instance();

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
            //TODO:CHAT
            client.getCurrentUser().setImage("https://natour-android.s3.eu-central-1.amazonaws.com/Users/ProfilePics/Umberto");

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
