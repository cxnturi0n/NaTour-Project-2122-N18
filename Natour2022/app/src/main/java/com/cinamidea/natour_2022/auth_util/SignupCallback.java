package com.cinamidea.natour_2022.auth_util;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.cinamidea.natour_2022.auth.PinActivity;

public class SignupCallback implements AuthenticationCallback{


    private String username;
    private Context context;

    public SignupCallback(String username, Context context){
        this.username = username;
        this.context = context;
    }

    @Override
    public void handleStatus200(String response) {

        Intent intent = new Intent(context, PinActivity.class);
        intent.putExtra("username", username);

         context.startActivity(intent);

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
