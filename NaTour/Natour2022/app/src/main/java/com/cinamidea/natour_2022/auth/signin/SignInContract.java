package com.cinamidea.natour_2022.auth.signin;

import android.content.SharedPreferences;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public interface SignInContract {


    interface View {
        void signInSuccess();

        void googleSignUp();

        void displayError(String message);
    }


    interface Presenter {
        void cognitoSignInButtonClicked(String username, String password, SharedPreferences cognito_preferences);

        void googleSignUpButtonClicked(String username, String email, String id_token, SharedPreferences shared_preferences);
    }

    interface Model {


        interface OnFinishListenerCognito {
            void onSuccess();

            void onFailure(String message);
        }


        interface OnFinishListenerGoogle {
            void onSuccess();

            void onFailure(String message);
        }


        void cognitoSignIn(String username, String password, SharedPreferences cognito_preferences, OnFinishListenerCognito listener);

        void googleSignUp(String username, String email, String id_token, SharedPreferences google_preferences, OnFinishListenerGoogle listener);
    }

}
