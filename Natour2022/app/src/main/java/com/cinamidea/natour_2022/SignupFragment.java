package com.cinamidea.natour_2022;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class SignupFragment extends Fragment {

    private Button button;
    private Animation scale_up, scale_down;
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button = view.findViewById(R.id.fragmentSignup_signup);
        scale_up = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_down);
        intent = new Intent(getActivity(), PinActivity.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAnimation(button);
            }
        });

    }

    private void buttonAnimation(Button button) {

        button.startAnimation(scale_up);
        button.startAnimation(scale_down);
        button.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        }, 200);

    }

}