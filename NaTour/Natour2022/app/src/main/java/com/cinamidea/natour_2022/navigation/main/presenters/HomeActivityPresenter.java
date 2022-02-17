package com.cinamidea.natour_2022.navigation.main.presenters;

import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.cinamidea.natour_2022.navigation.main.contracts.HomeActivityContract;
import com.cinamidea.natour_2022.navigation.main.models.HomeActivityModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class HomeActivityPresenter implements HomeActivityContract.Presenter {
    HomeActivityContract.Model model;
    HomeActivityContract.View view;

    public HomeActivityPresenter(HomeActivityContract.View view) {
        this.model = new HomeActivityModel();
        this.view = view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void putImage(String current_username, String id_token, InputStream image_stream) {

        try {
            byte[] image_as_byte_array = getBytes(image_stream);
            String image_base64 = Base64.getEncoder().encodeToString(image_as_byte_array);

            model.putProfileImage(image_base64, current_username, id_token,image_as_byte_array, new HomeActivityContract.Model.OnFinishedListener() {
                @Override
                public void onSuccess(String response) {
                    view.loadProfileImage(image_as_byte_array);
                    view.setChatUserImage();
                }

                @Override
                public void onError(String response) {

                }

                @Override
                public void onUserUnauthorized(String response) {
                    view.logOutUnauthorizedUser();
                }

                @Override
                public void onNetworkError(String response) {

                }
            });
        } catch (IOException e) {
        }

    }


    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
