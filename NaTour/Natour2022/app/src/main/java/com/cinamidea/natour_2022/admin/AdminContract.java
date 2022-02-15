package com.cinamidea.natour_2022.admin;

import com.cinamidea.natour_2022.utilities.UserType;

public interface AdminContract {

    interface View {

        void displayError(String message);
        void mailSent(String message);

    }

    interface Presenter {

        void onSendButtonClicked(UserType user_type, String subject, String body);

    }

    interface Model {

        interface OnFinishListener {
            void onSuccess(String message);
            void onFailure(String message);
        }

        void sendMail(UserType user_type, String subject, String body, OnFinishListener onFinishListener);

    }

}
