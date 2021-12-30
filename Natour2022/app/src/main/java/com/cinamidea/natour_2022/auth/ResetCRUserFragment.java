package com.cinamidea.natour_2022.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cinamidea.natour_2022.R;

public class ResetCRUserFragment extends Fragment {

    private CallBackListener callBackListener;
    private Button button;
    private TextView text_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resetcr_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        callBackListener = (CallBackListener) getActivity();
        button = view.findViewById(R.id.fragmentCR1_continue);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callBackListener.onContinueAfterUser();

            }
        });

        text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });


    }
}