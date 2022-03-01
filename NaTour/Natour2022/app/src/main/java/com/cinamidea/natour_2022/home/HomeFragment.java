package com.cinamidea.natour_2022.home;

import android.content.Intent;
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

import com.cinamidea.natour_2022.MainActivity;
import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.navigation.main.recyclerview.RecyclerViewAdapter;
import com.cinamidea.natour_2022.utilities.UserType;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements HomeContract.View {

    private Button button_all, button_easy, button_medium, button_hard, button_extreme;
    private Button position_button;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private HomeContract.Presenter presenter;
    private UserType user_type;
    private ProgressBar progressBar;
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

        presenter = new HomePresenter(this);
        user_type = new UserType(getActivity());
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
        progressBar = view.findViewById(R.id.fragmentHome_progress);

        presenter.getAllRoutesButtonClicked(user_type.getUsername(), user_type.getUserType() + user_type.getIdToken());

    }


    private void filterListeners() {

        button_all.setOnClickListener(view -> {

            setupFilterButton(button_all);
            getActivity().runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));

            UserType type = new UserType(getContext());
            presenter.getAllRoutesButtonClicked(type.getUsername(), type.getIdToken());

        });

        button_easy.setOnClickListener(view -> {

            setupFilterButton(button_easy);
            getActivity().runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));

            presenter.getRoutesByDifficultyButtonClicked(user_type.getUsername(), user_type.getUserType() + user_type.getIdToken(), "Easy");

        });

        button_medium.setOnClickListener(view -> {

            setupFilterButton(button_medium);
            getActivity().runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));

            String id_token = user_type.getUserType() + user_type.getIdToken();
            presenter.getRoutesByDifficultyButtonClicked(user_type.getUsername(), id_token, "Medium");

        });

        button_hard.setOnClickListener(view -> {

            setupFilterButton(button_hard);
            getActivity().runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));

            String id_token = user_type.getUserType() + user_type.getIdToken();
            presenter.getRoutesByDifficultyButtonClicked(user_type.getUsername(),id_token, "Hard");

        });

        button_extreme.setOnClickListener(view -> {

            setupFilterButton(button_extreme);
            getActivity().runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));

            String id_token = user_type.getUserType() + user_type.getIdToken();
            presenter.getRoutesByDifficultyButtonClicked(user_type.getUsername(),id_token, "Extreme");

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

    @Override
    public void loadRoutes(ArrayList<Route> routes, ArrayList<Route> fav_routes) {
        getActivity().runOnUiThread(() -> {
            progressBar.setVisibility(View.GONE);
            recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), routes, fav_routes, false);
            recyclerView.setAdapter(recyclerViewAdapter);
        });
    }

    @Override
    public void displayError(String message) {
        //TODO ERROR
    }

    @Override
    public void logOutUnauthorizedUser() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}