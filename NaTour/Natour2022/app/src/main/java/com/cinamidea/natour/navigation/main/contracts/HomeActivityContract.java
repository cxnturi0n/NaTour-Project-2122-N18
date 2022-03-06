package com.cinamidea.natour.navigation.main.contracts;

import java.io.InputStream;

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
            void onUserUnauthorized();

        }

        void putProfileImage(String imageBase64,String current_username,String id_token,byte[] image_as_byte_array,OnFinishedListener listener);
    }


    interface Presenter {
        void putImage(String current_username, String id_token, InputStream image_stream);
    }


}
