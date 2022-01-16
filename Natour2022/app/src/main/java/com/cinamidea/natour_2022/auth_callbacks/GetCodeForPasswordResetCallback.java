package com.cinamidea.natour_2022.auth_callbacks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.ResetCRPasswordFragment;
import com.cinamidea.natour_2022.auth_callbacks.AuthenticationCallback;

public class GetCodeForPasswordResetCallback implements AuthenticationCallback {

    private AppCompatActivity activity;

    public GetCodeForPasswordResetCallback(AppCompatActivity activity){

        this.activity = activity;
    }
    @Override
    public void handleStatus200(String response) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activityResetCR_framelayout, new ResetCRPasswordFragment());
        fragmentTransaction.commit();

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
