package com.cinamidea.natour_2022.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cinamidea.natour_2022.R;

public class ResetCRCodeFragment extends Fragment {

    private ResetCRFragmentSwitcher resetCRFragmentSwitcher;
    private Button button;
    private com.chaos.view.PinView pin_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resetcr_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        resetCRFragmentSwitcher = (ResetCRFragmentSwitcher) getActivity();
        button = view.findViewById(R.id.fragmentCR2_continue);
        pin_view = view.findViewById(R.id.fragmentCR2_code);

        button.setOnClickListener(view1 -> resetCRFragmentSwitcher.switchToSigninFragment(pin_view.getText().toString()));

    }
}