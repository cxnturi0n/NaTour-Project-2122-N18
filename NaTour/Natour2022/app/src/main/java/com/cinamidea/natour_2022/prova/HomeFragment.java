package com.cinamidea.natour_2022.prova;

import android.os.Bundle;
import android.util.Log;
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
import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.navigation.main.RecyclerViewAdapter;
import com.cinamidea.natour_2022.utilities.UserType;
import com.cinamidea.natour_2022.utilities.auth.UserSharedPreferences;
import com.cinamidea.natour_2022.utilities.http.RoutesHTTP;
import com.cinamidea.natour_2022.utilities.http.callbacks.routes.GetAllRoutesCallback;

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
        Log.e("main",Thread.currentThread().getName());

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

        String id_token = user_type.getUserType() + user_type.getIdToken();
        presenter.getAllRoutesButtonClicked(id_token);

    }


    private void filterListeners() {

        button_all.setOnClickListener(view -> {

            setupFilterButton(button_all);
            getActivity().runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));

            String id_token = user_type.getUserType() + user_type.getIdToken();
            presenter.getAllRoutesButtonClicked(id_token);

        });

        button_easy.setOnClickListener(view -> {

            setupFilterButton(button_easy);
            getActivity().runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));

            String id_token = user_type.getUserType() + user_type.getIdToken();
            presenter.getRoutesByDifficultyButtonClicked(id_token, "Easy");

        });

        button_medium.setOnClickListener(view -> {

            setupFilterButton(button_medium);
            getActivity().runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));

            String id_token = user_type.getUserType() + user_type.getIdToken();
            presenter.getRoutesByDifficultyButtonClicked(id_token, "Medium");

        });

        button_hard.setOnClickListener(view -> {

            setupFilterButton(button_hard);
            getActivity().runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));

            String id_token = user_type.getUserType() + user_type.getIdToken();
            presenter.getRoutesByDifficultyButtonClicked(id_token, "Hard");

        });

        button_extreme.setOnClickListener(view -> {

            setupFilterButton(button_extreme);
            getActivity().runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));

            String id_token = user_type.getUserType() + user_type.getIdToken();
            presenter.getRoutesByDifficultyButtonClicked(id_token, "Extreme");

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
            Log.e("loadroutes",Thread.currentThread().getName());

        getActivity().runOnUiThread(() -> {
            Log.e("loadroutesinthr",Thread.currentThread().getName());
            progressBar.setVisibility(View.GONE);
            recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), routes, fav_routes, false);
            recyclerView.setAdapter(recyclerViewAdapter);
        });
    }
}