package com.cinamidea.natour_2022.admin;

import com.cinamidea.natour_2022.utilities.UserType;

public interface AdminContract {

    interface View {

        void displayError(String message);
        void mailSent(String message);
        void logOutUnauthorizedUser();

    }

    interface Presenter {

        void onSendButtonClicked(UserType user_type, String subject, String body);

    }

    interface Model {

        interface OnFinishListener {
            void onSuccess(String response);
            void onError(String response);
            void onUserUnauthorized(String response);
            void onNetworkError(String response);
        }

        void sendMail(UserType user_type, String subject, String body, OnFinishListener onFinishListener);

    }

}
