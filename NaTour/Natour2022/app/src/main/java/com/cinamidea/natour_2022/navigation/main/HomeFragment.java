package com.cinamidea.natour_2022.navigation.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.signin.SigninFragment;
import com.cinamidea.natour_2022.utilities.auth.UserSharedPreferences;
import com.cinamidea.natour_2022.utilities.http.RoutesHTTP;
import com.cinamidea.natour_2022.utilities.http.callbacks.routes.GetAllRoutesCallback;

public class HomeFragment extends Fragment {

    private Button button_all, button_easy, button_medium, button_hard, button_extreme;
    private Button position_button;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewComponents();
        filterListeners();
    }

    private void setupViewComponents() {

        button_all = view.findViewById(R.id.fragmentHome_all);
        button_easy = view.findViewById(R.id.fragmentHome_easy);
        button_medium = view.findViewById(R.id.fragmentHome_medium);
        button_hard = view.findViewById(R.id.fragmentHome_hard);
        button_extreme = view.findViewById(R.id.fragmentHome_extreme);
        position_button = button_all;

        recyclerView = view.findViewById(R.id.fragmentHome_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ProgressBar progressBar = view.findViewById(R.id.fragmentHome_progress);


        UserSharedPreferences user_type = new UserSharedPreferences(getActivity());
        String id_token = user_type.getUser_type() + user_type.getId_token();
        new RoutesHTTP().getAllRoutes(id_token,
                new GetAllRoutesCallback(SigninFragment.current_username, id_token, recyclerView, recyclerViewAdapter, getActivity(), progressBar));

    }


    private void filterListeners() {
        ProgressBar progressBar = view.findViewById(R.id.fragmentHome_progress);

        button_all.setOnClickListener(view -> {

            setupFilterButton(button_all);
            progressBar.setVisibility(View.VISIBLE);
            UserSharedPreferences user_type = new UserSharedPreferences(getActivity());
            String id_token = user_type.getUser_type() + user_type.getId_token();
            new RoutesHTTP().getAllRoutes(id_token,
                    new GetAllRoutesCallback(SigninFragment.current_username, id_token, recyclerView, recyclerViewAdapter, getActivity(), progressBar));


        });

        button_easy.setOnClickListener(view -> {

            setupFilterButton(button_easy);
            progressBar.setVisibility(View.VISIBLE);
            UserSharedPreferences user_type = new UserSharedPreferences(getActivity());
            String id_token = user_type.getUser_type() + user_type.getId_token();
            new RoutesHTTP().getRoutesByLevel(id_token, "Easy", new GetAllRoutesCallback(SigninFragment.current_username, id_token, recyclerView, recyclerViewAdapter, getActivity(), progressBar));

        });

        button_medium.setOnClickListener(view -> {

            setupFilterButton(button_medium);
            progressBar.setVisibility(View.VISIBLE);
            UserSharedPreferences user_type = new UserSharedPreferences(getActivity());
            String id_token = user_type.getUser_type() + user_type.getId_token();
            new RoutesHTTP().getRoutesByLevel(id_token, "Medium", new GetAllRoutesCallback(SigninFragment.current_username, id_token, recyclerView, recyclerViewAdapter, getActivity(), progressBar));


        });

        button_hard.setOnClickListener(view -> {

            setupFilterButton(button_hard);
            progressBar.setVisibility(View.VISIBLE);
            UserSharedPreferences user_type = new UserSharedPreferences(getActivity());
            String id_token = user_type.getUser_type() + user_type.getId_token();
            new RoutesHTTP().getRoutesByLevel(id_token, "Hard", new GetAllRoutesCallback(SigninFragment.current_username, id_token, recyclerView, recyclerViewAdapter, getActivity(), progressBar));


        });

        button_extreme.setOnClickListener(view -> {

            setupFilterButton(button_extreme);
            progressBar.setVisibility(View.VISIBLE);
            UserSharedPreferences user_type = new UserSharedPreferences(getActivity());
            String id_token = user_type.getUser_type() + user_type.getId_token();
            new RoutesHTTP().getRoutesByLevel(id_token, "Extreme", new GetAllRoutesCallback(SigninFragment.current_username, id_token, recyclerView, recyclerViewAdapter, getActivity(), progressBar));


        });


    }

    private void setupFilterButton(Button button) {

        if (position_button.getCurrentTextColor() != button.getCurrentTextColor()) {

            button.setBackgroundResource(R.drawable.background_home_filters);
            button.setTextColor(getResources().getColor(R.color.natour_white));
            position_button.setBackgroundResource(R.drawable.background_filter_off);
            position_button.setTextColor(getResources().getColor(R.color.natour_grey));
            position_button = button;

        }

    }

}