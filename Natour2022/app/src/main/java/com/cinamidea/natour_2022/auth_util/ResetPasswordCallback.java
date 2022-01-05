package com.cinamidea.natour_2022.auth_util;

import android.app.Activity;
import android.content.Context;

public class ResetPasswordCallback implements AuthenticationCallback{


    private Activity activity;

    public ResetPasswordCallback(Activity activity){
        this.activity = activity;
    }
    @Override
    public void handleStatus200(String response) {
        activity.finish();
    }

    @Override
    public void handleStatus400(String response) {

    }

    @Override
    public void handleStatus401(String response) {

    }

    @Override
    public void handleStatus500(String response) {

    }

    @Override
    public void handleRequestException(String message) {

    }
}
