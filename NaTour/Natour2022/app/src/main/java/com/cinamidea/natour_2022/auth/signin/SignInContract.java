package com.cinamidea.natour_2022.auth.signin;

import android.content.SharedPreferences;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public interface SignInContract {


    //Metodo chiamato dal presenter per aggiornare la UI
    interface View {
        void signInSuccess();

        void googleSignUp();

        void displayError(String message);
    }

    //Metodo chiamato dalla view al premere del bottone per il signin
    interface Presenter {
        void cognitoSignInButtonClicked(String username, String password, SharedPreferences cognito_preferences);

        void googleSignInButtonClicked(GoogleSignInClient client, SharedPreferences google_preferences);

        void googleSignUpButtonClicked(String username, String email, String id_token, SharedPreferences shared_preferences);
    }

    interface Model {

        //Metodi richiamati dal model dopo la chiamata http, sono implementati nel presenter
        interface OnFinishListenerCognito {
            void onSuccess();

            void onFailure(String message);
        }

        //Metodi richiamati dal model dopo la chiamata http, sono implementati nel presenter
        interface OnFinishListenerGoogle {
            void onSuccess();

            void onSignUpNeeded();

            void onFailure(String message);
        }

        //Metodo per effettuare il signin tramite call rest
        void cognitoSignIn(String username, String password, SharedPreferences cognito_preferences, OnFinishListenerCognito listener);

        void googleSilentSignIn(GoogleSignInClient client, SharedPreferences google_preferences, OnFinishListenerGoogle listener);

        void googleSignUp(String username, String email, String id_token, SharedPreferences google_preferences, OnFinishListenerGoogle listener);
    }

}
