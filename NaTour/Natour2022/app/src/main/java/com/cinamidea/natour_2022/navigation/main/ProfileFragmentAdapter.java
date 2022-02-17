package com.cinamidea.natour_2022.navigation.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cinamidea.natour_2022.navigation.profile.ProfileMyRoadsFragment;
import com.cinamidea.natour_2022.navigation.profile.favourites.ProfileFavouriteRoutesFragment;
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
                return new ProfileFavouriteRoutesFragment();
            case 2:
                return new ProfileRoadsToVisitFragment();

        }
        return new ProfileMyRoadsFragment();

    }
}