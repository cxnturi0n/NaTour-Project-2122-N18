package com.cinamidea.natour_2022.auth;

import android.os.Bundle;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth_util.AWSCognitoAuthentication;

public class ResetCRActivity extends CustomAuthActivity implements ResetCRFragmentSwitcher {

    String username;
    String password;
    String code;
    AWSCognitoAuthentication auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetcr);

        changeFragment(R.id.activityResetCR_framelayout, new ResetCRUserFragment());
        setupViewComponents();
    }

    @Override
    protected void setupViewComponents() {

        auth = new AWSCognitoAuthentication(this);

    }

    @Override
    public void switchToResetCRPasswordFragment(String username) {

        this.username = username;
        auth.initiateForgotPassword(username);
        auth.handleAuthentication(() -> {
            changeFragment(R.id.activityResetCR_framelayout, new ResetCRPasswordFragment());
        });


    }

    @Override
    public void switchToSigninFragment(String confirmation_code) {

        this.code = confirmation_code;
        auth.initiateResetPassword(username, password, code);
        auth.handleAuthentication(() -> {
            changeFragment(R.id.activityResetCR_framelayout, new ResetCRPasswordFragment());
            finish();
        });
    }

    @Override
    public void switchToResetCRCodeFragment(String password) {

        this.password = password;
        changeFragment(R.id.activityResetCR_framelayout, new ResetCRCodeFragment());

    }

}