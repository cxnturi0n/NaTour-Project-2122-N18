package com.cinamidea.natour_2022;

import android.content.SharedPreferences;

import com.cinamidea.natour_2022.utilities.UserType;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public interface MainContract {

    interface View{

        void silentSignIn();
        void displayError(String message);
    }

    interface Model{

        interface OnFinishListener{
            void onSuccess();
            void onUserUnauthorized(String message);
            void onError(String message);
        }

        void cognitoSilentSignIn(UserType user_type, OnFinishListener listener);
        void refreshCognitoIdAndAccessTokens(UserType user_type, OnFinishListener listener);
        void googleSilentSignIn(GoogleSignInClient client, SharedPreferences google_preferences, MainContract.Model.OnFinishListener listener);
    }

    interface Presenter{

        void googleSilentSignIn(GoogleSignInClient client, SharedPreferences google_preferences);
        void cognitoSilentSignIn(UserType user_type);
    }

}
