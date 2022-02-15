package com.cinamidea.natour_2022.auth.resetpassword.views;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.resetpassword.ResetCRContract;
import com.cinamidea.natour_2022.auth.resetpassword.ResetCRFragmentSwitcher;
import com.cinamidea.natour_2022.auth.resetpassword.ResetCRModel;
import com.cinamidea.natour_2022.auth.resetpassword.ResetCRPresenter;

public class ResetCRActivity extends AppCompatActivity implements ResetCRFragmentSwitcher, ResetCRContract.View {

    private String username;
    private String password;
    private ResetCRPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetcr);

        presenter = new ResetCRPresenter(this, new ResetCRModel());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activityResetCR_framelayout, new ResetCRUserFragment());
        fragmentTransaction.commit();

    }

    @Override
    public void switchToResetCRPasswordFragment(String username) {

        this.username = username;
        presenter.requestCodeButtonClicked(username);

    }

    @Override
    public void switchToSigninFragment(String confirmation_code) {

        presenter.resetPasswordButtonClicked(username, password, confirmation_code);

    }

    @Override
    public void switchToResetCRCodeFragment(String password) {

        this.password = password;
        changeFragmentToResetCRCode();

    }

    private void changeFragmentToResetCRCode() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activityResetCR_framelayout, new ResetCRCodeFragment());
        fragmentTransaction.commit();

    }

    @Override
    public void changeFragmentToResetCRPassword() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activityResetCR_framelayout, new ResetCRPasswordFragment());
        fragmentTransaction.commit();

    }

    @Override
    public void resetDone(String message) {
        //TODO Inserire toast reset ok
        finish();
    }

    @Override
    public void displayUsernameNotFoundError(String message) {
        Log.e("notfounht","12");
        //TODO Inserire toast
    }

    @Override
    public void displayResetError(String message) {
        Log.e("123","123123123");
        //TODO Inserire toast
    }
}