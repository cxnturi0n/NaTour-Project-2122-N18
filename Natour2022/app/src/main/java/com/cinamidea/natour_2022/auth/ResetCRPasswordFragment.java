package com.cinamidea.natour_2022.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cinamidea.natour_2022.R;

public class ResetCRPasswordFragment extends CustomAuthFragment {

    private ResetCRFragmentSwitcher resetCRFragmentSwitcher;
    private Button button;
    private EditText et_password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resetcr_password, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewComponents(view);

        button.setOnClickListener(view1 -> {

            resetCRFragmentSwitcher.switchToResetCRCodeFragment(et_password.getText().toString());

        });


    }

    @Override
    protected void setupViewComponents(View view) {

        resetCRFragmentSwitcher = (ResetCRFragmentSwitcher) getActivity();
        button = view.findViewById(R.id.fragmentCR3_continue);
        et_password = view.findViewById(R.id.fragmentCR3_password);

    }
}