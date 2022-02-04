package com.cinamidea.natour_2022.navigation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cinamidea.natour_2022.navigation.profile.ProfileMyRoadsFragment;
import com.cinamidea.natour_2022.navigation.profile.ProfilePreferredRoadsFragment;
import com.cinamidea.natour_2022.navigation.profile.ProfileRoadsToVisitFragment;

public class ProfileFragmentAdapter extends FragmentStateAdapter {


    public ProfileFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {

            case 1:
                return new ProfilePreferredRoadsFragment();
            case 2:
                return new ProfileRoadsToVisitFragment();

        }
        return new ProfileMyRoadsFragment();

    }
}