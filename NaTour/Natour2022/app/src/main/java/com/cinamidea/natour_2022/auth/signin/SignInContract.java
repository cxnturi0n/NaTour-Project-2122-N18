package com.cinamidea.natour_2022.auth.signin;

import com.cinamidea.natour_2022.utilities.auth.GoogleAuthentication;

public interface SignInContract {


    //Metodo chiamato dal presenter per aggiornare la UI
    interface View{
        void signInSuccess();
        void displayError(String message);
    }

    //Metodo chiamato dalla view al premere del bottone per il signin
    interface Presenter{
        void cognitoSignInButtonClicked(String username, String password);
        void googleSignInButtonClicked(GoogleAuthentication google_auth);
    }

    interface Model{

        //Metodi richiamati dal model dopo la chiamata http, sono implementati nel presenter
        interface OnFinishListener {
            void onSuccess(String message);
            void onFailure(String message);
        }
        //Metodo per effettuare il signin tramite call rest
        void cognitoSignIn(String username, String password, OnFinishListener listener);
        void googleSignIn(GoogleAuthentication google_auth);
    }

}
