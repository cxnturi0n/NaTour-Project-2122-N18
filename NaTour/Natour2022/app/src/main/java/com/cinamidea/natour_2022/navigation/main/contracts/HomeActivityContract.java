package com.cinamidea.natour_2022.navigation.main.contracts;

import android.content.Intent;
import android.net.Uri;

import com.cinamidea.natour_2022.MainActivity;
import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.utilities.UserType;

import java.io.InputStream;
import java.util.ArrayList;

public interface HomeActivityContract {

    interface View{
        void loadProfileImage(byte[] image_as_byte_array);
        void setChatUserImage();
        void displayError(String message);
        void logOutUnauthorizedUser();
    }


    interface Model {
        interface OnFinishedListener{
            void onSuccess(String response);
            void onError(String response);
            void onUserUnauthorized(String response);
            void onNetworkError(String response);

        }

        void putProfileImage(String imageBase64,String current_username,String id_token,byte[] image_as_byte_array,OnFinishedListener listener);
    }


    interface Presenter {
        void putImage(String current_username, String id_token, InputStream image_stream);
    }


}
