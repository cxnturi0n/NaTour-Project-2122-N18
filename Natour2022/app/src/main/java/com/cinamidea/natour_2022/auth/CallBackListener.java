package com.cinamidea.natour_2022.auth;

public interface CallBackListener {

    void onContinueAfterUser();
    void onContinueAfterCode();
    void onContinueAfterPassword();

}
