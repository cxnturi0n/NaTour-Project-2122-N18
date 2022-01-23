package com.cinamidea.natour_2022.auth;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.cinamidea.natour_2022.R;

public abstract class CustomAuthFragment extends Fragment {

    private Animation anim_scale_up;
    private Animation anim_scale_down;
    protected Handler handler = new Handler();

    protected abstract void setupViewComponents(View view);

    protected void setupAnimation() {

        anim_scale_up = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_up);
        anim_scale_down = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_down);

    }

    protected void runAnimation(Button button) {

        button.startAnimation(anim_scale_up);
        button.startAnimation(anim_scale_down);

    }

    protected void runHandledIntent(Intent intent) {

        handler.postDelayed(() -> startActivity(intent),170);

    }


    protected void runHandledIntent(Intent intent, int enter_animation, int exit_animation) {

        handler.postDelayed(() -> {

            startActivity(intent);
            getActivity().overridePendingTransition(enter_animation, exit_animation);

        },170);

    }

}