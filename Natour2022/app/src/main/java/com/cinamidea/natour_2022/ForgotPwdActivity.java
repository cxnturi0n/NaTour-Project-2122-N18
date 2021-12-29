package com.cinamidea.natour_2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class ForgotPwdActivity extends AppCompatActivity implements CallBackListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);

        changeFragment(new ForgotPwdFirstFragment());

    }

    private void changeFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activityForgotPwd_framelayout, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onSend() {

        changeFragment(new ForgotPwdSecondFragment());

    }
}