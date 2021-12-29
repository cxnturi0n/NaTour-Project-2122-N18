package com.cinamidea.natour_2022;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ForgotPwdFirstFragment extends Fragment {

    private CallBackListener callBackListener;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgotpwd_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        callBackListener = (CallBackListener) getActivity();
        button = view.findViewById(R.id.activityForgotPwd_sendcode);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callBackListener.onSend();

            }
        });



    }
}