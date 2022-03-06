package com.cinamidea.natour.navigation.main.views;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cinamidea.natour.navigation.profile.created.ProfileMyRoadsFragment;
import com.cinamidea.natour.navigation.profile.favourites.ProfileFavouriteRoutesFragment;
import com.cinamidea.natour.navigation.profile.tovisit.ProfileToVisitRoutesFragment;

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
                return new ProfileToVisitRoutesFragment();

        }
        return new ProfileMyRoadsFragment();

    }
}