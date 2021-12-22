package com.cinamidea.natour_2022;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class SigninFragment extends Fragment {

    private Button button;
    private Animation anim_scale_up, anim_scale_down;
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewComponents(view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonAnimator(button);
                runIntent(intent);

            }
        });

    }

    private void setupViewComponents(View view) {

        button = view.findViewById(R.id.fragmentSignin_signin);
        anim_scale_up = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_up);
        anim_scale_down = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_down);

        intent = new Intent(getActivity(), ProfileActivity.class);

    }
    private void buttonAnimator(Button button) {

        button.startAnimation(anim_scale_up);
        button.startAnimation(anim_scale_down);

    }

    private void runIntent(Intent intent) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        },170);

    }


}