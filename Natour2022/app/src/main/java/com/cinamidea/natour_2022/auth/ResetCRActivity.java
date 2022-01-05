package com.cinamidea.natour_2022.auth;

import android.os.Bundle;
import android.util.Log;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth_util.AWSCognitoAuthentication;
import com.cinamidea.natour_2022.auth_util.AuthenticationCallback;
import com.cinamidea.natour_2022.auth_util.GetCodeForPasswordResetCallback;
import com.cinamidea.natour_2022.auth_util.ResetPasswordCallback;

public class ResetCRActivity extends CustomAuthActivity implements ResetCRFragmentSwitcher {

    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetcr);

        changeFragment(R.id.activityResetCR_framelayout, new ResetCRUserFragment());
        setupViewComponents();
    }

    @Override
    protected void setupViewComponents() {


    }

    @Override
    public void switchToResetCRPasswordFragment(String username) {

        Log.e("Error","1");
        this.username = username;

        AWSCognitoAuthentication auth = new AWSCognitoAuthentication();

        auth.getCodeForPasswordReset(username, new GetCodeForPasswordResetCallback(this));
    }

    @Override
    public void switchToSigninFragment(String confirmation_code) {

        AWSCognitoAuthentication auth = new AWSCognitoAuthentication();
        auth.resetPassword(username, password, confirmation_code, new ResetPasswordCallback(this));
    }

    @Override
    public void switchToResetCRCodeFragment(String password) {

        this.password = password;
        changeFragment(R.id.activityResetCR_framelayout, new ResetCRCodeFragment());

    }

}