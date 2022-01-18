package com.cinamidea.natour_2022.auth_callbacks;

import android.util.Log;

public class ChangePasswordCallback implements AuthenticationCallback{

    @Override
    public void handleStatus200(String response) {

        //PASSWORD CAMBIATA
        Log.e("200",response);
    }

    @Override
    public void handleStatus400(String response) {

        //Errore nella vecchia password o nella nuova
        Log.e("400",response);
    }

    @Override
    public void handleStatus401(String response) {

        //Errore validita access token
        Log.e("401",response);
    }

    @Override
    public void handleStatus500(String response) {

    }

    @Override
    public void handleRequestException(String message) {

    }
}
