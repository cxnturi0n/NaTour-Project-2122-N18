package com.cinamidea.natour_2022.auth;

import android.os.Bundle;

import com.cinamidea.natour_2022.R;

public class ResetCRActivity extends CustomAuthActivity implements CallBackListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetcr);

        changeFragment(R.id.activityResetCR_framelayout, new ResetCRUserFragment());

    }

    @Override
    protected void setupViewComponents() {

    }

    @Override
    public void onContinueAfterUser() {

        changeFragment(R.id.activityResetCR_framelayout, new ResetCRCodeFragment());

    }

    @Override
    public void onContinueAfterCode() {

        changeFragment(R.id.activityResetCR_framelayout, new ResetCRPasswordFragment());

    }

    @Override
    public void onContinueAfterPassword() {

        finish();

    }

}