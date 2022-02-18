package com.cinamidea.natour_2022.navigation.profile.tovisit;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.navigation.main.recyclerview.RecyclerViewAdapter;
import com.cinamidea.natour_2022.navigation.main.views.HomeActivity;
import com.cinamidea.natour_2022.utilities.auth.UserSharedPreferences;

public class ProfileRoadsToVisitFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private View view;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_roads_to_visit, container, false);
        setupViewComponents(view);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        if (!HomeActivity.counter_updated[2]) {
            recyclerView.setAdapter(null);
            progressBar.setVisibility(View.VISIBLE);
            loadRoutes();
            HomeActivity.is_updated = false;
            HomeActivity.counter_updated[2] = true;
        }
    }

    private void setupViewComponents(View view) {

        recyclerView = view.findViewById(R.id.fragmentToVisit_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = view.findViewById(R.id.fragmentToVisit_progress);
        loadRoutes();

    }

    private void loadRoutes() {

        UserSharedPreferences user_type = new UserSharedPreferences(getActivity());
        String id_token = user_type.getUser_type() + user_type.getId_token();

    }
}